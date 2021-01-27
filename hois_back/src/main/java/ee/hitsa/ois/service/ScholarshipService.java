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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import ee.hitsa.ois.enums.HigherAssessment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Committee;
import ee.hitsa.ois.domain.CommitteeMember;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.ScholarshipNoApplication;
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
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.ScholarshipUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.SearchCommand;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshiApplicationRejectionForm;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipApplicationListSubmitForm;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipApplicationRankingSearchCommand;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipApplicationSearchCommand;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipCommitteeSearchCommand;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipDecisionForm;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipSearchCommand;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipStudentApplicationForm;
import ee.hitsa.ois.web.commandobject.scholarship.ScholarshipTermForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.ScholarshipNoApplicationDto;
import ee.hitsa.ois.web.dto.ScholarshipTermApplicationRankingSearchDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipApplicationDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipApplicationRankingSearchDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipApplicationSearchDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipApplicationStudentDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipDecisionDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipStudentRejectionDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermApplicationDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermComplianceDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermResult;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermSearchDto;
import ee.hitsa.ois.web.dto.scholarship.ScholarshipTermStudentDto;
import ee.hitsa.ois.web.dto.scholarship.UnappliedScholarshipApplicationDto;

@Transactional
@Service
public class ScholarshipService {

    private static final int SAIS_POINTS_MONTHS = 4;
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
    private AutocompleteService autocompleteService;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private ClassifierService classifierService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private StudentResultHigherService studentResultHigherService;
    @Autowired
    private XlsService xlsService;

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
        EntityUtil.bindToEntity(form, scholarshipTerm, classifierRepository, "curriculums", "studyLoads", "courses", "isNominalEnd");
        if (ScholarshipType.STIPTOETUS_SOIDU.name().equals(form.getType())) {
            scholarshipTerm.setIsStudyBacklog(Boolean.TRUE);
        }
        if (ScholarshipType.STIPTOETUS_MUU.name().equals(form.getType())) {
            scholarshipTerm.setIsNominalEnd(form.getIsNominalEnd());
        }
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
            UserUtil.throwAccessDeniedIf(!committee.getMembers().stream()
                    .anyMatch(cm -> user.getPersonId().equals(EntityUtil.getId(cm.getPerson()))),
                    "Teacher is not member of decision committee");
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
        List<ScholarshipTerm> scholarshipTerms = availableScholarshipTerms(EntityUtil.getId(student.getSchool()), null,
                EntityUtil.getId(student.getCurriculumVersion().getCurriculum()), drGrants);
        List<ScholarshipTermStudentDto> availableStipends = StreamUtil
                .toMappedList(st -> ScholarshipTermStudentDto.of(st), scholarshipTerms);
        Map<Long, ScholarshipTermComplianceDto> termCompliances = StreamUtil.toMap(r -> r.getId(),
                r -> studentCompliesTerm(student, r), scholarshipTerms);

        for (ScholarshipTermStudentDto stipend : availableStipends) {
            stipend.setTermCompliance(termCompliances.get(stipend.getId()));
        }

        return availableStipends;
    }

    private List<ScholarshipTerm> availableScholarshipTerms(Long schoolId, String scholarshipType, Long curriculumId,
            Boolean drGrants) {
        LocalDate today = LocalDate.now();
        JpaQueryBuilder<ScholarshipTerm> qb = new JpaQueryBuilder<>(ScholarshipTerm.class, "st",
                curriculumId != null ? "join st.scholarshipTermCurriculums stc" : null);
        qb.requiredCriteria("st.school.id = :schoolId and st.isOpen = true", "schoolId", schoolId);
        qb.requiredCriteria("((st.applicationStart <= :today or st.applicationStart is null)", "today", today);
        qb.requiredCriteria("(st.applicationEnd >= :today or st.applicationEnd is null))", "today", today);
        qb.optionalCriteria("stc.curriculum.id = :curriculumId", "curriculumId", curriculumId);
        qb.optionalCriteria("st.type.code in (:scholarshipTypes)", "scholarshipTypes",
                ScholarshipType.STIPEND_TYPES.get(scholarshipType));
        if (drGrants != null) {
            qb.requiredCriteria("st.type.code " + (Boolean.TRUE.equals(drGrants) ? "=" : "!=") + ":scholarshipType",
                    "scholarshipType", ScholarshipType.STIPTOETUS_DOKTOR);
        }
        return qb.select(em).getResultList();
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
            dto.setTermStudyPeriod(term.getStudyPeriod() != null ? AutocompleteResult.ofWithYear(term.getStudyPeriod()) : null);
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

    public Map<String, Object> getStudentApplicationView(HoisUserDetails user, ScholarshipTerm term,
             Student student) {
        if (student == null) {
            student = em.getReference(Student.class, user.getStudentId());
        }
        ScholarshipUtil.assertCanEditApplication(user, term, student);

        Map<String, Object> result = new HashMap<>();
        result.put("stipend", ScholarshipTermApplicationDto.of(term));
        ScholarshipApplication application = getApplicationForTermAndStudent(term, student);
        result.put("application", getStudentApplicationDto(student, term, application));
        result.put("termCompliance", studentCompliesTerm(student, term));

        if (!user.isStudent()) {
            result.put("student", AutocompleteResult.of(student));
            if (application != null) {
                if (application.getStudentGroup() != null) {
                    result.put("studentGroup", AutocompleteResult.of(application.getStudentGroup()));
                }
            } else if (student.getStudentGroup() != null) {
                result.put("studentGroup", AutocompleteResult.of(student.getStudentGroup()));
            }
        }
        return result;
    }

    public ScholarshipApplicationDto getStudentApplicationDto(Student student, ScholarshipTerm term) {
        ScholarshipApplication application = getApplicationForTermAndStudent(term, student);
        return getStudentApplicationDto(student, term, application);
    }

    private ScholarshipApplicationDto getStudentApplicationDto(Student student, ScholarshipTerm term,
            ScholarshipApplication application) {
        Person person = student.getPerson();
        ScholarshipApplicationDto applicationDto = application == null ? 
                new ScholarshipApplicationDto() : ScholarshipApplicationDto.of(application);
        applicationDto.setCanApply(Boolean.valueOf(ScholarshipUtil.canApply(term, application)));
        applicationDto.setAddress(person.getAddress());

        StudentResults results = getStudentResults(term, student);
        BigDecimal credits = results.getCredits();
        applicationDto.setCredits(credits);
        applicationDto.setAverageMark(results.getAverageMark());
        applicationDto.setWagMark(results.getWagMark());
        applicationDto.setLastPeriodMark(results.getLastPeriodMark());
        applicationDto.setLastPeriodWagMark(results.getLastPeriodWagMark());
        applicationDto.setCurriculumCompletion(results.getCurriculumCompletion());
        applicationDto.setIsStudyBacklog(results.getIsStudyBacklog());
        applicationDto.setAbsences(results.getAbsences());
        if (application == null) {
            applicationDto.setPhone(person.getPhone());
            applicationDto.setEmail(student.getEmail());
        }

        applicationDto.setUseSaisPoints(Boolean.valueOf(useSaisPoints(term, student)));
        if (Boolean.TRUE.equals(applicationDto.getUseSaisPoints())) {
            applicationDto.setSaisPoints(getSaisPoints(student));
        }

        if (application == null) {
            List<ScholarshipApplication> prevApplications = studentStipends(student.getId(), null);
            prevApplications = StreamUtil.nullSafeList(prevApplications).stream()
                    .filter(a -> a.getBankAccount() != null && (a.getBankAccountOwnerIdcode() == null
                            || a.getBankAccountOwnerIdcode().equals(person.getIdcode())))
                    .sorted(Comparator.comparing(a -> a.getInserted(), Comparator.reverseOrder()))
                    .collect(Collectors.toList());

            applicationDto.setBankAccount(person.getBankaccount());
            applicationDto.setBankAccountOwnerName(PersonUtil.fullname(person));
            applicationDto.setBankAccountOwnerIdcode(person.getIdcode());
            if (person.getBankaccount() == null && !prevApplications.isEmpty()) {
                ScholarshipApplication prevApplication = prevApplications.get(0);
                applicationDto.setBankAccount(prevApplication.getBankAccount());
                applicationDto.setBankAccountOwnerIdcode(prevApplication.getBankAccountOwnerIdcode());
            }
        }
        return applicationDto;
    }

    public Map<String, Object> getApplicationView(HoisUserDetails user, ScholarshipApplication application) {
        Map<String, Object> result = new HashMap<>();
        result.put("stipend", ScholarshipTermApplicationDto.of(application.getScholarshipTerm()));
        result.put("application", getApplicationDto(application));

        if (!user.isStudent()) {
            result.put("student", AutocompleteResult.of(application.getStudent()));
            if (application.getStudentGroup() != null) {
                result.put("studentGroup", AutocompleteResult.of(application.getStudentGroup()));
            }
        }
        return result;
    }

    public ScholarshipApplication saveApplication(HoisUserDetails user, ScholarshipTerm term,
            ScholarshipStudentApplicationForm form) {
        Student student = em.getReference(Student.class, user.isStudent() ? user.getStudentId() : form.getStudentId());
        ScholarshipUtil.assertCanCreateApplication(user, term, student);

        ScholarshipApplication application = getApplicationForTermAndStudent(term, student);
        if (application != null) {
            return application;
        }
        if(!studentCompliesTerm(student, term).getFullyComplies()) {
            throw new ValidationFailedException(ScholarshipUtil.isScholarship(term)
                    ? "stipend.messages.error.studentDoesntComplyScholarship"
                    : "stipend.messages.error.studentDoesntComplyGrant");
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
        application.setCurriculumCompletion(term.getCurriculumCompletion() == null && term.getCurriculumCompletionPriority() == null
                ? null : results.getCurriculumCompletion());

        boolean isSais = useSaisPoints(term, student);
        BigDecimal saisPoints = getSaisPoints(student);

        application.setAverageMark(term.getAverageMark() == null && term.getAverageMarkPriority() == null
                ? null : isSais ? saisPoints : results.getAverageMark());
        application.setWagMark(term.getWagMark() == null && term.getWagMarkPriority() == null
                ? null : isSais ? saisPoints : results.getWagMark());
        application.setLastPeriodMark(term.getLastPeriodMark() == null && term.getLastPeriodMarkPriority() == null
                ? null : isSais ? saisPoints : results.getLastPeriodMark());
        application.setLastPeriodWagMark(term.getLastPeriodWagMark() == null && term.getLastPeriodWagMarkPriority() == null
                ? null : isSais ? saisPoints : results.getLastPeriodWagMark());
        application.setAbsences(term.getMaxAbsences() == null && term.getMaxAbsencesPriority() == null
                ? null : results.getAbsences());
        application.setIsSais(Boolean.valueOf(isSais));
    }

    private StudentResults getHigherResults(ScholarshipTerm term, Student student) {
        StudentResults results = new StudentResults();

        List<StudyResult> studyResults = higherStudyResults(term, student, null);
        PeriodAverages resultAverages = setHigherAverageMarks(studyResults);
        results.setAverageMark(resultAverages.getAverageMark());
        results.setWagMark(resultAverages.getWagMark());

        Long currentStudyPeriod = studyYearService.getCurrentStudyPeriod(EntityUtil.getId(student.getSchool()));
        Long lastPeriod = lastDeclarationPeriod(student, currentStudyPeriod);
        List<StudyResult> lastPeriodResults = new ArrayList<>();
        if (lastPeriod != null) {
            lastPeriodResults = higherStudyResults(term, student, lastPeriod);
        }
        PeriodAverages lastPeriodAverages = setHigherAverageMarks(lastPeriodResults);
        results.setLastPeriodMark(lastPeriodAverages.getAverageMark());
        results.setLastPeriodWagMark(lastPeriodAverages.getWagMark());

        BigDecimal credits = studyResults.stream().filter(r -> HigherAssessment.GRADE_POSITIVE.contains(r.getGrade()))
                .map(StudyResult::getCredits).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        results.setCredits(credits);
        results.setCurriculumCompletion(studentResultHigherService.getConsideredCurriculumCompletion(student, credits));

        return results;
    }

    private static PeriodAverages setHigherAverageMarks(List<StudyResult> results) {
        @SuppressWarnings("synthetic-access")
        PeriodAverages averages = new PeriodAverages();
        if (!results.isEmpty()) {
            long passedResults = results.stream().filter(r -> HigherAssessment.KORGHINDAMINE_A.name()
                    .equals(r.getGrade())).count();
            if (results.size() == passedResults) {
                averages.setAverageMark(BigDecimal.valueOf(5));
                averages.setWagMark(BigDecimal.valueOf(5));
            } else {
                List<StudyResult> distinctiveResults = results.stream()
                        .filter(r -> HigherAssessment.GRADE_DISTINCTIVE.contains(r.getGrade()))
                        .collect(Collectors.toList());

                int gradeSum = 0;
                BigDecimal wagGradeSum = BigDecimal.ZERO;
                for (StudyResult result : distinctiveResults) {
                    Short mark = HigherAssessment.getGradeMark(result.getGrade());
                    if (mark != null) {
                        gradeSum += mark.shortValue();
                        wagGradeSum = wagGradeSum.add(BigDecimal.valueOf(mark.shortValue()).multiply(result.getCredits()));
                    }
                }

                BigDecimal wagMark = null;
                Optional<BigDecimal> resultCredits = distinctiveResults.stream().map(StudyResult::getCredits)
                        .reduce(BigDecimal::add);
                if (resultCredits.isPresent()) {
                    wagMark = wagGradeSum.divide(resultCredits.get(), 3, RoundingMode.FLOOR);
                }

                averages.setAverageMark(averageMark(gradeSum, distinctiveResults.size()));
                averages.setWagMark(wagMark);
            }
        }
        return averages;
    }

    private Long lastDeclarationPeriod(Student student, Long currentStudyPeriod) {
        List<?> data = em.createNativeQuery("select sp.id from declaration d "
                + "join study_period sp on sp.id = d.study_period_id "
                + "where d.student_id = :studentId "
                + "and sp.end_date <= (select sp2.start_date from study_period sp2 where sp2.id = :currentStudyPeriod) "
                + "order by sp.end_date desc, sp.start_date desc")
                .setParameter("studentId", EntityUtil.getId(student))
                .setParameter("currentStudyPeriod", currentStudyPeriod)
                .setMaxResults(1)
                .getResultList();
        return !data.isEmpty() ? resultAsLong(data.get(0), 0) : null;
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
            results.setIsStudyBacklog(getIsStudyBacklog(lastPeriodGrades));
        }
        if (ScholarshipType.STIPTOETUS_POHI.name().equals(EntityUtil.getNullableCode(term.getType()))) {
            results.setAbsences(getAbsences(term, student));
        }
        return results;
    }

    private StudentResults getStudentResults(ScholarshipTerm term, Student student) {
        Classifier termType = term.getType();
        if (termType.isHigher()) {
            return getHigherResults(term, student);
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
        List<String> distinctiveResults = StreamUtil.toFilteredList(OccupationalGrade::isDistinctive,
                studentGrades);
        int gradeSum = 0;
        for (String grade : distinctiveResults) {
            gradeSum += OccupationalGrade.getGradeMark(grade);
        }
        return averageMark(gradeSum, distinctiveResults.size());
    }

    private static BigDecimal averageMark(int gradeSum, int distinctiveResults) {
        if (distinctiveResults == 0) {
            return null;
        }
        BigDecimal averageMark = gradeSum > 0 ? BigDecimal.valueOf((double) gradeSum / distinctiveResults) : null;
        return averageMark != null ? averageMark.setScale(2, RoundingMode.DOWN) : null;
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
        boolean isOutcomes = Boolean.TRUE.equals(params.getIsOutcomes());
        List<String> entryTypes = entryTypes(params);

        if (!(isModuleGrade || isApelGrade || isOutcomes || !entryTypes.isEmpty())) {
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

        if (isOutcomes) {
            if (addUnion) {
                sql += " union all ";
            }
            sql += "select grade.code as grade_code"
                    + " from student_curriculum_module_outcomes_result scmor"
                    + " join classifier grade on grade.code = scmor.grade_code"
                    + " where scmor.student_id = ?1";
            if (periodStartExists || periodEndExists) {
                sql += " and scmor.grade_date" + periodComparisonCondition(periodStartExists, periodEndExists);
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

    private List<StudyResult> higherStudyResults(ScholarshipTerm term, Student student, Long studyPeriod) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_higher_result shr");
        qb.requiredCriteria("shr.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.filter("shr.is_active = true and shr.is_module = false");
        if (!Boolean.TRUE.equals(term.getIsApelGrade())) {
            qb.filter("shr.apel_application_record_id is null");
        }
        if (!Boolean.TRUE.equals(term.getIsBeforeImmat())) {
            qb.filter("(shr.grade_date is null or shr.grade_date >="
                    + "(select s.study_start from student s where s.id = shr.student_id))");
        }
        if (!Boolean.TRUE.equals(term.getIsNegative())) {
            qb.requiredCriteria("shr.grade_code in (:positiveGardes)", "positiveGardes", HigherAssessment.GRADE_POSITIVE);
        }
        qb.optionalCriteria("shr.grade_date >= (select start_date from study_period where id = :studyPeriodId)",
                "studyPeriodId", studyPeriod);
        qb.optionalCriteria("shr.grade_date <= (select end_date from study_period where id = :studyPeriodId)",
                "studyPeriodId", studyPeriod);

        List<?> data = qb.select("shr.grade_code, shr.credits", em).getResultList();
        return StreamUtil.toMappedList(r -> new StudyResult(resultAsString(r, 0), resultAsDecimal(r, 1)), data);
    }

    private static boolean useSaisPoints(ScholarshipTerm term, Student student) {
        return term.getType().isHigher() && Boolean.TRUE.equals(term.getIsSais()) && student.getStudyStart() != null
                && LocalDate.now().minusMonths(SAIS_POINTS_MONTHS).isBefore(student.getStudyStart())
                && student.getStudentGroup() != null && Short.valueOf((short) 1).equals(student.getStudentGroup().getCourse());
    }

    private BigDecimal getSaisPoints(Student student) {
        List<?> result = em.createNativeQuery("select coalesce(sa.points, 0) points"
                + " from directive_student ds"
                + " join sais_application sa on sa.id = ds.sais_application_id"
                + " where ds.canceled = false and ds.student_id = ?1")
                .setParameter(1, EntityUtil.getId(student))
                .setMaxResults(1)
                .getResultList();
        return result.isEmpty() ? BigDecimal.ZERO : resultAsDecimal(result.get(0), 0);
    }

    // TODO: Absences uses same dates as last period grade. Change variable name?
    private Long getAbsences(ScholarshipTerm term, Student student) {
        boolean periodStartExists = term.getLastPeriodGradeFrom() != null;
        boolean periodEndExists = term.getLastPeriodGradeThru() != null;

        String entryAbsences = "select sum(coalesce(je.lessons, 1)) as lessons from journal_entry_student jes"
                + " join journal_student js on js.id = jes.journal_student_id"
                + " join journal_entry je on je.id = jes.journal_entry_id"
                + " where js.student_id = ?1 and jes.absence_code = ?4";

        String lessonAbsences = "select sum(1) as lessons from journal_entry_student jes"
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
        //TODO: better user rights check
        if (application == null || !(EntityUtil.getId(application.getStudent()).equals(user.getStudentId())
                || EntityUtil.getId(application.getStudent()).equals(form.getStudentId()))) {
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

    public ScholarshipApplicationDto getApplicationDto(ScholarshipApplication application) {
        ScholarshipApplicationDto dto = ScholarshipApplicationDto.of(application);
        dto.setUseSaisPoints(application.getIsSais());
        if (Boolean.TRUE.equals(dto.getUseSaisPoints())) {
            // sais points are saved in every mark field
            dto.setSaisPoints(application.getAverageMark());
        }
        return dto;
    }

    public ScholarshipApplication apply(ScholarshipApplication application) {
        if (studentCompliesTerm(application.getStudent(), application.getScholarshipTerm()).getFullyComplies()) {
            application.setStatus(em.getReference(Classifier.class, ScholarshipStatus.STIPTOETUS_STAATUS_E.name()));
            return EntityUtil.save(application, em);
        }
        throw new ValidationFailedException(ScholarshipUtil.isScholarship(application.getScholarshipTerm())
                ? "stipend.messages.error.studentDoesntComplyScholarship"
                : "stipend.messages.error.studentDoesntComplyGrant");
    }

    public Page<ScholarshipApplicationSearchDto> applications(HoisUserDetails user,
            ScholarshipApplicationSearchCommand command, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from scholarship_application sa"
            + " join scholarship_term st on st.id = sa.scholarship_term_id"
            + " join study_period sp on sp.id = st.study_period_id"
            + " join student s on s.id = sa.student_id"
            + " join person p on p.id = s.person_id"
            + " join curriculum_version cv on sa.curriculum_version_id = cv.id"
            + " join curriculum c on c.id = cv.curriculum_id"
            + " join student_group sg on sg.id = sa.student_group_id").sort(pageable);

        setApplicationSearchUserCriteria(user, qb);
        qb.requiredCriteria("st.type_code in (:scholarshipTypeCodes)", "scholarshipTypeCodes",
                ScholarshipType.STIPEND_TYPES.get(command.getScholarshipType()));
        qb.optionalCriteria("st.type_code = :typeCode", "typeCode", command.getType());
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"),
                "personName", command.getStudentName());
        qb.optionalCriteria("(st.application_start is null or st.application_start >= :from)", "from",
                command.getAppliedFrom());
        qb.optionalCriteria("(st.application_end is null or st.application_end >= :from)", "from",
                command.getAppliedFrom());
        qb.optionalCriteria("(st.application_end is null or st.application_end <= :thru)",
                "thru", command.getAppliedThru());
        qb.optionalCriteria("(st.application_start is null or st.application_start <= :thru)",
                "thru", command.getAppliedThru());

        qb.optionalCriteria("sp.study_year_id = :studyYearId", "studyYearId", command.getStudyYear());
        qb.optionalContains("st.name_et", "nameEt", command.getNameEt());
        qb.optionalCriteria("sa.status_code = :status", "status", command.getStatus());

        String select = "sa.id sa_id, s.id s_id, p.firstname, p.lastname, sg.code, st.id st_id, st.type_code,"
                + " st.name_et, st.application_start, st.application_end, sa.status_code, sa.inserted, sg.teacher_id, st.scholarship_ehis_code";
        return JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            LocalDate applicationStart = resultAsLocalDate(r, 8);
            LocalDate applicationEnd = resultAsLocalDate(r, 9);
            String applicationStatus = resultAsString(r, 10);
            ScholarshipApplicationSearchDto dto = new ScholarshipApplicationSearchDto(resultAsLong(r, 0),
                    resultAsLong(r, 1), PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3)),
                    resultAsString(r, 4), resultAsLong(r, 5), resultAsString(r, 6), resultAsString(r, 7),
                    applicationStart, applicationEnd, applicationStatus, resultAsLocalDate(r, 11), resultAsString(r, 13));
            dto.setCanEdit(Boolean.valueOf(ScholarshipUtil.canEditApplication(user, applicationStatus, applicationStart,
                    applicationEnd, resultAsLong(r, 12))));
            return dto;
        });
    }

    private static void setApplicationSearchUserCriteria(HoisUserDetails user, JpaNativeQueryBuilder qb) {
        qb.requiredCriteria("st.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("(c.id in (select uc.curriculum_id from user_curriculum uc"
                    + " where uc.user_id = :userId)"
                    + " or st.committee_id in (select c.id from committee c"
                    + " join committee_member cm on cm.committee_id = c.id"
                    + " join user_ u on u.person_id = cm.person_id"
                    + " where u.id = :userId and c.type_code = '" + CommitteeType.KOMISJON_T.name() + "')"
                    + ")", "userId", user.getUserId());
        } else if (user.isTeacher()) {
            qb.requiredCriteria("(sa.student_group_id in (select sg.id from student_group sg"
                    + " where sg.teacher_id = :teacherId)"
                    + " or st.committee_id in (select c.id"
                    + " from committee_member cm"
                    + " join committee c on c.id = cm.committee_id"
                    + " join teacher t on t.person_id = cm.person_id"
                    + " where t.id = :teacherId and c.type_code = '" + CommitteeType.KOMISJON_T.name() + "')"
                    + ")", "teacherId", user.getTeacherId());
        }
    }

    public ScholarshipTermApplicationRankingSearchDto applicationsRanking(HoisUserDetails user,
            ScholarshipApplicationRankingSearchCommand command) {
        List<ScholarshipApplicationRankingSearchDto> applications = applicationsForCommand(command, user, null);

        ScholarshipTerm term;
        if(!applications.isEmpty()) {
            term = em.getReference(ScholarshipTerm.class, applications.get(0).getTerm());
        } else {
            return null;
        }
        Collections.sort(applications, comparatorForTerm(term));

        return new ScholarshipTermApplicationRankingSearchDto(term.getPlaces(), applications);
    }

    private List<ScholarshipApplicationRankingSearchDto> applicationsForCommand(ScholarshipApplicationRankingSearchCommand command,
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

        setApplicationSearchUserCriteria(user, qb);
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
            qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"),
                    "personName", command.getStudentName());
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
                + ", sa.credits, sa.absences, sa.reject_comment, st.is_teacher_confirm, sa.is_sais"
                + ", (select exists(select 1 from directive_student ds join directive d on d.id = ds.directive_id"
                    + " where ds.scholarship_application_id = sa.id and ds.canceled = false"
                    + " and d.status_code in (" + directiveStatuses + "))) as has_directive"
                + ", sd.id, sd.decided, sg.code as student_group_code, sa.bank_account_owner_idcode"
                + ", st.scholarship_ehis_code, sa.wag_mark, sa.last_period_wag_mark, c.mer_code as htmCode";
        List<?> data = qb.select(select, em).getResultList();
        List<ScholarshipApplicationRankingSearchDto> applications = StreamUtil.toMappedList(r -> {
        	ScholarshipApplicationRankingSearchDto dto = new ScholarshipApplicationRankingSearchDto();
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
            dto.setIsSais(resultAsBoolean(r, 20));
            dto.setHasDirective(resultAsBoolean(r, 21));
            dto.setDecisionId(resultAsLong(r, 22));
            dto.setDecisionDecided(resultAsLocalDate(r, 23));
            dto.setStudentGroup(resultAsString(r, 24));
            dto.setBankAccountOwnerIdcode(resultAsString(r, 25));
            dto.setTypeEhis(resultAsString(r, 26));
            dto.setWagMark(resultAsDecimal(r, 27));
            dto.setLastPeriodWagMark(resultAsDecimal(r, 28));
            dto.setHtmCode(resultAsString(r, 29));
            return dto;
        }, data);

        if (user.isTeacher()) {
            setCanViewDecision(user, applications);
        } else if (user.isLeadingTeacher()) {
            setCanViewStudent(user, applications);
        }
        return applications;
    }

    // leading teacher can see only his/her curriculum student group students
    private void setCanViewStudent(HoisUserDetails user, List<ScholarshipApplicationRankingSearchDto> applications) {
        Set<Long> applicationStudentIds = StreamUtil.toMappedSet(a -> a.getStudentId(), applications);
        if (applicationStudentIds.isEmpty()) {
            return;
        }

        List<?> data = em.createNativeQuery("select s.id from student s"
                + " join curriculum_version cv on cv.id = s.curriculum_version_id"
                + " where cv.curriculum_id in (?1) and s.id in (?2)")
                .setParameter(1, user.getCurriculumIds())
                .setParameter(2, applicationStudentIds)
                .getResultList();
        Set<Long> studentIds = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);

        for (ScholarshipApplicationRankingSearchDto dto : applications) {
            dto.setCanViewStudent(Boolean.valueOf(studentIds.contains(dto.getStudentId())));
        }
    }

    // teacher can see only decisions of committees that he/she is apart of
    private void setCanViewDecision(HoisUserDetails user, List<ScholarshipApplicationRankingSearchDto> applications) {
        Set<Long> allowedDecisionIds = new HashSet<>();
        Set<Long> decisionIds = StreamUtil.nullSafeList(applications).stream()
                .filter(a -> a.getDecisionId() != null).map(a -> a.getDecisionId()).collect(Collectors.toSet());
        if (!decisionIds.isEmpty()) {
            List<?> data = em.createNativeQuery("select sd.id from scholarship_decision sd"
                    + " join committee c on c.id = sd.committee_id"
                    + " join committee_member cm on cm.committee_id = c.id"
                    + " where cm.person_id = ?1 and sd.id in (?2)")
                    .setParameter(1, user.getPersonId())
                    .setParameter(2, decisionIds)
                    .getResultList();
            allowedDecisionIds = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);
        }

        for (ScholarshipApplicationRankingSearchDto dto : applications) {
            dto.setCanViewDecision(Boolean.valueOf(allowedDecisionIds.contains(dto.getDecisionId())));
        }
    }

    public HttpStatus acceptApplications(HoisUserDetails user, List<Long> applicationIds) {
        List<ScholarshipApplication> result = getApplications(applicationIds);
        result.removeIf(a -> !UserUtil.isSameSchool(user, a.getScholarshipTerm().getSchool())
                || !ScholarshipStatus.STIPTOETUS_STAATUS_E.name().equals(EntityUtil.getCode(a.getStatus())));
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

    public ScholarshipTermComplianceDto studentCompliesTerm(Student student, ScholarshipTerm term) {
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

        String type = EntityUtil.getCode(term.getType());
        compliance.setNominalStudyEnd((ScholarshipType.STIPTOETUS_POHI.name().equals(type) ||
                (ScholarshipType.SCHOLARSHIPS.contains(type) && !Boolean.TRUE.equals(term.getIsNominalEnd())))
                && (student.getNominalStudyEnd() == null || student.getNominalStudyEnd().isBefore(LocalDate.now())));

        StudentResults results = getStudentResults(term, student);

        compliance.setStudyBacklog(
                !Boolean.TRUE.equals(term.getIsStudyBacklog()) && Boolean.TRUE.equals(results.getIsStudyBacklog()));

        if (!useSaisPoints(term, student)) {
            if (term.getAverageMark() != null && BigDecimal.ZERO.compareTo(term.getAverageMark()) != 0) {
                compliance.setAverageMark(results.getAverageMark() == null
                        || results.getAverageMark().compareTo(term.getAverageMark()) < 0);
            }

            if (term.getWagMark() != null && BigDecimal.ZERO.compareTo(term.getWagMark()) != 0) {
                compliance.setWagMark(results.getWagMark() == null
                        || results.getWagMark().compareTo(term.getWagMark()) < 0);
            }

            if (term.getLastPeriodMark() != null && BigDecimal.ZERO.compareTo(term.getLastPeriodMark()) != 0) {
                compliance.setLastPeriodMark((results.getLastPeriodMark() == null
                        || results.getLastPeriodMark().compareTo(term.getLastPeriodMark()) < 0));
            }

            if (term.getLastPeriodWagMark() != null && BigDecimal.ZERO.compareTo(term.getLastPeriodWagMark()) != 0) {
                compliance.setLastPeriodWagMark((results.getLastPeriodWagMark() == null
                        || results.getLastPeriodWagMark().compareTo(term.getLastPeriodWagMark()) < 0));
            }
        }

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

    public List<AutocompleteResult> schoolAvailableStipends(Long schoolId, String scholarshipType) {
        List<ScholarshipTerm> scholarshipTerms = availableScholarshipTerms(schoolId, scholarshipType, null, null);
        return StreamUtil.toMappedList(st -> new ScholarshipTermResult(st.getId(), st.getNameEt(), st.getNameEt(),
                EntityUtil.getCode(st.getType()), st.getApplicationStart(), st.getApplicationEnd()), scholarshipTerms);
    }

    public Page<AutocompleteResult> stipendAvailableStudents(HoisUserDetails user, Long termId, SearchCommand lookup) {
        ScholarshipTerm term = em.getReference(ScholarshipTerm.class, termId);
        return new PageImpl<>(autocompleteService.stipendAvailableStudents(user, term, lookup));
    }

    public List<UnappliedScholarshipApplicationDto> unappliedScholarships(HoisUserDetails user) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from scholarship_application sa"
                + " join scholarship_term st on st.id = sa.scholarship_term_id");
        qb.requiredCriteria("sa.student_id = :studentId", "studentId", user.getStudentId());
        qb.requiredCriteria("sa.status_code in (:status)", "status",
                EnumUtil.toNameList(ScholarshipStatus.STIPTOETUS_STAATUS_K, ScholarshipStatus.STIPTOETUS_STAATUS_T));
        qb.requiredCriteria("(st.application_end is null or st.application_end >= :today)", "today", LocalDate.now());

        qb.sort("st.application_end");
        List<?> data = qb.select("sa.id sa_id, st.id st_id, st.name_et, st.application_end", em).getResultList();
        return StreamUtil.toMappedList(r -> new UnappliedScholarshipApplicationDto(resultAsLong(r, 0),
                resultAsLong(r, 1), resultAsString(r, 2), resultAsLocalDate(r, 3)), data);
    }

    private static final Map<Function<ScholarshipTerm, Classifier>, Comparator<ScholarshipApplicationRankingSearchDto>> COMPARATOR = new HashMap<>();
    static {
        COMPARATOR.put(ScholarshipTerm::getAverageMarkPriority, Comparator.comparing(
                ScholarshipApplicationRankingSearchDto::getAverageMark, Comparator.nullsLast(Comparator.reverseOrder())));
        COMPARATOR.put(ScholarshipTerm::getWagMarkPriority, Comparator.comparing(
                ScholarshipApplicationRankingSearchDto::getWagMark, Comparator.nullsLast(Comparator.reverseOrder())));
        COMPARATOR.put(ScholarshipTerm::getLastPeriodMarkPriority, Comparator.comparing(
                ScholarshipApplicationRankingSearchDto::getLastPeriodMark, Comparator.nullsLast(Comparator.reverseOrder())));
        COMPARATOR.put(ScholarshipTerm::getLastPeriodWagMarkPriority, Comparator.comparing(
                ScholarshipApplicationRankingSearchDto::getLastPeriodWagMark, Comparator.nullsLast(Comparator.reverseOrder())));
        COMPARATOR.put(ScholarshipTerm::getCurriculumCompletionPriority, Comparator.comparing(
                ScholarshipApplicationRankingSearchDto::getCurriculumCompletion, Comparator.nullsLast(Comparator.reverseOrder())));
        COMPARATOR.put(ScholarshipTerm::getMaxAbsencesPriority, Comparator.comparing(
                ScholarshipApplicationRankingSearchDto::getAbsences, Comparator.nullsLast(Comparator.naturalOrder())));
    }



    private static Comparator<ScholarshipApplicationRankingSearchDto> comparatorForTerm(ScholarshipTerm term) {
        List<Comparator<ScholarshipApplicationRankingSearchDto>> comparators = new ArrayList<>(Collections.nCopies(Priority.values().length, null));
        for (Entry<Function<ScholarshipTerm, Classifier>, Comparator<ScholarshipApplicationRankingSearchDto>> me : COMPARATOR.entrySet()) {
            Classifier priority = me.getKey().apply(term);
            if (priority != null) {
                comparators.set(Priority.valueOf(EntityUtil.getCode(priority)).ordinal(), me.getValue());
            }
        }

        comparators = StreamUtil.toFilteredList(r -> r != null, comparators);
        if (comparators.isEmpty()) {
            return Comparator.comparing(ScholarshipApplicationRankingSearchDto::getLastName, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(ScholarshipApplicationRankingSearchDto::getFirstName, String.CASE_INSENSITIVE_ORDER);
        }

        Comparator<ScholarshipApplicationRankingSearchDto> comparator = null;
        for (Comparator<ScholarshipApplicationRankingSearchDto> c : comparators) {
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

    /**
     * Collects all school stipends which does not require an application
     *
     * @param schoolId
     * @return classifier codes
     */
    public ScholarshipNoApplicationDto getAllowedWithoutApplicationStipends(Long schoolId) {
        return ScholarshipNoApplicationDto.of(em.getReference(School.class, schoolId));
    }


    public void updateAllowedWithoutApplicationStipends(HoisUserDetails user,
            ScholarshipNoApplicationDto form) {
        EntityUtil.setUsername(user.getUsername(), em);
        School school = em.getReference(School.class, user.getSchoolId());
        final ClassifierCache classifierCache = new ClassifierUtil.ClassifierCache(classifierService);
        EntityUtil.bindEntityCollection(school.getScholarshipNoApplicationTypes(), type -> EntityUtil.getCode(type.getScholarshipEhis()),
                form.getAllowedEhisTypes(), newCode -> {
                    ScholarshipNoApplication entity = new ScholarshipNoApplication();
                    entity.setSchool(school);
                    entity.setScholarshipEhis(classifierCache.getByCode(newCode, MainClassCode.EHIS_STIPENDIUM));
                    return entity;
                });
    }

    private static class StudyResult {
        private final String grade;
        private final BigDecimal credits;

        public StudyResult(String grade, BigDecimal credits) {
            this.grade = grade;
            this.credits = credits;
        }

        public String getGrade() {
            return grade;
        }

        public BigDecimal getCredits() {
            return credits;
        }
    }

    private static class PeriodAverages {
        private BigDecimal averageMark;
        private BigDecimal wagMark;

        public BigDecimal getAverageMark() {
            return averageMark;
        }

        public void setAverageMark(BigDecimal averageMark) {
            this.averageMark = averageMark;
        }

        public BigDecimal getWagMark() {
            return wagMark;
        }

        public void setWagMark(BigDecimal wagMark) {
            this.wagMark = wagMark;
        }
    }

    private static class StudentResults {
        private BigDecimal averageMark;
        private BigDecimal lastPeriodMark;
        private BigDecimal wagMark;
        private BigDecimal lastPeriodWagMark;
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

        public BigDecimal getWagMark() {
            return wagMark;
        }

        public void setWagMark(BigDecimal wagMark) {
            this.wagMark = wagMark;
        }

        public BigDecimal getLastPeriodWagMark() {
            return lastPeriodWagMark;
        }

        public void setLastPeriodWagMark(BigDecimal lastPeriodWagMark) {
            this.lastPeriodWagMark = lastPeriodWagMark;
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

    public ScholarshipTerm copy(ScholarshipTerm term) {
        ScholarshipTerm copiedTerm = new ScholarshipTerm();
        BeanUtils.copyProperties(term, copiedTerm,
                // BaseEntity
                "id", "version", "inserted", "insertedBy", "changed", "changedBy",
                // Fields to not be copied by task
                "applicationStart", "applicationEnd", "paymentStart", "paymentEnd",
                // Status
                "isOpen",
                // OneToMany fields
                "scholarshipTermCourses", "scholarshipTermCurriculums", "scholarshipTermStudyLoads",
                "scholarshipTermStudyForms", "scholarshipApplications");
        copiedTerm.setIsOpen(Boolean.FALSE);
        copyOneToMany(term.getScholarshipTermCourses(), ScholarshipTermCourse::new, (nObj) -> {
            nObj.setScholarshipTerm(copiedTerm);
            copiedTerm.getScholarshipTermCourses().add(nObj);
        });
        copyOneToMany(term.getScholarshipTermCurriculums(), ScholarshipTermCurriculum::new, (nObj) -> {
            nObj.setScholarshipTerm(copiedTerm);
            copiedTerm.getScholarshipTermCurriculums().add(nObj);
        });
        copyOneToMany(term.getScholarshipTermStudyLoads(), ScholarshipTermStudyLoad::new, (nObj) -> {
            nObj.setScholarshipTerm(copiedTerm);
            copiedTerm.getScholarshipTermStudyLoads().add(nObj);
        });
        copyOneToMany(term.getScholarshipTermStudyForms(), ScholarshipTermStudyForm::new, (nObj) -> {
            nObj.setScholarshipTerm(copiedTerm);
            copiedTerm.getScholarshipTermStudyForms().add(nObj);
        });
        return EntityUtil.save(copiedTerm, em);
    }

    private static <T extends BaseEntityWithId> void copyOneToMany(List<T> objects, Supplier<T> supplier, Consumer<T> link) {
        objects.stream().forEach(obj -> {
            T newObj = supplier.get();
            BeanUtils.copyProperties(obj, newObj,
                    "id", "version", "inserted", "insertedBy", "changed", "changedBy",
                    "scholarshipTerm");
            link.accept(newObj);
        });
    }

    public List<ClassifierDto> getSchoolScholarshipTypes(Long schoolId, boolean all) {
        SchoolType schoolType = schoolService.schoolType(schoolId);
        School school = em.getReference(School.class, schoolId);
        List<ClassifierDto> result = new ArrayList<>();
        List<Classifier> stipends = classifierService.findAllByMainClassCode(MainClassCode.STIPTOETUS);

        boolean noDoctoralCurriculum = em.createNativeQuery(
                "select 1 from curriculum c join classifier cl on cl.code = c.orig_study_level_code where c.school_id = ?1 and cl.value ~ '^7.*$'")
                .setParameter(1, schoolId)
                .setMaxResults(1).getResultList().isEmpty();

        stipends.stream().filter(cl -> {
            if (all) {
                return true; // for readonly
            }
            if (!ClassifierUtil.isValid(cl)) {
                return false;
            }
            if (ClassifierUtil.oneOf(cl, ScholarshipType.STIPTOETUS_DOKTOR) && noDoctoralCurriculum) {
                return false;
            }
            if (cl.isHigher() && schoolType.isHigher()) {
                return true;
            }
            if (cl.isVocational() && schoolType.isVocational()) {
                return true;
            }
            return false;
        }).map(ClassifierDto::of).collect(Collectors.toCollection(() -> result));

        if (all) {
            classifierService.findAllByMainClassCode(MainClassCode.EHIS_STIPENDIUM).stream().map(ClassifierDto::of)
                    .collect(Collectors.toCollection(() -> result));
        } else {
            school.getScholarshipNoApplicationTypes().stream().map(type -> type.getScholarshipEhis())
                    .map(ClassifierDto::of).collect(Collectors.toCollection(() -> result));
        }
        return result;
    }

    public byte[] scholarshipsGrantsRankingXls(HoisUserDetails user, ScholarshipApplicationRankingSearchCommand command) {
        return xlsService.generate("scholarshipApplicationRankingGrants.xls", getXlsData(user, command));
    }

    public byte[] scholarshipsRankingXls(HoisUserDetails user, ScholarshipApplicationRankingSearchCommand command) {
        return xlsService.generate("scholarshipApplicationRanking.xls", getXlsData(user, command));
    }
    
    public byte[] scholarshipsDoctorRankingXls(HoisUserDetails user, ScholarshipApplicationRankingSearchCommand command) {
        return xlsService.generate("scholarshipApplicationRankingDoctor.xls", getXlsData(user, command));
    }
    
    private Map<String, Object> getXlsData(HoisUserDetails user, ScholarshipApplicationRankingSearchCommand command) {
        ScholarshipTermApplicationRankingSearchDto dto = applicationsRanking(user, command);
        Map<String, Object> data = new HashMap<>();
        data.put("content", dto != null ? dto.getApplications() : new ArrayList<>());
        data.put("type", command.getType());
        return data;
    }
}
