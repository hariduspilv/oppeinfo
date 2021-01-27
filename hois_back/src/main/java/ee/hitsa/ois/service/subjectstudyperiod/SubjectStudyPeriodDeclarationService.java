package ee.hitsa.ois.service.subjectstudyperiod;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.service.DeclarationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Declaration;
import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModuleSubject;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodSubgroup;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.enums.DeclarationType;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectStudyPeriodUtil;


@Transactional
@Service
public class SubjectStudyPeriodDeclarationService {

    @Autowired
    private DeclarationService declarationService;
    @Autowired
    private EntityManager em;

    private static final String DECLARATION_EXISTS = "exists(select * from declaration d where d.student_id = s.id and d.study_period_id = :studyPeriodId)";
    private static final String DECLARATION_DOES_NOT_EXIST = "not " + DECLARATION_EXISTS;

    private static final String SUBJECT_IS_MANDATORY = "exists(select * "
                + "from curriculum_version_hmodule_subject cvhms "
                + "join curriculum_version_hmodule cvhm on cvhms.curriculum_version_hmodule_id = cvhm.id "
                + "where cvhms.is_optional is false and cvhm.is_minor_speciality = false "
                + "and cvhm.curriculum_version_id = s.curriculum_version_id "
                + "and cvhm.type_code != '" + HigherModuleType.KORGMOODUL_L.name() + "' "
                + "and (s.curriculum_speciality_id is null or "
                    + DeclarationService.isCurriculumSpecialitySubject("s.curriculum_speciality_id", "cvhm.id") + ") "
                + "and (cvhm.type_code != '" + HigherModuleType.KORGMOODUL_F.name() + "' or "
                    + DeclarationService.finalExamModuleSubjectAllowedQuery("s.id", "s.curriculum_version_id",
                        "s.curriculum_speciality_id") + ")"
                + "and cvhms.subject_id = :subjectId)";

    private static final String SUBJECT_NOT_IN_FINAL_MODULE = "not exists(select 1 "
            + "from curriculum_version_hmodule_subject cvhms "
            + "join curriculum_version_hmodule cvhm on cvhms.curriculum_version_hmodule_id = cvhm.id "
            + "join curriculum_version cv on cv.id = cvhm.curriculum_version_id and cv.id = s.curriculum_version_id "
            + "where cvhms.subject_id = :subjectId and cvhm.type_code in :finalModuleTypes)";

    private static final String NOT_DECLARED_IN_CURRENT_TERM = " not exists "
            + "(select ds.id "
            + "from declaration_subject ds "
            + "join declaration d on d.id = ds.declaration_id "
            + "join subject_study_period ssp on ssp.id = ds.subject_study_period_id and ssp.study_period_id = :studyPeriodId "
            + "where d.student_id = s.id "
            + "and ssp.subject_id = :subjectId)";
        
    private static final String NO_POSITIVE_RESULT = "not exists("
            + DeclarationService.hasPositiveResultQuery("s.id", ":subjectId", ":hprGradeCode")
            + ")";

    private static final String STUDENT_IS_STUDYING = " s.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "' ";

    public List<Long> addToDeclarations(SubjectStudyPeriod subjectStudyPeriod, boolean isMandatory) {
        List<Long> studentsWithDeclaration = new ArrayList<>();
        List<Long> studentsWithoutDeclaration = new ArrayList<>();

        if(addToDeclarationsByStudentGroup(subjectStudyPeriod)) {
            studentsWithDeclaration = getStudentsByGroupWithDeclaration(subjectStudyPeriod, isMandatory);
            studentsWithoutDeclaration = getStudentsByGroupWithoutDeclaration(subjectStudyPeriod, isMandatory);
        } else if(addToDeclarationsByCurriculum(subjectStudyPeriod)) {
            studentsWithDeclaration = studentsWithDeclaration(subjectStudyPeriod);
            studentsWithoutDeclaration = studentsWithoutDeclaration(subjectStudyPeriod);
        }
        addSubjectToDeclarations(subjectStudyPeriod, studentsWithDeclaration, studentsWithoutDeclaration);
        return Stream.of(studentsWithDeclaration, studentsWithoutDeclaration).flatMap(p -> p.stream()).collect(Collectors.toList());
    }

    public static boolean addToDeclarationsByStudentGroup(SubjectStudyPeriod subjectStudyPeriod) {
        return ClassifierUtil.equals(DeclarationType.DEKLARATSIOON_KOIK, subjectStudyPeriod.getDeclarationType());
    }

    public static boolean addToDeclarationsByCurriculum(SubjectStudyPeriod subjectStudyPeriod) {
        return ClassifierUtil.equals(DeclarationType.DEKLARATSIOON_LISA, subjectStudyPeriod.getDeclarationType());
    }

    private List<Long> studentsWithDeclaration(SubjectStudyPeriod subjectStudyPeriod) {
        Long schoolId = EntityUtil.getId(subjectStudyPeriod.getSubject().getSchool());
        Long studyPeriod = EntityUtil.getId(subjectStudyPeriod.getStudyPeriod());
        Long subjectId = EntityUtil.getId(subjectStudyPeriod.getSubject());

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria(DECLARATION_EXISTS, "studyPeriodId", studyPeriod);
        qb.requiredCriteria(NOT_DECLARED_IN_CURRENT_TERM, "subjectId", subjectId);
        qb.requiredCriteria(SUBJECT_IS_MANDATORY, "subjectId", subjectId);
        qb.filter(STUDENT_IS_STUDYING);
        qb.filter(NO_POSITIVE_RESULT);
        qb.parameter("hprGradeCode", HigherAssessment.GRADE_POSITIVE);

        List<?> data = qb.select("s.id", em).getResultList();
        return StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
    }

    private List<Long> studentsWithoutDeclaration(SubjectStudyPeriod subjectStudyPeriod) {
        Long schoolId = EntityUtil.getId(subjectStudyPeriod.getSubject().getSchool());
        Long studyPeriod = EntityUtil.getId(subjectStudyPeriod.getStudyPeriod());
        Long subjectId = EntityUtil.getId(subjectStudyPeriod.getSubject());

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria(DECLARATION_DOES_NOT_EXIST, "studyPeriodId", studyPeriod);
        qb.requiredCriteria(NOT_DECLARED_IN_CURRENT_TERM, "subjectId", subjectId);
        qb.requiredCriteria(SUBJECT_IS_MANDATORY, "subjectId", subjectId);
        qb.filter(STUDENT_IS_STUDYING);
        qb.filter(NO_POSITIVE_RESULT);
        qb.parameter("hprGradeCode", HigherAssessment.GRADE_POSITIVE);

        List<?> data = qb.select("s.id", em).getResultList();
        return StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
    }

    private List<Long> getStudentsByGroupWithDeclaration(SubjectStudyPeriod subjectStudyPeriod, boolean isMandatory) {
        Long schoolId = EntityUtil.getId(subjectStudyPeriod.getSubject().getSchool());
        Long studyPeriod = EntityUtil.getId(subjectStudyPeriod.getStudyPeriod());
        Long subjectId = EntityUtil.getId(subjectStudyPeriod.getSubject());
        Set<Long> studentGroups = StreamUtil.toMappedSet(sg -> EntityUtil.getId(sg.getStudentGroup()), subjectStudyPeriod.getStudentGroups());

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("s.student_group_id in :studentGroupIds", "studentGroupIds", studentGroups);
        qb.requiredCriteria(DECLARATION_EXISTS, "studyPeriodId", studyPeriod);
        qb.requiredCriteria(NOT_DECLARED_IN_CURRENT_TERM, "subjectId", subjectId);
        if (isMandatory) {
            qb.requiredCriteria(SUBJECT_IS_MANDATORY, "subjectId", subjectId);
        } else {
            qb.requiredCriteria(SUBJECT_NOT_IN_FINAL_MODULE, "subjectId", subjectId);
            qb.parameter("finalModuleTypes", HigherModuleType.FINAL_MODULES);
        }
        qb.filter(STUDENT_IS_STUDYING);
        qb.filter(NO_POSITIVE_RESULT);
        qb.parameter("hprGradeCode", HigherAssessment.GRADE_POSITIVE);

        List<?> data = qb.select("s.id", em).getResultList();
        return StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
    }

    private List<Long> getStudentsByGroupWithoutDeclaration(SubjectStudyPeriod subjectStudyPeriod, boolean isMandatory) {
        Long schoolId = EntityUtil.getId(subjectStudyPeriod.getSubject().getSchool());
        Long studyPeriod = EntityUtil.getId(subjectStudyPeriod.getStudyPeriod());
        Long subjectId = EntityUtil.getId(subjectStudyPeriod.getSubject());
        Set<Long> studentGroups = StreamUtil.toMappedSet(sg -> EntityUtil.getId(sg.getStudentGroup()), subjectStudyPeriod.getStudentGroups());

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("s.student_group_id in :studentGroupIds", "studentGroupIds", studentGroups);
        qb.requiredCriteria(DECLARATION_DOES_NOT_EXIST, "studyPeriodId", studyPeriod);
        qb.requiredCriteria(NOT_DECLARED_IN_CURRENT_TERM, "subjectId", subjectId);
        if (isMandatory) {
            qb.requiredCriteria(SUBJECT_IS_MANDATORY, "subjectId", subjectId);
        } else {
            qb.requiredCriteria(SUBJECT_NOT_IN_FINAL_MODULE, "subjectId", subjectId);
            qb.parameter("finalModuleTypes", HigherModuleType.FINAL_MODULES);
        }
        qb.filter(STUDENT_IS_STUDYING);
        qb.filter(NO_POSITIVE_RESULT);
        qb.parameter("hprGradeCode", HigherAssessment.GRADE_POSITIVE);

        List<?> data = qb.select("s.id", em).getResultList();
        return StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
    }

    private void addSubjectToDeclarations(SubjectStudyPeriod subjectStudyPeriod, List<Long> studentsWithDeclaration,
            List<Long> studentsWithoutDeclaration) {

        List<Declaration> declarations = new ArrayList<>();
        if(!CollectionUtils.isEmpty(studentsWithDeclaration)) {
            declarations.addAll(existingStudentsDeclarations(studentsWithDeclaration, subjectStudyPeriod.getStudyPeriod()));
        }
        if(!CollectionUtils.isEmpty(studentsWithoutDeclaration)) {
            declarations.addAll(createDeclarations(studentsWithoutDeclaration, subjectStudyPeriod.getStudyPeriod()));
        }
        addSubjectToDeclarations(declarations, subjectStudyPeriod);
    }

    private void addSubjectToDeclarations(List<Declaration> declarations, SubjectStudyPeriod subjectStudyPeriod) {
        LinkedList<SubjectStudyPeriodSubgroup> sortedSubgroupsByCode = new LinkedList<>(subjectStudyPeriod.getSubgroups().stream()
                .sorted(SubjectStudyPeriodUtil.COMPARATOR_SUBGROUP)
                .collect(Collectors.toList()));

        SubjectStudyPeriodSubgroup lastSubgroup = sortedSubgroupsByCode.peekLast();
        SubjectStudyPeriodSubgroup subgroup = sortedSubgroupsByCode.pollFirst();
        
        for(Declaration declaration : declarations) {
            CurriculumVersionHigherModuleSubject moduleSubject = declarationService.curriculumSubjectModule(declaration,
                    EntityUtil.getId(subjectStudyPeriod));

            DeclarationSubject ds = new DeclarationSubject();
            ds.setDeclaration(declaration);
            ds.setSubjectStudyPeriod(subjectStudyPeriod);
            ds.setModule(moduleSubject != null ? moduleSubject.getModule() : null);
            ds.setIsOptional(moduleSubject != null ? moduleSubject.getOptional() : Boolean.TRUE);
            while (subgroup != null && subgroup.getDeclarationSubjects().size() >= subgroup.getPlaces().intValue()) {
                subgroup = sortedSubgroupsByCode.pollFirst();
            }
            
            if (subgroup == null) {
                if (lastSubgroup != null) {
                    lastSubgroup.addDeclarationSubject(ds);
                }
            } else {
                subgroup.addDeclarationSubject(ds);
            }
            em.persist(ds);
        }
    }

    private List<Declaration> createDeclarations(List<Long> studentsWithoutDeclaration, StudyPeriod studyPeriod) {
        List<Declaration> declarations = new ArrayList<>();
        for(Long studentId : studentsWithoutDeclaration) {
            Declaration declaration = new Declaration();
            declaration.setStudent(em.getReference(Student.class, studentId));
            declaration.setStudyPeriod(studyPeriod);
            declaration.setStatus(em.getReference(Classifier.class, DeclarationStatus.OPINGUKAVA_STAATUS_S.name()));
            em.persist(declaration);
            declarations.add(declaration);
        }
        return declarations;
    }

    private List<Declaration> existingStudentsDeclarations(List<Long> studentsWithDeclaration, StudyPeriod studyPeriod) {
        return em.createQuery("select d from Declaration d where d.studyPeriod.id = ?1 and d.student.id in (?2)", Declaration.class)
            .setParameter(1, EntityUtil.getId(studyPeriod)).setParameter(2, studentsWithDeclaration).getResultList();
    }
}
