package ee.hitsa.ois.domain.studymaterial;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.timetable.Journal;

@Entity
public class StudyMaterialConnect extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private StudyMaterial studyMaterial;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Journal journal;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SubjectStudyPeriod subjectStudyPeriod;
    
    public StudyMaterial getStudyMaterial() {
        return studyMaterial;
    }
    public void setStudyMaterial(StudyMaterial studyMaterial) {
        this.studyMaterial = studyMaterial;
    }
    
    public Journal getJournal() {
        return journal;
    }
    public void setJournal(Journal journal) {
        this.journal = journal;
    }
    
    public SubjectStudyPeriod getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }
    public void setSubjectStudyPeriod(SubjectStudyPeriod subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }
    
}
