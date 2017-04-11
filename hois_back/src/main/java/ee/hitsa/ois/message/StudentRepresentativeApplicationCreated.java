package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.student.StudentRepresentativeApplication;

public class StudentRepresentativeApplicationCreated extends StudentMessage {

    public StudentRepresentativeApplicationCreated() {
    }

    public StudentRepresentativeApplicationCreated(StudentRepresentativeApplication application) {
        super(application.getStudent());
    }
}
