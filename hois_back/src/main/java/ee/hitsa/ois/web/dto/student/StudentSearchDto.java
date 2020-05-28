package ee.hitsa.ois.web.dto.student;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentSearchDto {

    private Long id;
    private String fullname;
    private String idcode;
    private AutocompleteResult curriculumVersion;
    private AutocompleteResult curriculum;
    private Boolean higher;
    private AutocompleteResult studentGroup;
    private AutocompleteResult journal;
    private AutocompleteResult subject;
    private String studyForm;
    private String status;
    private String type;
    private Long personId;
    private Boolean canSelect = Boolean.TRUE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AutocompleteResult getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(AutocompleteResult curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }
    
    public AutocompleteResult getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(AutocompleteResult curriculum) {
        this.curriculum = curriculum;
    }
    
    public Boolean getHigher() {
        return higher;
    }
    
    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

    public AutocompleteResult getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(AutocompleteResult studentGroup) {
        this.studentGroup = studentGroup;
    }
    
    public AutocompleteResult getJournal() {
        return journal;
    }

    public void setJournal(AutocompleteResult journal) {
        this.journal = journal;
    }

    public AutocompleteResult getSubject() {
        return subject;
    }

    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getCanSelect() {
        return canSelect;
    }

    public void setCanSelect(Boolean canSelect) {
        this.canSelect = canSelect;
    }
}
