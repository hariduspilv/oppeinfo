package ee.hois.moodle.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Grade {
    private BigDecimal points;
    @JsonProperty("identity")
    private String student;
    private String comment;
    
    public BigDecimal getPoints() {
        return points;
    }
    public void setPoints(BigDecimal points) {
        this.points = points;
    }
    
    public String getStudent() {
        return student;
    }
    public void setStudent(String student) {
        this.student = student;
    }
    
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    
}
