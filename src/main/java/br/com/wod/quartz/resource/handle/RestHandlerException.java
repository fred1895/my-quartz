package br.com.wod.quartz.resource.handle;

import br.com.wod.quartz.resource.exception.MySchedulerException;
import br.com.wod.quartz.resource.exception.StandardError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestHandlerException {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<StandardError> handleResponseStatusException(ResponseStatusException e, HttpServletRequest request) {
        String message = e.getReason();
        HttpStatus httpStatus = e.getStatus();
        StandardError error = new StandardError(httpStatus.value(), message, System.currentTimeMillis());
        return ResponseEntity.status(httpStatus).body(error);
    }

    @ExceptionHandler(MySchedulerException.class)
    public ResponseEntity<StandardError> handleResponseStatusException(MySchedulerException e, HttpServletRequest request) {
        String message = e.getMessage();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError(httpStatus.value(), message, System.currentTimeMillis());
        return ResponseEntity.status(httpStatus).body(error);
    }

}
