package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDto {

    private Long id;
    private String fullname;
    private String status;
    private Boolean isIndividualCurriculum;
    private List<StudentResultColumnDto> resultColumns = new ArrayList<>();
    private Map<String, Short> absences = new HashMap<>();
    private Boolean hasAddInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsIndividualCurriculum() {
        return isIndividualCurriculum;
    }

    public void setIsIndividualCurriculum(Boolean isIndividualCurriculum) {
        this.isIndividualCurriculum = isIndividualCurriculum;
    }

    public List<StudentResultColumnDto> getResultColumns() {
        return resultColumns;
    }

    public void setResultColumns(List<StudentResultColumnDto> resultColumns) {
        this.resultColumns = resultColumns;
    }

    public Map<String, Short> getAbsences() {
        return absences;
    }

    public void setAbsences(Map<String, Short> absences) {
        this.absences = absences;
    }

    public Boolean getHasAddInfo() {
        return hasAddInfo;
    }

    public void setHasAddInfo(Boolean hasAddInfo) {
        this.hasAddInfo = hasAddInfo;
    }

}
