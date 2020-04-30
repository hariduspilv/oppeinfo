package ee.hitsa.ois.web.dto;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.UserForm;

public class UserDto extends UserForm {

    private Long id;
    private PersonMinDto person;
    
    private Boolean higher;
    private Boolean vocational;

    public static UserDto of(HoisUserDetails hoisUser, User user) {
        UserDto dto = EntityUtil.bindToDto(user, new UserDto(), "curriculums");
        dto.person = EntityUtil.bindToDto(user.getPerson(), new PersonMinDto());
        
        dto.person.setSchoolAdminInSchools(user.getPerson().getUsers().stream()
                //.filter(u -> user.getSchool() != null && u.getSchool() != null && user.getSchool().equals(u.getSchool()))
                .filter(u -> !u.equals(user) && ClassifierUtil.equals(Role.ROLL_A, u.getRole()))
                .filter(u -> (hoisUser.isSchoolAdmin() && hoisUser.getSchoolId().equals(EntityUtil.getNullableId(u.getSchool()))
                            && !EntityUtil.getId(u).equals(EntityUtil.getNullableId(user)))
                        || (hoisUser.isMainAdmin() && EntityUtil.getNullableId(u.getSchool()) != null))
                .filter(u -> DateUtils.isValid(u.getValidFrom(), u.getValidThru()))
                .map(u -> EntityUtil.getId(u.getSchool()))
                .collect(Collectors.toSet()));
        
        dto.setSchool(user.getSchool() != null ? AutocompleteResult.of(user.getSchool()) : null);

        Map<String, List<String>> rights = user.getUserRights().stream().collect(
                Collectors.groupingBy(r -> EntityUtil.getCode(r.getObject()),
                        Collectors.mapping(r -> EntityUtil.getCode(r.getPermission()), Collectors.toList())));
        dto.setRights(rights);
        if (user.getUserSchoolRole() == null) {
            if (user.getTeacher() != null) {
                dto.setUserRole(AutocompleteResult.of(user.getTeacher().getTeacherOccupation()));
            }
        } else {
            dto.setUserRole(AutocompleteResult.of(user.getUserSchoolRole()));
        }
        
        dto.setCurriculums(user.getUserCurriculums().stream().map(r -> {
            AutocompleteResult cDto = AutocompleteResult.of(r);
            cDto.setId(r.getCurriculum().getId());
            return cDto;
        }).collect(Collectors.toSet()));
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

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

    public Boolean getVocational() {
        return vocational;
    }

    public void setVocational(Boolean vocational) {
        this.vocational = vocational;
    }

    public static class PersonMinDto {

        private Long id;
        private String idcode;
        private String fullname;
        private Set<Long> schoolAdminInSchools;

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

        public Set<Long> getSchoolAdminInSchools() {
            return schoolAdminInSchools;
        }

        public void setSchoolAdminInSchools(Set<Long> schoolAdminInSchools) {
            this.schoolAdminInSchools = schoolAdminInSchools;
        }

    }
}
