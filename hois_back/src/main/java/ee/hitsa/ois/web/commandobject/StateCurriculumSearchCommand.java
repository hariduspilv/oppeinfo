package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.validation.DateRange;

@DateRange
public class StateCurriculumSearchCommand extends SearchCommand {

	private LocalDate validFrom;
	private LocalDate validThru;
	private List<String> status;
	private List<String> iscedClass;
	private List<String> ekrLevel;

	public List<String> getEkrLevel() {
		return ekrLevel;
	}

	public void setEkrLevel(List<String> ekrLevels) {
		this.ekrLevel = ekrLevels;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> statusCode) {
		this.status = statusCode;
	}

	public List<String> getIscedClass() {
		return iscedClass;
	}

	public void setIscedClass(List<String> iscedClassCode) {
		this.iscedClass = iscedClassCode;
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
