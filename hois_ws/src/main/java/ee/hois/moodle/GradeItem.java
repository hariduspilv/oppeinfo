package ee.hois.moodle;

public class GradeItem {
    private Long id;
    private String type;
    private String name;
    private Double pass;
    private Double max;
    
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
    
    public Double getPass() {
        return pass;
    }
    public void setPass(Double pass) {
        this.pass = pass;
    }
    public Double getMax() {
        return max;
    }
    public void setMax(Double max) {
        this.max = max;
    }
    
}
