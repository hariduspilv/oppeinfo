package ee.hitsa.ois.xml.timetable.asc;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ee.hitsa.ois.xml.CommaSeparatedListXmlAdapter;

@XmlRootElement(name = "card")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AscCard {

    private AscLesson lesson;
    private List<String> classrooms;
    private String period;
    private List<String> weeks;
    private List<String> terms;
    private List<Integer> days;

    @XmlAttribute(name = "lessonid")
    @XmlIDREF
    public AscLesson getLesson() {
        return lesson;
    }

    public void setLesson(AscLesson lesson) {
        this.lesson = lesson;
    }

    @XmlAttribute(name = "classroomids")
    @XmlJavaTypeAdapter(value = CommaSeparatedListXmlAdapter.class)
    public List<String> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<String> classrooms) {
        this.classrooms = classrooms;
    }

    @XmlAttribute(name = "period")
    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @XmlAttribute(name = "weeks")
    @XmlJavaTypeAdapter(value = CommaSeparatedListXmlAdapter.class)
    public List<String> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<String> weeks) {
        this.weeks = weeks;
    }

    @XmlAttribute(name = "terms")
    @XmlJavaTypeAdapter(value = CommaSeparatedListXmlAdapter.class)
    public List<String> getTerms() {
        return terms;
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
    }

    @XmlAttribute(name = "days")
    @XmlJavaTypeAdapter(value = AscDaysIntegerXmlAdapter.class)
    public List<Integer> getDays() {
        return days;
    }

    public void setDays(List<Integer> days) {
        this.days = days;
    }
}
