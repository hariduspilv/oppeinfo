package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.PracticeJournalEntry;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class PracticeJournalEntryDto extends VersionedCommand {

    private Long id;
    private String description;
    private LocalDate practiceDate;
    private Integer hours;
    private String supervisorComment;
    private String teacherComment;
    private Boolean isStudentEntry;

    public static PracticeJournalEntryDto of(PracticeJournalEntry practiceJournalEntry) {
        PracticeJournalEntryDto dto = EntityUtil.bindToDto(practiceJournalEntry, new PracticeJournalEntryDto());
        dto.setIsStudentEntry(Boolean.valueOf(StringUtils.isEmpty(practiceJournalEntry.getSupervisorComment())
                && StringUtils.isEmpty(practiceJournalEntry.getTeacherComment())));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPracticeDate() {
        return practiceDate;
    }

    public void setPracticeDate(LocalDate practiceDate) {
        this.practiceDate = practiceDate;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public String getSupervisorComment() {
        return supervisorComment;
    }

    public void setSupervisorComment(String supervisorComment) {
        this.supervisorComment = supervisorComment;
    }

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }

    public Boolean getIsStudentEntry() {
        return isStudentEntry;
    }

    public void setIsStudentEntry(Boolean isStudentEntry) {
        this.isStudentEntry = isStudentEntry;
    }

}
