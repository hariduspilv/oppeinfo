package ee.hitsa.ois.domain.poll;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import ee.hitsa.ois.domain.BaseEntity;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.ContractSupervisor;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.PracticeJournal;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.teacher.Teacher;

@Entity
public class ResponseObject extends BaseEntity {
    
    @Id
    @GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "response"))
    @GeneratedValue(generator = "generator")
    @Column(unique = true, nullable = false)
    private Long responseId;
    
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Response response;
    
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Student student;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Teacher teacher;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private PollTarget pollTarget;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Person person;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private ContractSupervisor contractSupervisor;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private CurriculumVersion curriculumVersion;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Classifier studyForm;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PracticeJournal practiceJournal;
    private String pollUrl;
    
    public Response getResponse() {
        return response;
    }
    public void setResponse(Response response) {
        this.response = response;
    }
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public Teacher getTeacher() {
        return teacher;
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public PollTarget getPollTarget() {
        return pollTarget;
    }
    public void setPollTarget(PollTarget pollTarget) {
        this.pollTarget = pollTarget;
    }
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }
    public ContractSupervisor getContractSupervisor() {
        return contractSupervisor;
    }
    public void setContractSupervisor(ContractSupervisor contractSupervisor) {
        this.contractSupervisor = contractSupervisor;
    }
    public CurriculumVersion getCurriculumVersion() {
        return curriculumVersion;
    }
    public void setCurriculumVersion(CurriculumVersion curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }
    public Classifier getStudyForm() {
        return studyForm;
    }
    public void setStudyForm(Classifier studyForm) {
        this.studyForm = studyForm;
    }
    
    @Override
    public int hashCode() {
        return response == null ? 31 : response.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || response == null || !Hibernate.getClass(this).equals(Hibernate.getClass(obj))) {
            return false;
        }

        return obj instanceof Classifier && response.equals(((ResponseObject) obj).getResponse());
    }
    public String getPollUrl() {
        return pollUrl;
    }
    public void setPollUrl(String pollUrl) {
        this.pollUrl = pollUrl;
    }
    public PracticeJournal getPracticeJournal() {
        return practiceJournal;
    }
    public void setPracticeJournal(PracticeJournal practiceJournal) {
        this.practiceJournal = practiceJournal;
    }
}
