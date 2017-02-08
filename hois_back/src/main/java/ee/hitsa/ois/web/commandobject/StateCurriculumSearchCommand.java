package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.validation.DateRange;

@DateRange
public class StateCurriculumSearchCommand extends SearchCommand {

	private LocalDate validFrom;
	private LocalDate validThru;
	private List<String> statusCode;
	private List<String> iscedClassCode;
	private List<String> ekrLevels;

	public List<String> getEkrLevels() {
		return ekrLevels;
	}

	public void setEkrLevels(List<String> ekrLevels) {
		this.ekrLevels = ekrLevels;
	}

	public List<String> getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(List<String> statusCode) {
		this.statusCode = statusCode;
	}

	public List<String> getIscedClassCode() {
		return iscedClassCode;
	}

	public void setIscedClassCode(List<String> iscedClassCode) {
		this.iscedClassCode = iscedClassCode;
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
}
