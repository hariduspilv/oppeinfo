package ee.hitsa.ois.web.dto.directive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ScholarshipApplicationSelectDto {

    private Long id;
    private String nameEt;
    private LocalDate startDate;
    private LocalDate endDate;
    private String bankAccount;
    private BigDecimal amountPaid;
    private String scholarshipEhis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
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

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getScholarshipEhis() {
        return scholarshipEhis;
    }

    public void setScholarshipEhis(String scholarshipEhis) {
        this.scholarshipEhis = scholarshipEhis;
    }

}
