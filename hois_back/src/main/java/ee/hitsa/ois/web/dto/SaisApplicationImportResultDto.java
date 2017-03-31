package ee.hitsa.ois.web.dto;

import java.util.ArrayList;
import java.util.List;

public class SaisApplicationImportResultDto {

    private List<SaisApplicationImportedRowDto> successful = new ArrayList<>();
    private List<SaisApplicationImportedRowDto> failed = new ArrayList<>();

    public List<SaisApplicationImportedRowDto> getSuccessful() {
        return successful;
    }
    public void setSuccessful(List<SaisApplicationImportedRowDto> successful) {
        this.successful = successful;
    }
    public List<SaisApplicationImportedRowDto> getFailed() {
        return failed;
    }
    public void setFailed(List<SaisApplicationImportedRowDto> failed) {
        this.failed = failed;
    }


}