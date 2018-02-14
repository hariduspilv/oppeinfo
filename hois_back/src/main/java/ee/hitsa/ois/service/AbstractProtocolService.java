package ee.hitsa.ois.service;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
import ee.hitsa.ois.web.commandobject.ProtocolStudentForm;

public class AbstractProtocolService {

    @Autowired
    private AutomaticMessageService automaticMessageService;
    @Autowired
    protected EntityManager em;

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
}
