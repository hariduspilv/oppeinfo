package ee.hitsa.ois.service;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.StudyPeriodRepository;
import ee.hitsa.ois.repository.StudyYearRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.StudyPeriodForm;
import ee.hitsa.ois.web.commandobject.StudyYearForm;
import ee.hitsa.ois.web.dto.StudyPeriodDto;
import ee.hitsa.ois.web.dto.StudyYearDto;
import ee.hitsa.ois.web.dto.StudyYearsSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        studyPeriodRepository.delete(studyPeriod);
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
}
