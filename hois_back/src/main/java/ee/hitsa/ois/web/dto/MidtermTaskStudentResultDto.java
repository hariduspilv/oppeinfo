package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class MidtermTaskStudentResultDto {
    
    private Long id;
    @NotNull
    private Long delcarationSubject;
    @NotNull
    private Long midtermTask;
    private BigDecimal points;
    private String pointsTxt;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((delcarationSubject == null) ? 0 : delcarationSubject.hashCode());
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
        if (delcarationSubject == null) {
            if (other.delcarationSubject != null)
                return false;
        } else if (!delcarationSubject.equals(other.delcarationSubject))
            return false;
        if (midtermTask == null) {
            if (other.midtermTask != null)
                return false;
        } else if (!midtermTask.equals(other.midtermTask))
            return false;
        return true;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getDelcarationSubject() {
        return delcarationSubject;
    }
    public void setDelcarationSubject(Long delcarationSubject) {
        this.delcarationSubject = delcarationSubject;
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
