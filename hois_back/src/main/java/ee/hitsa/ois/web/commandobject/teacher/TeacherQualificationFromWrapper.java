package ee.hitsa.ois.web.commandobject.teacher;

import java.util.Set;

public class TeacherQualificationFromWrapper {
    private Set<TeacherQualificationForm> qualifications;

    public Set<TeacherQualificationForm> getQualifications() {
        return qualifications;
    }

    public void setQualifications(Set<TeacherQualificationForm> qualifications) {
        this.qualifications = qualifications;
    }
}
