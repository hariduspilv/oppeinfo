package ee.hitsa.ois.xls;

public class XlsDynamicColumnCommand {
	
	private String sheet;
	private Integer startColumn;
	private Integer endColumn;
	
	public XlsDynamicColumnCommand(String sheet, Integer startColumn, Integer endColumn) {
		this.sheet = sheet;
		this.startColumn = startColumn;
		this.endColumn = endColumn;
	}
	
	public XlsDynamicColumnCommand(String sheet, Integer startColumn) {
		this(sheet, startColumn, null);
	}
	
	public Integer getEndColumn() {
		return endColumn;
	}
	public void setEndColumn(Integer endColumn) {
		this.endColumn = endColumn;
	}
	public String getSheet() {
		return sheet;
	}
	public void setSheet(String sheet) {
		this.sheet = sheet;
	}
	public Integer getStartColumn() {
		return startColumn;
	}
	public void setStartColumn(Integer startColumn) {
		this.startColumn = startColumn;
	}
	
	

}
