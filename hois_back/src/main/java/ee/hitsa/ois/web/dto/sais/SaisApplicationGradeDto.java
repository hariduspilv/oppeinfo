package ee.hitsa.ois.web.dto.sais;

import ee.hitsa.ois.domain.sais.SaisApplicationGrade;
import ee.hitsa.ois.util.EntityUtil;

public class SaisApplicationGradeDto {

    private Long id;
    private String subjectName;
    private String subjectType;
    private String grade;

    public static SaisApplicationGradeDto of(SaisApplicationGrade saisApplicationGrade) {
        SaisApplicationGradeDto dto = EntityUtil.bindToDto(saisApplicationGrade, new SaisApplicationGradeDto());
        return dto;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

}
