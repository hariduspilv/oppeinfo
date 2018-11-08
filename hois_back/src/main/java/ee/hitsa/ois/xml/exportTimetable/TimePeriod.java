package ee.hitsa.ois.xml.exportTimetable;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "timePeriod")
public class TimePeriod {
	@XmlAttribute(name = "id")
    private String id;
	private Integer day;
	private Integer period;
	private String starttime;
	private String endtime;
	private List<Boolean> days;
	
	public TimePeriod() {
		
	}
	
	public TimePeriod(String id, List<Boolean> days, Integer period, String starttime, String endtime) {
		this.id = id;
		this.period = period;
		this.days = days;
		this.starttime = starttime;
		this.endtime = endtime;
	}
	
	public TimePeriod(String id, Integer day, Integer period, String starttime, String endtime) {
		this.id = id;
		this.day = day;
		this.period = period;
		this.starttime = starttime;
		this.endtime = endtime;
	}
	
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getId() {
		return this.id;
	}
	public List<Boolean> getDays() {
		return days;
	}

}
