package ee.hitsa.ois.web.dto.directive;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.FinSource;
import ee.hitsa.ois.enums.StudyLoad;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class DirectiveStudentDto extends DirectiveForm.DirectiveFormStudent {

    private String fullname;
    private String oldStudyForm;
    private String oldStudyLoad;
    private AutocompleteResult oldCurriculumVersion;
    private String oldFin;
    private String oldFinSpecific;
    private String oldLanguage;
    private String curriculumGrade;
    private Boolean isCumLaude;
    private String addInfo;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getOldStudyForm() {
        return oldStudyForm;
    }

    public void setOldStudyForm(String oldStudyForm) {
        this.oldStudyForm = oldStudyForm;
    }

    public String getOldStudyLoad() {
        return oldStudyLoad;
    }

    public void setOldStudyLoad(String oldStudyLoad) {
        this.oldStudyLoad = oldStudyLoad;
    }

    public AutocompleteResult getOldCurriculumVersion() {
        return oldCurriculumVersion;
    }

    public void setOldCurriculumVersion(AutocompleteResult oldCurriculumVersion) {
        this.oldCurriculumVersion = oldCurriculumVersion;
    }

    public String getOldFin() {
        return oldFin;
    }

    public void setOldFin(String oldFin) {
        this.oldFin = oldFin;
    }

    public String getOldFinSpecific() {
        return oldFinSpecific;
    }

    public void setOldFinSpecific(String oldFinSpecific) {
        this.oldFinSpecific = oldFinSpecific;
    }

    public String getOldLanguage() {
        return oldLanguage;
    }

    public void setOldLanguage(String oldLanguage) {
        this.oldLanguage = oldLanguage;
    }

    public String getCurriculumGrade() {
        return curriculumGrade;
    }

    public void setCurriculumGrade(String curriculumGrade) {
        this.curriculumGrade = curriculumGrade;
    }

    public Boolean getIsCumLaude() {
        return isCumLaude;
    }

    public void setIsCumLaude(Boolean isCumLaude) {
        this.isCumLaude = isCumLaude;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public static DirectiveStudentDto of(Application application, DirectiveType directiveType) {
        DirectiveStudentDto dto = of(application.getStudent(), directiveType);
        dto.setApplication(application.getId());
        // FIXME should copy old* values from application?
        switch(directiveType) {
        case KASKKIRI_AKAD:
            // TODO
            dto.setStartDate(application.getStartDate());
            dto.setEndDate(application.getEndDate());
            break;
        case KASKKIRI_AKADK:
            dto.setStartDate(application.getStartDate());
            break;
        case KASKKIRI_EKSMAT:
            dto.setReason(EntityUtil.getNullableCode(application.getReason()));
            dto.setAddInfo(application.getAddInfo());
            break;
        case KASKKIRI_FINM:
            dto.setFinSpecific(EntityUtil.getNullableCode(application.getNewFinSpecific()));
            break;
        case KASKKIRI_OKAVA:
            dto.setCurriculumVersion(EntityUtil.getNullableId(application.getNewCurriculumVersion()));
            dto.setStudyForm(EntityUtil.getNullableCode(application.getNewStudyForm()));
            break;
        case KASKKIRI_OVORM:
            dto.setStudyForm(EntityUtil.getNullableCode(application.getNewStudyForm()));
            break;
        default:
            break;
        }
        return dto;
    }

    public static DirectiveStudentDto of(DirectiveStudent directiveStudent) {
        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directiveStudent.getDirective().getType()));
        Application app = directiveStudent.getApplication();
        Student student = directiveStudent.getStudent();
        DirectiveStudentDto dto;
        if(app != null) {
            dto = of(app, directiveType);
        } else if(student != null) {
            dto = of(student, directiveType);
        } else {
            // without student: KASKKIRI_IMMAT, KASKKIRI_IMMATV
            dto = new DirectiveStudentDto();
            Person person = directiveStudent.getPerson();
            if(person != null) {
                setPersonData(person, dto);
            }
        }
        return EntityUtil.bindToDto(student, dto);
    }

    public static DirectiveStudentDto of(Student student, DirectiveType directiveType) {
        DirectiveStudentDto dto = new DirectiveStudentDto();
        dto.setStudent(student.getId());
        setPersonData(student.getPerson(), dto);

        switch(directiveType) {
        case KASKKIRI_ENNIST:
            dto.setOldCurriculumVersion(AutocompleteResult.of(student.getCurriculumVersion()));
            dto.setOldStudyForm(EntityUtil.getNullableCode(student.getStudyForm()));
            dto.setOldStudyLoad(EntityUtil.getNullableCode(student.getStudyLoad()));
            dto.setOldFin(EntityUtil.getNullableCode(student.getFin()));
            dto.setOldFinSpecific(EntityUtil.getNullableCode(student.getFinSpecific()));
            dto.setOldLanguage(EntityUtil.getNullableCode(student.getLanguage()));
            break;
        case KASKKIRI_FINM:
            dto.setOldFinSpecific(EntityUtil.getNullableCode(student.getFinSpecific()));
            boolean free = FinSource.FINALLIKAS_RE.name().equals(EntityUtil.getNullableCode(student.getFin()));
            dto.setFin(free ? FinSource.FINALLIKAS_REV.name() : FinSource.FINALLIKAS_RE.name());
            break;
        case KASKKIRI_LOPET:
            dto.setOldCurriculumVersion(AutocompleteResult.of(student.getCurriculumVersion()));
            // TODO
            // dto.setIsCumLaude(isCumLaude);
            // dto.setCurriculumGrade(curriculumGrade);
            break;
        case KASKKIRI_OKAVA:
            dto.setOldStudyForm(EntityUtil.getNullableCode(student.getStudyForm()));
            dto.setOldCurriculumVersion(AutocompleteResult.of(student.getCurriculumVersion()));
            break;
        case KASKKIRI_OKOORM:
            // we are adding new student to the directive, calculate new study load and fin from existing values
            boolean partial = StudyLoad.OPPEKOORMUS_OSA.name().equals(EntityUtil.getNullableCode(student.getStudyLoad()));
            dto.setStudyLoad(partial ? StudyLoad.OPPEKOORMUS_TAIS.name() : StudyLoad.OPPEKOORMUS_OSA.name());
            dto.setFin(partial ? FinSource.FINALLIKAS_RE.name() : FinSource.FINALLIKAS_REV.name());
            break;
        case KASKKIRI_OVORM:
            dto.setOldStudyForm(EntityUtil.getNullableCode(student.getStudyForm()));
            break;
        default:
            break;
        }
        return dto;
    }

    private static void setPersonData(Person person, DirectiveStudentDto dto) {
        dto.setIdcode(person.getIdcode());
        dto.setFirstname(person.getFirstname());
        dto.setLastname(person.getLastname());
        dto.setFullname(person.getFullname());        
    }
}
