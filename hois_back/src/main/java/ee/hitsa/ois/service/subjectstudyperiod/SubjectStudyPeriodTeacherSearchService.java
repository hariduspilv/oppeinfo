package ee.hitsa.ois.service.subjectstudyperiod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.repository.TeacherRepository;
import ee.hitsa.ois.service.XlsService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectStudyPeriodUtil;
import ee.hitsa.ois.web.commandobject.subject.studyperiod.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodTeacherSearchDto;

import static ee.hitsa.ois.service.subjectstudyperiod.SubjectStudyPeriodCapacitiesService.SQL_SELECT_TEACHER_CAPACITY;
import static ee.hitsa.ois.util.JpaQueryUtil.getOrDefault;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

@Transactional
@Service
public class SubjectStudyPeriodTeacherSearchService {

    @Autowired
    private EntityManager em;
    @Autowired
    private XlsService xlsService;
    @Autowired
    private SchoolService schoolService;
    
    public Page<SubjectStudyPeriodTeacherSearchDto> search(Long schoolId, SubjectStudyPeriodSearchCommand criteria,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period_teacher sspt " +
                "join teacher t on sspt.teacher_id = t.id " +
                "join person p on t.person_id = p.id " +
                "join subject_study_period ssp on sspt.subject_study_period_id = ssp.id " +
                "join study_period sp on ssp.study_period_id = sp.id ");
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("t.id in :tId", "tId", criteria.getTeacher());
        qb.filter("t.is_higher = true");
        qb.filter("t.is_active = true");
        qb.optionalCriteria((criteria.getStudyPeriod() != null ? "sp.id = :timeId" : "sp.study_year_id = :timeId"),
                "timeId", criteria.getStudyPeriod() != null ? criteria.getStudyPeriod() : criteria.getStudyYear());

        qb.sort(pageable);
        qb.groupBy("t.id, sp.id, p.id");
        Page<Object> pagingResult = JpaQueryUtil.pagingResult(qb, "t.id as tid, sp.id as spid," +
                " coalesce((select sum(sspc2.hours)" +
                    " from subject_study_period_teacher sspt2" +
                    " join subject_study_period ssp2 on sspt2.subject_study_period_id = ssp2.id" +
                    " join study_period sp2 on ssp2.study_period_id = sp2.id" +
                    " join subject_study_period_capacity sspc2 on ssp2.id = sspc2.subject_study_period_id" +
                    " where sspt2.teacher_id = t.id" +
                        " and (ssp2.is_capacity_diff is null or ssp2.is_capacity_diff is false)" +
                        " and sp.id = ssp2.study_period_id" +
                    " group by sspt2.teacher_id), 0) +" +
                " coalesce((select sum(ssptc2.hours)" +
                    " from subject_study_period_teacher sspt2" +
                    " join subject_study_period ssp2 on sspt2.subject_study_period_id = ssp2.id" +
                    " join study_period sp2 on ssp2.study_period_id = sp2.id" +
                    " join subject_study_period_capacity sspc2 on ssp2.id = sspc2.subject_study_period_id" +
                    " join (" + SQL_SELECT_TEACHER_CAPACITY + ") ssptc2" +
                        " on ssptc2.subject_study_period_capacity_id = sspc2.id and" +
                        " ssptc2.subject_study_period_teacher_id = sspt2.id" +
                    " where sspt2.teacher_id = t.id" +
                        " and ssp2.is_capacity_diff = true" +
                        " and sp.id = ssp2.study_period_id" +
                    " group by sspt2.teacher_id), 0) as sp_hours," +
                " coalesce((select sum(jc.hours)" +
                    " from journal_teacher jt" +
                    " join journal j on j.id = jt.journal_id" +
                    " join journal_capacity jc on j.id = jc.journal_id" +
                    " where (j.is_capacity_diff is null or j.is_capacity_diff = false)" +
                        " and jt.teacher_id = t.id" +
                        " and sp.id = jc.study_period_id" +
                    " group by jt.teacher_id), 0) +" +
                " coalesce((select sum(jtc.hours)" +
                    " from journal_teacher jt2" +
                    " join journal j2 on j2.id = jt2.journal_id" +
                    " join journal_teacher_capacity jtc on jt2.id = jtc.journal_teacher_id" +
                    " where j2.is_capacity_diff = true" +
                        " and t.id = jt2.teacher_id" +
                        " and sp.id = jtc.study_period_id" +
                    " group by jt2.teacher_id), 0) as j_hours", em, pageable);
        Set<Long> teacherIds = pagingResult.getContent().stream()
                .map(r -> resultAsLong(r, 0))
                .collect(Collectors.toSet());
        if (teacherIds.isEmpty()) {
            // Empty mapping for return
            return pagingResult.map(r -> new SubjectStudyPeriodTeacherSearchDto());
        }

        // fetch everything we need for this request to decrease amount of requests
        em.createQuery("select t from Teacher t " +
                "join fetch t.subjectStudyPeriods sspt " +
                //"join fetch sspt.capacities ssptc " +
                "join fetch sspt.subjectStudyPeriod ssp " +
                //"join fetch ssp.capacities sspc " +
                "join fetch ssp.studyPeriod sp " +
                "join fetch sp.studyYear sy " +
                "where t.id in :tIds", Teacher.class)
                .setParameter("tIds", teacherIds)
                .getResultList();

        return pagingResult.map(r -> getDto(
                em.getReference(Teacher.class, resultAsLong(r, 0)),
                em.getReference(StudyPeriod.class, resultAsLong(r, 1)),
                getOrDefault(resultAsLong(r, 2), 0L),
                getOrDefault(resultAsLong(r, 3), 0L)
        ));
    }
    
    private static SubjectStudyPeriodTeacherSearchDto getDto(Teacher t, StudyPeriod studyPeriod,
                                                             Long higherHours, Long vocationalHours) {
        SubjectStudyPeriodTeacherSearchDto dto = new SubjectStudyPeriodTeacherSearchDto();
        dto.setId(EntityUtil.getId(t));
        dto.setName(t.getPerson().getFullname());
        dto.setHours(Long.valueOf(higherHours.longValue() + vocationalHours.longValue()));
        dto.setHigherHours(higherHours);
        dto.setVocationalHours(vocationalHours);
        dto.setStudyPeriod(AutocompleteResult.ofWithYear(studyPeriod));
        dto.setTimetable(null);
        return dto;
    }

    private static Long getHours(Teacher t, Long studyPeriod) {
        List<SubjectStudyPeriod> ssps = SubjectStudyPeriodUtil.filterSsps
                (StreamUtil.toMappedList(sspt -> sspt.getSubjectStudyPeriod(), t.getSubjectStudyPeriods()), 
                        studyPeriod);
        return SubjectStudyPeriodUtil.getHours(ssps, EntityUtil.getId(t));
    }
    
    public byte[] searchByTeacherAsExcel(Long schoolId, SubjectStudyPeriodSearchCommand criteria) {
        List<SubjectStudyPeriodTeacherSearchDto> teachers = search(schoolId, criteria,
                new PageRequest(0, Integer.MAX_VALUE, Direction.ASC, "p.lastname", "p.firstname")).getContent();

        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("subjectstudyperiod.studyYear", criteria.getStudyYear() != null
                ? AutocompleteResult.of(em.getReference(StudyYear.class, criteria.getStudyYear())) : "-");
        parameters.put("subjectstudyperiod.period", criteria.getStudyPeriod() != null
                ? em.getReference(StudyPeriod.class, criteria.getStudyPeriod()) : "-");

        SchoolService.SchoolType schoolType = schoolService.schoolType(schoolId);

        Map<String, Object> data = new HashMap<>();
        data.put("teachers", teachers);
        data.put("parameters", parameters);
        data.put("vocational", Boolean.valueOf(schoolType.isVocational()));
        return xlsService.generate("searchByTeacher.xls", data);
    }
}
