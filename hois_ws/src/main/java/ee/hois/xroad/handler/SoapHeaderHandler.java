package ee.hois.xroad.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import ee.hois.xroad.helpers.XRoadHeader;
import ee.hois.xroad.helpers.XRoadResponse;

public class SoapHeaderHandler implements SOAPHandler<SOAPMessageContext> {

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        XRoadResponse xRoadResponse;
        XRoadHeader xRoadHeader;
        if (context.containsKey("xRoadResponse")) {
            xRoadResponse = (XRoadResponse) context.get("xRoadResponse");
        } else {
            xRoadResponse = new XRoadResponse();
        }
        if (context.containsKey("xRoadHeader")) {
            xRoadHeader = (XRoadHeader) context.get("xRoadHeader");
        } else {
            xRoadHeader = new XRoadHeader();
        }

        // if this is a request, true for outbound messages, false for inbound
        if (isRequest) {
            try {
                SOAPMessage soapMsg = context.getMessage();
                SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
                SOAPHeader soapHeader = soapEnv.getHeader();

                // if no header, add one
                if (soapHeader == null) {
                    soapHeader = soapEnv.addHeader();
                }

                QName qConsumer = new QName("http://x-road.ee/xsd/x-road.xsd", "consumer");
                QName qProducer = new QName("http://x-road.ee/xsd/x-road.xsd", "producer");
                QName qUserId = new QName("http://x-road.ee/xsd/x-road.xsd", "userId");
                QName qId = new QName("http://x-road.ee/xsd/x-road.xsd", "id");
                QName qService = new QName("http://x-road.ee/xsd/x-road.xsd", "service");

                SOAPHeaderElement consumerElement = soapHeader.addHeaderElement(qConsumer);
                SOAPHeaderElement producerElement = soapHeader.addHeaderElement(qProducer);
                SOAPHeaderElement userIdElement = soapHeader.addHeaderElement(qUserId);
                SOAPHeaderElement idElement = soapHeader.addHeaderElement(qId);
                SOAPHeaderElement serviceElement = soapHeader.addHeaderElement(qService);

                consumerElement.addTextNode(xRoadHeader.getConsumer());
                producerElement.addTextNode(xRoadHeader.getProducer());
                userIdElement.addTextNode(xRoadHeader.getUserId());
                idElement.addTextNode(xRoadHeader.getId());
                serviceElement.addTextNode(xRoadHeader.getService());

                soapMsg.saveChanges();

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                soapMsg.writeTo(out);
                xRoadResponse.setXmlQuery(new String(out.toByteArray()));

            } catch (SOAPException e) {
                System.err.println(e);
            } catch (IOException e) {
                System.err.println(e);
            }

        } else {
            try {
                SOAPMessage soapMsg = context.getMessage();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                soapMsg.writeTo(out);
                xRoadResponse.setXmlResponse(new String(out.toByteArray()));
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        context.put("xRoadResponse", xRoadResponse);
        context.setScope("xRoadResponse", MessageContext.Scope.APPLICATION);
        return true;
    }

    @Override
    public void close(MessageContext arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean handleFault(SOAPMessageContext arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Set<QName> getHeaders() {
        // TODO Auto-generated method stub
        return null;
    }

}
