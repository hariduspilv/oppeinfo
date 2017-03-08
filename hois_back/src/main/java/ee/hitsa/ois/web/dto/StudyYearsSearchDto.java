package ee.hitsa.ois.web.dto;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.time.LocalDate;

public class StudyYearsSearchDto {
    private String code;
    private String nameEt;
    private String nameEn;
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long count;

    public StudyYearsSearchDto(Object[] row) {
        this.code = (String) row[0];
        this.nameEt = (String) row[1];
        this.nameEn = (String) row[2];
        this.id = resultAsLong(row, 3);
        this.startDate = resultAsLocalDate(row, 4);
        this.endDate = resultAsLocalDate(row, 5);
        this.count = resultAsLong(row, 6);
    }

    public String getCode() {
        return code;
    }

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
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

    public Long getCount() {
        return count;
    }
}
