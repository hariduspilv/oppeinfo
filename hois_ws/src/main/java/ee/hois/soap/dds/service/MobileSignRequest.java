package ee.hois.soap.dds.service;

public class MobileSignRequest {

    private Integer sesscode;
    private String signerIDCode = "";
    private String signersCountry = "";
    private String signerPhoneNo = "";
    private String serviceName = "";
    private String additionalDataToBeDisplayed = "";
    private String language = "";
    private String role = "";
    private String city = "";
    private String stateOrProvince = "";
    private String postalCode = "";
    private String countryName = "";
    private String signingProfile = "";
    private String messagingMode = "";
    private int asyncConfiguration;
    private boolean returnDocInfo;
    private boolean returnDocData;
    
    public Integer getSesscode() {
        return sesscode;
    }
    public void setSesscode(Integer sesscode) {
        this.sesscode = sesscode;
    }
    
    public String getSignerIDCode() {
        return signerIDCode;
    }
    public void setSignerIDCode(String signerIDCode) {
        this.signerIDCode = signerIDCode;
    }
    
    public String getSignersCountry() {
        return signersCountry;
    }
    public void setSignersCountry(String signersCountry) {
        this.signersCountry = signersCountry;
    }
    
    public String getSignerPhoneNo() {
        return signerPhoneNo;
    }
    public void setSignerPhoneNo(String signerPhoneNo) {
        this.signerPhoneNo = signerPhoneNo;
    }
    
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public String getAdditionalDataToBeDisplayed() {
        return additionalDataToBeDisplayed;
    }
    public void setAdditionalDataToBeDisplayed(String additionalDataToBeDisplayed) {
        this.additionalDataToBeDisplayed = additionalDataToBeDisplayed;
    }
    
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getStateOrProvince() {
        return stateOrProvince;
    }
    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    
    public String getSigningProfile() {
        return signingProfile;
    }
    public void setSigningProfile(String signingProfile) {
        this.signingProfile = signingProfile;
    }
    
    public String getMessagingMode() {
        return messagingMode;
    }
    public void setMessagingMode(String messagingMode) {
        this.messagingMode = messagingMode;
    }
    
    public int getAsyncConfiguration() {
        return asyncConfiguration;
    }
    public void setAsyncConfiguration(int asyncConfiguration) {
        this.asyncConfiguration = asyncConfiguration;
    }
    
    public boolean isReturnDocInfo() {
        return returnDocInfo;
    }
    public void setReturnDocInfo(boolean returnDocInfo) {
        this.returnDocInfo = returnDocInfo;
    }
    
    public boolean isReturnDocData() {
        return returnDocData;
    }
    public void setReturnDocData(boolean returnDocData) {
        this.returnDocData = returnDocData;
    }
    
}
