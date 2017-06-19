package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.util.Translatable;
@Entity
@Table(name = "curriculum_joint_partners")
public class CurriculumJointPartner extends BaseEntityWithId implements Translatable {

	private static final long serialVersionUID = 6980376403338348043L;

	@Column(name="is_abroad")
	private boolean abroad;
	private String contractEt;
	private String contractEn;
	private String supervisor;
	private String nameEt;
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

	public Classifier getEhisSchool() {
		return ehisSchool;
	}

	public void setEhisSchool(Classifier ehisSchool) {
		this.ehisSchool = ehisSchool;
	}
}
