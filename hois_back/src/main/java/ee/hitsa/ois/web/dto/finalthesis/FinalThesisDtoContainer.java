package ee.hitsa.ois.web.dto.finalthesis;

public class FinalThesisDtoContainer {

    private FinalThesisDto finalThesis;
    private FinalThesisStudentDto student;

    private Boolean isStudentActive;
    private Boolean finalThesisExpected;
    private Boolean noFinalExamDeclaration;
    private Boolean specialitySet;

    public FinalThesisDto getFinalThesis() {
        return finalThesis;
    }

    public void setFinalThesis(FinalThesisDto finalThesis) {
        this.finalThesis = finalThesis;
    }

    public FinalThesisStudentDto getStudent() {
        return student;
    }

    public void setStudent(FinalThesisStudentDto student) {
        this.student = student;
    }

    public Boolean getIsStudentActive() {
        return isStudentActive;
    }

    public void setIsStudentActive(Boolean isStudentActive) {
        this.isStudentActive = isStudentActive;
    }

    public Boolean getFinalThesisExpected() {
        return finalThesisExpected;
    }

    public void setFinalThesisExpected(Boolean finalThesisExpected) {
        this.finalThesisExpected = finalThesisExpected;
    }

    public Boolean getNoFinalExamDeclaration() {
        return noFinalExamDeclaration;
    }

    public void setNoFinalExamDeclaration(Boolean noFinalExamDeclaration) {
        this.noFinalExamDeclaration = noFinalExamDeclaration;
    }

    public Boolean getSpecialitySet() {
        return specialitySet;
    }

    public void setSpecialitySet(Boolean specialitySet) {
        this.specialitySet = specialitySet;
    }
}
