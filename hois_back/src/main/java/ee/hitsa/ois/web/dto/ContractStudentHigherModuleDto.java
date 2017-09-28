package ee.hitsa.ois.web.dto;

import java.util.ArrayList;
import java.util.List;

public class ContractStudentHigherModuleDto {

    private AutocompleteResult module;
    private List<ContractStudentSubjectDto> subjects = new ArrayList<>();

    public AutocompleteResult getModule() {
        return module;
    }

    public void setModule(AutocompleteResult module) {
        this.module = module;
    }

    public List<ContractStudentSubjectDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<ContractStudentSubjectDto> subjects) {
        this.subjects = subjects;
    }

}
