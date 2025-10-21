package mateus.madeira.desafiopicpay.dto.wallet;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WalletWithdrawDTO(
        @Schema(description = "Valor a ser sacado (deve ser maior que 0.00)", example = "50.25")
        @NotNull(message = "O valor não pode ser nulo")
        @DecimalMin(value = "0.01", message = "O valor do saque deve ser no mínimo 0.01")
        BigDecimal amount
) {}
