package ee.hitsa.ois.web.commandobject;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.validation.NotEmpty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@DateRange
public class UserForm extends VersionedCommand {

    private Long school;

    @NotEmpty
    @ClassifierRestriction(MainClassCode.ROLL)
    private String role;
    @NotNull
    private LocalDate validFrom;
    private LocalDate validThru;

    private Set<UserRight> rights;

    public static class UserRight {
        @NotEmpty
        @ClassifierRestriction(MainClassCode.TEEMAOIGUS)
        private String object;

        private Boolean oigusV = Boolean.FALSE;
        private Boolean oigusK = Boolean.FALSE;
        private Boolean oigusM = Boolean.FALSE;
        //private Boolean OIGUS_D = Boolean.FALSE;

        public static UserRight of(String object) {
            UserRight dto = new UserRight();
            dto.object = object;
            return dto;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public Boolean getOigusV() {
            return oigusV;
        }

        public void setOigusV(Boolean oigusV) {
            this.oigusV = oigusV;
        }

        public Boolean getOigusK() {
            return oigusK;
        }

        public void setOigusK(Boolean oigusK) {
            this.oigusK = oigusK;
        }

        public Boolean getOigusM() {
            return oigusM;
        }

        public void setOigusM(Boolean oigusM) {
            this.oigusM = oigusM;
        }
/*
        public Boolean getOIGUS_D() {
            return OIGUS_D;
        }

        public void setOIGUS_D(Boolean OIGUS_D) {
            this.OIGUS_D = OIGUS_D;
        }
*/
    }

    public Long getSchool() {
        return school;
    }

    public void setSchool(Long school) {
        this.school = school;
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

    public Set<UserRight> getRights() {
        return rights;
    }

    public void setRights(Set<UserRight> rights) {
        this.rights = rights;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
