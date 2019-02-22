package ee.hitsa.ois.domain.enterprise;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.student.Student;

@Entity
public class PracticeApplication extends BaseEntityWithId {
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = true, updatable = true)
	@JsonIgnore
    private PracticeAdmission practiceAdmission;
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
	private Student student;
	private String addInfo;
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = true)
	@JsonIgnore
	private Classifier status;
	private LocalDate submitted;
	private String rejectReason;
	
	@OneToOne(mappedBy = "practiceApplication")
	private Contract contract;
	
	public PracticeAdmission getPracticeAdmission() {
		return practiceAdmission;
	}
	public void setPracticeAdmission(PracticeAdmission practiceAdmission) {
		this.practiceAdmission = practiceAdmission;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getAddInfo() {
		return addInfo;
	}
	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}
	public Classifier getStatus() {
		return status;
	}
	public void setStatus(Classifier status) {
		this.status = status;
	}
	public LocalDate getSubmitted() {
        return submitted;
    }
    public void setSubmitted(LocalDate submitted) {
        this.submitted = submitted;
    }
    public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
    public Contract getContract() {
        return contract;
    }
    public void setContract(Contract contract) {
        this.contract = contract;
    }

}
