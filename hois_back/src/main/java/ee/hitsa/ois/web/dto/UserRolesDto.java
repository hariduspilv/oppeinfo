package ee.hitsa.ois.web.dto;

import java.util.List;
import java.util.Map;

public class UserRolesDto {

    // {role: {object: [permission]}]
    private final Map<String, Map<String, List<String>>> defaultRights;

    public UserRolesDto(Map<String, Map<String, List<String>>> defaultRights) {
        this.defaultRights = defaultRights;
    }

    public Map<String, Map<String, List<String>>> getDefaultRights() {
        return defaultRights;
    }
}
