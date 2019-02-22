package ee.hitsa.ois.web.commandobject.enterprise;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class PracticeEnterpriseIscedClassCommand {
	@ClassifierRestriction(MainClassCode.ISCED_RYHM)
	private String iscedClass;
	private Integer places;
	private String addInfo;
	
	public String getIscedClass() {
		return iscedClass;
	}
	
	public void setIscedClass(String iscedClass) {
		this.iscedClass = iscedClass;
	}

	public Integer getPlaces() {
		return places;
	}

	public void setPlaces(Integer places) {
		this.places = places;
	}

	public String getAddInfo() {
		return addInfo;
	}

	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}
}
