package ee.hitsa.ois.web.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JournalUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.ProtocolUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class ModuleProtocolStudentDto {

    private Long id;
    private Long studentId;
    private String fullname;
    private String studentGroup;
    private String idcode;
    @ClassifierRestriction(MainClassCode.KUTSEHINDAMINE)
    private String grade;
    @ClassifierRestriction(MainClassCode.OPPURSTAATUS)
    private String status;
    private String addInfo;
    private List<ModuleProtocolJournalResultDto> journalResults = new ArrayList<>();
    private List<ModuleProtocolOutcomeResultDto> outcomeResults = new ArrayList<>();
    private List<ProtocolPracticeJournalResultDto> practiceJournalResults = new ArrayList<>();
    
    /**
     * This variable does not consider user rights, it is checked by ModuleProtocolDto.canBeEdited
     */
    private Boolean canBeDeleted;
    private Boolean canChangeGrade;

    public static ModuleProtocolStudentDto of(ProtocolStudent protocolStudent) {
        ModuleProtocolStudentDto dto = EntityUtil.bindToDto(protocolStudent, new ModuleProtocolStudentDto(),
                "protocolStudentHistories");
        dto.setStudentId(protocolStudent.getStudent().getId());
        if (protocolStudent.getStudent().getStudentGroup() != null) {
            dto.setStudentGroup(protocolStudent.getStudent().getStudentGroup().getCode());
        }
        dto.setFullname(PersonUtil.fullname(protocolStudent.getStudent()));
        dto.setIdcode(protocolStudent.getStudent().getPerson().getIdcode());
        dto.setStatus(EntityUtil.getCode(protocolStudent.getStudent().getStatus()));

        if (protocolStudent.getStudent().getJournalStudents() != null) {
            List<JournalStudent> journalStudents = protocolStudent.getStudent().getJournalStudents();
            for (JournalStudent journalStudent : journalStudents) {
                journalStudent.getJournalEntryStudents().stream()
                        .filter(jes -> JournalEntryType.SISSEKANNE_L.name()
                                .equals(EntityUtil.getCode(jes.getJournalEntry().getEntryType())))
                        .filter(jes -> EntityUtil.getNullableCode(jes.getGrade()) != null)
                        .filter(jes -> JournalUtil.filterJournalEntryStudentsByCurriculumModule(
                                EntityUtil.getId(protocolStudent.getProtocol().getProtocolVdata().getCurriculumVersionOccupationModule().getCurriculumModule()), jes))
                        .forEach(jes -> dto.getJournalResults()
                                .add(new ModuleProtocolJournalResultDto(jes.getJournalEntry().getJournal().getId(),
                                        jes.getJournalEntry().getJournal().getNameEt(),
                                        Integer.valueOf(jes.getJournalEntry().getJournal().getJournalCapacities()
                                                .stream().mapToInt(it -> it.getHours() == null ? 0
                                                        : it.getHours().intValue())
                                                .sum()),
                                        EntityUtil.getCode(jes.getGrade()))));
            }
            
        }

        if (protocolStudent.getStudent().getPracticeJournals() != null) {
            protocolStudent.getStudent().getPracticeJournals().stream()
                .filter(pj -> EntityUtil.getNullableCode(pj.getGrade()) != null)
                .filter(pj -> StreamUtil.nullSafeSet(pj.getModuleSubjects()).stream().filter(r -> r.getModule() != null)
                        .map(r -> EntityUtil.getId(r.getModule().getCurriculumModule())).collect(Collectors.toList())
                        .contains(EntityUtil.getId(protocolStudent.getProtocol().getProtocolVdata().getCurriculumVersionOccupationModule().getCurriculumModule())))
                    .forEach(pj -> dto.getPracticeJournalResults()
                            .add(new ProtocolPracticeJournalResultDto(pj.getId(), EntityUtil.getCode(pj.getGrade()),
                                    pj.getGradeInserted() != null ? pj.getGradeInserted() : pj.getInserted(),
                                    pj.getModuleSubjects())));
        }
        dto.setCanBeDeleted(Boolean.valueOf(ProtocolUtil.studentCanBeDeleted(protocolStudent)));
        dto.setCanChangeGrade(Boolean.valueOf(ProtocolUtil.studentGradeCanBeChanged(protocolStudent)));
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<ModuleProtocolJournalResultDto> getJournalResults() {
        return journalResults;
    }

    public void setJournalResults(List<ModuleProtocolJournalResultDto> journalResults) {
        this.journalResults = journalResults;
    }
    
    public List<ModuleProtocolOutcomeResultDto> getOutcomeResults() {
        return outcomeResults;
    }

    public void setOutcomeResults(List<ModuleProtocolOutcomeResultDto> outcomeResults) {
        this.outcomeResults = outcomeResults;
    }
    
    public List<ProtocolPracticeJournalResultDto> getPracticeJournalResults() {
        return practiceJournalResults;
    }

    public void setPracticeJournalResults(List<ProtocolPracticeJournalResultDto> practiceJournalResults) {
        this.practiceJournalResults = practiceJournalResults;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public Boolean getCanBeDeleted() {
        return canBeDeleted;
    }

    public void setCanBeDeleted(Boolean canBeDeleted) {
        this.canBeDeleted = canBeDeleted;
    }

    public Boolean getCanChangeGrade() {
        return canChangeGrade;
    }

    public void setCanChangeGrade(Boolean canChangeGrade) {
        this.canChangeGrade = canChangeGrade;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }
}
