package ee.hitsa.ois.domain.subject.subjectprogram;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class SubjectProgramStudyContent extends BaseEntityWithId {
    
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    private SubjectProgram subjectProgram;
    private String weekNr;
    private LocalDate studyDt;
    @Column(nullable=false)
    private String studyInfo;
    
    public SubjectProgram getSubjectProgram() {
        return subjectProgram;
    }
    public void setSubjectProgram(SubjectProgram subjectProgram) {
        this.subjectProgram = subjectProgram;
    }
    public String getWeekNr() {
        return weekNr;
    }
    public void setWeekNr(String weekNr) {
        this.weekNr = weekNr;
    }
    public LocalDate getStudyDt() {
        return studyDt;
    }
    public void setStudyDt(LocalDate studyDt) {
        this.studyDt = studyDt;
    }
    public String getStudyInfo() {
        return studyInfo;
    }
    public void setStudyInfo(String studyInfo) {
        this.studyInfo = studyInfo;
    }
}
