package ee.hitsa.ois.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ee.hitsa.ois.ClassifierJsonDeserializer;
@Entity
@Table(name = "curriculum_files")
public class CurriculumFile extends BaseEntityWithId{

	private static final long serialVersionUID = 6469422072870683447L;

	@NotNull
	@Column(name="is_ehis")
	private boolean ehis;

	@NotNull
	private boolean sendEhis;

	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	private OisFile oisFile;

	@ManyToOne(optional = false)
	@JsonDeserialize(using = ClassifierJsonDeserializer.class)
	private Classifier ehisFile;

	public boolean isEhis() {
		return ehis;
	}

	public void setEhis(boolean ehis) {
		this.ehis = ehis;
	}

	public boolean isSendEhis() {
		return sendEhis;
	}

	public void setSendEhis(boolean sendEhis) {
		this.sendEhis = sendEhis;
	}

	public OisFile getOisFile() {
		return oisFile;
	}

	public void setOisFile(OisFile oisFile) {
		this.oisFile = oisFile;
	}

	public Classifier getEhisFile() {
		return ehisFile;
	}

	public void setEhisFile(Classifier ehisFile) {
		this.ehisFile = ehisFile;
	}
}
