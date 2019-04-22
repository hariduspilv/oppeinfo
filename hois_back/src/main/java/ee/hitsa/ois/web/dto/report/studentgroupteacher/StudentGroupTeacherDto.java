package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StudentGroupTeacherDto {

    private List<ModuleTypeDto> moduleTypes = new ArrayList<>();
    private List<ModuleDto> modules = new ArrayList<>();
    private List<ResultColumnDto> resultColumns = new ArrayList<>();
    private List<StudentDto> students = new ArrayList<>();
    private BigDecimal averageGrade;
    private Boolean showModuleGrade;
    private Boolean showAverageGrade;
    private Boolean showWeightedAverageGrade;
    private Boolean showOnlyModuleGrades;

    public List<ModuleTypeDto> getModuleTypes() {
        return moduleTypes;
    }

    public void setModuleTypes(List<ModuleTypeDto> moduleTypes) {
        this.moduleTypes = moduleTypes;
    }

    public List<ModuleDto> getModules() {
        return modules;
    }

    public void setModules(List<ModuleDto> modules) {
        this.modules = modules;
    }
    
    public List<ResultColumnDto> getResultColumns() {
        return resultColumns;
    }

    public void setResultColumns(List<ResultColumnDto> resultColumns) {
        this.resultColumns = resultColumns;
    }

    public List<StudentDto> getStudents() {
        return students;
    }

    public void setStudents(List<StudentDto> students) {
        this.students = students;
    }

    public BigDecimal getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(BigDecimal averageGrade) {
        this.averageGrade = averageGrade;
    }

    public Boolean getShowModuleGrade() {
        return showModuleGrade;
    }

    public void setShowModuleGrade(Boolean showModuleGrade) {
        this.showModuleGrade = showModuleGrade;
    }

    public Boolean getShowAverageGrade() {
        return showAverageGrade;
    }

    public void setShowAverageGrade(Boolean showAverageGrade) {
        this.showAverageGrade = showAverageGrade;
    }

    public Boolean getShowWeightedAverageGrade() {
        return showWeightedAverageGrade;
    }

    public void setShowWeightedAverageGrade(Boolean showWeightedAverageGrade) {
        this.showWeightedAverageGrade = showWeightedAverageGrade;
    }

    public Boolean getShowOnlyModuleGrades() {
        return showOnlyModuleGrades;
    }

    public void setShowOnlyModuleGrades(Boolean showOnlyModuleGrades) {
        this.showOnlyModuleGrades = showOnlyModuleGrades;
    }

}
