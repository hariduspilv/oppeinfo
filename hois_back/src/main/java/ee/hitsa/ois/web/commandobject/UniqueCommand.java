package ee.hitsa.ois.web.commandobject;

public class UniqueCommand {
	Long id;
	Long fk;
	String paramName;
	String paramValue;

	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFk() {
		return fk;
	}
	public void setFk(Long fk) {
		this.fk = fk;
	}
}
