package ee.hitsa.ois.web.dto.enterprise;

public class EnterpriseImportedRowMessageDto {
	
    private int rowNr;
    private String message;
    private String regCode;
    private String name;

    public EnterpriseImportedRowMessageDto() {
    }

    public EnterpriseImportedRowMessageDto(int rowNr, String message) {
        this.rowNr = rowNr;
        this.message = message;
    }
    
    public EnterpriseImportedRowMessageDto(int rowNr, String message, String regCode, String name) {
        this.rowNr = rowNr;
        this.message = message;
        this.regCode = regCode;
        this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

}
