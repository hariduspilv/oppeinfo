package ee.hitsa.ois.web.dto.studymaterial;

import ee.hitsa.ois.web.commandobject.OisFileViewDto;

public class StudyMaterialSearchDto {

    private Long id;
    private String nameEt;
    private String typeCode;
    private Boolean isPublic;
    private Boolean isVisibleToStudents;
    private String url;
    private OisFileViewDto oisFile;
    private Long journalCount;
    private StudyMaterialConnectDto connect;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean getIsVisibleToStudents() {
        return isVisibleToStudents;
    }

    public void setIsVisibleToStudents(Boolean isVisibleToStudents) {
        this.isVisibleToStudents = isVisibleToStudents;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public OisFileViewDto getOisFile() {
        return oisFile;
    }

    public void setOisFile(OisFileViewDto oisFile) {
        this.oisFile = oisFile;
    }

    public Long getJournalCount() {
        return journalCount;
    }

    public void setJournalCount(Long journalCount) {
        this.journalCount = journalCount;
    }

    public StudyMaterialConnectDto getConnect() {
        return connect;
    }

    public void setConnect(StudyMaterialConnectDto connect) {
        this.connect = connect;
    }

}
