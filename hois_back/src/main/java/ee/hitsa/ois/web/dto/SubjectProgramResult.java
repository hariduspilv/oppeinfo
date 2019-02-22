package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.StudyPeriod;

public class SubjectProgramResult extends AutocompleteResult {
    
    private String teacherName;
    private AutocompleteResult studyPeriod;
    private String status;

    public SubjectProgramResult() {
        super();
    }
    
    public SubjectProgramResult(Long id, String nameEt, String nameEn) {
        super(id, nameEt, nameEn);
    }

    public SubjectProgramResult(Long id, String nameEt, String nameEn, String teacherName, StudyPeriod studyPeriod, String status) {
        super(id, nameEt, nameEn);
        this.teacherName = teacherName;
        this.studyPeriod = AutocompleteResult.ofWithYear(studyPeriod);
        this.status = status;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(AutocompleteResult studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
