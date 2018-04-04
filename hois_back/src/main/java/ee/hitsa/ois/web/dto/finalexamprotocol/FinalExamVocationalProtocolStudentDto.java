package ee.hitsa.ois.web.dto.finalexamprotocol;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.FinalExamProtocolUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.ModuleProtocolStudentDto;

public class FinalExamVocationalProtocolStudentDto extends ModuleProtocolStudentDto {

    private String studentGroup;
    private List<FinalExamVocationalProtocolStudentOccupationDto> curriculumOccupations = new ArrayList<>();
    
    public static FinalExamVocationalProtocolStudentDto of(ProtocolStudent protocolStudent) {
        FinalExamVocationalProtocolStudentDto dto = EntityUtil.bindToDto(protocolStudent, new FinalExamVocationalProtocolStudentDto());
        dto.setStudentId(protocolStudent.getStudent().getId());
        dto.setFullname(PersonUtil.fullname(protocolStudent.getStudent().getPerson()));
        dto.setIdcode(protocolStudent.getStudent().getPerson().getIdcode());
        dto.setStatus(EntityUtil.getCode(protocolStudent.getStudent().getStatus()));
        dto.setStudentGroup(protocolStudent.getStudent().getStudentGroup().getCode());
        dto.setCanBeDeleted(Boolean.valueOf(FinalExamProtocolUtil.studentCanBeDeleted(protocolStudent)));
        
        if (protocolStudent.getProtocolStudentOccupations() != null) {
            protocolStudent.getProtocolStudentOccupations().forEach(oc -> {
                dto.getCurriculumOccupations()
                        .add(new FinalExamVocationalProtocolStudentOccupationDto(
                                oc.getStudentOccupationCertificate() != null ? oc.getStudentOccupationCertificate().getCertificateNr() : null, 
                                oc.getOccupation().getCode(),
                                oc.getPartOccupation() != null ? oc.getPartOccupation().getCode() : null,
                                oc.getStudentOccupationCertificate() != null ? oc.getStudentOccupationCertificate().getId() : null));
            });
        }

        return dto;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public List<FinalExamVocationalProtocolStudentOccupationDto> getCurriculumOccupations() {
        return curriculumOccupations;
    }

    public void setCurriculumOccupations(List<FinalExamVocationalProtocolStudentOccupationDto> curriculumOccupations) {
        this.curriculumOccupations = curriculumOccupations;
    }
    
}
