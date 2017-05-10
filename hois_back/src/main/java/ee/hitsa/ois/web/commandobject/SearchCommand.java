package ee.hitsa.ois.web.commandobject;

import ee.hitsa.ois.enums.Language;

public abstract class SearchCommand {

    private Language lang = Language.ET;
    private String code;
    private String name;
    private Long id;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
