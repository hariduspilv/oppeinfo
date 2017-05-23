package ee.hitsa.ois.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class StudyPeriod extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private StudyYear studyYear;
    private String nameEt;
    private String nameEn;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier type;
    private LocalDate startDate;
    private LocalDate endDate;

    public StudyYear getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Classifier getType() {
        return type;
    }

    public void setType(Classifier type) {
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Transient
    public List<Long> getWeekNrs() {
        List<Long> weekNrs = new ArrayList<>();
        LocalDate start = startDate;

        do {
            weekNrs.add(Long.valueOf(start.get(ChronoField.ALIGNED_WEEK_OF_YEAR)));
            start = start.plusDays(7);
        } while(!start.isAfter(endDate) || (start.getYear() == endDate.getYear() && start.get(ChronoField.ALIGNED_WEEK_OF_YEAR) == endDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR)));
        return weekNrs;
    }
}
