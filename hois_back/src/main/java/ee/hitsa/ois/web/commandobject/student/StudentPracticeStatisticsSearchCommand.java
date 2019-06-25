package ee.hitsa.ois.web.commandobject.student;

import java.time.LocalDate;

public class StudentPracticeStatisticsSearchCommand {
	
	private Long enterprise;
	private Long studyPeriod;
	private Long student;
	private Long studentGroup;
	private LocalDate startDate;
	private LocalDate endDate;
	private String status;
	
	public Long getStudentGroup() {
		return studentGroup;
	}
	public void setStudentGroup(Long studentGroup) {
		this.studentGroup = studentGroup;
	}
	public Long getEnterprise() {
		return enterprise;
	}
	public void setEnterprise(Long enterprise) {
		this.enterprise = enterprise;
	}
	public Long getStudyPeriod() {
		return studyPeriod;
	}
	public void setStudyPeriod(Long studyPeriod) {
		this.studyPeriod = studyPeriod;
	}
	public Long getStudent() {
		return student;
	}
	public void setStudent(Long student) {
		this.student = student;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
