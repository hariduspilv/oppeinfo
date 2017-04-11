package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;

import ee.hitsa.ois.domain.SaisApplicationGraduatedSchool;
import ee.hitsa.ois.util.EntityUtil;

public class SaisApplicationGraduatedSchoolDto {

    private Long id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    public static SaisApplicationGraduatedSchoolDto of(SaisApplicationGraduatedSchool saisApplicationGraduatedSchool) {
        SaisApplicationGraduatedSchoolDto dto = EntityUtil.bindToDto(saisApplicationGraduatedSchool, new SaisApplicationGraduatedSchoolDto());
        return dto;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public LocalDateTime getStartDate() {
        return startDate;
    }


    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }


    public LocalDateTime getEndDate() {
        return endDate;
    }


    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

}
