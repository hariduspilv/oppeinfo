package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;

public class CommitteeAutocompleteCommand extends SearchCommand {

    private String type;
    private LocalDate validFrom;
    private LocalDate validThru;
    private Long memberPerson;
    private Boolean valid;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }

    public Long getMemberPerson() {
        return memberPerson;
    }

    public void setMemberPerson(Long memberPerson) {
        this.memberPerson = memberPerson;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
    

}
