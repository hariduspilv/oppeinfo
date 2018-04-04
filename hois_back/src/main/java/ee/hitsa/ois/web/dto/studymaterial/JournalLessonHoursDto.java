package ee.hitsa.ois.web.dto.studymaterial;

import java.util.List;
import java.util.Set;

import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalCapacity;
import ee.hitsa.ois.domain.timetable.JournalEntry;
import ee.hitsa.ois.enums.CapacityType;
import ee.hitsa.ois.util.ClassifierUtil;

public class JournalLessonHoursDto {
    
    private Integer totalPlannedHours;
    private Integer totalUsedHours;
    private Integer plannedHoursCapacityA;
    private Integer usedHoursCapacityA;
    private Integer plannedHoursCapacityI;
    private Integer usedHoursCapacityI;
    private Integer plannedHoursCapacityP;
    private Integer usedHoursCapacityP;
    private Integer plannedHoursCapacityS;
    private Integer usedHoursCapacityS;
    
    public static JournalLessonHoursDto of(Journal journal) {
        JournalLessonHoursDto dto = new JournalLessonHoursDto(); 
        List<JournalCapacity> capacities = journal.getJournalCapacities();
        Set<JournalEntry> entries = journal.getJournalEntries();
        
        dto.setTotalPlannedHours(calculatePlannedHours(capacities, null));
        dto.setTotalUsedHours(calculateUsedHours(entries, null));
        
        dto.setPlannedHoursCapacityA(calculatePlannedHours(capacities, CapacityType.MAHT_a));
        dto.setUsedHoursCapacityA(calculateUsedHours(entries, CapacityType.MAHT_a));
        dto.setPlannedHoursCapacityI(calculatePlannedHours(capacities, CapacityType.MAHT_i));
        dto.setUsedHoursCapacityI(calculateUsedHours(entries, CapacityType.MAHT_i));
        dto.setPlannedHoursCapacityP(calculatePlannedHours(capacities, CapacityType.MAHT_p));
        dto.setUsedHoursCapacityP(calculateUsedHours(entries, CapacityType.MAHT_p));
        dto.setPlannedHoursCapacityS(calculatePlannedHours(capacities, CapacityType.MAHT_S));
        dto.setUsedHoursCapacityS(calculateUsedHours(entries, CapacityType.MAHT_S));

        return dto;
    }
    
    private static Integer calculatePlannedHours(List<JournalCapacity> capacities, CapacityType type) {
        return Integer.valueOf(capacities.stream()
                .filter(it -> type == null || ClassifierUtil.equals(type, it.getJournalCapacityType().getCapacityType()))
                .mapToInt(it -> it.getHours() == null ? 0 : it.getHours().intValue())
                .sum());
    }
    
    private static Integer calculateUsedHours(Set<JournalEntry> entries, CapacityType type) {
        return Integer.valueOf(entries.stream()
                .filter(it -> type == null || it.getJournalEntryCapacityTypes().stream().anyMatch(ct -> ClassifierUtil.equals(type, ct.getCapacityType())))
                .mapToInt(it -> it.getLessons() == null ? 0 : it.getLessons().intValue()).sum());
    }
    
    public Integer getTotalPlannedHours() {
        return totalPlannedHours;
    }
    
    public void setTotalPlannedHours(Integer totalPlannedHours) {
        this.totalPlannedHours = totalPlannedHours;
    }
    
    public Integer getTotalUsedHours() {
        return totalUsedHours;
    }
    
    public void setTotalUsedHours(Integer totalUsedHours) {
        this.totalUsedHours = totalUsedHours;
    }
    
    public Integer getPlannedHoursCapacityA() {
        return plannedHoursCapacityA;
    }
    
    public void setPlannedHoursCapacityA(Integer plannedHoursCapacityA) {
        this.plannedHoursCapacityA = plannedHoursCapacityA;
    }
    
    public Integer getUsedHoursCapacityA() {
        return usedHoursCapacityA;
    }
    
    public void setUsedHoursCapacityA(Integer usedHoursCapacityA) {
        this.usedHoursCapacityA = usedHoursCapacityA;
    }
    
    public Integer getPlannedHoursCapacityI() {
        return plannedHoursCapacityI;
    }
    
    public void setPlannedHoursCapacityI(Integer plannedHoursCapacityI) {
        this.plannedHoursCapacityI = plannedHoursCapacityI;
    }
    
    public Integer getUsedHoursCapacityI() {
        return usedHoursCapacityI;
    }
    
    public void setUsedHoursCapacityI(Integer usedHoursCapacityI) {
        this.usedHoursCapacityI = usedHoursCapacityI;
    }
    
    public Integer getPlannedHoursCapacityP() {
        return plannedHoursCapacityP;
    }
    
    public void setPlannedHoursCapacityP(Integer plannedHoursCapacityP) {
        this.plannedHoursCapacityP = plannedHoursCapacityP;
    }
    
    public Integer getUsedHoursCapacityP() {
        return usedHoursCapacityP;
    }
    
    public void setUsedHoursCapacityP(Integer usedHoursCapacityP) {
        this.usedHoursCapacityP = usedHoursCapacityP;
    }
    
    public Integer getPlannedHoursCapacityS() {
        return plannedHoursCapacityS;
    }
    
    public void setPlannedHoursCapacityS(Integer plannedHoursCapacityS) {
        this.plannedHoursCapacityS = plannedHoursCapacityS;
    }
    
    public Integer getUsedHoursCapacityS() {
        return usedHoursCapacityS;
    }
    
    public void setUsedHoursCapacityS(Integer usedHoursCapacityS) {
        this.usedHoursCapacityS = usedHoursCapacityS;
    }
    
}
