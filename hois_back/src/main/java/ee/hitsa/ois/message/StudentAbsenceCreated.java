package ee.hitsa.ois.message;

import ee.hitsa.ois.domain.student.StudentAbsence;

/**
 * Student absence created by representative/parent.
 * Automatic message is sent to school admins.
 */
public class StudentAbsenceCreated extends StudentMessage {

    public StudentAbsenceCreated() {
    }

    public StudentAbsenceCreated(StudentAbsence absence) {
        super(absence.getStudent());
    }
}
