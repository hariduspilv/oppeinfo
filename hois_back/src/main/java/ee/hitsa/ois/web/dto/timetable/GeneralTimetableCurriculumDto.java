package ee.hitsa.ois.web.dto.timetable;

public class GeneralTimetableCurriculumDto {
    private final String studentGroupCode;
    private final String curriculumCode;
    private final String nameEt;
    private final String nameEn;
    
    public GeneralTimetableCurriculumDto(Object[] row) {
        this.studentGroupCode = (String) row[0];
        this.curriculumCode = (String) row[1];
        this.nameEt = (String) row[2];
        this.nameEn = (String) row[3];
    }

    public String getStudentGroupCode() {
        return studentGroupCode;
    }
    
    public String getcurriculumCode() {
        return curriculumCode;
    }

    public String getNameEt() {
        return nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }
    
}
