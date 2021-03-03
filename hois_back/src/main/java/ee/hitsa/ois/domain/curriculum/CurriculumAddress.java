package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.student.StudentGroup;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CurriculumAddress extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Curriculum curriculum;

	@Column(nullable = false)
    private String address;
	@Column(nullable = false)
    private String addressAds;
    @Column(nullable = false)
    private String addressOid;
    @Column(nullable = false)
    private String addressOv;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "curriculumAddress")
    private List<StudentGroup> studentGroups;
    
    public Curriculum getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }
    
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getAddressAds() {
        return addressAds;
    }
    public void setAddressAds(String addressAds) {
        this.addressAds = addressAds;
    }
    
    public String getAddressOid() {
        return addressOid;
    }
    public void setAddressOid(String addressOid) {
        this.addressOid = addressOid;
    }
    
    public String getAddressOv() {
        return addressOv;
    }
    public void setAddressOv(String addressOv) {
        this.addressOv = addressOv;
    }

    public List<StudentGroup> getStudentGroups() {
        return studentGroups != null ? studentGroups : (studentGroups = new ArrayList<>());
    }

    public void setStudentGroups(List<StudentGroup> studentGroups) {
        getStudentGroups().clear();
        getStudentGroups().addAll(studentGroups);
    }
}
