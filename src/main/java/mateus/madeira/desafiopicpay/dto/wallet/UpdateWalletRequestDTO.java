package mateus.madeira.desafiopicpay.dto.wallet;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import mateus.madeira.desafiopicpay.dto.validation.CpfCnpj;

public record UpdateWalletRequestDTO(
        @Schema(description = "Novo nome completo do usuário", example = "John A. Doe")
        String fullName,

        @Schema(description = "Novo CPF ou CNPJ (será validado)", example = "98765432100")
        @CpfCnpj
        String cpfCnpj,

        @Schema(description = "Novo email do usuário (deve ser válido)", example = "john.new@example.com")
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "Formato de e-mail inválido")
        String email,

        @Schema(description = "Nova senha (mín. 8 caracteres, 1 maiúscula, 1 minúscula, 1 número, 1 especial)", example = "StrongP@ss123")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "A senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma minúscula, um número e um caractere especial."
        )
        String password
) {}
