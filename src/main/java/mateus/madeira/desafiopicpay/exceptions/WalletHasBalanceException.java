package mateus.madeira.desafiopicpay.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class WalletHasBalanceException extends PicPayException{
    private Long id;

    public WalletHasBalanceException(Long id){
        this.id = id;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);

        pd.setTitle("Wallet cannot be deleted");
        pd.setDetail("Wallet with id" + id.toString() + "has balance and cannot be deleted");

        return pd;
    }
}
