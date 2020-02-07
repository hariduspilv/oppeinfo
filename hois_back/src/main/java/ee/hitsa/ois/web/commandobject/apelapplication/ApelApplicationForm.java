package ee.hitsa.ois.web.commandobject.apelapplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;

public class ApelApplicationForm extends InsertedChangedVersionDto {

    @NotNull
    private AutocompleteResult student;
    @Valid
    private List<ApelApplicationRecordForm> records = new ArrayList<>();
    private String decision;
    private Long committeeId;
    private String nominalType;
    private LocalDate newNominalStudyEnd;

    @Size(max = 4000)
    private String addInfo;

    public AutocompleteResult getStudent() {
        return student;
    }

    public void setStudent(AutocompleteResult student) {
        this.student = student;
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

    public String getNominalType() {
        return nominalType;
    }

    public void setNominalType(String nominalType) {
        this.nominalType = nominalType;
    }

    public LocalDate getNewNominalStudyEnd() {
        return newNominalStudyEnd;
    }

    public void setNewNominalStudyEnd(LocalDate newNominalStudyEnd) {
        this.newNominalStudyEnd = newNominalStudyEnd;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

}
