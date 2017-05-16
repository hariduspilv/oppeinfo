package ee.hitsa.ois.web.commandobject.timetable;

import java.util.List;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class LessonPlanJournalForm extends VersionedCommand {

    @NotEmpty
    @ClassifierRestriction(MainClassCode.KUTSEHINDAMISVIIS)
    private String assessment;
    @NotEmpty
    private String nameEt;
    @NotEmpty
    @ClassifierRestriction(MainClassCode.PAEVIK_GRUPI_JAOTUS)
    private String groupProportion;
    @NotEmpty
    @ClassifierRestriction(MainClassCode.MAHT)
    private List<String> journalCapacityTypes;

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getGroupProportion() {
        return groupProportion;
    }

    public void setGroupProportion(String groupProportion) {
        this.groupProportion = groupProportion;
    }

    public List<String> getJournalCapacityTypes() {
        return journalCapacityTypes;
    }

    public void setJournalCapacityTypes(List<String> journalCapacityTypes) {
        this.journalCapacityTypes = journalCapacityTypes;
    }
}
