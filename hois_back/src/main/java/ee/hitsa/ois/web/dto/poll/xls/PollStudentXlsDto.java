package ee.hitsa.ois.web.dto.poll.xls;

public class PollStudentXlsDto {
    
    private String studentCode;
    private String studyForm;
    private String curriculumCode;
    
    public String getStudentCode() {
        return studentCode;
    }
    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }
    public String getCurriculumCode() {
        return curriculumCode;
    }
    public void setCurriculumCode(String curriculumCode) {
        this.curriculumCode = curriculumCode;
    }
    public String getStudyForm() {
        return studyForm;
    }
    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }
}
