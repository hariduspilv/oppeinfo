package ee.hois.xroad.ehis.service;

import ee.hois.xroad.helpers.XRoadResponse;

public class EhisLaeKorgharidusedResponse extends XRoadResponse {

    private String request;
    private String response;
    private Boolean hasXRoadErrors = Boolean.FALSE;
    private Boolean hasOtherErrors = Boolean.FALSE;
    private String logTxt = "";

    public EhisLaeKorgharidusedResponse(XRoadResponse xRoadResponse) {
        response = xRoadResponse.getXmlResponse();
        request = xRoadResponse.getXmlQuery();
        if (xRoadResponse.getError() != null) {
            logTxt = xRoadResponse.getError();
        }
        if (xRoadResponse.getxRoadErrors() != null) {
            hasXRoadErrors = xRoadResponse.getxRoadErrors();
        }
        if (xRoadResponse.getProcessingErrors() != null) {
            hasOtherErrors = xRoadResponse.getProcessingErrors();
        }
    }

    public EhisLaeKorgharidusedResponse() {

    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Boolean isHasXRoadErrors() {
        return hasXRoadErrors;
    }

    public void setHasXRoadErrors(Boolean hasXRoadErrors) {
        this.hasXRoadErrors = hasXRoadErrors;
    }

    public Boolean isHasOtherErrors() {
        return hasOtherErrors;
    }

    public void setHasOtherErrors(Boolean hasOtherErrors) {
        this.hasOtherErrors = hasOtherErrors;
    }

    public String getLogTxt() {
        return logTxt;
    }

    public void setLogTxt(String logTxt) {
        this.logTxt = logTxt;
    }
}
