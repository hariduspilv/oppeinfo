package ee.hitsa.ois.web.commandobject;

import ee.hitsa.ois.domain.school.School;

public class UserContractForm {

    private Boolean isStudentTerms = Boolean.FALSE;
    private String contract;
    
    public static UserContractForm of(School school) {
        UserContractForm dto = new UserContractForm();
        dto.setIsStudentTerms(school.getIsStudentTerms());
        dto.setContract(school.getContractText());
        return dto;
    }
    
    public Boolean getIsStudentTerms() {
        return isStudentTerms;
    }
    
    public void setIsStudentTerms(Boolean isStudentTerms) {
        this.isStudentTerms = isStudentTerms;
    }
    
    public String getContract() {
        return contract;
    }
    
    public void setContract(String contract) {
        this.contract = contract;
    }
}
