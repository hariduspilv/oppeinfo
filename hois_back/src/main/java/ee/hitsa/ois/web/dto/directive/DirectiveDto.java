package ee.hitsa.ois.web.dto.directive;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm;

public class DirectiveDto extends DirectiveForm {

    private Long id;
    private String status;
    private LocalDateTime inserted;
    private String insertedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }

    public String getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }

    public static DirectiveDto of(Directive directive) {
        DirectiveDto dto = EntityUtil.bindToDto(directive, new DirectiveDto(), "students");
        dto.setStudents(directive.getStudents().stream().map(DirectiveStudentDto::of).collect(Collectors.toList()));
        return dto;
    }
}
