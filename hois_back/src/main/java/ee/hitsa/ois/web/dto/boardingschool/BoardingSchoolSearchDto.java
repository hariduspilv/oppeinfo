package ee.hitsa.ois.web.dto.boardingschool;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class BoardingSchoolSearchDto {

    private Long id;
    private Long student;
    private String fullname;
    private AutocompleteResult studentGroup;
    private String idcode;
    private LocalDate validFrom;
    private LocalDate validThru;
    private AutocompleteResult room;
    private List<BoardingSchoolResidentDto> neighbours = new ArrayList<>();
    private String neighboursXls;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AutocompleteResult getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(AutocompleteResult studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
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

    public AutocompleteResult getRoom() {
        return room;
    }

    public void setRoom(AutocompleteResult room) {
        this.room = room;
    }

    public List<BoardingSchoolResidentDto> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<BoardingSchoolResidentDto> neighbours) {
        this.neighbours = neighbours;
    }

    public String getNeighboursXls() {
        return neighboursXls;
    }

    public void setNeighboursXls(String neighboursXls) {
        this.neighboursXls = neighboursXls;
    }

}
