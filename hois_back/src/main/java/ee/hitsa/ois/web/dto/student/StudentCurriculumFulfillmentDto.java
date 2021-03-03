package ee.hitsa.ois.web.dto.student;

import java.math.BigDecimal;

public class StudentCurriculumFulfillmentDto {

    private BigDecimal credits;
    private BigDecimal kkh;
    private BigDecimal apelApplicationCredits;
    private Boolean isCurriculumFulfilled;
    private BigDecimal fulfillmentPercentage;

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public BigDecimal getKkh() {
        return kkh;
    }

    public void setKkh(BigDecimal kkh) {
        this.kkh = kkh;
    }

    public BigDecimal getApelApplicationCredits() {
        return apelApplicationCredits;
    }

    public void setApelApplicationCredits(BigDecimal apelApplicationCredits) {
        this.apelApplicationCredits = apelApplicationCredits;
    }

    public Boolean getIsCurriculumFulfilled() {
        return isCurriculumFulfilled;
    }

    public void setIsCurriculumFulfilled(Boolean isCurriculumFulfilled) {
        this.isCurriculumFulfilled = isCurriculumFulfilled;
    }

    public BigDecimal getFulfillmentPercentage() {
        return fulfillmentPercentage;
    }

    public void setFulfillmentPercentage(BigDecimal fulfillmentPercentage) {
        this.fulfillmentPercentage = fulfillmentPercentage;
    }
}
