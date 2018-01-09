package ee.hitsa.ois.web.dto.scholarship;

import java.math.BigDecimal;

import ee.hitsa.ois.domain.scholarship.ScholarshipApplicationFamily;
import ee.hitsa.ois.util.EntityUtil;

public class ScholarshipApplicationFamilyDto {
    private Long id;
    private String name;
    private BigDecimal netSalary;
    private BigDecimal otherIncome;
    private BigDecimal pension;
    private BigDecimal stateBenefit;
    private BigDecimal unemployedBenefit;

    public static ScholarshipApplicationFamilyDto of(ScholarshipApplicationFamily fam) {
        ScholarshipApplicationFamilyDto dto = new ScholarshipApplicationFamilyDto();
        return EntityUtil.bindToDto(fam, dto);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(BigDecimal netSalary) {
        this.netSalary = netSalary;
    }

    public BigDecimal getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(BigDecimal otherIncome) {
        this.otherIncome = otherIncome;
    }

    public BigDecimal getPension() {
        return pension;
    }

    public void setPension(BigDecimal pension) {
        this.pension = pension;
    }

    public BigDecimal getStateBenefit() {
        return stateBenefit;
    }

    public void setStateBenefit(BigDecimal stateBenefit) {
        this.stateBenefit = stateBenefit;
    }

    public BigDecimal getUnemployedBenefit() {
        return unemployedBenefit;
    }

    public void setUnemployedBenefit(BigDecimal unemployedBenefit) {
        this.unemployedBenefit = unemployedBenefit;
    }

}
