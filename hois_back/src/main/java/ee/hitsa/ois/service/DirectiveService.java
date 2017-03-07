package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveCoordinator;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.repository.DirectiveCoordinatorRepository;
import ee.hitsa.ois.repository.DirectiveRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm;
import ee.hitsa.ois.web.commandobject.directive.DirectiveSearchCommand;
import ee.hitsa.ois.web.commandobject.directive.DirectiveForm.DirectiveFormStudent;
import ee.hitsa.ois.web.dto.directive.DirectiveCoordinatorDto;
import ee.hitsa.ois.web.dto.directive.DirectiveSearchDto;
import ee.hitsa.ois.web.dto.directive.DirectiveStudentDto;

@Transactional
@Service
public class DirectiveService {
    private static final String DIRECTIVE_LIST_SELECT =
            "d.id, d.headline, d.directive_nr, d.type_code, d.status_code, d.inserted, d.confirm_date";
    private static final String DIRECTIVE_LIST_FROM =
            "from directive d inner join classifier type on d.type_code=type.code inner join classifier status on d.status_code=status.code";

    @Autowired
    private EntityManager em;
    @Autowired
    private DirectiveRepository directiveRepository;
    @Autowired
    private DirectiveCoordinatorRepository directiveCoordinatorRepository;
    @Autowired
    private PersonRepository personRepository;

    public Page<DirectiveSearchDto> search(Long schoolId, DirectiveSearchCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(DIRECTIVE_LIST_FROM, pageable);

        qb.requiredCriteria("d.school_id = :schoolId", "schoolId", schoolId);

        qb.optionalCriteria("d.type_code in (:type)", "type", criteria.getType());
        qb.optionalContains("d.headline", "headline", criteria.getHeadline());
        qb.optionalContains("d.directive_nr", "directiveNr", criteria.getDirectiveNr());
        qb.optionalCriteria("d.confirm_date >= :confirmDateFrom", "confirmDateFrom", criteria.getConfirmDateFrom());
        qb.optionalCriteria("d.confirm_date <= :confirmDateThru", "confirmDateThru", criteria.getConfirmDateThru());
        qb.optionalCriteria("d.status_code in (:status)", "status", criteria.getStatus());
        if(criteria.getInsertedFrom() != null) {
            qb.requiredCriteria("d.inserted >= :insertedFrom", "insertedFrom", LocalDateTime.of(criteria.getInsertedFrom(), LocalTime.MIN));
        }
        if(criteria.getInsertedThru() != null) {
            qb.requiredCriteria("d.inserted <= :insertedThru", "insertedThru", LocalDateTime.of(criteria.getInsertedThru(), LocalTime.MAX));
        }
        if(StringUtils.hasText(criteria.getStudentGroup())) {
            qb.requiredCriteria("d.id in (select ds.directive_id from directive_student ds inner join student_group sg on ds.student_group_id=sg.id where upper(sg.code) like :studentGroup)", "studentGroup", "%"+criteria.getStudentGroup().toUpperCase()+"%");
        }

        return JpaQueryUtil.pagingResult(qb.select(DIRECTIVE_LIST_SELECT, em), pageable, () -> qb.count(em)).map(r -> {
            DirectiveSearchDto dto = new DirectiveSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setHeadline(resultAsString(r, 1));
            dto.setDirectiveNr(resultAsString(r, 2));
            dto.setType(resultAsString(r, 3));
            dto.setStatus(resultAsString(r, 4));
            dto.setCreated(resultAsLocalDate(r, 5));
            dto.setConfirmDate(resultAsLocalDate(r, 6));
            return dto;
        });
    }

    public Directive save(Directive directive, DirectiveForm form) {
        // TODO directive state 'KOOSTAMISEL'
        EntityUtil.bindToEntity(form, directive, "students");
        if(form.getStudents() != null) {
            List<DirectiveStudent> students = directive.getStudents();
            Map<Long, DirectiveStudent> studentMapping = students.stream().collect(Collectors.toMap(DirectiveStudent::getId, ds -> ds));
            for(DirectiveFormStudent formStudent : form.getStudents()) {
                DirectiveStudent student = studentMapping.remove(formStudent.getId());
                if(student == null) {
                    student = new DirectiveStudent();
                    student.setDirective(directive);
                    students.add(student);
                }
                EntityUtil.bindToEntity(formStudent, student);

                String idcode = formStudent.getIdcode();
                if(StringUtils.hasText(idcode) && DirectiveType.KASKKIRI_IMMAT.name().equals(EntityUtil.getCode(directive.getType()))) {
                    // add new person if person idcode is not known
                    Person person = personRepository.findByIdcode(idcode);
                    // FIXME should update existing person?
                    if(person == null) {
                        person = new Person();
                        person.setIdcode(idcode);
                        person.setFirstname(formStudent.getFirstname());
                        person.setLastname(formStudent.getLastname());
                        person = personRepository.save(person);
                    }
                    student.setPerson(person);
                }
            }
            // remove possible existing directive students not included in update command
            students.removeAll(studentMapping.values());
        }
        return directiveRepository.save(directive);
    }

    public void delete(Directive directive) {
        // TODO directive state 'KOOSTAMISEL'
        EntityUtil.deleteEntity(directiveRepository, directive);
    }

    public List<DirectiveStudentDto> searchStudents(Long schoolId) {
        // TODO
        return null;
    }

    public Page<DirectiveCoordinatorDto> search(Long schoolId, Pageable pageable) {
        return directiveCoordinatorRepository.findAllBySchool_id(schoolId, pageable);
    }

    public DirectiveCoordinator save(DirectiveCoordinator coordinator) {
        return directiveCoordinatorRepository.save(coordinator);
    }

    public void delete(DirectiveCoordinator coordinator) {
        directiveCoordinatorRepository.delete(coordinator);
    }
}
