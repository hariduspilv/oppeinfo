package ee.hitsa.ois.web.dto.curriculum;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionElectiveModule;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class CurriculumVersionElectiveModuleDto extends VersionedCommand {

    private Long id;

    private Long referenceNumber;

    @NotBlank
    @Size(max=255)
    private String nameEt;
    @NotBlank
    @Size(max=255)
    private String nameEn;

    private Set<Long> subjects;

    public static CurriculumVersionElectiveModuleDto of(CurriculumVersionElectiveModule electiveModule) {
        CurriculumVersionElectiveModuleDto dto = EntityUtil.bindToDto(electiveModule, new CurriculumVersionElectiveModuleDto(),
                "subjects");
        if(electiveModule.getSubjects() != null) {
            Set<Long> subjects = electiveModule.getSubjects().stream().map(em -> em.getSubject().getId()).collect(Collectors.toSet());
            dto.setSubjects(subjects);
            dto.setReferenceNumber(dto.getId());
        }
        return dto;
    }

    public Long getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(Long referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Set<Long> getSubjects() {
        return subjects != null ? subjects : new HashSet<>();
    }

    public void setSubjects(Set<Long> subjects) {
        this.subjects = subjects;
    }
}
