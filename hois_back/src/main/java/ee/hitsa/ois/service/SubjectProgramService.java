package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
import ee.hitsa.ois.web.commandobject.subject.SubjectProgramForm;
import ee.hitsa.ois.web.commandobject.subject.SubjectProgramSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SubjectProgramDto;
import ee.hitsa.ois.web.dto.SubjectProgramSearchDto;
import ee.hitsa.ois.web.dto.SubjectProgramStudyContentDto;

@Transactional
@Service
public class SubjectProgramService {

    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;
    
    public Page<SubjectProgramSearchDto> searchMyPrograms(HoisUserDetails user, SubjectProgramSearchCommand cmd, Pageable pageable) {
        StringBuilder from = new StringBuilder("from subject_program sp ");
        from.append("join subject_study_period_teacher sspt on sspt.id = sp.subject_study_period_teacher_id ");
        from.append("join subject_study_period ssp on ssp.id = sspt.subject_study_period_id ");
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).sort(pageable);
        qb.optionalCriteria("sp.status_code = :status", "status", cmd.getStatus());
        qb.optionalCriteria("sspt.teacher_id = :teacherId", "teacherId", user.isTeacher() ? user.getTeacherId() : cmd.getTeacher() != null ? cmd.getTeacher().getId() : null);
        qb.optionalCriteria("ssp.subject_id = :subjectId", "subjectId", cmd.getSubject());
        qb.optionalCriteria("ssp.study_period_id = :periodId", "periodId", cmd.getStudyPeriod());
        return JpaQueryUtil.pagingResult(qb, "distinct sp.id, ssp.subject_id, ssp.study_period_id, sspt.teacher_id, sp.status_code, ssp.id as sspId",
                em, pageable).map(r -> {
            SubjectProgramSearchDto dto = new SubjectProgramSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setSubject(AutocompleteResult.of(em.getReference(Subject.class, resultAsLong(r, 1))));
            dto.setStudyPeriod(AutocompleteResult.of(em.getReference(StudyPeriod.class, resultAsLong(r, 2))));
            dto.setTeacher(AutocompleteResult.of(em.getReference(Teacher.class, resultAsLong(r, 3))));
            dto.setStatus(resultAsString(r, 4));
            dto.setSubjectStudyPeriod(resultAsLong(r, 5));
            return dto;
        });
    }

    public Page<SubjectProgramSearchDto> search(HoisUserDetails user, SubjectProgramSearchCommand cmd,
            Pageable pageable) {
        StringBuilder from = new StringBuilder("from subject_program sp ");
        from.append("join curriculum c on c.teacher_id = :mainTeacherId ");
        from.append("join curriculum_version cv on c.id = cv.curriculum_id "); 
        from.append("join curriculum_version_hmodule cvhm on cvhm.curriculum_version_id = cv.id "); 
        from.append("join curriculum_version_hmodule_subject cvhms on cvhms.curriculum_version_hmodule_id = cvhm.id ");
        from.append("join subject_study_period_teacher sspt on sspt.id = sp.subject_study_period_teacher_id ");
        from.append("join subject_study_period ssp on ssp.id = sspt.subject_study_period_id and ssp.subject_id = cvhms.subject_id ");
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).sort(pageable);
        qb.parameter("mainTeacherId", user.getTeacherId());
        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("sp.status_code = :status", "status", cmd.getStatus());
        qb.optionalCriteria("sspt.teacher_id = :teacherId", "teacherId", cmd.getTeacher() == null ? null : cmd.getTeacher().getId());
        qb.optionalCriteria("ssp.subject_id = :subjectId", "subjectId", cmd.getSubject());
        qb.optionalCriteria("ssp.study_period_id = :periodId", "periodId", cmd.getStudyPeriod());
        return JpaQueryUtil.pagingResult(qb, "distinct sp.id, ssp.subject_id, ssp.study_period_id, sspt.teacher_id, sp.status_code, ssp.id as sspId",
                em, pageable).map(r -> {
            SubjectProgramSearchDto dto = new SubjectProgramSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setSubject(AutocompleteResult.of(em.getReference(Subject.class, resultAsLong(r, 1))));
            dto.setStudyPeriod(AutocompleteResult.of(em.getReference(StudyPeriod.class, resultAsLong(r, 2))));
            dto.setTeacher(AutocompleteResult.of(em.getReference(Teacher.class, resultAsLong(r, 3))));
            dto.setStatus(resultAsString(r, 4));
            dto.setSubjectStudyPeriod(resultAsLong(r, 5));
            return dto;
        });
    }
    
    public SubjectProgramDto get(SubjectProgram program) {
        SubjectProgramDto dto = SubjectProgramDto.of(program);
        Long subjectId = ((List<?>) em.createNativeQuery("select ssp.subject_id from subject_program sp " +
                "join subject_study_period_teacher sspt on sspt.id = sp.subject_study_period_teacher_id " +
                "join subject_study_period ssp on ssp.id = sspt.subject_study_period_id " +
                "where sp.id = ?1").setParameter(1, program.getId()).getResultList()).stream().map(row -> resultAsLong(row, 0)).findAny().orElse(null);
        dto.setSubjectId(subjectId);
        List<?> results = em.createNativeQuery("select distinct c.teacher_id " + 
                "from subject_program sp " + 
                "join subject_study_period_teacher sspt on sspt.id = sp.subject_study_period_teacher_id " + 
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
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period ssp "
                + "inner join subject s on s.id = ssp.subject_id "
                + "inner join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id "
                + "inner join teacher t on t.id = sspt.teacher_id").limit(1);
        qb.requiredCriteria("t.id = :teacherId", "teacherId", user.isSchoolAdmin() ? form.getTeacherId() : user.getTeacherId());
        qb.requiredCriteria("s.id = :sId", "sId", form.getSubjectId());
        qb.requiredCriteria("ssp.id = :sspId", "sspId", form.getSubjectStudyPeriodId());
        List<?> results = qb.select("sspt.id", em).getResultList();
        if (results.size() != 1) {
            throw new ValidationFailedException("main.messages.error.nopermission");
        }
        SubjectStudyPeriodTeacher reference = em.getReference(SubjectStudyPeriodTeacher.class, resultAsLong(results.get(0), 0));
        if (reference.getSubjectPrograms().size() > 0) {
            throw new ValidationFailedException("subjectProgram.error.hasalreadysubjectprogram");
        }
        program.setSubjectStudyPeriodTeacher(reference);
        return save(user, program, form);
    }

    public SubjectProgram save(HoisUserDetails user, SubjectProgram program, SubjectProgramForm form) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.bindToEntity(form, program, classifierRepository, "studyContents", "subjectId", "subjectStudyPeriodId", "supervisorIds");
        program.setStatus(em.getReference(Classifier.class, SubjectProgramStatus.AINEPROGRAMM_STAATUS_I.name()));

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject s "
                + "inner join subject_study_period ssp on ssp.subject_id = s.id "
                + "inner join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id").limit(1);
        qb.requiredCriteria("sspt.id = :ssptId", "ssptId", program.getSubjectStudyPeriodTeacher().getId());
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
        
        updateStudyContents(program, form.getStudyContents());
        return EntityUtil.save(program, em);
    }

    public void delete(SubjectProgram program) {
        EntityUtil.deleteEntity(program, em);
    }

    public Set<AutocompleteResult> getProgramsRelatedToTeacher(HoisUserDetails user, Subject subject) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_program sp "
                + "inner join subject_study_period_teacher sspt on sspt.id = sp.subject_study_period_teacher_id "
                + "inner join teacher t on t.id = sspt.teacher_id "
                + "inner join subject_study_period ssp on ssp.id = sspt.subject_study_period_id "
                + "inner join study_period sper on sper.id = ssp.study_period_id "
                + "inner join study_year sy on sy.id = sper.study_year_id "
                + "inner join classifier cl on cl.code = sy.year_code "
                + "inner join subject s on s.id = ssp.subject_id "
                + "join person p on p.id = t.person_id ");
        if (user.isTeacher()) {
            qb.requiredCriteria("t.id = :teacherId", "teacherId", user.getTeacherId());
        }
        qb.requiredCriteria("s.id = :subjectId", "subjectId", subject.getId());
        List<?> results = qb.select("sp.id, s.name_et as sEt, s.name_en as sEn, sper.name_et as spEt, sper.name_en as spEn, cl.name_et as clEt, cl.name_en as clEn, p.firstname || ' ' || p.lastname", em).getResultList();
        return StreamUtil.toMappedSet(r -> {
            return new AutocompleteResult(resultAsLong(r, 0),
                    resultAsString(r, 1) + " - " + resultAsString(r, 3) + " (" + resultAsString(r, 5) + ")" + (user.isSchoolAdmin() ? " - " + resultAsString(r, 7) : ""),
                    resultAsString(r, 2) + " - " + resultAsString(r, 4) + " (" + resultAsString(r, 6) + ")" + (user.isSchoolAdmin() ? " - " + resultAsString(r, 7) : ""));
        }, results);
    }

    public SubjectProgram confirm(HoisUserDetails user, SubjectProgram program) {
        EntityUtil.setUsername(user.getUsername(), em);
        program.setConfirmed(LocalDateTime.now());
        program.setConfirmedBy(em.getReference(Person.class, user.getPersonId()).getFullname());
        program.setStatus(em.getReference(Classifier.class, SubjectProgramStatus.AINEPROGRAMM_STAATUS_K.name()));
        return EntityUtil.save(program, em);
    }

    public SubjectProgram complete(HoisUserDetails user, SubjectProgram program) {
        EntityUtil.setUsername(user.getUsername(), em);
        program.setStatus(em.getReference(Classifier.class, SubjectProgramStatus.AINEPROGRAMM_STAATUS_V.name()));
        if (program.getRejectInfo() != null) {
            program.setRejectInfo(null);
        }
        return EntityUtil.save(program, em);
    }

    private void updateStudyContents(SubjectProgram program, Set<SubjectProgramStudyContentDto> studyContents) {
        EntityUtil.bindEntityCollection(program.getStudyContents(), SubjectProgramStudyContent::getId,
                studyContents, SubjectProgramStudyContentDto::getId,
                dto -> createStudyContent(program, dto), this::updateStudyContent);
    }
    
    private void updateStudyContent(SubjectProgramStudyContentDto dto, SubjectProgramStudyContent content) {
        EntityUtil.bindToEntity(dto, content, classifierRepository);
    }
    
    private static SubjectProgramStudyContent createStudyContent(SubjectProgram program, SubjectProgramStudyContentDto dto) {
        SubjectProgramStudyContent entity = new SubjectProgramStudyContent();
        entity.setSubjectProgram(program);
        return EntityUtil.bindToEntity(dto, entity);
    }
    
    public Set<AutocompleteResult> getSubjectsViaPrograms(HoisUserDetails user, Long teacherId) {
        StringBuilder from = new StringBuilder("from subject_program sp "); 
        from.append("join subject_study_period_teacher sspt on sspt.id = sp.subject_study_period_teacher_id "); 
        from.append("join subject_study_period ssp on ssp.id = sspt.subject_study_period_id "); 
        from.append("join subject s on s.id = ssp.subject_id");
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).groupBy("s.id");
        qb.requiredCriteria("sspt.teacher_id = :teacherId", "teacherId", user.isTeacher() ? user.getTeacherId() : teacherId);
        List<?> results = qb.select("s.id, s.name_et, s.name_en, s.code, s.credits", em, true).getResultList();
        return getSubjects(results);
    }

    public Set<AutocompleteResult> getSubjectsViaCurriculums(HoisUserDetails user) {
        StringBuilder from = new StringBuilder("from curriculum c "); 
        from.append("join curriculum_version cv on c.id = cv.curriculum_id "); 
        from.append("left join curriculum_version_hmodule cvhm on cvhm.curriculum_version_id = cv.id "); 
        from.append("left join curriculum_version_hmodule_subject cvhms on cvhms.curriculum_version_hmodule_id = cvhm.id ");
        from.append("left join subject s on s.id = cvhms.subject_id");
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString());
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("c.teacher_id = :teacherId", "teacherId", user.getTeacherId());
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
            from.append("join subject_program spr on spr.subject_study_period_teacher_id = sspt.id ");
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
            from.append("join subject_program spr on spr.subject_study_period_teacher_id = sspt.id ");
            from.append("join study_period sp on sp.id = ssp.study_period_id ");
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).limit(1);
            qb.requiredCriteria("sspt.teacher_id = :teacherId", "teacherId", user.getTeacherId());
            qb.requiredCriteria("spr.status_code = :status", "status", SubjectProgramStatus.AINEPROGRAMM_STAATUS_I);
            qb.requiredCriteria("sp.end_date >= :now and sp.start_date <= :now", "now", LocalDate.now());
            if (qb.select("spr.id", em).getResultList().size() == 1) {
                return new SimpleEntry<>("has", Boolean.TRUE);
            }
        }
        return new SimpleEntry<>("has", Boolean.FALSE);
    }
}
