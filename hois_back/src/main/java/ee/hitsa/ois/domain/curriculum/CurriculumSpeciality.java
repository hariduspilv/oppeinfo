package ee.hitsa.ois.domain.curriculum;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
@Entity
public class CurriculumSpeciality extends BaseEntityWithId {

	private static final long serialVersionUID = 8173305313184771116L;

	@NotBlank
	@Size(max=100)
	private String nameEt;
	@NotBlank
	@Size(max=100)
	private String nameEn;
	@NotNull
	private Double credits;
	@Size(max=1000)
	private String description;
	@Size(max=255)
	private String occupationEt;
	@Size(max=255)
	private String occupationEn;

	@ManyToOne
	private Classifier occupation;
	
	@OneToMany(mappedBy = "curriculumSpeciality", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CurriculumVersionSpeciality> curriculumVersionSpecialities;
	
	@Transient
	private Long referenceNumber;

    public Set<CurriculumVersionSpeciality> getCurriculumVersionSpecialities() {
        return curriculumVersionSpecialities != null ? curriculumVersionSpecialities 
                : (curriculumVersionSpecialities = new HashSet<>());
    }

    public void setCurriculumVersionSpecialities(Set<CurriculumVersionSpeciality> curriculumVersionSpecialities) {
        this.curriculumVersionSpecialities = curriculumVersionSpecialities;
    }

    public Long getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(Long referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getNameEt() {
		return nameEt;
	}

	public void setNameEt(String nameEt) {
		this.nameEt = nameEt;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public Double getCredits() {
		return credits;
	}

	public void setCredits(Double credits) {
		this.credits = credits;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOccupationEt() {
		return occupationEt;
	}

	public void setOccupationEt(String occupationEt) {
		this.occupationEt = occupationEt;
	}

	public String getOccupationEn() {
		return occupationEn;
	}

	public void setOccupationEn(String occupationEn) {
		this.occupationEn = occupationEn;
	}

	public Classifier getOccupation() {
		return occupation;
	}

	public void setOccupation(Classifier occupation) {
		this.occupation = occupation;
	}
}
