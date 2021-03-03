package ee.hitsa.ois.web.commandobject.teacher;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class TeacherOtherLoadForm {

    private Long id;
    @NotBlank
    @Size(max = 4000)
    private String nameEt;
    @NotNull
    @Size(max = 999999)
    private BigDecimal hours;
    @NotNull
    @Size(max = 999)
    private BigDecimal percent;
    @NotNull
    private Long studyYear;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public BigDecimal getHours() {
        return hours;
    }

    public void setHours(BigDecimal hours) {
        this.hours = hours;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }
}
