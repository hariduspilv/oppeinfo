package ee.hitsa.ois.web.dto;

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
    private String gradeValue;
    private String practiceResult;
    private Long studentId;
    private AutocompleteResult student;
    @Size(max = 255)
    private String addInfo;
    
    public static HigherProtocolStudentDto of(ProtocolStudent protocolStudent) {
        HigherProtocolStudentDto s = new HigherProtocolStudentDto();
        EntityUtil.bindToDto(protocolStudent, s, "student");
        Student student = protocolStudent.getStudent();
        String name = PersonUtil.fullname(student.getPerson());
        s.setStudent(new AutocompleteResult(EntityUtil.getId(student), name, name));
        return s;
    }
    
    public String getPracticeResult() {
        return practiceResult;
    }

    public void setPracticeResult(String practiceResult) {
        this.practiceResult = practiceResult;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
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
}
