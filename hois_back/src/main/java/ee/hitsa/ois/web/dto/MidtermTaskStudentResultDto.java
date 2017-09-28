package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.MidtermTaskStudentResult;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.MidtermTaskUtil;

public class MidtermTaskStudentResultDto {
    
    private Long id;
    @NotNull
    private Long midtermTask;
    @NotNull
    private Long declarationSubject;
    /**
     * Is used for higher protocols
     */
    private Long studentId;
    @Min(0)
    @Max(999)
    private BigDecimal points;
    @Size(max=10)
    private String pointsTxt;
    private Boolean isText;
    private BigDecimal maxPoints;
    
    public static MidtermTaskStudentResultDto of(MidtermTaskStudentResult studentResult) {
        MidtermTaskStudentResultDto dto = new MidtermTaskStudentResultDto();
        dto = EntityUtil.bindToDto(studentResult, dto, "midtermTask", "declarationSubject");
        dto.setMidtermTask(EntityUtil.getId(studentResult.getMidtermTask()));
        dto.setDeclarationSubject(EntityUtil.getId(studentResult.getDeclarationSubject()));
        dto.setMaxPoints(studentResult.getMidtermTask().getMaxPoints());
        dto.setIsText(MidtermTaskUtil.getStudentResultIsText(studentResult.getMidtermTask()));
        dto.setStudentId(EntityUtil.getId(studentResult.getDeclarationSubject().getDeclaration().getStudent()));
        return dto;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((declarationSubject == null) ? 0 : declarationSubject.hashCode());
        result = prime * result + ((midtermTask == null) ? 0 : midtermTask.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MidtermTaskStudentResultDto other = (MidtermTaskStudentResultDto) obj;
        if (declarationSubject == null) {
            if (other.declarationSubject != null)
                return false;
        } else if (!declarationSubject.equals(other.declarationSubject))
            return false;
        if (midtermTask == null) {
            if (other.midtermTask != null)
                return false;
        } else if (!midtermTask.equals(other.midtermTask))
            return false;
        return true;
    }

    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public Boolean getIsText() {
        return isText;
    }
    public void setIsText(Boolean isText) {
        this.isText = isText;
    }
    public BigDecimal getMaxPoints() {
        return maxPoints;
    }
    public void setMaxPoints(BigDecimal maxPoints) {
        this.maxPoints = maxPoints;
    }
    public Long getDeclarationSubject() {
        return declarationSubject;
    }
    public void setDeclarationSubject(Long declarationSubject) {
        this.declarationSubject = declarationSubject;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getMidtermTask() {
        return midtermTask;
    }
    public void setMidtermTask(Long midtermTask) {
        this.midtermTask = midtermTask;
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
}
