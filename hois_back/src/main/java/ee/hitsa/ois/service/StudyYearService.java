package ee.hitsa.ois.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyPeriodEvent;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.StudyPeriodEventRepository;
import ee.hitsa.ois.repository.StudyYearRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
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

    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private StudyYearRepository studyYearRepository;
    @Autowired
    private StudyPeriodEventRepository studyPeriodEventRepository;

    // TODO use enum for classifier
    private static final Set<String> STUDY_PERIOD_EVENTS = new HashSet<>(Arrays.asList("SYNDMUS_AVES", "SYNDMUS_DEKP", "SYNDMUS_VOTA"));

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

    public void delete(StudyPeriod studyPeriod) {
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

    public void delete(StudyPeriodEvent studyPeriodEvent) {
        EntityUtil.deleteEntity(studyPeriodEvent, em);
    }

    public Long getPreviousStudyPeriod(Long school) {
        String from = "from study_period ss inner join study_year yy on ss.study_year_id = yy.id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);

        qb.requiredCriteria("yy.school_id = :school_id", "school_id", school);
        qb.requiredCriteria(
                "ss.end_date = (select max(ss2.end_date) from study_period ss2 join study_year yy2"
                        + " on ss2.study_year_id = yy2.id and yy2.school_id = :school_id where ss2.end_date < current_date) ",
                "school_id", school);
        List<?> result = qb.select("ss.id", em).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return Long.valueOf(((Number) result.get(0)).longValue());
    }

    public Long getCurrentStudyPeriod(Long school) {
        String from = "from study_period ss inner join study_year yy on ss.study_year_id = yy.id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);

        qb.requiredCriteria("yy.school_id = :school_id", "school_id", school);
        qb.requiredCriteria(
                "ss.end_date = (select min(ss2.end_date) from study_period ss2 join study_year yy2"
                        + " on ss2.study_year_id = yy2.id and yy2.school_id = :school_id where ss2.end_date >= current_date)",
                "school_id", school);
        List<?> result = qb.select("ss.id", em).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return Long.valueOf(((Number) result.get(0)).longValue());
    }

    public StudyYear getCurrentStudyYear(School school) {
        //TODO: what if study year is not found? Should we return previous study year or next study year instead of null?
        LocalDate now = LocalDate.now();
        return studyYearRepository.findOne((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), EntityUtil.getId(school)));
            filters.add(cb.lessThanOrEqualTo(root.get("startDate"), now));
            filters.add(cb.greaterThanOrEqualTo(root.get("endDate"), now));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
    }
}
