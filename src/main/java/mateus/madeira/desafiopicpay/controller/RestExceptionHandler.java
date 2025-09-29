package mateus.madeira.desafiopicpay.controller;

import mateus.madeira.desafiopicpay.exceptions.PicPayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(PicPayException.class)
    public ProblemDetail handlePicPayException(PicPayException ex){
        return ex.toProblemDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){

        var fieldErros = ex.getFieldErrors()
                .stream()
                .map(fe -> new InvalidParam(fe.getField(), fe.getDefaultMessage()))
                .toList();

        var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("your request parameters didn't validate");
        pd.setProperty("invalid params", fieldErros);

        return pd;
    }

    private record InvalidParam(String name, String reason){}
}
