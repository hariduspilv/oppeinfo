package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.domain.student.StudentCurriculumCompletion;
import ee.hitsa.ois.domain.student.StudentHistory;
import ee.hitsa.ois.domain.student.StudentOccupationCertificate;
import ee.hitsa.ois.domain.student.StudentSpecialNeed;
import ee.hitsa.ois.enums.ApelApplicationStatus;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.enums.CurriculumModuleType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.SupportServiceType;
import ee.hitsa.ois.message.StudentAbsenceCreated;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.CurriculumVersionOccupationModuleRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentAbsenceUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.OisFileCommand;
import ee.hitsa.ois.web.commandobject.student.StudentAbsenceForm;
import ee.hitsa.ois.web.commandobject.student.StudentForm;
import ee.hitsa.ois.web.commandobject.student.StudentSearchCommand;
import ee.hitsa.ois.web.commandobject.student.StudentSpecialitySearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.RoomAutocompleteResult;
import ee.hitsa.ois.web.dto.SpecialityAutocompleteResult;
import ee.hitsa.ois.web.dto.StudentOccupationCertificateDto;
import ee.hitsa.ois.web.dto.StudentSupportServiceDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeDto;
import ee.hitsa.ois.web.dto.student.StudentAbsenceDto;
import ee.hitsa.ois.web.dto.student.StudentAbsenceSearchDto;
import ee.hitsa.ois.web.dto.student.StudentApplicationDto;
import ee.hitsa.ois.web.dto.student.StudentDirectiveDto;
import ee.hitsa.ois.web.dto.student.StudentDormitoryHistoryDto;
import ee.hitsa.ois.web.dto.student.StudentForeignstudyDto;
import ee.hitsa.ois.web.dto.student.StudentPracticeContractDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;
import ee.hitsa.ois.web.dto.student.StudentSpecialitySearchDto;
import ee.hitsa.ois.web.dto.student.StudentViewDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalConnectedEntity;
import ee.hitsa.ois.web.dto.student.StudentVocationalModuleDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalResultByTimeDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalResultDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalResultModuleThemeDto;
import ee.hitsa.ois.web.dto.student.StudentVocationalStudyProgramme;

@Transactional
@Service
public class StudentService {

    private static final String STUDENT_LIST_SELECT = "s.id, person.firstname, person.lastname, person.idcode, "+
            "curriculum_version.id curriculum_version_id, curriculum_version.code curriculum_version_code, curriculum.id curriculum_id, curriculum.name_et, curriculum.name_en, " +
            "student_group.id student_group_id, student_group.code student_group_code, s.study_form_code, s.status_code, s.person_id";
    private static final String STUDENT_LIST_FROM = "from student s inner join person person on s.person_id=person.id "+
            "inner join curriculum_version curriculum_version on s.curriculum_version_id=curriculum_version.id "+
            "inner join curriculum curriculum on curriculum_version.curriculum_id=curriculum.id "+
            "inner join classifier status on s.status_code=status.code "+
            "left outer join student_group student_group on s.student_group_id=student_group.id "+
            "left outer join classifier study_form on s.study_form_code=study_form.code ";
    
    private static final List<String> JOURNAL_RESULT_ENTRY_TYPES = EnumUtil.toNameList(JournalEntryType.SISSEKANNE_L,
            JournalEntryType.SISSEKANNE_O, JournalEntryType.SISSEKANNE_R, JournalEntryType.SISSEKANNE_H);

    @Autowired
    private AutomaticMessageService automaticMessageService;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private CurriculumVersionOccupationModuleRepository curriculumVersionOccupationModuleRepository;
    @Autowired
    private StudentRemarkService studentRemarkService;

    /**
     * Search students
     *
     * @param schoolId
     * @param criteria
     * @param pageable
     * @return
     */
    public Page<StudentSearchDto> search(HoisUserDetails user, StudentSearchCommand criteria, Pageable pageable) {
        StringBuilder select = new StringBuilder(STUDENT_LIST_SELECT);
        StringBuilder from = new StringBuilder(STUDENT_LIST_FROM);
        final boolean isJournalUsed = !CollectionUtils.isEmpty(criteria.getJournalId());
        final boolean isSubjectUsed = !CollectionUtils.isEmpty(criteria.getSubjectId());
        if (isJournalUsed) {
            select.append(", j.id as journal_id, j.name_et as journal_name");
            from.append("left join journal_student js on js.student_id = s.id ");
            from.append("left join journal j on j.id = js.journal_id ");
        }
        if (isSubjectUsed) {
            select.append(", sj.id as subject_id, sj.name_et as subject_name_et, sj.name_en as subject_name_en");
            from.append("left join declaration d on d.student_id = s.id ");
            from.append("left join declaration_subject ds on ds.declaration_id = d.id ");
            from.append("left join subject_study_period ssp on ssp.id = ds.subject_study_period_id ");
            from.append("left join subject sj on sj.id = ssp.subject_id ");
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).sort(pageable);

        if (user.isStudent()) {
            // student can search active students only
            qb.requiredCriteria("s.status_code in (:activeStudents)", "activeStudents", StudentStatus.STUDENT_STATUS_ACTIVE);
        }

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        // student cannot search by idcode
        if(!user.isStudent()) {
            qb.optionalCriteria("person.idcode = :idcode", "idcode", criteria.getIdcode());
        }
        qb.optionalContains(Arrays.asList("person.firstname", "person.lastname", "person.firstname || ' ' || person.lastname"), "name", criteria.getName());

        qb.optionalCriteria("curriculum.id in (:curriculum)", "curriculum", criteria.getCurriculum());
        qb.optionalCriteria("s.curriculum_version_id in (:curriculumVersion)", "curriculumVersion", criteria.getCurriculumVersion());
        qb.optionalContains("student_group.code", "code", criteria.getStudentGroup());
        qb.optionalCriteria("s.student_group_id in (:studentGroup)", "studentGroup", criteria.getStudentGroupId());
        qb.optionalCriteria("s.study_form_code in (:studyForm)", "studyForm", criteria.getStudyForm());
        qb.optionalCriteria("s.status_code in (:status)", "status", criteria.getStatus());
        qb.optionalCriteria("j.id in (:journalIds)", "journalIds", criteria.getJournalId());
        qb.optionalCriteria("sj.id in (:subjectIds)", "subjectIds", criteria.getSubjectId());

        if (Boolean.TRUE.equals(criteria.getHigher())) {
            qb.filter("curriculum.is_higher = true");
        }
        if (Boolean.FALSE.equals(criteria.getHigher())) {
            qb.filter("curriculum.is_higher = false");
        }

        return JpaQueryUtil.pagingResult(qb, select.toString(), em, pageable).map(r -> {
            StudentSearchDto dto = new StudentSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setIdcode(user.isStudent() ? null : resultAsString(r, 3));
            String curriculumVersionCode = resultAsString(r, 5);
            dto.setCurriculumVersion(new AutocompleteResult(resultAsLong(r, 4),
                    CurriculumUtil.versionName(curriculumVersionCode, resultAsString(r, 7)),
                    CurriculumUtil.versionName(curriculumVersionCode, resultAsString(r, 8))));
            dto.setCurriculum(new AutocompleteResult(resultAsLong(r, 6), resultAsString(r, 7), resultAsString(r, 8)));
            dto.setStudentGroup(new AutocompleteResult(resultAsLong(r, 9), resultAsString(r, 10), resultAsString(r, 10)));
            dto.setStudyForm(resultAsString(r, 11));
            dto.setStatus(resultAsString(r, 12));
            dto.setPersonId(user.isStudent() ? null : resultAsLong(r, 13));
            if (isJournalUsed) {
                dto.setJournal(new AutocompleteResult(resultAsLong(r, 14), resultAsString(r, 15), resultAsString(r, 15)));
                if (isSubjectUsed) {
                    dto.setSubject(new AutocompleteResult(resultAsLong(r, 16), resultAsString(r, 17), resultAsString(r, 18)));
                }
            } else if (isSubjectUsed) {
                dto.setSubject(new AutocompleteResult(resultAsLong(r, 14), resultAsString(r, 15), resultAsString(r, 16)));
            }
            return dto;
        });
    }
    

    /**
     * 
     * @param user
     * @param criteria
     * @param pageable
     * @return
     */
    public Page<StudentSpecialitySearchDto> search(HoisUserDetails user, StudentSpecialitySearchCommand criteria,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s " + 
                "join person p on p.id = s.person_id " + 
                "join curriculum_version cv on cv.id = s.curriculum_version_id " + 
                "left join student_group sg on sg.id = s.student_group_id " + 
                "left join curriculum_speciality cs on cs.id = s.curriculum_speciality_id ").sort("fullname");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("s.status_code in (:activeStudents)", "activeStudents", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.optionalCriteria("cv.id = :cvId", "cvId", criteria.getCurriculumVersion());
        if (Boolean.TRUE.equals(criteria.getWoSpeciality())) {
            qb.filter("cs.id is null");
        }
        return JpaQueryUtil.pagingResult(qb, "s.id as student, p.firstname || ' ' || p.lastname as fullname, p.idcode,"
                + " cv.id as cv, cs.id as spec, cs.name_et, cs.name_en,"
                + " sg.id as sg, sg.code",
                em, pageable).map(r -> {
            StudentSpecialitySearchDto dto = new StudentSpecialitySearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setName(resultAsString(r, 1));
            dto.setIdcode(resultAsString(r, 2));
            dto.setGroup(new AutocompleteResult(resultAsLong(r, 7), resultAsString(r, 8), resultAsString(r, 8)));
            dto.setSpeciality(new SpecialityAutocompleteResult(resultAsLong(r, 4), resultAsString(r, 5), resultAsString(r, 6)));
            return dto;
        });
    }

    public Student save(HoisUserDetails user, Student student, StudentForm form) {
        Person p = EntityUtil.bindToEntity(form.getPerson(), student.getPerson(), classifierRepository);
        PersonUtil.conditionalClean(p);
        EntityUtil.save(p, em);
        
        OisFile photo = student.getPhoto();
        if (Boolean.TRUE.equals(form.getDeleteCurrentPhoto())) {
            if (photo != null) {
                removePhotoFromStudentHistory(student, photo);
                student.setPhoto(null);
                em.remove(photo);
            }
        } else if (form.getPhoto() != null) {
            if (photo == null) {
                photo = new OisFile();
            }
            EntityUtil.bindToEntity(form.getPhoto(), photo);
            photo = EntityUtil.save(photo, em);
            student.setPhoto(photo);
        }

        if (!UserUtil.isSchoolAdmin(user, student.getSchool())) {
            return student;
        }

        EntityUtil.bindToEntity(form, student, classifierRepository, "person", "curriculumSpeciality", "specialNeeds", "specialNeed");
        student.setCurriculumSpeciality(form.getCurriculumSpeciality() != null
                ? em.getReference(CurriculumSpeciality.class, form.getCurriculumSpeciality().getId()) : null);
        student.setEmail(form.getSchoolEmail());
        student.setLanguage(form.getStudyLanguage() != null ? 
                classifierRepository.getOne(form.getStudyLanguage())
                : null);
        EntityUtil.bindEntityCollection(student.getSpecialNeeds(), specialNeed -> specialNeed.getSpecialNeed().getCode(),
            form.getSpecialNeeds(), specialNeed -> specialNeed, formSpecialNeed -> {
                StudentSpecialNeed specialNeed = new StudentSpecialNeed();
                specialNeed.setStudent(student);
                specialNeed.setSpecialNeed(em.getReference(Classifier.class, formSpecialNeed));
                return specialNeed;
            });

        LocalDate studyStart = student.getStudyStart();
        LocalDate nominalStudyEnd = student.getNominalStudyEnd();
        if (studyStart != null && nominalStudyEnd != null && nominalStudyEnd.isBefore(studyStart)) {
            throw new ValidationFailedException("student.error.nominalStudyEndIsBeforeStudyStart");
        }
        
        return saveWithHistory(student);
    }
    
    public void removePhotoFromStudentHistory(Student student, OisFile photo) {
        List<?> data = em
                .createNativeQuery("select sh.student_id from student_history sh where sh.student_id = ?1 and sh.ois_file_id = ?2")
                .setParameter(1, EntityUtil.getId(student))
                .setParameter(2, EntityUtil.getId(photo))
                .getResultList();

        if(!data.isEmpty()) {
            List<Long> studentHistoryIds = StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
            em.createNativeQuery("update student_history set ois_file_id = null where student_id in (?1)")
                .setParameter(1, studentHistoryIds)
                .executeUpdate();
        }
    }

    public Student saveWithHistory(Student student) {
        // student version handling: update current version validity end
        StudentHistory old = student.getStudentHistory();
        LocalDateTime now = LocalDateTime.now();
        if(old != null) {
            old.setValidThru(now);
        }
        // and create new version
        StudentHistory current = EntityUtil.bindToEntity(student, new StudentHistory());
        current.setStudent(student);
        current.setValidFrom(now);
        current.setPrevStudentHistory(old);
        student.setStudentHistory(current);
        return EntityUtil.save(student, em);
    }

    /**
     * Absences of student
     *
     * @param user
     * @param student
     * @param pageable
     * @return
     */
    public StudentAbsenceSearchDto absences(HoisUserDetails user, Student student, Pageable pageable) {
        Long studentId = EntityUtil.getId(student);
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_absence sa").sort(pageable);
        qb.requiredCriteria("sa.student_id = :studentId", "studentId", studentId);

        // absence object for checking rights. So far only student is used
        StudentAbsence absence = new StudentAbsence();
        absence.setStudent(student);

        Page<StudentAbsenceDto> data = JpaQueryUtil.pagingResult(qb, "sa.id, sa.is_accepted, sa.is_rejected, sa.valid_from, sa.valid_thru, sa.cause, sa.version, sa.reject_reason", em, pageable).map(r -> {
            StudentAbsenceDto dto = new StudentAbsenceDto();
            dto.setId(resultAsLong(r, 0));
            dto.setIsAccepted(resultAsBoolean(r, 1));
            dto.setIsRejected(resultAsBoolean(r, 2));
            dto.setValidFrom(resultAsLocalDate(r, 3));
            dto.setValidThru(resultAsLocalDate(r, 4));
            dto.setCause(resultAsString(r, 5));
            dto.setVersion(resultAsLong(r, 6));
            dto.setRejectReason(resultAsString(r, 7));
            absence.setIsAccepted(dto.getIsAccepted());
            absence.setIsRejected(dto.getIsRejected() != null ? dto.getIsRejected() : Boolean.FALSE);
            dto.setUserCanEdit(Boolean.valueOf(StudentAbsenceUtil.canEdit(user, absence)));
            return dto;
        });

        // fetch student-related data
        qb = new JpaNativeQueryBuilder("from student s inner join person p on s.person_id = p.id left outer join student_group sg on s.student_group_id = sg.id");
        qb.requiredCriteria("s.id = :studentId", "studentId", studentId);
        Object studentData = qb.select("p.firstname, p.lastname, sg.code", em).getSingleResult();
        String studentName = PersonUtil.fullname(resultAsString(studentData, 0), resultAsString(studentData, 1));
        String studentGroup = resultAsString(studentData, 2);
        boolean canAddAbsence = StudentAbsenceUtil.canCreate(user, student);

        return new StudentAbsenceSearchDto(data.getContent(), pageable, data.getTotalElements(), studentName, studentGroup, canAddAbsence);
    }

    /**
     * Create new absence of student.
     * If absence is created by representative, send message about it to school admins.
     *
     * @param user
     * @param student
     * @param form
     * @return
     */
    public StudentAbsence create(HoisUserDetails user, Student student, StudentAbsenceForm form) {
        StudentAbsence absence = new StudentAbsence();
        absence.setStudent(student);
        absence.setIsAccepted(Boolean.FALSE);
        absence.setIsRejected(Boolean.FALSE);
        absence = save(absence, form);
        if(user.isRepresentative()) {
            // send message to school admins, if absence is created by parent/representative
            automaticMessageService.sendMessageToSchoolAdmins(MessageType.TEATE_LIIK_OP_PT, student.getSchool(), new StudentAbsenceCreated(absence));
        }
        return absence;
    }

    /**
     * Update absence of student
     *
     * @param absence
     * @param form
     * @return
     */
    public StudentAbsence save(StudentAbsence absence, StudentAbsenceForm form) {
        EntityUtil.bindToEntity(form, absence);
        return EntityUtil.save(absence, em);
    }

    /**
     * Delete absence of student
     *
     * @param user
     * @param absence
     */
    public void delete(HoisUserDetails user, StudentAbsence absence) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(absence, em);
    }

    /**
     * Applications of student
     *
     * @param studentId
     * @param pageable
     * @return
     */
    public Page<StudentApplicationDto> applications(Long studentId, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from application a join classifier type on type.code = a.type_code "+
                "join classifier status on status.code = a.status_code").sort(pageable);

        qb.requiredCriteria("a.student_id = :studentId", "studentId", studentId);
        return JpaQueryUtil.pagingResult(qb, "a.id, a.type_code, a.inserted, a.status_code, case when a.status_code = 'AVALDUS_STAATUS_KINNITATUD' then a.changed else null end, a.submitted, a.reject_reason", em, pageable).map(r -> {
            StudentApplicationDto dto = new StudentApplicationDto();
            dto.setId(resultAsLong(r, 0));
            dto.setType(resultAsString(r, 1));
            dto.setInserted(resultAsLocalDateTime(r, 2));
            dto.setStatus(resultAsString(r, 3));
            dto.setConfirmDate(resultAsLocalDateTime(r, 4));
            dto.setSubmitted(resultAsLocalDateTime(r, 5));
            dto.setRejectReason(resultAsString(r, 6));
            return dto;
        });
    }

    /**
     * Directives related to student
     *
     * @param user
     * @param student
     * @param pageable
     * @return
     */
    public Page<StudentDirectiveDto> directives(HoisUserDetails user, Student student, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive d join classifier type on type.code = d.type_code").sort(pageable);

        String showCanceled = "";
        if(!UserUtil.isSchoolAdmin(user, student.getSchool())) {
            // don't show these directives which are cancelled for given student
            showCanceled = " and ds.canceled = false";
        }
        qb.requiredCriteria(String.format("d.id in (select ds.directive_id from directive_student ds where ds.student_id = :studentId%s)", showCanceled), "studentId", EntityUtil.getId(student));

        qb.requiredCriteria("d.type_code <> :directiveType", "directiveType", DirectiveType.KASKKIRI_TYHIST);
        qb.requiredCriteria("d.status_code = :directiveStatus", "directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);

        return JpaQueryUtil.pagingResult(qb, "d.id, d.headline, d.type_code, d.directive_nr, d.confirm_date, d.inserted_by", em, pageable).map(r -> {
            StudentDirectiveDto dto = new StudentDirectiveDto();
            dto.setId(resultAsLong(r, 0));
            dto.setHeadline(resultAsString(r, 1));
            dto.setType(resultAsString(r, 2));
            dto.setDirectiveNr(resultAsString(r, 3));
            dto.setConfirmDate(resultAsLocalDate(r, 4));
            dto.setInsertedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 5)));
            return dto;
        });
    }
    
    /**
     * Practice contracts related to student
     *
     * @param user
     * @param student
     * @param pageable
     * @return
     */
    public Page<StudentPracticeContractDto> practiceContracts(HoisUserDetails user, Student student, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from contract c"
                + " join enterprise e on c.enterprise_id = e.id"
                + " join teacher t on c.teacher_id = t.id"
                + " join person p on t.person_id = p.id"
                + " join classifier status on c.status_code = status.code").sort(pageable);
        qb.requiredCriteria("c.student_id = :studentId", "studentId", EntityUtil.getId(student));

        return JpaQueryUtil.pagingResult(qb,
                "c.id, c.contract_nr, c.start_date, c.end_date, e.name, e.contact_person_name, p.firstname, p.lastname, c.confirm_date, c.status_code",
                em, pageable).map(r -> {
            StudentPracticeContractDto dto = new StudentPracticeContractDto();
            dto.setId(resultAsLong(r, 0));
            dto.setContractNr(resultAsString(r, 1));
            dto.setStartDate(resultAsLocalDate(r, 2));
            dto.setEndDate(resultAsLocalDate(r, 3));
            dto.setEnterprise(resultAsString(r, 4));
            dto.setSupervisor(resultAsString(r, 5));
            dto.setSchoolSupervisor(PersonUtil.fullname(resultAsString(r, 6), resultAsString(r, 7)));
            dto.setContractDate(resultAsLocalDate(r, 8));
            dto.setStatus(resultAsString(r, 9));
            return dto;
        });
    }

    /**
     * Foreign studies related to student
     *
     * @param user
     * @param student
     * @param pageable
     * @return
     */
    public Page<StudentForeignstudyDto> foreignstudies(HoisUserDetails user, Student student, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_student ds join directive d on ds.directive_id = d.id " +
                "join classifier country on ds.country_code = country.code " +
                "join classifier purpose on ds.abroad_purpose_code = purpose.code " +
                "join classifier programme on ds.abroad_programme_code = programme.code " +
                "left join classifier ehis_school on ds.ehis_school_code = ehis_school.code " +
                "left join study_period sp on ds.study_period_start_id = sp.id " +
                "left join study_period ep on ds.study_period_end_id = ep.id").sort(pageable);
        qb.requiredCriteria("ds.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_VALIS);
        qb.requiredCriteria("d.status_code = :directiveStatus", "directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        qb.filter("ds.canceled = false");

        return JpaQueryUtil.pagingResult(qb, "case when ds.is_abroad then abroad_school else ehis_school.name_et end, ds.country_code, " +
                "case when ds.is_period then sp.start_date else ds.start_date end, case when ds.is_period then ep.end_date else ds.end_date end, " +
                "ds.abroad_purpose_code, ds.abroad_programme_code", em, pageable).map(r -> {
            StudentForeignstudyDto dto = new StudentForeignstudyDto();
            dto.setSchool(resultAsString(r, 0));
            dto.setCountry(resultAsString(r, 1));
            dto.setStartDate(resultAsLocalDate(r, 2));
            dto.setEndDate(resultAsLocalDate(r, 3));
            dto.setAbroadPurpose(resultAsString(r, 4));
            dto.setAbroadProgramme(resultAsString(r, 5));
            return dto;
        });
    }

    /**
     * Subjects related to student
     *
     * @param student
     * @return
     */
    public List<AutocompleteResult> subjects(Student student) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject s "
                + "inner join curriculum_version_hmodule_subject cvhms on cvhms.subject_id = s.id "
                + "inner join curriculum_version_hmodule cvh on cvh.id = cvhms.curriculum_version_hmodule_id").groupBy("s.id");
        qb.requiredCriteria("cvh.curriculum_version_id = :curriculumVersionId", "curriculumVersionId", EntityUtil.getId(student.getCurriculumVersion()));

        List<?> data = qb.select("s.id, s.name_et, s.name_en, s.code, s.credits", em).getResultList();
        return StreamUtil.toMappedList(r ->
            new AutocompleteResult(resultAsLong(r, 0),
                    SubjectUtil.subjectName(resultAsString(r, 3), resultAsString(r, 1), resultAsDecimal(r, 4)),
                    SubjectUtil.subjectName(resultAsString(r, 3), resultAsString(r, 2), resultAsDecimal(r, 4))), data);
    }

    /**
     * Student data for student view form, main data tab
     *
     * @param user
     * @param student
     * @return
     */
    public StudentViewDto getStudentView(HoisUserDetails user, Student student) {
        StudentViewDto dto = StudentViewDto.of(student);
        // rights for editing student data, adding representative and displaying sensitive fields
        dto.setUserCanEditStudent(Boolean.valueOf(UserUtil.canEditStudent(user, student)));
        dto.setUserCanAddRepresentative(Boolean.valueOf(UserUtil.canAddStudentRepresentative(user, student)));
        dto.setUserIsSchoolAdmin(Boolean.valueOf(UserUtil.isSchoolAdmin(user, student.getSchool())));
        dto.setUserIsStudentGroupTeacher(Boolean.valueOf(UserUtil.isStudentGroupTeacher(user, student)));
        dto.setUserCanViewStudentSpecificData(Boolean.valueOf(UserUtil.canViewStudentSpecificData(user, student)));
        dto.setUserCanUpdateRR(Boolean.valueOf(UserUtil.canUpdateStudentRR(user, student)));
        dto.setUserCanViewStudentSupportServices(Boolean.valueOf(UserUtil.canViewStudentSupportServices(user, student)));
        dto.setUserCanViewPrivateStudentSupportServices(Boolean.valueOf(UserUtil.canViewPrivateStudentSupportServices(user, student)));
        if(!(Boolean.TRUE.equals(dto.getUserIsSchoolAdmin()) || UserUtil.isStudent(user, student) || UserUtil.isStudentRepresentative(user, student))) {
            dto.setSpecialNeed(null);
            dto.setIsRepresentativeMandatory(null);
        }

        if (Boolean.TRUE.equals(dto.getIsVocational())) {
            StudentCurriculumCompletion completion = getStudentCurriculumCompletion(student);
            if (completion != null) {
                dto.setCredits(completion.getCredits());
                dto.setKkh(completion.getAverageMark());
                dto.setIsCurriculumFulfilled(Boolean.valueOf(BigDecimal.ZERO.setScale(1).equals(completion.getStudyBacklog())));
            } else {
                dto.setCredits(BigDecimal.ZERO);
                dto.setKkh(BigDecimal.ZERO);
                dto.setIsCurriculumFulfilled(Boolean.FALSE);
            }

            dto.setHasRemarksPastSevenDays(studentRemarkService.studentHasRemarksPastSevenDays(student));
        }
        dto.setOccupationCertificates(occupationCertificates(student));
        dto.setDormitoryHistory(dormitoryHistory(student));

        OisFile logo = student.getPhoto();
        if (logo != null) {
            dto.setPhoto(EntityUtil.bindToDto(logo, new OisFileCommand()));
        }
        setStudentIndividualCurriculum(dto);
        setStudentBoardingSchool(dto);
        return dto;
    }

    public StudentCurriculumCompletion getStudentCurriculumCompletion(Student student) {
        List<StudentCurriculumCompletion> result = em.createQuery("select scc"
                + " from StudentCurriculumCompletion scc where scc.student = ?1", 
                StudentCurriculumCompletion.class)
                .setParameter(1, student)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    private void setStudentIndividualCurriculum(StudentViewDto studentDto) {
        List<?> result = em.createNativeQuery("select ds.start_date, coalesce(ds_lop.start_date, ds.end_date)"
                + " from directive_student ds"
                + " join directive d on d.id = ds.directive_id"
                + " left join (directive_student ds_lop join directive d_lop on d_lop.id = ds_lop.directive_id "
                + " and d_lop.type_code = :lopDirectiveType and d_lop.status_code = :directiveStatus) "
                + " on ds_lop.directive_student_id = ds.id and ds_lop.canceled = false"
                + " where ds.student_id = :studentId and d.type_code = :directiveType"
                + " and d.status_code = :directiveStatus and ds.canceled = false"
                + " and ds.start_date <= :today and coalesce(ds_lop.start_date, ds.end_date) >= :today"
                + " union all"
                + " select ds2.start_date, coalesce(ds_lop2.start_date, ds2.end_date)"
                + " from directive_student ds2"
                + " join directive d2 on d2.id = ds2.directive_id"
                + " join application a on a.id = ds2.application_id"
                + " join application_support_service ass on ass.application_id = a.id"
                + " left join (directive_student ds_lop2 join directive d_lop2 on d_lop2.id = ds_lop2.directive_id"
                + " and d_lop2.type_code = :lopDirectiveType2 and d_lop2.status_code = :directiveStatus)"
                + " on ds_lop2.directive_student_id = ds2.id and ds_lop2.canceled = false"
                + " where ds2.student_id = :studentId and d2.type_code = :directiveType2 and d2.status_code = :directiveStatus"
                + " and ds2.canceled = false and ass.support_service_code = :supportServiceCode" 
                + " and ds2.start_date <= :today and coalesce(ds_lop2.start_date, ds2.end_date) >= :today")
                .setParameter("studentId", studentDto.getId())
                .setParameter("directiveType", DirectiveType.KASKKIRI_INDOK.name())
                .setParameter("directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name())
                .setParameter("lopDirectiveType", DirectiveType.KASKKIRI_INDOKLOP.name())
                .setParameter("directiveType2", DirectiveType.KASKKIRI_TUGI.name())
                .setParameter("supportServiceCode", SupportServiceType.TUGITEENUS_1.name())
                .setParameter("lopDirectiveType2", DirectiveType.KASKKIRI_TUGILOPP.name())
                .setParameter("today", JpaQueryUtil.parameterAsTimestamp(LocalDate.now()))
                .getResultList();
        if (!result.isEmpty()) {
            studentDto.setIndividualCurriculum(Boolean.TRUE);
            studentDto.setIndividualCurriculumStart(resultAsLocalDate(result.get(0), 0));
            studentDto.setIndividualCurriculumEnd(resultAsLocalDate(result.get(0), 1));
        }
    }

    private void setStudentBoardingSchool(StudentViewDto studentDto) {
        List<?> result = em.createNativeQuery("select d.id, b.code b_code, r.code r_code, d.valid_from,"
                + " d.valid_thru, d.add_info from dormitory d"
                + " join room r on r.id = d.room_id"
                + " join building b on b.id = r.building_id"
                + " where d.student_id = :studentId and d.valid_from <= :today and d.valid_thru >= :today"
                + " order by d.valid_thru desc, d.valid_from desc")
                .setParameter("studentId", studentDto.getId())
                .setParameter("today", JpaQueryUtil.parameterAsTimestamp(LocalDate.now()))
                .getResultList();
        if (!result.isEmpty()) {
            Object r = result.get(0);
            studentDto.setBoardingSchool(new RoomAutocompleteResult(resultAsLong(r, 0),
                    resultAsString(r, 1), resultAsString(r, 2)));
            studentDto.setBoardingSchoolValidFrom(resultAsLocalDate(r, 3));
            studentDto.setBoardingSchoolValidThru(resultAsLocalDate(r, 4));
            studentDto.setBoardingSchoolAddInfo(resultAsString(r, 5));
        }
    }

    public Collection<StudentVocationalResultByTimeDto> vocationalResultsByTimeResults(Student student) {
        String journalResults = 
                "select false as module, coalesce(je.entry_date,jes.grade_inserted) as kp, j.id, j.name_et, " + 
                    "(select string_agg(tp.firstname||' '||tp.lastname,', ') " + 
                        "from journal_teacher jt " + 
                        "join teacher t on t.id = jt.teacher_id " + 
                        "join person tp on tp.id = t.person_id " + 
                    "where jt.journal_id=j.id) as teacher, " + 
                    "je.entry_type_code, " +
                    "(select string_agg(cvot.name_et,', ')||' ('||string_agg(distinct(cm.name_et||' - '||mcl.name_et),',')||')'" +
                        "from journal_omodule_theme jot " + 
                        "join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id " +
                        "join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id " +
                        "join curriculum_module cm on cm.id = cvo.curriculum_module_id " +
                        "join classifier mcl ON mcl.code = cm.module_code " +
                        "join curriculum_version cv on cv.id = cvo.curriculum_version_id " +
                    "where jot.journal_id = js.journal_id and cv.id=ss.curriculum_version_id) as my_theme, " + 
                    "(select string_agg(cvot.name_et,', ')||' ('||string_agg(distinct(coalesce(cm.name_en,cm.name_et)||' - '||coalesce(mcl.name_en,mcl.name_et)),',')||')'" +
                        "from journal_omodule_theme jot " + 
                        "join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id " +
                        "join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id " +
                        "join curriculum_module cm on cm.id = cvo.curriculum_module_id " +
                        "join classifier mcl on mcl.code = cm.module_code " +
                        "join curriculum_version cv on cv.id = cvo.curriculum_version_id " +
                    "where jot.journal_id = js.journal_id and cv.id=ss.curriculum_version_id) as my_theme_en, " +
                    "(select string_agg(cvot.name_et,', ')||' ('||string_agg(distinct(cm.name_et||' - '||mcl.name_et)||'('||cv.code||')',',')||')'" +
                        "from journal_omodule_theme jot " + 
                        "join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id " +
                        "join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id " +
                        "join curriculum_module cm on cm.id = cvo.curriculum_module_id " +
                        "join classifier mcl on mcl.code = cm.module_code " + 
                        "join curriculum_version cv on cv.id = cvo.curriculum_version_id " +
                    "where jot.journal_id = js.journal_id and cv.id!=ss.curriculum_version_id) as foreign_theme, " +
                    "(select string_agg(cvot.name_et,', ')||' ('||string_agg(distinct(coalesce(cm.name_en,cm.name_et)||' - '||coalesce(mcl.name_en,mcl.name_et)),',')||')'" + 
                        "from journal_omodule_theme jot " + 
                        "join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id " +
                        "join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id " +
                        "join curriculum_module cm on cm.id = cvo.curriculum_module_id " +
                        "join classifier mcl on mcl.code = cm.module_code " +
                        "join curriculum_version cv on cv.id = cvo.curriculum_version_id " +
                   "where jot.journal_id = js.journal_id and cv.id!=ss.curriculum_version_id) as foreign_theme_en, " +
                   "jes.grade_code, sy.year_code, sy.start_date, " +
                   "false as informal, false as formal, false as practice " +
                "from journal_student js " +
                "join journal j on j.id = js.journal_id " +
                "join journal_entry je on je.journal_id = js.journal_id " +
                "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id=js.id " + 
                "join student ss on ss.id=js.student_id " +
                "join study_year sy on sy.id = j.study_year_id " +
                "where js.student_id =:studentId and je.entry_type_code in (:entryTypeCodes)";
        
        String practiceJournalResults = "select false as module, pj.grade_inserted, pj.id, null, tp.firstname||' '||tp.lastname as teacher, null, " + 
                    "cvot.name_et || ' (' || cm.name_et || ' - ' || mcl.name_et || ')' as my_theme, " +
                    "cvot.name_et || ' (' || coalesce(cm.name_en,cm.name_et) || ' - ' || coalesce(cm.name_en,cm.name_et) || ')' as my_theme_en, " +
                    "null, null, pj.grade_code, sy.year_code, sy.start_date, false as informal, false as formal, true as practice " +
                "from practice_journal pj " +
                "join practice_journal_module_subject pjms on pj.id = pjms.practice_journal_id " +
                "join curriculum_version_omodule_theme cvot on cvot.id = pjms.curriculum_version_omodule_theme_id " +
                "join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id " +
                "join curriculum_module cm on cm.id = cvo.curriculum_module_id " +
                "join classifier mcl on mcl.code = cm.module_code " +
                "join curriculum_version cv on cv.id = cvo.curriculum_version_id " +
                "join curriculum c on c.id = cv.curriculum_id " +
                "join study_year sy on sy.id = pj.study_year_id " +
                "join teacher t on t.id = pj.teacher_id " +
                "join person tp on tp.id = t.person_id " +
                "where pj.student_id =:studentId";
        
        String protocolResults = 
                "select true as module, ps.grade_date,pp.id, " + 
                    "cm.name_et||' - '||mcl.name_et ||case when cv.id!=ss.curriculum_version_id then ' ('||cv.code||')' else '' end, " +
                    "tp.firstname ||' '||tp.lastname as teacher, null," +
                    "cm.name_et||' - '||mcl.name_et ||case when cv.id!=ss.curriculum_version_id then ' ('||cv.code||')' else '' end, " +
                    "coalesce(cm.name_en,cm.name_et)||' - '||coalesce(mcl.name_en,mcl.name_et) ||case when cv.id!=ss.curriculum_version_id then ' ('||cv.code||')' else '' end ," +
                    "null, null, ps.grade_code, sy.year_code, sy.start_date, false as informal, false as formal, false as practice " + 
                "from protocol pp " +
                "join protocol_vdata pv on pp.id=pv.protocol_id " +
                "join protocol_student ps on pp.id=ps.protocol_id " +
                "join curriculum_version_omodule cvo on pv.curriculum_version_omodule_id=cvo.id " +
                "join curriculum_module cm on cm.id = cvo.curriculum_module_id " +
                "join classifier mcl ON mcl.code = cm.module_code " + 
                "join curriculum_version cv on cv.id = cvo.curriculum_version_id " +
                "join student ss on ss.id=ps.student_id " +
                "left join teacher t on t .id = pv.teacher_id " +
                "left join person tp on tp.id = t.person_id " +
                "join study_year sy on pv.study_year_id=sy.id " +
          "where ps.student_id=:studentId";
        
        String informalApelResults = 
                "select case when aai.curriculum_version_omodule_theme_id is not null then false else true end as module, aa.confirmed, aa.id, null, null, null, " + 
                    "case when aai.curriculum_version_omodule_theme_id is not null then cvot.name_et || ' (' || cm.name_et || ' - ' || mcl.name_et || ')' " + 
                        "else cm.name_et || ' - ' || mcl.name_et end as my_theme, " +
                    "case when aai.curriculum_version_omodule_theme_id is not null then cvot.name_et || ' (' || cm.name_en || ' - ' || mcl.name_en || ')' " +
                        "else cm.name_en || ' - ' || mcl.name_en end as my_theme_en, " +
                    "null, null, aai.grade_code, sy.year_code, sy.start_date, true as informal, false as formal, false as practice " + 
                "from apel_application aa " + 
                "join apel_application_record aar on aa.id=aar.apel_application_id " + 
                "join apel_application_informal_subject_or_module aai on aar.id=aai.apel_application_record_id " + 
                "join curriculum_version_omodule cvo on aai.curriculum_version_omodule_id=cvo.id " + 
                "left join curriculum_version_omodule_theme cvot on aai.curriculum_version_omodule_theme_id=cvot.id " + 
                "join curriculum_module cm on cvo.curriculum_module_id=cm.id " + 
                "join classifier mcl on mcl.code = cm.module_code " + 
                "join study_year sy on sy.id = get_study_year(cast(aa.confirmed as date), cast(aa.school_id as int)) " + 
            "where aa.student_id=:studentId and aa.status_code='VOTA_STAATUS_C' and aai.transfer = true";
        
        String formalApelResults =  "select true as module, aaf.grade_date, aa.id, null, aaf.teachers, null, " + 
                "    cm.name_et || ' - ' || mcl.name_et || case when cv.id != ss.curriculum_version_id then ' (' || cv.code || ')' else '' end, " + 
                "    cm.name_en || ' - ' || mcl.name_en || case when cv.id != ss.curriculum_version_id then ' (' || cv.code || ')' else '' end, " + 
                "    aaf.name_et || ' - ' || a_s.name_et as foreign_theme, " + 
                "    aaf.name_en || ' - ' || a_s.name_en as foreign_theme_en, " + 
                "    aaf.grade_code, sy.year_code, sy.start_date, false as informal, true as formal, false as practice " +
                "from apel_application aa " + 
                "join apel_application_record aar on aa.id=aar.apel_application_id " + 
                "join apel_application_formal_subject_or_module aaf on aar.id=aaf.apel_application_record_id " + 
                "left join apel_school a_s on aaf.apel_school_id = a_s.id " + 
                "left join curriculum_version_omodule cvo on aaf.curriculum_version_omodule_id=cvo.id " + 
                "left join curriculum_module cm on cvo.curriculum_module_id=cm.id " + 
                "left join classifier mcl on mcl.code = cm.module_code " + 
                "left join curriculum_version cv on cvo.curriculum_version_id = cv.id " + 
                "left join student ss on aa.student_id = ss.id " + 
                "join study_year sy on sy.id = get_study_year(cast(aa.confirmed as date), cast(aa.school_id as int)) " + 
            "where aa.student_id=:studentId and aa.status_code='VOTA_STAATUS_C' and aaf.transfer = true";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from (" + journalResults + " union all " + practiceJournalResults + " union all " + protocolResults + 
                " union all " + informalApelResults + " union all " + formalApelResults + ") xx where grade_code is not null");
        qb.parameter("studentId", EntityUtil.getId(student));
        qb.parameter("entryTypeCodes", JOURNAL_RESULT_ENTRY_TYPES);
        
        qb.sort("kp desc, my_theme");
        
        List<?> rows = qb.select("*",em).getResultList();
        
        List<StudentVocationalResultByTimeDto> result = new ArrayList<>();
        for (Object r : rows) {
            Boolean isModule = resultAsBoolean(r, 0);
            
            StudentVocationalResultByTimeDto dto = new StudentVocationalResultByTimeDto();
            if (!isModule.booleanValue()) {
                dto.setJournalName(resultAsString(r, 3));
                dto.setEntryType(resultAsString(r, 5));
            }
            dto.setIsModule(isModule);
            
            boolean studentCurriculumResult = resultAsString(r, 6) != null ? true : false;
            dto.setName(studentCurriculumResult ? new AutocompleteResult(null, resultAsString(r, 6), resultAsString(r, 7))
                            : new AutocompleteResult(null, resultAsString(r, 8), resultAsString(r, 9)));
            
            dto.setDate(resultAsLocalDate(r, 1));
            dto.setGrade(resultAsString(r, 10));
            dto.setTeachers(resultAsString(r, 4));
            dto.setStudyYear(resultAsString(r, 11));
            dto.setStudyYearStartDate(resultAsLocalDate(r, 12));
            dto.setIsInformal(resultAsBoolean(r, 13));
            dto.setIsFormal(resultAsBoolean(r, 14));
            dto.setIsPractice(resultAsBoolean(r, 15));
            dto.setIsApel(Boolean.valueOf(dto.getIsInformal().booleanValue() || dto.getIsFormal().booleanValue()));
            
            result.add(dto);
        }
        return result;
    }

    public StudentVocationalResultDto vocationalResults(Student student) {
        StudentVocationalResultDto dto = new StudentVocationalResultDto();
        List<StudentVocationalResultModuleThemeDto> results = studentVocationalResults(student);
        dto.setResults(results);
        
        Long curriculumVersionId = EntityUtil.getId(student.getCurriculumVersion());
        String specialityCode = student.getStudentGroup() != null && student.getStudentGroup().getSpeciality() != null
                ? EntityUtil.getCode(student.getStudentGroup().getSpeciality())
                : "";
        dto.setCurriculumModules(vocationalCurriculumModules(student, curriculumVersionId, specialityCode));
        setExtraCurriculaModules(dto, results, curriculumVersionId, specialityCode);
        
        addOtherCurriculumVersionModuleThemes(dto.getResults(), dto.getCurriculumModules(),
                dto.getExtraCurriculaModules());
        return dto;
    }

    public List<StudentVocationalResultModuleThemeDto> studentVocationalResults(Student student) {
        List<StudentVocationalResultModuleThemeDto> result = new ArrayList<>();
        result.addAll(vocationalResultsThemeResults(student));
        result.addAll(vocationalResultsModuleResults(student));
        result.addAll(formalLearningReplacedModuleResults(student));

        return result;
    }
    
    // protocol results and apel informal learning results
    private List<StudentVocationalResultModuleThemeDto> vocationalResultsModuleResults(Student student) {
        List<StudentVocationalResultModuleThemeDto> result = new ArrayList<>();
        
        String from = "from student_vocational_result svr"
                + " join curriculum_version_omodule cvo on cvo.id = svr.curriculum_version_omodule_id"
                + " join curriculum_version cv on cv.id = cvo.curriculum_version_id"
                + " join curriculum_module cm on cm.id = cvo.curriculum_module_id"
                + " join classifier mcl on mcl.code = cm.module_code"
                + " left join study_year sy on sy.id = svr.study_year_id"
                + " left join apel_application_record aar on svr.apel_application_record_id = aar.id";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("svr.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.filter("svr.arr_modules is null");
        qb.sort("svr.grade_date");
        
        List<?> rows = qb.select("distinct cvo.id cvo_id, cm.id cm_id, cv.code, cm.name_et as module_name_et, mcl.name_et classifer_name_et, "
                + "cm.name_en as module_name_en, mcl.name_en as classifer_name_en, "
                + "cm.credits, svr.apel_application_record_id, svr.grade_code, svr.grade_date, "
                + "svr.teachers, sy.year_code, sy.start_date",
                em).getResultList();

        for (Object r : rows) {
            StudentVocationalResultModuleThemeDto dto = new StudentVocationalResultModuleThemeDto();
            
            dto.setCurriculumVersionModuleId(resultAsLong(r, 0));
            dto.setModule(new AutocompleteResult(resultAsLong(r, 1),
                    CurriculumUtil.moduleName(resultAsString(r, 3), resultAsString(r, 4), resultAsString(r, 2)),
                    CurriculumUtil.moduleName(resultAsString(r, 5), resultAsString(r, 6), resultAsString(r, 2))));
            dto.setCredits(resultAsDecimal(r, 7));
            
            Long apelApplicationRecordId = resultAsLong(r, 8);
            dto.setIsApelTransfer(apelApplicationRecordId != null ? Boolean.TRUE : Boolean.FALSE);
            dto.setIsFormalLearning(Boolean.FALSE);
            dto.setGrade(resultAsString(r, 9));
            dto.setDate(resultAsLocalDate(r, 10));
            dto.getTeachers().add(new AutocompleteResult(null, resultAsString(r, 11), resultAsString(r, 11)));
            dto.setStudyYear(resultAsString(r, 12));
            dto.setStudyYearStartDate(resultAsLocalDate(r, 13));
            result.add(dto);
        }
        return result;
    }
    
    private List<StudentVocationalResultModuleThemeDto> formalLearningReplacedModuleResults(Student student) {
        List<StudentVocationalResultModuleThemeDto> result = new ArrayList<>();
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_vocational_result svr "
                + "join curriculum_version_omodule cvo on cvo.id = any(svr.arr_modules) "
                + "join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "join classifier mcl on mcl.code = cm.module_code "
                + "left join apel_school a_s on svr.apel_school_id = a_s.id "
                + "left join study_year sy on sy.id = svr.study_year_id");
        qb.requiredCriteria("svr.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.sort("svr.grade_date");
        qb.groupBy("svr.arr_modules, svr.module_name_et, svr.module_name_en, svr.credits, a_s.id, "
                + "svr.grade_code, svr.grade_date, svr.teachers, sy.year_code, sy.start_date");
        
        List<?> rows = qb.select("svr.module_name_et, svr.module_name_en, string_agg(cm.name_et || ' - ' || mcl.name_et, ', ') as replaced_modules_et, " + 
                "string_agg(cm.name_en || ' - ' || mcl.name_en, ', ') as replaced_modules_en, svr.credits, a_s.id as school_id, " + 
                "a_s.name_et, a_s.name_en, svr.grade_code, svr.grade_date, svr.teachers, sy.year_code, sy.start_date, " +
                "array_to_string(array_agg(cast(cm.id as text)), ',')", em).getResultList();

        for (Object r : rows) {
            StudentVocationalResultModuleThemeDto dto = new StudentVocationalResultModuleThemeDto();
            dto.setCurriculumVersionModuleId(null);
            dto.setModule(new AutocompleteResult(null, 
                    formalLearningResultModuleName(resultAsString(r, 0), resultAsString(r, 2), resultAsString(r, 6)),
                    formalLearningResultModuleName(resultAsString(r, 1), resultAsString(r, 3), resultAsString(r, 7))));
            dto.setCredits(resultAsDecimal(r, 4));
            dto.setGrade(resultAsString(r, 8));
            dto.setIsApelTransfer(Boolean.TRUE);
            dto.setIsFormalLearning(Boolean.TRUE);
            
            String replacedModulesAsString = resultAsString(r, 13);
            if(replacedModulesAsString != null) {
                List<String> replacedModules = Arrays.asList(replacedModulesAsString.split(","));
                dto.setReplacedModules(new ArrayList<>());
                for (String moduleId : replacedModules) {
                    dto.getReplacedModules().add(Long.valueOf(moduleId));
                }
            }
            result.add(dto);
       }
       return result;
    }
    
    private static String formalLearningResultModuleName(String moduleName, String replacedModules, String school) {
        if (moduleName != null && replacedModules != null) {
            String name = moduleName + " -> (" + replacedModules + ")";
            return school != null ? name + " - " + school : name;
        }
        return null;
    }

    private Collection<StudentVocationalResultModuleThemeDto> vocationalResultsThemeResults(Student student) {
        String journalSelect = "cvo_id, cm_id, cv_code, cm_name_et, mcl_name_et, cm_name_en, mcl_name_en, cvot_id, cvot_name_et, cvot_credits, "
                + "grade_code, grade_inserted, teacher_id, teacher_firstname, teacher_lastname, sy_year_code, sy_start_date, "
                + "is_apel_transfer, is_formal, curriculum_version_result";

        String journalCurriculumResults = " select * from (select distinct on (cvot.id, teacher_id) cvo.id cvo_id, cm.id cm_id, "
                + "cvot.curriculum_version_omodule_id, cvot.name_et cvot_name_et, cvot.credits cvot_credits, "
                + "jes.grade_code grade_code, jes.grade_inserted grade_inserted, tp.id teacher_id, tp.firstname teacher_firstname, "
                + "tp.lastname teacher_lastname, cvot.id cvot_id, cv.code cv_code, cm.name_et cm_name_et, mcl.name_et mcl_name_et, cm.name_en cm_name_en, "
                + "mcl.name_en mcl_name_en, cm.credits cm_credits, sy.year_code sy_year_code, sy.start_date sy_start_date, "
                + "false is_apel_transfer, false is_formal, cv.id = :curriculumVersionId curriculum_version_result "
                + "from journal_student js "
                + "join journal_omodule_theme jot on jot.journal_id = js.journal_id "
                + "join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id "
                + "join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id " 
                + "join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "join classifier mcl on mcl.code = cm.module_code "
                + "join curriculum_version cv on cv.id = cvo.curriculum_version_id "
                + "join curriculum c on c.id = cv.curriculum_id "
                + "join journal j on j.id = js.journal_id "
                + "join study_year sy on sy.id = j.study_year_id " 
                + "join journal_entry je on je.journal_id = js.journal_id "
                + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                + "left join journal_teacher jt on jt.journal_id = js.journal_id "
                + "left join teacher t on t.id = jt.teacher_id "
                + "left join person tp on tp.id = t.person_id "
                + "where js.student_id = :studentId and je.entry_type_code = :entryTypeCode and jes.grade_code is not null "
                    + "and cm.id in (select cmo.curriculum_module_id from curriculum_version cv "
                    + "join curriculum_version_omodule cmo on cv.id = cmo.curriculum_version_id where cv.id = :curriculumVersionId)) x";
        
        String journalExtraCurriculumResults = " select distinct on (cvot.id, teacher_id) cvo.id cvo_id, cm.id cm_id, "
                + "cvot.curriculum_version_omodule_id, cvot.name_et cvot_name_et, cvot.credits cvot_credits, "
                + "jes.grade_code grade_code, jes.grade_inserted grade_inserted, tp.id teacher_id, tp.firstname teacher_firstname, "
                + "tp.lastname teacher_lastname, cvot.id cvot_id, cv.code cv_code, cm.name_et cm_name_et, mcl.name_et mcl_name_et, cm.name_en cm_name_en, "
                + "mcl.name_en mcl_name_en, cm.credits cm_credits, sy.year_code sy_year_code, sy.start_date sy_start_date, "
                + "false is_apel_transfer, false is_formal, cv.id = :curriculumVersionId curriculum_version_result "
                + "from journal_student js "
                + "join journal_omodule_theme jot on jot.journal_id = js.journal_id "
                + "join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id "
                + "join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id "
                + "join curriculum_module cm on cm.id = cvo.curriculum_module_id " 
                + "join classifier mcl on mcl.code = cm.module_code "
                + "join curriculum_version cv on cv.id = cvo.curriculum_version_id "
                + "join curriculum c on c.id = cv.curriculum_id " 
                + "join journal j on j.id = js.journal_id "
                + "join study_year sy on sy.id = j.study_year_id "
                + "join journal_entry je on je.journal_id = js.journal_id "
                + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id "
                + "left join journal_teacher jt on jt.journal_id = js.journal_id " 
                + "left join teacher t on t.id = jt.teacher_id "
                + "left join person tp on tp.id = t.person_id "
                + "where js.student_id = :studentId and je.entry_type_code = :entryTypeCode and jes.grade_code is not null "
                    + "and cm.id not in (select cmo3.curriculum_module_id from curriculum_version cv3 "
                        + "join curriculum_version_omodule cmo3 on cv3.id = cmo3.curriculum_version_id where cv3.id = :curriculumVersionId) "
                    + "and (select count(*) from journal_omodule_theme jot2 join curriculum_version_omodule_theme cvot2 on "
                    + "cvot2.id = jot2.curriculum_version_omodule_theme_id join curriculum_version_omodule cvo2 on cvo2.id = cvot2.curriculum_version_omodule_id "
                    + "where jot2.journal_id = j.id and cvo2.curriculum_module_id in "
                        + "(select cmo3.curriculum_module_id from curriculum_version cv3 "
                        + "join curriculum_version_omodule cmo3 on cv3.id = cmo3.curriculum_version_id "
                        + "where cv3.id = :curriculumVersionId)) = 0";
        
        String practiceJournalResults = "select cvo.id cvo_id, cm.id cm_id, cvot.curriculum_version_omodule_id, cvot.name_et cvot_name_et, "
                + "cvot.credits cvot_credits, pj.grade_code grade_code, pj.grade_inserted grade_inserted, tp.id teacher_id, tp.firstname teacher_firstname, "
                + "tp.lastname teacher_lastname, cvot.id cvot_id, cv.code cv_code, cm.name_et cm_name_et, mcl.name_et mcl_name_et, cm.name_en cm_name_en, " 
                + "mcl.name_en mcl_name_en, cm.credits cm_credits, sy.year_code sy_year_code, sy.start_date sy_start_date, "
                + "false is_apel_transfer, false is_formal, cv.id = :curriculumVersionId curriculum_version_result "
                + "from practice_journal pj "
                + "join practice_journal_module_subject pjms on pj.id = pjms.practice_journal_id "
                + "join curriculum_version_omodule_theme cvot on cvot.id = pjms.curriculum_version_omodule_theme_id "
                + "join curriculum_version_omodule cvo on cvo.id = cvot.curriculum_version_omodule_id "
                + "join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "join classifier mcl on mcl.code = cm.module_code "
                + "join curriculum_version cv on cv.id = cvo.curriculum_version_id " 
                + "join curriculum c on c.id = cv.curriculum_id "
                + "join study_year sy on sy.id = pj.study_year_id "
                + "join teacher t on t.id = pj.teacher_id "
                + "join person tp on tp.id = t.person_id "
                + "where pj.student_id = :studentId and pj.grade_code is not null";
        
        String journalResultsOrderBy = " order by 1 desc, teacher_id, grade_inserted desc";
        
        String apelInformalResults = "select cvo.id cvo_id, cm.id cm_id, cv.code cv_code, "
                + "cm.name_et cm_name_et, mcl.name_et mcl_name_et, cm.name_en cm_name_en, mcl.name_en mcl_name_en, "
                + "cvot.id cvot_id, cvot.name_et cvot_name_et, cvot.credits cvot_credits, aai.grade_code grade_code, aa.confirmed grade_inserted, null as teacher_id, "
                + "null teacher_firstname, null teacher_lastname, sy.year_code sy_year_code, sy.start_date sy_start_date, "
                + "true is_apel_transfer, false is_formal, cv.id = :curriculumVersionId curriculum_version_result "
                + "from apel_application aa join apel_application_record aar on aa.id = aar.apel_application_id "
                + "join apel_application_informal_subject_or_module aai on aar.id = aai.apel_application_record_id "
                + "join curriculum_version_omodule_theme cvot on aai.curriculum_version_omodule_theme_id = cvot.id "
                + "join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id = cvo.id "
                + "join curriculum_version cv on cvo.curriculum_version_id = cv.id "
                + "join curriculum_module cm on cvo.curriculum_module_id = cm.id "
                + "join classifier mcl on mcl.code = cm.module_code "
                + "join study_year sy on sy.id = get_study_year(cast(aa.confirmed as date), cast(aa.school_id as int)) "
                + "where aa.student_id = :studentId and aa.status_code = :applicationStatus and aai.transfer = true";
        
        String apelFormalResults = "select cvo.id cvo_id, cm.id cm_id, cv.code cv_code, cm.name_et cm_name_et, "
                + "mcl.name_et mcl_name_et, cm.name_en cm_name_en, mcl.name_en mcl_name_en, "
                + "cvot.id cvot_id, cvot.name_et cvot_name_et, cvot.credits cvot_credits, 'KUTSEHINDAMINE_A' grade_code, aa.confirmed grade_inserted, null as teacher_id, "
                + "null teacher_firstname, null teacher_lastname, sy.year_code sy_year_code, sy.start_date sy_start_date, "
                + "true is_apel_transfer, true is_formal, cv.id = :curriculumVersionId curriculum_version_result "
                + "from apel_application aa join apel_application_record aar on aa.id = aar.apel_application_id "
                + "join apel_application_formal_subject_or_module aaf on aar.id = aaf.apel_application_record_id "
                + "join apel_application_formal_replaced_subject_or_module aarf on aar.id = aarf.apel_application_record_id "
                + "join curriculum_version_omodule_theme cvot on aarf.curriculum_version_omodule_theme_id = cvot.id "
                + "join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id = cvo.id "
                + "join curriculum_version cv on cvo.curriculum_version_id = cv.id "
                + "join curriculum_module cm on cvo.curriculum_module_id = cm.id "
                + "join classifier mcl on mcl.code = cm.module_code "
                + "join study_year sy on sy.id = get_study_year(cast(aa.confirmed as date), cast(aa.school_id as int)) "
                + "where aa.student_id = :studentId and aa.status_code = :applicationStatus and aaf.transfer = true";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from (select " + journalSelect + " from (" + journalCurriculumResults + " union "
                        + journalExtraCurriculumResults + " union " + practiceJournalResults + journalResultsOrderBy
                        + ") as journal_results union all " + apelInformalResults + " union all " + apelFormalResults
                        + " order by is_apel_transfer desc, grade_inserted desc) as results");

        qb.parameter("entryTypeCode", JournalEntryType.SISSEKANNE_L.name());
        qb.parameter("studentId", EntityUtil.getId(student));
        qb.parameter("curriculumVersionId", EntityUtil.getId(student.getCurriculumVersion()));
        qb.parameter("curriculumId", EntityUtil.getId(student.getCurriculumVersion().getCurriculum()));
        qb.parameter("applicationStatus", ApelApplicationStatus.VOTA_STAATUS_C.name());

        List<?> rows = qb.select("cvo_id, cm_id, cv_code, cm_name_et, mcl_name_et, cm_name_en, mcl_name_en,"
                + "cvot_id, cvot_name_et, cvot_credits, grade_code, grade_inserted, teacher_id, teacher_firstname, teacher_lastname, "
                + "sy_year_code, sy_start_date, is_apel_transfer, is_formal, curriculum_version_result",
                em).getResultList();

        Map<Long, StudentVocationalResultModuleThemeDto> result = new HashMap<>();
        for (Object r : rows) {
            Long themeId = resultAsLong(r, 7);
            StudentVocationalResultModuleThemeDto dto = result.get(themeId);
            if (dto != null) {
                String teacherName = PersonUtil.fullname(resultAsString(r, 13), resultAsString(r, 14));
                dto.getTeachers().add(new AutocompleteResult(resultAsLong(r, 12), teacherName, teacherName));
            } else {
                dto = new StudentVocationalResultModuleThemeDto();
                dto.setCurriculumVersionModuleId(resultAsLong(r, 0));
                dto.setModule(new AutocompleteResult(resultAsLong(r, 1),
                        CurriculumUtil.moduleName(resultAsString(r, 3), resultAsString(r, 4), resultAsString(r, 2)),
                        CurriculumUtil.moduleName(resultAsString(r, 5), resultAsString(r, 6), resultAsString(r, 2))));

                dto.setTheme(new AutocompleteResult(themeId, resultAsString(r, 8), resultAsString(r, 8)));
                dto.setCredits(resultAsDecimal(r, 9));
                dto.setGrade(resultAsString(r, 10));
                dto.setDate(resultAsLocalDate(r, 11));
                String teacherName = PersonUtil.fullname(resultAsString(r, 13), resultAsString(r, 14));
                dto.getTeachers().add(new AutocompleteResult(resultAsLong(r, 12), teacherName, teacherName));
                dto.setStudyYear(resultAsString(r, 15));
                dto.setStudyYearStartDate(resultAsLocalDate(r, 16));
                dto.setIsApelTransfer(resultAsBoolean(r, 17));
                dto.setIsFormalLearning(resultAsBoolean(r, 18));
                dto.setIsCurrentCurriculumVersionResult(resultAsBoolean(r, 19));
                result.put(themeId, dto);
            }
        }
        return result.values();
    }

    private List<StudentVocationalModuleDto> vocationalCurriculumModules(Student student, Long curriculumVersionId,
            String specialityCode) {
        List<?> data = em.createNativeQuery("select cvo.id from curriculum_version_omodule cvo "
                + "left join curriculum_module_occupation cmo on cmo.curriculum_module_id = cvo.curriculum_module_id and cmo.occupation_code like ?1 "
                + "where cvo.curriculum_version_id = ?2 and (cmo.id is null or cmo.occupation_code = ?3)")
                .setParameter(1, "%" + MainClassCode.SPETSKUTSE.name() + "%")
                .setParameter(2, curriculumVersionId)
                .setParameter(3, specialityCode)
                .getResultList();

        Set<Long> moduleIds = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);
        List<StudentVocationalModuleDto> modules = curriculumVersionOccupationModules(moduleIds);
        Map<Long, StudentVocationalModuleDto> modulesMap = StreamUtil.toMap(m -> m.getCurriculumModule().getId(),
                modules);
        if (!modulesMap.isEmpty()) {
            Map<Long, Set<Long>> replacedThemes = curriculumModuleReplacedThemes(student, modulesMap.keySet());
            for (Long curriculumModuleId : replacedThemes.keySet()) {
                Set<Long> moduleReplacedThemes = replacedThemes.get(curriculumModuleId);
                if (moduleReplacedThemes != null) {
                    modulesMap.get(curriculumModuleId).getReplacedThemes().addAll(moduleReplacedThemes);
                }
            }
        }

        return modules;
    }

    private List<StudentVocationalModuleDto> curriculumVersionOccupationModules(Set<Long> moduleIds) {
        if (moduleIds == null || moduleIds.isEmpty()) {
            return new LinkedList<>();
        }
        List<CurriculumVersionOccupationModule> modules = em
                .createQuery("select cvo from CurriculumVersionOccupationModule cvo where cvo.id in (?1)",
                        CurriculumVersionOccupationModule.class)
                .setParameter(1, moduleIds).getResultList();

        return StreamUtil.toMappedList(StudentVocationalModuleDto::of, modules);
    }

    private Map<Long, Set<Long>> curriculumModuleReplacedThemes(Student student, Set<Long> curriculumModuleIds) {
        List<?> data = em.createNativeQuery("select aot.curriculum_module_id, aot.curriculum_version_omodule_theme_id "
                + "from application_omodule_theme aot "
                + "join application a on a.id = aot.application_id "
                + "where a.student_id = ?1 and a.type_code = ?2 and a.status_code = ?3 "
                + "and aot.curriculum_module_id in (?4) and aot.is_old = false")
                .setParameter(1, EntityUtil.getId(student))
                .setParameter(2, ApplicationType.AVALDUS_LIIK_RAKKAVA.name())
                .setParameter(3, ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name())
                .setParameter(4, curriculumModuleIds)
                .getResultList();

        return StreamUtil.nullSafeList(data).stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> resultAsLong(r, 1), Collectors.toSet())));
    }

    private void setExtraCurriculaModules(StudentVocationalResultDto dto,
            List<StudentVocationalResultModuleThemeDto> results, Long curriculumVersionId, String specialityCode) {
        Map<Long, Long> extraCurriculaModuleIds = extraCurriculaSpecialityModules(curriculumVersionId, specialityCode);
        Set<Long> curriculaModuleIds = StreamUtil.toMappedSet(it -> it.getCurriculumModule().getId(),
                dto.getCurriculumModules());

        if (!results.isEmpty()) {
            for (StudentVocationalResultModuleThemeDto result : results) {
                if (result.getModule().getId() != null && result.getCurriculumVersionModuleId() != null
                        && !extraCurriculaModuleIds.containsKey(result.getModule().getId())) {
                    extraCurriculaModuleIds.put(result.getModule().getId(), result.getCurriculumVersionModuleId());
                }
            }
        }
        
        extraCurriculaModuleIds.keySet().removeIf(curriculaModuleIds::contains);
        dto.getExtraCurriculaModules().addAll(StreamUtil.toMappedList(StudentVocationalModuleDto::of,
                curriculumVersionOccupationModuleRepository.findAll(extraCurriculaModuleIds.values())));
    }

    private Map<Long, Long> extraCurriculaSpecialityModules(Long curriculumVersionId, String specialityCode) {
        List<?> data = em.createNativeQuery("select distinct cm.id cm_id, cvo.id cvo_id from curriculum_version_omodule cvo "
                + "join curriculum_module cm on cm.id = cvo.curriculum_module_id "
                + "join curriculum_module_occupation cmo on cmo.curriculum_module_id = cvo.curriculum_module_id and cmo.occupation_code like ?1 "
                + "where cvo.curriculum_version_id = ?2 and cmo.occupation_code != ?3")
                .setParameter(1, "%" + MainClassCode.SPETSKUTSE.name() + "%")
                .setParameter(2, curriculumVersionId)
                .setParameter(3, specialityCode)
                .getResultList();
        
        return StreamUtil.toMap(r -> resultAsLong(r, 0), r -> resultAsLong(r, 1), data);
    }

    private void addOtherCurriculumVersionModuleThemes(List<StudentVocationalResultModuleThemeDto> results,
            List<StudentVocationalModuleDto> curriculumModules,
            List<StudentVocationalModuleDto> extraCurriculaModules) {
        List<StudentVocationalResultModuleThemeDto> themeResults = StreamUtil.toFilteredList(r -> r.getTheme() != null
                && r.getTheme().getId() != null && Boolean.FALSE.equals(r.getIsCurrentCurriculumVersionResult()),
                results);

        if (!themeResults.isEmpty()) {
            Map<Long, List<StudentVocationalResultModuleThemeDto>> themesByModules = themeResults.stream()
                    .collect(Collectors.groupingBy(t -> t.getModule().getId()));

            List<StudentVocationalModuleDto> modules = new ArrayList<>();
            modules.addAll(curriculumModules);
            modules.addAll(extraCurriculaModules);
            Map<Long, StudentVocationalModuleDto> modulesMap = StreamUtil.toMap(m -> m.getCurriculumModule().getId(),
                    modules);

            for (Long moduleId : modulesMap.keySet()) {
                StudentVocationalModuleDto module = modulesMap.get(moduleId);
                Map<Long, CurriculumVersionOccupationModuleThemeDto> moduleThemes = StreamUtil.toMap(t -> t.getId(),
                        module.getThemes());
                List<StudentVocationalResultModuleThemeDto> moduleThemeResults = themesByModules.get(moduleId);

                if (moduleThemeResults != null) {
                    for (StudentVocationalResultModuleThemeDto themeResult : moduleThemeResults) {
                        if (!moduleThemes.containsKey(themeResult.getTheme().getId())) {
                            CurriculumVersionOccupationModuleThemeDto themeDto = CurriculumVersionOccupationModuleThemeDto
                                    .forCurriculumFulfillment(em.getReference(CurriculumVersionOccupationModuleTheme.class,
                                            themeResult.getTheme().getId()));
                            themeDto.setOtherCurriculumVersionModuleTheme(Boolean.TRUE);
                            module.getOtherCurriculumVersionModuleThemes().add(themeDto);
                        }
                    }
                }
            }
        }
    }

    private List<StudentOccupationCertificateDto> occupationCertificates(Student student) {
        List<StudentOccupationCertificate> data = em.createQuery(
                "select soc from StudentOccupationCertificate soc where soc.student.id = ?1 order by soc.issueDate desc", StudentOccupationCertificate.class)
            .setParameter(1, EntityUtil.getId(student))
            .getResultList();
        return StreamUtil.toMappedList(StudentOccupationCertificateDto::new, data);
    }

    private List<StudentDormitoryHistoryDto> dormitoryHistory(Student student) {
        List<?> data = em.createNativeQuery("select sh.inserted, sh.dormitory_code from student s "
                + "join student_history sh on sh.student_id = s.id "
                + "where s.id = ?1 and sh.dormitory_code is not null order by sh.inserted desc")
                .setParameter(1, EntityUtil.getId(student))
                .getResultList();

        List<StudentDormitoryHistoryDto> dormitoryHistory = new ArrayList<>();
        for (Object row : data) {
            StudentDormitoryHistoryDto newerHistoryDto = dormitoryHistory.size() > 0
                    ? dormitoryHistory.get(dormitoryHistory.size() - 1) : null;
            StudentDormitoryHistoryDto historyDto = new StudentDormitoryHistoryDto(resultAsLocalDate(row, 0),
                    resultAsString(row, 1));
            if (newerHistoryDto != null && newerHistoryDto.getDormitory().equals(historyDto.getDormitory())) {
                newerHistoryDto.setDate(historyDto.getDate());
            } else {
                dormitoryHistory.add(historyDto);
            }
        }
        return dormitoryHistory;
    }

    public List<StudentVocationalConnectedEntity> vocationalConnectedEntities(Long studentId) {
        Query q = em.createNativeQuery("select jj.id, 'journal' as type, jj.name_et as name_et, jj.name_et as name_en, sy.end_date, sy.year_code, null as protocol_nr from journal jj "
                + "join journal_student js on jj.id=js.journal_id " 
                + "join study_year sy on jj.study_year_id = sy.id " 
                + "where js.student_id = ?1 " 
                + "union " 
                + "select pj.id, 'practice', 'Praktikapäevik', 'Practice journal', pj.grade_inserted, sy.year_code, null as protocol_nr from practice_journal pj "
                + "join study_year sy on pj.study_year_id = sy.id "
                + "where pj.student_id = ?1 "
                + "union "
                + "select ps.protocol_id, 'protocol', cm.name_et, cm.name_et, sy.end_date, sy.year_code, p.protocol_nr from protocol_student ps "
                + "join protocol p on ps.protocol_id = p.id "
                + "join protocol_vdata pvd on ps.protocol_id = pvd.protocol_id "  
                + "join curriculum_version_omodule cvo on pvd.curriculum_version_omodule_id = cvo.id " 
                + "join curriculum_module cm on cvo.curriculum_module_id = cm.id " 
                + "join study_year sy on pvd.study_year_id = sy.id " 
                + "where ps.student_id = ?1 " 
                + "union " 
                + "select aa.id, 'apel', 'VÕTA avaldus', 'APEL application', aa.confirmed, null as year_code, null from apel_application aa "
                + "where aa.student_id = ?1 and aa.status_code='VOTA_STAATUS_C' " 
                + "order by 5 desc");
        q.setParameter(1, studentId);
        List<?> data = q.getResultList();
        
        return StreamUtil.toMappedList(
                r -> new StudentVocationalConnectedEntity(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2),
                        resultAsString(r, 3), resultAsLocalDate(r, 4), resultAsString(r, 5), resultAsString(r, 6)), data);
    }

    public List<AutocompleteResult> specialities(Student student) {
        return StreamUtil.toMappedList(spec -> AutocompleteResult.of(spec.getCurriculumSpeciality()),
                student.getCurriculumVersion().getSpecialities());
    }

    public List<StudentSpecialitySearchDto> saveSpecialities(HoisUserDetails user, List<StudentSpecialitySearchDto> form) {
        EntityUtil.setUsername(user.getUsername(), em);
        Map<Long, StudentSpecialitySearchDto> rows = form.stream().collect(Collectors.toMap(StudentSpecialitySearchDto::getId, Function.identity()));
        rows.forEach((id, studentSpec) -> {
            Student student = em.getReference(Student.class, id);
            if (UserUtil.isSameSchool(user, student.getSchool()) && StudentUtil.isActive(student) 
                    && !Objects.equals(EntityUtil.getNullableId(student.getCurriculumSpeciality()), studentSpec.getSpeciality().getId())) {
                student.setCurriculumSpeciality(studentSpec.getSpeciality().getId() == null ? null : em.getReference(CurriculumSpeciality.class, studentSpec.getSpeciality().getId()));
            } else {
                studentSpec.getSpeciality().setId(EntityUtil.getNullableId(student.getCurriculumSpeciality()));
            }
        });
        return rows.values().stream().collect(Collectors.toList());
    }

    public Map<Long, StudentVocationalStudyProgramme> studentStudyProgrammes(Set<Long> studentIds) {
        List<?> data = em.createNativeQuery("select student_id, "
                + "sum(case when m.module_code = 'KUTSEMOODUL_Y' then m.credits else 0 end) as general, "
                + "sum(case when m.module_code = 'KUTSEMOODUL_P' then m.credits else 0 end) as core, "
                + "sum(case when m.module_code = 'KUTSEMOODUL_L' then m.credits else 0 end) as final, "
                + "min(m.optional_study_credits) as free_choice from "
                + "(select distinct ss.id student_id, cvo.id cvo_id, cm.id as m_id, cm.credits, "
                + "cc.optional_study_credits, cm.module_code from curriculum_version cv "
                + "join curriculum_version_omodule cvo on cv.id = cvo.curriculum_version_id "
                + "join curriculum_module cm on cvo.curriculum_module_id = cm.id and cv.curriculum_id = cm.curriculum_id "
                    + "and coalesce(cm.is_additional, false) = false and cm.module_code in (?1) "
                    + "and coalesce(cm.is_additional, false) = false "
                + "join curriculum cc on cv.curriculum_id = cc.id "
                + "join student ss on cv.id = ss.curriculum_version_id "
                + "left join student_group sg on ss.student_group_id = sg.id "
                + "where ss.id in (?2) and (sg.id is null or coalesce( sg.speciality_code, 'x' )= 'x' "
                + "or coalesce( sg.speciality_code, 'x' )!= 'x' and exists(select 1 from curriculum_module_occupation cmo left "
                + "join classifier_connect ccc on cmo.occupation_code = ccc.connect_classifier_code "
                + "where cmo.curriculum_module_id = cm.id and (cmo.occupation_code = sg.speciality_code "
                + "or ccc.classifier_code = sg.speciality_code)))) as m group by student_id")
                .setParameter(1, EnumUtil.toNameList(CurriculumModuleType.KUTSEMOODUL_Y,
                        CurriculumModuleType.KUTSEMOODUL_P, CurriculumModuleType.KUTSEMOODUL_L))
                .setParameter(2, studentIds)
                .getResultList();
        return StreamUtil.toMap(r -> resultAsLong(r, 0), r -> new StudentVocationalStudyProgramme(resultAsDecimal(r, 1),
                resultAsDecimal(r, 2), resultAsDecimal(r, 3), resultAsDecimal(r, 4)), data);
    }


    public Page<StudentSupportServiceDto> supportServices(Student student, Pageable pageable, boolean showPrivate) {
        HashMap<String, Object> params = new HashMap<>();
        return JpaQueryUtil.pagingResult(supportServicesQB(student, params, pageable, showPrivate),
                "id, entry_date, title, \"content\", validity_code, is_public, ois_file_id, submitter, artificial, ending_id", params, em, pageable)
                .map(r -> new StudentSupportServiceDto(r, EntityUtil.getOptionalOne(OisFile.class, resultAsLong(r, 6), em)));
    }

    public List<StudentSupportServiceDto> supportServicesList(Student student, boolean showPrivate, LocalDate from, LocalDate thru) {
        HashMap<String, Object> params = new HashMap<>();
        JpaNativeQueryBuilder qb = supportServicesQB(student, params, null, showPrivate);
        qb.optionalCriteria("entry_date >= :from", "from", from);
        qb.optionalCriteria("entry_date <= :thru", "thru", thru);
        qb.sort("entry_date desc");
        List<?> results = qb.select("id, entry_date, title, \"content\", validity_code, is_public, ois_file_id, submitter, artificial, ending_id", em, params).getResultList();
        return StreamUtil.toMappedList(r -> new StudentSupportServiceDto(r, EntityUtil.getOptionalOne(OisFile.class, resultAsLong(r, 6), em)), results);
    }
    
    private static JpaNativeQueryBuilder studentSupportServices(Student student) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_support_service sss");
        qb.requiredCriteria("sss.student_id = :studentId", "studentId", student.getId());
        return qb;
    }
    
    private static JpaNativeQueryBuilder directiveSupportServices(Student student) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from directive_student ds "
                + "join directive d on d.id = ds.directive_id "
                + "join application a on a.id = ds.application_id " // Application should be for every ds.
                + "join application_support_service ass on ass.application_id = a.id " // If there is a decision and it is TRUE then it HAS TO have at least 1 service.
                + "join (select c.id as c_id, concat ( c.name_et, ' - (', "
                + "coalesce(string_agg( p.firstname || ' ' || p.lastname, ', ' order by p.firstname, p.lastname ), cm.member_name), ')' ) as submitter "
                + "from committee c join committee_member cm on c.id = cm.committee_id and cm.is_chairman "
                + "left join person p on p.id = cm.person_id group by c.id, cm.member_name) as sub on sub.c_id = a.committee_id "
                + "left join directive_student ds2 on ds.id = ds2.directive_student_id and ds2.canceled = false "
                + "left join directive d2 on d2.id = ds2.directive_id and d2.type_code = :lopDirectiveType and d2.status_code = :lopDirectiveStatus");
        qb.requiredCriteria("ds.student_id = :studentId", "studentId", student.getId());
        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_TUGI.name());
        qb.requiredCriteria("d.status_code = :directiveStatus", "directiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
        qb.filter("ds.canceled = false");
        qb.parameter("lopDirectiveType", DirectiveType.KASKKIRI_TUGILOPP.name());
        qb.parameter("lopDirectiveStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
        qb.groupBy("d.id, ds.id, d.confirm_date, sub.submitter, d2.id, coalesce (ds2.start_date, ds.end_date)");
        return qb;
    }
    
    private static JpaNativeQueryBuilder supportServicesQB(Student student, Map<String, Object> parameters, Pageable pageable, boolean showPrivate) {
        JpaNativeQueryBuilder qb = studentSupportServices(student);
        String supportServicesQuery = qb.querySql("sss.id as id, sss.entry_date as entry_date, sss.name_et as title,"
                + "sss.\"content\" as \"content\", sss.validity_code as validity_code, sss.is_public as is_public,"
                + "sss.ois_file_id as ois_file_id, sss.changed_by as submitter, false as artificial, null as ending_id", false);
        parameters.putAll(qb.queryParameters());

        qb = directiveSupportServices(student); 
        String directiveServicesQuery = qb.querySql("d.id as id, d.confirm_date as entry_date, null as title, "
                + "string_agg( ass.support_service_code, ';' order by ass.support_service_code) as \"content\", "
                + "case when coalesce ( ds2.start_date, ds.end_date) >= current_date then 'TUGIKEHTIV_K' else 'TUGIKEHTIV_L' end as validity_code, "
                + "false as is_public, null as ois_file_id, sub.submitter as submitter, true as artificial, d2.id as ending_id", false);
        parameters.putAll(qb.queryParameters());

        qb = new JpaNativeQueryBuilder("from (" + supportServicesQuery + " union all " + directiveServicesQuery + ") as support_services");
        if (pageable != null) {
            qb.sort(pageable);
        }
        if (!showPrivate) {
            qb.filter("is_public is true");
        }
        return qb;
    }

}
