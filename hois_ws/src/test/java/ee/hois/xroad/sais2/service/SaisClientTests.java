package ee.hois.xroad.sais2.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ee.hois.xroad.helpers.XRoadHeader;
import ee.hois.xroad.sais2.generated.AllAdmissionsExportRequest;
import ee.hois.xroad.sais2.generated.AllAppsExportRequest;
import ee.hois.xroad.sais2.generated.ArrayOfInt;

public class SaisClientTests {

    private SaisClient saisClient;

    @Before
    public void setUp() throws Exception {
        if(saisClient == null) {
            saisClient = new SaisClient();
        }
    }

    @Test
    public void testClassificationExport() {
        XRoadHeader header = new XRoadHeader();
        header.setConsumer("10239452");
        header.setProducer("sais2");
        header.setUserId("EE30101010007");
        header.setId("3aed1ae3813eb7fbed9396fda70ca1215d3f3fe1");
        header.setService("sais2.ClassificationsExport.v1");
        header.setEndpoint("http://141.192.105.184/cgi-bin/consumer_proxy");

        SaisClassificationResponse response = saisClient.classificationsExport(header);
        Assert.assertNull("expecting no errors", response.getLog().getError());
        Assert.assertFalse("expecting some response", response.getLog().getIncomingXml() == null);
    }

    @Test
    public void testAdmissionExport() throws DatatypeConfigurationException {
        XRoadHeader header = new XRoadHeader();
        header.setConsumer("10239452");
        header.setProducer("sais2");
        header.setUserId("EE30101010007");
        header.setId("3aed1ae3813eb7fbed9396fda70ca1215d3f3fe1");
        header.setService("sais2.AllAdmissionsExport.v1");
        header.setEndpoint("http://141.192.105.184/cgi-bin/consumer_proxy");

        GregorianCalendar cal = new GregorianCalendar();
        AllAdmissionsExportRequest request = new AllAdmissionsExportRequest();
        cal.setTime(new Date(LocalDate.parse("2016-09-01").toEpochDay()));
        request.setCreateDateFrom(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
        cal.setTime(new Date(LocalDate.parse("2017-09-01").toEpochDay()));
        request.setCreateDateTo(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
        SaisAdmissionResponse response = saisClient.admissionsExport(header, request);
        Assert.assertNull("expecting no errors", response.getLog().getError());
        Assert.assertFalse("expecting some response", response.getLog().getIncomingXml() == null);
    }

    @Test
    public void testApplicationExport() throws DatatypeConfigurationException {
        XRoadHeader header = new XRoadHeader();
        header.setConsumer("10239452");
        header.setProducer("sais2");
        header.setUserId("EE30101010007");
        header.setId("3aed1ae3813eb7fbed9396fda70ca1215d3f3fe1");
        header.setService("sais2.AllApplicationsExport.v1");
        header.setEndpoint("http://141.192.105.184/cgi-bin/consumer_proxy");

        GregorianCalendar cal = new GregorianCalendar();
        AllAppsExportRequest request = new AllAppsExportRequest();
        cal.setTime(new Date(LocalDate.parse("2017-05-01").toEpochDay()));
        request.setStatusChangeDateFrom(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
        cal.setTime(new Date(LocalDate.parse("2017-09-01").toEpochDay()));
        request.setStatusChangeDateTo(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));

        ArrayOfInt regCodes = new ArrayOfInt();
        regCodes.getInt().add(Integer.valueOf(10239452));
        request.setInstitutionRegCodes(regCodes);
        SaisApplicationResponse response = saisClient.applicationsExport(header, request);
        Assert.assertNull("expecting no errors", response.getLog().getError());
        Assert.assertFalse("expecting some response", response.getLog().getIncomingXml() == null);
    }
}
