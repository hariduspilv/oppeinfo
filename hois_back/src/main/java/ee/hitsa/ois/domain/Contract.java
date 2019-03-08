package ee.hitsa.ois.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.Email;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.directive.DirectiveCoordinator;
import ee.hitsa.ois.domain.enterprise.Enterprise;
import ee.hitsa.ois.domain.enterprise.PracticeApplication;
import ee.hitsa.ois.domain.enterprise.PracticeEvaluation;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.teacher.Teacher;

@Entity
public class Contract extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Student student;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_version_omodule_id", nullable = false)
    private CurriculumVersionOccupationModule module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_version_omodule_theme_id")
    private CurriculumVersionOccupationModuleTheme theme;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Enterprise enterprise;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private DirectiveCoordinator contractCoordinator;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier cancelReason;

    @Column(nullable = false)
    private BigDecimal credits;

    @Column(nullable = false)
    private Short hours;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private String practicePlace;

    private String contractNr;

    @Column(nullable = false)
    private String contactPersonName;
    private String contactPersonPhone;

    @Column(nullable = false)
    @Email
    private String contactPersonEmail;
    private String otherSupervisor;
    @Column(nullable = false)
    private String practicePlan;
    private String canceledBy;
    
    private LocalDate canceled;
    private LocalDateTime ekisDate;
    private LocalDate confirmDate;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contract_id", nullable = false, updatable = false , insertable = false)
    private List<ContractSupervisor> contractSupervisors = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contract_id", nullable = false, updatable = false , insertable = false)
    private List<ContractModuleSubject> moduleSubjects = new ArrayList<>();

    private String cancelDesc;
    private Long wdId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private PracticeEvaluation practiceEvaluation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private PracticeEvaluation studentPracticeEvaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;
    
    private Boolean isPracticeAbsence;
    private Boolean isPracticeHidden;
    private Boolean isPracticeSchool;
    private Boolean isPracticeTelework;
    private Boolean isPracticeEnterprise;
    private Boolean isPracticeOther;
    
    @OneToOne(mappedBy = "contract")
    private StudentAbsence studentAbsence;
    
    @OneToOne
    private PracticeApplication practiceApplication;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public DirectiveCoordinator getContractCoordinator() {
        return contractCoordinator;
    }

    public void setContractCoordinator(DirectiveCoordinator contractCoordinator) {
        this.contractCoordinator = contractCoordinator;
    }

    public Classifier getStatus() {
        return status;
    }

    public void setStatus(Classifier status) {
        this.status = status;
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

    public String getContractNr() {
        return contractNr;
    }

    public void setContractNr(String contractNr) {
        this.contractNr = contractNr;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    public String getOtherSupervisor() {
        return otherSupervisor;
    }

    public void setOtherSupervisor(String otherSupervisor) {
        this.otherSupervisor = otherSupervisor;
    }

    public String getPracticePlan() {
        return practicePlan;
    }

    public void setPracticePlan(String practicePlan) {
        this.practicePlan = practicePlan;
    }

    public LocalDateTime getEkisDate() {
        return ekisDate;
    }

    public void setEkisDate(LocalDateTime ekisDate) {
        this.ekisDate = ekisDate;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Long getWdId() {
        return wdId;
    }

    public void setWdId(Long wdId) {
        this.wdId = wdId;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Boolean getIsPracticeAbsence() {
        return isPracticeAbsence;
    }

    public void setIsPracticeAbsence(Boolean isPracticeAbsence) {
        this.isPracticeAbsence = isPracticeAbsence;
    }

    public Boolean getIsPracticeHidden() {
        return isPracticeHidden;
    }

    public void setIsPracticeHidden(Boolean isPracticeHidden) {
        this.isPracticeHidden = isPracticeHidden;
    }

    public StudentAbsence getStudentAbsence() {
        return studentAbsence;
    }

    public void setStudentAbsence(StudentAbsence studentAbsence) {
        this.studentAbsence = studentAbsence;
    }

    public PracticeApplication getPracticeApplication() {
        return practiceApplication;
    }

    public void setPracticeApplication(PracticeApplication practiceApplication) {
        this.practiceApplication = practiceApplication;
    }

	public LocalDate getCanceled() {
		return canceled;
	}

	public void setCanceled(LocalDate canceled) {
		this.canceled = canceled;
	}

	public Classifier getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(Classifier cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getCancelDesc() {
		return cancelDesc;
	}

	public void setCancelDesc(String cancelDesc) {
		this.cancelDesc = cancelDesc;
	}

    public String getCanceledBy() {
        return canceledBy;
    }

    public void setCanceledBy(String canceledBy) {
        this.canceledBy = canceledBy;
    }

    public Boolean getIsPracticeSchool() {
        return isPracticeSchool;
    }

    public void setIsPracticeSchool(Boolean isPracticeSchool) {
        this.isPracticeSchool = isPracticeSchool;
    }

    public Boolean getIsPracticeTelework() {
        return isPracticeTelework;
    }

    public void setIsPracticeTelework(Boolean isPracticeTelework) {
        this.isPracticeTelework = isPracticeTelework;
    }

    public Boolean getIsPracticeEnterprise() {
        return isPracticeEnterprise;
    }

    public void setIsPracticeEnterprise(Boolean isPracticeEnterprise) {
        this.isPracticeEnterprise = isPracticeEnterprise;
    }

    public Boolean getIsPracticeOther() {
        return isPracticeOther;
    }

    public void setIsPracticeOther(Boolean isPracticeOther) {
        this.isPracticeOther = isPracticeOther;
    }

    public List<ContractSupervisor> getContractSupervisors() {
        return contractSupervisors;
    }

    public void setContractSupervisors(List<ContractSupervisor> contractSupervisors) {
        this.contractSupervisors = contractSupervisors;
    }

    public List<ContractModuleSubject> getModuleSubjects() {
        return moduleSubjects;
    }

    public void setModuleSubjects(List<ContractModuleSubject> moduleSubjects) {
        this.moduleSubjects = moduleSubjects;
    }

    public PracticeEvaluation getPracticeEvaluation() {
        return practiceEvaluation;
    }

    public void setPracticeEvaluation(PracticeEvaluation practiceEvaluation) {
        this.practiceEvaluation = practiceEvaluation;
    }

    public PracticeEvaluation getStudentPracticeEvaluation() {
        return studentPracticeEvaluation;
    }

    public void setStudentPracticeEvaluation(PracticeEvaluation studentPracticeEvaluation) {
        this.studentPracticeEvaluation = studentPracticeEvaluation;
    }
    
}
