package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.protocol.ProtocolStudentHistory;
import ee.hitsa.ois.domain.protocol.ProtocolStudentOccupation;
import ee.hitsa.ois.domain.student.StudentOccupationCertificate;
import ee.hitsa.ois.enums.CommitteeType;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.message.StudentResultMessage;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.ProtocolUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.ProtocolStudentForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

@Transactional
public class AbstractProtocolService {
    
    protected static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private AutomaticMessageService automaticMessageService;
    @Autowired
    protected EntityManager em;

    @org.springframework.transaction.annotation.Transactional
    public void delete(HoisUserDetails user, Protocol protocol) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(protocol, em);
    }

    // TODO: proper per school protocol number generation
    protected String generateProtocolNumber() {
        Query q = em.createNativeQuery("select nextval('public.protocol_id_seq')");
        return DateUtils.shortYear(LocalDate.now()) + String.format("%04d", q.getSingleResult());
    }

    public static boolean gradeChangedButNotRemoved(ProtocolStudentForm dto, ProtocolStudent ps) {
        return dto.getGrade() != null && !dto.getGrade().isEmpty() &&
                !dto.getGrade().equals(EntityUtil.getNullableCode(ps.getGrade()));
    }

    protected static boolean gradeRemoved(ProtocolStudentForm dto, ProtocolStudent ps) {
        return dto.getGrade() == null || dto.getGrade().isEmpty() &&
                EntityUtil.getNullableCode(ps.getGrade()) != null;
    }

    protected static void addHistory(ProtocolStudent ps) {
        if(EntityUtil.getNullableCode(ps.getGrade()) != null) {
            ProtocolStudentHistory history = new ProtocolStudentHistory();
            history.setProtocolStudent(ps);
            history.setAddInfo(ps.getAddInfo());
            history.setGrade(ps.getGrade());
            ps.getProtocolStudentHistories().add(history);
        }
    }

    protected static void removeGrade(ProtocolStudent ps) {
        ps.setGrade(null);
        ps.setGradeDate(null);
        ps.setGradeMark(null);
        ps.setGradeValue(null);
    }

    protected static void gradeStudent(ProtocolStudent ps, Classifier grade, Short gradeMark, Boolean isLetterGrade) {
        ps.setGrade(grade);
        ps.setGradeDate(LocalDate.now());
        ps.setGradeMark(gradeMark);
        ps.setGradeValue(Boolean.TRUE.equals(isLetterGrade) ? grade.getValue2() : grade.getValue2());
    }

    protected void setConfirmation(HoisUserDetails user, Protocol protocol) {
        protocol.setStatus(em.getReference(Classifier.class, ProtocolStatus.PROTOKOLL_STAATUS_K.name()));
        protocol.setConfirmDate(LocalDate.now());
        protocol.setConfirmer(user.getUsername());
    }

    protected void sendStudentResultMessages(Protocol protocol) {
        // send automatic messages that student got result
        for (ProtocolStudent protocolStudent : protocol.getProtocolStudents()) {
            StudentResultMessage msg = new StudentResultMessage(protocolStudent);
            automaticMessageService.sendMessageToStudent(MessageType.TEATE_LIIK_OA_TULEMUS, protocolStudent.getStudent(), msg);
        }
    }
    
    public void removeStudent(HoisUserDetails user, ProtocolStudent student) {
        if (!ProtocolUtil.studentCanBeDeleted(student)) {
            throw new ValidationFailedException("finalProtocol.messages.cantRemoveStudent");
        }
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(student, em);
    }
    
    public AutocompleteResult lessonPlanModuleTeacher(Long studyYearId, Long curriculumVersionOmoduleId) {
        Query q = em.createNativeQuery("select lpm.teacher_id, p.firstname, p.lastname from lesson_plan_module lpm " + 
                "join lesson_plan lp on lpm.lesson_plan_id = lp.id " + 
                "join teacher t on lpm.teacher_id = t.id " +
                "join person p on t.person_id = p.id " +
                "where lp.study_year_id = ?1 and lpm.curriculum_version_omodule_id = ?2 and lpm.teacher_id is not null");
        q.setParameter(1, studyYearId);
        q.setParameter(2, curriculumVersionOmoduleId);
        
        List<?> teachers = q.setMaxResults(1).getResultList();
        if (!teachers.isEmpty()) {
            Object teacher = teachers.get(0);
            String teacherName = PersonUtil.fullname(resultAsString(teacher, 1), resultAsString(teacher, 2)); 
            return new AutocompleteResult(resultAsLong(teacher, 0), teacherName, teacherName); 
        }
        return null;
    }
    
    /* FINAL EXAM methods */
    
    public List<AutocompleteResult> committeesForSelection(HoisUserDetails user, LocalDate finalDate) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from committee c"
                + " left join committee_member cm on c.id = cm.committee_id"
                + " left join teacher t on t.id = cm.teacher_id left join person p on p.id = t.person_id ");

        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("c.type_code = :type", "type", CommitteeType.KOMISJON_K.name());
        qb.optionalCriteria("c.valid_from <= :finalDate", "finalDate", finalDate);
        qb.optionalCriteria("c.valid_thru >= :finalDate", "finalDate", finalDate);
        qb.groupBy(" c.id ");

        List<?> committees = qb.select("distinct c.id,"
                + " array_to_string(array_agg(case when cm.is_external"
                + " then cm.member_name"
                + " else p.firstname || ' ' || p.lastname end), ', ') as members", em).getResultList();

        return StreamUtil.toMappedList(r -> {
            return new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 1));
        }, committees);
    }
    
    protected void includeCorrectImportedOccupationCertificates(Protocol protocol) {
        if (!ClassifierUtil.equals(ProtocolStatus.PROTOKOLL_STAATUS_K, protocol.getStatus())) {
            List<Long> studentIds = StreamUtil.toMappedList(ps -> EntityUtil.getId(ps.getStudent()), protocol.getProtocolStudents());
            Set<String> occupations = curriculumOccpations(protocol);
            Map<Long, List<StudentOccupationCertificate>> studentOccupationCertificates = studentOccupationCertificates(studentIds, occupations);
            Map<Long, Map<Long, ProtocolStudentOccupation>> protocolCertificates =
                em.createQuery("select pso from ProtocolStudentOccupation pso where pso.protocolStudent.protocol.id = ?1", ProtocolStudentOccupation.class)
                    .setParameter(1, protocol.getId())
                    .getResultList().stream().filter(pso -> pso.getStudentOccupationCertificate() != null).collect(Collectors.groupingBy(pso -> EntityUtil.getId(pso.getProtocolStudent()), Collectors.toMap(pso -> EntityUtil.getId(pso.getStudentOccupationCertificate()), v -> v)));

            for(ProtocolStudent ps : protocol.getProtocolStudents()) {
                Map<Long, ProtocolStudentOccupation> protocolStudentCertificates = protocolCertificates.get(EntityUtil.getId(ps));
                Map<Long, StudentOccupationCertificate> studentCertificates = StreamUtil.toMap(soc -> EntityUtil.getId(soc), studentOccupationCertificates.get(EntityUtil.getId(ps.getStudent())));

                studentCertificates.forEach((k, v) -> {
                    if (protocolStudentCertificates == null || !protocolStudentCertificates.keySet().remove(k)) {
                        // remove manually added occupation certificate before replacing it with imported certificate
                        if (Boolean.FALSE.equals(protocol.getIsVocational())) {
                            ps.getProtocolStudentOccupations().removeIf(it -> it.getStudentOccupationCertificate() == null);
                        } else {
                            removeOccupationAddedToProtocol(ps, EntityUtil.getCode(v.getOccupation()),
                                    v.getPartOccupation() != null ? EntityUtil.getCode(v.getPartOccupation()) : null);
                        }
                        addStudentOccupationCertificateToProtocol(ps, v);
                    }
                });

                if(protocolStudentCertificates != null) {
                    protocolStudentCertificates.values().forEach(v -> {
                        ps.getProtocolStudentOccupations().removeIf(it -> it.equals(v));
                    });
                }
            }
        }
    }
    
    protected Set<String> curriculumOccpations(Protocol protocol) {
        if (Boolean.TRUE.equals(protocol.getIsVocational())) {
            return StreamUtil.toMappedSet(o -> EntityUtil.getCode(o.getOccupation()),
                    protocol.getProtocolVdata().getCurriculumVersion().getCurriculum().getOccupations());
        }
        return StreamUtil.toMappedSet(s -> EntityUtil.getCode(s.getOccupation()),
                protocol.getProtocolHdata().getCurriculum().getSpecialities().stream()
                        .filter(s -> s.getOccupation() != null).collect(Collectors.toList()));
    }
    
    private static void removeOccupationAddedToProtocol(ProtocolStudent student, String occupation, String partOccupation) {
        student.getProtocolStudentOccupations()
                .removeIf(pso -> occupation.equals(EntityUtil.getCode(pso.getOccupation()))
                        && ((partOccupation == null && pso.getPartOccupation() == null)
                                || (partOccupation != null && pso.getPartOccupation() != null
                                        && partOccupation.equals(EntityUtil.getCode(pso.getPartOccupation()))))
                        && pso.getStudentOccupationCertificate() == null);
    }
    
    protected static void addStudentOccupationCertificateToProtocol(ProtocolStudent protocolStudent, StudentOccupationCertificate ceritificate) {
        ProtocolStudentOccupation protocolStudentOccupation = new ProtocolStudentOccupation();
        protocolStudentOccupation.setProtocolStudent(protocolStudent);
        protocolStudentOccupation.setStudentOccupationCertificate(ceritificate);
        protocolStudentOccupation.setOccupation(ceritificate.getOccupation());
        protocolStudentOccupation.setPartOccupation(ceritificate.getPartOccupation());
        protocolStudent.getProtocolStudentOccupations().add(protocolStudentOccupation);
    }

    protected Map<Long, List<StudentOccupationCertificate>> studentOccupationCertificates(List<Long> studentIds, Collection<String> occupations) {
        if (!studentIds.isEmpty() && !occupations.isEmpty()) {
            List<StudentOccupationCertificate> occupationCertificates = em.createQuery("select soc from StudentOccupationCertificate soc"
                    + " where soc.student.id in (?1) and (soc.occupation.code in (?2) or soc.partOccupation.code in (?2))", StudentOccupationCertificate.class)
                    .setParameter(1, studentIds)
                    .setParameter(2, occupations)
                    .getResultList();
            return occupationCertificates.stream().collect(Collectors.groupingBy(oc -> EntityUtil.getId(oc.getStudent())));
        }
        return new HashMap<>();
    }
    
    protected static Map<String, Long> currentCertificateCodes(ProtocolStudent student) {
        Map<String, Long> currentCertificateCodes = new HashMap<>();
        for(ProtocolStudentOccupation c : student.getProtocolStudentOccupations()) {
            if (c.getPartOccupation() != null) {
                currentCertificateCodes.put(EntityUtil.getCode(c.getPartOccupation()), c.getId());
            } else {
                currentCertificateCodes.put(EntityUtil.getCode(c.getOccupation()), c.getId());
            }
        }
        return currentCertificateCodes;
    }
}
