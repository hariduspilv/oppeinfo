package ee.hitsa.ois.web.dto.finalexamprotocol;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.OisFileViewDto;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.CommitteeDto;
import ee.hitsa.ois.web.dto.StudyPeriodDto;
import ee.hitsa.ois.web.dto.SubjectDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumGradeDto;

public class FinalExamHigherProtocolDto extends VersionedCommand {

    private Long id;
    private StudyPeriodDto studyPeriod;
    private SubjectDto subject;
    private List<String> teachers;
    private String status;
    private String studyLevel;
    private String protocolNr;
    private LocalDate confirmed;
    private String confirmedBy;
    private LocalDateTime inserted;
    private List<FinalExamHigherProtocolStudentDto> protocolStudents = new ArrayList<>();
    private OisFileViewDto oisFile;
    private LocalDate finalDate;
    private CommitteeDto committee;
    private List<Long> presentCommitteeMembers;
    private List<FinalExamProtocolOccupationDto> occupations = new ArrayList<>();
    private List<CurriculumGradeDto> curriculumGrades = new ArrayList<>();
    
    private Boolean canBeEdited;
    private Boolean canBeDeleted;
    
    public static FinalExamHigherProtocolDto of(Protocol protocol) {
        FinalExamHigherProtocolDto dto = EntityUtil.bindToDto(protocol, new FinalExamHigherProtocolDto());
        dto.setProtocolStudents(StreamUtil.toMappedList(FinalExamHigherProtocolStudentDto::of, protocol.getProtocolStudents()));
        
        SubjectStudyPeriod ssp = protocol.getProtocolHdata().getSubjectStudyPeriod();
        dto.setStudyPeriod(StudyPeriodDto.of(ssp.getStudyPeriod()));
        dto.setSubject(SubjectDto.of(ssp.getSubject(), null));
        dto.setTeacher(StreamUtil.toMappedList(sspt -> PersonUtil.fullname(sspt.getTeacher().getPerson()), ssp.getTeachers()));
        
        if (protocol.getCommittee() != null) {
            dto.setCommittee(CommitteeDto.of(protocol.getCommittee()));
        }
        if (protocol.getProtocolCommitteeMembers() != null) {
            dto.setPresentCommitteeMembers(protocol.getProtocolCommitteeMembers().stream().map(cm -> cm.getCommitteeMember().getId()).collect(Collectors.toList()));
        }
        
        Curriculum curriculum = protocol.getProtocolHdata().getCurriculum();
        dto.setStudyLevel(EntityUtil.getCode(curriculum.getOrigStudyLevel()));
        curriculum.getSpecialities().stream().filter(s -> s.getOccupation() != null).forEach(s -> {
            dto.getOccupations().add(new FinalExamProtocolOccupationDto(null, EntityUtil.getCode(s.getOccupation()), s.getOccupation().getNameEt(), s.getOccupation().getNameEn()));
        });
        dto.setCurriculumGrades(StreamUtil.toMappedList(g -> CurriculumGradeDto.of(g), curriculum.getGrades()));

        if (protocol.getOisFile() != null) {
            dto.setOisFile(EntityUtil.bindToDto(protocol.getOisFile(), new OisFileViewDto()));
        }
        dto.setConfirmedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(protocol.getConfirmer()));
        
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudyPeriodDto getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(StudyPeriodDto studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeacher(List<String> teachers) {
        this.teachers = teachers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(String studyLevel) {
        this.studyLevel = studyLevel;
    }

    public String getProtocolNr() {
        return protocolNr;
    }

    public void setProtocolNr(String protocolNr) {
        this.protocolNr = protocolNr;
    }

    public LocalDate getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(LocalDate confirmed) {
        this.confirmed = confirmed;
    }

    public String getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }

    public List<FinalExamHigherProtocolStudentDto> getProtocolStudents() {
        return protocolStudents;
    }

    public void setProtocolStudents(List<FinalExamHigherProtocolStudentDto> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }
    
    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }

    public OisFileViewDto getOisFile() {
        return oisFile;
    }

    public void setOisFile(OisFileViewDto oisFile) {
        this.oisFile = oisFile;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }

    public CommitteeDto getCommittee() {
        return committee;
    }

    public void setCommittee(CommitteeDto committee) {
        this.committee = committee;
    }

    public List<Long> getPresentCommitteeMembers() {
        return presentCommitteeMembers;
    }

    public void setPresentCommitteeMembers(List<Long> presentCommitteeMembers) {
        this.presentCommitteeMembers = presentCommitteeMembers;
    }
    
    public List<FinalExamProtocolOccupationDto> getOccupations() {
        return occupations;
    }

    public void setOccupations(List<FinalExamProtocolOccupationDto> occupations) {
        this.occupations = occupations;
    }
    
    public List<CurriculumGradeDto> getCurriculumGrades() {
        return curriculumGrades;
    }

    public void setCurriculumGrades(List<CurriculumGradeDto> curriculumGrades) {
        this.curriculumGrades = curriculumGrades;
    }

    public Boolean getCanBeEdited() {
        return canBeEdited;
    }

    public void setCanBeEdited(Boolean canBeEdited) {
        this.canBeEdited = canBeEdited;
    }

    public Boolean getCanBeDeleted() {
        return canBeDeleted;
    }

    public void setCanBeDeleted(Boolean canBeDeleted) {
        this.canBeDeleted = canBeDeleted;
    }
    
}
