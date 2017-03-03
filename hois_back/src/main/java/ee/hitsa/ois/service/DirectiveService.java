package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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
import ee.hitsa.ois.web.commandobject.DirectiveForm;
import ee.hitsa.ois.web.commandobject.DirectiveForm.DirectiveFormStudent;
import ee.hitsa.ois.web.commandobject.DirectiveSearchCommand;
import ee.hitsa.ois.web.dto.DirectiveCoordinatorDto;
import ee.hitsa.ois.web.dto.DirectiveSearchDto;

@Transactional
@Service
public class DirectiveService {

    @Autowired
    private DirectiveRepository directiveRepository;
    @Autowired
    private DirectiveCoordinatorRepository directiveCoordinatorRepository;
    @Autowired
    private PersonRepository personRepository;

    public Page<DirectiveSearchDto> search(Long schoolId, DirectiveSearchCommand criteria, Pageable pageable) {
        return directiveRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), schoolId));

            if(!CollectionUtils.isEmpty(criteria.getType())) {
                filters.add(root.get("type").get("code").in(criteria.getType()));
            }
            propertyContains(() -> root.get("headline"), cb, criteria.getHeadline(), filters::add);
            propertyContains(() -> root.get("directiveNr"), cb, criteria.getDirectiveNr(), filters::add);
            if(criteria.getConfirmDateFrom() != null) {
                filters.add(cb.greaterThanOrEqualTo(root.get("confirmDate"), criteria.getConfirmDateFrom()));
            }
            if(criteria.getConfirmDateThru() != null) {
                filters.add(cb.lessThanOrEqualTo(root.get("confirmDate"), criteria.getConfirmDateThru()));
            }
            if(!CollectionUtils.isEmpty(criteria.getStatus())) {
                filters.add(root.get("status").get("code").in(criteria.getStatus()));
            }
            if(criteria.getInsertedFrom() != null) {
                filters.add(cb.greaterThanOrEqualTo(root.get("inserted"), LocalDateTime.of(criteria.getInsertedFrom(), LocalTime.MIN)));
            }
            if(criteria.getInsertedThru() != null) {
                filters.add(cb.lessThanOrEqualTo(root.get("inserted"), LocalDateTime.of(criteria.getInsertedThru(), LocalTime.MAX)));
            }
            if(StringUtils.hasText(criteria.getStudentGroup())) {
                Subquery<Long> studentGroupQuery = query.subquery(Long.class);
                Root<DirectiveStudent> studentGroupRoot = studentGroupQuery.from(DirectiveStudent.class);
                Join<Object, Object> studentGroup = studentGroupRoot.join("studentGroup", JoinType.INNER);
                studentGroupQuery = studentGroupQuery.select(studentGroupRoot.get("directive").get("id"));
                propertyContains(() -> studentGroup.get("code"), cb, criteria.getStudentGroup(), studentGroupQuery::where);
                filters.add(root.get("id").in(studentGroupQuery));
            }

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(DirectiveSearchDto::of);
    }

    public Directive save(Directive directive, DirectiveForm form) {
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
                if(StringUtils.hasText(idcode) && DirectiveType.IMMAT.name().equals(EntityUtil.getCode(directive.getType()))) {
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
