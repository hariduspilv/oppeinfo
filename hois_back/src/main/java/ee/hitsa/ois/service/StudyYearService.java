package ee.hitsa.ois.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyPeriodEvent;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.StudyPeriodEventRepository;
import ee.hitsa.ois.repository.StudyPeriodRepository;
import ee.hitsa.ois.repository.StudyYearRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.StudyPeriodEventForm;
import ee.hitsa.ois.web.commandobject.StudyPeriodForm;
import ee.hitsa.ois.web.commandobject.StudyYearForm;
import ee.hitsa.ois.web.dto.StudyYearsSearchDto;

@Service
@Transactional
public class StudyYearService {

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

    public List<StudyYearsSearchDto> getStudyYears(Long schoolId) {
        return StreamUtil.toMappedList(StudyYearsSearchDto::new, studyYearRepository.findStudyYearsBySchool(schoolId));
    }

    public StudyYear getCurrentStudyYear(Long schoolId) {
        LocalDate now = LocalDate.now();
        return studyYearRepository.findFirstBySchoolIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByStartDateDesc(schoolId, now, now);
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
}
