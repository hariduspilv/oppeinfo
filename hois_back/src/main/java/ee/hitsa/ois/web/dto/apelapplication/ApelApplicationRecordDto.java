package ee.hitsa.ois.web.dto.apelapplication;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationRecord;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class ApelApplicationRecordDto extends VersionedCommand {
    
    private Long id;
    private Boolean isFormalLearning;
    private Long applicationPlannedSubject;
    private List<ApelApplicationInformalExperienceDto> informalExperiences;
    private List<ApelApplicationInformalSubjectOrModuleDto> informalSubjectsOrModules;
    private List<ApelApplicationFormalSubjectOrModuleDto> formalSubjectsOrModules;
    private List<ApelApplicationFormalReplacedSubjectOrModuleDto> formalReplacedSubjectsOrModules;

    public static ApelApplicationRecordDto of(ApelApplicationRecord record) {
        if (record == null) {
            return null;
        }
        ApelApplicationRecordDto dto = EntityUtil.bindToDto(record, new ApelApplicationRecordDto(), "applicationPlannedSubject",
                "informalExperiences", "informalSubjectsOrModules", "formalSubjectsOrModules", "formalReplacedSubjectsOrModules");
        dto.setApplicationPlannedSubject(EntityUtil.getNullableId(record.getApplicationPlannedSubject()));
        dto.setInformalExperiences(StreamUtil.toMappedList(ApelApplicationInformalExperienceDto::of, record.getInformalExperiences()));
        dto.setInformalSubjectsOrModules(StreamUtil.toMappedList(ApelApplicationInformalSubjectOrModuleDto::of,
                record.getInformalSubjectsOrModules()));
        Collections.sort(dto.getInformalSubjectsOrModules(), Comparator.comparing(ApelApplicationInformalSubjectOrModuleDto::getId));
        dto.setFormalSubjectsOrModules(StreamUtil.toMappedList(ApelApplicationFormalSubjectOrModuleDto::of, 
                record.getFormalSubjectsOrModules()));
        Collections.sort(dto.getFormalSubjectsOrModules(), Comparator.comparing(ApelApplicationFormalSubjectOrModuleDto::getId));
        dto.setFormalReplacedSubjectsOrModules(StreamUtil.toMappedList(ApelApplicationFormalReplacedSubjectOrModuleDto::of, 
                record.getFormalReplacedSubjectsOrModules()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsFormalLearning() {
        return isFormalLearning;
    }

    public void setIsFormalLearning(Boolean isFormalLearning) {
        this.isFormalLearning = isFormalLearning;
    }

    public Long getApplicationPlannedSubject() {
        return applicationPlannedSubject;
    }

    public void setApplicationPlannedSubject(Long applicationPlannedSubject) {
        this.applicationPlannedSubject = applicationPlannedSubject;
    }

    public List<ApelApplicationInformalExperienceDto> getInformalExperiences() {
        return informalExperiences;
    }

    public void setInformalExperiences(List<ApelApplicationInformalExperienceDto> informalExperiences) {
        this.informalExperiences = informalExperiences;
    }

    public List<ApelApplicationInformalSubjectOrModuleDto> getInformalSubjectsOrModules() {
        return informalSubjectsOrModules;
    }

    public void setInformalSubjectsOrModules(List<ApelApplicationInformalSubjectOrModuleDto> informalSubjectsOrModules) {
        this.informalSubjectsOrModules = informalSubjectsOrModules;
    }

    public List<ApelApplicationFormalSubjectOrModuleDto> getFormalSubjectsOrModules() {
        return formalSubjectsOrModules;
    }

    public void setFormalSubjectsOrModules(List<ApelApplicationFormalSubjectOrModuleDto> formalSubjectsOrModules) {
        this.formalSubjectsOrModules = formalSubjectsOrModules;
    }

    public List<ApelApplicationFormalReplacedSubjectOrModuleDto> getFormalReplacedSubjectsOrModules() {
        return formalReplacedSubjectsOrModules;
    }

    public void setFormalReplacedSubjectsOrModules(
            List<ApelApplicationFormalReplacedSubjectOrModuleDto> formalReplacedSubjectsOrModules) {
        this.formalReplacedSubjectsOrModules = formalReplacedSubjectsOrModules;
    }
    

}
