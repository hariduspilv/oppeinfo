package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.validation.DateRange;

@DateRange
public class CurriculumSearchCommand extends SearchCommand {

	private List<Long> school;
	private List<String> studyLevel;
	private List<String> ekrLevel;
	private String merCode;
	private List<String> iscedClassCode;
	private List<String> studyLanguage;
	private Double creditsMin;
	private Double creditsMax;
	private LocalDate validFrom;
	private LocalDate validThru;
	private List<String> status;
	private List<String> ehisStatus;
	private Boolean isJoint;
	private List<Long> department;
	private Boolean isVocational;

	public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public Double getCreditsMin() {
		return creditsMin;
	}

	public void setCreditsMin(Double creditsMin) {
		this.creditsMin = creditsMin;
	}

	public Double getCreditsMax() {
		return creditsMax;
	}

	public void setCreditsMax(Double creditsMax) {
		this.creditsMax = creditsMax;
	}

	public List<Long> getSchool() {
		return school;
	}

	public List<String> getStudyLevel() {
		return studyLevel;
	}

	public List<String> getEkrLevel() {
		return ekrLevel;
	}

	public List<String> getIscedClassCode() {
		return iscedClassCode;
	}

	public List<String> getStudyLanguage() {
		return studyLanguage;
	}

	public LocalDate getValidFrom() {
		return validFrom;
	}

	public LocalDate getValidThru() {
		return validThru;
	}

	public List<String> getStatus() {
		return status;
	}

	public List<String> getEhisStatus() {
		return ehisStatus;
	}

	public Boolean getIsJoint() {
		return isJoint;
	}

	public List<Long> getDepartment() {
		return department;
	}

	public void setSchool(List<Long> school) {
		this.school = school;
	}

	public void setStudyLevel(List<String> studyLevel) {
		this.studyLevel = studyLevel;
	}

	public void setEkrLevel(List<String> ekrLevel) {
		this.ekrLevel = ekrLevel;
	}

	public void setIscedClassCode(List<String> iscedClassCode) {
		this.iscedClassCode = iscedClassCode;
	}


	public void setStudyLanguage(List<String> studyLanguage) {
		this.studyLanguage = studyLanguage;
	}

	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}

	public void setValidThru(LocalDate validThru) {
		this.validThru = validThru;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

	public void setEhisStatus(List<String> ehisStatus) {
		this.ehisStatus = ehisStatus;
	}

	public void setIsJoint(Boolean isJoint) {
		this.isJoint = isJoint;
	}

	public void setDepartment(List<Long> department) {
		this.department = department;
	}

    public Boolean getIsVocational() {
        return isVocational;
    }

    public void setIsVocational(Boolean isVocational) {
        this.isVocational = isVocational;
    }


}
