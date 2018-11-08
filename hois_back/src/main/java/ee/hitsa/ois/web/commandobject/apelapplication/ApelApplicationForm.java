package ee.hitsa.ois.web.commandobject.apelapplication;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;

public class ApelApplicationForm extends InsertedChangedVersionDto {

    @NotNull
    private AutocompleteResult student;

    @NotNull
    private Boolean isVocational;

    private List<ApelApplicationRecordForm> records;
    private String decision;
    private Long committeeId;
    
    @Size(max = 4000)
    private String addInfo;

    public AutocompleteResult getStudent() {
        return student;
    }

    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }

    public Boolean getIsVocational() {
        return isVocational;
    }
    
    public void setIsVocational(Boolean isVocational) {
        this.isVocational = isVocational;
    }

    public List<ApelApplicationRecordForm> getRecords() {
        return records;
    }

    public void setRecords(List<ApelApplicationRecordForm> records) {
        this.records = records;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Long getCommitteeId() {
        return committeeId;
    }

    public void setCommitteeId(Long committeeId) {
        this.committeeId = committeeId;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

}
