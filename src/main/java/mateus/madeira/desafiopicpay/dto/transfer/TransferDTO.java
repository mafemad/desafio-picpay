package mateus.madeira.desafiopicpay.dto.transfer;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferDTO(@DecimalMin("0.01") @NotNull BigDecimal value,
                          @NotNull Long payer,
                          @NotNull Long payee) {
}
