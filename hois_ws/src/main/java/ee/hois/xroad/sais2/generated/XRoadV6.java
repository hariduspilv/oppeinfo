package ee.hois.xroad.sais2.generated;

import java.net.MalformedURLException;
import java.net.URL;

import javax.jws.HandlerChain;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.14
 * 2019-03-26T09:16:46.506+02:00
 * Generated source version: 3.1.14
 * 
 */
@WebServiceClient(name = "XRoadV6", 
                  wsdlLocation = "file:src/main/resources/wsdl/sais/sais3.wsdl",
                  targetNamespace = "http://sais2.x-road.eu/")
@HandlerChain(file="/handler/sais/handler-chain.xml")
public class XRoadV6 extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://sais2.x-road.eu/", "XRoadV6");
    public final static QName XRoadSoap = new QName("http://sais2.x-road.eu/", "XRoadSoap");
    static {
        URL url = null;
        try {
            url = new URL("file:src/main/resources/wsdl/sais/sais3.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(XRoadV6.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:src/main/resources/wsdl/sais/sais3.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public XRoadV6(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public XRoadV6(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public XRoadV6() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public XRoadV6(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public XRoadV6(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public XRoadV6(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns XRoadSoap
     */
    @WebEndpoint(name = "XRoadSoap")
    public XRoadSoap getXRoadSoap() {
        return super.getPort(XRoadSoap, XRoadSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns XRoadSoap
     */
    @WebEndpoint(name = "XRoadSoap")
    public XRoadSoap getXRoadSoap(WebServiceFeature... features) {
        return super.getPort(XRoadSoap, XRoadSoap.class, features);
    }

}
