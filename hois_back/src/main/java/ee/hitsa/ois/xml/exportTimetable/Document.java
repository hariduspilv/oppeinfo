package ee.hitsa.ois.xml.exportTimetable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="document")
public class Document {
	
	@XmlAttribute(name="date")
	private String generationDate;
	
	@XmlAttribute(name="time")
	private String generationTime;
	
	private General general;
	
	private TimePeriods timeperiods;
	
	private Rooms rooms;
	
	private Subjects subjects;
	
	private Teachers teachers;
	
	private Classes classes;
	
	private Lessons lessons;

	public void setGenerationDate(String generationDate) {
		this.generationDate = generationDate;
	}

	public void setGenerationTime(String generationTime) {
		this.generationTime = generationTime;
	}

	public General getGeneral() {
		return general;
	}

	public void setGeneral(General general) {
		this.general = general;
	}

	public Rooms getRooms() {
		return rooms;
	}

	public void setRooms(Rooms rooms) {
		this.rooms = rooms;
	}

	public Subjects getSubjects() {
		return subjects;
	}

	public void setSubjects(Subjects subjects) {
		this.subjects = subjects;
	}

	public Teachers getTeachers() {
		return teachers;
	}

	public void setTeachers(Teachers teachers) {
		this.teachers = teachers;
	}

	public Classes getClasses() {
		return classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	public Lessons getLessons() {
		return lessons;
	}

	public void setLessons(Lessons lessons) {
		this.lessons = lessons;
	}

	public TimePeriods getTimeperiods() {
		return timeperiods;
	}

	public void setTimeperiods(TimePeriods timeperiods) {
		this.timeperiods = timeperiods;
	}
	
	
}
