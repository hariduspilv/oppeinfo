package ee.hitsa.ois.web.commandobject.curriculum;

import java.util.List;

public class CurriculumVersionOccupationModuleThemeAutocompleteCommand {

    private Long curriculumVersionOmoduleId;
    private List<Long> curriculumVersionOmoduleIds;
    private Boolean closedCurriculumVersionModules;
    private Boolean addStudyYearToName;
    private Boolean existInOtherJournals;
    private Long journalId;
    private Long journalSubId;
    private Long studentGroupId;

    public Long getCurriculumVersionOmoduleId() {
        return curriculumVersionOmoduleId;
    }

    public void setCurriculumVersionOmoduleId(Long curriculumVersionOmoduleId) {
        this.curriculumVersionOmoduleId = curriculumVersionOmoduleId;
    }

    public List<Long> getCurriculumVersionOmoduleIds() {
        return curriculumVersionOmoduleIds;
    }

    public void setCurriculumVersionOmoduleIds(List<Long> curriculumVersionOmoduleIds) {
        this.curriculumVersionOmoduleIds = curriculumVersionOmoduleIds;
    }

    public Boolean getClosedCurriculumVersionModules() {
        return closedCurriculumVersionModules;
    }

    public void setClosedCurriculumVersionModules(Boolean closedCurriculumVersionModules) {
        this.closedCurriculumVersionModules = closedCurriculumVersionModules;
    }

    public Boolean getAddStudyYearToName() {
        return addStudyYearToName;
    }

    public void setAddStudyYearToName(Boolean addStudyYearToName) {
        this.addStudyYearToName = addStudyYearToName;
    }

    public Boolean getExistInOtherJournals() {
        return existInOtherJournals;
    }

    public void setExistInOtherJournals(Boolean existInOtherJournals) {
        this.existInOtherJournals = existInOtherJournals;
    }

    public Long getJournalId() {
        return journalId;
    }

    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }

    public Long getJournalSubId() {
        return journalSubId;
    }

    public void setJournalSubId(Long journalSubId) {
        this.journalSubId = journalSubId;
    }

    public Long getStudentGroupId() {
        return studentGroupId;
    }

    public void setStudentGroupId(Long studentGroupId) {
        this.studentGroupId = studentGroupId;
    }
}
