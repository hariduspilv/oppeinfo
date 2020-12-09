package ee.hitsa.ois.web.commandobject.api;

import java.time.LocalDate;

import ee.hitsa.ois.validation.Required;

public class StudentCommand {
	
	@Required
	private LocalDate startFrom;
	private LocalDate startThru;
	
	public LocalDate getStartFrom() {
		return startFrom;
	}
	public void setStartFrom(LocalDate startFrom) {
		this.startFrom = startFrom;
	}
	public LocalDate getStartThru() {
		return startThru;
	}
	public void setStartThru(LocalDate startThru) {
		this.startThru = startThru;
	}
}
