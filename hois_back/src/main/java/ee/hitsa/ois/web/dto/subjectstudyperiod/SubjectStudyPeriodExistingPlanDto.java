package ee.hitsa.ois.web.dto.subjectstudyperiod;

import ee.hitsa.ois.util.Translatable;

import java.time.LocalDate;

public class SubjectStudyPeriodExistingPlanDto implements Translatable {

    private Long periodId;
    private Long groupId;
    private LocalDate endDate;

    private String nameEt;
    private String nameEn;

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    @Override
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
}
