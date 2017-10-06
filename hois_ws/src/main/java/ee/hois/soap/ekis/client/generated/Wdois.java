package ee.hois.soap.ekis.client.generated;

import java.net.MalformedURLException;
import java.net.URL;

import javax.jws.HandlerChain;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.13
 * 2017-09-20T17:07:42.763+03:00
 * Generated source version: 3.1.13
 * 
 */
@WebServiceClient(name = "wdois", 
                  wsdlLocation = "file:src/main/resources/wsdl/ekis/ekis_toend.wsdl",
                  targetNamespace = "urn:ekis") 
@HandlerChain(file="/handler/ekis/handler-chain.xml")
public class Wdois extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("urn:ekis", "wdois");
    public final static QName WdoisPort = new QName("urn:ekis", "wdoisPort");
    static {
        URL url = null;
        try {
            url = new URL("file:src/main/resources/wsdl/ekis/ekis_toend.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(Wdois.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:src/main/resources/wsdl/ekis/ekis_toend.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public Wdois(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public Wdois(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Wdois() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public Wdois(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public Wdois(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public Wdois(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns WdoisPortType
     */
    @WebEndpoint(name = "wdoisPort")
    public WdoisPortType getWdoisPort() {
        return super.getPort(WdoisPort, WdoisPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WdoisPortType
     */
    @WebEndpoint(name = "wdoisPort")
    public WdoisPortType getWdoisPort(WebServiceFeature... features) {
        return super.getPort(WdoisPort, WdoisPortType.class, features);
    }

}
