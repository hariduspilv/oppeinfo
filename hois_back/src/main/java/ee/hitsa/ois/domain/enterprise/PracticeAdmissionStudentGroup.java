package ee.hitsa.ois.domain.enterprise;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.student.StudentGroup;

@Entity
public class PracticeAdmissionStudentGroup extends BaseEntityWithId {
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = true, updatable = true)
	@JsonIgnore
    private PracticeAdmission practiceAdmission;
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = true, updatable = true)
	@JsonIgnore
	private StudentGroup studentGroup;
	
	public PracticeAdmission getPracticeAdmission() {
		return practiceAdmission;
	}
	
	public void setPracticeAdmission(PracticeAdmission practiceAdmission) {
		this.practiceAdmission = practiceAdmission;
	}
	
	public StudentGroup getStudentGroup() {
		return studentGroup;
	}
	public void setStudentGroup(StudentGroup studentGroup) {
		this.studentGroup = studentGroup;
	}
}
