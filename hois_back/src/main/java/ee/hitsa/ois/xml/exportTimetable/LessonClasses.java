package ee.hitsa.ois.xml.exportTimetable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

@XmlRootElement(name="lesson_classes")
public class LessonClasses {
	@XmlAttribute(name="id")
	public String id;
	
	public LessonClasses() {
		
	}
	
	public LessonClasses(String id) {
		this.id = id;
	}
	
	public void addId(@SuppressWarnings("hiding") String id) {
		if (StringUtils.isEmpty(id)) {
			this.id = id;
		} else {
			this.id += " " + id;
		}
	}
}
