package mateus.madeira.desafiopicpay.controller.dto;

import mateus.madeira.desafiopicpay.entity.Wallet;
import mateus.madeira.desafiopicpay.entity.WalletType;

import java.math.BigDecimal;

public record WalletResponseDTO(
        Long id,
        String fullName,
        String cpfCnpj,
        String email,
        BigDecimal balance,
        WalletType walletType
) {

    public WalletResponseDTO(Wallet wallet) {
        this(wallet.getId(),
                wallet.getFullName(),
                wallet.getCpfCnpj(),
                wallet.getEmail(),
                wallet.getBalance(),
                wallet.getWalletType());
    }
}
