package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.student.StudentRepresentative;

public class StudentRepresentativeApplicationAccepted extends StudentMessage {

    public StudentRepresentativeApplicationAccepted() {
    }

    public StudentRepresentativeApplicationAccepted(StudentRepresentative representative) {
        super(representative.getStudent());
    }
}
