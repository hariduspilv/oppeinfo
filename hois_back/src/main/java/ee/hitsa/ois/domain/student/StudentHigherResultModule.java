package ee.hitsa.ois.domain.student;

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
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;

@Entity
public class StudentHigherResultModule extends BaseEntity {

    @Id
    @GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "studentHigherResult"))
    @GeneratedValue(generator = "generator")
    @Column(unique = true, nullable = false)
    private Long studentHigherResultId;
    
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private StudentHigherResult studentHigherResult;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = true)
    private CurriculumVersionHigherModule curriculumVersionHmodule;

    private Boolean isOptional;

    public StudentHigherResult getStudentHigherResult() {
        return studentHigherResult;
    }

    public void setStudentHigherResult(StudentHigherResult studentHigherResult) {
        this.studentHigherResult = studentHigherResult;
    }

    public CurriculumVersionHigherModule getCurriculumVersionHmodule() {
        return curriculumVersionHmodule;
    }

    public void setCurriculumVersionHmodule(CurriculumVersionHigherModule curriculumVersionHmodule) {
        this.curriculumVersionHmodule = curriculumVersionHmodule;
    }

    public Boolean getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }

}
