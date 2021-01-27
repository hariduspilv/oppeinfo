package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.gradingschema.GradingSchemaRow;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.JpaQueryBuilder;
import ee.hitsa.ois.util.ProtocolUtil;
import ee.hitsa.ois.web.commandobject.ProtocolStudentSaveForm;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.dto.HigherProtocolModuleDto;
import ee.hitsa.ois.web.dto.HigherProtocolModuleSubjectDto;
import ee.hitsa.ois.web.dto.HigherProtocolModuleSubjectResultDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodMidtermTaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolHdata;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodExamStudent;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.enums.ExamType;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.enums.ProtocolType;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.report.HigherProtocolReport;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HigherProtocolGradeUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.HigherProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.HigherProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.HigherProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.HigherProtocolStudentSearchCommand;
import ee.hitsa.ois.web.commandobject.ProtocolCalculateCommand;
import ee.hitsa.ois.web.commandobject.higherprotocol.SubjectStudyPeriodCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.HigherProtocolDto;
import ee.hitsa.ois.web.dto.HigherProtocolSearchDto;
import ee.hitsa.ois.web.dto.HigherProtocolStudentDto;
import ee.hitsa.ois.web.dto.ProtocolPracticeJournalResultDto;
import ee.hitsa.ois.web.dto.ProtocolStudentResultDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

@Transactional
@Service
public class HigherProtocolService extends AbstractProtocolService {

    private static final String STUDENT_SELECT = "s.id, p.firstname, p.lastname, "
            + "sg.code as studentGroupCode, c.code as curriculumCode, s.type_code";
    private static final String STUDENT_FROM = "from student s "
            + "join person p on p.id = s.person_id "
            + "left join student_group sg on sg.id = s.student_group_id "
            + "left join curriculum_version cv on cv.id = s.curriculum_version_id "
            + "left join curriculum c on c.id = cv.curriculum_id "
            + "join declaration d on d.student_id = s.id "
            + "join declaration_subject ds on d.id = ds.declaration_id "
            + "left join curriculum_version_hmodule cvh on cvh.id = ds.curriculum_version_hmodule_id";

    public Page<HigherProtocolSearchDto> search(HoisUserDetails user, HigherProtocolSearchCommand criteria,
            Pageable pageable) {
        JpaQueryBuilder<Protocol> qb = new JpaQueryBuilder<>(Protocol.class, "p",
                "join p.protocolHdata phd "
                + "left join phd.subjectStudyPeriod ssp "
                + "left join ssp.studyPeriod sp "
                + "left join ssp.subject s "
                + "left join phd.curriculumVersionHmodule cvh "
                + "left join cvh.curriculumVersion.curriculum cvh_c")
                .sort(pageable);

        qb.requiredCriteria("p.isFinal = :isFinal", "isFinal", Boolean.FALSE);
        qb.requiredCriteria("p.isVocational = :isVocational", "isVocational", Boolean.FALSE);
        qb.requiredCriteria("p.school.id = :schoolId", "schoolId", user.getSchoolId());

        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("(exists (select 1 from s.curriculumVersionHigherModuleSubjects cvhms "
                    + "where cvhms.subject.id = s.id and cvhms.module.curriculumVersion.curriculum.id in (:curriculumIds)) "
                    + "or cvh_c.id in (:curriculumIds))", "curriculumIds", user.getCurriculumIds());
        }
        
        if (criteria.getStudentGroup() != null) {
            qb.optionalCriteria("exists (select 1 from p.protocolStudents ps " + 
                    " where ps.student.studentGroup.id = :studentGroupId)", "studentGroupId",
                    criteria.getStudentGroup().getId());
        }

        qb.optionalCriteria("(ssp.id is not null and exists (select 1 from ssp.teachers sspt "
                + "where sspt.teacher.id = :teacherId) or phd.teacher.id = :teacherId)", "teacherId",
                user.isTeacher() ? user.getTeacherId() : criteria.getTeacher());

        if (criteria.getStudyPeriod() != null) {
            StudyPeriod studyPeriod = em.getReference(StudyPeriod.class, criteria.getStudyPeriod());
            qb.requiredCriteria("((sp.id is null and p.inserted >= :spStart and p.inserted <= :spEnd) or sp.id = :spId)",
                    "spId", criteria.getStudyPeriod());
            qb.parameter("spStart", DateUtils.firstMomentOfDay(studyPeriod.getStartDate()));
            qb.parameter("spEnd", DateUtils.lastMomentOfDay(studyPeriod.getEndDate()));
        }

        qb.optionalCriteria("p.status.code = :protocolStatus", "protocolStatus", criteria.getStatus());
        qb.optionalCriteria("p.protocolNr = :protocolNr", "protocolNr", criteria.getProtocolNr());
        qb.optionalContains(Arrays.asList("s.code", "s.nameEt", "s.nameEn", "cvh.nameEt", "cvh.nameEn"),
                "subjectModule", criteria.getSubjectModule());

        qb.optionalCriteria("p.inserted >= :insertedFrom", "insertedFrom", criteria.getInsertedFrom(),
                DateUtils::firstMomentOfDay);
        qb.optionalCriteria("p.inserted <= :insertedThru", "insertedThru", criteria.getInsertedThru(),
                DateUtils::lastMomentOfDay);
        qb.optionalCriteria("p.confirmDate >= :confirmedFrom", "confirmedFrom", criteria.getConfirmDateFrom());
        qb.optionalCriteria("p.confirmDate <= :confirmedThru", "confirmedThru", criteria.getConfirmDateThru());

        if (Boolean.TRUE.equals(criteria.getShowOnlyModuleProtocols())) {
            qb.filter("cvh.id is not null");
        }
        Page<HigherProtocolSearchDto> page = JpaQueryUtil.pagingResult(qb, em, pageable).map(p -> HigherProtocolSearchDto.ofWithUserRights(p, user));
        List<Long> protocolIds = page.getContent().stream().map(p -> p.getId()).collect(Collectors.toList());
        Map<Long, List<String>> studentGroups = getProtocolStudentGroups(protocolIds);
        return page.map(r -> {
            r.setStudentGroups(studentGroups.get(r.getId()));
            return r;
        });
    }

    private Map<Long, List<String>> getProtocolStudentGroups(List<Long> protocolIds) {
        if(protocolIds.isEmpty()) {
            return Collections.emptyMap();
        }

        List<?> data = em.createNativeQuery("select distinct p.id as protocolId, sg.code "
                + "from protocol p "
                + "join protocol_student ps on ps.protocol_id = p.id "
                + "join student s on s.id = ps.student_id "
                + "join student_group sg on sg.id = s.student_group_id "
                + "where p.id in (?1)")
            .setParameter(1, protocolIds)
            .getResultList();
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> {
                    return resultAsString(r, 1);
                }, Collectors.toList())));
    }

    public HigherProtocolDto create(HoisUserDetails user, HigherProtocolCreateForm form) {
        Protocol protocol = new Protocol();
        protocol.setIsFinal(Boolean.FALSE);
        protocol.setIsVocational(Boolean.FALSE);
        protocol.setProtocolNr(generateProtocolNumber());
        protocol.setSchool(em.getReference(School.class, user.getSchoolId()));
        protocol.setStatus(em.getReference(Classifier.class, ProtocolStatus.PROTOKOLL_STAATUS_S.name()));

        ProtocolHdata protocolHData = new ProtocolHdata();
        protocolHData.setType(em.getReference(Classifier.class, form.getProtocolType()));
        protocolHData.setSubjectStudyPeriod(EntityUtil.getOptionalOne(SubjectStudyPeriod.class,
                form.getSubjectStudyPeriod(), em));
        protocolHData.setCurriculumVersionHmodule(EntityUtil.getOptionalOne(CurriculumVersionHigherModule.class,
                form.getCurriculumVersionHmodule(), em));
        protocolHData.setTeacher(EntityUtil.getOptionalOne(Teacher.class, form.getTeacher(), em));
        protocolHData.setProtocol(protocol);
        protocol.setProtocolHdata(protocolHData);

        Map<Long, Long> examRegistrations = new HashMap<>();
        Set<Long> studentIds = form.getStudents();
        if(studentIds != null && !studentIds.isEmpty()) {
            boolean repeating = ClassifierUtil.equals(ProtocolType.PROTOKOLLI_LIIK_K, protocolHData.getType());
            if(repeating) {
                List<?> data = em.createNativeQuery("select d.student_id, sspes.id from subject_study_period_exam_student sspes "
                        + "join subject_study_period_exam sspe on sspes.subject_study_period_exam_id = sspe.id "
                        + "join declaration_subject ds on sspes.declaration_subject_id = ds.id "
                        + "join declaration d on ds.declaration_id = d.id "
                        + "where sspe.type_code = ?1 and ds.subject_study_period_id = ?2 "
                        + "and not exists (select ps.id from protocol_student ps where ps.subject_study_period_exam_student_id = sspes.id)")
                    .setParameter(1, ExamType.SOORITUS_K.name())
                    .setParameter(2, EntityUtil.getId(protocolHData.getSubjectStudyPeriod()))
                    .getResultList();
                data.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> resultAsLong(r, 1), (o, n) -> o, () -> examRegistrations));
            }
        }
        protocol.setProtocolStudents(StreamUtil.toMappedList(studentId -> {
            ProtocolStudent protocolStudent =  new ProtocolStudent();
            protocolStudent.setStudent(em.getReference(Student.class, studentId));
            protocolStudent.setProtocol(protocol);
            // link with exam
            Long examRegistrationId = examRegistrations.get(studentId);
            protocolStudent.setSubjectStudyPeriodExamStudent(EntityUtil.getOptionalOne(SubjectStudyPeriodExamStudent.class, examRegistrationId, em));
            return protocolStudent;
        }, studentIds));

        return HigherProtocolDto.ofWithIdOnly(EntityUtil.save(protocol, em));
    }

    public Protocol save(Protocol protocol, HigherProtocolSaveForm form) {
        protocol.setFinalDate(form.getFinalDate());
        updateProtocolStudents(protocol, form);
        return EntityUtil.save(protocol, em);
    }

    private void updateProtocolStudents(Protocol protocol, HigherProtocolSaveForm form) {
        boolean isMainProtocol = ClassifierUtil.equals(ProtocolType.PROTOKOLLI_LIIK_P, protocol.getProtocolHdata().getType());
        Boolean isLetterGrade = protocol.getSchool().getIsLetterGrade();
        Map<Long, LocalDate> protocolStudentExamDates = protocolStudentExamDates(protocol);
        EntityUtil.bindEntityCollection(protocol.getProtocolStudents(), ProtocolStudent::getId,
                // no protocol students created here
                form.getProtocolStudents(), ProtocolStudentSaveForm::getId, null, (dto, ps) -> {
                    if (gradeChangedButNotRemoved(dto, ps)) {
                        assertHasAddInfoIfProtocolConfirmed(dto, protocol);
                        addHistory(ps);
                        Classifier grade = em.getReference(Classifier.class, dto.getGrade().getCode());
                        Long psId = EntityUtil.getId(ps);
                        Short mark = HigherAssessment.getGradeMark(dto.getGrade().getCode());
                        GradingSchemaRow gradingSchemaRow = EntityUtil.getOptionalOne(GradingSchemaRow.class,
                                dto.getGrade().getGradingSchemaRowId(), em);
                        LocalDate gradeDate;
                        if (isMainProtocol) {
                            gradeDate = protocol.getFinalDate() != null ? protocol.getFinalDate() : LocalDate.now();
                        } else {
                            gradeDate = protocolStudentExamDates.containsKey(psId)
                                    ? protocolStudentExamDates.get(psId)
                                    : protocol.getFinalDate() != null ? protocol.getFinalDate() : LocalDate.now();
                        }
                        gradeStudent(ps, grade, mark, isLetterGrade, gradingSchemaRow, gradeDate);
                    } else if (gradeRemoved(dto, ps)) {
                        assertHasAddInfoIfProtocolConfirmed(dto, protocol);
                        addHistory(ps);
                        removeGrade(ps);
                    } else if (ps.getGrade() != null) {
                        // If protocol date (saved in final_date field) changes then protocol student grades need to
                        // be updated. History protocol student history won't be updated.
                        if (isMainProtocol) {
                            ps.setGradeDate(protocol.getFinalDate() != null ? protocol.getFinalDate() : LocalDate.now());
                        } else {
                            Long psId = EntityUtil.getId(ps);
                            ps.setGradeDate(protocolStudentExamDates.containsKey(psId)
                                    ? protocolStudentExamDates.get(psId)
                                    : protocol.getFinalDate() != null ? protocol.getFinalDate() : LocalDate.now());
                        }
                    }
                    ps.setAddInfo(dto.getAddInfo());
                });
    }

    private Map<Long, LocalDate> protocolStudentExamDates(Protocol protocol) {
        List<?> data = em.createNativeQuery("select ps.id, te.start from protocol_student ps "
                + "join subject_study_period_exam_student sspes on sspes.id = ps.subject_study_period_exam_student_id "
                + "join subject_study_period_exam sspe on sspe.id = sspes.subject_study_period_exam_id "
                + "join timetable_event te on te.id = sspe.timetable_event_id "
                + "where ps.protocol_id = :protocolId")
                .setParameter("protocolId", protocol.getId())
                .getResultList();
        return StreamUtil.toMap(r -> resultAsLong(r, 0), r -> resultAsLocalDate(r, 1), data);
    }

    public Protocol confirm(HoisUserDetails user, Protocol protocol, HigherProtocolSaveForm form) {
        setConfirmation(user, protocol);
        
        if (form != null) {
            protocol.setFinalDate(form.getFinalDate());
            updateProtocolStudents(protocol, form);
        }
        protocol = EntityUtil.save(protocol, em);
        
        for (ProtocolStudent protocolStudent : protocol.getProtocolStudents()) {
            if (protocolStudent.getGrade() == null) {
                throw new ValidationFailedException("higherProtocol.message.gradeNotSelectedForAllStudents");
            }
        }
        sendStudentResultMessages(protocol);
        return protocol;
    }

    public List<AutocompleteResult> getSubjectStudyPeriods(HoisUserDetails user, SubjectStudyPeriodCommand lookup) {
        if(lookup.getStudyPeriodId() == null) {
            return Collections.emptyList();
        }
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period ssp "
                + "join subject s on s.id = ssp.subject_id "
                + "left join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id "
                + "left join teacher t on t.id = sspt.teacher_id "
                + "left join person p on p.id = t.person_id");
        qb.requiredCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", lookup.getStudyPeriodId());
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("exists (select 1 from subject_study_period_teacher sspt "
                + "where sspt.subject_study_period_id = ssp.id and sspt.teacher_id = :teacherId)",
                "teacherId", user.getTeacherId());
        qb.optionalContains("s.name_et", "name", lookup.getName());
        qb.optionalCriteria("exists (select 1 from subject_study_period_student_group sspsg "
                + "where sspsg.subject_study_period_id = ssp.id "
                + "and sspsg.student_group_id = :studentGroupId)", "studentGroupId", lookup.getStudentGroupId());
        
        List<?> data = qb.groupBy("ssp.id, s.code, s.name_et, s.name_en, s.credits")
                .sort("s.name_et, s.name_en, teachers, s.code")
                .select("ssp.id, s.code, s.name_et, s.name_en, s.credits, string_agg(distinct p.firstname || ' ' || p.lastname, ', ') as teachers", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String teacherNames = resultAsString(r, 5) != null ? " - " + resultAsString(r, 5) : "";
            String nameEt = SubjectUtil.subjectName(resultAsString(r, 1), resultAsString(r, 2), resultAsDecimal(r, 4)) + teacherNames;
            String nameEn = SubjectUtil.subjectName(resultAsString(r, 1), resultAsString(r, 3), resultAsDecimal(r, 4)) + teacherNames;
            return new AutocompleteResult(resultAsLong(r, 0), nameEt, nameEn);
        }, data);
    }

    public Page<AutocompleteResult> getModuleProtocolCurriculumVersions(HoisUserDetails user, SearchCommand lookup,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_version cv "
                + "join curriculum c on cv.curriculum_id = c.id");

        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("cv.status_code != :statusCode", "statusCode", CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_C);
        qb.requiredCriteria("c.is_higher = :higher", "higher", Boolean.TRUE);
        qb.optionalContains(Arrays.asList("cv.code", "cv.code || ' ' || c.name_et", "cv.code || ' ' || c.name_en"),
                "name", lookup.getName());
        qb.filter("exists (select 1 from curriculum_version_hmodule cvh where cvh.curriculum_version_id = cv.id "
                + "and cvh.is_grade = true and cvh.is_minor_speciality = false)");

        qb.sort(Language.EN.equals(lookup.getLang()) ? "cv.code, c.name_et" : "cv.code, c.name_en");
        return JpaQueryUtil.pagingResult(qb, "cv.id, cv.code, c.name_et, c.name_en", em, pageable).map(r ->
            new AutocompleteResult(resultAsLong(r, 0), CurriculumUtil.versionName(
                    resultAsString(r, 1), resultAsString(r, 2)), CurriculumUtil.versionName(resultAsString(r, 1),
                    resultAsString(r, 3))));
    }

    public List<StudentSearchDto> getStudents(Long schoolId, HigherProtocolStudentSearchCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(STUDENT_FROM);

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("s.status_code in :activeStatuses", "activeStatuses",
                StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.requiredCriteria("d.status_code = :status", "status", DeclarationStatus.OPINGUKAVA_STAATUS_K);
        qb.requiredCriteria("ds.subject_study_period_id = :subjectStudyPeriodId",
                "subjectStudyPeriodId", criteria.getSubjectStudyPeriod());
        qb.requiredCriteria("(cvh.id is null or cvh.type_code not in (:finalModuleTypes))", "finalModuleTypes",
                HigherModuleType.FINAL_MODULES);
        qb.optionalCriteria("sg.id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());

        boolean repeating = ProtocolType.PROTOKOLLI_LIIK_K.name().equals(criteria.getProtocolType());
        if(repeating) {
            qb.filter("exists (select p.id from protocol p " 
                    + "join protocol_hdata ph on p.id = ph.protocol_id "
                    + "left join protocol_student ps on p.id = ps.protocol_id "
                    + "where ph.type_code = '" + ProtocolType.PROTOKOLLI_LIIK_P.name() + "' "
                    + "and ph.subject_study_period_id = ds.subject_study_period_id and ps.student_id = s.id "
                    + "and ps.grade_code is not null)");

            // and no registration for repeating exam without result
            String sql = "select 1 from subject_study_period_exam_student s2 " +
                    "join subject_study_period_exam e2 on s2.subject_study_period_exam_id = e2.id " +
                    "join protocol_student ps on ps.subject_study_period_exam_student_id = s2.id " +
                    "where s2.declaration_subject_id = ds.id and ps.grade is null and e2.type_code = '"+ExamType.SOORITUS_K.name()+"'";
            qb.filter("not exists(" + sql +")");

            qb.filter("not exists (select p.id from protocol p "
                    + "join protocol_hdata ph on p.id = ph.protocol_id "
                    + "left join protocol_student ps on p.id = ps.protocol_id "
                    + "where ph.type_code = '" + ProtocolType.PROTOKOLLI_LIIK_K.name() + "' "
                    + "and ph.subject_study_period_id = ds.subject_study_period_id and ps.student_id = s.id "
                    + "and ps.grade_code is null)");
        } else {
            qb.filter("not exists (select p.id from protocol p " 
                    + "join protocol_hdata ph on p.id = ph.protocol_id "
                    + "left join protocol_student ps on p.id = ps.protocol_id "
                    + "where ph.type_code = '" + ProtocolType.PROTOKOLLI_LIIK_P.name() + "' "
                    + "and ph.subject_study_period_id = ds.subject_study_period_id and ps.student_id = s.id)");
        }

        qb.sort("p.firstname, p.lastname");
        List<?> result = qb.select(STUDENT_SELECT, em).getResultList();
        return StreamUtil.toMappedList(this::mapStudent, result);
    }

    public List<StudentSearchDto> getModuleProtocolStudents(Long schoolId, Long curriculumVersionHmodule) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
                + "join person p on p.id = s.person_id "
                + "left join student_group sg on sg.id = s.student_group_id "
                + "join curriculum_version cv on cv.id = s.curriculum_version_id "
                + "join curriculum c on c.id = cv.curriculum_id "
                + "join curriculum_version_hmodule cvhm2 on cvhm2.curriculum_version_id = cv.id ");

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("cvhm2.id = :moduleId", "moduleId", curriculumVersionHmodule);
        qb.requiredCriteria("s.status_code in :activeStatuses", "activeStatuses",
                StudentStatus.STUDENT_STATUS_ACTIVE);

        qb.filter("(coalesce(s.curriculum_speciality_id, 0) = 0 or coalesce(s.curriculum_speciality_id, 0) > 0 "
                + "and exists(select 1 from curriculum_version_hmodule_speciality hs "
                + "join curriculum_version_speciality cvs on hs.curriculum_version_speciality_id = cvs.id "
                + "where hs.curriculum_version_hmodule_id = cvhm2.id and "
                + "cvs.curriculum_speciality_id = s.curriculum_speciality_id))");

        qb.requiredCriteria("not exists (select 1 from protocol_student ps "
                + "join protocol p on p.id = ps.protocol_id "
                + "join protocol_hdata phd on phd.protocol_id = p.id "
                + "where ps.student_id = s.id and phd.curriculum_version_hmodule_id = cvhm2.id "
                + "and (grade_code is null or grade_code in (:positiveGrades)) "
                + "union all "
                + "select 1 from student_higher_result shr2 "
                + "where shr2.student_id = s.id and shr2.is_active = true and shr2.is_module = true "
                + "and shr2.curriculum_version_hmodule_id = cvhm2.id and shr2.grade_code in (:positiveGrades))",
                "positiveGrades", HigherAssessment.GRADE_POSITIVE);

        qb.sort("p.lastname, p.firstname");
        String select = STUDENT_SELECT + ", ((select cvhm.compulsory_study_credits <= sum(case when x.is_optional = false then x.credits else 0 end) "
                    + "and cvhm.optional_study_credits <= sum(case when x.is_optional then x.credits else 0 end) "
                    + "from (select 0, shr.id, shr.credits, coalesce(shrm.is_optional, shr.is_optional) as is_optional, "
                        + "coalesce(shrm.curriculum_version_hmodule_id, shr.curriculum_version_hmodule_id) as curriculum_version_hmodule_id "
                        + "from student_higher_result shr "
                        + "left join student_higher_result_module shrm on shrm.student_higher_result_id = shr.id "
                        + "join curriculum_version_hmodule cvhm on cvhm.id = coalesce(shrm.curriculum_version_hmodule_id, shr.curriculum_version_hmodule_id) "
                        + "where shr.student_id = s.id and cvhm.id = :moduleId and shr.grade_code in (:positiveGrades) "
                        + "and (select count(*) from student_higher_result_replaced_subject shrrs where shr.id = shrrs.student_higher_result_id) = 0 "
                    + "union "
                    + "select distinct 1, sb.id,sb.credits, shrrs.is_optional, shrrs.curriculum_version_hmodule_id "
                        + "from student_higher_result shr "
                        + "join student_higher_result_replaced_subject shrrs on shrrs.student_higher_result_id = shr.id "
                        + "join curriculum_version_hmodule cvhm on cvhm.id = shrrs.curriculum_version_hmodule_id "
                        + "join subject sb on sb.id = shrrs.subject_id "
                        + "where shr.student_id = s.id and cvhm.id = :moduleId and shr.grade_code in (:positiveGrades)) x "
                    + "join curriculum_version_hmodule cvhm on cvhm.id = x.curriculum_version_hmodule_id "
                    + "group by cvhm.id) "
                + "or exists (select 1 from student_curriculum_completion_hmodule scch "
                + "where scch.student_id = s.id and scch.curriculum_version_hmodule_id = :moduleId)) module_fulfilled";
        List<?> result = qb.select(select, em).getResultList();
        return StreamUtil.toMappedList(r -> {
            StudentSearchDto dto = mapStudent(r);
            dto.setCanSelect(resultAsBoolean(r, 6));
            return dto;
        }, result);
    }

    public StudentSearchDto mapStudent(Object result) {
        StudentSearchDto dto = new StudentSearchDto();
        dto.setId(resultAsLong(result, 0));
        dto.setFullname(PersonUtil.fullnameTypeSpecific(resultAsString(result, 1), resultAsString(result, 2),
                resultAsString(result, 5)));
        dto.setStudentGroup(new AutocompleteResult(null, resultAsString(result, 3), resultAsString(result, 3)));
        dto.setCurriculum(new AutocompleteResult(null, resultAsString(result, 4), resultAsString(result, 4)));
        return dto;
    }

    public List<ProtocolStudentResultDto> calculateGrades(Protocol protocol, ProtocolCalculateCommand command) {
        List<ProtocolStudentResultDto> calculatedResults = new ArrayList<>();

        if (protocol.getProtocolHdata().getSubjectStudyPeriod() != null) {
            for (Long protocolStudentId : command.getProtocolStudents()) {
                ProtocolStudent ps = em.getReference(ProtocolStudent.class, protocolStudentId);
                HigherAssessment grade = HigherProtocolGradeUtil.calculateGrade(ps);
                calculatedResults.add(new ProtocolStudentResultDto(protocolStudentId, grade));
            }
        } else {
            Map<Long, List<HigherProtocolModuleSubjectResultDto>> results = studentSubjectResults(protocol.getId(),
                    command.getProtocolStudents(), true);
            for(Long protocolStudentId : command.getProtocolStudents()) {
                List<HigherProtocolModuleSubjectResultDto> studentResults = results.get(protocolStudentId);
                HigherAssessment grade = calculateModuleProtocolGrade(studentResults);
                calculatedResults.add(new ProtocolStudentResultDto(protocolStudentId, grade));
            }
        }
        return calculatedResults;
    }

    public HigherProtocolDto get(HoisUserDetails user, Protocol protocol) {
        HigherProtocolDto dto = HigherProtocolDto.ofWithUserRights(user, protocol);
        dto.setStudyYearId(higherProtocolStudyYear(protocol));
        SubjectStudyPeriod subjectStudyPeriod = protocol.getProtocolHdata().getSubjectStudyPeriod();
        if (subjectStudyPeriod != null) {
            dto.setAssessmentCode(EntityUtil.getNullableCode(subjectStudyPeriod.getSubject().getAssessment()));

        }
        Map<Long, List<HigherProtocolModuleSubjectResultDto>> subjectResults = new HashMap<>();
        Map<Long, List<ProtocolPracticeJournalResultDto>> practiceResults = new HashMap<>();
        List<Long> studentsWithNewerProtocols = studentsWithNewerProtocols(protocol);

        if (dto.getSubjectStudyPeriodMidtermTaskDto() != null) {
            SubjectStudyPeriodMidtermTaskDto sspMidtermTaskDto = dto.getSubjectStudyPeriodMidtermTaskDto();
            if (Boolean.TRUE.equals(sspMidtermTaskDto.getSubjectStudyPeriod().getIsPracticeSubject())) {
                practiceResults = studentPracticeResults(dto);
            }
        } else {
            subjectResults = studentSubjectResults(protocol.getId(), null, false);
            setModuleDtoSubjects(dto);
        }

        for (HigherProtocolStudentDto protocolStudent : dto.getProtocolStudents()) {
            Long studentId = protocolStudent.getStudent().getId();

            if (studentsWithNewerProtocols.contains(studentId)) {
                protocolStudent.setCanBeDeleted(Boolean.FALSE);
                protocolStudent.setCanChangeGrade(Boolean.FALSE);
            }
            List<ProtocolPracticeJournalResultDto> studentPracticeResults = practiceResults.get(studentId);
            if (studentPracticeResults != null) {
                protocolStudent.setPracticeJournalResults(studentPracticeResults);
            }
            List<HigherProtocolModuleSubjectResultDto> subjectStudentResults = subjectResults.get(protocolStudent.getId());
            if (subjectStudentResults != null) {
                protocolStudent.setSubjectResults(subjectStudentResults);
            }
        }
        return dto;
    }

    private  Map<Long, List<ProtocolPracticeJournalResultDto>> studentPracticeResults(HigherProtocolDto dto) {
        Set<Long> students = StreamUtil.toMappedSet(ps -> ps.getStudent().getId(), dto.getProtocolStudents());
        if (students.isEmpty()) {
            return new HashMap<>();
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from practice_journal pj "
                + "join practice_journal_module_subject pjms on pjms.practice_journal_id = pj.id");

        qb.requiredCriteria("pj.student_id in (:studentId)", "studentId", students);
        qb.requiredCriteria("pjms.subject_id = :subjectId", "subjectId",
                dto.getSubjectStudyPeriodMidtermTaskDto().getSubjectStudyPeriod().getSubject().getId());
        qb.filter("pj.grade_code is not null");

        List<?> data = qb.select("pj.student_id, pj.id, pj.grade_code, pj.grading_schema_row_id, "
                + "pj.grade_inserted", em).getResultList();

        return StreamUtil.nullSafeList(data).stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> new ProtocolPracticeJournalResultDto(resultAsLong(r, 1), resultAsString(r, 2),
                        resultAsLong(r, 3), resultAsLocalDateTime(r, 4), null), Collectors.toList())));
    }

    private List<Long> studentsWithNewerProtocols(Protocol protocol) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from protocol p "
                + "join protocol_student ps on ps.protocol_id = p.id "
                + "join protocol_hdata hdata on p.id = hdata.protocol_id");
        qb.requiredCriteria("p.id = :protocolId", "protocolId", EntityUtil.getId(protocol));

        qb.filter("exists (select 1 from protocol p2 "
                + "join protocol_student ps2 on ps2.protocol_id = p2.id "
                + "join protocol_hdata hdata2 on p2.id = hdata2.protocol_id "
                + "where p.id < p2.id and (hdata.subject_study_period_id = hdata2.subject_study_period_id "
                + "or hdata.curriculum_version_hmodule_id = hdata2.curriculum_version_hmodule_id) "
                + "and ps.student_id = ps2.student_id)");

        List<?> data = qb.select("ps.student_id", em).getResultList();
        return StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
    }

    @Override
    public void removeStudent(HoisUserDetails user, ProtocolStudent student) {
        if (ProtocolUtil.hasGrade(student)) {
            throw new ValidationFailedException("higherProtocol.error.cantRemoveStudent");
        }
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(student, em);
    }

    public HigherProtocolReport higherProtocolReport(Protocol protocol) {
        School school = protocol.getSchool();
        return new HigherProtocolReport(protocol, school.getIsLetterGrade());
    }

    private void setModuleDtoSubjects(HigherProtocolDto dto) {
        HigherProtocolModuleDto module = dto.getModuleDto();
        Long moduleId = module.getModule().getId();
        Set<Long> studentIds = StreamUtil.toMappedSet(ps -> ps.getStudent().getId(), dto.getProtocolStudents());

        Map<String, Object> parameters = new HashMap<>();
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject s "
                + "join curriculum_version_hmodule_subject cvhs on cvhs.subject_id = s.id");
        qb.requiredCriteria("cvhs.curriculum_version_hmodule_id = :moduleId", "moduleId", moduleId);

        String moduleSubjects = qb.querySql("s.id subject_id, s.code, s.name_et, s.name_en, s.credits, "
                + "null shr_id, cvhs.is_optional, true module_subject", false);
        parameters.putAll(qb.queryParameters());

        String resultSubjects = null;
        if (!studentIds.isEmpty()) {
            qb = new JpaNativeQueryBuilder("from student_higher_result shr "
                    + "left join student_higher_result_module shrm on shrm.student_higher_result_id = shr.id");

            qb.requiredCriteria("coalesce(shrm.curriculum_version_hmodule_id, shr.curriculum_version_hmodule_id) = :moduleId",
                    "moduleId", moduleId);
            qb.requiredCriteria("shr.student_id in (:studentIds)", "studentIds", studentIds);
            qb.requiredCriteria("shr.is_active = :isActive", "isActive", Boolean.TRUE);
            qb.requiredCriteria("shr.is_module = :isModule", "isModule", Boolean.FALSE);
            qb.filter("(shr.subject_id is null or shr.subject_id not in "
                    + "(select cvhs.subject_id from curriculum_version_hmodule_subject cvhs "
                    + "where cvhs.curriculum_version_hmodule_id = :moduleId))");

            resultSubjects = qb.querySql("shr.subject_id, coalesce(shr.subject_code, "
                    + "substring(shr.subject_name_et from 0 for 8)) code, shr.subject_name_et, shr.subject_name_en, "
                    + "shr.credits, shr.id shr_id, coalesce(shrm.is_optional, shr.is_optional), false module_subject", false);
            parameters.putAll(qb.queryParameters());
        }

        String from = "from (" + moduleSubjects + (resultSubjects != null ? " union all " + resultSubjects : "") + ") as r";
        qb = new JpaNativeQueryBuilder(from).sort("module_subject desc, subject_id nulls last, is_optional desc, code");
        List<?> data = qb.select("*", em, parameters).getResultList();

        List<HigherProtocolModuleSubjectDto> subjects = StreamUtil.toMappedList(r -> {
            HigherProtocolModuleSubjectDto subject = new HigherProtocolModuleSubjectDto();
            subject.setId(resultAsLong(r, 0));
            subject.setCode(resultAsString(r, 1));
            subject.setNameEt(resultAsString(r, 2));
            subject.setNameEn(resultAsString(r, 3));
            subject.setCredits(resultAsDecimal(r, 4));
            subject.setStudentHigherResultId(resultAsLong(r, 5));
            return subject;
        }, data);
        dto.getModuleDto().setSubjects(subjects);
    }

    private Map<Long, List<HigherProtocolModuleSubjectResultDto>> studentSubjectResults(Long protocolId,
            Set<Long> protocolStudents, boolean positiveGrades) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from protocol p"
                + " join protocol_hdata phd on phd.protocol_id = p.id"
                + " join protocol_student ps on ps.protocol_id = p.id"
                + " join student_higher_result shr on shr.student_id = ps.student_id"
                + " left join student_higher_result_module shrm on shrm.student_higher_result_id = shr.id"
                + " join curriculum_version_hmodule cvhm on cvhm.id = phd.curriculum_version_hmodule_id and"
                + " cvhm.id = coalesce(shrm.curriculum_version_hmodule_id, shr.curriculum_version_hmodule_id)");
        qb.requiredCriteria("p.id = :protocolId", "protocolId", protocolId);
        qb.requiredCriteria("shr.is_active = :isActive", "isActive", Boolean.TRUE);
        qb.requiredCriteria("shr.is_module = :isModule", "isModule", Boolean.FALSE);
        qb.optionalCriteria("ps.id in (:protocolStudenttIds)", "protocolStudenttIds", protocolStudents);
        if (positiveGrades) {
            qb.requiredCriteria("shr.grade_code in (:positiveGrades)", "positiveGrades", HigherAssessment.GRADE_POSITIVE);
        }

        List<?> data = qb.select("ps.id ps_id, shr.id shr_id, shr.subject_id, shr.credits, shr.grade_code,"
                + " shr.grading_schema_row_id,  shr.apel_application_record_id", em).getResultList();
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> new HigherProtocolModuleSubjectResultDto(resultAsLong(r, 1), resultAsLong(r, 2),
                        resultAsDecimal(r, 3), resultAsString(r, 4), resultAsLong(r, 5), resultAsLong(r, 6)),
                        Collectors.toList())));
    }

    private HigherAssessment calculateModuleProtocolGrade(List<HigherProtocolModuleSubjectResultDto> studentResults) {
        if (studentResults != null) {
            List<HigherAssessment> results = StreamUtil.toMappedList(r -> HigherAssessment.valueOf(r.getGrade().getCode()),
                    studentResults);
            boolean includesDistinctiveResults = results.stream().anyMatch(r -> Boolean.TRUE.equals(r.getIsDistinctive()));
            if (!includesDistinctiveResults) {
                return HigherAssessment.KORGHINDAMINE_A;
            }

            BigDecimal totalCredits = BigDecimal.ZERO;
            BigDecimal wagCredits = BigDecimal.ZERO;
            for (HigherProtocolModuleSubjectResultDto result : studentResults) {
                HigherAssessment resultGrade = HigherAssessment.valueOf(result.getGrade().getCode());
                if (resultGrade.getIsDistinctive()) {
                    totalCredits = totalCredits.add(result.getCredits());
                    wagCredits = wagCredits.add(BigDecimal.valueOf(resultGrade.getMark()).multiply(result.getCredits()));
                }
            }
            BigDecimal wagMark = wagCredits.divide(totalCredits, 0, BigDecimal.ROUND_HALF_UP);
            return HigherAssessment.valueOf(MainClassCode.KORGHINDAMINE.name() + "_" + wagMark.intValue());
        }
        return HigherAssessment.KORGHINDAMINE_0;
    }

}
