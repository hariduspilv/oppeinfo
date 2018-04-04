package ee.hois.moodle.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GradeItem {

    private Long id;
    @JsonProperty("itemtype")
    private String type;
    @JsonProperty("itemname")
    private String name;
    @JsonProperty("gradepass")
    private BigDecimal pass;
    @JsonProperty("grademin")
    private BigDecimal min;
    @JsonProperty("grademax")
    private BigDecimal max;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public BigDecimal getPass() {
        return pass;
    }
    public void setPass(BigDecimal pass) {
        this.pass = pass;
    }
    
    public BigDecimal getMin() {
        return min;
    }
    public void setMin(BigDecimal min) {
        this.min = min;
    }
    
    public BigDecimal getMax() {
        return max;
    }
    public void setMax(BigDecimal max) {
        this.max = max;
    }
    
}
