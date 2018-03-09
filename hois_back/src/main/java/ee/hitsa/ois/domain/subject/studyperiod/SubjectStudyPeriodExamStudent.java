package ee.hitsa.ois.domain.subject.studyperiod;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.DeclarationSubject;

@Entity
public class SubjectStudyPeriodExamStudent extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private SubjectStudyPeriodExam subjectStudyPeriodExam;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private DeclarationSubject declarationSubject;

    public SubjectStudyPeriodExam getSubjectStudyPeriodExam() {
        return subjectStudyPeriodExam;
    }

    public void setSubjectStudyPeriodExam(SubjectStudyPeriodExam subjectStudyPeriodExam) {
        this.subjectStudyPeriodExam = subjectStudyPeriodExam;
    }

    public DeclarationSubject getDeclarationSubject() {
        return declarationSubject;
    }

    public void setDeclarationSubject(DeclarationSubject declarationSubject) {
        this.declarationSubject = declarationSubject;
    }
}
