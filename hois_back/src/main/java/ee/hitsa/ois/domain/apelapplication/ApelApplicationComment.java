package ee.hitsa.ois.domain.apelapplication;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
public class ApelApplicationComment extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private ApelApplication apelApplication;
    
    @JoinColumn(nullable = false)
    private String addInfo;
    /** Is comment visible for student */
    private Boolean isStudent;
    
    public ApelApplication getApelApplication() {
        return apelApplication;
    }

    public void setApelApplication(ApelApplication apelApplication) {
        this.apelApplication = apelApplication;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public Boolean getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(Boolean isStudent) {
        this.isStudent = isStudent;
    }
    
}
