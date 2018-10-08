package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.studentgroupyeartransfer.StudentGroupYearTransfer;
import ee.hitsa.ois.domain.studentgroupyeartransfer.StudentGroupYearTransferLog;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.GroupMismatchReason;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.commandobject.studentgroupyeartransfer.CalculateCommand;
import ee.hitsa.ois.web.commandobject.studentgroupyeartransfer.TransferCommand;
import ee.hitsa.ois.web.dto.StudyYearSearchDto;
import ee.hitsa.ois.web.dto.studentgroupyeartransfer.CalculatedStudentGroupDto;
import ee.hitsa.ois.web.dto.studentgroupyeartransfer.StudentDto;
import ee.hitsa.ois.web.dto.studentgroupyeartransfer.StudentGroupDto;

@Transactional
@Service
public class StudentGroupYearTransferService {

    private static final String SELECT_AKADK = "select ds2.start_date as end_date from directive d2"
            + " join directive_student ds2 on ds2.directive_id = d2.id"
            + " join application a on a.id = ds2.application_id"
            + " where d2.status_code = ?1 and d2.type_code = ?3 and ds2.canceled = false"
            + " and ds2.student_id = ds.student_id and a.directive_id = ds.directive_id";
    
    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierService classifierService;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private StudentService studentService;

    public List<StudyYearSearchDto> studyYears(Long schoolId) {
        List<?> data = em.createNativeQuery("select c.code, c.name_et, c.name_en, sy.id, sy.start_date, sy.end_date, 0 as count"
                + " from study_year sy"
                + " join classifier c on sy.year_code = c.code"
                + " where sy.school_id = ?1 and (sy.end_date > now()"
                + " or exists (select id from student_group_year_transfer where study_year_id = sy.id))"
                + " order by c.code")
                .setParameter(1, schoolId)
                .getResultList();
        return StreamUtil.toMappedList(r -> new StudyYearSearchDto((Object[])r), data);
    }

    public List<StudentGroupDto> search(HoisUserDetails user, SearchCommand criteria) {
        Long studyYearId = criteria.getId();
        StudyYear studyYear = studyYear(user, studyYearId);
        Stream<StudentGroupDto> logs = searchLogs(criteria).stream();
        return (LocalDate.now().isBefore(studyYear.getEndDate()) ? 
                Stream.concat(searchGroups(user.getSchoolId(), criteria, studyYear).stream(), logs) : logs)
                .sorted(Comparator.comparing(StudentGroupDto::getTransfered, Comparator.nullsFirst(Comparator.naturalOrder()))
                        .thenComparing(Comparator.comparing(StudentGroupDto::getOldCode)))
                .collect(Collectors.toList());
    }

    private List<StudentGroupDto> searchGroups(Long schoolId, SearchCommand criteria, StudyYear studyYear) {
        List<?> result = em.createNativeQuery("select sg.id as group_id, sg.code as group_code, sg.course, sg.valid_thru"
                + ", cv.code, c.name_" + (Language.EN.equals(criteria.getLang()) ? "en" : "et")
                + ", (select count(*) from student where student_group_id = sg.id) as students"
                + " from student_group sg"
                + " join curriculum c on c.id = sg.curriculum_id"
                + " left join curriculum_version cv on cv.id = sg.curriculum_version_id"
                + " where sg.school_id = ?1 and (coalesce(sg.valid_thru, cast('infinity' as date)) > ?2"
                    + " or exists (select id from student where student_group_id = sg.id and status_code in ?3))"
                + " and sg.id not in (select student_group_id from student_group_year_transfer where study_year_id = ?4)")
                .setParameter(1, schoolId)
                .setParameter(2, JpaQueryUtil.parameterAsTimestamp(studyYear.getStartDate()))
                .setParameter(3, StudentStatus.STUDENT_STATUS_ACTIVE)
                .setParameter(4, criteria.getId())
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            StudentGroupDto dto = new StudentGroupDto();
            dto.setId(resultAsLong(r, 0));
            String code = resultAsString(r, 1);
            dto.setOldCode(code);
            dto.setNewCode(code);
            Short course = resultAsShort(r, 2);
            dto.setOldCourse(course);
            dto.setNewCourse(getNewCourse(course));
            dto.setValidThru(resultAsLocalDate(r, 3));
            String curriculumVersionCode = resultAsString(r, 4);
            dto.setCurriculumName(curriculumVersionCode != null ? curriculumVersionCode : resultAsString(r, 5));
            dto.setRelatedStudents(resultAsLong(r, 6));
            return dto;
        }, result);
    }

    private List<StudentGroupDto> searchLogs(SearchCommand criteria) {
        List<?> result = em.createNativeQuery("select sgyt.id, sgyt.student_group_id, sgyt.is_transfered"
                + ", sgyt.old_code, sgyt.old_course, sgyt.new_code, sgyt.new_course, sg.valid_thru"
                + ", cv.code, c.name_" + (Language.EN.equals(criteria.getLang()) ? "en" : "et")
                + ", (select count(*) from student_group_year_transfer_log where student_group_year_transfer_id = sgyt.id) as students"
                + ", (select count(*) from student_group_year_transfer_log where student_group_year_transfer_id = sgyt.id"
                + " and is_matching = true) as matching_students"
                + ", (select count(*) from student_group_year_transfer_log where student_group_year_transfer_id = sgyt.id"
                + " and is_matching = false) as mismatching_students"
                + " from student_group_year_transfer sgyt"
                + " join student_group sg on sg.id = sgyt.student_group_id"
                + " join curriculum c on c.id = sgyt.curriculum_id"
                + " left join curriculum_version cv on cv.id = sgyt.curriculum_version_id"
                + " where sgyt.study_year_id = ?1")
                .setParameter(1, criteria.getId())
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            StudentGroupDto dto = new StudentGroupDto();
            dto.setLogId(resultAsLong(r, 0));
            dto.setId(resultAsLong(r, 1));
            dto.setTransfered(resultAsBoolean(r, 2));
            dto.setOldCode(resultAsString(r, 3));
            dto.setOldCourse(resultAsShort(r, 4));
            dto.setNewCode(resultAsString(r, 5));
            dto.setNewCourse(resultAsShort(r, 6));
            dto.setValidThru(resultAsLocalDate(r, 7));
            String curriculumVersionCode = resultAsString(r, 8);
            dto.setCurriculumName(curriculumVersionCode != null ? curriculumVersionCode : resultAsString(r, 9));
            dto.setRelatedStudents(resultAsLong(r, 10));
            dto.setSuitableStudents(resultAsLong(r, 11));
            dto.setUnsuitableStudents(resultAsLong(r, 12));
            return dto;
        }, result);
    }
    
    public Map<Long, CalculatedStudentGroupDto> calculate(HoisUserDetails user, CalculateCommand command) {
        Long studyYearId = command.getId();
        StudyYear studyYear = studyYear(user, studyYearId);
        Map<Long, CalculatedStudentGroupDto> resultMap = new HashMap<>();
        List<StudentGroupYearTransfer> groupLogs = em.createQuery("select sgyt from StudentGroupYearTransfer sgyt"
                + " where sgyt.studyYear = ?1 and sgyt.studentGroup.id in ?2", StudentGroupYearTransfer.class)
                .setParameter(1, studyYear)
                .setParameter(2, command.getStudentGroupIds())
                .getResultList();
        Map<Long, StudentGroupYearTransfer> groupMap = StreamUtil.toMap(
                sgyt -> EntityUtil.getId(sgyt.getStudentGroup()), groupLogs);
        EntityUtil.setUsername(user.getUsername(), em);
        for (Long groupId : command.getStudentGroupIds()) {
            StudentGroupYearTransfer groupLog = groupMap.get(groupId);
            if (groupLog == null) {
                groupLog = new StudentGroupYearTransfer();
                groupLog.setStudyYear(studyYear);
                StudentGroup studentGroup = em.getReference(StudentGroup.class, groupId);
                groupLog.setStudentGroup(studentGroup);
                groupLog.setOldCode(studentGroup.getCode());
                groupLog.setCurriculum(studentGroup.getCurriculum());
                groupLog.setCurriculumVersion(studentGroup.getCurriculumVersion());
                groupLog.setOldCourse(studentGroup.getCourse());
                if (studentGroup.getCourse() != null) {
                    groupLog.setNewCourse(getNewCourse(studentGroup.getCourse()));
                }
                groupLog.setIsTransfered(Boolean.FALSE);
            }
            groupLog.setNewCode(command.getNewGroupCodes().get(groupId));
            groupLog = EntityUtil.save(groupLog, em);
            groupMap.put(groupId, groupLog);
            resultMap.put(groupId, new CalculatedStudentGroupDto(EntityUtil.getId(groupLog)));
        }
        if (!groupLogs.isEmpty()) {
            em.createNativeQuery("delete from student_group_year_transfer_log where id in ("
                    + "select sgytl.id"
                    + " from student_group_year_transfer_log sgytl"
                    + " join student_group_year_transfer sgyt on sgyt.id = sgytl.student_group_year_transfer_id"
                    + " join student s on s.id = sgytl.student_id"
                    + " where sgyt.id in ?1 and (s.student_group_id is null or sgyt.student_group_id != s.student_group_id))")
            .setParameter(1, StreamUtil.toMappedList(EntityUtil::getId, groupLogs))
            .executeUpdate();
        }
        List<StudentGroupYearTransferLog> studentLogs = groupLogs.isEmpty() ? Collections.emptyList() : 
            em.createQuery("select sgytl from StudentGroupYearTransferLog sgytl"
                + " where sgytl.studentGroupYearTransfer in ?1", StudentGroupYearTransferLog.class)
                .setParameter(1, groupLogs)
                .getResultList();
        Map<Long, StudentGroupYearTransferLog> studentMap = StreamUtil.toMap(
                sgytl -> EntityUtil.getId(sgytl.getStudent()), studentLogs);
        ClassifierCache classifiers = new ClassifierCache(classifierService);
        Map<Long, GroupMismatchReason> mismatchMap = getMismatches(studyYear, command);
        List<Student> students = em.createQuery("select s from Student s"
                + " where s.studentGroup.id in ?1", Student.class)
                .setParameter(1, command.getStudentGroupIds())
                .getResultList();
        for (Student student : students) {
            Long studentId = EntityUtil.getId(student);
            Long groupId = EntityUtil.getId(student.getStudentGroup());
            String statusCode = EntityUtil.getNullableCode(student.getStatus());
            StudentGroupYearTransferLog studentLog = studentMap.get(studentId);
            if (studentLog == null) {
                studentLog = new StudentGroupYearTransferLog();
                studentLog.setStudentGroupYearTransfer(groupMap.get(groupId));
                studentLog.setStudent(student);
            }
            GroupMismatchReason mismatch = mismatchMap.get(studentId);
            CalculatedStudentGroupDto groupResult = resultMap.get(groupId);
            if (mismatch == null) {
                studentLog.setIsMatching(Boolean.TRUE);
                groupResult.setSuitableStudents(Long.valueOf(groupResult.getSuitableStudents().longValue() + 1));
            } else {
                studentLog.setIsMatching(Boolean.FALSE);
                studentLog.setMismatch(classifiers.getByCode(mismatch.name(), MainClassCode.OPPERYHM_EISOBI));
                groupResult.setUnsuitableStudents(Long.valueOf(groupResult.getUnsuitableStudents().longValue() + 1));
                if (statusCode != null && StudentStatus.STUDENT_STATUS_ACTIVE.contains(statusCode)) {
                    StudentDto studentDto = new StudentDto();
                    studentDto.setId(studentId);
                    studentDto.setName(PersonUtil.fullname(student.getPerson()));
                    studentDto.setMismatchCode(mismatch.name());
                    groupResult.getMismatchingStudents().add(studentDto);
                }
            }
            studentLog = EntityUtil.save(studentLog, em);
            studentMap.put(studentId, studentLog);
        }
        return resultMap;
    }

    private Map<Long, GroupMismatchReason> getMismatches(StudyYear studyYear, CalculateCommand command) {
        Map<Long, GroupMismatchReason> mismatchMap = new HashMap<>();
        List<?> result = em.createNativeQuery("select s.id"
                + " from student s"
                + " join student_group sg on sg.id = s.student_group_id"
                + " join curriculum_version cv on cv.id = s.curriculum_version_id"
                + " where s.student_group_id in ?1 and (cv.curriculum_id != sg.curriculum_id"
                + " or (sg.curriculum_version_id is not null and s.curriculum_version_id != sg.curriculum_version_id))")
                .setParameter(1, command.getStudentGroupIds())
                .getResultList();
        addMismatches(mismatchMap, result, GroupMismatchReason.OPPERYHM_EISOBI_O);
        if (Boolean.TRUE.equals(command.getAcademicLeave())) {
            result = em.createNativeQuery("select ds.student_id"
                    + " from directive d"
                    + " join directive_student ds on ds.directive_id = d.id"
                    + " where d.status_code = ?1 and d.type_code = ?2"
                    + " and ?4 between coalesce(ds.start_date, cast('-infinity' as date))"
                        + " and coalesce((select min(end_date) from (select ds.end_date"
                        + " union " + SELECT_AKADK + ") end_dates), cast('infinity' as date))"
                    + " and ds.student_id in (select id from student where student_group_id in ?5)")
                    .setParameter(1, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
                    .setParameter(2, DirectiveType.KASKKIRI_AKAD.name())
                    .setParameter(3, DirectiveType.KASKKIRI_AKADK.name())
                    .setParameter(4, JpaQueryUtil.parameterAsTimestamp(studyYear.getStartDate()))
                    .setParameter(5, command.getStudentGroupIds())
                    .getResultList();
            addMismatches(mismatchMap, result, GroupMismatchReason.OPPERYHM_EISOBI_A);
        }
        if (command.getAcademicLeaveDays() != null) {
            StudyYear previousStudyYear = studyYearService.getPreviousStudyYear(studyYear);
            if (previousStudyYear == null) {
                throw new ValidationFailedException("studentGroupYearTransfer.error.noPreviousStudyYear");
            }
            result = em.createNativeQuery("select ds.student_id"
                    + " from directive d"
                    + " join directive_student ds on ds.directive_id = d.id"
                    + " where d.status_code = ?1 and d.type_code = ?2"
                    + " and coalesce(ds.start_date, cast('-infinity' as date)) < ?5 and coalesce(ds.end_date, cast('infinity' as date)) > ?4"
                    + " and ((select min(end_date) from (select ds.end_date union select ?5 as end_date"
                            + " union " + SELECT_AKADK + ") end_dates)"
                        + " - (select max(start_date) from (select ds.start_date union select ?4 as start_date) start_dates) + 1) >= ?6"
                    + " and ds.student_id in (select id from student where student_group_id in ?7)")
                    .setParameter(1, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
                    .setParameter(2, DirectiveType.KASKKIRI_AKAD.name())
                    .setParameter(3, DirectiveType.KASKKIRI_AKADK.name())
                    .setParameter(4, JpaQueryUtil.parameterAsTimestamp(previousStudyYear.getStartDate()))
                    .setParameter(5, JpaQueryUtil.parameterAsTimestamp(previousStudyYear.getEndDate()))
                    .setParameter(6, command.getAcademicLeaveDays())
                    .setParameter(7, command.getStudentGroupIds())
                    .getResultList();
            addMismatches(mismatchMap, result, GroupMismatchReason.OPPERYHM_EISOBI_A);
        }
        if (Boolean.TRUE.equals(command.getAbroadStudies())) {
            result = em.createNativeQuery("select ds.student_id"
                    + " from directive d"
                    + " join directive_student ds on ds.directive_id = d.id"
                    + " where d.status_code = ?1 and d.type_code = ?2"
                    + " and ?3 between coalesce(ds.start_date, cast('-infinity' as date)) and coalesce(ds.end_date, cast('infinity' as date))"
                    + " and ds.student_id in (select id from student where student_group_id in ?4)")
                    .setParameter(1, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
                    .setParameter(2, DirectiveType.KASKKIRI_VALIS.name())
                    .setParameter(3, JpaQueryUtil.parameterAsTimestamp(studyYear.getStartDate()))
                    .setParameter(4, command.getStudentGroupIds())
                    .getResultList();
            addMismatches(mismatchMap, result, GroupMismatchReason.OPPERYHM_EISOBI_V);
        }
        if (command.getAbroadStudiesDays() != null) {
            StudyYear previousStudyYear = studyYearService.getPreviousStudyYear(studyYear);
            if (previousStudyYear == null) {
                throw new ValidationFailedException("studentGroupYearTransfer.error.noPreviousStudyYear");
            }
            result = em.createNativeQuery("select ds.student_id"
                    + " from directive d"
                    + " join directive_student ds on ds.directive_id = d.id"
                    + " where d.status_code = ?1 and d.type_code = ?2"
                    + " and coalesce(ds.start_date, cast('-infinity' as date)) < ?4 and coalesce(ds.end_date, cast('infinity' as date)) > ?3"
                    + " and ((select min(end_date) from (select ds.end_date union select ?4 as end_date) end_dates)"
                        + " - (select max(start_date) from (select ds.start_date union select ?3 as start_date) start_dates) + 1) >= ?5"
                    + " and ds.student_id in (select id from student where student_group_id in ?6)")
                    .setParameter(1, DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
                    .setParameter(2, DirectiveType.KASKKIRI_VALIS.name())
                    .setParameter(3, JpaQueryUtil.parameterAsTimestamp(previousStudyYear.getStartDate()))
                    .setParameter(4, JpaQueryUtil.parameterAsTimestamp(previousStudyYear.getEndDate()))
                    .setParameter(5, command.getAbroadStudiesDays())
                    .setParameter(6, command.getStudentGroupIds())
                    .getResultList();
            addMismatches(mismatchMap, result, GroupMismatchReason.OPPERYHM_EISOBI_V);
        }
        result = em.createNativeQuery("select id"
                + " from student"
                + " where student_group_id in ?1 and status_code not in ?2")
                .setParameter(1, command.getStudentGroupIds())
                .setParameter(2, StudentStatus.STUDENT_STATUS_ACTIVE)
                .getResultList();
        addMismatches(mismatchMap, result, GroupMismatchReason.OPPERYHM_EISOBI_E);
        return mismatchMap;
    }

    private static void addMismatches(Map<Long, GroupMismatchReason> map, List<?> result, GroupMismatchReason reason) {
        for (Object r : result) {
            map.put(resultAsLong(r, 0), reason);
        }
    }

    private StudentGroup findNewGroup(Student student) {
        Short course = student.getStudentGroup().getCourse();
        List<StudentGroup> groups = em.createQuery("select sg from StudentGroup sg"
                + " where sg.course = ?1 and sg.curriculumVersion = ?2", StudentGroup.class)
                .setParameter(1, course)
                .setParameter(2, student.getCurriculumVersion())
                .getResultList();
        if (groups.size() == 1) {
            return groups.get(0);
        }
        if (groups.size() > 1) {
            return null;
        }
        groups = em.createQuery("select sg from StudentGroup sg"
                + " where sg.course = ?1 and sg.curriculum = ?2", StudentGroup.class)
                .setParameter(1, course)
                .setParameter(2, student.getCurriculumVersion().getCurriculum())
                .getResultList();
        if (groups.size() == 1) {
            return groups.get(0);
        }
        return null;
    }
    
    private StudentGroup findNewGroup(Student student, Map<Long, Long> givenGroupIds) {
        Long newGroupId = givenGroupIds.get(EntityUtil.getId(student));
        if (newGroupId != null) {
            return em.getReference(StudentGroup.class, newGroupId);
        }
        return findNewGroup(student);
    }
    
    public void transfer(HoisUserDetails user, TransferCommand command) {
        List<StudentGroupYearTransfer> groupLogs = em.createQuery("select sgyt from StudentGroupYearTransfer sgyt"
                + " where sgyt.id in ?1", StudentGroupYearTransfer.class)
                .setParameter(1, command.getLogIds())
                .getResultList();
        for (StudentGroupYearTransfer groupLog : groupLogs) {
            assertCanAccess(user, groupLog.getStudyYear());
        }
        for (StudentGroupYearTransfer groupLog : groupLogs) {
            List<StudentGroupYearTransferLog> studentLogs = em.createQuery("select sgytl from StudentGroupYearTransferLog sgytl"
                    + " where sgytl.studentGroupYearTransfer = ?1", StudentGroupYearTransferLog.class)
                    .setParameter(1, groupLog)
                    .getResultList();
            for (StudentGroupYearTransferLog studentLog : studentLogs) {
                if (Boolean.FALSE.equals(studentLog.getIsMatching())) {
                    GroupMismatchReason reason = GroupMismatchReason.valueOf(EntityUtil.getCode(studentLog.getMismatch()));
                    Student student = studentLog.getStudent();
                    if (reason == GroupMismatchReason.OPPERYHM_EISOBI_E) {
                        student.setStudentGroup(null);
                    } else {
                        StudentGroup newGroup = findNewGroup(student, command.getNewStudentGroups());
                        AssertionFailedException.throwIf(newGroup == null, "Cannot find new group for student");
                        student.setStudentGroup(newGroup);
                    }
                    studentService.saveWithHistory(student);
                }
            }
            StudentGroup group = groupLog.getStudentGroup();
            String newCode = command.getNewGroupCodes().get(EntityUtil.getId(group));
            groupLog.setNewCode(newCode);
            group.setCode(newCode);
            group.setCourse(groupLog.getNewCourse());
            EntityUtil.save(group, em);
            groupLog.setIsTransfered(Boolean.TRUE);
            EntityUtil.save(groupLog, em);
        }
    }

    public List<String> matching(HoisUserDetails user, Long logId) {
        transferLog(user, logId);
        List<?> result = em.createNativeQuery("select p.firstname, p.lastname"
                + " from student_group_year_transfer_log sgytl"
                + " join student s on s.id = sgytl.student_id"
                + " join person p on p.id = s.person_id"
                + " where sgytl.student_group_year_transfer_id = ?1 and sgytl.is_matching = true"
                + " order by p.firstname, p.lastname")
                .setParameter(1, logId)
                .getResultList();
        return StreamUtil.toMappedList(r -> PersonUtil.fullname(resultAsString(r, 0), resultAsString(r, 1)), result);
    }
    
    public List<StudentDto> mismatching(HoisUserDetails user, Long logId) {
        transferLog(user, logId);
        List<?> result = em.createNativeQuery("select p.firstname, p.lastname, sgytl.mismatch_code"
                + " from student_group_year_transfer_log sgytl"
                + " join student s on s.id = sgytl.student_id"
                + " join person p on p.id = s.person_id"
                + " where sgytl.student_group_year_transfer_id = ?1 and sgytl.is_matching = false"
                + " order by p.firstname, p.lastname")
                .setParameter(1, logId)
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            StudentDto dto = new StudentDto();
            dto.setName(PersonUtil.fullname(resultAsString(r, 0), resultAsString(r, 1)));
            dto.setMismatchCode(resultAsString(r, 2));
            return dto;
        }, result);
    }

    private static Short getNewCourse(Short course) {
        return Short.valueOf((short) (course.intValue() + 1));
    }

    private StudyYear studyYear(HoisUserDetails user, Long studyYearId) {
        StudyYear studyYear = em.getReference(StudyYear.class, studyYearId);
        assertCanAccess(user, studyYear);
        return studyYear;
    }

    private StudentGroupYearTransfer transferLog(HoisUserDetails user, Long logId) {
        StudentGroupYearTransfer log = em.getReference(StudentGroupYearTransfer.class, logId);
        assertCanAccess(user, log.getStudyYear());
        return log;
    }

    private static void assertCanAccess(HoisUserDetails user, StudyYear studyYear) {
        UserUtil.assertSameSchool(user, studyYear.getSchool());
    }

}
