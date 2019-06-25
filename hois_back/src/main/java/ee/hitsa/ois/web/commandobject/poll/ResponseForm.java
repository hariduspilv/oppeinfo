package ee.hitsa.ois.web.commandobject.poll;

import java.util.List;

import ee.hitsa.ois.web.dto.poll.ThemeDto;

public class ResponseForm {
    private List<ThemeDto> themes;

    public List<ThemeDto> getThemes() {
        return themes;
    }

    public void setThemes(List<ThemeDto> themes) {
        this.themes = themes;
    }
}
