package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ee.hitsa.ois.ClassifierJsonDeserializer;
@Entity
public class CurriculumGrade extends BaseEntityWithId{

	private static final long serialVersionUID = 5225487267992767869L;
	@NotBlank
	@Size(max=255)
	private String nameEt;
	@NotBlank
	@Size(max=255)
	private String nameEn;
	@Size(max=255)
	private String nameGenitiveEt;
	
	@ManyToOne(optional = false)
	@JsonDeserialize(using = ClassifierJsonDeserializer.class)
	private Classifier ehisGrade;

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
