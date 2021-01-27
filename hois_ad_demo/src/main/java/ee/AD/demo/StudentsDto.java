package ee.AD.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentsDto {

	private List<StudentDto> students = new ArrayList<>();

	public List<StudentDto> getStudents() {
		return students;
	}

	public void setStudents(List<StudentDto> students) {
		this.students = students;
	}
	
	@Override
    public String toString() {
        return students.stream().map(p -> "\n" + (students.indexOf(p) + 1) + ": " + p.toString()).collect(Collectors.joining(";"));
    }
}
