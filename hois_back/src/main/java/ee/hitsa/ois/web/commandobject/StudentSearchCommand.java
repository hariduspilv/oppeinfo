package ee.hitsa.ois.web.commandobject;


public class StudentSearchCommand {

    private String name;
    private String idcode;
    private Long curriculumVersionId;
    private String studentGroupCode;
    private String studyFormCode;
    private String statusCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public Long getCurriculumVersionId() {
        return curriculumVersionId;
    }

    public void setCurriculumVersionId(Long curriculumVersionId) {
        this.curriculumVersionId = curriculumVersionId;
    }

    public String getStudentGroupCode() {
        return studentGroupCode;
    }

    public void setStudentGroupCode(String studentGroupCode) {
        this.studentGroupCode = studentGroupCode;
    }

    public String getStudyFormCode() {
        return studyFormCode;
    }

    public void setStudyFormCode(String studyFormCode) {
        this.studyFormCode = studyFormCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
