package ee.hois.soap;

public class SoapAttachment {

    private final byte[] content;
    private final String contentType;
    private final String contentId;
    
    public SoapAttachment(byte[] content, String contentType, String contentId) {
        this.content = content;
        this.contentType = contentType;
        this.contentId = contentId;
    }

    public byte[] getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }

    public String getContentId() {
        return contentId;
    }
    
}
