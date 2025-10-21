package mateus.madeira.desafiopicpay.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mateus.madeira.desafiopicpay.dto.wallet.*;
import mateus.madeira.desafiopicpay.entity.Wallet;
import mateus.madeira.desafiopicpay.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("wallets")
@Tag(name = "Wallets", description = "Endpoints para gerenciamento de carteiras")
@SecurityRequirement(name = "bearerAuth")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }


    @Operation(summary = "Listar todas as carteiras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de carteiras recuperada",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = WalletResponseDTO.class)))),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<WalletResponseDTO>> getAllWallets() {
        var wallets = walletService.getAll();

        return ResponseEntity.ok(wallets);
    }

    @Operation(summary = "Buscar carteira por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carteira encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WalletResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Carteira não encontrada (WalletNotFoundException)", content = @Content)
    })
    @GetMapping("/{walletId}")
    public ResponseEntity<WalletResponseDTO> getWallet(
            @Parameter(description = "ID da carteira a ser buscada", required = true, example = "1")
            @PathVariable("walletId") Long walletId) {
        return ResponseEntity.ok(walletService.getWalletById(walletId));
    }


    @Operation(summary = "Realizar um depósito em uma carteira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Depósito realizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Wallet.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (ex: valor negativo ou nulo)", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Carteira não encontrada (WalletNotFoundException)", content = @Content)
    })
    @PostMapping("/{walletId}/deposit")
    public ResponseEntity<WalletResponseDTO> deposit(
            @Parameter(description = "ID da carteira que receberá o depósito", required = true, example = "1")
            @PathVariable("walletId") Long walletId,
            @RequestBody @Valid WalletDepositDTO walletDepositDTO ) {

        var wallet = walletService.deposit(walletDepositDTO, walletId);

        return ResponseEntity.ok(wallet);

    }


    @Operation(summary = "Realizar um saque de uma carteira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saque realizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Wallet.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (ex: valor negativo ou nulo)", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Carteira não encontrada (WalletNotFoundException)", content = @Content),
            @ApiResponse(responseCode = "422", description = "Saldo insuficiente (InsuficientBalanceException)", content = @Content)
    })
    @PostMapping("/{walletId}/withdraw")
    public ResponseEntity<WalletResponseDTO> withdraw(
            @Parameter(description = "ID da carteira para o saque", required = true, example = "1")
            @PathVariable("walletId") Long walletId,
            @RequestBody @Valid WalletWithdrawDTO walletWithdrawDTO ) {


        var wallet = walletService.withdraw(walletId, walletWithdrawDTO);

        return ResponseEntity.ok(wallet);

    }


    @Operation(summary = "Atualizar dados de uma carteira (parcial)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carteira atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WalletResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (ex: email fora de formato, senha fraca)", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Carteira não encontrada (WalletNotFoundException)", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflito de dados (Email ou CPF/CNPJ já existe) (WalletDataAlreadyExistsException)", content = @Content)
    })
    @PatchMapping("/{walletId}")
    public ResponseEntity<WalletResponseDTO> updateWallet(
            @Parameter(description = "ID da carteira a ser atualizada", required = true, example = "1")
            @PathVariable("walletId") Long walletId,
            @RequestBody @Valid UpdateWalletRequestDTO updateDto) {
        var updatedWallet = walletService.updateWallet(walletId, updateDto);
        return ResponseEntity.ok(updatedWallet);
    }

    @Operation(summary = "Excluir uma carteira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carteira excluída com sucesso", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Carteira não encontrada (WalletNotFoundException)", content = @Content),
            @ApiResponse(responseCode = "422", description = "Não é possível excluir carteira com saldo (WalletHasBalanceException)", content = @Content)
    })
    @DeleteMapping("/{walletId}")
    public ResponseEntity<Void> deleteWallet(
            @Parameter(description = "ID da carteira a ser excluída", required = true, example = "1")
            @PathVariable("walletId") Long walletId) {
        walletService.deleteWallet(walletId);
        return ResponseEntity.noContent().build();
    }
}
