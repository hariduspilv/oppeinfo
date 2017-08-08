package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.UserForm;

import java.util.Collection;
import java.util.HashSet;

public class UserDto extends UserForm {

    private Long id;
    private PersonMinDto person;

    public static UserDto of(User user, Collection<Classifier> allByMainClassCode) {
        UserDto dto = EntityUtil.bindToDto(user, new UserDto());
        dto.person = EntityUtil.bindToDto(user.getPerson(), new PersonMinDto());
        if (user.getSchool() != null) {
            dto.setSchool(AutocompleteResult.of(user.getSchool()));
        }
        if (allByMainClassCode != null) {
            dto.setRights(StreamUtil.toMappedSet(it -> {
                UserRight userRight = new UserRight();
                userRight.setObject(it.getCode());
                user.getUserRights().stream().filter(userRights -> it.getCode().equals(EntityUtil.getCode(userRights.getObject()))).forEach(userRights -> {
                    String permissionCode = EntityUtil.getCode(userRights.getPermission());
                    if (Permission.OIGUS_V.name().equals(permissionCode)) {
                        userRight.setOigusV(Boolean.TRUE);
                    } else if (Permission.OIGUS_K.name().equals(permissionCode)) {
                        userRight.setOigusK(Boolean.TRUE);
                    } else if (Permission.OIGUS_M.name().equals(permissionCode)) {
                        userRight.setOigusM(Boolean.TRUE);
                    }
                });
                return userRight;
            }, allByMainClassCode));
        } else {
            dto.setRights(new HashSet<>());
        }
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonMinDto getPerson() {
        return person;
    }

    public void setPerson(PersonMinDto person) {
        this.person = person;
    }

    public static class PersonMinDto {

        private Long id;
        private String idcode;
        private String fullname;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getIdcode() {
            return idcode;
        }

        public void setIdcode(String idcode) {
            this.idcode = idcode;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }
    }
}
