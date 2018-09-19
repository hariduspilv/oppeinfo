package ee.hitsa.ois.service.subjectstudyperiod;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumDepartment;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodStudentGroup;
import ee.hitsa.ois.repository.StudentGroupRepository;
import ee.hitsa.ois.service.XlsService;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectStudyPeriodUtil;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodStudentGroupSearchDto;

@Transactional
@Service
public class SubjectStudyPeriodStudentGroupSearchService {
    
    private static final String TIMETABLE_SELECT = " t.id ";
    private static final String TIMETABLE_FROM = " from timetable t "
            + "join timetable_object tobj on tobj.timetable_id = t.id "
            + "join timetable_object_student_group sg on sg.timetable_object_id = tobj.id ";

    @Autowired
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private XlsService xlsService;

    public Page<SubjectStudyPeriodStudentGroupSearchDto> searchByStudentGroup(Long schoolId, SubjectStudyPeriodSearchCommand criteria,
            Pageable pageable) {

        return studentGroupRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));
            if (criteria.getStudentGroup() != null) {
                filters.add(cb.equal(root.get("id"), criteria.getStudentGroup()));
            }
            if (criteria.getCurriculum() != null) {
                filters.add(cb.equal(root.get("curriculum").get("id"), criteria.getCurriculum()));
            }
            Long department = criteria.getDepartment();
            if (department != null) {
                Subquery<Long> departmentQuery = query.subquery(Long.class);
                Root<CurriculumDepartment> curriculumDepartmentRoot = departmentQuery.from(CurriculumDepartment.class);
                departmentQuery = departmentQuery.select(curriculumDepartmentRoot.get("curriculum").get("id")).where(
                        curriculumDepartmentRoot.get("schoolDepartment").get("id").in(Arrays.asList(department)));
                filters.add(root.get("curriculum").get("id").in(departmentQuery));
            }
            filters.add(cb.equal(root.get("curriculum").get("higher"), Boolean.TRUE));

            /*
             * Only valid student groups should be shown
             */
            filters.add(cb.or(cb.lessThanOrEqualTo(root.get("validFrom"), LocalDate.now()),
                    cb.isNull(root.get("validFrom"))));
            filters.add(cb.or(cb.greaterThanOrEqualTo(root.get("validThru"), LocalDate.now()),
                    cb.isNull(root.get("validThru"))));

            /*
             * Search should show only those studentGroups, which have any
             * connections with subject_study_period_student_group table with
             * specific studyPeriod
             */
            Subquery<Long> sspStudentGroupQuery = query.subquery(Long.class);
            Root<SubjectStudyPeriodStudentGroup> sspStudentGroupRoot = sspStudentGroupQuery
                    .from(SubjectStudyPeriodStudentGroup.class);
            sspStudentGroupQuery = sspStudentGroupQuery.select(sspStudentGroupRoot.get("studentGroup").get("id"))
                    .where(cb.equal(sspStudentGroupRoot.get("subjectStudyPeriod").get("studyPeriod").get("id"),
                            criteria.getStudyPeriod()));
            filters.add(root.get("id").in(sspStudentGroupQuery));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(sg -> getSearchDto(sg, criteria.getStudyPeriod()));
    }

    private SubjectStudyPeriodStudentGroupSearchDto getSearchDto(StudentGroup sg, Long studyPeriod) {
        SubjectStudyPeriodStudentGroupSearchDto dto = new SubjectStudyPeriodStudentGroupSearchDto();
        dto.setId(EntityUtil.getId(sg));
        dto.setCode(sg.getCode());
        dto.setCurriculum(curriculum(sg.getCurriculum()));
        dto.setHours(getHours(sg, studyPeriod));
        dto.setStudyPeriod(studyPeriod);
        /*
         * This check prevents the exception in 
         * TimetableEventService.getGeneralTimetableCurriculum
         * When user proceeds to watch timetable 
         */
        if(sg.getCurriculumVersion() != null) {
            dto.setTimetable(getTimetable(dto.getId(), studyPeriod));
        }
        return dto;
    }

    private static Long getHours(StudentGroup sg, Long studyPeriod) {
        List<SubjectStudyPeriod> ssps = SubjectStudyPeriodUtil.filterSsps
                (StreamUtil.toMappedList(sspSg -> sspSg.getSubjectStudyPeriod(), sg.getSubjectStudyPeriods()), studyPeriod);
        return SubjectStudyPeriodUtil.getHours(ssps);
    }

    private Long getTimetable(Long studentGroup, Long studyPeriod) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(TIMETABLE_FROM);
        qb.requiredCriteria("t.study_period_id = :studyPeriod", "studyPeriod", studyPeriod);
        qb.requiredCriteria("sg.student_group_id = :studentGroup", "studentGroup", studentGroup);
        qb.filter(" t.start_date <= current_date ");
        qb.filter(" t.end_date >= current_date ");
        List<?> result = qb.select(TIMETABLE_SELECT, em).setMaxResults(1).getResultList();
        if(result.isEmpty()) {
            return null;
        }
        return resultAsLong(result.get(0), 0);
    }

    private static AutocompleteResult curriculum(Curriculum curriculum) {
        String nameEt = curriculum.getCode() + " - " + curriculum.getNameEt();
        String nameEn = curriculum.getCode() + " - " + curriculum.getNameEn();
        return new AutocompleteResult(EntityUtil.getId(curriculum), nameEt, nameEn);
    }
    
    public byte[] searchByStudentGroupAsExcel(Long schoolId, SubjectStudyPeriodSearchCommand criteria) {
        List<SubjectStudyPeriodStudentGroupSearchDto> studentGroups = searchByStudentGroup(schoolId, criteria,
                new PageRequest(0, Integer.MAX_VALUE, Direction.ASC, "code")).getContent();
        
        Map<String, Object> data = new HashMap<>();
        data.put("studyPeriod", em.getReference(StudyPeriod.class, criteria.getStudyPeriod()));
        data.put("studentGroups", studentGroups);
        return xlsService.generate("searchByStudentGroup.xls", data);
    }
}
