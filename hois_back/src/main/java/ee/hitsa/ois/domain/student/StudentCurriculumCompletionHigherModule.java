package ee.hitsa.ois.domain.student;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="student_curriculum_completion_hmodule")
public class StudentCurriculumCompletionHigherModule extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private Student student;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="curriculum_version_hmodule_id", nullable = false, updatable = false)
    private CurriculumVersionHigherModule module;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public CurriculumVersionHigherModule getModule() {
        return module;
    }

    public void setModule(CurriculumVersionHigherModule module) {
        this.module = module;
    }

}
