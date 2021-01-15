package kr.mikuwallets.djyurika400.exception;

import org.springframework.http.HttpStatus;

public class ShellCommandException extends DDJException {
    public ShellCommandException(Exception e) {
        super(e);
    }
    public ShellCommandException(String message) {
        super(message);
    }
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
