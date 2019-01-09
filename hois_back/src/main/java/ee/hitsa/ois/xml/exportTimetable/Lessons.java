package ee.hitsa.ois.xml.exportTimetable;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="lessons")
public class Lessons {
	
	private Set<Lesson> lessons;
	
	public Lessons() {
		
	}
	
	public Lessons(Set<Lesson> lessons) {
		this.lessons = lessons;
	}
	
	public Set<Lesson> getLessons() {
		return lessons;
	}
	
	@XmlElement(name = "lesson")
    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }
	
    public void add(Lesson lesson) {
        if (this.lessons == null) {
            this.lessons = new HashSet<>();
        }
        this.lessons.add(lesson);
    }
	
}