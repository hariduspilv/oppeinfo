package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import ee.hitsa.ois.domain.gradingschema.GradingSchemaRow;
import ee.hitsa.ois.web.dto.GradeDto;
import ee.hitsa.ois.web.dto.ModuleProtocolDto;
import ee.hitsa.ois.web.dto.ModuleProtocolOutcomeResultDto;
import ee.hitsa.ois.web.dto.ModuleProtocolStudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.protocol.ProtocolVdata;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.LessonPlanModule;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.report.ModuleProtocolReport;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.ModuleProtocolGradeUtil;
import ee.hitsa.ois.util.ModuleProtocolUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.ModuleProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.ModuleProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.ModuleProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.ProtocolStudentSaveForm;
import ee.hitsa.ois.web.commandobject.ProtocolCalculateCommand;
import ee.hitsa.ois.web.commandobject.ProtocolVdataForm;
import ee.hitsa.ois.web.commandobject.timetable.OtherStudentsSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ModuleProtocolOccupationalModuleDto;
import ee.hitsa.ois.web.dto.ModuleProtocolSearchDto;
import ee.hitsa.ois.web.dto.ModuleProtocolStudentSelectDto;
import ee.hitsa.ois.web.dto.ProtocolStudentResultDto;
import ee.hitsa.ois.web.dto.StudyPeriodDto;
import ee.hitsa.ois.web.dto.StudyYearDto;

@Transactional
@Service
public class ModuleProtocolService extends AbstractProtocolService {

    @Autowired
    private SchoolService schoolService;

    private static final String FINAL_EXAM_CODE = "KUTSEMOODUL_L";

    public Page<ModuleProtocolSearchDto> search(HoisUserDetails user, ModuleProtocolSearchCommand cmd,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from protocol p "
                + "join protocol_vdata pvd on pvd.protocol_id = p.id "
                + "join curriculum_version cv on cv.id = pvd.curriculum_version_id").sort(pageable);

        qb.filter("p.is_final = false");
        qb.filter("p.is_vocational = true");
        qb.requiredCriteria("p.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria(
                    "exists (select cv.curriculum_id from protocol_vdata pvd "
                            + "join curriculum_version cv on cv.id = pvd.curriculum_version_id "
                            + "where pvd.protocol_id = p.id and cv.curriculum_id in (:userCurriculumIds))",
                    "userCurriculumIds", user.getCurriculumIds());
        }

        qb.optionalCriteria(
                "exists (select protocol_id from protocol_vdata pvd where pvd.protocol_id = p.id and pvd.study_year_id = :studyYearId)",
                "studyYearId", cmd.getStudyYear());
        qb.optionalCriteria(
                "exists (select protocol_id from protocol_student ps "
                        + "inner join student s on s.id = ps.student_id "
                        + "where ps.protocol_id = p.id "
                        + "and s.student_group_id = :studentGroupId)",
                "studentGroupId", cmd.getStudentGroup());
        qb.optionalCriteria(
                "exists (select protocol_id "
                + "from protocol_vdata pvd "
                + "where pvd.protocol_id = p.id "
                + "and pvd.curriculum_version_id = :curriculumVersionId)",
                "curriculumVersionId", cmd.getCurriculumVersion());
        qb.optionalCriteria(
                "exists (select protocol_id "
                + "from protocol_vdata pvd "
                + "join curriculum_version_omodule omodule on pvd.curriculum_version_omodule_id = omodule.id "
                + "where pvd.protocol_id = p.id and omodule.curriculum_module_id in :module)",
                "module", cmd.getModule());
        qb.optionalCriteria("p.status_code = :statusCode", "statusCode", cmd.getStatus());
        qb.optionalCriteria("p.protocol_nr = :protocolNr", "protocolNr", cmd.getProtocolNr());
        qb.optionalCriteria("p.inserted >= :from", "from", cmd.getInsertedFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("p.inserted <= :thru", "thru", cmd.getInsertedThru(), DateUtils::lastMomentOfDay);
        qb.optionalCriteria("p.confirm_date >= :from", "from", cmd.getConfirmDateFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("p.confirm_date <= :thru", "thru", cmd.getConfirmDateThru(), DateUtils::lastMomentOfDay);

        if (user.isTeacher()) {
            qb.requiredCriteria("pvd.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        }

        Map<Long, ModuleProtocolSearchDto> dtoById = new HashMap<>();
        Page<ModuleProtocolSearchDto> result = JpaQueryUtil.pagingResult(qb, "p.id, p.protocol_nr, p.status_code, "
                + "p.inserted, p.confirm_date, p.confirmer, cv.curriculum_id, pvd.teacher_id", em, pageable)
                .map(r -> {
                    ModuleProtocolSearchDto dto = new ModuleProtocolSearchDto();
                    dto.setId(resultAsLong(r, 0));
                    dto.setProtocolNr(resultAsString(r, 1));
                    dto.setStatus(resultAsString(r, 2));
                    dto.setInserted(resultAsLocalDate(r, 3));
                    dto.setConfirmDate(resultAsLocalDate(r, 4));
                    dto.setConfirmer(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 5)));
                    dto.setCanEdit(Boolean.valueOf(ModuleProtocolUtil.canEdit(user, ProtocolStatus.valueOf(dto.getStatus()),
                            resultAsLong(r, 6), resultAsLong(r, 7))));
                    dtoById.put(dto.getId(), dto);
                    return dto;
                });

        if (!dtoById.isEmpty()) {
            JpaNativeQueryBuilder pvdQb = new JpaNativeQueryBuilder(
                    "from protocol_vdata pvd " + "left join curriculum_version cv on cv.id = pvd.curriculum_version_id "
                            + "left join curriculum c on c.id = cv.curriculum_id "
                            + "left join curriculum_version_omodule cvo on cvo.id = pvd.curriculum_version_omodule_id "
                            + "left join curriculum_module cm on cm.id = cvo.curriculum_module_id ");
            pvdQb.requiredCriteria("pvd.protocol_id in :protocolIds", "protocolIds", dtoById.keySet());

            List<?> data = pvdQb
                    .select("pvd.protocol_id as pvd_id, cv.id as cv_id, cv.code, c.name_et as c_name_et, c.name_en as c_name_en, "
                            + "cm.id as cm_id, cm.name_et, cm.name_en", em)
                    .getResultList();
            for (Object r : data) {
                ModuleProtocolSearchDto dto = dtoById.get(resultAsLong(r, 0));
                AutocompleteResult curriculumVersion = new AutocompleteResult(resultAsLong(r, 1),
                        CurriculumUtil.versionName(resultAsString(r, 2), resultAsString(r, 3)),
                        CurriculumUtil.versionName(resultAsString(r, 2), resultAsString(r, 4)));
                dto.getCurriculumVersions().add(curriculumVersion);
                AutocompleteResult curriculumVersionOccupationModule = new AutocompleteResult(resultAsLong(r, 5),
                        resultAsString(r, 6), resultAsString(r, 7));
                dto.getCurriculumVersionOccupationModules().add(curriculumVersionOccupationModule);
            }

            JpaNativeQueryBuilder psQb = new JpaNativeQueryBuilder(
                    "from protocol_student ps " + "inner join student s on s.id = ps.student_id "
                            + "inner join student_group sg on sg.id = s.student_group_id");
            psQb.requiredCriteria("ps.protocol_id in :protocolIds", "protocolIds", dtoById.keySet());
            List<?> studentData = psQb.select("distinct ps.protocol_id, sg.code", em).getResultList();
            for (Object r : studentData) {
                ModuleProtocolSearchDto dto = dtoById.get(resultAsLong(r, 0));
                dto.getStudentGroups().add(resultAsString(r, 1));
            }

        }

        return result;
    }

    public ModuleProtocolDto get(HoisUserDetails user, Protocol protocol) {
        ModuleProtocolDto dto = ModuleProtocolDto.of(protocol);
        setStudentOutcomeResults(dto);

        dto.setCanBeEdited(ModuleProtocolUtil.canEdit(user, protocol));
        dto.setCanBeConfirmed(ModuleProtocolUtil.canConfirm(user, protocol));
        dto.setCanBeDeleted(ModuleProtocolUtil.canDelete(user, protocol));
        return dto;
    }

    private void setStudentOutcomeResults(ModuleProtocolDto dto) {
        List<Long> outcomeIds = StreamUtil.toMappedList(o -> o.getId(), dto.getProtocolVdata().getOutcomes());
        List<Long> studentIds = StreamUtil.toMappedList(ps -> ps.getStudentId(), dto.getProtocolStudents());
        if (!outcomeIds.isEmpty() && !studentIds.isEmpty()) {
            Map<Long, List<ModuleProtocolOutcomeResultDto>> studentOutcomeResults = studentOutcomeResults(studentIds,
                    outcomeIds);
            for (ModuleProtocolStudentDto studentDto : dto.getProtocolStudents()) {
                List<ModuleProtocolOutcomeResultDto> studentResults = studentOutcomeResults.get(studentDto.getStudentId());
                if (studentResults != null) {
                    studentDto.setOutcomeResults(studentResults);
                }
            }
        }
    }

    private Map<Long, List<ModuleProtocolOutcomeResultDto>> studentOutcomeResults(List<Long> studentIds,
            List<Long> outcomeIds) {
        List<?> data = em.createNativeQuery("select scmor.student_id, scmor.curriculum_module_outcomes_id, "
                + "scmor.grade_code, scmor.grading_schema_row_id, scmor.grade_date, scmor.grade_inserted "
                + "from student_curriculum_module_outcomes_result scmor "
                + "where scmor.student_id in (:studentIds) and scmor.curriculum_module_outcomes_id in (:outcomeIds)")
                .setParameter("studentIds", studentIds)
                .setParameter("outcomeIds", outcomeIds)
                .getResultList();
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> new ModuleProtocolOutcomeResultDto(resultAsLong(r, 1), resultAsString(r, 2),
                        resultAsLong(r, 3), resultAsLocalDate(r, 4), resultAsLocalDateTime(r, 5)), Collectors.toList())));
    }

    public List<AutocompleteResult> occupationModules(HoisUserDetails user, Long curriculumVersionId) {
        String from = "from curriculum_version_omodule cvo "
                + "inner join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "inner join classifier mcl on mcl.code = cm.module_code "
                + "inner join curriculum_version cv on cv.id = cvo.curriculum_version_id "
                + "inner join curriculum c on c.id = cv.curriculum_id";

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("cm.module_code != :module_code", "module_code", FINAL_EXAM_CODE);
        qb.requiredCriteria("cvo.curriculum_version_id = :curriculumVersionId", "curriculumVersionId",
                curriculumVersionId);
        qb.optionalCriteria(" exists(select id from lesson_plan_module "
               + "where curriculum_version_omodule_id = cvo.id and teacher_id = :teacherId) ", "teacherId", user.getTeacherId());

        String select = "cvo.id, cv.code, cm.name_et, mcl.name_et as mcl_name_et, cm.name_en, mcl.name_en as mcl_name_en";
        List<?> data = qb.select(select, em).getResultList();

        List<AutocompleteResult> results = new ArrayList<>();
        for (Object r : data) {
            results.add(new AutocompleteResult(resultAsLong(r, 0),
                    CurriculumUtil.moduleName(resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 1)),
                    CurriculumUtil.moduleName(resultAsString(r, 4), resultAsString(r, 5), resultAsString(r, 1))));
        }
        return results;
    }

    public Collection<ModuleProtocolStudentSelectDto> occupationModuleStudents(HoisUserDetails user,
            Long occupationalModuleId) {
        Map<Long, ModuleProtocolStudentSelectDto> result = studentsForSelection(user, occupationalModuleId);
        addJournalAndPracticeJournalResults(result, occupationalModuleId);
        return result.values();
    }

    private void addJournalAndPracticeJournalResults(Map<Long, ModuleProtocolStudentSelectDto> result,
            Long occupationalModuleId) {
        if (!result.isEmpty()) {
            CurriculumVersionOccupationModule occupationalModule = em
                    .getReference(CurriculumVersionOccupationModule.class, occupationalModuleId);
            List<?> grades = em.createNativeQuery("select js.student_id, jes.grade_code, jes.grading_schema_row_id "
                    + "from journal_entry_student jes "
                    + "join journal_student js on js.id = jes.journal_student_id "
                    + "join journal_entry je on je.id = jes.journal_entry_id "
                    + "join journal j on je.journal_id = j.id "
                    + "where js.student_id in (:studentIds) and je.entry_type_code = :entryTypeCode "
                    + "and exists (select jot.id from journal_omodule_theme jot "
                        + "join curriculum_version_omodule_theme t on t.id = jot.curriculum_version_omodule_theme_id "
                        + "join curriculum_version_omodule cvo on cvo.id = t.curriculum_version_omodule_id "
                        + "join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                        + "where jot.journal_id = js.journal_id and cm.id = :curriculumModuleId) "
                    + "union all "
                    + "select pj.student_id, pj.grade_code, pj.grading_schema_row_id from practice_journal pj "
                    + "where pj.student_id in (:studentIds) and pj.grade_code is not null "
                    + "and exists (select pjms.id from practice_journal_module_subject pjms "
                        + "join curriculum_version_omodule cvo2 on cvo2.id = pjms.curriculum_version_omodule_id "
                        + "join curriculum_module cm2 on cm2.id = cvo2.curriculum_module_id "
                        + "where pjms.practice_journal_id = pj.id and cm2.id = :curriculumModuleId)")
            .setParameter("studentIds", result.keySet())
            .setParameter("entryTypeCode", JournalEntryType.SISSEKANNE_L.name())
            .setParameter("curriculumModuleId", EntityUtil.getId(occupationalModule.getCurriculumModule()))
            .getResultList();

            grades.stream().filter(r -> StringUtils.hasText(resultAsString(r, 1))).forEach(r -> {
                result.get(resultAsLong(r, 0)).getJournalResults().add(
                        new GradeDto(resultAsString(r, 1), resultAsLong(r, 2)));
            });
        }
    }
    
    private static final String HAS_NO_POSITIVE_RESULT_IN_THIS_MODULE = "s.id not in (select ps.student_id from protocol_student ps "
            + "inner join protocol p on p.id = ps.protocol_id "
            + "inner join protocol_vdata pvd on pvd.protocol_id = p.id "
            + "where p.is_vocational = true and (grade_code is null or grade_code in (:positiveGrades)) and pvd.curriculum_version_omodule_id = cvo.id "
            + "union all "
            + "select svr.student_id from student_vocational_result svr "
            + "where svr.grade_code in (:positiveGrades) and (svr.curriculum_version_omodule_id = cvo.id or cvo.id = any(svr.arr_modules)))";

    private Map<Long, ModuleProtocolStudentSelectDto> studentsForSelection(HoisUserDetails user, Long occupationalModuleId) {
        JpaNativeQueryBuilder studentsQb = new JpaNativeQueryBuilder(
                "from student s " +
                "left join student_group sg on sg.id = s.student_group_id " + 
                "join person p on p.id = s.person_id " + 
                "join curriculum_version cv on s.curriculum_version_id = cv.id " + 
                "join curriculum_version_omodule cvo on cv.id = cvo.curriculum_version_id");

        studentsQb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        studentsQb.requiredCriteria("cvo.id = :occupationalModuleId",
                "occupationalModuleId", occupationalModuleId);
        studentsQb.requiredCriteria("s.status_code in :activeStatuses", "activeStatuses",
                StudentStatus.STUDENT_STATUS_ACTIVE);

        studentsQb.requiredCriteria(HAS_NO_POSITIVE_RESULT_IN_THIS_MODULE,
                "positiveGrades", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);
        

        studentsQb.sort("p.firstname, p.lastname");
        List<?> students = studentsQb.select("distinct s.id, p.firstname, p.lastname, p.idcode, " +
                "s.status_code, s.type_code, sg.code", em) .getResultList();

        return students.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> {
            ModuleProtocolStudentSelectDto dto = new ModuleProtocolStudentSelectDto();
            dto.setStudentId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullnameTypeSpecific(resultAsString(r, 1), resultAsString(r, 2),
                    resultAsString(r, 5)));
            dto.setIdcode(resultAsString(r, 3));
            dto.setStatus(resultAsString(r, 4));
            dto.setStudentGroup(resultAsString(r, 6));
            return dto;
        }));
    }

    @org.springframework.transaction.annotation.Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    public Protocol create(HoisUserDetails user, ModuleProtocolCreateForm form) {
        Protocol protocol = EntityUtil.bindToEntity(form, new Protocol(), "protocolStudents", "protocolVdata");
        protocol.setIsFinal(Boolean.FALSE);
        protocol.setIsVocational(Boolean.TRUE);
        protocol.setStatus(em.getReference(Classifier.class, ProtocolStatus.PROTOKOLL_STAATUS_S.name()));
        protocol.setSchool(em.getReference(School.class, user.getSchoolId()));
        protocol.setProtocolNr(generateProtocolNumber());
        protocol.setProtocolStudents(StreamUtil.toMappedList(dto -> {
            ProtocolStudent protocolStudent = EntityUtil.bindToEntity(dto, new ProtocolStudent());
            protocolStudent.setStudent(em.getReference(Student.class, dto.getStudentId()));
            return protocolStudent;
        }, form.getProtocolStudents()));
        ProtocolVdata protocolVdata = protocolVdataFromDto(form.getProtocolVdata());
        protocolVdata.setProtocol(protocol);
        protocol.setProtocolVdata(protocolVdata);

        SchoolService.SchoolType type = schoolService.schoolType(user.getSchoolId());
        ModuleProtocolUtil.assertIsSchoolAdminOrLeadingTeacherOrTeacherResponsible(user, protocolVdata, type.isHigher());
        return EntityUtil.save(protocol, em);
    }

    private ProtocolVdata protocolVdataFromDto(ProtocolVdataForm vdata) {
        ProtocolVdata protocolVdata = new ProtocolVdata();
        protocolVdata.setCurriculumVersion(em.getReference(CurriculumVersion.class, vdata.getCurriculumVersion()));
        protocolVdata.setCurriculumVersionOccupationModule(
                em.getReference(CurriculumVersionOccupationModule.class, vdata.getCurriculumVersionOccupationModule()));
        protocolVdata.setStudyYear(em.getReference(StudyYear.class, vdata.getStudyYear()));
        protocolVdata.setTeacher(em.getReference(Teacher.class, vdata.getTeacher()));
        return protocolVdata;
    }

    public Protocol save(Protocol protocol, ModuleProtocolSaveForm form) {
        List<ProtocolStudent> storedStudents = new ArrayList<>(protocol.getProtocolStudents());
        EntityUtil.bindEntityCollection(protocol.getProtocolStudents(), ProtocolStudent::getId,
                // no protocol students created here
                form.getProtocolStudents(), ProtocolStudentSaveForm::getId, null, (dto, ps) -> {
                    if (gradeChangedButNotRemoved(dto, ps)) {
                        assertHasAddInfoIfProtocolConfirmed(dto, protocol);
                        addHistory(ps);
                        Classifier grade = em.getReference(Classifier.class, dto.getGrade().getCode());
                        Short mark = getMark(EntityUtil.getCode(grade));
                        GradingSchemaRow gradingSchemaRow = EntityUtil.getOptionalOne(GradingSchemaRow.class,
                                dto.getGrade().getGradingSchemaRowId(), em);
                        gradeStudent(ps, grade, mark, Boolean.FALSE, gradingSchemaRow, LocalDate.now());
                        ps.setAddInfo(dto.getAddInfo());
                    } else if (gradeRemoved(dto, ps)) {
                        assertHasAddInfoIfProtocolConfirmed(dto, protocol);
                        addHistory(ps);
                        removeGrade(ps);
                    }
                });

        return EntityUtil.save(protocol, em);
    }
    
    private static Short getMark(String grade) {
        return Short.valueOf((short) OccupationalGrade.valueOf(grade).getMark());
    }

    private static final String NOT_ADDED_TO_PROTOCOL = "s.id not in (select ps.student_id from protocol_student ps "
            + "where ps.protocol_id = :protocolId)";
    
    public Page<ModuleProtocolStudentSelectDto> otherStudents(HoisUserDetails user, Protocol protocol,
            OtherStudentsSearchCommand command, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s " +
                " join person p on s.person_id = p.id " + 
                " join student_group as sg on s.student_group_id = sg.id " + 
                " join curriculum_version cv on s.curriculum_version_id = cv.id " +
                " join curriculum c on cv.curriculum_id = c.id").sort(pageable);
        
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("s.status_code in (:activeStatuses)", "activeStatuses", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.optionalCriteria("c.is_higher = :is_higher", "is_higher", Boolean.FALSE);
        qb.optionalContains("p.firstname || ' ' || p.lastname", "name", command.getStudentName());

        qb.filter("s.id not in (select ps.student_id from protocol_student ps "
            + "join protocol p on p.id = ps.protocol_id "
            + "join protocol_vdata pvd on pvd.protocol_id = p.id "
            + "where p.is_vocational = true and (grade_code is null or "
            + "grade_code in (:positiveGrades)) "
            + "and pvd.curriculum_version_omodule_id = :moduleId "
            + "union all "
            + "select svr.student_id from student_vocational_result svr "
            + "where svr.grade_code in (:positiveGrades) "
            + "and (svr.curriculum_version_omodule_id = :moduleId or :moduleId = any(svr.arr_modules)))");
        qb.parameter("moduleId", EntityUtil.getId(protocol.getProtocolVdata().getCurriculumVersionOccupationModule()));
        qb.parameter("positiveGrades", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);

        qb.requiredCriteria(NOT_ADDED_TO_PROTOCOL, "protocolId", EntityUtil.getId(protocol));

        return JpaQueryUtil
            .pagingResult(qb, "s.id s_id, p.firstname, p.lastname, p.idcode, sg.code sg_code, cv.id cv_id, cv.code cv_code, c.name_et, c.name_en, s.type_code", em, pageable)
            .map(r -> {
                ModuleProtocolStudentSelectDto dto = new ModuleProtocolStudentSelectDto();
                dto.setStudentId(resultAsLong(r, 0));
                dto.setFullname(PersonUtil.fullnameTypeSpecific(resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 9)));
                dto.setIdcode(resultAsString(r, 3));
                dto.setStudentGroup(resultAsString(r, 4));
                dto.setCurriculum(new AutocompleteResult(resultAsLong(r, 5),
                        CurriculumUtil.curriculumName(resultAsString(r, 6), resultAsString(r, 7)),
                        CurriculumUtil.curriculumName(resultAsString(r, 6), resultAsString(r, 8))));
                return dto;
            });
    }

    public ModuleProtocolOccupationalModuleDto occupationModule(HoisUserDetails user, Long studyYearId,
            Long curriculumVersionOccupationModuleId) {
        ModuleProtocolOccupationalModuleDto dto = new ModuleProtocolOccupationalModuleDto();
        dto.setOccupationModuleStudents(occupationModuleStudents(user, curriculumVersionOccupationModuleId));
        dto.setTeacher(lessonPlanModuleTeacher(studyYearId, curriculumVersionOccupationModuleId));
        return dto;
    }

    public Protocol addStudents(Protocol protocol, ModuleProtocolSaveForm form) {
        Map<Long, ProtocolStudent> existingStudents = StreamUtil.toMap(it -> EntityUtil.getId(it.getStudent()), protocol.getProtocolStudents());

        for (ProtocolStudentSaveForm moduleProtocolStudentForm : form.getProtocolStudents()) {
            if (existingStudents.containsKey(moduleProtocolStudentForm.getStudentId())) {
                log.warn("student {} is already added to protocol {}", moduleProtocolStudentForm.getStudentId(),
                        protocol.getId());
            } else {
                ProtocolStudent ps = new ProtocolStudent();
                ps.setStudent(em.getReference(Student.class, moduleProtocolStudentForm.getStudentId()));
                ps.setProtocol(protocol);
                protocol.getProtocolStudents().add(ps);
            }
        }
        return EntityUtil.save(protocol, em);
    }

    public Protocol confirm(HoisUserDetails user, Protocol protocol, ModuleProtocolSaveForm moduleProtocolSaveForm) {
        setConfirmation(user, protocol);
        Protocol confirmedProtocol = null;
        if (moduleProtocolSaveForm != null) {
            confirmedProtocol = save(protocol, moduleProtocolSaveForm);
        } else {
            confirmedProtocol = EntityUtil.save(protocol, em);
        }

        for (ProtocolStudent protocolStudent : confirmedProtocol.getProtocolStudents()) {
            if (protocolStudent.getGrade() == null) {
                throw new ValidationFailedException("moduleProtocol.messages.gradeNotSelectedForAllStudents");
            }
        }
        sendStudentResultMessages(confirmedProtocol);
        return confirmedProtocol;
    }

    public boolean hasStudentPositiveGradeInModule(Student student, CurriculumVersionOccupationModule module) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from protocol_student ps "
                + "inner join protocol p on p.id = ps.protocol_id "
                + "inner join protocol_vdata pvd on pvd.protocol_id = p.id ");

        qb.filter("p.is_vocational = true");
        qb.requiredCriteria("p.status_code = :status", "status", ProtocolStatus.PROTOKOLL_STAATUS_K);
        qb.requiredCriteria("ps.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.requiredCriteria("pvd.curriculum_version_omodule_id = :curriculumVersionOmoduleId",
                "curriculumVersionOmoduleId", EntityUtil.getId(module));
        qb.requiredCriteria("ps.grade_code in :positiveGrades", "positiveGrades",
                OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);

        return !qb.select("true", em).getResultList().isEmpty();
    }

    public List<ProtocolStudentResultDto> calculateGrades(ProtocolCalculateCommand command) {
        List<ProtocolStudent> activeStudents = getActiveSelectedStudents(command.getProtocolStudents());
        List<ProtocolStudentResultDto> calculatedResults = new ArrayList<>();
        for (ProtocolStudent ps : activeStudents) {
            OccupationalGrade grade = ModuleProtocolGradeUtil.calculateGrade(ps);
            calculatedResults.add(new ProtocolStudentResultDto(EntityUtil.getId(ps), grade));
        }
        return calculatedResults;
    }

    private List<ProtocolStudent> getActiveSelectedStudents(Set<Long> protocolStudents) {
        return em.createQuery("select ps from ProtocolStudent ps where ps.id in (?1) and ps.student.status.code in (?2)",
                        ProtocolStudent.class)
                .setParameter(1, protocolStudents).setParameter(2, StudentStatus.STUDENT_STATUS_ACTIVE).getResultList();
    }

    public ModuleProtocolReport moduleProtocolReport(Protocol protocol) {
        School school = protocol.getSchool();
        Boolean isHigherSchool = Boolean.valueOf(schoolService.schoolType(EntityUtil.getId(school)).isHigher());
        return new ModuleProtocolReport(protocol, isHigherSchool);
    }

    public Page<TeacherModuleMinimumDto> availableTeacherModules(HoisUserDetails user, Long studyYear, Pageable pageable) {
        String from = "from curriculum_version_omodule cvo "
                + "inner join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "inner join classifier mcl on mcl.code = cm.module_code "
                + "inner join curriculum_version cv on cv.id = cvo.curriculum_version_id "
                + "inner join curriculum c on c.id = cv.curriculum_id";

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("cm.module_code != :module_code", "module_code", FINAL_EXAM_CODE);
        qb.requiredCriteria(" exists(select lpm.id from lesson_plan_module lpm join lesson_plan lp on lp.id = lpm.lesson_plan_id "
               + "where lpm.curriculum_version_omodule_id = cvo.id and lpm.teacher_id = :teacherId and lp.study_year_id = :studyYear) ", "teacherId", user.getTeacherId());
        qb.parameter("studyYear", studyYear);

        return JpaQueryUtil.pagingResult(qb, "cvo.id", em, pageable).map(r -> {
            return TeacherModuleMinimumDto.of(em.getReference(CurriculumVersionOccupationModule.class, resultAsLong(r, 0)));
        });
    }
    
    public LessonPlanHistoryDto getModuleLessonPlanHistory(CurriculumVersionOccupationModule module) {
        return LessonPlanHistoryDto.of(module);
    }
    
    public static class TeacherModuleMinimumDto {
        
        private AutocompleteResult module;
        private AutocompleteResult curriculumVersion;
        
        public static TeacherModuleMinimumDto of(CurriculumVersionOccupationModule module) {
            TeacherModuleMinimumDto dto = new TeacherModuleMinimumDto();
            dto.setModule(AutocompleteResult.of(module));
            dto.setCurriculumVersion(AutocompleteResult.of(module.getCurriculumVersion()));
            return dto;
        }

        public AutocompleteResult getModule() {
            return module;
        }

        public void setModule(AutocompleteResult module) {
            this.module = module;
        }

        public AutocompleteResult getCurriculumVersion() {
            return curriculumVersion;
        }

        public void setCurriculumVersion(AutocompleteResult curriculumVersion) {
            this.curriculumVersion = curriculumVersion;
        }
    }
    
    public static class LessonPlanHistoryDto {
        
        private AutocompleteResult module;
        private BigDecimal credits;
        private AutocompleteResult curriculumVersion;
        private List<LessonPlanModuleInfo> lessonPlanModules;
        
        public static LessonPlanHistoryDto of(CurriculumVersionOccupationModule module) {
            LessonPlanHistoryDto dto = new LessonPlanHistoryDto();
            dto.setModule(new AutocompleteResult(module.getId(), module.getCurriculumModule().getNameEt(), module.getCurriculumModule().getNameEn()));
            dto.setCredits(module.getCurriculumModule().getCredits());
            dto.setCurriculumVersion(AutocompleteResult.of(module.getCurriculumVersion()));
            
            dto.setLessonPlanModules(module.getLessonPlanModules().stream()
                    .filter(lpm -> !lpm.getJournalOccupationModuleThemes().isEmpty())
                    .map(lpm -> LessonPlanModuleInfo.of(lpm))
                    .sorted(new Comparator<LessonPlanModuleInfo>() {

                        @Override
                        public int compare(LessonPlanModuleInfo o1, LessonPlanModuleInfo o2) {
                            return o1.getStudyYear().getEndDate().compareTo(o2.getStudyYear().getEndDate());
                        }
                    })
                    .collect(Collectors.toList()));
            

            return dto;
        }

        public AutocompleteResult getModule() {
            return module;
        }

        public void setModule(AutocompleteResult module) {
            this.module = module;
        }

        public BigDecimal getCredits() {
            return credits;
        }

        public void setCredits(BigDecimal credits) {
            this.credits = credits;
        }

        public AutocompleteResult getCurriculumVersion() {
            return curriculumVersion;
        }

        public void setCurriculumVersion(AutocompleteResult curriculumVersion) {
            this.curriculumVersion = curriculumVersion;
        }
        
        public List<LessonPlanModuleInfo> getLessonPlanModules() {
            return lessonPlanModules;
        }

        public void setLessonPlanModules(List<LessonPlanModuleInfo> lessonPlanModules) {
            this.lessonPlanModules = lessonPlanModules;
        }

        public static class LessonPlanModuleInfo {
            
            private StudyYearDto studyYear;
            private AutocompleteResult representative;
            private List<AutocompleteResult> journals;
            private Map<Long, List<String>> journalTeachers; 
            private List<StudyPeriodDto> periods;
            /** Journal - Period - Hours */
            private Map<Long, Map<Long, Integer>> mappedHours;
            
            @SuppressWarnings("boxing")
            public static LessonPlanModuleInfo of(LessonPlanModule module) {
                LessonPlanModuleInfo dto = new LessonPlanModuleInfo();
                dto.setStudyYear(StudyYearDto.of(module.getLessonPlan().getStudyYear()));
                dto.setRepresentative(module.getTeacher() != null ? AutocompleteResult.of(module.getTeacher()) : null);
                
                Set<StudyPeriod> periods = module.getLessonPlan().getStudyYear().getStudyPeriods();
                List<AutocompleteResult> journals = new ArrayList<>();
                Map<Long, List<String>> journalTeachers = new HashMap<>();
                Map<Long, Map<Long, Integer>> mappedHours = new HashMap<>();
                
                //module.getLessonPlan().getStudentGroup(); // XXX What to do with it?
                
                module.getJournalOccupationModuleThemes().stream().map(r -> r.getJournal()).distinct().forEach(journal -> {
                    journals.add(AutocompleteResult.of(journal));
                    journalTeachers.put(journal.getId(), journal.getJournalTeachers().stream().map(r -> r.getTeacher().getPerson().getFullname()).sorted().collect(Collectors.toList()));

                    HashSet<StudyPeriod> leftovers = new HashSet<>(periods);
                    Map<Long, Integer> mappedPeriodHours = new HashMap<>();
                    journal.getJournalCapacities().forEach(jc -> {
                        if (!mappedPeriodHours.containsKey(jc.getStudyPeriod().getId())) {
                            leftovers.remove(jc.getStudyPeriod());
                            mappedPeriodHours.put(jc.getStudyPeriod().getId(), 0);
                        }
                        mappedPeriodHours.put(jc.getStudyPeriod().getId(), mappedPeriodHours.get(jc.getStudyPeriod().getId()) + jc.getHours());
                    });
                    leftovers.forEach(p -> mappedPeriodHours.put(p.getId(), 0));
                    mappedHours.put(journal.getId(), mappedPeriodHours);
                });
                
                dto.setPeriods(periods.stream().map(r -> StudyPeriodDto.of(r)).sorted(Comparator.comparing(r -> r.getEndDate())).collect(Collectors.toList()));
                dto.setJournals(journals.stream().sorted(Comparator.comparing(r -> r.getNameEt(), String.CASE_INSENSITIVE_ORDER)).collect(Collectors.toList()));
                dto.setJournalTeachers(journalTeachers);
                dto.setMappedHours(mappedHours);
                
                return dto;
            }
            
            public StudyYearDto getStudyYear() {
                return studyYear;
            }
            
            public void setStudyYear(StudyYearDto studyYear) {
                this.studyYear = studyYear;
            }

            public AutocompleteResult getRepresentative() {
                return representative;
            }

            public void setRepresentative(AutocompleteResult representative) {
                this.representative = representative;
            }

            public List<AutocompleteResult> getJournals() {
                return journals;
            }

            public void setJournals(List<AutocompleteResult> journals) {
                this.journals = journals;
            }

            public Map<Long, List<String>> getJournalTeachers() {
                return journalTeachers;
            }

            public void setJournalTeachers(Map<Long, List<String>> journalTeachers) {
                this.journalTeachers = journalTeachers;
            }

            public List<StudyPeriodDto> getPeriods() {
                return periods;
            }

            public void setPeriods(List<StudyPeriodDto> periods) {
                this.periods = periods;
            }

            public Map<Long, Map<Long, Integer>> getMappedHours() {
                return mappedHours;
            }

            public void setMappedHours(Map<Long, Map<Long, Integer>> mappedHours) {
                this.mappedHours = mappedHours;
            }
        }
    }
}
