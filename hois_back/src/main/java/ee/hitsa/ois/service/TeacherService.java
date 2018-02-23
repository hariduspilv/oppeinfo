package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.parameterAsTimestamp;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.school.SchoolDepartment;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.teacher.TeacherContinuingEducation;
import ee.hitsa.ois.domain.teacher.TeacherMobility;
import ee.hitsa.ois.domain.teacher.TeacherPositionEhis;
import ee.hitsa.ois.domain.teacher.TeacherQualification;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.TeacherOccupationRepository;
import ee.hitsa.ois.service.ehis.EhisTeacherExportService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TeacherUserRights;
import ee.hitsa.ois.validation.EstonianIdCodeValidator;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.teacher.TeacherContinuingEducationForm;
import ee.hitsa.ois.web.commandobject.teacher.TeacherForm;
import ee.hitsa.ois.web.commandobject.teacher.TeacherMobilityForm;
import ee.hitsa.ois.web.commandobject.teacher.TeacherQualificationForm;
import ee.hitsa.ois.web.commandobject.teacher.TeacherSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.TeacherAbsenceDto;
import ee.hitsa.ois.web.dto.TeacherDto;
import ee.hitsa.ois.web.dto.TeacherSearchDto;

@Transactional
@Service
public class TeacherService {

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EhisTeacherExportService ehisTeacherExportService;
    @Autowired
    private EntityManager em;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private TeacherOccupationRepository teacherOccupationRepository;
    @Autowired
    private UserService userService;

    /**
     * Create new teacher
     *
     * @param user
     * @param teacherForm
     * @return
     */
    public TeacherDto create(HoisUserDetails user, TeacherForm teacherForm) {
        Teacher teacher = new Teacher();
        teacher.setSchool(em.getReference(School.class, user.getSchoolId()));
        Person person;
        TeacherForm.TeacherPersonForm personForm = teacherForm.getPerson();
        if (StringUtils.hasText(personForm.getIdcode())) {
            person = personRepository.findByIdcode(personForm.getIdcode());
        } else {
            person = EntityUtil.getOptionalOne(Person.class, personForm.getId(), em);
            if(person == null && personForm.getBirthdate() != null) {
                // XXX similar code in DirectiveService.findForeignPerson
                List<Person> data = em.createQuery("select p from Person p where p.idcode is null and upper(p.firstname) = ?1 and upper(p.lastname) = ?2 and p.birthdate = ?3 and p.sex.code = ?4", Person.class)
                    .setParameter(1, personForm.getFirstname().toUpperCase())
                    .setParameter(2, personForm.getLastname().toUpperCase())
                    .setParameter(3, personForm.getBirthdate())
                    .setParameter(4, personForm.getSex())
                    .setMaxResults(1)
                    .getResultList();
                if(!data.isEmpty()) {
                    person = data.get(0);
                }
            }
        }
        if (person == null) {
            person = EntityUtil.bindToEntity(personForm, new Person(), classifierRepository);
            String idcode = person.getIdcode();
            if(StringUtils.hasText(idcode)) {
                person.setBirthdate(EstonianIdCodeValidator.birthdateFromIdcode(idcode));
                person.setSex(em.getReference(Classifier.class, EstonianIdCodeValidator.sexFromIdcode(idcode)));
            }
            em.persist(person);
        }
        teacher.setPerson(person);
        return save(user, teacher, teacherForm);
    }

    /**
     * Update teacher
     *
     * @param user
     * @param teacher
     * @param teacherForm
     * @return
     */
    public TeacherDto save(HoisUserDetails user, Teacher teacher, TeacherForm teacherForm) {
        return get(user, saveInternal(user, teacher, teacherForm));
    }

    /**
     * Update teacher, user is teacher. Only personal contact data can updated
     *
     * @param user
     * @param teacher
     * @param teacherForm
     * @return
     */
    public TeacherDto saveAsTeacher(HoisUserDetails user, Teacher teacher, TeacherForm teacherForm) {
        Person person = teacher.getPerson();
        TeacherForm.TeacherPersonForm personForm = teacherForm.getPerson();
        // teacher can change only his/her contact data
        person.setEmail(personForm.getEmail());
        person.setPhone(personForm.getPhone());
        EntityUtil.save(person, em);
        return get(user, teacher);
    }

    /**
     * Send teacher data to EHIS
     *
     * @param user
     * @param teacher
     * @param teacherForm
     * @return
     */
    public TeacherDto sendToEhis(HoisUserDetails user, Teacher teacher, TeacherForm teacherForm) {
        teacher = saveInternal(user, teacher, teacherForm);
        ehisTeacherExportService.exportToEhis(teacher);
        return get(user, teacher);
    }

    /**
     * Search teachers
     *
     * @param user
     * @param criteria
     * @param pageable
     * @return
     */
    public Page<TeacherSearchDto> search(HoisUserDetails user, TeacherSearchCommand criteria, Pageable pageable) {
        if(!user.isExternalExpert()) {
            criteria.setSchool(user.getSchoolId());
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from teacher t join person p on t.person_id = p.id join school s on t.school_id = s.id "+
                "join teacher_occupation tot on t.teacher_occupation_id = tot.id").sort(pageable);
        qb.optionalCriteria("t.school_id = :schoolId", "schoolId", criteria.getSchool());
        qb.optionalCriteria("p.idcode = :idcode", "idcode", criteria.getIdcode());
        qb.optionalCriteria("t.is_higher = :isHigher", "isHigher", criteria.getIsHigher());
        if(user.isExternalExpert() || Boolean.TRUE.equals(criteria.getIsActive())) {
            qb.filter("t.is_active = true");
        }
        LocalDate now = LocalDate.now();
        if(criteria.getSchoolDepartment() != null) {
            qb.requiredCriteria("t.id in (select tpe.teacher_id from teacher_position_ehis tpe where tpe.is_contract_ended = false and (tpe.contract_end is null or tpe.contract_end >= :now) and tpe.school_department_id = :schoolDepartment)", "schoolDepartment", criteria.getSchoolDepartment());
            qb.parameter("now", now);
        }
        qb.optionalCriteria("t.teacher_occupation_id = :teacherOccupation", "teacherOccupation", criteria.getTeacherOccupation());
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getName());

        Boolean canEdit = Boolean.valueOf(TeacherUserRights.hasPermissionToEdit(user));
        Page<TeacherSearchDto> result = JpaQueryUtil.pagingResult(qb, "t.id, s.id as school_id, s.name_et, s.name_en, p.firstname, p.lastname, p.idcode, "+
                "t.email, t.phone, t.is_active, t.teacher_occupation_id, tot.occupation_et, tot.occupation_en", em, pageable).map(r -> {
            TeacherSearchDto dto = new TeacherSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setSchool(new AutocompleteResult(resultAsLong(r, 1), resultAsString(r, 2), resultAsString(r, 3)));
            dto.setName(PersonUtil.fullname(resultAsString(r, 4), resultAsString(r, 5)));
            dto.setIdcode(resultAsString(r, 6));
            dto.setEmail(resultAsString(r, 7));
            dto.setPhone(resultAsString(r, 8));
            dto.setIsActive(resultAsBoolean(r, 9));
            dto.setTeacherOccupation(new AutocompleteResult(resultAsLong(r, 10), resultAsString(r, 11), resultAsString(r, 12)));
            dto.setCanEdit(canEdit);
            return dto;
        });

        List<TeacherSearchDto> teachers = result.getContent();
        if(!teachers.isEmpty()) {
            // set school departments
            List<?> data = em.createNativeQuery("select distinct tpe.teacher_id, sd.id, sd.name_et, sd.name_en from teacher_position_ehis tpe "+
                    "join school_department sd on tpe.school_department_id = sd.id " +
                    "where tpe.teacher_id in (?1) and tpe.is_contract_ended = false and (tpe.contract_end is null or tpe.contract_end >= ?2)")
                .setParameter(1, StreamUtil.toMappedList(TeacherSearchDto::getId, teachers))
                .setParameter(2, parameterAsTimestamp(now))
                .getResultList();
            Map<Long, List<AutocompleteResult>> schoolDepartments = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
                return new AutocompleteResult(resultAsLong(r, 1), resultAsString(r, 2), resultAsString(r, 3));
            }, Collectors.toList())));

            for(TeacherSearchDto dto : teachers) {
                dto.setSchoolDepartments(schoolDepartments.get(dto.getId()));
            }
        }
        return result;
    }

    /**
     * Delete teacher
     *
     * @param user
     * @param teacher
     */
    public void delete(HoisUserDetails user, Teacher teacher) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(teacher, em);
    }

    public TeacherDto saveMobilities(HoisUserDetails user, Teacher teacher, Set<TeacherMobilityForm> mobilityForms) {
        bindTeacherMobilityForm(teacher, mobilityForms);
        return get(user, EntityUtil.save(teacher, em));
    }

    public TeacherDto saveContinuingEducations(HoisUserDetails user, Teacher teacher, List<TeacherContinuingEducationForm> teacherContinuingEducationForms) {
        List<TeacherContinuingEducation> teacherContinuingEducations = teacher.getTeacherContinuingEducation();
        if (Boolean.TRUE.equals(teacher.getIsVocational())) {
            EntityUtil.bindEntityCollection(teacherContinuingEducations, TeacherContinuingEducation::getId, teacherContinuingEducationForms, TeacherContinuingEducationForm::getId,
                    teacherContinuingEducationForm -> createTeacherContinuingEducation(teacher, teacherContinuingEducationForm, new TeacherContinuingEducation()),
                    (teacherContinuingEducationForm, teacherContinuingEducation) -> createTeacherContinuingEducation(teacher, teacherContinuingEducationForm, teacherContinuingEducation));
        } else if(!teacherContinuingEducations.isEmpty()) {
            teacherContinuingEducations.clear();
        }
        return get(user, EntityUtil.save(teacher, em));
    }

    public void delete(HoisUserDetails user, TeacherContinuingEducation continuingEducation) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(continuingEducation, em);
    }

    public TeacherDto saveQualifications(HoisUserDetails user, Teacher teacher, Set<TeacherQualificationForm> teacherQualificationFroms) {
        Set<TeacherQualification> teacherQualifications = teacher.getTeacherQualification();
        EntityUtil.bindEntityCollection(teacherQualifications, TeacherQualification::getId, teacherQualificationFroms, TeacherQualificationForm::getId,
                 teacherQualificationFrom -> createTeacherQualification(teacher, teacherQualificationFrom, new TeacherQualification()),
                 (teacherQualificationFrom, teacherQualification) -> createTeacherQualification(teacher, teacherQualificationFrom, teacherQualification));

        return get(user, EntityUtil.save(teacher, em));
    }

    public void delete(HoisUserDetails user, TeacherQualification qualification) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(qualification, em);
    }

    public void delete(HoisUserDetails user, TeacherMobility teacherMobility) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(teacherMobility, em);
    }

    public void delete(HoisUserDetails user, TeacherPositionEhis teacherPositionEhis) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(teacherPositionEhis, em);
    }

    private Teacher saveInternal(HoisUserDetails user, Teacher teacher, TeacherForm teacherForm) {
        if (!Boolean.TRUE.equals(teacherForm.getIsHigher()) && !Boolean.TRUE.equals(teacherForm.getIsVocational())) {
            throw new ValidationFailedException("teacher-vocational-higher");
        }

        updatePerson(teacherForm.getPerson(), teacher.getPerson());

        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.bindToEntity(teacherForm, teacher, classifierRepository, "person", "teacherPositionEhis", "teacherMobility", "teacherQualification", "teacherContinuingEducation");
        teacher.setTeacherOccupation(teacherOccupationRepository.getOneByIdAndSchool_Id(teacherForm.getTeacherOccupation().getId(), user.getSchoolId()));
        bindTeacherPositionEhisForm(teacher, teacherForm);
        if (!Boolean.TRUE.equals(teacher.getIsHigher())) {
            // remove possible leftovers of higher teacher
            bindTeacherMobilityForm(teacher, Collections.emptySet());
        }

        teacher = EntityUtil.save(teacher, em);
        if(Boolean.TRUE.equals(teacher.getIsActive())) {
            userService.enableUser(teacher, LocalDate.now());
        } else {
            userService.disableUser(teacher, LocalDate.now());
        }
        return teacher;
    }

    private void updatePerson(TeacherForm.TeacherPersonForm personForm, Person person) {
        person.setFirstname(personForm.getFirstname());
        person.setLastname(personForm.getLastname());
        person.setEmail(personForm.getEmail());
        person.setPhone(personForm.getPhone());
        person.setNativeLanguage(personForm.getNativeLanguage());
        Classifier citizenship = em.getReference(Classifier.class, personForm.getCitizenship());
        if (!ClassifierUtil.mainClassCodeEquals(MainClassCode.RIIK, citizenship)) {
            throw new ValidationFailedException("person.citizenship", "null");
        }
        person.setCitizenship(citizenship);
        if(!StringUtils.hasText(person.getIdcode())) {
            // modify sex and birthdate only if person does not have idcode 
            person.setSex(EntityUtil.validateClassifier(em.getReference(Classifier.class, personForm.getSex()), MainClassCode.SUGU));
            if (personForm.getBirthdate() != null) {
                if (!LocalDate.now().isAfter(personForm.getBirthdate())) {
                    throw new ValidationFailedException("person.birthdate", "future");
                }
                person.setBirthdate(personForm.getBirthdate());
            } else {
                throw new ValidationFailedException("person.birthdate", "null");
            }
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
        EntityUtil.bindToEntity(mobilityForm, teacherMobility, classifierRepository);
        teacherMobility.setTeacher(teacher);
        return teacherMobility;
    }

    private TeacherContinuingEducation createTeacherContinuingEducation(Teacher teacher, TeacherContinuingEducationForm teacherContinuingEducationForm, TeacherContinuingEducation teacherContinuingEducation) {
        EntityUtil.bindToEntity(teacherContinuingEducationForm, teacherContinuingEducation, classifierRepository);
        teacherContinuingEducation.setTeacher(teacher);
        
        return teacherContinuingEducation;
    }

    private TeacherQualification createTeacherQualification(Teacher teacher, TeacherQualificationForm teacherQualificationForm, TeacherQualification teacherQualification) {
        EntityUtil.bindToEntity(teacherQualificationForm, teacherQualification, classifierRepository);
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
        EntityUtil.bindToEntity(positionEhis, oldPositionEhis, classifierRepository);
        oldPositionEhis.setTeacher(teacher);
        SchoolDepartment schoolDepartment = null;
        if (positionEhis.getSchoolDepartment() != null && positionEhis.getSchoolDepartment().longValue() > 0) {
            schoolDepartment = em.getReference(SchoolDepartment.class, positionEhis.getSchoolDepartment());
        }
        oldPositionEhis.setSchoolDepartment(schoolDepartment);
        return oldPositionEhis;
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

    public Page<TeacherAbsenceDto> teacherAbsences(Teacher teacher, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from teacher_absence ta").sort(pageable);
        qb.requiredCriteria("ta.teacher_id = :teacherId", "teacherId", EntityUtil.getId(teacher));

        return JpaQueryUtil.pagingResult(qb, "ta.start_date, ta.end_date, ta.reason, ta.changed", em, pageable).map(r -> {
            TeacherAbsenceDto dto = new TeacherAbsenceDto();
            dto.setStartDate(resultAsLocalDate(r, 0));
            dto.setEndDate(resultAsLocalDate(r, 1));
            dto.setReason(resultAsString(r, 2));
            dto.setChanged(resultAsLocalDateTime(r, 3));
            return dto;
        });
    }

    /**
     * Get teacher record
     *
     * @param user
     * @param teacher
     * @return
     */
    public TeacherDto get(HoisUserDetails user, Teacher teacher) {
        TeacherDto dto = TeacherDto.of(teacher);
        dto.setCanEdit(Boolean.valueOf(TeacherUserRights.canEdit(user, teacher) || TeacherUserRights.canEditAsTeacher(user, teacher)));
        return dto;
    }
}
