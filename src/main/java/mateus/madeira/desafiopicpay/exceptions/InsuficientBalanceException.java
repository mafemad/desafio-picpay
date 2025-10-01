package mateus.madeira.desafiopicpay.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

import java.math.BigDecimal;

public class InsuficientBalanceException extends PicPayException {

    private BigDecimal trasnferValue;
    private BigDecimal senderBalance;

    public InsuficientBalanceException(BigDecimal transferValue, BigDecimal senderBalance) {
        this.trasnferValue = transferValue;
        this.senderBalance = senderBalance;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pd.setTitle("Insuficient Balance");
        pd.setDetail("you cannot transfer an amount: " + this.trasnferValue+
                "bigger than your current balance: "  + this.senderBalance+".");
        return pd;
    }
}
