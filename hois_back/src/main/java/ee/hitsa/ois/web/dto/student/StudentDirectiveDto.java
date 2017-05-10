package ee.hitsa.ois.web.dto.student;

import java.time.LocalDate;

public class StudentDirectiveDto {

    private Long id;
    private String headline;
    private String type;
    private String directiveNr;
    private LocalDate confirmDate;
    private String insertedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirectiveNr() {
        return directiveNr;
    }

    public void setDirectiveNr(String directiveNr) {
        this.directiveNr = directiveNr;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }
}
