package mateus.madeira.desafiopicpay.controller;

import jakarta.validation.Valid;
import mateus.madeira.desafiopicpay.controller.dto.CreateWalletRequestDTO;
import mateus.madeira.desafiopicpay.controller.dto.WalletDepositDTO;
import mateus.madeira.desafiopicpay.controller.dto.WalletWithdrawDTO;
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

    @PostMapping()
    public ResponseEntity<Wallet> createWallet(@RequestBody @Valid CreateWalletRequestDTO walletDTO) {
        Wallet createdWallet = walletService.createWallet(walletDTO);

        return ResponseEntity.ok(createdWallet);
    }

    @GetMapping
    public ResponseEntity<List<Wallet>> getAllWallets() {
        var wallets = walletService.getAll();

        return ResponseEntity.ok(wallets);
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
}
