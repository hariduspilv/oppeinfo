package ee.hitsa.ois.mock;

import java.util.Collections;

import ee.hois.soap.LogContext;
import ee.hois.xroad.ehis.generated.KhlOppeasutusList;
import ee.hois.xroad.ehis.generated.OppeasutusList;
import ee.hois.xroad.ehis.generated.OppejoudList;
import ee.hois.xroad.ehis.service.EhisClient;
import ee.hois.xroad.ehis.service.EhisLaeKorgharidusedResponse;
import ee.hois.xroad.ehis.service.EhisLaeOppejoudResponse;
import ee.hois.xroad.ehis.service.EhisLaePedagoogidResponse;
import ee.hois.xroad.helpers.XRoadHeaderV4;

public class MockEhisClient extends EhisClient {

    @Override
    public EhisLaeKorgharidusedResponse laeKorgharidused(XRoadHeaderV4 header, KhlOppeasutusList laeKorgharidus) {
        LogContext log = header.logContext();
        log.setOutgoingXml("Test - päringut ei koostatud");
        return new EhisLaeKorgharidusedResponse(log, Collections.emptyList(), null, null);
    }

    @Override
    public EhisLaeOppejoudResponse laeOppejoud(XRoadHeaderV4 header, OppejoudList oppejoudList) {
        LogContext log = header.logContext();
        log.setOutgoingXml("Test - päringut ei koostatud");
        return new EhisLaeOppejoudResponse(log, Collections.emptyList(), null, null);
    }

    @Override
    public EhisLaePedagoogidResponse laePedagoogid(XRoadHeaderV4 header, OppeasutusList oppeasutusList) {
        LogContext log = header.logContext();
        log.setOutgoingXml("Test - päringut ei koostatud");
        return new EhisLaePedagoogidResponse(log, Collections.emptyList(), null, null);
    }
}
