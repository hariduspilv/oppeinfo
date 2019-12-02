package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsStringList;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.midtermresult.MidtermResultSearchDto;
import ee.hitsa.ois.web.dto.midtermresult.MidtermResultStudentDto;

@Transactional
@Service
public class MidtermResultService {
    
    @Autowired
    private EntityManager em;
    
    public Page<MidtermResultSearchDto> getDeclaredSubjectsByPeriod(Long studentId, StudyPeriod period, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from declaration d "
                + "join declaration_subject ds on ds.declaration_id = d.id "
                + "join midterm_task_student_result mtsr on mtsr.declaration_subject_id = ds.id "
                + "join subject_study_period ssp on ssp.id = ds.subject_study_period_id "
                + "join subject s on s.id = ssp.subject_id "
                + "join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id "
                + "join teacher t on t.id = sspt.teacher_id "
                + "join person pt on pt.id = t.person_id ").sort(pageable).groupBy("ds.id, s.id, ssp.id");
        
        qb.requiredCriteria("d.study_period_id = :studyPeriod", "studyPeriod", period.getId());
        qb.requiredCriteria("d.student_id = :studentId", "studentId", studentId);
        
        return JpaQueryUtil.pagingResult(qb, "ds.id, s.name_et, coalesce(s.name_en, s.name_et), s.code, string_agg(distinct pt.firstname || ' ' || pt.lastname, ';')", em, pageable).map(r -> {
            MidtermResultSearchDto dto = new MidtermResultSearchDto();
            dto.setId(resultAsLong(r, 0));
            String code = resultAsString(r, 3);
            dto.setSubject(new AutocompleteResult(null, resultAsString(r, 1) + "/" + code, resultAsString(r, 2) + "/" + code));
            dto.setTeachers(resultAsStringList(r, 4, ";"));
            return dto;
        });
    }
    
    public MidtermResultStudentDto getDeclarationSubjectMidtermResults(DeclarationSubject subject) {
        return MidtermResultStudentDto.of(subject);
    }

}
