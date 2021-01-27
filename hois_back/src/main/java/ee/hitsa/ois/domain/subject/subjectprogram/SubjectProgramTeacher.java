package ee.hitsa.ois.domain.subject.subjectprogram;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SubjectProgramTeacher extends BaseEntityWithId {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SubjectProgram subjectProgram;
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST})
    private SubjectStudyPeriodTeacher subjectStudyPeriodTeacher;
    private Boolean isReady;
    private LocalDateTime isReadyDt;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private List<SubjectProgramStudyContentTeacher> studyContentTeachers;

    public SubjectProgram getSubjectProgram() {
        return subjectProgram;
    }

    public void setSubjectProgram(SubjectProgram subjectProgram) {
        this.subjectProgram = subjectProgram;
    }

    public SubjectStudyPeriodTeacher getSubjectStudyPeriodTeacher() {
        return subjectStudyPeriodTeacher;
    }

    public void setSubjectStudyPeriodTeacher(SubjectStudyPeriodTeacher subjectStudyPeriodTeacher) {
        this.subjectStudyPeriodTeacher = subjectStudyPeriodTeacher;
    }

    public Boolean getIsReady() {
        return isReady;
    }

    public void setIsReady(Boolean isReady) {
        this.isReady = isReady;
    }

    public LocalDateTime getIsReadyDt() {
        return isReadyDt;
    }

    public void setIsReadyDt(LocalDateTime isReadyDt) {
        this.isReadyDt = isReadyDt;
    }

    public List<SubjectProgramStudyContentTeacher> getStudyContentTeachers() {
        return studyContentTeachers != null ? studyContentTeachers : (studyContentTeachers = new ArrayList<>());
    }

    public void setStudyContentTeachers(List<SubjectProgramStudyContentTeacher> studyContentTeachers) {
        getStudyContentTeachers().clear();
        getStudyContentTeachers().addAll(studyContentTeachers);
    }
}
