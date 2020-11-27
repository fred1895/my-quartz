package br.com.wod.quartz.resource.handle;

import br.com.wod.quartz.resource.exception.MySchedulerException;
import br.com.wod.quartz.resource.exception.QrtzObjectNotFoundException;
import br.com.wod.quartz.resource.exception.StandardError;
import br.com.wod.quartz.resource.exception.ValidationStandardError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class RestHandlerException {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<StandardError> handleResponseStatusException(ResponseStatusException e, HttpServletRequest request) {
        String message = e.getReason();
        HttpStatus httpStatus = e.getStatus();
        StandardError error = new StandardError(System.currentTimeMillis(), httpStatus.value(), httpStatus.name(), message);
        return ResponseEntity.status(httpStatus).body(error);
    }

    @ExceptionHandler(MySchedulerException.class)
    public ResponseEntity<StandardError> handleMySchedulerException(MySchedulerException e, HttpServletRequest request) {
        String message = e.getMessage();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError(System.currentTimeMillis(), httpStatus.value(), httpStatus.name(), message);
        return ResponseEntity.status(httpStatus).body(error);
    }

    @ExceptionHandler(QrtzObjectNotFoundException.class)
    public ResponseEntity<StandardError> handleObjectNotFoundException(QrtzObjectNotFoundException e, HttpServletRequest request) {
        String message = e.getMessage();
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        StandardError error = new StandardError(System.currentTimeMillis(), httpStatus.value(), httpStatus.name(), message);
        return ResponseEntity.status(httpStatus).body(error);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<StandardError> handleMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String message = "Wrong Http verb to this request: " + e.getMessage();
        HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        StandardError error = new StandardError(System.currentTimeMillis(), httpStatus.value(), httpStatus.name(), message);
        return ResponseEntity.status(httpStatus).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> handleMethodNotSupported(HttpMessageNotReadableException e, HttpServletRequest request) {
        String message = e.getMessage();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError(System.currentTimeMillis(), httpStatus.value(), httpStatus.name(), message);
        return ResponseEntity.status(httpStatus).body(error);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationStandardError> handleValidationError(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<String> messages = result.getAllErrors()
                .stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(toList());

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ValidationStandardError error = new ValidationStandardError(System.currentTimeMillis(), httpStatus.value(), httpStatus.name(), messages);
        return ResponseEntity.status(httpStatus).body(error);

    }

}
