package ee.hitsa.ois.xml.exportTimetable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="lesson")
public class Lesson {
	@XmlAttribute(name = "id")
    public String id;
	private Integer periods;
	private LessonSubject lesson_subject;
	private LessonTeacher lesson_teacher;
	private LessonClasses lesson_classes;
	private LessonRoom lesson_room;
	private String effectivebegindate;
	private String effectiveenddate;
	private String occurence;
	
	public Lesson() {
		
	}
	
	public Lesson(String id, Integer periods, LessonSubject lessonSubject, String effectivebegindate, String effectiveenddate, String occurence) {
		this.id = id;
		this.periods = periods;
		this.lesson_subject = lessonSubject;
		this.effectivebegindate = effectivebegindate;
		this.effectiveenddate = effectiveenddate;
		this.occurence = occurence;
	}
	public Lesson(String id, Integer periods, LessonSubject lessonSubject, LessonRoom lessonRoom, LessonClasses lessonClasses, String effectiveBegindate, String effectiveenddate, String occurence) {
		this.id = id;
		this.periods = periods;
		this.lesson_classes = lessonClasses;
		this.lesson_room = lessonRoom;
		this.lesson_subject = lessonSubject;
		this.effectivebegindate = effectiveBegindate;
		this.effectiveenddate = effectiveenddate;
		this.occurence = occurence;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public Integer getPeriods() {
		return periods;
	}

	public void setPeriods(Integer periods) {
		this.periods = periods;
	}

	public LessonSubject getLesson_subject() {
		return lesson_subject;
	}

	public void setLesson_subject(LessonSubject lesson_subject) {
		this.lesson_subject = lesson_subject;
	}

	public LessonTeacher getLesson_teacher() {
		return lesson_teacher;
	}

	public void setLesson_teacher(LessonTeacher lesson_teacher) {
		this.lesson_teacher = lesson_teacher;
	}

	public LessonClasses getLesson_classes() {
		return lesson_classes;
	}

	public void setLesson_classes(LessonClasses lesson_classes) {
		this.lesson_classes = lesson_classes;
	}

	public String getEffectivebegindate() {
		return effectivebegindate;
	}

	public void setEffectivebegindate(String effectivebegindate) {
		this.effectivebegindate = effectivebegindate;
	}

	public String getEffectiveenddate() {
		return effectiveenddate;
	}

	public void setEffectiveenddate(String effectiveenddate) {
		this.effectiveenddate = effectiveenddate;
	}

	public String getOccurence() {
		return occurence;
	}

	public void setOccurence(String occurence) {
		this.occurence = occurence;
	}

	public LessonRoom getLesson_room() {
		return lesson_room;
	}

	public void setLesson_room(LessonRoom lesson_room) {
		this.lesson_room = lesson_room;
	}
}
