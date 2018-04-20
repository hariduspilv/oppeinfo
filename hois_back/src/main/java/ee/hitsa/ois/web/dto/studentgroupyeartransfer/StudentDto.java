package ee.hitsa.ois.web.dto.studentgroupyeartransfer;

public class StudentDto {

    private Long id;
    private String name;
    private String mismatchCode;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getMismatchCode() {
        return mismatchCode;
    }
    public void setMismatchCode(String mismatchCode) {
        this.mismatchCode = mismatchCode;
    }
    
}
