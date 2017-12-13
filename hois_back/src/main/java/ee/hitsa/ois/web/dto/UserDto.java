package ee.hitsa.ois.web.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.UserForm;

public class UserDto extends UserForm {

    private Long id;
    private PersonMinDto person;

    public static UserDto of(User user) {
        UserDto dto = EntityUtil.bindToDto(user, new UserDto());
        dto.person = EntityUtil.bindToDto(user.getPerson(), new PersonMinDto());
        dto.setSchool(user.getSchool() != null ? AutocompleteResult.of(user.getSchool()) : null);

        Map<String, List<String>> rights = user.getUserRights().stream().collect(
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
