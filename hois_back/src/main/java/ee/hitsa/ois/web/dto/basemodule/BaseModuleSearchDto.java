package ee.hitsa.ois.web.dto.basemodule;

import java.util.Set;

public class BaseModuleSearchDto {

    private Long id;
    private String nameEt;
    private String nameEn;
    private String addNameEt;
    private Set<String> curriculums;
    private Set<String> curriculumVersions;
    private Integer credits;
    private Boolean canEdit;

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

    public String getAddNameEt() {
        return addNameEt;
    }

    public void setAddNameEt(String addNameEt) {
        this.addNameEt = addNameEt;
    }

    public Set<String> getCurriculums() {
        return curriculums;
    }

    public void setCurriculums(Set<String> curriculums) {
        this.curriculums = curriculums;
    }

    public Set<String> getCurriculumVersions() {
        return curriculumVersions;
    }

    public void setCurriculumVersions(Set<String> curriculumVersions) {
        this.curriculumVersions = curriculumVersions;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }
}
