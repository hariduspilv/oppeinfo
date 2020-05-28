package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodExam;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodExamStudent;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.TimetableEvent;
import ee.hitsa.ois.domain.timetable.TimetableEventRoom;
import ee.hitsa.ois.domain.timetable.TimetableEventTeacher;
import ee.hitsa.ois.domain.timetable.TimetableEventTime;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.enums.ExamType;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.enums.ProtocolType;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.TimetableEventRepeat;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import ee.hitsa.ois.web.commandobject.exam.ExamForm;
import ee.hitsa.ois.web.commandobject.exam.ExamSearchForm;
import ee.hitsa.ois.web.commandobject.exam.StudentExamSearchForm;
import ee.hitsa.ois.web.commandobject.exam.StudentSearchCriteria;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.exam.ExamDto;
import ee.hitsa.ois.web.dto.exam.ExamSearchDto;
import ee.hitsa.ois.web.dto.exam.ExamStudentRegistrationDto;
import ee.hitsa.ois.web.dto.exam.SubjectStudyPeriodDto;

@Transactional
@Service
public class ExamService {

    private static final String FIRST_RESULT = "select 1 from protocol_hdata ph " +
            "join protocol_student ps on ps.protocol_id = ph.protocol_id and ps.student_id = d.student_id " +
            "where ph.subject_study_period_id = ds.subject_study_period_id " +
            "and ph.type_code = '"+ProtocolType.PROTOKOLLI_LIIK_P.name()+"'";
    
    private static final String REGISTERED_EXAM_SUBJECTS = " select sspe2.subject_study_period_id from subject_study_period_exam sspe2 " +
            "join subject_study_period_exam_student sspes2 on sspe2.id = sspes2.subject_study_period_exam_id " +
            "join declaration_subject ds2 on sspes2.declaration_subject_id = ds2.id " +
            "join declaration d2 on ds2.declaration_id = d2.id " +
            "where d2.student_id = d.student_id and sspe2.type_code = '" +ExamType.SOORITUS_P.name() + "'";

    @Autowired
    private EntityManager em;

    /**
     * read exam
     *
     * @param user
     * @param examId
     * @return
     */
    public ExamDto get(HoisUserDetails user, Long examId) {
        return getInternal(user, exam(user, examId));
    }

    /**
     * Create new exam
     *
     * @param user
     * @param form
     * @return
     */
    public Long create(HoisUserDetails user, ExamForm form) {
        SubjectStudyPeriodExam exam = new SubjectStudyPeriodExam();
        exam.setSubjectStudyPeriod(EntityUtil.getOptionalOne(SubjectStudyPeriod.class, form.getSubjectStudyPeriod(), em));
        TimetableEvent te = new TimetableEvent();
        te.setSchool(em.getReference(School.class, user.getSchoolId()));
        te.setConsiderBreak(Boolean.FALSE);
        te.setRepeatCode(em.getReference(Classifier.class, TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_EI.name()));
        exam.setTimetableEvent(te);
        exam = saveInternal(user, exam, form);
        return exam.getId();
    }

    /**
     * Update exam
     *
     * @param user
     * @param examId
     * @param form
     * @return
     */
    public ExamDto save(HoisUserDetails user, Long examId, ExamForm form) {
        SubjectStudyPeriodExam exam = exam(user, examId);
        EntityUtil.assertEntityVersion(exam, form.getVersion());

        exam = saveInternal(user, exam, form);
        em.flush();

        return getInternal(user, exam);
    }

    /**
     * Delete exam
     *
     * @param user
     * @param examId
     * @param version
     */
    public void delete(HoisUserDetails user, Long examId, Long version) {
        SubjectStudyPeriodExam exam = exam(user, examId);
        EntityUtil.assertEntityVersion(exam, version);

        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(exam, em);
    }

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
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("exists (select cv.curriculum_id from curriculum_version_hmodule_subject cvhs "
                    + "join curriculum_version_hmodule cvh on cvh.id = cvhs.curriculum_version_hmodule_id "
                    + "join curriculum_version cv on cv.id = cvh.curriculum_version_id "
                    + "join user_curriculum uc on uc.curriculum_id = cv.curriculum_id "
                    + "where cvhs.subject_id = s.id and uc.user_id = :userId)", "userId", user.getUserId());
        }

        qb.optionalCriteria("ssp.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        qb.optionalCriteria("s.id = :subject", "subject", criteria.getSubject());
        qb.optionalCriteria("te.start >= :from", "from", criteria.getFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("te.end <= :thru", "thru", criteria.getThru(), DateUtils::lastMomentOfDay);

        if(user.isTeacher()) {
            criteria.setTeacher(user.getTeacherId());
        }
        qb.optionalCriteria("ssp.id in (select sspt.subject_study_period_id from subject_study_period_teacher sspt where sspt.teacher_id = :teacherId)", "teacherId", criteria.getTeacher());

        Page<Object> result = JpaQueryUtil.pagingResult(qb, "sspe.id, s.code, s.name_et, s.name_en, te.start, sspe.type_code, (select count(*) from subject_study_period_exam_student sspes where sspes.subject_study_period_exam_id=sspe.id), sspe.subject_study_period_id", em, pageable);
        Map<Long, List<String>> teachers = teachersForSubjectStudyPeriods(StreamUtil.toMappedSet(r -> resultAsLong(r, 7), result.getContent()));
        return result.map(r -> {
            ExamSearchDto dto = new ExamSearchDto();
            dto.setId(resultAsLong(r, 0));
            String subjectCode = resultAsString(r, 1);
            dto.setSubject(new AutocompleteResult(null, SubjectUtil.subjectName(subjectCode, resultAsString(r, 2)), SubjectUtil.subjectName(subjectCode, resultAsString(r, 3))));
            dto.setTeacherNames(teachers.get(resultAsLong(r, 7)));
            dto.setStart(resultAsLocalDateTime(r, 4));
            dto.setType(resultAsString(r, 5));
            dto.setStudentCount(resultAsLong(r, 6));
            dto.setUserCanEdit(Boolean.valueOf(examIsEditable(dto.getStart())));
            return dto;
        });
    }

    /**
     * List of exams student is registered / can register, starting from today
     *
     * @param user
     * @param criteria
     * @param pageable
     * @return
     */
    public Page<ExamStudentRegistrationDto> examsForRegistration(HoisUserDetails user, StudentExamSearchForm criteria, Pageable pageable) {
        LocalDateTime now = LocalDateTime.now();
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from declaration d " +
                "join declaration_subject ds on d.id = ds.declaration_id " +
                "join subject_study_period_exam sspe on ds.subject_study_period_id = sspe.subject_study_period_id " +
                "join subject_study_period ssp on sspe.subject_study_period_id = ssp.id "+
                "join subject s on ssp.subject_id = s.id "+
                "join timetable_event te on sspe.timetable_event_id = te.id " +
                "join classifier tp on sspe.type_code = tp.code " +
                "join classifier sm on s.assessment_code = sm.code " +
                "left join subject_study_period_exam_student sspes on ds.id = sspes.declaration_subject_id and sspes.subject_study_period_exam_id = sspe.id")
                .sort(pageable);
        qb.requiredCriteria("d.student_id = :studentId", "studentId", user.getStudentId());
        qb.requiredCriteria("d.status_code = :status", "status", DeclarationStatus.OPINGUKAVA_STAATUS_K);
        qb.requiredCriteria("te.start >= :start", "start", DateUtils.firstMomentOfDay(now));
        qb.requiredCriteria("ssp.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        // registration deadline not exceed
        qb.requiredCriteria("(sspes.id is not null or sspe.deadline is null or sspe.deadline >= :now)", "now", now);
        // grade logic
        qb.filter("(sspes.id is not null or (" +
                "(sspe.type_code = '"+ExamType.SOORITUS_P.name()+"' and sspe.subject_study_period_id not in (" + REGISTERED_EXAM_SUBJECTS + ")) or " +
                "(sspe.type_code = '"+ExamType.SOORITUS_K.name()+"' and exists(" + FIRST_RESULT + " and ps.grade is not null)" +
                " and not exists(select 1 from subject_study_period_exam_student s2 " +
                    "join subject_study_period_exam e2 on s2.subject_study_period_exam_id = e2.id " +
                    "left join protocol_student ps on ps.subject_study_period_exam_student_id = s2.id " +
                    "where s2.declaration_subject_id = ds.id and ps.grade is null and e2.type_code = sspe.type_code and e2.id != sspe.id " +
                "))))");

        Page<Object> result = JpaQueryUtil.pagingResult(qb, "sspe.id, s.code, s.name_et, s.name_en, s.assessment_code, te.start, sspe.type_code, " +
                "sspes.id as student_id, sspe.subject_study_period_id, sspe.deadline, sspe.add_info, sspe.places, (select count(*) " +
                "from subject_study_period_exam_student sspes2 where sspes2.subject_study_period_exam_id = sspe.id), ds.id as ds_id, " +
                "(select phdata.protocol_id from protocol_hdata phdata " + 
                "join protocol_student pstu on phdata.protocol_id = pstu.protocol_id " + 
                "join subject_study_period_exam ssexam on phdata.subject_study_period_id = ssexam.subject_study_period_id " + 
                "join subject_study_period_exam_student ssexams on ssexam.id = ssexams.subject_study_period_exam_id " + 
                "where ssexam.id = sspe.id and pstu.subject_study_period_exam_student_id = ssexams.id) as protocol_id", em, pageable);
        Map<Long, List<String>> teachers = teachersForSubjectStudyPeriods(StreamUtil.toMappedSet(r -> resultAsLong(r, 8), result.getContent()));
        Map<Long, List<AutocompleteResult>> rooms = roomsForExams(StreamUtil.toMappedSet(r -> resultAsLong(r, 0), result.getContent()));
        Map<Long, List<Grade>> grades = gradesForDeclarationSubjects(StreamUtil.toMappedSet(r -> resultAsLong(r, 13), result.getContent()));
        return result.map(r -> {
            ExamStudentRegistrationDto dto = new ExamStudentRegistrationDto();
            dto.setId(resultAsLong(r, 0));
            String subjectCode = resultAsString(r, 1);
            dto.setSubject(new AutocompleteResult(null, SubjectUtil.subjectName(subjectCode, resultAsString(r, 2)), SubjectUtil.subjectName(subjectCode, resultAsString(r, 3))));
            dto.setAssessment(resultAsString(r, 4));
            dto.setTeacherNames(teachers.get(resultAsLong(r, 8)));
            dto.setStart(resultAsLocalDateTime(r, 5));
            dto.setRooms(StreamUtil.toMappedList(it -> it.getNameEt(), rooms.get(dto.getId())));
            dto.setType(resultAsString(r, 6));
            dto.setDeadline(resultAsLocalDateTime(r, 9));
            dto.setAddInfo(resultAsString(r, 10));
            Long places = resultAsLong(r, 11);
            Long booked = resultAsLong(r, 12);
            dto.setFreePlaces(places != null ? Long.valueOf(Math.max(places.longValue() - booked.longValue(), 0)) : null);
            dto.setProtocol(resultAsLong(r, 14));
            boolean registered = resultAsLong(r, 7) != null;
            dto.setRegistered(Boolean.valueOf(registered));
            dto.setCanChange(Boolean.valueOf(isBeforeDeadline(dto.getDeadline(), dto.getStart()) && (!registered || !hasGrade(grades.get(resultAsLong(r, 13)), dto.getId(), dto.getType()))));
            return dto;
        });
    }

    /**
     * get registered students for exam
     *
     * @param user
     * @param exam
     */
    private List<ExamDto.ExamStudent> registrations(HoisUserDetails user, SubjectStudyPeriodExam exam) {
        Long examId = EntityUtil.getId(exam);

        List<?> data = em.createNativeQuery("select ds.id, s.id as s_id, p.firstname, p.lastname, cv.code, c.name_et, c.name_en, " +
                "sg.code as sg_code, sspes.inserted, sspes.id as es_id, s.type_code as studentType " +
                "from subject_study_period_exam_student sspes " +
                "join declaration_subject ds on sspes.declaration_subject_id = ds.id " +
                "join declaration d on ds.declaration_id = d.id " +
                "join student s on d.student_id = s.id join person p on s.person_id = p.id " +
                "left join curriculum_version cv on s.curriculum_version_id = cv.id " +
                "left join curriculum c on cv.curriculum_id = c.id " +
                "left join student_group sg on s.student_group_id = sg.id " +
                "where sspes.subject_study_period_exam_id = ?1 order by p.lastname, p.firstname")
            .setParameter(1, examId)
            .getResultList();

        Map<Long, List<Grade>> grades = gradesForDeclarationSubjects(StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data));
        return studentsForExam(exam, data, grades);
    }

    /**
     * Get candidate students for given exam
     *
     * @param user
     * @param examId
     * @param criteria
     * @return
     */
    public List<ExamDto.ExamStudent> studentsForRegistration(HoisUserDetails user, Long examId, StudentSearchCriteria criteria) {
        SubjectStudyPeriodExam exam = exam(user, examId);

        // all students who have declared subject
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from declaration_subject ds " +
                "join declaration d on ds.declaration_id = d.id " +
                "join student s on d.student_id = s.id join person p on s.person_id = p.id " +
                "left join curriculum_version cv on s.curriculum_version_id = cv.id " +
                "left join curriculum c on cv.curriculum_id = c.id " +
                "left join student_group sg on s.student_group_id = sg.id").sort("p.lastname", "p.firstname");
        qb.requiredCriteria("ds.subject_study_period_id = :subjectStudyPeriod", "subjectStudyPeriod", EntityUtil.getId(exam.getSubjectStudyPeriod()));
        qb.requiredCriteria("d.status_code = :declarationStatus", "declarationStatus", DeclarationStatus.OPINGUKAVA_STAATUS_K);
        qb.requiredCriteria("s.school_id = :school", "school", user.getSchoolId());
        qb.requiredCriteria("s.status_code in (:studentStatus)", "studentStatus", StudentStatus.STUDENT_STATUS_ACTIVE);
        if(ExamType.SOORITUS_P.name().equals(criteria.getType())) {
            // first exam
            // students who have already registered to a subject exam
            qb.filter("ds.subject_study_period_id not in (" + REGISTERED_EXAM_SUBJECTS + ")");
        } else if(ExamType.SOORITUS_K.name().equals(criteria.getType())) {
            // repeating exam
            // students who have result for first exam
            qb.filter("exists(" + FIRST_RESULT + " and ps.grade is not null)");
            // and no registration for repeating exam without result
            String sql = "select 1 from subject_study_period_exam_student s2 " +
                    "join subject_study_period_exam e2 on s2.subject_study_period_exam_id = e2.id " +
                    "left join protocol_student ps on ps.subject_study_period_exam_student_id = s2.id " +
                    "where s2.declaration_subject_id = ds.id and ps.grade is null and e2.type_code = '"+ExamType.SOORITUS_K.name()+"' and e2.id != :exam";
            qb.requiredCriteria("not exists(" + sql +")", "exam", examId);
        } else {
            throw new AssertionFailedException("Unknown exam type");
        }

        List<?> data = qb.select("ds.id, s.id as s_id, p.firstname, p.lastname, cv.code, c.name_et, c.name_en, "
                + "sg.code as sg_code, cast(null as date) as inserted, cast(null as int) as es_id, s.type_code", em).getResultList();
        return studentsForExam(exam, data, Collections.emptyMap());
    }

    /**
     * Register given user (student) as participating for exam
     *
     * @param user
     * @param examId
     * @throws EntityNotFoundException if exam was not found
     * @throws ValidationFailedException if student cannot register
     */
    public void register(HoisUserDetails user, Long examId) {
        SubjectStudyPeriodExam exam = em.getReference(SubjectStudyPeriodExam.class, examId);
        DeclarationSubject ds = findDeclarationSubject(exam, user.getStudentId());
        if(ds == null) {
            throw new ValidationFailedException("exam.message.subjectnotdeclared");
        }

        // check that student can register (deadline and places)
        checkRegistrationDeadline(exam);
        List<SubjectStudyPeriodExamStudent> students = exam.getStudents();
        if(exam.getPlaces() != null && students.size() >= exam.getPlaces().longValue()) {
            throw new ValidationFailedException("exam.message.places");
        }
        Long dsId = EntityUtil.getId(ds);
        if(students.stream().anyMatch(r -> dsId.equals(EntityUtil.getId(r.getDeclarationSubject())))) {
            throw new ValidationFailedException("exam.message.studentalreadyregistered");
        }
        
        List<Long> declarationSubjects = Collections.singletonList(dsId);
        List<Grade> grades = gradesForDeclarationSubjects(declarationSubjects).get(dsId);
        List<ExamRegistration> registrations = registrationsForDeclarationSubjects(declarationSubjects).get(dsId);
        checkPreviousRegistrations(exam, registrations, grades);

        SubjectStudyPeriodExamStudent s = new SubjectStudyPeriodExamStudent();
        s.setDeclarationSubject(ds);
        s.setSubjectStudyPeriodExam(exam);
        students.add(s);
    }

    /**
     * Remove user (student) from exam
     *
     * @param user
     * @param examId
     * @throws EntityNotFoundException if exam or student declaration for given subject-teacher pair is not found
     * @throws ValidationFailedException if student was not registered for exam
     */
    public void unregister(HoisUserDetails user, Long examId) {
        SubjectStudyPeriodExam exam = em.getReference(SubjectStudyPeriodExam.class, examId);
        DeclarationSubject ds = findDeclarationSubject(exam, user.getStudentId());
        if(ds == null) {
            throw new ValidationFailedException("exam.message.subjectnotdeclared");
        }

        // check that student can unregister (deadline)
        checkRegistrationDeadline(exam);

        // check that there is no grade for student
        Long dsId = EntityUtil.getId(ds);
        List<Grade> grades = gradesForDeclarationSubjects(Collections.singletonList(dsId)).get(dsId);
        if(hasGrade(grades, examId, EntityUtil.getCode(exam.getType()))) {
            throw new ValidationFailedException("exam.message.studenthasgrade");
        }

        // check that student exam is not connected to a protocol already
        List<ExamRegistration> registrations = registrationsForDeclarationSubjects(Collections.singletonList(dsId)).get(dsId);
        if (hasConnectedProtocol(registrations, examId)) {
            throw new ValidationFailedException("exam.message.studentconnectedprotocol");
        }

        if(!exam.getStudents().removeIf(r -> dsId.equals(EntityUtil.getId(r.getDeclarationSubject())))) {
            // student was not registered for exam
            throw new ValidationFailedException("exam.message.studentnotregistered");
        }
    }

    public List<SubjectStudyPeriodDto> subjectStudyPeriods(HoisUserDetails user, Long studyPeriodId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period ssp " +
                "join subject s on ssp.subject_id = s.id " +
                "join study_period sp on ssp.study_period_id = sp.id");
        qb.requiredCriteria("ssp.study_period_id = :studyPeriod", "studyPeriod", studyPeriodId);
        qb.requiredCriteria("s.school_id = :school", "school", user.getSchoolId());
        if(user.isTeacher()) {
            qb.requiredCriteria("ssp.id in (select sspt.subject_study_period_id from subject_study_period_teacher sspt where sspt.teacher_id = :teacherId)", "teacherId", user.getTeacherId());
        }
        List<?> data = qb.select("ssp.id, s.id as subj_id, s.code, s.name_et, s.name_en, sp.id as sp_id, sp.name_et as sp_name_et, sp.name_en as sp_name_en, s.assessment_code", em).getResultList();
        Map<Long, List<String>> teachers = teachersForSubjectStudyPeriods(StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data));

        return StreamUtil.toMappedList(r -> {
            Long id = resultAsLong(r, 0);
            String subjectCode = resultAsString(r, 2);
            return new SubjectStudyPeriodDto(id,
                    new AutocompleteResult(resultAsLong(r, 1), SubjectUtil.subjectName(subjectCode, resultAsString(r, 3)), SubjectUtil.subjectName(subjectCode, resultAsString(r, 4))),
                    teachers.get(id),
                    new AutocompleteResult(resultAsLong(r, 5), resultAsString(r, 6), resultAsString(r, 7)),
                    resultAsString(r, 8));
        }, data);
    }

    private SubjectStudyPeriodExam saveInternal(HoisUserDetails user, SubjectStudyPeriodExam exam, ExamForm form) {
        TimetableEvent te = exam.getTimetableEvent();

        if(te.getStart() != null && !examIsEditable(te.getStart())) {
            throw new ValidationFailedException("exam.message.editingdateexceed");
        }
        // if there are registered students, then exam time and type are not changeable
        // and deadline can be updated to be later only
        boolean hasRegistrations = exam.getId() != null && !StreamUtil.nullSafeList(exam.getStudents()).isEmpty();
        if(!hasRegistrations) {
            te.setStart(form.getStartDate().toLocalDate().atTime(form.getStartTime()));
            te.setEnd(form.getStartDate().toLocalDate().atTime(form.getEndTime()));
            if(te.getEnd().isBefore(te.getStart())) {
                throw new ValidationFailedException("exam.message.startafterend");
            }
            exam.setType(EntityUtil.validateClassifier(EntityUtil.getOptionalOne(form.getType(), em), MainClassCode.SOORITUS));
            te.setName(generateExamName(exam));
        }
        if(form.getDeadlineDate() != null && form.getDeadlineTime() != null) {
            LocalDateTime deadline = form.getDeadlineDate().toLocalDate().atTime(form.getDeadlineTime());
            if(deadline.isAfter(te.getStart())) {
                throw new ValidationFailedException("exam.message.deadlineafterstart");
            }
            if(hasRegistrations && exam.getDeadline() != null && deadline.isBefore(exam.getDeadline())) {
                throw new ValidationFailedException("exam.message.deadlinecannotshortened");
            }
            exam.setDeadline(deadline);
        } else {
            exam.setDeadline(null);
        }

        exam.setPlaces(form.getPlaces());
        exam.setAddInfo(form.getAddInfo());

        // room
        updateRoomsAndTeachers(te, form);
        updateRegistrations(user, exam, form);
        if(te.getId() == null) {
            em.persist(te);
        }
        return EntityUtil.save(exam, em);
    }

    private void updateRoomsAndTeachers(TimetableEvent te, ExamForm form) {
        List<TimetableEventTime> eventTimes = te.getTimetableEventTimes();
        if(eventTimes == null) {
            te.setTimetableEventTimes(eventTimes = new ArrayList<>());
        }
        TimetableEventTime tet;
        if(eventTimes.isEmpty()) {
            tet = new TimetableEventTime();
            tet.setTimetableEvent(te);
            eventTimes.add(tet);
        } else {
            tet = eventTimes.get(0);
        }
        tet.setStart(te.getStart());
        tet.setEnd(te.getEnd());
                
        List<EntityConnectionCommand> formRooms = new ArrayList<>();
        formRooms.add(form.getRoom());
        EntityUtil.bindEntityCollection(tet.getTimetableEventRooms(), r -> EntityUtil.getId(r.getRoom()), formRooms,
                r -> r.getId(), r -> {
                    TimetableEventRoom ter = new TimetableEventRoom();
                    ter.setRoom(em.getReference(Room.class, r.getId()));
                    ter.setTimetableEventTime(tet);
                    return ter;
                });
        
        SubjectStudyPeriod ssp = EntityUtil.getOptionalOne(SubjectStudyPeriod.class, form.getSubjectStudyPeriod(), em);
        List<Teacher> teachers = StreamUtil.toMappedList(t -> t.getTeacher(), ssp.getTeachers());
        EntityUtil.bindEntityCollection(tet.getTimetableEventTeachers(), t -> EntityUtil.getId(t.getTeacher()), teachers,
                t -> EntityUtil.getId(t), t -> {
                    TimetableEventTeacher tete = new TimetableEventTeacher();
                    tete.setTeacher(t);
                    tete.setTimetableEventTime(tet);
                    return tete;
                });
    }

    /**
     * Update registered students for exam
     *
     * @param user
     * @param exam
     * @param form
     */
    private void updateRegistrations(HoisUserDetails user, SubjectStudyPeriodExam exam, ExamForm form) {
        // verify all registered students have declared subject
        List<?> data = em.createNativeQuery("select ds.id from declaration_subject ds join declaration d on ds.declaration_id = d.id " +
                "where ds.subject_study_period_id = ?1 and d.status_code = ?2")
            .setParameter(1, EntityUtil.getId(exam.getSubjectStudyPeriod()))
            .setParameter(2, DeclarationStatus.OPINGUKAVA_STAATUS_K.name())
            .getResultList();

        Set<Long> declarations = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);
        Set<Long> studentIds = new HashSet<>(StreamUtil.nullSafeList(form.getStudents()));
        if(!declarations.containsAll(studentIds)) {
            // FIXME better error reporting - for each student?
            throw new ValidationFailedException("exam.message.subjectnotdeclared");
        }

        Set<Long> oldStudentIds = StreamUtil.toMappedSet(r -> EntityUtil.getId(r.getDeclarationSubject()), exam.getStudents());
        Set<Long> declarationSubjects = Stream.of(oldStudentIds, studentIds).flatMap(Collection::stream).collect(Collectors.toSet());
        Map<Long, List<Grade>> grades = gradesForDeclarationSubjects(declarationSubjects);
        Map<Long, List<ExamRegistration>> registrations = registrationsForDeclarationSubjects(declarationSubjects);
        Long examId = exam.getId();
        if(examId != null) {
            String examType = EntityUtil.getCode(exam.getType());
            // verify that removed students do not have result
            Set<Long> removedStudents = new HashSet<>(oldStudentIds);
            removedStudents.removeAll(studentIds);

            if(removedStudents.stream().anyMatch(r -> hasGrade(grades.get(r), examId, examType))) {
                // FIXME better error reporting - for each student?
                throw new ValidationFailedException("exam.message.studenthasgrade");
            }
            
            if(removedStudents.stream().anyMatch(r -> hasConnectedProtocol(registrations.get(r), examId))) {
                throw new ValidationFailedException("exam.message.studentconnectedprotocol");
            }
        }

        for(Long dsId : studentIds) {
            if(!oldStudentIds.contains(dsId)) {
                // check for first/repeating exam and if student has already result for new registrations
                checkPreviousRegistrations(exam, registrations.get(dsId), grades.get(dsId));
            }
        }
        // registration deadline is not validated for admin and teacher
        if(exam.getPlaces() != null && exam.getPlaces().longValue() < studentIds.size()) {
            throw new ValidationFailedException("exam.message.overbooked");
        }

        EntityUtil.setUsername(user.getUsername(), em);
        List<SubjectStudyPeriodExamStudent> students = exam.getStudents();
        if(students == null) {
            exam.setStudents(students = new ArrayList<>());
        }
        EntityUtil.bindEntityCollection(students, r -> EntityUtil.getId(r.getDeclarationSubject()), studentIds, id -> {
            SubjectStudyPeriodExamStudent s = new SubjectStudyPeriodExamStudent();
            s.setSubjectStudyPeriodExam(exam);
            s.setDeclarationSubject(em.getReference(DeclarationSubject.class, id));
            return s;
        });
    }

    private ExamDto getInternal(HoisUserDetails user, SubjectStudyPeriodExam exam) {
        Long examId = EntityUtil.getId(exam);
        List<AutocompleteResult> rooms = roomsForExams(Collections.singleton(examId)).get(examId);
        Long subjectStudyPeriodId = EntityUtil.getId(exam.getSubjectStudyPeriod());
        List<String> teachers = teachersForSubjectStudyPeriods(Collections.singleton(subjectStudyPeriodId)).get(subjectStudyPeriodId);
        return new ExamDto(exam, rooms.isEmpty() ? null : rooms.get(0), teachers, registrations(user, exam),
                examIsEditable(user, exam.getTimetableEvent().getStart()));
    }

    private DeclarationSubject findDeclarationSubject(SubjectStudyPeriodExam exam, Long studentId) {
        List<DeclarationSubject> data = em.createQuery("select ds from DeclarationSubject ds " +
                "where ds.subjectStudyPeriod.id = ?1 and ds.declaration.student.id = ?2 and ds.declaration.status.code = ?3", DeclarationSubject.class)
            .setParameter(1, EntityUtil.getId(exam.getSubjectStudyPeriod()))
            .setParameter(2, studentId)
            .setParameter(3, DeclarationStatus.OPINGUKAVA_STAATUS_K.name())
            .setMaxResults(1)
            .getResultList();

        return data.isEmpty() ? null : data.get(0);
    }

    private Map<Long, List<AutocompleteResult>> roomsForExams(Set<Long> examIds) {
        if(examIds.isEmpty()) {
            return Collections.emptyMap();
        }

        List<?> data = em.createNativeQuery("select sspe.id, r.id as r_id, b.code || '-' || r.code from subject_study_period_exam sspe " +
                "join timetable_event_time tet on sspe.timetable_event_id = tet.timetable_event_id " +
                "join timetable_event_room ter on tet.id = ter.timetable_event_time_id " +
                "join room r on ter.room_id = r.id " +
                "join building b on r.building_id = b.id " +
                "where sspe.id in (?1) order by r.code")
            .setParameter(1, examIds)
            .getResultList();
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> new AutocompleteResult(resultAsLong(r, 1), resultAsString(r, 2), null), Collectors.toList())));
    }

    private Map<Long, List<String>> teachersForSubjectStudyPeriods(Set<Long> subjectStudyPeriodIds) {
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

    private Map<Long, List<Grade>> gradesForDeclarationSubjects(Collection<Long> declarationSubjectIds) {
        if(declarationSubjectIds.isEmpty()) {
            return Collections.emptyMap();
        }

        List<?> data = em.createNativeQuery("select ds.id, sspes.subject_study_period_exam_id, ph.type_code, ps.grade " +
                "from protocol_student ps " +
                "join protocol_hdata ph on ps.protocol_id = ph.protocol_id " +
                "join declaration d on ps.student_id = d.student_id " +
                "join declaration_subject ds on d.id = ds.declaration_id and ph.subject_study_period_id = ds.subject_study_period_id " +
                "left join subject_study_period_exam_student sspes on ps.subject_study_period_exam_student_id = sspes.id " +
                "where ds.id in (?1)")
             .setParameter(1, declarationSubjectIds)
             .getResultList();
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(
                r -> new Grade(resultAsLong(r, 1), resultAsString(r, 2), resultAsString(r, 3)), Collectors.toList())));
    }

    private Map<Long, List<ExamRegistration>> registrationsForDeclarationSubjects(Collection<Long> declarationSubjectIds) {
        if(declarationSubjectIds.isEmpty()) {
            return Collections.emptyMap();
        }

        List<?> data = em.createNativeQuery("select ds.id, sspes.subject_study_period_exam_id, sspe.type_code, sspe.subject_study_period_id, " +
                "(select ssexams.id from protocol_hdata phdata " + 
                "join protocol_student pstu on phdata.protocol_id = pstu.protocol_id " + 
                "join subject_study_period_exam ssexam on phdata.subject_study_period_id = ssexam.subject_study_period_id " + 
                "join subject_study_period_exam_student ssexams on ssexam.id = ssexams.subject_study_period_exam_id " + 
                "where ssexam.id = sspe.id and pstu.subject_study_period_exam_student_id = ssexams.id and pstu.student_id = d.student_id) " +
                "as subject_study_period_exam_student_id " +
                "from subject_study_period_exam_student sspes " +
                "join subject_study_period_exam sspe on sspes.subject_study_period_exam_id = sspe.id " +
                "join declaration_subject ds on sspes.declaration_subject_id = ds.id " + 
                "join declaration d on ds.declaration_id = d.id " +
                "where ds.id in (?1)")
             .setParameter(1, declarationSubjectIds)
             .getResultList();
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(
                r -> new ExamRegistration(resultAsLong(r, 1), resultAsString(r, 2), resultAsLong(r, 3), resultAsLong(r, 4)), Collectors.toList())));
    }

    private SubjectStudyPeriodExam exam(HoisUserDetails user, Long examId) {
        SubjectStudyPeriodExam exam = em.getReference(SubjectStudyPeriodExam.class, examId);
        UserUtil.assertSameSchool(user, exam.getSubjectStudyPeriod().getSubject().getSchool());
        return exam;
    }

    private static List<ExamDto.ExamStudent> studentsForExam(SubjectStudyPeriodExam exam, List<?> data, Map<Long, List<Grade>> grades) {
        Long examId = EntityUtil.getId(exam);
        String examType = EntityUtil.getCode(exam.getType());

        return StreamUtil.toMappedList(r -> {
            ExamDto.ExamStudent dto = new ExamDto.ExamStudent();
            dto.setId(resultAsLong(r, 0));
            dto.setStudentId(resultAsLong(r, 1));
            dto.setFullname(PersonUtil.fullnameOptionalGuest(resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 10)));
            String curriculumVersionCode = resultAsString(r, 4);
            dto.setCurriculumVersion(new AutocompleteResult(null,
                    CurriculumUtil.versionName(curriculumVersionCode, resultAsString(r, 5)),
                    CurriculumUtil.versionName(curriculumVersionCode, resultAsString(r, 6))));
            dto.setStudentGroup(resultAsString(r, 7));
            dto.setRegistered(resultAsLocalDate(r, 8));
            dto.setGrade(Boolean.valueOf(hasGrade(grades.get(dto.getId()), examId, examType)));
            return dto;
        }, data);
    }

    private static String generateExamName(SubjectStudyPeriodExam exam) {
        SubjectStudyPeriod subjectStudyPeriod = exam.getSubjectStudyPeriod();
        Subject subject = subjectStudyPeriod.getSubject();
        return String.join(" ", SubjectUtil.subjectName(subject.getCode(), subject.getNameEt()), exam.getType().getNameEt().toLowerCase());
    }

    private static void checkPreviousRegistrations(SubjectStudyPeriodExam exam, List<ExamRegistration> registrations, List<Grade> grades) {
        Long examId = EntityUtil.getId(exam);
        if(ClassifierUtil.equals(ExamType.SOORITUS_P, exam.getType())) {
            // first exam
            if (registrations != null && registrations.stream().anyMatch(r -> ExamType.SOORITUS_P.name().equals(r.examType)
                    && !EntityUtil.getId(exam).equals(r.examId) && EntityUtil.getId(exam.getSubjectStudyPeriod()).equals(r.subjectStudyPeriod))) {
                throw new ValidationFailedException("exam.message.duplicatefirstexam");
            }
        } else {
            // repeating exam
            if(grades == null || !grades.stream().anyMatch(r -> ProtocolType.PROTOKOLLI_LIIK_P.name().equals(r.protocolType) && StringUtils.hasText(r.grade))) {
                // no result for first exam
                throw new ValidationFailedException("exam.message.missingresultforfirstexam");
            }
            if(grades.stream().anyMatch(r -> ProtocolType.PROTOKOLLI_LIIK_K.name().equals(r.protocolType) && !examId.equals(r.examId) && r.grade == null)) {
                // there is already registration for repeating exam
                throw new ValidationFailedException("exam.message.duplicaterepeatingexam");
            }
        }
    }

    private static void checkRegistrationDeadline(SubjectStudyPeriodExam exam) {
        if(!isBeforeDeadline(exam.getDeadline(), exam.getTimetableEvent().getStart())) {
            throw new ValidationFailedException("exam.message.deadline");
        }
    }

    private static boolean isBeforeDeadline(LocalDateTime deadline, LocalDateTime start) {
        return LocalDateTime.now().isBefore(deadline != null ? deadline : start);
    }

    private static boolean examIsEditable(HoisUserDetails user, LocalDateTime start) {
        return (user.isSchoolAdmin() || user.isTeacher())
                && UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_EKSAM)
                && examIsEditable(start);
    }

    private static boolean examIsEditable(LocalDateTime start) {
        // can modify next day after exam and before
        return !LocalDate.now().isAfter(start.toLocalDate().plusDays(1));
    }

    private static boolean hasGrade(List<Grade> grades, Long examId, String examType) {
        if(grades == null) {
            return false;
        }
        boolean repeating = ExamType.SOORITUS_K.name().equals(examType);
        String protocolType = (repeating ? ProtocolType.PROTOKOLLI_LIIK_K : ProtocolType.PROTOKOLLI_LIIK_P).name();
        return grades.stream().anyMatch(r -> protocolType.equals(r.protocolType) &&
                // for main protocol any grade, for repeating same exam
                (!repeating || examId.equals(r.examId)) && StringUtils.hasText(r.grade));
    }
    
    private static boolean hasConnectedProtocol(List<ExamRegistration> registrations, Long examId) {
        if(registrations == null) {
            return false;
        }
        return registrations.stream().anyMatch(r -> examId.equals(r.examId) && r.subjectStudyPeriodExamStudent != null);
    }

    static class Grade {
        final Long examId;
        final String protocolType;
        final String grade;

        public Grade(Long examId, String protocolType, String grade) {
            this.examId = examId;
            this.protocolType = protocolType;
            this.grade = grade;
        }
    }
    
    static class ExamRegistration {
        final Long examId;
        final String examType;
        final Long subjectStudyPeriod;
        final Long subjectStudyPeriodExamStudent;
        
        public ExamRegistration(Long examId, String examType, Long subjectStudyPeriod,
                Long subjectStudyPeriodExamStudent) {
            this.examId = examId;
            this.examType = examType;
            this.subjectStudyPeriod = subjectStudyPeriod;
            this.subjectStudyPeriodExamStudent = subjectStudyPeriodExamStudent;
        }
    }
}
