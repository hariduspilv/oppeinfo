package ee.hois.xroad.handler;

import java.lang.invoke.MethodHandles;
import java.util.UUID;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.hois.soap.LogContext;
import ee.hois.soap.SoapHandler;
import ee.hois.xroad.helpers.XRoadHeaderV4;

public class XRoadV4HeaderHandler extends SoapHandler {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String XROAD_NAMESPACE = "http://x-road.eu/xsd/xroad.xsd";
    private static final String XROAD_IDENTIFIERS = "http://x-road.eu/xsd/identifiers";

    @Override
    protected boolean handleOutgoing(SOAPMessageContext context) {
        XRoadHeaderV4 header = (XRoadHeaderV4) context.get(XRoadHeaderV4.XROAD_HEADER);
        if(header == null) {
            header = new XRoadHeaderV4();
        }

        SOAPMessage message = context.getMessage();
        try {
            SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
            SOAPHeader soapHeader = envelope.getHeader();
            // if no header, add one
            if (soapHeader == null) {
                soapHeader = envelope.addHeader();
            }

            QName qClient = new QName(XROAD_NAMESPACE, "client", "xrd");
            SOAPHeaderElement client = soapHeader.addHeaderElement(qClient);

            client.addAttribute(new QName(XROAD_IDENTIFIERS, "objectType", "id"), "SUBSYSTEM");

            SOAPElement xRoadInstance = client.addChildElement(new QName(XROAD_IDENTIFIERS, "xRoadInstance", "id"));
            SOAPElement memberClass = client.addChildElement(new QName(XROAD_IDENTIFIERS, "memberClass", "id"));
            SOAPElement memberCode = client.addChildElement(new QName(XROAD_IDENTIFIERS, "memberCode", "id"));
            SOAPElement subsystemCode = client.addChildElement(new QName(XROAD_IDENTIFIERS, "subsystemCode", "id"));

            xRoadInstance.addTextNode(header.getClient().getXRoadInstantce());
            memberClass.addTextNode(header.getClient().getMemberClass());
            memberCode.addTextNode(header.getClient().getMemberCode());
            subsystemCode.addTextNode(header.getClient().getSubSystemCode());

            SOAPHeaderElement service = soapHeader.addHeaderElement(new QName(XROAD_NAMESPACE, "service", "xrd"));

            service.addAttribute(new QName(XROAD_IDENTIFIERS, "objectType", "id"), "SERVICE");

            SOAPElement serviceXRoadInstance = service.addChildElement(new QName(XROAD_IDENTIFIERS, "xRoadInstance", "id"));
            SOAPElement serviceMemberClass = service.addChildElement(new QName(XROAD_IDENTIFIERS, "memberClass", "id"));
            SOAPElement serviceMemberCode = service.addChildElement(new QName(XROAD_IDENTIFIERS, "memberCode", "id"));
            SOAPElement serviceSubsystem = service.addChildElement(new QName(XROAD_IDENTIFIERS, "subsystemCode", "id"));
            SOAPElement serviceServiceCode = service.addChildElement(new QName(XROAD_IDENTIFIERS, "serviceCode", "id"));
            SOAPElement serviceVersion = service.addChildElement(new QName(XROAD_IDENTIFIERS, "serviceVersion", "id"));

            serviceXRoadInstance.addTextNode(header.getService().getxRoadInstance());
            serviceMemberClass.addTextNode(header.getService().getMemberClass());
            serviceMemberCode.addTextNode(header.getService().getMemberCode());
            serviceSubsystem.addTextNode(header.getService().getSubsystemCode());
            serviceServiceCode.addTextNode(header.getService().getServiceCode());
            serviceVersion.addTextNode(header.getService().getServiceVersion());

            if (header.getUserId() != null && !header.getUserId().isEmpty()) {
                SOAPElement userId = soapHeader.addHeaderElement(new QName(XROAD_NAMESPACE, "userId", "xrd"));
                userId.addTextNode(header.getUserId());
            }

            SOAPElement uuid = soapHeader.addHeaderElement(new QName(XROAD_NAMESPACE, "id", "xrd"));
            uuid.addTextNode(UUID.randomUUID().toString());

            SOAPElement protocolVersion = soapHeader.addHeaderElement(new QName(XROAD_NAMESPACE, "protocolVersion", "xrd"));
            protocolVersion.addTextNode(XRoadHeaderV4.getProtocolVersion());

            message.saveChanges();

            super.handleOutgoing(context);
        } catch (SOAPException e) {
            LogContext response = ctx(context);
            response.setError(e);
            log.error("sending soap message failed", e);
        }
        return true;
    }
}
