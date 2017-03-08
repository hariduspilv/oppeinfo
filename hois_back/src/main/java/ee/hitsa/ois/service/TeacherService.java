package ee.hitsa.ois.service;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.school.SchoolDepartment;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.teacher.TeacherMobility;
import ee.hitsa.ois.domain.teacher.TeacherPositionEhis;
import ee.hitsa.ois.domain.teacher.TeacherQualification;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.SchoolDepartmentRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.TeacherOccupationRepository;
import ee.hitsa.ois.repository.TeacherRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ee.hitsa.ois.util.SearchUtil.propertyContains;

@Transactional
@Service
public class TeacherService {

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private SchoolDepartmentRepository schoolDepartmentRepository;
    @Autowired
    private TeacherOccupationRepository teacherOccupationRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    public TeacherDto save(HoisUserDetails user, Teacher teacher, TeacherForm teacherForm) {
        if (!Boolean.TRUE.equals(teacherForm.getIsHigher()) && !Boolean.TRUE.equals(teacherForm.getIsVocational())) {
            throw new ValidationFailedException(null, "teacher-vocational-higher");
        }
        String nativeLanguage = teacherForm.getPerson().getNativeLanguage();
        if (Boolean.TRUE.equals(teacherForm.getIsVocational() && (nativeLanguage == null || nativeLanguage.trim().length() == 0))) {
            throw new ValidationFailedException("nativeLanguage", null);
        }
        EntityUtil.bindToEntity(teacherForm, teacher, classifierRepository, "person", "teacherPositionEhis", "teacherMobility", "teacherQualification");
        teacher.setSchool(schoolRepository.getOne(user.getSchoolId()));
        teacher.setTeacherOccupation(teacherOccupationRepository.getOneByIdAndSchool_Id(teacherForm.getTeacherOccupation(), user.getSchoolId()));
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
            bindOldPerson(teacherForm, person);
            personRepository.save(person);
            teacher.setPerson(person);
        }
        bindTeacherPositionEhisForm(teacher, teacherForm);
        bindTeacherQualificationForm(teacher, teacherForm);
        bindTeacherMobilityForm(teacher, teacherForm);
        teacherRepository.save(teacher);
        return TeacherDto.of(teacher);
    }

    private void bindOldPerson(TeacherForm teacherForm, Person person) {
        person.setEmail(teacherForm.getPerson().getEmail());
        person.setPhone(teacherForm.getPerson().getPhone());
        person.setNativeLanguage(teacherForm.getPerson().getNativeLanguage());
        Classifier citizenship = classifierRepository.getOne(teacherForm.getPerson().getCitizenship());
        if (citizenship == null || !MainClassCode.RIIK.name().equals(citizenship.getMainClassCode())) {
            throw new ValidationFailedException("person.citizenship", "null");
        }
        person.setCitizenship(citizenship);
        // TODO: generate from idcode
        if (person.getSex() == null) {
            person.setSex(classifierRepository.findOneByCodeAndMainClassCode(teacherForm.getPerson().getSex(), MainClassCode.SUGU.name()));
        }
        // TODO: generate from idcode
        if (person.getBirthdate() == null) {
            if (teacherForm.getPerson().getBirthdate() != null ) {
                if (LocalDate.now().isAfter(teacherForm.getPerson().getBirthdate())) {
                    person.setBirthdate(teacherForm.getPerson().getBirthdate());
                } else {
                    throw new ValidationFailedException("person.birthdate", "future");
                }
            } else {
                throw new ValidationFailedException("person.birthdate", "null");
            }
        }
    }

    private void bindTeacherMobilityForm(Teacher teacher, TeacherForm teacherForm) {
        Set<TeacherMobility> teacherMobilities = teacher.getTeacherMobility();
        Set<TeacherMobility> result = new HashSet<>();
        if (Boolean.TRUE.equals(teacher.getIsHigher())) {
            Map<Long, TeacherMobility> mobilityMap = teacherMobilities
                    .stream().collect(Collectors.toMap(BaseEntityWithId::getId, v -> v));
            for (TeacherForm.TeacherMobilityForm mobilityForm : teacherForm.getTeacherMobility()) {
                Long id = mobilityForm.getId();
                TeacherMobility teacherMobility;
                if (id == null) {
                    teacherMobility = new TeacherMobility();
                } else {
                    teacherMobility = mobilityMap.get(id);
                    if (teacherMobility == null) {
                        throw new ValidationFailedException("teacherMobility", "dirty-entity");
                    }
                }
                result.add(createTeacherMobility(teacher, mobilityForm, teacherMobility));
            }
        }
        teacherMobilities.clear();
        teacherMobilities.addAll(result);
    }

    private TeacherMobility createTeacherMobility(Teacher teacher, TeacherForm.TeacherMobilityForm mobilityForm, TeacherMobility teacherMobility) {
        teacherMobility = EntityUtil.bindToEntity(mobilityForm, teacherMobility, classifierRepository);
        if (teacherMobility.getStart() != null && teacherMobility.getEnd() != null && teacherMobility.getEnd().isBefore(teacherMobility.getStart())) {
            throw new ValidationFailedException("end", "early");
        }
        teacherMobility.setTeacher(teacher);
        return teacherMobility;
    }

    private void bindTeacherQualificationForm(Teacher teacher, TeacherForm teacherForm) {
        Set<TeacherQualification> teacherQualifications = teacher.getTeacherQualification();
        Set<TeacherQualification> result = new HashSet<>();
        if (Boolean.TRUE.equals(teacher.getIsHigher())) {
            Map<Long, TeacherQualification> qualifications = teacherQualifications
                    .stream().collect(Collectors.toMap(BaseEntityWithId::getId, v -> v));
            for (TeacherForm.TeacherQualificationFrom teacherQualificationFrom : teacherForm.getTeacherQualifications()) {
                Long id = teacherQualificationFrom.getId();
                TeacherQualification teacherQualification;
                if (id == null) {
                    teacherQualification = new TeacherQualification();
                } else {
                    teacherQualification = qualifications.get(id);
                    if (teacherQualification == null) {
                        throw new ValidationFailedException("teacherQualification", "dirty-entity");
                    }
                }
                result.add(createTeacherQualification(teacher, teacherQualificationFrom, teacherQualification));
            }
        }
        teacherQualifications.clear();
        teacherQualifications.addAll(result);
    }

    private TeacherQualification createTeacherQualification(Teacher teacher, TeacherForm.TeacherQualificationFrom teacherQualificationForm, TeacherQualification teacherQualification) {
        teacherQualification = EntityUtil.bindToEntity(teacherQualificationForm, teacherQualification, classifierRepository);
        teacherQualification.setTeacher(teacher);
        if (ClassifierUtil.isEstonia(teacherQualification.getState())) {
            teacherQualification.setSchoolOther(null);
        }
        return teacherQualification;
    }

    private void bindTeacherPositionEhisForm(Teacher teacher, TeacherForm teacherForm) {
        Set<TeacherPositionEhis> oldTeacherPositions = teacher.getTeacherPositionEhis();
        Map<Long, TeacherPositionEhis> teacherPositions = oldTeacherPositions
                .stream().collect(Collectors.toMap(BaseEntityWithId::getId, v -> v));
        Set<TeacherPositionEhis> result = new HashSet<>();
        for (TeacherForm.TeacherPositionEhisForm positionEhis: teacherForm.getTeacherPositionEhis()) {
            clearConflictingFields(positionEhis);
            Long id = positionEhis.getId();
            if (id == null) {
                result.add(createTeacherPositionEhisForm(teacher, positionEhis, new TeacherPositionEhis()));
            } else {
                TeacherPositionEhis oldTeacherPositionEhis = teacherPositions.get(id);
                if (oldTeacherPositionEhis == null) {
                    throw new ValidationFailedException("TeacherPositionEhis", "dirty-entity");
                }
                result.add(createTeacherPositionEhisForm(teacher, positionEhis, oldTeacherPositionEhis));
            }
        }
        oldTeacherPositions.clear();
        oldTeacherPositions.addAll(result);
    }

    private TeacherPositionEhis createTeacherPositionEhisForm(Teacher teacher, TeacherForm.TeacherPositionEhisForm positionEhis, TeacherPositionEhis oldPositionEhis) {
        TeacherPositionEhis newTeacherPositionEhis = EntityUtil.bindToEntity(positionEhis, oldPositionEhis, classifierRepository);
        newTeacherPositionEhis.setTeacher(teacher);
        SchoolDepartment schoolDepartment = null;
        if (positionEhis.getSchoolDepartment() != null && positionEhis.getSchoolDepartment().longValue() > 0) {
            schoolDepartment = schoolDepartmentRepository.findOne(positionEhis.getSchoolDepartment());
        }
        newTeacherPositionEhis.setSchoolDepartment(schoolDepartment);
        return newTeacherPositionEhis;
    }

    private static void clearConflictingFields(TeacherForm.TeacherPositionEhisForm positionEhis) {
        if (Boolean.TRUE.equals(positionEhis.getIsVocational())) {
            positionEhis.setEmploymentCode(null);
            positionEhis.setEmploymentType(null);
            positionEhis.setEmploymentTypeSpecification(null);
            positionEhis.setPositionSpecificationEn(null);
            positionEhis.setSchoolDepartment(null);
            positionEhis.setIsTeacher(Boolean.FALSE);
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

            if (criteria.getSchool() != null) {
                filters.add(cb.equal(root.get("school").get("id"), criteria.getSchool()));
            }

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

    public void delete(Teacher teacher) {
        EntityUtil.deleteEntity(teacherRepository, teacher);
    }
}
