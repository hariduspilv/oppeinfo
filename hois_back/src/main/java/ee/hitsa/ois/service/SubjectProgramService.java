package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgramStudyContentTeacher;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgramTeacher;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.subject.subjectprogram.SubjectProgramTeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgramStudyContent;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.SubjectAssessment;
import ee.hitsa.ois.enums.SubjectProgramStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.commandobject.subject.subjectprogram.SubjectProgramForm;
import ee.hitsa.ois.web.commandobject.subject.subjectprogram.SubjectProgramSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.subject.subjectprogram.SubjectProgramDto;
import ee.hitsa.ois.web.dto.subject.subjectprogram.SubjectProgramSearchDto;
import ee.hitsa.ois.web.dto.subject.subjectprogram.SubjectProgramStudyContentDto;

@Transactional
@Service
public class SubjectProgramService {

    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Value("${hois.subject.program.lock}")
    private Integer lockTime;
    
    public Page<SubjectProgramSearchDto> searchMyPrograms(HoisUserDetails user, SubjectProgramSearchCommand cmd, Pageable pageable) {
        StringBuilder from = new StringBuilder("from subject_program sp ");
        from.append("join subject_program_teacher spt on sp.id = spt.subject_program_id ");
        from.append("join subject_study_period_teacher sspt on sspt.id = spt.subject_study_period_teacher_id ");
        from.append("join subject_study_period ssp on ssp.id = sp.subject_study_period_id ");
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).sort(pageable);
        qb.optionalCriteria("sp.status_code = :status", "status", cmd.getStatus());
        qb.optionalCriteria("sspt.teacher_id = :teacherId", "teacherId", user.isTeacher() ? user.getTeacherId() : cmd.getTeacher() != null ? cmd.getTeacher().getId() : null);
        qb.optionalCriteria("ssp.subject_id = :subjectId", "subjectId", cmd.getSubject());
        qb.optionalCriteria("ssp.study_period_id = :periodId", "periodId", cmd.getStudyPeriod());
        return JpaQueryUtil.pagingResult(qb, "distinct sp.id, ssp.subject_id, ssp.study_period_id, null as teacherid, sp.status_code, ssp.id as sspId, sp.is_joint",
                em, pageable).map(r -> {
            SubjectProgramSearchDto dto = new SubjectProgramSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setSubject(AutocompleteResult.of(em.getReference(Subject.class, resultAsLong(r, 1))));
            dto.setStudyPeriod(AutocompleteResult.of(em.getReference(StudyPeriod.class, resultAsLong(r, 2))));
            dto.setStatus(resultAsString(r, 4));
            dto.setSubjectStudyPeriod(resultAsLong(r, 5));
            dto.setJoint(resultAsBoolean(r, 6));
            return dto;
        });
    }

    public Page<SubjectProgramSearchDto> search(HoisUserDetails user, SubjectProgramSearchCommand cmd,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_program sp " +
                "join subject_study_period ssp on ssp.id = sp.subject_study_period_id ")
                .sort(pageable);

        qb.requiredCriteria("exists(select 1 from curriculum_version_hmodule_subject cvhms " +
                "join curriculum_version_hmodule cvhm on cvhms.curriculum_version_hmodule_id = cvhm.id " +
                "join curriculum_version cv on cvhm.curriculum_version_id = cv.id " +
                "join curriculum c on c.id = cv.curriculum_id " +
                "where c.teacher_id = :mainTeacherId and ssp.subject_id = cvhms.subject_id and c.school_id = :schoolId)",
                "mainTeacherId", user.getTeacherId());
        qb.parameter("schoolId", user.getSchoolId());

        qb.optionalCriteria("sp.status_code = :status", "status", cmd.getStatus());
        qb.optionalCriteria("exists(select 1 from subject_study_period_teacher sspt " +
                "join subject_program_teacher spt on sspt.id = spt.subject_study_period_teacher_id " +
                "where sspt.teacher_id = :teacherId and sp.id = spt.subject_program_id)", "teacherId", cmd.getTeacher());
        qb.optionalCriteria("ssp.subject_id = :subjectId", "subjectId", cmd.getSubject());
        qb.optionalCriteria("ssp.study_period_id = :periodId", "periodId", cmd.getStudyPeriod());
        return JpaQueryUtil.pagingResult(qb, "distinct sp.id, ssp.subject_id, ssp.study_period_id, " +
                        "(select string_agg(p.firstname || ' ' || p.lastname, ', ' order by p.lastname, p.firstname) from subject_program_teacher spt " +
                            "join subject_study_period_teacher sspt on sspt.id = spt.subject_study_period_teacher_id " +
                            "join teacher t on sspt.teacher_id = t.id " +
                            "join person p on t.person_id = p.id " +
                            "where sp.id = spt.subject_program_id) as teachers, " +
                        "sp.status_code, ssp.id as sspId, sp.is_joint",
                em, pageable).map(r -> {
            SubjectProgramSearchDto dto = new SubjectProgramSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setSubject(AutocompleteResult.of(em.getReference(Subject.class, resultAsLong(r, 1))));
            dto.setStudyPeriod(AutocompleteResult.of(em.getReference(StudyPeriod.class, resultAsLong(r, 2))));
            dto.setTeachers(resultAsString(r, 3));
            dto.setStatus(resultAsString(r, 4));
            dto.setSubjectStudyPeriod(resultAsLong(r, 5));
            dto.setJoint(resultAsBoolean(r, 6));
            return dto;
        });
    }
    
    public SubjectProgramDto get(SubjectProgram program) {
        SubjectProgramDto dto = SubjectProgramDto.of(program);
        dto.setSubjectId(EntityUtil.getId(program.getSubjectStudyPeriod().getSubject()));
        List<?> results = em.createNativeQuery("select distinct c.teacher_id " + 
                "from subject_program sp " +
                "join subject_program_teacher spt on sp.id = spt.subject_program_id " +
                "join subject_study_period_teacher sspt on sspt.id = spt.subject_study_period_teacher_id " +
                "join subject_study_period ssp on ssp.id = sspt.subject_study_period_id " + 
                "join curriculum_version_hmodule_subject hms on hms.subject_id = ssp.subject_id "+ 
                "join curriculum_version_hmodule hm on hm.id = hms.curriculum_version_hmodule_id " + 
                "join curriculum_version cv on cv.id = hm.curriculum_version_id " + 
                "join curriculum c on c.id = cv.curriculum_id " + 
                "where sp.id = ?1 and c.teacher_id is not null")
        .setParameter(1, program.getId()).getResultList();
        dto.setSupervisorIds(StreamUtil.toMappedSet(r -> {
            return resultAsLong(r, 0);
        }, results));
        return dto;
    }

    public SubjectProgram create(HoisUserDetails user, SubjectProgramForm form) {
        SubjectProgram program = new SubjectProgram();
        boolean isJoint = Boolean.TRUE.equals(form.getIsJoint());
        SubjectStudyPeriod ssp = em.getReference(SubjectStudyPeriod.class, form.getSubjectStudyPeriodId());

        if (isJoint) {
            List<Integer> programs = em.createQuery("select 1 from SubjectProgram sp " +
                    "join sp.teachers spt " +
                    // if joint then should have at least one program with same period
                    "where spt.subjectStudyPeriodTeacher.subjectStudyPeriod.id = :sspId and sp.isJoint = true", Integer.class)
                    .setParameter("sspId", form.getSubjectStudyPeriodId())
                    .setMaxResults(1)
                    .getResultList();
            ValidationFailedException.throwIf(!programs.isEmpty(),
                    "subjectProgram.error.hasalreadyjointsubjectprogram");
            ValidationFailedException.throwIf(!EntityUtil.getId(ssp.getSubject()).equals(form.getSubjectId()),
                    "main.messages.error.nopermission");
            ssp.getTeachers().forEach(sspt -> {
                SubjectProgramTeacher pt = new SubjectProgramTeacher();
                pt.setSubjectStudyPeriodTeacher(sspt);
                pt.setSubjectProgram(program);
                program.getTeachers().add(pt);
            });
        } else {
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period ssp "
                    + "inner join subject s on s.id = ssp.subject_id "
                    + "inner join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id "
                    + "inner join teacher t on t.id = sspt.teacher_id").limit(1);
            qb.requiredCriteria("t.id = :teacherId", "teacherId", user.isSchoolAdmin() ? form.getTeacherId() : user.getTeacherId());
            qb.requiredCriteria("s.id = :sId", "sId", form.getSubjectId());
            qb.requiredCriteria("ssp.id = :sspId", "sspId", form.getSubjectStudyPeriodId());
            List<?> results = qb.select("sspt.id", em).getResultList();
            ValidationFailedException.throwIf(results.size() != 1, "main.messages.error.nopermission");
            SubjectStudyPeriodTeacher reference = em.getReference(SubjectStudyPeriodTeacher.class, resultAsLong(results.get(0), 0));
            long individualProgramCount = reference.getSubjectProgramTeachers().stream().filter(pt ->
                    !Boolean.TRUE.equals(pt.getSubjectProgram().getIsJoint())).count();
            ValidationFailedException.throwIf(individualProgramCount > 0,
                    "subjectProgram.error.hasalreadysubjectprogram");
            SubjectProgramTeacher programTeacher = new SubjectProgramTeacher();
            programTeacher.setSubjectProgram(program);
            programTeacher.setSubjectStudyPeriodTeacher(reference);
            program.getTeachers().add(programTeacher);
        }
        program.setSubjectStudyPeriod(ssp);
        return save(user, program, form);
    }

    public SubjectProgram save(HoisUserDetails user, SubjectProgram program, SubjectProgramForm form) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.bindToEntity(form, program, classifierRepository, "studyContents", "subjectId", "subjectStudyPeriodId", "supervisorIds");

        program.setStatus(em.getReference(Classifier.class, SubjectProgramStatus.AINEPROGRAMM_STAATUS_I.name()));
        if (user.isTeacher()) {
            program.getTeachers().stream()
                    .filter(pt -> user.getTeacherId().equals(
                            EntityUtil.getId(pt.getSubjectStudyPeriodTeacher().getTeacher())))
                    .findAny()
                    .ifPresent(programTeacher -> {
                        programTeacher.setIsReady(Boolean.FALSE);
                        programTeacher.setIsReadyDt(null);
                    });
        }

        assertLock(user, program);
        assertVersion(program, form);

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject s "
                + "join subject_study_period ssp on ssp.subject_id = s.id ").limit(1);
        qb.requiredCriteria("ssp.id = :sspId", "sspId",
                EntityUtil.getId(program.getSubjectStudyPeriod()));
        List<?> results = qb.select("s.assessment_code", em).getResultList();
        if (results.size() != 1) {
            throw new ValidationFailedException("subjectProgram.error.nosubjectassessment");
        }
        Classifier assessment = em.getReference(Classifier.class, resultAsString(results.get(0), 0));
        if (ClassifierUtil.equals(SubjectAssessment.HINDAMISVIIS_A, assessment)) {
            program.setGrade0Description(null);
            program.setGrade1Description(null);
            program.setGrade2Description(null);
            program.setGrade3Description(null);
            program.setGrade4Description(null);
            program.setGrade5Description(null);
        } else if (ClassifierUtil.equals(SubjectAssessment.HINDAMISVIIS_E, assessment)) {
            program.setPassDescription(null);
            program.setNpassDescription(null);
        } else if (ClassifierUtil.equals(SubjectAssessment.HINDAMISVIIS_H, assessment)) {
            program.setPassDescription(null);
            program.setNpassDescription(null);
            program.setGrade0Description(null);
            program.setGrade1Description(null);
            program.setGrade2Description(null);
        }

        Map<Long, SubjectProgramTeacher> mappedBySsptTeachers = program.getTeachers().stream()
                .collect(Collectors.toMap(pt -> EntityUtil.getId(pt.getSubjectStudyPeriodTeacher()), pt -> pt, (o, n) -> o));
        updateStudyContents(program, form.getStudyContents(), mappedBySsptTeachers);
        return EntityUtil.save(program, em);
    }

    public void delete(HoisUserDetails user, SubjectProgram program) {
        assertLock(user, program);
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(program, em);
    }

    public Set<AutocompleteResult> getProgramsRelatedToTeacher(HoisUserDetails user, Subject subject) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_program sp "
                + "join subject_study_period ssp on ssp.id = sp.subject_study_period_id "
                + "join study_period sper on sper.id = ssp.study_period_id "
                + "join study_year sy on sy.id = sper.study_year_id "
                + "join classifier cl on cl.code = sy.year_code "
                + "join subject s on s.id = ssp.subject_id ");
        if (user.isTeacher()) {
            qb.requiredCriteria("exists(select 1 from subject_program_teacher spt " +
                    "join subject_study_period_teacher sspt on spt.subject_study_period_teacher_id = sspt.id " +
                    "where sspt.teacher_id = :teacherId and spt.subject_program_id = sp.id)", "teacherId", user.getTeacherId());
        }
        qb.requiredCriteria("s.id = :subjectId", "subjectId", subject.getId());
        List<?> results = qb.select("sp.id, s.name_et as sEt, coalesce(s.name_en, s.name_et) as sEn, " +
                "sper.name_et as spEt, coalesce(sper.name_en, sper.name_et) as spEn, " +
                "cl.name_et as clEt, coalesce(cl.name_en, cl.name_et) as clEn, " +
                "(select string_agg(distinct p.firstname || ' ' || p.lastname, ', ') " +
                    "from subject_program_teacher spt " +
                    "join subject_study_period_teacher sspt on spt.subject_study_period_teacher_id = sspt.id " +
                    "join teacher t on sspt.teacher_id = t.id " +
                    "join person p on t.person_id = p.id " +
                "where spt.subject_program_id = sp.id) as teachers", em).getResultList();
        return StreamUtil.toMappedSet(r -> {
            return new AutocompleteResult(resultAsLong(r, 0),
                    resultAsString(r, 1) + " - " + resultAsString(r, 3) + " (" + resultAsString(r, 5) + ")" + " - " + resultAsString(r, 7),
                    resultAsString(r, 2) + " - " + resultAsString(r, 4) + " (" + resultAsString(r, 6) + ")" + " - " + resultAsString(r, 7));
        }, results);
    }

    public SubjectProgram confirm(HoisUserDetails user, SubjectProgram program) {
        assertLock(user, program);
        EntityUtil.setUsername(user.getUsername(), em);
        program.setConfirmed(LocalDateTime.now());
        program.setConfirmedBy(em.getReference(Person.class, user.getPersonId()).getFullname());
        program.setStatus(em.getReference(Classifier.class, SubjectProgramStatus.AINEPROGRAMM_STAATUS_K.name()));
        return EntityUtil.save(program, em);
    }

    public SubjectProgram complete(HoisUserDetails user, SubjectProgram program) {
        assertLock(user, program);
        EntityUtil.setUsername(user.getUsername(), em);

        if (program.getRejectInfo() != null) {
            program.setRejectInfo(null);
        }
        if (user.isTeacher()) {
            program.getTeachers().stream()
                    .filter(pt -> user.getTeacherId().equals(
                            EntityUtil.getId(pt.getSubjectStudyPeriodTeacher().getTeacher())))
                    .findAny()
                    .ifPresent(programTeacher -> {
                        programTeacher.setIsReady(Boolean.TRUE);
                        programTeacher.setIsReadyDt(LocalDateTime.now());
                    });
        }
        if (Boolean.TRUE.equals(program.getIsJoint())) {
            if (user.isTeacher()) {
                boolean everyTeacherCompleted = program.getTeachers().stream()
                        .allMatch(pt -> Boolean.TRUE.equals(pt.getIsReady()));
                if (everyTeacherCompleted) {
                    program.setStatus(em.getReference(Classifier.class, SubjectProgramStatus.AINEPROGRAMM_STAATUS_V.name()));
                }
            } else {
                program.setStatus(em.getReference(Classifier.class, SubjectProgramStatus.AINEPROGRAMM_STAATUS_V.name()));
            }
        } else {
            program.setStatus(em.getReference(Classifier.class, SubjectProgramStatus.AINEPROGRAMM_STAATUS_V.name()));
        }
        unlockProgram(user, program);
        return EntityUtil.save(program, em);
    }

    private void updateStudyContents(SubjectProgram program, List<SubjectProgramStudyContentDto> studyContents,
                                     Map<Long, SubjectProgramTeacher> mappedBySsptTeachers) {
        EntityUtil.bindEntityCollection(program.getStudyContents(), SubjectProgramStudyContent::getId,
                studyContents, SubjectProgramStudyContentDto::getId,
                dto -> createStudyContent(program, dto, mappedBySsptTeachers),
                (dto, entity) -> updateStudyContent(dto, entity, mappedBySsptTeachers));
    }
    
    private void updateStudyContent(SubjectProgramStudyContentDto dto, SubjectProgramStudyContent content,
                                    Map<Long, SubjectProgramTeacher> mappedBySsptTeachers) {
        EntityUtil.bindToEntity(dto, content, classifierRepository, "teachers", "teacher", "capacityType", "subjectProgramId");
        content.setTeacher(EntityUtil.getOptionalOne(Teacher.class, dto.getTeacher(), em));
        content.setCapacityType(EntityUtil.getOptionalOne(dto.getCapacityType(), em));
        EntityUtil.bindEntityCollection(content.getTeachers(),
                contentTeacher -> EntityUtil.getId(contentTeacher.getTeacher().getSubjectStudyPeriodTeacher()),
                dto.getTeachers(), ssptId -> {
                    SubjectProgramStudyContentTeacher contentTeacher = new SubjectProgramStudyContentTeacher();
                    contentTeacher.setStudyContent(content);
                    contentTeacher.setTeacher(mappedBySsptTeachers.getOrDefault(ssptId, null));
                    return contentTeacher;
                });
    }
    
    private SubjectProgramStudyContent createStudyContent(SubjectProgram program, SubjectProgramStudyContentDto dto,
                                                          Map<Long, SubjectProgramTeacher> mappedBySsptTeachers) {
        SubjectProgramStudyContent entity = EntityUtil.bindToEntity(dto, new SubjectProgramStudyContent(),
                "subjectProgramId", "teacher", "capacityType", "teachers");
        entity.setSubjectProgram(program);
        entity.setTeacher(EntityUtil.getOptionalOne(Teacher.class, dto.getTeacher(), em));
        entity.setCapacityType(EntityUtil.getOptionalOne(dto.getCapacityType(), em));
        EntityUtil.bindEntityCollection(entity.getTeachers(),
                contentTeacher -> EntityUtil.getId(contentTeacher.getTeacher().getSubjectStudyPeriodTeacher()),
                dto.getTeachers(), ssptId -> {
                    SubjectProgramStudyContentTeacher contentTeacher = new SubjectProgramStudyContentTeacher();
                    contentTeacher.setStudyContent(entity);
                    contentTeacher.setTeacher(mappedBySsptTeachers.getOrDefault(ssptId, null));
                    return contentTeacher;
                });
        return entity;
    }
    
    public Set<AutocompleteResult> getSubjectsViaPrograms(HoisUserDetails user, Long teacherId, SearchCommand lookup) {
        StringBuilder from = new StringBuilder("from subject_program sp ");
        from.append("join subject_program_teacher spt on sp.id = spt.subject_program_id ");
        from.append("join subject_study_period_teacher sspt on sspt.id = spt.subject_study_period_teacher_id ");
        from.append("join subject_study_period ssp on ssp.id = sspt.subject_study_period_id "); 
        from.append("join subject s on s.id = ssp.subject_id");
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).groupBy("s.id");
        qb.requiredCriteria("sspt.teacher_id = :teacherId", "teacherId", user.isTeacher() ? user.getTeacherId() : teacherId);
        qb.optionalContains("s.name_et", "nameEt", lookup.getName());
        List<?> results = qb.select("s.id, s.name_et, s.name_en, s.code, s.credits", em, true).getResultList();
        return getSubjects(results);
    }

    public Set<AutocompleteResult> getSubjectsViaCurriculums(HoisUserDetails user, SearchCommand lookup) {
        StringBuilder from = new StringBuilder("from curriculum c "); 
        from.append("join curriculum_version cv on c.id = cv.curriculum_id "); 
        from.append("left join curriculum_version_hmodule cvhm on cvhm.curriculum_version_id = cv.id "); 
        from.append("left join curriculum_version_hmodule_subject cvhms on cvhms.curriculum_version_hmodule_id = cvhm.id ");
        from.append("left join subject s on s.id = cvhms.subject_id");
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString());
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("c.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        qb.optionalContains("s.name_et", "nameEt", lookup.getName());
        List<?> results = qb.select("s.id, s.name_et, s.name_en, s.code, s.credits", em, true).getResultList();
        return getSubjects(results);
    }
    
    private static Set<AutocompleteResult> getSubjects(List<?> data) {
        return StreamUtil.toMappedSet(r -> {
            String code = resultAsString(r, 3);
            BigDecimal credits = resultAsDecimal(r, 4);
            return new AutocompleteResult(resultAsLong(r, 0),
                    String.format("%s - %s (%.1f EAP)", code, resultAsString(r, 1), credits),
                    String.format("%s - %s (%.1f EAP)", code, resultAsString(r, 2), credits));
        }, data);
    }

    public SubjectProgram reject(HoisUserDetails user, SubjectProgram program, String rejectInfo) {
        assertLock(user, program);
        EntityUtil.setUsername(user.getUsername(), em);
        program.setStatus(em.getReference(Classifier.class, SubjectProgramStatus.AINEPROGRAMM_STAATUS_I.name()));
        program.setRejectInfo(rejectInfo);
        if (program.getConfirmed() != null) {
            program.setConfirmed(null);
        }
        if (program.getConfirmedBy() != null) {
            program.setConfirmedBy(null);
        }
        return EntityUtil.save(program, em);
    }
    
    public SimpleEntry<String, Boolean> hasUnconfirmedSubjectPrograms(HoisUserDetails user) {
        if (user.isTeacher() || user.isSchoolAdmin()) {
            StringBuilder from = new StringBuilder("from curriculum c ");
            from.append("join curriculum_version cv on cv.curriculum_id = c.id ");
            from.append("join curriculum_version_hmodule cvhm on cvhm.curriculum_version_id = cv.id ");
            from.append("join curriculum_version_hmodule_subject cvhms on cvhms.curriculum_version_hmodule_id = cvhm.id ");
            from.append("join subject_study_period ssp on ssp.subject_id = cvhms.subject_id ");
            from.append("join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id ");
            from.append("join subject_program_teacher spt on sspt.id = spt.subject_study_period_teacher_id ");
            from.append("join subject_program spr on spt.subject_program_id = spr.id ");
            from.append("join study_period sp on sp.id = ssp.study_period_id ");
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).limit(1);
            if (user.isSchoolAdmin()) {
                qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
            } else {
                qb.requiredCriteria("c.teacher_id = :teacherId", "teacherId", user.getTeacherId());
            }
            qb.requiredCriteria("spr.status_code = :status", "status", SubjectProgramStatus.AINEPROGRAMM_STAATUS_V);
            qb.requiredCriteria("sp.end_date >= :now", "now", LocalDate.now());
            if (qb.select("spr.id", em).getResultList().size() == 1) {
                return new SimpleEntry<>("has", Boolean.TRUE);
            }
        }
        return new SimpleEntry<>("has", Boolean.FALSE);
    }
    
    public SimpleEntry<String, Boolean> hasUncompletedSubjectPrograms(HoisUserDetails user) {
        if (user.isTeacher()) {
            StringBuilder from = new StringBuilder("from subject_study_period_teacher sspt ");
            from.append("join subject_study_period ssp on sspt.subject_study_period_id = ssp.id ");
            from.append("join subject_program_teacher spt on sspt.id = spt.subject_study_period_teacher_id ");
            from.append("join subject_program spr on spt.subject_program_id = spr.id ");
            from.append("join study_period sp on sp.id = ssp.study_period_id ");
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).limit(1);
            qb.requiredCriteria("sspt.teacher_id = :teacherId", "teacherId", user.getTeacherId());
            qb.requiredCriteria("spr.status_code = :status and (not coalesce(spr.is_joint, false) " +
                    "or (spr.is_joint is true and not coalesce(spt.is_ready, false)))", "status", SubjectProgramStatus.AINEPROGRAMM_STAATUS_I);
            qb.requiredCriteria("sp.end_date >= :now and sp.start_date <= :now", "now", LocalDate.now());
            if (qb.select("spr.id", em).getResultList().size() == 1) {
                return new SimpleEntry<>("has", Boolean.TRUE);
            }
        }
        return new SimpleEntry<>("has", Boolean.FALSE);
    }

    public Map<String, Object> getAdditionalData(HoisUserDetails user, SubjectStudyPeriod ssp, boolean joint, Long teacherId) {
        Map<String, Object> map = new HashMap<>();
        map.put("period", AutocompleteResult.ofWithYear(ssp.getStudyPeriod()));
        map.put("teachers", ssp.getTeachers().stream()
                .filter(sspt -> joint ||
                        (user.isTeacher() && user.getTeacherId().equals(EntityUtil.getId(sspt.getTeacher()))) ||
                        (teacherId != null && teacherId.equals(EntityUtil.getId(sspt.getTeacher()))))
                .map(sspt -> {
                    SubjectProgramTeacherDto dto = new SubjectProgramTeacherDto();
                    dto.setTeacher(AutocompleteResult.of(sspt.getTeacher()));
                    dto.getTeacher().setId(sspt.getId());
                    dto.setTeacherId(sspt.getTeacher().getId());
                    return dto;
                }).collect(Collectors.toList()));
        return map;
    }

    public SubjectProgram lockProgram(HoisUserDetails user, SubjectProgram program) {
        program.setAccessed(LocalDateTime.now());
        program.setAccessedBy(user.getUsername());
        program.setAccessedUserId(user.getUserId());
        return program;
    }

    public SubjectProgram unlockProgram(HoisUserDetails user, SubjectProgram program) {
        if (!user.getUserId().equals(program.getAccessedUserId())) {
           return program;
        }
        program.setAccessed(null);
        program.setAccessedBy(null);
        program.setAccessedUserId(null);
        return program;
    }

    private void assertLock(HoisUserDetails user, SubjectProgram program) {
        ValidationFailedException.throwIf(program.getAccessedUserId() != null &&
                        !user.getUserId().equals(program.getAccessedUserId()) &&
                        program.getAccessed().isAfter(LocalDateTime.now().minusMinutes(lockTime)),
                "subjectProgram.error.locked");
    }

    private void assertVersion(SubjectProgram program, VersionedCommand versionedCommand) {
        if (program.getVersion() != null && !program.getVersion().equals(versionedCommand.getVersion())) {
            throw new OptimisticLockException(String.format("version mismatch for entity %s (is %d, but request has %d)",
                    program.getClass(), program.getVersion(), versionedCommand.getVersion()));
        }
    }
}
