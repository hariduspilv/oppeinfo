package ee.hitsa.ois.web.dto;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JournalUtil;
import ee.hitsa.ois.util.ModuleProtocolUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class ModuleProtocolStudentDto {

    private Long id;
    private Long studentId;
    private String fullname;
    private String idcode;
    @ClassifierRestriction(MainClassCode.KUTSEHINDAMINE)
    private String grade;
    @ClassifierRestriction(MainClassCode.OPPURSTAATUS)
    private String status;
    private List<ModuleProtocolJournalResultDto> journalResults = new ArrayList<>();
    /**
     * This variable does not consider user rights, it is checked by ModuleProtocolDto.canBeEdited
     */
    private Boolean canBeDeleted;

    public static ModuleProtocolStudentDto of(ProtocolStudent protocolStudent) {
        ModuleProtocolStudentDto dto = EntityUtil.bindToDto(protocolStudent, new ModuleProtocolStudentDto(),
                "protocolStudentHistories");
        dto.setStudentId(protocolStudent.getStudent().getId());
        dto.setFullname(PersonUtil.fullname(protocolStudent.getStudent().getPerson()));
        dto.setIdcode(protocolStudent.getStudent().getPerson().getIdcode());
        dto.setStatus(EntityUtil.getCode(protocolStudent.getStudent().getStatus()));

        if (protocolStudent.getStudent().getJournalStudents() != null) {
            for (JournalStudent journalStudent : protocolStudent.getStudent().getJournalStudents()) {
                journalStudent.getJournalEntryStudents().stream()
                        .filter(jes -> JournalEntryType.SISSEKANNE_L.name()
                                .equals(EntityUtil.getCode(jes.getJournalEntry().getEntryType())))
                        .filter(jes -> EntityUtil.getId(jes.getJournalEntry().getJournal().getStudyYear())
                                .equals(EntityUtil.getId(protocolStudent.getProtocol().getProtocolVdata().getStudyYear())))
                        .filter(jes -> EntityUtil.getNullableCode(jes.getGrade()) != null)
                        .filter(jes -> JournalUtil.filterJournalEntryStudentsByOccupationalModule(protocolStudent.getProtocol().getProtocolVdata().getCurriculumVersionOccupationModule(), jes))
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
        dto.setCanBeDeleted(Boolean.valueOf(ModuleProtocolUtil.studentCanBeDeleted(protocolStudent)));
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getCanBeDeleted() {
        return canBeDeleted;
    }

    public void setCanBeDeleted(Boolean canBeDeleted) {
        this.canBeDeleted = canBeDeleted;
    }
}
