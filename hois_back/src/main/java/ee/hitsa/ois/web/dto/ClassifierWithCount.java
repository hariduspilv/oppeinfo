package ee.hitsa.ois.web.dto;

public class ClassifierWithCount {
    private final String code;
    private final String nameEt;
    private final String nameEn;
    private final Long count;

    public ClassifierWithCount(String code, String nameEt, String nameEn, Long count) {
        this.code = code;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
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

    public Long getCount() {
        return count;
    }
}
