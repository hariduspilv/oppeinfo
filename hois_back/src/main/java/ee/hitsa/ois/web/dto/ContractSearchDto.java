package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.enterprise.Enterprise;
import ee.hitsa.ois.domain.enterprise.EnterpriseSchoolPerson;
import ee.hitsa.ois.util.EntityUtil;

public class ContractSearchDto {

    private Long id;
    private AutocompleteResult student;
    private String contractNr;
    private LocalDate startDate;
    private LocalDate endDate;
    private String enterpriseName;
    private String enterpriseContactPersonName;
    private AutocompleteResult teacher;
    private LocalDate confirmDate;
    private String status;
    private String studentGroup;
    
    public static ContractSearchDto of(Contract contract) {
    	ContractSearchDto contractSearchDto = EntityUtil.bindToDto(contract, new ContractSearchDto());
    	contractSearchDto.setStatus(contract.getStatus().getNameEt());
    	Enterprise enterprise = contract.getEnterprise();
    	if (enterprise != null && !enterprise.getEnterpriseSchools().isEmpty()) {
    		List<EnterpriseSchoolPerson> enterpriseContactPersons = new ArrayList<>();
    		enterprise.getEnterpriseSchools().forEach(p->enterpriseContactPersons.addAll(p.getEnterpriseSchoolPersons()));
    		List<String> pairs = enterpriseContactPersons.stream().map(p->p.getFirstname() + ' ' + p.getLastname()).collect(Collectors.toList());
    		pairs.removeIf(p->p.trim().equals(""));
    		contractSearchDto.setEnterpriseContactPersonName(String.join(", ", pairs));
    		if (StringUtils.isEmpty(contractSearchDto.getEnterpriseContactPersonName())) {
    			contractSearchDto.setEnterpriseContactPersonName(contract.getContactPersonName());
    		}
    	} else {
    		contractSearchDto.setEnterpriseContactPersonName(contract.getContactPersonName());
    	}
		return contractSearchDto;
	}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutocompleteResult getStudent() {
        return student;
    }

    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }

    public String getContractNr() {
        return contractNr;
    }

    public void setContractNr(String contractNr) {
        this.contractNr = contractNr;
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

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseContactPersonName() {
        return enterpriseContactPersonName;
    }

    public void setEnterpriseContactPersonName(String enterpriseContactPersonName) {
        this.enterpriseContactPersonName = enterpriseContactPersonName;
    }

    public AutocompleteResult getTeacher() {
        return teacher;
    }

    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

}
