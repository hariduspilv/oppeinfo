package ee.hitsa.ois.service.subjectstudyperiod;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodCapacity;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.SubjectRepository;
import ee.hitsa.ois.repository.SubjectStudyPeriodRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodCapacityDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodDtoContainer;

@Transactional
@Service
public class SubjectStudyPeriodCapacitiesService {
    
    
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private SubjectStudyPeriodRepository subjectStudyPeriodRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    
    /**
     * TODO: method and class does not match each other (by name at least by name),
     * but still both of them are used in subject study period controller
     */
    public void setSubjects(SubjectStudyPeriodDtoContainer container) {
        List<Long> subjectIds = StreamUtil.toMappedList(s -> s.getSubject(), container.getSubjectStudyPeriodDtos());
        List<Subject> subjects = subjectRepository.findAll(subjectIds);
        List<AutocompleteResult> dtos = StreamUtil.toMappedList(AutocompleteResult::of, subjects);
        container.setSubjects(dtos);
    }
    
    public void updateSspCapacities(Long schoolId, SubjectStudyPeriodDtoContainer container) {
        List<SubjectStudyPeriod> ssps = new ArrayList<>();

        for (SubjectStudyPeriodDto dto : container.getSubjectStudyPeriodDtos()) {
            SubjectStudyPeriod ssp = em.getReference(SubjectStudyPeriod.class, dto.getId());

            AssertionFailedException.throwIf(!EntityUtil.getId(ssp.getSubject().getSchool()).equals(schoolId),
                    "User and subject have different schools!");

            List<SubjectStudyPeriodCapacityDto> newCapacities = StreamUtil.toFilteredList(c -> c.getHours() != null, dto.getCapacities());
            EntityUtil.bindEntityCollection(ssp.getCapacities(), SubjectStudyPeriodCapacity::getId, newCapacities,
                    SubjectStudyPeriodCapacityDto::getId, dto3 -> {
                        SubjectStudyPeriodCapacity newCapacity = EntityUtil.bindToEntity(dto3,
                                new SubjectStudyPeriodCapacity(), classifierRepository);
                        newCapacity.setSubjectStudyPeriod(ssp);
                        return newCapacity;
                    }, (dto2, c) -> {
                        c.setHours(dto2.getHours());
                    });
            ssps.add(ssp);
        }
        subjectStudyPeriodRepository.save(ssps);
    }

}
