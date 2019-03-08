package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;

import ee.hitsa.ois.domain.PracticeJournalFile;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.OisFileViewDto;

public class PracticeJournalFileDto {

    private Long id;
    private OisFileViewDto oisFile;
    private LocalDateTime inserted;
    private String insertedBy;
    private Boolean isStudent;

    public static PracticeJournalFileDto of(PracticeJournalFile practiceJournalFile) {
        PracticeJournalFileDto dto = EntityUtil.bindToDto(practiceJournalFile, new PracticeJournalFileDto(), "oisFile");
        dto.setOisFile(OisFileViewDto.of(practiceJournalFile.getOisFile()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OisFileViewDto getOisFile() {
        return oisFile;
    }

    public void setOisFile(OisFileViewDto oisFile) {
        this.oisFile = oisFile;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }

    public String getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }

    public Boolean getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(Boolean isStudent) {
        this.isStudent = isStudent;
    }

}
