package ee.hitsa.ois.web.commandobject.finalprotocol;

import ee.hitsa.ois.web.commandobject.ProtocolStudentSaveForm;

public class FinalHigherProtocolStudentSaveForm extends ProtocolStudentSaveForm {

    private String occupationCode;
    private Long curriculumGradeId;

    public String getOccupationCode() {
        return occupationCode;
    }

    public void setOccupationCode(String occupationCode) {
        this.occupationCode = occupationCode;
    }

    public Long getCurriculumGradeId() {
        return curriculumGradeId;
    }

    public void setCurriculumGradeId(Long curriculumGradeId) {
        this.curriculumGradeId = curriculumGradeId;
    }

}
