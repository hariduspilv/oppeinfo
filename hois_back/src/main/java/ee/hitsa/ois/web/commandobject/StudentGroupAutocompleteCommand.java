package ee.hitsa.ois.web.commandobject;

public class StudentGroupAutocompleteCommand extends AutocompleteCommand {
    private Boolean valid;
    private Boolean higher;
    
    public Boolean getValid() {
        return valid;
    }
    public void setValid(Boolean valid) {
        this.valid = valid;
    }
    public Boolean getHigher() {
        return higher;
    }
    public void setHigher(Boolean higher) {
        this.higher = higher;
    }
    
}
