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
	private List<String> iscedSuun;
	private List<String> curriculumGroup;
	private String iscedVald;
	private List<String> studyLanguage;
	private Long creditsMin;
	private Long creditsMax;
	private LocalDate validFrom;
	private LocalDate validThru;
	private List<String> status;
	private List<String> ehisStatus;
	private Boolean isJoint;
	private List<Long> department;
	private Boolean isVocational;
	private List<Long> subjects;
		
	public List<Long> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Long> subjects) {
        this.subjects = subjects;
    }

    public List<String> getCurriculumGroup() {
        return curriculumGroup;
    }

    public void setCurriculumGroup(List<String> curriculumGroup) {
        this.curriculumGroup = curriculumGroup;
    }

    public List<String> getIscedSuun() {
        return iscedSuun;
    }

    public void setIscedSuun(List<String> iscedSuun) {
        this.iscedSuun = iscedSuun;
    }

    public String getIscedVald() {
        return iscedVald;
    }

    public void setIscedVald(String iscedVald) {
        this.iscedVald = iscedVald;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public Long getCreditsMin() {
		return creditsMin;
	}

	public void setCreditsMin(Long creditsMin) {
		this.creditsMin = creditsMin;
	}

	public Long getCreditsMax() {
		return creditsMax;
	}

	public void setCreditsMax(Long creditsMax) {
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
