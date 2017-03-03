package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.SubjectConnect;
import ee.hitsa.ois.enums.SubjectConnection;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import ee.hitsa.ois.web.commandobject.SubjectForm;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SubjectDto extends SubjectForm {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Set<AutocompleteResult<Long>> primarySubjects;

    private LocalDateTime inserted;

    private String insertedBy;

    private LocalDateTime changed;

    private String changedBy;

    private Set<AutocompleteResult<Long>> curriculumVersions;

    public static SubjectDto of(Subject subject, List<CurriculumVersion> curriculumVersions) {
        SubjectDto dto = EntityUtil.bindToDto(subject, new SubjectDto(), "languages", "schoolDepartment", "curriculumVersions");
        dto.setLanguages(SubjectUtil.getLanguages(subject).stream().map(EntityUtil::getCode).collect(Collectors.toSet()));
        dto.setSchoolDepartment(EntityUtil.getNullableId(subject.getSchoolDepartment()));
        dto.setCurriculumVersions(curriculumVersions.stream().map(AutocompleteResult::of).collect(Collectors.toSet()));

        dto.setPrimarySubjects(
                subject.getParentConnections().stream()
                        .filter(it -> SubjectConnection.AINESEOS_EK.name().equals(EntityUtil.getCode(it.getConnection())))
                        .map(it -> AutocompleteResult.of(it.getPrimarySubject()))
                        .collect(Collectors.toSet()));

        Set<EntityConnectionCommand<Long>> mandatoryPrerequisiteSubjects = new HashSet<>();

        Set<EntityConnectionCommand<Long>> recommendedPrerequisiteSubjects = new HashSet<>();

        Set<EntityConnectionCommand<Long>> substituteSubjects = new HashSet<>();

        for (SubjectConnect connetion: subject.getSubjectConnections()) {
            AutocompleteResult<Long> s = AutocompleteResult.of(connetion.getConnectSubject());
            String connectionCode = EntityUtil.getCode(connetion.getConnection());
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

    public Set<AutocompleteResult<Long>> getPrimarySubjects() {
        return primarySubjects;
    }

    public void setPrimarySubjects(Set<AutocompleteResult<Long>> primarySubjects) {
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

    public Set<AutocompleteResult<Long>> getCurriculumVersions() {
        return curriculumVersions;
    }

    public void setCurriculumVersions(Set<AutocompleteResult<Long>> curriculumVersions) {
        this.curriculumVersions = curriculumVersions;
    }
}
