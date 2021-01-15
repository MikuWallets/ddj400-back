package kr.mikuwallets.djyurika400.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({DDJException.class})
    public ResponseEntity<ErrorResponse> handleFloException(DDJException e, HttpServletRequest req) {
        return new ResponseEntity<>(new ErrorResponse(e, req), e.getHttpStatus());
    }
}
