package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.domain.PracticeJournal;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class PracticeJournalDto extends VersionedCommand {

    private Long id;
    private String status;
    private AutocompleteResult student;
    private AutocompleteResult school;
    private AutocompleteResult studentCurriculumVersion;
    private String studentStudyForm;
    private AutocompleteResult module;
    private AutocompleteResult theme;
    private BigDecimal credits;
    private Short hours;
    private LocalDate startDate;
    private LocalDate endDate;
    private String practicePlace;
    private AutocompleteResult teacher;
    private String practicePlan;
    private String practiceReport;
    private String supervisorComment;
    private String supervisorOpinion;
    private String teacherComment;
    private String teacherOpinion;
    private String grade;
    private ContractDto contract;
    private List<PracticeJournalEntryDto> practiceJournalEntries;
    private List<PracticeJournalFileDto> practiceJournalFiles;
    private Boolean canDelete;
    private AutocompleteResult subject;
    private Boolean isHigher;

    public static PracticeJournalDto of(PracticeJournal practiceJournal) {
        PracticeJournalDto dto = EntityUtil.bindToDto(practiceJournal, new PracticeJournalDto(), "contract",
                "practiceJournalEntries", "practiceJournalFiles");
        dto.setContract(ContractDto.of(practiceJournal.getContract()));
        dto.setPracticeJournalEntries(
                StreamUtil.toMappedList(PracticeJournalEntryDto::of, practiceJournal.getPracticeJournalEntries()));
        dto.setPracticeJournalFiles(
                StreamUtil.toMappedList(PracticeJournalFileDto::of, practiceJournal.getPracticeJournalFiles()));
        dto.setStudentCurriculumVersion(AutocompleteResult.of(practiceJournal.getStudent().getCurriculumVersion()));
        dto.setStudentStudyForm(EntityUtil.getCode(practiceJournal.getStudent().getStudyForm()));
        dto.setIsHigher(Boolean.valueOf(StudentUtil.isHigher(practiceJournal.getStudent())));
        return dto;
    }

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

    public AutocompleteResult getStudent() {
        return student;
    }

    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }

    public AutocompleteResult getStudentCurriculumVersion() {
        return studentCurriculumVersion;
    }

    public void setStudentCurriculumVersion(AutocompleteResult studentCurriculumVersion) {
        this.studentCurriculumVersion = studentCurriculumVersion;
    }

    public String getStudentStudyForm() {
        return studentStudyForm;
    }

    public void setStudentStudyForm(String studentStudyForm) {
        this.studentStudyForm = studentStudyForm;
    }

    public AutocompleteResult getModule() {
        return module;
    }

    public void setModule(AutocompleteResult module) {
        this.module = module;
    }

    public AutocompleteResult getTheme() {
        return theme;
    }

    public void setTheme(AutocompleteResult theme) {
        this.theme = theme;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public Short getHours() {
        return hours;
    }

    public void setHours(Short hours) {
        this.hours = hours;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getPracticePlace() {
        return practicePlace;
    }

    public void setPracticePlace(String practicePlace) {
        this.practicePlace = practicePlace;
    }

    public AutocompleteResult getTeacher() {
        return teacher;
    }

    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }

    public String getPracticePlan() {
        return practicePlan;
    }

    public void setPracticePlan(String practicePlan) {
        this.practicePlan = practicePlan;
    }

    public String getPracticeReport() {
        return practiceReport;
    }

    public void setPracticeReport(String practiceReport) {
        this.practiceReport = practiceReport;
    }

    public String getSupervisorComment() {
        return supervisorComment;
    }

    public void setSupervisorComment(String supervisorComment) {
        this.supervisorComment = supervisorComment;
    }

    public String getSupervisorOpinion() {
        return supervisorOpinion;
    }

    public void setSupervisorOpinion(String supervisorOpinion) {
        this.supervisorOpinion = supervisorOpinion;
    }

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }

    public String getTeacherOpinion() {
        return teacherOpinion;
    }

    public void setTeacherOpinion(String teacherOpinion) {
        this.teacherOpinion = teacherOpinion;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public ContractDto getContract() {
        return contract;
    }

    public void setContract(ContractDto contract) {
        this.contract = contract;
    }

    public List<PracticeJournalEntryDto> getPracticeJournalEntries() {
        return practiceJournalEntries;
    }

    public void setPracticeJournalEntries(List<PracticeJournalEntryDto> practiceJournalEntries) {
        this.practiceJournalEntries = practiceJournalEntries;
    }

    public List<PracticeJournalFileDto> getPracticeJournalFiles() {
        return practiceJournalFiles;
    }

    public void setPracticeJournalFiles(List<PracticeJournalFileDto> practiceJournalFiles) {
        this.practiceJournalFiles = practiceJournalFiles;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }

    public AutocompleteResult getSubject() {
        return subject;
    }

    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }

    public Boolean getIsHigher() {
        return isHigher;
    }

    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }

    public AutocompleteResult getSchool() {
        return school;
    }

    public void setSchool(AutocompleteResult school) {
        this.school = school;
    }
}
