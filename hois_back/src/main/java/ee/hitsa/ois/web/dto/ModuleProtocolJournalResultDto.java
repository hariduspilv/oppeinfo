package ee.hitsa.ois.web.dto;

public class ModuleProtocolJournalResultDto {

    private Long journalId;
    private String nameEt;
    private Integer capacity;

    private GradeDto grade;

    public ModuleProtocolJournalResultDto() {

    }

    public ModuleProtocolJournalResultDto(Long journalId, String nameEt, Integer capacity, String gradeCode,
            Long gradingSchemaRowId) {
        this.journalId = journalId;
        this.nameEt = nameEt;
        this.capacity = capacity;
        this.grade = new GradeDto(gradeCode, gradingSchemaRowId);
    }

    public Long getJournalId() {
        return journalId;
    }

    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public GradeDto getGrade() {
        return grade;
    }

    public void setGrade(GradeDto grade) {
        this.grade = grade;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

}
