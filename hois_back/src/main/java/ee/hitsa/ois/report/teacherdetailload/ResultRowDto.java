package ee.hitsa.ois.report.teacherdetailload;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.report.teacherdetailload.PeriodDetailLoadDto;

public class ResultRowDto extends PeriodDetailLoadDto {

    private AutocompleteResult name;
    private String studentGroups;
    private String subjectCode;
    private BigDecimal eap;
    private List<Object> loads = new ArrayList<>();

    public AutocompleteResult getName() {
        return name;
    }

    public void setName(AutocompleteResult name) {
        this.name = name;
    }

    public String getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(String studentGroups) {
        this.studentGroups = studentGroups;
    }

	public List<Object> getLoads() {
		return loads;
	}

	public void setLoads(List<Object> loads) {
		this.loads = loads;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public BigDecimal getEap() {
		return eap;
	}

	public void setEap(BigDecimal eap) {
		this.eap = eap;
	}

}
