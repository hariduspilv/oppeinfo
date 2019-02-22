package ee.hitsa.ois.web.commandobject.enterprise;

import java.time.LocalDate;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class PracticeEnterpriseGradeCommand {
	
	@ClassifierRestriction(MainClassCode.PR_HINNANG)
	private String ratingCode;
	private LocalDate ratingThru;
	private String ratingInfo;
	
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
}
