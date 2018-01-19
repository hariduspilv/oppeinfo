package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
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
import ee.hitsa.ois.repository.LessonPlanModuleRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.ModuleProtocolGradeUtil;
import ee.hitsa.ois.util.ModuleProtocolUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.ProtocolUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.ModuleProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.ModuleProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.ModuleProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.ModuleProtocolStudentSaveForm;
import ee.hitsa.ois.web.commandobject.ProtocolCalculateCommand;
import ee.hitsa.ois.web.commandobject.ProtocolVdataForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ModuleProtocolOccupationalModuleDto;
import ee.hitsa.ois.web.dto.ModuleProtocolSearchDto;
import ee.hitsa.ois.web.dto.ModuleProtocolStudentSelectDto;
import ee.hitsa.ois.web.dto.ProtocolStudentResultDto;

@Transactional
@Service
public class ModuleProtocolService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String FINAL_EXAM_CODE = "KUTSEMOODUL_L";

    @Autowired
    private EntityManager em;
    @Autowired
    private LessonPlanModuleRepository lessonPlanModuleRepository;

    public Page<ModuleProtocolSearchDto> search(HoisUserDetails user, ModuleProtocolSearchCommand cmd,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from protocol p "
                + "inner join protocol_vdata pvd on pvd.protocol_id = p.id").sort(pageable);

        qb.filter("p.is_final = false");
        qb.filter("p.is_vocational = true");
        qb.requiredCriteria("p.school_id = :schoolId", "schoolId", user.getSchoolId());
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
        Page<ModuleProtocolSearchDto> result = JpaQueryUtil
                .pagingResult(qb, "p.id, p.protocol_nr, p.status_code, p.inserted, p.confirm_date, p.confirmer, pvd.teacher_id", em, pageable)
                .map(r -> {
                    ModuleProtocolSearchDto dto = new ModuleProtocolSearchDto();
                    dto.setId(resultAsLong(r, 0));
                    dto.setProtocolNr(resultAsString(r, 1));
                    dto.setStatus(resultAsString(r, 2));
                    dto.setInserted(resultAsLocalDate(r, 3));
                    dto.setConfirmDate(resultAsLocalDate(r, 4));
                    dto.setConfirmer(resultAsString(r, 5));
                    dto.setCanEdit(Boolean.valueOf(ModuleProtocolUtil.canEdit(user, ProtocolStatus.valueOf(dto.getStatus()), resultAsLong(r, 6))));
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
        addJournalResults(result, occupationalModuleId);
        return result.values();
    }

    private static final String FILTER_BY_OCCUPATIONAL_MODULE = "exists("
            + "select * "
            + "from journal_omodule_theme jot "
            + "join curriculum_version_omodule_theme t on t.id = jot.curriculum_version_omodule_theme_id "
            + "where jot.journal_id = js.journal_id "
            + "and t.curriculum_version_omodule_id = :occupationalModule)";

    private void addJournalResults(Map<Long, ModuleProtocolStudentSelectDto> result, Long occupationalModule) {
        if (!result.isEmpty()) {
            JpaNativeQueryBuilder gradeQb = new JpaNativeQueryBuilder(
                    "from journal_entry_student jes "
                            + "inner join journal_student js on js.id = jes.journal_student_id "
                            + "inner join journal_entry je on je.id = jes.journal_entry_id");
            gradeQb.requiredCriteria("js.student_id in :studentsId", "studentsId", result.keySet());

            gradeQb.requiredCriteria("je.entry_type_code = :entryType", "entryType", JournalEntryType.SISSEKANNE_L);
            
            gradeQb.requiredCriteria(FILTER_BY_OCCUPATIONAL_MODULE, "occupationalModule", occupationalModule);

            List<?> grades = gradeQb.select("js.student_id, jes.grade_code", em).getResultList();
            grades.stream().filter(r -> StringUtils.hasText(resultAsString(r, 1))).forEach(r -> {
                result.get(resultAsLong(r, 0)).getJournalResults().add(resultAsString(r, 1));
            });
        }
    }

    private Map<Long, ModuleProtocolStudentSelectDto> studentsForSelection(HoisUserDetails user,
            Long occupationalModuleId) {
        return studentsForSelection(user, occupationalModuleId, null);
    }
    
    private static final String HAS_NO_POSITIVE_RESULT_IN_THIS_MODULE = "js.student_id not in (select ps.student_id from protocol_student ps "
            + "inner join protocol p on p.id = ps.protocol_id "
            + "inner join protocol_vdata pvd on pvd.protocol_id = p.id "
            + "where p.is_vocational = true and "
            + "grade_code in :positiveGrades and pvd.curriculum_version_omodule_id = cvo.id)";
    
    private static final String NOT_ADDED_TO_PROTOCOL = "js.student_id not in (" + "select ps.student_id from protocol_student ps "
            + "where ps.protocol_id = :protocolId )";

    private Map<Long, ModuleProtocolStudentSelectDto> studentsForSelection(HoisUserDetails user,
            Long occupationalModuleId, Long notInProtocolId) {
        JpaNativeQueryBuilder studentsQb = new JpaNativeQueryBuilder(
                "from journal_omodule_theme jot " + "inner join journal j on j.id = jot.journal_id "
                        + "inner join journal_student js on js.journal_id = j.id "
                        + "inner join student s on s.id = js.student_id " + "inner join person p on p.id = s.person_id "
                        + "inner join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id "
                        + "inner join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id");

        studentsQb.requiredCriteria("j.school_id = :schoolId", "schoolId", user.getSchoolId());
        studentsQb.requiredCriteria("cvot.curriculum_version_omodule_id = :occupationalModuleId",
                "occupationalModuleId", occupationalModuleId);
        studentsQb.requiredCriteria("s.status_code in :activeStatuses", "activeStatuses",
                StudentStatus.STUDENT_STATUS_ACTIVE);

        studentsQb.requiredCriteria(HAS_NO_POSITIVE_RESULT_IN_THIS_MODULE,
                "positiveGrades", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);

        studentsQb.optionalCriteria(NOT_ADDED_TO_PROTOCOL, "protocolId", notInProtocolId);

        List<?> students = studentsQb.select("distinct s.id, p.firstname, p.lastname, p.idcode, s.status_code", em)
                .getResultList();

        return students.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> {
            ModuleProtocolStudentSelectDto dto = new ModuleProtocolStudentSelectDto();
            dto.setStudentId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setIdcode(resultAsString(r, 3));
            dto.setStatus(resultAsString(r, 4));
            return dto;
        }));
    }

    @org.springframework.transaction.annotation.Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    public Protocol create(HoisUserDetails user, ModuleProtocolCreateForm form) {
        Protocol protocol = EntityUtil.bindToEntity(form, new Protocol(), "protocolStudents", "protocolVdata");
        protocol.setIsFinal(Boolean.FALSE);
        protocol.setIsVocational(Boolean.TRUE);
        protocol.setIsVocational(Boolean.TRUE);
        protocol.setStatus(em.getReference(Classifier.class, ProtocolStatus.PROTOKOLL_STAATUS_S.name()));
        protocol.setSchool(em.getReference(School.class, user.getSchoolId()));
        protocol.setProtocolNr(ProtocolUtil.generateProtocolNumber(em));
        protocol.setProtocolStudents(StreamUtil.toMappedList(dto -> {
            ProtocolStudent protocolStudent = EntityUtil.bindToEntity(dto, new ProtocolStudent());
            protocolStudent.setStudent(em.getReference(Student.class, dto.getStudentId()));
            return protocolStudent;
        }, form.getProtocolStudents()));
        ProtocolVdata protocolVdata = protocolVdataFromDto(form.getProtocolVdata());
        protocolVdata.setProtocol(protocol);
        protocol.setProtocolVdata(protocolVdata);
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
                form.getProtocolStudents(), ModuleProtocolStudentSaveForm::getId, dto -> {
                    ProtocolStudent ps = EntityUtil.bindToEntity(dto, new ProtocolStudent(), "student");
                    ps.setStudent(em.getReference(Student.class, dto.getStudentId()));
                    return ps;
                }, (dto, ps) -> {
                    if (ProtocolUtil.gradeChangedButNotRemoved(dto, ps)) {
                        ProtocolUtil.addHistory(ps);
                        Classifier grade = em.getReference(Classifier.class, dto.getGrade());
                        Short mark = getMark(EntityUtil.getCode(grade));
                        ProtocolUtil.gradeStudent(ps, grade, mark);
                    } else if (ProtocolUtil.gradeRemoved(dto, ps)) {
                        ProtocolUtil.addHistory(ps);
                        ProtocolUtil.removeGrade(ps);
                    }
                });
        assertRemovedStudents(storedStudents, protocol.getProtocolStudents());

        return EntityUtil.save(protocol, em);
    }
    
    private static Short getMark(String grade) {
        return Short.valueOf((short) OccupationalGrade.valueOf(grade).getMark());
    }

    private static void assertRemovedStudents(List<ProtocolStudent> oldStudents, List<ProtocolStudent> newStudents) {
        Set<Long> newIds = StreamUtil.toMappedSet(ProtocolStudent::getId, newStudents);
        List<ProtocolStudent> removedStudents = StreamUtil.toFilteredList(oldStudent -> !newIds.contains(oldStudent.getId()), oldStudents);
        for (ProtocolStudent protocolStudent : removedStudents) {
            if(!ModuleProtocolUtil.studentCanBeDeleted(protocolStudent)) {
                throw new ValidationFailedException("moduleProtocol.messages.cantRemoveStudent");
            }
        }
    }

    public Collection<ModuleProtocolStudentSelectDto> otherStudents(HoisUserDetails user, Protocol protocol) {
        Map<Long, ModuleProtocolStudentSelectDto> result = studentsForSelection(user,
                EntityUtil.getId(protocol.getProtocolVdata().getCurriculumVersionOccupationModule()), protocol.getId());
        Long occupationalModule = EntityUtil.getId(protocol.getProtocolVdata().getCurriculumVersionOccupationModule());
        addJournalResults(result, occupationalModule);
        return result.values();
    }

    public ModuleProtocolOccupationalModuleDto occupationModule(HoisUserDetails user,
            Long curriculumVersionOccupationModuleId) {
        ModuleProtocolOccupationalModuleDto dto = new ModuleProtocolOccupationalModuleDto();
        dto.setOccupationModuleStudents(occupationModuleStudents(user, curriculumVersionOccupationModuleId));
        LessonPlanModule lessonPlanModule = lessonPlanModuleRepository
                .findFirstByCurriculumVersionOccupationModuleId(curriculumVersionOccupationModuleId);
        if (lessonPlanModule != null && lessonPlanModule.getTeacher() != null) {
            dto.setTeacher(AutocompleteResult.of(lessonPlanModule.getTeacher()));
        }
        return dto;
    }

    public Protocol addStudents(Protocol protocol, ModuleProtocolSaveForm form) {
        Map<Long, ProtocolStudent> existingStudents = StreamUtil.toMap(it -> EntityUtil.getId(it.getStudent()), protocol.getProtocolStudents());

        for (ModuleProtocolStudentSaveForm moduleProtocolStudentForm : form.getProtocolStudents()) {
            if (existingStudents.containsKey(moduleProtocolStudentForm.getStudentId())) {
                log.warn("student {} is already added to protocol {}", moduleProtocolStudentForm.getStudentId(),
                        protocol.getId());
            } else {
                ProtocolStudent ps = new ProtocolStudent(em.getReference(Student.class, moduleProtocolStudentForm.getStudentId()));
                ps.setProtocol(protocol);
              protocol.getProtocolStudents().add(ps);
                
              /*
               * Code below caused a system error in method ModuleProtocolStudentDto.of() (protocolStudent.protocol was null) 
               */
//                protocol.getProtocolStudents()
//                        .add(new ProtocolStudent(em.getReference(Student.class, moduleProtocolStudentForm.getStudentId())));
            }
        }
        return EntityUtil.save(protocol, em);
    }

    public Protocol confirm(HoisUserDetails user, Protocol protocol, ModuleProtocolSaveForm moduleProtocolSaveForm) {
        protocol.setStatus(em.getReference(Classifier.class, ProtocolStatus.PROTOKOLL_STAATUS_K.name()));
        protocol.setConfirmDate(LocalDate.now());
        protocol.setConfirmer(user.getUsername());
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
        return confirmedProtocol;
    }

    public void delete(HoisUserDetails user, Protocol protocol) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(protocol, em);
    }

    public boolean hasStudentPositiveGradeInModule(Student student, CurriculumVersionOccupationModule module) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from protocol_student ps "
                + "inner join protocol p on p.id = ps.protocol_id "
                + "inner join protocol_vdata pvd on pvd.protocol_id = p.id ");

        qb.filter("p.is_vocational = true");
        qb.requiredCriteria("p.status_code = :status", "status", ProtocolStatus.PROTOKOLL_STAATUS_K);
        qb.requiredCriteria("ps.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.requiredCriteria("pvd.curriculum_version_omodule_id = :curriculumVersionOmoduleId", "curriculumVersionOmoduleId", EntityUtil.getId(module));
        qb.requiredCriteria("ps.grade_code in :positiveGrades", "positiveGrades", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);

        return !qb.select("true", em).getResultList().isEmpty();
    }

    public List<ProtocolStudentResultDto> calculateGrades(ProtocolCalculateCommand command) {
        List<ProtocolStudentResultDto> calculatedResults = new ArrayList<>();
        for(Long protocolStudentId : command.getProtocolStudents()) {
            ProtocolStudent ps = em.getReference(ProtocolStudent.class, protocolStudentId);
            OccupationalGrade grade = ModuleProtocolGradeUtil.calculateGrade(ps);
            calculatedResults.add(new ProtocolStudentResultDto(protocolStudentId, grade));
        }
        return calculatedResults;
    }

}
