package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.PracticeJournal;
import ee.hitsa.ois.domain.PracticeJournalEvaluation;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PracticeJournalUserRights;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.practice.PracticeEvaluationCriteriaDto;
import ee.hitsa.ois.web.dto.practice.PracticeEvaluationDto;

public class PracticeJournalDto extends VersionedCommand {

    private Long id;
    private String status;
    private AutocompleteResult student;
    private AutocompleteResult studentGroup;
    private AutocompleteResult school;
    private AutocompleteResult studentCurriculumVersion;
    private String studentStudyForm;
    private AutocompleteResult module;
    private AutocompleteResult theme;
    private List<PracticeEvaluationCriteriaDto> studentPracticeEvalCriteria = new ArrayList<>();
    private List<PracticeEvaluationCriteriaDto> supervisorPracticeEvalCriteria = new ArrayList<>();
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
    private List<PracticeJournalFileDto> practiceJournalStudentFiles = new ArrayList<>();
    private List<PracticeJournalModuleSubjectDto> moduleSubjects;
    private Boolean canEdit;
    private Boolean canDelete;
    private Boolean canConfirm;
    private AutocompleteResult subject;
    private Boolean isHigher;
    private AutocompleteResult practiceEvaluation;
    private Boolean canAddEntries;

    public static PracticeJournalDto of(PracticeJournal practiceJournal) {
        PracticeJournalDto dto = EntityUtil.bindToDto(practiceJournal, new PracticeJournalDto(), "contract",
                "practiceJournalEntries", "practiceJournalFiles", "moduleSubjects", "practiceEvaluation", "studentGroup");
        dto.setStudentGroup(AutocompleteResult.of(practiceJournal.getStudent().getStudentGroup()));
        dto.setContract(ContractDto.of(practiceJournal.getContract()));
        if (practiceJournal.getPracticeEvaluation() != null) {
            dto.setPracticeEvaluation(AutocompleteResult.of(practiceJournal.getPracticeEvaluation()));
        }
        dto.setPracticeJournalEntries(
                StreamUtil.toMappedList(PracticeJournalEntryDto::of, practiceJournal.getPracticeJournalEntries()));
        List<PracticeJournalFileDto> files = StreamUtil.toMappedList(PracticeJournalFileDto::of, practiceJournal.getPracticeJournalFiles());
        dto.setPracticeJournalFiles(
                files.stream().filter(p -> p.getIsStudent() == null || p.getIsStudent().booleanValue() == false).collect(Collectors.toList()));
        dto.setPracticeJournalStudentFiles(
                files.stream().filter(p -> p.getIsStudent() != null && p.getIsStudent().booleanValue() == true).collect(Collectors.toList()));
        dto.setModuleSubjects(StreamUtil.toMappedList(PracticeJournalModuleSubjectDto::of, practiceJournal.getModuleSubjects()));
        dto.getModuleSubjects().sort(Comparator.comparing(ms -> ms.getModule() != null ? ms.getModule().getNameEt() : ms.getSubject().getNameEt(), 
                String.CASE_INSENSITIVE_ORDER));
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

    public List<PracticeJournalModuleSubjectDto> getModuleSubjects() {
        return moduleSubjects;
    }

    public void setModuleSubjects(List<PracticeJournalModuleSubjectDto> moduleSubjects) {
        this.moduleSubjects = moduleSubjects;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }
    
    public Boolean getCanConfirm() {
        return canConfirm;
    }

    public void setCanConfirm(Boolean canConfirm) {
        this.canConfirm = canConfirm;
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

    public List<PracticeEvaluationCriteriaDto> getStudentPracticeEvalCriteria() {
        return studentPracticeEvalCriteria;
    }

    public void setStudentPracticeEvalCriteria(List<PracticeEvaluationCriteriaDto> studentPracticeEvalCriteria) {
        this.studentPracticeEvalCriteria = studentPracticeEvalCriteria;
    }

    public List<PracticeEvaluationCriteriaDto> getSupervisorPracticeEvalCriteria() {
        return supervisorPracticeEvalCriteria;
    }

    public void setSupervisorPracticeEvalCriteria(List<PracticeEvaluationCriteriaDto> supervisorPracticeEvalCriteria) {
        this.supervisorPracticeEvalCriteria = supervisorPracticeEvalCriteria;
    }

    public List<PracticeJournalFileDto> getPracticeJournalStudentFiles() {
        return practiceJournalStudentFiles;
    }

    public void setPracticeJournalStudentFiles(List<PracticeJournalFileDto> practiceJournalStudentFiles) {
        this.practiceJournalStudentFiles = practiceJournalStudentFiles;
    }

    public AutocompleteResult getPracticeEvaluation() {
        return practiceEvaluation;
    }

    public void setPracticeEvaluation(AutocompleteResult practiceEvaluation) {
        this.practiceEvaluation = practiceEvaluation;
    }

    public AutocompleteResult getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(AutocompleteResult studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Boolean getCanAddEntries() {
        return canAddEntries;
    }

    public void setCanAddEntries(Boolean canAddEntries) {
        this.canAddEntries = canAddEntries;
    }
}
