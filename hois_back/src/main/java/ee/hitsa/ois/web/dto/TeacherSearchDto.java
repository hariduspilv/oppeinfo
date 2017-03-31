package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.teacher.Teacher;

public class TeacherSearchDto {
    private Long id;
    private AutocompleteResult school;
    private String name;
    private String idcode;
    private String email;
    private String phone;

    public static TeacherSearchDto of(Teacher teacher) {
        TeacherSearchDto dto = new TeacherSearchDto();
        dto.id = teacher.getId();
        dto.school = AutocompleteResult.of(teacher.getSchool());
        dto.name = teacher.getPerson().getFullname();
        dto.idcode = teacher.getPerson().getIdcode();
        dto.email = teacher.getEmail();
        dto.phone = teacher.getPhone();
        return dto;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
