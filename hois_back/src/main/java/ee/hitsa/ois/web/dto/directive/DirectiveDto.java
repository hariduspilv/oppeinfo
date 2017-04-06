package ee.hitsa.ois.web.dto.directive;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

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
        if(DirectiveType.KASKKIRI_TYHIST.name().equals(EntityUtil.getCode(directive.getType()))) {
            return DirectiveCancelDto.of(directive);
        }

        DirectiveDto dto = EntityUtil.bindToDto(directive, new DirectiveDto(), "students");
        dto.setStudents(directive.getStudents().stream().map(DirectiveStudentDto::of).collect(Collectors.toList()));
        return dto;
    }

    public static class DirectiveCancelDto extends DirectiveDto {
        private AutocompleteResult canceledDirectiveData;
        private String canceledDirectiveType;
        private List<DirectiveViewStudentDto> canceledStudents;
        private List<Long> changedStudents;

        public AutocompleteResult getCanceledDirectiveData() {
            return canceledDirectiveData;
        }

        public void setCanceledDirectiveData(AutocompleteResult canceledDirectiveData) {
            this.canceledDirectiveData = canceledDirectiveData;
        }

        public String getCanceledDirectiveType() {
            return canceledDirectiveType;
        }

        public void setCanceledDirectiveType(String canceledDirectiveType) {
            this.canceledDirectiveType = canceledDirectiveType;
        }

        public List<DirectiveViewStudentDto> getCanceledStudents() {
            return canceledStudents;
        }

        public void setCanceledStudents(List<DirectiveViewStudentDto> canceledStudents) {
            this.canceledStudents = canceledStudents;
        }

        public List<Long> getChangedStudents() {
            return changedStudents;
        }

        public void setChangedStudents(List<Long> changedStudents) {
            this.changedStudents = changedStudents;
        }

        public static DirectiveCancelDto of(Directive directive) {
            DirectiveCancelDto dto = EntityUtil.bindToDto(directive, new DirectiveCancelDto(), "students");
            Directive canceled = directive.getCanceledDirective();
            dto.setCanceledDirectiveData(new AutocompleteResult(canceled.getId(), canceled.getHeadline(), null));
            dto.setCanceledDirectiveType(EntityUtil.getCode(canceled.getType()));
            dto.setCanceledStudents(canceled.getStudents().stream().map(DirectiveViewStudentDto::of).collect(Collectors.toList()));
            // TODO fetch list of students which cannot canceled
            // dto.setChangedStudents()
            return dto;
        }
    }
}
