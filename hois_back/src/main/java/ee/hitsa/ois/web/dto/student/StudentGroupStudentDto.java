package ee.hitsa.ois.web.dto.student;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentGroupStudentDto {

    private Long id;
    private String fullname;
    private String idcode;
    private AutocompleteResult curriculumVersion;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public AutocompleteResult getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(AutocompleteResult curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static StudentGroupStudentDto of(HoisUserDetails user, Student student) {
        StudentGroupStudentDto dto = new StudentGroupStudentDto();
        Person p = student.getPerson();
        dto.setFullname(PersonUtil.fullnameOptionalGuest(p.getFullname(), EntityUtil.getNullableCode(student.getType())));
        if (user.isSchoolAdmin() || user.isLeadingTeacher() || user.isTeacher()) {
            dto.setId(student.getId());
            dto.setIdcode(p.getIdcode());
        }
        dto.setCurriculumVersion(AutocompleteResult.of(student.getCurriculumVersion()));
        dto.setStatus(EntityUtil.getCode(student.getStatus()));
        return dto;
    }
}
