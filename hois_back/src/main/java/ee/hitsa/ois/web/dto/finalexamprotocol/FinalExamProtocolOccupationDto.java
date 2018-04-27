package ee.hitsa.ois.web.dto.finalexamprotocol;

public class FinalExamProtocolOccupationDto {
    
    private Long id;
    private String code;
    private String nameEt;
    private String nameEn;
    
    public FinalExamProtocolOccupationDto(Long id, String code, String nameEt, String nameEn) {
        this.id = id;
        this.code = code;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
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
    
}
