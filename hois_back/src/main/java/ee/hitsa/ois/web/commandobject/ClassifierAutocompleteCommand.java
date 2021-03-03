package ee.hitsa.ois.web.commandobject;

import ee.hitsa.ois.enums.Language;

import java.util.List;

import javax.validation.constraints.AssertTrue;

public class ClassifierAutocompleteCommand {

    private String mainClassCode;
    private List<String> mainClassCodes;
    private String name;
    private Language lang = Language.ET;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

    @AssertTrue
    public boolean isValid() {
        return mainClassCode != null || !(mainClassCodes == null || mainClassCodes.isEmpty());
    }
}
