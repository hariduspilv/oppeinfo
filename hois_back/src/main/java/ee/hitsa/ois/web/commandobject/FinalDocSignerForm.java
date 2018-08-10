package ee.hitsa.ois.web.commandobject;

public class FinalDocSignerForm extends VersionedCommand {

    private String name;
    private String position;
    private String positionEn;
    private Boolean isFirst;
    private Boolean isValid;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getPositionEn() {
        return positionEn;
    }
    public void setPositionEn(String positionEn) {
        this.positionEn = positionEn;
    }
    
    public Boolean getIsFirst() {
        return isFirst;
    }
    public void setIsFirst(Boolean isFirst) {
        this.isFirst = isFirst;
    }
    
    public Boolean getIsValid() {
        return isValid;
    }
    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
    
}
