package ee.hitsa.ois.web.commandobject.application;

import java.util.Set;

import ee.hitsa.ois.web.dto.application.ApplicationPlannedSubjectDto;

public class ApplicationSubjectForm {
    
    private Set<ApplicationPlannedSubjectDto> plannedSubjects;

    public Set<ApplicationPlannedSubjectDto> getPlannedSubjects() {
        return plannedSubjects;
    }

    public void setPlannedSubjects(Set<ApplicationPlannedSubjectDto> plannedSubjects) {
        this.plannedSubjects = plannedSubjects;
    }
}
