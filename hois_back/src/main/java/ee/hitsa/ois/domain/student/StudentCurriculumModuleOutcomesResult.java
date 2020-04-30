package ee.hitsa.ois.domain.student;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.apelapplication.ApelApplication;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;
import ee.hitsa.ois.domain.teacher.Teacher;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class StudentCurriculumModuleOutcomesResult extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Student student;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private CurriculumModuleOutcome curriculumModuleOutcomes;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier grade;

    private LocalDate gradeDate;
    private LocalDateTime gradeInserted;
    private String gradeInsertedBy;
    private String addInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Teacher gradeInsertedTeacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private ApelApplication apelApplication;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_curriculum_module_outcomes_result_id", nullable = false, updatable = false)
    private Set<StudentCurriculumModuleOutcomesResultHistory> history = new HashSet<>();

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public CurriculumModuleOutcome getCurriculumModuleOutcomes() {
        return curriculumModuleOutcomes;
    }

    public void setCurriculumModuleOutcomes(CurriculumModuleOutcome curriculumModuleOutcomes) {
        this.curriculumModuleOutcomes = curriculumModuleOutcomes;
    }

    public Classifier getGrade() {
        return grade;
    }

    public void setGrade(Classifier grade) {
        this.grade = grade;
    }

    public LocalDate getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
    }

    public LocalDateTime getGradeInserted() {
        return gradeInserted;
    }

    public void setGradeInserted(LocalDateTime gradeInserted) {
        this.gradeInserted = gradeInserted;
    }

    public String getGradeInsertedBy() {
        return gradeInsertedBy;
    }

    public void setGradeInsertedBy(String gradeInsertedBy) {
        this.gradeInsertedBy = gradeInsertedBy;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public Teacher getGradeInsertedTeacher() {
        return gradeInsertedTeacher;
    }

    public void setGradeInsertedTeacher(Teacher gradeInsertedTeacher) {
        this.gradeInsertedTeacher = gradeInsertedTeacher;
    }

    public ApelApplication getApelApplication() {
        return apelApplication;
    }

    public void setApelApplication(ApelApplication apelApplication) {
        this.apelApplication = apelApplication;
    }

    public Set<StudentCurriculumModuleOutcomesResultHistory> getHistory() {
        return history;
    }

    public void setHistory(Set<StudentCurriculumModuleOutcomesResultHistory> history) {
        this.history = history;
    }

    public void addToHistory() {
        StudentCurriculumModuleOutcomesResultHistory history = new StudentCurriculumModuleOutcomesResultHistory();
        history.setStudentCurriculumModuleOutcomesResult(this);
        history.setGrade(this.getGrade());
        history.setGradeDate(this.getGradeDate());
        history.setGradeInserted(this.getGradeInserted());
        history.setGradeInsertedBy(this.getGradeInsertedBy());
        history.setAddInfo(this.getAddInfo());

        this.getHistory().add(history);
    }

    public void removeGrade() {
        this.setGrade(null);
        this.setGradeDate(null);
        this.setGradeInserted(null);
        this.setGradeInsertedBy(null);
        this.setAddInfo(null);
        this.setGradeInsertedTeacher(null);
        this.setApelApplication(null);
    }
}
