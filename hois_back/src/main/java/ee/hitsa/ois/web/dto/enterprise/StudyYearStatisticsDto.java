package ee.hitsa.ois.web.dto.enterprise;

public class StudyYearStatisticsDto {
	
	private String studentGroup;
	private String curriculumName;
	private String curriculumCode;
	private String curriculumGroup;
	private String course;
	private String totalWent;
	private String totalCompleted;
	private String totalFailed;
	private String nameAndReason;
	private String grade;
	private String enterprises;
	
	public String getStudentGroup() {
		return studentGroup;
	}
	public void setStudentGroup(String studentGroup) {
		this.studentGroup = studentGroup;
	}
	public String getCurriculumName() {
		return curriculumName;
	}
	public void setCurriculumName(String curriculumName) {
		this.curriculumName = curriculumName;
	}
	public String getCurriculumCode() {
		return curriculumCode;
	}
	public void setCurriculumCode(String curriculumCode) {
		this.curriculumCode = curriculumCode;
	}
	public String getCurriculumGroup() {
		return curriculumGroup;
	}
	public void setCurriculumGroup(String curriculumGroup) {
		this.curriculumGroup = curriculumGroup;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getTotalWent() {
		return totalWent;
	}
	public void setTotalWent(String totalWent) {
		this.totalWent = totalWent;
	}
	public String getTotalCompleted() {
		return totalCompleted;
	}
	public void setTotalCompleted(String totalCompleted) {
		this.totalCompleted = totalCompleted;
	}
	public String getTotalFailed() {
		return totalFailed;
	}
	public void setTotalFailed(String totalFailed) {
		this.totalFailed = totalFailed;
	}
	public String getNameAndReason() {
		return nameAndReason;
	}
	public void setNameAndReason(String nameAndReason) {
		this.nameAndReason = nameAndReason;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getEnterprises() {
		return enterprises;
	}
	public void setEnterprises(String enterprises) {
		this.enterprises = enterprises;
	}
}
