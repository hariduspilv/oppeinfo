package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.util.StreamUtil;

public class TeacherSearchDto {
    private Long id;
    private AutocompleteResult school;
    private String name;
    private String idcode;
    private String email;
    private String phone;
    private Boolean isActive;
    private AutocompleteResult teacherOccupation;
    private Set<AutocompleteResult> schoolDepartments;
    /**
     * Planned number of hours in studyPeriod
     */
    private Long hours;
    private Boolean canEdit;

    public static TeacherSearchDto of(Teacher teacher) {
        TeacherSearchDto dto = new TeacherSearchDto();
        dto.id = teacher.getId();
        dto.school = AutocompleteResult.of(teacher.getSchool());
        dto.name = teacher.getPerson().getFullname();
        dto.idcode = teacher.getPerson().getIdcode();
        dto.email = teacher.getEmail();
        dto.phone = teacher.getPhone();
        dto.isActive = teacher.getIsActive();
        dto.teacherOccupation = AutocompleteResult.of(teacher.getTeacherOccupation());
        dto.schoolDepartments = StreamUtil.toMappedSet(o -> {

            return AutocompleteResult.of(o.getSchoolDepartment());
        }, teacher.getTeacherPositionEhis().stream().filter(o -> o.getSchoolDepartment() != null)
                .filter(o -> !Boolean.TRUE.equals(o.getIsContractEnded()))
                .filter(o -> o.getContractEnd() == null || !LocalDate.now().isAfter(o.getContractEnd()))
                .collect(Collectors.toSet()));
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public AutocompleteResult getTeacherOccupation() {
        return teacherOccupation;
    }

    public void setTeacherOccupation(AutocompleteResult teacherOccupation) {
        this.teacherOccupation = teacherOccupation;
    }

    public Set<AutocompleteResult> getSchoolDepartments() {
        return schoolDepartments;
    }

    public void setSchoolDepartments(Set<AutocompleteResult> schoolDepartments) {
        this.schoolDepartments = schoolDepartments;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

}
