package ee.hitsa.ois.web.dto.sais;

import java.util.ArrayList;
import java.util.List;

public class SaisApplicationImportResultDto {

    private final List<SaisApplicationImportedRowDto> successful = new ArrayList<>();
    private final List<SaisApplicationImportedRowDto> failed = new ArrayList<>();

    public List<SaisApplicationImportedRowDto> getSuccessful() {
        return successful;
    }

    public List<SaisApplicationImportedRowDto> getFailed() {
        return failed;
    }
}
