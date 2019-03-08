package ee.hitsa.ois.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.enterprise.PracticeEvaluation;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.teacher.Teacher;

@Entity
public class PracticeJournal extends BaseEntityWithId {
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "practice_journal_id", nullable = false, updatable = false)
    private Set<PracticeJournalEvaluation> practiceJournalEvaluations = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "practice_journal_id", nullable = false, updatable = false)
    private Set<PracticeJournalEntry> practiceJournalEntries = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "practice_journal_id", nullable = false, updatable = false)
    private Set<PracticeJournalFile> practiceJournalFiles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "practice_journal_id", nullable = false, updatable = false)
    private Set<PracticeJournalModuleSubject> moduleSubjects = new HashSet<>();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private StudyYear studyYear;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Contract contract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_version_omodule_id", nullable = false)
    private CurriculumVersionOccupationModule module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_version_omodule_theme_id")
    private CurriculumVersionOccupationModuleTheme theme;

    @Column(nullable = false)
    private BigDecimal credits;

    @Column(nullable = false)
    private Short hours;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private String practicePlace;

    @Column(nullable = false)
    private String practicePlan;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Teacher teacher;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Classifier status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private PracticeEvaluation practiceEvaluation;

    //KUTSEHINDAMINE if module != null and KORGHINDAMINE if subject != null
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier grade;

    private LocalDateTime gradeInserted;

    private String practiceReport;
    private String supervisorComment;
    private String supervisorOpinion;
    private String teacherComment;
    private String teacherOpinion;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;

    public Set<PracticeJournalEntry> getPracticeJournalEntries() {
        return practiceJournalEntries;
    }

    public void setPracticeJournalEntries(Set<PracticeJournalEntry> practiceJournalEntries) {
        this.practiceJournalEntries = practiceJournalEntries;
    }

    public Set<PracticeJournalFile> getPracticeJournalFiles() {
        return practiceJournalFiles;
    }

    public void setPracticeJournalFiles(Set<PracticeJournalFile> practiceJournalFiles) {
        this.practiceJournalFiles = practiceJournalFiles;
    }

    public Set<PracticeJournalModuleSubject> getModuleSubjects() {
        return moduleSubjects;
    }

    public void setModuleSubjects(Set<PracticeJournalModuleSubject> moduleSubjects) {
        this.moduleSubjects = moduleSubjects;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public StudyYear getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public CurriculumVersionOccupationModule getModule() {
        return module;
    }

    public void setModule(CurriculumVersionOccupationModule module) {
        this.module = module;
    }

    public CurriculumVersionOccupationModuleTheme getTheme() {
        return theme;
    }

    public void setTheme(CurriculumVersionOccupationModuleTheme theme) {
        this.theme = theme;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public Short getHours() {
        return hours;
    }

    public void setHours(Short hours) {
        this.hours = hours;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getPracticePlace() {
        return practicePlace;
    }

    public void setPracticePlace(String practicePlace) {
        this.practicePlace = practicePlace;
    }

    public String getPracticePlan() {
        return practicePlan;
    }

    public void setPracticePlan(String practicePlan) {
        this.practicePlan = practicePlan;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Classifier getStatus() {
        return status;
    }

    public void setStatus(Classifier status) {
        this.status = status;
    }

    public Classifier getGrade() {
        return grade;
    }

    public void setGrade(Classifier grade) {
        this.grade = grade;
    }

    public LocalDateTime getGradeInserted() {
        return gradeInserted;
    }

    public void setGradeInserted(LocalDateTime gradeInserted) {
        this.gradeInserted = gradeInserted;
    }

    public String getPracticeReport() {
        return practiceReport;
    }

    public void setPracticeReport(String practiceReport) {
        this.practiceReport = practiceReport;
    }

    public String getSupervisorComment() {
        return supervisorComment;
    }

    public void setSupervisorComment(String supervisorComment) {
        this.supervisorComment = supervisorComment;
    }

    public String getSupervisorOpinion() {
        return supervisorOpinion;
    }

    public void setSupervisorOpinion(String supervisorOpinion) {
        this.supervisorOpinion = supervisorOpinion;
    }

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }

    public String getTeacherOpinion() {
        return teacherOpinion;
    }

    public void setTeacherOpinion(String teacherOpinion) {
        this.teacherOpinion = teacherOpinion;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public PracticeEvaluation getPracticeEvaluation() {
        return practiceEvaluation;
    }

    public void setPracticeEvaluation(PracticeEvaluation practiceEvaluation) {
        this.practiceEvaluation = practiceEvaluation;
    }

    public Set<PracticeJournalEvaluation> getPracticeJournalEvaluations() {
        return practiceJournalEvaluations;
    }

    public void setPracticeJournalEvaluations(Set<PracticeJournalEvaluation> practiceJournalEvaluations) {
        this.practiceJournalEvaluations = practiceJournalEvaluations;
    }

}
