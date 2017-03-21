package ee.hitsa.ois.web.dto.directive;

import java.time.LocalDate;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class DirectiveViewStudentDto {

    private Long student;
    private String idcode;
    private String firstname;
    private String lastname;
    private String fullname;
    private LocalDate startDate;
    private String reason;
    private String addInfo;
    private String fin;
    private String oldFinSpecific;
    private String finSpecific;
    private String oldStudyForm;
    private AutocompleteResult oldCurriculumVersion;
    private AutocompleteResult curriculumVersion;
    private String studentGroup;

    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getOldFinSpecific() {
        return oldFinSpecific;
    }

    public void setOldFinSpecific(String oldFinSpecific) {
        this.oldFinSpecific = oldFinSpecific;
    }

    public String getFinSpecific() {
        return finSpecific;
    }

    public void setFinSpecific(String finSpecific) {
        this.finSpecific = finSpecific;
    }

    public String getOldStudyForm() {
        return oldStudyForm;
    }

    public void setOldStudyForm(String oldStudyForm) {
        this.oldStudyForm = oldStudyForm;
    }

    public AutocompleteResult getOldCurriculumVersion() {
        return oldCurriculumVersion;
    }

    public void setOldCurriculumVersion(AutocompleteResult oldCurriculumVersion) {
        this.oldCurriculumVersion = oldCurriculumVersion;
    }

    public AutocompleteResult getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(AutocompleteResult curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public static DirectiveViewStudentDto of(DirectiveStudent directiveStudent) {
        DirectiveViewStudentDto dto = new DirectiveViewStudentDto();
        Student student = directiveStudent.getStudent();
        Person person;
        if(student != null) {
            dto.setStudent(student.getId());
            person = student.getPerson();
        } else {
            person = directiveStudent.getPerson();
        }
        if(person != null) {
            dto.setIdcode(person.getIdcode());
            dto.setFirstname(person.getFirstname());
            dto.setLastname(person.getLastname());
            dto.setFullname(person.getFullname());
        }
        if(student != null) {        
            Application application = directiveStudent.getApplication();
            DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directiveStudent.getDirective().getType()));
            switch(directiveType) {
            case KASKKIRI_EKSMAT:
                if(application != null) {
                    dto.setAddInfo(application.getAddInfo());
                }
                break;
            case KASKKIRI_OKAVA:
                dto.setOldStudyForm(EntityUtil.getNullableCode(student.getStudyForm()));
                dto.setOldCurriculumVersion(AutocompleteResult.of(student.getCurriculumVersion()));
                dto.setCurriculumVersion(AutocompleteResult.of(directiveStudent.getCurriculumVersion()));
                dto.setStudentGroup(student.getStudentGroup() != null ? student.getStudentGroup().getCode() : null);
                break;
            case KASKKIRI_FINM:
                dto.setOldFinSpecific(EntityUtil.getNullableCode(student.getFinSpecific()));
                break;
            case KASKKIRI_OVORM:
                dto.setOldStudyForm(EntityUtil.getNullableCode(student.getStudyForm()));
                dto.setStudentGroup(student.getStudentGroup() != null ? student.getStudentGroup().getCode() : null);
                break;
            case KASKKIRI_LOPET:
                dto.setOldCurriculumVersion(AutocompleteResult.of(student.getCurriculumVersion()));
                // TODO
                // dto.setIsCumLaude(isCumLaude);
                // dto.setCurriculumGrade(curriculumGrade);
                break;
            default:
                break;
            }
        }
        return EntityUtil.bindToDto(directiveStudent, dto);
    }
}    
