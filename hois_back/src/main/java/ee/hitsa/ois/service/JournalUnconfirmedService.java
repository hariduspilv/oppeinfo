package ee.hitsa.ois.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.enums.JournalStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;

/**
 * This class provides information on home page about 
 * unconfirmed journals
 */
@Service
public class JournalUnconfirmedService {
    
    @Autowired
    private EntityManager em;
    
    public Map<String, ?> getInfo(HoisUserDetails user) {
        Map<String, Object> response = new HashMap<>();
        Number count = count(user);
        response.put("unconfirmedJournalCount", count);
        if(count.longValue() != 0) {
            response.put("hasEndedUnconfirmedJournals", Boolean.valueOf(hasEndedUnconfirmedJournals(user)));
        }
        return response;
    }

    private static final String FILTER_JOURNAL_BY_TEACHER =
            "exists(select jt.id "
            + "from journal_teacher jt "
            + "where jt.journal_id = j.id and jt.teacher_id = :teacherId) ";
    
    /**
     * Teacher or school admin must see unconfirmed journals' count 
     * which end in two weeks
     */
    private static final int TWO_WEEKS = 2; 

    /**
     * @return number of unconfirmed journals which will expire in two weeks 
     */
    private Number count(HoisUserDetails user) {
      JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j");
      qb.requiredCriteria("j.school_id = :schoolId", "schoolId", user.getSchoolId());
      qb.optionalCriteria(FILTER_JOURNAL_BY_TEACHER, "teacherId", user.getTeacherId());
      qb.requiredCriteria("j.end_date > :twoWeeksBeforeEnd", "twoWeeksBeforeEnd", LocalDate.now().minusWeeks(TWO_WEEKS));
      qb.requiredCriteria("j.status_code = :notConfirmed", "notConfirmed", JournalStatus.PAEVIK_STAATUS_T);
      return qb.count(em);
    }

    /**
     * @return are there any unconfirmed journals already ended
     */
    private boolean hasEndedUnconfirmedJournals(HoisUserDetails user) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j");
        qb.requiredCriteria("j.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria(FILTER_JOURNAL_BY_TEACHER, "teacherId", user.getTeacherId());
        qb.filter("j.end_date > current_date");
        qb.requiredCriteria("j.end_date > :today", "today", LocalDate.now());
        qb.requiredCriteria("j.status_code = :notConfirmed", "notConfirmed", JournalStatus.PAEVIK_STAATUS_T);
        return !qb.select("j.id", em).setMaxResults(1).getResultList().isEmpty();
    }
}
