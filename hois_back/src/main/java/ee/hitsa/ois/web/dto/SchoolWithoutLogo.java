package ee.hitsa.ois.web.dto;

public class SchoolWithoutLogo {

    private final Long id;
    private final String code;
    private final String nameEt;
    private final String nameEn;
    private final String email;

    public SchoolWithoutLogo(Long id, String code, String nameEt, String nameEn, String email) {
        this.id = id;
        this.code = code;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getEmail() {
        return email;
    }
}
