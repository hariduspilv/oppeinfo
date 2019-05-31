package ee.hitsa.ois.web.dto.report.teacherdetailload;

import java.util.HashMap;
import java.util.Map;

public class PeriodDetailLoadDto {

    private Map<Long, Long> plannedHours = new HashMap<>();
    private Map<Long, Map<String, Long>> capacityPlannedHours = new HashMap<>();
    private Map<Long, Long> journalOccurredLessons = new HashMap<>();
    private Map<Long, Map<String, Long>> capacityJournalOccurredLessons = new HashMap<>();
    private Map<Long, Long> timetableOccurredLessons = new HashMap<>();
    private Map<Long, Map<String, Long>> capacityTimetableOccurredLessons = new HashMap<>();
    private Map<Long, Long> substitutedLessons = new HashMap<>();
    private Map<Long, Long> singleEvents = new HashMap<>();

    private Long totalPlannedHours;
    private Map<String, Long> totalCapacityPlannedHours = new HashMap<>();
    private Long journalTotalOccurredLessons;
    private Map<String, Long> journalTotalCapacityOccurredLessons = new HashMap<>();
    private Long timetableTotalOccurredLessons;
    private Map<String, Long> timetableTotalCapacityOccurredLessons = new HashMap<>();
    private Long totalSubstitutedLessons;
    private Long totalSingleEvents;

    public Map<Long, Long> getPlannedHours() {
        return plannedHours;
    }

    public void setPlannedHours(Map<Long, Long> plannedHours) {
        this.plannedHours = plannedHours;
    }

    public Map<Long, Map<String, Long>> getCapacityPlannedHours() {
        return capacityPlannedHours;
    }

    public void setCapacityPlannedHours(Map<Long, Map<String, Long>> capacityPlannedHours) {
        this.capacityPlannedHours = capacityPlannedHours;
    }

    public Map<Long, Long> getJournalOccurredLessons() {
        return journalOccurredLessons;
    }

    public void setJournalOccurredLessons(Map<Long, Long> journalOccurredLessons) {
        this.journalOccurredLessons = journalOccurredLessons;
    }

    public Map<Long, Map<String, Long>> getCapacityJournalOccurredLessons() {
        return capacityJournalOccurredLessons;
    }

    public void setCapacityJournalOccurredLessons(Map<Long, Map<String, Long>> capacityJournalOccurredLessons) {
        this.capacityJournalOccurredLessons = capacityJournalOccurredLessons;
    }

    public Map<Long, Long> getTimetableOccurredLessons() {
        return timetableOccurredLessons;
    }

    public void setTimetableOccurredLessons(Map<Long, Long> timetableOccurredLessons) {
        this.timetableOccurredLessons = timetableOccurredLessons;
    }

    public Map<Long, Map<String, Long>> getCapacityTimetableOccurredLessons() {
        return capacityTimetableOccurredLessons;
    }

    public void setCapacityTimetableOccurredLessons(Map<Long, Map<String, Long>> capacityTimetableOccurredLessons) {
        this.capacityTimetableOccurredLessons = capacityTimetableOccurredLessons;
    }

    public Map<Long, Long> getSubstitutedLessons() {
        return substitutedLessons;
    }

    public void setSubstitutedLessons(Map<Long, Long> substitutedLessons) {
        this.substitutedLessons = substitutedLessons;
    }

    public Map<Long, Long> getSingleEvents() {
        return singleEvents;
    }

    public void setSingleEvents(Map<Long, Long> singleEvents) {
        this.singleEvents = singleEvents;
    }

    public Long getTotalPlannedHours() {
        return totalPlannedHours;
    }

    public void setTotalPlannedHours(Long totalPlannedHours) {
        this.totalPlannedHours = totalPlannedHours;
    }

    public Map<String, Long> getTotalCapacityPlannedHours() {
        return totalCapacityPlannedHours;
    }

    public void setTotalCapacityPlannedHours(Map<String, Long> totalCapacityPlannedHours) {
        this.totalCapacityPlannedHours = totalCapacityPlannedHours;
    }

    public Long getJournalTotalOccurredLessons() {
        return journalTotalOccurredLessons;
    }

    public void setJournalTotalOccurredLessons(Long journalTotalOccurredLessons) {
        this.journalTotalOccurredLessons = journalTotalOccurredLessons;
    }

    public Map<String, Long> getJournalTotalCapacityOccurredLessons() {
        return journalTotalCapacityOccurredLessons;
    }

    public void setJournalTotalCapacityOccurredLessons(Map<String, Long> journalTotalCapacityOccurredLessons) {
        this.journalTotalCapacityOccurredLessons = journalTotalCapacityOccurredLessons;
    }

    public Long getTimetableTotalOccurredLessons() {
        return timetableTotalOccurredLessons;
    }

    public void setTimetableTotalOccurredLessons(Long timetableTotalOccurredLessons) {
        this.timetableTotalOccurredLessons = timetableTotalOccurredLessons;
    }

    public Map<String, Long> getTimetableTotalCapacityOccurredLessons() {
        return timetableTotalCapacityOccurredLessons;
    }

    public void setTimetableTotalCapacityOccurredLessons(Map<String, Long> timetableTotalCapacityOccurredLessons) {
        this.timetableTotalCapacityOccurredLessons = timetableTotalCapacityOccurredLessons;
    }

    public Long getTotalSubstitutedLessons() {
        return totalSubstitutedLessons;
    }

    public void setTotalSubstitutedLessons(Long totalSubstitutedLessons) {
        this.totalSubstitutedLessons = totalSubstitutedLessons;
    }

    public Long getTotalSingleEvents() {
        return totalSingleEvents;
    }

    public void setTotalSingleEvents(Long totalSingleEvents) {
        this.totalSingleEvents = totalSingleEvents;
    }

}
