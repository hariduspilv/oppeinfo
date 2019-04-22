package ee.hitsa.ois.web.dto.timetable;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StudentUtil;

public class JournalStudentDto {

    private Long id;
    private Long studentId;
    private String firstname;
    private String lastname;
    private String fullname;
    private String studentGroup;
    private String curriculum;
    private Boolean isMoodleRegistered;
    private String status;
    private Boolean isIndividualCurriculum;
    private List<JournalStudentApelResultDto> apelResults;
    private List<JournalStudentRemarkDto> remarks;

    private Boolean canEdit;

    public static JournalStudentDto of(Student student) {
        JournalStudentDto dto = new JournalStudentDto();
        dto.setStudentId(student.getId());
        
        Person person = student.getPerson();
        dto.setFirstname(person.getFirstname());
        dto.setLastname(person.getLastname());
        dto.setFullname(PersonUtil.fullname(person));
        
        if (student.getStudentGroup() != null) {
            dto.setStudentGroup(student.getStudentGroup().getCode());
        }
        if (student.getCurriculumVersion() != null) {
            CurriculumVersion cv = student.getCurriculumVersion();
            dto.setCurriculum(CurriculumUtil.versionName(cv.getCode(), cv.getCurriculum().getNameEt()));
            dto.setIsIndividualCurriculum(cv.getIndividual());
        }
        dto.setStatus(EntityUtil.getCode(student.getStatus()));
        dto.setCanEdit(Boolean.valueOf(StudentUtil.isActive(student)));
        return dto;
    }

    public static JournalStudentDto of(JournalStudent journalStudent) {
        JournalStudentDto dto = of(journalStudent.getStudent());
        dto.setId(journalStudent.getId());
        dto.setIsMoodleRegistered(journalStudent.getIsMoodleRegistered());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public Boolean getIsMoodleRegistered() {
        return isMoodleRegistered;
    }

    public void setIsMoodleRegistered(Boolean isMoodleRegistered) {
        this.isMoodleRegistered = isMoodleRegistered;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsIndividualCurriculum() {
        return isIndividualCurriculum;
    }

    public void setIsIndividualCurriculum(Boolean isIndividualCurriculum) {
        this.isIndividualCurriculum = isIndividualCurriculum;
    }

    public List<JournalStudentApelResultDto> getApelResults() {
        return apelResults != null ?  apelResults : (apelResults = new ArrayList<>());
    }

    public void setApelResults(List<JournalStudentApelResultDto> apelResults) {
        this.apelResults = apelResults;
    }

    public List<JournalStudentRemarkDto> getRemarks() {
        return remarks != null ? remarks : (remarks = new ArrayList<>());
    }

    public void setRemarks(List<JournalStudentRemarkDto> remarks) {
        this.remarks = remarks;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }
}
