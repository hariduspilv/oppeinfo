package ee.hitsa.ois.web.dto.message;

import java.util.List;

public class MessageFormDto {

    private List<String> targetGroups;
    private String defaultTargetGroup;

    private List<Group> additionalGroup;

    public List<String> getTargetGroups() {
        return targetGroups;
    }

    public void setTargetGroups(List<String> targetGroups) {
        this.targetGroups = targetGroups;
    }

    public String getDefaultTargetGroup() {
        return defaultTargetGroup;
    }

    public void setDefaultTargetGroup(String defaultTargetGroup) {
        this.defaultTargetGroup = defaultTargetGroup;
    }

    public List<Group> getAdditionalGroup() {
        return additionalGroup;
    }

    public void setAdditionalGroup(List<Group> additionalGroup) {
        this.additionalGroup = additionalGroup;
    }

    public static class Group {

        private final String code;
        private final List<String> roles;
        private final String translateRef;

        public Group(String code, List<String> roles, String translateRef) {
            this.code = code;
            this.roles = roles;
            this.translateRef = translateRef;
        }

        public String getCode() {
            return code;
        }

        public List<String> getRoles() {
            return roles;
        }

        public String getTranslateRef() {
            return translateRef;
        }
    }
}
