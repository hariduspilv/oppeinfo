package ee.hitsa.ois.web.dto.student;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.StudentType;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.web.commandobject.OisFileCommand;
import ee.hitsa.ois.web.commandobject.student.StudentForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.StudentOccupationCertificateDto;

public class StudentViewDto extends StudentForm {

    private Long id;
    
    // study
    private AutocompleteResult school;
    private String status;
    private Long curriculum;
    private AutocompleteResult curriculumVersion;
    private AutocompleteResult studentGroup;
    private String speciality;
    private Short course;
    private String studentCard;
    private String studentCardStatus;
    private LocalDate studentCardValidThru;
    private LocalDate studentCardGivenDt;
    private LocalDate studentCardReturnedDt;
    private Boolean isStudentCardRepetitive;
    private LocalDate studyStart;
    private LocalDate studyEnd;
    private String studyForm;
    private String studyLoad;
    private String fin;
    private Boolean isVocational;
    private Boolean userCanEditStudent;
    private Boolean userCanAddRepresentative;
    private Boolean userIsSchoolAdmin;
    private Boolean userIsLeadingTeacher;
    private Boolean userIsStudentGroupTeacher;
    private Boolean userCanViewStudentSpecificData;
    private Boolean userCanUpdateRR;
    private Boolean userCanViewStudentSupportServices;
    private Boolean userCanViewPrivateStudentSupportServices;
    private Boolean studentCanAddPhoto;
    private BigDecimal curriculumCredits;
    private BigDecimal credits;
    private BigDecimal kkh;
    private Boolean isCurriculumFulfilled;
    private Boolean hasRemarksPastSevenDays;
    private Boolean individualCurriculum = Boolean.FALSE;
    private LocalDate individualCurriculumStart;
    private LocalDate individualCurriculumEnd;
    private AutocompleteResult boardingSchool;
    private LocalDate boardingSchoolValidFrom;
    private LocalDate boardingSchoolValidThru;
    private String boardingSchoolAddInfo;
    private String type;
    private List<StudentOccupationCertificateDto> occupationCertificates;
    private List<StudentDormitoryHistoryDto> dormitoryHistory;

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

    public AutocompleteResult getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(AutocompleteResult studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Short getCourse() {
        return course;
    }

    public void setCourse(Short course) {
        this.course = course;
    }

    public String getStudentCard() {
        return studentCard;
    }

    public void setStudentCard(String studentCard) {
        this.studentCard = studentCard;
    }

    public String getStudentCardStatus() {
        return studentCardStatus;
    }

    public void setStudentCardStatus(String studentCardStatus) {
        this.studentCardStatus = studentCardStatus;
    }

    public LocalDate getStudentCardValidThru() {
        return studentCardValidThru;
    }

    public void setStudentCardValidThru(LocalDate studentCardValidThru) {
        this.studentCardValidThru = studentCardValidThru;
    }

    public LocalDate getStudentCardGivenDt() {
        return studentCardGivenDt;
    }

    public void setStudentCardGivenDt(LocalDate studentCardGivenDt) {
        this.studentCardGivenDt = studentCardGivenDt;
    }

    public LocalDate getStudentCardReturnedDt() {
        return studentCardReturnedDt;
    }

    public void setStudentCardReturnedDt(LocalDate studentCardReturnedDt) {
        this.studentCardReturnedDt = studentCardReturnedDt;
    }

    public Boolean getIsStudentCardRepetitive() {
        return isStudentCardRepetitive;
    }

    public void setIsStudentCardRepetitive(Boolean isStudentCardRepetitive) {
        this.isStudentCardRepetitive = isStudentCardRepetitive;
    }

    public LocalDate getStudyStart() {
        return studyStart;
    }

    public void setStudyStart(LocalDate studyStart) {
        this.studyStart = studyStart;
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

    public Boolean getUserIsLeadingTeacher() {
        return userIsLeadingTeacher;
    }

    public void setUserIsLeadingTeacher(Boolean userIsLeadingTeacher) {
        this.userIsLeadingTeacher = userIsLeadingTeacher;
    }

    public Boolean getUserIsStudentGroupTeacher() {
        return userIsStudentGroupTeacher;
    }

    public void setUserIsStudentGroupTeacher(Boolean userIsStudentGroupTeacher) {
        this.userIsStudentGroupTeacher = userIsStudentGroupTeacher;
    }

    public Boolean getUserCanViewStudentSpecificData() {
        return userCanViewStudentSpecificData;
    }

    public void setUserCanViewStudentSpecificData(Boolean userCanViewStudentSpecificData) {
        this.userCanViewStudentSpecificData = userCanViewStudentSpecificData;
    }
    
    public Boolean getUserCanUpdateRR() {
        return userCanUpdateRR;
    }

    public void setUserCanUpdateRR(Boolean userCanUpdateRR) {
        this.userCanUpdateRR = userCanUpdateRR;
    }

    public Boolean getUserCanViewStudentSupportServices() {
        return userCanViewStudentSupportServices;
    }

    public void setUserCanViewStudentSupportServices(Boolean userCanViewStudentSupportServices) {
        this.userCanViewStudentSupportServices = userCanViewStudentSupportServices;
    }

    public Boolean getUserCanViewPrivateStudentSupportServices() {
        return userCanViewPrivateStudentSupportServices;
    }

    public void setUserCanViewPrivateStudentSupportServices(Boolean userCanViewPrivateStudentSupportServices) {
        this.userCanViewPrivateStudentSupportServices = userCanViewPrivateStudentSupportServices;
    }

    public Boolean getStudentCanAddPhoto() {
        return studentCanAddPhoto;
    }

    public void setStudentCanAddPhoto(Boolean studentCanAddPhoto) {
        this.studentCanAddPhoto = studentCanAddPhoto;
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

    public Boolean getIsCurriculumFulfilled() {
        return isCurriculumFulfilled;
    }

    public void setIsCurriculumFulfilled(Boolean isCurriculumFulfilled) {
        this.isCurriculumFulfilled = isCurriculumFulfilled;
    }

    public Boolean getHasRemarksPastSevenDays() {
        return hasRemarksPastSevenDays;
    }

    public void setHasRemarksPastSevenDays(Boolean hasRemarksPastSevenDays) {
        this.hasRemarksPastSevenDays = hasRemarksPastSevenDays;
    }

    public Boolean getIndividualCurriculum() {
        return individualCurriculum;
    }

    public void setIndividualCurriculum(Boolean individualCurriculum) {
        this.individualCurriculum = individualCurriculum;
    }

    public LocalDate getIndividualCurriculumStart() {
        return individualCurriculumStart;
    }

    public void setIndividualCurriculumStart(LocalDate individualCurriculumStart) {
        this.individualCurriculumStart = individualCurriculumStart;
    }

    public LocalDate getIndividualCurriculumEnd() {
        return individualCurriculumEnd;
    }

    public void setIndividualCurriculumEnd(LocalDate individualCurriculumEnd) {
        this.individualCurriculumEnd = individualCurriculumEnd;
    }

    public AutocompleteResult getBoardingSchool() {
        return boardingSchool;
    }

    public void setBoardingSchool(AutocompleteResult boardingSchool) {
        this.boardingSchool = boardingSchool;
    }

    public LocalDate getBoardingSchoolValidFrom() {
        return boardingSchoolValidFrom;
    }

    public void setBoardingSchoolValidFrom(LocalDate boardingSchoolValidFrom) {
        this.boardingSchoolValidFrom = boardingSchoolValidFrom;
    }

    public LocalDate getBoardingSchoolValidThru() {
        return boardingSchoolValidThru;
    }

    public void setBoardingSchoolValidThru(LocalDate boardingSchoolValidThru) {
        this.boardingSchoolValidThru = boardingSchoolValidThru;
    }

    public String getBoardingSchoolAddInfo() {
        return boardingSchoolAddInfo;
    }

    public void setBoardingSchoolAddInfo(String boardingSchoolAddInfo) {
        this.boardingSchoolAddInfo = boardingSchoolAddInfo;
    }

    public List<StudentOccupationCertificateDto> getOccupationCertificates() {
        return occupationCertificates;
    }

    public void setOccupationCertificates(List<StudentOccupationCertificateDto> occupationCertificates) {
        this.occupationCertificates = occupationCertificates;
    }

    public List<StudentDormitoryHistoryDto> getDormitoryHistory() {
        return dormitoryHistory;
    }

    public void setDormitoryHistory(List<StudentDormitoryHistoryDto> dormitoryHistory) {
        this.dormitoryHistory = dormitoryHistory;
    }

    public static StudentViewDto of(Student student) {
        StudentViewDto dto = EntityUtil.bindToDto(student, new StudentViewDto(), "specialNeeds");

        // header
        OisFile photo = student.getPhoto();
        if (photo != null) {
            dto.setPhoto(EntityUtil.bindToDto(photo, new OisFileCommand()));
        }
        dto.setPerson(EntityUtil.bindToDto(student.getPerson(), new StudentViewPersonDto()));

        // study
        dto.setStudyLanguage(EntityUtil.getNullableCode(student.getLanguage()));
        dto.setCourse(student.getStudentGroup() != null ? student.getStudentGroup().getCourse() : null);
        CurriculumVersion curriculumVersion = student.getCurriculumVersion();
        if (curriculumVersion != null) {
            Curriculum curriculum = student.getCurriculumVersion().getCurriculum();
            if (curriculum != null) {
                dto.setCurriculum(EntityUtil.getId(curriculum));
                dto.setCurriculumCredits(curriculum.getCredits());
                dto.setIsVocational(Boolean.valueOf(StudentUtil.isVocational(student)));
            }
        }
        dto.setType(EntityUtil.getNullableCode(student.getType()));
        dto.setIsGuestStudent(Boolean.valueOf(StudentType.OPPUR_K.name().equals(dto.getType())));
        dto.setSchoolEmail(student.getEmail());
        dto.setSpecialNeeds(student.getSpecialNeeds().stream().map(n -> EntityUtil.getCode(n.getSpecialNeed())).collect(Collectors.toList()));
        return dto;
    }

    public static class StudentViewPersonDto extends StudentPersonForm {
        private String firstname;
        private String lastname;
        private String fullname;
        private String idcode;
        private String sex;
        private LocalDate birthdate;

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

        public LocalDate getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(LocalDate birthdate) {
            this.birthdate = birthdate;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
