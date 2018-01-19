package ee.hitsa.ois.web.commandobject.scholarship;

import java.util.List;

public class ScholarshipSearchCommand {
    private Long studyPeriod;
    private String type;
    private String nameEt;
    private List<String> allowedStipendTypes;
    private Long isOpen;

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public List<String> getAllowedStipendTypes() {
        return allowedStipendTypes;
    }

    public void setAllowedStipendTypes(List<String> allowedStipendTypes) {
        this.allowedStipendTypes = allowedStipendTypes;
    }

    public Long getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Long isOpen) {
        this.isOpen = isOpen;
    }

}
