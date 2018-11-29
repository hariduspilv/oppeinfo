package ee.hitsa.ois.web.dto.timetable;

import java.util.HashSet;
import java.util.Set;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class TimetableTimeOccupiedDto {

    private Boolean occupied;
    private Set<AutocompleteResult> teachers = new HashSet<>();
    private Set<AutocompleteResult> rooms = new HashSet<>();
    private Set<AutocompleteResult> studentGroups = new HashSet<>();
    
    public Boolean getOccupied() {
        return occupied;
    }
    
    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }
    
    public Set<AutocompleteResult> getTeachers() {
        return teachers;
    }
    
    public void setTeachers(Set<AutocompleteResult> teachers) {
        this.teachers = teachers;
    }
    
    public Set<AutocompleteResult> getRooms() {
        return rooms;
    }
    
    public void setRooms(Set<AutocompleteResult> rooms) {
        this.rooms = rooms;
    }

    public Set<AutocompleteResult> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(Set<AutocompleteResult> studentGroups) {
        this.studentGroups = studentGroups;
    }
    
}
