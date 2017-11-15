package ee.hitsa.ois.web.commandobject.curriculum;

public class CurriculumVersionAutocompleteCommand {

    private String name;
    private Boolean higher;
    private Boolean sais;
    private Boolean valid;
    private Boolean languages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

    public Boolean getSais() {
        return sais;
    }

    public void setSais(Boolean sais) {
        this.sais = sais;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Boolean getLanguages() {
        return languages;
    }

    public void setLanguages(Boolean languages) {
        this.languages = languages;
    }
}
