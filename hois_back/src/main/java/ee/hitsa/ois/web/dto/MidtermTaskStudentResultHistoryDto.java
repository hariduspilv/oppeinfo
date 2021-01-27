package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.MidtermTaskStudentResultHistory;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.MidtermTaskUtil;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MidtermTaskStudentResultHistoryDto {

    private Long id;
    private Long studentResult;
    @Min(0)
    @Max(999)
    private BigDecimal points;
    @Size(max=10)
    private String pointsTxt;
    private Boolean isText;
    private LocalDateTime changed;

    public static MidtermTaskStudentResultHistoryDto of(MidtermTaskStudentResultHistory history) {
        MidtermTaskStudentResultHistoryDto dto = new MidtermTaskStudentResultHistoryDto();
        dto.setId(history.getId());
        dto.setStudentResult(EntityUtil.getId(history.getMidtermTaskStudentResult()));
        dto.setPoints(history.getPoints());
        dto.setPointsTxt(history.getPointsTxt());
        dto.setIsText(MidtermTaskUtil.resultIsText(history));
        dto.setChanged(history.getChanged());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentResult() {
        return studentResult;
    }

    public void setStudentResult(Long studentResult) {
        this.studentResult = studentResult;
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

    public LocalDateTime getChanged() {
        return changed;
    }

    public void setChanged(LocalDateTime changed) {
        this.changed = changed;
    }

    public Boolean getIsText() {
        return isText;
    }

    public void setIsText(Boolean isText) {
        this.isText = isText;
    }
}
