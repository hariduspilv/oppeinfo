package ee.hitsa.ois.web.dto.student;

import java.util.List;
import java.util.stream.Stream;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.student.StudentGroupForm;

public class StudentGroupDto extends StudentGroupForm {

    private Long id;
    private List<StudentGroupStudentDto> members;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<StudentGroupStudentDto> getMembers() {
        return members;
    }

    public void setMembers(List<StudentGroupStudentDto> members) {
        this.members = members;
    }

    public static StudentGroupDto of(StudentGroup studentGroup) {
        StudentGroupDto dto = EntityUtil.bindToDto(studentGroup, new StudentGroupDto(), "students");
        // sort students in name order
        Stream<Student> students = studentGroup.getStudents().stream().sorted((o1, o2) -> PersonUtil.SORT.compare(o1.getPerson(), o2.getPerson()));
        dto.setMembers(StreamUtil.toMappedList(StudentGroupStudentDto::of, students));
        return dto;
    }
}
