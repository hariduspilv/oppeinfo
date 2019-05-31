package ee.hitsa.ois.web.dto.student;

import java.time.LocalDate;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierDto;

public class StudentCardSearchDto {

    private Long studentId;
    private String fullname;
    private AutocompleteResult studentGroup;
    private Boolean pictureExists;
    private Boolean isStudentActive;
    private String cardNr;
    private ClassifierDto status;
    private LocalDate validThru;
    private Boolean given;
    private LocalDate givenDate;
    private Boolean returned;
    private LocalDate returnedDate;
    private Boolean isStudentCardRepetitive;
    
    public static StudentCardSearchDto of(Student student) {
        StudentCardSearchDto dto = new StudentCardSearchDto();
        dto.setStudentId(student.getId());
        dto.setFullname(student.getPerson().getFullname());
        dto.setStudentGroup(student.getStudentGroup() == null ? null : AutocompleteResult.of(student.getStudentGroup()));
        dto.setPictureExists(Boolean.valueOf(student.getPhoto() != null));
        dto.setIsStudentActive(Boolean.valueOf(StudentUtil.isActive(student)));
        dto.setCardNr(student.getStudentCard());
        dto.setStatus(student.getStudentCardStatus() == null ? null : ClassifierDto.of(student.getStudentCardStatus()));
        dto.setValidThru(student.getStudentCardValidThru());
        dto.setGiven(student.getIsStudentCardGiven());
        dto.setGivenDate(student.getStudentCardGivenDt());
        dto.setReturned(student.getIsStudentCardReturned());
        dto.setReturnedDate(student.getStudentCardReturnedDt());
        dto.setIsStudentCardRepetitive(student.getIsStudentCardRepetitive());
        return dto;
    }

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

    public AutocompleteResult getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(AutocompleteResult studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Boolean getPictureExists() {
        return pictureExists;
    }

    public void setPictureExists(Boolean pictureExists) {
        this.pictureExists = pictureExists;
    }

    public Boolean getIsStudentActive() {
        return isStudentActive;
    }

    public void setIsStudentActive(Boolean isStudentActive) {
        this.isStudentActive = isStudentActive;
    }

    public String getCardNr() {
        return cardNr;
    }

    public void setCardNr(String cardNr) {
        this.cardNr = cardNr;
    }

    public ClassifierDto getStatus() {
        return status;
    }

    public void setStatus(ClassifierDto status) {
        this.status = status;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }

    public Boolean getGiven() {
        return given;
    }

    public void setGiven(Boolean given) {
        this.given = given;
    }

    public LocalDate getGivenDate() {
        return givenDate;
    }

    public void setGivenDate(LocalDate givenDate) {
        this.givenDate = givenDate;
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    public Boolean getIsStudentCardRepetitive() {
        return isStudentCardRepetitive;
    }

    public void setIsStudentCardRepetitive(Boolean isStudentCardRepetitive) {
        this.isStudentCardRepetitive = isStudentCardRepetitive;
    }

}