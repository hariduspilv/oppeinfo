package ee.hitsa.ois.web.dto;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.student.StudentSupportService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.commandobject.OisFileViewDto;

public class StudentSupportServiceDto {

    private Long id;
    private LocalDate entryDate;
    private String nameEt;
    private String content;
    private String validity;
    private Boolean isPublic;
    private OisFileViewDto file;
    private String entrySubmitter;
    
    /** FALSE - real StudentSupportService. TRUE - any other support service */
    private Boolean isArtificial = Boolean.FALSE;
    
    private Long endingDirectiveId;
    
    public StudentSupportServiceDto() { }
    
    public StudentSupportServiceDto(Object record, OisFile oisFile) {
        id = resultAsLong(record, 0);
        entryDate = resultAsLocalDate(record, 1);
        nameEt = resultAsString(record, 2);
        content = resultAsString(record, 3);
        validity = resultAsString(record, 4);
        isPublic = resultAsBoolean(record, 5);
        if (oisFile != null) {
            file = OisFileViewDto.of(oisFile);
        }
        isArtificial = resultAsBoolean(record, 8);
        entrySubmitter = Boolean.FALSE.equals(isArtificial) ? PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(record, 7)) : resultAsString(record, 7);
        
        endingDirectiveId = resultAsLong(record, 9);
    }
    
    public static StudentSupportServiceDto of(StudentSupportService supportService) {
        StudentSupportServiceDto dto = EntityUtil.bindToDto(supportService, new StudentSupportServiceDto(), "file");
        if (supportService.getOisFile() != null) {
            dto.setFile(OisFileViewDto.of(supportService.getOisFile()));
        }
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public OisFileViewDto getFile() {
        return file;
    }

    public void setFile(OisFileViewDto file) {
        this.file = file;
    }

    public String getEntrySubmitter() {
        return entrySubmitter;
    }

    public void setEntrySubmitter(String entrySubmitter) {
        this.entrySubmitter = entrySubmitter;
    }

    public Boolean getIsArtificial() {
        return isArtificial;
    }

    public void setIsArtificial(Boolean isArtificial) {
        this.isArtificial = isArtificial;
    }

    public Long getEndingDirectiveId() {
        return endingDirectiveId;
    }

    public void setEndingDirectiveId(Long endingDirectiveId) {
        this.endingDirectiveId = endingDirectiveId;
    }
}
