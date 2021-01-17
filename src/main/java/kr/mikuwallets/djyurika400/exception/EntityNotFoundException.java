package kr.mikuwallets.djyurika400.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends DDJException {
    public EntityNotFoundException(Exception e) {
        super(e);
    }
    public EntityNotFoundException(String message) {
        super(message);
    }
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
