package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.protocol.ProtocolStudentHistory;
import ee.hitsa.ois.util.EntityUtil;

public class ProtocolStudentHistoryDto {

    private String grade;
    private String addInfo;

    public static ProtocolStudentHistoryDto of(ProtocolStudentHistory protocolStudentHistory) {
        ProtocolStudentHistoryDto dto = EntityUtil.bindToDto(protocolStudentHistory, new ProtocolStudentHistoryDto());
        return dto;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

}
