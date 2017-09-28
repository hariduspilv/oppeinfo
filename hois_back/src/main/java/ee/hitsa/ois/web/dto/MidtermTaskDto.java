package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.MidtermTask;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.MidtermTaskUtil;

public class MidtermTaskDto {

    private Long id;
    @NotNull
    @Size(max=255)
    private String nameEt;
    @Size(max=255)
    private String nameEn;
    @NotNull
    @Size(max=4000)
    private String descriptionEt;
    @Size(max=4000)
    private String descriptionEn;
    @NotNull
    @Min(0)
    @Max(999)
    private BigDecimal maxPoints;
    private Boolean threshold;
    @NotNull
    @Min(0)
    @Max(100)
    private Short percentage;
    @Min(0)
    @Max(100)
    private Short thresholdPercentage;
    private LocalDate taskDate;
    private Boolean canBeDeleted;
    private Boolean studentResultIsText;

    public static MidtermTaskDto of(MidtermTask midtermTask) {
        MidtermTaskDto dto = EntityUtil.bindToDto(midtermTask, new MidtermTaskDto());
        dto.setCanBeDeleted(MidtermTaskUtil.midtermTaskCanBeDeleted(midtermTask));
        return dto;
    }

    public static MidtermTaskDto ofForStudentResultsForm(MidtermTask midtermTask) {
        MidtermTaskDto dto = EntityUtil.bindToDto(midtermTask, new MidtermTaskDto(),
                "descriptionEt", "descriptionEn", "thresholdPercentage", "threshold");
        dto.setStudentResultIsText(MidtermTaskUtil.getStudentResultIsText(midtermTask));
        return dto;
    }

    public Boolean getStudentResultIsText() {
        return studentResultIsText;
    }
    public void setStudentResultIsText(Boolean studentResultIsText) {
        this.studentResultIsText = studentResultIsText;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Boolean getCanBeDeleted() {
        return canBeDeleted;
    }
    public void setCanBeDeleted(Boolean canBeDeleted) {
        this.canBeDeleted = canBeDeleted;
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
    public String getDescriptionEt() {
        return descriptionEt;
    }
    public void setDescriptionEt(String descriptionEt) {
        this.descriptionEt = descriptionEt;
    }
    public String getDescriptionEn() {
        return descriptionEn;
    }
    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }
    public BigDecimal getMaxPoints() {
        return maxPoints;
    }
    public void setMaxPoints(BigDecimal maxPoints) {
        this.maxPoints = maxPoints;
    }
    public Boolean getThreshold() {
        return threshold;
    }
    public void setThreshold(Boolean threshold) {
        this.threshold = threshold;
    }
    public Short getPercentage() {
        return percentage;
    }
    public void setPercentage(Short percentage) {
        this.percentage = percentage;
    }
    public Short getThresholdPercentage() {
        return thresholdPercentage;
    }
    public void setThresholdPercentage(Short thresholdPercentage) {
        this.thresholdPercentage = thresholdPercentage;
    }
    public LocalDate getTaskDate() {
        return taskDate;
    }
    public void setTaskDate(LocalDate taskDate) {
        this.taskDate = taskDate;
    }
}
