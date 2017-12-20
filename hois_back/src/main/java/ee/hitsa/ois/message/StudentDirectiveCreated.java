package ee.hitsa.ois.message;

import java.time.LocalDate;

import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.util.DateUtils;

public class StudentDirectiveCreated extends StudentMessage {

    private final String directiveNr;
    private final String directiveType;
    private final String directiveHeadline;
    private final LocalDate confirmDate;

    public StudentDirectiveCreated() {
        directiveNr = null;
        directiveType = null;
        directiveHeadline = null;
        confirmDate = null;
    }

    public StudentDirectiveCreated(DirectiveStudent directiveStudent) {
        super(directiveStudent.getStudent());

        Directive directive = directiveStudent.getDirective();
        directiveNr = directive.getDirectiveNr();
        directiveType = directive.getType().getNameEt();
        directiveHeadline = directive.getHeadline();
        confirmDate = directive.getConfirmDate();
    }

    public String getDirectiveNr() {
        return directiveNr;
    }

    public String getDirectiveType() {
        return directiveType;
    }

    public String getDirectiveHeadline() {
        return directiveHeadline;
    }

    public String getConfirmDate() {
        return DateUtils.date(confirmDate);
    }

    public String getKaskkirjaNr() {
        return directiveNr;
    }

    public String getKaskkirjaLiik() {
        return directiveType;
    }

    public String getKaskkirjaPealkiri() {
        return directiveHeadline;
    }

    public String getKaskkirjaKuupaev() {
        return DateUtils.date(confirmDate);
    }
}
