package ee.hitsa.ois.web.commandobject;

public class BuildingAutocompleteCommand extends SearchCommand {

    private Boolean isDormitory;

    public Boolean getIsDormitory() {
        return isDormitory;
    }

    public void setIsDormitory(Boolean isDormitory) {
        this.isDormitory = isDormitory;
    }

}
