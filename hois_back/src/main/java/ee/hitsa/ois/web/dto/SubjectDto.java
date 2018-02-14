package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.SubjectConnect;
import ee.hitsa.ois.enums.SubjectConnection;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import ee.hitsa.ois.web.commandobject.SubjectForm;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;

public class SubjectDto extends SubjectForm {

    private Long id;
    
    private Boolean canEdit;
    private Boolean canDelete;
    private Boolean canSetActive;
    private Boolean canSetPassive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Set<AutocompleteResult> primarySubjects;

    private LocalDateTime inserted;

    private String insertedBy;

    private LocalDateTime changed;

    private String changedBy;

    private Set<CurriculumVersionResult> curriculumVersions;

    public static SubjectDto of(Subject subject, List<CurriculumVersion> curriculumVersions) {
        SubjectDto dto = EntityUtil.bindToDto(subject, new SubjectDto(), "languages", "curriculumVersions");
        dto.setLanguages(StreamUtil.toMappedSet(r -> EntityUtil.getCode(r.getLanguage()), subject.getSubjectLanguages()));
        dto.setCurriculumVersions(StreamUtil.toMappedSet(AutocompleteResult::of, curriculumVersions));

        dto.setPrimarySubjects(
                subject.getParentConnections().stream()
                        .filter(it -> ClassifierUtil.equals(SubjectConnection.AINESEOS_EK, it.getConnection()))
                        .map(it -> AutocompleteResult.of(it.getPrimarySubject()))
                        .collect(Collectors.toSet()));

        Set<EntityConnectionCommand> mandatoryPrerequisiteSubjects = new HashSet<>();
        Set<EntityConnectionCommand> recommendedPrerequisiteSubjects = new HashSet<>();
        Set<EntityConnectionCommand> substituteSubjects = new HashSet<>();

        for (SubjectConnect connection: subject.getSubjectConnections()) {
            AutocompleteResult s = AutocompleteResult.of(connection.getConnectSubject());
            String connectionCode = EntityUtil.getCode(connection.getConnection());
            if (SubjectConnection.AINESEOS_EK.name().equals(connectionCode)) {
                mandatoryPrerequisiteSubjects.add(s);
            } else if (SubjectConnection.AINESEOS_EV.name().equals(connectionCode)) {
                recommendedPrerequisiteSubjects.add(s);
            } else if (SubjectConnection.AINESEOS_A.name().equals(connectionCode)) {
                substituteSubjects.add(s);
            }

        }

        dto.setMandatoryPrerequisiteSubjects(mandatoryPrerequisiteSubjects);
        dto.setRecommendedPrerequisiteSubjects(recommendedPrerequisiteSubjects);
        dto.setSubstituteSubjects(substituteSubjects);

        return dto;
    }

    public Set<AutocompleteResult> getPrimarySubjects() {
        return primarySubjects;
    }

    public void setPrimarySubjects(Set<AutocompleteResult> primarySubjects) {
        this.primarySubjects = primarySubjects;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }

    public String getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }

    public LocalDateTime getChanged() {
        return changed;
    }

    public void setChanged(LocalDateTime changed) {
        this.changed = changed;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public Set<CurriculumVersionResult> getCurriculumVersions() {
        return curriculumVersions;
    }

    public void setCurriculumVersions(Set<CurriculumVersionResult> curriculumVersions) {
        this.curriculumVersions = curriculumVersions;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }

    public Boolean getCanSetActive() {
        return canSetActive;
    }

    public void setCanSetActive(Boolean canSetActive) {
        this.canSetActive = canSetActive;
    }

    public Boolean getCanSetPassive() {
        return canSetPassive;
    }

    public void setCanSetPassive(Boolean canSetPassive) {
        this.canSetPassive = canSetPassive;
    }

}
