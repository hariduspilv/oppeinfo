package ee.hitsa.ois.service.subjectstudyperiod;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.repository.TeacherRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectStudyPeriodUtil;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodTeacherSearchDto;

@Transactional
@Service
public class SubjectStudyPeriodTeacherSearchService {
    
    @Autowired
    private TeacherRepository teacherRepository;
    
    public Page<SubjectStudyPeriodTeacherSearchDto> search(Long schoolId, SubjectStudyPeriodSearchCommand criteria,
            Pageable pageable) {
        return teacherRepository.findAll((root, query, cb) -> {

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));

            if (criteria.getTeacher() != null) {
                filters.add(cb.equal(root.get("id"), criteria.getTeacher()));
            }

            filters.add(cb.equal(root.get("isHigher"), Boolean.TRUE));
            filters.add(cb.equal(root.get("isActive"), Boolean.TRUE));

            /*
             * Search should show only those teachers, which have any
             * connections with subject_study_period_teacher table with specific
             * studyPeriod
             */
            Subquery<Long> sspTeacherQuery = query.subquery(Long.class);
            Root<SubjectStudyPeriodTeacher> sspTeacherRoot = sspTeacherQuery.from(SubjectStudyPeriodTeacher.class);
            sspTeacherQuery = sspTeacherQuery.select(sspTeacherRoot.get("teacher").get("id")).where(cb.equal(
                    sspTeacherRoot.get("subjectStudyPeriod").get("studyPeriod").get("id"), criteria.getStudyPeriod()));
            filters.add(root.get("id").in(sspTeacherQuery));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(t -> getDto(t, criteria.getStudyPeriod()));
    }
    
    private static SubjectStudyPeriodTeacherSearchDto getDto(Teacher t, Long studyPeriod) {
        SubjectStudyPeriodTeacherSearchDto dto = new SubjectStudyPeriodTeacherSearchDto();
        dto.setId(EntityUtil.getId(t));
        dto.setName(t.getPerson().getFullname());
        dto.setHours(getHours(t, studyPeriod));
        dto.setStudyPeriod(studyPeriod);
        dto.setTimetable(getTimetable(t, studyPeriod));
        return dto;
    }

    private static Long getHours(Teacher t, Long studyPeriod) {
        List<SubjectStudyPeriod> ssps = SubjectStudyPeriodUtil.filterSsps
                (StreamUtil.toMappedList(sspt -> sspt.getSubjectStudyPeriod(), t.getSubjectStudyPeriods()), 
                        studyPeriod);
        return SubjectStudyPeriodUtil.getHours(ssps);
    }

    private static Long getTimetable(Teacher t, Long studyPeriod) {
        // TODO Auto-generated method stub
        return null;
    }
}
