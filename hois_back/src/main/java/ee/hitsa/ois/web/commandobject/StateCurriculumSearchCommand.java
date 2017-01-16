package ee.hitsa.ois.web.commandobject;

import java.util.ArrayList;

public class StateCurriculumSearchCommand extends SearchCommand {

	private Long validFromMillis;
	private Long validThruMillis;
	private ArrayList<String> statusCode;
	private ArrayList<String> iscedClassCode;
	private ArrayList<String> ekrLevels;

	public ArrayList<String> getEkrLevels() {
		return ekrLevels;
	}

	public void setEkrLevels(ArrayList<String> ekrLevels) {
		this.ekrLevels = ekrLevels;
	}

	public ArrayList<String> getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(ArrayList<String> statusCode) {
		this.statusCode = statusCode;
	}

	public ArrayList<String> getIscedClassCode() {
		return iscedClassCode;
	}

	public void setIscedClassCode(ArrayList<String> iscedClassCode) {
		this.iscedClassCode = iscedClassCode;
	}

	public Long getValidFromMillis() {
		return validFromMillis;
	}

	public void setValidFromMillis(Long validFromMillis) {
		this.validFromMillis = validFromMillis;
	}

	public Long getValidThruMillis() {
		return validThruMillis;
	}

	public void setValidThruMillis(Long validThruMillis) {
		this.validThruMillis = validThruMillis;
	}
}
