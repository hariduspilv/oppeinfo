package ee.hitsa.ois.domain.subject.studyperiod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.MidtermTask;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.protocol.ProtocolHdata;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodCapacity;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodStudentGroup;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodSubgroup;

/**
 * NB! BeanUtils.copyProperties has been used on this class.
 * When you change/add properties, check:
 * SubjectStudyPeriodStudentGroupService.copyPlan
 */
@Entity
public class SubjectStudyPeriod extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private Subject subject;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private StudyPeriod studyPeriod;
    
    private String addInfo;
    private Long moodleCourseId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier declarationType;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier groupProportion;
    
    @OneToMany(mappedBy = "subjectStudyPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubjectStudyPeriodTeacher> teachers;

    @OneToMany(mappedBy = "subjectStudyPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubjectStudyPeriodStudentGroup> studentGroups;

    @OneToMany(mappedBy = "subjectStudyPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubjectStudyPeriodCapacity> capacities;

    @OneToMany(mappedBy = "subjectStudyPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubjectProgram> subjectPrograms;
    
    /*
     * This mapping is required for saving midtermTasks using EntityUtil.bindEntityCollection.
     * However, SubjectStudyPeriod cannot be removed via its controller if it has any midtermTask
     * Exception is thrown in SubjectStudyPeriodService.delete() in that case.
     */
    @OneToMany(mappedBy = "subjectStudyPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MidtermTask> midtermTasks;

    @OneToMany(mappedBy = "subjectStudyPeriod", fetch = FetchType.LAZY)
    private List<DeclarationSubject> declarationSubjects;
    
    @OneToMany(mappedBy = "subjectStudyPeriod", fetch = FetchType.LAZY)
    private List<ProtocolHdata> protocols;

    @Column(name = "is_capacity_diff")
    private Boolean capacityDiff;
    private Short places;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier coefficient;
    
    @OneToMany(mappedBy = "period", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<SubjectStudyPeriodSubgroup> subgroups;

    public List<ProtocolHdata> getProtocols() {
        return protocols != null ? protocols : (protocols = new ArrayList<>());
    }

    public void setProtocols(List<ProtocolHdata> protocols) {
        this.protocols = protocols;
    }

    public List<DeclarationSubject> getDeclarationSubjects() {
        return declarationSubjects != null ? declarationSubjects : (declarationSubjects = new ArrayList<>());
    }

    public void setDeclarationSubjects(List<DeclarationSubject> declarationSubjects) {
        this.declarationSubjects = declarationSubjects;
    }

    public List<MidtermTask> getMidtermTasks() {
        return midtermTasks != null ? midtermTasks : (midtermTasks = new ArrayList<>());
    }

    public void setMidtermTasks(List<MidtermTask> midtermTasks) {
        this.midtermTasks = midtermTasks;
    }

    public List<SubjectStudyPeriodCapacity> getCapacities() {
        return capacities != null ? capacities : (capacities = new ArrayList<>());
    }

    public void setCapacities(List<SubjectStudyPeriodCapacity> capacities) {
        getCapacities().clear();
        getCapacities().addAll(capacities);
    }

    public List<SubjectStudyPeriodStudentGroup> getStudentGroups() {
        return studentGroups != null ? studentGroups : (studentGroups = new ArrayList<>());
    }

    public void setStudentGroups(List<SubjectStudyPeriodStudentGroup> studentGroups) {
        getStudentGroups().clear();
        getStudentGroups().addAll(studentGroups);
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public StudyPeriod getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(StudyPeriod studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public List<SubjectStudyPeriodTeacher> getTeachers() {
        return teachers != null ? teachers : (teachers = new ArrayList<>());
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public Long getMoodleCourseId() {
        return moodleCourseId;
    }

    public void setMoodleCourseId(Long moodleCourseId) {
        this.moodleCourseId = moodleCourseId;
    }

    public Classifier getDeclarationType() {
        return declarationType;
    }

    public void setDeclarationType(Classifier declarationType) {
        this.declarationType = declarationType;
    }

    public Classifier getGroupProportion() {
        return groupProportion;
    }

    public void setGroupProportion(Classifier groupProportion) {
        this.groupProportion = groupProportion;
    }

    public void setTeachers(List<SubjectStudyPeriodTeacher> teachers) {
        getTeachers().clear();
        if(teachers != null) {
            getTeachers().addAll(teachers);
        }
    }

    public Boolean getCapacityDiff() {
        return capacityDiff;
    }

    public void setCapacityDiff(Boolean isCapacityDiff) {
        this.capacityDiff = isCapacityDiff;
    }

    public Short getPlaces() {
        return places;
    }

    public void setPlaces(Short places) {
        this.places = places;
    }

    public Set<SubjectStudyPeriodSubgroup> getSubgroups() {
        return subgroups != null ? subgroups : (subgroups = new HashSet<>());
    }

    public void setSubgroups(Set<SubjectStudyPeriodSubgroup> subgroups) {
        getSubgroups().clear();
        getSubgroups().addAll(subgroups);
    }

    public Classifier getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Classifier coefficient) {
        this.coefficient = coefficient;
    }

    public List<SubjectProgram> getSubjectPrograms() {
        return subjectPrograms != null ? subjectPrograms : (subjectPrograms = new ArrayList<>());
    }

    public void setSubjectPrograms(List<SubjectProgram> subjectPrograms) {
        getSubjectPrograms().clear();
        getSubjectPrograms().addAll(subjectPrograms);
    }
}
