package ee.hitsa.ois.xml.timetable.asc;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ee.hitsa.ois.xml.CommaSeparatedListXmlAdapter;

@XmlRootElement(name = "weeksdef")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AscWeeksDef {

    private String id;
    private String name;
    private String shortName;
    private List<String> weeks;

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

    @XmlAttribute(name = "weeks")
    @XmlJavaTypeAdapter(value = CommaSeparatedListXmlAdapter.class)
    public List<String> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<String> weeks) {
        this.weeks = weeks;
    }
}
