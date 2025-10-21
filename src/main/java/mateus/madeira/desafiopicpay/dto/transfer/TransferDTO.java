package mateus.madeira.desafiopicpay.dto.transfer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferDTO(
        @Schema(description = "Valor da transferência (deve ser no mínimo 0.01)",
                example = "150.25")
        @DecimalMin("0.01")
        @NotNull
        BigDecimal value,

        @Schema(description = "ID da carteira do pagador (quem envia o dinheiro)",
                example = "1")
        @NotNull
        Long payer,

        @Schema(description = "ID da carteira do recebedor (quem recebe o dinheiro)",
                example = "2")
        @NotNull
        Long payee
) {
}
