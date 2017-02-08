package ee.hitsa.ois.web.commandobject;

import java.util.List;

public class ClassifierConnectSearchCommand extends SearchCommand {

	private String classifierCode;
	private String connectClassifierCode;
	private String mainClassifierCode;
	private List<String> connectClassifierCodes;


	public String getClassifierCode() {
		return classifierCode;
	}
	public void setClassifierCode(String classifierCode) {
		this.classifierCode = classifierCode;
	}
	public String getConnectClassifierCode() {
		return connectClassifierCode;
	}
	public void setConnectClassifierCode(String connectClassifierCode) {
		this.connectClassifierCode = connectClassifierCode;
	}
    public String getMainClassifierCode() {
        return mainClassifierCode;
    }
    public void setMainClassifierCode(String mainClassifierCode) {
        this.mainClassifierCode = mainClassifierCode;
    }
    public List<String> getConnectClassifierCodes() {
        return connectClassifierCodes;
    }
    public void setConnectClassifierCodes(List<String> connectClassifierCodes) {
        this.connectClassifierCodes = connectClassifierCodes;
    }

}
