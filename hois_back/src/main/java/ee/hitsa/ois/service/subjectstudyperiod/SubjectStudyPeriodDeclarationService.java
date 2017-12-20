package ee.hitsa.ois.service.subjectstudyperiod;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
import ee.hitsa.ois.enums.DeclarationType;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;


@Transactional
@Service
public class SubjectStudyPeriodDeclarationService {

    @Autowired
    private EntityManager em;

    private static final String DECLARATION_EXISTS = "exists(select * from declaration d where d.student_id = s.id and d.study_period_id = :studyPeriodId)";
    private static final String DECLARATION_DOES_NOT_EXIST = "not " + DECLARATION_EXISTS;

    private static final String SUBJECT_IS_MANDATORY = "exists( select * "
                + "from curriculum_version_hmodule_subject cvhms "
                + "join curriculum_version_hmodule cvhm on cvhms.curriculum_version_hmodule_id = cvhm.id "
                + "where cvhms.is_optional is false "
                + "and cvhm.curriculum_version_id = s.curriculum_version_id "
                + "and cvhms.subject_id = :subjectId)";
    
    private static final String NOT_DECLARED_IN_CURRENT_TERM = " not exists "
            + "(select ds.id "
            + "from declaration_subject ds "
            + "join declaration d on d.id = ds.declaration_id "
            + "join subject_study_period ssp on ssp.id = ds.subject_study_period_id "
            + "where d.student_id = s.id "
            + "and ssp.id = :subjectStudyPeriodId)";
        
    private static final String NO_POSITIVE_RESULT = ""
            + " not exists "
            + "("
                + " select ps.id from protocol_student ps join protocol p on p.id = ps.protocol_id"
                + " join protocol_hdata hdata on hdata.protocol_id = p.id"
                + " join subject_study_period ssp on ssp.id = hdata.subject_study_period_id"
                + " where ssp.subject_id = :subjectId"
                + " and ps.student_id = s.id"
                + " and ps.grade_code not in "
                + "("
                    + "'" + HigherAssessment.KORGHINDAMINE_0.name() + "', "
                    + "'" + HigherAssessment.KORGHINDAMINE_M.name() + "', "
                    + "'" + HigherAssessment.KORGHINDAMINE_MI.name() + "'"
                + ")"
            + ")";

    private static final String STUDENT_IS_STUDYING = " s.status_code = '" + StudentStatus.OPPURSTAATUS_O.name() + "' ";

    public void addToDeclarations(SubjectStudyPeriod subjectStudyPeriod) {
        List<Long> studentsWithDeclaration = new ArrayList<>();
        List<Long> studentsWithoutDeclaration = new ArrayList<>();

        if(addToDeclarationsByStudentGroup(subjectStudyPeriod)) {
            studentsWithDeclaration = getStudentsByGroupWithDeclaration(subjectStudyPeriod);
            studentsWithoutDeclaration = getStudentsByGroupWithoutDeclaration(subjectStudyPeriod);
        } else if(addToDeclarationsByCurriculum(subjectStudyPeriod)) {
            studentsWithDeclaration = studentsWithDeclaration(subjectStudyPeriod);
            studentsWithoutDeclaration = studentsWithoutDeclaration(subjectStudyPeriod);
        }
        addSubjectToDeclations(subjectStudyPeriod, studentsWithDeclaration, studentsWithoutDeclaration);
    }

    private static boolean addToDeclarationsByStudentGroup(SubjectStudyPeriod subjectStudyPeriod) {
        return ClassifierUtil.equals(DeclarationType.DEKLARATSIOON_KOIK, subjectStudyPeriod.getDeclarationType());
    }

    private static boolean addToDeclarationsByCurriculum(SubjectStudyPeriod subjectStudyPeriod) {
        return ClassifierUtil.equals(DeclarationType.DEKLARATSIOON_LISA, subjectStudyPeriod.getDeclarationType());
    }

    private List<Long> studentsWithDeclaration(SubjectStudyPeriod subjectStudyPeriod) {
        Long schoolId = EntityUtil.getId(subjectStudyPeriod.getSubject().getSchool());
        Long studyPeriod = EntityUtil.getId(subjectStudyPeriod.getStudyPeriod());
        Long subjectId = EntityUtil.getId(subjectStudyPeriod.getSubject());
        Long subjectStudyPeriodId = EntityUtil.getId(subjectStudyPeriod);

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria(DECLARATION_EXISTS, "studyPeriodId", studyPeriod);
        qb.requiredCriteria(NOT_DECLARED_IN_CURRENT_TERM, "subjectStudyPeriodId", subjectStudyPeriodId);
        qb.requiredCriteria(NO_POSITIVE_RESULT, "subjectId", subjectId);
        qb.requiredCriteria(SUBJECT_IS_MANDATORY, "subjectId", subjectId);
        qb.filter(STUDENT_IS_STUDYING);

        List<?> data = qb.select("s.id", em).getResultList();
        return StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
    }

    private List<Long> studentsWithoutDeclaration(SubjectStudyPeriod subjectStudyPeriod) {
        Long schoolId = EntityUtil.getId(subjectStudyPeriod.getSubject().getSchool());
        Long studyPeriod = EntityUtil.getId(subjectStudyPeriod.getStudyPeriod());
        Long subjectId = EntityUtil.getId(subjectStudyPeriod.getSubject());
        Long subjectStudyPeriodId = EntityUtil.getId(subjectStudyPeriod);

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria(DECLARATION_DOES_NOT_EXIST, "studyPeriodId", studyPeriod);
        qb.requiredCriteria(NOT_DECLARED_IN_CURRENT_TERM, "subjectStudyPeriodId", subjectStudyPeriodId);
        qb.requiredCriteria(NO_POSITIVE_RESULT, "subjectId", subjectId);
        qb.requiredCriteria(SUBJECT_IS_MANDATORY, "subjectId", subjectId);
        qb.filter(STUDENT_IS_STUDYING);

        List<?> data = qb.select("s.id", em).getResultList();
        return StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
    }

    private List<Long> getStudentsByGroupWithDeclaration(SubjectStudyPeriod subjectStudyPeriod) {
        Long schoolId = EntityUtil.getId(subjectStudyPeriod.getSubject().getSchool());
        Long studyPeriod = EntityUtil.getId(subjectStudyPeriod.getStudyPeriod());
        Long subjectId = EntityUtil.getId(subjectStudyPeriod.getSubject());
        Long subjectStudyPeriodId = EntityUtil.getId(subjectStudyPeriod);
        Set<Long> studentGroups = StreamUtil.toMappedSet(sg -> EntityUtil.getId(sg.getStudentGroup()), subjectStudyPeriod.getStudentGroups());

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("s.student_group_id in :studentGroupIds", "studentGroupIds", studentGroups);
        qb.requiredCriteria(DECLARATION_EXISTS, "studyPeriodId", studyPeriod);
        qb.requiredCriteria(NOT_DECLARED_IN_CURRENT_TERM, "subjectStudyPeriodId", subjectStudyPeriodId);
        qb.requiredCriteria(NO_POSITIVE_RESULT, "subjectId", subjectId);
        qb.filter(STUDENT_IS_STUDYING);

        List<?> data = qb.select("s.id", em).getResultList();
        return StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
    }

    private List<Long> getStudentsByGroupWithoutDeclaration(SubjectStudyPeriod subjectStudyPeriod) {
        Long schoolId = EntityUtil.getId(subjectStudyPeriod.getSubject().getSchool());
        Long studyPeriod = EntityUtil.getId(subjectStudyPeriod.getStudyPeriod());
        Long subjectId = EntityUtil.getId(subjectStudyPeriod.getSubject());
        Long subjectStudyPeriodId = EntityUtil.getId(subjectStudyPeriod);
        Set<Long> studentGroups = StreamUtil.toMappedSet(sg -> EntityUtil.getId(sg.getStudentGroup()), subjectStudyPeriod.getStudentGroups());

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("s.student_group_id in :studentGroupIds", "studentGroupIds", studentGroups);
        qb.requiredCriteria(DECLARATION_DOES_NOT_EXIST, "studyPeriodId", studyPeriod);
        qb.requiredCriteria(NOT_DECLARED_IN_CURRENT_TERM, "subjectStudyPeriodId", subjectStudyPeriodId);
        qb.requiredCriteria(NO_POSITIVE_RESULT, "subjectId", subjectId);
        qb.filter(STUDENT_IS_STUDYING);

        List<?> data = qb.select("s.id", em).getResultList();
        return StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
    }

    private void addSubjectToDeclations(SubjectStudyPeriod subjectStudyPeriod, List<Long> studentsWithDeclaration,
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
        for(Declaration declaration : declarations) {
            DeclarationSubject ds = new DeclarationSubject();
            ds.setDeclaration(declaration);
            ds.setSubjectStudyPeriod(subjectStudyPeriod);
            ds.setModule(getHigherModuleOfSubject(declaration, subjectStudyPeriod));
            ds.setIsOptional(Boolean.FALSE);
            em.persist(ds);
        }
    }

    private CurriculumVersionHigherModule getHigherModuleOfSubject(Declaration declaration, SubjectStudyPeriod subjectStudyPeriod) {
        Long subjectId = EntityUtil.getId(subjectStudyPeriod.getSubject());
        Long curriculumVersionId = EntityUtil.getId(declaration.getStudent().getCurriculumVersion());

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from curriculum_version_hmodule_subject cvhms "
                + "join curriculum_version_hmodule cvhm on cvhm.id = cvhms.curriculum_version_hmodule_id");
        qb.requiredCriteria("cvhms.subject_id = :subjectId ", "subjectId", subjectId);
        qb.requiredCriteria("cvhm.curriculum_version_id = :curriculumVersionId ", "curriculumVersionId", curriculumVersionId);
        qb.filter("cvhm.is_minor_speciality is false ");

        List<?> data = qb.select("cvhms.curriculum_version_hmodule_id ", em).setMaxResults(1).getResultList();
        if(data.isEmpty()) {
            return null;
        }
        return em.getReference(CurriculumVersionHigherModule.class, resultAsLong(data.get(0), 0));
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