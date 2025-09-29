package mateus.madeira.desafiopicpay.controller.dto;

import mateus.madeira.desafiopicpay.entity.Wallet;
import mateus.madeira.desafiopicpay.entity.WalletType;

public record CreateWalletRequestDTO(String fullName,
                                     String cpfCnpj,
                                     String email,
                                     String password,
                                     WalletType.WalletTypeEnum walletType) {

    public Wallet toWallet() {
        return new Wallet(
                fullName,
                cpfCnpj,
                email,
                password,
                walletType.getWalletType()
        );
    }
}
