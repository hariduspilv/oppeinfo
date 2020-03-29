package ee.hitsa.ois.web.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ee.hitsa.ois.enums.SchoolTimetableType;
import ee.hitsa.ois.web.dto.timetable.NameAndCode;

public class TimetableImportErrorDto {

    private SchoolTimetableType type;
    private Map<String, List<NameAndCode>> messages = new HashMap<>();

    public SchoolTimetableType getType() {
        return type;
    }

    public void setType(SchoolTimetableType type) {
        this.type = type;
    }

    public Map<String, List<NameAndCode>> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, List<NameAndCode>> messages) {
        this.messages = messages;
    }

}
