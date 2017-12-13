package ee.hitsa.ois.service.ekis;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.Enterprise;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.WsEkisLog;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveCoordinator;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.enums.CertificateStatus;
import ee.hitsa.ois.enums.CertificateType;
import ee.hitsa.ois.enums.ContractStatus;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.Translatable;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hois.soap.LogResult;
import ee.hois.soap.ekis.client.DeleteDirectiveRequest;
import ee.hois.soap.ekis.client.EkisClient;
import ee.hois.soap.ekis.client.EkisRequestContext;
import ee.hois.soap.ekis.client.RegisterCertificateRequest;
import ee.hois.soap.ekis.client.RegisterDirectiveRequest;
import ee.hois.soap.ekis.client.RegisterPracticeContractRequest;
import ee.hois.soap.ekis.client.generated.Content;

@Transactional
@Service
public class EkisService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    // XXX we send 0 as missing wd_id value
    private static final int MISSING_WD_ID = 0;

    @Autowired
    private EkisClient ekis;
    @Autowired
    private EkisLogService ekisLogService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    protected EntityManager em;
    @Value("${ekis.endpoint}")
    protected String endpoint;

    /**
     * Send register certificate request to EKIS
     *
     * @param certificateId
     * @return certificate with status updated, certificate nr and wd_id set
     * @throws ValidationFailedException if there was an error
     */
    public Certificate registerCertificate(Long certificateId) {
        Certificate certificate = em.getReference(Certificate.class, certificateId);

        RegisterCertificateRequest request = new RegisterCertificateRequest();
        request.setQguid(qguid());
        request.setEhisId(certificate.getSchool().getEhisSchool().getEhisValue());
        request.setOisId(certificate.getId().toString());

        Person person;
        if(CertificateType.isOther(EntityUtil.getCode(certificate.getType())) && certificate.getStudent() == null) {
            person = personRepository.findByIdcode(certificate.getOtherIdcode());
        } else {
            person = certificate.getStudent().getPerson();
        }
        request.setStudent(person.getFullname());
        request.setEmail(person.getEmail());

        request.setSubject(certificate.getHeadline());
        request.setBody(certificate.getContent());
        request.setItemCreator(certificate.getSignatoryIdcode());
        request.setType(value(certificate.getType()));
        request.setInstitution(certificate.getWhom());

        return withResponse(ekis.registerCertificate(ctx(), request), (result) -> {
            certificate.setWdId(Long.valueOf(result.getWdId()));
            certificate.setCertificateNr(result.getRegno());
            certificate.setStatus(em.getReference(Classifier.class, CertificateStatus.TOEND_STAATUS_V.name()));
            return save(certificate);
        }, certificate.getSchool(), certificate, l -> l.setCertificate(certificate));
    }

    /**
     * Send register directive request to EKIS
     *
     * @param directiveId
     * @return directive with status update and wd_id set
     * @throws ValidationFailedException if there was an error
     */
    public Directive registerDirective(Long directiveId) {
        Directive directive = em.getReference(Directive.class, directiveId);

        RegisterDirectiveRequest request = new RegisterDirectiveRequest();
        request.setQguid(qguid());
        request.setEhisId(ehisId(directive.getSchool()));
        request.setOisId(directive.getId().toString());
        request.setDirectiveType(value(directive.getType()));
        request.setTitle(directive.getHeadline());
        DirectiveCoordinator manager = directive.getDirectiveCoordinator();
        // XXX item creator same as manager
        request.setItemCreator(manager != null ? manager.getIdcode() : null);
        request.setCreateTime(date(directive.getInserted()));
        request.setManager(manager != null ? manager.getIdcode() : null);
        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));
        request.setContent(StreamUtil.toMappedList(ds -> studentForRegisterDirective(directiveType, ds), directive.getStudents()));
        request.setWdId(directive.getWdId() != null ? directive.getWdId().intValue() : MISSING_WD_ID);

        return withResponse(ekis.registerDirective(ctx(), request), (result) -> {
            directive.setWdId(Long.valueOf(result.getWdId()));
            directive.setStatus(em.getReference(Classifier.class, DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL.name()));
            return save(directive);
        }, directive.getSchool(), directive, l -> l.setDirective(directive));
    }

    /**
     * send delete directive request to EKIS
     *
     * @param directiveId
     * @throws ValidationFailedException if there was an error
     */
    public void deleteDirective(Long directiveId) {
        Directive directive = em.getReference(Directive.class, directiveId);

        DeleteDirectiveRequest request = new DeleteDirectiveRequest();
        request.setQguid(qguid());
        request.setOisId(directive.getId().toString());
        request.setWdId(directive.getWdId().intValue());

        withResponse(ekis.deleteDirective(ctx(), request), (result) -> {
            // do nothing
            return null;
        }, directive.getSchool(), directive, l -> {});
    }

    /**
     * send register practice contract request to EKIS
     *
     * @param directiveId
     * @return contract with status updated and wd_id set
     * @throws ValidationFailedException if there was an error
     */
    public Contract registerPracticeContract(Long contractId) {
        Contract contract = em.getReference(Contract.class, contractId);

        RegisterPracticeContractRequest request = new RegisterPracticeContractRequest();
        request.setQguid(qguid());
        request.setEhisId(ehisId(contract.getStudent().getSchool()));
        request.setOisId(contract.getId().toString());
        request.setManager(contract.getContractCoordinator() != null ? contract.getContractCoordinator().getIdcode() : null);

        Student student = contract.getStudent();
        Person person = student.getPerson();
        request.setStIdCode(person.getIdcode());
        request.setStFirstNames(person.getFirstname());
        request.setStLastName(person.getLastname());
        request.setStEmail(student.getEmail());
        request.setStCurricula(student.getCurriculumVersion().getCurriculum().getNameEt());
        request.setStForm(student.getStudyForm() != null ? student.getStudyForm().getNameEt() : null);
        StudentGroup sg = student.getStudentGroup();
        request.setStCourse(sg != null ? sg.getCourse().toString() : null);
        request.setStEkap(contract.getCredits() != null ? contract.getCredits().toString() : null);
        request.setStHours(contract.getHours() != null ? contract.getHours().toString() : null);

        boolean higher = StudentUtil.isHigher(student);
        Translatable module = higher ? contract.getSubject() : contract.getModule().getCurriculumModule();
        String stModule = module != null ? module.getNameEt() : null;
        if(!higher) {
            // add theme
            List<String> names = Arrays.asList(stModule, contract.getTheme() != null ? contract.getTheme().getNameEt() : null);
            stModule = names.stream().filter(r -> r != null).collect(Collectors.joining(", "));
        }
        request.setStModule(stModule);

        Enterprise enterprise = contract.getEnterprise();
        request.setOrgName(enterprise.getName());
        request.setOrgCode(enterprise.getRegCode());
        request.setOrgContactName(contract.getContactPersonName());
        request.setOrgTel(contract.getContactPersonPhone());
        request.setOrgEmail(contract.getContactPersonEmail());
        request.setOrgTutorName(contract.getSupervisorName());
        request.setOrgTutorTel(contract.getSupervisorPhone());
        request.setOrgTutorEmail(contract.getSupervisorEmail());
        request.setProgramme(contract.getPracticePlan());
        request.setStartDate(date(contract.getStartDate()));
        request.setEndDate(date(contract.getEndDate()));
        request.setSchoolTutorId(contract.getContractCoordinator() != null ? contract.getContractCoordinator().getIdcode() : null);
        request.setPlace(contract.getPracticePlace());

        return withResponse(ekis.registerPracticeContract(ctx(), request), (result) -> {
            contract.setWdId(Long.valueOf(result.getWdId()));
            contract.setStatus(em.getReference(Classifier.class, ContractStatus.LEPING_STAATUS_Y.name()));
            return save(contract);
        }, contract.getStudent().getSchool(), contract, l -> l.setContract(contract));
    }

    protected <T extends BaseEntityWithId> T save(T entity) {
        return EntityUtil.save(entity, em);
    }

    private static Content studentForRegisterDirective(DirectiveType directiveType, DirectiveStudent ds) {
        Content content = new Content();
        content.setIdCode(ds.getPerson().getIdcode());
        content.setFirstName(ds.getPerson().getFirstname());
        content.setLastName(ds.getPerson().getLastname());
        switch(directiveType) {
        case KASKKIRI_AKAD:
            content.setStartDate(periodStart(ds));
            content.setEndDate(periodEnd(ds));
            content.setReason(value(ds.getReason()));
            break;
        case KASKKIRI_AKADK:
            content.setEndDate(date(ds.getStartDate()));
            break;
        case KASKKIRI_EKSMAT:
            content.setReason(value(ds.getReason()));
            break;
        case KASKKIRI_ENNIST:
            // only student group is edited, take other values from student
            Student student = ds.getStudent();
            content.setLoad(value(student.getStudyLoad()));
            content.setForm(value(student.getStudyForm()));
            content.setCurricula(curriculum(student));
            content.setGroup(studentGroup(ds));
            content.setFinsource(value(student.getFin()));
            content.setLang(value(student.getLanguage()));
            break;
        case KASKKIRI_FINM:
            content.setFinsource(value(ds.getFin()));
            break;
        case KASKKIRI_IMMAT:
        case KASKKIRI_IMMATV:
            content.setLoad(value(ds.getStudyLoad()));
            content.setForm(value(ds.getStudyForm()));
            content.setCurricula(curriculum(ds));
            content.setGroup(studentGroup(ds));
            content.setFinsource(value(ds.getFin()));
            content.setLang(value(ds.getLanguage()));
            break;
        case KASKKIRI_LOPET:
            content.setCurricula(curriculum(ds));
            // TODO cum laude, grade
            break;
        case KASKKIRI_OKAVA:
            content.setForm(value(ds.getStudyForm()));
            content.setCurricula(curriculum(ds));
            content.setGroup(studentGroup(ds));
            break;
        case KASKKIRI_OKOORM:
            content.setLoad(value(ds.getStudyLoad()));
            break;
        case KASKKIRI_OVORM:
            content.setForm(value(ds.getStudyForm()));
            content.setGroup(studentGroup(ds));
            break;
        case KASKKIRI_VALIS:
            content.setStartDate(periodStart(ds));
            content.setEndDate(periodEnd(ds));
            content.setOuterschool(Boolean.TRUE.equals(ds.getIsAbroad()) ? ds.getAbroadSchool() : (ds.getEhisSchool() != null ? ds.getEhisSchool().getNameEt() : null));
            break;
        default:
            break;
        }
        return content;
    }

    private <T, R> R withResponse(LogResult<T> result, Function<T, R> handler, School school, R errorValue, Consumer<WsEkisLog> logCustomizer) {
        try {
            if(!result.hasError()) {
                return handler.apply(result.getResult());
            }
        } catch (Exception e) {
            result.getLog().setError(e);
            LOG.error("Error while handling EKIS response :", e);
        } finally {
            WsEkisLog logRecord = new WsEkisLog();
            logCustomizer.accept(logRecord);
            ekisLogService.insertLog(logRecord, school, result.getLog());

            if(result.hasError()) {
                throw new ValidationFailedException(result.getLog().getError().toString());
            }
        }
        return errorValue;
    }

    private static String curriculum(DirectiveStudent ds) {
        CurriculumVersion cv = ds.getCurriculumVersion();
        return cv != null ? cv.getCode() : null;
    }

    private static String curriculum(Student student) {
        CurriculumVersion cv = student.getCurriculumVersion();
        return cv != null ? cv.getCode() : null;
    }

    private static String date(LocalDateTime date) {
        return date != null ? date(date.toLocalDate()) : null;
    }

    private static String date(LocalDate date) {
        return date != null ? date.toString() : null;
    }

    private static int ehisId(School school) {
        try {
            if(school.getEhisSchool() != null) {
                return Integer.parseInt(school.getEhisSchool().getEhisValue(), 10);
            }
        } catch(@SuppressWarnings("unused") NumberFormatException e) {
            // ignore
        }
        return 0;
    }

    private static String periodStart(DirectiveStudent ds) {
        return date(DateUtils.periodStart(ds));
    }

    private static String periodEnd(DirectiveStudent ds) {
        return date(DateUtils.periodEnd(ds));
    }

    private static String studentGroup(DirectiveStudent ds) {
        StudentGroup sg = ds.getStudentGroup();
        return sg != null ? sg.getCode() : null;
    }

    private static String value(Classifier classifier) {
        return classifier != null ? classifier.getValue() : null;
    }

    private static String qguid() {
        return UUID.randomUUID().toString();
    }

    private EkisRequestContext ctx() {
        return new EkisRequestContext(endpoint);
    }
}
