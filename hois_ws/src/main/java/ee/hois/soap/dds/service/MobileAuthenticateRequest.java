package ee.hois.soap.dds.service;

public class MobileAuthenticateRequest {

    private String idCode = "";
    private String countryCode = "";
    private String phoneNo = "";
    private String language = "";
    private String serviceName = "";
    private String messageToDisplay = "";
    private String spChallenge = "";
    private String messagingMode = "";
    private int asyncConfiguration;
    private boolean returnCertData;
    private boolean returnRevocationData;
    
    public String getIdCode() {
        return idCode;
    }
    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }
    
    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    
    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public String getMessageToDisplay() {
        return messageToDisplay;
    }
    public void setMessageToDisplay(String messageToDisplay) {
        this.messageToDisplay = messageToDisplay;
    }
    
    public String getSpChallenge() {
        return spChallenge;
    }
    public void setSpChallenge(String spChallenge) {
        this.spChallenge = spChallenge;
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
    
    public boolean isReturnCertData() {
        return returnCertData;
    }
    public void setReturnCertData(boolean returnCertData) {
        this.returnCertData = returnCertData;
    }
    
    public boolean isReturnRevocationData() {
        return returnRevocationData;
    }
    public void setReturnRevocationData(boolean returnRevocationData) {
        this.returnRevocationData = returnRevocationData;
    }
    
}
