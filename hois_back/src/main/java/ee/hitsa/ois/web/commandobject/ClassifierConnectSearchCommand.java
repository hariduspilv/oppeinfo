package ee.hitsa.ois.web.commandobject;

import java.util.List;

public class ClassifierConnectSearchCommand extends SearchCommand {

    private String mainClassifierCode;
    private List<String> connectClassifierCode;
    private List<String> connectClassifierMainClassCode;

	private List<String> classifierCode;
	private List<String> classifierMainClassCode;

    public String getMainClassifierCode() {
        return mainClassifierCode;
    }
    public void setMainClassifierCode(String mainClassifierCode) {
        this.mainClassifierCode = mainClassifierCode;
    }
    public List<String> getConnectClassifierCode() {
        return connectClassifierCode;
    }
    public void setConnectClassifierCode(List<String> connectClassifierCode) {
        this.connectClassifierCode = connectClassifierCode;
    }
    public List<String> getClassifierCode() {
        return classifierCode;
    }
    public void setClassifierCode(List<String> classifierCode) {
        this.classifierCode = classifierCode;
    }
    public List<String> getConnectClassifierMainClassCode() {
        return connectClassifierMainClassCode;
    }
    public void setConnectClassifierMainClassCode(List<String> connectClassifierMainClassCode) {
        this.connectClassifierMainClassCode = connectClassifierMainClassCode;
    }
    public List<String> getClassifierMainClassCode() {
        return classifierMainClassCode;
    }
    public void setClassifierMainClassCode(List<String> classifierMainClassCode) {
        this.classifierMainClassCode = classifierMainClassCode;
    }

}
