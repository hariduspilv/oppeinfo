package ee.hitsa.ois.web.dto.finalexamprotocol;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.FinalExamProtocolUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class FinalExamVocationalProtocolStudentDto {

    private Long studentId;
    private String fullname;
    private String idcode;
    @ClassifierRestriction(MainClassCode.KUTSEHINDAMINE)
    private String grade;
    @ClassifierRestriction(MainClassCode.OPPURSTAATUS)
    private String status;
    private String studentGroup;
    private List<FinalExamVocationalProtocolCurriculumOccupationDto> curriculumOccupations = new ArrayList<>();
    private Boolean canBeDeleted;
    
    public static FinalExamVocationalProtocolStudentDto of(ProtocolStudent protocolStudent) {
        FinalExamVocationalProtocolStudentDto dto = EntityUtil.bindToDto(protocolStudent, new FinalExamVocationalProtocolStudentDto());
        dto.setStudentId(protocolStudent.getStudent().getId());
        dto.setFullname(PersonUtil.fullname(protocolStudent.getStudent().getPerson()));
        dto.setIdcode(protocolStudent.getStudent().getPerson().getIdcode());
        dto.setStatus(EntityUtil.getCode(protocolStudent.getStudent().getStatus()));
        dto.setStudentGroup(protocolStudent.getStudent().getStudentGroup().getCode());
        dto.setCanBeDeleted(Boolean.valueOf(FinalExamProtocolUtil.studentCanBeDeleted(protocolStudent)));

        return dto;
    }
    
    public Long getStudentId() {
        return studentId;
    }
    
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    
    public String getFullname() {
        return fullname;
    }
    
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    
    public String getIdcode() {
        return idcode;
    }
    
    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }
    
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public List<FinalExamVocationalProtocolCurriculumOccupationDto> getCurriculumOccupations() {
        return curriculumOccupations;
    }

    public void setCurriculumOccupations(List<FinalExamVocationalProtocolCurriculumOccupationDto> curriculumOccupations) {
        this.curriculumOccupations = curriculumOccupations;
    }

    public Boolean getCanBeDeleted() {
        return canBeDeleted;
    }

    public void setCanBeDeleted(Boolean canBeDeleted) {
        this.canBeDeleted = canBeDeleted;
    }
    
}
