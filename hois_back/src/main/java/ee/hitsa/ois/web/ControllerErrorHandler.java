package ee.hitsa.ois.web;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;

import ee.hitsa.ois.validation.ValidationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.annotation.JsonProperty;

import ee.hitsa.ois.util.EntityRemoveException;

@ControllerAdvice
public class ControllerErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(ControllerErrorHandler.class);

    @ExceptionHandler
    public ResponseEntity<ErrorInfo> handleException(Exception e) {
        HttpStatus status = null;
        ErrorInfo info = null;

        if(e instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        }else if(e instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
        }else if(e instanceof BindException) {
            info = ErrorInfo.fromErrors(((BindException) e).getBindingResult());
            status = HttpStatus.PRECONDITION_FAILED;
        }else if(e instanceof MethodArgumentNotValidException) {
            info = ErrorInfo.fromErrors(((MethodArgumentNotValidException) e).getBindingResult());
            status = HttpStatus.PRECONDITION_FAILED;
        }else if(e instanceof ValidationFailedException) {
            info = ErrorInfo.of(e.getMessage(), ((ValidationFailedException) e).getField());
            status = HttpStatus.PRECONDITION_FAILED;
        }else if(e instanceof EntityRemoveException) {
            String errorCode = e.getMessage();
            info = ErrorInfo.of(errorCode != null ? errorCode: "main.messages.record.referenced", null);
            // FIXME better status code?
            status = HttpStatus.PRECONDITION_FAILED;
        }else if(e instanceof DataIntegrityViolationException) {
            // TODO check real cause
            // if real cause is unique violation, report as "validation failed"
            // otherwise it's internal error - invalid data should not pass validation
            /*
            Throwable cause = ((DataIntegrityViolationException) e).getRootCause();
            if(cause instanceof PSQLException) {
                for(Throwable t : ((PSQLException)cause)) {
                    log.error("Root cause for data integrity exception: ", t);
                }
            } */

            status = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error("Data violation error occured during request handling:", e);
        }else if(e instanceof OptimisticLockingFailureException || e instanceof OptimisticLockException) {
            status = HttpStatus.CONFLICT;
        }else if(e instanceof AuthenticationException) {
            status = HttpStatus.FORBIDDEN;
        } else if(e instanceof HttpRequestMethodNotSupportedException) {
            status = HttpStatus.METHOD_NOT_ALLOWED;
        }else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error("Error occured during request handling:", e);
        }

        return new ResponseEntity<>(info, status);
    }

    // TODO error format
    public static class ErrorInfo {
        private final List<Error> errors;

        public ErrorInfo(List<Error> errors) {
            this.errors = errors;
        }

        @JsonProperty("_errors")
        public List<Error> getErrors() {
            return errors;
        }

        static ErrorInfo fromErrors(Errors errors) {
            List<Error> err = errors.getAllErrors().stream().map(e -> new Error(e.getCode(), e instanceof FieldError ? ((FieldError)e).getField() : null)).collect(Collectors.toList());
            return new ErrorInfo(err);
        }

        static ErrorInfo of(String code, String field) {
            return new ErrorInfo(Collections.singletonList(new Error(code, field)));
        }

        static class Error {
            private final String code;
            private final String field;

            public Error(String code, String field) {
                this.code = code;
                this.field = field;
            }

            public String getCode() {
                return code;
            }

            public String getField() {
                return field;
            }
        }
    }
}
