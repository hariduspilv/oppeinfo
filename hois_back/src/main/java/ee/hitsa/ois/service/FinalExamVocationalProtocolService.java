package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Committee;
import ee.hitsa.ois.domain.CommitteeMember;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolCommitteeMember;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.protocol.ProtocolStudentOccupation;
import ee.hitsa.ois.domain.protocol.ProtocolVdata;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentOccupationCertificate;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.LessonPlanModule;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.repository.LessonPlanModuleRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.FinalExamProtocolUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.ProtocolVdataForm;
import ee.hitsa.ois.web.commandobject.finalexamprotocol.FinalExamProtocolCommitteeMemberForm;
import ee.hitsa.ois.web.commandobject.finalexamprotocol.FinalExamVocationalProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.finalexamprotocol.FinalExamVocationalProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.finalexamprotocol.FinalExamVocationalProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.finalexamprotocol.FinalExamVocationalProtocolSearchDto;
import ee.hitsa.ois.web.commandobject.finalexamprotocol.FinalExamVocationalProtocolStudentSaveForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;
import ee.hitsa.ois.web.dto.finalexamprotocol.FinalExamVocationalProtocolDto;
import ee.hitsa.ois.web.dto.finalexamprotocol.FinalExamVocationalProtocolOccupationalModuleDto;
import ee.hitsa.ois.web.dto.finalexamprotocol.FinalExamVocationalProtocolStudentDto;

@Transactional
@Service
public class FinalExamVocationalProtocolService extends AbstractProtocolService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String FINAL_EXAM_CODE = "KUTSEMOODUL_L";

    @Autowired
    private LessonPlanModuleRepository lessonPlanModuleRepository;

    public Page<FinalExamVocationalProtocolSearchDto> search(HoisUserDetails user, FinalExamVocationalProtocolSearchCommand cmd,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from protocol p "
                + "inner join protocol_vdata pvd on pvd.protocol_id = p.id").sort(pageable);

        qb.filter("p.is_final = true");
        qb.filter("p.is_vocational = true");
        qb.requiredCriteria("p.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria(
                "exists (select protocol_id from protocol_vdata pvd where pvd.protocol_id = p.id and pvd.study_year_id = :studyYearId)",
                "studyYearId", cmd.getStudyYear());
        qb.optionalCriteria(
                "exists (select protocol_id "
                + "from protocol_vdata pvd "
                + "where pvd.protocol_id = p.id "
                + "and pvd.teacher_id = :teacherId)",
                "teacherId", cmd.getTeacher());
        qb.optionalCriteria(
                "exists (select protocol_id "
                + "from protocol_vdata pvd "
                + "where pvd.protocol_id = p.id "
                + "and pvd.curriculum_version_id = :curriculumVersionId)",
                "curriculumVersionId", cmd.getCurriculumVersion());
        qb.optionalCriteria("pvd.teacher_id = :teacherId", "teacherId", cmd.getTeacher());
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

        Map<Long, FinalExamVocationalProtocolSearchDto> dtoById = new HashMap<>();
        Page<FinalExamVocationalProtocolSearchDto> result = JpaQueryUtil
                .pagingResult(qb, "p.id, p.protocol_nr, p.status_code, p.inserted, p.confirm_date, p.confirmer, pvd.teacher_id", em, pageable)
                .map(r -> {
                    FinalExamVocationalProtocolSearchDto dto = new FinalExamVocationalProtocolSearchDto();
                    dto.setId(resultAsLong(r, 0));
                    dto.setProtocolNr(resultAsString(r, 1));
                    dto.setStatus(resultAsString(r, 2));
                    dto.setInserted(resultAsLocalDate(r, 3));
                    dto.setConfirmDate(resultAsLocalDate(r, 4));
                    dto.setConfirmer(resultAsString(r, 5));
                    dto.setCanEdit(Boolean.valueOf(FinalExamProtocolUtil.canEdit(user, em.getReference(Protocol.class, dto.getId()), resultAsLong(r, 6))));
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
                FinalExamVocationalProtocolSearchDto dto = dtoById.get(resultAsLong(r, 0));
                AutocompleteResult curriculumVersion = new AutocompleteResult(resultAsLong(r, 1),
                        CurriculumUtil.versionName(resultAsString(r, 2), resultAsString(r, 3)),
                        CurriculumUtil.versionName(resultAsString(r, 2), resultAsString(r, 4)));
                dto.getCurriculumVersions().add(curriculumVersion);
                AutocompleteResult curriculumVersionOccupationModule = new AutocompleteResult(resultAsLong(r, 5),
                        resultAsString(r, 6), resultAsString(r, 7));
                dto.getCurriculumVersionOccupationModules().add(curriculumVersionOccupationModule);
            }

            JpaNativeQueryBuilder psQb = new JpaNativeQueryBuilder(
                    "from protocol_vdata pvd " + "inner join teacher t on t.id = pvd.teacher_id "
                            + "inner join person p on p.id = t.person_id");
            psQb.requiredCriteria("pvd.protocol_id in :protocolIds", "protocolIds", dtoById.keySet());
            List<?> teacherData = psQb.select("distinct pvd.protocol_id, p.firstname, p.lastname", em).getResultList();
            for (Object r : teacherData) {
                FinalExamVocationalProtocolSearchDto dto = dtoById.get(resultAsLong(r, 0));
                dto.setTeacher(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            }
        }

        return result;
    }
    
    public FinalExamVocationalProtocolDto finalExamVocationalProtocol(HoisUserDetails user, Protocol protocol) {
        includeCorrectImportedOccupationCertificates(protocol);
        
        FinalExamVocationalProtocolDto dto = FinalExamVocationalProtocolDto.of(protocol);
        dto.setCanBeEdited(Boolean.valueOf(FinalExamProtocolUtil.canEdit(user, protocol)));
        dto.setCanBeDeleted(Boolean.valueOf(FinalExamProtocolUtil.canDelete(user, protocol)));
        return dto;
    }
    
    private void includeCorrectImportedOccupationCertificates(Protocol protocol) {
        if (!ClassifierUtil.equals(ProtocolStatus.PROTOKOLL_STAATUS_K, protocol.getStatus())) {
            List<Long> studentIds = StreamUtil.toMappedList(ps -> EntityUtil.getId(ps.getStudent()), protocol.getProtocolStudents());
            Set<String> occupations = StreamUtil.toMappedSet(o -> EntityUtil.getCode(o.getOccupation()),
                    protocol.getProtocolVdata().getCurriculumVersionOccupationModule().getCurriculumModule().getOccupations());
            Map<Long, List<StudentOccupationCertificate>> studentOccupationCertificates = studentOccupationCertificates(studentIds, occupations);
            Map<Long, Map<Long, ProtocolStudentOccupation>> protocolCertificates =
                em.createQuery("select pso from ProtocolStudentOccupation pso where pso.protocolStudent.protocol.id = ?1", ProtocolStudentOccupation.class)
                    .setParameter(1, protocol.getId())
                    .getResultList().stream().filter(pso -> pso.getStudentOccupationCertificate() != null).collect(Collectors.groupingBy(pso -> EntityUtil.getId(pso.getProtocolStudent()), Collectors.toMap(pso -> EntityUtil.getId(pso.getStudentOccupationCertificate()), v -> v)));

            for(ProtocolStudent ps : protocol.getProtocolStudents()) {
                Map<Long, ProtocolStudentOccupation> protocolStudentCertificates = protocolCertificates.get(EntityUtil.getId(ps));
                Map<Long, StudentOccupationCertificate> studentCertificates = StreamUtil.toMap(soc -> EntityUtil.getId(soc), studentOccupationCertificates.get(EntityUtil.getId(ps.getStudent())));

                studentCertificates.forEach((k, v) -> {
                    if (protocolStudentCertificates == null || !protocolStudentCertificates.keySet().remove(k)) {
                        // remove manually added occupation certificate before replacing it with imported certificate
                        removeOccupationAddedToProtocol(ps, EntityUtil.getCode(v.getOccupation()), EntityUtil.getCode(v.getPartOccupation()));
                        addStudentOccupationCertificateToProtocol(ps, v);
                    }
                });

                if(protocolStudentCertificates != null) {
                    protocolStudentCertificates.values().forEach(v -> {
                        ps.getProtocolStudentOccupations().removeIf(it -> it.equals(v));
                    });
                }
            }
        }
    }
    
    private static void removeOccupationAddedToProtocol(ProtocolStudent student, String occupation, String partOccupation) {
        student.getProtocolStudentOccupations()
                .removeIf(pso -> occupation.equals(EntityUtil.getCode(pso.getOccupation()))
                        && partOccupation.equals(EntityUtil.getCode(pso.getPartOccupation()))
                        && pso.getStudentOccupationCertificate() == null);
    }

    public FinalExamVocationalProtocolDto create(HoisUserDetails user, FinalExamVocationalProtocolCreateForm form) {
        Protocol protocol = EntityUtil.bindToEntity(form, new Protocol(), "protocolStudents", "protocolVdata");
        protocol.setIsFinal(Boolean.TRUE);
        protocol.setIsVocational(Boolean.TRUE);
        protocol.setStatus(em.getReference(Classifier.class, ProtocolStatus.PROTOKOLL_STAATUS_S.name()));
        protocol.setSchool(em.getReference(School.class, user.getSchoolId()));
        protocol.setProtocolNr(generateProtocolNumber());
        
        ProtocolVdata protocolVdata = protocolVdataFromDto(form.getProtocolVdata());
        protocolVdata.setProtocol(protocol);
        protocol.setProtocolVdata(protocolVdata);
        
        List<Long> studentIds = StreamUtil.toMappedList(ps -> ps.getStudentId(), form.getProtocolStudents());
        List<String> occupations = StreamUtil.toMappedList(o -> o.getOccupation().getCode(),
                protocolVdata.getCurriculumVersionOccupationModule().getCurriculumModule().getOccupations());
        Map<Long, List<StudentOccupationCertificate>> studentOccupationCertificates = studentOccupationCertificates(studentIds, occupations);

        protocol.setProtocolStudents(StreamUtil.toMappedList(dto -> {
            ProtocolStudent protocolStudent = EntityUtil.bindToEntity(dto, new ProtocolStudent());
            protocolStudent.setStudent(em.getReference(Student.class, dto.getStudentId()));

            List<StudentOccupationCertificate> studentCertificates = studentOccupationCertificates.get(dto.getStudentId());
            if (studentCertificates != null) {
                for(StudentOccupationCertificate sc : studentCertificates) {
                    addStudentOccupationCertificateToProtocol(protocolStudent, sc);
                }
            }
            return protocolStudent;
        }, form.getProtocolStudents()));
        return FinalExamVocationalProtocolDto.of(EntityUtil.save(protocol, em));
    }
    
    private static void addStudentOccupationCertificateToProtocol(ProtocolStudent protocolStudent, StudentOccupationCertificate ceritificate) {
        ProtocolStudentOccupation protocolStudentOccupation = new ProtocolStudentOccupation();
        protocolStudentOccupation.setProtocolStudent(protocolStudent);
        protocolStudentOccupation.setStudentOccupationCertificate(ceritificate);
        protocolStudentOccupation.setOccupation(ceritificate.getOccupation());
        protocolStudentOccupation.setPartOccupation(ceritificate.getPartOccupation());
        protocolStudent.getProtocolStudentOccupations().add(protocolStudentOccupation);
    }

    private Map<Long, List<StudentOccupationCertificate>> studentOccupationCertificates(List<Long> studentIds, Collection<String> occupations) {
        if (!studentIds.isEmpty() && !occupations.isEmpty()) {
            List<StudentOccupationCertificate> occupationCertificates = em.createQuery("select soc from StudentOccupationCertificate soc"
                    + " where soc.student.id in (?1) and (soc.occupation.code in (?2) or soc.partOccupation.code in (?2))", StudentOccupationCertificate.class)
                    .setParameter(1, studentIds)
                    .setParameter(2, occupations)
                    .getResultList();
            return occupationCertificates.stream().collect(Collectors.groupingBy(oc -> EntityUtil.getId(oc.getStudent())));
        }
        return new HashMap<>();
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

    public Protocol save(Protocol protocol, FinalExamVocationalProtocolSaveForm form) {
        EntityUtil.bindToEntity(form, protocol, "committee", "protocolStudents", "protocolCommitteeMembers");
        protocol.setCommittee(form.getCommitteeId() != null ? em.getReference(Committee.class, form.getCommitteeId()) : null);
        saveCommitteeMembers(protocol, form);
        saveStudents(protocol, form);
        
        return EntityUtil.save(protocol, em);
    }
    
    private void saveCommitteeMembers(Protocol protocol, FinalExamVocationalProtocolSaveForm form) {
        EntityUtil.bindEntityCollection(protocol.getProtocolCommitteeMembers(), ProtocolCommitteeMember::getId,
                form.getProtocolCommitteeMembers(), FinalExamProtocolCommitteeMemberForm::getId, dto -> {
                    ProtocolCommitteeMember pcm = EntityUtil.bindToEntity(dto, new ProtocolCommitteeMember(), "committeeMember");
                    pcm.setCommitteeMember(em.getReference(CommitteeMember.class, dto.getCommitteeMemberId()));
                    return pcm;
                });
    }

    private void saveStudents(Protocol protocol, FinalExamVocationalProtocolSaveForm form) {
        List<ProtocolStudent> storedStudents = new ArrayList<>(protocol.getProtocolStudents());
        EntityUtil.bindEntityCollection(protocol.getProtocolStudents(), ProtocolStudent::getId,
                form.getProtocolStudents(), FinalExamVocationalProtocolStudentSaveForm::getId, dto -> {
                    ProtocolStudent ps = EntityUtil.bindToEntity(dto, new ProtocolStudent(), "student");
                    ps.setStudent(em.getReference(Student.class, dto.getStudentId()));
                    saveOccupationCertificates(ps, dto);
                    return ps;
                }, (dto, ps) -> {
                    if (gradeChangedButNotRemoved(dto, ps)) {
                        addHistory(ps);
                        Classifier grade = em.getReference(Classifier.class, dto.getGrade());
                        Short mark = getMark(EntityUtil.getCode(grade));
                        gradeStudent(ps, grade, mark);
                        ps.setAddInfo(dto.getAddInfo());
                    } else if (gradeRemoved(dto, ps)) {
                        addHistory(ps);
                        removeGrade(ps);
                    }
                    saveOccupationCertificates(ps, dto);
                });
        assertRemovedStudents(storedStudents, protocol.getProtocolStudents());
    }
    
    private static void assertRemovedStudents(List<ProtocolStudent> oldStudents, List<ProtocolStudent> newStudents) {
        Set<Long> newIds = StreamUtil.toMappedSet(ProtocolStudent::getId, newStudents);
        List<ProtocolStudent> removedStudents = StreamUtil
                .toFilteredList(oldStudent -> !newIds.contains(oldStudent.getId()), oldStudents);
        for (ProtocolStudent protocolStudent : removedStudents) {
            if (!FinalExamProtocolUtil.studentCanBeDeleted(protocolStudent)) {
                throw new ValidationFailedException("finalExamProtocol.messages.cantRemoveStudent");
            }
        }
    }
    
    private void saveOccupationCertificates(ProtocolStudent student, FinalExamVocationalProtocolStudentSaveForm form) {
        Map<String, Long> currentCertificates = currentCertificateCodes(student);
        for (String code : form.getOccupations()) {
            Long certificateId = currentCertificates.remove(code);
            if (certificateId == null) {
                Classifier occupation = null, partOccupation = null;
                if (code.startsWith(MainClassCode.OSAKUTSE.name())) {
                    partOccupation = em.getReference(Classifier.class, code);
                    occupation = ClassifierUtil.parentFor(partOccupation, MainClassCode.KUTSE).get();
                } else if (code.startsWith(MainClassCode.KUTSE.name())) {
                    occupation = em.getReference(Classifier.class, code);
                }
                if(occupation != null) {
                    ProtocolStudentOccupation pso = new ProtocolStudentOccupation();
                    pso.setProtocolStudent(student);
                    pso.setOccupation(occupation);
                    pso.setPartOccupation(partOccupation);
                    student.getProtocolStudentOccupations().add(pso);
                }
            }
        }
        
        student.getProtocolStudentOccupations().removeIf(it -> currentCertificates.containsValue(it.getId()));
    }
    
    private static Map<String, Long> currentCertificateCodes(ProtocolStudent student) {
        Map<String, Long> currentCertificateCodes = new HashMap<>();
        for(ProtocolStudentOccupation c : student.getProtocolStudentOccupations()) {
            if (c.getPartOccupation() != null) {
                currentCertificateCodes.put(EntityUtil.getCode(c.getPartOccupation()), c.getId());
            } else {
                currentCertificateCodes.put(EntityUtil.getCode(c.getPartOccupation()), c.getId());
            }
        }
        return currentCertificateCodes;
    }
    
    public Collection<FinalExamVocationalProtocolStudentDto> otherStudents(HoisUserDetails user, Protocol protocol) {
        Map<Long, FinalExamVocationalProtocolStudentDto> result = studentsForSelection(user,
                EntityUtil.getId(protocol.getProtocolVdata().getCurriculumVersionOccupationModule()), protocol.getId());
        //TODO: results?
        return result.values();
    }
    
    public Protocol addStudents(Protocol protocol, FinalExamVocationalProtocolSaveForm form) {
        Map<Long, ProtocolStudent> existingStudents = StreamUtil.toMap(it -> EntityUtil.getId(it.getStudent()), protocol.getProtocolStudents());

        for (FinalExamVocationalProtocolStudentSaveForm protocolStudentForm : form.getProtocolStudents()) {
            if (existingStudents.containsKey(protocolStudentForm.getStudentId())) {
                log.warn("student {} is already added to protocol {}", protocolStudentForm.getStudentId(),
                        protocol.getId());
            } else {
                ProtocolStudent ps = new ProtocolStudent();
                ps.setStudent(em.getReference(Student.class, protocolStudentForm.getStudentId()));
                ps.setProtocol(protocol);
                protocol.getProtocolStudents().add(ps);
            }
        }
        return EntityUtil.save(protocol, em);
    }

    private static Short getMark(String grade) {
        return Short.valueOf((short) OccupationalGrade.valueOf(grade).getMark());
    }

    public List<CurriculumVersionResult> curriculumVersionsForSelection(HoisUserDetails user) {
        String from = "from curriculum_version cv"
                + " inner join curriculum c on cv.curriculum_id = c.id"
                + " inner join curriculum_version_omodule cvo on cvo.curriculum_version_id = cv.id"
                + " inner join curriculum_module cm on cvo.curriculum_module_id = cm.id"
                + " left outer join curriculum_study_form sf on cv.curriculum_study_form_id = sf.id";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("c.is_higher = :higher", "higher", Boolean.FALSE);
        qb.requiredCriteria("cm.module_code = :moduleCode", "moduleCode", FINAL_EXAM_CODE);
        
        List<?> data = qb.select("distinct cv.id, cv.code, c.name_et, c.name_en, c.id as curriculum_id, cv.school_department_id, sf.study_form_code, c.is_higher", em).getResultList();
        
        return StreamUtil.toMappedList(r -> {
            String code = resultAsString(r, 1);
            return new CurriculumVersionResult(resultAsLong(r, 0), CurriculumUtil.versionName(code, resultAsString(r, 2)),
                    CurriculumUtil.versionName(code, resultAsString(r, 3)), resultAsLong(r, 4), resultAsLong(r, 5), resultAsString(r, 6), Boolean.valueOf(!Boolean.TRUE.equals(resultAsBoolean(r, 7))));
        }, data);
    }
    
    public List<AutocompleteResult> occupationModulesForSelection(HoisUserDetails user, Long curriculumVersionId) {
        String from = "from curriculum_version_omodule cvo "
                + "inner join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "inner join classifier mcl on mcl.code = cm.module_code "
                + "inner join curriculum_version cv on cv.id = cvo.curriculum_version_id "
                + "inner join curriculum c on c.id = cv.curriculum_id";

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("cvo.curriculum_version_id = :curriculumVersionId", "curriculumVersionId", curriculumVersionId);
        qb.requiredCriteria("cm.module_code = :module_code", "module_code", FINAL_EXAM_CODE);
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
    
    public FinalExamVocationalProtocolOccupationalModuleDto occupationModule(HoisUserDetails user,
            Long curriculumVersionOccupationModuleId) {
        FinalExamVocationalProtocolOccupationalModuleDto dto = new FinalExamVocationalProtocolOccupationalModuleDto();
        dto.setOccupationModuleStudents(occupationModuleStudents(user, curriculumVersionOccupationModuleId));
        
        LessonPlanModule lessonPlanModule = lessonPlanModuleRepository
                .findFirstByCurriculumVersionOccupationModuleId(curriculumVersionOccupationModuleId);
        if (lessonPlanModule != null && lessonPlanModule.getTeacher() != null) {
            dto.setTeacher(AutocompleteResult.of(lessonPlanModule.getTeacher()));
        }

        return dto;
    }
    
    public Collection<FinalExamVocationalProtocolStudentDto> occupationModuleStudents(HoisUserDetails user,
            Long occupationalModuleId) {
        Map<Long, FinalExamVocationalProtocolStudentDto> result = studentsForSelection(user, occupationalModuleId);
        return result.values();
    }
    
    private Map<Long, FinalExamVocationalProtocolStudentDto> studentsForSelection(HoisUserDetails user,
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

    private Map<Long, FinalExamVocationalProtocolStudentDto> studentsForSelection(HoisUserDetails user,
            Long occupationalModuleId, Long notInProtocolId) {
        JpaNativeQueryBuilder studentsQb = new JpaNativeQueryBuilder(
                "from journal_omodule_theme jot " + "inner join journal j on j.id = jot.journal_id "
                        + "inner join journal_student js on js.journal_id = j.id "
                        + "inner join student s on s.id = js.student_id " + "inner join person p on p.id = s.person_id "
                        + "inner join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id "
                        + "inner join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id "
                        + "inner join student_group sg on s.student_group_id = sg.id");

        studentsQb.requiredCriteria("j.school_id = :schoolId", "schoolId", user.getSchoolId());
        studentsQb.requiredCriteria("cvot.curriculum_version_omodule_id = :occupationalModuleId",
                "occupationalModuleId", occupationalModuleId);
        studentsQb.requiredCriteria("s.status_code in :activeStatuses", "activeStatuses",
                StudentStatus.STUDENT_STATUS_ACTIVE);

        studentsQb.requiredCriteria(HAS_NO_POSITIVE_RESULT_IN_THIS_MODULE,
                "positiveGrades", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);

        studentsQb.optionalCriteria(NOT_ADDED_TO_PROTOCOL, "protocolId", notInProtocolId);

        List<?> students = studentsQb.select("distinct s.id, p.firstname, p.lastname, p.idcode, s.status_code, sg.code", em)
                .getResultList();

        return students.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> {
            FinalExamVocationalProtocolStudentDto dto = new FinalExamVocationalProtocolStudentDto();
            dto.setStudentId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setIdcode(resultAsString(r, 3));
            dto.setStatus(resultAsString(r, 4));
            dto.setStudentGroup(resultAsString(r, 5));
            return dto;
        }));
    }
    
    public Protocol confirm(HoisUserDetails user, Protocol protocol, FinalExamVocationalProtocolSaveForm protocolSaveForm) {
        setConfirmation(user, protocol);
        Protocol confirmedProtocol = null;
        if (protocolSaveForm != null) {
            confirmedProtocol = save(protocol, protocolSaveForm);
        } else {
            confirmedProtocol = EntityUtil.save(protocol, em);
        }

        if(confirmedProtocol.getProtocolStudents().stream().anyMatch(ps -> ps.getGrade() == null)) {
            throw new ValidationFailedException("finalExamProtocol.messages.gradeNotSelectedForAllStudents");
        }

        sendStudentResultMessages(confirmedProtocol);
        return confirmedProtocol;
    }
}
