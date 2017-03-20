package ee.hitsa.ois.web.commandobject;

import java.util.List;

public class ClassifierConnectSearchCommand extends SearchCommand {

    private String mainClassifierCode;
    private List<String> connectClassifierCode;
	private List<String> classifierCode;

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

}
