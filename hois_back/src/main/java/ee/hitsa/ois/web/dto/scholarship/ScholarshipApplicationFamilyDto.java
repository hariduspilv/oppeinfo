package ee.hitsa.ois.web.dto.scholarship;

import ee.hitsa.ois.domain.scholarship.ScholarshipApplicationFamily;
import ee.hitsa.ois.util.EntityUtil;

public class ScholarshipApplicationFamilyDto {
    private Long id;
    private String name;
    private Long netSalary;
    private Long otherIncome;
    private Long pension;
    private Long stateBenefit;
    private Long unemployedBenefit;

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

    public Long getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(Long netSalary) {
        this.netSalary = netSalary;
    }

    public Long getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(Long otherIncome) {
        this.otherIncome = otherIncome;
    }

    public Long getPension() {
        return pension;
    }

    public void setPension(Long pension) {
        this.pension = pension;
    }

    public Long getStateBenefit() {
        return stateBenefit;
    }

    public void setStateBenefit(Long stateBenefit) {
        this.stateBenefit = stateBenefit;
    }

    public Long getUnemployedBenefit() {
        return unemployedBenefit;
    }

    public void setUnemployedBenefit(Long unemployedBenefit) {
        this.unemployedBenefit = unemployedBenefit;
    }
    
    


}
