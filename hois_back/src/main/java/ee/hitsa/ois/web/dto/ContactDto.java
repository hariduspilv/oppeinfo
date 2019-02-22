package ee.hitsa.ois.web.dto;

public class ContactDto {
    private Long id;
    private String contactPersonName;
    private String contactPersonEmail;
    private String contactPersonPhone;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContactPersonName() {
        return contactPersonName;
    }
    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }
    public String getContactPersonEmail() {
        return contactPersonEmail;
    }
    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }
    public String getContactPersonPhone() {
        return contactPersonPhone;
    }
    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }
}
