package ee.hitsa.ois.web.dto.directive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class DirectiveViewDto {

    private Long id;
    private String headline;
    private String type;
    private String directiveNr;
    private LocalDate confirmDate;
    private String addInfo;
    private String status;
    private AutocompleteResult directiveCoordinator;
    private String preamble;
    private AutocompleteResult canceledDirective;
    private String canceledDirectiveType;
    private String scholarshipType;
    private LocalDateTime inserted;
    private List<DirectiveViewStudentDto> students;
    private List<DirectiveSearchDto> cancelingDirectives;
    private Boolean userCanCancel;
    private Boolean userCanEdit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirectiveNr() {
        return directiveNr;
    }

    public void setDirectiveNr(String directiveNr) {
        this.directiveNr = directiveNr;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AutocompleteResult getDirectiveCoordinator() {
        return directiveCoordinator;
    }

    public void setDirectiveCoordinator(AutocompleteResult directiveCoordinator) {
        this.directiveCoordinator = directiveCoordinator;
    }

    public String getPreamble() {
        return preamble;
    }

    public void setPreamble(String preamble) {
        this.preamble = preamble;
    }

    public AutocompleteResult getCanceledDirective() {
        return canceledDirective;
    }

    public void setCanceledDirective(AutocompleteResult canceledDirective) {
        this.canceledDirective = canceledDirective;
    }

    public String getCanceledDirectiveType() {
        return canceledDirectiveType;
    }

    public void setCanceledDirectiveType(String canceledDirectiveType) {
        this.canceledDirectiveType = canceledDirectiveType;
    }

    public String getScholarshipType() {
        return scholarshipType;
    }

    public void setScholarshipType(String scholarshipType) {
        this.scholarshipType = scholarshipType;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }

    public List<DirectiveViewStudentDto> getStudents() {
        return students;
    }

    public void setStudents(List<DirectiveViewStudentDto> students) {
        this.students = students;
    }

    public List<DirectiveSearchDto> getCancelingDirectives() {
        return cancelingDirectives;
    }

    public void setCancelingDirectives(List<DirectiveSearchDto> cancelingDirectives) {
        this.cancelingDirectives = cancelingDirectives;
    }

    public Boolean getUserCanCancel() {
        return userCanCancel;
    }

    public void setUserCanCancel(Boolean userCanCancel) {
        this.userCanCancel = userCanCancel;
    }

    public Boolean getUserCanEdit() {
        return userCanEdit;
    }

    public void setUserCanEdit(Boolean userCanEdit) {
        this.userCanEdit = userCanEdit;
    }

    public static DirectiveViewDto of(Directive directive, Set<Long> filteredStudentId, boolean onlyValid) {
        DirectiveViewDto dto = EntityUtil.bindToDto(directive, new DirectiveViewDto(), "students");
        dto.setDirectiveCoordinator(directive.getDirectiveCoordinator() != null ? AutocompleteResult.of(directive.getDirectiveCoordinator()) : null);

        Stream<DirectiveStudent> students;
        Directive canceled = directive.getCanceledDirective();
        if(canceled != null) {
            dto.setCanceledDirective(new AutocompleteResult(canceled.getId(), canceled.getHeadline(), null));
            dto.setCanceledDirectiveType(EntityUtil.getCode(canceled.getType()));
            Set<Long> studentsOnDirective = StreamUtil.toMappedSet(r -> EntityUtil.getId(r.getStudent()), directive.getStudents());
            if(filteredStudentId != null) {
                studentsOnDirective.retainAll(filteredStudentId);
            }
            students = canceled.getStudents().stream().filter(r-> studentsOnDirective.contains(EntityUtil.getId(r.getStudent())));
        } else{
            students = directive.getStudents().stream().filter(r -> filteredStudentId == null || filteredStudentId.contains(EntityUtil.getId(r.getStudent())));
        }
        if(onlyValid) {
            // do not show canceled rows of directive
            students = students.filter(ds -> !Boolean.TRUE.equals(ds.getCanceled()));
        }
        dto.setStudents(StreamUtil.toMappedList(DirectiveViewStudentDto::of, students));
        return dto;
    }
}
