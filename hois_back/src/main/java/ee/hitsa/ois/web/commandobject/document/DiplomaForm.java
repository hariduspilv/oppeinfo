package ee.hitsa.ois.web.commandobject.document;

public class DiplomaForm extends DiplomaBaseForm {

    private Long signer1Id;
    private Long signer2Id;
    private Long numeral;
    
    public Long getSigner1Id() {
        return signer1Id;
    }
    public void setSigner1Id(Long signer1Id) {
        this.signer1Id = signer1Id;
    }
    
    public Long getSigner2Id() {
        return signer2Id;
    }
    public void setSigner2Id(Long signer2Id) {
        this.signer2Id = signer2Id;
    }
    
    public Long getNumeral() {
        return numeral;
    }
    public void setNumeral(Long numeral) {
        this.numeral = numeral;
    }
    
}
