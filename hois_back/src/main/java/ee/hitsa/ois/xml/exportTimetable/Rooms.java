package ee.hitsa.ois.xml.exportTimetable;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="rooms")
public class Rooms {
	
	private Set<Room> rooms;
	
	public Rooms() {
		
	}
	
	public Rooms(Set<Room> rooms) {
		this.rooms = rooms;
	}
	
	public Set<Room> getRooms() {
		return rooms;
	}
	@XmlElement(name = "room")
    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }
    public void add(Room room) {
        if (this.rooms == null) {
            this.rooms = new HashSet<>();
        }
        this.rooms.add(room);
    }
	
}
