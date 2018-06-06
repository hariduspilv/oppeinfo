package ee.hitsa.ois.web.commandobject.document;

import java.util.List;

public class DiplomaConfirmForm extends DiplomaBaseForm {

    private List<Long> numerals;
    
    public List<Long> getNumerals() {
        return numerals;
    }
    public void setNumerals(List<Long> numerals) {
        this.numerals = numerals;
    }
    
}
