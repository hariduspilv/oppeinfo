package ee.hitsa.ois.domain.curriculum;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.OisFile;
@Entity
@Table(name = "curriculum_files")
public class CurriculumFile extends BaseEntityWithId{

	private static final long serialVersionUID = 6469422072870683447L;

	@NotNull
	@Column(name="is_ehis")
	private boolean ehis;

	@NotNull
	private boolean sendEhis;

	@ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private OisFile oisFile;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Classifier ehisFile;
	
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
	private Curriculum curriculum;

	public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

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
