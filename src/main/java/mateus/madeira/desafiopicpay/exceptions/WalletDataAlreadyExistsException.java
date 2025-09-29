package mateus.madeira.desafiopicpay.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class WalletDataAlreadyExistsException extends PicPayException{

    private String detail;

    public WalletDataAlreadyExistsException(String detail) {
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pd.setTitle("Wallet Data already exists");
        pd.setDetail(detail);

        return pd;
    }
}

