package ee.hitsa.ois.web.dto.timetable;

import java.util.ArrayList;
import java.util.List;

public class SubjectTeacherPairDto {

    // subject study period id
    private Long id;
    // subject code
    private String code;
    // subject names
    private String nameEt;
    private String nameEn;
    private String teacherNames;
    private String teacherNamesShort;
    private String studentGroups;
    private List<TimetableEventDto> lessons = new ArrayList<>();

    public SubjectTeacherPairDto() {}

    public SubjectTeacherPairDto(Long id, String code, String teacherNames, String teacherNamesShort, String nameEt, String nameEn) {
        this.id = id;
        this.code = code;
        this.teacherNames = teacherNames;
        this.teacherNamesShort = teacherNamesShort;
        this.nameEn = nameEn;
        this.nameEt = nameEt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getTeacherNames() {
        return teacherNames;
    }

    public void setTeacherNames(String teacherNames) {
        this.teacherNames = teacherNames;
    }

    public String getTeacherNamesShort() {
        return teacherNamesShort;
    }

    public void setTeacherNamesShort(String teacherNamesShort) {
        this.teacherNamesShort = teacherNamesShort;
    }

    public String getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(String studentGroups) {
        this.studentGroups = studentGroups;
    }

    public List<TimetableEventDto> getLessons() {
        return lessons;
    }

    public void setLessons(List<TimetableEventDto> lessons) {
        this.lessons = lessons;
    }
}
