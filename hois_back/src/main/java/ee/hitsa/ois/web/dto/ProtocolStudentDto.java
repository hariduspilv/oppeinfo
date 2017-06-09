package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class ProtocolStudentDto {

    private Long id;
    private Long studentId;
    private String fullname;
    private String idcode;
    private String gradeValue;
    @ClassifierRestriction(MainClassCode.KUTSEHINDAMINE)
    private String grade;
    private Integer gradeMark;
    private LocalDate gradeDate;
    private String addInfo;
    private List<ProtocolStudentHistoryDto> protocolStudentHistories;

    public static ProtocolStudentDto of(ProtocolStudent protocolStudent) {
        ProtocolStudentDto dto = EntityUtil.bindToDto(protocolStudent, new ProtocolStudentDto(), "protocolStudentHistories");
        dto.setStudentId(protocolStudent.getStudent().getId());
        dto.setFullname(PersonUtil.fullname(protocolStudent.getStudent().getPerson()));
        dto.setIdcode(protocolStudent.getStudent().getPerson().getIdcode());
        if (!CollectionUtils.isEmpty(protocolStudent.getProtocolStudentHistories())) {
            dto.setProtocolStudentHistories(protocolStudent.getProtocolStudentHistories().stream().map(ProtocolStudentHistoryDto::of).collect(Collectors.toList()));
        }
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getGradeMark() {
        return gradeMark;
    }

    public void setGradeMark(Integer gradeMark) {
        this.gradeMark = gradeMark;
    }

    public LocalDate getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public List<ProtocolStudentHistoryDto> getProtocolStudentHistories() {
        return protocolStudentHistories;
    }

    public void setProtocolStudentHistories(List<ProtocolStudentHistoryDto> protocolStudentHistories) {
        this.protocolStudentHistories = protocolStudentHistories;
    }

}
