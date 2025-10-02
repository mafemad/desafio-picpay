package mateus.madeira.desafiopicpay.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WalletDepositDTO(@NotNull @DecimalMin("0.01") BigDecimal amount) {
}
