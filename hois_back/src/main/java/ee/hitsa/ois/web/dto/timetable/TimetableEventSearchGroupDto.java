package ee.hitsa.ois.web.dto.timetable;

public class TimetableEventSearchGroupDto {
    private final Long id;
    private final String code;
    
    public TimetableEventSearchGroupDto(Long id, String code) {
        this.id = id;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }
    
}
