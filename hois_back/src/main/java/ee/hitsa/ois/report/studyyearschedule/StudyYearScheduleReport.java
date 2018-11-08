package ee.hitsa.ois.report.studyyearschedule;

import java.util.List;

import ee.hitsa.ois.web.dto.StudyYearScheduleLegendDto;

public class StudyYearScheduleReport {

    private List<StudyYearScheduleLegendDto> legends;
    private List<ReportTable> tables;
    
    public List<StudyYearScheduleLegendDto> getLegends() {
        return legends;
    }
    public void setLegends(List<StudyYearScheduleLegendDto> legends) {
        this.legends = legends;
    }
    
    public List<ReportTable> getTables() {
        return tables;
    }
    public void setTables(List<ReportTable> tables) {
        this.tables = tables;
    }
    
}
