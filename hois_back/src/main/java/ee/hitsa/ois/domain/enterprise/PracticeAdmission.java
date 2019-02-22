package ee.hitsa.ois.domain.enterprise;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class PracticeAdmission extends BaseEntityWithId {
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = true, updatable = true)
	@JsonIgnore
    private EnterpriseSchool enterpriseSchool;
	private LocalDate validFrom;
	private LocalDate validThru;
	private Short places;
	private String addInfo;
	private Boolean isStrict;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "practice_admission_id", nullable = false, updatable = false , insertable = false)
	private List<PracticeAdmissionStudentGroup> practiceAdmissionStudentGroups = new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "practice_admission_id", nullable = false, updatable = false , insertable = false)
	private List<PracticeApplication> practiceApplications = new ArrayList<>();
	
	public List<PracticeApplication> getPracticeApplications() {
		return practiceApplications;
	}
	public void setPracticeApplications(List<PracticeApplication> practiceApplications) {
		this.practiceApplications.clear();
		this.practiceApplications.addAll(practiceApplications);
	}
	public List<PracticeAdmissionStudentGroup> getPracticeAdmissionStudentGroups() {
		return practiceAdmissionStudentGroups;
	}
	public void setPracticeAdmissionStudentGroups(List<PracticeAdmissionStudentGroup> practiceAdmissionStudentGroups) {
		this.practiceAdmissionStudentGroups.clear();
		this.practiceAdmissionStudentGroups.addAll(practiceAdmissionStudentGroups);
	}
	public String getAddInfo() {
		return addInfo;
	}
	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}
	public Short getPlaces() {
		return places;
	}
	public void setPlaces(Short places) {
		this.places = places;
	}
	public LocalDate getValidThru() {
		return validThru;
	}
	public void setValidThru(LocalDate validThru) {
		this.validThru = validThru;
	}
	public LocalDate getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}
	public void setEnterpriseSchool(EnterpriseSchool enterpriseSchool) {
		this.enterpriseSchool = enterpriseSchool;
	}
	public EnterpriseSchool getEnterpriseSchool() {
		return enterpriseSchool;
	}
    public Boolean getIsStrict() {
        return isStrict;
    }
    public void setIsStrict(Boolean isStrict) {
        this.isStrict = isStrict;
    }

}
