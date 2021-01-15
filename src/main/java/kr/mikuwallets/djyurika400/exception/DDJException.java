package kr.mikuwallets.djyurika400.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public abstract class DDJException extends RuntimeException {
    private Date timestamp = new Date();
    protected DDJException(Exception e) {
        super(e);
    }
    protected DDJException(String message) {
        super(message);
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public abstract HttpStatus getHttpStatus();
}
