package ee.hitsa.ois.web.dto.midtermresult;

import java.math.BigDecimal;
import java.time.LocalDate;

import ee.hitsa.ois.domain.MidtermTaskStudentResult;
import ee.hitsa.ois.util.MidtermTaskUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class MidtermResultDto {
    
    private AutocompleteResult name;
    private AutocompleteResult description;
    private BigDecimal points;
    private String pointsTxt;
    private Boolean isTxt;
    private LocalDate midtermDate;
    private BigDecimal maxPoints;
    private Short percentage;
    private Short thresholdPercentage;
    
    public static MidtermResultDto of(MidtermTaskStudentResult result) {
        MidtermResultDto dto = new MidtermResultDto();
        dto.setName(AutocompleteResult.of(result.getMidtermTask()));
        dto.setDescription(new AutocompleteResult(null, result.getMidtermTask().getDescriptionEt(), result.getMidtermTask().getDescriptionEn()));
        dto.setIsTxt(MidtermTaskUtil.getStudentResultIsText(result.getMidtermTask()));
        dto.setPoints(result.getPoints());
        dto.setPointsTxt(result.getPointsTxt());
        dto.setMidtermDate(result.getMidtermTask().getTaskDate());
        dto.setMaxPoints(result.getMidtermTask().getMaxPoints());
        dto.setPercentage(result.getMidtermTask().getPercentage());
        dto.setThresholdPercentage(result.getMidtermTask().getThresholdPercentage());
        return dto;
    }

    public AutocompleteResult getName() {
        return name;
    }

    public void setName(AutocompleteResult name) {
        this.name = name;
    }

    public AutocompleteResult getDescription() {
        return description;
    }

    public void setDescription(AutocompleteResult description) {
        this.description = description;
    }

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    public String getPointsTxt() {
        return pointsTxt;
    }

    public void setPointsTxt(String pointsTxt) {
        this.pointsTxt = pointsTxt;
    }

    public Boolean getIsTxt() {
        return isTxt;
    }

    public void setIsTxt(Boolean isTxt) {
        this.isTxt = isTxt;
    }

    public LocalDate getMidtermDate() {
        return midtermDate;
    }

    public void setMidtermDate(LocalDate midtermDate) {
        this.midtermDate = midtermDate;
    }

    public BigDecimal getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(BigDecimal maxPoints) {
        this.maxPoints = maxPoints;
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
}
