package ee.hitsa.ois.web.commandobject.directive;

import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.EstonianIdCode;
import ee.hitsa.ois.validation.Required;

public class DirectiveStudentSearchCommand {

    @Size(max = 255)
    private String name;
    @EstonianIdCode
    private String idcode;
    private Long studentGroup;
    private Boolean application;
    @Required
    private String type;
    private String scholarshipType;
    private Long directive;
    private Boolean isHigher;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcode() {
        return idcode;
    }

    public Long getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public Boolean getApplication() {
        return application;
    }

    public void setApplication(Boolean application) {
        this.application = application;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScholarshipType() {
        return scholarshipType;
    }

    public void setScholarshipType(String scholarshipType) {
        this.scholarshipType = scholarshipType;
    }

    public Long getDirective() {
        return directive;
    }

    public void setDirective(Long directive) {
        this.directive = directive;
    }

    public Boolean getIsHigher() {
        return isHigher;
    }

    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }
    
}
