package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.web.dto.subject.subjectprogram.SubjectProgramResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class SubjectStudyPeriodSearchDto {

    private Long id;
    private Long studentsNumber;
    private Long hours;
    private List<String> teachers;
    private List<SubjectProgramResult> programs;
    private SubjectProgramResult jointProgram;
    private AutocompleteResult subject;
    private AutocompleteResult studyPeriod;
    private Set<AutocompleteResult> midtermTasks;
    private Boolean isPracticeSubject;
    private Long moodleCourseId;
    private SubjectProgramResult program;
    private Integer subgroups;
    private BigDecimal credits;
    private String studentgroups;

    private Boolean canEdit;
    private Boolean canEditJointProgram;

    public Boolean getIsPracticeSubject() {
        return isPracticeSubject;
    }

    public void setIsPracticeSubject(Boolean isPracticeSubject) {
        this.isPracticeSubject = isPracticeSubject;
    }

    public Set<AutocompleteResult> getMidtermTasks() {
        return midtermTasks;
    }

    public void setMidtermTasks(Set<AutocompleteResult> midtermTasks) {
        this.midtermTasks = midtermTasks;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutocompleteResult getSubject() {
        return subject;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }

    public List<SubjectProgramResult> getPrograms() {
        return programs;
    }

    public void setPrograms(List<SubjectProgramResult> programs) {
        this.programs = programs;
    }

    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }

    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(AutocompleteResult studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public Long getStudentsNumber() {
        return studentsNumber;
    }

    public void setStudentsNumber(Long studentsNumber) {
        this.studentsNumber = studentsNumber;
    }

    public Long getMoodleCourseId() {
        return moodleCourseId;
    }

    public void setMoodleCourseId(Long moodleCourseId) {
        this.moodleCourseId = moodleCourseId;
    }

    public SubjectProgramResult getProgram() {
        return program;
    }

    public void setProgram(SubjectProgramResult program) {
        this.program = program;
    }

    public Integer getSubgroups() {
        return subgroups;
    }

    public void setSubgroups(Integer subgroups) {
        this.subgroups = subgroups;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public String getStudentgroups() {
        return studentgroups;
    }

    public void setStudentgroups(String studentgroups) {
        this.studentgroups = studentgroups;
    }

    public SubjectProgramResult getJointProgram() {
        return jointProgram;
    }

    public void setJointProgram(SubjectProgramResult jointProgram) {
        this.jointProgram = jointProgram;
    }

    public Boolean getCanEditJointProgram() {
        return canEditJointProgram;
    }

    public void setCanEditJointProgram(Boolean canEditJointProgram) {
        this.canEditJointProgram = canEditJointProgram;
    }
}
