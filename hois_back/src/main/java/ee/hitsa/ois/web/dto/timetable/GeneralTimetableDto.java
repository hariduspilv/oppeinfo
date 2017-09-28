package ee.hitsa.ois.web.dto.timetable;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.time.LocalDate;

public class GeneralTimetableDto {
    private final Long id;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Long studyPeriodId;
    private final String nameEt;
    private final String nameEn;
    
    public GeneralTimetableDto(Object[] row) {
        this.id = resultAsLong(row, 0);
        this.startDate = resultAsLocalDate(row, 1);
        this.endDate = resultAsLocalDate(row, 2);
        this.studyPeriodId = resultAsLong(row, 3);
        this.nameEt = (String) row[4];
        this.nameEn = (String) row[5];
    }

    public Long getId() {
        return id;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public Long getStudyPeriodId() {
        return studyPeriodId;
    }

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }
}