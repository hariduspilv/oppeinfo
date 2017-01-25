package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ee.hitsa.ois.ClassifierJsonDeserializer;
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
	@JsonDeserialize(using = ClassifierJsonDeserializer.class)
	private Classifier occupation;

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
