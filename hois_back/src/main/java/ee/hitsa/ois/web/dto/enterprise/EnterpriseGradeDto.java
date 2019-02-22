package ee.hitsa.ois.web.dto.enterprise;

import java.time.LocalDate;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class EnterpriseGradeDto {
	
	private Long id;
	private String ratingCode;
	private LocalDate ratingThru;
	private String ratingInfo;
	private AutocompleteResult schoolName;
	
	public String getRatingCode() {
		return ratingCode;
	}
	public void setRatingCode(String ratingCode) {
		this.ratingCode = ratingCode;
	}
	public LocalDate getRatingThru() {
		return ratingThru;
	}
	public void setRatingThru(LocalDate ratingThru) {
		this.ratingThru = ratingThru;
	}
	public String getRatingInfo() {
		return ratingInfo;
	}
	public void setRatingInfo(String ratingInfo) {
		this.ratingInfo = ratingInfo;
	}
	public AutocompleteResult getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(AutocompleteResult schoolName) {
		this.schoolName = schoolName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
