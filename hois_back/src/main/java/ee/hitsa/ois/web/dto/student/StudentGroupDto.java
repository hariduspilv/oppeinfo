package ee.hitsa.ois.web.dto.student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.util.EntityUtil;
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
        List<Student> students = new ArrayList<>(studentGroup.getStudents());
        Comparator<Student> c = Comparator.comparing((i) -> i.getPerson().getLastname());
        students.sort(c.thenComparing(Comparator.comparing((i) -> i.getPerson().getFirstname())));
        dto.setMembers(StreamUtil.toMappedList(StudentGroupStudentDto::of, students));
        return dto;
    }
}
