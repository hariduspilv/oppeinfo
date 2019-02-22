package ee.hitsa.ois.web.dto.enterprise;

import java.time.LocalDate;

public class ContractStatisticsDto {
	
	private Long id;
	private String contractNr;
	private String student;
	private String studentGroup;
	private LocalDate startDate;
	private LocalDate endDate;
	private String enterprise;
	private LocalDate cancelDate;
	private String cancelCode;
	private String cancelReason;
	
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
	public String getStudent() {
		return student;
	}
	public void setStudent(String student) {
		this.student = student;
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
	public LocalDate getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(LocalDate cancelDate) {
		this.cancelDate = cancelDate;
	}
	public String getCancelCode() {
		return cancelCode;
	}
	public void setCancelCode(String cancelCode) {
		this.cancelCode = cancelCode;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
}
