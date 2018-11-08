package ee.hitsa.ois.xml.exportTimetable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="class_teacher")
public class ClassTeacher {
	@XmlAttribute(name="id")
	private String id;
	
	public ClassTeacher(String id) {
		this.id = id;
	}
	
	public ClassTeacher() {
		
	}
	
	public String getId() {
		return id;
	}
}
