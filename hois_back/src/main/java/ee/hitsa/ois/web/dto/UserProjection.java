package ee.hitsa.ois.web.dto;

import java.io.Serializable;

public class UserProjection implements Serializable {

    private final Long id;
    private final String schoolCode;
    private final String role;
    private final String nameEt;
    private final String nameEn;
    private final String studentName;
    private final String studentGroup;

    public UserProjection(Long id, String schoolCode, String role, String nameEt, String nameEn,  String studentName, String studentGroup) {
        this.id = id;
        this.schoolCode = schoolCode;
        this.role = role;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.studentName = studentName;
        this.studentGroup = studentGroup;
    }

    public Long getId() {
        return id;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public String getRole() {
        return role;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }    
}
