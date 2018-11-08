package ee.hitsa.ois.xml.exportTimetable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="lesson_subject")
public class LessonSubject {
	@XmlAttribute(name="id")
	String id;
	
	public LessonSubject() {
		
	}
	
	public LessonSubject(String id) {
		this.id = id;
	}
}
