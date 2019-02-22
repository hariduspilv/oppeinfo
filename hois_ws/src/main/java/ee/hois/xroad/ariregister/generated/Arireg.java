package ee.hois.xroad.ariregister.generated;

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
 * 2018-12-03T17:38:09.257+02:00
 * Generated source version: 3.1.14
 * 
 */
@WebServiceClient(name = "arireg", 
                  wsdlLocation = "file:src/main/resources/wsdl/ariregister/ariregister.wsdl",
                  targetNamespace = "http://arireg.x-road.eu/producer/") 
@HandlerChain(file = "/handler/ariregister/handler-chain.xml")
public class Arireg extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://arireg.x-road.eu/producer/", "arireg");
    public final static QName AriregXtee = new QName("http://arireg.x-road.eu/producer/", "AriregXtee");
    static {
        URL url = null;
        try {
            url = new URL("file:src/main/resources/wsdl/ariregister/ariregister.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(Arireg.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:src/main/resources/wsdl/ariregister/ariregister.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public Arireg(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public Arireg(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Arireg() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public Arireg(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public Arireg(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public Arireg(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns AriregXtee
     */
    @WebEndpoint(name = "AriregXtee")
    public AriregXtee getAriregXtee() {
        return super.getPort(AriregXtee, AriregXtee.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AriregXtee
     */
    @WebEndpoint(name = "AriregXtee")
    public AriregXtee getAriregXtee(WebServiceFeature... features) {
        return super.getPort(AriregXtee, AriregXtee.class, features);
    }

}