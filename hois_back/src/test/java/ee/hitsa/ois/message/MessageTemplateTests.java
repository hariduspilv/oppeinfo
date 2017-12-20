package ee.hitsa.ois.message;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.domain.student.StudentRepresentative;
import ee.hitsa.ois.domain.student.StudentRepresentativeApplication;
import ee.hitsa.ois.service.MessageTemplateService.HoisReflectivePropertyAccessor;

public class MessageTemplateTests {

    private static ExpressionParser spelParser;

    @Test
    public void academicLeaveEnding() {
        DirectiveStudent ds = new DirectiveStudent();
        ds.setEndDate(LocalDate.now());
        AcademicLeaveEnding m = new AcademicLeaveEnding(ds);
        mergeTemplate("Teie akadeemiline puhkus lõpeb  #{ak_puhkuse_loppemise_kuupaev}", m);
    }

    @Test
    public void confirmationNeededMessage() {
        Application app = new Application();
        app.setStudent(student());
        Classifier type = new Classifier();
        type.setNameEt("Application type in estonian");
        app.setType(type);
        ConfirmationNeededMessage m = new ConfirmationNeededMessage(app);
        mergeTemplate("Teile on saabunud avaldus #{avalduse_liik}, mis vajab õppuri (#{oppuri_nimi} #{oppuri_isikukood}) esindaja kinnitust.", m);
    }

    @Test
    public void practiceJournalUniqueUrlMessage() {
        PracticeJournalUniqueUrlMessage m = new PracticeJournalUniqueUrlMessage();
        m.setUrl("unique url");
        mergeTemplate("Praktika päevikule ligipääsemiseks unikaalne url sisu #{url}", m);
    }

    @Test
    public void studentAbsenceCreated() {
        StudentAbsence a = new StudentAbsence();
        a.setStudent(student());
        StudentAbsenceCreated m = new StudentAbsenceCreated(a);
        mergeTemplate("Teile on saabunud tõend õppuri puudumise kohta. Palun vaadake ÕIS-is saabunud teateid.\n" +
                "Õppur: #{oppuri_nimi}\n" +
                "Isikukood: #{oppuri_isikukood}", m);
    }

    @Test
    public void studentApplicationRejectedMessage() {
        Application app = new Application();
        app.setStudent(student());
        Classifier type = new Classifier();
        type.setNameEt("Application type in estonian");
        app.setType(type);
        app.setRejectReason("Reject reason");
        StudentApplicationRejectedMessage m = new StudentApplicationRejectedMessage(app);
        mergeTemplate("Õppuri ( #{oppuri_nimi} #{oppuri_isikukood} ) avaldus on tagasi lükatud järgmisel põhjusel: #{pohjendus}", m);
    }

    @Test
    public void studentDirectiveCreated() {
        DirectiveStudent ds = new DirectiveStudent();
        ds.setStudent(student());
        Directive directive = new Directive();
        directive.setDirectiveNr("12345");
        Classifier type = new Classifier();
        type.setNameEt("Directive type in estonian");
        directive.setType(type);
        directive.setHeadline("Directive headline");
        directive.setConfirmDate(LocalDate.now());
        ds.setDirective(directive);
        StudentDirectiveCreated m = new StudentDirectiveCreated(ds);
        mergeTemplate("Teile on koostatud käskkiri:\n" +
                "Käskkirja nr: #{kaskkirja_nr}\n" +
                "Käskkirja liik: #{kaskkirja_liik}\n" +
                "Käskkirja pealkiri: #{kaskkirja_pealkiri} \n" +
                "Kinnitamise kuupäev: #{kaskkirja_kuupaev}", m);
    }

    @Test
    public void studentRepresentativeApplicationAccepted() {
        StudentRepresentative r = new StudentRepresentative();
        r.setStudent(student());
        StudentRepresentativeApplicationAccepted m = new StudentRepresentativeApplicationAccepted(r);
        mergeTemplate("Olete ÕIS-is lisatud õppuri #{oppuri_nimi}, #{oppuri_isikukood} esindajaks.", m);
    }

    @Test
    public void studentRepresentativeApplicationCreated() {
        StudentRepresentativeApplicationCreated m = new StudentRepresentativeApplicationCreated(student());
        mergeTemplate("Teile on saabunud taotlus õppuri andmete nägemiseks. Palun vaadake ÕIS-is saabunud avaldustes.\n" +
                "Õppur: #{oppuri_nimi}\n" +
                "Õppuri isikukood: #{oppuri_isikukood}", m);
    }

    @Test
    public void studentRepresentativeApplicationRejectedMessage() {
        StudentRepresentativeApplication app = new StudentRepresentativeApplication();
        app.setStudent(student());
        app.setRejectReason("Reject reason");
        StudentRepresentativeApplicationRejectedMessage m = new StudentRepresentativeApplicationRejectedMessage(app);
        mergeTemplate("Teie avaldus õppuri ( #{oppuri_nimi} #{oppuri_isikukood} ) andmete nägemiseks on tagasi lükatud järgmisel põhjusel: #{pohjendus}", m);
    }

    private static Student student() {
        Student student = new Student();
        Person person = new Person();
        person.setFirstname("Firstname");
        person.setLastname("Lastname");
        person.setIdcode("48403150000");
        student.setPerson(person);
        return student;
    }

    private static String mergeTemplate(String template, Object data) {
        StandardEvaluationContext ctx = new StandardEvaluationContext(data);
        ctx.setPropertyAccessors(Arrays.asList(new HoisReflectivePropertyAccessor()));
        if(spelParser == null) {
            spelParser = new SpelExpressionParser();
        }
        return spelParser.parseExpression(template, new TemplateParserContext()).getValue(ctx, String.class);
    }
}
