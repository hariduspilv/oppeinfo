package ee.hitsa.ois.web.commandobject.enterprise;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.web.dto.student.StudentGroupResult;

public class PracticeAdmissionCommand {
	
	private LocalDate validFrom;
	private LocalDate validThru;
	private Short places;
	private String addInfo;
	private Boolean allStudentGroups;
	private Boolean isStrict;
	private List<StudentGroupResult> studentGroups;
	
	public LocalDate getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}
	public LocalDate getValidThru() {
		return validThru;
	}
	public void setValidThru(LocalDate validThru) {
		this.validThru = validThru;
	}
	public Short getPlaces() {
		return places;
	}
	public void setPlaces(Short places) {
		this.places = places;
	}
	public String getAddInfo() {
		return addInfo;
	}
	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}
	public Boolean getAllStudentGroups() {
		return allStudentGroups;
	}
	public void setAllStudentGroups(Boolean allStudentGroups) {
		this.allStudentGroups = allStudentGroups;
	}
	public List<StudentGroupResult> getStudentGroups() {
		return studentGroups;
	}
	public void setStudentGroups(List<StudentGroupResult> studentGroups) {
		this.studentGroups = studentGroups;
	}
    public Boolean getIsStrict() {
        return isStrict;
    }
    public void setIsStrict(Boolean isStrict) {
        this.isStrict = isStrict;
    }
}
