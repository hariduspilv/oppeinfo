package ee.hitsa.ois.web.dto.timetable;

import java.util.ArrayList;
import java.util.List;

public class TimetableCurriculumDto {
    private String nameEt;
    private String nameEn;
    private String curriculumCode;
    private String curriculumStudyLevelCode;
    private List<TimetableStudentGroupDto> groups;
    
    public TimetableCurriculumDto(String nameEt, String nameEn, String curriculumCode, String curriculumStudyLevelCode) {
        this.nameEt = nameEt;
        this.nameEn = nameEn;
        this.curriculumCode = curriculumCode;
        this.curriculumStudyLevelCode = curriculumStudyLevelCode;
        this.groups = new ArrayList<>();
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCurriculumCode() {
        return curriculumCode;
    }

    public void setCurriculumCode(String curriculumCode) {
        this.curriculumCode = curriculumCode;
    }

    public String getCurriculumStudyLevelCode() {
        return curriculumStudyLevelCode;
    }

    public void setCurriculumStudyLevelCode(String curriculumStudyLevelCode) {
        this.curriculumStudyLevelCode = curriculumStudyLevelCode;
    }

    public List<TimetableStudentGroupDto> getGroups() {
        return groups;
    }

    public void setGroups(List<TimetableStudentGroupDto> groups) {
        this.groups = groups;
    }
}
