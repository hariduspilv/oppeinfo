package ee.hitsa.ois.web.dto.report;

import java.math.BigDecimal;
import java.time.LocalDate;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class ScholarshipReportDto {
    
    private String group;
    private Person student;
    private LocalDate decisionDate;
    private String protocolNr;
    private AutocompleteResult type;
    private BigDecimal amountPaid;
    private Person receiver;
    private String bankAccount;
    private LocalDate from;
    private LocalDate thru;
    
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Person getStudent() {
        return student;
    }

    public void setStudent(Person student) {
        this.student = student;
    }

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getThru() {
        return thru;
    }

    public void setThru(LocalDate thru) {
        this.thru = thru;
    }

    public LocalDate getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(LocalDate decisionDate) {
        this.decisionDate = decisionDate;
    }

    public String getProtocolNr() {
        return protocolNr;
    }

    public void setProtocolNr(String protocolNr) {
        this.protocolNr = protocolNr;
    }

    public AutocompleteResult getType() {
        return type;
    }

    public void setType(AutocompleteResult type) {
        this.type = type;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public static class Person {
        
        private String idcode;
        private String name;
        
        public Person(String idcode, String name) {
            this.idcode = idcode;
            this.name = name;
        }

        public String getIdcode() {
            return idcode;
        }
        
        public void setIdcode(String idcode) {
            this.idcode = idcode;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
    }
}
