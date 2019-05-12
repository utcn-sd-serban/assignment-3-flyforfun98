package ro.utcn.sd.flav.stackoverflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.utcn.sd.flav.stackoverflow.dto.ErrorDTO;

@Component
@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorDTO handleException(Exception ex){
        return new ErrorDTO("Exception");
    }
}
