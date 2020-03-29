package ee.hitsa.ois.xml.timetable.asc;

import java.time.LocalTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ee.hitsa.ois.xml.LocalTimeXmlAdapter;

@XmlRootElement(name = "period")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AscPeriod {

    private String name;
    private String shortName;
    private String period;
    private LocalTime startTime;
    private LocalTime endTime;

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

    @XmlAttribute(name = "period")
    @XmlID
    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @XmlAttribute(name = "starttime")
    @XmlJavaTypeAdapter(value = LocalTimeXmlAdapter.class)
    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    @XmlAttribute(name = "endtime")
    @XmlJavaTypeAdapter(value = LocalTimeXmlAdapter.class)
    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

}
