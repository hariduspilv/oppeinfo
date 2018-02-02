package ee.hitsa.ois.web.dto.finalexamprotocol;

import java.util.Collection;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class FinalExamVocationalProtocolOccupationalModuleDto {

    private Collection<FinalExamVocationalProtocolStudentDto> occupationModuleStudents;
    private AutocompleteResult teacher;

    public Collection<FinalExamVocationalProtocolStudentDto> getOccupationModuleStudents() {
        return occupationModuleStudents;
    }
    public void setOccupationModuleStudents(Collection<FinalExamVocationalProtocolStudentDto> occupationModuleStudents) {
        this.occupationModuleStudents = occupationModuleStudents;
    }
    public AutocompleteResult getTeacher() {
        return teacher;
    }
    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }
}
