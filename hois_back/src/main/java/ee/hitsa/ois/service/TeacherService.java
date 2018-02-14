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
        return save(user, new Teacher(), teacherForm);
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
        return TeacherDto.of(saveInternal(user, teacher, teacherForm));
    }

    public TeacherDto sendToEhis(HoisUserDetails user, Teacher teacher, TeacherForm teacherForm) {
        teacher = saveInternal(user, teacher, teacherForm);
        ehisTeacherExportService.exportToEhis(teacher);
        return TeacherDto.of(teacher);
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
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from teacher t join person p on t.person_id = p.id join school s on t.school_id = s.id "+
                "join teacher_occupation tot on t.teacher_occupation_id = tot.id").sort(pageable);
        qb.optionalCriteria("t.school_id = :schoolId", "schoolId", criteria.getSchool());
        qb.optionalCriteria("p.idcode = :idcode", "idcode", criteria.getIdcode());
        qb.optionalCriteria("t.is_higher = :isHigher", "isHigher", criteria.getIsHigher());
        if(Boolean.TRUE.equals(criteria.getIsActive())) {
            qb.filter("t.is_active = true");
        }
        qb.optionalCriteria("t.id in (select tpe.teacher_id from teacher_position_ehis tpe where tpe.school_department_id = :schoolDepartment)", "schoolDepartment", criteria.getSchoolDepartment());
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
                .setParameter(2, parameterAsTimestamp(LocalDate.now()))
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

    public TeacherDto saveMobilities(Teacher teacher, Set<TeacherMobilityForm> mobilityForms) {
        bindTeacherMobilityForm(teacher, mobilityForms);
        return TeacherDto.of(EntityUtil.save(teacher, em));
    }

    public TeacherDto saveContinuingEducations(Teacher teacher, List<TeacherContinuingEducationForm> teacherContinuingEducationForms) {
        bindTeacherContinuingEducationForm(teacher, teacherContinuingEducationForms);
        return TeacherDto.of(EntityUtil.save(teacher, em));
    }

    public void delete(HoisUserDetails user, TeacherContinuingEducation continuingEducation) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(continuingEducation, em);
    }

    public TeacherDto saveQualifications(Teacher teacher, Set<TeacherQualificationForm> teacherQualificationFroms) {
        bindTeacherQualificationForm(teacher, teacherQualificationFroms);
        return TeacherDto.of(EntityUtil.save(teacher, em));
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

        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.bindToEntity(teacherForm, teacher, classifierRepository, "person", "teacherPositionEhis", "teacherMobility", "teacherQualification", "teacherContinuingEducation");
        teacher.setSchool(em.getReference(School.class, user.getSchoolId()));
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
            EntityUtil.save(teacher.getPerson(), em);
        } else {
            bindOldPerson(teacherForm, person);
            teacher.setPerson(EntityUtil.save(person, em));
        }
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

    private void bindOldPerson(TeacherForm teacherForm, Person person) {
        TeacherForm.TeacherPersonForm personForm = teacherForm.getPerson();
        person.setEmail(personForm.getEmail());
        person.setPhone(personForm.getPhone());
        person.setNativeLanguage(personForm.getNativeLanguage());
        Classifier citizenship = em.getReference(Classifier.class, personForm.getCitizenship());
        if (citizenship == null || !ClassifierUtil.mainClassCodeEquals(MainClassCode.RIIK, citizenship)) {
            throw new ValidationFailedException("person.citizenship", "null");
        }
        person.setCitizenship(citizenship);
        person.setSex(EntityUtil.validateClassifier(em.getReference(Classifier.class, teacherForm.getPerson().getSex()), MainClassCode.SUGU));
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
        EntityUtil.bindToEntity(mobilityForm, teacherMobility, classifierRepository);
        teacherMobility.setTeacher(teacher);
        return teacherMobility;
    }

    private void bindTeacherContinuingEducationForm(Teacher teacher, List<TeacherContinuingEducationForm> continuingEducationForms) {
        List<TeacherContinuingEducation> teacherContinuingEducations = teacher.getTeacherContinuingEducation();
        if (Boolean.TRUE.equals(teacher.getIsVocational())) {
            EntityUtil.bindEntityCollection(teacherContinuingEducations, TeacherContinuingEducation::getId, continuingEducationForms, TeacherContinuingEducationForm::getId,
                    teacherContinuingEducationForm -> createTeacherContinuingEducation(teacher, teacherContinuingEducationForm, new TeacherContinuingEducation()),
                    (teacherContinuingEducationForm, teacherContinuingEducation) -> createTeacherContinuingEducation(teacher, teacherContinuingEducationForm, teacherContinuingEducation));
        } else if(!teacherContinuingEducations.isEmpty()) {
            teacherContinuingEducations.clear();
        }
    }

    private TeacherContinuingEducation createTeacherContinuingEducation(Teacher teacher, TeacherContinuingEducationForm teacherContinuingEducationForm, TeacherContinuingEducation teacherContinuingEducation) {
        EntityUtil.bindToEntity(teacherContinuingEducationForm, teacherContinuingEducation, classifierRepository);
        teacherContinuingEducation.setTeacher(teacher);
        
        return teacherContinuingEducation;
    }

    private void bindTeacherQualificationForm(Teacher teacher, Set<TeacherQualificationForm> qualificationFroms) {
        Set<TeacherQualification> teacherQualifications = teacher.getTeacherQualification();
        EntityUtil.bindEntityCollection(teacherQualifications, TeacherQualification::getId, qualificationFroms, TeacherQualificationForm::getId,
                 teacherQualificationFrom -> createTeacherQualification(teacher, teacherQualificationFrom, new TeacherQualification()),
                 (teacherQualificationFrom, teacherQualification) -> createTeacherQualification(teacher, teacherQualificationFrom, teacherQualification));
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

    public TeacherDto get(HoisUserDetails user, Teacher teacher) {
        TeacherDto dto = TeacherDto.of(teacher);  
        dto.setCanEdit(Boolean.valueOf(TeacherUserRights.canEdit(user, teacher)));
        return dto;
    }
}
