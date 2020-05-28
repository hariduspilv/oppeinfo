package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodExam;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.ProtocolUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.commandobject.ProtocolStudentForm;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class HigherProtocolStudentDto extends VersionedCommand implements ProtocolStudentForm {

    @NotNull
    private Long id;
    @ClassifierRestriction(MainClassCode.KORGHINDAMINE)
    private String grade;
    private List<ProtocolPracticeJournalResultDto> practiceJournalResults = new ArrayList<>();
    private List<HigherProtocolModuleSubjectResultDto> subjectResults = new ArrayList<>();
    private Long studentId;
    private AutocompleteResult student;
    private String studentGroup;
    @Size(max = 255)
    private String addInfo;
    private AutocompleteResult subgroup;
    private LocalDate examDate;
    private String studentStatus;

    private Boolean canBeDeleted;
    private Boolean canChangeGrade;

    public static HigherProtocolStudentDto of(ProtocolStudent protocolStudent) {
        HigherProtocolStudentDto s = new HigherProtocolStudentDto();
        EntityUtil.bindToDto(protocolStudent, s, "student");
        Student student = protocolStudent.getStudent();
        if (student != null && student.getStudentGroup() != null) {
            s.setStudentGroup(student.getStudentGroup().getCode());
        }
        String name = PersonUtil.fullnameOptionalGuest(student);
        s.setStudent(new AutocompleteResult(EntityUtil.getId(student), name, name));

        SubjectStudyPeriodExam exam = protocolStudent.getSubjectStudyPeriodExamStudent() != null
                ?  protocolStudent.getSubjectStudyPeriodExamStudent().getSubjectStudyPeriodExam() : null;
        s.setExamDate(exam != null ? exam.getTimetableEvent().getStart().toLocalDate() : null);
        s.setStudentStatus(EntityUtil.getCode(protocolStudent.getStudent().getStatus()));

        s.setCanChangeGrade(Boolean.valueOf(ProtocolUtil.studentGradeCanBeChanged(protocolStudent)));
        s.setCanBeDeleted(Boolean.valueOf(!ProtocolUtil.hasGrade(protocolStudent))); // different from other protocols
        return s;
    }

    public static HigherProtocolStudentDto ofWithSubgroups(ProtocolStudent protocolStudent, DeclarationSubject declarationSubject) {
        HigherProtocolStudentDto dto = of(protocolStudent);
        if (declarationSubject != null && declarationSubject.getSubgroup() != null) {
            dto.setSubgroup(AutocompleteResult.of(declarationSubject.getSubgroup()));
        }
        return dto;
    }

    public List<ProtocolPracticeJournalResultDto> getPracticeJournalResults() {
        return practiceJournalResults;
    }

    public void setPracticeJournalResults(List<ProtocolPracticeJournalResultDto> practiceJournalResults) {
        this.practiceJournalResults = practiceJournalResults;
    }

    public List<HigherProtocolModuleSubjectResultDto> getSubjectResults() {
        return subjectResults;
    }

    public void setSubjectResults(List<HigherProtocolModuleSubjectResultDto> subjectResults) {
        this.subjectResults = subjectResults;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public AutocompleteResult getStudent() {
        return student;
    }

    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public AutocompleteResult getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(AutocompleteResult subgroup) {
        this.subgroup = subgroup;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }

    public Boolean getCanBeDeleted() {
        return canBeDeleted;
    }

    public void setCanBeDeleted(Boolean canBeDeleted) {
        this.canBeDeleted = canBeDeleted;
    }

    public Boolean getCanChangeGrade() {
        return canChangeGrade;
    }

    public void setCanChangeGrade(Boolean canChangeGrade) {
        this.canChangeGrade = canChangeGrade;
    }
}
