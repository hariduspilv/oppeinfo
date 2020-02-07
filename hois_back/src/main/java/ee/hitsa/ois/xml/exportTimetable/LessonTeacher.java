package ee.hitsa.ois.xml.exportTimetable;

import java.util.List;
import java.util.stream.Collectors;

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
	
	public LessonTeacher(List<String> ids) {
	    this.id = ids.stream().map(p -> "TR_" + p).collect(Collectors.joining(" "));
	}
	
	public String getId() {
		return this.id;
	}

	public void addId(@SuppressWarnings("hiding") String id) {
		if (StringUtils.isEmpty(id)) {
			this.id = id;
		} else {
			this.id += " " + id;
		}
	}
}
