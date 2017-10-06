package ee.hois.soap.ekis.client;

public class RegisterCertificateRequest {

    private String qguid;
    private String ehisId;
    private String oisId;
    private String student;
    private String email;
    private String subject;
    private String body;
    private String itemCreator;
    private String type;
    private String institution;

    public String getQguid() {
        return qguid;
    }

    public void setQguid(String qguid) {
        this.qguid = qguid;
    }

    public String getEhisId() {
        return ehisId;
    }

    public void setEhisId(String ehisId) {
        this.ehisId = ehisId;
    }

    public String getOisId() {
        return oisId;
    }

    public void setOisId(String oisId) {
        this.oisId = oisId;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getItemCreator() {
        return itemCreator;
    }

    public void setItemCreator(String itemCreator) {
        this.itemCreator = itemCreator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }
}
