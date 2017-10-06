package ee.hitsa.ois.web.dto.timetable;

public class TimetableStudentGroupDto {
    protected Long id;
    protected String code;
    protected Long curriculumId;

    public TimetableStudentGroupDto(Long id, String code, Long curriculumId) {
        this.id = id;
        this.code = code;
        this.curriculumId = curriculumId;
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

    public Long getCurriculumId() {
        return curriculumId;
    }

    public void setCurriculumId(Long curriculumId) {
        this.curriculumId = curriculumId;
    }

}