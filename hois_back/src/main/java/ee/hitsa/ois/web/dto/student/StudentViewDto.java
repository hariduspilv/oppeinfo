package ee.hitsa.ois.web.dto.student;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.web.commandobject.OisFileCommand;
import ee.hitsa.ois.web.commandobject.student.StudentForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.StudentOccupationCertificateDto;

public class StudentViewDto extends StudentForm {

    private Long id;

    // header
    private OisFileCommand photo;

    // study
    private AutocompleteResult school;
    private String status;
    private Long curriculum;
    private AutocompleteResult curriculumVersion;
    private String studyLanguage;
    private AutocompleteResult curriculumSpeciality;
    private AutocompleteResult studentGroup;
    private Short course;
    private LocalDate studyStart;
    private LocalDate nominalStudyEnd;
    private LocalDate studyEnd;
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
    private BigDecimal curriculumCredits;
    private BigDecimal credits;
    private BigDecimal kkh;
    private List<StudentOccupationCertificateDto> occupationCertificates;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCurriculum() {
        return curriculum;
    }

    public LocalDate getStudyEnd() {
        return studyEnd;
    }

    public void setStudyEnd(LocalDate studyEnd) {
        this.studyEnd = studyEnd;
    }

    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
    }

    public OisFileCommand getPhoto() {
        return photo;
    }

    public void setPhoto(OisFileCommand photo) {
        this.photo = photo;
    }

    public AutocompleteResult getSchool() {
        return school;
    }

    public void setSchool(AutocompleteResult school) {
        this.school = school;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AutocompleteResult getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(AutocompleteResult curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public String getStudyLanguage() {
        return studyLanguage;
    }

    public void setStudyLanguage(String studyLanguage) {
        this.studyLanguage = studyLanguage;
    }

    public AutocompleteResult getCurriculumSpeciality() {
        return curriculumSpeciality;
    }

    public void setCurriculumSpeciality(AutocompleteResult curriculumSpeciality) {
        this.curriculumSpeciality = curriculumSpeciality;
    }

    public AutocompleteResult getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(AutocompleteResult studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Short getCourse() {
        return course;
    }

    public void setCourse(Short course) {
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

    public BigDecimal getCurriculumCredits() {
        return curriculumCredits;
    }

    public void setCurriculumCredits(BigDecimal curriculumCredits) {
        this.curriculumCredits = curriculumCredits;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public BigDecimal getKkh() {
        return kkh;
    }

    public void setKkh(BigDecimal kkh) {
        this.kkh = kkh;
    }

    public List<StudentOccupationCertificateDto> getOccupationCertificates() {
        return occupationCertificates;
    }

    public void setOccupationCertificates(List<StudentOccupationCertificateDto> occupationCertificates) {
        this.occupationCertificates = occupationCertificates;
    }

    public static StudentViewDto of(Student student) {
        StudentViewDto dto = EntityUtil.bindToDto(student, new StudentViewDto());

        // header
        OisFile photo = student.getPhoto();
        if (photo != null) {
            dto.setPhoto(EntityUtil.bindToDto(photo, new OisFileCommand()));
        }
        dto.setPerson(EntityUtil.bindToDto(student.getPerson(), new StudentViewPersonDto()));

        // study
        dto.setStudyLanguage(EntityUtil.getNullableCode(student.getLanguage()));
        dto.setCourse(student.getStudentGroup() != null ? student.getStudentGroup().getCourse() : null);
        Curriculum curriculum = student.getCurriculumVersion().getCurriculum();
        dto.setIsVocational(Boolean.valueOf(StudentUtil.isVocational(student)));
        if (!Boolean.TRUE.equals(dto.getIsVocational())) {
            dto.setStudyCompany(null);
            dto.setBoardingSchool(null);
        }
        dto.setCurriculum(EntityUtil.getId(curriculum));
        dto.setCurriculumCredits(curriculum.getCredits());
        dto.setSchoolEmail(student.getEmail());
        return dto;
    }

    public static class StudentViewPersonDto extends StudentPersonForm {
        private String firstname;
        private String lastname;
        private String fullname;
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

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
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
