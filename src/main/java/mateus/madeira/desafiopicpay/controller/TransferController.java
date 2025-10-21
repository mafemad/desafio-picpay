package mateus.madeira.desafiopicpay.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mateus.madeira.desafiopicpay.dto.transfer.TransferDTO;
import mateus.madeira.desafiopicpay.entity.Transfer;
import mateus.madeira.desafiopicpay.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Transfers", description = "Endpoint para realizar transferências")
@SecurityRequirement(name = "bearerAuth")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }


    @Operation(summary = "Realizar uma nova transferência",
            description = "Inicia uma transferência de valor entre duas carteiras (pagador e recebedor).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferência realizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transfer.class))),

            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição (ex: valor nulo/negativo, IDs nulos)",
                    content = @Content),

            @ApiResponse(responseCode = "401", description = "Não autorizado (Token JWT inválido ou ausente)",
                    content = @Content),

            @ApiResponse(responseCode = "403", description = "Transferência não autorizada pelo serviço externo",
                    content = @Content),

            @ApiResponse(responseCode = "404", description = "Carteira do pagador ou recebedor não encontrada",
                    content = @Content),

            @ApiResponse(responseCode = "409", description = "Transferência duplicada detectada nos últimos 60 segundos",
                    content = @Content),

            @ApiResponse(responseCode = "422", description = "Erro de regra de negócio (Saldo insuficiente ou Tipo de carteira não permitido para transferir)",
                    content = @Content)
    })
    @PostMapping("/transfer")
    public ResponseEntity<Transfer> transfer(@RequestBody @Valid TransferDTO transferDto) {
        var resp = transferService.transfer(transferDto);
        return ResponseEntity.ok(resp);
    }
}
