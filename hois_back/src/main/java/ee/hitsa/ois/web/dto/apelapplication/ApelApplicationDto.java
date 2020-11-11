package ee.hitsa.ois.web.dto.apelapplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import ee.hitsa.ois.domain.apelapplication.ApelApplication;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;
import ee.hitsa.ois.web.dto.timetable.DateRangeDto;

public class ApelApplicationDto extends InsertedChangedVersionDto {

    private Long id;
    private AutocompleteResult school;
    private AutocompleteResult student;
    private CurriculumVersionResult curriculumVersion;
    private String studentGroup;
    private String status;
    private String submittedBy;
    private LocalDate submitted;
    private String confirmedBy;
    private LocalDateTime confirmed;
    private Boolean isVocational;
    private List<ApelApplicationRecordDto> records;
    private List<ApelApplicationCommentDto> comments;
    private List<ApelApplicationFileDto> files;
    private AutocompleteResult committee;
    private String decision;
    private String nominalType;
    private LocalDate newNominalStudyEnd;
    private LocalDate oldNominalStudyEnd;
    private Boolean isEhisSent;

    private List<DateRangeDto> abroadStudyPeriods;
    private Boolean hasAbroadStudies;
    private Boolean hasPlannedSubjectsToTransfer;
    private Boolean hasMultipleNominalDurationExtensions;

    private Boolean canExtendNominalDuration;
    private Boolean canEdit;
    private Boolean canReview;
    private Boolean canSendToConfirm;
    private Boolean canSendToCommittee;
    private Boolean canSendBackToCreation;
    private Boolean canSendBack;
    private Boolean canReject;
    private Boolean canConfirm;
    private Boolean canRemoveConfirmation;
    private Boolean canChangeTransferStatus;

    public static ApelApplicationDto of(ApelApplication application) {
        if (application == null) {
            return null;
        }
        ApelApplicationDto dto = EntityUtil.bindToDto(application, new ApelApplicationDto(), "student", "record",
                "files", "confirmedBy", "submittedBy");
        dto.setStudent(AutocompleteResult.of(application.getStudent(), false));
        if (application.getStudent() != null && application.getStudent().getStudentGroup() != null) {
            dto.setStudentGroup(application.getStudent().getStudentGroup().getCode());
        }
        dto.setCurriculumVersion(AutocompleteResult.of(application.getStudent().getCurriculumVersion()));
        dto.setRecords(StreamUtil.toMappedList(ApelApplicationRecordDto::of, application.getRecords()));
        dto.setComments(StreamUtil.toMappedList(ApelApplicationCommentDto::of, application.getComments()));
        dto.setFiles(StreamUtil.toMappedList(ApelApplicationFileDto::of, application.getFiles()));
        dto.setConfirmedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(application.getConfirmedBy()));
        dto.setSubmittedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(application.getSubmittedBy()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutocompleteResult getSchool() {
        return school;
    }

    public void setSchool(AutocompleteResult school) {
        this.school = school;
    }

    public AutocompleteResult getStudent() {
        return student;
    }

    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }

    
    public CurriculumVersionResult getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(CurriculumVersionResult curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
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

    public AutocompleteResult getCommittee() {
        return committee;
    }

    public void setCommittee(AutocompleteResult committee) {
        this.committee = committee;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
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

    public LocalDate getOldNominalStudyEnd() {
        return oldNominalStudyEnd;
    }

    public void setOldNominalStudyEnd(LocalDate oldNominalStudyEnd) {
        this.oldNominalStudyEnd = oldNominalStudyEnd;
    }

    public Boolean getIsEhisSent() {
        return isEhisSent;
    }

    public void setIsEhisSent(Boolean isEhisSent) {
        this.isEhisSent = isEhisSent;
    }

    public List<DateRangeDto> getAbroadStudyPeriods() {
        return abroadStudyPeriods;
    }

    public void setAbroadStudyPeriods(List<DateRangeDto> abroadStudyPeriods) {
        this.abroadStudyPeriods = abroadStudyPeriods;
    }

    public Boolean getHasAbroadStudies() {
        return hasAbroadStudies;
    }

    public void setHasAbroadStudies(Boolean hasAbroadStudies) {
        this.hasAbroadStudies = hasAbroadStudies;
    }

    public Boolean getHasPlannedSubjectsToTransfer() {
        return hasPlannedSubjectsToTransfer;
    }

    public void setHasPlannedSubjectsToTransfer(Boolean hasPlannedSubjectsToTransfer) {
        this.hasPlannedSubjectsToTransfer = hasPlannedSubjectsToTransfer;
    }

    public Boolean getHasMultipleNominalDurationExtensions() {
        return hasMultipleNominalDurationExtensions;
    }

    public void setHasMultipleNominalDurationExtensions(Boolean hasMultipleNominalDurationExtensions) {
        this.hasMultipleNominalDurationExtensions = hasMultipleNominalDurationExtensions;
    }

    public Boolean getCanExtendNominalDuration() {
        return canExtendNominalDuration;
    }

    public void setCanExtendNominalDuration(Boolean canExtendNominalDuration) {
        this.canExtendNominalDuration = canExtendNominalDuration;
    }

    public Boolean getCanReview() {
        return canReview;
    }

    public void setCanReview(Boolean canReview) {
        this.canReview = canReview;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public Boolean getCanSendToConfirm() {
        return canSendToConfirm;
    }

    public void setCanSendToConfirm(Boolean canSendToConfirm) {
        this.canSendToConfirm = canSendToConfirm;
    }

    public Boolean getCanSendToCommittee() {
        return canSendToCommittee;
    }

    public void setCanSendToCommittee(Boolean canSendToCommittee) {
        this.canSendToCommittee = canSendToCommittee;
    }

    public Boolean getCanSendBackToCreation() {
        return canSendBackToCreation;
    }

    public void setCanSendBackToCreation(Boolean canSendBackToCreation) {
        this.canSendBackToCreation = canSendBackToCreation;
    }

    public Boolean getCanSendBack() {
        return canSendBack;
    }

    public void setCanSendBack(Boolean canSendBack) {
        this.canSendBack = canSendBack;
    }

    public Boolean getCanReject() {
        return canReject;
    }

    public void setCanReject(Boolean canReject) {
        this.canReject = canReject;
    }

    public Boolean getCanConfirm() {
        return canConfirm;
    }

    public void setCanConfirm(Boolean canConfirm) {
        this.canConfirm = canConfirm;
    }

    public Boolean getCanRemoveConfirmation() {
        return canRemoveConfirmation;
    }

    public void setCanRemoveConfirmation(Boolean canRemoveConfirmation) {
        this.canRemoveConfirmation = canRemoveConfirmation;
    }

    public Boolean getCanChangeTransferStatus() {
        return canChangeTransferStatus;
    }

    public void setCanChangeTransferStatus(Boolean canChangeTransferStatus) {
        this.canChangeTransferStatus = canChangeTransferStatus;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public LocalDate getSubmitted() {
        return submitted;
    }

    public void setSubmitted(LocalDate submitted) {
        this.submitted = submitted;
    }

}
