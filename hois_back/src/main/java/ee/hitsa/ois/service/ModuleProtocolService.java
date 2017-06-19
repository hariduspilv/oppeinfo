package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.protocol.ProtocolVdata;
import ee.hitsa.ois.domain.timetable.LessonPlanModule;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumVersionOccupationModuleRepository;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.repository.LessonPlanModuleRepository;
import ee.hitsa.ois.repository.ProtocolRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.repository.StudyYearRepository;
import ee.hitsa.ois.repository.TeacherRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.ModuleProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.ModuleProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.ModuleProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.ModuleProtocolStudentSaveForm;
import ee.hitsa.ois.web.commandobject.ProtocolVdataForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ModuleProtocolOccupationalModuleDto;
import ee.hitsa.ois.web.dto.ModuleProtocolSearchDto;
import ee.hitsa.ois.web.dto.ModuleProtocolStudentSelectDto;

@Transactional
@Service
public class ModuleProtocolService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static DateTimeFormatter shortYearFormatter = DateTimeFormatter.ofPattern("YY");

    @Autowired
    private EntityManager em;
    @Autowired
    private ProtocolRepository protocolRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CurriculumVersionRepository curriculumVersionRepository;
    @Autowired
    private CurriculumVersionOccupationModuleRepository curriculumVersionOccupationModuleRepository;
    @Autowired
    private StudyYearRepository studyYearRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private LessonPlanModuleRepository lessonPlanModuleRepository;

    public Page<ModuleProtocolSearchDto> search(HoisUserDetails user, ModuleProtocolSearchCommand cmd,
            Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from protocol p").sort(pageable);

        qb.requiredCriteria("p.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria(
                "exists (select protocol_id from protocol_vdata pvd where pvd.study_year_id = :studyYearId)",
                "studyYearId", cmd.getStudyYear());
        qb.optionalCriteria(
                "exists (select protocol_id from protocol_student ps "
                        + "inner join student s on s.id = ps.student_id where s.student_group_id = :studentGroupId)",
                "studentGroupId", cmd.getStudentGroup());
        qb.optionalCriteria(
                "exists (select protocol_id from protocol_vdata pvd where pvd.curriculum_version_id = :curriculumVersionId)",
                "curriculumVersionId", cmd.getCurriculumVersion());
        qb.optionalCriteria(
                "exists (select protocol_id from protocol_vdata pvd where pvd.curriculum_version_omodule_id in :module)",
                "module", cmd.getModule());
        qb.optionalCriteria("p.status_code = :statusCode", "statusCode", cmd.getStatus());
        qb.optionalCriteria("p.protocol_nr = :protocolNr", "protocolNr", cmd.getProtocolNr());
        qb.optionalCriteria("p.inserted > :from", "from", cmd.getInsertedFrom());
        qb.optionalCriteria("p.inserted < :thru", "thru", cmd.getInsertedThru());
        qb.optionalCriteria("p.confirm_date > :from", "from", cmd.getConfirmDateFrom());
        qb.optionalCriteria("p.confirm_date < :thru", "thru", cmd.getConfirmDateThru());

        Map<Long, ModuleProtocolSearchDto> dtoById = new HashMap<>();
        Page<ModuleProtocolSearchDto> result = JpaQueryUtil
                .pagingResult(qb, "p.id, p.protocol_nr, p.status_code, p.inserted, p.confirm_date", em, pageable)
                .map(r -> {
                    ModuleProtocolSearchDto dto = new ModuleProtocolSearchDto();
                    dto.setId(resultAsLong(r, 0));
                    dto.setProtocolNr(resultAsString(r, 1));
                    dto.setStatus(resultAsString(r, 2));
                    dto.setInserted(resultAsLocalDate(r, 3));
                    dto.setConfirmDate(resultAsLocalDate(r, 4));
                    dtoById.put(dto.getId(), dto);
                    return dto;
                });

        if (!dtoById.isEmpty()) {
            JpaQueryUtil.NativeQueryBuilder pvdQb = new JpaQueryUtil.NativeQueryBuilder(
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

            JpaQueryUtil.NativeQueryBuilder psQb = new JpaQueryUtil.NativeQueryBuilder(
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
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from curriculum_version_omodule cvo "
                + "inner join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "inner join classifier mcl on mcl.code = cm.module_code "
                + "inner join curriculum_version cv on cv.id = cvo.curriculum_version_id "
                + "inner join curriculum c on c.id = cv.curriculum_id");

        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("cvo.curriculum_version_id = :curriculumVersionId", "curriculumVersionId",
                curriculumVersionId);

        List<AutocompleteResult> results = new ArrayList<>();
        List<?> data = qb
                .select("cvo.id, cv.code, cm.name_et, mcl.name_et as mcl_name_et, cm.name_en, mcl.name_en as mcl_name_en",
                        em)
                .getResultList();
        for (Object r : data) {
            results.add(new AutocompleteResult(resultAsLong(r, 0),
                    resultAsString(r, 2) + " - " + resultAsString(r, 3) + ", " + resultAsString(r, 1),
                    resultAsString(r, 4) + " - " + resultAsString(r, 5) + ", " + resultAsString(r, 1)));
        }
        return results;
    }

    public Collection<ModuleProtocolStudentSelectDto> occupationModuleStudents(HoisUserDetails user,
            Long occupationalModuleId) {
        Map<Long, ModuleProtocolStudentSelectDto> result = studentsForSelection(user, occupationalModuleId);
        addJournalResults(result);
        return result.values();
    }

    private void addJournalResults(Map<Long, ModuleProtocolStudentSelectDto> result) {
        if (!result.isEmpty()) {
            JpaQueryUtil.NativeQueryBuilder gradeQb = new JpaQueryUtil.NativeQueryBuilder(
                    "from journal_entry_student jes "
                            + "inner join journal_student js on js.id = jes.journal_student_id "
                            + "inner join journal_entry je on je.id = jes.journal_entry_id");
            gradeQb.requiredCriteria("js.student_id in :studentsId", "studentsId", result.keySet());
            // Mooduliga seotud päevikute lõpptulemused
            gradeQb.requiredCriteria("je.entry_type_code = :entryType", "entryType", JournalEntryType.SISSEKANNE_L);

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

    private Map<Long, ModuleProtocolStudentSelectDto> studentsForSelection(HoisUserDetails user,
            Long occupationalModuleId, Long notInProtocolId) {
        JpaQueryUtil.NativeQueryBuilder studentsQb = new JpaQueryUtil.NativeQueryBuilder(
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

        // kellel puudub positiivne õppetulemus vastavas moodulis
        studentsQb.requiredCriteria(
                "js.student_id not in (" + "select ps.student_id from protocol_student ps "
                        + "inner join protocol p on p.id = ps.protocol_id "
                        + "inner join protocol_vdata pvd on pvd.protocol_id = p.id "
                        + "where grade_code in :positiveGrades and " + "pvd.curriculum_version_omodule_id = cvo.id)",
                "positiveGrades", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);

        // kelle andmeid ei ole veel protokollile lisatud
        if (notInProtocolId != null) {
            studentsQb.requiredCriteria("js.student_id not in (" + "select ps.student_id from protocol_student ps "
                    + "where ps.protocol_id = :protocolId )", "protocolId", notInProtocolId);
        }

        List<?> students = studentsQb.select("distinct s.id, p.firstname, p.lastname, p.idcode, s.status_code", em)
                .getResultList();
        Map<Long, ModuleProtocolStudentSelectDto> result = students.stream()
                .collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> {
                    ModuleProtocolStudentSelectDto dto = new ModuleProtocolStudentSelectDto();
                    dto.setStudentId(resultAsLong(r, 0));
                    dto.setFullname(resultAsString(r, 1) + " " + resultAsString(r, 2));
                    dto.setIdcode(resultAsString(r, 3));
                    dto.setStatus(resultAsString(r, 4));
                    return dto;
                }));
        return result;
    }

    @org.springframework.transaction.annotation.Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    public Protocol create(HoisUserDetails user, ModuleProtocolCreateForm form) {
        Protocol protocol = EntityUtil.bindToEntity(form, new Protocol(), "protocolStudents", "protocolVdata");
        protocol.setIsVocational(Boolean.TRUE);
        protocol.setStatus(classifierRepository.getOne(ProtocolStatus.PROTOKOLL_STAATUS_S.name()));
        protocol.setSchool(schoolRepository.getOne(user.getSchoolId()));
        protocol.setProtocolNr(generateProtocolNumber());
        protocol.setProtocolStudents(form.getProtocolStudents().stream().map(dto -> {
            ProtocolStudent protocolStudent = EntityUtil.bindToEntity(dto, new ProtocolStudent());
            protocolStudent.setStudent(studentRepository.getOne(dto.getStudentId()));
            return protocolStudent;
        }).collect(Collectors.toList()));
        ProtocolVdata protocolVdata = protocolVdataFromDto(form.getProtocolVdata());
        protocolVdata.setProtocol(protocol);
        protocol.setProtocolVdata(protocolVdata);
        return protocolRepository.save(protocol);
    }

    // TODO: proper per school protocol number generation
    private String generateProtocolNumber() {
        Query q = em.createNativeQuery("select nextval('public.protocol_id_seq')");
        return LocalDate.now().format(shortYearFormatter) + String.format("%04d", q.getSingleResult());
    }

    private ProtocolVdata protocolVdataFromDto(ProtocolVdataForm vdata) {
        ProtocolVdata protocolVdata = new ProtocolVdata();
        protocolVdata.setCurriculumVersion(curriculumVersionRepository.getOne(vdata.getCurriculumVersion()));
        protocolVdata.setCurriculumVersionOccupationModule(
                curriculumVersionOccupationModuleRepository.getOne(vdata.getCurriculumVersionOccupationModule()));
        protocolVdata.setStudyYear(studyYearRepository.getOne(vdata.getStudyYear()));
        protocolVdata.setTeacher(teacherRepository.getOne(vdata.getTeacher()));
        return protocolVdata;
    }

    public Protocol save(Protocol protocol, ModuleProtocolSaveForm form) {
        List<ProtocolStudent> storedStudents = new ArrayList<>(protocol.getProtocolStudents());
        EntityUtil.bindEntityCollection(protocol.getProtocolStudents(), ProtocolStudent::getId,
                form.getProtocolStudents(), ModuleProtocolStudentSaveForm::getId, dto -> {
                    ProtocolStudent ps = EntityUtil.bindToEntity(dto, new ProtocolStudent(), "student");
                    ps.setStudent(studentRepository.getOne(dto.getStudentId()));
                    return ps;
                }, (dto, ps) -> {
                    if (dto.getGrade() != null && !dto.getGrade().equals(EntityUtil.getNullableCode(ps.getGrade()))) {
                        ps.setGrade(classifierRepository.getOne(dto.getGrade()));
                    } else if (dto.getGrade() == null) {
                        ps.setGrade(null);
                    }
                });

        assertRemovedStudents(storedStudents, protocol.getProtocolStudents());

        return protocolRepository.save(protocol);
    }

    private static void assertRemovedStudents(List<ProtocolStudent> oldStudents, List<ProtocolStudent> newStudents) {
        List<Long> newIds = StreamUtil.toMappedList(ProtocolStudent::getId, newStudents);
        List<ProtocolStudent> removedStudents = oldStudents.stream()
                .filter(oldStudent -> !newIds.contains(oldStudent.getId())).collect(Collectors.toList());
        for (ProtocolStudent protocolStudent : removedStudents) {
            if (!StudentStatus.OPPURSTAATUS_K.name()
                    .equals(EntityUtil.getCode(protocolStudent.getStudent().getStatus()))) {
                throw new ValidationFailedException("moduleProtocol.messages.cantRemoveNonDismissedStudent");
            } else if (protocolStudent.getGrade() != null) {
                throw new ValidationFailedException("moduleProtocol.messages.cantRemoveGradedStudent");
            }
        }
    }

    public Collection<ModuleProtocolStudentSelectDto> otherStudents(HoisUserDetails user, Protocol protocol) {
        Map<Long, ModuleProtocolStudentSelectDto> result = studentsForSelection(user,
                EntityUtil.getId(protocol.getProtocolVdata().getCurriculumVersionOccupationModule()), protocol.getId());
        addJournalResults(result);
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
        Map<Long, ProtocolStudent> existingStudents = protocol.getProtocolStudents().stream()
                .collect(Collectors.toMap(it -> EntityUtil.getId(it.getStudent()), it -> it));

        for (ModuleProtocolStudentSaveForm moduleProtocolStudentForm : form.getProtocolStudents()) {
            if (existingStudents.containsKey(moduleProtocolStudentForm.getStudentId())) {
                log.warn("student {} is already added to protocol {}", moduleProtocolStudentForm.getStudentId(),
                        protocol.getId());
            } else {
                protocol.getProtocolStudents()
                        .add(new ProtocolStudent(studentRepository.getOne(moduleProtocolStudentForm.getStudentId())));
            }
        }
        return protocolRepository.save(protocol);
    }

    public Protocol confirm(Protocol protocol, ModuleProtocolSaveForm moduleProtocolSaveForm) {
        protocol.setStatus(classifierRepository.getOne(ProtocolStatus.PROTOKOLL_STAATUS_K.name()));
        Protocol confirmedProtocol = save(protocol, moduleProtocolSaveForm);
        for (ProtocolStudent protocolStudent : confirmedProtocol.getProtocolStudents()) {
            if (protocolStudent.getGrade() == null) {
                throw new ValidationFailedException("moduleProtocol.messages.gradeNotSelectedForAllStudents");
            }
        }
        return confirmedProtocol;
    }

    public void delete(Protocol protocol) {
        EntityUtil.deleteEntity(protocolRepository, protocol);
    }

}
