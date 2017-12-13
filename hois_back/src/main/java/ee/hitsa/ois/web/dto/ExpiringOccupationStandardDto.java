package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.util.List;

public class ExpiringOccupationStandardDto {

    private final String nameEt;
    private final String nameEn;
    private final LocalDate validThru;

    public ExpiringOccupationStandardDto(String nameEt, String nameEn, LocalDate validThru, List<String> curriculums) {
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.validThru = validThru;
        this.curriculums = curriculums;
    }

    private final List<String> curriculums;

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public List<String> getCurriculums() {
        return curriculums;
    }
}
