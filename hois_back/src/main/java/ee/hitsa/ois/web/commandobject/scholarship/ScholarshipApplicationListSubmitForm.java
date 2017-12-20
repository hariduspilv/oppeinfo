package ee.hitsa.ois.web.commandobject.scholarship;

import java.util.List;

import ee.hitsa.ois.validation.NotEmpty;

public class ScholarshipApplicationListSubmitForm {
    @NotEmpty
    private List<Long> applications;

    public List<Long> getApplications() {
        return applications;
    }

    public void setApplications(List<Long> applications) {
        this.applications = applications;
    }

}
