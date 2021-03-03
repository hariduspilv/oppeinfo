package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.SubjectConnect;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.enums.SubjectConnection;
import ee.hitsa.ois.enums.SubjectProgramStatus;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectProgramValidation;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import ee.hitsa.ois.web.commandobject.subject.SubjectForm;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    
    private Set<AutocompleteResult> programs;

    public static SubjectDto forPublic(Subject subject, List<CurriculumVersion> curriculumVersions, HoisUserDetails user, SubjectProgramValidation validation) {
        return SubjectDto.ofWithPrograms(subject, curriculumVersions, true, user, validation);
    }
    
    public static SubjectDto of(Subject subject, List<CurriculumVersion> curriculumVersions) {
        return SubjectDto.of(subject, curriculumVersions, false);
    }

    private static SubjectDto of(Subject subject, List<CurriculumVersion> curriculumVersions, boolean isPublic) {
        SubjectDto dto = EntityUtil.bindToDto(subject, new SubjectDto(), "languages", "curriculumVersions");
        dto.setLanguages(StreamUtil.toMappedSet(r -> EntityUtil.getCode(r.getLanguage()), subject.getSubjectLanguages()));
        dto.setCurriculumVersions(StreamUtil.nullSafeList(curriculumVersions).stream()
            .filter(cv -> !isPublic || ClassifierUtil.equals(CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K, cv.getStatus()))
            .map(AutocompleteResult::of).collect(Collectors.toSet()));

        dto.setPrimarySubjects(
            StreamUtil.nullSafeSet(subject.getParentConnections()).stream()
                .filter(it -> ClassifierUtil.equals(SubjectConnection.AINESEOS_EK, it.getConnection()))
                .filter(it -> !isPublic || ClassifierUtil.equals(SubjectStatus.AINESTAATUS_K, it.getPrimarySubject().getStatus()))
                .map(it -> AutocompleteResult.of(it.getPrimarySubject()))
                .collect(Collectors.toSet()));

        Set<EntityConnectionCommand> mandatoryPrerequisiteSubjects = new HashSet<>();
        Set<EntityConnectionCommand> recommendedPrerequisiteSubjects = new HashSet<>();
        Set<EntityConnectionCommand> substituteSubjects = new HashSet<>();

        for (SubjectConnect connection: subject.getSubjectConnections()) {
            if (isPublic && !ClassifierUtil.equals(SubjectStatus.AINESTAATUS_K, connection.getConnectSubject().getStatus())) {
                continue;
            }
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

    public static SubjectDto ofWithPrograms(Subject subject, List<CurriculumVersion> curriculumVersions, boolean isPublic, HoisUserDetails user, SubjectProgramValidation validation) {
        SubjectDto dto = of(subject, curriculumVersions, isPublic);
        dto.setPrograms(StreamUtil.nullSafeSet(subject.getSubjectStudyPeriods()).stream()
                .flatMap(period -> period.getSubjectPrograms().stream())
                .filter(p -> {
                    if (validation != null) {
                        return validation.canView(user, p);
                    }
                    return !isPublic
                            // if public for all then completed and confirmed programs allowed
                            || (Boolean.TRUE.equals(p.getPublicAll()) && ClassifierUtil.oneOf(p.getStatus(), SubjectProgramStatus.AINEPROGRAMM_STAATUS_K, SubjectProgramStatus.AINEPROGRAMM_STAATUS_V))
                            // otherwise if confirmed
                            || (ClassifierUtil.oneOf(p.getStatus(), SubjectProgramStatus.AINEPROGRAMM_STAATUS_K));
                })
                .map(AutocompleteResult::of).collect(Collectors.toSet()));
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

    public Set<AutocompleteResult> getPrograms() {
        return programs;
    }

    public void setPrograms(Set<AutocompleteResult> programs) {
        this.programs = programs;
    }

}
