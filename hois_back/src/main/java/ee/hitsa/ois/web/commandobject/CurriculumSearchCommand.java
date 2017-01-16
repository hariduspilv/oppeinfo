package ee.hitsa.ois.web.commandobject;

import java.util.ArrayList;

public class CurriculumSearchCommand extends SearchCommand {
	ArrayList<Long> school;
	ArrayList<String> studyLevel;
	ArrayList<String> ekrLevel;
	String htmCode;
	ArrayList<String> iscedClassCode;
	ArrayList<String> studyLanguage;
	Double creditsMin;
	Double creditsMax;
	Long validFromMillis;
	Long validThruMillis;
	ArrayList<String> status;
	ArrayList<String> ehisStatus;
	Boolean isJoint;
	ArrayList<Long> department;
	
	@Override
	public String toString() {
		return "CurriculumSearchCommand [school=" + school + ", studyLevel=" + studyLevel + ", ekrLevel=" + ekrLevel
				+ ", htmCode=" + htmCode + ", iscedClassCode=" + iscedClassCode 
				+ ", studyLanguage=" + studyLanguage + ", creditsMin=" + creditsMin + ", creditsMax=" + creditsMax
				+ ", validFromMillis=" + validFromMillis + ", validThruMillis=" + validThruMillis + ", status=" + status
				+ ", ehisStatus=" + ehisStatus + ", isJoint=" + isJoint + ", department=" + department + "]";
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

	public ArrayList<Long> getSchool() {
		return school;
	}

	public ArrayList<String> getStudyLevel() {
		return studyLevel;
	}

	public ArrayList<String> getEkrLevel() {
		return ekrLevel;
	}

	public String getHtmCode() {
		return htmCode;
	}

	public ArrayList<String> getIscedClassCode() {
		return iscedClassCode;
	}

	public ArrayList<String> getStudyLanguage() {
		return studyLanguage;
	}

	public Long getValidFromMillis() {
		return validFromMillis;
	}

	public Long getValidThruMillis() {
		return validThruMillis;
	}

	public ArrayList<String> getStatus() {
		return status;
	}

	public ArrayList<String> getEhisStatus() {
		return ehisStatus;
	}

	public Boolean getIsJoint() {
		return isJoint;
	}

	public ArrayList<Long> getDepartment() {
		return department;
	}

	public void setSchool(ArrayList<Long> school) {
		this.school = school;
	}

	public void setStudyLevel(ArrayList<String> studyLevel) {
		this.studyLevel = studyLevel;
	}

	public void setEkrLevel(ArrayList<String> ekrLevel) {
		this.ekrLevel = ekrLevel;
	}

	public void setHtmCode(String htmCode) {
		this.htmCode = htmCode;
	}

	public void setIscedClassCode(ArrayList<String> iscedClassCode) {
		this.iscedClassCode = iscedClassCode;
	}


	public void setStudyLanguage(ArrayList<String> studyLanguage) {
		this.studyLanguage = studyLanguage;
	}

	public void setValidFromMillis(Long validFromMillis) {
		this.validFromMillis = validFromMillis;
	}

	public void setValidThruMillis(Long validThruMillis) {
		this.validThruMillis = validThruMillis;
	}

	public void setStatus(ArrayList<String> status) {
		this.status = status;
	}

	public void setEhisStatus(ArrayList<String> ehisStatus) {
		this.ehisStatus = ehisStatus;
	}

	public void setIsJoint(Boolean isJoint) {
		this.isJoint = isJoint;
	}

	public void setDepartment(ArrayList<Long> department) {
		this.department = department;
	}
}
