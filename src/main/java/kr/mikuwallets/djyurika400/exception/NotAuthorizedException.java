package kr.mikuwallets.djyurika400.exception;

import org.springframework.http.HttpStatus;

public class NotAuthorizedException extends DDJException {
    public NotAuthorizedException(Exception e) {
        super(e);
    }
    public NotAuthorizedException(String message) {
        super(message);
    }
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
