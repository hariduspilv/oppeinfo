package ee.hois.soap.dds.service;

public class AddDataFileRequest {

    private Integer sesscode;
    private String fileName;
    private String mimeType;
    private int size;
    private String content;
    
    public Integer getSesscode() {
        return sesscode;
    }
    public void setSesscode(Integer sesscode) {
        this.sesscode = sesscode;
    }
    
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getMimeType() {
        return mimeType;
    }
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
}
