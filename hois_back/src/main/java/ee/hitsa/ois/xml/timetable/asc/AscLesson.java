package ee.hitsa.ois.xml.timetable.asc;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ee.hitsa.ois.xml.CommaSeparatedListXmlAdapter;

@XmlRootElement(name = "lesson")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AscLesson {

    private String id;
    private List<String> classes;
    private AscSubject subject;
    private String periodsPerCard; // 2
    private String periodsPerWeek; // 2.0
    private List<String> teachers;
    private List<String> classrooms;
    private List<String> groups;
    private String capacity; // "*"
    private String seminargroup;
    private AscTermsDef termsDef;
    private AscWeeksDef weeksDef;
    private AscDaysDef daysDef;
    private String partnerId;

    @XmlAttribute(name = "id")
    @XmlID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute(name = "classids")
    @XmlJavaTypeAdapter(value = CommaSeparatedListXmlAdapter.class)
    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    @XmlAttribute(name = "subjectid")
    @XmlIDREF
    public AscSubject getSubject() {
        return subject;
    }

    public void setSubject(AscSubject subject) {
        this.subject = subject;
    }

    @XmlAttribute(name = "periodspercard")
    public String getPeriodsPerCard() {
        return periodsPerCard;
    }

    public void setPeriodsPerCard(String periodsPerCard) {
        this.periodsPerCard = periodsPerCard;
    }

    @XmlAttribute(name = "periodsperweek")
    public String getPeriodsPerWeek() {
        return periodsPerWeek;
    }

    public void setPeriodsPerWeek(String periodsPerWeek) {
        this.periodsPerWeek = periodsPerWeek;
    }

    @XmlAttribute(name = "teacherids")
    @XmlJavaTypeAdapter(value = CommaSeparatedListXmlAdapter.class)
    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }

    @XmlAttribute(name = "classroomids")
    @XmlJavaTypeAdapter(value = CommaSeparatedListXmlAdapter.class)
    public List<String> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<String> classrooms) {
        this.classrooms = classrooms;
    }

    @XmlAttribute(name = "groupids")
    @XmlJavaTypeAdapter(value = CommaSeparatedListXmlAdapter.class)
    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    @XmlAttribute(name = "capacity")
    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    @XmlAttribute(name = "seminargroup")
    public String getSeminargroup() {
        return seminargroup;
    }

    public void setSeminargroup(String seminargroup) {
        this.seminargroup = seminargroup;
    }

    @XmlAttribute(name = "termsdefid")
    @XmlIDREF
    public AscTermsDef getTermsDef() {
        return termsDef;
    }

    public void setTermsDef(AscTermsDef termsDef) {
        this.termsDef = termsDef;
    }

    @XmlAttribute(name = "weeksdefid")
    @XmlIDREF
    public AscWeeksDef getWeeksDef() {
        return weeksDef;
    }

    public void setWeeksDef(AscWeeksDef weeksDef) {
        this.weeksDef = weeksDef;
    }

    @XmlAttribute(name = "daysdefid")
    @XmlIDREF
    public AscDaysDef getDaysDef() {
        return daysDef;
    }

    public void setDaysDef(AscDaysDef daysDef) {
        this.daysDef = daysDef;
    }

    @XmlAttribute(name = "partner_id")
    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

}
