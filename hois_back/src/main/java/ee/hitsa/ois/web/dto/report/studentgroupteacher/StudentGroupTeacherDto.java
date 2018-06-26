package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import java.util.ArrayList;
import java.util.List;

public class StudentGroupTeacherDto {

    private List<ModuleTypeDto> moduleTypes = new ArrayList<>();
    private List<ModuleDto> modules = new ArrayList<>();
    private List<ResultColumnDto> resultColumns = new ArrayList<>();
    private List<StudentDto> students = new ArrayList<>();
    private Boolean showModuleGrade;

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

    public Boolean getShowModuleGrade() {
        return showModuleGrade;
    }

    public void setShowModuleGrade(Boolean showModuleGrade) {
        this.showModuleGrade = showModuleGrade;
    }
    
}
