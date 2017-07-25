package ee.hitsa.ois.web.dto.timetable;

public class StudentGroupForTimetableDto {
    private Long id;
    private String code;
    private Long curriculumId;

    public StudentGroupForTimetableDto(Long id, String code, Long curriculumId) {
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