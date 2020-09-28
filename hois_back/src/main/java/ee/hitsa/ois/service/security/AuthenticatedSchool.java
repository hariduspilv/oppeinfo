package ee.hitsa.ois.service.security;

import java.io.Serializable;

public class AuthenticatedSchool implements Serializable {

    private final Long id;
    private final boolean basic;
    private final boolean secondary;
    private final boolean vocational;
    private final boolean higher;
    private final boolean doctoral;
    private final boolean letterGrades;
    private final boolean withoutEkis;
    private final boolean hmodules;
    private final boolean isNotAbsence;
    private final String ehisSchool;
    private byte[] logo;

    public AuthenticatedSchool(Long id, boolean basic, boolean secondary, boolean vocational, boolean higher,
            boolean doctoral, boolean letterGrades, String ehisSchool, boolean withoutEkis, boolean hmodules,
            boolean isNotAbsence) {
        this.id = id;
        this.basic = basic;
        this.secondary = secondary;
        this.vocational = vocational;
        this.higher = higher;
        this.doctoral = doctoral;
        this.letterGrades = letterGrades;
        this.ehisSchool = ehisSchool;
        this.withoutEkis = withoutEkis;
        this.hmodules = hmodules;
        this.isNotAbsence = isNotAbsence;
    }

    public Long getId() {
        return id;
    }

    public boolean isBasic() {
        return basic;
    }

    public boolean isSecondary() {
        return secondary;
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

    public boolean isWithoutEkis() {
        return withoutEkis;
    }

    public boolean isHmodules() {
        return hmodules;
    }

    public boolean isNotAbsence() {
        return isNotAbsence;
    }
    
}
