package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
import ee.hitsa.ois.repository.TeacherMobilityRepository;
import ee.hitsa.ois.repository.TeacherOccupationRepository;
import ee.hitsa.ois.repository.TeacherPositionEhisRepository;
import ee.hitsa.ois.repository.TeacherQualificationRepository;
import ee.hitsa.ois.repository.TeacherRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.teacher.TeacherForm;
import ee.hitsa.ois.web.commandobject.teacher.TeacherMobilityForm;
import ee.hitsa.ois.web.commandobject.teacher.TeacherQualificationFrom;
import ee.hitsa.ois.web.commandobject.teacher.TeacherSearchCommand;
import ee.hitsa.ois.web.dto.TeacherDto;
import ee.hitsa.ois.web.dto.TeacherSearchDto;

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
    private TeacherMobilityRepository teacherMobilityRepository;
    @Autowired
    private TeacherOccupationRepository teacherOccupationRepository;
    @Autowired
    private TeacherPositionEhisRepository teacherPositionEhisRepository;
    @Autowired
    private TeacherQualificationRepository teacherQualificationRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    public TeacherDto create(HoisUserDetails user, TeacherForm teacherForm) {
        return save(user, new Teacher(), teacherForm);
    }

    public TeacherDto save(HoisUserDetails user, Teacher teacher, TeacherForm teacherForm) {
        if (!Boolean.TRUE.equals(teacherForm.getIsHigher()) && !Boolean.TRUE.equals(teacherForm.getIsVocational())) {
            throw new ValidationFailedException("teacher-vocational-higher");
        }
        EntityUtil.bindToEntity(teacherForm, teacher, classifierRepository, "person", "teacherPositionEhis", "teacherMobility", "teacherQualification");
        teacher.setSchool(schoolRepository.getOne(user.getSchoolId()));
        teacher.setTeacherOccupation(teacherOccupationRepository.getOneByIdAndSchool_Id(teacherForm.getTeacherOccupation().getId(), user.getSchoolId()));
        // TODO: this logic is wrong?
        Person person = null;
        if (teacherForm.getPerson().getIdcode() != null) {
            person = personRepository.findByIdcode(teacherForm.getPerson().getIdcode());
        } else if (teacherForm.getPerson().getId() != null) {
            person = personRepository.getOne(teacherForm.getPerson().getId());
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
        if (!Boolean.TRUE.equals(teacher.getIsHigher())) {
            bindTeacherMobilityForm(teacher, Collections.emptySet());
            bindTeacherQualificationForm(teacher, Collections.emptySet());
        }
        teacherRepository.save(teacher);
        return TeacherDto.of(teacher);
    }

    private void bindOldPerson(TeacherForm teacherForm, Person person) {
        TeacherForm.TeacherPersonForm personForm = teacherForm.getPerson();
        person.setEmail(personForm.getEmail());
        person.setPhone(personForm.getPhone());
        person.setNativeLanguage(personForm.getNativeLanguage());
        Classifier citizenship = classifierRepository.getOne(personForm.getCitizenship());
        if (citizenship == null || !MainClassCode.RIIK.name().equals(citizenship.getMainClassCode())) {
            throw new ValidationFailedException("person.citizenship", "null");
        }
        person.setCitizenship(citizenship);
        person.setSex(EntityUtil.validateClassifier(classifierRepository.getOne(teacherForm.getPerson().getSex()), MainClassCode.SUGU));
        // TODO: generate from idcode
        if (personForm.getBirthdate() != null) {
            if (LocalDate.now().isAfter(personForm.getBirthdate())) {
                person.setBirthdate(personForm.getBirthdate());
            } else {
                throw new ValidationFailedException("person.birthdate", "future");
            }
        } else {
            throw new ValidationFailedException("person.birthdate", "null");
        }
    }

    private void bindTeacherMobilityForm(Teacher teacher, Set<TeacherMobilityForm> teacherMobilityForms) {
        Set<TeacherMobility> teacherMobilities = teacher.getTeacherMobility();
        if (Boolean.TRUE.equals(teacher.getIsHigher())) {
            EntityUtil.bindEntityCollection(teacherMobilities, TeacherMobility::getId, teacherMobilityForms, TeacherMobilityForm::getId,
                    mobilityForm -> createTeacherMobility(teacher, mobilityForm, new TeacherMobility()),
                    (mobilityForm, teacherMobility) -> createTeacherMobility(teacher, mobilityForm, teacherMobility));
        } else if(!teacherMobilities.isEmpty()) {
            teacherMobilities.clear();
        }
    }

    private TeacherMobility createTeacherMobility(Teacher teacher, TeacherMobilityForm mobilityForm, TeacherMobility teacherMobility) {
        teacherMobility = EntityUtil.bindToEntity(mobilityForm, teacherMobility, classifierRepository);
        teacherMobility.setTeacher(teacher);
        return teacherMobility;
    }

    private void bindTeacherQualificationForm(Teacher teacher, Set<TeacherQualificationFrom> qualificationFroms) {
        Set<TeacherQualification> teacherQualifications = teacher.getTeacherQualification();
        if (Boolean.TRUE.equals(teacher.getIsHigher())) {
            EntityUtil.bindEntityCollection(teacherQualifications, TeacherQualification::getId, qualificationFroms, TeacherQualificationFrom::getId,
                    teacherQualificationFrom -> createTeacherQualification(teacher, teacherQualificationFrom, new TeacherQualification()),
                    (teacherQualificationFrom, teacherQualification) -> createTeacherQualification(teacher, teacherQualificationFrom, teacherQualification));
        } else if(!teacherQualifications.isEmpty()) {
            teacherQualifications.clear();
        }
    }

    private TeacherQualification createTeacherQualification(Teacher teacher, TeacherQualificationFrom teacherQualificationForm, TeacherQualification teacherQualification) {
        teacherQualification = EntityUtil.bindToEntity(teacherQualificationForm, teacherQualification, classifierRepository);
        teacherQualification.setTeacher(teacher);
        if (ClassifierUtil.isEstonia(teacherQualification.getState())) {
            teacherQualification.setSchoolOther(null);
        }
        return teacherQualification;
    }

    private void bindTeacherPositionEhisForm(Teacher teacher, TeacherForm teacherForm) {
        EntityUtil.bindEntityCollection(teacher.getTeacherPositionEhis(), TeacherPositionEhis::getId, teacherForm.getTeacherPositionEhis(), TeacherForm.TeacherPositionEhisForm::getId, positionEhis -> {
            clearConflictingFields(positionEhis);
            return createTeacherPositionEhisForm(teacher, positionEhis, new TeacherPositionEhis());
        }, (positionEhis, oldTeacherPositionEhis) -> {
            clearConflictingFields(positionEhis);
            createTeacherPositionEhisForm(teacher, positionEhis, oldTeacherPositionEhis);
        });
    }

    private TeacherPositionEhis createTeacherPositionEhisForm(Teacher teacher, TeacherForm.TeacherPositionEhisForm positionEhis, TeacherPositionEhis oldPositionEhis) {
        TeacherPositionEhis newTeacherPositionEhis = EntityUtil.bindToEntity(positionEhis, oldPositionEhis, classifierRepository);
        newTeacherPositionEhis.setTeacher(teacher);
        SchoolDepartment schoolDepartment = null;
        if (positionEhis.getSchoolDepartment() != null && positionEhis.getSchoolDepartment().longValue() > 0) {
            schoolDepartment = schoolDepartmentRepository.getOne(positionEhis.getSchoolDepartment());
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

    public Page<TeacherSearchDto> search(TeacherSearchCommand criteria, Pageable pageable) {
        return teacherRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            if (criteria.getSchool() != null) {
                filters.add(cb.equal(root.get("school").get("id"), criteria.getSchool()));
            }

            if(!StringUtils.isEmpty(criteria.getIdcode())) {
                filters.add(cb.equal(root.get("person").get("idcode"), criteria.getIdcode()));
            }
            
            if(criteria.getIsHigher() != null) {
                filters.add(cb.equal(root.get("isHigher"), criteria.getIsHigher()));
            }
            
            if(Boolean.TRUE.equals(criteria.getIsActive())) {
                filters.add(cb.equal(root.get("isActive"), Boolean.TRUE));
            }
            
            if(criteria.getSchoolDepartment() != null) {
                Subquery<Long> teacherPositionSubquery = query.subquery(Long.class);
                Root<TeacherPositionEhis> teacherPositionEhisRoot = teacherPositionSubquery.from(TeacherPositionEhis.class);
                teacherPositionSubquery = teacherPositionSubquery
                        .select(teacherPositionEhisRoot.get("teacher").get("id"))
                        .where(cb.equal(teacherPositionEhisRoot.get("schoolDepartment").get("id"), criteria.getSchoolDepartment()));
                filters.add(root.get("id").in(teacherPositionSubquery));                
            }
            
            if(criteria.getTeacherOccupation() != null) {
                filters.add(cb.equal(root.get("teacherOccupation").get("id"), criteria.getTeacherOccupation()));
            }

            if(!StringUtils.isEmpty(criteria.getName())) {
                List<Predicate> name = new ArrayList<>();
                propertyContains(() -> root.get("person").get("firstname"), cb, criteria.getName(), name::add);
                propertyContains(() -> root.get("person").get("lastname"), cb, criteria.getName(), name::add);
                name.add(cb.like(cb.concat(cb.upper(root.get("person").get("firstname")), cb.concat(" ", cb.upper(root.get("person").get("lastname")))), JpaQueryUtil.toContains(criteria.getName())));
                if(!name.isEmpty()) {
                    filters.add(cb.or(name.toArray(new Predicate[name.size()])));
                }
            }

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(TeacherSearchDto::of);
    }

    public void delete(Teacher teacher) {
        EntityUtil.deleteEntity(teacherRepository, teacher);
    }

    public TeacherDto saveMobilities(Teacher teacher, Set<TeacherMobilityForm> mobilityForms) {
        bindTeacherMobilityForm(teacher, mobilityForms);
        return TeacherDto.of(teacherRepository.save(teacher));
    }

    public TeacherDto saveQualifications(Teacher teacher, Set<TeacherQualificationFrom> teacherQualificationFroms) {
        bindTeacherQualificationForm(teacher, teacherQualificationFroms);
        return TeacherDto.of(teacherRepository.save(teacher));
    }

    public void delete(TeacherQualification qualification) {
        EntityUtil.deleteEntity(teacherQualificationRepository, qualification);
    }

    public void delete(TeacherMobility teacherMobility) {
        EntityUtil.deleteEntity(teacherMobilityRepository, teacherMobility);
    }

    public void delete(TeacherPositionEhis teacherPositionEhis) {
        EntityUtil.deleteEntity(teacherPositionEhisRepository, teacherPositionEhis);
    }
}
