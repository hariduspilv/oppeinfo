package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.protocol.ProtocolStudentHistory;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.message.StudentResultMessage;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.ProtocolUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.ProtocolStudentForm;
import ee.hitsa.ois.web.dto.finalexamprotocol.FinalExamVocationalProtocolCommitteeSelectDto;

@Transactional
public class AbstractProtocolService {
    
    private static final Pattern GRADE_PATTERN = Pattern.compile("[0-5]");

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
        ps.setGradeDate(LocalDate.now());
        ps.setGradeMark(null);
        ps.setGradeValue(null);
    }

    protected static void gradeStudent(ProtocolStudent ps, Classifier grade, Short gradeMark) {
        String gradeValue = grade.getValue();
        ps.setGradeMark(gradeMark);
        ps.setGradeValue(gradeValue);
        ps.setGrade(grade);
        ps.setGradeDate(LocalDate.now());
    }

    protected void setConfirmation(HoisUserDetails user,Protocol protocol) {
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
    
    public List<FinalExamVocationalProtocolCommitteeSelectDto> committeesForSelection(HoisUserDetails user, LocalDate finalDate) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from committee c"
                + " left join committee_member cm on c.id = cm.committee_id"
                + " left join teacher t on t.id = cm.teacher_id left join person p on p.id = t.person_id ");

        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("c.valid_from <= :finalExamDate", "finalExamDate", finalDate);
        qb.optionalCriteria("c.valid_thru >= :finalExamDate", "finalExamDate", finalDate);
        qb.groupBy(" c.id ");

        List<?> committees = qb.select("distinct c.id,"
                + " array_to_string(array_agg(case when cm.is_external"
                + " then cm.member_name"
                + " else p.firstname || ' ' || p.lastname end), ', ') as members", em).getResultList();

        return StreamUtil.toMappedList(r -> {
            FinalExamVocationalProtocolCommitteeSelectDto dto = new FinalExamVocationalProtocolCommitteeSelectDto();
            dto.setId(resultAsLong(r, 0));
            dto.setMembers(resultAsString(r, 1));
            return dto;
        }, committees);
    }
    
    protected static Short getHigherGradeMark(Classifier grade) {
        Short gradeMark = null;
        String gradeValue = grade.getValue();
        if(GRADE_PATTERN.matcher(gradeValue).matches()) {
            gradeMark = Short.valueOf(gradeValue);
        }
        return gradeMark;
    }
    
    public void removeStudent(HoisUserDetails user, ProtocolStudent student) {
        if (!ProtocolUtil.studentCanBeDeleted(student)) {
            throw new ValidationFailedException("finalExamProtocol.messages.cantRemoveStudent");
        }
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(student, em);
    }
}
