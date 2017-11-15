package ee.hitsa.ois.web.dto.timetable;

public class TimetableCalendarDto {
    private String filename;
    private String content;

    public TimetableCalendarDto(String filename, String content) {
        this.filename = filename;
        this.content = content;
    }

    public String getFilename() {
        return filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
