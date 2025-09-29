package mateus.madeira.desafiopicpay.controller;

import jakarta.validation.Valid;
import mateus.madeira.desafiopicpay.controller.dto.CreateWalletRequestDTO;
import mateus.madeira.desafiopicpay.entity.Wallet;
import mateus.madeira.desafiopicpay.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/wallets")
    public ResponseEntity<Wallet> createWallet(@RequestBody @Valid CreateWalletRequestDTO walletDTO) {
        Wallet createdWallet = walletService.createWallet(walletDTO);

        return ResponseEntity.ok(createdWallet);
    }
}
