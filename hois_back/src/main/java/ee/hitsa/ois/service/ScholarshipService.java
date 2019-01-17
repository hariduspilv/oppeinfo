package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Committee;
import ee.hitsa.ois.domain.CommitteeMember;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.scholarship.ScholarshipApplication;
import ee.hitsa.ois.domain.scholarship.ScholarshipApplicationFamily;
import ee.hitsa.ois.domain.scholarship.ScholarshipApplicationFile;
import ee.hitsa.ois.domain.scholarship.ScholarshipDecision;
import ee.hitsa.ois.domain.scholarship.ScholarshipDecisionCommitteeMember;
import ee.hitsa.ois.domain.scholarship.ScholarshipTerm;
import ee.hitsa.ois.domain.scholarship.ScholarshipTermCourse;
import ee.hitsa.ois.domain.scholarship.ScholarshipTermCurriculum;
import ee.hitsa.ois.domain.scholarship.ScholarshipTermStudyForm;
import ee.hitsa.ois.domain.scholarship.ScholarshipTermStudyLoad;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.Absence;
import ee.hitsa.ois.enums.CommitteeType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.Priority;
import ee.hitsa.ois.enums.ScholarshipStatus;
import ee.hitsa.ois.enums.ScholarshipType;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.ScholarshipUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshiApplicationRejectionForm;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipApplicationListSubmitForm;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipApplicationSearchCommand;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipCommitteeSearchCommand;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipDecisionForm;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipSearchCommand;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipStudentApplicationForm;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipTermForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ScholarshipTermApplicationSearchDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipApplicationDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipApplicationSearchDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipApplicationStudentDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipDecisionDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipStudentRejectionDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermApplicationDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermComplianceDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermSearchDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermStudentDto;

@Transactional
@Service
public class ScholarshipService {

    private static final int SAIS_POINTS_MONTHS = 6;
    private static final List<String> VALID_DIRECTIVE_STATUSES = EnumUtil.toNameList(
            DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL,
            DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL,
            DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);

    private static final String STUDENT_JOURNAL_ENTRIES = " from journal j"
            + " join journal_entry je on je.journal_id = j.id"
            + " join journal_student js on js.journal_id = j.id"
            + " left join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js.id"
            + " left join classifier grade on grade.code = jes.grade_code"
            + " where js.student_id = ?1 and je.entry_type_code in ?4";
    private static final String JOURNAL_MODULE_IN_STUDENT_RESULTS = "select svr2.grade_code from curriculum_version_omodule_theme cvot"
            + " join journal_omodule_theme jot on cvot.id = jot.curriculum_version_omodule_theme_id"
            + " join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id = cvo.id"
            + " join student_vocational_result svr2 on cvo.id = svr2.curriculum_version_omodule_id"
            + " join classifier grade2  on svr2.grade_code = grade2.code"
            + " where jot.journal_id = js.journal_id and svr2.student_id = ?1";

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private StudyYearService studyYearService;
    
    /**
     * Create scholarship term
     *
     * @param user
     * @param form
     * @return 
     */
    public ScholarshipTerm create(HoisUserDetails user, ScholarshipTermForm form) {
        ScholarshipTerm term = new ScholarshipTerm();
        term.setSchool(em.getReference(School.class, user.getSchoolId()));
        term.setStudyPeriod(em.getReference(StudyPeriod.class, form.getStudyPeriod()));
        return save(term, form);
    }

    public Page<ScholarshipTermSearchDto> list(HoisUserDetails user, ScholarshipSearchCommand command,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from scholarship_term st").sort(pageable);

        qb.requiredCriteria("st.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalContains("st.name_et", "nameEt", command.getNameEt());
        qb.optionalCriteria("st.type_code = :typeCode", "typeCode", command.getType());
        qb.optionalCriteria("st.study_period_id = :studyPeriod", "studyPeriod", command.getStudyPeriod());
        qb.optionalCriteria("st.type_code in (:typeCodes)", "typeCodes", command.getAllowedStipendTypes());
        qb.optionalCriteria("st.is_open = :isOpen", "isOpen", command.getIsOpen() == null ? null : Boolean.valueOf(command.getIsOpen().longValue() == 1L));

        String select = "st.id, st.name_et, st.type_code, st.application_start, st.application_end, st.places, st.is_open";
        return JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new ScholarshipTermSearchDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2),
                    resultAsLocalDate(r, 3), resultAsLocalDate(r, 4), resultAsLong(r, 5), resultAsBoolean(r, 6));
        });
    }

    public ScholarshipTermDto get(ScholarshipTerm term) {
        return ScholarshipTermDto.of(term);
    }

    public ScholarshipTerm save(ScholarshipTerm scholarshipTerm, ScholarshipTermForm form) {
        EntityUtil.bindToEntity(form, scholarshipTerm, classifierRepository, "curriculums", "studyLoads", "courses");
        scholarshipTerm.setStudyPeriod(em.getReference(StudyPeriod.class, form.getStudyPeriod()));
        Long committeeId = form.getCommittee();
        scholarshipTerm.setCommittee(committeeId != null ? em.getReference(Committee.class, committeeId) : null);
        bindFormArraysToEntity(form, scholarshipTerm);
        return EntityUtil.save(scholarshipTerm, em);
    }

    private ScholarshipTerm bindFormArraysToEntity(ScholarshipTermForm form, ScholarshipTerm scholarshipTerm) {
        if (form.getCourses() != null) {
            List<ScholarshipTermCourse> courses = scholarshipTerm.getScholarshipTermCourses();
            EntityUtil.bindEntityCollection(courses, c -> EntityUtil.getCode(c.getCourse()), form.getCourses(), c -> {
                ScholarshipTermCourse course = new ScholarshipTermCourse();
                course.setScholarshipTerm(scholarshipTerm);
                course.setCourse(
                        EntityUtil.validateClassifier(em.getReference(Classifier.class, c), MainClassCode.KURSUS));
                return course;
            });
        } else if (scholarshipTerm.getScholarshipTermCourses() != null) {
            scholarshipTerm.getScholarshipTermCourses().clear();
        }

        if (form.getCurriculums() != null) {
            List<ScholarshipTermCurriculum> curriculums = scholarshipTerm.getScholarshipTermCurriculums();
            EntityUtil.bindEntityCollection(curriculums, c -> EntityUtil.getId(c.getCurriculum()),
                    form.getCurriculums(), c -> c.getId(), c -> {
                        ScholarshipTermCurriculum curriculum = new ScholarshipTermCurriculum();
                        curriculum.setScholarshipTerm(scholarshipTerm);
                        curriculum.setCurriculum(em.getReference(Curriculum.class, c.getId()));
                        return curriculum;
                    });
        } else if (scholarshipTerm.getScholarshipTermCurriculums() != null) {
            scholarshipTerm.getScholarshipTermCurriculums().clear();
        }

        if (form.getStudyForms() != null) {
            List<ScholarshipTermStudyForm> studyForms = scholarshipTerm.getScholarshipTermStudyForms();
            EntityUtil.bindEntityCollection(studyForms, c -> EntityUtil.getCode(c.getStudyForm()), form.getStudyForms(),
                    c -> {
                        ScholarshipTermStudyForm studyForm = new ScholarshipTermStudyForm();
                        studyForm.setScholarshipTerm(scholarshipTerm);
                        studyForm.setStudyForm(EntityUtil.validateClassifier(em.getReference(Classifier.class, c),
                                MainClassCode.OPPEVORM));
                        return studyForm;
                    });
        } else if (scholarshipTerm.getScholarshipTermStudyForms() != null) {
            scholarshipTerm.getScholarshipTermStudyForms().clear();
        }

        if (form.getStudyLoads() != null) {
            List<ScholarshipTermStudyLoad> studyLoads = scholarshipTerm.getScholarshipTermStudyLoads();
            EntityUtil.bindEntityCollection(studyLoads, c -> EntityUtil.getCode(c.getStudyLoad()), form.getStudyLoads(),
                    c -> {
                        ScholarshipTermStudyLoad studyLoad = new ScholarshipTermStudyLoad();
                        studyLoad.setScholarshipTerm(scholarshipTerm);
                        studyLoad.setStudyLoad(EntityUtil.validateClassifier(em.getReference(Classifier.class, c),
                                MainClassCode.OPPEKOORMUS));
                        return studyLoad;
                    });
        } else if (scholarshipTerm.getScholarshipTermStudyLoads() != null) {
            scholarshipTerm.getScholarshipTermStudyLoads().clear();
        }

        return scholarshipTerm;
    }

    public ScholarshipTerm publish(ScholarshipTerm scholarshipTerm) {
        scholarshipTerm.setIsOpen(Boolean.TRUE);
        return EntityUtil.save(scholarshipTerm, em);
    }
    
    public List<AutocompleteResult> committeesForSelection(HoisUserDetails user, ScholarshipCommitteeSearchCommand command) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from committee c"
                + " left join committee_member cm on c.id = cm.committee_id"
                + " left join person p on p.id = cm.person_id ");

        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("c.type_code = :type", "type", CommitteeType.KOMISJON_T.name());
        if (command.getValidDate() != null && command.getId() != null) {
            qb.parameter("validDate", command.getValidDate());
            qb.parameter("id", command.getId());
            qb.filter("((c.valid_from <= :validDate and c.valid_thru >= :validDate) or c.id = :id)");
        } else {
            qb.optionalCriteria("c.valid_from <= :validDate", "validDate", command.getValidDate());
            qb.optionalCriteria("c.valid_thru >= :validDate", "validDate", command.getValidDate());
            qb.optionalCriteria("c.id = :id", "id", command.getId());
        }
        qb.optionalCriteria("(exists(select cc.id"
                    + " from committee_curriculum cc"
                    + " where cc.committee_id = c.id and cc.curriculum_id in :curriclums) "
                + " or not exists(select cc.id"
                    + " from committee_curriculum cc"
                    + " where cc.committee_id = c.id))", "curriclums", command.getCurriclumIds());
        qb.groupBy(" c.id ");

        List<?> committees = qb.select("distinct c.id,c.name_et,"
                + " array_to_string(array_agg("
                + " p.firstname || ' ' || p.lastname), ', ') as members", em).getResultList();

        return StreamUtil.toMappedList(r -> {
            String name = resultAsString(r, 1);
            String caption = (name != null ? name : "-") + " (" + resultAsString(r, 2) + ")";
            AutocompleteResult dto = new AutocompleteResult(resultAsLong(r, 0), caption, caption);
            return dto;
        }, committees);
    }
    
    public boolean canCreateDecision(List<Long> applicationIds) {
        List<?> result = em.createNativeQuery("select sa.id"
                + " from scholarship_application sa"
                + " join scholarship_term st on st.id = sa.scholarship_term_id"
                + " left join directive_student ds on ds.scholarship_application_id = sa.id"
                + " left join directive d on d.id = ds.directive_id"
                + " where sa.id in ?1 "
                + " and (sa.status_code not in ?2"
                    + " or (ds is not null and ds.canceled is not true)"
                    + " or (d is not null and d.status_code in ?3))")
                .setParameter(1, applicationIds)
                .setParameter(2, EnumUtil.toNameList(ScholarshipStatus.STIPTOETUS_STAATUS_A, ScholarshipStatus.STIPTOETUS_STAATUS_L))
                .setParameter(3, VALID_DIRECTIVE_STATUSES)
                .getResultList();
        return result.isEmpty();
    }
    
    public ScholarshipDecisionDto decision(HoisUserDetails user, List<Long> applicationIds) {
        ScholarshipDecisionDto dto = new ScholarshipDecisionDto();
        List<?> result = em.createNativeQuery("select distinct st.committee_id"
                + " from scholarship_application sa"
                + " join scholarship_term st on st.id = sa.scholarship_term_id"
                + " where sa.id in ?1 and st.committee_id is not null")
                .setParameter(1, applicationIds)
                .getResultList();
        if (result.size() == 1) {
            dto.setCommitteeId(resultAsLong(result.get(0), 0));
        }
        dto.setApplications(applicationsForCommand(null, user, applicationIds));
        return dto;
    }

    public ScholarshipDecisionDto decision(HoisUserDetails user, Long decisionId) {
        ScholarshipDecision decision = em.getReference(ScholarshipDecision.class, decisionId);
        Committee committee = decision.getCommittee();
        UserUtil.assertSameSchool(user, committee.getSchool());
        ScholarshipDecisionDto dto = new ScholarshipDecisionDto();
        dto.setId(EntityUtil.getId(decision));
        dto.setProtocolNr(decision.getProtocolNr());
        dto.setDecided(decision.getDecided());
        dto.setAddInfo(decision.getAddInfo());
        dto.setCommitteeId(EntityUtil.getId(committee));
        dto.setCommitteeName(committee.getNameEt());
        dto.setPresentCommitteeMembers(StreamUtil.toMappedList(m -> EntityUtil.getId(m.getCommitteeMember()), decision.getMembers()));
        dto.setApplications(applicationsForCommand(null, user, getDecisionApplicationIds(decisionId)));
        if (user.isTeacher()) {
            ScholarshipTerm scholarshipTerm = em.getReference(ScholarshipTerm.class, dto.getApplications().get(0).getTerm());
            Committee termCommittee = scholarshipTerm.getCommittee();
            UserUtil.throwAccessDeniedIf(termCommittee != null && !termCommittee.getMembers().stream()
                    .anyMatch(cm -> user.getPersonId().equals(EntityUtil.getId(cm.getPerson()))), 
                    "Teacher is not member of scholarship term committee");
        }
        return dto;
    }
    
    private List<Long> getDecisionApplicationIds(Long decisionId) {
        List<?> result = em.createNativeQuery("select sa.id"
                + " from scholarship_application sa"
                + " where sa.scholarship_decision_id = ?1")
                .setParameter(1, decisionId)
                .getResultList();
        return StreamUtil.toMappedList(r -> resultAsLong(r, 0), result);
    }

    private boolean canDeleteDecision(Long decisionId) {
        List<?> result = em.createNativeQuery("select sa.id"
                + " from scholarship_application sa"
                + " join directive_student ds on ds.scholarship_application_id = sa.id"
                + " join directive d on d.id = ds.directive_id"
                + " where sa.scholarship_decision_id = ?1 and ds.canceled = false"
                + " and d.status_code in ?2")
                .setParameter(1, decisionId)
                .setParameter(2, VALID_DIRECTIVE_STATUSES)
                .getResultList();
        return result.isEmpty();
    }
    
    public void deleteDecision(HoisUserDetails user, Long decisionId) {
        if (!canDeleteDecision(decisionId)) {
            throw new ValidationFailedException("stipend.messages.error.cannotDeleteDecision");
        }
        ScholarshipDecision decision = em.getReference(ScholarshipDecision.class, decisionId);
        Committee committee = decision.getCommittee();
        UserUtil.assertSameSchool(user, committee.getSchool());
        em.createNativeQuery("update scholarship_application set scholarship_decision_id = null"
                + " where scholarship_decision_id = ?1")
            .setParameter(1, decisionId)
            .executeUpdate();
        EntityUtil.deleteEntity(decision, em);
    }
    
    public void decide(HoisUserDetails user, ScholarshipDecisionForm form) {
        if (!canCreateDecision(form.getApplicationIds())) {
            throw new ValidationFailedException("stipend.messages.error.cannotCreateDecision");
        }
        List<Long> presentCommitteeMembers = form.getPresentCommitteeMembers();
        if (presentCommitteeMembers == null || presentCommitteeMembers.isEmpty()) {
            throw new ValidationFailedException("main.messages.error.atLeastOneMustBeSelected");
        }
        ScholarshipDecision decision = new ScholarshipDecision();
        decision.setProtocolNr(form.getProtocolNr());
        decision.setDecided(form.getDecided());
        decision.setAddInfo(form.getAddInfo());
        Committee committee = null;
        for (Long memberId : presentCommitteeMembers) {
            CommitteeMember committeeMember = em.getReference(CommitteeMember.class, memberId);
            ScholarshipDecisionCommitteeMember member = new ScholarshipDecisionCommitteeMember();
            member.setScholarshipDecision(decision);
            member.setCommitteeMember(committeeMember);
            decision.getMembers().add(member);
            if (committee == null) {
                committee = committeeMember.getCommittee();
            }
        }
        if (committee != null) {
            UserUtil.assertSameSchool(user, committee.getSchool());
            decision.setCommittee(committee);
            EntityUtil.save(decision, em);
        }
        for (Long applicationId : form.getApplicationIds()) {
            ScholarshipApplication application = em.getReference(ScholarshipApplication.class, applicationId);
            UserUtil.assertSameSchool(user, application.getScholarshipTerm().getSchool());
            application.setScholarshipDecision(decision);
            EntityUtil.save(application, em);
        }
    }

    public List<ScholarshipTermStudentDto> availableStipends(Long studentId) {
        return availableStipends(studentId, Boolean.FALSE);
    }

    public List<ScholarshipTermStudentDto> availableDrGrants(Long studentId) {
        return availableStipends(studentId, Boolean.TRUE);
    }
    
    public List<ScholarshipApplicationStudentDto> studentStipends(Long studentId) {
        return scholarshipApplicationStudentDtos(studentStipends(studentId, Boolean.FALSE));
    }

    public List<ScholarshipApplicationStudentDto> studentDrGrants(Long studentId) {
        return scholarshipApplicationStudentDtos(studentStipends(studentId, Boolean.TRUE));
    }

    private List<ScholarshipTermStudentDto> availableStipends(Long studentId, Boolean drGrants) {
        Student student = em.getReference(Student.class, studentId);
        TypedQuery<ScholarshipTerm> query = em.createQuery(
                "SELECT st FROM ScholarshipTerm st JOIN st.scholarshipTermCurriculums stc WHERE stc.curriculum.id = (?1) and"
                        + " st.isOpen = true and st.school.id = (?2)"
                        + " and ((st.applicationStart <= (?3) or st.applicationStart is null)"
                        + " and (st.applicationEnd >= (?3) or st.applicationEnd is null))"
                        + (Boolean.TRUE.equals(drGrants) ? " and st.type.code = ?4" : 
                            (Boolean.FALSE.equals(drGrants) ? " and st.type.code != ?4" : "")), 
                        ScholarshipTerm.class)
                .setParameter(1, EntityUtil.getId(student.getCurriculumVersion().getCurriculum()))
                .setParameter(2, EntityUtil.getId(student.getSchool()))
                .setParameter(3, LocalDate.now());
        if (drGrants != null) {
            query = query.setParameter(4, ScholarshipType.STIPTOETUS_DOKTOR.name());
        }
        List<ScholarshipTerm> result = query.getResultList();
        List<ScholarshipTermStudentDto> availableStipends = StreamUtil
                .toMappedList(st -> ScholarshipTermStudentDto.of(st), result);
        Map<Long, ScholarshipTermComplianceDto> termCompliances = StreamUtil.toMap(r -> r.getId(),
                r -> studentCompliesTerm(student, r), result);

        for (ScholarshipTermStudentDto stipend : availableStipends) {
            stipend.setTermCompliance(termCompliances.get(stipend.getId()));
        }

        return availableStipends;
    }

    private List<ScholarshipApplication> studentStipends(Long studentId, Boolean drGrants) {
        TypedQuery<ScholarshipApplication> query = em.createQuery("SELECT sa FROM ScholarshipApplication sa"
                + " WHERE sa.student.id = (?1)"
                + (Boolean.TRUE.equals(drGrants) ? " and sa.scholarshipTerm.type.code = ?2" : 
                    (Boolean.FALSE.equals(drGrants) ? " and sa.scholarshipTerm.type.code != ?2" : "")),
                ScholarshipApplication.class)
                .setParameter(1, studentId);
        if (drGrants != null) {
            query = query.setParameter(2, ScholarshipType.STIPTOETUS_DOKTOR.name());
        }
        return query.getResultList();
    }

    private static List<ScholarshipApplicationStudentDto> scholarshipApplicationStudentDtos(List<ScholarshipApplication> stipends) {
        return StreamUtil.toMappedList(sa -> {
            ScholarshipApplicationStudentDto dto = new ScholarshipApplicationStudentDto();
            dto.setId(EntityUtil.getId(sa));
            ScholarshipTerm term = sa.getScholarshipTerm();
            dto.setTermName(term.getNameEt());
            dto.setTermId(EntityUtil.getId(term));
            dto.setType(EntityUtil.getCode(term.getType()));
            dto.setAverageMark(sa.getAverageMark());
            dto.setLastPeriodMark(sa.getLastPeriodMark());
            dto.setCurriculumCompletion(sa.getCurriculumCompletion());
            dto.setAbsences(sa.getAbsences());
            dto.setInserted(sa.getInserted());
            dto.setStatus(EntityUtil.getCode(sa.getStatus()));
            dto.setDecisionDate(sa.getDecisionDate());
            dto.setRejectComment(sa.getRejectComment());
            dto.setIsTeacherConfirm(sa.getIsTeacherConfirmed());
            dto.setNeedsConfirm(sa.getScholarshipTerm().getIsTeacherConfirm());
            dto.setCanApply(Boolean.valueOf(ScholarshipUtil.canApply(sa.getScholarshipTerm(), sa)));
            return dto;
        }, stipends);
    }

    public Map<String, Object> getStudentApplicationView(HoisUserDetails user, ScholarshipTerm term) {
        Map<String, Object> result = new HashMap<>();
        result.put("stipend", ScholarshipTermApplicationDto.of(term));
        result.put("application", getStudentApplicationDto(user, term));
        result.put("termCompliance", studentCompliesTerm(em.getReference(Student.class, user.getStudentId()), term));
        return result;
    }

    public ScholarshipApplicationDto getStudentApplicationDto(HoisUserDetails user, ScholarshipTerm term) {
        Student student = em.getReference(Student.class, user.getStudentId());
        ScholarshipApplication application = getApplicationForTermAndStudent(term, student);
        ScholarshipApplicationDto applicationDto = application == null ? 
                getApplicationDto(student, term) : getApplicationDto(application);
        applicationDto.setCanApply(Boolean.valueOf(ScholarshipUtil.canApply(term, application)));
        applicationDto.setAddress(student.getPerson().getAddress());
        
        StudentResults results = getStudentResults(term, student);
        BigDecimal credits = results.getCredits();
        applicationDto.setCredits(credits);
        applicationDto.setAverageMark(results.getAverageMark());
        applicationDto.setLastPeriodMark(results.getLastPeriodMark());
        applicationDto.setCurriculumCompletion(results.getCurriculumCompletion());
        applicationDto.setIsStudyBacklog(results.getIsStudyBacklog());
        applicationDto.setAbsences(results.getAbsences());
        if (application == null) {
            applicationDto.setPhone(student.getPerson().getPhone());
            applicationDto.setEmail(student.getEmail());
        }
        List<ScholarshipApplication> prevApplications = studentStipends(user.getStudentId(), null);
        if (!prevApplications.isEmpty()) {
            prevApplications = prevApplications.stream()
                    .filter(a -> a.getBankAccount() != null)
                    .sorted(Comparator.comparing(a -> a.getInserted(), Comparator.reverseOrder()))
                    .collect(Collectors.toList());
            if (!prevApplications.isEmpty()) {
                applicationDto.setBankAccount(prevApplications.get(0).getBankAccount());
            }
        }
        return applicationDto;
    }

    public Map<String, Object> getApplicationView(HoisUserDetails user, ScholarshipApplication application) {
        Student student = application.getStudent();
        if (user.isStudent()) {
            UserUtil.throwAccessDeniedIf(!user.getStudentId().equals(EntityUtil.getId(student)), 
                    "Student can only view his or her own application");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("stipend", ScholarshipTermApplicationDto.of(application.getScholarshipTerm()));
        result.put("application", getApplicationDto(application));
        return result;
    }

    public ScholarshipApplication saveApplication(HoisUserDetails user, ScholarshipTerm term,
            ScholarshipStudentApplicationForm form) {
        Student student = em.getReference(Student.class, user.getStudentId());
        ScholarshipApplication application = getApplicationForTermAndStudent(term, student);
        if (application != null) {
            return application;
        }
        if(!studentCompliesTerm(student, term).getFullyComplies()) {
            return null;
        }
        application = new ScholarshipApplication();
        application.setScholarshipTerm(term);
        application.setStatus(em.getReference(Classifier.class, ScholarshipStatus.STIPTOETUS_STAATUS_K.name()));
        application.setStudent(student);
        application.setStudentGroup(student.getStudentGroup());
        refreshCompletionWithApplication(application);
        refreshAddressWithApplication(application);
        application.setCurriculumVersion(student.getCurriculumVersion());
        application = bindApplicationFormToApplication(application, form);
        return EntityUtil.save(application, em);
    }

    private void refreshCompletionWithApplication(ScholarshipApplication application) {
        ScholarshipTerm term = application.getScholarshipTerm();
        Student student = application.getStudent();
        StudentResults results = getStudentResults(term, student);

        application.setCredits(results.getCredits());
        application.setCurriculumCompletion(
                term.getCurriculumCompletion() == null ? null : results.getCurriculumCompletion());
        application.setAverageMark(term.getAverageMark() == null ? null : 
            useSaisPoints(term, student) ? getSaisPoints(student) : results.getAverageMark());
        application.setLastPeriodMark(term.getLastPeriodMark() == null ? null : results.getLastPeriodMark());
        application.setAbsences(term.getMaxAbsences() == null ? null : results.getAbsences());
    }

    private StudentResults getHigherResults(Student student) {
        StudentResults results = new StudentResults();
        BigDecimal credits = getCredits(student);
        results.setCredits(credits);
        Long schoolId = EntityUtil.getId(student.getSchool());
        results.setAverageMark(getAverageGrade(student, studyYearService.getCurrentStudyPeriod(schoolId), credits));
        results.setLastPeriodMark(getAverageGrade(student, studyYearService.getPreviousStudyPeriod(schoolId), credits));
        results.setCurriculumCompletion(StudentUtil.getCurriculumCompletion(credits, student));
        return results;
    }

    private StudentResults getVocationalResults(ScholarshipTerm term, Student student) {
        StudentResults results = new StudentResults();
        results.setCredits(BigDecimal.ZERO);
        
        List<String> lastPeriodGrades = getLastPeriodVocationalStudentGrades(term, student);
        if (ScholarshipType.STIPTOETUS_POHI.name().equals(EntityUtil.getNullableCode(term.getType()))
                || ScholarshipType.STIPTOETUS_ERI.name().equals(EntityUtil.getNullableCode(term.getType()))) {
            results.setAverageMark(getCurrentPeriodAverageGrade(term, student));
            results.setLastPeriodMark(getAverageGrade(lastPeriodGrades));
            results.setCurriculumCompletion(getVocationalCurriculumCompletion(lastPeriodGrades));
        }
        if (ScholarshipType.STIPTOETUS_POHI.name().equals(EntityUtil.getNullableCode(term.getType()))) {
            results.setAbsences(getAbsences(term, student));
        }
        results.setIsStudyBacklog(getIsStudyBacklog(lastPeriodGrades));
        return results;
    }

    private StudentResults getStudentResults(ScholarshipTerm term, Student student) {
        Classifier termType = term.getType();
        if (termType.isHigher()) {
            return getHigherResults(student);
        } else if (termType.isVocational()) {
            return getVocationalResults(term, student);
        }
        throw new AssertionFailedException("Scholarship term type must be higher or vocational");
    }

    private BigDecimal getCurrentPeriodAverageGrade(ScholarshipTerm term, Student student) {
        AverageGradeParams params = new AverageGradeParams();
        params.setIsOutcomes(term.getIsOutcomes());
        params.setIsPeriodGrade(term.getIsPeriodGrade());
        params.setIsJournalFinalGrade(term.getIsJournalFinalGrade());
        params.setIsModuleGrade(term.getIsModuleGrade());
        params.setIsApelGrade(term.getIsApelGrade());
        params.setIsJournalGrade(term.getIsJournalGrade());
        
        List<String> studentResults = vocationalStudentGrades(student, params);
        return getAverageGrade(studentResults);
    }

    private List<String> getLastPeriodVocationalStudentGrades(ScholarshipTerm term, Student student) {
        AverageGradeParams params = new AverageGradeParams();
        params.setPeriodStart(term.getLastPeriodGradeFrom());
        params.setPeriodEnd(term.getLastPeriodGradeThru());
        params.setIsOutcomes(term.getIsLastPeriodOutcomes());
        params.setIsPeriodGrade(term.getIsLastPeriodPeriodGrade());
        params.setIsJournalFinalGrade(term.getIsLastPeriodJournalFinalGrade());
        params.setIsModuleGrade(term.getIsLastPeriodModuleGrade());
        params.setIsApelGrade(term.getIsLastPeriodApelGrade());
        params.setIsJournalGrade(term.getIsLastPeriodJournalGrade());
        return vocationalStudentGrades(student, params);
    }

    private static BigDecimal getAverageGrade(List<String> studentGrades) {
        List<String> distinctiveResults = StreamUtil.toFilteredList(r -> OccupationalGrade.isDistinctive(r),
                studentGrades);
        int gradeSum = 0;
        for (String grade : distinctiveResults) {
            gradeSum += OccupationalGrade.getGradeMark(grade);
        }
        BigDecimal averageGrade = gradeSum > 0 && !distinctiveResults.isEmpty()
                ? BigDecimal.valueOf((double) gradeSum / distinctiveResults.size())
                : null;
        return averageGrade != null ? averageGrade.setScale(2, RoundingMode.DOWN) : null;
    }

    private static BigDecimal getVocationalCurriculumCompletion(List<String> studentGrades) {
        List<String> positiveResults = StreamUtil.toFilteredList(r -> OccupationalGrade.isPositive(r), studentGrades);
        Double curriculumCompletion = !studentGrades.isEmpty()
                ? Double.valueOf((double) positiveResults.size() / studentGrades.size() * 100)
                : Double.valueOf(100);
        return BigDecimal.valueOf(curriculumCompletion.doubleValue()).setScale(1, RoundingMode.DOWN);
    }

    private static Boolean getIsStudyBacklog(List<String> studentGrades) {
        List<String> positiveResults = StreamUtil.toFilteredList(r -> OccupationalGrade.isPositive(r), studentGrades);
        return Boolean.valueOf(studentGrades.isEmpty() ? false : positiveResults.size() != studentGrades.size());
    }

    private List<String> vocationalStudentGrades(Student student, AverageGradeParams params) {
        String sql = "";
        boolean addUnion = false;
        boolean periodStartExists = params.getPeriodStart() != null;
        boolean periodEndExists = params.getPeriodEnd() != null;
        boolean isModuleGrade = Boolean.TRUE.equals(params.getIsModuleGrade()); 
        boolean isApelGrade = Boolean.TRUE.equals(params.getIsApelGrade());
        List<String> entryTypes = entryTypes(params);

        if (!(isModuleGrade || isApelGrade || !entryTypes.isEmpty())) {
            return new ArrayList<>();
        }

        if (isModuleGrade) {
            sql += "select grade.code as grade_code"
                    + " from student_vocational_result svr"
                    + " join classifier grade on grade.code = grade_code"
                    + " where svr.student_id = ?1 and svr.apel_application_record_id is null";
            if (periodStartExists || periodEndExists) {
                sql += " and coalesce(svr.grade_date, svr.inserted)"
                        + periodComparisonCondition(periodStartExists, periodEndExists);
            }
            addUnion = true;
        }

        if (isApelGrade) {
            if (addUnion) {
                sql += " union all ";
            }
            sql += "select grade.code as grade_code"
                    + " from student_vocational_result svr"
                    + " join classifier grade on grade.code = grade_code"
                    + " where svr.student_id = ?1 and svr.apel_application_record_id is not null";
            if (periodStartExists || periodEndExists) {
                sql += " and coalesce(svr.grade_date, svr.inserted)"
                        + periodComparisonCondition(periodStartExists, periodEndExists);
            }
            addUnion = true;
        }

        if (!entryTypes.isEmpty()) {
            if (addUnion) {
                sql += " union all ";
            }
            String periodDateCondition = periodStartExists || periodEndExists
                    ? " and je.entry_date" + periodComparisonCondition(periodStartExists, periodEndExists) : "";
            String moduleResultCondition = isModuleGrade
                    ? " and not exists (" + JOURNAL_MODULE_IN_STUDENT_RESULTS
                            + " and svr2.apel_application_record_id is null)" : "";
            String apelResultCondition = isApelGrade
                    ? " and not exists (" + JOURNAL_MODULE_IN_STUDENT_RESULTS
                            + " and svr2.apel_application_record_id is not null and je.entry_type_code = '"
                            + JournalEntryType.SISSEKANNE_L.name() + "')" : "";

            sql += " select g.grade_code as grade_code"
                    + " from (select js.journal_id, jes.grade_inserted, grade.code as grade_code"
                    + STUDENT_JOURNAL_ENTRIES + periodDateCondition + moduleResultCondition + apelResultCondition; 
            sql += ") g";
            addUnion = true;
        }

        Query query = em.createNativeQuery(sql).setParameter(1, EntityUtil.getId(student));
        if (params.getPeriodStart() != null) {
            query.setParameter(2, JpaQueryUtil.parameterAsTimestamp(params.getPeriodStart()));
        }
        if (params.getPeriodEnd() != null) {
            query.setParameter(3, JpaQueryUtil.parameterAsTimestamp(params.getPeriodEnd()));
        }
        if (!entryTypes.isEmpty()) {
            query.setParameter(4, entryTypes);
        }
        List<?> data = query.getResultList();
        return StreamUtil.toMappedList(r -> resultAsString(r, 0), data);
    }

    private static List<String> entryTypes(AverageGradeParams params) {
        List<String> entryTypes = new ArrayList<>();
        if (Boolean.TRUE.equals(params.getIsOutcomes())) {
            entryTypes.add(JournalEntryType.SISSEKANNE_O.name());
        }
        if (Boolean.TRUE.equals(params.getIsPeriodGrade())) {
            entryTypes.add(JournalEntryType.SISSEKANNE_R.name());
        }
        if (Boolean.TRUE.equals(params.getIsJournalFinalGrade())) {
            entryTypes.add(JournalEntryType.SISSEKANNE_L.name());
        }
        if (Boolean.TRUE.equals(params.getIsJournalGrade())) {
            entryTypes.add(JournalEntryType.SISSEKANNE_H.name());
        }
        return entryTypes;
    }

    private static String periodComparisonCondition(boolean periodStartExists, boolean periodEndExists) {
        String condition = "";
        if (periodStartExists && periodEndExists) {
            condition = " between ?2 and ?3";
        } else if (periodStartExists) {
            condition = " >= ?2";
        } else if (periodEndExists) {
            condition = " <= ?3";
        }
        return condition;
    }

    private BigDecimal getAverageGrade(Student student, Long studyPeriodId, BigDecimal credits) {
        Number result = (Number) em.createNativeQuery("select sum(grade_mark * credits) as weighted_grade_sum"
                + " from student_higher_result shr"
                + " where shr.student_id = ?1 and shr.study_period_id = ?2")
                .setParameter(1, EntityUtil.getId(student))
                .setParameter(2, studyPeriodId)
                .getSingleResult();
        return result == null ? null : BigDecimal.valueOf(result.doubleValue()).divide(credits, 2, RoundingMode.DOWN);
    }

    private BigDecimal getCredits(Student student) {
        Number result = (Number) em.createNativeQuery("select sum(credits) as total_credits"
                + " from student_higher_result shr"
                + " where shr.student_id = ?1")
                .setParameter(1, EntityUtil.getId(student))
                .getSingleResult();
        return result == null ? BigDecimal.ZERO : BigDecimal.valueOf(result.doubleValue());
    }

    private static boolean useSaisPoints(ScholarshipTerm term, Student student) {
        return term.getType().isHigher() && student.getStudyStart() != null && 
                LocalDate.now().minusMonths(SAIS_POINTS_MONTHS).isBefore(student.getStudyStart());
    }
    
    private BigDecimal getSaisPoints(Student student) {
        List<?> result = em.createNativeQuery("select sa.points"
                + " from directive_student ds"
                + " join sais_application sa on sa.id = ds.sais_application_id"
                + " where ds.canceled = false and ds.student_id = ?1")
                .setParameter(1, EntityUtil.getId(student))
                .setMaxResults(1)
                .getResultList();
        return result.isEmpty() ? null : BigDecimal.valueOf(((Number) result.get(0)).doubleValue());
    }

    // TODO: Absences uses same dates as last period grade. Change variable name?
    private Long getAbsences(ScholarshipTerm term, Student student) {
        boolean periodStartExists = term.getLastPeriodGradeFrom() != null;
        boolean periodEndExists = term.getLastPeriodGradeThru() != null;

        String entryAbsences = "select sum(coalesce(je.lessons, 1)) as lessons from journal_entry_student jes"
                + " join journal_student js on js.id = jes.journal_student_id"
                + " join journal_entry je on je.id = jes.journal_entry_id"
                + " where js.student_id = ?1 and jes.absence_code = ?4";

        String lessonAbsences = "select sum(1) as lesons from journal_entry_student jes"
                + " join journal_student js on js.id = jes.journal_student_id"
                + " join journal_entry_student_lesson_absence jesla on jesla.journal_entry_student_id = jes.id"
                + " join journal_entry je on je.id = jes.journal_entry_id"
                + " where js.student_id = ?1 and jesla.absence_code = ?4";

        if (periodStartExists || periodEndExists) {
            entryAbsences += " and je.entry_date" + periodComparisonCondition(periodStartExists, periodEndExists);
            lessonAbsences += " and je.entry_date" + periodComparisonCondition(periodStartExists, periodEndExists);
        }

        String absenceSql = "select sum(lessons) as total_lessons from (" + entryAbsences + " union all "
                + lessonAbsences + ") a";

        Query query = em.createNativeQuery(absenceSql)
                .setParameter(1, EntityUtil.getId(student))
                .setParameter(4, Absence.PUUDUMINE_P.name());

        if (periodStartExists) {
            query.setParameter(2, JpaQueryUtil.parameterAsTimestamp(term.getLastPeriodGradeFrom()));
        }
        if (periodEndExists) {
            query.setParameter(3, JpaQueryUtil.parameterAsTimestamp(term.getLastPeriodGradeThru()));
        }

        Number result = (Number) query.getSingleResult();
        return result == null ? Long.valueOf(0) : Long.valueOf(result.longValue());
    }

    private static void refreshAddressWithApplication(ScholarshipApplication application) {
        Person person = application.getStudent().getPerson();
        application.setAddress(person.getAddress());
        application.setAddressAds(person.getAddressAds());
        application.setAddressAdsOid(person.getAddressAdsOid());
    }

    public ScholarshipApplication updateApplication(HoisUserDetails user, ScholarshipStudentApplicationForm form,
            ScholarshipApplication application) {
        if (application == null || !EntityUtil.getId(application.getStudent()).equals(user.getStudentId())) {
            return null;
        }
        if (!ClassifierUtil.oneOf(application.getStatus(), ScholarshipStatus.STIPTOETUS_STAATUS_K, ScholarshipStatus.STIPTOETUS_STAATUS_T)) {
            return application;
        }
        refreshCompletionWithApplication(application);
        refreshAddressWithApplication(application);
        bindApplicationFormToApplication(application, form);
        return EntityUtil.save(application, em);
    }

    private ScholarshipApplication bindApplicationFormToApplication(ScholarshipApplication application,
            ScholarshipStudentApplicationForm form) {
        EntityUtil.bindToEntity(form, application, classifierRepository, "files", "family");
        if (form.getFiles() != null) {
            List<ScholarshipApplicationFile> files = application.getScholarshipApplicationFiles();
            EntityUtil.bindEntityCollection(files, EntityUtil::getId, form.getFiles(), f -> f.getId(), f -> {
                ScholarshipApplicationFile file = new ScholarshipApplicationFile();
                file.setScholarshipApplication(application);
                file.setOisFile(EntityUtil.bindToEntity(f, new OisFile()));
                EntityUtil.save(file.getOisFile(), em);
                return file;
            });
        } else {
            if (application.getScholarshipApplicationFiles() != null) {
                application.getScholarshipApplicationFiles().clear();
            }
        }
        List<ScholarshipApplicationFamily> families = application.getScholarshipApplicationFamilies();
        EntityUtil.bindEntityCollection(families, EntityUtil::getId, form.getFamily(), f -> f.getId(), f -> {
            ScholarshipApplicationFamily fam = new ScholarshipApplicationFamily();
            fam.setScholarshipApplication(application);
            return EntityUtil.bindToEntity(f, fam);
        });
        return application;
    }

    private ScholarshipApplication getApplicationForTermAndStudent(ScholarshipTerm term, Student student) {
        List<ScholarshipApplication> result = em.createQuery(
                "SELECT sa FROM ScholarshipApplication sa WHERE sa.scholarshipTerm.id = (?1) AND sa.student.id = (?2)",
                ScholarshipApplication.class).setParameter(1, EntityUtil.getId(term))
                .setParameter(2, EntityUtil.getId(student)).setMaxResults(1).getResultList();

        return result.isEmpty() ? null : result.get(0);
    }

    private ScholarshipApplicationDto getApplicationDto(Student student, ScholarshipTerm term) {
        ScholarshipApplicationDto dto = new ScholarshipApplicationDto();
        dto.setFiles(new ArrayList<>());
        dto.setCanApply(Boolean.TRUE);
        dto.setUseSaisPoints(Boolean.valueOf(useSaisPoints(term, student)));
        if (Boolean.TRUE.equals(dto.getUseSaisPoints())) {
            dto.setSaisPoints(getSaisPoints(student));
        }
        return dto;
    }

    public ScholarshipApplicationDto getApplicationDto(ScholarshipApplication application) {
        ScholarshipApplicationDto dto = ScholarshipApplicationDto.of(application);
        Student student = application.getStudent();
        dto.setUseSaisPoints(Boolean.valueOf(useSaisPoints(application.getScholarshipTerm(), student)));
        if (Boolean.TRUE.equals(dto.getUseSaisPoints())) {
            dto.setSaisPoints(getSaisPoints(student));
        }
        return dto;
    }

    public ScholarshipApplication apply(HoisUserDetails user, ScholarshipApplication application) {
        if (user.getStudentId().equals(EntityUtil.getId(application.getStudent()))
                && studentCompliesTerm(application.getStudent(), application.getScholarshipTerm()).getFullyComplies()) {
            application.setStatus(em.getReference(Classifier.class, ScholarshipStatus.STIPTOETUS_STAATUS_E.name()));
            return EntityUtil.save(application, em);
        }
        throw new ValidationFailedException("stipend.messages.error.studentDoesntComply");
    }

    public ScholarshipTermApplicationSearchDto applications(ScholarshipApplicationSearchCommand command,
            HoisUserDetails user) {
        List<ScholarshipApplicationSearchDto> applications = applicationsForCommand(command, user, null);
        
        ScholarshipTerm term;
        if(!applications.isEmpty()) {
            term = em.getReference(ScholarshipTerm.class, applications.get(0).getTerm());
        } else {
            return null;
        }
        Collections.sort(applications, comparatorForTerm(term));
        
        return new ScholarshipTermApplicationSearchDto(term.getPlaces(), applications);
    }

    private List<ScholarshipApplicationSearchDto> applicationsForCommand(ScholarshipApplicationSearchCommand command, 
            HoisUserDetails user, List<Long> applicationIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from scholarship_application sa" 
                        + " join scholarship_term st on st.id = sa.scholarship_term_id"
                        + " join student s on s.id = sa.student_id" 
                        + " join person p on p.id = s.person_id"
                        + " join student_group sg on sg.id = sa.student_group_id"
                        + " join curriculum_version cv on sa.curriculum_version_id = cv.id"
                        + " join curriculum c on c.id = cv.curriculum_id"
                        + " left join scholarship_decision sd on sd.id = sa.scholarship_decision_id");

        qb.requiredCriteria("st.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isTeacher()) {
            qb.requiredCriteria("(sa.student_group_id in (select sg.id from student_group sg"
                    + " where sg.teacher_id = :teacherId)"
                    + " or st.committee_id in (select c.id"
                    + " from committee_member cm"
                    + " join committee c on c.id = cm.committee_id"
                    + " join teacher t on t.person_id = cm.person_id"
                    + " where t.id = :teacherId and c.type_code = '" + CommitteeType.KOMISJON_T.name() + "')"
                    + ")", "teacherId", user.getTeacherId());
        }
        
        if (command != null) {
            qb.optionalContains("st.name_et", "nameEt", command.getNameEt());
            qb.optionalCriteria("st.type_code = :typeCode", "typeCode", command.getType());
            qb.optionalCriteria("sa.status_code = :status", "status", command.getStatus());
            qb.optionalCriteria("st.study_period_id = :studyPeriod", "studyPeriod", command.getStudyPeriod());
            qb.optionalCriteria("sa.scholarship_term_id in (select scholarship_term_id from scholarship_term_course where " 
                    + "course_code in (:courseCodes))", "courseCodes", command.getCourses());
            qb.optionalCriteria("c.id in (:curriculumIds)", "curriculumIds", command.getCurriculum());
            /*qb.optionalCriteria("sa.scholarship_term_id in (select scholarship_term_id from scholarship_term_curriculum where "
                + "curriculum_id in (:curriculumIds))", "curriculumIds", command.getCurriculum());*/
            qb.optionalContains(Arrays.asList("sg.code"), "studentGroup", command.getStudentGroup());
            qb.optionalContains(Arrays.asList("p.firstname", "p.lastname"), "personName", command.getStudentName());
        }
        if (applicationIds != null) {
            qb.requiredCriteria("sa.id in :applicationIds", "applicationIds", applicationIds);
        }

        qb.requiredCriteria("sa.status_code != :compositionStatus", "compositionStatus",
                ScholarshipStatus.STIPTOETUS_STAATUS_K);

        String directiveStatuses = VALID_DIRECTIVE_STATUSES.stream()
                .map(s -> "'" + s + "'").collect(Collectors.joining(", "));
        String select = "sa.id as application_id, st.type_code, st.id as term_id, st.name_et, c.code, s.id as student_id"
                + ", p.firstname, p.lastname, p.idcode, sa.average_mark, sa.last_period_mark , sa.curriculum_completion"
                + ", sa.is_teacher_confirmed, sa.status_code, sa.compensation_reason_code, sa.compensation_frequency_code"
                + ", sa.credits, sa.absences, sa.reject_comment, st.is_teacher_confirm"
                + ", (select case when s.study_start > date(now()) - interval '" + SAIS_POINTS_MONTHS + " months'"
                    + " then sais.points else null end"
                    + " from directive_student ds"
                    + " join sais_application sais on sais.id = ds.sais_application_id"
                    + " where ds.canceled = false and ds.student_id = s.id) as sais_points"
                + ", (select exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id"
                    + " where ds.scholarship_application_id = sa.id and ds.canceled = false"
                    + " and d.status_code in (" + directiveStatuses + "))) as has_directive"
                + ", sd.id, sd.decided, sg.code as student_group_code";
        List<?> data = qb.select(select, em).getResultList();
        return StreamUtil.toMappedList(r -> {
        	ScholarshipApplicationSearchDto dto = new ScholarshipApplicationSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setType(resultAsString(r, 1));
            dto.setTerm(resultAsLong(r, 2));
            dto.setTermNameEt(resultAsString(r, 3));
            dto.setCurriculumCode(resultAsString(r, 4));
            dto.setStudentId(resultAsLong(r, 5));
            dto.setStudentName(PersonUtil.fullname(resultAsString(r, 6), resultAsString(r, 7)));
            dto.setFirstName(resultAsString(r, 6));
            dto.setLastName(resultAsString(r, 7));
            dto.setIdcode(resultAsString(r, 8));
            dto.setAverageMark(resultAsDecimal(r, 9));
            dto.setLastPeriodMark(resultAsDecimal(r, 10));
            dto.setCurriculumCompletion(resultAsDecimal(r, 11));
            dto.setIsTeacherConfirm(resultAsBoolean(r, 12));
            dto.setStatus(resultAsString(r, 13));
            dto.setCompensationReason(resultAsString(r, 14));
            dto.setCompensationFrequency(resultAsString(r, 15));
            dto.setCredits(resultAsDecimal(r, 16));
            dto.setAbsences(resultAsLong(r, 17));
            dto.setRejectComment(resultAsString(r, 18));
            dto.setNeedsConfirm(resultAsBoolean(r, 19));
            dto.setSaisPoints(resultAsDecimal(r, 20));
            dto.setHasDirective(resultAsBoolean(r, 21));
            dto.setDecisionId(resultAsLong(r, 22));
            dto.setDecisionDecided(resultAsLocalDate(r, 23));
            dto.setStudentGroup(resultAsString(r, 24));
            return dto;
        }, data);
    }

    public HttpStatus acceptApplications(HoisUserDetails user, List<Long> applicationIds) {
        List<ScholarshipApplication> result = getApplications(applicationIds);
        result.removeIf(a -> !UserUtil.isSameSchool(user, a.getScholarshipTerm().getSchool()));
        updateApplicationStatuses(result, ScholarshipStatus.STIPTOETUS_STAATUS_A);
        return HttpStatus.OK;
    }

    public HttpStatus annulApplications(ScholarshipApplicationListSubmitForm form, HoisUserDetails user) {
        List<ScholarshipApplication> result = getApplications(form);
        result = setRejectionComments(form, result);
        result.removeIf(a -> !UserUtil.isSameSchool(user, a.getScholarshipTerm().getSchool()));
        assertCanAnnulAndReject(StreamUtil.toMappedList(r -> EntityUtil.getId(r), result));
        updateApplicationStatuses(result, ScholarshipStatus.STIPTOETUS_STAATUS_T);
        return HttpStatus.OK;
    }

    public HttpStatus rejectApplications(ScholarshipApplicationListSubmitForm form, HoisUserDetails user) {
        List<ScholarshipApplication> result = getApplications(form);
        result = setRejectionComments(form, result);
        result.removeIf(a -> !UserUtil.isSameSchool(user, a.getScholarshipTerm().getSchool()));
        assertCanAnnulAndReject(StreamUtil.toMappedList(r -> EntityUtil.getId(r), result));
        updateApplicationStatuses(result, ScholarshipStatus.STIPTOETUS_STAATUS_L);
        return HttpStatus.OK;
    }

    private void assertCanAnnulAndReject(List<Long> applicationIds) {
        List<Long> result = applicationsConnectedToDirectives(applicationIds);
        if (!result.isEmpty()) {
            throw new ValidationFailedException("stipend.messages.error.connectedDirectives");
        }
    }

    public HttpStatus refreshResults(HoisUserDetails user, List<Long> applicationIds) {
        removeApplicationsWithDirectives(applicationIds);
        List<ScholarshipApplication> applications = getApplications(applicationIds);
        checkAccess(user, applications);
        for (ScholarshipApplication application : applications) {
            refreshCompletionWithApplication(application);
            EntityUtil.save(application, em);
        }
        return HttpStatus.OK;
    }

    public ScholarshipTermComplianceDto studentTermCompliance(ScholarshipApplication application) {
        return studentCompliesTerm(application.getStudent(), application.getScholarshipTerm());
    }

    public Map<Long, ScholarshipTermComplianceDto> checkComplies(HoisUserDetails user, List<Long> applicationIds) {
        removeApplicationsWithDirectives(applicationIds);
        List<ScholarshipApplication> applications = getApplications(applicationIds);
        checkAccess(user, applications);
        Map<Long, ScholarshipTermComplianceDto> result = new HashMap<>();
        for (ScholarshipApplication application : applications) {
            result.put(EntityUtil.getId(application), 
                    studentCompliesTerm(application.getStudent(), application.getScholarshipTerm()));
        }
        return result;
    }

    private void removeApplicationsWithDirectives(List<Long> applicationIds) {
        List<Long> applicationsWithDirectives = applicationsConnectedToDirectives(applicationIds);
        applicationIds.removeIf(a -> applicationsWithDirectives.contains(a));

        if (applicationIds.isEmpty()) {
            throw new ValidationFailedException("stipend.messages.error.noApplicationsWithoutDirectives");
        }
    }

    private List<Long> applicationsConnectedToDirectives(List<Long> applicationIds) {
        List<?> result = em.createNativeQuery("select sa.id"
                + " from scholarship_application sa"
                + " join directive_student ds on ds.scholarship_application_id = sa.id"
                + " where sa.id in (?1)")
                .setParameter(1, applicationIds)
                .getResultList();
        
        return StreamUtil.toMappedList(r -> resultAsLong(r, 0), result);
    }

    public HttpStatus teacherConfirmApplications(HoisUserDetails user, List<Long> applicationIds, Boolean isTeacherConfirmed) {
        List<ScholarshipApplication> applications = getApplications(applicationIds);
        checkTeacherAccess(user, applications);
        updateApplicationTeacherConfirms(applications, isTeacherConfirmed);
        return HttpStatus.OK;
    }

    private static void checkAccess(HoisUserDetails user, List<ScholarshipApplication> applications) {
        for (ScholarshipApplication application : applications) {
            UserUtil.throwAccessDeniedIf(!UserUtil.isSameSchool(user, application.getScholarshipTerm().getSchool()), 
                    "User has no right to edit these applications");
        }
    }

    private static void checkTeacherAccess(HoisUserDetails user, List<ScholarshipApplication> applications) {
        for (ScholarshipApplication application : applications) {
            UserUtil.throwAccessDeniedIf(!UserUtil.isStudentGroupTeacher(user, application.getStudentGroup()), 
                    "User has no right to edit these applications");
        }
    }

    private static List<ScholarshipApplication> setRejectionComments(ScholarshipApplicationListSubmitForm form,
            List<ScholarshipApplication> applications) {
        Map<Long, ScholarshipApplication> applicationMap = StreamUtil.toMap(a -> EntityUtil.getId(a), applications);
        for(ScholarshiApplicationRejectionForm rej : form.getApplications()) {
            ScholarshipApplication app = applicationMap.get(rej.getId());
            app.setRejectComment(rej.getRejectComment());
        }
        return new ArrayList<>(applicationMap.values());
    }

    private List<ScholarshipApplication> getApplications(List<Long> applications) {
        if (!applications.isEmpty() ) {
            return em.createQuery("SELECT sa FROM ScholarshipApplication sa WHERE sa.id in (?1)",
                    ScholarshipApplication.class).setParameter(1, applications).getResultList();
        }
        return new ArrayList<>();
    }

    private List<ScholarshipApplication> getApplications(ScholarshipApplicationListSubmitForm form) {
        return getApplications(StreamUtil.toMappedList(ScholarshiApplicationRejectionForm::getId, form.getApplications()));
    }

    private void updateApplicationStatuses(List<ScholarshipApplication> entities, ScholarshipStatus status) {
        Classifier statusCl = em.getReference(Classifier.class, status.name());
        for (ScholarshipApplication application : entities) {
            application.setStatus(statusCl);
            application.setDecisionDate(LocalDate.now());
            EntityUtil.save(application, em);
        }
    }

    private void updateApplicationTeacherConfirms(List<ScholarshipApplication> entities, Boolean isTeacherConfirmed) {
        for (ScholarshipApplication application : entities) {
            application.setIsTeacherConfirmed(isTeacherConfirmed);
            EntityUtil.save(application, em);
        }
    }

    public List<ScholarshipStudentRejectionDto> getStudentProfilesForRejection(List<Long> applicationIds) {
        List<ScholarshipApplication> applications = em
                .createQuery("SELECT sa FROM ScholarshipApplication sa where sa.id in (?1)",
                        ScholarshipApplication.class)
                .setParameter(1, applicationIds).getResultList();
        return StreamUtil.toMappedList(s -> ScholarshipStudentRejectionDto.of(s), applications);
    }

    private ScholarshipTermComplianceDto studentCompliesTerm(Student student, ScholarshipTerm term) {
        ScholarshipTermComplianceDto compliance = new ScholarshipTermComplianceDto();

        compliance.setOnAcademicLeave(Boolean.FALSE.equals(term.getIsAcademicLeave())
                && ClassifierUtil.equals(StudentStatus.OPPURSTAATUS_A, student.getStatus()));

        compliance.setStudentGroup(EntityUtil.getNullableId(student.getStudentGroup()) == null);

        List<Directive> directives = studentTermDirectives(student);
        List<Directive> academicLeaveDirectives = StreamUtil.toFilteredList(
                d -> DirectiveType.KASKKIRI_AKAD.name().equals(EntityUtil.getCode(d.getType())), directives);
        List<Directive> exmatriculationDirectives = StreamUtil.toFilteredList(
                d -> DirectiveType.KASKKIRI_EKSMAT.name().equals(EntityUtil.getCode(d.getType())), directives);

        compliance.setAcademicLeaveDirectives(!academicLeaveDirectives.isEmpty());

        compliance.setExmatriculationDirectives(!exmatriculationDirectives.isEmpty());

        compliance.setCourse(
                !term.getScholarshipTermCourses().isEmpty() && (student.getStudentGroup() == null || !StreamUtil
                        .toMappedList(c -> Short.valueOf(c.getCourse().getValue()), term.getScholarshipTermCourses())
                        .contains(student.getStudentGroup().getCourse())));

        compliance.setStudyLoad(!term.getScholarshipTermStudyLoads().isEmpty() && !StreamUtil
                .toMappedList(l -> EntityUtil.getCode(l.getStudyLoad()), term.getScholarshipTermStudyLoads())
                .contains(EntityUtil.getNullableCode(student.getStudyLoad())));

        compliance.setStudyForm(!term.getScholarshipTermStudyForms().isEmpty() && !StreamUtil
                .toMappedList(f -> EntityUtil.getCode(f.getStudyForm()), term.getScholarshipTermStudyForms())
                .contains(EntityUtil.getNullableCode(student.getStudyForm())));

        compliance.setCurriculum(
                !StreamUtil.toMappedList(c -> EntityUtil.getId(c.getCurriculum()), term.getScholarshipTermCurriculums())
                        .contains(EntityUtil.getId(student.getCurriculumVersion().getCurriculum())));

        compliance.setStudyStartPeriod(term.getStudyStartPeriodStart() != null && term.getStudyStartPeriodEnd() != null
                && (student.getStudyStart().isBefore(term.getStudyStartPeriodStart())
                        || student.getStudyStart().isAfter(term.getStudyStartPeriodEnd()))
                && (!student.getStudyStart().isEqual(term.getStudyStartPeriodEnd())
                        || !student.getStudyStart().isEqual(term.getStudyStartPeriodStart())));

        compliance.setNominalStudyEnd(
                ClassifierUtil.oneOf(term.getType(), ScholarshipType.STIPTOETUS_DOKTOR, ScholarshipType.STIPTOETUS_POHI)
                        && student.getNominalStudyEnd().isBefore(LocalDate.now()));

        StudentResults results = getStudentResults(term, student);

        compliance.setStudyBacklog(
                !Boolean.TRUE.equals(term.getIsStudyBacklog()) && Boolean.TRUE.equals(results.getIsStudyBacklog()));

        compliance.setAverageMark(false);
        if (term.getAverageMark() != null && BigDecimal.ZERO.compareTo(term.getAverageMark()) != 0) {
            BigDecimal comparable = useSaisPoints(term, student) ? getSaisPoints(student) : results.getAverageMark();
            if (comparable == null || comparable.compareTo(term.getAverageMark()) < 0) {
                compliance.setAverageMark(true);
            }
        }

        compliance.setLastPeriodMark(term.getLastPeriodMark() != null && (results.getLastPeriodMark() == null
                || results.getLastPeriodMark().compareTo(term.getLastPeriodMark()) < 0));

        compliance.setCurriculumCompletion(term.getCurriculumCompletion() != null
                && results.getCurriculumCompletion().compareTo(term.getCurriculumCompletion()) < 0);

        compliance.setAbsences(term.getMaxAbsences() != null && results.getAbsences() != null
                && results.getAbsences().compareTo(term.getMaxAbsences()) > 0);
        
        compliance.setFullyComplies();
        return compliance;
    }

    private List<Directive> studentTermDirectives(Student student) {
        return em.createQuery("select d from Directive d join d.students ds"
                + " where ds.student = ?1 and d.status.code in (?2)  and d.type.code in (?3)", Directive.class)
                .setParameter(1, student)
                .setParameter(2, EnumUtil.toNameList(DirectiveStatus.KASKKIRI_STAATUS_KOOSTAMISEL,
                        DirectiveStatus.KASKKIRI_STAATUS_KINNITAMISEL))
                .setParameter(3, EnumUtil.toNameList(DirectiveType.KASKKIRI_AKAD, DirectiveType.KASKKIRI_EKSMAT))
                .getResultList();
    }

    private static final Map<Function<ScholarshipTerm, Classifier>, Comparator<ScholarshipApplicationSearchDto>> COMPARATOR = new HashMap<>();
    static {
        COMPARATOR.put(ScholarshipTerm::getAverageMarkPriority, Comparator.comparing(
                ScholarshipApplicationSearchDto::getAverageMark, Comparator.nullsLast(Comparator.reverseOrder())));
        COMPARATOR.put(ScholarshipTerm::getLastPeriodMarkPriority, Comparator.comparing(
                ScholarshipApplicationSearchDto::getLastPeriodMark, Comparator.nullsLast(Comparator.reverseOrder())));
        COMPARATOR.put(ScholarshipTerm::getCurriculumCompletionPriority, Comparator.comparing(
                ScholarshipApplicationSearchDto::getCurriculumCompletion, Comparator.nullsLast(Comparator.reverseOrder())));
        COMPARATOR.put(ScholarshipTerm::getMaxAbsencesPriority, Comparator.comparing(
                ScholarshipApplicationSearchDto::getAbsences, Comparator.nullsLast(Comparator.naturalOrder())));
    }

    

    private static Comparator<ScholarshipApplicationSearchDto> comparatorForTerm(ScholarshipTerm term) {
        List<Comparator<ScholarshipApplicationSearchDto>> comparators = new ArrayList<>(Collections.nCopies(Priority.values().length, null));
        for (Entry<Function<ScholarshipTerm, Classifier>, Comparator<ScholarshipApplicationSearchDto>> me : COMPARATOR.entrySet()) {
            Classifier priority = me.getKey().apply(term);
            if (priority != null) {
                comparators.set(Priority.valueOf(EntityUtil.getCode(priority)).ordinal(), me.getValue());
            }
        }

        comparators = StreamUtil.toFilteredList(r -> r != null, comparators);
        if (comparators.isEmpty()) {
            return Comparator.comparing(ScholarshipApplicationSearchDto::getLastName, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(ScholarshipApplicationSearchDto::getFirstName, String.CASE_INSENSITIVE_ORDER);
        }

        Comparator<ScholarshipApplicationSearchDto> comparator = null;
        for (Comparator<ScholarshipApplicationSearchDto> c : comparators) {
            if (comparator == null) {
                comparator = c;
            } else {
                comparator = comparator.thenComparing(c);
            }
        }
        return comparator;
    }

    public void delete(ScholarshipTerm term) {
        if(!term.getIsOpen().booleanValue() && term.getScholarshipApplications().isEmpty()) {
            EntityUtil.deleteEntity(term, em);
        }
    }

    private static class StudentResults {
        private BigDecimal averageMark;
        private BigDecimal lastPeriodMark;
        private BigDecimal credits;
        private BigDecimal curriculumCompletion;
        private Long absences;
        private Boolean isStudyBacklog;

        public StudentResults() {
        }

        public BigDecimal getAverageMark() {
            return averageMark;
        }

        public void setAverageMark(BigDecimal averageMark) {
            this.averageMark = averageMark;
        }

        public BigDecimal getLastPeriodMark() {
            return lastPeriodMark;
        }

        public void setLastPeriodMark(BigDecimal lastPeriodMark) {
            this.lastPeriodMark = lastPeriodMark;
        }

        public BigDecimal getCredits() {
            return credits;
        }

        public void setCredits(BigDecimal credits) {
            this.credits = credits;
        }

        public BigDecimal getCurriculumCompletion() {
            return curriculumCompletion;
        }

        public void setCurriculumCompletion(BigDecimal curriculumCompletion) {
            this.curriculumCompletion = curriculumCompletion;
        }

        public Long getAbsences() {
            return absences;
        }

        public void setAbsences(Long absences) {
            this.absences = absences;
        }

        public Boolean getIsStudyBacklog() {
            return isStudyBacklog;
        }

        public void setIsStudyBacklog(Boolean isStudyBacklog) {
            this.isStudyBacklog = isStudyBacklog;
        }
    }

    private static class AverageGradeParams {
        private LocalDate periodStart;
        private LocalDate periodEnd;
        private Boolean isOutcomes;
        private Boolean isPeriodGrade;
        private Boolean isJournalFinalGrade;
        private Boolean isModuleGrade;
        private Boolean isApelGrade;
        private Boolean isJournalGrade;

        public AverageGradeParams() {
        }

        public LocalDate getPeriodStart() {
            return periodStart;
        }

        public void setPeriodStart(LocalDate periodStart) {
            this.periodStart = periodStart;
        }

        public LocalDate getPeriodEnd() {
            return periodEnd;
        }

        public void setPeriodEnd(LocalDate periodEnd) {
            this.periodEnd = periodEnd;
        }

        public Boolean getIsOutcomes() {
            return isOutcomes;
        }

        public void setIsOutcomes(Boolean isOutcomes) {
            this.isOutcomes = isOutcomes;
        }

        public Boolean getIsPeriodGrade() {
            return isPeriodGrade;
        }

        public void setIsPeriodGrade(Boolean isPeriodGrade) {
            this.isPeriodGrade = isPeriodGrade;
        }

        public Boolean getIsJournalFinalGrade() {
            return isJournalFinalGrade;
        }

        public void setIsJournalFinalGrade(Boolean isJournalFinalGrade) {
            this.isJournalFinalGrade = isJournalFinalGrade;
        }

        public Boolean getIsModuleGrade() {
            return isModuleGrade;
        }

        public void setIsModuleGrade(Boolean isModuleGrade) {
            this.isModuleGrade = isModuleGrade;
        }

        public Boolean getIsApelGrade() {
            return isApelGrade;
        }

        public void setIsApelGrade(Boolean isApelGrade) {
            this.isApelGrade = isApelGrade;
        }

        public Boolean getIsJournalGrade() {
            return isJournalGrade;
        }

        public void setIsJournalGrade(Boolean isJournalGrade) {
            this.isJournalGrade = isJournalGrade;
        }

    }
}
