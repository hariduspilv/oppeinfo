package ee.hitsa.ois.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyPeriodEvent;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.StudyPeriodEventType;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.StudyPeriodEventRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.StudyPeriodValidation;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.StudyPeriodEventForm;
import ee.hitsa.ois.web.commandobject.StudyPeriodForm;
import ee.hitsa.ois.web.commandobject.StudyYearForm;
import ee.hitsa.ois.web.dto.StudyYearSearchDto;

@Service
@Transactional
public class StudyYearService {

    private static final Set<String> STUDY_PERIOD_EVENTS = EnumUtil.toNameSet(
            StudyPeriodEventType.SYNDMUS_AVES, StudyPeriodEventType.SYNDMUS_DEKP, StudyPeriodEventType.SYNDMUS_VOTA);

    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private StudyPeriodEventRepository studyPeriodEventRepository;

    public List<StudyYearSearchDto> getStudyYears(Long schoolId) {
        Query q = em.createNativeQuery("select c.code, c.name_et, c.name_en, sy.id, sy.start_date, sy.end_date, sy.count " +
                "from classifier c left outer join " +
                "(select y.id, y.start_date, y.end_date, y.year_code, count(p.study_year_id) " +
                "from study_year y left outer join study_period p on y.id = p.study_year_id " +
                "where y.school_id = ?1 group by y.id) sy on c.code = sy.year_code " +
                "where c.main_class_code = ?2 order by c.code desc");
        q.setParameter(1, schoolId);
        q.setParameter(2, MainClassCode.OPPEAASTA.name());
        List<?> data = q.getResultList();

        return StreamUtil.toMappedList(r -> new StudyYearSearchDto((Object[])r), data);
    }

    public StudyYear create(HoisUserDetails user, StudyYearForm studyYearForm) {
        StudyYear studyYear = new StudyYear();
        studyYear.setSchool(em.getReference(School.class, user.getSchoolId()));
        return save(studyYear, studyYearForm);
    }

    public StudyYear save(StudyYear studyYear, StudyYearForm studyYearForm) {
        EntityUtil.bindToEntity(studyYearForm, studyYear, classifierRepository);
        return EntityUtil.save(studyYear, em);
    }

    public void delete(HoisUserDetails user, StudyPeriod studyPeriod) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(studyPeriod, em);
    }

    public StudyPeriod createStudyPeriod(StudyYear studyYear, StudyPeriodForm request) {
        return saveStudyPeriod(studyYear, new StudyPeriod(), request);
    }

    public StudyPeriod saveStudyPeriod(StudyYear studyYear, StudyPeriod studyPeriod, StudyPeriodForm request) {
        StudyPeriodValidation.validate(studyYear, studyPeriod, request);

        EntityUtil.bindToEntity(request, studyPeriod, classifierRepository);
        if (studyPeriod.getId() != null) {
            if (!EntityUtil.getId(studyYear).equals(EntityUtil.getId(studyPeriod.getStudyYear()))) {
                throw new AssertionFailedException("Study year mismatch");
            }
        } else {
            studyPeriod.setStudyYear(studyYear);
        }
        return EntityUtil.save(studyPeriod, em);
    }

    public StudyPeriodEvent create(StudyYear studyYear, StudyPeriodEventForm request) {
        return save(studyYear, new StudyPeriodEvent(), request);
    }

    public StudyPeriodEvent save(StudyYear studyYear, StudyPeriodEvent studyPeriodEvent, StudyPeriodEventForm request) {
        EntityUtil.bindToEntity(request, studyPeriodEvent, classifierRepository, "studyPeriod");
        studyPeriodEvent.setStudyPeriod(EntityUtil.getOptionalOne(StudyPeriod.class, request.getStudyPeriod(), em));

        String eventType = EntityUtil.getCode(studyPeriodEvent.getEventType());
        if (STUDY_PERIOD_EVENTS.contains(eventType)) {
            Set<StudyPeriodEvent> events = studyPeriodEventRepository.findAllByStudyYearAndStudyPeriodAndEventType(studyYear, studyPeriodEvent.getStudyPeriod(), studyPeriodEvent.getEventType());
            if (events.stream().anyMatch(it -> !it.getId().equals(studyPeriodEvent.getId()))) {
                throw new ValidationFailedException("eventType", "duplicate-found");
            }
        }

        if (studyPeriodEvent.getId() != null) {
            if (!EntityUtil.getId(studyYear).equals(EntityUtil.getId(studyPeriodEvent.getStudyYear())) ||
                    studyPeriodEvent.getStudyPeriod() != null && !EntityUtil.getId(studyPeriodEvent.getStudyPeriod().getStudyYear()).equals(EntityUtil.getId(studyYear))) {
                throw new AssertionFailedException("Study year mismatch");
            }
        } else {
            studyPeriodEvent.setStudyYear(studyYear);
        }
        return EntityUtil.save(studyPeriodEvent, em);
    }

    public void delete(HoisUserDetails user, StudyPeriodEvent studyPeriodEvent) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(studyPeriodEvent, em);
    }

    /**
     * Get previous study period id
     * @param schoolId
     * @return null if there is no previous study period
     */
    public Long getPreviousStudyPeriod(Long schoolId) {
        String from = "from study_period ss inner join study_year yy on ss.study_year_id = yy.id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.requiredCriteria("yy.school_id = :school_id", "school_id", schoolId);
        qb.requiredCriteria(
                "ss.end_date = (select max(ss2.end_date) from study_period ss2 join study_year yy2"
                        + " on ss2.study_year_id = yy2.id and yy2.school_id = :school_id where ss2.end_date < current_date) ",
                "school_id", schoolId);
        List<?> result = qb.select("ss.id", em).setMaxResults(1).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return Long.valueOf(((Number) result.get(0)).longValue());
    }

    /**
     * Get current study period id
     * @param schoolId
     * @return null if there is no current study period
     */
    public Long getCurrentStudyPeriod(Long schoolId) {
        String from = "from study_period ss inner join study_year yy on ss.study_year_id = yy.id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.requiredCriteria("yy.school_id = :school_id", "school_id", schoolId);
        qb.requiredCriteria(
                "ss.end_date = (select min(ss2.end_date) from study_period ss2 join study_year yy2"
                        + " on ss2.study_year_id = yy2.id and yy2.school_id = :school_id where ss2.end_date >= current_date)",
                "school_id", schoolId);
        List<?> result = qb.select("ss.id", em).setMaxResults(1).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return Long.valueOf(((Number) result.get(0)).longValue());
    }

    /**
     * Returns current study year
     * @param schoolId
     * @return null if there is no current study year
     */
    public StudyYear getCurrentStudyYear(Long schoolId) {
        LocalDate now = LocalDate.now();
        List<StudyYear> data = em.createQuery("select sy from StudyYear sy where sy.school.id = ?1 and sy.startDate <= ?2 and sy.endDate >= ?2", StudyYear.class)
            .setParameter(1, schoolId)
            .setParameter(2, now)
            .setMaxResults(1).getResultList();
        return data.isEmpty() ? null : data.get(0);
    }
}
