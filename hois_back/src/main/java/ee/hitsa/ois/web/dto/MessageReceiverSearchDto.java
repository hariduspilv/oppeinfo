package ee.hitsa.ois.web.dto;

public class MessageReceiverSearchDto {
    
    final Long personId;
    final String fullname;
    final Long studentGroup;
    final String idcode;
    
    public MessageReceiverSearchDto(Long personId, String fullname, Long studentGroup, String idcode) {
        this.personId = personId;
        this.fullname = fullname;
        this.studentGroup = studentGroup;
        this.idcode = idcode;
    }

    public Long getPersonId() {
        return personId;
    }

    public String getFullname() {
        return fullname;
    }

    public Long getStudentGroup() {
        return studentGroup;
    }

    public String getIdcode() {
        return idcode;
    }
}
