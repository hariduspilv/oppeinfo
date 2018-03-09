package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.parameterAsTimestamp;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Declaration;
import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyPeriodEvent;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.protocol.ProtocolHdata;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.ProtocolStudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.DeclarationUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.DeclarationSearchCommand;
import ee.hitsa.ois.web.commandobject.DeclarationSubjectForm;
import ee.hitsa.ois.web.commandobject.UsersSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.DeclarationDto;
import ee.hitsa.ois.web.dto.DeclarationSubjectDto;
import ee.hitsa.ois.web.dto.StudyPeriodEventDto;
import ee.hitsa.ois.web.dto.SubjectSearchDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

@Transactional
@Service
public class DeclarationService {

    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private EntityManager em;
    @Autowired
    private ProtocolStudentRepository protocolStudentRepository;
    
    private static final String DECLARATION_PERIOD_EVENT_TYPE = "SYNDMUS_DEKP";

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
    
    private static final String WITHOUT_DECLARATION_SELECT = " s.id, p.firstname, p.lastname, p.idcode, "
            + "cv.id as cv_id, cv.code as cv_code, sg.id as sg_id, sg.code as sg_code ";
    
    private static final String WITHOUT_DECLARATION_FROM = " from student s "
            + "join person p on p.id = s.person_id "
            + "left join student_group sg on sg.id = s.student_group_id "
            + "left join curriculum_version cv on cv.id = s.curriculum_version_id "
            + "left join curriculum c on c.id = cv.curriculum_id ";

    public Page<DeclarationDto> search(HoisUserDetails user, DeclarationSearchCommand criteria, Pageable pageable) {

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(DECLARATION_FROM).sort(pageable);

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
            dto.setCanBeChanged(Boolean.valueOf(DeclarationUtil.canChangeDeclarationFromSearchForm(user, dto)));
            return dto;
        });
    }

    public Page<DeclarationDto> searchStudentsPreviousDeclarations(Long studentId, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(DECLARATION_FROM).sort(pageable);

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

    public DeclarationDto get(HoisUserDetails user, Declaration declaration) {
        DeclarationDto dto = DeclarationDto.of(declaration);
        setAreSubjectsDeclaredRepeatedy(dto.getSubjects(), declaration.getId());
        dto.setCanBeChanged(Boolean.valueOf(DeclarationUtil.canChangeDeclaration(user, declaration)));
        dto.setCanBeSetUnconfirmed(Boolean.valueOf(DeclarationUtil.canUnconfirmDeclaration(user, declaration)));
        dto.setCanBeSetConfirmed(Boolean.valueOf(DeclarationUtil.canConfirmDeclaration(user, declaration)));
        Long studentId = dto.getStudent().getId();
        for(DeclarationSubjectDto subject : dto.getSubjects()) {
            subject.setIsAssessed(subjectAssessed(studentId, subject.getSubjectStudyPeriod(), subject.getId()));
        }
        return dto;
    }

    public boolean canCreate(HoisUserDetails user, Long studentId) {
        if(!user.isStudent() && !user.isSchoolAdmin()) {
            return false;
        }
        if(getCurrent(user.getSchoolId(), studentId) != null) {
            // declaration already created
            return false;
        }
        Student student = em.getReference(Student.class, studentId);
        if(!StudentUtil.isHigher(student)) {
            return false;
        }
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

    public void deleteSubject(HoisUserDetails user, DeclarationSubject subject) {
        if(Boolean.TRUE.equals(subjectAssessed(subject))) {
            throw new ValidationFailedException("declaration.error.subjectDelete");
        }
        EntityUtil.setUsername(user.getUsername(), em);
        deleteFromProtocol(subject);
        EntityUtil.deleteEntity(subject, em);
    }

    private void deleteFromProtocol(DeclarationSubject subject) {
        Long studentId = EntityUtil.getId(subject.getDeclaration().getStudent());
        Long subjectStudyPeriodId = EntityUtil.getId(subject.getSubjectStudyPeriod());
        List<ProtocolStudent> list = protocolStudentRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("student").get("id"), studentId));
            Subquery<Long> protocolHdataQuery = query.subquery(Long.class);
            Root<ProtocolHdata> targetRoot = protocolHdataQuery.from(ProtocolHdata.class);
            protocolHdataQuery = protocolHdataQuery
                    .select(targetRoot.get("protocol").get("id")).where(cb.equal(targetRoot.get("subjectStudyPeriod").get("id"), subjectStudyPeriodId));
            filters.add(root.get("protocol").get("id").in(protocolHdataQuery));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
        protocolStudentRepository.delete(list);
    }

    /*
     * TODO: it would be better to send CurriculumVersionHigherModuleSubject.id
     * instead of isOptional and moduleId. Extra validation could be added to
     * see if moduleSubject and subject match
     */
    public DeclarationSubjectDto addSubject(HoisUserDetails user, DeclarationSubjectForm form) {
        Declaration declaration = em.getReference(Declaration.class, form.getDeclaration());
        DeclarationUtil.assertCanChangeDeclaration(user, declaration);

        SubjectStudyPeriod ssp = em.getReference(SubjectStudyPeriod.class, form.getSubjectStudyPeriod());

        AssertionFailedException.throwIf(!EntityUtil.getId(declaration.getStudyPeriod()).equals(EntityUtil.getId(ssp.getStudyPeriod())),
                "Declaration's and study period's ids does not match");
        UserUtil.assertSameSchool(user, ssp.getStudyPeriod().getStudyYear().getSchool());

        DeclarationSubject declarationSubject = new DeclarationSubject();
        declarationSubject.setDeclaration(declaration);
        declarationSubject.setSubjectStudyPeriod(ssp);
        Long higherModule = form.getCurriculumVersionHigherModule();
        if(higherModule != null) {
            declarationSubject.setModule(em.getReference(CurriculumVersionHigherModule.class, higherModule));
        }
        declarationSubject.setIsOptional(form.getIsOptional());
        DeclarationSubjectDto dto = DeclarationSubjectDto.of(EntityUtil.save(declarationSubject, em));
        setAreSubjectsDeclaredRepeatedy(Arrays.asList(dto), dto.getDeclaration());
        return dto;
    }

    public Declaration create(HoisUserDetails user, Long studentId) {
        AssertionFailedException.throwIf(!canCreate(user, studentId), "You cannot create declaration!");

        Student student = em.getReference(Student.class, studentId);
        Long studyPeriodId = studyYearService.getCurrentStudyPeriod(user.getSchoolId());
        if(studyPeriodId == null) {
            throw new ValidationFailedException("studyYear.studyPeriod.missingCurrent");
        }
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

        List<Declaration> result = em.createQuery("select d from Declaration d where d.student.id = ?1 and d.studyPeriod.id = ?2", Declaration.class)
                .setParameter(1, studentId)
                .setParameter(2, studyPeriodId)
                .setMaxResults(1).getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public Declaration confirm(String confirmer, Declaration declaration) {
        setDeclarationStatus(declaration, DeclarationStatus.OPINGUKAVA_STAATUS_K);
        declaration.setConfirmer(confirmer);
        declaration.setConfirmDate(LocalDate.now());
        return EntityUtil.save(declaration, em);
    }

    /**
     * @return number of confirmed declarations
     */
    public Integer confirmAll(HoisUserDetails user) {
        List<Declaration> declarations = unconfirmedDeclarations(user.getSchoolId());
        Classifier confirmedStatus = em.getReference(Classifier.class, DeclarationStatus.OPINGUKAVA_STAATUS_K.name());
        for (Declaration declaration : declarations) {
            declaration.setStatus(confirmedStatus);
            declaration.setConfirmer(user.getUsername());
            declaration.setConfirmDate(LocalDate.now());
            EntityUtil.save(declaration, em);
        } 
        return Integer.valueOf(declarations.size());
    }

    private List<Declaration> unconfirmedDeclarations(Long schoolId) {
        return em.createQuery("select d from Declaration d where d.student.school.id = ?1 and d.status.code = ?2", Declaration.class)
            .setParameter(1, schoolId).setParameter(2, DeclarationStatus.OPINGUKAVA_STAATUS_S.name())
            .getResultList();
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
        Long curriculumVersion = EntityUtil.getId(student.getCurriculumVersion());

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SUBJECT_FROM);

        qb.requiredCriteria("cvhm.curriculum_version_id = :curriculumVersionId", "curriculumVersionId",
                curriculumVersion);
        qb.requiredCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", studyPeriod);

        List<?> result = qb.select(SUBJECT_CURRICULUM_SELECT, em).getResultList();

        return subjectsQueryResultToDto(result);
    }

    public List<DeclarationSubjectDto> getExtraCurriculumSubjectsOptions(Declaration declaration) {
        Long studyPeriod = EntityUtil.getId(declaration.getStudyPeriod());
        Long curriculumVersion = EntityUtil.getId(declaration.getStudent().getCurriculumVersion());

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SUBJECT_FROM);
        
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

    public AutocompleteResult getCurrentStudyPeriod(Long schoolId) {
        Long studyPeriodId = studyYearService.getCurrentStudyPeriod(schoolId);
        return studyPeriodId != null ? AutocompleteResult.of(em.getReference(StudyPeriod.class, studyPeriodId)) : null;
    }

    private StudyPeriodEventDto getCurrentStudyPeriodDeclarationPeriod(Long schoolId) {
        Long currentStudyPeriod = studyYearService.getCurrentStudyPeriod(schoolId);
        if (currentStudyPeriod == null) {
            return null;
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from study_period_event spe join study_period sp on sp.id = spe.study_period_id");
        qb.requiredCriteria("sp.id = :studyPeriodId", "studyPeriodId", currentStudyPeriod);
        qb.requiredCriteria("spe.event_type_code = :eventTypeCode", "eventTypeCode", DECLARATION_PERIOD_EVENT_TYPE);

        List<?> result = qb.select("spe.id", em).setMaxResults(1).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return StudyPeriodEventDto.of(em.getReference(StudyPeriodEvent.class,  resultAsLong(result.get(0), 0)));
    }
    
    public Map<String, ?> isDeclarationPeriod(HoisUserDetails user) {
        StudyPeriodEventDto declarationPeriod = getCurrentStudyPeriodDeclarationPeriod(user.getSchoolId());
        
        Boolean isDeclarationPeriod = Boolean.FALSE;
        Map<String, Object> data = new HashMap<>();
        if (declarationPeriod != null) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime periodStart = declarationPeriod.getStart();
            LocalDateTime periodEnd = declarationPeriod.getEnd() != null ? declarationPeriod.getEnd() : null;
            
            if (now.isAfter(periodStart) && periodEnd == null) {
                isDeclarationPeriod = Boolean.TRUE;
            } else if (now.isAfter(periodStart) && periodEnd != null && now.isBefore(periodEnd)) {
                isDeclarationPeriod = Boolean.TRUE;
            }
            
            if (periodStart != null && now.isBefore(periodStart)) {
                data.put("declarationPeriodStart", periodStart);
            } else if (periodEnd != null && now.isAfter(periodEnd)) {
                data.put("declarationPeriodEnd", periodEnd);
            }
        }
        data.put("isDeclarationPeriod", isDeclarationPeriod);
        return data;
    }

    private void setAreSubjectsDeclaredRepeatedy(Collection<DeclarationSubjectDto> subjects, Long declarationId) {
        if(!CollectionUtils.isEmpty(subjects)) {
            final String SELECT = "ds.id, ssp.subject_id, exists( "
                    + "select * from declaration_subject ds2 "
                    + "join declaration d2 on d2.id = ds2.declaration_id "
                    + "join subject_study_period ssp2 on ssp2.id = ds2.subject_study_period_id "
                    + "and ds2.declaration_id <> ds.declaration_id "
                    + "and d2.student_id = d.student_id "
                    + "and ssp2.subject_id = ssp.subject_id)";

            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from declaration_subject ds "
                    + "join declaration d on d.id = ds.declaration_id "
                    + "join subject_study_period ssp on ds.subject_study_period_id = ssp.id");

            qb.requiredCriteria("d.id = :declarationId", "declarationId", declarationId);
            List<?> result = qb.select(SELECT, em).getResultList();

            Map<Long, Boolean> map = StreamUtil.toMap(r -> resultAsLong(r, 0), r -> resultAsBoolean(r, 2), result);

            for(DeclarationSubjectDto subject : subjects) {
                subject.setIsDeclaredRepeatedly(map.get(subject.getId()));
            }
        }
    }

    public void delete(HoisUserDetails user, Declaration declaration) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(declaration, em);
    }

    private void setDeclarationStatus(Declaration declaration, DeclarationStatus status) {
        declaration.setStatus(em.getReference(Classifier.class, status.name()));
    }

    private Boolean subjectAssessed(DeclarationSubject subject) {
        Long studentId = EntityUtil.getId(subject.getDeclaration().getStudent());
        Long subjectStudyPeriodId = EntityUtil.getId(subject.getSubjectStudyPeriod());
        Long declarationSubjectId = EntityUtil.getId(subject);
        return subjectAssessed(studentId, subjectStudyPeriodId, declarationSubjectId);
    }

    /**
     * Check if subject is graded in protocol or has graded midterm task
     */
    public Boolean subjectAssessed(Long studentId, Long subjectStudyPeriodId, Long declarationSubjectId) {
        Query q = em.createNativeQuery("select exists(select * from protocol_student ps "
                + "join protocol p on p.id = ps.protocol_id "
                + "join protocol_hdata phd on p.id = phd.protocol_id "
                + "where ps.student_id = ?1 and phd.subject_study_period_id = ?2) "
                + "or exists(select * from midterm_task_student_result r where r.declaration_subject_id = ?3)");
        q.setParameter(1, studentId);
        q.setParameter(2, subjectStudyPeriodId);
        q.setParameter(3, declarationSubjectId);
        
        List<?> result = q.getResultList();
        if (result.isEmpty()) {
            return Boolean.FALSE;
        }
        return resultAsBoolean(result.get(0), 0);
    }
    
    public Page<AutocompleteResult> getStudentsWithoutDeclaration(UsersSearchCommand command, Pageable pageable,
            Long schoolId) {
        return searchStudentsWithoutDeclaration(command, schoolId, pageable)
                .map(s -> new AutocompleteResult(s.getId(), s.getFullname(), s.getFullname()));
    }

    public Page<StudentSearchDto> searchStudentsWithoutDeclaration(UsersSearchCommand command, Long schoolId, Pageable pageable) {
        Long studyPeriodId = studyYearService.getCurrentStudyPeriod(schoolId);
        if(studyPeriodId == null) {
            return new PageImpl<>(Arrays.asList());
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(WITHOUT_DECLARATION_FROM).sort(pageable);

        qb.requiredCriteria("s.school_id = :schoolId ", "schoolId", schoolId);
        qb.requiredCriteria(" s.status_code in :active ", "active", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.filter(" c.is_higher = true ");

        qb.requiredCriteria(" not exists("
                + "select d.id from declaration d where d.student_id = s.id and d.study_period_id = :currentStudyPeriod) ", 
                "currentStudyPeriod", studyPeriodId);

        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name",
                command.getName());

        Page<Object[]> results = JpaQueryUtil.pagingResult(qb, WITHOUT_DECLARATION_SELECT, em, pageable);
        return results.map(r -> {
            StudentSearchDto dto = new StudentSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setIdcode(resultAsString(r, 3));
            dto.setCurriculumVersion(
                    new AutocompleteResult(resultAsLong(r, 4), resultAsString(r, 5), resultAsString(r, 5)));
            dto.setStudentGroup(
                    new AutocompleteResult(resultAsLong(r, 6), resultAsString(r, 7), resultAsString(r, 7)));
            return dto;
        });
    }
}
