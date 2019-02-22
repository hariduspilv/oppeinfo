package ee.hitsa.ois.service.arireg;

import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.WsAriregLog;
import ee.hitsa.ois.domain.WsEkisLog;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.ContractStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hois.soap.LogResult;
import ee.hois.xroad.ariregister.generated.ParinglihtV5Paring;
import ee.hois.xroad.ariregister.service.AriregisterClient;
import ee.hois.xroad.ariregister.service.LihtandmedResponse;
import ee.hois.xroad.helpers.XRoadHeaderV4;

@Transactional
@Service
public class AriregisterService {
	
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	private static final String ERROR_MARKER_RESULT = "Viga!";
    private static final String ERROR_MARKER_MSG = "VIGA";

    @Autowired
    protected AriregisterClient ariregisterClient;
    
    @Autowired
    protected AriregLogService ariregLogService;

    @Value("${ariregister.endpoint}")
    protected String endpoint;

    @Value("${ariregister.userid}")
    protected String userid;
    
    @Value("${ariregister.client.xRoadInstance}")
    protected String clientXRoadInstance;
    @Value("${ariregister.client.memberClass}")
    protected String clientMemberClass;
    @Value("${ariregister.client.memberCode}")
    protected String clientMemberCode;
    @Value("${ariregister.client.subsystemCode}")
    protected String clientSubsystemCode;

    @Value("${ariregister.service.xRoadInstance}")
    protected String serviceXRoadInstance;
    @Value("${ariregister.service.memberClass}")
    protected String serviceMemberClass;
    @Value("${ariregister.service.memberCode}")
    protected String serviceMemberCode;
    @Value("${ariregister.service.subsystemCode}")
    protected String serviceSubsystemCode;
    @Value("${ariregister.service.serviceCode}")
    protected String serviceCode;
    
    public LihtandmedResponse getSimpleData(BigInteger regCode, School school) {
    	ParinglihtV5Paring simpleRequest = new ParinglihtV5Paring();
    	simpleRequest.setAriregistriKood(regCode);
    	LihtandmedResponse response = ariregisterClient.lihtandmed(getXroadHeader(), simpleRequest);
    	return withResponse(ariregisterClient.lihtandmed(getXroadHeader(), simpleRequest), school , response);
    }
    
    protected XRoadHeaderV4 getXroadHeader() {
        XRoadHeaderV4.Client client = new XRoadHeaderV4.Client();
        client.setXRoadInstance(clientXRoadInstance);
        client.setMemberClass(clientMemberClass);
        client.setMemberCode(clientMemberCode);
        client.setSubSystemCode(clientSubsystemCode);

        XRoadHeaderV4.Service service = new XRoadHeaderV4.Service();
        service.setxRoadInstance(serviceXRoadInstance);
        service.setMemberClass(serviceMemberClass);
        service.setMemberCode(serviceMemberCode);
        service.setServiceCode(serviceCode);
        service.setSubsystemCode(serviceSubsystemCode);

        XRoadHeaderV4 header = new XRoadHeaderV4();
        header.setClient(client);
        header.setService(service);
        header.setEndpoint(endpoint);
        header.setUserId(userid);
        return header;
    }
    
    /**
     * errors are reported as success. If any of item has magic string Viga! then consider result as error
     * @param result
     * @return
     */
    protected boolean resultHasError(List<String> result) {
        return StreamUtil.nullSafeList(result).stream().anyMatch(r -> r != null && r.contains(ERROR_MARKER_RESULT));
    }
    
    protected boolean messageHasError(String msg) {
        return msg == null || msg.contains(ERROR_MARKER_MSG);
    }
    
    private <T, R> R withResponse(LogResult<T> result, School school, R response) {
        try {
            if(!result.hasError()) {
                return response;
            }
        } catch (Exception e) {
            result.getLog().setError(e);
            LOG.error("Error while handling Arireg response :", e);
        } finally {
            WsAriregLog logRecord = new WsAriregLog();
            ariregLogService.insertLog(logRecord, school, result.getLog());

            if(result.hasError()) {
                throw new ValidationFailedException(result.getLog().getError().toString());
            }
        }
        return response;
    }
}
