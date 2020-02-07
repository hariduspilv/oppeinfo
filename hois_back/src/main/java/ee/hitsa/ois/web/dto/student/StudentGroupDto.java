package ee.hitsa.ois.web.dto.student;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.student.StudentGroupForm;

public class StudentGroupDto extends StudentGroupForm {

    private Long id;
    private Short curriculumVersionAdmissinYear;
    private List<StudentGroupStudentDto> members;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getCurriculumVersionAdmissinYear() {
        return curriculumVersionAdmissinYear;
    }

    public void setCurriculumVersionAdmissinYear(Short curriculumVersionAdmissinYear) {
        this.curriculumVersionAdmissinYear = curriculumVersionAdmissinYear;
    }

    public List<StudentGroupStudentDto> getMembers() {
        return members;
    }

    public void setMembers(List<StudentGroupStudentDto> members) {
        this.members = members;
    }

    public static StudentGroupDto of(HoisUserDetails user, StudentGroup studentGroup) {
        StudentGroupDto dto = EntityUtil.bindToDto(studentGroup, new StudentGroupDto(), "students");
        Stream<Student> students = studentGroup.getStudents().stream();
        if (!(user.isSchoolAdmin() || user.isLeadingTeacher() || user.isTeacher())) {
            // only show active members
            Set<String> active = new HashSet<>(StudentStatus.STUDENT_STATUS_ACTIVE);
            students = students.filter(s -> active.contains(EntityUtil.getCode(s.getStatus())));
        }
        // sort students in name order
        students = students.sorted((o1, o2) -> PersonUtil.SORT.compare(o1.getPerson(), o2.getPerson()));
        dto.setMembers(StreamUtil.toMappedList(s -> StudentGroupStudentDto.of(user, s), students));
        if (studentGroup.getCurriculumVersion() != null) {
            dto.setCurriculumVersionAdmissinYear(studentGroup.getCurriculumVersion().getAdmissionYear());
        }
        return dto;
    }
}
