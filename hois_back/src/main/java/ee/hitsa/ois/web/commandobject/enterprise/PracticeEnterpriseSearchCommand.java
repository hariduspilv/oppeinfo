package ee.hitsa.ois.web.commandobject.enterprise;

public class PracticeEnterpriseSearchCommand {
	
    private String enterpriseName;
    private String enterpriseCode;
    private String ratingCode;
    private Boolean enterpriseActive;
    
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public String getEnterpriseCode() {
		return enterpriseCode;
	}
	public void setEnterpriseCode(String enterpriseCode) {
		this.enterpriseCode = enterpriseCode;
	}
	public Boolean getEnterpriseActive() {
		return enterpriseActive;
	}
	public void setEnterpriseActive(Boolean enterpriseActive) {
		this.enterpriseActive = enterpriseActive;
	}
	public String getRatingCode() {
		return ratingCode;
	}
	public void setRatingCode(String ratingCode) {
		this.ratingCode = ratingCode;
	}

}
