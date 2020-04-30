package ee.hitsa.ois.web.commandobject.report;

public class StudyDataCommand {
    
    private StudentDataBoolean guestStudent;
    private StudentDataBoolean foreignStudent;
    private StudentDataBoolean cumLaude;
    private StudentData immatDuration;
    private StudentData finishedDuration;
    private StudentDataStringList directiveType;
    private StudentDataStringList directiveReason;
    private StudentData directiveDuration;
    private StudentDataStringList studentGroup;
    private StudentDataStringList studentStatus;
    private StudentDataStringList studyForm;
    private StudentDataStringList studyLoad;
    private StudentDataLongList schoolDepartment;
    private StudentDataLongList curriculum;
    private StudentDataString ehisCode;
    private StudentDataStringList educationLevel;
    private StudentDataStringList speciality;
    //reserved for studyyear
    private StudentDataStringList fin;
    private StudentDataStringList language;
    
    public StudentDataBoolean getGuestStudent() {
        return guestStudent;
    }
    public void setGuestStudent(StudentDataBoolean guestStudent) {
        this.guestStudent = guestStudent;
    }
    public StudentDataBoolean getForeignStudent() {
        return foreignStudent;
    }
    public void setForeignStudent(StudentDataBoolean foreignStudent) {
        this.foreignStudent = foreignStudent;
    }
    public StudentDataBoolean getCumLaude() {
        return cumLaude;
    }
    public void setCumLaude(StudentDataBoolean cumLaude) {
        this.cumLaude = cumLaude;
    }
    public StudentData getImmatDuration() {
        return immatDuration;
    }
    public void setImmatDuration(StudentData immatDuration) {
        this.immatDuration = immatDuration;
    }
    public StudentDataStringList getDirectiveType() {
        return directiveType;
    }
    public void setDirectiveType(StudentDataStringList directiveType) {
        this.directiveType = directiveType;
    }
    public StudentDataStringList getDirectiveReason() {
        return directiveReason;
    }
    public void setDirectiveReason(StudentDataStringList directiveReason) {
        this.directiveReason = directiveReason;
    }
    public StudentData getDirectiveDuration() {
        return directiveDuration;
    }
    public void setDirectiveDuration(StudentData directiveDuration) {
        this.directiveDuration = directiveDuration;
    }
    public StudentDataStringList getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(StudentDataStringList studentGroup) {
        this.studentGroup = studentGroup;
    }
    public StudentDataStringList getStudentStatus() {
        return studentStatus;
    }
    public void setStudentStatus(StudentDataStringList studentStatus) {
        this.studentStatus = studentStatus;
    }
    public StudentDataStringList getStudyForm() {
        return studyForm;
    }
    public void setStudyForm(StudentDataStringList studyForm) {
        this.studyForm = studyForm;
    }
    public StudentDataStringList getStudyLoad() {
        return studyLoad;
    }
    public void setStudyLoad(StudentDataStringList studyLoad) {
        this.studyLoad = studyLoad;
    }
    public StudentDataLongList getSchoolDepartment() {
        return schoolDepartment;
    }
    public void setSchoolDepartment(StudentDataLongList schoolDepartment) {
        this.schoolDepartment = schoolDepartment;
    }
    public StudentDataLongList getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(StudentDataLongList curriculum) {
        this.curriculum = curriculum;
    }
    public StudentDataString getEhisCode() {
        return ehisCode;
    }
    public void setEhisCode(StudentDataString ehisCode) {
        this.ehisCode = ehisCode;
    }
    public StudentDataStringList getEducationLevel() {
        return educationLevel;
    }
    public void setEducationLevel(StudentDataStringList educationLevel) {
        this.educationLevel = educationLevel;
    }
    public StudentDataStringList getSpeciality() {
        return speciality;
    }
    public void setSpeciality(StudentDataStringList speciality) {
        this.speciality = speciality;
    }
    public StudentData getFinishedDuration() {
        return finishedDuration;
    }
    public void setFinishedDuration(StudentData finishedDuration) {
        this.finishedDuration = finishedDuration;
    }
    public StudentDataStringList getFin() {
        return fin;
    }
    public void setFin(StudentDataStringList fin) {
        this.fin = fin;
    }
    public StudentDataStringList getLanguage() {
        return language;
    }
    public void setLanguage(StudentDataStringList language) {
        this.language = language;
    }
    
}
