package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Subject;

public class AutocompleteResult<ID> {

    private final ID id;
    private final String nameEt;
    private final String nameEn;

    public AutocompleteResult(ID id, String nameEt, String nameEn) {
        this.id = id;
        this.nameEt = nameEt;
        this.nameEn = nameEn;
    }

    public ID getId() {
        return id;
    }

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public static AutocompleteResult<Long> of(Subject subject) {
        return new AutocompleteResult<>(subject.getId(), subject.getNameEt(), subject.getNameEn());
    }
}
