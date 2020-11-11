package ee.hitsa.ois.web.dto.gradingSchema;

public class SchoolExistingGradingSchemasDto {

    private Boolean vocational;
    private Boolean higher;
    private Boolean basic;
    private Boolean secondary;

    public SchoolExistingGradingSchemasDto(Boolean vocational, Boolean higher, Boolean basic, Boolean secondary) {
        this.vocational = vocational;
        this.higher = higher;
        this.basic = basic;
        this.secondary = secondary;
    }

    public Boolean getVocational() {
        return vocational;
    }

    public void setVocational(Boolean vocational) {
        this.vocational = vocational;
    }

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

    public Boolean getBasic() {
        return basic;
    }

    public void setBasic(Boolean basic) {
        this.basic = basic;
    }

    public Boolean getSecondary() {
        return secondary;
    }

    public void setSecondary(Boolean secondary) {
        this.secondary = secondary;
    }
}
