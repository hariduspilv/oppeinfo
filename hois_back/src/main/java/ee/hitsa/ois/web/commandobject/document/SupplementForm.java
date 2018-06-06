package ee.hitsa.ois.web.commandobject.document;

public class SupplementForm {

    private Long signer1Id;
    private Long numeral;
    private Long additionalNumeral;
    
    public Long getSigner1Id() {
        return signer1Id;
    }
    public void setSigner1Id(Long signer1Id) {
        this.signer1Id = signer1Id;
    }
    
    public Long getNumeral() {
        return numeral;
    }
    public void setNumeral(Long numeral) {
        this.numeral = numeral;
    }
    
    public Long getAdditionalNumeral() {
        return additionalNumeral;
    }
    public void setAdditionalNumeral(Long additionalNumeral) {
        this.additionalNumeral = additionalNumeral;
    }
    
}
