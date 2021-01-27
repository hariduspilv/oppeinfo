package ee.hitsa.ois.domain.subject.subjectprogram;

import ee.hitsa.ois.domain.BaseEntityWithId;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SubjectProgramStudyContentTeacher extends BaseEntityWithId {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_program_study_content_id", updatable = false, nullable = false)
    private SubjectProgramStudyContent studyContent;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_program_teacher_id", updatable = false, nullable = false)
    private SubjectProgramTeacher teacher;

    public SubjectProgramStudyContent getStudyContent() {
        return studyContent;
    }

    public void setStudyContent(SubjectProgramStudyContent studyContent) {
        this.studyContent = studyContent;
    }

    public SubjectProgramTeacher getTeacher() {
        return teacher;
    }

    public void setTeacher(SubjectProgramTeacher teacher) {
        this.teacher = teacher;
    }
}
