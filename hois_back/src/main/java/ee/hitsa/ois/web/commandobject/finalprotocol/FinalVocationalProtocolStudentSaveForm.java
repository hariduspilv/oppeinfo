package ee.hitsa.ois.web.commandobject.finalprotocol;

import java.util.List;

import ee.hitsa.ois.web.commandobject.ProtocolStudentSaveForm;

public class FinalVocationalProtocolStudentSaveForm extends ProtocolStudentSaveForm {

    private List<String> occupationCodes;

    public List<String> getOccupationCodes() {
        return occupationCodes;
    }

    public void setOccupationCodes(List<String> occupationCodes) {
        this.occupationCodes = occupationCodes;
    } 

}
