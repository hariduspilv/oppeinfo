package ee.hois.xroad.helpers.sais;

import ee.hois.xroad.helpers.XRoadResponse;
import ee.hois.xroad.sais2.generated.ClassificationExport;

public class SaisClassificationResponse extends XRoadResponse {
    ClassificationExport classificationsExport;
    
    public SaisClassificationResponse(XRoadResponse response) {
        this.setQueryEnd(response.getQueryEnd());
        this.setQueryName(response.getQueryName());
        this.setQueryStart(response.getQueryStart());
        this.setXmlQuery(response.getXmlQuery());
        this.setXmlResponse(response.getXmlResponse());
    }

    public SaisClassificationResponse() {
        // TODO Auto-generated constructor stub
    }

    public ClassificationExport getClassificationsExport() {
        return classificationsExport;
    }

    public void setClassificationExport(ClassificationExport classificationsExport) {
        this.classificationsExport = classificationsExport;
    }
    
}
