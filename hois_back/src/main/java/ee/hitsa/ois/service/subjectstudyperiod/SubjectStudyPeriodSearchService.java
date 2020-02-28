package ee.hitsa.ois.service.subjectstudyperiod;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.SubjectProgramStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.commandobject.subject.studyperiod.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectProgramResult;
import ee.hitsa.ois.web.dto.SubjectResult;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSearchDto;

@Transactional
@Service
public class SubjectStudyPeriodSearchService {
    
    private static final String FROM = "from subject_study_period ssp "
            + "inner join subject s on s.id = ssp.subject_id "
            + "inner join study_period sp on ssp.study_period_id = sp.id ";

    private static final String SELECT =
              "ssp.id as subjectStudyPeriodId, " 
            + "sp.name_et as spNameEt, sp.name_en as spNameEn, "
            + "s.id as subjectId, s.name_et as subNameEt, s.name_en as subNameEn, s.code, s.credits, "
            + "(select count(*) from declaration_subject ds where ds.subject_study_period_id = ssp.id) as declared_students";

    private static final String FILTER_BY_TEACHER_ID = "exists"
            + "(select sspt.id " 
            + "from subject_study_period_teacher sspt "
            + "where sspt.teacher_id = :teacherId "
            + "and sspt.subject_study_period_id = ssp.id)";

    private static final String FILTER_BY_DECLARED_STUDENT_ID = "exists("
            + "select * "
            + "from subject_study_period ssp3 "
            + "left join declaration_subject ds on ds.subject_study_period_id = ssp3.id "
            + "left join declaration d on d.id = ds.declaration_id "
            + "where d.student_id = :studentId "
            + "and ssp3.id = ssp.id)";
    
    @Autowired
    private EntityManager em;
    
    public List<SubjectResult> searchSubjects(HoisUserDetails user, SearchCommand lookup) {

        String from ="from subject s"
                + " join subject_study_period ssp on ssp.subject_id = s.id"
                + " left join curriculum_version_hmodule_subject cvhs on cvhs.subject_id = s.id"
                + " left join curriculum_version_hmodule cvh on cvh.id = cvhs.curriculum_version_hmodule_id"
                + " left join curriculum_version cv on cv.id = cvh.curriculum_version_id";

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalContains((Language.EN.equals(lookup.getLang()) ? "s.name_en" : "s.name_et") + " || ' (' || s.code || ')'", "name", lookup.getName());

        qb.sort(Language.EN.equals(lookup.getLang()) ? "s.name_en" : "s.name_et");

        String query = "distinct s.id, s.name_et, s.name_en, s.code, s.credits, s.assessment_code";
        List<?> data = qb.select(query, em).getResultList();

        return StreamUtil.toMappedList(r -> {
            String code = resultAsString(r, 3);
            BigDecimal credits = resultAsDecimal(r, 4);
            String nameEt = SubjectUtil.subjectName(code, resultAsString(r, 1), null);
            String nameEn = SubjectUtil.subjectName(code, resultAsString(r, 2), null);
            return new SubjectResult(resultAsLong(r, 0), nameEt, nameEn, code, credits, resultAsString(r, 5), null,
                    null, null);
        }, data);
    }

    public Page<SubjectStudyPeriodSearchDto> search(HoisUserDetails user, SubjectStudyPeriodSearchCommand criteria,
            Pageable pageable) {
        StringBuilder from = new StringBuilder(FROM);
        StringBuilder select = new StringBuilder(SELECT);
        if (user.isTeacher()) {
            from.append("join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id ");
            from.append("left join subject_program spr on spr.subject_study_period_teacher_id = sspt.id ");
            select.append(", spr.id, spr.status_code");
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).sort(pageable);

        if (criteria.getTeacherObject() != null) {
            qb.requiredCriteria(FILTER_BY_TEACHER_ID, "teacherId", criteria.getTeacherObject().getId());
        }
        qb.optionalCriteria("s.id = :subjectId", "subjectId", criteria.getSubjectObject());
        qb.optionalCriteria("sp.id in (:studyPeriods)", "studyPeriods", criteria.getStudyPeriods());
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria(FILTER_BY_DECLARED_STUDENT_ID, "studentId", criteria.getStudent());
        if (SubjectProgramStatus.AINEPROGRAMM_STAATUS_L.name().equals(criteria.getProgramStatus())) {
            qb.filter("spr.status_code is null");
        } else {
            qb.optionalCriteria("spr.status_code = :status", "status", criteria.getProgramStatus());
        }
        if(user.isTeacher()) {
            qb.requiredCriteria("sspt.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        }
        Page<Object[]> results = JpaQueryUtil.pagingResult(qb, select.toString(), em, pageable);
        List<Long> sspIds = StreamUtil.toMappedList(r -> resultAsLong(r, 0), results.getContent());
        Map<Long, List<SubjectProgramResult>> teachersAndPrograms = teachersAndProgramsForSubjectStudyPeriods(sspIds);
        Map<Long, Integer> subgroupCount = subgroupsQueryForSubjectStudyPeriods(sspIds);
        return results.map(r -> {
            SubjectStudyPeriodSearchDto dto = new SubjectStudyPeriodSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setStudyPeriod(new AutocompleteResult(null, resultAsString(r, 1), resultAsString(r, 2)));
            dto.setSubject(getSubject(r));
            dto.setTeachers(teachersAndPrograms.get(dto.getId()).stream().map(d -> d.getTeacherName()).collect(Collectors.toList()));
            dto.setPrograms(teachersAndPrograms.get(dto.getId()));
            dto.setCredits(resultAsDecimal(r, 7));
            dto.setStudentsNumber(resultAsLong(r, 8));
            if (user.isTeacher()) {
                dto.setSubjectProgramId(resultAsLong(r, 9));
                String status = resultAsString(r, 10);
                if (status == null) {
                    dto.setSubjectProgramStatus(SubjectProgramStatus.AINEPROGRAMM_STAATUS_L.name());
                } else {
                    dto.setSubjectProgramStatus(status);
                }
            }
            dto.setSubgroups(subgroupCount.get(dto.getId()));
            return dto;
        });
    }

    private static AutocompleteResult getSubject(Object r) {
        Long id = resultAsLong(r, 3);
        String code = resultAsString(r, 6);
        String nameEtCode = resultAsString(r, 4) + " (" + code + ")";
        String nameEnCode = resultAsString(r, 5) + " (" + code + ")";
        return new AutocompleteResult(id, nameEtCode,
                nameEnCode);
    }
    
    private Map<Long, List<SubjectProgramResult>> teachersAndProgramsForSubjectStudyPeriods(List<Long> subjectStudyPeriods) {
        if(subjectStudyPeriods.isEmpty()) {
            return Collections.emptyMap();
        }

        List<?> data = em.createNativeQuery("select ssp.id as sspId, t.id as tId, p.firstname || ' ' || p.lastname, sp.id as spId, coalesce( sp.status_code, 'AINEPROGRAMM_STAATUS_L' ) "
                + "from subject_study_period ssp "
                + "left join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id "
                + "left join teacher t on t.id = sspt.teacher_id "
                + "left join person p on p.id = t.person_id "
                + "left join subject_program sp on sp.subject_study_period_teacher_id = sspt.id "
                + "where ssp.id in (?1)")
            .setParameter(1, subjectStudyPeriods)
            .getResultList();
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> {
                    SubjectProgramResult dto = new SubjectProgramResult();
                    dto.setTeacherId(resultAsLong(r, 1));
                    dto.setTeacherName(resultAsString(r, 2));
                    dto.setId(resultAsLong(r, 3));
                    dto.setStatus(resultAsString(r, 4));
                    return dto;
                }, Collectors.toList())));
    }

    private Map<Long, Integer> subgroupsQueryForSubjectStudyPeriods(List<Long> subjectStudyPeriods) {
        if (subjectStudyPeriods.isEmpty()) {
            return Collections.emptyMap();
        }
        
        List<?> results = em.createNativeQuery("select ssp.id, count(sub.id) "
                + "from subject_study_period ssp "
                + "left join subject_study_period_subgroup sub on sub.subject_study_period_id = ssp.id "
                + "where ssp.id in ?1 "
                + "group by ssp.id")
            .setParameter(1, subjectStudyPeriods)
            .getResultList();
        
        return results.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> resultAsInteger(r, 1), (o, n) -> o));
    }
}
