package ee.hitsa.ois.web.dto.directive;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class DirectiveDto extends DirectiveForm {

    private Long id;
    private String status;
    private LocalDateTime inserted;
    private String insertedBy;
    private Boolean canEditDirective;

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

    public Boolean getCanEditDirective() {
        return canEditDirective;
    }

    public void setCanEditDirective(Boolean canEditDirective) {
        this.canEditDirective = canEditDirective;
    }

    public static DirectiveDto of(Directive directive) {
        DirectiveDto dto = EntityUtil.bindToDto(directive, new DirectiveDto(), "students");
        dto.setStudents(StreamUtil.nullSafeList(directive.getStudents()).stream().sorted(Comparator.comparing(DirectiveStudent::getId))
                .map(DirectiveStudentDto::of).collect(Collectors.toList()));
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

        public static DirectiveCancelDto of(Directive directive, List<Long> changedStudents) {
            DirectiveCancelDto dto = EntityUtil.bindToDto(directive, new DirectiveCancelDto(), "students");
            Directive canceled = directive.getCanceledDirective();
            dto.setCanceledDirectiveData(new AutocompleteResult(canceled.getId(), canceled.getHeadline(), null));
            dto.setCanceledDirectiveType(EntityUtil.getCode(canceled.getType()));
            dto.setCanceledStudents(StreamUtil.toMappedList(DirectiveViewStudentDto::of, canceled.getStudents()));
            dto.setChangedStudents(changedStudents);
            dto.setSelectedStudents(StreamUtil.toMappedList(ds -> EntityUtil.getId(ds.getStudent()), directive.getStudents()));
            return dto;
        }
    }
}
