package ee.hois.xroad.ehis.service;

import ee.hois.xroad.ehis.generated.EhisPortType;
import ee.hois.xroad.ehis.generated.EhisService;

import ee.hois.xroad.ehis.generated.KhlOppeasutusList;
import ee.hois.xroad.ehis.generated.OppejoudList;
import ee.hois.xroad.ehis.generated.StrArray;
import ee.hois.xroad.helpers.XRoadHeaderV4;
import ee.hois.xroad.helpers.XRoadResponse;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.math.BigInteger;
import java.util.Map;

public class EhisXroadService {

    private EhisService service;

    public EhisXroadService() {
        service = new EhisService();
    }

    public EhisLaeKorgharidusedResponse laeKorgharidused(XRoadHeaderV4 header, KhlOppeasutusList laeKorgharidus) {
        EhisLaeKorgharidusedResponse response;
        EhisPortType port = service.getEhisPort();
        Map<String, Object> context = ((BindingProvider) port).getRequestContext();
        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, header.getEndpoint());
        context.put("xRoadHeader", header);
        Holder<String> teade = new Holder<>();
        Holder<BigInteger> kood = new Holder<>();
        Holder<StrArray> data = new Holder<>();

        try {
            port.laeKorgharidus(laeKorgharidus, teade, kood, data);

            Map<String, Object> responseContext = ((BindingProvider) port).getResponseContext();

            XRoadResponse xRoadResponse = (XRoadResponse) responseContext.get("xRoadResponse");
            response = new EhisLaeKorgharidusedResponse(xRoadResponse);

            response.setLogTxt(String.join("; ", data.value.getItem()));
        } catch (Exception e) {
            Map<String, Object> responseContext = ((BindingProvider) port).getResponseContext();
            if (responseContext.containsKey("xRoadResponse")) {
                response = new EhisLaeKorgharidusedResponse((XRoadResponse)responseContext.get("xRoadResponse"));
            } else {
                response = new EhisLaeKorgharidusedResponse();
            }
            response.setError(e.toString());
            if (response.getxRoadErrors() != Boolean.TRUE) {
                response.setHasOtherErrors(Boolean.TRUE);
            }
        }
        return response;
    }
    
    public EhisLaeOppejoudResponse laeOppejoud(XRoadHeaderV4 header, OppejoudList oppejoudList) {
        EhisLaeOppejoudResponse response;
        EhisPortType port = service.getEhisPort();
        Map<String, Object> context = ((BindingProvider) port).getRequestContext();
        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, header.getEndpoint());
        context.put("xRoadHeader", header);
        Holder<String> teade = new Holder<>();
        Holder<BigInteger> kood = new Holder<>();
        Holder<StrArray> data = new Holder<>();

        try {
            port.laeOppejoud(oppejoudList, teade, kood, data);

            Map<String, Object> responseContext = ((BindingProvider) port).getResponseContext();

            XRoadResponse xRoadResponse = (XRoadResponse) responseContext.get("xRoadResponse");
            response = new EhisLaeOppejoudResponse(xRoadResponse);

            response.setLogTxt(String.join("; ", data.value.getItem()));
        } catch (Exception e) {
            Map<String, Object> responseContext = ((BindingProvider) port).getResponseContext();
            if (responseContext.containsKey("xRoadResponse")) {
                response = new EhisLaeOppejoudResponse((XRoadResponse)responseContext.get("xRoadResponse"));
            } else {
                response = new EhisLaeOppejoudResponse();
            }
            response.setError(e.toString());
            if (response.getxRoadErrors() != Boolean.TRUE) {
                response.setHasOtherErrors(Boolean.TRUE);
            }
        }
        return response;
    }
}
