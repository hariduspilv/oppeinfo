package ee.hitsa.ois.xml.exportTimetable;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="subjects")
public class Subjects {
	
	private Set<Subject> subjects;
	
	public Subjects() {
		
	}
	
	public Subjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}
	
	public Set<Subject> getSubjects() {
		return subjects;
	}
	@XmlElement(name = "subject")
    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
    public void add(Subject subject) {
        if (this.subjects == null) {
            this.subjects = new HashSet<Subject>();
        }
        this.subjects.add(subject);
    }
	
}
