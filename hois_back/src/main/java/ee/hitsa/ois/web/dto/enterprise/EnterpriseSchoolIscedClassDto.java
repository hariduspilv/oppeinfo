package ee.hitsa.ois.web.dto.enterprise;

import java.time.LocalDate;

public class EnterpriseSchoolIscedClassDto {
	
	private Long id;
	private String iscedClass;
	private Long places;
	private String addInfo;
	private Boolean exists;
	private String iscedValue;
	private LocalDate validFrom;
	private LocalDate validThru;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPlaces() {
		return places;
	}

	public void setPlaces(Long places) {
		this.places = places;
	}

	public String getAddInfo() {
		return addInfo;
	}

	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}

	public String getIscedClass() {
		return iscedClass;
	}

	public void setIscedClass(String iscedClass) {
		this.iscedClass = iscedClass;
	}

	public String getIscedValue() {
		return iscedValue;
	}

	public void setIscedValue(String iscedValue) {
		this.iscedValue = iscedValue;
	}

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }

    public Boolean getExists() {
        return exists;
    }

    public void setExists(Boolean exists) {
        this.exists = exists;
    }
}
