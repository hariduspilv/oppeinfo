package ee.hitsa.ois.web.commandobject.finalprotocol;

import ee.hitsa.ois.web.dto.HigherProtocolSearchDto;

public class FinalHigherProtocolSearchDto extends HigherProtocolSearchDto {

    private Boolean isFinalThesis;
    private String committeeChairman;

    public Boolean getIsFinalThesis() {
        return isFinalThesis;
    }

    public void setIsFinalThesis(Boolean isFinalThesis) {
        this.isFinalThesis = isFinalThesis;
    }

    public String getCommitteeChairman() {
        return committeeChairman;
    }

    public void setCommitteeChairman(String committeeChairman) {
        this.committeeChairman = committeeChairman;
    }
}
