package ee.hitsa.ois.web.dto.poll;

import java.time.LocalDate;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class PracticeThemesDto extends ThemesDto {
    
    private AutocompleteResult school;
    private String student;
    private String enterprise;
    private LocalDate practiceFrom;
    private LocalDate practiceThru;
    private AutocompleteResult studentGroup;
    
    public PracticeThemesDto(ThemesDto dto) {
        super(dto.getThemes(), dto.getConfirmed(), dto.getForeword(), dto.getAfterword());
        this.setIsThemePageable(dto.getIsThemePageable());
        this.setType(dto.getType());
        this.setResponseId(dto.getResponseId());
    }

    public AutocompleteResult getSchool() {
        return school;
    }

    public void setSchool(AutocompleteResult school) {
        this.school = school;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public LocalDate getPracticeFrom() {
        return practiceFrom;
    }

    public void setPracticeFrom(LocalDate practiceFrom) {
        this.practiceFrom = practiceFrom;
    }

    public LocalDate getPracticeThru() {
        return practiceThru;
    }

    public void setPracticeThru(LocalDate practiceThru) {
        this.practiceThru = practiceThru;
    }

    public AutocompleteResult getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(AutocompleteResult studentGroup) {
        this.studentGroup = studentGroup;
    }

}
