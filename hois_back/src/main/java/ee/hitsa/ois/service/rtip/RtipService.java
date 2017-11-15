package ee.hitsa.ois.service.rtip;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.util.EntityUtil;
import ee.hois.soap.LogResult;
import ee.hois.xroad.helpers.XRoadHeaderV4;
import ee.hois.xroad.rtip.generated.ZABSENCE;
import ee.hois.xroad.rtip.generated.ZEMPLOEESRequestType;
import ee.hois.xroad.rtip.service.RtipClient;

@Transactional
@Service
public class RtipService {

    @Autowired
    private EntityManager em;
    @Autowired
    private RtipClient rtipClient;

    @Value("${rtip.endpoint}")
    protected String endpoint;
    @Value("${rtip.user}")
    protected String user;

    @Value("${rtip.client.xRoadInstance}")
    protected String clientXRoadInstance;
    @Value("${rtip.client.memberClass}")
    protected String clientMemberClass;
    @Value("${rtip.client.memberCode}")
    protected String clientMemberCode;
    @Value("${rtip.client.subsystemCode}")
    protected String clientSubsystemCode;

    @Value("${rtip.service.xRoadInstance}")
    protected String serviceXRoadInstance;
    @Value("${rtip.service.memberClass}")
    protected String serviceMemberClass;
    @Value("${rtip.service.memberCode}")
    protected String serviceMemberCode;
    @Value("${rtip.service.subsystemCode}")
    protected String serviceSubsystemCode;

    /**
     * Automatic task to refresh data from rtip
     */
    // TODO enable task
    // @Scheduled(cron = "${hois.jobs.rtip.cron}")
    public void sync() {
        for(School school : rtipSchools()) {
            syncSchool(school);
        }
    }

    private void syncSchool(School school) {
        XRoadHeaderV4 header = getXroadHeader();
        ZEMPLOEESRequestType request = new ZEMPLOEESRequestType();
        request.setCOMPANYCODE(school.getRtipSchoolCode());
        withResponse(rtipClient.zEMPLOEES(header, request), (result) -> {
            if(result.getPUUDUMINE() != null && !result.getPUUDUMINE().getItem().isEmpty()) {
                // load all teachers for given school with pernr
                Map<String, Long> teacherIds = rtipTeachers(school);
                for(ZABSENCE absence : result.getPUUDUMINE().getItem()) {
                    Long teacherId = teacherIds.get(absence.getPERNR());
                    if(teacherId != null) {
                        // TODO sync absence
                    }
                }
            }
        });
    }

    private List<School> rtipSchools() {
        return em.createQuery("select s from School s where s.rtipSchoolCode is not null", School.class).getResultList();
    }

    private Map<String, Long> rtipTeachers(School school) {
        List<?> data = em.createNativeQuery("select t.pernr, t.id from teacher t where t.school_id = ?1 and t.pernr is not null")
                .setParameter(1, EntityUtil.getId(school)).getResultList();
        return data.stream().collect(Collectors.toMap(r -> resultAsString(r, 0), r -> resultAsLong(r, 1), (o, n) -> o));
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
        service.setServiceCode("Z_EMPLOEES");
        service.setSubsystemCode(serviceSubsystemCode);

        XRoadHeaderV4 header = new XRoadHeaderV4();
        header.setClient(client);
        header.setService(service);
        header.setEndpoint(endpoint);
        header.setUserId(user);
        return header;
    }

    private static <T> void withResponse(LogResult<T> result, Consumer<T> handler) {
        try {
            if(!result.hasError()) {
                handler.accept(result.getResult());
            }
        } catch (Exception e) {
            result.getLog().setError(e);
        } finally {
            // TODO insert log
        }
    }
}
