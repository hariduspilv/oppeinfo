package ee.hitsa.ois.web.commandobject.timetable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.OisFileDto;

public class TimetableImportFileForm extends VersionedCommand {

    @NotNull
    private StudyPeriod studyPeriod;
    @NotNull
    @Valid
    private OisFileDto oisFile;

    public OisFileDto getOisFile() {
        return oisFile;
    }

    public void setOisFile(OisFileDto oisFile) {
        this.oisFile = oisFile;
    }

    public StudyPeriod getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(StudyPeriod studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
}
