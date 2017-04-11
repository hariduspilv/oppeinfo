package ee.hitsa.ois.web.commandobject;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class SubjectStudyPeriodForm  extends VersionedCommand {

    private List<SubjectStudyPeriodTeacherForm> teachers;
    @NotNull
    private Long studyPeriod;
    @NotNull
    private Long subject;

    public List<SubjectStudyPeriodTeacherForm> getTeachers() {
        return teachers != null ? teachers : (teachers = new ArrayList<>());
    }

    public void setTeachers(List<SubjectStudyPeriodTeacherForm> teachers) {
        this.teachers = teachers;
    }

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }
}
