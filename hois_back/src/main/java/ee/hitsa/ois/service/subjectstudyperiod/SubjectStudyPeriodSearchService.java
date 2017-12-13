package ee.hitsa.ois.service.subjectstudyperiod;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
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
            + "s.id as subjectId, s.name_et as subNameEt, s.name_en as subNameEn, s.code, "
            + "(select string_agg(p2.firstname || ' ' || p2.lastname, ';') " + "from subject_study_period ssp2 "
            + "left join subject_study_period_teacher sspt2 on sspt2.subject_study_period_id = ssp2.id "
            + "left join teacher t2 on t2.id = sspt2.teacher_id " + "left join person p2 on p2.id = t2.person_id "
            + "where ssp2.id = ssp.id ) as names, "
            + "(select count(*) from declaration_subject ds where ds.subject_study_period_id = ssp.id) as declared_students";

    private static final String FILTER_BY_TEACHER_NAME = "exists"
            + "(select sspt.id " 
            + "from subject_study_period_teacher sspt "
            + "inner join teacher t on t.id = sspt.teacher_id " + "inner join person p on p.id = t.person_id "
            + "where upper(p.firstname || ' ' || p.lastname) like :teachersName "
            + "and sspt.subject_study_period_id = ssp.id)";

    private static final String FILTER_BY_DECLARED_STUDENT_ID = "exists("
            + "select * "
            + "from subject_study_period ssp3 "
            + "left join declaration_subject ds on ds.subject_study_period_id = ssp3.id "
            + "left join declaration d on d.id = ds.declaration_id "
            + "where d.student_id = :studentId "
            + "and ssp3.id = ssp.id)";
    
    private static final String FILTER_BY_TEACHER_ID = "exists "
            + "(select sspt.id " 
            + "from subject_study_period_teacher sspt "
            + "where "
            + "sspt.subject_study_period_id = ssp.id "
            + "and sspt.teacher_id = :teacherId ) ";

    @Autowired
    private EntityManager em;

    public Page<SubjectStudyPeriodSearchDto> search(HoisUserDetails user, SubjectStudyPeriodSearchCommand criteria,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(FROM).sort(pageable);

        if (StringUtils.hasText(criteria.getTeachersFullname())) {
            qb.requiredCriteria(FILTER_BY_TEACHER_NAME, "teachersName",
                    JpaQueryUtil.toContains(criteria.getTeachersFullname()));
        }
        qb.optionalContains(Arrays.asList("s.name_et", "s.name_en", "s.code", "s.name_et || '/' || s.code",
                "s.name_en || '/' || s.code"), "subjectNameAndCode", criteria.getSubjectNameAndCode());
        qb.optionalCriteria("sp.id in (:studyPeriods)", "studyPeriods", criteria.getStudyPeriods());
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria(FILTER_BY_DECLARED_STUDENT_ID, "studentId", criteria.getStudent());
        if(user.isTeacher()) {
            qb.requiredCriteria(FILTER_BY_TEACHER_ID, "teacherId", user.getTeacherId());
        }
        Page<Object[]> results = JpaQueryUtil.pagingResult(qb, SELECT, em, pageable);
        return results.map(this::resultToDto);
    }

    private SubjectStudyPeriodSearchDto resultToDto(Object r) {
        SubjectStudyPeriodSearchDto dto = new SubjectStudyPeriodSearchDto();
        dto.setId(resultAsLong(r, 0));
        dto.setStudyPeriod(new AutocompleteResult(null, resultAsString(r, 1), resultAsString(r, 2)));
        dto.setSubject(getSubject(r));
        dto.setTeachers(getTeachers(r));
        dto.setStudentsNumber(resultAsLong(r, 8));
        return dto;
    }

    private static AutocompleteResult getSubject(Object r) {
        Long id = resultAsLong(r, 3);
        String code = resultAsString(r, 6);
        String nameEtCode = resultAsString(r, 4) + "/" + code;
        String nameEnCode = resultAsString(r, 5) + "/" + code;
        return new AutocompleteResult(id, nameEtCode,
                nameEnCode);
    }

    private static List<String> getTeachers(Object r) {
        String teachersNames = resultAsString(r, 7);
        if (teachersNames != null) {
            return Arrays.asList(teachersNames.split(";"));
        }
        return null;
    }
}
