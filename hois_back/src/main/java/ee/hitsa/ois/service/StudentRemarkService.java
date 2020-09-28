package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentRemark;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.message.StudentRemarkCreated;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.student.StudentRemarkForm;
import ee.hitsa.ois.web.commandobject.student.StudentRemarkSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.student.StudentRemarkDto;

@Transactional
@Service
public class StudentRemarkService {

    @Autowired
    private EntityManager em;
    @Autowired
    private AutomaticMessageService automaticMessageService;
    @Autowired
    private StudyYearService studyYearService;

    private static final String STUDENT_REMARKS_SELECT = "sr.id remark_id, null as student_entry_id, "
            + "s.id student_id, p.firstname, p.lastname, sg.code student_group, "
            + "sr.reason_code, sr.remark, sr.inserted_by remark_inserted_by, sr.remark_time, "
            + "null journal_id, null journal_name, s.type_code as studentType";

    private static final String STUDENT_REMARKS_FROM = "from student_remark sr "
            + "join student s on s.id = sr.student_id " 
            + "join student_group sg on sg.id = s.student_group_id "
            + "join person p on p.id = s.person_id";

    private static final String JOURNAL_REMARKS_SELECT = "null remark_id, jes.id student_entry_id, "
            + "s2.id student_id, p2.firstname, p2.lastname, sg2.code student_group, "
            + "null reason_code, jes.add_info remark, jes.remark_inserted_by, jes.remark_inserted remark_time, "
            + "j.id journal_id, j.name_et journal_name, s2.type_code as studentType";

    private static final String JOURNAL_REMARKS_FROM = "from journal_entry_student jes "
            + "join journal_student js on js.id = jes.journal_student_id " + "join journal j on j.id = js.journal_id "
            + "join student s2 on s2.id = js.student_id " + "join student_group sg2 on sg2.id = s2.student_group_id "
            + "join person p2 on p2.id = s2.person_id";

    private static final String REMARKS_UNINON_SELECT = "remark_id, student_entry_id, student_id, "
            + "firstname, lastname, student_group, reason_code, remark, remark_inserted_by, "
            + "remark_time, journal_id, journal_name, studentType";

    public Page<StudentRemarkDto> search(HoisUserDetails user, StudentRemarkSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = studentRemarksQueryBuilder(user, criteria).sort(pageable);
        Map<String, Object> parameters = studentRemarksParameters(user, criteria);
        return JpaQueryUtil.pagingResult(qb, REMARKS_UNINON_SELECT, parameters, em, pageable).map(r -> {
            return mapRemarkDto(r);
        });
    }

    private static JpaNativeQueryBuilder studentRemarksQueryBuilder(HoisUserDetails user,
            StudentRemarkSearchCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(STUDENT_REMARKS_FROM);
        if (user != null) {
            qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
            if (user.isTeacher()) {
                qb.requiredCriteria("sg.teacher_id = :teacherId", "teacherId", user.getTeacherId());
            }
        }
        qb.optionalCriteria("sg.id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());
        qb.optionalCriteria("sg.id in (:studentGroupIds)", "studentGroupIds", criteria.getStudentGroups());
        qb.optionalCriteria("s.id = :studentId", "studentId", criteria.getStudent());
        qb.optionalCriteria("s.id in (:studentIds)", "studentIds", criteria.getStudents());
        qb.optionalCriteria("sr.remark_time >= :studyYearStart", "studyYearStart", criteria.getStudyYearStart(),
                DateUtils::firstMomentOfDay);
        qb.optionalCriteria("sr.remark_time <= :studyYearEnd", "studyYearEnd", criteria.getStudyYearEnd(),
                DateUtils::lastMomentOfDay);
        qb.optionalCriteria("sr.remark_time >= :from", "from", criteria.getFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("sr.remark_time <= :thru", "thru", criteria.getThru(), DateUtils::lastMomentOfDay);
        qb.optionalCriteria("sr.reason_code in (:reasons)", "reasons", criteria.getReasons());
        String studentRemarksQuery = qb.querySql(STUDENT_REMARKS_SELECT, false);

        String journalRemarksQuery = "";
        if (Boolean.TRUE.equals(criteria.getShowJournalRemarks())) {
            qb = new JpaNativeQueryBuilder(JOURNAL_REMARKS_FROM);
            if (user != null) {
                qb.requiredCriteria("s2.school_id = :schoolId", "schoolId", user.getSchoolId());
                if (user.isTeacher()) {
                    qb.requiredCriteria("sg2.teacher_id = :teacherId", "teacherId", user.getTeacherId());
                }
            }
            qb.optionalCriteria("sg2.id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());
            qb.optionalCriteria("sg2.id in (:studentGroupIds)", "studentGroupIds", criteria.getStudentGroups());
            qb.optionalCriteria("s2.id = :studentId", "studentId", criteria.getStudent());
            qb.optionalCriteria("s2.id in (:studentIds)", "studentIds", criteria.getStudents());
            qb.optionalCriteria("jes.remark_inserted >= :studyYearStart", "studyYearStart",
                    criteria.getStudyYearStart(), DateUtils::firstMomentOfDay);
            qb.optionalCriteria("jes.remark_inserted <= :studyYearEnd", "studyYearEnd", criteria.getStudyYearEnd(),
                    DateUtils::lastMomentOfDay);
            qb.optionalCriteria("jes.remark_inserted >= :from", "from", criteria.getFrom(),
                    DateUtils::firstMomentOfDay);
            qb.optionalCriteria("jes.remark_inserted <= :thru", "thru", criteria.getThru(), DateUtils::lastMomentOfDay);
            qb.filter("jes.is_remark = true");
            journalRemarksQuery = qb.querySql(JOURNAL_REMARKS_SELECT, false);
        }

        qb = new JpaNativeQueryBuilder("from (" + studentRemarksQuery
                + (Boolean.TRUE.equals(criteria.getShowJournalRemarks()) ? " union all " + journalRemarksQuery : "")
                + ") as remarks");
        return qb;
    }

    private static Map<String, Object> studentRemarksParameters(HoisUserDetails user,
            StudentRemarkSearchCommand criteria) {
        HashMap<String, Object> parameters = new HashMap<>();
        if (user != null) {
            parameters.put("schoolId", user.getSchoolId());
            if (user.getTeacherId() != null) {
                parameters.put("teacherId", user.getTeacherId());
            }
        }
        if (criteria.getStudentGroup() != null) {
            parameters.put("studentGroupId", criteria.getStudentGroup());
        }
        if (criteria.getStudentGroups() != null) {
            parameters.put("studentGroupIds", criteria.getStudentGroups());
        }
        if (criteria.getStudent() != null) {
            parameters.put("studentId", criteria.getStudent());
        }
        if (criteria.getStudents() != null) {
            parameters.put("studentIds", criteria.getStudents());
        }
        if (criteria.getStudyYearStart() != null) {
            parameters.put("studyYearStart", DateUtils.firstMomentOfDay(criteria.getStudyYearStart()));
        }
        if (criteria.getStudyYearEnd() != null) {
            parameters.put("studyYearEnd", DateUtils.lastMomentOfDay(criteria.getStudyYearEnd()));
        }
        if (criteria.getFrom() != null) {
            parameters.put("from", DateUtils.firstMomentOfDay(criteria.getFrom()));
        }
        if (criteria.getThru() != null) {
            parameters.put("thru", DateUtils.lastMomentOfDay(criteria.getThru()));
        }
        if (criteria.getReasons() != null) {
            parameters.put("reasons", criteria.getReasons());
        }
        return parameters;
    }

    private static StudentRemarkDto mapRemarkDto(Object result) {
        StudentRemarkDto dto = new StudentRemarkDto();
        dto.setId(resultAsLong(result, 0));
        dto.setJournalStudentEntryId(resultAsLong(result, 1));
        String personFullname = PersonUtil.fullnameTypeSpecific(resultAsString(result, 3), resultAsString(result, 4), resultAsString(result, 12));
        dto.setStudent(new AutocompleteResult(resultAsLong(result, 2), personFullname, personFullname));
        dto.setStudentGroup(resultAsString(result, 5));
        dto.setReason(resultAsString(result, 6));
        dto.setRemark(resultAsString(result, 7));
        dto.setRemarkInsertedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(result, 8)));

        LocalDateTime remarkTime = resultAsLocalDateTime(result, 9);
        dto.setRemarkDate(remarkTime.toLocalDate());
        LocalTime time = remarkTime.toLocalTime();
        dto.setRemarkTime(LocalTime.of(time.getHour(), time.getMinute()));

        Long journalId = resultAsLong(result, 10);
        if (journalId != null) {
            String journalName = resultAsString(result, 11);
            dto.setJournal(new AutocompleteResult(journalId, journalName, journalName));
        }
        return dto;
    }

    public StudentRemark create(StudentRemarkForm form) {
        StudentRemark studentRemark = new StudentRemark();
        Student student = em.getReference(Student.class, form.getStudent().getId());
        studentRemark.setStudent(student);
        studentRemark = save(studentRemark, form);
        sendRemarkMessage(student);
        return studentRemark;
    }

    public StudentRemark save(StudentRemark studentRemark, StudentRemarkForm form) {
        studentRemark.setReason(em.getReference(Classifier.class, form.getReason()));
        studentRemark.setRemark(form.getRemark());
        LocalDateTime remarkTime = LocalDateTime.of(form.getRemarkDate(),
                form.getRemarkTime() != null ? form.getRemarkTime() : LocalTime.MIN);
        studentRemark.setRemarkTime(remarkTime);
        return EntityUtil.save(studentRemark, em);
    }

    private void sendRemarkMessage(Student student) {
        StudentRemarkCreated data = new StudentRemarkCreated(student);
        automaticMessageService.sendMessageToStudent(MessageType.TEATE_LIIK_OP_MARKUS, student, data);
    }

    public void delete(HoisUserDetails user, StudentRemark studentRemark) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(studentRemark, em);
    }

    public Page<StudentRemarkDto> studentRemarks(Long studentId, Pageable pageable) {
        StudentRemarkSearchCommand criteria = new StudentRemarkSearchCommand();
        criteria.setStudent(studentId);
        criteria.setShowJournalRemarks(Boolean.TRUE);

        JpaNativeQueryBuilder qb = studentRemarksQueryBuilder(null, criteria).sort(pageable);
        Map<String, Object> parameters = studentRemarksParameters(null, criteria);
        return JpaQueryUtil.pagingResult(qb, REMARKS_UNINON_SELECT, parameters, em, pageable).map(r -> {
            return mapRemarkDto(r);
        });
    }

    public List<StudentRemarkDto> studentRecentRemarks(Long schoolId, Long studentId) {
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);
        if (studyYear == null) {
            return null;
        }
        LocalDate today = LocalDate.now();
        StudentRemarkSearchCommand criteria = new StudentRemarkSearchCommand();
        criteria.setStudent(studentId);
        criteria.setStudyYearStart(studyYear.getStartDate());
        criteria.setStudyYearEnd(studyYear.getEndDate());
        criteria.setFrom(today.minusDays(30));
        criteria.setThru(today);
        criteria.setShowJournalRemarks(Boolean.TRUE);

        Map<Long, List<StudentRemarkDto>> remarksByStudents = remarksByStudents(criteria);
        return remarksByStudents.containsKey(studentId) ? remarksByStudents.get(studentId) : new ArrayList<>();
    }

    public Map<Long, List<StudentRemarkDto>> remarksByStudents(StudentRemarkSearchCommand criteria) {
        JpaNativeQueryBuilder qb = studentRemarksQueryBuilder(null, criteria);
        Map<String, Object> parameters = studentRemarksParameters(null, criteria);

        List<?> data = qb.select(REMARKS_UNINON_SELECT, em, parameters).getResultList();
        return StreamUtil.nullSafeList(data).stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 2),
                Collectors.mapping(r -> mapRemarkDto(r), Collectors.toList())));
    }

    public Boolean studentHasRemarksPastSevenDays(Student student) {
        Long studentId = EntityUtil.getId(student);
        LocalDate today = LocalDate.now();
        StudentRemarkSearchCommand criteria = new StudentRemarkSearchCommand();
        criteria.setStudent(studentId);
        criteria.setFrom(today.minusDays(7));
        criteria.setThru(today);
        criteria.setShowJournalRemarks(Boolean.TRUE);

        Map<Long, List<StudentRemarkDto>> remarksByStudents = remarksByStudents(criteria);
        List<StudentRemarkDto> studentRemarks = remarksByStudents.containsKey(studentId)
                ? remarksByStudents.get(studentId)
                : new ArrayList<>();
        return Boolean.valueOf(!studentRemarks.isEmpty());
    }

    public List<StudentRemarkDto> studentGroupTodaysRemarks(HoisUserDetails user) {
        LocalDate today = LocalDate.now();
        StudentRemarkSearchCommand criteria = new StudentRemarkSearchCommand();
        criteria.setFrom(today);
        criteria.setThru(today);
        criteria.setShowJournalRemarks(Boolean.TRUE);

        JpaNativeQueryBuilder qb = studentRemarksQueryBuilder(user, criteria);
        Map<String, Object> parameters = studentRemarksParameters(user, criteria);
        List<?> data = qb.select(REMARKS_UNINON_SELECT, em, parameters).getResultList();
        return StreamUtil.toMappedList(r -> mapRemarkDto(r), data);
    }

    private static boolean canSearch(HoisUserDetails user) {
        return (user.isTeacher() || user.isSchoolAdmin())
                && UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_MARKUS);
    }

    private boolean canCreate(HoisUserDetails user, Long studentId) {
        return ((user.isTeacher() && isStudentGroupTeacher(user, studentId))
                || UserUtil.isSchoolAdmin(user, em.getReference(Student.class, studentId).getSchool()))
                && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_MARKUS);
    }

    private boolean canView(HoisUserDetails user, Student student) {
        return ((user.isTeacher() && isStudentGroupTeacher(user, student.getId()))
                || UserUtil.isSchoolAdmin(user, student.getSchool()))
                && UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_MARKUS);
    }

    private boolean canEdit(HoisUserDetails user, Student student) {
        return ((user.isTeacher() && isStudentGroupTeacher(user, student.getId()))
                || UserUtil.isSchoolAdmin(user, student.getSchool()))
                && UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_MARKUS);
    }

    private boolean isStudentGroupTeacher(HoisUserDetails user, Long studentId) {
        List<?> data = em.createNativeQuery("select s.id from student s "
                + "join student_group sg on sg.id = s.student_group_id "
                + "where s.id = ?1 and sg.teacher_id = ?2")
                .setParameter(1, studentId)
                .setParameter(2, user.getTeacherId())
                .setMaxResults(1).getResultList();
        return !data.isEmpty();
    }

    public void assertCanSearch(HoisUserDetails user) {
        if (!canSearch(user)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public void assertCanCreate(HoisUserDetails user, Long studentId) {
        if (!canCreate(user, studentId)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public void assertCanView(HoisUserDetails user, Student student) {
        if (!canView(user, student)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }

    public void assertCanEdit(HoisUserDetails user, Student student) {
        if (!canEdit(user, student)) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
    }
}
