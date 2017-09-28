package ee.hitsa.ois.web.dto.timetable;

public class SubjectTeacherPairDto {

    private Long id;
    //subject code
    private String code;
    private String teacherNames;
    private String teacherNamesShort;

    public SubjectTeacherPairDto(Long id, String code, String teacherNames, String teacherNamesShort) {
        this.id = id;
        this.code = code;
        this.teacherNames = teacherNames;
        this.teacherNamesShort = teacherNamesShort;
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

}
