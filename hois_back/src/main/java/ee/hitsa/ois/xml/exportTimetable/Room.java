package ee.hitsa.ois.xml.exportTimetable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "room")
public class Room {
	@XmlAttribute(name = "id")
    private String id;
	private String longname;
	
	public Room() {
		
	}
	
	public Room(String id, String longname) {
		this.id = id;
		this.longname = longname;
	}

	public String getLongname() {
		return longname;
	}

	public void setLongname(String longname) {
		this.longname = longname;
	}
}
