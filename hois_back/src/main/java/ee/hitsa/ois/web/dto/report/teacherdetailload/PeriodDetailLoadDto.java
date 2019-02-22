package ee.hitsa.ois.web.dto.report.teacherdetailload;

import java.util.HashMap;
import java.util.Map;

public class PeriodDetailLoadDto {

    private Map<Long, Long> plannedHours = new HashMap<>();
    private Map<Long, Map<String, Long>> capacityPlannedHours = new HashMap<>();
    private Map<Long, Long> occurredLessons = new HashMap<>();
    private Map<Long, Map<String, Long>> capacityOccurredLessons = new HashMap<>();
    private Map<Long, Long> substitutedLessons = new HashMap<>();
    private Map<Long, Long> singleEvents = new HashMap<>();
    
    private Long totalPlannedHours;
    private Map<String, Long> totalCapacityPlannedHours = new HashMap<>();
    private Long totalOccurredLessons;
    private Map<String, Long> totalCapacityOccurredLessons = new HashMap<>();
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

    public Map<Long, Long> getOccurredLessons() {
        return occurredLessons;
    }

    public void setOccurredLessons(Map<Long, Long> occurredLessons) {
        this.occurredLessons = occurredLessons;
    }

    public Map<Long, Map<String, Long>> getCapacityOccurredLessons() {
        return capacityOccurredLessons;
    }

    public void setCapacityOccurredLessons(Map<Long, Map<String, Long>> capacityOccurredLessons) {
        this.capacityOccurredLessons = capacityOccurredLessons;
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

    public Long getTotalOccurredLessons() {
        return totalOccurredLessons;
    }

    public void setTotalOccurredLessons(Long totalOccurredLessons) {
        this.totalOccurredLessons = totalOccurredLessons;
    }

    public Map<String, Long> getTotalCapacityOccurredLessons() {
        return totalCapacityOccurredLessons;
    }

    public void setTotalCapacityOccurredLessons(Map<String, Long> totalCapacityOccurredLessons) {
        this.totalCapacityOccurredLessons = totalCapacityOccurredLessons;
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
