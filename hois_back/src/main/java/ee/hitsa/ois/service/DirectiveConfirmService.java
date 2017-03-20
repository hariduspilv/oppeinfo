package ee.hitsa.ois.service;

import static ee.hitsa.ois.enums.DirectiveType.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.repository.ApplicationRepository;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;

@Transactional
@Service
public class DirectiveConfirmService {

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private StudentService studentService;

    public void confirm(HoisUserDetails user, Directive directive, LocalDate confirmDate) {
        // TODO assert correct status
        // update directive fields
        directive.setStatus(classifierRepository.getOne(DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name()));
        directive.setConfirmDate(confirmDate);
        directive.setConfirmer(user.getUsername());

        DirectiveType directiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getType()));
        if(KASKKIRI_TYHIST.equals(directiveType)) {
            cancelDirective(directive);
        } else {
            Classifier studentStatus = directiveType.studentStatus() != null ? classifierRepository.getOne(directiveType.studentStatus().name()) : null;
            for(DirectiveStudent ds : directive.getStudents()) {
                // TODO put changes which should occur in future, into task queue
                // store student version for undo
                ds.setStudentHistory(ds.getStudent().getStudentHistory());
                updateStudentApplicationStatus(ds, ApplicationStatus.AVALDUS_STAATUS_KINNITATUD);
                updateStudentData(directiveType, ds, studentStatus);
            }
        }
    }

    private void updateStudentData(DirectiveType directiveType, DirectiveStudent directive, Classifier studentStatus) {
        Student student = directive.getStudent();

        switch(directiveType) {
        case KASKKIRI_AKAD:
            // Akadeemilisele puhkusele lubamise käskkiri
            // TODO Õppuri eeldatav nominaalaja lõppkuupäev ==  „õppuri eeldatav nominaalaja lõppkuupäev“ + „akadeemilise puhkuse pikkus“.
            break;
        case KASKKIRI_AKADK:
            // Akadeemilise puhkuse katkestamise käskkiri
            // TODO Õppuri eeldatav nominaalaja lõppkuupäev ==  „õppuri eeldatav nominaalaja lõppkuupäev“ – „päevade arv, mis jäi akadeemilise puhkuse lõppkuupäeva ja akadeemilise puhkuse katkestamise kuupäeva vahele“.
            // startDate - katkestamise kuupäev
            break;
        case KASKKIRI_EKSMAT:
            // Eksmatrikuleerimise käskkiri
            // TODO Õppuri eksmatrikuleerimise kuupäev == käskkirja EKIS-es kinnitamise kuupäev
            // TODO Õppuri rollile ’õppur’ märgitakse kehtivuse lõppkuupäevaks käskkirja kinnitamise kuupäev
            break;
        case KASKKIRI_IMMAT:
        case KASKKIRI_IMMATV:
            // Immatrikuleerimise käskkiri
            student = createStudent(directive);
            break;
        case KASKKIRI_ENNIST:
            // Ennistamise käskkiri
            // TODO Ennistamise kuupäev == käskkirjal olev väärtus;
            // TODO Õppuri roll ’õppur’ muudetakse käskkirja kinnitamise kuupäevast alates kehtivaks;
            break;
        case KASKKIRI_KYLALIS:
            // Külalisõpilaseks vormistamise käskkiri
            // TODO Õppuri eeldatav nominaalaja lõppkuupäev ==  õppuri eeldatav nominaalaja lõppkuupäev + „nominaalaega pikendatakse“ väljale valitud semestrite arvu alusel kas ühe lisanduva semestri pikkus või kahe lisanduva semestri kogupikkus
            break;
        case RIIGIKEEL:
            // Riigikeele süvaõppe käskkiri
            // TODO Õppuri eeldatav nominaalaja lõppkuupäev ==  õppuri eeldatav nominaalaja lõppkuupäev + „nominaalaega pikendatakse“ väljale valitud EAP alusel (30 EAP – 1 semester, 60 EAP – 2 semestrit)  kas ühe lisanduva semestri pikkus või kahe lisanduva semestri kogupikkus
            break;
        default:
            break;
        }

        copyDirectiveProperties(directiveType, directive, student, false);

        // optional new status
        if(studentStatus != null) {
            student.setStatus(studentStatus);
        }
        studentService.saveWithHistory(student);
    }

    private static void cancelDirective(Directive directive) {
        // cancellation may include only some students
        Set<Long> includedStudentIds = directive.getStudents().stream().map(ds -> EntityUtil.getId(ds.getStudent())).collect(Collectors.toSet());
        DirectiveType canceledDirectiveType = DirectiveType.valueOf(EntityUtil.getCode(directive.getCanceledDirective().getType()));        
        for(DirectiveStudent ds : directive.getCanceledDirective().getStudents()) {
            Student student = ds.getStudent();
            if(includedStudentIds.contains(student.getId())) {
                if(!KASKKIRI_IMMAT.equals(canceledDirectiveType) && !KASKKIRI_IMMATV.equals(canceledDirectiveType)) {
                    copyDirectiveProperties(canceledDirectiveType, ds.getStudentHistory(), student, true);
                } else {
                    // TODO undo create student
                }
            }
        }
    }

    private void updateStudentApplicationStatus(DirectiveStudent directive, ApplicationStatus status) {
        Application application = directive.getApplication();
        if(application != null) {
            application.setStatus(classifierRepository.getOne(status.name()));
            applicationRepository.save(application);
        }
    }

    private static Student createStudent(DirectiveStudent directive) {
        Student student = new Student();
        student.setPerson(directive.getPerson());
        student.setSchool(directive.getDirective().getSchool());
        // TODO Õppima asumise kuupäev == käskkirjal olev väärtus
        // TODO Kursus == käskkirjal olev väärtus
        // TODO „Aktiivne == „jah“
        // TODO Õppurile omistatakse roll ’õppur’ alates käskkirja kinnitamise kuupäevast
        return student;
    }

    private static void copyDirectiveProperties(DirectiveType directiveType, Object source, Student student, boolean cancellation) {
        String[] copiedProperties = directiveType.updatedFields();
        if(copiedProperties.length > 0) {
            PropertyAccessor reader = PropertyAccessorFactory.forBeanPropertyAccess(source);
            PropertyAccessor writer = PropertyAccessorFactory.forBeanPropertyAccess(student);
            for(String propertyName : copiedProperties) {
                if(reader.isReadableProperty(propertyName)) {
                    Object value = reader.getPropertyValue(propertyName);
                    writer.setPropertyValue(propertyName, value);
                }
            }
            if(cancellation && directiveType.studentStatus() != null) {
                Object status = reader.getPropertyValue("status");
                writer.setPropertyValue("status", status);
            }
        }
    }
}
