package ee.hitsa.ois.service.subjectstudyperiod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.repository.SubjectRepository;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectStudyPeriodUtil;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSearchDto;

@Transactional
@Service
public class SubjectStudyPeriodSubjectSearchService {
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    public Page<SubjectStudyPeriodSearchDto> search(Long schoolId, SubjectStudyPeriodSearchCommand criteria,
            Pageable pageable) {
        return subjectRepository.findAll((root, query, cb) -> {

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));

            filters.add(cb.equal(root.get("status").get("code"), SubjectStatus.AINESTAATUS_K.name()));
            /*
             * Search should show only those subjects, which have any
             * connections with subject_study_period table with specific
             * studyPeriod
             */
            if(criteria.getStudyPeriod() != null) {
                Subquery<Long> sspSubjectQuery = query.subquery(Long.class);
                Root<SubjectStudyPeriod> sspSubjectRoot = sspSubjectQuery.from(SubjectStudyPeriod.class);
                sspSubjectQuery = sspSubjectQuery.select(sspSubjectRoot.get("subject").get("id"))
                        .where(cb.equal(sspSubjectRoot.get("studyPeriod").get("id"), criteria.getStudyPeriod()));
                filters.add(root.get("id").in(sspSubjectQuery));
            }

            if (criteria.getSubject() != null) {
                filters.add(cb.equal(root.get("id"), criteria.getSubject()));
            }
            if (criteria.getTeacher() != null) {
                Subquery<Long> sspTeachersQuery = query.subquery(Long.class);
                Root<SubjectStudyPeriodTeacher> sspTeachertRoot = sspTeachersQuery
                        .from(SubjectStudyPeriodTeacher.class);
                sspTeachersQuery = sspTeachersQuery
                        .select(sspTeachertRoot.get("subjectStudyPeriod").get("subject").get("id"))
                        .where(cb.equal(sspTeachertRoot.get("teacher").get("id"), criteria.getTeacher()));
                filters.add(root.get("id").in(sspTeachersQuery));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(s -> {
            SubjectStudyPeriodSearchDto dto = new SubjectStudyPeriodSearchDto();
            dto.setSubject(AutocompleteResult.of(s));

            Set<Person> teachers = new HashSet<>();
            List<SubjectStudyPeriod> ssps = SubjectStudyPeriodUtil.filterSsps(s.getSubjectStudyPeriods(), criteria.getStudyPeriod());
            for (SubjectStudyPeriod ssp : ssps) {
                teachers.addAll(StreamUtil.toMappedList(t -> t.getTeacher().getPerson(), ssp.getTeachers()));
            }
            dto.setTeachers(PersonUtil.sorted(teachers.stream()));
            dto.setHours(SubjectStudyPeriodUtil.getHours(ssps));
            return dto;
        });
    }

}
