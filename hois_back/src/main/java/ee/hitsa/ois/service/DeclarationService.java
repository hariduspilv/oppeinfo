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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodSubgroup;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.StudyPeriodEventType;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.ProtocolStudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.DeclarationUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.SubjectStudyPeriodUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.DeclarationSearchCommand;
import ee.hitsa.ois.web.commandobject.DeclarationSubjectForm;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.commandobject.UsersSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.DeclarationDto;
import ee.hitsa.ois.web.dto.DeclarationSubjectDto;
import ee.hitsa.ois.web.dto.PrerequisiteSubjectDto;
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
            + "sp.id, sp.name_et, sp.name_en, status.name_et as status_name_et, status.name_en as status_name_en ";
    private static final String DECLARATION_FROM = "from declaration d "
            + "join student s on s.id = d.student_id "
            + "join person p on p.id = s.person_id "
            + "left join curriculum_version cv on cv.id = s.curriculum_version_id "
            + "join classifier status on status.code = d.status_code "
            + "join study_period sp on sp.id = d.study_period_id "
            + "left join student_group sg on sg.id = s.student_group_id ";

    private static final String SUBJECT_FROM = "from subject_study_period ssp left join subject s on s.id = ssp.subject_id "
            + "left join curriculum_version_hmodule_subject cvhms on cvhms.subject_id = s.id "
            + "left join curriculum_version_hmodule cvhm on cvhm.id =  cvhms.curriculum_version_hmodule_id "
            + "join classifier c on c.code = s.assessment_code left join ("
            + "select ssp2.id as ssp2Id, array_agg(p.firstname || ' ' || p.lastname) as teacher "
            + "from subject_study_period ssp2 "
            + "left join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp2.id "
            + "left join teacher t on t.id = sspt.teacher_id left join person p on p.id = t.person_id "
            + "group by ssp2.id) teachers on ssp.id  = teachers.ssp2Id";

    private static final String SUBJECT_CURRICULUM_SELECT = " ssp.id as ssp1Id, s.id as subjectId, "
            + "s.name_et, s.name_en, s.code, s.credits, c.value, cvhm.id as moduleId, "
            + "cvhms.is_optional, array_to_string(teachers.teacher, ', ') as teachers, cvhm.name_et as moduleEt, cvhm.name_en as moduleEn";
    
    private static final String SUBJECT_EXTRACURRICULUM_SELECT = " distinct ssp.id as ssp1Id, s.id as subjectId, "
            + "s.name_et, s.name_en, s.code, s.credits, c.value, null as moduleId, "
            + " false as isOptional, array_to_string(teachers.teacher, ', ') as teachers, null as moduleEt, null as moduleEn";
    
    private static final String WITHOUT_DECLARATION_SELECT = " s.id, p.firstname, p.lastname, p.idcode, "
            + "cv.id as cv_id, cv.code as cv_code, sg.id as sg_id, sg.code as sg_code ";
    
    private static final String WITHOUT_DECLARATION_FROM = " from student s "
            + "join person p on p.id = s.person_id "
            + "left join student_group sg on sg.id = s.student_group_id "
            + "left join curriculum_version cv on cv.id = s.curriculum_version_id "
            + "left join curriculum c on c.id = cv.curriculum_id ";
    
    public List<AutocompleteResult> autocompleteStudents(HoisUserDetails user, SearchCommand lookup) {

        String from = "from declaration d join student s on s.id = d.student_id join person p on s.person_id = p.id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from).sort("p.lastname", "p.firstname");

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname", "concat(p.firstname, ' ', p.lastname, ' (', p.idcode, ')')"), "name", lookup.getName());
        qb.optionalCriteria("s.id = :studentId", "studentId", lookup.getId());

        String select = "distinct s.id, p.firstname, p.lastname, p.idcode, s.type_code";
        List<?> data = qb.select(select, em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String name = PersonUtil.fullnameAndIdcodeOptionalGuest(resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 4));
            return new AutocompleteResult(resultAsLong(r, 0), name, name);
        }, data);
    }

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
        qb.optionalCriteria("s.id = :studentId", "studentId",
                criteria.getStudent());

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
        Map<Long, String> studentResults = studentSubjectResults(studentId);
        for(DeclarationSubjectDto subject : dto.getSubjects()) {
            subject.setIsAssessed(subjectAssessed(studentId, subject.getSubjectStudyPeriod(), subject.getId()));
            
            for (PrerequisiteSubjectDto mandatory : subject.getMandatoryPrerequisiteSubjects()) {
                mandatory.setGrade(studentResults.get(mandatory.getId()));
            }
            for (PrerequisiteSubjectDto reccomended : subject.getRecommendedPrerequisiteSubjects()) {
                reccomended.setGrade(studentResults.get(reccomended.getId()));
            }
        }
        
        return dto;
    }
    
    private Map<Long, String> studentSubjectResults(Long studentId) {
        Query q = em.createNativeQuery("select distinct shr.subject_id, shr.grade, shr.grade_date " + 
                "from student_higher_result shr " +
                "where shr.student_id = ?1 " +
                "order by shr.grade_date desc");
        q.setParameter(1, studentId);
        
        List<?> data = q.getResultList();
        Map<Long, String> results = new HashMap<>();
        
        for (Object r : data) {
            if (results.get(resultAsLong(r, 0)) == null) {
                results.put(resultAsLong(r, 0), resultAsString(r, 1));
            }
        }
        return results;
    }

    public boolean canCreate(HoisUserDetails user, Long studentId) {
        if(!user.isStudent() && !user.isSchoolAdmin()) {
            return false;
        }
        Student student = em.getReference(Student.class, studentId);
        if(!StudentUtil.isHigher(student)) {
            return false;
        }
        if(user.isStudent()) {
            return UserUtil.isStudent(user, student) && StudentUtil.isStudying(student);
        }
        return UserUtil.isSchoolAdmin(user, student.getSchool());
    }
    
    public boolean canCreateCurrent(HoisUserDetails user, Long studentId) {
        if (getCurrent(user.getSchoolId(), studentId) != null) {
            // declaration already created
            return false;
        }
        return canCreate(user, studentId);
    }
    
    public boolean canCreateNext(HoisUserDetails user, Long studentId) {
        if (getNext(user.getSchoolId(), studentId) != null) {
            // declaration already created
            return false;
        }
        return canCreate(user, studentId);
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
        ValidationFailedException.throwIf(declaration.getSubjects().stream()
                .map(ds -> EntityUtil.getId(ds.getSubjectStudyPeriod().getSubject()))
                .filter(sId -> sId.equals(EntityUtil.getId(ssp.getSubject())))
                .findAny().isPresent(), "declaration.error.subjectExists");

        DeclarationSubject declarationSubject = new DeclarationSubject();
        declarationSubject.setDeclaration(declaration);
        declarationSubject.setSubjectStudyPeriod(ssp);
        Long higherModule = form.getCurriculumVersionHigherModule();
        if(higherModule != null) {
            declarationSubject.setModule(em.getReference(CurriculumVersionHigherModule.class, higherModule));
        }
        declarationSubject.setIsOptional(form.getIsOptional());
        declarationSubject.setSubgroup(EntityUtil.getOptionalOne(SubjectStudyPeriodSubgroup.class, form.getSubgroup(), em));
        DeclarationSubjectDto dto = DeclarationSubjectDto.of(EntityUtil.save(declarationSubject, em));
        setAreSubjectsDeclaredRepeatedy(Arrays.asList(dto), dto.getDeclaration());
        return dto;
    }

    public Declaration create(HoisUserDetails user, Long studentId, boolean nextPeriod) {
        AssertionFailedException.throwIf(nextPeriod ? !canCreateNext(user, studentId) : !canCreateCurrent(user, studentId), "You cannot create declaration!");

        Student student = em.getReference(Student.class, studentId);
        Long studyPeriodId;
        if (nextPeriod) {
            studyPeriodId = studyYearService.getNextStudyPeriod(user.getSchoolId());
            if(studyPeriodId == null) {
                throw new ValidationFailedException("studyYear.studyPeriod.missingCurrent");
            }   
        } else {
            studyPeriodId = studyYearService.getCurrentStudyPeriod(user.getSchoolId());
            if(studyPeriodId == null) {
                throw new ValidationFailedException("studyYear.studyPeriod.missingCurrent");
            }
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

    public Declaration getNext(Long schoolId, Long studentId) {
        Long studyPeriodId = studyYearService.getNextStudyPeriod(schoolId);
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
        Long studyPeriod = studyYearService.getCurrentStudyPeriod(schoolId);
        if(studyPeriod == null) {
            return new ArrayList<>();
        }
        
        return em.createQuery("select d from Declaration d where d.student.school.id = ?1 and d.status.code = ?2 and d.studyPeriod.id = ?3", 
                Declaration.class)
            .setParameter(1, schoolId)
            .setParameter(2, DeclarationStatus.OPINGUKAVA_STAATUS_S.name())
            .setParameter(3, studyPeriod)
            .getResultList();
    }

    public Declaration removeConfirmation(Declaration declaration) {
        setDeclarationStatus(declaration, DeclarationStatus.OPINGUKAVA_STAATUS_S);
        declaration.setConfirmer(null);
        declaration.setConfirmDate(null);
        return EntityUtil.save(declaration, em);
    }

    public Page<DeclarationSubjectDto> getCurriculumSubjectOptions(Declaration declaration, Pageable pageable) {
        Long studyPeriod = EntityUtil.getId(declaration.getStudyPeriod());
        Student student = declaration.getStudent();
        Long curriculumVersion = EntityUtil.getId(student.getCurriculumVersion());

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SUBJECT_FROM).sort(pageable);

        qb.requiredCriteria("cvhm.curriculum_version_id = :curriculumVersionId", "curriculumVersionId",
                curriculumVersion);
        qb.requiredCriteria("cvhm.type_code != :moduleType", "moduleType", HigherModuleType.KORGMOODUL_L.name());
        qb.requiredCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", studyPeriod);
        qb.requiredCriteria(
                " not exists "
                + "(select * from declaration_subject ds "
                + "join subject_study_period ssp2 on ssp2.id = ds.subject_study_period_id  "
                + "where ssp2.subject_id = ssp.subject_id and ds.declaration_id = :declarationId)", "declarationId",  
                EntityUtil.getId(declaration));

        Page<Object[]> result = JpaQueryUtil.pagingResult(qb, SUBJECT_CURRICULUM_SELECT, em, pageable);
        return result.map(r -> {
            DeclarationSubjectDto dto = subjectQueryResultToDto(r);
            // Set available subgroups
            SubjectStudyPeriod ssp = em.getReference(SubjectStudyPeriod.class, dto.getSubjectStudyPeriod());
            
            List<SubjectStudyPeriodSubgroup> sortedSubgroups = ssp.getSubgroups().stream()
                .sorted(SubjectStudyPeriodUtil.COMPARATOR_SUBGROUP)
                .collect(Collectors.toList());
            
            dto.setSubgroups(StreamUtil.toMappedSet(AutocompleteResult::of, sortedSubgroups.stream().filter(subgroup -> {
                return subgroup.getDeclarationSubjects().size() < subgroup.getPlaces().intValue();
            })));

            SubjectStudyPeriodSubgroup lastSubgroup = sortedSubgroups.isEmpty() ? null : sortedSubgroups.get(sortedSubgroups.size() - 1);
            if (dto.getSubgroups().isEmpty() && lastSubgroup != null) {
                dto.setSubgroups(Collections.singleton(AutocompleteResult.of(lastSubgroup)));
            }
            
            return dto;
        });
    }

    public List<DeclarationSubjectDto> getExtraCurriculumSubjectsOptions(Declaration declaration) {
        Long studyPeriod = EntityUtil.getId(declaration.getStudyPeriod());
        Long curriculumVersion = EntityUtil.getNullableId(declaration.getStudent().getCurriculumVersion());

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SUBJECT_FROM);
        
        qb.requiredCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", studyPeriod);

        // do not select student's curriculum version's subjects
        qb.optionalCriteria(" ssp.subject_id not in "
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

        List<?> result = qb.sort("s.name_et, s.name_en, teachers").select(SUBJECT_EXTRACURRICULUM_SELECT, em).getResultList();
        return StreamUtil.toMappedList(this::subjectQueryResultToDto, result);
    }

    private DeclarationSubjectDto subjectQueryResultToDto(Object r) {
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

        if (resultAsLong(r, 7) != null) {
            dto.setModule(new AutocompleteResult(resultAsLong(r, 7), resultAsString(r, 10), resultAsString(r, 11)));
        }
        dto.setIsOptional(resultAsBoolean(r, 8));
        dto.setTeachers(Arrays.asList(resultAsString(r, 9).split(", ")));
        return dto;
    }
    
    public List<AutocompleteResult> getSubjectStudyPeriodSubgroups(SubjectStudyPeriod subjectStudyPeriod) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period_subgroup ssps "
                + "left join subject_study_period_teacher sspt on sspt.id = ssps.subject_study_period_teacher_id "
                + "left join teacher t on sspt.teacher_id = t.id "
                + "left join person p on t.person_id = p.id "
                + "left join (select count(ds.id) as subjectCount, ds.subject_study_period_subgroup_id "
                    + "from declaration_subject ds "
                    + "group by ds.subject_study_period_subgroup_id) "
                    + "S1 on S1.subject_study_period_subgroup_id = ssps.id");
        qb.requiredCriteria("ssps.subject_study_period_id = :subjectStudyPeriodId", "subjectStudyPeriodId", EntityUtil.getId(subjectStudyPeriod));
        List<?> subgroupResult = qb.sort("ssps.code, p.lastname")
                .select("ssps.id as groupId, ssps.code, p.id as teacherId, p.firstname || ' ' || p.lastname as teacherName, S1.subjectCount, ssps.places", em).getResultList();
        List<AutocompleteResult> mappedSubgroups = subgroupResult.stream()
                .filter(p -> resultAsLong(p, 4) == null || resultAsLong(p, 4).longValue() < resultAsLong(p, 5).longValue())
                .map(this::subgroupDto).collect(Collectors.toList());
        if (!subgroupResult.isEmpty() && mappedSubgroups.isEmpty()) {
            Object r = subgroupResult.get(subgroupResult.size() - 1);
            return Arrays.asList(subgroupDto(r));
        }
        return mappedSubgroups;
    }
    
    private AutocompleteResult subgroupDto(Object r) {
        Long teacherPerson = resultAsLong(r, 2);
        if (teacherPerson == null) {
            return new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 1));
        }
        String name = String.format("%s (%s)", resultAsString(r, 1), resultAsString(r, 3));
        return new AutocompleteResult(resultAsLong(r, 0), name, name);
    }

    public AutocompleteResult getCurrentStudyPeriod(Long schoolId) {
        Long studyPeriodId = studyYearService.getCurrentStudyPeriod(schoolId);
        return studyPeriodId != null ? AutocompleteResult.ofWithYear(em.getReference(StudyPeriod.class, studyPeriodId)) : null;
    }
    
    public AutocompleteResult getNextStudyPeriodIfOpenDeclarationPeriod(Long schoolId) {
        Long studyPeriodId = studyYearService.getNextStudyPeriod(schoolId);
        if (studyPeriodId == null) {
            return null;
        }
        StudyPeriodEventDto declaration = getDeclarationPeriodByStudyPeriod(studyPeriodId);
        if (declaration == null) {
            return null;
        }
        LocalDateTime now = LocalDateTime.now();
        if (declaration.getStart() != null && !declaration.getStart().isAfter(now)
                && declaration.getEnd() != null && !declaration.getEnd().isBefore(now)) {
            return AutocompleteResult.ofWithYear(em.getReference(StudyPeriod.class, studyPeriodId));
        }
        return null;
    }
    
    private StudyPeriodEventDto getDeclarationPeriodByStudyPeriod(Long studyPeriodId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from study_period_event spe join study_period sp on sp.id = spe.study_period_id");
        qb.requiredCriteria("sp.id = :studyPeriodId", "studyPeriodId", studyPeriodId);
        qb.requiredCriteria("spe.event_type_code = :eventTypeCode", "eventTypeCode", DECLARATION_PERIOD_EVENT_TYPE);

        List<?> result = qb.select("spe.id", em).setMaxResults(1).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        
        return StudyPeriodEventDto.of(em.getReference(StudyPeriodEvent.class,  resultAsLong(result.get(0), 0)));
    }

    private StudyPeriodEventDto getCurrentStudyPeriodDeclarationPeriod(Long schoolId) {
        Long currentStudyPeriod = studyYearService.getCurrentStudyPeriod(schoolId);
        if (currentStudyPeriod == null) {
            return null;
        }
        return getDeclarationPeriodByStudyPeriod(currentStudyPeriod);
    }
    
    private StudyPeriodEventDto getNextStudyPeriodDeclarationPeriod(Long schoolId) {
        Long nextStudyPeriod = studyYearService.getNextStudyPeriod(schoolId);
        if (nextStudyPeriod == null) {
            return null;
        }
        
        return getDeclarationPeriodByStudyPeriod(nextStudyPeriod);
    }
    
    private Map<String, ?> declarationPeriodData(StudyPeriodEventDto period) {
        Boolean isDeclarationPeriod = Boolean.FALSE;
        Map<String, Object> data = new HashMap<>();
        if (period != null) {
            data.put("period", AutocompleteResult.ofWithYear(em.getReference(StudyPeriod.class, period.getStudyPeriod().getId())));
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime periodStart = period.getStart();
            LocalDateTime periodEnd = period.getEnd() != null ? period.getEnd() : null;
            
            if (now.isAfter(periodStart) && periodEnd == null) {
                isDeclarationPeriod = Boolean.TRUE;
            } else if (now.isAfter(periodStart) && periodEnd != null && now.isBefore(periodEnd)) {
                isDeclarationPeriod = Boolean.TRUE;
            }
            
            if (periodStart != null) {
                data.put("declarationPeriodStart", periodStart);
            }
            if (periodEnd != null) {
                data.put("declarationPeriodEnd", periodEnd);
            }
        }
        data.put("isDeclarationPeriod", isDeclarationPeriod);
        return data;
    }

    public Map<String, ?> declarationPeriod(Declaration declaration) {
        Optional<StudyPeriodEvent> optDeclarationPeriod = declaration.getStudyPeriod().getEvents().stream()
                .filter(e -> ClassifierUtil.equals(StudyPeriodEventType.SYNDMUS_DEKP, e.getEventType())).findAny();
        if (!optDeclarationPeriod.isPresent()) {
            return Collections.emptyMap();
        }
        
        Boolean isDeclarationPeriod = Boolean.FALSE;
        Map<String, Object> data = new HashMap<>();
        StudyPeriodEvent declarationPeriod = optDeclarationPeriod.get();
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime periodStart = declarationPeriod.getStart();
        LocalDateTime periodEnd = declarationPeriod.getEnd();
        
        if (now.isAfter(periodStart) && periodEnd == null) {
            isDeclarationPeriod = Boolean.TRUE;
        } else if (now.isAfter(periodStart) && periodEnd != null && now.isBefore(periodEnd)) {
            isDeclarationPeriod = Boolean.TRUE;
        }
        
        if (periodStart != null) {
            data.put("declarationPeriodStart", periodStart);
        }
        if (periodEnd != null) {
            data.put("declarationPeriodEnd", periodEnd);
        }
        data.put("isDeclarationPeriod", isDeclarationPeriod);
        
        return data;
    }
    
    public Map<String, ?> isDeclarationPeriod(HoisUserDetails user) {
        StudyPeriodEventDto declarationPeriod = getCurrentStudyPeriodDeclarationPeriod(user.getSchoolId());
        return declarationPeriodData(declarationPeriod);
    }
    
    public Map<String, ?> isNextDeclarationPeriod(HoisUserDetails user) {
        StudyPeriodEventDto declarationPeriod = getNextStudyPeriodDeclarationPeriod(user.getSchoolId());
        return declarationPeriodData(declarationPeriod);
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
                + "where ps.student_id = ?1 and phd.subject_study_period_id = ?2 and ps.grade_code is not null) "
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
        Long studyPeriodId = Boolean.TRUE.equals(command.getNextPeriod())
                ? studyYearService.getNextStudyPeriod(schoolId)
                : studyYearService.getCurrentStudyPeriod(schoolId);
        if(studyPeriodId == null) {
            return new PageImpl<>(Arrays.asList());
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(WITHOUT_DECLARATION_FROM).sort(pageable);

        qb.requiredCriteria("s.school_id = :schoolId ", "schoolId", schoolId);
        qb.requiredCriteria("s.status_code in :active ", "active", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.filter("(case when c.id is not null then c.is_higher = true else true end)");

        qb.requiredCriteria("not exists("
                + "select d.id from declaration d where d.student_id = s.id and d.study_period_id = :currentStudyPeriod)", 
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
