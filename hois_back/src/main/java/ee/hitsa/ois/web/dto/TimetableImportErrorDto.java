package ee.hitsa.ois.web.dto;

import java.util.HashSet;
import java.util.Set;

import ee.hitsa.ois.web.dto.timetable.NameAndCode;

public class TimetableImportErrorDto {
    
    Set<NameAndCode> errors = new HashSet<>();

    public Set<NameAndCode> getErrors() {
        return errors;
    }

    public void setErrors(Set<NameAndCode> errors) {
        this.errors = errors;
    }
}
