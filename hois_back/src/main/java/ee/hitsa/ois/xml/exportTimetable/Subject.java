package ee.hitsa.ois.xml.exportTimetable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="subject")
public class Subject {
	@XmlAttribute(name = "id")
    private String id;
	private String longname;
	
	public Subject() {
		
	}
	
	public Subject(String id, String longname) {
		this.id = id;
		this.longname = longname;
	}
	
	public String getId() {
		return id;
	}

	public String getLongname() {
		return longname;
	}

	public void setLongname(String longname) {
		this.longname = longname;
	}
}
