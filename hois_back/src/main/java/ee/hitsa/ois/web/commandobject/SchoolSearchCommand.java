package ee.hitsa.ois.web.commandobject;

public class SchoolSearchCommand extends SearchCommand {

    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
