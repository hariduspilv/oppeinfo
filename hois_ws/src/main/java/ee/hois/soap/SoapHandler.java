package ee.hois.soap;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoapHandler implements SOAPHandler<SOAPMessageContext> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String LOG_CONTEXT = "ee.hois.soap.LOG_CONTEXT";
    public static final String ATTACHMENT = "ee.hois.soap.ATTACHMENT";

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        if(Boolean.TRUE.equals(context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY))) {
            return handleOutgoing(context);
        }
        return handleIncoming(context);
    }

    protected boolean handleOutgoing(SOAPMessageContext context) {
        LogContext ctx = ctx(context);
        try {
            SOAPMessage msg = context.getMessage();
            String msgXml = SoapUtil.asPrettyString(msg);
            ctx.setOutgoingXml(msgXml);
            log.debug("Request {} outgoing message: {}", ctx.getId(), msgXml);
        } catch (SOAPException | IOException e) {
            log.error("logging soap outgoing message failed", e);
        }
        try {
            SOAPMessage msg = context.getMessage();
            SoapAttachment attachment = attachment(context);
            if (attachment != null) {
                byte[] content = attachment.getContent();
                AttachmentPart attachmentPart = msg.createAttachmentPart();
                attachmentPart.setRawContentBytes(content, 0, content.length, attachment.getContentType());
                attachmentPart.setContentId(attachment.getContentId());
                msg.addAttachmentPart(attachmentPart);
                msg.saveChanges();
            }
        } catch (SOAPException e) {
            log.error("adding attachment to soap outgoing message failed", e);
        }
        return true;
    }

    protected boolean handleIncoming(SOAPMessageContext context) {
        LogContext ctx = ctx(context);
        try {
            SOAPMessage msg = context.getMessage();
            String msgXml = SoapUtil.asPrettyString(msg);
            ctx.setIncomingXml(msgXml);
            log.debug(msgXml);
            log.debug("Request {} incoming message: {}", ctx.getId(), msgXml);
        } catch (SOAPException | IOException e) {
            log.error("logging soap incoming message failed", e);
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        LogContext ctx = ctx(context);
        try {
            SOAPMessage soapMsg = context.getMessage();
            ctx.setError(new SOAPFaultException(soapMsg.getSOAPBody().getFault()));

            String msgXml = SoapUtil.asPrettyString(soapMsg);
            if(Boolean.TRUE.equals(context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY))) {
                ctx.setOutgoingXml(msgXml);
            } else {
                ctx.setIncomingXml(msgXml);
            }
            log.debug("Request {} fault: {}", ctx.getId(), msgXml);
        } catch (SOAPException | IOException e) {
            log.error("logging soap fault failed", e);
        }
        return false;
    }

    protected LogContext ctx(SOAPMessageContext context) {
        LogContext ctx = (LogContext) context.get(LOG_CONTEXT);
        return ctx;
    }

    protected SoapAttachment attachment(SOAPMessageContext context) {
        SoapAttachment attachment = (SoapAttachment) context.get(ATTACHMENT);
        return attachment;
    }

    @Override
    public void close(MessageContext context) {
        log.debug("soap request {} got response {}", context.get(MessageContext.WSDL_OPERATION),
                context.get(MessageContext.HTTP_RESPONSE_CODE));
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }
}
