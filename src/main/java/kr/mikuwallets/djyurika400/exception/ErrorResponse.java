package kr.mikuwallets.djyurika400.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private Date timestamp;
    private int status;
    private String message;
    private String error;
    private String path;

    public ErrorResponse(DDJException e) {
        HttpStatus httpStatus = e.getHttpStatus();
        this.timestamp = e.getTimestamp();
        this.message = e.getMessage();
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
    }

    public ErrorResponse(DDJException e, HttpServletRequest request) {
        this(e);
        this.path = request.getServletPath();
    }

    public ErrorResponse(MaxUploadSizeExceededException e) {
        HttpStatus httpStatus = HttpStatus.PAYLOAD_TOO_LARGE;
        this.timestamp = new Date();
        this.message = e.getMessage();
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
    }

    public ErrorResponse(MaxUploadSizeExceededException e, HttpServletRequest request) {
        this(e);
        this.path = request.getServletPath();
    }

    public ErrorResponse(MaxUploadSizeExceededException e, HttpServletRequest request, String message) {
        this(e);
        this.path = request.getServletPath();
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
