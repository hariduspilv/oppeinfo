package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import java.util.ArrayList;
import java.util.List;

public class ModuleTypeDto {

    private String code;
    private List<ModuleDto> modules = new ArrayList<>();
    private Long colspan;
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public List<ModuleDto> getModules() {
        return modules;
    }
    
    public void setModules(List<ModuleDto> modules) {
        this.modules = modules;
    }

    public Long getColspan() {
        return colspan;
    }

    public void setColspan(Long colspan) {
        this.colspan = colspan;
    }
    
    public List<Long> getExcelCopsanColums() {
        List<Long> columns = new ArrayList<>();
        for (int i = 0; i < this.colspan.longValue() - 1; i++) {
            columns.add(Long.valueOf(i));
        }
        return columns;
    }
    
}