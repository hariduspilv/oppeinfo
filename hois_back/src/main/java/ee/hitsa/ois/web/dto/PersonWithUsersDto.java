package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.PersonForm;

import java.util.HashSet;
import java.util.Set;

public class PersonWithUsersDto extends PersonForm {
    private Long id;

    private Set<UsersSearchDto> users;

    public static PersonWithUsersDto of(Person person, Set<User> users) {
        PersonWithUsersDto dto = EntityUtil.bindToDto(person, new PersonWithUsersDto());
        //this.users = users.stream().map(UsersSearchDto::of).collect()
        dto.users = new HashSet<>();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<UsersSearchDto> getUsers() {
        return users;
    }

    public void setUsers(Set<UsersSearchDto> users) {
        this.users = users;
    }
}
