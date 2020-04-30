package ee.hitsa.ois.web.dto.report;

import ee.hitsa.ois.web.commandobject.report.QuerySaveCommand;

public class SchoolQueryDto extends QuerySaveCommand {
    
    private String resultType;
    private Boolean withoutGuestStudents;
    private String orderField1;
    private String orderField2;
    private String orderField3;
    
    private Boolean orderField1Desc;
    private Boolean orderField2Desc;
    private Boolean orderField3Desc;
    
    public String getOrderField2() {
        return orderField2;
    }
    public void setOrderField2(String orderField2) {
        this.orderField2 = orderField2;
    }
    public String getResultType() {
        return resultType;
    }
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
    public Boolean getWithoutGuestStudents() {
        return withoutGuestStudents;
    }
    public void setWithoutGuestStudents(Boolean withoutGuestStudents) {
        this.withoutGuestStudents = withoutGuestStudents;
    }
    public String getOrderField1() {
        return orderField1;
    }
    public void setOrderField1(String orderField1) {
        this.orderField1 = orderField1;
    }
    public String getOrderField3() {
        return orderField3;
    }
    public void setOrderField3(String orderField3) {
        this.orderField3 = orderField3;
    }
    public Boolean getOrderField1Desc() {
        return orderField1Desc;
    }
    public void setOrderField1Desc(Boolean orderField1Desc) {
        this.orderField1Desc = orderField1Desc;
    }
    public Boolean getOrderField2Desc() {
        return orderField2Desc;
    }
    public void setOrderField2Desc(Boolean orderField2Desc) {
        this.orderField2Desc = orderField2Desc;
    }
    public Boolean getOrderField3Desc() {
        return orderField3Desc;
    }
    public void setOrderField3Desc(Boolean orderField3Desc) {
        this.orderField3Desc = orderField3Desc;
    }

}
