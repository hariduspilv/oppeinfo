package ee.hois.xroad.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import ee.hois.xroad.helpers.XRoadHeaderV4;
import ee.hois.xroad.helpers.XRoadResponse;

public class XRoadV4HeaderHandler implements SOAPHandler<SOAPMessageContext> {

    private static final String XROAD_NAMESPACE = "http://x-road.eu/xsd/xroad.xsd";
    private static final String XROAD_IDENTIFIERS = "http://x-road.eu/xsd/identifiers";

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        XRoadHeaderV4 header = (XRoadHeaderV4) context.get("xRoadHeader");


        XRoadResponse response;
        if (context.containsKey("xRoadResponse")) {
            response = (XRoadResponse) context.get("xRoadResponse");
        } else {
            response = new XRoadResponse();
        }

        SOAPMessage message = context.getMessage();

        if (Boolean.TRUE.equals(isRequest)) {
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

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                message.writeTo(out);
                response.setXmlQuery(new String(out.toByteArray()));

            } catch (SOAPException | IOException e) {
                response.setxRoadErrors(Boolean.TRUE);
                response.setError(e.toString());

            }
        } else {
            setResponse(context, response);
        }
        context.put("xRoadResponse", response);
        context.setScope("xRoadResponse", MessageContext.Scope.APPLICATION);

        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        XRoadResponse response;
        if (context.containsKey("xRoadResponse")) {
            response = (XRoadResponse) context.get("xRoadResponse");
        } else {
            response = new XRoadResponse();
            context.put("xRoadResponse", response);
        }
        setResponse(context, response);
        response.setxRoadErrors(Boolean.TRUE);
        return false;
    }

    @Override
    public void close(MessageContext context) {

    }

    private static void setResponse(SOAPMessageContext context, XRoadResponse response) {
        try {
            SOAPMessage soapMsg = context.getMessage();
            SOAPBody soapBody = soapMsg.getSOAPBody();
            if (soapBody.hasFault()) {
                response.setError(soapBody.getFault().getFaultString());
                response.setxRoadErrors(Boolean.TRUE);
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            soapMsg.writeTo(out);
            response.setXmlResponse(new String(out.toByteArray()));
        } catch (Exception e) {
            //TODO: use logger
            e.printStackTrace();
        }
    }
}
