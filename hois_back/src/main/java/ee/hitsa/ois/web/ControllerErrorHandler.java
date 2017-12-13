package ee.hitsa.ois.web;

import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;
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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.exception.EntityRemoveException;
import ee.hitsa.ois.exception.SingleMessageWithParamsException;
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

        if (e instanceof PersistenceException && e.getCause() instanceof Exception) {
            e = (Exception)e.getCause();
        }

        if (e instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (e instanceof AssertionFailedException) {
            status = HttpStatus.BAD_REQUEST;
            log.error("Assertion failure:", e);
        } else if (e instanceof BindException) {
            info = ErrorInfo.of(((BindException) e).getBindingResult());
            status = HttpStatus.PRECONDITION_FAILED;
        } else if (e instanceof MethodArgumentNotValidException) {
            info = ErrorInfo.of(((MethodArgumentNotValidException) e).getBindingResult());
            status = HttpStatus.PRECONDITION_FAILED;
        } else if (e instanceof MissingServletRequestParameterException) {
            info = ErrorInfo.of("Missing", ((MissingServletRequestParameterException) e).getParameterName());
            status = HttpStatus.PRECONDITION_FAILED;
        } else if (e instanceof ValidationFailedException) {
            info = ((ValidationFailedException) e).getErrorInfo();
            status = HttpStatus.PRECONDITION_FAILED;
        } else if (e instanceof EntityRemoveException) {
            String errorCode = e.getMessage();
            info = ErrorInfo.of(errorCode != null ? errorCode : "main.messages.record.referenced", (String)null);
            // FIXME better status code?
            status = HttpStatus.PRECONDITION_FAILED;
        } else if (e instanceof DataIntegrityViolationException || e instanceof ConstraintViolationException) {
            // if real cause is unique violation, report as "validation failed"
            // otherwise it's internal error - invalid data should not pass
            // validation
            Throwable cause = e instanceof DataIntegrityViolationException ? ((DataIntegrityViolationException) e).getRootCause() : e.getCause();
            if (cause instanceof SQLException
                    && POSTGRESQL_UNIQUE_VIOLATION.equals(((SQLException) cause).getSQLState())) {
                info = uniqueViolation((SQLException) cause);
                status = HttpStatus.PRECONDITION_FAILED;
            }

            if (status == null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                log.error("Data violation error occured during request handling:", e);
            }
        } else if (e instanceof OptimisticLockingFailureException || e instanceof OptimisticLockException) {
            status = HttpStatus.CONFLICT;
        } else if (e instanceof AuthenticationException) {
            status = HttpStatus.FORBIDDEN;
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            status = HttpStatus.METHOD_NOT_ALLOWED;
        }  else if (e instanceof SingleMessageWithParamsException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            SingleMessageWithParamsException singleMessageWithParamsException = (SingleMessageWithParamsException)e;
            info = ErrorInfo.of(singleMessageWithParamsException.getMessage(), singleMessageWithParamsException.getParams());
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error("Error occured during request handling:", e);
        }

        return new ResponseEntity<>(info, status);
    }

    private static ErrorInfo uniqueViolation(SQLException cause) {
        String tableName = null;
        if (cause instanceof PSQLException && ((PSQLException) cause).getServerErrorMessage() != null) {
            tableName = ((PSQLException) cause).getServerErrorMessage().getTable();
        }
        String msgId = UNIQUE_VIOLATION_MESSAGES.getOrDefault(tableName, "main.messages.error.unique");
        return ErrorInfo.of(msgId, (String)null);
    }

    // TODO error format
    public static class ErrorInfo {
        private final List<Error> errors;

        public ErrorInfo(List<? extends Error> errors) {
            this.errors = new ArrayList<>(errors != null ? errors : Collections.emptyList());
        }

        @JsonProperty("_errors")
        public List<Error> getErrors() {
            return errors;
        }

        public static ErrorInfo of(Errors errors) {
            List<Error> err = StreamUtil.toMappedList(
                    e -> new ErrorForField(e.getCode(), e instanceof FieldError ? ((FieldError) e).getField() : null),
                    errors.getAllErrors());
            return new ErrorInfo(err);
        }

        public static ErrorInfo of(String code, String field) {
            return new ErrorInfo(Collections.singletonList(new ErrorForField(code, field)));
        }

        public static ErrorInfo of(String code) {
            return new ErrorInfo(Collections.singletonList(new Error(code)));
        }

        public static ErrorInfo of(String code, Map<Object, Object> params) {
            return new ErrorInfo(Collections.singletonList(new Error(code, params)));
        }

        public static ErrorInfo of(List<? extends Error> errors) {
            return new ErrorInfo(errors);
        }

        // TODO rename to avoid name clash with java.lang.Error
        @JsonInclude(value = Include.NON_NULL)
        public static class Error {
            private final String code;
            private final Map<Object, Object> params;

            public Error(String code) {
                this.code = code;
                this.params = null;
            }

            public Error(String code, Map<Object, Object> params) {
                this.code = code;
                this.params = params;
            }

            public String getCode() {
                return code;
            }

            public Map<Object, Object> getParams() {
                return params;
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
        UNIQUE_VIOLATION_MESSAGES.put("student_representative_application",
                "student.representative.application.alreadyexist");
        UNIQUE_VIOLATION_MESSAGES.put("subject", "subject.alreadyexist");
        UNIQUE_VIOLATION_MESSAGES.put("teacher", "teacher.person.alreadyexist");
    }
}
