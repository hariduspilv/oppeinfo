package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.enterprise.Enterprise;
import ee.hitsa.ois.domain.enterprise.EnterpriseSchool;
import ee.hitsa.ois.domain.enterprise.EnterpriseSchoolPerson;
import ee.hitsa.ois.domain.enterprise.PracticeAdmission;
import ee.hitsa.ois.domain.enterprise.PracticeApplication;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.PracticeApplicationStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.practice.PracticeApplicationForm;
import ee.hitsa.ois.web.commandobject.practice.PracticeApplicationPeriodsSearchCommand;
import ee.hitsa.ois.web.commandobject.practice.PracticeApplicationRejectForm;
import ee.hitsa.ois.web.commandobject.practice.PracticeApplicationSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.practice.PracticeAdmissionStudentDto;
import ee.hitsa.ois.web.dto.practice.PracticeApplicationContractDto;
import ee.hitsa.ois.web.dto.practice.PracticeApplicationPeriodSearchDto;
import ee.hitsa.ois.web.dto.practice.PracticeApplicationSearchDto;

@Transactional
@Service
public class PracticeApplicationService {

    @Autowired
    private EntityManager em;

    public Map<String, Object> canApply(HoisUserDetails user) {
        Map<String, Object> result = new HashMap<>();
        result.put("canApply", Boolean.valueOf(!openAdmissions(user).isEmpty()));
        return result;
    }

    public List<PracticeAdmissionStudentDto> openAdmissions(HoisUserDetails user) {
        return studentAdmissions(user, false);
    }

    public List<PracticeAdmissionStudentDto> passedAdmissions(HoisUserDetails user) {
        return studentAdmissions(user, true);
    }

    private List<PracticeAdmissionStudentDto> studentAdmissions(HoisUserDetails user, boolean passed) {
        List<?> result = em.createNativeQuery("select padm.id as admission_id, e.name, padm.valid_from, padm.valid_thru, padm.places, "
                + "(select count(*) from practice_application where practice_admission_id = padm.id and status_code in (?3, ?4)) as app_count, "
                + "padm.add_info, papp.submitted, papp.status_code, papp.reject_reason, c.id as contract_id, padm.is_strict "
                + " from practice_admission padm"
                + " join enterprise_school es on es.id = padm.enterprise_school_id"
                + " join enterprise e on e.id = es.enterprise_id"
                + " left join practice_application papp on papp.practice_admission_id = padm.id and papp.student_id = ?2"
                + " left join contract c on c.practice_application_id = papp.id"
                + " where es.school_id = ?1 and "
                + (passed ? "not(now() between padm.valid_from and padm.valid_thru) and papp.id is not null" : 
                    " (now() between padm.valid_from and padm.valid_thru"
                        + " and exists(select id from practice_admission_student_group where practice_admission_id = padm.id"
                        + " and student_group_id in (select student_group_id from student where id = ?2)))")
                + " order by e.name, padm.valid_from, padm.valid_thru")
                .setParameter(1, user.getSchoolId())
                .setParameter(2, user.getStudentId())
                .setParameter(3, PracticeApplicationStatus.PR_TAOTLUS_A.name())
                .setParameter(4, PracticeApplicationStatus.PR_TAOTLUS_E.name())
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            PracticeAdmissionStudentDto dto = new PracticeAdmissionStudentDto();
            dto.setId(resultAsLong(r, 0));
            dto.setEnterpriseName(resultAsString(r, 1));
            dto.setValidFrom(resultAsLocalDate(r, 2));
            dto.setValidThru(resultAsLocalDate(r, 3));
            dto.setPlaces(resultAsLong(r, 4));
            dto.setSubmittedApplications(resultAsLong(r, 5));
            dto.setAddInfo(resultAsString(r, 6));
            dto.setSubmitDate(resultAsLocalDate(r, 7));
            dto.setStatus(resultAsString(r, 8));
            dto.setRejectReason(resultAsString(r, 9));
            dto.setContractId(resultAsLong(r, 10));
            dto.setIsStrict(resultAsBoolean(r, 11));
            return dto;
        }, result);
    }
    
    public void apply(HoisUserDetails user, PracticeAdmission admission, PracticeApplicationForm form) {
        UserUtil.assertSameSchool(user, admission.getEnterpriseSchool().getSchool());
        Student student = em.getReference(Student.class, user.getStudentId());
        PracticeApplication application = getStudentPracticeApplication(admission, student);
        AssertionFailedException.throwIf(application != null && !ClassifierUtil.equals(PracticeApplicationStatus.PR_TAOTLUS_C, application.getStatus()), 
                "Student already applied for this admission");
        ValidationFailedException.throwIf(!admissionHasPlaces(admission), "practiceApplication.errors.noplaces");
        if (application == null) {
            application = new PracticeApplication();
        }
        application.setPracticeAdmission(admission);
        application.setStudent(student);
        application.setAddInfo(form.getAddInfo());
        application.setStatus(em.getReference(Classifier.class, PracticeApplicationStatus.PR_TAOTLUS_E.name()));
        application.setSubmitted(LocalDate.now());
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.save(application, em);
    }

    @SuppressWarnings("null")
    public void annul(HoisUserDetails user, PracticeAdmission admission) {
        UserUtil.assertSameSchool(user, admission.getEnterpriseSchool().getSchool());
        Student student = em.getReference(Student.class, user.getStudentId());
        PracticeApplication application = getStudentPracticeApplication(admission, student);
        AssertionFailedException.throwIf(application == null, "Student did not apply for this admission");
        PracticeApplicationStatus applicationStatus = PracticeApplicationStatus.valueOf(EntityUtil.getCode(application.getStatus()));
        AssertionFailedException.throwIf(!(PracticeApplicationStatus.PR_TAOTLUS_E == applicationStatus
                || (PracticeApplicationStatus.PR_TAOTLUS_A == applicationStatus) && application.getContract() == null), 
                "Cannot annul this application");
        EntityUtil.setUsername(user.getUsername(), em);
        application.setStatus(em.getReference(Classifier.class, PracticeApplicationStatus.PR_TAOTLUS_C.name()));
    }
    
    private boolean admissionHasPlaces(PracticeAdmission admission) {
        if (Boolean.TRUE.equals(admission.getIsStrict()) && admission.getPlaces() != null) {
            return em.createNativeQuery("select papp.id from practice_admission pa "
                    + "join practice_application papp on pa.id = papp.practice_admission_id "
                    + "where pa.id = ?1 and papp.status_code in (?2, ?3) limit " + admission.getPlaces().intValue())
                .setParameter(1, admission.getId())
                .setParameter(2, PracticeApplicationStatus.PR_TAOTLUS_A.name())
                .setParameter(3, PracticeApplicationStatus.PR_TAOTLUS_E.name())
                .getResultList().size() < admission.getPlaces().intValue();
        }
        return true;
    }
    
    private PracticeApplication getStudentPracticeApplication(PracticeAdmission admission, Student student) {
        List<PracticeApplication> result = em.createQuery("select papp from PracticeApplication papp"
                + " where papp.practiceAdmission = ?1 and papp.student = ?2", PracticeApplication.class)
                .setParameter(1, admission)
                .setParameter(2, student)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
    
    public Page<PracticeApplicationSearchDto> applications(HoisUserDetails user, PracticeApplicationSearchCommand command, 
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from practice_application papp" 
                        + " join practice_admission padm on padm.id = papp.practice_admission_id"
                        + " join enterprise_school es on es.id = padm.enterprise_school_id"
                        + " join enterprise e on e.id = es.enterprise_id"
                        + " join student s on s.id = papp.student_id" 
                        + " join person p on p.id = s.person_id"
                        + " join student_group sg on sg.id = s.student_group_id"
                        + " left join contract c on c.practice_application_id = papp.id").sort(pageable);

        qb.requiredCriteria("es.school_id = :schoolId", "schoolId", user.getSchoolId());
        
        qb.optionalCriteria("sg.id = :studentGroup", "studentGroup", command.getStudentGroup());
        qb.optionalContains("p.firstname || ' ' || p.lastname", "studentName", command.getStudentName());
        qb.optionalCriteria("papp.submitted >= :submitFrom", "submitFrom", command.getSubmitFrom());
        qb.optionalCriteria("papp.submitted <= :submitThru", "submitThru", command.getSubmitThru());
        qb.optionalCriteria("papp.status_code = :status", "status", command.getStatus());
        qb.optionalCriteria("e.id = :enterprise", "enterprise", command.getEnterprise());
        
        return JpaQueryUtil.pagingResult(qb, "papp.id as application_id, s.id as student_id, p.firstname, p.lastname"
                + ", sg.code as student_group_code, papp.status_code, papp.submitted, papp.add_info, e.id, e.name"
                + ", padm.valid_from, padm.valid_thru, papp.reject_reason, c.id as contract_id", em, pageable).map(r -> {
            PracticeApplicationSearchDto dto = new PracticeApplicationSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setStudentId(resultAsLong(r, 1));
            dto.setStudentName(PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3)));
            dto.setStudentGroup(resultAsString(r, 4));
            dto.setStatus(resultAsString(r, 5));
            dto.setSubmitDate(resultAsLocalDate(r, 6));
            dto.setAddInfo(resultAsString(r, 7));
            dto.setEnterpriseId(resultAsLong(r, 8));
            dto.setEnterpriseName(resultAsString(r, 9));
            dto.setValidFrom(resultAsLocalDate(r, 10));
            dto.setValidThru(resultAsLocalDate(r, 11));
            dto.setRejectReason(resultAsString(r, 12));            
            dto.setContractId(resultAsLong(r, 13));
            return dto;
        });
    }

    public void reject(HoisUserDetails user, PracticeApplication application, PracticeApplicationRejectForm form) {
        UserUtil.assertSameSchool(user, application.getPracticeAdmission().getEnterpriseSchool().getSchool());
        PracticeApplicationStatus applicationStatus = PracticeApplicationStatus.valueOf(EntityUtil.getCode(application.getStatus()));
        AssertionFailedException.throwIf(!(PracticeApplicationStatus.PR_TAOTLUS_E == applicationStatus
                || (PracticeApplicationStatus.PR_TAOTLUS_A == applicationStatus) && application.getContract() == null), 
                "Cannot reject this application");
        EntityUtil.setUsername(user.getUsername(), em);
        application.setStatus(em.getReference(Classifier.class, PracticeApplicationStatus.PR_TAOTLUS_T.name()));
        application.setRejectReason(form.getRejectReason());
    }

    public void accept(HoisUserDetails user, PracticeApplication application) {
        UserUtil.assertSameSchool(user, application.getPracticeAdmission().getEnterpriseSchool().getSchool());
        PracticeApplicationStatus applicationStatus = PracticeApplicationStatus.valueOf(EntityUtil.getCode(application.getStatus()));
        AssertionFailedException.throwIf(!EnumUtil.toNameSet(PracticeApplicationStatus.PR_TAOTLUS_E, PracticeApplicationStatus.PR_TAOTLUS_T)
                .contains(applicationStatus.name()), "Cannot accept this application");
        EntityUtil.setUsername(user.getUsername(), em);
        application.setStatus(em.getReference(Classifier.class, PracticeApplicationStatus.PR_TAOTLUS_A.name()));
    }
    
    public PracticeApplicationContractDto contractData(HoisUserDetails user, PracticeApplication application) {
        EnterpriseSchool enterpriseSchool = application.getPracticeAdmission().getEnterpriseSchool();
        UserUtil.assertSameSchool(user, enterpriseSchool.getSchool());
        PracticeApplicationContractDto dto = new PracticeApplicationContractDto();
        Student student = application.getStudent();
        dto.setIsHigher(Boolean.valueOf(StudentUtil.isHigher(student)));
        dto.setStudent(AutocompleteResult.of(student));
        Enterprise enterprise = enterpriseSchool.getEnterprise();
        dto.setEnterprise(new AutocompleteResult(EntityUtil.getId(enterprise), enterprise.getName(), enterprise.getName()));
        List<EnterpriseSchoolPerson> enterpriseSchoolPersons = enterpriseSchool.getEnterpriseSchoolPersons();
        List<EnterpriseSchoolPerson> contactPersons = StreamUtil.toFilteredList(esp -> Boolean.TRUE.equals(esp.getContact()), 
                enterpriseSchoolPersons);
        EnterpriseSchoolPerson contactPerson = contactPersons.size() == 1 ? contactPersons.get(0) : null;
        if (contactPerson != null) {
            dto.setContactPersonName(PersonUtil.fullname(contactPerson.getFirstname(), contactPerson.getLastname()));
            dto.setContactPersonPhone(contactPerson.getPhone());
            dto.setContactPersonEmail(contactPerson.getEmail());
        }
        List<EnterpriseSchoolPerson> supervisors = StreamUtil.toFilteredList(esp -> Boolean.TRUE.equals(esp.getSupervisor()), 
                enterpriseSchoolPersons);
        EnterpriseSchoolPerson supervisor = supervisors.size() == 1 ? supervisors.get(0) : null;
        if (supervisor != null) {
            dto.setSupervisorName(PersonUtil.fullname(supervisor.getFirstname(), supervisor.getLastname()));
            dto.setSupervisorPhone(supervisor.getPhone());
            dto.setSupervisorEmail(supervisor.getEmail());
        }
        return dto;
    }

    public Page<PracticeApplicationPeriodSearchDto> applicationPeriods(HoisUserDetails user,
            PracticeApplicationPeriodsSearchCommand cmd, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from practice_admission padm"
                + " join enterprise_school es on es.id = padm.enterprise_school_id"
                + " join enterprise e on e.id = es.enterprise_id").sort(pageable);
        qb.requiredCriteria("es.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalContains("e.\"name\"", "enterpriseName", cmd.getEnterpriseName());
        if (Boolean.TRUE.equals(cmd.getOpened())) {
            qb.requiredCriteria("padm.valid_from <= :now and padm.valid_thru >= :now", "now", LocalDate.now());
        }
        qb.optionalCriteria("padm.valid_from >= :validFrom", "validFrom", cmd.getValidFrom());
        qb.optionalCriteria("padm.valid_thru <= :validThru", "validThru", cmd.getValidThru());
        return JpaQueryUtil.pagingResult(qb, "padm.id, e.\"name\", padm.places, padm.valid_from, padm.valid_thru, e.reg_code", em, pageable).map(r -> {
            PracticeApplicationPeriodSearchDto dto = new PracticeApplicationPeriodSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setEnterpriseName(resultAsString(r, 1));
            dto.setPlaces(resultAsShort(r, 2));
            dto.setValidFrom(resultAsLocalDate(r, 3));
            dto.setValidThru(resultAsLocalDate(r, 4));
            dto.setEnterpriseRegCode(resultAsString(r, 5));
            return dto;
        });
    }

}
