package ee.hitsa.ois.web.commandobject.curriculum;

import java.util.HashSet;
import java.util.Set;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class CurriculumSchoolDepartmentCommand {

    @ClassifierRestriction(MainClassCode.EHIS_KOOL)
    private Set<String> ehisShools;
    
    private Long curriculum;
    
    public Set<String> getEhisShools() {
        return ehisShools != null ? ehisShools : (ehisShools = new HashSet<>());
    }

    public void setEhisShools(Set<String> ehisShools) {
        this.ehisShools = ehisShools;
    }

    public Long getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
    }
}
