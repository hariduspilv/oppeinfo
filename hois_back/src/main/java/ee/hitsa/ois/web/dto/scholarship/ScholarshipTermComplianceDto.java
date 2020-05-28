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
    private boolean wagMark;
    private boolean lastPeriodMark;
    private boolean lastPeriodWagMark;
    private boolean curriculumCompletion;
    private boolean absences;
    private boolean fullyComplies;

    public boolean getOnAcademicLeave() {
        return onAcademicLeave;
    }

    public void setOnAcademicLeave(boolean onAcademicLeave) {
        this.onAcademicLeave = onAcademicLeave;
    }

    public boolean getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(boolean studentGroup) {
        this.studentGroup = studentGroup;
    }

    public boolean getAcademicLeaveDirectives() {
        return academicLeaveDirectives;
    }

    public void setAcademicLeaveDirectives(boolean academicLeaveDirectives) {
        this.academicLeaveDirectives = academicLeaveDirectives;
    }

    public boolean getExmatriculationDirectives() {
        return exmatriculationDirectives;
    }

    public void setExmatriculationDirectives(boolean exmatriculationDirectives) {
        this.exmatriculationDirectives = exmatriculationDirectives;
    }

    public boolean getCourse() {
        return course;
    }

    public void setCourse(boolean course) {
        this.course = course;
    }

    public boolean getStudyLoad() {
        return studyLoad;
    }

    public void setStudyLoad(boolean studyLoad) {
        this.studyLoad = studyLoad;
    }

    public boolean getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(boolean studyForm) {
        this.studyForm = studyForm;
    }

    public boolean getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(boolean curriculum) {
        this.curriculum = curriculum;
    }

    public boolean getStudyStartPeriod() {
        return studyStartPeriod;
    }

    public void setStudyStartPeriod(boolean studyStartPeriod) {
        this.studyStartPeriod = studyStartPeriod;
    }

    public boolean getNominalStudyEnd() {
        return nominalStudyEnd;
    }

    public void setNominalStudyEnd(boolean nominalStudyEnd) {
        this.nominalStudyEnd = nominalStudyEnd;
    }

    public boolean getStudyBacklog() {
        return studyBacklog;
    }

    public void setStudyBacklog(boolean studyBacklog) {
        this.studyBacklog = studyBacklog;
    }

    public boolean getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(boolean averageMark) {
        this.averageMark = averageMark;
    }

    public boolean getWagMark() {
        return wagMark;
    }

    public void setWagMark(boolean wagMark) {
        this.wagMark = wagMark;
    }

    public boolean getLastPeriodMark() {
        return lastPeriodMark;
    }

    public void setLastPeriodMark(boolean lastPeriodMark) {
        this.lastPeriodMark = lastPeriodMark;
    }

    public boolean getLastPeriodWagMark() {
        return lastPeriodWagMark;
    }

    public void setLastPeriodWagMark(boolean lastPeriodWagMark) {
        this.lastPeriodWagMark = lastPeriodWagMark;
    }

    public boolean getCurriculumCompletion() {
        return curriculumCompletion;
    }

    public void setCurriculumCompletion(boolean curriculumCompletion) {
        this.curriculumCompletion = curriculumCompletion;
    }

    public boolean getAbsences() {
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
                && !studyBacklog && !averageMark && !wagMark && !lastPeriodMark && !lastPeriodWagMark
                && !curriculumCompletion && !absences;
    }
}
