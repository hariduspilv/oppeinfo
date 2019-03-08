package ee.hitsa.ois.web.dto.enterprise;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class EnterpriseAdmissionWithStudentGroupsDto {
	
	private Long id;
	private LocalDate validFrom;
	private LocalDate validThru;
	private Short places;
	private String addInfo;
	private Boolean allStudentGroups;
	private Boolean isStrict;
	private List<AutocompleteResult> studentGroups;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public List<AutocompleteResult> getStudentGroups() {
		return studentGroups;
	}
	public void setStudentGroups(List<AutocompleteResult> studentGroups) {
		this.studentGroups = studentGroups;
	}
	public Boolean getAllStudentGroups() {
		return allStudentGroups;
	}
	public void setAllStudentGroups(Boolean allStudentGroups) {
		this.allStudentGroups = allStudentGroups;
	}
    public Boolean getIsStrict() {
        return isStrict;
    }
    public void setIsStrict(Boolean isStrict) {
        this.isStrict = isStrict;
    }
}
