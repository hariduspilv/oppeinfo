package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.UserRoleDefault;
import ee.hitsa.ois.util.EntityUtil;

public class UserRolesDto {
    private String roleCode;
    private String data;

    public static UserRolesDto of(UserRoleDefault userRoleDefault) {
        UserRolesDto dto = EntityUtil.bindToDto(userRoleDefault, new UserRolesDto());
        return dto;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
