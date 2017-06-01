package ee.hois.xroad.helpers.sais;

import ee.hois.xroad.helpers.XRoadResponse;
import ee.hois.xroad.sais2.generated.AppExportResponse;

public class SaisApplicationResponse extends XRoadResponse {
    AppExportResponse appExportResponse;

    public SaisApplicationResponse(XRoadResponse response) {
        this.setQueryEnd(response.getQueryEnd());
        this.setQueryName(response.getQueryName());
        this.setQueryStart(response.getQueryStart());
        this.setXmlQuery(response.getXmlQuery());
        this.setXmlResponse(response.getXmlResponse());
    }

    public SaisApplicationResponse() {
        // TODO Auto-generated constructor stub
    }

    public AppExportResponse getAppExportResponse() {
        return appExportResponse;
    }

    public void setAppExportResponse(AppExportResponse appExportResponse) {
        this.appExportResponse = appExportResponse;
    }
}
