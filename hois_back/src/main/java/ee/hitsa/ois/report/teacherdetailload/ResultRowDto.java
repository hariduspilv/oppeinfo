package ee.hitsa.ois.report.teacherdetailload;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.report.teacherdetailload.PeriodDetailLoadDto;

public class ResultRowDto extends PeriodDetailLoadDto {

    private AutocompleteResult name;
    private String studentGroups;
    private List<Long> loads = new ArrayList<>();

    public AutocompleteResult getName() {
        return name;
    }

    public void setName(AutocompleteResult name) {
        this.name = name;
    }

    public String getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(String studentGroups) {
        this.studentGroups = studentGroups;
    }

    public List<Long> getLoads() {
        return loads;
    }

    public void setLoads(List<Long> loads) {
        this.loads = loads;
    }

}
