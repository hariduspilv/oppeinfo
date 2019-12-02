package ee.hitsa.ois.web.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.UserSchoolRole;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.UserSchoolRoleForm;

public class UserSchoolRoleDto extends UserSchoolRoleForm {

    private Long id;
    private Long schoolId;

    public static UserSchoolRoleDto of(UserSchoolRole role) {
        UserSchoolRoleDto dto = EntityUtil.bindToDto(role, new UserSchoolRoleDto(), "school", "teacherOccupation", "rights", "users");
        dto.setSchoolId(EntityUtil.getId(role.getSchool()));
        if (role.getTeacherOccupation() != null) {
            dto.setTeacherOccupation(AutocompleteResult.of(role.getTeacherOccupation()));
        }

        Map<String, List<String>> rights = role.getRights().stream().collect(
                Collectors.groupingBy(r -> EntityUtil.getCode(r.getObject()),
                        Collectors.mapping(r -> EntityUtil.getCode(r.getPermission()), Collectors.toList())));
        dto.setRights(rights);
        return dto;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }
}
