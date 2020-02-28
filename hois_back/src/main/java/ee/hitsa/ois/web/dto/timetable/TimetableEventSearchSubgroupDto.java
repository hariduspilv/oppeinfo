package ee.hitsa.ois.web.dto.timetable;

public class TimetableEventSearchSubgroupDto {

    private Long id;
    private String code;

    public  TimetableEventSearchSubgroupDto(Long id, String code) {
        this.id = id;
        this.code = code;
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
}
