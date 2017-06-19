package ee.hitsa.ois.domain.protocol;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import ee.hitsa.ois.domain.BaseEntity;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.teacher.Teacher;

@Entity
public class ProtocolVdata extends BaseEntity {

    @Id
    @GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "protocol"))
    @GeneratedValue(generator = "generator")
    @Column(unique = true, nullable = false)
    private Long protocolId;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Protocol protocol;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_version_omodule_id", nullable = false, updatable = false)
    private CurriculumVersionOccupationModule curriculumVersionOccupationModule;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private CurriculumVersion curriculumVersion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private StudyYear studyYear;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Teacher teacher;

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public CurriculumVersionOccupationModule getCurriculumVersionOccupationModule() {
        return curriculumVersionOccupationModule;
    }

    public void setCurriculumVersionOccupationModule(
            CurriculumVersionOccupationModule curriculumVersionOccupationModule) {
        this.curriculumVersionOccupationModule = curriculumVersionOccupationModule;
    }

    public CurriculumVersion getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(CurriculumVersion curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public StudyYear getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

}
