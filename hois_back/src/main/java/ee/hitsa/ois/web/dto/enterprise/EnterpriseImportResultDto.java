package ee.hitsa.ois.web.dto.enterprise;

import java.util.ArrayList;
import java.util.List;

public class EnterpriseImportResultDto {
	
	private final List<EnterpriseImportedRowMessageDto> successful = new ArrayList<>();
    private final List<EnterpriseImportedRowMessageDto> failed = new ArrayList<>();

    public List<EnterpriseImportedRowMessageDto> getSuccessful() {
        return successful;
    }

    public List<EnterpriseImportedRowMessageDto> getFailed() {
        return failed;
    }
}
