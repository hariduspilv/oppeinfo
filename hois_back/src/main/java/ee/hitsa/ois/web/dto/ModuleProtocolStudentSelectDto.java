package ee.hitsa.ois.web.dto;

import java.util.ArrayList;
import java.util.List;

public class ModuleProtocolStudentSelectDto {

    private Long studentId;
    private String fullname;
    private String idcode;
    private String status;
    private List<String> journalResults = new ArrayList<>();

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getJournalResults() {
        return journalResults;
    }

    public void setJournalResults(List<String> journalResults) {
        this.journalResults = journalResults;
    }

}
