package ee.hitsa.ois.web.dto.enterprise;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudyYearStatisticsDto {
	
	private String studentGroup;
	private AutocompleteResult curriculumName;
	private String curriculumCode;
	private AutocompleteResult curriculumGroup;
	private Long course;
	private int totalWent;
	private int totalCompleted;
	private int totalFailed;
	private String nameAndReason;
	private double grade;
	private String enterprises;
	
	public String getStudentGroup() {
		return studentGroup;
	}
	public void setStudentGroup(String studentGroup) {
		this.studentGroup = studentGroup;
	}
	public AutocompleteResult getCurriculumName() {
		return curriculumName;
	}
	public void setCurriculumName(AutocompleteResult curriculumName) {
		this.curriculumName = curriculumName;
	}
	public String getCurriculumCode() {
		return curriculumCode;
	}
	public void setCurriculumCode(String curriculumCode) {
		this.curriculumCode = curriculumCode;
	}
	public AutocompleteResult getCurriculumGroup() {
		return curriculumGroup;
	}
	public void setCurriculumGroup(AutocompleteResult curriculumGroup) {
		this.curriculumGroup = curriculumGroup;
	}
	public Long getCourse() {
		return course;
	}
	public void setCourse(Long course) {
		this.course = course;
	}
	public int getTotalWent() {
		return totalWent;
	}
	public void setTotalWent(int totalWent) {
		this.totalWent = totalWent;
	}
	public int getTotalCompleted() {
		return totalCompleted;
	}
	public void setTotalCompleted(int totalCompleted) {
		this.totalCompleted = totalCompleted;
	}
	public int getTotalFailed() {
		return totalFailed;
	}
	public void setTotalFailed(int totalFailed) {
		this.totalFailed = totalFailed;
	}
	public String getNameAndReason() {
		return nameAndReason;
	}
	public void setNameAndReason(String nameAndReason) {
		this.nameAndReason = nameAndReason;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	public String getEnterprises() {
		return enterprises;
	}
	public void setEnterprises(String enterprises) {
		this.enterprises = enterprises;
	}
}
