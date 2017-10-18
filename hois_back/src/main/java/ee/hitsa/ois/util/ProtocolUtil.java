package ee.hitsa.ois.util;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.protocol.ProtocolStudentHistory;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.web.commandobject.ProtocolStudentForm;

public abstract class ProtocolUtil {

    // TODO: proper per school protocol number generation
    public static String generateProtocolNumber(EntityManager em) {
        Query q = em.createNativeQuery("select nextval('public.protocol_id_seq')");
        return DateUtils.shortYear(LocalDate.now()) + String.format("%04d", q.getSingleResult());
    }

    public static boolean confirmed(Protocol protocol) {
        return ClassifierUtil.equals(ProtocolStatus.PROTOKOLL_STAATUS_K, protocol.getStatus());
    }

    public static boolean gradeChangedButNotRemoved(ProtocolStudentForm dto, ProtocolStudent ps) {
        return dto.getGrade() != null && !dto.getGrade().isEmpty() &&
                !dto.getGrade().equals(EntityUtil.getNullableCode(ps.getGrade()));
    }

    public static boolean gradeRemoved(ProtocolStudentForm dto, ProtocolStudent ps) {
        return dto.getGrade() == null || dto.getGrade().isEmpty() &&
                EntityUtil.getNullableCode(ps.getGrade()) != null;
    }

    public static void addHistory(ProtocolStudent ps) {
        if(EntityUtil.getNullableCode(ps.getGrade()) != null) {
            ProtocolStudentHistory history = new ProtocolStudentHistory();
            history.setProtocolStudent(ps);
            history.setAddInfo(ps.getAddInfo());
            history.setGrade(ps.getGrade());
            ps.getProtocolStudentHistories().add(history);
        }
    }

    public static void removeGrade(ProtocolStudent ps) {
        ps.setGrade(null);
        ps.setGradeDate(LocalDate.now());
        ps.setGradeMark(null);
        ps.setGradeValue(null);
    }

    public static void gradeStudent(ProtocolStudent ps, Classifier grade, Short gradeMark) {
        String gradeValue = grade.getValue();
        ps.setGradeMark(gradeMark);
        ps.setGradeValue(gradeValue);
        ps.setGrade(grade);
        ps.setGradeDate(LocalDate.now());
    }
}
