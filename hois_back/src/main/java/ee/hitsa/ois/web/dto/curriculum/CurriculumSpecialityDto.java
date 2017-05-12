package ee.hitsa.ois.web.dto.curriculum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class CurriculumSpecialityDto extends VersionedCommand {
    
    private Long id;
    private Long referenceNumber;
    @NotBlank
    @Size(max=100)
    private String nameEt;
    @NotBlank
    @Size(max=100)
    private String nameEn;
    @NotNull
    private Double credits;
    @Size(max=1000)
    private String description;
    @Size(max=255)
    private String occupationEt;
    @Size(max=255)
    private String occupationEn;
    @ClassifierRestriction(MainClassCode.KUTSE)
    private String occupation;
    private Long curriculum;
    

    public static CurriculumSpecialityDto of(CurriculumSpeciality speciality) {
        CurriculumSpecialityDto dto = EntityUtil.bindToDto(speciality, new CurriculumSpecialityDto(), "referenceNumber", "curriculum");
        dto.setCurriculum(EntityUtil.getId(speciality.getCurriculum()));
        return dto;
    }
    
    public Long getCurriculum() {
        return curriculum;
    }
    
    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
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
        setReferenceNumber(id);
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

    public Double getCredits() {
        return credits;
    }

    public void setCredits(Double credits) {
        this.credits = credits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOccupationEt() {
        return occupationEt;
    }

    public void setOccupationEt(String occupationEt) {
        this.occupationEt = occupationEt;
    }

    public String getOccupationEn() {
        return occupationEn;
    }

    public void setOccupationEn(String occupationEn) {
        this.occupationEn = occupationEn;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
