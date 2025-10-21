package mateus.madeira.desafiopicpay.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import mateus.madeira.desafiopicpay.dto.validation.CpfCnpj;
import mateus.madeira.desafiopicpay.entity.Wallet;
import mateus.madeira.desafiopicpay.entity.WalletType;

public record CreateWalletRequestDTO(@NotBlank String fullName,

                                     @NotBlank @CpfCnpj String cpfCnpj,

                                     @NotBlank
                                     @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                             message = "Formato de e-mail inválido")
                                     String email,

                                     @Pattern(
                                             regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                                             message = "A senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma minúscula, um número e um caractere especial."
                                     )
                                     @NotBlank String password,

                                     @NotNull WalletType.WalletTypeEnum walletType) {

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
