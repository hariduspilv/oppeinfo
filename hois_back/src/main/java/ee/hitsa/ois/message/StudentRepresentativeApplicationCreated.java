package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.student.Student;

public class StudentRepresentativeApplicationCreated extends StudentMessage {

    public StudentRepresentativeApplicationCreated() {
    }

    public StudentRepresentativeApplicationCreated(Student student) {
        super(student);
    }
}
