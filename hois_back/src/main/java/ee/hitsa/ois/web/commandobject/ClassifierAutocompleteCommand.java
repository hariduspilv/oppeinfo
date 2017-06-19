package ee.hitsa.ois.web.commandobject;

import java.util.List;

import javax.validation.constraints.AssertTrue;

public class ClassifierAutocompleteCommand {

    private String mainClassCode;
    private List<String> mainClassCodes;

    public String getMainClassCode() {
        return mainClassCode;
    }

    public void setMainClassCode(String mainClassCode) {
        this.mainClassCode = mainClassCode;
    }

    public List<String> getMainClassCodes() {
        return mainClassCodes;
    }

    public void setMainClassCodes(List<String> mainClassCodes) {
        this.mainClassCodes = mainClassCodes;
    }

    @AssertTrue
    public boolean isValid() {
        return mainClassCode != null || !(mainClassCodes == null || mainClassCodes.isEmpty());
    }
}
