package ee.hitsa.ois.web.commandobject.finalprotocol;

import ee.hitsa.ois.web.dto.HigherProtocolStudentDto;

public class FinalHigherProtocolStudentSaveForm extends HigherProtocolStudentDto {

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
