package ee.hitsa.ois.web.dto;

import java.util.List;

public class ContractForEkisDto {
    private Long id;
    private String studentGroup;
    private AutocompleteResult student;
    private AutocompleteResult teacher;
    private String enterpriseContactPersonName;
    private String enterpriseName;
    private List<AutocompleteResult> moduleSubjects;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public AutocompleteResult getStudent() {
        return student;
    }

    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }

    public AutocompleteResult getTeacher() {
        return teacher;
    }

    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }

    public String getEnterpriseContactPersonName() {
        return enterpriseContactPersonName;
    }

    public void setEnterpriseContactPersonName(String enterpriseContactPersonName) {
        this.enterpriseContactPersonName = enterpriseContactPersonName;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public List<AutocompleteResult> getModuleSubjects() {
        return moduleSubjects;
    }

    public void setModuleSubjects(List<AutocompleteResult> moduleSubjects) {
        this.moduleSubjects = moduleSubjects;
    }

}
