package ee.hitsa.ois.xml.exportTimetable;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="timeperiods")
public class TimePeriods {
	
	private Set<TimePeriod> timeperiods;
	
	public TimePeriods() {
		
	}
	
	public TimePeriods(Set<TimePeriod> timeperiods) {
		this.timeperiods = timeperiods;
	}
	
	public Set<TimePeriod> getTimeperiods() {
		return timeperiods;
	}
	
	@XmlElement(name = "timeperiod")
    public void setTimeperiods(Set<TimePeriod> timeperiods) {
        this.timeperiods = timeperiods;
    }
	
    public void add(TimePeriod timeperiod) {
        if (this.timeperiods == null) {
            this.timeperiods = new HashSet<>();
        }
        this.timeperiods.add(timeperiod);
    }
	
}
