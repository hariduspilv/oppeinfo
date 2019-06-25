package ee.hitsa.ois.web.dto.poll;

import java.util.List;

public class ThemesDto {
    
    private String type;
    private Boolean isThemePageable;
    private Boolean confirmed;
    private String foreword;
    private String afterword;
    private List<ThemeDto> themes;
    private Long responseId;
    
    public ThemesDto(List<ThemeDto> themes, Boolean confirmed, String foreword, String afterword) {
        this.themes = themes;
        this.confirmed = confirmed;
        this.foreword = foreword;
        this.afterword = afterword;
    }
    
    public ThemesDto() {}
    
    public List<ThemeDto> getThemes() {
        return themes;
    }

    public void setThemes(List<ThemeDto> themes) {
        this.themes = themes;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getForeword() {
        return foreword;
    }

    public void setForeword(String foreword) {
        this.foreword = foreword;
    }

    public String getAfterword() {
        return afterword;
    }

    public void setAfterword(String afterword) {
        this.afterword = afterword;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

    public Long getResponseId() {
        return responseId;
    }

    public Boolean getIsThemePageable() {
        return isThemePageable;
    }

    public void setIsThemePageable(Boolean isThemePageable) {
        this.isThemePageable = isThemePageable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
