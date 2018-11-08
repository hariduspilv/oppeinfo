package ee.hitsa.ois.xml.exportTimetable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="class")
public class Class {
	@XmlAttribute(name="id")
	private String id;
	private String longname;
	private ClassTeacher class_teacher;
	
	public Class() {
		
	}
	
	public Class(String id, String longname, ClassTeacher classteacher) {
		this.id = id;
		this.setLongname(longname);
		this.setClass_teacher(classteacher);
	}
	
	public Class(String id, String longname) {
		this.id = id;
		this.setLongname(longname);
	}
	
	public String getId() {
		return this.id;
	}

	public ClassTeacher getClass_teacher() {
		return class_teacher;
	}

	public void setClass_teacher(ClassTeacher class_teacher) {
		this.class_teacher = class_teacher;
	}

	public String getLongname() {
		return longname;
	}

	public void setLongname(String longname) {
		this.longname = longname;
	}
}
