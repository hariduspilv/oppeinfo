package ee.hitsa.ois.web.commandobject.finalexamprotocol;

import java.util.List;

import ee.hitsa.ois.web.commandobject.ModuleProtocolStudentSaveForm;

public class FinalExamVocationalProtocolStudentSaveForm extends ModuleProtocolStudentSaveForm {

    private List<String> occupations; 

    public List<String> getOccupations() {
        return occupations;
    }

    public void setOccupations(List<String> occupations) {
        this.occupations = occupations;
    }
    
}
