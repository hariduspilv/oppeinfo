package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.UserForm;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDto extends UserForm {

    private PersonMinDto person;

    private Long id;

    public static UserDto of(User user, Set<Classifier> allByMainClassCode) {
        UserDto dto = EntityUtil.bindToDto(user, new UserDto());
        dto.person = EntityUtil.bindToDto(user.getPerson(), new PersonMinDto());
        if (allByMainClassCode != null) {
            Set<UserRight> rights = allByMainClassCode.stream().map(it -> {
                UserRight userRight = new UserRight();
                userRight.setObject(it.getCode());
                user.getUserRights().stream().filter(userRights -> it.getCode().equals(EntityUtil.getCode(userRights.getObject()))).forEach(userRights -> {
                    if ("OIGUS_V".equals(EntityUtil.getCode(userRights.getPermission()))) {
                        userRight.setOigusV(Boolean.TRUE);
                    } else if ("OIGUS_K".equals(EntityUtil.getCode(userRights.getPermission()))) {
                        userRight.setOigusK(Boolean.TRUE);
                    } else if ("OIGUS_M".equals(EntityUtil.getCode(userRights.getPermission()))) {
                        userRight.setOigusM(Boolean.TRUE);
                    }
                });
                return userRight;
            }).collect(Collectors.toSet());
            dto.setRights(rights);
        } else {
            dto.setRights(new HashSet<>());
        }
        return dto;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
