package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import ee.hitsa.ois.domain.gradingschema.GradingSchemaRow;
import ee.hitsa.ois.util.ProtocolUtil;
import ee.hitsa.ois.util.StudentUtil;
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
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.FinalProtocolUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.ProtocolVdataForm;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalProtocolCommitteeMemberForm;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalVocationalProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalVocationalProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalVocationalProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalVocationalProtocolSearchDto;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalVocationalProtocolStudentSaveForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionResult;
import ee.hitsa.ois.web.dto.finalprotocol.FinalVocationalProtocolDto;
import ee.hitsa.ois.web.dto.finalprotocol.FinalVocationalProtocolOccupationalModuleDto;
import ee.hitsa.ois.web.dto.finalprotocol.FinalVocationalProtocolStudentDto;

@Transactional
@Service
public class FinalVocationalProtocolService extends AbstractProtocolService {

    @Autowired
    private SchoolService schoolService;

    private static final String FINAL_EXAM_CODE = "KUTSEMOODUL_L";
    
    private static final String LIST_FROM = "from protocol p "
            + "join protocol_vdata pvd on pvd.protocol_id = p.id "
            + "join study_year sy on pvd.study_year_id = sy.id "
            + "join curriculum_version_omodule cvo on cvo.id = pvd.curriculum_version_omodule_id "
            + "join curriculum_module cm on cm.id = cvo.curriculum_module_id "
            + "join curriculum_version cv on cv.id = cvo.curriculum_version_id "
            + "join curriculum c on c.id = cv.curriculum_id "
            + "left join committee com on p.committee_id = com.id "
            + "left join committee_member cme on com.id = cme.committee_id and cme.is_chairman = true "
            + "left join teacher t on cme.teacher_id = t.id " + "left join person per on t.person_id = per.id";
    
    private static final String LIST_SELECT = "p.id as protocol_id, p.protocol_nr, p.is_final_thesis, p.status_code, "
            + "sy.year_code, cv.id cv_id, cv.code cv_code, c.name_et c_name_et, c.name_en c_name_en, "
            + "cm.id as cm_id, cm.name_et cm_name_et, cm.name_en cm_name_en, cm.credits, "
            + "t.id as t_id, per.firstname, per.lastname, cme.member_name, p.inserted, p.confirm_date, p.confirmer, pvd.teacher_id";

    public Page<FinalVocationalProtocolSearchDto> search(HoisUserDetails user, FinalVocationalProtocolSearchCommand cmd,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(LIST_FROM).sort(pageable);

        qb.filter("p.is_final = true");
        qb.filter("p.is_vocational = true");
        qb.requiredCriteria("p.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("c.id in (:userCurriculumIds)", "userCurriculumIds", user.getCurriculumIds());
        }

        qb.optionalCriteria(
                "exists (select protocol_id from protocol_vdata pvd where pvd.protocol_id = p.id and pvd.study_year_id = :studyYearId)",
                "studyYearId", cmd.getStudyYear());
        qb.optionalCriteria(
                "exists (select protocol_id " + "from protocol_vdata pvd "
                        + "join curriculum_version_omodule omodule on pvd.curriculum_version_omodule_id = omodule.id "
                        + "where pvd.protocol_id = p.id and omodule.curriculum_module_id = :module)",
                "module", cmd.getModule());
        qb.optionalCriteria("p.is_final_thesis = :isFinalThesis", "isFinalThesis", cmd.getIsFinalThesis());
        qb.optionalCriteria("p.status_code = :statusCode", "statusCode", cmd.getStatus());
        qb.optionalCriteria("p.protocol_nr = :protocolNr", "protocolNr", cmd.getProtocolNr());
        qb.optionalCriteria("p.inserted >= :insertedFrom", "insertedFrom", cmd.getInsertedFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("p.inserted <= :insertedThru", "insertedThru", cmd.getInsertedThru(), DateUtils::lastMomentOfDay);
        qb.optionalCriteria("p.confirm_date >= :confirmedFrom", "confirmedFrom", cmd.getConfirmDateFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("p.confirm_date <= :confirmedThru", "confirmedThru", cmd.getConfirmDateThru(), DateUtils::lastMomentOfDay);
        qb.optionalCriteria("p.id in (select p.id from protocol p "
                + "join protocol_student ps on p.id = ps.protocol_id "
                + "join student s on ps.student_id = s.id "
                + "where s.student_group_id in (:studentGroups))", "studentGroups", cmd.getStudentGroups());
        
        if (cmd.getStudentName() != null) {
            qb.filter("p.id in (select p.id from protocol p "
                    + "join protocol_student ps on p.id = ps.protocol_id "
                    + "join student s on ps.student_id = s.id "
                    + "join person per on s.person_id = per.id "
                    + "where upper(per.firstname || ' ' || per.lastname) like '%" + cmd.getStudentName().toUpperCase() + "%')");
        }

        if (user.isTeacher()) {
            qb.optionalCriteria("exists (select protocol_id " + "from protocol_vdata pvd "
                    + "where pvd.protocol_id = p.id " + "and pvd.teacher_id = :teacherId)", "teacherId",
                    user.getTeacherId());
        }

        Page<FinalVocationalProtocolSearchDto> result = JpaQueryUtil.pagingResult(qb, LIST_SELECT, em, pageable).map(r -> {
            FinalVocationalProtocolSearchDto dto = new FinalVocationalProtocolSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setProtocolNr(resultAsString(r, 1));
            dto.setIsFinalThesis(resultAsBoolean(r, 2));
            dto.setStatus(resultAsString(r, 3));
            dto.setStudyYear(resultAsString(r, 4));
            dto.getCurriculumVersions().add(
                new AutocompleteResult(resultAsLong(r, 5),
                        CurriculumUtil.versionName(resultAsString(r, 6), resultAsString(r, 7)),
                        CurriculumUtil.versionName(resultAsString(r, 6), resultAsString(r, 8))));
            dto.getCurriculumVersionOccupationModules().add(
                new AutocompleteResult(resultAsLong(r, 9),
                        String.format("%1$s (%2$s EKAP)", resultAsString(r, 10), resultAsLong(r, 12)),
                        String.format("%1$s (%2$s EKAP)", resultAsString(r, 11), resultAsLong(r, 12))));

            if (resultAsLong(r, 13) != null) {
                dto.setCommitteeChairman(PersonUtil.fullname(resultAsString(r, 14), resultAsString(r, 15)));
            } else {
                dto.setCommitteeChairman(resultAsString(r, 16));
            }

            dto.setInserted(resultAsLocalDate(r, 17));
            dto.setConfirmDate(resultAsLocalDate(r, 18));
            dto.setConfirmer(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 19)));

            Long teacherId = resultAsLong(r, 20);
            dto.setCanEdit(Boolean.valueOf(FinalProtocolUtil.canEditVocational(user, dto.getStatus(),
                    teacherId != null ? Arrays.asList(teacherId) : null)));
            return dto;
        });
        return result;
    }

    public FinalVocationalProtocolDto finalVocationalProtocol(HoisUserDetails user, Protocol protocol) {
        includeCorrectImportedOccupationCertificates(protocol);

        FinalVocationalProtocolDto dto = FinalVocationalProtocolDto.of(protocol);
        dto.setCanBeEdited(Boolean.valueOf(FinalProtocolUtil.canEdit(user, protocol)));
        dto.setCanBeConfirmed(Boolean.valueOf(FinalProtocolUtil.canConfirm(user, protocol)));
        dto.setCanBeDeleted(Boolean.valueOf(FinalProtocolUtil.canDelete(user, protocol)));
        return dto;
    }

    public Protocol create(HoisUserDetails user, FinalVocationalProtocolCreateForm form) {
        SchoolService.SchoolType type = schoolService.schoolType(user.getSchoolId());
        FinalProtocolUtil.assertIsSchoolAdminOrTeacherResponsible(user, type.isHigher(),
                form.getProtocolVdata().getTeacher());
        
        Protocol protocol = EntityUtil.bindToEntity(form, new Protocol(), "protocolStudents", "protocolVdata");
        protocol.setIsFinal(Boolean.TRUE);
        protocol.setIsVocational(Boolean.TRUE);
        protocol.setIsFinalThesis(form.getIsFinalThesis());
        protocol.setStatus(em.getReference(Classifier.class, ProtocolStatus.PROTOKOLL_STAATUS_S.name()));
        protocol.setSchool(em.getReference(School.class, user.getSchoolId()));
        protocol.setProtocolNr(generateProtocolNumber());

        ProtocolVdata protocolVdata = protocolVdataFromDto(form.getProtocolVdata());
        protocolVdata.setProtocol(protocol);
        protocol.setProtocolVdata(protocolVdata);

        protocol.setProtocolStudents(StreamUtil.toMappedList(dto -> {
            ProtocolStudent protocolStudent = EntityUtil.bindToEntity(dto, new ProtocolStudent());
            protocolStudent.setStudent(em.getReference(Student.class, dto.getStudentId()));
            return protocolStudent;
        }, form.getProtocolStudents()));
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

    public Protocol save(Protocol protocol, FinalVocationalProtocolSaveForm form) {
        EntityUtil.bindToEntity(form, protocol, "committee", "protocolStudents", "protocolCommitteeMembers");
        protocol.setCommittee(
                form.getCommitteeId() != null ? em.getReference(Committee.class, form.getCommitteeId()) : null);
        saveCommitteeMembers(protocol, form);
        saveStudents(protocol, form);

        return EntityUtil.save(protocol, em);
    }

    private void saveCommitteeMembers(Protocol protocol, FinalVocationalProtocolSaveForm form) {
        EntityUtil.bindEntityCollection(protocol.getProtocolCommitteeMembers(), ProtocolCommitteeMember::getId,
                form.getProtocolCommitteeMembers(), FinalProtocolCommitteeMemberForm::getId, dto -> {
                    ProtocolCommitteeMember pcm = EntityUtil.bindToEntity(dto, new ProtocolCommitteeMember(),
                            "committeeMember");
                    pcm.setCommitteeMember(em.getReference(CommitteeMember.class, dto.getCommitteeMemberId()));
                    return pcm;
                });
    }

    private void saveStudents(Protocol protocol, FinalVocationalProtocolSaveForm form) {
        List<ProtocolStudent> storedStudents = new ArrayList<>(protocol.getProtocolStudents());
        EntityUtil.bindEntityCollection(protocol.getProtocolStudents(), ProtocolStudent::getId,
                // no protocol students created here
                form.getProtocolStudents(), FinalVocationalProtocolStudentSaveForm::getId, null, (dto, ps) -> {
                    if (!ProtocolUtil.studentCanBeEdited(ps)) {
                        return;
                    }

                    LocalDate gradeDate = protocol.getFinalDate() != null ? protocol.getFinalDate() : LocalDate.now();
                    if (gradeChangedButNotRemoved(dto, ps)) {
                        assertHasAddInfoIfProtocolConfirmed(dto, protocol);
                        addHistory(ps);
                        Classifier grade = em.getReference(Classifier.class, dto.getGrade().getCode());
                        Short mark = getMark(EntityUtil.getCode(grade));
                        GradingSchemaRow gradingSchemaRow = EntityUtil.getOptionalOne(GradingSchemaRow.class,
                                dto.getGrade().getGradingSchemaRowId(), em);
                        gradeStudent(ps, grade, mark, Boolean.FALSE, gradingSchemaRow, gradeDate);
                        ps.setAddInfo(dto.getAddInfo());
                    } else if (gradeRemoved(dto, ps)) {
                        assertHasAddInfoIfProtocolConfirmed(dto, protocol);
                        addHistory(ps);
                        removeGrade(ps);
                    } else if (ps.getGrade() != null) {
                        // works like higher main protocols
                        ps.setGradeDate(gradeDate);
                    }
                    saveOccupationCertificates(ps, dto);
                    ps.setLanguage(!StudentUtil.isStudyingInEstonian(ps.getStudent()) ?
                            EntityUtil.getOptionalOne(dto.getLanguage(), em) : null);
                });
    }

    private void saveOccupationCertificates(ProtocolStudent student, FinalVocationalProtocolStudentSaveForm form) {
        Map<String, Long> currentCertificates = currentCertificateCodes(student);
        for (String code : form.getOccupationCodes()) {
            Long certificateId = currentCertificates.remove(code);
            if (certificateId == null) {
                Classifier occupation = null, partOccupation = null;
                if (code.startsWith(MainClassCode.OSAKUTSE.name())) {
                    partOccupation = em.getReference(Classifier.class, code);
                    occupation = ClassifierUtil.parentFor(partOccupation, MainClassCode.KUTSE).get();
                } else if (code.startsWith(MainClassCode.KUTSE.name())) {
                    occupation = em.getReference(Classifier.class, code);
                }
                if (occupation != null) {
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

    public Collection<FinalVocationalProtocolStudentDto> otherStudents(HoisUserDetails user, Protocol protocol) {
        return studentsForSelection(user,
                EntityUtil.getId(protocol.getProtocolVdata().getCurriculumVersionOccupationModule()),
                protocol.getIsFinalThesis(), protocol.getId()).values();
    }

    public Protocol addStudents(Protocol protocol, FinalVocationalProtocolSaveForm form) {
        Map<Long, ProtocolStudent> existingStudents = StreamUtil.toMap(it -> EntityUtil.getId(it.getStudent()),
                protocol.getProtocolStudents());

        for (FinalVocationalProtocolStudentSaveForm studentForm : form.getProtocolStudents()) {
            if (existingStudents.containsKey(studentForm.getStudentId())) {
                log.warn("student {} is already added to protocol {}", studentForm.getStudentId(), protocol.getId());
            } else {
                ProtocolStudent ps = new ProtocolStudent();
                ps.setStudent(em.getReference(Student.class, studentForm.getStudentId()));
                ps.setProtocol(protocol);
                protocol.getProtocolStudents().add(ps);
            }
        }
        return EntityUtil.save(protocol, em);
    }

    private static Short getMark(String grade) {
        return Short.valueOf((short) OccupationalGrade.valueOf(grade).getMark());
    }

    public List<CurriculumVersionResult> curriculumVersionsForSelection(HoisUserDetails user, SearchCommand lookup) {
        String from = "from curriculum_version cv" + " inner join curriculum c on cv.curriculum_id = c.id"
                + " inner join curriculum_version_omodule cvo on cvo.curriculum_version_id = cv.id"
                + " inner join curriculum_module cm on cvo.curriculum_module_id = cm.id"
                + " left outer join curriculum_study_form sf on cv.curriculum_study_form_id = sf.id";

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("c.is_higher = :higher", "higher", Boolean.FALSE);
        qb.requiredCriteria("c.status_code != :curriculumStatus", "curriculumStatus", CurriculumStatus.OPPEKAVA_STAATUS_C.name());
        qb.requiredCriteria("cv.status_code != :cvStatusCode", "cvStatusCode", CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_C.name());
        qb.requiredCriteria("cm.module_code = :moduleCode", "moduleCode", FINAL_EXAM_CODE);
        qb.optionalContains(Arrays.asList("cv.code", "cv.code || ' ' || c.name_et", "cv.code || ' ' || c.name_en"), "name", lookup.getName());
        
        qb.sort(Language.EN.equals(lookup.getLang()) ? "cv.code, c.name_et" : "cv.code, c.name_en");
        List<?> data = qb.select(
                "distinct cv.id, cv.code, c.name_et, c.name_en, c.id as curriculum_id, cv.school_department_id, sf.study_form_code, c.is_higher",
                em).getResultList();

        return StreamUtil.toMappedList(r -> {
            String code = resultAsString(r, 1);
            return new CurriculumVersionResult(resultAsLong(r, 0),
                    CurriculumUtil.versionName(code, resultAsString(r, 2)),
                    CurriculumUtil.versionName(code, resultAsString(r, 3)), resultAsLong(r, 4), resultAsLong(r, 5),
                    resultAsString(r, 6), Boolean.valueOf(!Boolean.TRUE.equals(resultAsBoolean(r, 7))));
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
        qb.requiredCriteria("cvo.curriculum_version_id = :curriculumVersionId", "curriculumVersionId",
                curriculumVersionId);
        qb.requiredCriteria("cm.module_code = :module_code", "module_code", FINAL_EXAM_CODE);
        qb.optionalCriteria(
                " exists(select id from lesson_plan_module "
                        + "where curriculum_version_omodule_id = cvo.id and teacher_id = :teacherId) ",
                "teacherId", user.getTeacherId());

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

    public FinalVocationalProtocolOccupationalModuleDto occupationModule(HoisUserDetails user, Long studyYearId,
            Long curriculumVersionOccupationModuleId, Boolean isFinalThesis) {
        FinalVocationalProtocolOccupationalModuleDto dto = new FinalVocationalProtocolOccupationalModuleDto();
        dto.setOccupationModuleStudents(
                occupationModuleStudents(user, curriculumVersionOccupationModuleId, isFinalThesis));
        dto.setTeacher(lessonPlanModuleTeacher(studyYearId, curriculumVersionOccupationModuleId));
        return dto;
    }

    public Collection<FinalVocationalProtocolStudentDto> occupationModuleStudents(HoisUserDetails user,
            Long occupationalModuleId, Boolean isFinalThesis) {
        Map<Long, FinalVocationalProtocolStudentDto> result = studentsForSelection(user, occupationalModuleId,
                isFinalThesis);
        return result.values();
    }
    
    private Map<Long, FinalVocationalProtocolStudentDto> studentsForSelection(HoisUserDetails user,
            Long occupationalModuleId, Boolean isFinalThesis) {
        return studentsForSelection(user, occupationalModuleId, isFinalThesis, null);
    }

    private Map<Long, FinalVocationalProtocolStudentDto> studentsForSelection(HoisUserDetails user,
            Long occupationalModuleId, Boolean isFinalThesis, Long notInProtocolId) {
        JpaNativeQueryBuilder studentsQb = new JpaNativeQueryBuilder("from student s " 
                + "join person p on p.id = s.person_id "
                + "join curriculum_version cv on s.curriculum_version_id = cv.id "
                + "join curriculum_version_omodule cvo on cv.id = cvo.curriculum_version_id "
                + "left join student_group sg on s.student_group_id = sg.id");

        studentsQb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        studentsQb.requiredCriteria("cvo.id = :occupationalModuleId",
                "occupationalModuleId", occupationalModuleId);
        studentsQb.requiredCriteria("s.status_code in :activeStatuses", "activeStatuses",
                StudentStatus.STUDENT_STATUS_ACTIVE);
        
        studentsQb.filter("not exists (select p.id from protocol p "
                + "join protocol_vdata pvd on p.id = pvd.protocol_id "
                + "left join protocol_student ps on p.id = ps.protocol_id "
                + "where pvd.curriculum_version_omodule_id = " + occupationalModuleId + " and ps.student_id = s.id "
                + "and (ps.grade_code is null or ps.grade_code in ("
                + OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE.stream().map(g -> "'" + g + "'").collect(Collectors.joining(", ")) + ")))");
        
        studentsQb.optionalCriteria(
                "s.id not in (select ps.student_id from protocol_student ps where ps.protocol_id = :protocolId)",
                "protocolId", notInProtocolId);

        if (isFinalThesis.booleanValue()) {
            studentsQb.filter("s.id in (select ft.student_id from final_thesis ft "
                    + "join student s on s.id = ft.student_id " + "where ft.status_code = 'LOPUTOO_STAATUS_K')");
        }
        
        studentsQb.sort("p.firstname, p.lastname");
        List<?> students = studentsQb.select("distinct s.id, p.firstname, p.lastname, p.idcode, "
                + "s.status_code, sg.code, s.type_code", em).getResultList();

        return students.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> {
            FinalVocationalProtocolStudentDto dto = new FinalVocationalProtocolStudentDto();
            dto.setStudentId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullnameTypeSpecific(resultAsString(r, 1), resultAsString(r, 2),
                    resultAsString(r, 6)));
            dto.setIdcode(resultAsString(r, 3));
            dto.setStatus(resultAsString(r, 4));
            dto.setStudentGroup(resultAsString(r, 5));
            return dto;
        }));
    }

    public Protocol confirm(HoisUserDetails user, Protocol protocol) {
        setConfirmation(user, protocol);
        Protocol confirmedProtocol = EntityUtil.save(protocol, em);

        if (confirmedProtocol.getProtocolStudents().stream().anyMatch(ps -> ps.getGrade() == null)) {
            throw new ValidationFailedException("finalProtocol.messages.gradeNotSelectedForAllStudents");
        }

        sendStudentResultMessages(confirmedProtocol);
        return confirmedProtocol;
    }
}
