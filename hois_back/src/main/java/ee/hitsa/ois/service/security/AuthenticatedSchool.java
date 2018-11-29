package ee.hitsa.ois.service.security;

import java.io.Serializable;

public class AuthenticatedSchool implements Serializable {

    private final Long id;
    private final boolean vocational;
    private final boolean higher;
    private final boolean doctoral;
    private final boolean letterGrades;
    private final String ehisSchool;
    private byte[] logo;

    public AuthenticatedSchool(Long id, boolean higher, boolean vocational, boolean doctoral, boolean letterGrades,
            String ehisSchool) {
        this.id = id;
        this.higher = higher;
        this.vocational = vocational;
        this.doctoral = doctoral;
        this.letterGrades = letterGrades;
        this.ehisSchool = ehisSchool;
    }

    public Long getId() {
        return id;
    }

    public boolean isVocational() {
        return vocational;
    }

    public boolean isHigher() {
        return higher;
    }

    public boolean isDoctoral() {
        return doctoral;
    }

    public boolean isLetterGrades() {
        return letterGrades;
    }

    public String getEhisSchool() {
        return ehisSchool;
    }

    public byte[] getLogo() {
        return logo;
    }
    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
    
}
