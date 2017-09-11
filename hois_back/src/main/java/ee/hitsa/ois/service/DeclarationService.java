package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.parameterAsTimestamp;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Declaration;
import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.DeclarationSearchCommand;
import ee.hitsa.ois.web.commandobject.DeclarationSubjectForm;
import ee.hitsa.ois.web.commandobject.UsersSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.DeclarationDto;
import ee.hitsa.ois.web.dto.DeclarationSubjectDto;
import ee.hitsa.ois.web.dto.SubjectSearchDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

@Transactional
@Service
public class DeclarationService {

    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private EntityManager em;

    private static final String DECLARATION_SELECT = " distinct d.id as d_id, s.id as sId, "
            + "p.firstname, p.lastname, p.idcode, cv.id as cv_id, cv.code as cv_code, "
            + "sg.id as sgId, sg.code as sg_code, d.inserted, d.status_code, d.confirm_date, "
            + "sp.id, sp.name_et, sp.name_en ";
    private static final String DECLARATION_FROM = "from declaration d "
            + "left join student s on s.id = d.student_id "
            + "left join person p on p.id = s.person_id "
            + "left join student_group sg on sg.id = s.student_group_id "
            + "left join curriculum_version cv on cv.id = s.curriculum_version_id "
            + "left join classifier status on status.code = d.status_code "
            + "left join study_period sp on sp.id = d.study_period_id ";

    private static final String SUBJECT_FROM = "from subject_study_period ssp left join subject s on s.id = ssp.subject_id "
            + "left join curriculum_version_hmodule_subject cvhms on cvhms.subject_id = s.id "
            + "left join curriculum_version_hmodule cvhm on cvhm.id =  cvhms.curriculum_version_hmodule_id "
            + "join classifier c on c.code = s.assessment_code left join ("
            + "select ssp2.id as ssp2Id, array_agg(p.firstname || ' ' || p.lastname) as teacher "
            + "from subject_study_period ssp2 "
            + "left join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp2.id "
            + "left join teacher t on t.id = sspt.teacher_id left join person p on p.id = t.person_id "
            + "group by ssp2.id) teachers on ssp.id  = teachers.ssp2Id";

    private static final String SUBJECT_CURRICULUM_SELECT = " distinct ssp.id as ssp1Id, s.id as subjectId, "
            + "s.name_et, s.name_en, s.code, s.credits, c.value, cvhm.id as moduleId, "
            + "cvhms.is_optional, array_to_string(teachers.teacher, ', ')";
    
    private static final String SUBJECT_EXTRACURRICULUM_SELECT = " distinct ssp.id as ssp1Id, s.id as subjectId, "
            + "s.name_et, s.name_en, s.code, s.credits, c.value, null as moduleId, "
            + " false as isOptional, array_to_string(teachers.teacher, ', ')";

    public Page<DeclarationDto> search(DeclarationSearchCommand criteria, Pageable pageable) {

        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(DECLARATION_FROM).sort(pageable);

        qb.requiredCriteria("d.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        qb.optionalCriteria("d.status_code = :status", "status", criteria.getStatus());
        qb.optionalCriteria("d.inserted >= :insertedFrom", "insertedFrom", criteria.getInsertedFrom(),
                DateUtils::firstMomentOfDay);
        qb.optionalCriteria("d.inserted <= :insertedThru", "insertedThru", criteria.getInsertedThru(),
                DateUtils::lastMomentOfDay);
        qb.optionalCriteria("d.confirm_date >= :confirmedFrom", "confirmedFrom", criteria.getConfirmedFrom(),
                DateUtils::firstMomentOfDay);
        qb.optionalCriteria("d.confirm_date <= :confirmedThru", "confirmedThru", criteria.getConfirmedThru(),
                DateUtils::lastMomentOfDay);

        qb.optionalCriteria("s.curriculum_version_id = :curriculumVersion", "curriculumVersion",
                criteria.getCurriculumVersion());
        qb.optionalCriteria("s.student_group_id in (:studentGroups)", "studentGroups", criteria.getStudentGroups());
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name",
                criteria.getStudentsName());

        if (Boolean.TRUE.equals(criteria.getRepeatingDeclaration())) {
            qb.filter("exists(select ds2.id " + "from declaration_subject ds2 "
                    + "left join declaration d2 on d2.id = ds2.declaration_id "
                    + "join subject_study_period ssp on ssp.id = ds2.subject_study_period_id "
                    + "where declaration_id <> d.id and d2.student_id = d.student_id and ssp.subject_id in( "
                    + "select ssp2.subject_id from declaration_subject ds3 "
                    + "join subject_study_period ssp2 on ssp2.id = ds3.subject_study_period_id "
                    + "where ds3.declaration_id = d.id ))");
        }

        Page<Object[]> results = JpaQueryUtil.pagingResult(qb, DECLARATION_SELECT, em, pageable);
        return results.map(r -> {
            DeclarationDto dto = new DeclarationDto();
            dto.setId(resultAsLong(r, 0));

            StudentSearchDto studentDto = new StudentSearchDto();
            studentDto.setId(resultAsLong(r, 1));
            studentDto.setFullname(PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3)));
            studentDto.setIdcode(resultAsString(r, 4));
            studentDto.setCurriculumVersion(
                    new AutocompleteResult(resultAsLong(r, 5), resultAsString(r, 6), resultAsString(r, 6)));
            studentDto.setStudentGroup(
                    new AutocompleteResult(resultAsLong(r, 7), resultAsString(r, 8), resultAsString(r, 8)));
            dto.setStudent(studentDto);

            dto.setInserted(resultAsLocalDateTime(r, 9));
            dto.setStatus(resultAsString(r, 10));
            dto.setConfirmDate(resultAsLocalDate(r, 11));
            return dto;
        });
    }

    public Page<DeclarationDto> searchStudentsPreviousDeclarations(Long studentId, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(DECLARATION_FROM).sort(pageable);

        qb.requiredCriteria("d.study_period_id in (select id from study_period where end_date < :now)", "now", LocalDate.now());
        qb.requiredCriteria("d.student_id = :studentId", "studentId", studentId);

        Page<Object[]> results = JpaQueryUtil.pagingResult(qb, DECLARATION_SELECT, em, pageable);
        return results.map(r -> {
            DeclarationDto dto = new DeclarationDto();
            dto.setId(resultAsLong(r, 0));
            dto.setInserted(resultAsLocalDateTime(r, 9));
            dto.setStatus(resultAsString(r, 10));
            dto.setConfirmDate(resultAsLocalDate(r, 11));
            dto.setStudyPeriod(
                    new AutocompleteResult(resultAsLong(r, 12), resultAsString(r, 13), resultAsString(r, 14)));
            return dto;
        });
    }

    public boolean canCreate(HoisUserDetails user, Long studentId) {
        if(!user.isStudent() && !user.isSchoolAdmin()) {
            return false;
        }

        if(getCurrent(user.getSchoolId(), studentId) != null) {
            // declaration already exists
            return false;
        }

        Student student = em.getReference(Student.class, studentId);
        if(user.isStudent()) {
            return UserUtil.isSame(user, student) && StudentUtil.isStudying(student);
        }
        return UserUtil.isSchoolAdmin(user, student.getSchool());
    }

    public boolean studentHasPreviousDeclarations(Long studentId) {
        Query q = em.createNativeQuery("select id from declaration where student_id = ?1 and study_period_id in (select id from study_period where end_date < ?2)");
        q.setParameter(1,  studentId);
        q.setParameter(2, parameterAsTimestamp(LocalDate.now()));
        return !q.setMaxResults(1).getResultList().isEmpty();
    }

    public void deleteSubject(DeclarationSubject subject) {
        EntityUtil.deleteEntity(subject, em);
    }

    /*
     * TODO: it would be better to send CurriculumVersionHigherModuleSubject.id
     * instead of isOptional and moduleId. Extra validation could be added to
     * see if moduleSubject and subject match
     */
    public DeclarationSubject addSubject(HoisUserDetails user, DeclarationSubjectForm form) {
        DeclarationSubject declarationSubject = new DeclarationSubject();
        Declaration declaration = em.getReference(Declaration.class, form.getDeclaration());
        SubjectStudyPeriod ssp = em.getReference(SubjectStudyPeriod.class, form.getSubjectStudyPeriod());

        AssertionFailedException.throwIf(!EntityUtil.getId(declaration.getStudyPeriod()).equals(EntityUtil.getId(ssp.getStudyPeriod())),
                "Declaration's and study period's ids does not match");
        UserUtil.assertSameSchool(user, ssp.getStudyPeriod().getStudyYear().getSchool());
        
        declarationSubject.setDeclaration(declaration);
        declarationSubject.setSubjectStudyPeriod(ssp);
        Long higherModule = form.getCurriculumVersionHigherModule();
        if(higherModule != null) {
            declarationSubject.setModule(em.getReference(CurriculumVersionHigherModule.class, higherModule));
        }
        declarationSubject.setIsOptional(form.getIsOptional());
        return EntityUtil.save(declarationSubject, em);
    }

    public Declaration create(HoisUserDetails user, Long studentId) {
        AssertionFailedException.throwIf(!canCreate(user, studentId), "You cannot create declaration!");

        Student student = em.getReference(Student.class, studentId);
        Long studyPeriodId = studyYearService.getCurrentStudyPeriod(user.getSchoolId());
        StudyPeriod studyPeriod = em.getReference(StudyPeriod.class, studyPeriodId);

        AssertionFailedException.throwIf(!EntityUtil.getId(studyPeriod.getStudyYear().getSchool()).equals(user.getSchoolId()),
                "User's and studyPeriod's schools does not match!");

        Declaration declaration = new Declaration();
        declaration.setStudent(student);
        declaration.setStudyPeriod(studyPeriod);
        setDeclarationStatus(declaration, DeclarationStatus.OPINGUKAVA_STAATUS_S);
        return EntityUtil.save(declaration, em);
    }

    public Declaration getCurrent(Long schoolId, Long studentId) {
        Long studyPeriodId = studyYearService.getCurrentStudyPeriod(schoolId);
        if (studyPeriodId == null) {
            return null;
        }

        TypedQuery<Declaration> q = em.createQuery("select d from Declaration d where d.student.id = ?1 and d.studyPeriod.id = ?2", Declaration.class);
        q.setParameter(1, studentId);
        q.setParameter(2, studyPeriodId);
        List<Declaration> result = q.setMaxResults(1).getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public Declaration confirm(String confirmer, Declaration declaration) {
        setDeclarationStatus(declaration, DeclarationStatus.OPINGUKAVA_STAATUS_K);
        declaration.setConfirmer(confirmer);
        declaration.setConfirmDate(LocalDate.now());
        return EntityUtil.save(declaration, em);
    }

    public Integer confirmAll(HoisUserDetails user) {
        Long studyPeriodId = studyYearService.getCurrentStudyPeriod(user.getSchoolId());
        TypedQuery<Declaration> q = em.createQuery("select d from Declaration where d.studyPeriod.id = ?1", Declaration.class);
        q.setParameter(1, studyPeriodId);
        List<Declaration> declarations = q.getResultList();
        int count = 0;
        Classifier confirmedStatus = em.getReference(Classifier.class, DeclarationStatus.OPINGUKAVA_STAATUS_K.name());
        for (Declaration declaration : declarations) {
            if (declaration.getConfirmDate() == null) {
                declaration.setStatus(confirmedStatus);
                declaration.setConfirmer(user.getUsername());
                declaration.setConfirmDate(LocalDate.now());
                EntityUtil.save(declaration, em);
                count++;
            }
        }
        return Integer.valueOf(count);
    }

    public Declaration removeConfirmation(Declaration declaration) {
        setDeclarationStatus(declaration, DeclarationStatus.OPINGUKAVA_STAATUS_S);
        declaration.setConfirmer(null);
        declaration.setConfirmDate(null);
        return EntityUtil.save(declaration, em);
    }

    public List<AutocompleteResult> getModules(Declaration declaration) {
        Student student = declaration.getStudent();
        return StreamUtil.toMappedList(AutocompleteResult::of, student.getCurriculumVersion().getModules().stream().filter(m -> Boolean.FALSE.equals(m.getMinorSpeciality())));
    }

    public List<DeclarationSubjectDto> getCurriculumSubjectOptions(Declaration declaration) {
        Long studyPeriod = EntityUtil.getId(declaration.getStudyPeriod());
        Student student = declaration.getStudent();
        Long CurriculumVersion = EntityUtil.getId(student.getCurriculumVersion());

        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(SUBJECT_FROM);

        qb.requiredCriteria("cvhm.curriculum_version_id = :curriculumVersionId", "curriculumVersionId",
                CurriculumVersion);
        qb.requiredCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", studyPeriod);

        List<?> result = qb.select(SUBJECT_CURRICULUM_SELECT, em).getResultList();

        return subjectsQueryResultToDto(result);
    }

    public List<DeclarationSubjectDto> getExtraCurriculumSubjectsOptions(Declaration declaration) {
        Long studyPeriod = EntityUtil.getId(declaration.getStudyPeriod());
        Long curriculumVersion = EntityUtil.getId(declaration.getStudent().getCurriculumVersion());

        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(SUBJECT_FROM);
        
        qb.requiredCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", studyPeriod);

        // do not select student's curriculum version's subjects
        qb.requiredCriteria(" ssp.subject_id not in "
                + "(select cs.subject_id "
                + "from curriculum_version_hmodule_subject cs "
                + "join curriculum_version_hmodule m on m.id = cs.curriculum_version_hmodule_id "
                + "where m.curriculum_version_id = :curriculumVersionId)", "curriculumVersionId",
                curriculumVersion);

        // do not select subject which are already added
        qb.requiredCriteria(
                " not exists "
                + "(select * from declaration_subject ds "
                + "join subject_study_period ssp2 on ssp2.id = ds.subject_study_period_id  "
                + "where ssp2.subject_id = ssp.subject_id and ds.declaration_id = :declarationId)", "declarationId",  
                EntityUtil.getId(declaration));

        List<?> result = qb.select(SUBJECT_EXTRACURRICULUM_SELECT, em).getResultList();

        return subjectsQueryResultToDto(result);
    }

    private static List<DeclarationSubjectDto> subjectsQueryResultToDto(List<?> result) {
        return StreamUtil.toMappedList(r -> {
            DeclarationSubjectDto dto = new DeclarationSubjectDto();
            dto.setSubjectStudyPeriod(resultAsLong(r, 0));

            SubjectSearchDto subjectDto = new SubjectSearchDto();
            subjectDto.setId(resultAsLong(r, 1));

            subjectDto.setNameEt(resultAsString(r, 2));
            subjectDto.setNameEn(resultAsString(r, 3));
            subjectDto.setCode(resultAsString(r, 4));
            subjectDto.setCredits(resultAsDecimal(r, 5));
            subjectDto.setAssessment(resultAsString(r, 6));
            dto.setSubject(subjectDto);

            dto.setModule(new AutocompleteResult(resultAsLong(r, 7), null, null));
            dto.setIsOptional(resultAsBoolean(r, 8));
            dto.setTeachers(Arrays.asList(resultAsString(r, 9).split(", ")));

            return dto;
        }, result);
    }

    public Page<AutocompleteResult> getStudentsWithoutDeclaration(UsersSearchCommand command, Pageable pageable,
            Long schoolId) {
        final String SELECT = "s.id, p.firstname, p.lastname";
        final String FROM = "from student s join person p on p.id = s.person_id";
        Long studyPeriodId = studyYearService.getCurrentStudyPeriod(schoolId);
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(FROM).sort(pageable);
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.filter("s.status_code = 'OPPURSTAATUS_O'");
        // select only students without declaration
        qb.requiredCriteria(
                "not exists(select d.student_id from declaration d "
                        + "where d.student_id = s.id and d.study_period_id = :studyPeriodId)",
                "studyPeriodId", studyPeriodId);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name",
                command.getName());

        Page<Object[]> result = JpaQueryUtil.pagingResult(qb, SELECT, em, pageable);

        return result.map(r -> {
            String name = PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2));
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        });
    }

    public AutocompleteResult getCurrentStudyPeriod(Long schoolId) {
        Long studyPeriodId = studyYearService.getCurrentStudyPeriod(schoolId);
        return AutocompleteResult.of(em.getReference(StudyPeriod.class, studyPeriodId));
    }

    public void setAreSubjectsDeclaredRepeatedy(Set<DeclarationSubjectDto> subjects, Long declarationId) {
        if(!CollectionUtils.isEmpty(subjects)) {
            final String SELECT = "ds.id, ssp.subject_id, exists( "
                    + "select * from declaration_subject ds2 "
                    + "join declaration d2 on d2.id = ds2.declaration_id "
                    + "join subject_study_period ssp2 on ssp2.id = ds2.subject_study_period_id "
                    + "and ds2.declaration_id <> ds.declaration_id "
                    + "and d2.student_id = d.student_id "
                    + "and ssp2.subject_id = ssp.subject_id)";
            final String FROM = "from declaration_subject ds "
                    + "join declaration d on d.id = ds.declaration_id "
                    + "join subject_study_period ssp on ds.subject_study_period_id = ssp.id";
            JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(FROM);

            qb.requiredCriteria("d.id = :declarationId", "declarationId", declarationId);
            List<?> result = qb.select(SELECT, em).getResultList();

            Map<Long, Boolean> map = StreamUtil.toMap(r -> resultAsLong(r, 0), r -> resultAsBoolean(r, 2), result);

            for(DeclarationSubjectDto subject : subjects) {
                subject.setIsDeclaredRepeatedly(map.get(subject.getId()));
            }
        }
    }

    public void delete(Declaration declaration) {
        EntityUtil.deleteEntity(declaration, em);
    }

    private void setDeclarationStatus(Declaration declaration, DeclarationStatus status) {
        declaration.setStatus(em.getReference(Classifier.class, status.name()));
    }
}
