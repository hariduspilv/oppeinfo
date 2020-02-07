package ee.hitsa.ois.web.dto.timetable;

public class NameAndCode {
    
    private String name;
    private String code;
    private String bracketsInfo;
    
    public NameAndCode(String name, String code) {
        this.name = name;
        this.code = code;
    }
    
    public NameAndCode(String name, String code, String addInfo) {
        this(name, code);
        this.bracketsInfo = addInfo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getBracketsInfo() {
        return bracketsInfo;
    }

    public void setBracketsInfo(String bracketsInfo) {
        this.bracketsInfo = bracketsInfo;
    }

    @Override
    public String toString() {
      return "NameAndCode [name=" + name + ", code=" + code + "]";
    }
}
