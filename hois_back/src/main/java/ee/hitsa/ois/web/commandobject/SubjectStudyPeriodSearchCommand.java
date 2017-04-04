package ee.hitsa.ois.web.commandobject;

import java.util.List;

public class SubjectStudyPeriodSearchCommand extends SearchCommand {
    
    private String subjectNameAndCode;
    private String teachersFullname;
    private List<Long> studyPeriods;

    public String getSubjectNameAndCode() {
        return subjectNameAndCode;
    }
    public void setSubjectNameAndCode(String subjectNameAndCode) {
        this.subjectNameAndCode = subjectNameAndCode;
    }
    public String getTeachersFullname() {
        return teachersFullname;
    }
    public void setTeachersFullname(String teachersFullname) {
        this.teachersFullname = teachersFullname;
    }
    public List<Long> getStudyPeriods() {
        return studyPeriods;
    }
    public void setStudyPeriods(List<Long> studyPeriods) {
        this.studyPeriods = studyPeriods;
    }
}
