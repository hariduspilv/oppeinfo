package ee.hitsa.ois.xml.exportTimetable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "teacher")
public class Teacher {
	@XmlAttribute(name = "id")
    private String id;
	private String forename;
	private String surname;
	private String gender;
	
	public Teacher() {
		
	}
	
	public Teacher(String id, String forename, String surname, String gender) {
		this.id = id;
		this.forename = forename;
		this.surname = surname;
		this.gender = gender;
	}
	
	public String getId() {
		return id;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
