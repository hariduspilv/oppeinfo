package ee.hitsa.ois.web.commandobject.finalprotocol;

import java.util.List;

import ee.hitsa.ois.web.commandobject.ProtocolStudentSaveForm;

public class FinalVocationalProtocolStudentSaveForm extends ProtocolStudentSaveForm {

    private List<String> occupationCodes;
    private String language;

    public List<String> getOccupationCodes() {
        return occupationCodes;
    }

    public void setOccupationCodes(List<String> occupationCodes) {
        this.occupationCodes = occupationCodes;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
