package ee.hitsa.ois.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TeacherLoad {
	private final String capacityType;
	private final Long lessons;
	private final Boolean isContact;
	private final BigDecimal koef;
	private final Long studyPeriodId;
	private final LocalDate date;
    
    public TeacherLoad(String capacityType, Long lessons, Boolean isContact, BigDecimal koef, Long studyPeriodId, LocalDate date) {
    	this.capacityType = capacityType;
    	this.lessons = lessons;
    	this.isContact = isContact;
    	this.koef = koef;
    	this.studyPeriodId = studyPeriodId;
    	this.date = date;
    }

	public String getCapacityType() {
		return capacityType;
	}

	public Boolean getIsContact() {
		return isContact;
	}

	public BigDecimal getKoef() {
		return koef;
	}

	public Long getLessons() {
		return lessons;
	}

	public Long getStudyPeriodId() {
		return studyPeriodId;
	}

	public LocalDate getDate() {
		return date;
	}
}
