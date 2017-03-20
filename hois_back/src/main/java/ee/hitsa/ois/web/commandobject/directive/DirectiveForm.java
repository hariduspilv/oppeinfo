package ee.hitsa.ois.web.commandobject.directive;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.EstonianIdCode;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class DirectiveForm extends VersionedCommand {

    @NotEmpty
    @ClassifierRestriction(MainClassCode.KASKKIRI)
    private String type;
    @NotEmpty
    @Size(max = 500)
    private String headline;
    @Size(max = 4000)
    private String addInfo;
    private Long directiveCoordinator;
    @Valid
    private List<? extends DirectiveFormStudent> students;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public Long getDirectiveCoordinator() {
        return directiveCoordinator;
    }

    public void setDirectiveCoordinator(Long directiveCoordinator) {
        this.directiveCoordinator = directiveCoordinator;
    }

    public List<? extends DirectiveFormStudent> getStudents() {
        return students;
    }

    public void setStudents(List<? extends DirectiveFormStudent> students) {
        this.students = students;
    }

    public static class DirectiveFormStudent {
        private Long id;
        @EstonianIdCode
        private String idcode;
        @Size(max = 255)
        private String firstname;
        @Size(max = 255)
        private String lastname;
        private LocalDate startDate;
        private LocalDate endDate;
        @ClassifierRestriction({MainClassCode.AKADPUHKUS_POHJUS, MainClassCode.EKSMAT_POHJUS})
        private String reason;
        @ClassifierRestriction(MainClassCode.OPPEVORM)
        private String studyForm;
        private Long studentGroup;
        private Long curriculumVersion;
        @ClassifierRestriction(MainClassCode.OPPEKOORMUS)
        private String studyLoad;
        @ClassifierRestriction(MainClassCode.FINALLIKAS)
        private String fin;
        @ClassifierRestriction(MainClassCode.FINTAPSUSTUS)
        private String finSpecific;
        @ClassifierRestriction(MainClassCode.OPPEKEEL)
        private String language;
        @ClassifierRestriction(MainClassCode.OPPEASTE)
        private String previousStudyLevel;
        private String email;
        private Long application;
        private Long student;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getStudyForm() {
            return studyForm;
        }

        public void setStudyForm(String studyForm) {
            this.studyForm = studyForm;
        }

        public Long getStudentGroup() {
            return studentGroup;
        }

        public void setStudentGroup(Long studentGroup) {
            this.studentGroup = studentGroup;
        }

        public Long getCurriculumVersion() {
            return curriculumVersion;
        }

        public void setCurriculumVersion(Long curriculumVersion) {
            this.curriculumVersion = curriculumVersion;
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

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getPreviousStudyLevel() {
            return previousStudyLevel;
        }

        public void setPreviousStudyLevel(String previousStudyLevel) {
            this.previousStudyLevel = previousStudyLevel;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Long getApplication() {
            return application;
        }

        public void setApplication(Long application) {
            this.application = application;
        }

        public Long getStudent() {
            return student;
        }

        public void setStudent(Long student) {
            this.student = student;
        }
    }
}
