package mateus.madeira.desafiopicpay.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @Schema(description = "Email de login do usuário", example = "john.doe@example.com")
        @NotEmpty(message = "email obrigatório")
        String email,

        @Schema(description = "Senha de login do usuário", example = "StrongP@ss123")
        @NotEmpty(message = "senha obrigatória")
        String password
) {
}
