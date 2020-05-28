package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import ee.hitsa.ois.domain.Declaration;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

public class DeclarationDto {

    private Long id;
    private AutocompleteResult studyPeriod;
    private Short course;
    private StudentSearchDto student;
    private String status;
    private BigDecimal credits;
    private LocalDateTime inserted;
    private LocalDate confirmDate;
    private String confirmer;
    private Set<DeclarationSubjectDto> subjects;
    private Boolean canBeChanged;
    private Boolean canBeSetUnconfirmed;
    private Boolean canBeSetConfirmed;
    private Boolean isPrevious;

    public static DeclarationDto of(Declaration declaration) {
        DeclarationDto dto = new DeclarationDto();
        EntityUtil.bindToDto(declaration, dto, "studyPeriod", "student", "subjects");
        dto.setStudyPeriod(AutocompleteResult.ofWithYear(declaration.getStudyPeriod()));
        dto.setSubjects(StreamUtil.toMappedSet(DeclarationSubjectDto::of, declaration.getSubjects()));
        StudentSearchDto student = new StudentSearchDto();
        student.setId(EntityUtil.getId(declaration.getStudent()));
        student.setFullname(declaration.getStudent().getPerson().getFullname());
        student.setIdcode(declaration.getStudent().getPerson().getIdcode());
        student.setStatus(EntityUtil.getNullableCode(declaration.getStudent().getStatus()));
        student.setType(EntityUtil.getNullableCode(declaration.getStudent().getType()));
        CurriculumVersion cv = declaration.getStudent().getCurriculumVersion();
        if (cv != null) student.setCurriculumVersion(new AutocompleteResult(cv.getId(), cv.getCode(), cv.getCode()));
        
        StudentGroup sg = declaration.getStudent().getStudentGroup();
        if(sg != null) {
            dto.setCourse(sg.getCourse());
            student.setStudentGroup(AutocompleteResult.of(sg));
        }
        dto.setStudent(student);
        dto.setIsPrevious(Boolean.valueOf(declaration.getStudyPeriod().getEndDate().isBefore(LocalDate.now())));
        return dto;
    }

    public Boolean getCanBeSetConfirmed() {
        return canBeSetConfirmed;
    }

    public void setCanBeSetConfirmed(Boolean canBeSetConfirmed) {
        this.canBeSetConfirmed = canBeSetConfirmed;
    }

    public Boolean getCanBeChanged() {
        return canBeChanged;
    }

    public void setCanBeChanged(Boolean canBeChanged) {
        this.canBeChanged = canBeChanged;
    }

    public Boolean getCanBeSetUnconfirmed() {
        return canBeSetUnconfirmed;
    }

    public void setCanBeSetUnconfirmed(Boolean canBeSetUnconfirmed) {
        this.canBeSetUnconfirmed = canBeSetUnconfirmed;
    }

    public Short getCourse() {
        return course;
    }

    public void setCourse(Short course) {
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(AutocompleteResult studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public StudentSearchDto getStudent() {
        return student;
    }

    public void setStudent(StudentSearchDto student) {
        this.student = student;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getConfirmer() {
        return confirmer;
    }

    public void setConfirmer(String confirmer) {
        this.confirmer = confirmer;
    }

    public Set<DeclarationSubjectDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<DeclarationSubjectDto> subjects) {
        this.subjects = subjects;
    }

    public Boolean getIsPrevious() {
        return isPrevious;
    }

    public void setIsPrevious(Boolean isPrevious) {
        this.isPrevious = isPrevious;
    }
}
