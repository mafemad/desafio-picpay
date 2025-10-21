package mateus.madeira.desafiopicpay.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import mateus.madeira.desafiopicpay.dto.validation.CpfCnpj;
import mateus.madeira.desafiopicpay.entity.Wallet;
import mateus.madeira.desafiopicpay.entity.WalletType;

public record CreateWalletRequestDTO(
        @Schema(description = "Nome completo do usuário", example = "John Doe")
        @NotBlank String fullName,

        @Schema(description = "CPF ou CNPJ do usuário (deve ser único)", example = "123.456.789-00")
        @NotBlank @CpfCnpj String cpfCnpj,

        @Schema(description = "Email do usuário (deve ser único)", example = "john.doe@example.com")
        @NotBlank
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "Formato de e-mail inválido")
        String email,

        @Schema(description = "Senha (mín. 8 caracteres, 1 maiúscula, 1 minúscula, 1 número, 1 especial)",
                example = "StrongP@ss123")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "A senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma minúscula, um número e um caractere especial."
        )
        @NotBlank String password,

        @Schema(description = "Tipo de carteira (USER ou MERCHANT)", example = "USER")
        @NotNull WalletType.WalletTypeEnum walletType
) {
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