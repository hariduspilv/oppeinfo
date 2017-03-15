package ee.hitsa.ois.service;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyPeriodEvent;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.StudyPeriodEventRepository;
import ee.hitsa.ois.repository.StudyPeriodRepository;
import ee.hitsa.ois.repository.StudyYearRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.StudyPeriodEventForm;
import ee.hitsa.ois.web.commandobject.StudyPeriodForm;
import ee.hitsa.ois.web.commandobject.StudyYearForm;
import ee.hitsa.ois.web.dto.StudyYearsSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudyYearService {

    @Autowired
    private ClassifierRepository classifierRepository;

    @Autowired
    private StudyYearRepository studyYearRepository;

    @Autowired
    private StudyPeriodRepository studyPeriodRepository;

    @Autowired
    private StudyPeriodEventRepository studyPeriodEventRepository;

    public List<StudyYearsSearchDto> getStudyYears(Long schoolId) {
        return studyYearRepository.findStudyYearsBySchool(schoolId).stream().map(StudyYearsSearchDto::new).collect(Collectors.toList());
    }

    public StudyYear create(School school, StudyYearForm studyYearForm) {
        return save(school, new StudyYear(), studyYearForm);
    }

    public StudyYear save(School school, StudyYear studyYear, StudyYearForm studyYearForm) {
        EntityUtil.bindToEntity(studyYearForm, studyYear, classifierRepository);
        studyYear.setSchool(school);
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
                throw new IllegalArgumentException();
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
        if (studyPeriodEvent.getId() != null) {
            if (!EntityUtil.getId(studyYear).equals(EntityUtil.getId(studyPeriodEvent.getStudyYear())) ||
                    studyPeriodEvent.getStudyPeriod() != null && !EntityUtil.getId(studyPeriodEvent.getStudyPeriod().getStudyYear()).equals(EntityUtil.getId(studyYear))) {
                throw new IllegalArgumentException();
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
