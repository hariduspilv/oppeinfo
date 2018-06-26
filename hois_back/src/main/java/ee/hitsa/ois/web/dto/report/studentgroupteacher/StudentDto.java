package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import java.util.ArrayList;
import java.util.List;

public class StudentDto {
    
    private Long id;
    private String fullname;
    private List<StudentResultColumnDto> resultColumns = new ArrayList<>();
    private Short absenceH;
    private Short absenceP;
    private Short absenceV;
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

    public List<StudentResultColumnDto> getResultColumns() {
        return resultColumns;
    }

    public void setResultColumns(List<StudentResultColumnDto> resultColumns) {
        this.resultColumns = resultColumns;
    }

    public Short getAbsenceH() {
        return absenceH;
    }

    public void setAbsenceH(Short absenceH) {
        this.absenceH = absenceH;
    }

    public Short getAbsenceP() {
        return absenceP;
    }

    public void setAbsenceP(Short absenceP) {
        this.absenceP = absenceP;
    }

    public Short getAbsenceV() {
        return absenceV;
    }

    public void setAbsenceV(Short absenceV) {
        this.absenceV = absenceV;
    }

    public Boolean getHasAddInfo() {
        return hasAddInfo;
    }

    public void setHasAddInfo(Boolean hasAddInfo) {
        this.hasAddInfo = hasAddInfo;
    }
    
}
