package ee.hitsa.ois.web.dto.timetable;

public class SubjectTeacherPairDto {

    private Long id;
    private String nameEt;
    private String nameEn;
    private String subjectCode;
    private String teacherName;

    public SubjectTeacherPairDto(Long id, String nameEt, String nameEn, String subjectCode, String teacherName) {
        this.id = id;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.subjectCode = subjectCode;
        this.teacherName = teacherName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
