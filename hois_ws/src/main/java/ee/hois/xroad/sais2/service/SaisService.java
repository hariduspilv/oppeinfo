package ee.hois.xroad.sais2.service;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import ee.hois.xroad.helpers.XRoadHeader;
import ee.hois.xroad.helpers.XRoadResponse;
import ee.hois.xroad.helpers.sais.SaisAdmissionResponse;
import ee.hois.xroad.helpers.sais.SaisApplicationResponse;
import ee.hois.xroad.helpers.sais.SaisClassificationResponse;
import ee.hois.xroad.sais2.generated.AdmissionExportResponse;
import ee.hois.xroad.sais2.generated.AllAdmissionsExportRequest;
import ee.hois.xroad.sais2.generated.AllAppsExportRequest;
import ee.hois.xroad.sais2.generated.AppExportResponse;
import ee.hois.xroad.sais2.generated.ClassificationExport;
import ee.hois.xroad.sais2.generated.EmptyParameters;
import ee.hois.xroad.sais2.generated.XRoad;
import ee.hois.xroad.sais2.generated.XRoadSoap;

public class SaisService {
    private static final QName SERVICE_NAME = new QName("http://sais2.x-road.ee/producer/", "XRoad");
    private static final String XROAD_HEADER = "xRoadHeader";
    private static final String XROAD_RESPONSE = "xRoadResponse";
    private static final String WSDL = "sais3.wsdl";

    public SaisClassificationResponse classificationsExport(XRoadHeader xRoadHeader) {
        SaisClassificationResponse responseObject = null;
        try {
            URL wsdlUrl = SaisService.class.getResource(WSDL);
            XRoad ss = new XRoad(wsdlUrl, SERVICE_NAME);
            XRoadSoap port = ss.getXRoadSoap();

            Map<String, Object> context = ((BindingProvider) port).getRequestContext();
            context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, xRoadHeader.getEndpoint());
            context.put(XROAD_HEADER, xRoadHeader);

            EmptyParameters emptyParams = new EmptyParameters();
            Holder<EmptyParameters> request = new Holder<>(emptyParams);
            Holder<ClassificationExport> response = new Holder<>();

            port.classificationsExport(request, response);

            Map<String, Object> responseContext = ((BindingProvider) port).getResponseContext();

            XRoadResponse xRoadResponse = (XRoadResponse) responseContext.get(XROAD_RESPONSE);
            responseObject = new SaisClassificationResponse(xRoadResponse);
            responseObject.setQueryName(xRoadHeader.getService());
            responseObject.setClassificationExport(response.value);
        } catch (Exception e) {
            if(responseObject == null) {
                responseObject = new SaisClassificationResponse();
                responseObject.setError(Arrays.toString(e.getStackTrace()));
                responseObject.setxRoadErrors(Boolean.TRUE);
            }
        }

        return responseObject;
    }


    public SaisAdmissionResponse admissionsExport(XRoadHeader xRoadHeader, AllAdmissionsExportRequest requestValue) {
        SaisAdmissionResponse responseObject = null;
        try {
            URL wsdlUrl = SaisService.class.getResource(WSDL);
            XRoad ss = new XRoad(wsdlUrl, SERVICE_NAME);
            XRoadSoap port = ss.getXRoadSoap();

            Map<String, Object> context = ((BindingProvider) port).getRequestContext();
            context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, xRoadHeader.getEndpoint());
            context.put(XROAD_HEADER, xRoadHeader);

            Holder<AllAdmissionsExportRequest> request = new Holder<>(requestValue);
            Holder<AdmissionExportResponse> response = new Holder<>();

            port.allAdmissionsExport(request, response);

            Map<String, Object> responseContext = ((BindingProvider) port).getResponseContext();

            XRoadResponse xRoadResponse = (XRoadResponse) responseContext.get(XROAD_RESPONSE);
            responseObject = new SaisAdmissionResponse(xRoadResponse);
            responseObject.setQueryName(xRoadHeader.getService());
            responseObject.setAdmissionExportResponse(response.value);
        } catch (Exception e) {
            if(responseObject == null) {
                responseObject = new SaisAdmissionResponse();
                responseObject.setError(Arrays.toString(e.getStackTrace()));
                //TODO: use logger instead of system out
                System.out.println(Arrays.toString(e.getStackTrace()));
                responseObject.setxRoadErrors(Boolean.TRUE);
            }
        }

        return responseObject;
    }


    public SaisApplicationResponse applicationsExport(XRoadHeader xRoadHeader, AllAppsExportRequest requestValue) {
        SaisApplicationResponse responseObject = null;
        try {
            URL wsdlUrl = SaisService.class.getResource(WSDL);
            XRoad ss = new XRoad(wsdlUrl, SERVICE_NAME);
            XRoadSoap port = ss.getXRoadSoap();

            Map<String, Object> context = ((BindingProvider) port).getRequestContext();
            context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, xRoadHeader.getEndpoint());
            context.put(XROAD_HEADER, xRoadHeader);

            Holder<AllAppsExportRequest> request = new Holder<>(requestValue);
            Holder<AppExportResponse> response = new Holder<>();

            port.allApplicationsExport(request, response);

            Map<String, Object> responseContext = ((BindingProvider) port).getResponseContext();

            XRoadResponse xRoadResponse = (XRoadResponse) responseContext.get(XROAD_RESPONSE);
            responseObject = new SaisApplicationResponse(xRoadResponse);
            responseObject.setQueryName(xRoadHeader.getService());
            responseObject.setAppExportResponse(response.value);
        } catch (Exception e) {
            if(responseObject == null) {
                responseObject = new SaisApplicationResponse();
                responseObject.setError(Arrays.toString(e.getStackTrace()));
                //TODO: use logger instead of system out
                System.out.println(Arrays.toString(e.getStackTrace()));
                responseObject.setxRoadErrors(Boolean.TRUE);
            }
        }
        return responseObject;
    }

/*
    public static void main(String args[]) throws java.lang.Exception {
        SaisService saisService = new SaisService();

        XRoadHeader header = new XRoadHeader();
        header.setConsumer("10239452");
        header.setProducer("sais2");
        header.setUserId("EE30101010007");
        header.setId("3aed1ae3813eb7fbed9396fda70ca1215d3f3fe1");
        header.setService("sais2.ClassificationsExport.v1");
        header.setEndpoint("http://141.192.105.184/cgi-bin/consumer_proxy");

        saisService.classificationsExport(header);

        XRoadHeader header = new XRoadHeader();
        header.setConsumer("10239452");
        header.setProducer("sais2");
        header.setUserId("EE30101010007");
        header.setId("3aed1ae3813eb7fbed9396fda70ca1215d3f3fe1");
        header.setService("sais2.AllAdmissionsExport.v1");
        header.setEndpoint("http://141.192.105.184/cgi-bin/consumer_proxy");

        AllAdmissionsExportRequest request = new AllAdmissionsExportRequest();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar cal = new GregorianCalendar();

        cal.setTime(df.parse("2016-09-01"));
        request.setCreateDateFrom(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
        cal.setTime(df.parse("2017-09-01"));
        request.setCreateDateTo(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
        saisService.admissionsExport(header, request);

        XRoadHeader header = new XRoadHeader();
        header.setConsumer("10239452");
        header.setProducer("sais2");
        header.setUserId("EE30101010007");
        header.setId("3aed1ae3813eb7fbed9396fda70ca1215d3f3fe1");
        header.setService("sais2.AllApplicationsExport.v1");
        header.setEndpoint("http://141.192.105.184/cgi-bin/consumer_proxy");

        AllAppsExportRequest request = new AllAppsExportRequest();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar cal = new GregorianCalendar();

        cal.setTime(df.parse("2017-05-01"));
        request.setStatusChangeDateFrom(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
        cal.setTime(df.parse("2017-09-01"));
        request.setStatusChangeDateTo(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
        ArrayOfInt regCodes = new ArrayOfInt();
        regCodes.getInt().add(10239452);
        request.setInstitutionRegCodes(regCodes);
        SaisApplicationResponse response = saisService.applicationsExport(header, request);
        System.out.println(response);
        System.exit(0);
    }
*/
}
