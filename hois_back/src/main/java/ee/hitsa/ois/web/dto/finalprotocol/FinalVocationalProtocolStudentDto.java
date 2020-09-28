package ee.hitsa.ois.web.dto.finalprotocol;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.domain.FinalThesis;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentOccupationCertificate;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.FinalProtocolUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ModuleProtocolStudentDto;

public class FinalVocationalProtocolStudentDto extends ModuleProtocolStudentDto {

    private AutocompleteResult theme;
    private List<FinalProtocolStudentOccupationDto> curriculumOccupations = new ArrayList<>();
    
    public static FinalVocationalProtocolStudentDto of(ProtocolStudent protocolStudent) {
        FinalVocationalProtocolStudentDto dto = EntityUtil.bindToDto(protocolStudent, new FinalVocationalProtocolStudentDto());
        Student student = protocolStudent.getStudent();
        dto.setStudentId(student.getId());
        dto.setFullname(PersonUtil.fullname(student));
        dto.setIdcode(student.getPerson().getIdcode());
        dto.setStatus(EntityUtil.getCode(student.getStatus()));
        dto.setStudentGroup(student.getStudentGroup() != null ? student.getStudentGroup().getCode() : null);
        dto.setCanBeDeleted(Boolean.valueOf(FinalProtocolUtil.studentCanBeDeleted(protocolStudent)));
        FinalThesis thesis = !student.getFinalThesis().isEmpty() ? student.getFinalThesis().get(0) : null;
        dto.setTheme(thesis != null ? new AutocompleteResult(thesis.getId(), thesis.getThemeEt(), thesis.getThemeEn())
                : null);

        if (protocolStudent.getProtocolStudentOccupations() != null) {
            protocolStudent.getProtocolStudentOccupations().forEach(oc -> {
                StudentOccupationCertificate certificate = oc.getStudentOccupationCertificate();
                dto.getCurriculumOccupations()
                        .add(new FinalProtocolStudentOccupationDto(
                                certificate != null ? certificate.getCertificateNr() : null,
                                EntityUtil.getNullableCode(oc.getOccupation()),
                                EntityUtil.getNullableCode(oc.getPartOccupation()),
                                certificate != null ? EntityUtil.getNullableCode(certificate.getSpeciality()) : null,
                                certificate != null ? certificate.getId() : null));
            });
        }

        return dto;
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
