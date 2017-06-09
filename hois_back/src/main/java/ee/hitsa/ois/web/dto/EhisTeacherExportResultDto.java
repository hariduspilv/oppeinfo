package ee.hitsa.ois.web.dto;

public class EhisTeacherExportResultDto {
    String fullName;
    String message;
    
    public EhisTeacherExportResultDto(String fullName, String message) {
        this.fullName = fullName;
        this.message = message;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
