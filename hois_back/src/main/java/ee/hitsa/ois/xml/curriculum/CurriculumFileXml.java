package ee.hitsa.ois.xml.curriculum;

import ee.hitsa.ois.domain.curriculum.CurriculumFile;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;

public class CurriculumFileXml {
    
    private Long id;
    private Long version;
    private boolean ehis;
    private boolean sendEhis;
    private String ehisFile;
    private String fileName;
    
    public static CurriculumFileXml of(CurriculumFile file) {
        CurriculumFileXml xml = EntityUtil.bindToDto(file, new CurriculumFileXml(), "ehisFile");
        xml.setFileName(file.getOisFile().getFname());
        xml.setEhisFile(ClassifierUtil.getNullableNameEt(file.getEhisFile()));
        return xml;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean isEhis() {
        return ehis;
    }

    public void setEhis(boolean ehis) {
        this.ehis = ehis;
    }

    public boolean isSendEhis() {
        return sendEhis;
    }

    public void setSendEhis(boolean sendEhis) {
        this.sendEhis = sendEhis;
    }

    public String getEhisFile() {
        return ehisFile;
    }

    public void setEhisFile(String ehisFile) {
        this.ehisFile = ehisFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
