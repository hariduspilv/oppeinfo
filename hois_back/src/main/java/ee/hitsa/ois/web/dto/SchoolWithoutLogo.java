package ee.hitsa.ois.web.dto;

public class SchoolWithoutLogo {

    private final Long id;
    private final String code;
    private final String nameEt;
    private final String nameEn;
    private final String email;

    private Boolean higher;
    private Boolean vocational;
    private Boolean doctoral;

    private Boolean isNotPublic;
    private Boolean isNotPublicTimetable;
    private Boolean isNotPublicCurriculum;
    private Boolean isNotPublicSubject;

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

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

    public Boolean getVocational() {
        return vocational;
    }

    public void setVocational(Boolean vocational) {
        this.vocational = vocational;
    }

    public Boolean getDoctoral() {
        return doctoral;
    }

    public void setDoctoral(Boolean doctoral) {
        this.doctoral = doctoral;
    }

    public Boolean getIsNotPublic() {
        return isNotPublic;
    }

    public void setIsNotPublic(Boolean isNotPublic) {
        this.isNotPublic = isNotPublic;
    }

    public Boolean getIsNotPublicTimetable() {
        return isNotPublicTimetable;
    }

    public void setIsNotPublicTimetable(Boolean isNotPublicTimetable) {
        this.isNotPublicTimetable = isNotPublicTimetable;
    }

    public Boolean getIsNotPublicCurriculum() {
        return isNotPublicCurriculum;
    }

    public void setIsNotPublicCurriculum(Boolean isNotPublicCurriculum) {
        this.isNotPublicCurriculum = isNotPublicCurriculum;
    }

    public Boolean getIsNotPublicSubject() {
        return isNotPublicSubject;
    }

    public void setIsNotPublicSubject(Boolean isNotPublicSubject) {
        this.isNotPublicSubject = isNotPublicSubject;
    }
}
