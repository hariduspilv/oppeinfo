package ee.hitsa.ois.web.commandobject.curriculum;

import ee.hitsa.ois.web.commandobject.SearchCommand;

public class CurriculumAutocompleteCommand extends SearchCommand {

    private Boolean higher;
    private Boolean closed;
    private Long teacher;
    private Integer minSpecialities;

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

    public Integer getMinSpecialities() {
        return minSpecialities;
    }

    public void setMinSpecialities(Integer minSpecialities) {
        this.minSpecialities = minSpecialities;
    }

}
