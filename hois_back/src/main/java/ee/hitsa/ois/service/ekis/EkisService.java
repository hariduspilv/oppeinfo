package ee.hitsa.ois.service.ekis;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.Enterprise;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveCoordinator;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hois.soap.LogContext;
import ee.hois.soap.LogResult;
import ee.hois.soap.ekis.client.EkisClient;
import ee.hois.soap.ekis.client.EkisRequestContext;
import ee.hois.soap.ekis.client.RegisterCertificateRequest;
import ee.hois.soap.ekis.client.RegisterDirectiveRequest;
import ee.hois.soap.ekis.client.RegisterPracticeContractRequest;
import ee.hois.soap.ekis.client.generated.Content;

@Transactional
@Service
public class EkisService {

    @Autowired
    private EkisClient ekis;
    @Autowired
    private EkisLogService ekisLogService;
    @Autowired
    protected EntityManager em;
    @Value("${ekis.endpoint}")
    protected String endpoint;

    public Certificate registerCertificate(Certificate certificate) {
        RegisterCertificateRequest request = new RegisterCertificateRequest();
        request.setQguid(qguid());
        request.setEhisId(certificate.getSchool().getEhisSchool().getEhisValue());
        request.setOisId(certificate.getId().toString());
        Person person = certificate.getStudent().getPerson();
        request.setStudent(person.getFullname());
        request.setEmail(person.getEmail());
        request.setSubject(certificate.getHeadline());
        request.setBody(certificate.getContent());
        request.setItemCreator(certificate.getSignatoryIdcode());
        request.setType(certificate.getType().getValue());
        request.setInstitution(certificate.getWhom());

        return withResponse(ekis.registerCertificate(ctx(), request), (result) -> {
            certificate.setWdId(Long.valueOf(result.getWdId()));
            return save(certificate);
        }, certificate.getSchool(), certificate);
    }

    public Directive registerDirective(Directive directive) {
        RegisterDirectiveRequest request = new RegisterDirectiveRequest();
        request.setQguid(qguid());
        request.setEhisId(ehisId(directive.getSchool()));
        request.setOisId(directive.getId().toString());
        request.setDirectiveType(value(directive.getType()));
        request.setTitle(directive.getHeadline());
        request.setItemCreator(PersonUtil.idcodeFromFullnameAndIdcode(directive.getInsertedBy()));
        request.setCreateTime(date(directive.getInserted()));
        // TODO is this right?
        DirectiveCoordinator manager = directive.getDirectiveCoordinator();
        request.setManager(manager != null ? manager.getIdcode() : null);
        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));
        request.setContent(StreamUtil.toMappedList(ds -> studentForRegisterDirective(directiveType, ds), directive.getStudents()));
        // TODO how to identify missing value?
        request.setWdId(directive.getWdId() != null ? directive.getWdId().intValue() : 0);

        return withResponse(ekis.registerDirective(ctx(), request), (result) -> {
            // TODO how to identify missing value?
            directive.setWdId(Long.valueOf(result.getWdId()));
            return save(directive);
        }, directive.getSchool(), directive);
    }

    public Contract registerPracticeContract(Contract contract) {
        RegisterPracticeContractRequest request = new RegisterPracticeContractRequest();
        request.setQguid(qguid());
        request.setEhisId(ehisId(contract.getStudent().getSchool()));
        request.setOisId(contract.getId().toString());
        request.setCreateDate(date(contract.getInserted()));
        request.setManager(contract.getContractCoordinator().getIdcode());

        Student student = contract.getStudent();
        Person person = student.getPerson();
        request.setStIdCode(person.getIdcode());
        request.setStFirstNames(person.getFirstname());
        request.setStLastName(person.getLastname());
        request.setStEmail(student.getEmail());
        request.setStCurricula(student.getCurriculumVersion().getCurriculum().getNameEt());
        request.setStForm(value(student.getStudyForm()));
        // private String stCourse;
        // request.setStEkap(contract.get);
        request.setStHours(contract.getHours() != null ? contract.getHours().toString() : null);
        request.setStModule(contract.getModule().getCurriculumModule().getNameEt());

        Enterprise enterprise = contract.getEnterprise();
        request.setOrgName(enterprise.getName());
        request.setOrgCode(enterprise.getRegCode());
        request.setOrgContactName(enterprise.getContactPersonName());
        request.setOrgTel(enterprise.getContactPersonPhone());
        request.setOrgEmail(enterprise.getContactPersonEmail());
        request.setOrgTutorName(contract.getSupervisorName());
        request.setOrgTutorTel(contract.getSupervisorPhone());
        request.setOrgTutorEmail(contract.getSupervisorEmail());
        request.setProgramme(contract.getPracticePlan());

        return withResponse(ekis.registerPracticeContract(ctx(), request), (result) -> {
            contract.setWdId(Long.valueOf(result.getWdId()));
            return save(contract);
        }, contract.getStudent().getSchool(), contract);
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
            content.setLoad(value(ds.getStudyLoad()));
            content.setForm(value(ds.getStudyForm()));
            content.setCurricula(curriculum(ds));
            content.setGroup(studentGroup(ds));
            content.setFinsource(value(ds.getFin()));
            content.setLang(value(ds.getLanguage()));
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
            // TODO väliskool
            break;
        default:
            break;
        }
        return content;
    }

    private <T, R> R withResponse(LogResult<T> result, Function<T, R> handler, School school, R errorValue) {
        LogContext log = result.getLog();
        try {
            if(!result.hasError()) {
                return handler.apply(result.getResult());
            }
        } catch (Exception e) {
            log.setError(e);
        } finally {
            ekisLogService.insertLog(school, log);
        }
        return errorValue;
    }

    private static String curriculum(DirectiveStudent ds) {
        CurriculumVersion cv = ds.getCurriculumVersion();
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
