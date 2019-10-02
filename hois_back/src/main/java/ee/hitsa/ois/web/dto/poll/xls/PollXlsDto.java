package ee.hitsa.ois.web.dto.poll.xls;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class PollXlsDto {
    
    private Long id;
    private AutocompleteResult name;
    private String type;
    private LocalDate validFrom;
    private LocalDate validThru;
    private AutocompleteResult studyPeriod;
    private List<PollResponseXlsDto> responses = new ArrayList<>();
    private List<PollResponseLegendXlsDto> legend = new ArrayList<>();
    private List<PollCommentXlsDto> comments = new ArrayList<>();
    private Boolean targetStudent = Boolean.FALSE;
    private Boolean targetEnterprise = Boolean.FALSE;
    private Boolean targetTeacher = Boolean.FALSE;
    private Boolean usePersonCode = Boolean.FALSE;
    private Boolean hasStudyPeriod = Boolean.FALSE;
    private List<String> tableHeader = new ArrayList<>();
    private List<String> tableHeaderResults = new ArrayList<>();
    private Boolean higher = Boolean.FALSE;
    
    public AutocompleteResult getName() {
        return name;
    }
    public void setName(AutocompleteResult name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public LocalDate getValidFrom() {
        return validFrom;
    }
    public void setValidFrom(LocalDate validFrom) { 
        this.validFrom = validFrom;
    }
    public LocalDate getValidThru() {
        return validThru;
    }
    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }
    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(AutocompleteResult studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    public List<PollResponseXlsDto> getResponses() {
        return responses;
    }
    public void setResponses(List<PollResponseXlsDto> responses) {
        this.responses = responses;
    }
    public List<PollResponseLegendXlsDto> getLegend() {
        return legend;
    }
    public void setLegend(List<PollResponseLegendXlsDto> legend) {
        this.legend = legend;
    }
    public Boolean getTargetStudent() {
        return targetStudent;
    }
    public void setTargetStudent(Boolean targetStudent) {
        this.targetStudent = targetStudent;
    }
    public Boolean getTargetEnterprise() {
        return targetEnterprise;
    }
    public void setTargetEnterprise(Boolean targetEnterprise) {
        this.targetEnterprise = targetEnterprise;
    }
    public Boolean getTargetTeacher() {
        return targetTeacher;
    }
    public void setTargetTeacher(Boolean targetTeacher) {
        this.targetTeacher = targetTeacher;
    }
    public Boolean getUsePersonCode() {
        return usePersonCode;
    }
    public void setUsePersonCode(Boolean usePersonCode) {
        this.usePersonCode = usePersonCode;
    }
    public List<String> getTableHeader() {
        return tableHeader;
    }
    public void setTableHeader(List<String> tableHeader) {
        this.tableHeader = tableHeader;
    }
    public Boolean getHigher() {
        return higher;
    }
    public void setHigher(Boolean higher) {
        this.higher = higher;
    }
    public List<String> getTableHeaderResults() {
        return tableHeaderResults;
    }
    public void setTableHeaderResults(List<String> tableHeaderResults) {
        this.tableHeaderResults = tableHeaderResults;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Boolean getHasStudyPeriod() {
        return hasStudyPeriod;
    }
    public void setHasStudyPeriod(Boolean hasStudyPeriod) {
        this.hasStudyPeriod = hasStudyPeriod;
    }
    public List<PollCommentXlsDto> getComments() {
        return comments;
    }
    public void setComments(List<PollCommentXlsDto> comments) {
        this.comments = comments;
    }

}
