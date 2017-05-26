package ee.hitsa.ois.web.dto.curriculum;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class CurriculumGradeDto extends VersionedCommand {

    private Long id;

    @NotBlank
    @Size(max=255)
    private String nameEt;

    @NotBlank
    @Size(max=255)
    private String nameEn;

    @Size(max=255)
    private String nameGenitiveEt;

    @NotEmpty
    @ClassifierRestriction(MainClassCode.AKAD_KRAAD)
    private String ehisGrade;

    public static CurriculumGradeDto of(CurriculumGrade grade) {
        CurriculumGradeDto dto = EntityUtil.bindToDto(grade, new CurriculumGradeDto());
        return dto;
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

    public String getNameGenitiveEt() {
        return nameGenitiveEt;
    }

    public void setNameGenitiveEt(String nameGenitiveEt) {
        this.nameGenitiveEt = nameGenitiveEt;
    }

    public String getEhisGrade() {
        return ehisGrade;
    }

    public void setEhisGrade(String ehisGrade) {
        this.ehisGrade = ehisGrade;
    }

}
