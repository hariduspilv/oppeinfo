package ee.hois.xroad.ehis.service;

import java.math.BigInteger;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import ee.hois.soap.LogContext;
import ee.hois.soap.SoapHandler;
import ee.hois.soap.SoapUtil;
import ee.hois.xroad.ehis.generated.EhisPortType;
import ee.hois.xroad.ehis.generated.EhisService;
import ee.hois.xroad.ehis.generated.KhlOppeasutusList;
import ee.hois.xroad.ehis.generated.OppeasutusList;
import ee.hois.xroad.ehis.generated.OppejoudList;
import ee.hois.xroad.ehis.generated.StrArray;
import ee.hois.xroad.helpers.XRoadHeaderV4;

public class EhisClient {

    private final EhisService service = new EhisService();

    public EhisLaeKorgharidusedResponse laeKorgharidused(XRoadHeaderV4 header, KhlOppeasutusList laeKorgharidus) {
        EhisPortType port = initializePort(header);
        LogContext ctx = ctx(port);

        EhisLaeKorgharidusedResponse result = SoapUtil.withExceptionHandler(ctx, () -> {
            Holder<String> teade = new Holder<>();
            Holder<BigInteger> kood = new Holder<>();
            Holder<StrArray> data = new Holder<>();

            port.laeKorgharidus(laeKorgharidus, teade, kood, data);
            return new EhisLaeKorgharidusedResponse(ctx, data.value.getItem(), teade.value, kood.value);
        });
        return result != null ? result: new EhisLaeKorgharidusedResponse(ctx, null, null, null);
    }

    public EhisLaeOppejoudResponse laeOppejoud(XRoadHeaderV4 header, OppejoudList oppejoudList) {
        EhisPortType port = initializePort(header);
        LogContext ctx = ctx(port);

        EhisLaeOppejoudResponse result = SoapUtil.withExceptionHandler(ctx, () -> {
            Holder<String> teade = new Holder<>();
            Holder<BigInteger> kood = new Holder<>();
            Holder<StrArray> data = new Holder<>();

            port.laeOppejoud(oppejoudList, teade, kood, data);
            return new EhisLaeOppejoudResponse(ctx, data.value.getItem(), teade.value, kood.value);
        });
        return result != null ? result : new EhisLaeOppejoudResponse(ctx, null, null, null);
    }

    public EhisLaePedagoogidResponse laePedagoogid(XRoadHeaderV4 header, OppeasutusList oppeasutusList) {
        EhisPortType port = initializePort(header);
        LogContext ctx = ctx(port);

        EhisLaePedagoogidResponse result = SoapUtil.withExceptionHandler(ctx, () -> {
            Holder<String> teade = new Holder<>();
            Holder<BigInteger> kood = new Holder<>();
            Holder<StrArray> data = new Holder<>();

            port.laePedagoogid(oppeasutusList, teade, kood, data);
            return new EhisLaePedagoogidResponse(ctx, data.value.getItem(), teade.value, kood.value);
        });
        return result != null ? result : new EhisLaePedagoogidResponse(ctx, null, null, null);
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
