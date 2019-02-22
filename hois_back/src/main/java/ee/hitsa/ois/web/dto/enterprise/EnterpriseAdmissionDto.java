package ee.hitsa.ois.web.dto.enterprise;

import java.time.LocalDate;

public class EnterpriseAdmissionDto {
	
	private Long id;
	private LocalDate validFrom;
	private LocalDate validThru;
	private Long places;
	private String addInfo;
	private String studentGroups;
	
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
	public Long getPlaces() {
		return places;
	}
	public void setPlaces(Long places) {
		this.places = places;
	}
	public String getAddInfo() {
		return addInfo;
	}
	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}
	public String getStudentGroups() {
		return studentGroups;
	}
	public void setStudentGroups(String studentGroups) {
		this.studentGroups = studentGroups;
	}
}
