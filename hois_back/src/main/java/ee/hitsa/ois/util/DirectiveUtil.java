package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

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
