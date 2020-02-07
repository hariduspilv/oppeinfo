package ee.hitsa.ois.web.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserRolesDto {

    // {role: {object: [permission]}]
    private final Map<String, Map<String, Set<String>>> defaultRights;
    // used for storing leading teacher allowed rights that aren't added by default
    private Map<String, Map<String, Set<String>>> extraRights = new HashMap<>();

    public UserRolesDto(Map<String, Map<String, Set<String>>> defaultRights, Map<String, Map<String, Set<String>>> extraRights) {
        this.defaultRights = defaultRights;
        this.extraRights = extraRights;
    }

    public Map<String, Map<String, Set<String>>> getDefaultRights() {
        return defaultRights;
    }

    public Map<String, Map<String, Set<String>>> getExtraRights() {
        return extraRights;
    }

    public void setExtraRights(Map<String, Map<String, Set<String>>> extraRights) {
        this.extraRights = extraRights;
    }
}
