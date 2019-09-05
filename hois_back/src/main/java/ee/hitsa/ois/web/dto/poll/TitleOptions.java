package ee.hitsa.ois.web.dto.poll;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class TitleOptions {
    
    private Boolean display;
    private AutocompleteResult text;
    
    public Boolean getDisplay() {
        return display;
    }
    public void setDisplay(Boolean display) {
        this.display = display;
    }
    public AutocompleteResult getText() {
        return text;
    }
    public void setText(AutocompleteResult text) {
        this.text = text;
    }

}
