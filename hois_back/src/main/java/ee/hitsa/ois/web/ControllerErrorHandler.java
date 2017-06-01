package ee.hitsa.ois.web;

import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;

import org.postgresql.util.PSQLException;
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
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.annotation.JsonProperty;

import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.EntityRemoveException;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;

@ControllerAdvice
public class ControllerErrorHandler {
    private static final String POSTGRESQL_UNIQUE_VIOLATION = "23505";
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @ExceptionHandler
    public ResponseEntity<ErrorInfo> handleException(Exception e) {
        HttpStatus status = null;
        ErrorInfo info = null;

        if(e instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        }else if(e instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
        }else if(e instanceof AssertionFailedException) {
            status = HttpStatus.BAD_REQUEST;
            log.error("Assertion failure:", e);
        }else if(e instanceof BindException) {
            info = ErrorInfo.of(((BindException) e).getBindingResult());
            status = HttpStatus.PRECONDITION_FAILED;
        }else if(e instanceof MethodArgumentNotValidException) {
            info = ErrorInfo.of(((MethodArgumentNotValidException) e).getBindingResult());
            status = HttpStatus.PRECONDITION_FAILED;
        }else if(e instanceof MissingServletRequestParameterException) {
            info = ErrorInfo.of("Missing", ((MissingServletRequestParameterException) e).getParameterName());
            status = HttpStatus.PRECONDITION_FAILED;
        }else if(e instanceof ValidationFailedException) {
            info = ((ValidationFailedException) e).getErrorInfo();
            status = HttpStatus.PRECONDITION_FAILED;
        }else if(e instanceof EntityRemoveException) {
            String errorCode = e.getMessage();
            info = ErrorInfo.of(errorCode != null ? errorCode: "main.messages.record.referenced", null);
            // FIXME better status code?
            status = HttpStatus.PRECONDITION_FAILED;
        }else if(e instanceof DataIntegrityViolationException) {
            // if real cause is unique violation, report as "validation failed"
            // otherwise it's internal error - invalid data should not pass validation
            Throwable cause = ((DataIntegrityViolationException) e).getRootCause();
            if(cause instanceof SQLException && POSTGRESQL_UNIQUE_VIOLATION.equals(((SQLException)cause).getSQLState())) {
                status = HttpStatus.PRECONDITION_FAILED;
                info = uniqueViolation((SQLException) cause);
            }

            if(status == null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                log.error("Data violation error occured during request handling:", e);
            }
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

        e.printStackTrace();
        return new ResponseEntity<>(info, status);
    }

    private static ErrorInfo uniqueViolation(SQLException cause) {
        String tableName = null;
        if(cause instanceof PSQLException && ((PSQLException)cause).getServerErrorMessage() != null) {
            tableName = ((PSQLException)cause).getServerErrorMessage().getTable();
        }
        String msgId = UNIQUE_VIOLATION_MESSAGES.getOrDefault(tableName, "main.messages.error.unique");
        return ErrorInfo.of(msgId, null);
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

        public static ErrorInfo of(Errors errors) {
            List<Error> err = StreamUtil.toMappedList(e -> new ErrorForField(e.getCode(), e instanceof FieldError ? ((FieldError)e).getField() : null), errors.getAllErrors());
            return new ErrorInfo(err);
        }

        public static ErrorInfo of(String code, String field) {
            return new ErrorInfo(Collections.singletonList(new ErrorForField(code, field)));
        }

        public static ErrorInfo of(String code) {
            return new ErrorInfo(Collections.singletonList(new Error(code)));
        }

        public static ErrorInfo of(List<Map.Entry<String, String>> errors) {
            return new ErrorInfo(StreamUtil.toMappedList(me -> new ErrorForField(me.getValue(), me.getKey()), errors));
        }

        public static class Error {
            private final String code;

            public Error(String code) {
                this.code = code;
            }

            public String getCode() {
                return code;
            }

        }

        public static class ErrorForField extends Error {
            private final String field;

            public ErrorForField(String code, String field) {
                super(code);
                this.field = field;
            }

            public String getField() {
                return field;
            }
        }


    }

    private static Map<String, String> UNIQUE_VIOLATION_MESSAGES = new HashMap<>();
    static {
        UNIQUE_VIOLATION_MESSAGES.put("building", "building.alreadyexist");
        UNIQUE_VIOLATION_MESSAGES.put("directive_coordinator", "directive.coordinator.alreadyexist");
        UNIQUE_VIOLATION_MESSAGES.put("directive_student", "directive.student.alreadyexist");
        UNIQUE_VIOLATION_MESSAGES.put("room", "room.alreadyexist");
        UNIQUE_VIOLATION_MESSAGES.put("school", "school.alreadyexist");
        UNIQUE_VIOLATION_MESSAGES.put("student_representative", "student.representative.alreadyexist");
        UNIQUE_VIOLATION_MESSAGES.put("student_representative_application", "student.representative.application.alreadyexist");
        UNIQUE_VIOLATION_MESSAGES.put("subject", "subject.alreadyexist");
        UNIQUE_VIOLATION_MESSAGES.put("teacher", "teacher.person.alreadyexist");
    }
}
