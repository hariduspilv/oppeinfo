package ee.hitsa.ois.web.dto.student;

import java.time.LocalDate;

public class StudentPracticeStatisticsDto {
	
	private Long id;
	private String contractNr;
	private String student;
	private String studentGroup;
	private LocalDate startDate;
	private LocalDate endDate;
	private String enterprise;
	private String status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContractNr() {
		return contractNr;
	}
	public void setContractNr(String contractNr) {
		this.contractNr = contractNr;
	}
	public String getStudentGroup() {
		return studentGroup;
	}
	public void setStudentGroup(String studentGroup) {
		this.studentGroup = studentGroup;
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
	public String getEnterprise() {
		return enterprise;
	}
	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStudent() {
		return student;
	}
	public void setStudent(String student) {
		this.student = student;
	}

}
