package ee.hitsa.ois.web.commandobject.studymaterial;

import ee.hitsa.ois.web.commandobject.SearchCommand;

public class StudyMaterialAutocompleteCommand extends SearchCommand {

    private Long teacher;
    private Long journal;
    private Long subjectStudyPeriod;

    public Long getTeacher() {
        return teacher;
    }
    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }
    
    public Long getJournal() {
        return journal;
    }
    public void setJournal(Long journal) {
        this.journal = journal;
    }
    
    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }
    public void setSubjectStudyPeriod(Long subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }
    
}
