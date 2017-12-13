package ee.hitsa.ois.web.commandobject.apelapplication;

import java.util.List;

import ee.hitsa.ois.web.commandobject.SearchCommand;

public class ApelSchoolSearchCommand extends SearchCommand {
    
    private List<String> country;

    public List<String> getCountry() {
        return country;
    }

    public void setCountry(List<String> country) {
        this.country = country;
    }

}
