package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.protocol.ProtocolVdata;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumVersionOccupationModuleRepository;
import ee.hitsa.ois.repository.CurriculumVersionRepository;
import ee.hitsa.ois.repository.ProtocolRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.repository.StudyYearRepository;
import ee.hitsa.ois.repository.TeacherRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.ModuleProtocolCommand;
import ee.hitsa.ois.web.commandobject.ModuleProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.ModuleProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.ModuleProtocolSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ModuleProtocolSearchDto;
import ee.hitsa.ois.web.dto.ModuleProtocolStudentSelectDto;
import ee.hitsa.ois.web.dto.ProtocolStudentDto;
import ee.hitsa.ois.web.dto.ProtocolVdataDto;

@Transactional
@Service
public class ModuleProtocolService {

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

    public Page<ModuleProtocolSearchDto> search(HoisUserDetails user, ModuleProtocolSearchCommand cmd, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from protocol p").sort(pageable);

        qb.requiredCriteria("p.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("exists (select protocol_id from protocol_vdata pvd where pvd.study_year_id = :studyYearId)", "studyYearId", cmd.getStudyYear());
        qb.optionalCriteria("exists (select protocol_id from protocol_student ps "
                + "inner join student s on s.id = ps.student_id where s.student_group_id = :studentGroupId)", "studentGroupId", cmd.getStudentGroup());
        qb.optionalCriteria("exists (select protocol_id from protocol_vdata pvd where pvd.curriculum_version_id = :curriculumVersionId)", "curriculumVersionId", cmd.getCurriculumVersion());
        qb.optionalCriteria("exists (select protocol_id from protocol_vdata pvd where pvd.curriculum_version_omodule_id in :module)", "module", cmd.getModule());
        qb.optionalCriteria("p.status_code = :statusCode", "statusCode", cmd.getStatus());
        qb.optionalCriteria("p.protocol_nr = :protocolNr", "protocolNr", cmd.getProtocolNr());
        qb.optionalCriteria("p.inserted > :from", "from", cmd.getInsertedFrom());
        qb.optionalCriteria("p.inserted < :thru", "thru", cmd.getInsertedThru());
        qb.optionalCriteria("p.confirm_date > :from", "from", cmd.getConfirmDateFrom());
        qb.optionalCriteria("p.confirm_date < :thru", "thru", cmd.getConfirmDateThru());

        Map<Long, ModuleProtocolSearchDto> dtoById = new HashMap<>();
        Page<ModuleProtocolSearchDto> result = JpaQueryUtil.pagingResult(qb, "p.id, p.protocol_nr, p.status_code, p.inserted, p.confirm_date", em, pageable).map(r -> {
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
            JpaQueryUtil.NativeQueryBuilder pvdQb = new JpaQueryUtil.NativeQueryBuilder("from protocol_vdata pvd "
                    + "left join curriculum_version cv on cv.id = pvd.curriculum_version_id "
                    + "left join curriculum c on c.id = cv.curriculum_id "
                    + "left join curriculum_version_omodule cvo on cvo.id = pvd.curriculum_version_omodule_id "
                    + "left join curriculum_module cm on cm.id = cvo.curriculum_module_id ");
            pvdQb.requiredCriteria("pvd.protocol_id in :protocolIds", "protocolIds", dtoById.keySet());

            List<?> data = pvdQb.select("pvd.protocol_id as pvd_id, cv.id as cv_id, cv.code, c.name_et as c_name_et, c.name_en as c_name_en, "
                    + "cm.id as cm_id, cm.name_et, cm.name_en", em).getResultList();
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


            JpaQueryUtil.NativeQueryBuilder psQb = new JpaQueryUtil.NativeQueryBuilder("from protocol_student ps "
                    + "inner join student s on s.id = ps.student_id "
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
        qb.requiredCriteria("cvo.curriculum_version_id = :curriculumVersionId", "curriculumVersionId", curriculumVersionId);

        List<AutocompleteResult> results = new ArrayList<>();
        List<?> data = qb.select("cvo.id, cv.code, cm.name_et, mcl.name_et as mcl_name_et, cm.name_en, mcl.name_en as mcl_name_en", em).getResultList();
        for (Object r : data) {
            results.add(new AutocompleteResult(resultAsLong(r, 0),
                    resultAsString(r, 2) + " - " + resultAsString(r, 3) + ", " + resultAsString(r, 1),
                    resultAsString(r, 4) + " - " + resultAsString(r, 5) + ", " + resultAsString(r, 1)));
        }
        return results;
    }

    public Collection<ModuleProtocolStudentSelectDto> occupationModuleStudents(HoisUserDetails user, Long occupationalModuleId) {
        //TODO: kellel puudub vastavas moodulis positiivne tulemus.
        JpaQueryUtil.NativeQueryBuilder studentsQb = new JpaQueryUtil.NativeQueryBuilder("from journal_omodule_theme jot "
                + "inner join journal j on j.id = jot.journal_id "
                + "inner join journal_student js on js.journal_id = j.id "
                + "inner join student s on s.id = js.student_id "
                + "inner join person p on p.id = s.person_id "
                + "inner join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id "
                + "inner join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id");

        studentsQb.requiredCriteria("j.school_id = :schoolId", "schoolId", user.getSchoolId());
        studentsQb.requiredCriteria("cvot.curriculum_version_omodule_id = :occupationalModuleId", "occupationalModuleId", occupationalModuleId);
        studentsQb.requiredCriteria("s.status_code in :activeStatuses", "activeStatuses", StudentStatus.STUDENT_STATUS_ACTIVE);

        List<?> stuednts = studentsQb.select("distinct s.id, p.firstname, p.lastname, p.idcode, s.status_code", em).getResultList();
        Map<Long, ModuleProtocolStudentSelectDto> result = stuednts.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> {
            ModuleProtocolStudentSelectDto dto = new ModuleProtocolStudentSelectDto();
            dto.setStudentId(resultAsLong(r, 0));
            dto.setFullname(resultAsString(r, 1) + " " + resultAsString(r, 2));
            dto.setIdcode(resultAsString(r, 3));
            dto.setStatus(resultAsString(r, 4));
            return dto;
        }));

        if (!result.isEmpty()) {
            JpaQueryUtil.NativeQueryBuilder gradeQb = new JpaQueryUtil.NativeQueryBuilder("from journal_entry_student jes "
                    + "inner join journal_student js on js.id = jes.journal_student_id");
            gradeQb.requiredCriteria("js.student_id in :studentsId", "studentsId", result.keySet());
            List<?> grades = gradeQb.select("js.student_id, jes.grade_code", em).getResultList();
            grades.stream().filter(r -> StringUtils.hasText(resultAsString(r, 1)))
                .forEach(r -> {
                    result.get(resultAsLong(r, 0)).getJournalResults().add(resultAsString(r, 1));
                });
        }

        return result.values();
    }

    public Protocol createInitial(HoisUserDetails user, ModuleProtocolCommand command) {
        Protocol protocol = new Protocol();
        protocol.setIsVocational(Boolean.TRUE);
        protocol.setStatus(classifierRepository.getOne(ProtocolStatus.PROTOKOLL_STAATUS_S.name()));
        protocol.setProtocolNr(generateProtocolNumber());
        protocol.setSchool(schoolRepository.getOne(user.getSchoolId()));

        List<ProtocolStudent> protocolStudents = command.getStudents().stream()
                .map(studentId -> new ProtocolStudent(studentRepository.getOne(studentId))).collect(Collectors.toList());
        protocol.setProtocolStudents(protocolStudents);

        ProtocolVdata protocolVdata = new ProtocolVdata();
        protocolVdata.setCurriculumVersion(curriculumVersionRepository.getOne(command.getCurriculumVersion()));
        protocolVdata.setCurriculumVersionOccupationModule(curriculumVersionOccupationModuleRepository.getOne(command.getCurriculumVersionOccupationModule()));
        protocolVdata.setStudyYear(studyYearRepository.getOne(command.getStudyYear()));
        protocolVdata.setTeacher(teacherRepository.getOne(command.getTeacher()));
        protocolVdata.setProtocol(protocol);
        protocol.setProtocolVdata(protocolVdata);
        return protocol;
    }

    private String generateProtocolNumber() {
        //TODO how to handle race condition and duplicates
        Long jrk = EntityUtil.getNullableId(protocolRepository.findFirstByOrderByIdDesc());
        return String.format("%02d%04d", Integer.valueOf(LocalDate.now().getYear()), jrk == null ? Long.valueOf(1L) : jrk);
    }

    public Protocol create(HoisUserDetails user, ModuleProtocolCreateForm form) {
        Protocol protocol = EntityUtil.bindToEntity(form, new Protocol(), "protocolStudents", "protocolVdata");
        protocol.setIsVocational(Boolean.TRUE);
        protocol.setStatus(classifierRepository.getOne(ProtocolStatus.PROTOKOLL_STAATUS_S.name()));
        protocol.setSchool(schoolRepository.getOne(user.getSchoolId()));
        protocol.setProtocolStudents(form.getProtocolStudents().stream()
                .map(dto -> {
                    ProtocolStudent protocolStudent = EntityUtil.bindToEntity(dto, new ProtocolStudent());
                    protocolStudent.setStudent(studentRepository.getOne(dto.getStudentId()));
                    return protocolStudent;
                 })
                .collect(Collectors.toList()));
        ProtocolVdata protocolVdata = protocolVdataFromDto(form.getProtocolVdata());
        protocolVdata.setProtocol(protocol);
        protocol.setProtocolVdata(protocolVdata);
        return protocolRepository.save(protocol);
    }

    private ProtocolVdata protocolVdataFromDto(ProtocolVdataDto vdata) {
        ProtocolVdata protocolVdata = new ProtocolVdata();
        protocolVdata.setCurriculumVersion(curriculumVersionRepository.getOne(vdata.getCurriculumVersion().getId()));
        protocolVdata.setCurriculumVersionOccupationModule(curriculumVersionOccupationModuleRepository.getOne(vdata.getCurriculumVersionOccupationModule().getId()));
        protocolVdata.setStudyYear(studyYearRepository.getOne(vdata.getStudyYear().getId()));
        protocolVdata.setTeacher(teacherRepository.getOne(vdata.getTeacher().getId()));
        return protocolVdata;
    }

    public Protocol save(Protocol protocol, ModuleProtocolSaveForm form) {
        EntityUtil.bindEntityCollection(protocol.getProtocolStudents(),
                ProtocolStudent::getId, form.getProtocolStudents(),
                ProtocolStudentDto::getId,
        dto -> {
         ProtocolStudent ps = EntityUtil.bindToEntity(dto, new ProtocolStudent());
         return ps;
        }, (dto, ps) -> {
            if (dto.getGrade() != null && !dto.getGrade().equals(EntityUtil.getNullableCode(ps.getGrade()))) {
                ps.setGrade(classifierRepository.getOne(dto.getGrade()));
            } else if (dto.getGrade() == null) {
                ps.setGrade(null);
            }
        });
        return protocolRepository.save(protocol);
    }

}
