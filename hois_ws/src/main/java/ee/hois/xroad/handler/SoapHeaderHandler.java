package ee.hois.xroad.handler;

import java.lang.invoke.MethodHandles;

import javax.xml.namespace.QName;
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
import ee.hois.xroad.helpers.XRoadHeader;

public class SoapHeaderHandler extends SoapHandler {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    protected boolean handleOutgoing(SOAPMessageContext context) {
        XRoadHeader xRoadHeader = (XRoadHeader) context.get(XRoadHeader.XROAD_HEADER);
        if(xRoadHeader == null) {
            xRoadHeader = new XRoadHeader();
        }

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

            super.handleOutgoing(context);
        } catch (SOAPException e) {
            LogContext response = ctx(context);
            response.setError(e);
            log.error("handling soap message failed", e);
        }
        return true;
    }
}
