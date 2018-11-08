package ee.hitsa.ois.web.commandobject;

import java.util.Set;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.dto.SubjectProgramStudyContentDto;

public class SubjectProgramForm extends VersionedCommand {

    private Long subjectId;
    private Long subjectStudyPeriodId;
    
    private String independentStudy;
    private String assessmentDescription;
    private String studyLiterature;
    private Boolean publicAll = Boolean.FALSE;
    private Boolean publicHois = Boolean.FALSE;
    private Boolean publicStudent = Boolean.FALSE;
    
    private String passDescription;
    private String npassDescription;
    private String grade0Description;
    private String grade1Description;
    private String grade2Description;
    private String grade3Description;
    private String grade4Description;
    private String grade5Description;

    @NotNull
    @ClassifierRestriction(MainClassCode.OPPETOOSISU)
    private String studyContentType;
    private String studyDescription; // if StudyContentType.OPPETOOSISU_T
    private Set<SubjectProgramStudyContentDto> studyContents;
    
    /**
     * @return the independentStudy
     */
    public String getIndependentStudy() {
        return independentStudy;
    }
    /**
     * @param independentStudy the independentStudy to set
     */
    public void setIndependentStudy(String independentStudy) {
        this.independentStudy = independentStudy;
    }
    /**
     * @return the assessmentDescription
     */
    public String getAssessmentDescription() {
        return assessmentDescription;
    }
    /**
     * @param assessmentDescription the assessmentDescription to set
     */
    public void setAssessmentDescription(String assessmentDescription) {
        this.assessmentDescription = assessmentDescription;
    }
    /**
     * @return the studyLiterature
     */
    public String getStudyLiterature() {
        return studyLiterature;
    }
    /**
     * @param studyLiterature the studyLiterature to set
     */
    public void setStudyLiterature(String studyLiterature) {
        this.studyLiterature = studyLiterature;
    }
    /**
     * @return the studyDescription
     */
    public String getStudyDescription() {
        return studyDescription;
    }
    /**
     * @param studyDescription the studyDescription to set
     */
    public void setStudyDescription(String studyDescription) {
        this.studyDescription = studyDescription;
    }
    /**
     * @return the studyContentType
     */
    public String getStudyContentType() {
        return studyContentType;
    }
    /**
     * @param studyContentType the studyContentType to set
     */
    public void setStudyContentType(String studyContentType) {
        this.studyContentType = studyContentType;
    }
    /**
     * @return the publicAll
     */
    public Boolean getPublicAll() {
        return publicAll;
    }
    /**
     * @param publicAll the publicAll to set
     */
    public void setPublicAll(Boolean publicAll) {
        this.publicAll = publicAll;
    }
    /**
     * @return the publicHois
     */
    public Boolean getPublicHois() {
        return publicHois;
    }
    /**
     * @param publicHois the publicHois to set
     */
    public void setPublicHois(Boolean publicHois) {
        this.publicHois = publicHois;
    }
    /**
     * @return the publicStudent
     */
    public Boolean getPublicStudent() {
        return publicStudent;
    }
    /**
     * @param publicStudent the publicStudent to set
     */
    public void setPublicStudent(Boolean publicStudent) {
        this.publicStudent = publicStudent;
    }
    /**
     * @return the studyContents
     */
    public Set<SubjectProgramStudyContentDto> getStudyContents() {
        return studyContents;
    }
    public String getPassDescription() {
        return passDescription;
    }
    public void setPassDescription(String passDescription) {
        this.passDescription = passDescription;
    }
    public String getNpassDescription() {
        return npassDescription;
    }
    public void setNpassDescription(String npassDescription) {
        this.npassDescription = npassDescription;
    }
    public String getGrade0Description() {
        return grade0Description;
    }
    public void setGrade0Description(String grade0Description) {
        this.grade0Description = grade0Description;
    }
    public String getGrade1Description() {
        return grade1Description;
    }
    public void setGrade1Description(String grade1Description) {
        this.grade1Description = grade1Description;
    }
    public String getGrade2Description() {
        return grade2Description;
    }
    public void setGrade2Description(String grade2Description) {
        this.grade2Description = grade2Description;
    }
    public String getGrade3Description() {
        return grade3Description;
    }
    public void setGrade3Description(String grade3Description) {
        this.grade3Description = grade3Description;
    }
    public String getGrade4Description() {
        return grade4Description;
    }
    public void setGrade4Description(String grade4Description) {
        this.grade4Description = grade4Description;
    }
    public String getGrade5Description() {
        return grade5Description;
    }
    public void setGrade5Description(String grade5Description) {
        this.grade5Description = grade5Description;
    }
    /**
     * @param studyContents the studyContents to set
     */
    public void setStudyContents(Set<SubjectProgramStudyContentDto> studyContents) {
        this.studyContents = studyContents;
    }
    public Long getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
    public Long getSubjectStudyPeriodId() {
        return subjectStudyPeriodId;
    }
    public void setSubjectStudyPeriodId(Long subjectStudyPeriodId) {
        this.subjectStudyPeriodId = subjectStudyPeriodId;
    }
}
