package jp.co.axa.apidemo.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exception handler Class<BR>
 * Common controller exceptions will be handled by this class.
 *
 * @author Panchal.Baswaraj
 * @version 2022/02/28
 */
@ControllerAdvice
public class ApiExceptionsHandler extends ResponseEntityExceptionHandler {

    /**
     * To handle any exception.
     *
     * @return ResponseEntity - returns exception details and internal server error in case of exception.
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception exception, WebRequest request) {
        String errorMessageDescription = exception.getLocalizedMessage();
        if(errorMessageDescription == null) errorMessageDescription = exception.toString();
        return new ResponseEntity<>(
                errorMessageDescription, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
