package ee.hitsa.ois.web.dto.scholarship;

public class ScholarshipTermComplianceDto {

    private boolean onAcademicLeave;
    private boolean studentGroup;
    private boolean academicLeaveDirectives;
    private boolean exmatriculationDirectives;
    private boolean course;
    private boolean studyLoad;
    private boolean studyForm;
    private boolean curriculum;
    private boolean studyStartPeriod;
    private boolean nominalStudyEnd;
    private boolean studyBacklog;
    private boolean averageMark;
    private boolean lastPeriodMark;
    private boolean curriculumCompletion;
    private boolean absences;
    private boolean fullyComplies;

    public boolean isOnAcademicLeave() {
        return onAcademicLeave;
    }

    public void setOnAcademicLeave(boolean onAcademicLeave) {
        this.onAcademicLeave = onAcademicLeave;
    }

    public boolean isStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(boolean studentGroup) {
        this.studentGroup = studentGroup;
    }

    public boolean isAcademicLeaveDirectives() {
        return academicLeaveDirectives;
    }

    public void setAcademicLeaveDirectives(boolean academicLeaveDirectives) {
        this.academicLeaveDirectives = academicLeaveDirectives;
    }

    public boolean isExmatriculationDirectives() {
        return exmatriculationDirectives;
    }

    public void setExmatriculationDirectives(boolean exmatriculationDirectives) {
        this.exmatriculationDirectives = exmatriculationDirectives;
    }

    public boolean getIsCourse() {
        return course;
    }

    public void setCourse(boolean course) {
        this.course = course;
    }

    public boolean getIsStudyLoad() {
        return studyLoad;
    }

    public void setStudyLoad(boolean studyLoad) {
        this.studyLoad = studyLoad;
    }

    public boolean getIsStudyForm() {
        return studyForm;
    }

    public void setStudyForm(boolean studyForm) {
        this.studyForm = studyForm;
    }

    public boolean getIsCurriculum() {
        return curriculum;
    }

    public void setCurriculum(boolean curriculum) {
        this.curriculum = curriculum;
    }

    public boolean getIsStudyStartPeriod() {
        return studyStartPeriod;
    }

    public void setStudyStartPeriod(boolean studyStartPeriod) {
        this.studyStartPeriod = studyStartPeriod;
    }

    public boolean getIsNominalStudyEnd() {
        return nominalStudyEnd;
    }

    public void setNominalStudyEnd(boolean nominalStudyEnd) {
        this.nominalStudyEnd = nominalStudyEnd;
    }

    public boolean isStudyBacklog() {
        return studyBacklog;
    }

    public void setStudyBacklog(boolean studyBacklog) {
        this.studyBacklog = studyBacklog;
    }

    public boolean isAverageMark() {
        return averageMark;
    }

    public void setAverageMark(boolean averageMark) {
        this.averageMark = averageMark;
    }

    public boolean getIsLastPeriodMark() {
        return lastPeriodMark;
    }

    public void setLastPeriodMark(boolean lastPeriodMark) {
        this.lastPeriodMark = lastPeriodMark;
    }

    public boolean getIsCurriculumCompletion() {
        return curriculumCompletion;
    }

    public void setCurriculumCompletion(boolean curriculumCompletion) {
        this.curriculumCompletion = curriculumCompletion;
    }

    public boolean getIsAbsences() {
        return absences;
    }

    public void setAbsences(boolean absences) {
        this.absences = absences;
    }

    public boolean getFullyComplies() {
        return fullyComplies;
    }

    public void setFullyComplies() {
        this.fullyComplies = !onAcademicLeave && !studentGroup && !academicLeaveDirectives && !exmatriculationDirectives
                && !course && !studyLoad && !studyForm && !curriculum && !studyStartPeriod && !nominalStudyEnd
                && !studyBacklog && !averageMark && !lastPeriodMark && !curriculumCompletion && !absences;
    }
}
