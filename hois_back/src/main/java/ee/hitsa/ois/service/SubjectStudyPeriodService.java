package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.subject.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.repository.StudyPeriodRepository;
import ee.hitsa.ois.repository.SubjectRepository;
import ee.hitsa.ois.repository.SubjectStudyPeriodRepository;
import ee.hitsa.ois.repository.TeacherRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodForm;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.commandobject.SubjectStudyPeriodTeacherForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSearchDto;

@Transactional
@Service
public class SubjectStudyPeriodService {
    
    @Autowired
    private EntityManager em;
    @Autowired
    private SubjectStudyPeriodRepository subjectStudyPeriodRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudyPeriodRepository studyPeriodRepository;
    
    public Page<SubjectStudyPeriodSearchDto> search(Long schoolId, SubjectStudyPeriodSearchCommand criteria, Pageable pageable) {
        final String FROM = 
                "from subject_study_period ssp "
                + "inner join subject s on s.id = ssp.subject_id "
                + "inner join study_period sp on ssp.study_period_id = sp.id ";
        final String SELECT = "ssp.id as subjectStudyPeriodId, "
                + "sp.name_et as spNameEt, sp.name_en as spNameEn, "
                + "s.id as subjectId, s.name_et as subNameEt, s.name_en as subNameEn, s.code, "
                + "(select string_agg(p2.firstname || ' ' || p2.lastname, ';') "
                + "from subject_study_period ssp2 "
                + "left join subject_study_period_teacher sspt2 on sspt2.subject_study_period_id = ssp2.id "
                + "left join teacher t2 on t2.id = sspt2.teacher_id "
                + "left join person p2 on p2.id = t2.person_id "
                + "where ssp2.id = ssp.id ) as names";

        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(FROM, pageable);
        if(StringUtils.hasText(criteria.getTeachersFullname())) {
            qb.requiredCriteria("exists"
                    + "(select sspt.id "
                    + "from subject_study_period_teacher sspt "
                    + "inner join teacher t on t.id = sspt.teacher_id "
                    + "inner join person p on p.id = t.person_id "
                    + "where upper(p.firstname || ' ' || p.lastname) like :teachersName "
                    + "and sspt.subject_study_period_id = ssp.id)", "teachersName", JpaQueryUtil.toContains(criteria.getTeachersFullname()));
        }
        qb.optionalContains(Arrays.asList("s.name_et", "s.name_en", "s.code", "s.name_et || '/' || s.code", "s.name_en || '/' || s.code"), "subjectNameAndCode", criteria.getSubjectNameAndCode());
        qb.optionalCriteria("sp.id in (:studyPeriods)", "studyPeriods", criteria.getStudyPeriods());
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);

        Page<Object[]> results = JpaQueryUtil.pagingResult(qb, SELECT, em, pageable);
        return results.map(r -> {
            SubjectStudyPeriodSearchDto dto = new SubjectStudyPeriodSearchDto();
            dto.setId(resultAsLong(r, 0));
            AutocompleteResult studyPeriod = new AutocompleteResult(null, resultAsString(r, 1), resultAsString(r, 2));
            String subjectCode = resultAsString(r, 6);
            String subjectNameEtCode = resultAsString(r, 4) + "/" + subjectCode;
            String subjectNameEnCode = resultAsString(r, 5) + "/" + subjectCode;
            AutocompleteResult subject = new AutocompleteResult(resultAsLong(r, 3), subjectNameEtCode, subjectNameEnCode);
            dto.setStudyPeriod(studyPeriod);
            String s = resultAsString(r, 7);
            if(s != null) {
              dto.setTeachers(Arrays.asList(s.split(";")));
            }
            dto.setSubject(subject);
            //TODO: set number of declared students
            return dto;
        });
    }

    public SubjectStudyPeriod updateSubjectStudyPeriodTeachers(SubjectStudyPeriod subjectStudyPeriod, 
            SubjectStudyPeriodForm form) {

        Map<Long, SubjectStudyPeriodTeacher> oldTeachersMap = StreamUtil.toMap
                (t -> EntityUtil.getId(t.getTeacher()), subjectStudyPeriod.getTeachers());
        List<SubjectStudyPeriodTeacher> newTeachers = new ArrayList<>();
        for(SubjectStudyPeriodTeacherForm t : form.getTeachers()) {
            SubjectStudyPeriodTeacher teacher = oldTeachersMap.get(t.getTeacherId());
            if(teacher == null) {
                teacher = new SubjectStudyPeriodTeacher();
                teacher.setSubjectStudyPeriod(subjectStudyPeriod);
                teacher.setTeacher(teacherRepository.findOne(t.getTeacherId()));
            }
            teacher.setIsSignatory(t.getIsSignatory());
            newTeachers.add(teacher);
        }
        subjectStudyPeriod.setTeachers(newTeachers);
        return subjectStudyPeriodRepository.save(subjectStudyPeriod);
    }

    public SubjectStudyPeriod create(SubjectStudyPeriodForm form) {
        SubjectStudyPeriod subjectStudyPeriod = new SubjectStudyPeriod();
        subjectStudyPeriod.setSubject(subjectRepository.findOne(form.getSubject()));
        subjectStudyPeriod.setStudyPeriod(studyPeriodRepository.findOne(form.getStudyPeriod()));
        return updateSubjectStudyPeriodTeachers(subjectStudyPeriod, form);
    }

    public void delete(SubjectStudyPeriod subjectStudyPeriod) {
        EntityUtil.deleteEntity(subjectStudyPeriodRepository, subjectStudyPeriod);        
    }
}