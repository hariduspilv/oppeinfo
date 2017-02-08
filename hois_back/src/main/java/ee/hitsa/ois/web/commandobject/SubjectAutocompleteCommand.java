package ee.hitsa.ois.web.commandobject;

public class SubjectAutocompleteCommand extends AutocompleteCommand {

    private Long excludedId;

    public Long getExcludedId() {
        return excludedId;
    }

    public void setExcludedId(Long excludedId) {
        this.excludedId = excludedId;
    }
}
