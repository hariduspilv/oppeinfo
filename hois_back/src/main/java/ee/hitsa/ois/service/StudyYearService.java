package ee.hitsa.ois.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyPeriodEvent;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.StudyPeriodEventRepository;
import ee.hitsa.ois.repository.StudyPeriodRepository;
import ee.hitsa.ois.repository.StudyYearRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
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
    private SchoolRepository schoolRepository;
    @Autowired
    private StudyYearRepository studyYearRepository;
    @Autowired
    private StudyPeriodRepository studyPeriodRepository;
    @Autowired
    private StudyPeriodEventRepository studyPeriodEventRepository;

    // TODO use enum for classifier
    private static final String[] STUDY_PERIOD_EVENTS = {"SYNDMUS_AVES", "SYNDMUS_DEKP", "SYNDMUS_VOTA"};

    public List<StudyYearSearchDto> getStudyYears(Long schoolId) {
        return StreamUtil.toMappedList(StudyYearSearchDto::new, studyYearRepository.findStudyYearsBySchool(schoolId));
    }

    public StudyYear create(HoisUserDetails user, StudyYearForm studyYearForm) {
        StudyYear studyYear = new StudyYear();
        studyYear.setSchool(schoolRepository.getOne(user.getSchoolId()));
        return save(studyYear, studyYearForm);
    }

    public StudyYear save(StudyYear studyYear, StudyYearForm studyYearForm) {
        EntityUtil.bindToEntity(studyYearForm, studyYear, classifierRepository);
        return studyYearRepository.save(studyYear);
    }

    public void delete(StudyPeriod studyPeriod) {
        EntityUtil.deleteEntity(studyPeriodRepository, studyPeriod);
    }

    public StudyPeriod createStudyPeriod(StudyYear studyYear, StudyPeriodForm request) {
        return saveStudyPeriod(studyYear, new StudyPeriod(), request);
    }

    public StudyPeriod saveStudyPeriod(StudyYear studyYear, StudyPeriod studyPeriod, StudyPeriodForm request) {
        EntityUtil.bindToEntity(request, studyPeriod, classifierRepository);
        if (studyPeriod.getId() != null) {
            if (!EntityUtil.getId(studyYear).equals(EntityUtil.getId(studyPeriod.getStudyYear()))) {
                throw new AssertionFailedException("Study year mismatch");
            }
        } else {
            studyPeriod.setStudyYear(studyYear);
        }
        return studyPeriodRepository.save(studyPeriod);
    }

    public StudyPeriodEvent create(StudyYear studyYear, StudyPeriodEventForm request) {
        return save(studyYear, new StudyPeriodEvent(), request);
    }

    public StudyPeriodEvent save(StudyYear studyYear, StudyPeriodEvent studyPeriodEvent, StudyPeriodEventForm request) {
        EntityUtil.bindToEntity(request, studyPeriodEvent, classifierRepository, "studyPeriod");
        EntityUtil.setEntityFromRepository(request, studyPeriodEvent, studyPeriodRepository, "studyPeriod");
        String eventType = EntityUtil.getCode(studyPeriodEvent.getEventType());

        if (Arrays.asList(STUDY_PERIOD_EVENTS).contains(eventType)) {
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
        return studyPeriodEventRepository.save(studyPeriodEvent);
    }

    public void delete(StudyPeriodEvent studyPeriodEvent) {
        EntityUtil.deleteEntity(studyPeriodEventRepository, studyPeriodEvent);
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
