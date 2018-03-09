package ee.hitsa.ois.web.dto.sais;

import java.time.LocalDate;

import ee.hitsa.ois.domain.sais.SaisApplicationGraduatedSchool;

public class SaisApplicationGraduatedSchoolDto {

    private final Long id;
    private final String name;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public SaisApplicationGraduatedSchoolDto(SaisApplicationGraduatedSchool saisApplicationGraduatedSchool) {
        this.id = saisApplicationGraduatedSchool.getId();
        this.name = saisApplicationGraduatedSchool.getName();
        this.startDate = saisApplicationGraduatedSchool.getStartDate();
        this.endDate = saisApplicationGraduatedSchool.getEndDate();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
