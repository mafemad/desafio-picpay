package mateus.madeira.desafiopicpay.dto.auth;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(

        @NotEmpty(message = "email obrigatório")
        String email,
        @NotEmpty(message = "senha obrigatória")
        String password
) {
}
