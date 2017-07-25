package ee.hitsa.ois.web.dto.timetable;

import java.util.ArrayList;
import java.util.List;

public class JournalForTimetableDto {
    private String nameEt;
    private String nameEn;
    private String curriculumCode;
    private String curriculumStudyLevelCode;
    private List<StudentGroupForTimetableDto> groups;
    
    public JournalForTimetableDto(String nameEt, String nameEn, String curriculumCode, String curriculumStudyLevelCode) {
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

    public List<StudentGroupForTimetableDto> getGroups() {
        return groups;
    }

    public void setGroups(List<StudentGroupForTimetableDto> groups) {
        this.groups = groups;
    }
}
