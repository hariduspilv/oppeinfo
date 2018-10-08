package ee.hitsa.ois.web.dto;

import java.util.Arrays;
import java.util.List;

import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

public class MessageReceiverDto extends StudentSearchDto {
    
    public static MessageReceiverDto of(StudentSearchDto studentSearchDto) {
        MessageReceiverDto dto = new MessageReceiverDto();
        dto.setId(studentSearchDto.getId());
        dto.setFullname(studentSearchDto.getFullname());
        dto.setIdcode(studentSearchDto.getIdcode());
        dto.setPersonId(studentSearchDto.getPersonId());
        dto.setStudyForm(studentSearchDto.getStudyForm());
        dto.setStatus(studentSearchDto.getStatus());
        dto.setCurriculum(studentSearchDto.getCurriculum());
        dto.setStudentGroup(studentSearchDto.getStudentGroup());
        dto.setCurriculumVersion(studentSearchDto.getCurriculumVersion());
        dto.setRole(Arrays.asList(Role.ROLL_T.name()));
        dto.setJournal(studentSearchDto.getJournal());
        dto.setSubject(studentSearchDto.getSubject());
        return dto;
    }
    
    private List<String> role;

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }
}
