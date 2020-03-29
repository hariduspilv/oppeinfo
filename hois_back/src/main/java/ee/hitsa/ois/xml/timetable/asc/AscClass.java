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

@XmlRootElement(name = "class")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AscClass {

    private String id;
    private String name;
    private String shortName;
    private AscTeacher teacher;
    private List<String> classrooms;
    private AscGrade grade;
    private String partnerId;

    @XmlAttribute(name = "id")
    @XmlID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name = "short")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @XmlAttribute(name = "teacherid")
    @XmlIDREF
    public AscTeacher getTeacher() {
        return teacher;
    }

    public void setTeacher(AscTeacher teacher) {
        this.teacher = teacher;
    }

    @XmlAttribute(name = "classroomids")
    @XmlJavaTypeAdapter(value = CommaSeparatedListXmlAdapter.class)
    public List<String> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<String> classrooms) {
        this.classrooms = classrooms;
    }

    /**
     * XXX: Is it really XmlIDREF to grade?
     * 
     * @return
     */
    @XmlAttribute(name = "grade")
    @XmlIDREF
    public AscGrade getGrade() {
        return grade;
    }

    public void setGrade(AscGrade grade) {
        this.grade = grade;
    }

    @XmlAttribute(name = "partner_id")
    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

}
