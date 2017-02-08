package ee.hitsa.ois.web.dto;

public class ClassifierWithCount extends ClassifierSelection {

    private final Long count;

    public ClassifierWithCount(String code, String nameEt, String nameEn, String nameRu, Long count) {
        super(code, nameEt, nameEn, nameRu, Boolean.TRUE);
        this.count = count;
    }

    public Long getCount() {
        return count;
    }
}
