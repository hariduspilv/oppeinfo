package ee.hitsa.ois.web.dto.timetable;

import ee.hitsa.ois.web.dto.AutocompleteResult;

import java.util.ArrayList;
import java.util.List;

public class JournalOutcomeDto {

    private Long id;
    private String nameEt;
    private String nameEn;
    private Long curriculumId;
    private List<AutocompleteResult> connectedStudentGroups;
    private List<StudentCurriculumModuleOutcomesResultDto> outcomeStudents;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Long getCurriculumId() {
        return curriculumId;
    }

    public void setCurriculumId(Long curriculumId) {
        this.curriculumId = curriculumId;
    }

    public List<AutocompleteResult> getConnectedStudentGroups() {
        return connectedStudentGroups;
    }

    public void setConnectedStudentGroups(List<AutocompleteResult> connectedStudentGroups) {
        this.connectedStudentGroups = connectedStudentGroups;
    }

    public List<StudentCurriculumModuleOutcomesResultDto> getOutcomeStudents() {
        return outcomeStudents != null ? outcomeStudents : new ArrayList<>();
    }

    public void setOutcomeStudents(List<StudentCurriculumModuleOutcomesResultDto> outcomeStudents) {
        this.outcomeStudents = outcomeStudents;
    }

}
