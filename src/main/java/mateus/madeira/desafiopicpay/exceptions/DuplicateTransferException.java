package mateus.madeira.desafiopicpay.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class DuplicateTransferException extends PicPayException {
    private String message;

    public DuplicateTransferException(String message) {
        this.message = message;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        pd.setTitle("Transfer already made");
        pd.setDetail(message);
        return pd;
    }
}
