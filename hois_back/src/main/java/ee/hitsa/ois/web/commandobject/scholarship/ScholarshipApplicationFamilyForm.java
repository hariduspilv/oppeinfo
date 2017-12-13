package ee.hitsa.ois.web.commandobject.scholarship;

public class ScholarshipApplicationFamilyForm {
    private Long id;
    private String name;
    private Long netSalary;
    private Long otherIncome;
    private Long pension;
    private Long stateBenefit;
    private long unemplyedBenefit;

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

    public long getUnemplyedBenefit() {
        return unemplyedBenefit;
    }

    public void setUnemplyedBenefit(long unemplyedBenefit) {
        this.unemplyedBenefit = unemplyedBenefit;
    }

}
