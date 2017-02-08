package ee.hitsa.ois.service;

import ee.hitsa.ois.domain.*;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.TeacherOccupationRepository;
import ee.hitsa.ois.repository.TeacherRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.TeacherForm;
import ee.hitsa.ois.web.commandobject.TeacherSearchCommand;
import ee.hitsa.ois.web.dto.TeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

@Transactional
@Service
public class TeacherService {

    @Autowired
    private ClassifierRepository classifierRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TeacherOccupationRepository teacherOccupationRepository;

    public TeacherDto save(HoisUserDetails user, Teacher teacher, TeacherForm teacherForm) {
        if (!teacherForm.getIsHigher() && !teacherForm.getIsVocational()) {
            throw new ValidationFailedException(null, "teacher-vocational-higher");
        }
        EntityUtil.bindToEntity(teacherForm, teacher, classifierRepository, "person", "teacherPositionEhis");
        teacher.setSchool(user.getSchool());
        teacher.setTeacherOccupation(teacherOccupationRepository.getOneByIdAndSchool_Id(teacherForm.getTeacherOccupation(), user.getSchool().getId()));
        // TODO: this logic is wrong?
        Person person = null;
        if (teacherForm.getPerson().getIdcode() != null) {
            person = personRepository.findByIdcode(teacherForm.getPerson().getIdcode());
        } else if (teacherForm.getPerson().getId() != null) {
            person = personRepository.findOne(teacherForm.getPerson().getId());
        }
        if (person == null) {
            teacher.setPerson(EntityUtil.bindToEntity(teacherForm.getPerson(), new Person(), classifierRepository));
            personRepository.save(teacher.getPerson());
        } else {
            // TODO allow some fields to go over?
            teacher.setPerson(person);
        }
        bindTeacherPositionEhisForm(teacher, teacherForm);
        teacherRepository.save(teacher);
        return TeacherDto.of(teacher);
    }

    private void bindTeacherPositionEhisForm(Teacher teacher, TeacherForm teacherForm) {
        Map<Long, TeacherPositionEhis> teacherPositions = teacher.getTeacherPositionEhis()
                .stream()
                .collect(Collectors.toMap(BaseEntityWithId::getId, v -> v));
        Set<TeacherPositionEhis> result = new HashSet<>();
        for (TeacherForm.TeacherPositionEhisForm positionEhis: teacherForm.getTeacherPositionEhis()) {
            clearConflictingFields(positionEhis);
            Long id = positionEhis.getId();
            if (id == null) {
                TeacherPositionEhis newTeacherPositionEhis = EntityUtil.bindToEntity(positionEhis, new TeacherPositionEhis(), classifierRepository);
                newTeacherPositionEhis.setTeacher(teacher);
                result.add(newTeacherPositionEhis);
            } else {
                TeacherPositionEhis oldTeacherPositionEhis = teacherPositions.get(id);
                if (oldTeacherPositionEhis == null) {
                    throw new ValidationFailedException("TeacherPositionEhis", "dirty-entity");
                } else {
                    EntityUtil.bindToEntity(positionEhis, oldTeacherPositionEhis, classifierRepository, "teacher");
                    result.add(oldTeacherPositionEhis);
                }
            }
        }
        teacher.setTeacherPositionEhis(result);
    }

    private void clearConflictingFields(TeacherForm.TeacherPositionEhisForm positionEhis) {
        if (positionEhis.getIsVocational()) {
            positionEhis.setEmploymentCode(null);
            positionEhis.setEmploymentType(null);
            positionEhis.setEmploymentTypeSpecification(null);
            positionEhis.setPositionSpecificationEn(null);
            positionEhis.setIsTeacher(Boolean.FALSE);
            // TODO STRUKTUURIYKSUS?
        } else {
            positionEhis.setLanguage(null);
            positionEhis.setMeetsQualification(Boolean.FALSE);
            positionEhis.setIsChildCare(Boolean.FALSE);
            positionEhis.setIsClassTeacher(Boolean.FALSE);
        }
    }

    public Page<TeacherDto> search(TeacherSearchCommand criteria, Pageable pageable) {
        return teacherRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            if(!StringUtils.isEmpty(criteria.getIdcode())) {
                filters.add(cb.equal(root.get("person").get("idcode"), criteria.getIdcode()));
            }

            List<Predicate> name = new ArrayList<>();
            propertyContains(() -> root.get("person").get("firstname"), cb, criteria.getName(), name::add);
            propertyContains(() -> root.get("person").get("lastname"), cb, criteria.getName(), name::add);
            if(!name.isEmpty()) {
                filters.add(cb.or(name.toArray(new Predicate[name.size()])));
            }


            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(TeacherDto::of);
    }
}
