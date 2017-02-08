package ee.hitsa.ois.web.commandobject;

public class TeacherOccupationSearchCommand {

    private String occupationEt;
    private String occupationEn;
    private Boolean isValid;

    public String getOccupationEt() {
        return occupationEt;
    }

    public void setOccupationEt(String occupationEt) {
        this.occupationEt = occupationEt;
    }

    public String getOccupationEn() {
        return occupationEn;
    }

    public void setOccupationEn(String occupationEn) {
        this.occupationEn = occupationEn;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}
