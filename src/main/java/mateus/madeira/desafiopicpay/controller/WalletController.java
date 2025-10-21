package mateus.madeira.desafiopicpay.controller;

import jakarta.validation.Valid;
import mateus.madeira.desafiopicpay.dto.wallet.*;
import mateus.madeira.desafiopicpay.entity.Wallet;
import mateus.madeira.desafiopicpay.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public ResponseEntity<List<WalletResponseDTO>> getAllWallets() {
        var wallets = walletService.getAll();

        return ResponseEntity.ok(wallets);
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<WalletResponseDTO> getWallet(@PathVariable("walletId") Long walletId) {
        return ResponseEntity.ok(walletService.getWalletById(walletId));
    }

    @PostMapping("/{walletId}/deposit")
    public ResponseEntity<Wallet> deposit(@PathVariable("walletId") Long walletId,
                                          @RequestBody @Valid WalletDepositDTO walletDepositDTO ) {
        var wallet = walletService.deposit(walletDepositDTO, walletId);

        return ResponseEntity.ok(wallet);

    }

    @PostMapping("/{walletId}/withdraw")
    public ResponseEntity<Wallet> withdraw(@PathVariable("walletId") Long walletId,
                                          @RequestBody @Valid WalletWithdrawDTO walletWithdrawDTO ) {
        var wallet = walletService.withdraw(walletId, walletWithdrawDTO);

        return ResponseEntity.ok(wallet);

    }

    @PatchMapping("/{walletId}")
    public ResponseEntity<WalletResponseDTO> updateWallet(@PathVariable("walletId") Long walletId,
                                                          @RequestBody @Valid UpdateWalletRequestDTO updateDto) {
        var updatedWallet = walletService.updateWallet(walletId, updateDto);
        return ResponseEntity.ok(updatedWallet);
    }

    @DeleteMapping("/{walletId}")
    public ResponseEntity<Void> deleteWallet(@PathVariable("walletId") Long walletId) {
        walletService.deleteWallet(walletId);
        return ResponseEntity.noContent().build();
    }
}
