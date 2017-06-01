package ee.hois.xroad.helpers.sais;

import ee.hois.xroad.helpers.XRoadResponse;
import ee.hois.xroad.sais2.generated.AdmissionExportResponse;

public class SaisAdmissionResponse extends XRoadResponse {
    AdmissionExportResponse admissionExportResponse;
    
    public SaisAdmissionResponse(XRoadResponse response) {
        this.setQueryEnd(response.getQueryEnd());
        this.setQueryName(response.getQueryName());
        this.setQueryStart(response.getQueryStart());
        this.setXmlQuery(response.getXmlQuery());
        this.setXmlResponse(response.getXmlResponse());
    }

    public SaisAdmissionResponse() {
        // TODO Auto-generated constructor stub
    }

    public AdmissionExportResponse getAdmissionExportResponse() {
        return admissionExportResponse;
    }

    public void setAdmissionExportResponse(AdmissionExportResponse admissionExportResponse) {
        this.admissionExportResponse = admissionExportResponse;
    }


}
