package ee.hitsa.ois.web.dto.boardingschool;

import java.time.LocalDate;

public class BoardingSchoolResidentDto {

    private Long dormitory;
    private Long student;
    private String fullname;
    private String idcode;
    private Long room;
    private LocalDate validFrom;
    private LocalDate validThru;

    public BoardingSchoolResidentDto() {
    }

    public BoardingSchoolResidentDto(Long dormitory, Long student, String fullname, String idcode, Long room,
            LocalDate validFrom, LocalDate validThru) {
        this.dormitory = dormitory;
        this.student = student;
        this.fullname = fullname;
        this.idcode = idcode;
        this.room = room;
        this.validFrom = validFrom;
        this.validThru = validThru;
    }

    public Long getDormitory() {
        return dormitory;
    }

    public void setDormitory(Long dormitory) {
        this.dormitory = dormitory;
    }

    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
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

    public Long getRoom() {
        return room;
    }

    public void setRoom(Long room) {
        this.room = room;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }

}
