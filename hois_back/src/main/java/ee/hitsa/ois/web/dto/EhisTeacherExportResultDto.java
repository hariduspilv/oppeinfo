package ee.hitsa.ois.web.dto;

public class EhisTeacherExportResultDto {
    private final String fullName;
    private final String message;
    private final boolean error;

    public EhisTeacherExportResultDto(String fullName, String message, boolean error) {
        this.fullName = fullName;
        this.message = message;
        this.error = error;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }
}
