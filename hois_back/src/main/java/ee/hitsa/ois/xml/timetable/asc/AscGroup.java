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

@XmlRootElement(name = "group")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AscGroup {

    private String id;
    private String name;
    private AscClass clazz;
    private List<String> students;
    private String entireClass;
    private String divisionTag;
    private String studentCount;

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

    @XmlAttribute(name = "classid")
    @XmlIDREF
    public AscClass getClazz() {
        return clazz;
    }

    public void setClazz(AscClass clazz) {
        this.clazz = clazz;
    }

    @XmlAttribute(name = "studentids")
    @XmlJavaTypeAdapter(value = CommaSeparatedListXmlAdapter.class)
    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    @XmlAttribute(name = "entireclass")
    public String getEntireClass() {
        return entireClass;
    }

    public void setEntireClass(String entireClass) {
        this.entireClass = entireClass;
    }

    @XmlAttribute(name = "divisiontag")
    public String getDivisionTag() {
        return divisionTag;
    }

    public void setDivisionTag(String divisionTag) {
        this.divisionTag = divisionTag;
    }

    @XmlAttribute(name = "studentcount")
    public String getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(String studentCount) {
        this.studentCount = studentCount;
    }
}
