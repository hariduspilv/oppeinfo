package ee.hitsa.ois.web.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.commandobject.ProtocolStudentForm;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class HigherProtocolStudentDto extends VersionedCommand implements ProtocolStudentForm {

    @NotNull
    private Long id;
    @ClassifierRestriction(MainClassCode.KORGHINDAMINE)
    private String grade;
    private List<ProtocolPracticeJournalResultDto> practiceJournalResults = new ArrayList<>();
    private Long studentId;
    private AutocompleteResult student;
    @Size(max = 255)
    private String addInfo;
    private Boolean canEdit;

    public static HigherProtocolStudentDto of(ProtocolStudent protocolStudent) {
        HigherProtocolStudentDto s = new HigherProtocolStudentDto();
        EntityUtil.bindToDto(protocolStudent, s, "student");
        Student student = protocolStudent.getStudent();
        String name = PersonUtil.fullname(student.getPerson());
        s.setStudent(new AutocompleteResult(EntityUtil.getId(student), name, name));
        return s;
    }

    public List<ProtocolPracticeJournalResultDto> getPracticeJournalResults() {
        return practiceJournalResults;
    }

    public void setPracticeJournalResults(List<ProtocolPracticeJournalResultDto> practiceJournalResults) {
        this.practiceJournalResults = practiceJournalResults;
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

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

}
