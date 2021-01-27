package ee.hitsa.ois.web.dto.message;

import ee.hitsa.ois.web.dto.AutocompleteResult;

import java.util.List;

public class MessageTemplateUserResult extends AutocompleteResult {

    private String idcode;
    private List<String> userRights;

    public MessageTemplateUserResult(Long id, String nameEt, String nameEn, String idcode, List<String> userRights) {
        super(id, nameEt, nameEn);
        this.idcode = idcode;
        this.userRights = userRights;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public List<String> getUserRights() {
        return userRights;
    }

    public void setUserRights(List<String> userRights) {
        this.userRights = userRights;
    }
}
