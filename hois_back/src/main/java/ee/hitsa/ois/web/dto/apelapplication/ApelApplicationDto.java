package ee.hitsa.ois.web.dto.apelapplication;

import java.time.LocalDateTime;
import java.util.List;

import ee.hitsa.ois.domain.apelapplication.ApelApplication;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;

public class ApelApplicationDto extends InsertedChangedVersionDto {

    private Long id;
    private AutocompleteResult student;
    private AutocompleteResult curriculum;
    private String status;
    private String confirmedBy;
    private LocalDateTime confirmed;
    private Boolean isVocational;
    private List<ApelApplicationRecordDto> records;
    private List<ApelApplicationCommentDto> comments;
    private List<ApelApplicationFileDto> files;
    
    public static ApelApplicationDto of(ApelApplication application) {
        if (application == null) {
            return null;
        }
        ApelApplicationDto dto = EntityUtil.bindToDto(application, new ApelApplicationDto(), "record", "files");
        dto.setRecords(StreamUtil.toMappedList(ApelApplicationRecordDto::of, application.getRecords()));
        dto.setComments(StreamUtil.toMappedList(ApelApplicationCommentDto::of, application.getComments()));
        dto.setFiles(StreamUtil.toMappedList(ApelApplicationFileDto::of, application.getFiles()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutocompleteResult getStudent() {
        return student;
    }

    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }

    public AutocompleteResult getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(AutocompleteResult curriculum) {
        this.curriculum = curriculum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public LocalDateTime getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(LocalDateTime confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean getIsVocational() {
        return isVocational;
    }

    public void setIsVocational(Boolean isVocational) {
        this.isVocational = isVocational;
    }

    public List<ApelApplicationRecordDto> getRecords() {
        return records;
    }

    public void setRecords(List<ApelApplicationRecordDto> records) {
        this.records = records;
    }
    
    public List<ApelApplicationCommentDto> getComments() {
        return comments;
    }

    public void setComments(List<ApelApplicationCommentDto> comments) {
        this.comments = comments;
    }

    public List<ApelApplicationFileDto> getFiles() {
        return files;
    }

    public void setFiles(List<ApelApplicationFileDto> files) {
        this.files = files;
    }
    
}
