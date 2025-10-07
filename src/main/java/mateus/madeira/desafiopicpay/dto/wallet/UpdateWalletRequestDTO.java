package mateus.madeira.desafiopicpay.dto.wallet;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import mateus.madeira.desafiopicpay.dto.validation.CpfCnpj;

public record UpdateWalletRequestDTO(
        String fullName,

        @CpfCnpj
        String cpfCnpj,

        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "Formato de e-mail inválido")
        String email,


        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "A senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma minúscula, um número e um caractere especial."
        )
        String password
) {
}
