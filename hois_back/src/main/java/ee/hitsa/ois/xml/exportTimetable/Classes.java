package ee.hitsa.ois.xml.exportTimetable;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="classes")
public class Classes {
	
	private Set<Class> classes;
	
	public Classes() {
		
	}
	
	public Classes(Set<Class> classes) {
		this.classes = classes;
	}
	
	public Set<Class> getClasses() {
		return classes;
	}
	
	@XmlElement(name = "class")
    public void setClasses(Set<Class> classes) {
        this.classes = classes;
    }
	
}