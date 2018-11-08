package ee.hitsa.ois.xml.exportTimetable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

@XmlRootElement(name="lesson_teacher")
public class LessonTeacher {
	@XmlAttribute(name="id")
	String id;
	
	public LessonTeacher() {
		
	}
	
	public LessonTeacher(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}

	public void addId(String id) {
		if (StringUtils.isEmpty(id)) {
			this.id = id;
		} else {
			this.id += " " + id;
		}
	}
}
