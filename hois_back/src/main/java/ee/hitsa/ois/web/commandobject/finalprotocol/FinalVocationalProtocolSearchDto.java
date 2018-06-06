package ee.hitsa.ois.web.commandobject.finalprotocol;

import ee.hitsa.ois.web.dto.ModuleProtocolSearchDto;

public class FinalVocationalProtocolSearchDto extends ModuleProtocolSearchDto {

    private Boolean isFinalThesis;
    private String studyYear;
    private String committeeChairman;
    
    public Boolean getIsFinalThesis() {
        return isFinalThesis;
    }
    
    public void setIsFinalThesis(Boolean isFinalThesis) {
        this.isFinalThesis = isFinalThesis;
    }
    
    public String getStudyYear() {
        return studyYear;
    }
    
    public void setStudyYear(String studyYear) {
        this.studyYear = studyYear;
    }
    
    public String getCommitteeChairman() {
        return committeeChairman;
    }
    
    public void setCommitteeChairman(String committeeChairman) {
        this.committeeChairman = committeeChairman;
    }
    
}