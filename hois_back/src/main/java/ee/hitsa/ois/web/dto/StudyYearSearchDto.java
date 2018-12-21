package ee.hitsa.ois.web.dto;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.time.LocalDate;

import ee.hitsa.ois.domain.StudyYear;

public class StudyYearSearchDto {
    private String code;
    private String nameEt;
    private String nameEn;
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long count;
    
    public StudyYearSearchDto() {}

    public StudyYearSearchDto(Object[] row) {
        this.code = (String) row[0];
        this.nameEt = (String) row[1];
        this.nameEn = (String) row[2];
        this.id = resultAsLong(row, 3);
        this.startDate = resultAsLocalDate(row, 4);
        this.endDate = resultAsLocalDate(row, 5);
        this.count = resultAsLong(row, 6);
    }
    
    public static StudyYearSearchDto of(StudyYear studyYear) {
        StudyYearSearchDto dto = new StudyYearSearchDto();
        dto.setId(studyYear.getId());
        dto.setCode(studyYear.getYear().getCode());
        dto.setNameEt(studyYear.getYear().getNameEt());
        dto.setNameEn(studyYear.getYear().getNameEn());
        dto.setStartDate(studyYear.getStartDate());
        dto.setEndDate(studyYear.getEndDate());
        return dto;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
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

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public Long getCount() {
        return count;
    }
    public void setCount(Long count) {
        this.count = count;
    }
    
}
