package ee.hitsa.ois.service.subjectstudyperiod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.repository.SubjectRepository;
import ee.hitsa.ois.service.XlsService;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectStudyPeriodUtil;
import ee.hitsa.ois.web.commandobject.subject.studyperiod.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSearchDto;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

@Transactional
@Service
public class SubjectStudyPeriodSubjectSearchService {
    
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private XlsService xlsService;

    public Page<SubjectStudyPeriodSearchDto> search(Long schoolId, SubjectStudyPeriodSearchCommand criteria,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period ssp " +
                "join subject s on ssp.subject_id = s.id " +
                "join study_period sp on ssp.study_period_id = sp.id ");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("s.status_code = :statusCode", "statusCode", SubjectStatus.AINESTAATUS_K);
        qb.optionalCriteria((criteria.getStudyPeriod() != null ? "sp.id = :timeId" : "sp.study_year_id = :timeId"),
                "timeId", criteria.getStudyPeriod() != null ? criteria.getStudyPeriod() : criteria.getStudyYear());
        qb.optionalCriteria("s.id = :subjectId", "subjectId", criteria.getSubject());
        qb.optionalCriteria("exists(select 1 from subject_study_period_teacher sspt " +
                "where sspt.subject_study_period_id = ssp.id and sspt.teacher_id = :teacherId)",
                "teacherId", criteria.getTeacher());

        qb.sort(pageable);
        qb.groupBy("s.id, sp.id");
        Page<Object> pagingResult = JpaQueryUtil.pagingResult(qb, "s.id as sid, sp.id as spid", em, pageable);
        Set<Long> subjectIds = pagingResult.getContent().stream()
                .map(r -> resultAsLong(r, 0))
                .collect(Collectors.toSet());
        if (subjectIds.isEmpty()) {
            // Empty mapping for return
            return pagingResult.map(r -> new SubjectStudyPeriodSearchDto());
        }

        // fetch everything we need for this request to decrease amount of requests
        em.createQuery("select s from Subject s " +
                "join fetch s.subjectStudyPeriods ssp " +
                "join fetch ssp.studyPeriod sp " +
                "join fetch sp.studyYear sy " +
                "where s.id in :sIds", Subject.class)
                .setParameter("sIds", subjectIds)
                .getResultList();

        return pagingResult.map(r -> {
            Subject s = em.getReference(Subject.class, resultAsLong(r, 0));
            StudyPeriod period = em.getReference(StudyPeriod.class, resultAsLong(r, 1));
            SubjectStudyPeriodSearchDto dto = new SubjectStudyPeriodSearchDto();
            dto.setSubject(AutocompleteResult.of(s));
            dto.setStudyPeriod(AutocompleteResult.ofWithYear(period));

            Set<Person> teachers = new HashSet<>();
            List<SubjectStudyPeriod> ssps = SubjectStudyPeriodUtil.filterSsps(s.getSubjectStudyPeriods(), period.getId());
            for (SubjectStudyPeriod ssp : ssps) {
                teachers.addAll(StreamUtil.toMappedList(t -> t.getTeacher().getPerson(), ssp.getTeachers()));
            }
            dto.setTeachers(PersonUtil.sorted(teachers.stream()));
            dto.setHours(SubjectStudyPeriodUtil.getHours(ssps));
            return dto;
        });
    }

    public byte[] searchBySubjectAsExcel(Long schoolId, SubjectStudyPeriodSearchCommand criteria) {
        List<SubjectStudyPeriodSearchDto> subjects = search(schoolId, criteria,
                new PageRequest(0, Integer.MAX_VALUE, Direction.ASC, "code")).getContent();

        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("subjectstudyperiod.studyYear", criteria.getStudyYear() != null
                ? AutocompleteResult.of(em.getReference(StudyYear.class, criteria.getStudyYear())) : "-");
        parameters.put("subjectstudyperiod.period", criteria.getStudyPeriod() != null
                ? em.getReference(StudyPeriod.class, criteria.getStudyPeriod()) : "-");

        Map<String, Object> data = new HashMap<>();
        data.put("subjects", subjects);
        data.put("parameters", parameters);
        return xlsService.generate("searchBySubject.xls", data);
    }
}
