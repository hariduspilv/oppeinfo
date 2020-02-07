package ee.hitsa.ois.web.dto.finalprotocol;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentOccupationCertificate;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.FinalProtocolUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.dto.HigherProtocolStudentDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumGradeDto;

public class FinalHigherProtocolStudentDto extends HigherProtocolStudentDto {

    private String fullname;
    private String idcode;
    @ClassifierRestriction(MainClassCode.OPPURSTAATUS)
    private String status;
    private String studentGroup;
    private CurriculumGradeDto curriculumGrade;
    private List<FinalProtocolStudentOccupationDto> curriculumOccupations = new ArrayList<>();
    private Boolean canBeDeleted;
    
    public static FinalHigherProtocolStudentDto of(ProtocolStudent protocolStudent) {
        FinalHigherProtocolStudentDto dto = EntityUtil.bindToDto(protocolStudent, new FinalHigherProtocolStudentDto());
        Student student = protocolStudent.getStudent();
        dto.setStudentId(EntityUtil.getId(student));
        dto.setFullname(PersonUtil.fullnameOptionalGuest(student));
        dto.setIdcode(student.getPerson().getIdcode());
        dto.setStatus(EntityUtil.getCode(student.getStatus()));
        dto.setStudentGroup(student.getStudentGroup() != null ? student.getStudentGroup().getCode() : null);
        dto.setCurriculumGrade(protocolStudent.getCurriculumGrade() != null ? CurriculumGradeDto.of(protocolStudent.getCurriculumGrade()) : null);
        
        if (protocolStudent.getProtocolStudentOccupations() != null) {
            protocolStudent.getProtocolStudentOccupations().forEach(oc -> {
                StudentOccupationCertificate certificate = oc.getStudentOccupationCertificate();
                dto.getCurriculumOccupations()
                        .add(new FinalProtocolStudentOccupationDto(
                                certificate != null ? certificate.getCertificateNr() : null, 
                                oc.getOccupation().getCode(), null, null,
                                certificate != null ? certificate.getId() : null));
            });
        }
        
        dto.setCanBeDeleted(Boolean.valueOf(FinalProtocolUtil.studentCanBeDeleted(protocolStudent)));

        return dto;
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

    public CurriculumGradeDto getCurriculumGrade() {
        return curriculumGrade;
    }

    public void setCurriculumGrade(CurriculumGradeDto curriculumGrade) {
        this.curriculumGrade = curriculumGrade;
    }
    
    public List<FinalProtocolStudentOccupationDto> getCurriculumOccupations() {
        return curriculumOccupations;
    }

    public void setCurriculumOccupations(List<FinalProtocolStudentOccupationDto> curriculumOccupations) {
        this.curriculumOccupations = curriculumOccupations;
    }

    public Boolean getCanBeDeleted() {
        return canBeDeleted;
    }

    public void setCanBeDeleted(Boolean canBeDeleted) {
        this.canBeDeleted = canBeDeleted;
    }
    
}
