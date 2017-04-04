package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSearchDto;

@Transactional
@Service
public class SubjectStudyPeriodService {
    
    @Autowired
    private EntityManager em;
    
    public Page<SubjectStudyPeriodSearchDto> search(Long schoolId, SubjectStudyPeriodSearchCommand criteria, Pageable pageable) {
        final String FROM = 
                "from subject_study_period ssp "
                + "inner join teacher t on t.id = ssp.teacher_id "
                + "inner join person p on p.id = t.person_id "
                + "inner join subject s on s.id = ssp.subject_id "
                + "inner join study_period sp on ssp.study_period_id = sp.id ";
        final String SELECT = "ssp.id as subjectStudyPeriodId, "
                + "sp.name_et as spNameEt, sp.name_en as spNameEn, "
                + "t.id as teachersId, p.firstname, p.lastname, s.id as subjectId, "
                + "s.name_et as subNameEt, s.name_en as subNameEn, s.code";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(FROM, pageable);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getTeachersFullname());
        qb.optionalContains(Arrays.asList("s.name_et", "s.name_en", "s.code", "s.name_et || '/' || s.code", "s.name_en || '/' || s.code"), "subjectNameAndCode", criteria.getSubjectNameAndCode());
        qb.optionalCriteria("sp.id in (:studyPeriods)", "studyPeriods", criteria.getStudyPeriods());
        qb.optionalCriteria("s.school_id = :schoolId", "schoolId", schoolId);

        Page<Object[]> results = JpaQueryUtil.pagingResult(qb, SELECT, em, pageable);
        return results.map(r -> {
            SubjectStudyPeriodSearchDto dto = new SubjectStudyPeriodSearchDto();
            dto.setId(resultAsLong(r, 0));
            AutocompleteResult studyPeriod = new AutocompleteResult(null, resultAsString(r, 1), resultAsString(r, 2));
            String teachersName = PersonUtil.fullname(resultAsString(r, 4), resultAsString(r, 5));
            AutocompleteResult teacher = new AutocompleteResult(resultAsLong(r, 3), teachersName, teachersName);
            String subjectCode = resultAsString(r, 9);
            String subjectNameEtCode = resultAsString(r, 7) + "/" + subjectCode;
            String subjectNameEnCode = resultAsString(r, 8) + "/" + subjectCode;
            AutocompleteResult subject = new AutocompleteResult(resultAsLong(r, 6), subjectNameEtCode, subjectNameEnCode);
            dto.setStudyPeriod(studyPeriod);
            dto.setTeacher(teacher);
            dto.setSubject(subject);
            //TODO: set number of declared students
            return dto;
          }
        );
    }
}
