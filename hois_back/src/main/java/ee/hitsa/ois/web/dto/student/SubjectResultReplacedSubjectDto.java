package ee.hitsa.ois.web.dto.student;

import java.math.BigDecimal;

import ee.hitsa.ois.web.dto.SubjectResult;

public class SubjectResultReplacedSubjectDto {

    private Long id;
    private SubjectResult subject;

    public SubjectResultReplacedSubjectDto(Long id, String nameEt, String nameEn, String code, BigDecimal credits) {
        this.id = id;
        this.subject = new SubjectResult(id, nameEt, nameEn, code, credits);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubjectResult getSubject() {
        return subject;
    }

    public void setSubject(SubjectResult subject) {
        this.subject = subject;
    }

}
