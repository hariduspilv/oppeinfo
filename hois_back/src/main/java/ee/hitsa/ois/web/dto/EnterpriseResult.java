package ee.hitsa.ois.web.dto;

public class EnterpriseResult extends AutocompleteResult {

    private String contactPersonName;
    private String contactPersonPhone;
    private String contactPersonEmail;

    public EnterpriseResult() {
    }

    public EnterpriseResult(Long id, String nameEt, String nameEn) {
        super(id, nameEt, nameEn);
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

}
