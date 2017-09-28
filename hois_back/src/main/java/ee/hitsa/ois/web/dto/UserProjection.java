package ee.hitsa.ois.web.dto;

import java.io.Serializable;

public class UserProjection implements Serializable {

    private final Long id;
    private final String schoolCode;
    private final String role;

    public UserProjection(Long id, String schoolCode, String role) {
        this.id = id;
        this.schoolCode = schoolCode;
        this.role = role;
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
}
