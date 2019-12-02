package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.Conditional;
import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.validation.Required;

@DateRange
@Conditional(selected = "role", values = {"ROLL_J"}, required = {"curriculums"})
public class UserForm extends VersionedCommand {

    private EntityConnectionCommand school;
    @Required
    @ClassifierRestriction(MainClassCode.ROLL)
    private String role;
    @Required
    private LocalDate validFrom;
    private LocalDate validThru;
    private EntityConnectionCommand userRole;
    private Map<String, List<String>> rights;
    private Set<EntityConnectionCommand> curriculums;

    public EntityConnectionCommand getSchool() {
        return school;
    }

    public void setSchool(EntityConnectionCommand school) {
        this.school = school;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public Map<String, List<String>> getRights() {
        return rights;
    }

    public void setRights(Map<String, List<String>> rights) {
        this.rights = rights;
    }

    public EntityConnectionCommand getUserRole() {
        return userRole;
    }

    public void setUserRole(EntityConnectionCommand userRole) {
        this.userRole = userRole;
    }

    public Set<EntityConnectionCommand> getCurriculums() {
        return curriculums;
    }

    public void setCurriculums(Set<EntityConnectionCommand> curriculums) {
        this.curriculums = curriculums;
    }
}
