package ee.hitsa.ois.service.security;

import java.io.Serializable;

import ee.hitsa.ois.domain.School;
import ee.hitsa.ois.util.SchoolUtil;

public class AuthenticatedSchool implements Serializable {

    private Long id;

    private boolean vocational;

    private boolean higher;

    public AuthenticatedSchool() {
    }

    public AuthenticatedSchool(School school) {
        id = school.getId();
        higher = SchoolUtil.isHigher(school);
        vocational = SchoolUtil.isVocational(school);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isVocational() {
        return vocational;
    }

    public void setVocational(boolean vocational) {
        this.vocational = vocational;
    }

    public boolean isHigher() {
        return higher;
    }

    public void setHigher(boolean higher) {
        this.higher = higher;
    }
}
