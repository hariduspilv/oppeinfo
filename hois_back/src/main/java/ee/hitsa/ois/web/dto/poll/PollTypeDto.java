package ee.hitsa.ois.web.dto.poll;

import java.time.LocalDate;

public class PollTypeDto {
    
    private Long id;
    private String type;
    private String responseStatus;
    private Long responseId;
    private LocalDate pollValidFrom;
    private LocalDate pollValidThru;
    private Boolean pollThemePageable;
    private String enterpriseName;
    private String contractStudentName;
    private String practiceDuration;
    private String targetCode;
    private String pollNameEt;
    private String pollNameEn;
    private LocalDate practiceJournalFrom;
    private LocalDate practiceJournalThru;
    private LocalDate searchJournalFrom;
    private LocalDate searchJournalThru;
    
    public PollTypeDto() {
        
    }
    
    public PollTypeDto(PollTypeDto dto) {
        this.id = dto.getId();
        this.type = dto.getType();
        this.responseStatus = dto.getResponseStatus();
        this.responseId = dto.getResponseId();
        this.pollValidFrom = dto.getPollValidFrom();
        this.pollValidThru = dto.getPollValidThru();
        this.pollThemePageable = dto.getPollThemePageable();
        this.enterpriseName = dto.getEnterpriseName();
        this.contractStudentName = dto.getContractStudentName();
        this.practiceDuration = dto.getPracticeDuration();
        this.targetCode = dto.getTargetCode();
        this.pollNameEt = dto.getPollNameEt();
        this.pollNameEn = dto.getPollNameEn();
        this.practiceJournalFrom = dto.getPracticeJournalFrom();
        this.practiceJournalThru = dto.getPracticeJournalThru();
        this.searchJournalFrom = dto.getSearchJournalFrom();
        this.searchJournalThru = dto.getSearchJournalThru();
    }
     
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Long getResponseId() {
        return responseId;
    }
    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }
    public String getResponseStatus() {
        return responseStatus;
    }
    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }
    public LocalDate getPollValidFrom() {
        return pollValidFrom;
    }
    public void setPollValidFrom(LocalDate pollValidFrom) {
        this.pollValidFrom = pollValidFrom;
    }
    public LocalDate getPollValidThru() {
        return pollValidThru;
    }
    public void setPollValidThru(LocalDate pollValidThru) {
        this.pollValidThru = pollValidThru;
    }
    public Boolean getPollThemePageable() {
        return pollThemePageable;
    }
    public void setPollThemePageable(Boolean pollThemePageable) {
        this.pollThemePageable = pollThemePageable;
    }
    public String getEnterpriseName() {
        return enterpriseName;
    }
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
    public String getContractStudentName() {
        return contractStudentName;
    }
    public void setContractStudentName(String contractStudentName) {
        this.contractStudentName = contractStudentName;
    }
    public String getPracticeDuration() {
        return practiceDuration;
    }
    public void setPracticeDuration(String practiceDuration) {
        this.practiceDuration = practiceDuration;
    }
    public String getPollNameEt() {
        return pollNameEt;
    }
    public void setPollNameEt(String pollNameEt) {
        this.pollNameEt = pollNameEt;
    }
    public String getPollNameEn() {
        return pollNameEn;
    }
    public void setPollNameEn(String pollNameEn) {
        this.pollNameEn = pollNameEn;
    }
    public String getTargetCode() {
        return targetCode;
    }
    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }
    public LocalDate getPracticeJournalFrom() {
        return practiceJournalFrom;
    }
    public void setPracticeJournalFrom(LocalDate practiceJournalFrom) {
        this.practiceJournalFrom = practiceJournalFrom;
    }
    public LocalDate getPracticeJournalThru() {
        return practiceJournalThru;
    }
    public void setPracticeJournalThru(LocalDate practiceJournalThru) {
        this.practiceJournalThru = practiceJournalThru;
    }

    public LocalDate getSearchJournalFrom() {
        return searchJournalFrom;
    }

    public void setSearchJournalFrom(LocalDate searchJournalFrom) {
        this.searchJournalFrom = searchJournalFrom;
    }

    public LocalDate getSearchJournalThru() {
        return searchJournalThru;
    }

    public void setSearchJournalThru(LocalDate searchJournalThru) {
        this.searchJournalThru = searchJournalThru;
    }

}
