package ee.hitsa.ois.web.commandobject;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import ee.hitsa.ois.web.dto.StudyYearScheduleLegendDto;

public class SchoolUpdateStudyYearScheduleLegendsCommand extends VersionedCommand {
    @Valid
    private List<StudyYearScheduleLegendDto> legends;

    public List<StudyYearScheduleLegendDto> getLegends() {
        return legends != null ? legends : (legends = new ArrayList<>());
    }

    public void setLegends(List<StudyYearScheduleLegendDto> legends) {
        this.legends = legends;
    }
}
