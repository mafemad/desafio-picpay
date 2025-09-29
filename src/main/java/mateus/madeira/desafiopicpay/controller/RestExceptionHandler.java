package mateus.madeira.desafiopicpay.controller;

import mateus.madeira.desafiopicpay.exceptions.PicPayException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(PicPayException.class)
    public ProblemDetail handlePicPayException(PicPayException ex){
        return ex.toProblemDetail();
    }
}
