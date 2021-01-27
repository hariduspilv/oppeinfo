package ee.hitsa.ois.web.dto.timetable;

public class SchoolPublicDataSettingsDto {

    private Boolean isAcademicCalendarNotPublic;
    private Boolean isTimetableNotPublic;
    private Boolean isCurriculumNotPublic;
    private Boolean isSubjectNotPublic;
    private Boolean higher;

    public Boolean getIsAcademicCalendarNotPublic() {
        return isAcademicCalendarNotPublic;
    }

    public void setIsAcademicCalendarNotPublic(Boolean isAcademicCalendarNotPublic) {
        this.isAcademicCalendarNotPublic = isAcademicCalendarNotPublic;
    }

    public Boolean getIsTimetableNotPublic() {
        return isTimetableNotPublic;
    }

    public void setIsTimetableNotPublic(Boolean isTimetableNotPublic) {
        this.isTimetableNotPublic = isTimetableNotPublic;
    }

    public Boolean getIsCurriculumNotPublic() {
        return isCurriculumNotPublic;
    }

    public void setIsCurriculumNotPublic(Boolean isCurriculumNotPublic) {
        this.isCurriculumNotPublic = isCurriculumNotPublic;
    }

    public Boolean getIsSubjectNotPublic() {
        return isSubjectNotPublic;
    }

    public void setIsSubjectNotPublic(Boolean isSubjectNotPublic) {
        this.isSubjectNotPublic = isSubjectNotPublic;
    }

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }
}
