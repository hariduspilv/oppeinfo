package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.web.commandobject.exam.ExamSearchForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.exam.ExamRegistrationDto;
import ee.hitsa.ois.web.dto.exam.ExamSearchDto;

@Transactional
@Service
public class ExamService {

    @Autowired
    private EntityManager em;

    /**
     * Exams search
     *
     * @param user
     * @param criteria
     * @param pageable
     * @return
     */
    public Page<ExamSearchDto> search(HoisUserDetails user, ExamSearchForm criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period_exam sspe " +
                "join timetable_event te on sspe.timetable_event_id = te.id " +
                "join subject_study_period ssp on sspe.subject_study_period_id = ssp.id "+
                "join subject s on ssp.subject_id = s.id "+
                "join classifier tp on sspe.type_code = tp.code").sort(pageable);

        qb.requiredCriteria("s.school_id = :school", "school", user.getSchoolId());
        qb.optionalCriteria("ssp.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        qb.optionalCriteria("te.start >= :from", "from", criteria.getFrom());
        qb.optionalCriteria("te.end <= :thru", "thru", criteria.getThru());

        if(!user.isSchoolAdmin()) {
            qb.requiredCriteria("ssp.id in (select sspt.subject_study_period_id from subject_study_period_teacher sspt where sspt.teacher_id = :teacherId)", "teacherId", user.getTeacherId());
        } else {
            // TODO school teachers by contains
            // qb.optionalContains("p.firstname", "p.lastname", )
        }

        Page<Object> result = JpaQueryUtil.pagingResult(qb, "sspe.id, s.code, s.name_et, s.name_en, te.start, sspe.type_code, (select count(*) from subject_study_period_exam_student sspes where sspes.subject_study_period_exam_id=sspe.id), sspe.subject_study_period_id, cast(te.start as time) as starttime", em, pageable);
        Map<Long, List<String>> teachers = teachersForExams(StreamUtil.toMappedSet(r -> resultAsLong(r, 7), result.getContent()));
        LocalDate now = LocalDate.now();
        return result.map(r -> {
            ExamSearchDto dto = new ExamSearchDto();
            dto.setId(resultAsLong(r, 0));
            String subjectCode = resultAsString(r, 1);
            dto.setSubject(new AutocompleteResult(null, SubjectUtil.subjectName(subjectCode, resultAsString(r, 2)), SubjectUtil.subjectName(subjectCode, resultAsString(r, 3))));
            dto.setTeacherNames(teachers.get(resultAsLong(r, 7)));
            dto.setStart(resultAsLocalDateTime(r, 4));
            dto.setType(resultAsString(r, 5));
            dto.setStudentCount(resultAsLong(r, 6));
            dto.setUserCanEdit(Boolean.valueOf(!now.isBefore(dto.getStart().toLocalDate())));
            return dto;
        });
    }

    /**
     * List of exams student is registered / can register, starting from today
     *
     * @param user
     * @param pageable
     * @return
     */
    public Page<ExamRegistrationDto> examsForRegistration(HoisUserDetails user, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from declaration d join declaration_subject ds on d.id = ds.declaration_id " +
                "join subject_study_period_exam sspe on ds.subject_study_period_id = sspe.subject_study_period_id " +
                "join subject_study_period ssp on sspe.subject_study_period_id = ssp.id "+
                "join subject s on ssp.subject_id = s.id "+
                "join timetable_event te on sspe.timetable_event_id = te.id " +
                "join classifier tp on sspe.type_code = tp.code " +
                "join classifier sm on s.assessment_code = sm.code " +
                "left join subject_study_period_exam_student sspes on ds.id = sspes.declaration_subject_id ").sort(pageable);
        qb.requiredCriteria("d.student_id = :studentId", "studentId", user.getStudentId());
        qb.requiredCriteria("d.status_code = :status", "status", DeclarationStatus.OPINGUKAVA_STAATUS_K);
        qb.requiredCriteria("te.start >= :start", "start", LocalDate.now());
        // TODO not already fully booked
        // TODO first/repeating

        Page<Object> result = JpaQueryUtil.pagingResult(qb, "sspe.id, s.code, s.name_et, s.name_en, s.assessment_code, te.start, sspe.type_code, sspes.id as student_id, sspe.subject_study_period_id", em, pageable);
        Map<Long, List<String>> teachers = teachersForExams(StreamUtil.toMappedSet(r -> resultAsLong(r, 9), result.getContent()));
        Map<Long, List<String>> rooms = roomsForExams(StreamUtil.toMappedSet(r -> resultAsLong(r, 1), result.getContent()));
        return result.map(r -> {
            ExamRegistrationDto dto = new ExamRegistrationDto();
            dto.setId(resultAsLong(r, 0));
            String subjectCode = resultAsString(r, 1);
            dto.setSubject(new AutocompleteResult(null, SubjectUtil.subjectName(subjectCode, resultAsString(r, 2)), SubjectUtil.subjectName(subjectCode, resultAsString(r, 3))));
            dto.setAssessment(resultAsString(r, 4));
            dto.setTeacherNames(teachers.get(resultAsLong(r, 7)));
            dto.setStart(resultAsLocalDateTime(r, 5));
            dto.setRooms(rooms.get(dto.getId()));
            dto.setType(resultAsString(r, 6));
            dto.setRegistered(Boolean.valueOf(resultAsLong(r, 8) != null));
            return dto;
        });
    }

    private Map<Long, List<String>> roomsForExams(Set<Long> examIds) {
        if(examIds.isEmpty()) {
            return Collections.emptyMap();
        }

        List<?> data = em.createNativeQuery("select sspe.id, r.code from subject_study_period_exam sspe " +
                "join timetable_event_time tet on sspe.timetable_event_id = tet.timetable_event_id " +
                "join timetable_event_room ter on tet.id = ter.timetable_event_time_id " +
                "join room r on ter.room_id = r.id where sspe.id in (?1) order by r.code")
            .setParameter(1, examIds)
            .getResultList();
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> resultAsString(r, 1), Collectors.toList())));
    }

    private Map<Long, List<String>> teachersForExams(Set<Long> subjectStudyPeriodIds) {
        if(subjectStudyPeriodIds.isEmpty()) {
            return Collections.emptyMap();
        }

        List<?> data = em.createNativeQuery("select sspt.subject_study_period_id, p.firstname, p.lastname from person p " +
                "join teacher t on t.person_id = p.id " +
                "join subject_study_period_teacher sspt on sspt.teacher_id = t.id where sspt.subject_study_period_id in (?1) " +
                "order by p.lastname, p.firstname")
            .setParameter(1, subjectStudyPeriodIds)
            .getResultList();
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)), Collectors.toList())));
    }
}
