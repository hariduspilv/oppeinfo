package ee.hitsa.ois.service;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.StudyYearRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.StudyYearForm;
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

    public List<StudyYearsSearchDto> getStudyYears(Long schoolId) {
        return studyYearRepository.findStudyYearsBySchool(schoolId).stream().map(StudyYearsSearchDto::new).collect(Collectors.toList());
    }

    public StudyYearDto save(School school, StudyYear studyYear, StudyYearForm studyYearForm) {
        EntityUtil.bindToEntity(studyYearForm, studyYear, classifierRepository);
        studyYear.setSchool(school);

        return StudyYearDto.of(studyYearRepository.save(studyYear));
    }
}
