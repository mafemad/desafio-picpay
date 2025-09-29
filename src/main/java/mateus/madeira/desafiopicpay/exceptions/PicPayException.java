package mateus.madeira.desafiopicpay.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class PicPayException extends RuntimeException{

    public ProblemDetail toProblemDetail(){
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        pd.setTitle("PicPay Internal Server Error");

        return pd;
    }
}
