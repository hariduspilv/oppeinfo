package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.PracticeJournalSearchCommand;
import ee.hitsa.ois.web.dto.PracticeJournalSearchDto;

@Transactional
@Service
public class PracticeJournalService {

    @Autowired
    private EntityManager em;

    private static final String SEARCH_FROM = "from practice_journal pj "
            + "inner join student student on pj.student_id = student.id "
            + "inner join person student_person on student.person_id = student_person.id "
            + "left join contract contract on contract.id = pj.contract_id "
            + "left join enterprise enterprise on contract.enterprise_id = enterprise.id "
            + "inner join teacher teacher on pj.teacher_id = teacher.id "
            + "inner join person teacher_person on teacher.person_id = teacher_person.id "
            + "inner join curriculum_version_omodule cvo on pj.curriculum_version_omodule_id = cvo.id "
            + "left join curriculum_version_omodule_theme cvot on cvot.curriculum_version_omodule_id = cvo.id ";
    private static final String SEARCH_SELECT = "pj.id, pj.start_date, pj.end_date, pj.practice_place ";

    public Page<PracticeJournalSearchDto> search(HoisUserDetails user, PracticeJournalSearchCommand command,
            Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(SEARCH_FROM).sort(pageable);
        qb.requiredCriteria("pj.school_id = :schoolId", "schoolId", user.getSchoolId());

        qb.optionalCriteria("jt.study_year_id = :studyYearId", "studyYearId", command.getStudyYear());
        qb.optionalCriteria("student.student_group_id = :studentGroupId", "studentGroupId", command.getStudentGroup());
        qb.optionalContains(Arrays.asList("student_person.firstname", "student_person.lastname",
                "student_person.firstname || ' ' || student_person.lastname"), "name", command.getStudentName());
        qb.optionalCriteria("cvo.curriculum_version_id = :curriculumVersionId", "curriculumVersionId", command.getCurriculumVersion());
        qb.optionalCriteria("jt.teacher_id = :teacherId", "teacherId", command.getTeacher());

        return JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable).map(r -> {
            PracticeJournalSearchDto dto = new PracticeJournalSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setStartDate(resultAsLocalDate(r, 1));
            dto.setEndDate(resultAsLocalDate(r, 2));
            dto.setPracticePlace(resultAsString(r, 3));
            //TODO rest of fields
            return dto;
        });
    }

}
