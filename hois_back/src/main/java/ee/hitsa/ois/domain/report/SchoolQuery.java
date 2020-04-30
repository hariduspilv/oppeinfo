package ee.hitsa.ois.domain.report;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.school.School;

@Entity
public class SchoolQuery extends BaseEntityWithId {
    
    private Boolean isStudentQuery;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    private String nameEt;
    private String querySubType;
    private Boolean isWithoutGuests;
    private String orderby1;
    private String orderby2;
    private String orderby3;
    @Column(name = "is_orderby1_desc")
    private Boolean isOrderby1Desc;
    @Column(name = "is_orderby2_desc")
    private Boolean isOrderby2Desc;
    @Column(name = "is_orderby3_desc")
    private Boolean isOrderby3Desc;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "school_query_id", nullable = false, updatable = false , insertable = false)
    private List<SchoolQueryCriteria> schoolQueryCriteria = new ArrayList<>();
    
    public Boolean getIsStudentQuery() {
        return isStudentQuery;
    }
    public void setIsStudentQuery(Boolean isStudentQuery) {
        this.isStudentQuery = isStudentQuery;
    }
    public School getSchool() {
        return school;
    }
    public void setSchool(School school) {
        this.school = school;
    }
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    public String getQuerySubType() {
        return querySubType;
    }
    public void setQuerySubType(String querySubType) {
        this.querySubType = querySubType;
    }
    public Boolean getIsWithoutGuests() {
        return isWithoutGuests;
    }
    public void setIsWithoutGuests(Boolean isWithoutGuests) {
        this.isWithoutGuests = isWithoutGuests;
    }
    public String getOrderby1() {
        return orderby1;
    }
    public void setOrderby1(String orderby1) {
        this.orderby1 = orderby1;
    }
    public String getOrderby2() {
        return orderby2;
    }
    public void setOrderby2(String orderby2) {
        this.orderby2 = orderby2;
    }
    public String getOrderby3() {
        return orderby3;
    }
    public void setOrderby3(String orderby3) {
        this.orderby3 = orderby3;
    }
    public Boolean getIsOrderby1Desc() {
        return isOrderby1Desc;
    }
    public void setIsOrderby1Desc(Boolean isOrderby1Desc) {
        this.isOrderby1Desc = isOrderby1Desc;
    }
    public Boolean getIsOrderby2Desc() {
        return isOrderby2Desc;
    }
    public void setIsOrderby2Desc(Boolean isOrderby2Desc) {
        this.isOrderby2Desc = isOrderby2Desc;
    }
    public Boolean getIsOrderby3Desc() {
        return isOrderby3Desc;
    }
    public void setIsOrderby3Desc(Boolean isOrderby3Desc) {
        this.isOrderby3Desc = isOrderby3Desc;
    }
    public List<SchoolQueryCriteria> getSchoolQueryCriteria() {
        return schoolQueryCriteria;
    }
    public void setSchoolQueryCriteria(List<SchoolQueryCriteria> schoolQueryCriteria) {
        this.schoolQueryCriteria = schoolQueryCriteria;
    }
}
