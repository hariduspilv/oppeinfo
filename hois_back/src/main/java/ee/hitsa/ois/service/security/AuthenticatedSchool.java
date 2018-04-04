package ee.hitsa.ois.service.security;

import java.io.Serializable;

public class AuthenticatedSchool implements Serializable {

    private Long id;
    private boolean vocational;
    private boolean higher;
    private boolean doctoral;
    private String ehisSchool;

    public AuthenticatedSchool() {
    }

    public AuthenticatedSchool(Long id, boolean higher, boolean vocational, boolean doctoral, String ehisSchool) {
        this.id = id;
        this.higher = higher;
        this.vocational = vocational;
        this.doctoral = doctoral;
        this.ehisSchool = ehisSchool;
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

    public boolean isDoctoral() {
        return doctoral;
    }

    public void setDoctoral(boolean doctoral) {
        this.doctoral = doctoral;
    }

    public String getEhisSchool() {
        return ehisSchool;
    }

    public void setEhisSchool(String ehisSchool) {
        this.ehisSchool = ehisSchool;
    }
}
