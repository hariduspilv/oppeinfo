package ee.hitsa.ois.web.dto;

public class ClassifierWithCount extends ClassifierSelection {

    private final Long count;

    public ClassifierWithCount(String code, String nameEt, String nameEn, String nameRu, String mainClassCode, Long count, String value) {
        super(code, nameEt, nameEn, nameRu, Boolean.TRUE, null, null, mainClassCode, value);
        this.count = count;
    }

    public Long getCount() {
        return count;
    }
}
