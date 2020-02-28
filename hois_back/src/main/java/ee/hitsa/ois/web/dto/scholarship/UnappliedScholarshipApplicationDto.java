package ee.hitsa.ois.web.dto.scholarship;

import java.time.LocalDate;

public class UnappliedScholarshipApplicationDto {

    private Long id;
    private Long termId;
    private String nameEt;
    private LocalDate applicationEnd;

    public UnappliedScholarshipApplicationDto(Long id, Long termId, String nameEt, LocalDate applicationEnd) {
        this.id = id;
        this.termId = termId;
        this.nameEt = nameEt;
        this.applicationEnd = applicationEnd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public LocalDate getApplicationEnd() {
        return applicationEnd;
    }

    public void setApplicationEnd(LocalDate applicationEnd) {
        this.applicationEnd = applicationEnd;
    }
}
