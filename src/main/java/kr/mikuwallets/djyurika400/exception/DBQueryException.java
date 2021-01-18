package kr.mikuwallets.djyurika400.exception;

import org.springframework.http.HttpStatus;

public class DBQueryException extends DDJException {
    public DBQueryException(Exception e) {
        super(e);
    }
    public DBQueryException(String message) {
        super(message);
    }
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
