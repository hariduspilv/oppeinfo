package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.PersonForm;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonWithUsersDto extends PersonForm {
    private Long id;

    private Set<UsersDto> users;

    public static PersonWithUsersDto of(Person person, Set<User> users) {
        PersonWithUsersDto dto = EntityUtil.bindToDto(person, new PersonWithUsersDto());
        dto.users = users != null ? users.stream().map(PersonWithUsersDto.UsersDto::of).collect(Collectors.toSet()) : new HashSet<>();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<UsersDto> getUsers() {
        return users;
    }

    public void setUsers(Set<UsersDto> users) {
        this.users = users;
    }

    public static class UsersDto {
        private Long id;

        private AutocompleteResult school;

        private String role;

        private LocalDate validFrom;
        private LocalDate validThru;

        public static UsersDto of(User user) {
            return EntityUtil.bindToDto(user, new UsersDto());
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public AutocompleteResult getSchool() {
            return school;
        }

        public void setSchool(AutocompleteResult school) {
            this.school = school;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public LocalDate getValidThru() {
            return validThru;
        }

        public void setValidThru(LocalDate validThru) {
            this.validThru = validThru;
        }

        public LocalDate getValidFrom() {
            return validFrom;
        }

        public void setValidFrom(LocalDate validFrom) {
            this.validFrom = validFrom;
        }
    }
}
