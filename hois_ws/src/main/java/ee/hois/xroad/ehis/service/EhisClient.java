package ee.hois.xroad.ehis.service;

import java.util.Map;

import javax.xml.ws.BindingProvider;

import ee.hois.soap.LogContext;
import ee.hois.soap.SoapHandler;
import ee.hois.soap.SoapUtil;
import ee.hois.xroad.ehis.generated.EhisPortType;
import ee.hois.xroad.ehis.generated.EhisService;
import ee.hois.xroad.ehis.generated.KhlOppeasutusList;
import ee.hois.xroad.ehis.generated.LaeKorgharidus;
import ee.hois.xroad.ehis.generated.LaeKorgharidusResponse;
import ee.hois.xroad.ehis.generated.LaeOppejoud;
import ee.hois.xroad.ehis.generated.LaeOppejoudResponse;
import ee.hois.xroad.ehis.generated.LaePedagoogid;
import ee.hois.xroad.ehis.generated.LaePedagoogidResponse;
import ee.hois.xroad.ehis.generated.OisOppekava;
import ee.hois.xroad.ehis.generated.OisResponse;
import ee.hois.xroad.ehis.generated.OppeasutusList;
import ee.hois.xroad.ehis.generated.OppejoudList;
import ee.hois.xroad.helpers.XRoadHeaderV4;

public class EhisClient {

    private final EhisService service = new EhisService();

    public EhisLaeKorgharidusedResponse laeKorgharidused(XRoadHeaderV4 header, KhlOppeasutusList laeKorgharidus) {
        EhisPortType port = initializePort(header);
        LogContext ctx = ctx(port);

        EhisLaeKorgharidusedResponse result = SoapUtil.withExceptionHandler(ctx, () -> {
            LaeKorgharidus req = new LaeKorgharidus();
            req.setData(laeKorgharidus);
            LaeKorgharidusResponse resp = port.laeKorgharidus(req);
            return new EhisLaeKorgharidusedResponse(ctx, resp.getData().getValue().getItem(), resp.getTeade().getValue(), resp.getKood().getValue());
        });
        return result != null ? result: new EhisLaeKorgharidusedResponse(ctx, null, null, null);
    }

    public EhisLaeOppejoudResponse laeOppejoud(XRoadHeaderV4 header, OppejoudList oppejoudList) {
        EhisPortType port = initializePort(header);
        LogContext ctx = ctx(port);

        EhisLaeOppejoudResponse result = SoapUtil.withExceptionHandler(ctx, () -> {
            LaeOppejoud req = new LaeOppejoud();
            req.setData(oppejoudList);
            LaeOppejoudResponse resp = port.laeOppejoud(req);
            return new EhisLaeOppejoudResponse(ctx, resp.getData().getValue().getItem(), resp.getTeade().getValue(), resp.getKood().getValue());
        });
        return result != null ? result : new EhisLaeOppejoudResponse(ctx, null, null, null);
    }

    public EhisLaePedagoogidResponse laePedagoogid(XRoadHeaderV4 header, OppeasutusList oppeasutusList) {
        EhisPortType port = initializePort(header);
        LogContext ctx = ctx(port);

        EhisLaePedagoogidResponse result = SoapUtil.withExceptionHandler(ctx, () -> {
            LaePedagoogid req = new LaePedagoogid();
            req.setData(oppeasutusList);
            LaePedagoogidResponse resp = port.laePedagoogid(req);
            return new EhisLaePedagoogidResponse(ctx, resp.getData().getValue().getItem(), resp.getTeade().getValue(), resp.getKood().getValue());
        });
        return result != null ? result : new EhisLaePedagoogidResponse(ctx, null, null, null);
    }

    public EhisOisOppekavaResponse oisOppekava(XRoadHeaderV4 header, OisOppekava oisOppekava) {
        EhisPortType port = initializePort(header);
        LogContext ctx = ctx(port);

        EhisOisOppekavaResponse result = SoapUtil.withExceptionHandler(ctx, () -> {
            OisResponse resp = port.oisOppekava(oisOppekava);
            return new EhisOisOppekavaResponse(ctx, resp.getInfoteated().getInfoteade());
        });
        return result != null ? result : new EhisOisOppekavaResponse(ctx, null);
    }

    private EhisPortType initializePort(XRoadHeaderV4 header) {
        EhisPortType port = service.getEhisPort();
        Map<String, Object> context = ((BindingProvider) port).getRequestContext();
        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, header.getEndpoint());
        context.put(XRoadHeaderV4.XROAD_HEADER, header);
        context.put(SoapHandler.LOG_CONTEXT, header.logContext());
        return port;
    }

    private static LogContext ctx(EhisPortType port) {
        return (LogContext)((BindingProvider) port).getRequestContext().get(SoapHandler.LOG_CONTEXT);
    }
}
