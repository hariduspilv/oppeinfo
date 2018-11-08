package ee.hitsa.ois.xml.exportTimetable;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="teachers")
public class Teachers {
	
	private Set<Teacher> teachers;
	
	public Teachers() {
		
	}
	
	public Teachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}
	
	public Set<Teacher> getTeachers() {
		return teachers;
	}
	@XmlElement(name = "teacher")
    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }
    public void add(Teacher teacher) {
        if (this.teachers == null) {
            this.teachers = new HashSet<Teacher>();
        }
        this.teachers.add(teacher);
    }
	
}