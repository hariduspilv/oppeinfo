package ee.hitsa.ois.services;

import java.util.List;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.cxf.jaxws.handler.soap.SOAPMessageContextImpl;
import org.springframework.stereotype.Component;

import ee.hitsa.ois.service.ekis.EkisLogService;
import ee.hois.soap.LogContext;
import ee.hois.soap.SoapHandler;

@Component
public class LoggingSoapHandler extends SoapHandler {

    @Resource
    private EkisLogService ekisLogService;

    @Override
    public void close(MessageContext context) {
        logRequest((SOAPMessageContext)context);
        super.close(context);
    }

    private void logRequest(SOAPMessageContext context) {
        LogContext logContext = ctx(context);
        // get request for associating with log
        List<?> body = ((SOAPMessageContextImpl)context).getWrappedMessage().getExchange().getInMessage().getContent(List.class);
        Object request = null;
        if(body != null && body.size() == 1) {
            request = body.get(0);
        }
        ekisLogService.insertLog(logContext, request);
    }

    @Override
    protected LogContext ctx(SOAPMessageContext context) {
        LogContext ctx = (LogContext) context.get(LOG_CONTEXT);
        if(ctx == null) {
            QName operation = (QName)context.get(MessageContext.WSDL_OPERATION);
            String queryName = operation != null ? operation.getLocalPart() : null;
            ctx = new LogContext(null, queryName);
            context.put(LOG_CONTEXT, ctx);
        }
        return ctx;
    }
}
