package ee.hitsa.ois.web.commandobject.document;

public class SupplementSearchForm {

    private Long directiveId;
    private String diplomaStatus;
    private Long studentId;
    private Long curriculumVersionId;
    
    public Long getDirectiveId() {
        return directiveId;
    }
    public void setDirectiveId(Long directiveId) {
        this.directiveId = directiveId;
    }
    
    public String getDiplomaStatus() {
        return diplomaStatus;
    }
    public void setDiplomaStatus(String diplomaStatus) {
        this.diplomaStatus = diplomaStatus;
    }
    
    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    
    public Long getCurriculumVersionId() {
        return curriculumVersionId;
    }
    public void setCurriculumVersionId(Long curriculumVersionId) {
        this.curriculumVersionId = curriculumVersionId;
    }
    
}
