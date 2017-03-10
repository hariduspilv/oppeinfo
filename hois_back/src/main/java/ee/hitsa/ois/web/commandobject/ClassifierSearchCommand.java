package ee.hitsa.ois.web.commandobject;

import java.util.List;

public class ClassifierSearchCommand extends SearchCommand {

    private String value;
    private String mainClassCode;
    private List<String> mainClassCodes;
    private Boolean vocational;
    private Boolean higher;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

    public String getMainClassCode() {
        return mainClassCode;
    }

    public void setMainClassCode(String mainClassCode) {
        this.mainClassCode = mainClassCode;
    }

    public Boolean isVocational() {
        return vocational;
    }

    public void setVocational(Boolean vocational) {
        this.vocational = vocational;
    }

    public Boolean isHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

	public List<String> getMainClassCodes() {
        return mainClassCodes;
    }

    public void setMainClassCodes(List<String> mainClassCodes) {
        this.mainClassCodes = mainClassCodes;
    }

    @Override
	public String toString() {
		return "ClassifierSearchCommand [value=" + value + ", code=" + getCode() + ", name=" + getName() + ", mainClassCode="
				+ mainClassCode + ", language=" + getLang() + "]";
	}


}
