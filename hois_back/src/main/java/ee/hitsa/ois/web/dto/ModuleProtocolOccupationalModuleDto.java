package ee.hitsa.ois.web.dto;

import java.util.Collection;

public class ModuleProtocolOccupationalModuleDto {

    private Collection<ModuleProtocolStudentSelectDto> occupationModuleStudents;
    private AutocompleteResult teacher;

    public Collection<ModuleProtocolStudentSelectDto> getOccupationModuleStudents() {
        return occupationModuleStudents;
    }
    public void setOccupationModuleStudents(Collection<ModuleProtocolStudentSelectDto> occupationModuleStudents) {
        this.occupationModuleStudents = occupationModuleStudents;
    }
    public AutocompleteResult getTeacher() {
        return teacher;
    }
    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }


}
