package ee.hitsa.ois.web.dto.studymaterial;

public class JournalSearchDto extends JournalDto {

    private Long materialCount;
    
    public Long getMaterialCount() {
        return materialCount;
    }
    public void setMaterialCount(Long materialCount) {
        this.materialCount = materialCount;
    }
    
}
