package ee.hitsa.ois.web.dto.apelapplication;

public class ApelSchoolResult {

    private final Long id;
    private final String nameEt;
    private final String nameEn;
    private final String ehisSchool;
    private final String country;

    public ApelSchoolResult(Long id, String nameEt, String nameEn, String ehisSchool, String country) {
        this.id = id;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.ehisSchool = ehisSchool;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getEhisSchool() {
        return ehisSchool;
    }

    public String getCountry() {
        return country;
    }

}
