package ee.hitsa.ois.xml.timetable.asc;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "timetable")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlSeeAlso({ AscPeriod.class, AscDaysDef.class, AscWeeksDef.class, AscTermsDef.class, AscSubject.class,
        AscTeacher.class, AscClassroom.class, AscClass.class, AscGroup.class, AscStudent.class, AscStudentSubject.class,
        AscLesson.class, AscCard.class })
public class AscTimetable {

    private String ascTimetableVersion;
    private String importType;
    private String options;
    private String defaultExport;
    private String displayName;
    private String displayCountries;

    private List<AscPeriod> periods;
    private List<AscDaysDef> daysDefs;
    private List<AscWeeksDef> weeksDefs;
    private List<AscTermsDef> termsDefs;
    private List<AscSubject> subjects;
    private List<AscTeacher> teachers;
    private List<AscClassroom> classrooms;
    private List<AscGrade> grades;
    private List<AscClass> classes;
    private List<AscGroup> groups;
    private List<AscStudent> students;
    private List<AscStudentSubject> studentSubjects;
    private List<AscLesson> lessons;
    private List<AscCard> cards;

    @XmlAttribute(name = "ascttversion")
    public String getAscTimetableVersion() {
        return ascTimetableVersion;
    }

    public void setAscTimetableVersion(String ascTimetableVersion) {
        this.ascTimetableVersion = ascTimetableVersion;
    }

    @XmlAttribute(name = "importtype")
    public String getImportType() {
        return importType;
    }

    public void setImportType(String importType) {
        this.importType = importType;
    }

    @XmlAttribute(name = "options")
    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    @XmlAttribute(name = "defaultexport")
    public String getDefaultExport() {
        return defaultExport;
    }

    public void setDefaultExport(String defaultExport) {
        this.defaultExport = defaultExport;
    }

    @XmlAttribute(name = "displayname")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @XmlAttribute(name = "displaycountries")
    public String getDisplayCountries() {
        return displayCountries;
    }

    public void setDisplayCountries(String displayCountries) {
        this.displayCountries = displayCountries;
    }

    @XmlElementWrapper(name = "periods")
    @XmlElement(name = "period")
    public List<AscPeriod> getPeriods() {
        return periods;
    }

    public void setPeriods(List<AscPeriod> periods) {
        this.periods = periods;
    }

    @XmlElementWrapper(name = "daysdefs")
    @XmlElement(name = "daysdef")
    public List<AscDaysDef> getDaysDefs() {
        return daysDefs;
    }

    public void setDaysDefs(List<AscDaysDef> daysDefs) {
        this.daysDefs = daysDefs;
    }

    @XmlElementWrapper(name = "weeksdefs")
    @XmlElement(name = "weeksdef")
    public List<AscWeeksDef> getWeeksDefs() {
        return weeksDefs;
    }

    public void setWeeksDefs(List<AscWeeksDef> weeksDefs) {
        this.weeksDefs = weeksDefs;
    }

    @XmlElementWrapper(name = "termsdefs")
    @XmlElement(name = "termsdef")
    public List<AscTermsDef> getTermsDefs() {
        return termsDefs;
    }

    public void setTermsDefs(List<AscTermsDef> termsDefs) {
        this.termsDefs = termsDefs;
    }

    @XmlElementWrapper(name = "subjects")
    @XmlElement(name = "subject")
    public List<AscSubject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<AscSubject> subjects) {
        this.subjects = subjects;
    }

    @XmlElementWrapper(name = "teachers")
    @XmlElement(name = "teacher")
    public List<AscTeacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<AscTeacher> teachers) {
        this.teachers = teachers;
    }

    @XmlElementWrapper(name = "classrooms")
    @XmlElement(name = "classroom")
    public List<AscClassroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<AscClassroom> classrooms) {
        this.classrooms = classrooms;
    }

    @XmlElementWrapper(name = "grades")
    @XmlElement(name = "grade")
    public List<AscGrade> getGrades() {
        return grades;
    }

    public void setGrades(List<AscGrade> grades) {
        this.grades = grades;
    }

    @XmlElementWrapper(name = "classes")
    @XmlElement(name = "class")
    public List<AscClass> getClasses() {
        return classes;
    }

    public void setClasses(List<AscClass> classes) {
        this.classes = classes;
    }

    @XmlElementWrapper(name = "groups")
    @XmlElement(name = "group")
    public List<AscGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<AscGroup> groups) {
        this.groups = groups;
    }

    @XmlElementWrapper(name = "students")
    @XmlElement(name = "student")
    public List<AscStudent> getStudents() {
        return students;
    }

    public void setStudents(List<AscStudent> students) {
        this.students = students;
    }

    @XmlElementWrapper(name = "studentsubjects")
    @XmlElement(name = "studentsubject")
    public List<AscStudentSubject> getStudentSubjects() {
        return studentSubjects;
    }

    public void setStudentSubjects(List<AscStudentSubject> studentSubjects) {
        this.studentSubjects = studentSubjects;
    }

    @XmlElementWrapper(name = "lessons")
    @XmlElement(name = "lesson")
    public List<AscLesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<AscLesson> lessons) {
        this.lessons = lessons;
    }

    @XmlElementWrapper(name = "cards")
    @XmlElement(name = "card")
    public List<AscCard> getCards() {
        return cards;
    }

    public void setCards(List<AscCard> cards) {
        this.cards = cards;
    }

}
