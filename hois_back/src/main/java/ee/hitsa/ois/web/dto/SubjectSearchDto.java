package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;

import java.math.BigDecimal;
import java.util.Set;

public class SubjectSearchDto {
    private Long id;
    private String code;
    private String nameEt;
    private String nameEn;
    private BigDecimal credits;
    private String assessment;
    private Set<String> languages;
    private String status;
    private AutocompleteResult schoolDepartment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static SubjectSearchDto of(Subject subject) {
        SubjectSearchDto dto = EntityUtil.bindToDto(subject, new SubjectSearchDto(), "languages");
        dto.setLanguages(StreamUtil.toMappedSet(EntityUtil::getCode, SubjectUtil.getLanguages(subject)));
        return dto;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<String> languages) {
        this.languages = languages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AutocompleteResult getSchoolDepartment() {
        return schoolDepartment;
    }

    public void setSchoolDepartment(AutocompleteResult schoolDepartment) {
        this.schoolDepartment = schoolDepartment;
    }
}
