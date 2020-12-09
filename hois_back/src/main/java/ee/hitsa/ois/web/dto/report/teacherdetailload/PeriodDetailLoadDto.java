package ee.hitsa.ois.web.dto.report.teacherdetailload;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import ee.hitsa.ois.service.TeacherDetailLoadService;

public class PeriodDetailLoadDto {

    private Map<Long, TeacherDetailLoadService.TeacherDetailLoad> plannedHours = new HashMap<>();
    private Map<Long, TeacherDetailLoadService.TeacherDetailLoad> capacityPlannedHours = new HashMap<>();
    private Map<Long, TeacherDetailLoadService.TeacherDetailLoad> journalOccurredLessons = new HashMap<>();
    private Map<Long, TeacherDetailLoadService.TeacherDetailLoad> capacityJournalOccurredLessons = new HashMap<>();
    private Map<Long, TeacherDetailLoadService.TeacherDetailLoad> timetableOccurredLessons = new HashMap<>();
    private Map<Long, TeacherDetailLoadService.TeacherDetailLoad> capacityTimetableOccurredLessons = new HashMap<>();
    private Map<Long, TeacherDetailLoadService.TeacherDetailLoad> substitutedLessons = new HashMap<>();
    private Map<Long, TeacherDetailLoadService.TeacherDetailLoad> singleEvents = new HashMap<>();

    private Long totalPlannedHours;
    private Long totalPlannedContactHours;
    private BigDecimal totalPlannedKoefHours;
    private Map<String, Long> totalCapacityPlannedHours = new HashMap<>();
    private Long journalTotalOccurredLessons;
    private Long journalTotalOccurredContactLessons;
    private BigDecimal journalTotalOccurredKoefLessons;
    private Map<String, Long> journalTotalCapacityOccurredLessons = new HashMap<>();
    private Long timetableTotalOccurredLessons;
    private Map<String, Long> timetableTotalCapacityOccurredLessons = new HashMap<>();
    private Long totalSubstitutedLessons;
    private Long totalSingleEvents;
    private Long timetableTotalOccurredContactLessons;
    private BigDecimal timetableTotalOccurredKoefLessons;

    public Map<Long, TeacherDetailLoadService.TeacherDetailLoad> getPlannedHours() {
        return plannedHours;
    }

    public void setPlannedHours(Map<Long, TeacherDetailLoadService.TeacherDetailLoad> plannedHours) {
        this.plannedHours = plannedHours;
    }

    public Map<Long, TeacherDetailLoadService.TeacherDetailLoad> getSubstitutedLessons() {
        return substitutedLessons;
    }

    public void setSubstitutedLessons(Map<Long, TeacherDetailLoadService.TeacherDetailLoad> substitutedLessons) {
        this.substitutedLessons = substitutedLessons;
    }

    public Map<Long, TeacherDetailLoadService.TeacherDetailLoad> getSingleEvents() {
        return singleEvents;
    }

    public void setSingleEvents(Map<Long, TeacherDetailLoadService.TeacherDetailLoad> singleEvents) {
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

	public Map<Long, TeacherDetailLoadService.TeacherDetailLoad> getTimetableOccurredLessons() {
		return timetableOccurredLessons;
	}

	public void setTimetableOccurredLessons(
			Map<Long, TeacherDetailLoadService.TeacherDetailLoad> timetableOccurredLessons) {
		this.timetableOccurredLessons = timetableOccurredLessons;
	}

	public Long getTimetableTotalOccurredContactLessons() {
		return timetableTotalOccurredContactLessons;
	}

	public void setTimetableTotalOccurredContactLessons(Long timetableTotalOccurredContactLessons) {
		this.timetableTotalOccurredContactLessons = timetableTotalOccurredContactLessons;
	}

	public BigDecimal getTimetableTotalOccurredKoefLessons() {
		return timetableTotalOccurredKoefLessons;
	}

	public void setTimetableTotalOccurredKoefLessons(BigDecimal timetableTotalOccurredKoefLessons) {
		this.timetableTotalOccurredKoefLessons = timetableTotalOccurredKoefLessons;
	}

	public Long getTotalPlannedContactHours() {
		return totalPlannedContactHours;
	}

	public void setTotalPlannedContactHours(Long totalPlannedContactHours) {
		this.totalPlannedContactHours = totalPlannedContactHours;
	}

	public BigDecimal getTotalPlannedKoefHours() {
		return totalPlannedKoefHours;
	}

	public void setTotalPlannedKoefHours(BigDecimal totalPlannedKoefHours) {
		this.totalPlannedKoefHours = totalPlannedKoefHours;
	}

	public Map<Long, TeacherDetailLoadService.TeacherDetailLoad> getCapacityPlannedHours() {
		return capacityPlannedHours;
	}

	public void setCapacityPlannedHours(Map<Long, TeacherDetailLoadService.TeacherDetailLoad> capacityPlannedHours) {
		this.capacityPlannedHours = capacityPlannedHours;
	}

	public Map<Long, TeacherDetailLoadService.TeacherDetailLoad> getCapacityTimetableOccurredLessons() {
		return capacityTimetableOccurredLessons;
	}

	public void setCapacityTimetableOccurredLessons(
			Map<Long, TeacherDetailLoadService.TeacherDetailLoad> capacityTimetableOccurredLessons) {
		this.capacityTimetableOccurredLessons = capacityTimetableOccurredLessons;
	}

	public Map<Long, TeacherDetailLoadService.TeacherDetailLoad> getCapacityJournalOccurredLessons() {
		return capacityJournalOccurredLessons;
	}

	public void setCapacityJournalOccurredLessons(
			Map<Long, TeacherDetailLoadService.TeacherDetailLoad> capacityJournalOccurredLessons) {
		this.capacityJournalOccurredLessons = capacityJournalOccurredLessons;
	}

	public Map<Long, TeacherDetailLoadService.TeacherDetailLoad> getJournalOccurredLessons() {
		return journalOccurredLessons;
	}

	public void setJournalOccurredLessons(Map<Long, TeacherDetailLoadService.TeacherDetailLoad> journalOccurredLessons) {
		this.journalOccurredLessons = journalOccurredLessons;
	}

	public Long getJournalTotalOccurredContactLessons() {
		return journalTotalOccurredContactLessons;
	}

	public void setJournalTotalOccurredContactLessons(Long journalTotalOccurredContactLessons) {
		this.journalTotalOccurredContactLessons = journalTotalOccurredContactLessons;
	}

	public BigDecimal getJournalTotalOccurredKoefLessons() {
		return journalTotalOccurredKoefLessons;
	}

	public void setJournalTotalOccurredKoefLessons(BigDecimal journalTotalOccurredKoefLessons) {
		this.journalTotalOccurredKoefLessons = journalTotalOccurredKoefLessons;
	}

}
