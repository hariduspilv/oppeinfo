package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Form;
import ee.hitsa.ois.domain.diploma.Diploma;
import ee.hitsa.ois.domain.diploma.DiplomaSupplement;
import ee.hitsa.ois.domain.diploma.DiplomaSupplementForm;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.FormStatus;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

public abstract class DirectiveUtil {
    
    private static final Comparator<DirectiveStudent> ID_COMPARATOR = Comparator.comparing(DirectiveStudent::getId);
    private static final Comparator<DirectiveStudent> LASTNAME_COMPARATOR = Comparator.comparing(ds -> ds.getPerson().getLastname());
    private static final Comparator<DirectiveStudent> GRADUATE_COMPARATOR = Comparator.<DirectiveStudent, String>comparing(
            ds -> EntityUtil.getCode(ds.getStudent().getCurriculumVersion().getCurriculum().getOrigStudyLevel()))
            .thenComparing(ds -> ds.getStudent().getCurriculumVersion().getCurriculum().getNameEt().toUpperCase())
            .thenComparing(ds -> ds.getStudent().getStudentGroup(), Comparator.nullsLast(Comparator.comparing(sg -> sg.getCode().toUpperCase())))
            .thenComparing(ds -> ds.getPerson().getLastname().toUpperCase())
            .thenComparing(ds -> ds.getPerson().getFirstname(), Comparator.nullsLast(Comparator.comparing(fn -> fn.toUpperCase())));

    public static DirectiveStudent getDirectiveStudent(Directive directive, Long studentId) {
        return directive.getStudents().stream()
                .filter(ds -> !Boolean.TRUE.equals(ds.getCanceled()) 
                        && studentId.equals(EntityUtil.getNullableId(ds.getStudent())))
                .findAny().get();
    }
    
    public static Comparator<DirectiveStudent> getStudentDtoComparator(DirectiveType directiveType) {
        if (DirectiveType.KASKKIRI_LOPET.equals(directiveType)) {
            return GRADUATE_COMPARATOR;
        }
        return ID_COMPARATOR;
    }
    
    public static Comparator<DirectiveStudent> getStudentEkisComparator(DirectiveType directiveType) {
        if (DirectiveType.KASKKIRI_LOPET.equals(directiveType)) {
            return GRADUATE_COMPARATOR;
        }
        return LASTNAME_COMPARATOR;
    }

    public static void cancelFormsAndDocuments(String username, List<DirectiveStudent> directiveStudents, EntityManager em) {
        if (directiveStudents == null || directiveStudents.isEmpty()) {
            return;
        }
        Set<Long> studentIds = directiveStudents.stream()
                .map(ds -> EntityUtil.getNullableId(ds.getStudent()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Long, List<Form>> formsByStudentId = fetchFormsByStudentIds(studentIds, em);
        Map<Long, List<Form>> supFormsByStudentId = fetchSupplementFormsByStudentIds(studentIds, em);
        Map<Long, List<DiplomaSupplementForm>> supplementFormsByStudentId =
                fetchDiplomaSupplementFormsByStudentIds(studentIds, em);
        Map<Long, List<DiplomaSupplement>> supplementsByStudentId = fetchDiplomaSupplementsByStudentIds(studentIds, em);
        Map<Long, List<Diploma>> diplomasByStudentId = fetchDiplomasByStudentIds(studentIds, em);

        directiveStudents.forEach(directiveStudent -> {
            Directive directive = directiveStudent.getDirective();
            Student student = directiveStudent.getStudent();
            List<Form> forms = formsByStudentId.getOrDefault(EntityUtil.getNullableId(student), Collections.emptyList());
            List<Form> supForms = supFormsByStudentId.getOrDefault(EntityUtil.getNullableId(student), Collections.emptyList());
            Classifier formStatus = em.getReference(Classifier.class, FormStatus.LOPUBLANKETT_STAATUS_R.name());
            String reason = "Käskkiri nr " + (directive.getDirectiveNr() != null ? directive.getDirectiveNr() : " ")
                    + " (" + DateUtils.date(directive.getConfirmDate()) + ") tühistatud";
            LocalDate now = LocalDate.now();
            Stream.concat(forms.stream(), supForms.stream()).forEach(form -> {
                form.setStatus(formStatus);
                form.setDefectReason(reason);
                form.setDefected(now);
                form.setDefectedBy(username);
                EntityUtil.save(form, em);
            });

            for (DiplomaSupplementForm supplementForm : supplementFormsByStudentId
                    .getOrDefault(EntityUtil.getNullableId(student), Collections.emptyList())) {
                EntityUtil.deleteEntity(supplementForm, em);
            }
            for (DiplomaSupplement supplement : supplementsByStudentId
                    .getOrDefault(EntityUtil.getNullableId(student), Collections.emptyList())) {
                EntityUtil.deleteEntity(supplement, em);
            }
            for (Diploma diploma : diplomasByStudentId
                    .getOrDefault(EntityUtil.getNullableId(student), Collections.emptyList())) {
                EntityUtil.deleteEntity(diploma, em);
            }
        });
    }

    private static Map<Long, List<Form>> fetchFormsByStudentIds(Set<Long> studentIds, EntityManager em) {
        if (studentIds == null || studentIds.isEmpty()) {
            return Collections.emptyMap();
        }
        return em.createQuery("select d.student.id, d.form from Diploma d " +
                "where d.student.id in :studentIds and d.form.status.code = :statusCode", Object[].class)
                .setParameter("studentIds", studentIds)
                .setParameter("statusCode", FormStatus.LOPUBLANKETT_STAATUS_T.name())
                .getResultList().stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                        Collectors.mapping(r -> (Form) r[1], Collectors.toList())));
    }

    private static Map<Long, List<Form>> fetchSupplementFormsByStudentIds(Set<Long> studentIds, EntityManager em) {
        if (studentIds == null || studentIds.isEmpty()) {
            return Collections.emptyMap();
        }
        return em.createQuery("select dip.student.id, f " +
                "from DiplomaSupplementForm dsf " +
                "join dsf.diplomaSupplement ds " +
                "join ds.diploma dip " +
                "join dsf.form f " +
                "where dip.student.id in :studentIds " +
                "and f.status.code = :statusCode", Object[].class)
                .setParameter("studentIds", studentIds)
                .setParameter("statusCode", FormStatus.LOPUBLANKETT_STAATUS_T.name())
                .getResultList().stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                        Collectors.mapping(r -> (Form) r[1], Collectors.toList())));
    }

    private static Map<Long, List<DiplomaSupplementForm>> fetchDiplomaSupplementFormsByStudentIds(
            Set<Long> studentIds, EntityManager em) {
        if (studentIds == null || studentIds.isEmpty()) {
            return Collections.emptyMap();
        }
        return em.createQuery("select dsf.diplomaSupplement.diploma.student.id, dsf from DiplomaSupplementForm dsf " +
                "where dsf.diplomaSupplement.diploma.student.id in :studentIds", Object[].class)
                .setParameter("studentIds", studentIds)
                .getResultList().stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                        Collectors.mapping(r -> (DiplomaSupplementForm) r[1], Collectors.toList())));
    }

    private static Map<Long, List<DiplomaSupplement>> fetchDiplomaSupplementsByStudentIds(
            Set<Long> studentIds, EntityManager em) {
        if (studentIds == null || studentIds.isEmpty()) {
            return Collections.emptyMap();
        }
        return em.createQuery("select ds.diploma.student.id, ds from DiplomaSupplement ds " +
                "where ds.diploma.student.id in :studentIds", Object[].class)
                .setParameter("studentIds", studentIds)
                .getResultList().stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                        Collectors.mapping(r -> (DiplomaSupplement) r[1], Collectors.toList())));
    }

    private static Map<Long, List<Diploma>> fetchDiplomasByStudentIds(Set<Long> studentIds, EntityManager em) {
        if (studentIds == null || studentIds.isEmpty()) {
            return Collections.emptyMap();
        }
        return em.createQuery("select d.student.id, d from Diploma d " +
                "where d.student.id in :studentIds", Object[].class)
                .setParameter("studentIds", studentIds)
                .getResultList().stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                        Collectors.mapping(r -> (Diploma) r[1], Collectors.toList())));
    }

    public static void cancelFormsAndDocuments(String username, DirectiveStudent directiveStudent, EntityManager em) {
        Directive directive = directiveStudent.getDirective();
        Student student = directiveStudent.getStudent();
        List<Form> forms = em.createQuery("select d.form from Diploma d"
                + " where d.student = ?1 and d.form.status.code = ?2", Form.class)
                .setParameter(1, student)
                .setParameter(2, FormStatus.LOPUBLANKETT_STAATUS_T.name())
                .getResultList();
        forms.addAll(em.createQuery("select dsf.form from DiplomaSupplementForm dsf"
                + " where dsf.diplomaSupplement.diploma.student = ?1"
                + " and dsf.form.status.code = ?2", Form.class)
                .setParameter(1, student)
                .setParameter(2, FormStatus.LOPUBLANKETT_STAATUS_T.name())
                .getResultList());
        Classifier formStatus = em.getReference(Classifier.class, FormStatus.LOPUBLANKETT_STAATUS_R.name());
        String reason = "Käskkiri nr " + (directive.getDirectiveNr() != null ? directive.getDirectiveNr() : " ")
                + " (" + DateUtils.date(directive.getConfirmDate()) + ") tühistatud";
        LocalDate now = LocalDate.now();
        for (Form form : forms) {
            form.setStatus(formStatus);
            form.setDefectReason(reason);
            form.setDefected(now);
            form.setDefectedBy(username);
            EntityUtil.save(form, em);
        }
        
        List<DiplomaSupplementForm> supplementForms = em.createQuery("select dsf from DiplomaSupplementForm dsf"
                + " where dsf.diplomaSupplement.diploma.student = ?1", DiplomaSupplementForm.class)
                .setParameter(1, student)
                .getResultList();
        for (DiplomaSupplementForm supplementForm : supplementForms) {
            EntityUtil.deleteEntity(supplementForm, em);
        }
        List<DiplomaSupplement> supplements = em.createQuery("select ds from DiplomaSupplement ds"
                + " where ds.diploma.student = ?1", DiplomaSupplement.class)
                .setParameter(1, student)
                .getResultList();
        for (DiplomaSupplement supplement : supplements) {
            EntityUtil.deleteEntity(supplement, em);
        }
        List<Diploma> diplomas = em.createQuery("select d from Diploma d"
                + " where d.student = ?1", Diploma.class)
                .setParameter(1, student)
                .getResultList();
        for (Diploma diploma : diplomas) {
            EntityUtil.deleteEntity(diploma, em);
        }
    }

}
