package ee.hitsa.ois.service.rr;

import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.rr.WsRrChangeLog;
import ee.hitsa.ois.domain.rr.WsRrLog;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.service.ClassifierService;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hois.soap.LogResult;
import ee.hois.xroad.helpers.XRoadHeaderV4;
import ee.hois.xroad.rahvastikuregister.generated.RR434;
import ee.hois.xroad.rahvastikuregister.generated.RR434RequestType;
import ee.hois.xroad.rahvastikuregister.generated.RR434ResponseType.Isikud.Isik;
import ee.hois.xroad.rahvastikuregister.generated.RR434ResponseType.Isikud.Isik.Elukohad.Elukoht;
import ee.hois.xroad.rahvastikuregister.generated.YeNo;
import ee.hois.xroad.rahvastikuregister.service.RRResponseResult;
import ee.hois.xroad.rahvastikuregister.service.RahvastikuregisterClient;

@Transactional(TxType.NOT_SUPPORTED)
@Service
public class PopulationRegisterService {
    
    private enum RRService {
        RR434("RR434", "v1");
        
        private String code;
        private String version;
        
        private RRService(String code, String version) {
            this.code = code;
            this.version = version;
        }
        
        String getCode() {
            return code;
        }
        
        String getVersion() {
            return version;
        }
    }
    
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    @Autowired
    private EntityManager em;
    @Autowired
    private RahvastikuregisterClient registerclient;
    @Autowired
    private PopulationRegisterLogService logService;
    @Autowired
    private ClassifierService classifierService;
    
    @Value("${rr.endpoint}")
    private String endpoint;
    @Value("${rr.userId}")
    private String userId;
    
    @Value("${rr.service.xRoadInstance}")
    private String serviceXRoadInstance;
    @Value("${rr.service.memberClass}")
    private String serviceMemberClass;
    @Value("${rr.service.memberCode}")
    private String serviceMemberCode;
    @Value("${rr.service.subsystemCode}")
    private String serviceSubsystemCode;

    @Value("${rr.client.xRoadInstance}")
    private String clientXRoadInstance;
    @Value("${rr.client.memberClass}")
    private String clientMemberClass;
    @Value("${rr.client.memberCode}")
    private String clientMemberCode;
    @Value("${rr.client.subsystemCode}")
    private String clientSubsystemCode;
    
    public static PersonRequest generateRequest(Person p) {
        LOG.info(String.format("[RR] Creating a request for person: %s", p.getFullname()));
        Set<School> schools = new HashSet<>();
        Set<Student> students = new HashSet<>();
        p.getUsers().forEach(u -> {
            if (UserUtil.isActiveUser(u)) {
                if (Role.ROLL_A.name().equals(u.getRole().getCode())) {
                    schools.add(u.getSchool());
                } else if (Role.ROLL_O.name().equals(u.getRole().getCode()) && u.getTeacher() != null && Boolean.TRUE.equals(u.getTeacher().getIsActive())) {
                    schools.add(u.getSchool());
                } else if (Role.ROLL_T.name().equals(u.getRole().getCode()) && StudentUtil.isActive(u.getStudent())) {
                    schools.add(u.getSchool());
                    students.add(u.getStudent());
                }
            }
        });
        if (!schools.isEmpty()) {
            LOG.info(String.format("[RR] Request created. Schools: %d. Students: %d", Integer.valueOf(schools.size()), Integer.valueOf(students.size())));
            PersonRequest req = new PersonRequest();
            req.setPerson(p);
            req.setStudents(students);
            req.setSchools(schools);
            return req;
        }
        LOG.info(String.format("[RR] For person '%s' found no schools.", p.getFullname()));
        return null;
    }
    
    public void updateAcitveUsers() {
        List<Person> persons = em.createQuery("select distinct p from Person p where p.idcode is not null order by p.lastname", Person.class).getResultList();
        persons.stream().map(PopulationRegisterService::generateRequest).filter(req -> req != null).forEach(this::updatePersonData);
    }
    
    public Throwable updatePersonData(PersonRequest data) {
        if (data == null) {
            return new HoisException("Request is empty");
        }
        RR434 request = new RR434();
        RR434RequestType req = new RR434RequestType();
        if (data.getPerson().getIdcode() == null) {
            return new HoisException(TranslateUtil.translate("rr.error.noEstonianIdcode", Language.ET));
        }
        req.setIsikukood(data.getPerson().getIdcode());
        req.setRiik("EST");
        req.setKehtivad(YeNo.JAH);
        request.setRequest(req);
        LOG.info(String.format("[RR] Making a request using XRoad for person '%s'.", data.getPerson().getFullname()));
        RRResponseResult result = withResponse(registerclient.requestPersonData(getXroadHeader(RRService.RR434), request), data, response -> {
            if (StringUtils.isNoneBlank(response.getResponse().getFaultCode())) {
                throw new HoisException(String.format("%s. %s", response.getResponse().getFaultCode(), Optional.ofNullable(response.getResponse().getFaultString()).orElse("")));
            }
            if (response.getResponse().getVeakood() != null) {
                throw new HoisException(String.format("%d. %s", response.getResponse().getVeakood(), Optional.ofNullable(response.getResponse().getVeatekst()).orElse("")));
            }
            Isik isik = StreamUtil.nullSafeList(response.getResponse().getIsikud().getIsik()).stream().findFirst().orElse(null);
            if (isik == null) {
                throw new HoisException(TranslateUtil.translate("rr.error.noperson", Language.ET));
            }
            Elukoht elukoht = isik.getElukohad().getElukoht().stream().filter(koht -> koht.getElukohtStaatus().equals("KEHTIV")).findFirst().orElse(null);
            
            String firstName = isik.getIsikEesnimi();
            String lastName = isik.getIsikPerenimi();
            String address = elukoht == null || StringUtils.isBlank(elukoht.getElukohtTekst()) ? null : elukoht.getElukohtTekst();
            String oids = elukoht == null || StringUtils.isBlank(elukoht.getElukohtAdsOid()) ? null : elukoht.getElukohtAdsOid();
            String postCode = elukoht == null || StringUtils.isBlank(elukoht.getElukohtPostiindeks()) ? null : elukoht.getElukohtPostiindeks();
            // unused, check happens using address.
            //String addressCode = elukoht == null || StringUtils.isBlank(elukoht.getElukohtKoodaadress()) ? null : elukoht.getElukohtKoodaadress();
            String addressAds = elukoht == null ? null : StringUtils.rightPad(
                            StringUtils.defaultIfBlank(StringUtils.leftPad(elukoht.getElukohtMaakonnaKood(), 2, '0'), "00")
                                + StringUtils.defaultIfBlank(StringUtils.leftPad(elukoht.getElukohtVallaKood(), 3, '0'), "000")
                                + StringUtils.defaultIfBlank(StringUtils.leftPad(elukoht.getElukohtKylaKood(), 4, '0'), "0000"),
                    33, '0');
            
            ClassifierCache classifiers = new ClassifierCache(classifierService);
            
            boolean hasSpecificChanges = hasSpecificChanges(data.getPerson(), firstName, lastName, oids);
            
            if (!data.getStudents().isEmpty() && hasSpecificChanges) {
                WsRrChangeLog log = new WsRrChangeLog();
                log.setOldFirstname(data.getPerson().getFirstname());
                log.setOldLastname(data.getPerson().getLastname());
                log.setOldAddress(data.getPerson().getAddress());
                log.setOldAddressAdsOid(data.getPerson().getAddressAdsOid());
                log.setNewFirstname(StringUtils.equalsIgnoreCase(data.getPerson().getFirstname(), firstName) ? data.getPerson().getFirstname() : PersonUtil.initCapName(firstName));
                log.setNewLastname(StringUtils.equalsIgnoreCase(data.getPerson().getLastname(), lastName) ? data.getPerson().getLastname() : PersonUtil.initCapName(lastName));
                log.setNewAddress(address);
                log.setNewAddressAdsOid(oids);
                log.setPerson(data.getPerson());
                logService.insertChangeLog(log, data.getStudents());
            }

            logService.savePersonData(data.getPerson(), firstName, lastName, classifiers.getByValue(isik.getIsikPohiKodakondsus(), MainClassCode.RIIK),
                    elukoht == null? null : classifiers.getByNameEt(elukoht.getElukohtRiikNim(), MainClassCode.RIIK), address, oids, postCode, addressAds);
        });
        return result.hasError() ? result.getLog().getError() : null;
    }
    
    private static boolean hasSpecificChanges(Person person, String firstName, String lastName, String oids) {
        return !StringUtils.equalsIgnoreCase(person.getFirstname(), firstName) || !StringUtils.equalsIgnoreCase(person.getLastname(), lastName) ||
                !StringUtils.equalsIgnoreCase(person.getAddressAdsOid(), oids);
    }
    
    private XRoadHeaderV4 getXroadHeader(RRService serviceData) {
        XRoadHeaderV4.Client client = new XRoadHeaderV4.Client();
        client.setXRoadInstance(clientXRoadInstance);
        client.setMemberClass(clientMemberClass);
        client.setMemberCode(clientMemberCode);
        client.setSubSystemCode(clientSubsystemCode);

        XRoadHeaderV4.Service service = new XRoadHeaderV4.Service();
        service.setxRoadInstance(serviceXRoadInstance);
        service.setMemberClass(serviceMemberClass);
        service.setMemberCode(serviceMemberCode);
        service.setServiceCode(serviceData.getCode());
        service.setServiceVersion(serviceData.getVersion());
        service.setSubsystemCode(serviceSubsystemCode);

        XRoadHeaderV4 header = new XRoadHeaderV4();
        header.setClient(client);
        header.setService(service);
        header.setEndpoint(endpoint);
        header.setUserId(userId);
        return header;
    }
    
    private <T, L extends LogResult<T>> L withResponse(L result, PersonRequest request, Consumer<T> handler) {
        try {
            LOG.info(String.format("[RR] Result has been received for person '%s'.", request.getPerson().getFullname()));
            if (!result.hasError()) {
                handler.accept(result.getResult());
            }
        } catch (Exception e) {
            LOG.info(String.format("[RR] Error occured for person '%s'.", request.getPerson().getFullname()));
            result.getLog().setError(e);
            LOG.error("Error while handling Rahvastikuregister response :", e);
        } finally {
            logService.insertLog(result.getLog(), request.getPerson(), request.getSchools());
        }
        return result;
    }
    
    public static class PersonRequest {
        private Person person;
        private Set<Student> students;
        private Set<School> schools;
        
        public Person getPerson() {
            return person;
        }
        public void setPerson(Person person) {
            this.person = person;
        }
        public Set<Student> getStudents() {
            return students;
        }
        public void setStudents(Set<Student> students) {
            this.students = students;
        }
        public Set<School> getSchools() {
            return schools;
        }
        public void setSchools(Set<School> schools) {
            this.schools = schools;
        }
    }
    
    public static class PersonResponse {
        private WsRrLog log;
        private WsRrChangeLog logChange;
        
        public WsRrLog getLog() {
            return log;
        }
        public void setLog(WsRrLog log) {
            this.log = log;
        }
        public WsRrChangeLog getLogChange() {
            return logChange;
        }
        public void setLogChange(WsRrChangeLog logChange) {
            this.logChange = logChange;
        }
    }
}
