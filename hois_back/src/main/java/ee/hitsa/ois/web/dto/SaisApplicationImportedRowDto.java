package ee.hitsa.ois.web.dto;

public class SaisApplicationImportedRowDto {

    private int rowNr;
    private String message;

    public SaisApplicationImportedRowDto() {
    }

    public SaisApplicationImportedRowDto(int rowNr, String message) {
        this.rowNr = rowNr;
        this.message = message;
    }

    public int getRowNr() {
        return rowNr;
    }
    public void setRowNr(int rowNr) {
        this.rowNr = rowNr;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
