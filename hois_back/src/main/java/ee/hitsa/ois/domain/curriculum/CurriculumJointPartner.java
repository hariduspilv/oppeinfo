package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
@Entity
@Table(name = "curriculum_joint_partners")
public class CurriculumJointPartner extends BaseEntityWithId{

	private static final long serialVersionUID = 6980376403338348043L;

	@NotNull
	@Column(name="is_abroad")
	private boolean abroad;

	@NotNull
	@Size(max=1000)
	private String contractEt;

	@Size(max=1000)
	private String contractEn;

	@Size(max=255)
	private String supervisor;

	@Size(max=255)
	private String nameEt;

	@Size(max=255)
	private String nameEn;

	@ManyToOne(fetch = FetchType.LAZY)
	private Classifier ehisSchool;

	public boolean isAbroad() {
		return abroad;
	}

	public void setAbroad(boolean abroad) {
		this.abroad = abroad;
	}

	public String getContractEt() {
		return contractEt;
	}

	public void setContractEt(String contractEt) {
		this.contractEt = contractEt;
	}

	public String getContractEn() {
		return contractEn;
	}

	public void setContractEn(String contractEn) {
		this.contractEn = contractEn;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
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

	public Classifier getEhisSchool() {
		return ehisSchool;
	}

	public void setEhisSchool(Classifier ehisSchool) {
		this.ehisSchool = ehisSchool;
	}
}
