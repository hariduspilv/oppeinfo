package ee.hitsa.ois.web.dto.finalprotocol;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.domain.FinalThesis;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.FinalProtocolUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ModuleProtocolStudentDto;

public class FinalVocationalProtocolStudentDto extends ModuleProtocolStudentDto {

    private String studentGroup;
    private AutocompleteResult theme;
    private List<FinalProtocolStudentOccupationDto> curriculumOccupations = new ArrayList<>();
    
    public static FinalVocationalProtocolStudentDto of(ProtocolStudent protocolStudent) {
        FinalVocationalProtocolStudentDto dto = EntityUtil.bindToDto(protocolStudent, new FinalVocationalProtocolStudentDto());
        Student student = protocolStudent.getStudent();
        dto.setStudentId(student.getId());
        dto.setFullname(PersonUtil.fullname(student.getPerson()));
        dto.setIdcode(student.getPerson().getIdcode());
        dto.setStatus(EntityUtil.getCode(student.getStatus()));
        dto.setStudentGroup(student.getStudentGroup().getCode());
        dto.setCanBeDeleted(Boolean.valueOf(FinalProtocolUtil.studentCanBeDeleted(protocolStudent)));
        FinalThesis thesis = student.getFinalThesis();
        dto.setTheme(thesis != null ? new AutocompleteResult(student.getFinalThesis().getId(),
                student.getFinalThesis().getThemeEt(), student.getFinalThesis().getThemeEn()) : null);
        
        if (protocolStudent.getProtocolStudentOccupations() != null) {
            protocolStudent.getProtocolStudentOccupations().forEach(oc -> {
                dto.getCurriculumOccupations()
                        .add(new FinalProtocolStudentOccupationDto(
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
    
    public AutocompleteResult getTheme() {
        return theme;
    }

    public void setTheme(AutocompleteResult theme) {
        this.theme = theme;
    }

    public List<FinalProtocolStudentOccupationDto> getCurriculumOccupations() {
        return curriculumOccupations;
    }

    public void setCurriculumOccupations(List<FinalProtocolStudentOccupationDto> curriculumOccupations) {
        this.curriculumOccupations = curriculumOccupations;
    }
    
}
