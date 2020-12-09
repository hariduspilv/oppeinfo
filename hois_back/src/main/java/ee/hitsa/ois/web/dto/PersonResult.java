package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersonResult extends OccupiedAutocompleteResult {

    private String firstname;
    private String lastname;

    public PersonResult(Long id, String firstname, String lastname) {
        super(id, PersonUtil.fullname(firstname, lastname), PersonUtil.fullname(firstname, lastname));
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public PersonResult(Long id, String fullname, String firstname, String lastname) {
        super(id,fullname, fullname);
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public static PersonResult of(Teacher teacher) {
        Person person = teacher.getPerson();
        return new PersonResult(teacher.getId(), person.getFirstname(), person.getLastname());
    }

    public static PersonResult of(Student student) {
        return PersonResult.of(student, false, false, false);
    }

    public static PersonResult of(Student student, boolean addIdcode, boolean addStudentGroup, boolean addStudentType) {
        Person person = student.getPerson();
        String fullname = PersonUtil.fullname(person.getFirstname(), person.getLastname());
        String parentheses = Stream.of(addIdcode ? person.getIdcode() : null,
                addStudentGroup && student.getStudentGroup() != null ? student.getStudentGroup().getCode() : null,
                addStudentType ? PersonUtil.studentTypeShort(EntityUtil.getNullableCode(student.getType())) : null)
                .filter(StringUtils::hasText).collect(Collectors.joining("; "));
        if (StringUtils.hasText(parentheses)) {
            fullname += " (" + parentheses + ")";
        }
        return new PersonResult(student.getId(), fullname, person.getFirstname(), person.getLastname());
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public static final Comparator<PersonResult> SORT = Comparator.comparing(PersonResult::getLastname, String.CASE_INSENSITIVE_ORDER)
            .thenComparing(PersonResult::getFirstname, String.CASE_INSENSITIVE_ORDER);
}
