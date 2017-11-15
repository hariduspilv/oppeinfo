package ee.hois.xroad.sais2.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ee.hois.xroad.helpers.XRoadHeaderV4;
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
        XRoadHeaderV4 header = header("ClassificationsExport", null);

        SaisClassificationResponse response = saisClient.classificationsExport(header);
        Assert.assertNull("expecting no errors", response.getLog().getError());
        Assert.assertFalse("expecting some response", response.getLog().getIncomingXml() == null);
    }

    @Test
    public void testAdmissionExport() throws DatatypeConfigurationException {
        XRoadHeaderV4 header = header("AllAdmissionsExport", null);

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
        XRoadHeaderV4 header = header("AllApplicationsExport", null);

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

    private static XRoadHeaderV4 header(String serviceCode, String serviceVersion) {
        XRoadHeaderV4 header = new XRoadHeaderV4();

        XRoadHeaderV4.Client client = new XRoadHeaderV4.Client();
        client.setXRoadInstance("ee-test");
        client.setMemberClass("COM");
        client.setMemberCode("10239452");
        client.setSubSystemCode("generic-consumer");
        header.setClient(client);

        XRoadHeaderV4.Service service = new XRoadHeaderV4.Service();
        service.setxRoadInstance("ee-test");
        service.setMemberClass("NGO");
        service.setMemberCode("90005872");
        service.setSubsystemCode("sais2");
        service.setServiceCode(serviceCode);
        service.setServiceVersion(serviceVersion);
        header.setService(service);

        header.setUserId("EE30101010007");
        header.setId(UUID.randomUUID().toString());
        header.setEndpoint("http://141.192.105.178/cgi-bin/consumer_proxy");

        return header;
    }
}
