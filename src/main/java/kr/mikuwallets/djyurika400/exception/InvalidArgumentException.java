package kr.mikuwallets.djyurika400.exception;

import org.springframework.http.HttpStatus;

public class InvalidArgumentException extends DDJException {
    public InvalidArgumentException(Exception e) {
        super(e);
    }

    public InvalidArgumentException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
