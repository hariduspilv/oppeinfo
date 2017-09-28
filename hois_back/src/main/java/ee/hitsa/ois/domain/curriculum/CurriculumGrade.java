package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.util.Translatable;
@Entity
public class CurriculumGrade extends BaseEntityWithId implements Translatable {

	private static final long serialVersionUID = 5225487267992767869L;
	@NotBlank
	@Size(max=255)
	private String nameEt;
	@NotBlank
	@Size(max=255)
	private String nameEn;
	@Size(max=255)
	private String nameGenitiveEt;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Classifier ehisGrade;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
	private Curriculum curriculum;
	

	public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    @Override
	public String getNameEt() {
		return nameEt;
	}

	public void setNameEt(String nameEt) {
		this.nameEt = nameEt;
	}

    @Override
	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getNameGenitiveEt() {
		return nameGenitiveEt;
	}

	public void setNameGenitiveEt(String nameGenitiveEt) {
		this.nameGenitiveEt = nameGenitiveEt;
	}

	public Classifier getEhisGrade() {
		return ehisGrade;
	}

	public void setEhisGrade(Classifier ehisGrade) {
		this.ehisGrade = ehisGrade;
	}
}
