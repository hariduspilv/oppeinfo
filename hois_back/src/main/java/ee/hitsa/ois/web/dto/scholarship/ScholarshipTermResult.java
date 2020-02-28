package ee.hitsa.ois.web.dto.scholarship;

import ee.hitsa.ois.web.dto.AutocompleteResult;

import java.time.LocalDate;

public class ScholarshipTermResult extends AutocompleteResult {

    private String type;
    private LocalDate applicationStart;
    private LocalDate applicationEnd;

    public ScholarshipTermResult() {
        super();
    }

    public ScholarshipTermResult(Long id, String nameEt, String nameEn) {
        super(id, nameEt, nameEn);
    }

    public ScholarshipTermResult(Long id, String nameEt, String nameEn, String type, LocalDate applicationStart,
            LocalDate applicationEnd) {
        super(id, nameEt, nameEn);
        this.type = type;
        this.applicationStart = applicationStart;
        this.applicationEnd = applicationEnd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getApplicationStart() {
        return applicationStart;
    }

    public void setApplicationStart(LocalDate applicationStart) {
        this.applicationStart = applicationStart;
    }

    public LocalDate getApplicationEnd() {
        return applicationEnd;
    }

    public void setApplicationEnd(LocalDate applicationEnd) {
        this.applicationEnd = applicationEnd;
    }
}
