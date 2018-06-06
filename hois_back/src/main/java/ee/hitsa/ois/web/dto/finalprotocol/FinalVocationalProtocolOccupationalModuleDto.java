package ee.hitsa.ois.web.dto.finalprotocol;

import java.util.Collection;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class FinalVocationalProtocolOccupationalModuleDto {

    private Collection<FinalVocationalProtocolStudentDto> occupationModuleStudents;
    private AutocompleteResult teacher;

    public Collection<FinalVocationalProtocolStudentDto> getOccupationModuleStudents() {
        return occupationModuleStudents;
    }
    public void setOccupationModuleStudents(Collection<FinalVocationalProtocolStudentDto> occupationModuleStudents) {
        this.occupationModuleStudents = occupationModuleStudents;
    }
    public AutocompleteResult getTeacher() {
        return teacher;
    }
    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }
}
