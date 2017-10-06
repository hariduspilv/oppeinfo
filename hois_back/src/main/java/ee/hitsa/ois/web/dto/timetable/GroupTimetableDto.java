package ee.hitsa.ois.web.dto.timetable;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

public class GroupTimetableDto {
    private Long groupId;
    private String groupCode;
    
    public GroupTimetableDto(Object[] row) {
        this.groupId =  resultAsLong(row, 0);
        this.groupCode = (String) row[1];
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
    
}
