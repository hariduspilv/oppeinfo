package ee.hitsa.ois.web.dto;

import java.io.Serializable;

public class UserProjection implements Serializable {

    private Long id;
    private String schoolCode;
    private String role;

    public UserProjection() {}

    public UserProjection(Long id, String schoolCode, String role) {
        this.id = id;
        this.schoolCode = schoolCode;
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
