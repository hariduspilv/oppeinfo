package ee.hitsa.ois.web.dto.student;

import java.time.LocalDate;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.Student;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.OisFileCommand;
import ee.hitsa.ois.web.commandobject.StudentForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentViewDto extends StudentForm {

    private Long id;

    // header
    private OisFileCommand photo;

    // study
    private AutocompleteResult<Long> school;
    private String status;
    private AutocompleteResult<Long> curriculumVersion;
    private String studyLanguage;
    private AutocompleteResult<Long> curriculumSpeciality;
    private AutocompleteResult<Long> studentGroup;
    private Integer course;
    private LocalDate studyStart;
    private LocalDate nominalStudyEnd;
    private String studyForm;
    private String studyLoad;
    private String fin;
    private String finSpecific;
    private String studyCompany;
    private String boardingSchool;
    private Boolean isVocational;
    private Boolean userCanEditStudent;
    private Boolean userCanAddRepresentative;
    private Boolean userIsSchoolAdmin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OisFileCommand getPhoto() {
        return photo;
    }

    public void setPhoto(OisFileCommand photo) {
        this.photo = photo;
    }

    public AutocompleteResult<Long> getSchool() {
        return school;
    }

    public void setSchool(AutocompleteResult<Long> school) {
        this.school = school;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AutocompleteResult<Long> getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(AutocompleteResult<Long> curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public String getStudyLanguage() {
        return studyLanguage;
    }

    public void setStudyLanguage(String studyLanguage) {
        this.studyLanguage = studyLanguage;
    }

    public AutocompleteResult<Long> getCurriculumSpeciality() {
        return curriculumSpeciality;
    }

    public void setCurriculumSpeciality(AutocompleteResult<Long> curriculumSpeciality) {
        this.curriculumSpeciality = curriculumSpeciality;
    }

    public AutocompleteResult<Long> getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(AutocompleteResult<Long> studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public LocalDate getStudyStart() {
        return studyStart;
    }

    public void setStudyStart(LocalDate studyStart) {
        this.studyStart = studyStart;
    }

    public LocalDate getNominalStudyEnd() {
        return nominalStudyEnd;
    }

    public void setNominalStudyEnd(LocalDate nominalStudyEnd) {
        this.nominalStudyEnd = nominalStudyEnd;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }

    public String getStudyLoad() {
        return studyLoad;
    }

    public void setStudyLoad(String studyLoad) {
        this.studyLoad = studyLoad;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getFinSpecific() {
        return finSpecific;
    }

    public void setFinSpecific(String finSpecific) {
        this.finSpecific = finSpecific;
    }

    public String getStudyCompany() {
        return studyCompany;
    }

    public void setStudyCompany(String studyCompany) {
        this.studyCompany = studyCompany;
    }

    public String getBoardingSchool() {
        return boardingSchool;
    }

    public void setBoardingSchool(String boardingSchool) {
        this.boardingSchool = boardingSchool;
    }

    public Boolean getIsVocational() {
        return isVocational;
    }

    public void setIsVocational(Boolean isVocational) {
        this.isVocational = isVocational;
    }

    public Boolean getUserCanEditStudent() {
        return userCanEditStudent;
    }

    public void setUserCanEditStudent(Boolean userCanEditStudent) {
        this.userCanEditStudent = userCanEditStudent;
    }

    public Boolean getUserCanAddRepresentative() {
        return userCanAddRepresentative;
    }

    public void setUserCanAddRepresentative(Boolean userCanAddRepresentative) {
        this.userCanAddRepresentative = userCanAddRepresentative;
    }

    public Boolean getUserIsSchoolAdmin() {
        return userIsSchoolAdmin;
    }

    public void setUserIsSchoolAdmin(Boolean userIsSchoolAdmin) {
        this.userIsSchoolAdmin = userIsSchoolAdmin;
    }

    public static StudentViewDto of(Student student) {
        StudentViewDto dto = EntityUtil.bindToDto(student, new StudentViewDto());

        // header
        OisFile photo = student.getPhoto();
        if(photo != null) {
            dto.setPhoto(EntityUtil.bindToDto(photo, new OisFileCommand()));
        }
        dto.setPerson(EntityUtil.bindToDto(student.getPerson(), new StudentViewPersonDto()));

        // study
        dto.setSchool(AutocompleteResult.of(student.getSchool()));
        dto.setCurriculumVersion(AutocompleteResult.of(student.getCurriculumVersion()));
        dto.setStudyLanguage(EntityUtil.getNullableCode(student.getLanguage()));
        dto.setCurriculumSpeciality(student.getCurriculumSpeciality() != null ? AutocompleteResult.of(student.getCurriculumSpeciality()) : null);
        dto.setStudentGroup(student.getStudentGroup() != null ? AutocompleteResult.of(student.getStudentGroup()) : null);
        dto.setCourse(student.getStudentGroup() != null ? student.getStudentGroup().getCourse() : null);
        dto.setIsVocational(Boolean.valueOf(CurriculumUtil.isVocational(student.getCurriculumVersion().getCurriculum().getOrigStudyLevel())));
        if(!Boolean.TRUE.equals(dto.getIsVocational())) {
            dto.setStudyCompany(null);
            dto.setBoardingSchool(null);
        }

        dto.setSchoolEmail(student.getEmail());
        return dto;
    }

    public static class StudentViewPersonDto extends StudentPersonForm {
        private String firstname;
        private String lastname;
        private String idcode;
        private String sex;
        private String citizenship;
        private String language;
        private String bankaccount;

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

        public String getIdcode() {
            return idcode;
        }

        public void setIdcode(String idcode) {
            this.idcode = idcode;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCitizenship() {
            return citizenship;
        }

        public void setCitizenship(String citizenship) {
            this.citizenship = citizenship;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getBankaccount() {
            return bankaccount;
        }

        public void setBankaccount(String bankaccount) {
            this.bankaccount = bankaccount;
        }
    }
}
