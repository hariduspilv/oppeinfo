package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.parameterAsTimestamp;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.web.commandobject.StudentDeclarationCommand;
import ee.hitsa.ois.web.dto.GradeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.NullHandling;
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
import ee.hitsa.ois.service.subjectstudyperiod.SubjectStudyPeriodDeclarationService;
import ee.hitsa.ois.util.ClassifierUtil;
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
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.DeclarationAutofillResponseDto;
import ee.hitsa.ois.web.dto.DeclarationDto;
import ee.hitsa.ois.web.dto.DeclarationSubjectDto;
import ee.hitsa.ois.web.dto.PrerequisiteSubjectDto;
import ee.hitsa.ois.web.dto.StudyPeriodEventDto;
import ee.hitsa.ois.web.dto.SubjectSearchDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSearchDto;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSubgroupDto;
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
    @Autowired
    private SubjectStudyPeriodDeclarationService subjectStudyPeriodDeclarationService;
    @Autowired
    private StudentService studentService;
    
    private static final String DECLARATION_PERIOD_EVENT_TYPE = "SYNDMUS_DEKP";

    private static final String DECLARATION_SELECT = " distinct d.id as d_id, s.id as sId, "
            + "p.firstname, p.lastname, p.idcode, cv.id as cv_id, cv.code as cv_code, "
            + "sg.id as sgId, sg.code as sg_code, d.inserted, d.status_code, d.confirm_date, "
            + "sp.id, sp.name_et, sp.name_en, status.name_et as status_name_et, status.name_en as status_name_en, s.type_code, "
            + "coalesce((select sum(s.credits) "
                + "from declaration_subject ds "
                + "join subject_study_period ssp on ssp.id = ds.subject_study_period_id "
                + "join subject s on ssp.subject_id = s.id "
                + "where ds.declaration_id = d.id "
            + "), 0) credits ";
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
            + "cvhms.is_optional, array_to_string(teachers.teacher, ', ') as teachers, "
            + "cvhm.name_et as moduleEt, cvhm.name_en as moduleEn, "
            + "(select count(*) from declaration_subject ds3 where ds3.subject_study_period_id = ssp.id) < coalesce(ssp.places, 9999) as has_places";

    private static final String SUBJECT_EXTRACURRICULUM_SELECT = " distinct ssp.id as ssp1Id, s.id as subjectId, "
            + "s.name_et, s.name_en, s.code, s.credits, c.value, null as moduleId, "
            + "false as isOptional, array_to_string(teachers.teacher, ', ') as teachers, null as moduleEt, null as moduleEn, "
            + "(select count(*) from declaration_subject ds3 where ds3.subject_study_period_id = ssp.id) < coalesce(ssp.places, 9999) as has_places";

    private static final String SUBJECT_NOT_IN_DECLARATION = "not exists (select 1 from declaration_subject ds "
            + "join subject_study_period ssp2 on ssp2.id = ds.subject_study_period_id "
            + "where ssp2.subject_id = ssp.subject_id and ds.declaration_id = :declarationId)";

    private static final String WITHOUT_DECLARATION_SELECT = " s.id, p.firstname, p.lastname, p.idcode, "
            + "cv.id as cv_id, cv.code as cv_code, sg.id as sg_id, sg.code as sg_code ";
    
    private static final String WITHOUT_DECLARATION_FROM = " from student s "
            + "join person p on p.id = s.person_id "
            + "left join student_group sg on sg.id = s.student_group_id "
            + "left join curriculum_version cv on cv.id = s.curriculum_version_id "
            + "left join curriculum c on c.id = cv.curriculum_id ";

    private static final String SUBGROUPS_SELECT= "ssp.id as ssp_id, subgroup.id as sg_id, subgroup.code, "
            + "p.firstname || ' ' || p.lastname, subgroup.places, ds.declarations";
    
    private static final String SUBGROUPS_FROM = "from subject_study_period ssp "
            + "join subject_study_period_subgroup subgroup on subgroup.subject_study_period_id = ssp.id "
            + "left join subject_study_period_teacher sspt on sspt.id = subgroup.subject_study_period_teacher_id "
            + "left join teacher t on t.id = sspt.teacher_id "
            + "left join person p on p.id = t.person_id "
            + "left join (select ds.subject_study_period_subgroup_id as subgroup_id, count(*) as declarations "
                + "from declaration_subject ds group by ds.subject_study_period_subgroup_id) as ds on ds.subgroup_id = subgroup.id";
    
    private static final Sort SUBGROUPS_SORT = new Sort(
            new Sort.Order(Direction.ASC, "subgroup.code"),
            new Sort.Order(Direction.ASC, "p.lastname", NullHandling.NULLS_FIRST),
            new Sort.Order(Direction.ASC, "p.firstname", NullHandling.NULLS_LAST),
            new Sort.Order(Direction.ASC, "subgroup.id"));
    
    private static final String STUDENT_GROUP_SELECT= "ssp.id as ssp_id, sg.code, sg.id as studentGroupId";
    
    private static final String STUDENT_GROUP_FROM = "from subject_study_period ssp "
            + "join subject_study_period_student_group s_group on s_group.subject_study_period_id = ssp.id "
            + "join student_group sg on sg.id = s_group.student_group_id";
    
    private static final Sort STUDENT_GROUP_SORT = new Sort(
            new Sort.Order(Direction.ASC, "sg.code"));
    
    public List<AutocompleteResult> autocompleteStudents(HoisUserDetails user, SearchCommand lookup) {

        String from = "from declaration d join student s on s.id = d.student_id join person p on s.person_id = p.id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from).sort("p.lastname", "p.firstname");

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname", "concat(p.firstname, ' ', p.lastname, ' (', p.idcode, ')')"), "name", lookup.getName());
        qb.optionalCriteria("s.id = :studentId", "studentId", lookup.getId());

        String select = "distinct s.id, p.firstname, p.lastname, p.idcode, s.type_code";
        List<?> data = qb.select(select, em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String name = PersonUtil.fullnameAndIdcodeTypeSpecific(resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 4));
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
            studentDto.setFullname(PersonUtil.fullnameTypeSpecific(resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 17)));
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
            dto.setCredits(resultAsDecimal(r, 18));
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
        Map<Long, GradeDto> studentResults = studentSubjectResults(studentId);
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
    
    private Map<Long, GradeDto> studentSubjectResults(Long studentId) {
        List<?> data = em.createNativeQuery("select shr.subject_id, shr.grade_code, shr.grading_schema_row_id " +
                "from student_higher_result shr " +
                "where shr.student_id = ?1 and shr.is_active = true")
                .setParameter(1, studentId)
                .getResultList();
        return data.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0),
                r -> new GradeDto(resultAsString(r, 1), resultAsLong(r, 2)), (o, n) -> o));
    }

    public boolean canCreate(HoisUserDetails user, Long studentId) {
        if(!user.isStudent() && !user.isSchoolAdmin()) {
            return false;
        }
        Student student = em.getReference(Student.class, studentId);
        if(!studentService.isHigher(student)) {
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
        return canCreateNext(user, studentId, null);
    }
    
    public boolean canCreateNext(HoisUserDetails user, Long studentId, Long period) {
        if (user.isSchoolAdmin()) {
            if (period != null) {
                if (getByPeriod(studentId, period) != null) {
                    return false;
                }
            } else if (getNext(user.getSchoolId(), studentId) != null) {
                return false;
            }
        } else if (getNext(user.getSchoolId(), studentId) != null) {
            return false;
        }
        if (getNextStudyPeriod(user.getSchoolId()) == null) {
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

    public DeclarationSubjectDto addSubject(HoisUserDetails user, DeclarationSubjectForm form) {
        Declaration declaration = em.getReference(Declaration.class, form.getDeclaration());
        DeclarationUtil.assertCanChangeDeclaration(user, declaration);

        SubjectStudyPeriod ssp = em.getReference(SubjectStudyPeriod.class, form.getSubjectStudyPeriod());

        ValidationFailedException.throwIf(declaration.getSubjects().stream()
                .map(ds -> EntityUtil.getId(ds.getSubjectStudyPeriod().getSubject()))
                .anyMatch(sId -> sId.equals(EntityUtil.getId(ssp.getSubject()))),
                "declaration.error.subjectExists");
        ValidationFailedException.throwIf(ssp.getPlaces() != null &&
                ssp.getDeclarationSubjects().size() >= ssp.getPlaces().intValue(),
                "declaration.noPlaces");
        ValidationFailedException.throwIf(hasPositiveResultsForSubjects(EntityUtil.getId(declaration.getStudent()),
                EntityUtil.getId(ssp.getSubject())), "declaration.error.hasPositiveResult");

        CurriculumVersionHigherModuleSubject moduleSubject = studentCvSubjectModule(declaration, form.getSubjectStudyPeriod());
        CurriculumVersionHigherModule module = moduleSubject != null ? moduleSubject.getModule() : null;
        ValidationFailedException.throwIf(module != null &&
                EntityUtil.getCode(module.getType()).equals(HigherModuleType.KORGMOODUL_F.name()) &&
                finalThesisExists(EntityUtil.getId(declaration.getStudent())), "declaration.error.finalThesisExists");

        DeclarationSubject declarationSubject = new DeclarationSubject();
        declarationSubject.setDeclaration(declaration);
        declarationSubject.setSubjectStudyPeriod(ssp);
        declarationSubject.setModule(module);
        declarationSubject.setIsOptional(moduleSubject != null ? moduleSubject.getOptional() : Boolean.TRUE);
        declarationSubject.setSubgroup(EntityUtil.getOptionalOne(SubjectStudyPeriodSubgroup.class, form.getSubgroup(), em));
        DeclarationSubjectDto dto = DeclarationSubjectDto.of(EntityUtil.save(declarationSubject, em));
        setAreSubjectsDeclaredRepeatedy(Arrays.asList(dto), dto.getDeclaration());
        return dto;
    }

    public CurriculumVersionHigherModuleSubject studentCvSubjectModule(Declaration declaration, Long subjectStudyPeriodId) {
        // is curriculum subject check
        CurriculumVersionHigherModuleSubject subjectModule = curriculumSubjectModule(declaration, subjectStudyPeriodId);
        if (subjectModule != null) {
            return subjectModule;
        }

        // is extra curriculum subject check
        if (isExtraCurriculumSubject(declaration, subjectStudyPeriodId)) {
            return null;
        }
        throw new ValidationFailedException("declaration.error.subjectNotAllowed");
    }

    public CurriculumVersionHigherModuleSubject curriculumSubjectModule(Declaration declaration,
            Long subjectStudyPeriodId) {
        // guest and external students might not have connected curriculum
        Long curriculumVersion = EntityUtil.getNullableId(declaration.getStudent().getCurriculumVersion());
        if (curriculumVersion == null) {
            return null;
        }

        JpaNativeQueryBuilder qb = curriculumSubjectOptionsQb(declaration);
        qb.requiredCriteria("ssp.id = :subjectStudyPeriodId", "subjectStudyPeriodId", subjectStudyPeriodId);
        List<?> data = qb.select("cvhms.id", em).setMaxResults(1).getResultList();
        if (data.isEmpty()) {
            return null;
        }
        return em.getReference(CurriculumVersionHigherModuleSubject.class, resultAsLong(data.get(0), 0));
    }

    private boolean isExtraCurriculumSubject(Declaration declaration, Long subjectStudyPeriodId) {
        JpaNativeQueryBuilder qb = extraCurriculumSubjectsOptionsQb(declaration);
        qb.requiredCriteria("ssp.id = :subjectStudyPeriodId", "subjectStudyPeriodId", subjectStudyPeriodId);
        return !qb.select("ssp.id", em).setMaxResults(1).getResultList().isEmpty();
    }

    private boolean finalThesisExists(Long studentId) {
        return !em.createNativeQuery("select ft.id from final_thesis ft where ft.student_id = :studentId")
                .setParameter("studentId", studentId)
                .setMaxResults(1).getResultList().isEmpty();
    }

    public Declaration create(HoisUserDetails user, Long studentId, boolean nextPeriod) {
        return create(user, studentId, nextPeriod, null);
    }

    public Declaration create(HoisUserDetails user, Long studentId, boolean nextPeriod, Long period) {
        AssertionFailedException.throwIf(nextPeriod ? !canCreateNext(user, studentId, period) : !canCreateCurrent(user, studentId), "You cannot create declaration!");

        Student student = em.getReference(Student.class, studentId);
        Long studyPeriodId = nextPeriod ? period : null;
        if (nextPeriod) {
            if (studyPeriodId == null) {
                studyPeriodId = studyYearService.getNextStudyPeriod(user.getSchoolId());
                if(studyPeriodId == null) {
                    throw new ValidationFailedException("studyYear.studyPeriod.missingCurrent");
                }
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

    public Declaration getByPeriod(Long studentId, Long periodId) {
        List<Declaration> result = em.createQuery("select d from Declaration d where d.student.id = ?1 and d.studyPeriod.id = ?2", Declaration.class)
                .setParameter(1, studentId)
                .setParameter(2, periodId)
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

    public JpaNativeQueryBuilder curriculumSubjectOptionsQb(Declaration declaration) {
        Long studyPeriod = EntityUtil.getId(declaration.getStudyPeriod());
        Student student = declaration.getStudent();
        Long curriculumVersion = EntityUtil.getId(student.getCurriculumVersion());
        Long curriculumSpeciality = EntityUtil.getNullableId(student.getCurriculumSpeciality());

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SUBJECT_FROM);
        qb.requiredCriteria(SUBJECT_NOT_IN_DECLARATION, "declarationId", declaration.getId());

        qb.requiredCriteria("cvhm.curriculum_version_id = :curriculumVersionId", "curriculumVersionId",
                curriculumVersion);
        qb.optionalCriteria(isCurriculumSpecialitySubject(":curriculumSpecialityId", "cvhm.id"),
                "curriculumSpecialityId", curriculumSpeciality);
        qb.filter("cvhm.is_minor_speciality = false");

        String examSubjectAllowed = finalExamModuleSubjectAllowedQuery(":studentId", ":curriculumVersionId",
                curriculumSpeciality != null ? curriculumSpeciality.toString() : "null");
        qb.requiredCriteria("(cvhm.type_code != :finalExam or " + examSubjectAllowed + ")", "finalExam",
                HigherModuleType.KORGMOODUL_F);
        qb.parameter("studentId", student.getId());

        qb.requiredCriteria("cvhm.type_code != :finalThesis", "finalThesis", HigherModuleType.KORGMOODUL_L);
        qb.requiredCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", studyPeriod);
        qb.filter("not exists(" + hasPositiveResultQuery(":hprStudentId", "ssp.subject_id", ":hprGradeCode") + ")");
        qb.parameter("hprStudentId", EntityUtil.getId(student));
        qb.parameter("hprGradeCode", HigherAssessment.GRADE_POSITIVE);

        return qb;
    }

    public Page<DeclarationSubjectDto> getCurriculumSubjectOptions(Declaration declaration, Pageable pageable) {
        JpaNativeQueryBuilder qb = curriculumSubjectOptionsQb(declaration).sort(pageable);

        Page<Object[]> result = JpaQueryUtil.pagingResult(qb, SUBJECT_CURRICULUM_SELECT, em, pageable);
        Set<Long> sspIds = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), result.getContent());
        Map<Long, List<SubjectStudyPeriodSubgroupDto>> subgroups = subquerySubgroups(sspIds);
        Map<Long, List<AutocompleteResult>> studentGroupMap = subqueryStudentGroups(sspIds);
        return result.map(r -> {
            DeclarationSubjectDto dto = subjectQueryResultToDto(r);
            List<AutocompleteResult> periodStudentGroups = studentGroupMap.get(dto.getSubjectStudyPeriod());
            dto.setStudentGroups(periodStudentGroups);
            List<SubjectStudyPeriodSubgroupDto> periodSubgroups = subgroups.get(dto.getSubjectStudyPeriod());
            if (periodSubgroups == null || periodSubgroups.isEmpty()) {
                return dto;
            }

            dto.setSubgroups(periodSubgroups.stream().filter(sgDto -> {
                if (sgDto.getDeclared() == null) {
                    return true;
                }
                return sgDto.getDeclared().intValue() < sgDto.getPlaces().intValue();
            }).map(sgDto -> {
                return new AutocompleteResult(sgDto.getId(), sgDto.getNameEt(), sgDto.getNameEn());
            }).collect(Collectors.toCollection(LinkedHashSet::new)));

            if (dto.getSubgroups().isEmpty()) {
                SubjectStudyPeriodSubgroupDto lastSubgroup = periodSubgroups.get(periodSubgroups.size() - 1);
                dto.setSubgroups(Collections.singleton(new AutocompleteResult(lastSubgroup.getId(), lastSubgroup.getNameEt(), lastSubgroup.getNameEn())));
            }
            
            return dto;
        });
    }

    private Map<Long, List<AutocompleteResult>> subqueryStudentGroups(Set<Long> sspIds) {
        if (sspIds == null || sspIds.isEmpty()) {
            return Collections.emptyMap();
        }
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(STUDENT_GROUP_FROM).sort(STUDENT_GROUP_SORT);
        qb.requiredCriteria("ssp.id in :sspIds", "sspIds", sspIds);
        
        List<?> results = qb.select(STUDENT_GROUP_SELECT, em).getResultList();
        return results.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> new AutocompleteResult(resultAsLong(r, 2), resultAsString(r, 1), resultAsString(r, 1)), Collectors.toList())));
    }

    public JpaNativeQueryBuilder extraCurriculumSubjectsOptionsQb(Declaration declaration) {
        Long studyPeriod = EntityUtil.getId(declaration.getStudyPeriod());
        Long curriculumVersion = EntityUtil.getNullableId(declaration.getStudent().getCurriculumVersion());
        Long curriculumSpeciality = EntityUtil.getNullableId(declaration.getStudent().getCurriculumSpeciality());

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SUBJECT_FROM);
        qb.requiredCriteria(SUBJECT_NOT_IN_DECLARATION, "declarationId", declaration.getId());

        if (curriculumSpeciality != null) {
            qb.optionalCriteria("(coalesce(cvhm.curriculum_version_id, 0) != :curriculumVersionId"
                    + " or (cvhm.curriculum_version_id = :curriculumVersionId and :curriculumSpecialityId not in"
                    + " (select cs.id from curriculum_version_hmodule_speciality cvhs"
                    + " join curriculum_version_speciality cvs on cvs.id = cvhs.curriculum_version_speciality_id"
                    + " join curriculum_speciality cs on cs.id = cvs.curriculum_speciality_id"
                    + " where cvhs.curriculum_version_hmodule_id = cvhm.id)))", "curriculumVersionId", curriculumVersion);
            qb.filter("s.id not in (select cvhs2.subject_id from curriculum_version_hmodule_subject cvhs2"
                    + " join curriculum_version_hmodule cvh2 on cvh2.id = cvhs2.curriculum_version_hmodule_id"
                    + " left join curriculum_version_hmodule_speciality cvhsp2 on cvhsp2.curriculum_version_hmodule_id = cvh2.id"
                    + " left join curriculum_version_speciality cvs2 on cvs2.id = cvhsp2.curriculum_version_speciality_id"
                    + " left join curriculum_speciality cs2 on cs2.id = cvs2.curriculum_speciality_id"
                    + " where cvh2.curriculum_version_id = :curriculumVersionId and cs2.id = :curriculumSpecialityId)");
            qb.parameter("curriculumSpecialityId", curriculumSpeciality);
        } else {
            qb.optionalCriteria("coalesce(cvhm.curriculum_version_id, 0) != :curriculumVersionId",
                    "curriculumVersionId", curriculumVersion);
            qb.optionalCriteria("s.id not in (select cvhs2.subject_id from curriculum_version_hmodule_subject cvhs2"
                    + " join curriculum_version_hmodule cvh2 on cvh2.id = cvhs2.curriculum_version_hmodule_id"
                    + " where cvh2.curriculum_version_id = :curriculumVersionId)", "curriculumVersionId", curriculumVersion);
        }

        qb.requiredCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", studyPeriod);
        qb.filter("not exists(" + hasPositiveResultQuery(":hprStudentId", "ssp.subject_id", ":hprGradeCode") + ")");
        qb.parameter("hprStudentId", EntityUtil.getId(declaration.getStudent()));
        qb.parameter("hprGradeCode", HigherAssessment.GRADE_POSITIVE);

        return qb;
    }

    public List<DeclarationSubjectDto> getExtraCurriculumSubjectsOptions(Declaration declaration) {
        JpaNativeQueryBuilder qb = extraCurriculumSubjectsOptionsQb(declaration);
        List<?> result = qb.sort("s.name_et, s.name_en, teachers").select(SUBJECT_EXTRACURRICULUM_SELECT, em).getResultList();
        return StreamUtil.toMappedList(this::subjectQueryResultToDto, result);
    }
    
    private Map<Long, List<SubjectStudyPeriodSubgroupDto>> subquerySubgroups(Set<Long> sspIds) {
        if (sspIds == null || sspIds.isEmpty()) {
            return Collections.emptyMap();
        }
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SUBGROUPS_FROM).sort(SUBGROUPS_SORT);
        qb.requiredCriteria("ssp.id in :sspIds", "sspIds", sspIds);
        
        List<?> results = qb.select(SUBGROUPS_SELECT, em).getResultList();
        return results.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> {
                    SubjectStudyPeriodSubgroupDto dto = new SubjectStudyPeriodSubgroupDto();
                    dto.setId(resultAsLong(r, 1));
                    dto.setCode(resultAsString(r, 2));
                    String teacherName = resultAsString(r, 3);
                    dto.setTeacher(teacherName == null ? null : new AutocompleteResult(null, teacherName, teacherName));
                    dto.setPlaces(resultAsShort(r, 4));
                    dto.setDeclared(resultAsInteger(r, 5));
                    return dto;
                }, Collectors.toList())));
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
        dto.setHasPlaces(resultAsBoolean(r, 12));
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
    
    public AutocompleteResult getNextStudyPeriod(Long schoolId) {
        Long studyPeriodId = studyYearService.getNextStudyPeriod(schoolId);
        if (studyPeriodId == null) {
            return null;
        }
        return AutocompleteResult.ofWithYear(em.getReference(StudyPeriod.class, studyPeriodId));
    }

    public List<AutocompleteResult> getNextStudyPeriods(Long schoolId) {
        return studyYearService.getNextStudyPeriods(schoolId).stream()
                .map(AutocompleteResult::ofWithYear)
                .collect(Collectors.toList());
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
    
    public Page<AutocompleteResult> getStudentsWithoutDeclaration(
            HoisUserDetails user, StudentDeclarationCommand command, Pageable pageable) {
        return searchStudentsWithoutDeclaration(user, command, pageable)
                .map(s -> new AutocompleteResult(s.getId(), s.getFullname(), s.getFullname()));
    }

    public Page<StudentSearchDto> searchStudentsWithoutDeclaration(
            HoisUserDetails user, StudentDeclarationCommand command, Pageable pageable) {
        Long studyPeriodId = Boolean.TRUE.equals(command.getNextPeriod())
                ? user.isSchoolAdmin() && command.getPeriod() != null
                    ? command.getPeriod()
                    : studyYearService.getNextStudyPeriod(user.getSchoolId())
                : studyYearService.getCurrentStudyPeriod(user.getSchoolId());
        if(studyPeriodId == null) {
            return new PageImpl<>(Arrays.asList());
        }
        UserUtil.assertSameSchool(user, em.getReference(StudyPeriod.class, studyPeriodId).getStudyYear().getSchool());

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(WITHOUT_DECLARATION_FROM).sort(pageable);

        qb.requiredCriteria("s.school_id = :schoolId ", "schoolId", user.getSchoolId());
        qb.requiredCriteria("s.status_code in :active ", "active", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.requiredCriteria("(c.is_higher = true or exists (select 1 from directive d"
                + " join directive_student ds on ds.directive_id = d.id and ds.canceled = false"
                + " where ds.student_id = s.id and d.is_higher = true and d.type_code in (:directiveTypes)"
                + " and d.status_code = :directiveStatus))",
                "directiveTypes", EnumUtil.toNameList(DirectiveType.KASKKIRI_EKSTERN, DirectiveType.KASKKIRI_KYLALIS));
        qb.parameter("directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());

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

    public DeclarationAutofillResponseDto addSubjectsToDeclaration(StudyPeriod studyPeriod, Long schoolId) {
        DeclarationAutofillResponseDto dto = new DeclarationAutofillResponseDto();
        Set<Long> studentIdsTotal = new HashSet<>();
        List<SubjectStudyPeriodSearchDto> subjectStudyPeriodsResponse = new ArrayList<>();
        List<SubjectStudyPeriod> subjectStudyPeriods = em.createQuery("select ssp from SubjectStudyPeriod ssp "
                + "where ssp.studyPeriod.id = ?1 "
                + "and ssp.subject.school.id = ?2", SubjectStudyPeriod.class)
                .setParameter(1, EntityUtil.getId(studyPeriod))
                .setParameter(2, schoolId).getResultList();
        for (SubjectStudyPeriod subjectStudyPeriod : subjectStudyPeriods) {
            if (SubjectStudyPeriodDeclarationService.addToDeclarationsByStudentGroup(subjectStudyPeriod) || SubjectStudyPeriodDeclarationService.addToDeclarationsByCurriculum(subjectStudyPeriod)) {
                List<Long> studentIds = subjectStudyPeriodDeclarationService.addToDeclarations(subjectStudyPeriod, true);
                // adding to set keeps unique IDs, makes IDs countable
                studentIdsTotal.addAll(studentIds);
            } else if (subjectStudyPeriod.getDeclarationType() == null) {
                SubjectStudyPeriodSearchDto ssp = new SubjectStudyPeriodSearchDto();
                ssp.setTeachers(PersonUtil.sorted(subjectStudyPeriod.getTeachers().stream().map(t -> t.getTeacher().getPerson())));
                ssp.setId(EntityUtil.getId(subjectStudyPeriod));
                ssp.setSubject(AutocompleteResult.of(subjectStudyPeriod.getSubject()));
                ssp.setStudyPeriod(AutocompleteResult.ofWithYear(subjectStudyPeriod.getStudyPeriod()));
                subjectStudyPeriodsResponse.add(ssp);
            }
        }
        dto.setSubjectStudyPeriods(subjectStudyPeriodsResponse);
        dto.setChangedDeclarations(Long.valueOf(studentIdsTotal.size()));
        return dto;
    }

    public static String hasPositiveResultQuery() {
        return hasPositiveResultQuery(":hprStudentId", ":hprSubjectId", ":hprGradeCode");
    }

    public static String hasPositiveResultQuery(String refStudent, String refSubject, String refGrade) {
        return "select shr.id " +
                "from student_higher_result shr " +
                    "join student_higher_result_replaced_subject shrrs on shr.id = shrrs.student_higher_result_id " +
                "where shr.is_active = true and shr.student_id = " + refStudent + " " +
                    "and shrrs.subject_id = " + refSubject + " " +
                    "and shr.grade_code in " + refGrade + " " +
                "union all " +
                "select shr.id " +
                "from student_higher_result shr " +
                    "join apel_application_record aar on shr.apel_application_record_id = aar.id " +
                    "join apel_application aa on aa.id = aar.apel_application_id and (aa.is_new is null or aa.is_new = false) " +
                    "join apel_application_formal_replaced_subject_or_module aafrs on aafrs.apel_application_record_id = aar.id " +
                "where shr.is_active = true and shr.student_id = " + refStudent + " " +
                    "and aafrs.subject_id = " + refSubject + " " +
                    "and shr.grade_code in " + refGrade + " " +
                "union all " +
                "select shr.id " +
                "from student_higher_result shr " +
                "where shr.is_active = true " +
                    "and shr.student_id = " + refStudent + " " +
                    "and shr.subject_id = " + refSubject + " " +
                    "and shr.grade_code in " + refGrade + " ";
    }

    private boolean hasPositiveResultsForSubjects(Long studentId, Long subjectId) {
        if (studentId == null || subjectId == null) {
            return false;
        }
        return !em.createNativeQuery(hasPositiveResultQuery() + " limit 1")
                .setParameter("hprStudentId", studentId)
                .setParameter("hprSubjectId", subjectId)
                .setParameter("hprGradeCode", HigherAssessment.GRADE_POSITIVE)
                .getResultList().isEmpty();
    }

    public static String isCurriculumSpecialitySubject(String refCurriculumSpeciality, String moduleRef) {
        return refCurriculumSpeciality + " in (select cs.id from curriculum_version_hmodule_speciality cvhs "
                + "join curriculum_version_speciality cvs on cvs.id = cvhs.curriculum_version_speciality_id "
                + "join curriculum_speciality cs on cs.id = cvs.curriculum_speciality_id "
                + "where cvhs.curriculum_version_hmodule_id = " + moduleRef + ")";
    }

    public static String finalExamModuleSubjectAllowedQuery(String refStudent, String refCurriculumVersion,
            String refCurriculumSpeciality) {
        // speciality must be set to declare final exam module subjects when curriculum has multiple specialities
        return  "(case when (select count(spe_cvs.id) from curriculum_version spe_cv "
                + "join curriculum_version_speciality spe_cvs on spe_cvs.curriculum_version_id = spe_cv.id "
                + "where spe_cv.id = " + refCurriculumVersion + ") > 1 then " + refCurriculumSpeciality + " is not null else true end "
                // final exam module subjects should not be declared when final thesis is already created
                + "and not exists (select 1 from final_thesis ft where ft.student_id = " + refStudent + " limit 1))";
    }
}
