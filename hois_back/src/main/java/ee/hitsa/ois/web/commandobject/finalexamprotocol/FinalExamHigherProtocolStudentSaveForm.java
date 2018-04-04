package ee.hitsa.ois.web.commandobject.finalexamprotocol;

import java.util.List;

import ee.hitsa.ois.web.dto.HigherProtocolStudentDto;

public class FinalExamHigherProtocolStudentSaveForm extends HigherProtocolStudentDto {

    private List<String> occupations; 

    public List<String> getOccupations() {
        return occupations;
    }

    public void setOccupations(List<String> occupations) {
        this.occupations = occupations;
    }
}
