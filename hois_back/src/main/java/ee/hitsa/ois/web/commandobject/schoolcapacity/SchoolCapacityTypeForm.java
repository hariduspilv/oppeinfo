package ee.hitsa.ois.web.commandobject.schoolcapacity;

public class SchoolCapacityTypeForm {

    private String typeCode;
    private Boolean isUsable;
    private Boolean isTimetable;
    
    public String getTypeCode() {
        return typeCode;
    }
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    
    public Boolean getIsUsable() {
        return isUsable;
    }
    public void setIsUsable(Boolean isUsable) {
        this.isUsable = isUsable;
    }
    
    public Boolean getIsTimetable() {
        return isTimetable;
    }
    public void setIsTimetable(Boolean isTimetable) {
        this.isTimetable = isTimetable;
    }
    
}
