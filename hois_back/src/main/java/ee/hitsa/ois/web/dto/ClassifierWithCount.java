package ee.hitsa.ois.web.dto;

public class ClassifierWithCount {

    private final String code;
    private final String nameEt;
    private final String nameEn;
    private final String nameRu;
    private final Long count;

    public ClassifierWithCount(String code, String nameEt, String nameEn, String nameRu, Long count) {
        this.code = code;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.nameRu = nameRu;
        this.count = count;
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

    public String getNameRu() {
        return nameRu;
    }

    public Long getCount() {
        return count;
    }
}
