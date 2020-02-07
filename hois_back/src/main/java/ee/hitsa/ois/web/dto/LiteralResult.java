package ee.hitsa.ois.web.dto;

public class LiteralResult extends AutocompleteResult {
    
    private String literalName;

    public LiteralResult(Long id, String nameEt, String nameEn, String literal) {
        super(id, nameEt, nameEn);
        this.literalName = literal;
    }

    public String getLiteralName() {
        return literalName;
    }

    public void setLiteralName(String literalName) {
        this.literalName = literalName;
    }

}
