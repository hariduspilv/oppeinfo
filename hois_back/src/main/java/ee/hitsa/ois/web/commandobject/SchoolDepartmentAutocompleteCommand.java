package ee.hitsa.ois.web.commandobject;

public class SchoolDepartmentAutocompleteCommand extends AutocompleteCommand {

    private Long excludedId;

    public Long getExcludedId() {
        return excludedId;
    }

    public void setExcludedId(Long excludedId) {
        this.excludedId = excludedId;
    }
}
