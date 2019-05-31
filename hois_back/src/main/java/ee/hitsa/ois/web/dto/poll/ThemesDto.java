package ee.hitsa.ois.web.dto.poll;

import java.util.List;

public class ThemesDto {
    
    public Boolean confirmed;
    
    public ThemesDto(List<ThemeDto> themes, Boolean confirmed) {
        this.themes = themes;
        this.confirmed = confirmed;
    }
    
    private List<ThemeDto> themes;

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

}
