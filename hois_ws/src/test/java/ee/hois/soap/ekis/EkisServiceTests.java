package ee.hois.soap.ekis;

import java.net.URL;

import javax.xml.ws.Endpoint;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ee.hois.soap.ekis.service.generated.EnforceContract;
import ee.hois.soap.ekis.service.generated.EnforceContractResponse;
import ee.hois.soap.ekis.service.generated.EnforceDirective;
import ee.hois.soap.ekis.service.generated.EnforceDirectiveResponse;
import ee.hois.soap.ekis.service.generated.RejectDirective;
import ee.hois.soap.ekis.service.generated.RejectDirectiveResponse;
import ee.hois.soap.ekis.service.EkisService;

public class EkisServiceTests {

    private static final String mockEndpoint = "http://localhost:8888/ekis";
    private static EkisService ekisService;

    @BeforeClass
    public static void setUpClass() throws Exception {
        Endpoint.publish(mockEndpoint, new EkisMockService());
        ekisService = new EkisService("http://tahvel.hois.ee/ekis", new URL(mockEndpoint + "?WSDL"));
    }

    @Test
    public void testRejectDirective() {
        RejectDirective request = new RejectDirective();
        RejectDirectiveResponse response = ekisService.rejectDirective(request);
        Assert.assertNotNull("expecting response", response);
    }

    @Test
    public void testEnforceDirective() {
        EnforceDirective request = new EnforceDirective();
        EnforceDirectiveResponse response = ekisService.enforceDirective(request);
        Assert.assertNotNull("expecting response", response);
    }

    @Test
    public void testEnforceContract() {
        EnforceContract request = new EnforceContract();
        EnforceContractResponse response = ekisService.enforceContract(request);
        Assert.assertNotNull("expecting response", response);
    }
}
