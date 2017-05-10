package ee.hitsa.ois.web.commandobject.teacher;

public class TeacherSearchCommand {

    private String name;
    private String idcode;
    private Boolean isHigher;

    private Long school;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public Long getSchool() {
        return school;
    }

    public void setSchool(Long school) {
        this.school = school;
    }

    public Boolean getIsHigher() {
        return isHigher;
    }

    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }
}
