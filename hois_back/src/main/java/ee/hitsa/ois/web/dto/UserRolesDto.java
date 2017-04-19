package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.UserRoleDefault;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.UserForm;

import java.util.List;

public class UserRolesDto {

    private List<UserForm.UserRight> userRoles;
    private List<UserRoleRightDefaultDto> defaultRights;

    public static UserRolesDto of(List<String> objects, List<UserRoleDefault> userRoleDefaults) {
        UserRolesDto dto = new UserRolesDto();
        dto.userRoles = StreamUtil.toMappedList(UserForm.UserRight::of, objects);
        dto.defaultRights = StreamUtil.toMappedList(UserRoleRightDefaultDto::of, userRoleDefaults);
        return dto;
    }

    public List<UserForm.UserRight> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserForm.UserRight> userRoles) {
        this.userRoles = userRoles;
    }

    public List<UserRoleRightDefaultDto> getDefaultRights() {
        return defaultRights;
    }

    public void setDefaultRights(List<UserRoleRightDefaultDto> defaultRights) {
        this.defaultRights = defaultRights;
    }

    public static class UserRoleRightDefaultDto {
        private String roleCode;
        private String data;

        public static UserRoleRightDefaultDto of(UserRoleDefault userRoleDefaults) {
            UserRoleRightDefaultDto dto = EntityUtil.bindToDto(userRoleDefaults, new UserRoleRightDefaultDto());
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
}
