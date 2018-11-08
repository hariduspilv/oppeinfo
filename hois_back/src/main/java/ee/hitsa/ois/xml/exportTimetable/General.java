package ee.hitsa.ois.xml.exportTimetable;

public class General {
	private String schoolname;
	private String schoolyearbegindate;
	private String schoolyearenddate;
	private String header1;
	private String header2;
	private String footer;
	private String termbegindate;
	private String termenddate;
	
	public General(String schoolname, String schoolyearbegindate, String schoolyearenddate, String header1, String header2, String footer, String termbegindate,  String termenddate) {
		this.schoolname = schoolname;
		this.schoolyearbegindate = schoolyearbegindate;
		this.schoolyearenddate = schoolyearenddate;
		this.header1 = header1;
		this.header2 = header2;
		this.footer = footer;
		this.termbegindate = termbegindate;
		this.termenddate = termenddate;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public String getSchoolyearbegindate() {
		return schoolyearbegindate;
	}

	public void setSchoolyearbegindate(String schoolyearbegindate) {
		this.schoolyearbegindate = schoolyearbegindate;
	}

	public String getSchoolyearenddate() {
		return schoolyearenddate;
	}

	public void setSchoolyearenddate(String schoolyearenddate) {
		this.schoolyearenddate = schoolyearenddate;
	}

	public String getHeader1() {
		return header1;
	}

	public void setHeader1(String header1) {
		this.header1 = header1;
	}

	public String getHeader2() {
		return header2;
	}

	public void setHeader2(String header2) {
		this.header2 = header2;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getTermbegindate() {
		return termbegindate;
	}

	public void setTermbegindate(String termbegindate) {
		this.termbegindate = termbegindate;
	}

	public String getTermenddate() {
		return termenddate;
	}

	public void setTermenddate(String termenddate) {
		this.termenddate = termenddate;
	}
}
