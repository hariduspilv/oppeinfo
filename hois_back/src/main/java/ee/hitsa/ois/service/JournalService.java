package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;
import javax.validation.Validator;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.curriculum.CurriculumModuleOutcome;
import ee.hitsa.ois.domain.student.StudentCurriculumModuleOutcomesResult;
import ee.hitsa.ois.domain.student.StudentCurriculumModuleOutcomesResultHistory;
import ee.hitsa.ois.enums.ApelApplicationStatus;
import ee.hitsa.ois.web.commandobject.timetable.JournalOutcomeForm;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleOutcomeResult;
import ee.hitsa.ois.web.dto.timetable.JournalAutomaticAddStudentsResult;
import ee.hitsa.ois.web.dto.timetable.JournalEntryByDateBaseDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryByDateXlsDto;
import ee.hitsa.ois.web.dto.timetable.JournalOutcomeDto;
import ee.hitsa.ois.web.dto.timetable.StudentCurriculumModuleOutcomesResultDto;
import ee.hitsa.ois.web.dto.timetable.StudentCurriculumModuleOutcomesResultForm;
import ee.hitsa.ois.web.dto.timetable.StudentCurriculumModuleOutcomesResultHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.common.base.Objects;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.student.StudentVocationalResult;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalCapacity;
import ee.hitsa.ois.domain.timetable.JournalEntry;
import ee.hitsa.ois.domain.timetable.JournalEntryCapacityType;
import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.domain.timetable.JournalEntryStudentHistory;
import ee.hitsa.ois.domain.timetable.JournalEntryStudentLessonAbsence;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.enums.Absence;
import ee.hitsa.ois.enums.ApplicationStatus;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.JournalStatus;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.OccupationalGrade;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.SupportServiceType;
import ee.hitsa.ois.message.StudentRemarkCreated;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.JournalRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JournalUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.StudyMaterialUserRights;
import ee.hitsa.ois.validation.JournalEntryValidation;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.SchoolCapacityTypeCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalEndDateCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntryForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntryQuickUpdateForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntryStudentForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntryStudentLessonAbsenceForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalReviewForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalStudentsCommand;
import ee.hitsa.ois.web.commandobject.timetable.OtherStudentsSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.StudyYearSearchDto;
import ee.hitsa.ois.web.dto.student.StudentAbsenceDto;
import ee.hitsa.ois.web.dto.studymaterial.CapacityHoursDto;
import ee.hitsa.ois.web.dto.studymaterial.JournalLessonHoursDto;
import ee.hitsa.ois.web.dto.timetable.JournalDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryByDateDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryLessonInfoDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryStudentAcceptedAbsenceDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryStudentHistoryDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryStudentLessonAbsenceDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryStudentResultDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryTableDto;
import ee.hitsa.ois.web.dto.timetable.JournalModuleDescriptionDto;
import ee.hitsa.ois.web.dto.timetable.JournalSearchDto;
import ee.hitsa.ois.web.dto.timetable.JournalStudentApelResultDto;
import ee.hitsa.ois.web.dto.timetable.JournalStudentDto;
import ee.hitsa.ois.web.dto.timetable.JournalStudentIndividualCurriculumDto;
import ee.hitsa.ois.web.dto.timetable.JournalStudentRemarkDto;
import ee.hitsa.ois.web.dto.timetable.JournalXlsDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalAbsenceDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalEntryDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalEntryLessonAbsenceDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalEntryPreviousResultDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalResultDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalStudyDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalStudyListDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalTaskDto;
import ee.hitsa.ois.web.dto.timetable.StudentJournalTaskListDto;

@Transactional
@Service
public class JournalService {

    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private Validator validator;
    @Autowired
    private XlsService xlsService;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private AutomaticMessageService automaticMessageService;

    private static final List<String> testEntryTypeCodes = EnumUtil.toNameList(JournalEntryType.SISSEKANNE_H,
            JournalEntryType.SISSEKANNE_L, JournalEntryType.SISSEKANNE_E, JournalEntryType.SISSEKANNE_I,
            JournalEntryType.SISSEKANNE_R, JournalEntryType.SISSEKANNE_P);

    private static final String JOURNAL_LIST_FROM = "from journal j " +
            "join journal_omodule_theme jot on j.id=jot.journal_id " +
            "join lesson_plan_module lpm on jot.lesson_plan_module_id=lpm.id " +
            "join lesson_plan lp on lpm.lesson_plan_id=lp.id " +
            "join student_group sg on lp.student_group_id=sg.id " +
            "join curriculum_version_omodule_theme cvot on jot.curriculum_version_omodule_theme_id=cvot.id " +
            "join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id=cvo.id " +
            "join curriculum_module cm on cvo.curriculum_module_id=cm.id " +
            "join curriculum_version cv on cvo.curriculum_version_id=cv.id " +
            "join curriculum c on cm.curriculum_id=c.id " +
            "join classifier cl on cm.module_code=cl.code " +
            "left join journal_teacher jt on j.id=jt.journal_id " +
            "left join teacher t on jt.teacher_id=t.id " +
            "left join person p on t.person_id=p.id";

    private static final String JOURNAL_LIST_SELECT = "j.id, string_agg(distinct sg.code, ', ') as student_groups, j.name_et, " +
            "string_agg(distinct p.firstname || ' ' || p.lastname, ', ') as teachers, " +
            "string_agg(distinct cm.name_et || ' - ' || cl.name_et || ' (' || cv.code || ')', ', ') as modules_et, " +
            "string_agg(distinct cm.name_en || ' - ' || cl.name_en || ' (' || cv.code || ')', ', ') as modules_en, " +
            "j.status_code, string_agg(distinct c.code, ', '), j.is_review_ok, j.review_date, " +
            "(select count(js.id) from journal_student js where js.journal_id = j.id) nr_of_students";

    private static final String STUDENT_JOURNAL_FROM = "from journal_student js "
            + "join journal j on j.id = js.journal_id "
            + "join study_year sy on j.study_year_id = sy.id "
            + "join student s on s.id = js.student_id "
            + "left join journal_teacher jt on j.id = jt.journal_id "
            + "left join teacher t on jt.teacher_id = t.id "
            + "left join person p on t.person_id = p.id "
            + "left join journal_omodule_theme jot on j.id = jot.journal_id "
            + "left join curriculum_version_omodule_theme cvot on jot.curriculum_version_omodule_theme_id = cvot.id "
            + "left join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id = cvo.id "
            + "left join curriculum_module cm on cvo.curriculum_module_id = cm.id "
            + "left join curriculum c on cm.curriculum_id = c.id "
            + "left join classifier mcl on cm.module_code = mcl.code";

    private static final String STUDENT_JOURNAL_SELECT = "j.id, j.name_et, j.study_year_id, sy.year_code, "
            + "string_agg(distinct p.firstname || ' ' || p.lastname, ', ') as teachers, "
            + "string_agg(distinct cm.name_et || ' - ' || mcl.name_et || ' (' || c.code || ')', ', ') as module_et, "
            + "string_agg(distinct cm.name_en || ' - ' || mcl.name_en || ' (' || c.code || ')', ', ') as module_en";

    private static final String JOURNAL_OUTCOMES_FROM = "from journal j "
            + "join journal_omodule_theme jot on jot.journal_id = j.id "
            + "join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id "
            + "join curriculum_version_omodule_outcomes cvoo on cvoo.curriculum_version_omodule_theme_id = cvot.id "
            + "join curriculum_module_outcomes cmo on cmo.id = cvoo.curriculum_module_outcomes_id "
            + "join curriculum_module cm on cm.id = cmo.curriculum_module_id";

    public Page<JournalSearchDto> search(HoisUserDetails user, JournalSearchCommand command, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(JOURNAL_LIST_FROM).sort(pageable);

        qb.requiredCriteria("j.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("c.id in (:userCurriculumIds)", "userCurriculumIds", user.getCurriculumIds());
        }

        qb.requiredCriteria("j.study_year_id = :studyYear", "studyYear", command.getStudyYear());

        if (user.isTeacher()) {
            command.setTeacher(user.getTeacherId());
        }
        if (command.getTeacher() != null) {
            if (Boolean.TRUE.equals(command.getOnlyMyJournals())) {
                qb.filter("j.id in (select j.id from journal j " + 
                        "join journal_teacher jt on j.id=jt.journal_id " + 
                        "where jt.teacher_id=" + command.getTeacher() + ")");
            } else {
                qb.filter("j.id in (select j.id from journal j " + 
                        "join journal_omodule_theme jot on j.id=jot.journal_id " + 
                        "join lesson_plan_module lpm on jot.lesson_plan_module_id=lpm.id " + 
                        "join lesson_plan lp on lpm.lesson_plan_id=lp.id " + 
                        "join student_group sg on lp.student_group_id=sg.id " +
                        "left join journal_teacher jt on j.id=jt.journal_id " +
                        "where lpm.teacher_id=" + command.getTeacher() + " or sg.teacher_id=" + command.getTeacher() +
                        " or jt.teacher_id=" + command.getTeacher() + ")");
            }
        }

        qb.optionalContains("j.name_et", "name", command.getJournalName());

        if (command.getStudentGroup() != null) {
            qb.filter("j.id in (select j.id from journal j " +
                    "join journal_omodule_theme jot on j.id=jot.journal_id " + 
                    "join lesson_plan_module lpm on jot.lesson_plan_module_id=lpm.id " + 
                    "join lesson_plan lp on lpm.lesson_plan_id=lp.id " + 
                    "join student_group sg on lp.student_group_id=sg.id " + 
                    "where sg.id=" + command.getStudentGroup() + ")");
        }

        if (command.getModule() != null) {
            String modules = command.getModule().stream().map(m -> String.valueOf(m)).collect(Collectors.joining(","));
            qb.filter("j.id in (select j.id from journal j " +
            "join journal_omodule_theme jot on j.id=jot.journal_id " +
            "join curriculum_version_omodule_theme cvot on jot.curriculum_version_omodule_theme_id=cvot.id " +
            "join curriculum_version_omodule cvo on cvot.curriculum_version_omodule_id=cvo.id " +
            "where cvo.id in (" + modules + "))"); 
        }

        qb.optionalCriteria("j.status_code = :status", "status", command.getStatus());

        qb.groupBy("j.id");

        return JpaQueryUtil.pagingResult(qb, JOURNAL_LIST_SELECT, em, pageable).map(r -> {
            JournalSearchDto dto = new JournalSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setStudentGroups(resultAsString(r, 1));
            dto.setNameEt(resultAsString(r, 2));
            dto.setTeachers(resultAsString(r, 3));
            dto.setModules(new AutocompleteResult(null, resultAsString(r, 4), resultAsString(r, 5)));
            dto.setStatus(resultAsString(r, 6));
            dto.setCurriculums(resultAsString(r, 7));
            dto.setIsReviewOk(resultAsBoolean(r, 8));
            dto.setReviewDate(resultAsLocalDate(r, 9));
            
            Journal journal = em.getReference(Journal.class, resultAsLong(r, 0));
            dto.setPlannedHours(Integer.valueOf(journal.getJournalCapacities().stream().mapToInt(it -> it.getHours() == null ? 0 : it.getHours().intValue()).sum()));
            dto.setUsedHours(Integer.valueOf(journal.getJournalEntries().stream().mapToInt(it -> it.getLessons() == null ? 0 : it.getLessons().intValue()).sum()));
            dto.setStudentCount(resultAsLong(r, 10));
            dto.setCanEdit(Boolean.valueOf(JournalUtil.hasPermissionToChange(user, journal)));
            return dto;
        });
    }

    public JournalDto get(HoisUserDetails user, Journal journal) {
        JournalDto dto = JournalDto.of(journal);
        dto.setCanBeConfirmed(Boolean.valueOf(JournalUtil.canConfirm(user, journal)));
        dto.setCanBeUnconfirmed(Boolean.valueOf(JournalUtil.canUnconfirm(user, journal)));
        dto.setCanEdit(Boolean.valueOf(JournalUtil.hasPermissionToChange(user, journal)));
        dto.setCanViewReview(Boolean.valueOf(JournalUtil.hasPermissionToViewReview(user, journal)));
        dto.setCanReview(Boolean.valueOf(JournalUtil.hasPermissionToReview(user, journal)));
        dto.setCanConnectStudyMaterials(Boolean.valueOf(StudyMaterialUserRights.canEditJournal(user, journal)));
        dto.setIncludesOutcomes(Boolean.valueOf(!journalOutcomeIds(journal.getId()).isEmpty()));
        dto.setFinalEntryAllowed(Boolean.valueOf(finalEntryAllowed(journal)));
        dto.setLessonHours(usedHours(journal));
        setStudentIndividualCurriculums(dto);

        if (Boolean.TRUE.equals(dto.getCanViewReview())) {
            dto.setIsReviewOk(journal.getReviewOk());
            dto.setReviewDate(journal.getReviewDate());
            dto.setReviewInfo(journal.getReviewInfo());
        }
        return dto;
    }

    public boolean canAddFinalEntry(Journal journal) {
        if (finalEntryAllowed(journal)) {
            // no final entry already added
            return em.createNativeQuery("select 1 from journal j " +
                    "join journal_entry je on je.journal_id = j.id " +
                    "where j.id = :journalId and je.entry_type_code = :entryCode")
                    .setParameter("journalId", journal.getId())
                    .setParameter("entryCode", JournalEntryType.SISSEKANNE_L.name())
                    .setMaxResults(1).getResultList().isEmpty();
        }
        return false;
    }

    public boolean finalEntryAllowed(Journal journal) {
        boolean noAssessment = EntityUtil.getNullableCode(journal.getAssessment()) == null;
        return !noAssessment && !JournalUtil.allThemesAssessedByOutcomes(journal);
    }

    private static JpaNativeQueryBuilder indokIndividualCurriculums(Long journalId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j " +
                "join journal_student js on js.journal_id = j.id " +
                "join student s on s.id = js.student_id " +
                "join person p on p.id = s.person_id " +
                "join directive_student ds on ds.student_id = s.id "+
                "join directive d on d.id = ds.directive_id " +
                "join directive_student_module dsm on dsm.directive_student_id = ds.id " +
                "join curriculum_version_omodule cvo on cvo.id = dsm.curriculum_version_omodule_id " +
                "join curriculum_module cm on cm.id = cvo.curriculum_module_id " +
                "join study_year sy on sy.id = j.study_year_id " +
                "left join (directive_student ds_lop join directive d_lop on d_lop.id = ds_lop.directive_id and d_lop.type_code = :lopDirectiveType " +
                "and d_lop.status_code = :directiveStatus) on ds_lop.directive_student_id = ds.id and ds_lop.canceled = false");

        qb.requiredCriteria("j.id = :journalId", "journalId", journalId);
        qb.requiredCriteria("d.type_code = :directiveType", "directiveType", DirectiveType.KASKKIRI_INDOK);
        qb.requiredCriteria("d.status_code = :directiveStatus", "directiveStatus",
                DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        qb.filter("ds.canceled = false and coalesce(j.end_date, sy.end_date) >= ds.start_date");
        qb.filter("cm.id in (select cm2.id from journal_omodule_theme jot " +
                "join curriculum_version_omodule_theme cvot on jot.curriculum_version_omodule_theme_id = cvot.id " +
                "join curriculum_version_omodule cvo2 on cvo2.id = cvot.curriculum_version_omodule_id " +
                "join curriculum_module cm2 on cm2.id = cvo2.curriculum_module_id " +
                "where jot.journal_id = j.id)");
        qb.parameter("lopDirectiveType", DirectiveType.KASKKIRI_INDOKLOP.name());
        return qb;
    }

    private static JpaNativeQueryBuilder tugiIndividualCurriculums(Long journalId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j2 " +
                "join journal_student js2 on js2.journal_id = j2.id " +
                "join student s2 on s2.id = js2.student_id " +
                "join person p2 on p2.id = s2.person_id " +
                "join directive_student ds2 on ds2.student_id = s2.id "+
                "join directive d2 on d2.id = ds2.directive_id " +
                "join application a on a.id = ds2.application_id " +
                "join application_support_service ass on ass.application_id = a.id " +
                "join application_support_service_module assm on assm.application_support_service_id = ass.id " +
                "join curriculum_version_omodule cvo3 on cvo3.id = assm.curriculum_version_omodule_id " +
                "join curriculum_module cm3 on cm3.id = cvo3.curriculum_module_id " +
                "join study_year sy2 on sy2.id = j2.study_year_id " +
                "left join (directive_student ds_lop2 join directive d_lop2 on d_lop2.id = ds_lop2.directive_id " +
                "and d_lop2.type_code = :lopDirectiveType2 and d_lop2.status_code = :directiveStatus) " +
                "on ds_lop2.directive_student_id = ds2.id and ds_lop2.canceled = false");

        qb.requiredCriteria("j2.id = :journalId", "journalId", journalId);
        qb.requiredCriteria("d2.type_code = :directiveType2", "directiveType2", DirectiveType.KASKKIRI_TUGI);
        qb.requiredCriteria("d2.status_code = :directiveStatus", "directiveStatus",
                DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD);
        qb.requiredCriteria("ass.support_service_code = :supportServiceCode", "supportServiceCode",
                SupportServiceType.TUGITEENUS_1);
        qb.filter("ds2.canceled = false and coalesce(j2.end_date, sy2.end_date) >= ds2.start_date");
        qb.filter("cm3.id in (select cm4.id from journal_omodule_theme jot2 " +
                "join curriculum_version_omodule_theme cvot2 on jot2.curriculum_version_omodule_theme_id = cvot2.id " +
                "join curriculum_version_omodule cvo4 on cvo4.id = cvot2.curriculum_version_omodule_id " +
                "join curriculum_module cm4 on cm4.id = cvo4.curriculum_module_id " +
                "where jot2.journal_id = j2.id)");
        qb.parameter("lopDirectiveType2", DirectiveType.KASKKIRI_TUGILOPP.name());
        return qb;
    }

    private void setStudentIndividualCurriculums(JournalDto journalDto) {
        List<JournalStudentIndividualCurriculumDto> individualCurriculums = new ArrayList<>();
        Long journalId = journalDto.getId();

        JpaNativeQueryBuilder qb = indokIndividualCurriculums(journalId);
        String indokQuery = qb.querySql("s.id student_id, p.firstname, p.lastname, cm.id curriculum_id, "
                + "cm.name_et cm_name_et, cm.name_en cm_name_en, dsm.add_info, ds.start_date, "
                + "coalesce(ds_lop.start_date, ds.end_date) end_date", false);
        Map<String, Object> parameters = new HashMap<>(qb.queryParameters());

        qb = tugiIndividualCurriculums(journalId);
        String tugiQuery = qb.querySql("s2.id student_id, p2.firstname, p2.lastname, cm3.id curriculum_id, "
                + "cm3.name_et cm_name_et, cm3.name_en cm_name_en, assm.add_info, ds2.start_date, "
                + "coalesce(ds_lop2.start_date, ds2.end_date) end_date", false);
        parameters.putAll(qb.queryParameters());

        qb = new JpaNativeQueryBuilder("from (" + indokQuery + " union all " + tugiQuery + ") as ic");
        List<?> data = qb.select("student_id, firstname, lastname, curriculum_id, "
                + "cm_name_et, cm_name_en, add_info, start_date, end_date", em, parameters)
                .getResultList();
        
        Map<Long, List<Object>> dataByStudents = StreamUtil.nullSafeList(data).stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.toList()));
        for (Long studentId : dataByStudents.keySet()) {
            List<Object> modules = dataByStudents.get(studentId);
            List<JournalModuleDescriptionDto> distinctionDtos = new ArrayList<>();
            for (Object module : modules) {
                JournalModuleDescriptionDto distinction = new JournalModuleDescriptionDto();
                distinction.setNameEt(resultAsString(module, 4));
                distinction.setNameEn(resultAsString(module, 5));
                distinction.setAddInfo(resultAsString(module, 6));
                distinction.setStartDate(resultAsLocalDate(module, 7));
                distinction.setEndDate(resultAsLocalDate(module, 8));
                distinctionDtos.add(distinction);
            }

            JournalStudentIndividualCurriculumDto individualCurriculumDto = new JournalStudentIndividualCurriculumDto();
            individualCurriculumDto.setStudentId(studentId);
            String fullname = PersonUtil.fullname(resultAsString(dataByStudents.get(studentId).get(0), 1),
                    resultAsString(dataByStudents.get(studentId).get(0), 2));
            individualCurriculumDto.setFullname(fullname);
            individualCurriculumDto.setDistinctions(distinctionDtos);
            individualCurriculums.add(individualCurriculumDto);
        }
        journalDto.setIndividualCurriculums(individualCurriculums);
    }

    public Journal confirm(Journal journal) {
        journal.setStatus(em.getReference(Classifier.class, JournalStatus.PAEVIK_STAATUS_K.name()));
        return EntityUtil.save(journal, em);
    }

    public Journal unconfirm(Journal journal) {
        journal.setStatus(em.getReference(Classifier.class, JournalStatus.PAEVIK_STAATUS_T.name()));
        return EntityUtil.save(journal, em);
    }

    // TODO: rewrite using JpaNativeQueryBuilder, suitedStudents method is already rewritten
    public Page<JournalStudentDto> otherStudents(HoisUserDetails user, Long journalId, OtherStudentsSearchCommand command,
            Pageable pageable) {
        return studentRepository.findAll((root, query, cb) -> {
            root.join("person", JoinType.INNER);
            root.join("studentGroup", JoinType.LEFT);
            root.join("curriculumVersion", JoinType.LEFT);
            
            Subquery<Long> directiveQuery = query.subquery(Long.class);
            Root<Directive> directiveRoot = directiveQuery.from(Directive.class);
            Join<Object, Object> directiveStudentsJoin = directiveRoot.join("students", JoinType.INNER);
            directiveQuery.select(directiveStudentsJoin.get("student").get("id"))
                    .where(cb.and(cb.or(cb.equal(directiveRoot.get("isHigher"), Boolean.FALSE), cb.isNull(directiveRoot.get("isHigher"))),
                            cb.equal(directiveRoot.get("type").get("code"), DirectiveType.KASKKIRI_KYLALIS.name()),
                            cb.equal(directiveStudentsJoin.get("student").get("id"), root.get("id"))));
            
            Subquery<Long> curriculumQuery = query.subquery(Long.class);
            Root<CurriculumVersion> curriculumRoot = curriculumQuery.from(CurriculumVersion.class);
            Join<Object, Object> curriculumStudentsJoin = curriculumRoot.join("curriculum", JoinType.INNER);
            curriculumQuery.select(curriculumStudentsJoin.get("id"))
                    .where(cb.and(cb.or(cb.equal(curriculumStudentsJoin.get("higher"), Boolean.FALSE),cb.isNull(curriculumStudentsJoin.get("higher"))),
                            cb.isNotNull(root.get("curriculumVersion")), cb.equal(curriculumRoot.get("id"), root.get("curriculumVersion").get("id"))));

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), user.getSchoolId()));
            if (user.isLeadingTeacher()) {
                filters.add(root.get("curriculumVersion").get("curriculum").get("id").in(user.getCurriculumIds()));
            }
            filters.add(cb.or(cb.equal(root.get("status").get("code"), StudentStatus.OPPURSTAATUS_A.name()),
                    cb.equal(root.get("status").get("code"), StudentStatus.OPPURSTAATUS_O.name()),
                    cb.equal(root.get("status").get("code"), StudentStatus.OPPURSTAATUS_V.name())));
            // check if student is higher by directive or curriculum
            filters.add(cb.or(cb.exists(directiveQuery), cb.exists(curriculumQuery)));

            if (StringUtils.hasText(command.getStudentName())) {
                List<Predicate> name = new ArrayList<>();
                propertyContains(() -> root.get("person").get("firstname"), cb, command.getStudentName(), name::add);
                propertyContains(() -> root.get("person").get("lastname"), cb, command.getStudentName(), name::add);
                name.add(cb.like(
                        cb.concat(cb.upper(root.get("person").get("firstname")),
                                cb.concat(" ", cb.upper(root.get("person").get("lastname")))),
                        JpaQueryUtil.toContains(command.getStudentName())));
                if (!name.isEmpty()) {
                    filters.add(cb.or(name.toArray(new Predicate[name.size()])));
                }
            }
            
            if(!CollectionUtils.isEmpty(command.getStudentId())) {
                filters.add(cb.not(root.get("id").in(command.getStudentId())));
            }
            
            if (command.getStudentGroupId() != null) {
                filters.add(cb.equal(root.get("studentGroup").get("id"), command.getStudentGroupId()));
            }

            Subquery<Long> studentsQuery = query.subquery(Long.class);
            Root<Journal> journalRoot = studentsQuery.from(Journal.class);
            Join<Object, Object> journalStudentsJoin = journalRoot.join("journalStudents", JoinType.LEFT);
            studentsQuery.select(journalStudentsJoin.get("student").get("id"))
                    .where(cb.and(cb.equal(journalRoot.get("id"), journalId),
                            cb.equal(journalStudentsJoin.get("student").get("id"), root.get("id"))));
            filters.add(cb.not(cb.exists(studentsQuery)));

            // who has no positive result in given module
            Journal journal = journalRepository.findOne(journalId);
            Set<Long> modules = StreamUtil.toMappedSet(
                    t -> EntityUtil.getId(t.getCurriculumVersionOccupationModuleTheme().getModule().getCurriculumModule()),
                    journal.getJournalOccupationModuleThemes());

            Subquery<Long> vocationalResultsQuery = query.subquery(Long.class);
            Root<StudentVocationalResult> vocationalResultRoot = vocationalResultsQuery
                    .from(StudentVocationalResult.class);
            vocationalResultsQuery.select(vocationalResultRoot.get("student").get("id")).where(
                    vocationalResultRoot.get("grade").get("code").in(OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE),
                    vocationalResultRoot.get("curriculumVersionOmodule").get("curriculumModule").get("id").in(modules));
            filters.add(cb.not(root.get("id").in(vocationalResultsQuery)));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(JournalStudentDto::of);
    }

    public List<JournalStudentDto> suitedStudents(HoisUserDetails user, Long journalId) {
        JpaNativeQueryBuilder qb = suitedStudentsQb(user);
        qb.requiredCriteria("j.id = :journalId", "journalId", journalId);

        List<?> data = qb.select("s.id", em).getResultList();
        Set<Long> studentIds = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);

        if (!studentIds.isEmpty()) {
            qb = new JpaNativeQueryBuilder("from student s "
                    + "join person p on s.person_id = p.id "
                    + "left join student_group sg on s.student_group_id = sg.id "
                    + "join curriculum_version cv on s.curriculum_version_id = cv.id "
                    + "join curriculum c on cv.curriculum_id = c.id");
            qb.requiredCriteria("s.id in (:studentIds)", "studentIds", studentIds);

            data = qb.select("s.id s_id, p.firstname, p.lastname, sg.code sg_code, c.id c_id, cv.code cv_code, "
                    + "c.name_et c_name_et, c.name_en c_name_en, s.status_code", em).getResultList();

            return StreamUtil.toMappedList(r -> new JournalStudentDto(resultAsLong(r, 0), resultAsString(r, 1),
                    resultAsString(r, 2), resultAsString(r, 3), resultAsLong(r, 4), resultAsString(r, 5),
                    resultAsString(r, 6), resultAsString(r, 8)), data);
        }
        return new ArrayList<>();
    }

    private JpaNativeQueryBuilder suitedStudentsQb(HoisUserDetails user) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j "
                + "join journal_omodule_theme jot on jot.journal_id = j.id "
                + "join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id "
                + "join lesson_plan lp on lp.id = lpm.lesson_plan_id "
                + "join student_group sg on sg.id = lp.student_group_id "
                + "join student s on s.student_group_id = sg.id "
                + "join curriculum_version_omodule cvo on cvo.curriculum_version_id = s.curriculum_version_id "
                    + "and cvo.id = lpm.curriculum_version_omodule_id "
                + "join curriculum_version_omodule_theme cvot on cvot.curriculum_version_omodule_id = cvo.id "
                    + "and cvot.id = jot.curriculum_version_omodule_theme_id "
                + "join curriculum_version cv on cv.id = cvo.curriculum_version_id");

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("s.status_code in (:activeStudents)", "activeStudents",
                StudentStatus.STUDENT_STATUS_ACTIVE);
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("cv.curriculum_id in (:userCurriculumIds)", "userCurriculumIds", user.getCurriculumIds());
        } else if (user.isTeacher()) {
            qb.requiredCriteria("exists (select jt.id from journal_teacher jt "
                    + "where jt.journal_id = j.id and jt.teacher_id = :teacherId and jt.is_filler = true)",
                    "teacherId", user.getTeacherId());
        }

        // who has no positive result in given module
        qb.filter("s.id not in (select js.student_id from journal_student js where js.journal_id = j.id)");
        qb.filter("s.id not in (select svr.student_id from student_vocational_result svr "
                + "join curriculum_version_omodule cvo2 on cvo2.id = svr.curriculum_version_omodule_id "
                    + "or cvo2.id = any(svr.arr_modules) "
                + "join curriculum_module cm on cm.id = cvo2.curriculum_module_id "
                + "where svr.grade_code in (:positiveGrades)"
                    + "and cm.id in (select cvo3.curriculum_module_id from curriculum_version_omodule_theme cvot3 "
                    + "join curriculum_version_omodule cvo3 on cvo3.id = cvot3.curriculum_version_omodule_id "
                    + "where cvot3.id = cvot.id))");
        qb.parameter("positiveGrades", OccupationalGrade.OCCUPATIONAL_GRADE_POSITIVE);

        // who has not replaced theme in RAKKAVA application
        qb.filter("s.id not in (select a.student_id from application_omodule_theme aot "
                + "join application a on a.id = aot.application_id "
                + "where a.student_id = s.id and a.type_code = :applicationType and a.status_code = :applicationStatus "
                    + "and aot.curriculum_version_omodule_theme_id = jot.curriculum_version_omodule_theme_id)");
        qb.parameter("applicationType", ApplicationType.AVALDUS_LIIK_RAKKAVA.name());
        qb.parameter("applicationStatus", ApplicationStatus.AVALDUS_STAATUS_KINNITATUD.name());

        // themes has no positive grade and is not replaced in apel application
        qb.filter("s.id not in ( "
                + "select js2.student_id from journal j2 "
                    + "join journal_omodule_theme jot2 on jot2.journal_id = j2.id "
                    + "join journal_student js2 on js2.journal_id = j2.id and js2.student_id = s.id "
                    + "join journal_entry je on je.journal_id = j2.id and je.entry_type_code = :finalEntry "
                    + "join journal_entry_student jes on jes.journal_entry_id = je.id and jes.journal_student_id = js2.id "
                    + "where jes.grade_code in (:positiveGrades) "
                    + "and jot2.curriculum_version_omodule_theme_id = cvot.id "
                + "union all "
                + "select aa.student_id from apel_application aa "
                    + "join apel_application_record aar on aar.apel_application_id = aa.id "
                    + "join apel_application_informal_subject_or_module aai on aai.apel_application_record_id = aar.id "
                    + "where aa.status_code = :apelStatus and aa.student_id = s.id and aai.transfer "
                    + "and aai.curriculum_version_omodule_theme_id = cvot.id "
                + "union all "
                + "select aa2.student_id from apel_application aa2 "
                    + "join apel_application_record aar2 on aar2.apel_application_id = aa2.id "
                    + "join apel_application_formal_subject_or_module aaf on aaf.apel_application_record_id = aar2.id "
                    + "join apel_application_formal_replaced_subject_or_module aarf on aarf.apel_application_record_id = aar2.id "
                    + "where aa2.status_code = :apelStatus and aa2.student_id = s.id and aaf.transfer "
                    + "and aarf.curriculum_version_omodule_theme_id = cvot.id)");
        qb.parameter("finalEntry", JournalEntryType.SISSEKANNE_L.name());
        qb.parameter("apelStatus", ApelApplicationStatus.VOTA_STAATUS_C.name());

        // has no positive results in all of the connected outcomes when theme is graded by them
        qb.filter("(coalesce(cvot.is_module_outcomes, false) = false or cvot.is_module_outcomes "
                + "and (select count(cmo.id) != count(scmor.id) from curriculum_version_omodule_theme cvot4 "
                    + "join curriculum_version_omodule_outcomes cvoo on cvoo.curriculum_version_omodule_theme_id = cvot4.id "
                    + "join curriculum_module_outcomes cmo on cmo.id = cvoo.curriculum_module_outcomes_id "
                    + "left join student_curriculum_module_outcomes_result scmor on scmor.student_id = s.id "
                    + "and scmor.curriculum_module_outcomes_id = cmo.id "
                    + "and scmor.grade_code in (:positiveGrades) "
                    + "where cvot4.id = jot.curriculum_version_omodule_theme_id))");
        return qb;
    }

    public Journal saveEndDate(Journal journal, JournalEndDateCommand command) {
        if (command.getEndDate() != null) {
            journal.setEndDate(command.getEndDate());
        }
        return EntityUtil.save(journal, em);
    }

    public Journal saveJournalReview(Journal journal, JournalReviewForm journalReviewForm) {
        if (Objects.equal(journal.getReviewOk(), journalReviewForm.getIsReviewOk())
                && Objects.equal(journal.getReviewInfo(), journalReviewForm.getReviewInfo())) {
            throw new ValidationFailedException("journal.messages.reviewIsNotChanged");
        }
        journal.setReviewOk(journalReviewForm.getIsReviewOk());
        journal.setReviewInfo(journalReviewForm.getReviewInfo());
        journal.setReviewDate(LocalDate.now());
        return EntityUtil.save(journal, em);
    }

    public Journal addStudentsToJournal(HoisUserDetails user, Journal journal, JournalStudentsCommand command) {
        Set<Long> existingStudents = journal.getJournalStudents().stream().map(js -> EntityUtil.getId(js.getStudent()))
                .collect(Collectors.toSet());
        
        // if final result entry exists already and student has transferred
        // journal module results then set transferred result as final result
        JournalEntry finalResultEntry = journalFinalResultEntry(journal);
        Map<Long, List<JournalStudentApelResultDto>> journalStudentApelResults = new HashMap<>();
        if (finalResultEntry != null) {
            Set<CurriculumVersionOccupationModuleTheme> themes = StreamUtil.toMappedSet(
                    t -> t.getCurriculumVersionOccupationModuleTheme(), journal.getJournalOccupationModuleThemes());
            Set<Long> themeIds = StreamUtil.toMappedSet(t -> EntityUtil.getId(t), themes);
            Set<Long> omoduleIds = StreamUtil.toMappedSet(t -> EntityUtil.getId(t.getModule()), themes);
            Set<Long> students = StreamUtil.toMappedSet(r -> r, command.getStudents());
            journalStudentApelResults = journalStudentApelResults(omoduleIds, themeIds, students);
        }
        
        for (Long student : command.getStudents()) {
            if (!existingStudents.contains(student)) {
                JournalStudent js = JournalStudent.of(em.getReference(Student.class, student));
                journal.getJournalStudents().add(js);
                EntityUtil.save(js, em);
                if (finalResultEntry != null) {
                    setApelResultAsFinalResult(user, finalResultEntry, js, journalStudentApelResults);
                }
            }
        }
        return EntityUtil.save(journal, em);
    }

    private JournalEntry journalFinalResultEntry(Journal journal) {
        List<JournalEntry> data = em
                .createQuery("select je from JournalEntry je where je.journal.id = ?1 and je.entryType.code = ?2",
                        JournalEntry.class)
                .setParameter(1, EntityUtil.getId(journal))
                .setParameter(2, JournalEntryType.SISSEKANNE_L.name())
                .getResultList();
        return data.isEmpty() ? null : data.get(0);
    }

    private void setApelResultAsFinalResult(HoisUserDetails user, JournalEntry finalResultEntry,
            JournalStudent addedStudent, Map<Long, List<JournalStudentApelResultDto>> journalStudentApelResults) {
        List<JournalStudentApelResultDto> results = journalStudentApelResults
                .get(EntityUtil.getId(addedStudent.getStudent()));
        results = StreamUtil.toFilteredList(r -> Boolean.TRUE.equals(r.getIsModule()), results);
        if (!results.isEmpty()) {
            JournalEntryStudent jes = new JournalEntryStudent();
            jes.setJournalEntry(finalResultEntry);
            jes.setJournalStudent(addedStudent);
            // if there are more than one module that has results, set grade as 'KUTSEHINDAMINE_A'
            String gradeCode = results.size() == 1 ? results.get(0).getGrade() : OccupationalGrade.KUTSEHINDAMINE_A.name();
            jes.setGrade(em.getReference(Classifier.class, gradeCode));
            jes.setGradeInserted(LocalDateTime.now());
            jes.setGradeInsertedBy(user.getUsername());
            finalResultEntry.getJournalEntryStudents().add(jes);
        }
    }

    public Journal removeStudentsFromJournal(HoisUserDetails user, Journal journal, JournalStudentsCommand command) {
        EntityUtil.setUsername(user.getUsername(), em);
        journal.getJournalStudents().removeIf(js -> command.getStudents().contains(EntityUtil.getId(js.getStudent())));
        return EntityUtil.save(journal, em);
    }

    public Journal saveJournalEntry(HoisUserDetails user, Journal journal, JournalEntryForm journalEntryForm) {
        validateJournalEntry(journal, null, journalEntryForm);
        EntityUtil.setUsername(user.getUsername(), em);
        JournalEntry journalEntry = EntityUtil.bindToEntity(journalEntryForm, new JournalEntry(), classifierRepository,
                "journalEntryStudents", "journalEntryCapacityTypes");
        journal.getJournalEntries().add(journalEntry);
        saveJournalEntryStudents(user, journalEntryForm, journalEntry);
        journal = EntityUtil.save(journal, em);
        sendRemarkMessages(getStudentsWithComments(journalEntry).values());
        return journal;
    }

    public void updateJournalEntry(HoisUserDetails user, Journal journal, JournalEntryForm journalEntryForm,
            Long journalEntrylId) {
        JournalEntry journalEntry = em.getReference(JournalEntry.class, journalEntrylId);
        validateJournalEntry(journal, journalEntry, journalEntryForm);
        EntityUtil.setUsername(user.getUsername(), em);
        Map<Long, Student> existingComments = getStudentsWithComments(journalEntry);
        EntityUtil.bindToEntity(journalEntryForm, journalEntry, classifierRepository, "journalEntryStudents",
                "journalEntryCapacityTypes");
        saveJournalEntryStudents(user, journalEntryForm, journalEntry);
        journalEntry = EntityUtil.save(journalEntry, em);
        Map<Long, Student> commentStudents = getStudentsWithComments(journalEntry);
        commentStudents.keySet().removeAll(existingComments.keySet());
        sendRemarkMessages(commentStudents.values());
    }

    private void sendRemarkMessages(Collection<Student> students) {
        for (Student student : students) {
            StudentRemarkCreated data = new StudentRemarkCreated(student);
            automaticMessageService.sendMessageToStudent(MessageType.TEATE_LIIK_OP_MARKUS, student, data);
            StudentGroup studentGroup = student.getStudentGroup();
            if (studentGroup != null) {
                Teacher studentGroupTeacher = studentGroup.getTeacher();
                if (studentGroupTeacher != null) {
                    automaticMessageService.sendMessageToTeacher(MessageType.TEATE_LIIK_OP_MARKUS, studentGroupTeacher, data);
                }
            }
        }
    }

    private static Map<Long, Student> getStudentsWithComments(JournalEntry journalEntry) {
        return StreamUtil.toMap(EntityUtil::getId, 
                journalEntry.getJournalEntryStudents().stream()
                .filter(jes -> StringUtils.hasText(jes.getAddInfo()) && Boolean.TRUE.equals(jes.getIsRemark()))
                .map(jes -> jes.getJournalStudent().getStudent()));
    }
    
    public void deleteJournalEntry(HoisUserDetails user, JournalEntry entry) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(entry, em);
    }

    private void validateJournalEntry(Journal journal, JournalEntry journalEntry, JournalEntryForm journalEntryForm) {
        if (StringUtils.hasText(journalEntryForm.getHomework())) {
            ValidationFailedException.throwOnError(validator.validate(journalEntryForm, JournalEntryValidation.Homework.class));
        }

        if (JournalEntryType.SISSEKANNE_T.name().equals(journalEntryForm.getEntryType())) {
            ValidationFailedException.throwOnError(validator.validate(journalEntryForm, JournalEntryValidation.Lesson.class));
        }

        if (JournalEntryType.SISSEKANNE_L.name().equals(journalEntryForm.getEntryType())) {
            if (journalEntry == null && !canAddFinalEntry(journal)) {
                throw new ValidationFailedException("journal.messages.finalEntryNotAllowed");
            }
        }
    }

    private void saveJournalEntryStudents(HoisUserDetails user, JournalEntryForm journalEntryForm,
            JournalEntry journalEntry) {
        for (JournalEntryStudentForm journalEntryStudentForm : journalEntryForm.getJournalEntryStudents()) {
            // is entry lessons is not set then it is considered to be 1 but it won't be saved
            Long lessons = journalEntryForm.getLessons() != null ? journalEntryForm.getLessons() : Long.valueOf(1);
            if (journalEntryStudentForm.getId() != null) {
                JournalEntryStudent journalEntryStudent = em.getReference(JournalEntryStudent.class, journalEntryStudentForm.getId());
                assertJournalEntryStudentRules(journalEntryStudent.getJournalStudent());
                
                updateJournalStudentEntry(user, journalEntryStudent, journalEntryStudentForm, lessons);
                if (Boolean.TRUE.equals(journalEntryStudentForm.getRemoveStudentHistory())) {
                    removeStudentGradeHistory(user, journalEntryStudent);
                }
            } else {
                saveJournalStudentEntry(user, journalEntry, journalEntryStudentForm, lessons);
            }
        }
        EntityUtil.bindEntityCollection(journalEntry.getJournalEntryCapacityTypes(),
                type -> EntityUtil.getCode(type.getCapacityType()), journalEntryForm.getJournalEntryCapacityTypes(),
                it -> {
                    JournalEntryCapacityType type = new JournalEntryCapacityType();
                    type.setCapacityType(em.getReference(Classifier.class, it));
                    return type;
                });
    }

    private void updateJournalStudentEntry(HoisUserDetails user, JournalEntryStudent journalEntryStudent,
            JournalEntryStudentForm journalEntryStudentForm, Long lessons) {
        String savedGrade = EntityUtil.getNullableCode(journalEntryStudent.getGrade());
        if (Boolean.FALSE.equals(journalEntryStudentForm.getRemoveStudentHistory()) && savedGrade != null
                && !savedGrade.equals(journalEntryStudentForm.getGrade())) {
            setJournalEntryStudentHistory(journalEntryStudent);
        }
        updateJournalEntryStudentGrade(user, journalEntryStudent, journalEntryStudentForm);
        updateStudentEntryRemark(user, journalEntryStudent, journalEntryStudentForm);
        EntityUtil.bindToEntity(journalEntryStudentForm, journalEntryStudent, classifierRepository,
                "journalEntryStudentHistories", "grade", "gradeInserted", "absence", "absenceInserted",
                "absenceAccepted", "journalEntryStudentLessonAbsences");
        updateStudentEntryAbsences(journalEntryStudent, journalEntryStudentForm, lessons);
    }

    private static void setJournalEntryStudentHistory(JournalEntryStudent journalEntryStudent) {
        JournalEntryStudentHistory journalEntryStudentHistory = new JournalEntryStudentHistory();
        journalEntryStudentHistory.setGrade(journalEntryStudent.getGrade());
        journalEntryStudentHistory.setGradeInserted(journalEntryStudent.getGradeInserted());
        
        String insertedBy;
        if (journalEntryStudent.getGradeInsertedBy() != null) {
            insertedBy = journalEntryStudent.getGradeInsertedBy();
        } else if (journalEntryStudent.getChangedBy() != null) {
            insertedBy = journalEntryStudent.getChangedBy();
        } else {
            insertedBy = journalEntryStudent.getInsertedBy();
        }
        journalEntryStudentHistory.setGradeInsertedBy(insertedBy);
        
        journalEntryStudent.getJournalEntryStudentHistories().add(journalEntryStudentHistory);
    }

    private void updateJournalEntryStudentGrade(HoisUserDetails user, JournalEntryStudent journalEntryStudent,
            JournalEntryStudentForm journalEntryStudentForm) {
        if (journalEntryStudentForm.getGrade() != null) {
            if (!journalEntryStudentForm.getGrade()
                    .equals(EntityUtil.getNullableCode(journalEntryStudent.getGrade()))) {
                journalEntryStudent.setGradeInserted(LocalDateTime.now());
                journalEntryStudent.setGradeInsertedBy(user.getUsername());
                journalEntryStudent.setGrade(em.getReference(Classifier.class, journalEntryStudentForm.getGrade()));
            }
        } else {
            journalEntryStudent.setGrade(null);
            journalEntryStudent.setGradeInserted(null);
            journalEntryStudent.setGradeInsertedBy(null);
        }
    }

    private static void updateStudentEntryRemark(HoisUserDetails user, JournalEntryStudent journalEntryStudent,
            JournalEntryStudentForm journalEntryStudentForm) {
        if (Boolean.TRUE.equals(journalEntryStudentForm.getIsRemark())) {
            if (!journalEntryStudentForm.getAddInfo().equals(journalEntryStudent.getAddInfo())
                    || !journalEntryStudentForm.getIsRemark().equals(journalEntryStudent.getIsRemark())) {
                journalEntryStudent.setRemarkInserted(LocalDateTime.now());
                journalEntryStudent.setRemarkInsertedBy(user.getUsername());
            }
        } else {
            journalEntryStudent.setRemarkInserted(null);
            journalEntryStudent.setRemarkInsertedBy(null);
        }
    }

    private static void assertJournalEntryStudentRules(JournalStudent journaStudent) {
        if (!StudentUtil.isActive(journaStudent.getStudent())) {
            throw new ValidationFailedException("journal.messages.changeIsNotAllowedStudentIsNotStudying");
        }
    }

    private void removeStudentGradeHistory(HoisUserDetails user, JournalEntryStudent journalEntryStudent) {
        EntityUtil.setUsername(user.getUsername(), em);
        journalEntryStudent.getJournalEntryStudentHistories().clear();
    }

    private void saveJournalStudentEntry(HoisUserDetails user, JournalEntry journalEntry,
            JournalEntryStudentForm journalEntryStudentForm, Long lessons) {
        JournalStudent journalStudent = em.getReference(JournalStudent.class, journalEntryStudentForm.getJournalStudent());
        assertJournalEntryStudentRules(journalStudent);

        JournalEntryStudent journalEntryStudent = EntityUtil.bindToEntity(journalEntryStudentForm,
                new JournalEntryStudent(), classifierRepository, "journalEntryStudentHistories", "gradeInserted",
                "absence", "absenceInserted", "absenceAccepted", "journalEntryStudentLessonAbsences");
        updateStudentEntryAbsences(journalEntryStudent, journalEntryStudentForm, lessons);

        journalEntryStudent.setJournalStudent(journalStudent);

        String inserter = user.getUsername();
        LocalDateTime now = LocalDateTime.now();
        if (journalEntryStudentForm.getGrade() != null) {
            journalEntryStudent.setGradeInserted(now);
            journalEntryStudent.setGradeInsertedBy(inserter);
        }
        if (Boolean.TRUE.equals(journalEntryStudentForm.getIsRemark())) {
            journalEntryStudent.setRemarkInserted(now);
            journalEntryStudent.setRemarkInsertedBy(inserter);
        }

        // TODO remove check, use database unique constraint
        Long id = EntityUtil.getId(journalStudent);
        if(journalEntry.getJournalEntryStudents().stream().anyMatch(it -> id.equals(EntityUtil.getId(it.getJournalStudent())))) {
            throw new ValidationFailedException("journal.messages.dublicateJournalStudentInJournalEntry");
        }
        journalEntry.getJournalEntryStudents().add(journalEntryStudent);
    }

    private void updateStudentEntryAbsences(JournalEntryStudent journalEntryStudent, JournalEntryStudentForm form,
            Long lessons) {
        journalEntryStudent.setIsLessonAbsence(form.getIsLessonAbsence());

        if (Boolean.TRUE.equals(form.getIsLessonAbsence())) {
            updateStudentEntryLessonAbsences(journalEntryStudent, form, lessons);
        } else {
            if (form.getAbsence() != null) {
                if (!form.getAbsence().equals(EntityUtil.getNullableCode(journalEntryStudent.getAbsence()))) {
                    journalEntryStudent.setAbsenceInserted(LocalDateTime.now());
                }
            } else {
                journalEntryStudent.setAbsenceInserted(null);
            }
            journalEntryStudent.setAbsence(EntityUtil.getOptionalOne(form.getAbsence(), em));
            journalEntryStudent.getJournalEntryStudentLessonAbsences().clear();
        }
    }

    private void updateStudentEntryLessonAbsences(JournalEntryStudent journalEntryStudent, JournalEntryStudentForm form,
            Long lessons) {
        journalEntryStudent.setAbsence(null);
        journalEntryStudent.setAbsenceInserted(null);
        journalEntryStudent.setAbsenceAccepted(null);

        List<JournalEntryStudentLessonAbsenceForm> formLessonAbsences = StreamUtil.toFilteredList(
                r -> r != null && r.getAbsence() != null && r.getLessonNr().longValue() <= lessons.longValue(),
                form.getLessonAbsences().values());
        List<Long> formLessonNrs = StreamUtil.toMappedList(r -> r.getLessonNr(), formLessonAbsences);
        journalEntryStudent.getJournalEntryStudentLessonAbsences().removeIf(r -> !formLessonNrs.contains(r.getLessonNr()));

        Map<Long, JournalEntryStudentLessonAbsence> savedLessonAbsences = StreamUtil.toMap(r -> r.getLessonNr(),
                journalEntryStudent.getJournalEntryStudentLessonAbsences());
        for (JournalEntryStudentLessonAbsenceForm absenceForm : formLessonAbsences) {
            JournalEntryStudentLessonAbsence lessonAbsence = savedLessonAbsences.get(absenceForm.getLessonNr());
            if (lessonAbsence == null) {
                lessonAbsence = new JournalEntryStudentLessonAbsence();
                lessonAbsence.setLessonNr(absenceForm.getLessonNr());
                journalEntryStudent.getJournalEntryStudentLessonAbsences().add(lessonAbsence);
            }
            
            if (!absenceForm.getAbsence().equals(EntityUtil.getNullableCode(lessonAbsence.getAbsence()))) {
                lessonAbsence.setAbsenceInserted(LocalDateTime.now());
            }
            lessonAbsence.setAbsence(em.getReference(Classifier.class, absenceForm.getAbsence()));
        }
    }

    public Map<Long, List<JournalEntryStudentResultDto>> quickUpdateJournalEntry(HoisUserDetails user,
            JournalEntryQuickUpdateForm journalEntryForm) {
        JournalEntry journalEntry = em.getReference(JournalEntry.class, journalEntryForm.getJournalEntryId());
        quickUpdateJournalStudents(user, journalEntry, journalEntryForm);
        journalEntry = EntityUtil.save(journalEntry, em);
        return journalEntryByDateStudentResults(journalEntry, journalEntryForm.getAllStudents());
    }

    private void quickUpdateJournalStudents(HoisUserDetails user, JournalEntry journalEntry,
            JournalEntryQuickUpdateForm journalEntryForm) {
        for (JournalEntryStudentForm journalEntryStudentForm : journalEntryForm.getJournalEntryStudents()) {
            if (journalEntryStudentForm.getId() != null) {
                JournalEntryStudent journalEntryStudent = em.getReference(JournalEntryStudent.class,
                        journalEntryStudentForm.getId());
                assertJournalEntryStudentRules(journalEntryStudent.getJournalStudent());

                if (journalEntryStudent.getGrade() != null && !EntityUtil.getCode(journalEntryStudent.getGrade())
                        .equals(journalEntryStudentForm.getGrade())) {
                    setJournalEntryStudentHistory(journalEntryStudent);
                }
                updateJournalEntryStudentGrade(user, journalEntryStudent, journalEntryStudentForm);
                journalEntryStudent.setAddInfo(journalEntryStudentForm.getAddInfo());
            } else {
                JournalStudent journalStudent = em.getReference(JournalStudent.class,
                        journalEntryStudentForm.getJournalStudent());

                JournalEntryStudent journalEntryStudent = new JournalEntryStudent();
                journalEntryStudent.setJournalStudent(journalStudent);
                if (journalEntryStudentForm.getGrade() != null) {
                    journalEntryStudent.setGrade(em.getReference(Classifier.class, journalEntryStudentForm.getGrade()));
                    journalEntryStudent.setGradeInserted(LocalDateTime.now());
                    journalEntryStudent.setGradeInsertedBy(user.getUsername());
                }
                journalEntryStudent.setAddInfo(journalEntryStudentForm.getAddInfo());
                journalEntry.getJournalEntryStudents().add(journalEntryStudent);

            }
        }
    }

    public JournalEntryLessonInfoDto journalEntryLessonInfo(Journal journal) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from timetable_event_time tet " + "inner join timetable_event te on te.id = tet.timetable_event_id "
                        + "inner join timetable_object tob on tob.id = te.timetable_object_id");
        qb.requiredCriteria("tob.journal_id = :journalId", "journalId", EntityUtil.getId(journal));
        List<?> result = qb.select("tet.start", em).getResultList();
        JournalEntryLessonInfoDto dto = new JournalEntryLessonInfoDto();
        dto.setLessonPlanDates(StreamUtil.toMappedList(r -> resultAsLocalDateTime(r, 0), result));
        return dto;
    }

    public Page<JournalEntryTableDto> journalTableEntries(Long journalId, Pageable pageable) {
        JpaNativeQueryBuilder jeQb = new JpaNativeQueryBuilder("from journal_entry je")
                .sort("je.entry_date desc nulls last, lower(je.entry_type_code)='sissekanne_l' asc");

        jeQb.requiredCriteria("je.journal_id=:journalId", "journalId", journalId);

        return JpaQueryUtil.pagingResult(jeQb, "je.id, je.entry_type_code, je.entry_date, je.lessons, je.name_et, "
                        + "je.content, je.homework, je.homework_duedate, je.moodle_grade_item_id", em,
                pageable).map(r -> {
                    JournalEntryTableDto dto = new JournalEntryTableDto();
                    dto.setId(resultAsLong(r, 0));
                    dto.setEntryType(resultAsString(r, 1));
                    dto.setEntryDate(resultAsLocalDate(r, 2));
                    dto.setLessons(resultAsLong(r, 3));
                    dto.setNameEt(resultAsString(r, 4));
                    dto.setContent(resultAsString(r, 5));
                    dto.setHomework(resultAsString(r, 6));
                    dto.setHomeworkDuedate(resultAsLocalDate(r, 7));
                    dto.setMoodleGradeItemId(resultAsLong(r, 8));
                    return dto;
                });
    }

    public JournalEntryDto journalEntry(Long journalId, Long journalEntrylId) {
        JournalEntry journalEntry = em.getReference(JournalEntry.class, journalEntrylId);
        if (EntityUtil.getId(journalEntry.getJournal()).equals(journalId)) {
            return JournalEntryDto.of(journalEntry);
        }
        return null;
    }

    public List<JournalEntryByDateDto> journalEntriesByDate(Journal journal, Boolean allStudents) {
        List<JournalEntryByDateDto> result = new ArrayList<>();

        for (JournalEntry journalEntry : journal.getJournalEntries()) {
            JournalEntryByDateDto journalEntryByDateDto = EntityUtil.bindToDto(journalEntry,
                    new JournalEntryByDateDto());
            journalEntryByDateDto.setTeacher(PersonUtil.stripIdcodeFromFullnameAndIdcode(journalEntry.getInsertedBy()));
            journalEntryByDateDto.setEntryDate(journalEntry.getEntryDate());
            journalEntryByDateDto.setJournalStudentResults(journalEntryByDateStudentResults(journalEntry, allStudents));
            result.add(journalEntryByDateDto);
        }

        List<CurriculumModuleOutcome> journalOutcomes = journalOutcomes(journal.getId());
        Map<Long, List<JournalSearchDto>> outcomeOtherJournals = outcomeOtherJournals(journal.getId(),
            StreamUtil.toMappedList(CurriculumModuleOutcome::getId, journalOutcomes));
        int outcomeWithoutOrderNr = 0;
        for (CurriculumModuleOutcome outcome : journalOutcomes) {
            JournalEntryByDateDto outcomeDto = new JournalEntryByDateDto();
            setOutcomeEntryBaseData(outcomeDto, outcome, outcomeWithoutOrderNr);
            List<StudentCurriculumModuleOutcomesResultDto> outcomeResults = journalOutcomeResultDtos(null, journal, outcome);
            outcomeDto.setStudentOutcomeResults(StreamUtil.toMap(StudentCurriculumModuleOutcomesResultForm::getStudentId,
                    outcomeResults));
            outcomeDto.setOtherJournals(outcomeOtherJournals.get(outcome.getId()));
            result.add(outcomeDto);
        }
        JournalUtil.setOutcomeEntriesUnqiueOrderNrs(result);

        JournalUtil.orderJournalEntriesByDate(result);
        return result;
    }
    
    private static Map<Long, List<JournalEntryStudentResultDto>> journalEntryByDateStudentResults(
            JournalEntry journalEntry, Boolean allStudents) {
        Map<Long, List<JournalEntryStudentResultDto>> studentResultsMap = new HashMap<>();
        for (JournalEntryStudent journalEntryStudent : journalEntry.getJournalEntryStudents()) {
            if (Boolean.TRUE.equals(allStudents)
                    || StudentUtil.isActive(journalEntryStudent.getJournalStudent().getStudent())) {
                List<JournalEntryStudentResultDto> studentResults = new ArrayList<>();
                JournalEntryStudentResultDto dto = EntityUtil.bindToDto(journalEntryStudent,
                        new JournalEntryStudentResultDto(), "gradeInsertedBy", "journalEntryStudentHistories",
                        "lessonAbsences");
                dto.setJournalEntryStudentId(EntityUtil.getId(journalEntryStudent));
                dto.setJournalStudentId(EntityUtil.getId(journalEntryStudent.getJournalStudent()));

                String insertedBy;
                if (journalEntryStudent.getGradeInsertedBy() != null) {
                    insertedBy = journalEntryStudent.getGradeInsertedBy();
                } else if (journalEntryStudent.getChangedBy() != null) {
                    insertedBy = journalEntryStudent.getChangedBy();
                } else {
                    insertedBy = journalEntryStudent.getInsertedBy();
                }

                dto.setGradeInsertedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(insertedBy));
                dto.setJournalEntryStudentHistories(StreamUtil.toMappedList(JournalEntryStudentHistoryDto::new,
                        journalEntryStudent.getJournalEntryStudentHistories()));
                dto.setLessonAbsences(StreamUtil.toMappedList(JournalEntryStudentLessonAbsenceDto::new,
                        journalEntryStudent.getJournalEntryStudentLessonAbsences()));
                studentResults.add(dto);
                studentResultsMap.put(dto.getJournalStudentId(), studentResults);
            }
        }
        return studentResultsMap;
    }

    private List<CurriculumModuleOutcome> journalOutcomes(Long journalId) {
        List<Long> outcomeIds = journalOutcomeIds(journalId);
        if (!outcomeIds.isEmpty()) {
            return em.createQuery("select cmo from CurriculumModuleOutcome cmo "
                    + "where cmo.id in (?1)", CurriculumModuleOutcome.class)
                    .setParameter(1, outcomeIds).getResultList();
        }
        return new ArrayList<>();
    }

    private List<Long> journalOutcomeIds(Long journalId) {
        JpaNativeQueryBuilder qb = journalOutcomesQb(journalId);
        List<?> data = qb.select("cmo.id", em).getResultList();
        return StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
    }

    private Map<Long, List<JournalSearchDto>> outcomeOtherJournals(Long journalId, List<Long> outcomeIds) {
        if (!outcomeIds.isEmpty()) {
            String from = JOURNAL_OUTCOMES_FROM;
            from += " join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id "
                    + "join lesson_plan lp on lp.id = lpm.lesson_plan_id "
                    + "join student_group sg on sg.id = lp.student_group_id "
                    + "join study_year sy on sy.id = j.study_year_id";

            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
            qb.requiredCriteria("j.id != :journalId", "journalId", journalId);
            qb.requiredCriteria("cmo.id in (:outcomeIds)", "outcomeIds", outcomeIds);
            qb.filter("(cvot.is_module_outcomes = true or j.add_module_outcomes = true)");
            qb.groupBy("cmo.id, j.id, sy.id");
            qb.sort("j.name_et");

            String select = "cmo.id cmo_id, j.id j_id, j.name_et, "
                    + "(select string_agg(p.firstname || ' ' || p.lastname, ', ' order by p.lastname, p.firstname) "
                    + "from journal_teacher jt join teacher t on t.id = jt.teacher_id join person p on p.id = t.person_id "
                    + "where jt.journal_id = j.id) teachers, "
                    + "string_agg(distinct sg.code, ', ' order by sg.code) student_groups, sy.year_code";
            List<?> data = qb.select(select, em).getResultList();
            return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                    Collectors.mapping(r -> {
                        JournalSearchDto dto = new JournalSearchDto();
                        dto.setId(resultAsLong(r, 1));
                        dto.setNameEt(resultAsString(r, 2));
                        dto.setTeachers(resultAsString(r, 3));
                        dto.setStudentGroups(resultAsString(r, 4));
                        dto.setStudyYear(resultAsString(r, 5));
                        return dto;
                    }, Collectors.toList())));
        }
        return new HashMap<>();
    }

    private static void setOutcomeEntryBaseData(JournalEntryByDateBaseDto dto,
            CurriculumModuleOutcome outcome, int outcomeWithoutOrderNr) {
        dto.setEntryType(JournalEntryType.SISSEKANNE_O.name());
        dto.setNameEt(outcome.getOutcomeEt());
        dto.setNameEn(outcome.getOutcomeEn());
        if (outcome.getOrderNr() != null) {
            dto.setOutcomeOrderNr(outcome.getOrderNr());
        } else {
            dto.setOutcomeOrderNr(Long.valueOf(outcomeWithoutOrderNr++));
        }
        dto.setCurriculumModule(EntityUtil.getId(outcome.getCurriculumModule()));
        dto.setCurriculumModuleOutcomes(outcome.getId());
    }

    public Page<CurriculumModuleOutcomeResult> journalTableOutcomes(Long journalId, Pageable pageable) {
        JpaNativeQueryBuilder qb = journalOutcomesQb(journalId);
        String select = "cmo.id, cmo.outcome_et, cmo.outcome_en, cmo.order_nr, "
                + "(select string_agg(distinct sg.code, ', ' order by sg.code) from journal_student js "
                + "join student s on s.id = js.student_id join student_group sg on sg.id = s.student_group_id "
                + "where js.journal_id = j.id and sg.curriculum_id = cm.curriculum_id) connected_groups";

        Page<CurriculumModuleOutcomeResult> page = JpaQueryUtil.pagingResult(qb, select, em, pageable).map(
                r -> {
                    String studentGroups = resultAsString(r, 4);
                    studentGroups = studentGroups != null ? " (" + studentGroups + ")" : "";
                    String nameEt = resultAsString(r, 1) + studentGroups;
                    String nameEn = resultAsString(r, 2);
                    nameEn = nameEn != null ? nameEn + studentGroups : nameEt;
                    return new CurriculumModuleOutcomeResult(resultAsLong(r, 0), nameEt, nameEn, resultAsLong(r, 3));
                });
        List<Long> assignedOrderNrs = new ArrayList<>();
        for (CurriculumModuleOutcomeResult dto : page.getContent()) {
            dto.setOrderNr(JournalUtil.uniqueOrderNr(dto.getOrderNr(), assignedOrderNrs));
        }
        return page;
    }

    private JpaNativeQueryBuilder journalOutcomesQb(Long journalId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(JOURNAL_OUTCOMES_FROM);
        qb.requiredCriteria("j.id = :journalId", "journalId", journalId);
        qb.filter("(cvot.is_module_outcomes = true or j.add_module_outcomes = true)");
        qb.groupBy("j.id, cmo.id, cm.curriculum_id");
        qb.sort("cmo.curriculum_module_id, cmo.order_nr");
        return qb;
    }

    public JournalOutcomeDto journalOutcome(HoisUserDetails user, Journal journal, CurriculumModuleOutcome outcome) {
        JournalOutcomeDto dto = new JournalOutcomeDto();
        dto.setId(outcome.getId());
        dto.setNameEt(outcome.getOutcomeEt());
        dto.setNameEn(outcome.getOutcomeEn());
        dto.setCurriculumId(EntityUtil.getId(outcome.getCurriculumModule().getCurriculum()));
        dto.setConnectedStudentGroups(journalOutcomeStudentGroups(journal, outcome));
        dto.setOutcomeStudents(journalOutcomeResultDtos(user, journal, outcome));
        return dto;
    }

    private List<AutocompleteResult> journalOutcomeStudentGroups(Journal journal, CurriculumModuleOutcome outcome) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal_student js "
                + "join student s on s.id = js.student_id "
                + "join student_group sg on sg.id = s.student_group_id "
                + "join curriculum c on c.id = sg.curriculum_id "
                + "join curriculum_module cm on cm.curriculum_id = c.id "
                + "join curriculum_module_outcomes cmo on cmo.curriculum_module_id = cm.id");
        qb.requiredCriteria("js.journal_id = :journalId", "journalId", journal.getId());
        qb.requiredCriteria("cmo.id = :outcomeId", "outcomeId", outcome.getId());
        qb.sort("sg.code");
        List<?> data = qb.select("distinct sg.id, sg.code", em).getResultList();
        return StreamUtil.toMappedList(r -> new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1),
                resultAsString(r, 1)), data);
    }

    private List<StudentCurriculumModuleOutcomesResultDto> journalOutcomeResultDtos(HoisUserDetails user,
            Journal journal, CurriculumModuleOutcome outcome) {
        List<StudentCurriculumModuleOutcomesResult> results = journalOutcomeResults(journal.getId(), outcome.getId());
        List<StudentCurriculumModuleOutcomesResultDto> resultDtos = StreamUtil.toMappedList(
                r -> {
                    StudentCurriculumModuleOutcomesResultDto dto = StudentCurriculumModuleOutcomesResultDto.of(r);
                    if (user != null) {
                        dto.setCanEdit(Boolean.valueOf(JournalUtil.canEditOutcomeGrade(user, r)));
                    }
                    return dto;
                }, results);
        if (!resultDtos.isEmpty()) {
            setOutcomeResultHistory(resultDtos);
        }
        return resultDtos;
    }

    private List<StudentCurriculumModuleOutcomesResult> journalOutcomeResults(Long journalId, Long outcomeId) {
        List<?> data = em.createNativeQuery("select scmor.id from student_curriculum_module_outcomes_result scmor "
                + "where scmor.curriculum_module_outcomes_id = ?1 and scmor.student_id in "
                + "(select js.student_id from journal_student js where js.journal_id = ?2)")
                .setParameter(1, outcomeId)
                .setParameter(2, journalId)
                .getResultList();

        if (!data.isEmpty()) {
            List<Long> resultIds = StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
            return em.createQuery("select scmor from StudentCurriculumModuleOutcomesResult "
                            + "scmor where scmor.id in (:resultIds)", StudentCurriculumModuleOutcomesResult.class)
                    .setParameter("resultIds", resultIds).getResultList();
        }
        return new ArrayList<>();
    }

    private void setOutcomeResultHistory(List<StudentCurriculumModuleOutcomesResultDto> resultDtos) {
        List<StudentCurriculumModuleOutcomesResultHistory> resultHistory = em.createQuery(
                "select scmorh from StudentCurriculumModuleOutcomesResultHistory scmorh "
                + "where scmorh.studentCurriculumModuleOutcomesResult.id in (:resultIds)",
                StudentCurriculumModuleOutcomesResultHistory.class)
                .setParameter("resultIds", StreamUtil.toMappedList(r -> r.getId(), resultDtos))
                .getResultList();

        List<StudentCurriculumModuleOutcomesResultHistoryDto> resultHistoryDtos = StreamUtil.toMappedList(
                StudentCurriculumModuleOutcomesResultHistoryDto::of, resultHistory);
        Map<Long, List<StudentCurriculumModuleOutcomesResultHistoryDto>> historyMap =
                resultHistoryDtos.stream().collect(Collectors.groupingBy(h -> h.getResultId()));
        for (StudentCurriculumModuleOutcomesResultDto resultDto : resultDtos) {
            resultDto.setHistory(historyMap.get(resultDto.getId()));
        }
    }

    public void saveOutcomeResults(HoisUserDetails user, CurriculumModuleOutcome outcome, JournalOutcomeForm form) {
        for (StudentCurriculumModuleOutcomesResultForm studentForm : form.getOutcomeStudents()) {
            if (studentForm.getId() != null) {
                StudentCurriculumModuleOutcomesResult result = em.getReference(StudentCurriculumModuleOutcomesResult.class, studentForm.getId());
                EntityUtil.assertEntityVersion(result, studentForm.getVersion());
                JournalUtil.assertCanEditOutcomeGrade(user, result);

                String savedGrade = EntityUtil.getNullableCode(result.getGrade());
                if ((savedGrade != null && !savedGrade.equals(studentForm.getGrade()))) {
                    result.addToHistory();
                }

                updateStudentOccupationResultGrade(user, result, studentForm);
                result.setGradeDate(studentForm.getGradeDate());
                result.setAddInfo(studentForm.getAddInfo());
                EntityUtil.save(result, em);
            } else {
                StudentCurriculumModuleOutcomesResult result = new StudentCurriculumModuleOutcomesResult();
                result.setStudent(em.getReference(Student.class, studentForm.getStudentId()));
                result.setCurriculumModuleOutcomes(outcome);
                result.setGrade(em.getReference(Classifier.class, studentForm.getGrade()));
                result.setGradeDate(studentForm.getGradeDate());
                result.setGradeInserted(LocalDateTime.now());
                result.setGradeInsertedBy(user.getUsername());
                result.setAddInfo(studentForm.getAddInfo());
                if (user.isTeacher()) {
                    result.setGradeInsertedTeacher(em.getReference(Teacher.class, user.getTeacherId()));
                }
                EntityUtil.save(result, em);
            }
        }
    }

    private void updateStudentOccupationResultGrade(HoisUserDetails user, StudentCurriculumModuleOutcomesResult result,
            StudentCurriculumModuleOutcomesResultForm studentForm) {
        if (studentForm.getGrade() != null) {
            if (!studentForm.getGrade().equals(EntityUtil.getNullableCode(result.getGrade()))) {
                result.setGrade(em.getReference(Classifier.class, studentForm.getGrade()));
                result.setGradeInserted(LocalDateTime.now());
                result.setGradeInsertedBy(user.getUsername());
                result.setGradeInsertedTeacher(EntityUtil.getOptionalOne(Teacher.class, user.getTeacherId(), em));
            }
        } else {
            result.removeGrade();
        }
    }

    public List<JournalStudentDto> journalStudents(HoisUserDetails user, Journal journal, Boolean allStudents) {
        List<JournalStudent> students = journal.getJournalStudents().stream()
                .filter(jt -> Boolean.TRUE.equals(allStudents) || StudentUtil.isActive(jt.getStudent()))
                .collect(Collectors.toList());
        
        Map<Long, JournalStudentDto> mappedStudentDtos = students.stream().map(JournalStudentDto::of)
                .collect(Collectors.toMap(r -> r.getStudentId(), r -> r));

        if (!mappedStudentDtos.isEmpty()) {
            Set<Long> studentIds = mappedStudentDtos.keySet();
            Set<CurriculumVersionOccupationModuleTheme> themes = StreamUtil.toMappedSet(
                    t -> t.getCurriculumVersionOccupationModuleTheme(), journal.getJournalOccupationModuleThemes());
            Set<Long> themeIds = StreamUtil.toMappedSet(t -> EntityUtil.getId(t), themes);
            Set<Long> omoduleIds = StreamUtil.toMappedSet(t -> EntityUtil.getId(t.getModule()), themes);

            if (user.isLeadingTeacher()) {
                Set<Long> viewableStudents = viewableStudents(user, studentIds);
                for (Long studentId : mappedStudentDtos.keySet()) {
                    if (!viewableStudents.contains(studentId)) {
                        mappedStudentDtos.get(studentId).setCanView(Boolean.FALSE);
                    }
                }
            }

            Map<Long, List<JournalStudentApelResultDto>> journalStudentApelResuts = journalStudentApelResults(
                    omoduleIds, themeIds, studentIds);
            for (Long studentId : journalStudentApelResuts.keySet()) {
                mappedStudentDtos.get(studentId).setApelResults(journalStudentApelResuts.get(studentId));
            }

            Map<Long, List<JournalStudentRemarkDto>> journalStudentRemarks = journalStudentRemarks(journal, studentIds);
            for (Long studentId : journalStudentRemarks.keySet()) {
                mappedStudentDtos.get(studentId).setRemarks(journalStudentRemarks.get(studentId));
            }

            Set<Long> studentsWithIndividualCurriculums = studentsWithIndividualCurriculums(EntityUtil.getId(journal));
            for (Long studentId : studentsWithIndividualCurriculums) {
                // all journal students might not be shown
                if (mappedStudentDtos.containsKey(studentId)) {
                    mappedStudentDtos.get(studentId).setIsIndividualCurriculum(Boolean.TRUE);
                }
            }
        }

        List<JournalStudentDto> studentDtos = new ArrayList<>();
        studentDtos.addAll(mappedStudentDtos.values());
        studentDtos.sort(Comparator
                .comparing(JournalStudentDto::getStudentGroup, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(JournalStudentDto::getLastname, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(JournalStudentDto::getFirstname, String.CASE_INSENSITIVE_ORDER));

        return studentDtos;
    }

    // leading teacher can see only his/her curriculum student group students
    private Set<Long> viewableStudents(HoisUserDetails user, Set<Long> studentIds) {
        List<?> data = em.createNativeQuery("select s.id from student s" +
                " join curriculum_version cv on cv.id = s.curriculum_version_id" +
                " where cv.curriculum_id in (?1) and s.id in (?2)")
                .setParameter(1, user.getCurriculumIds())
                .setParameter(2, studentIds)
                .getResultList();
        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);
    }

    private Map<Long, List<JournalStudentApelResultDto>> journalStudentApelResults(Set<Long> omoduleIds,
            Set<Long> themeIds, Set<Long> students) {
        if (omoduleIds.isEmpty() || themeIds.isEmpty() || students.isEmpty()) {
            return new HashMap<>();
        }

        String informalApelResults = "select aa.student_id, " +
            "case when aai.curriculum_version_omodule_theme_id is not null then false else true end as module, " +
            "case when aai.curriculum_version_omodule_theme_id is not null then cm.name_et || ' - ' || mcl.name_et || '/' || cvot.name_et " + 
                "else cm.name_et || ' - ' || mcl.name_et end as my_theme, " + 
            "case when aai.curriculum_version_omodule_theme_id is not null then cm.name_en || ' - ' || mcl.name_en || '/' || cvot.name_et " + 
                "else cm.name_en || ' - ' || mcl.name_en end as my_theme_en, " + 
            "aai.grade_code, aa.confirmed, false as is_formal_learning from apel_application aa " + 
            "join apel_application_record aar on aa.id=aar.apel_application_id " + 
            "join apel_application_informal_subject_or_module aai on aar.id=aai.apel_application_record_id " + 
            "join curriculum_version_omodule cvo on aai.curriculum_version_omodule_id=cvo.id " + 
            "left join curriculum_version_omodule_theme cvot on aai.curriculum_version_omodule_theme_id=cvot.id " + 
            "join curriculum_module cm on cvo.curriculum_module_id=cm.id " + 
            "join classifier mcl on mcl.code = cm.module_code " + 
            "where aa.student_id in (:studentIds) and cvo.id in (:moduleIds) and (cvot.id in (:themeIds) or cvot.id is null) " +
            "and aa.status_code='VOTA_STAATUS_C' and aai.transfer = true ";
        
        String formalApelResults = "select distinct aa.student_id, " +
            "case when aafr.curriculum_version_omodule_theme_id is not null then false else true end as module, " +
            "case when aafr.curriculum_version_omodule_theme_id is not null then cm.name_et || ' - ' || mcl.name_et || '/' || cvot.name_et " + 
                "else cm.name_et || ' - ' || mcl.name_et end as my_theme, " + 
            "case when aafr.curriculum_version_omodule_theme_id is not null then cm.name_en || ' - ' || mcl.name_en || '/' || cvot.name_et " + 
                "else cm.name_en || ' - ' || mcl.name_en end as my_theme_en, " +
            "(case when (select count( aaf2.grade_code ) from apel_application_formal_subject_or_module aaf2 " +
                "where aaf2.apel_application_record_id = aar.id and aaf2.transfer = true) = 1 then " +
                "(select aaf2.grade_code from apel_application_formal_subject_or_module aaf2 where aaf2.apel_application_record_id = aar.id " +
                "and aaf2.transfer = true ) else 'KUTSEHINDAMINE_A' end), " +
            "aa.confirmed, true as is_formal_learning " +
            "from apel_application aa " +
            "join apel_application_record aar on aa.id = aar.apel_application_id " +
            "join apel_application_formal_subject_or_module aaf on aar.id = aaf.apel_application_record_id " +
            "join apel_application_formal_replaced_subject_or_module aafr on aar.id = aafr.apel_application_record_id " +
            "join curriculum_version_omodule cvo on aafr.curriculum_version_omodule_id=cvo.id " +
            "left join curriculum_version_omodule_theme cvot on aafr.curriculum_version_omodule_theme_id=cvot.id " +
            "join curriculum_module cm on cvo.curriculum_module_id=cm.id " +
            "join classifier mcl on mcl.code = cm.module_code " +
            "where aa.student_id in (:studentIds) and cvo.id in (:moduleIds) and (cvot.id in (:themeIds) or cvot.id is null) " +
            "and aa.status_code='VOTA_STAATUS_C' and aaf.transfer = true";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from (" + informalApelResults + " union all " + formalApelResults + ") as apel_results");
        qb.parameter("studentIds", students);
        qb.parameter("moduleIds", omoduleIds);
        qb.parameter("themeIds", themeIds);
        List<?> data = qb.select("*",em).getResultList();

        Map<Long, List<JournalStudentApelResultDto>> result = data.stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
                    JournalStudentApelResultDto dto = new JournalStudentApelResultDto();
                    dto.setIsModule(resultAsBoolean(r, 1));
                    dto.setName(new AutocompleteResult(null, resultAsString(r, 2), resultAsString(r, 3)));
                    dto.setGrade(resultAsString(r, 4));
                    dto.setConfirmed(resultAsLocalDate(r, 5));
                    dto.setIsFormalLearning(resultAsBoolean(r, 6));
                    return dto;
                }, Collectors.toList())));
        return result;
    }

    private Map<Long, List<JournalStudentRemarkDto>> journalStudentRemarks(Journal journal, Set<Long> students) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal_entry_student jes " +
                "join journal_entry je on je.id = jes.journal_entry_id " +
                "join journal_student js on js.id = jes.journal_student_id");
        qb.requiredCriteria("je.journal_id = :journalId", "journalId", EntityUtil.getId(journal));
        qb.requiredCriteria("js.student_id in (:studentIds)", "studentIds", students);
        qb.filter("jes.is_remark = true");

        qb.sort("jes.remark_inserted asc");
        List<?> data = qb
                .select("js.student_id, je.id, jes.remark_inserted, jes.remark_inserted_by, jes.add_info", em)
                .getResultList();

        Map<Long, List<JournalStudentRemarkDto>> result = data.stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
                    JournalStudentRemarkDto dto = new JournalStudentRemarkDto();
                    dto.setEntryId(resultAsLong(r, 1));
                    dto.setInserted(resultAsLocalDateTime(r, 2));
                    dto.setInsertedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 3)));
                    dto.setAddInfo(resultAsString(r, 4));
                    return dto;
                }, Collectors.toList())));
        return result;
    }

    private Set<Long> studentsWithIndividualCurriculums(Long journalId) {
        JpaNativeQueryBuilder qb = indokIndividualCurriculums(journalId);
        String indokQuery = qb.querySql("s.id student_id", false);
        Map<String, Object> parameters = new HashMap<>(qb.queryParameters());

        qb = tugiIndividualCurriculums(journalId);
        String tugiQuery = qb.querySql("s2.id student_id", false);
        parameters.putAll(qb.queryParameters());

        qb = new JpaNativeQueryBuilder("from (" + indokQuery + " union all " + tugiQuery + ") as ic");
        List<?> data = qb.select("student_id", em, parameters).getResultList();
        return StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);
    }

    public JournalLessonHoursDto usedHours(Journal journal) {
        List<JournalCapacity> capacities = journal.getJournalCapacities();
        Set<JournalEntry> entries = journal.getJournalEntries();
        
        JournalLessonHoursDto dto = new JournalLessonHoursDto();
        dto.setTotalPlannedHours(calculatePlannedHours(capacities, null));
        dto.setTotalUsedHours(calculateUsedHours(entries, null));
        
        List<CapacityHoursDto> capacityHours = new ArrayList<>();
        SchoolCapacityTypeCommand capacityCommand = new SchoolCapacityTypeCommand();
        capacityCommand.setJournalId(journal.getId());
        capacityCommand.setEntryTypes(Boolean.TRUE);
        List<Classifier> capacityTypes = autocompleteService.schoolCapacityTypes(EntityUtil.getId(journal.getSchool()), capacityCommand);
        for (Classifier type : capacityTypes) {
            if (type.isVocational()) {
                CapacityHoursDto typeHours = new CapacityHoursDto();
                String typeCode = EntityUtil.getCode(type);
                typeHours.setCapacity(typeCode);
                typeHours.setPlannedHours(calculatePlannedHours(capacities, typeCode));
                typeHours.setUsedHours(calculateUsedHours(entries, typeCode));
                capacityHours.add(typeHours);
            }
        }
        dto.setCapacityHours(capacityHours);
        
        return dto;
    }

    private static Integer calculatePlannedHours(List<JournalCapacity> capacities, String typeCode) {
        return Integer.valueOf(capacities.stream()
                .filter(it -> typeCode == null
                        || typeCode.equals(EntityUtil.getCode(it.getJournalCapacityType().getCapacityType())))
                .mapToInt(it -> it.getHours() == null ? 0 : it.getHours().intValue()).sum());
    }
    
    private static Integer calculateUsedHours(Set<JournalEntry> entries, String typeCode) {
        return Integer.valueOf(entries.stream()
                .filter(it -> typeCode == null || it.getJournalEntryCapacityTypes().stream()
                        .anyMatch(ct -> typeCode.equals(EntityUtil.getCode(ct.getCapacityType()))))
                .mapToInt(it -> it.getLessons() == null ? 0 : it.getLessons().intValue()).sum());
    }

    public byte[] journalAsExcel(Journal journal) {
        JournalXlsDto dto = JournalXlsDto.of(journal);
        dto.setLessonHours(usedHours(journal));

        List<CurriculumModuleOutcome> journalOutcomes = journalOutcomes(journal.getId());
        int outcomeWithoutOrderNr = 0;
        for (CurriculumModuleOutcome outcome : journalOutcomes) {
            JournalEntryByDateXlsDto outcomeDto = new JournalEntryByDateXlsDto();
            setOutcomeEntryBaseData(outcomeDto, outcome, outcomeWithoutOrderNr);

            List<StudentCurriculumModuleOutcomesResult> results = journalOutcomeResults(journal.getId(), outcome.getId());
            if (!results.isEmpty()) {
                outcomeDto.setStudentOutcomeResults(results.stream().filter(r -> r.getGrade() != null)
                        .collect(Collectors.toMap(r -> EntityUtil.getId(r.getStudent()),
                                r -> r.getGrade().getValue())));
            }
            dto.getOutcomeEntries().add(outcomeDto);
        }
        JournalUtil.setOutcomeEntriesUnqiueOrderNrs(dto.getOutcomeEntries());
        
        SchoolType type = schoolService.schoolType(EntityUtil.getId(journal.getSchool()));
        dto.setIsHigherSchool(Boolean.valueOf(type.isHigher()));
        
        return xlsService.generate("journal.xls", Collections.singletonMap("journal", dto));
    }

    public List<JournalEntryStudentAcceptedAbsenceDto> journalStudentsWithAcceptedAbsences(Journal journal, LocalDate entryDate) {
        Map<Long, JournalEntryStudentAcceptedAbsenceDto> acceptedAbsences = new HashMap<>();
        Map<Long, List<StudentAbsenceDto>> wholeDayAbsences = wholeDayAcceptedAbsences(journal, entryDate);
        Map<Long, Set<Long>> lessonAbsences = lessonAbsences(journal, entryDate);

        for (Long journalStudent : wholeDayAbsences.keySet()) {
            boolean practice = wholeDayAbsences.get(journalStudent).stream().anyMatch(a -> a.getContractId() != null);

            JournalEntryStudentAcceptedAbsenceDto dto = new JournalEntryStudentAcceptedAbsenceDto();
            dto.setJournalStudent(journalStudent);
            dto.setWholeDay(Boolean.TRUE);
            dto.setPractice(Boolean.valueOf(practice));
            acceptedAbsences.put(journalStudent, dto);
        }

        for (Long journalStudent : lessonAbsences.keySet()) {
            JournalEntryStudentAcceptedAbsenceDto dto = acceptedAbsences.get(journalStudent);
            if (dto == null) {
                dto = new JournalEntryStudentAcceptedAbsenceDto();
                dto.setJournalStudent(journalStudent);
                dto.setWholeDay(Boolean.FALSE);
                acceptedAbsences.put(journalStudent, dto);
            }
            dto.setLessons(lessonAbsences.get(journalStudent));
        }
        return new ArrayList<>(acceptedAbsences.values());
    }
    
    private Map<Long, List<StudentAbsenceDto>> wholeDayAcceptedAbsences(Journal journal, LocalDate entryDate) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal_student js "
                + "join student_absence sa on sa.student_id = js.student_id "
                + "left join contract c on c.id = sa.contract_id "
                + "left join directive_student ds on ds.id = sa.directive_student_id");
        qb.requiredCriteria("js.journal_id = :journalId", "journalId", EntityUtil.getId(journal));
        qb.requiredCriteria("sa.valid_from <= :entryDate", "entryDate", entryDate);
        qb.requiredCriteria("coalesce(sa.valid_thru, sa.valid_from) >= :entryDate", "entryDate", entryDate);
        qb.filter("coalesce(sa.is_lesson_absence, false) = false");
        qb.filter("sa.is_accepted = true");

        List<?> result = qb.select("js.id student_id, sa.id absence_id, c.id contract_id, "
                + "c.is_practice_absence, ds.id directive_student_id", em).getResultList();
        return result.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
            StudentAbsenceDto dto = new StudentAbsenceDto();
            dto.setId(resultAsLong(r, 1));
            // if contract is_practice_absence is false act as if student absence is normal 'Absence with reason'(PUUDUMINE_V)
            if (Boolean.TRUE.equals(resultAsBoolean(r, 3))) {
                dto.setContractId(resultAsLong(r, 2));
            }
            dto.setDirectiveStudentId(resultAsLong(r, 4));
            return dto;
        }, Collectors.toList())));
    }
    
    private Map<Long, Set<Long>> lessonAbsences(Journal journal, LocalDate entryDate) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal_student js "
                + "join student_absence sa on js.student_id = sa.student_id "
                + "join student_absence_lesson sal on sa.id = sal.student_absence_id");
        qb.requiredCriteria("js.journal_id = :journalId", "journalId", EntityUtil.getId(journal));
        qb.requiredCriteria("sal.absence = :entryDate", "entryDate", entryDate);
        qb.filter("sa.is_accepted");
        
        List<?> result = qb.select("js.id, sal.lesson_nr", em).getResultList();
        return result.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> resultAsLong(r, 1), Collectors.toSet())));
    }
    
    /**
     * Get study years for view
     * @param studentId
     * @return list of study years in which student has journals
     */
    public List<StudyYearSearchDto> studentJournalStudyYears(Long studentId) {
        String from = "from journal j"
                + " join journal_student js on j.id = js.journal_id"
                + " join study_year sy on j.study_year_id = sy.id "
                + " join classifier c on sy.year_code = c.code";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("js.student_id = :studentId", "studentId", studentId);
        
        List<?> data = qb.select("distinct c.code, c.name_et, c.name_en, sy.id, sy.start_date, sy.end_date, 0 as count", em).getResultList();
        return StreamUtil.toMappedList(r -> new StudyYearSearchDto((Object[])r), data);
    }
    
    /**
     * Get student journals for view
     * @param studentId
     * @return list of student journals with student's absences
     */
    public List<StudentJournalDto> studentJournals(Long studentId, Long studyYearId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(STUDENT_JOURNAL_FROM);
        qb.requiredCriteria("s.id = :studentId", "studentId", studentId);
        qb.requiredCriteria("j.study_year_id = :studyYearId", "studyYearId", studyYearId);
        qb.groupBy("j.id, sy.year_code");
        List<?> result = qb.select(STUDENT_JOURNAL_SELECT, em).getResultList();

        List<StudentJournalDto> journals = StreamUtil.toMappedList(r -> {
            StudentJournalDto dto = new StudentJournalDto();
            dto.setId(resultAsLong(r, 0));
            dto.setNameEt(resultAsString(r, 1));
            dto.setStudyYearId(resultAsLong(r, 2));
            dto.setYearCode(resultAsString(r, 3));
            dto.setTeachers(resultAsString(r, 4));
            dto.setModules(new AutocompleteResult(null, resultAsString(r, 5), resultAsString(r, 6)));
            
            Map<String, Long> absences = new HashMap<>();
            for (Absence absence : Absence.values()) {
                absences.put(absence.name(), (Long.valueOf(0)));
            }
            dto.setAbsences(absences);
            
            return dto;
        }, result);
        setStudentJournalGrades(studentId, journals);
        setStudentJournalAbsences(studentId, journals);
        return journals;
    }

    private void setStudentJournalGrades(Long studentId, List<StudentJournalDto> journals) {
        List<Long> journalIds = StreamUtil.toMappedList(StudentJournalDto::getId, journals);

        if (!journalIds.isEmpty()) {
            Query gradesQuery = em.createNativeQuery("select * from ("
                    + "select je.journal_id, je.id, je.entry_type_code, jes.grade_code, jes.grade_inserted,"
                    + " coalesce(jes.grade_inserted_by, jes.changed_by, jes.inserted_by) grade_inserted_by,"
                    + " jes.add_info, jes.is_remark, null name_et, null name_en, je.entry_date from journal j"
                    + " join journal_entry je on j.id = je.journal_id"
                    + " join journal_student js on j.id = js.journal_id"
                    + " join journal_entry_student jes on je.id = jes.journal_entry_id and jes.journal_student_id = js.id"
                    + " where j.id in (?1) and js.student_id = ?2"
                    + " union all"
                    + " select jot.journal_id, null, '" + JournalEntryType.SISSEKANNE_O.name() + "' entry_type_code,"
                    + " scmor.grade_code, scmor.grade_inserted, scmor.grade_inserted_by, scmor.add_info,"
                    + " false is_remark, cmo.outcome_et, cmo.outcome_en, scmor.grade_date from journal_omodule_theme jot"
                    + " join curriculum_version_omodule_theme cvot on cvot.id = jot.curriculum_version_omodule_theme_id"
                    + " join curriculum_version_omodule_outcomes cvoo on cvoo.curriculum_version_omodule_theme_id = cvot.id"
                    + " join curriculum_module_outcomes cmo on cmo.id = cvoo.curriculum_module_outcomes_id"
                    + " join student_curriculum_module_outcomes_result scmor on scmor.curriculum_module_outcomes_id = cmo.id"
                    + " where jot.journal_id in (?1) and scmor.student_id = ?2 and scmor.grade_code is not null"
                    + ") results order by entry_date is null, entry_date desc");
            gradesQuery.setParameter(1, journalIds);
            gradesQuery.setParameter(2, studentId);
            List<?> data = gradesQuery.getResultList();

            if (!data.isEmpty()) {
                Map<Long, List<StudentJournalEntryDto>> entriesByJournals = data.stream().collect(
                        Collectors.groupingBy(r -> resultAsLong(r, 0),
                        Collectors.mapping(r -> {
                                StudentJournalEntryDto dto = new StudentJournalEntryDto();
                                dto.setId(resultAsLong(r, 1));
                                dto.setJournalId(resultAsLong(r, 0));
                                dto.setEntryType(resultAsString(r, 2));
                                dto.setGrade(resultAsString(r, 3));
                                dto.setGradeInserted(resultAsLocalDateTime(r, 4));
                                dto.setGradeInsertedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 5)));
                                dto.setAddInfo(resultAsString(r, 6));
                                dto.setIsRemark(resultAsBoolean(r, 7));
                                dto.setNameEt(resultAsString(r, 8));
                                dto.setNameEn(resultAsString(r, 9));
                                return dto;
                        }, Collectors.toList())));

                for (StudentJournalDto journal : journals) {
                    if (entriesByJournals.containsKey(journal.getId())) {
                        journal.setJournalEntries(entriesByJournals.get(journal.getId()));
                    }
                }
            }
        }
    }

    private void setStudentJournalAbsences(Long studentId, List<StudentJournalDto> journals) {
        List<Long> journalIds = StreamUtil.toMappedList(StudentJournalDto::getId, journals);
        
        if (!journalIds.isEmpty()) {
            Query absencesQuery = em.createNativeQuery("select j.id, jes.absence_code, je.lessons, jesla.absence_code as lesson_absence_code"
                    + " from journal_entry je"
                    + " join journal_entry_student jes on jes.journal_entry_id=je.id"
                    + " left join journal_entry_student_lesson_absence jesla on jesla.journal_entry_student_id = jes.id"
                    + " join journal_student js on jes.journal_student_id = js.id"
                    + " join journal j  on j.id = js.journal_id"
                    + " where j.id in (?1) and js.student_id = ?2 and (jes.absence_code is not null or jesla.absence_code is not null)");
            absencesQuery.setParameter(1, journalIds);
            absencesQuery.setParameter(2, studentId);
            List<?> data = absencesQuery.getResultList();
            
            if (!data.isEmpty()) {
                Map<Long, List<StudentJournalAbsenceDto>> absencesByJournal = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                        Collectors.mapping(r -> {
                            StudentJournalAbsenceDto absence = new StudentJournalAbsenceDto();
                            if (resultAsString(r, 1) != null) {
                                absence.setAbsenceCode(resultAsString(r, 1));
                                absence.setLessons(resultAsShort(r, 2));
                            } else {
                                absence.setAbsenceCode(resultAsString(r, 3));
                            }
                            return absence;
                        }, Collectors.toList())));
                
                for (StudentJournalDto journal : journals) {
                    List<StudentJournalAbsenceDto> journalAbsences = absencesByJournal.get(journal.getId());
                    
                    if (journalAbsences != null) {
                        for (String absenceType : journal.getAbsences().keySet()) {
                            List<StudentJournalAbsenceDto> journalAbsenceOfType = StreamUtil
                                    .toFilteredList(a -> absenceType.equals(a.getAbsenceCode()), journalAbsences);
                            int count = 0;
                            for (StudentJournalAbsenceDto absence : journalAbsenceOfType) {
                                if (absence.getLessons() != null) {
                                    count += absence.getLessons().intValue();
                                } else {
                                    count++;
                                }
                            }
                            journal.getAbsences().put(absenceType, Long.valueOf(count));
                        }
                    }
                }
            }
        }
    }

    public StudentJournalDto studentJournal(Long studentId, Long journalId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(STUDENT_JOURNAL_FROM);
        qb.requiredCriteria("j.id = :journalId", "journalId", journalId);
        qb.groupBy("j.id, sy.year_code");
        Object result = qb.select(STUDENT_JOURNAL_SELECT, em).setMaxResults(1).getResultList().get(0);

        StudentJournalDto dto = new StudentJournalDto();
        dto.setId(resultAsLong(result, 0));
        dto.setNameEt(resultAsString(result, 1));
        dto.setStudyYearId(resultAsLong(result, 2));
        dto.setYearCode(resultAsString(result, 3));
        dto.setTeachers(resultAsString(result, 4));
        dto.setModules(new AutocompleteResult(null, resultAsString(result, 5), resultAsString(result, 6)));
        return getStudentJournalWithEntries(studentId, dto);
    }

    private StudentJournalDto getStudentJournalWithEntries(Long studentId, StudentJournalDto journal) {
        Query entriesQuery = em
                .createNativeQuery("select je.id, je.journal_id, je.entry_type_code, je.entry_date, je.content, jes.grade_code, "
                        + " jes.grade_inserted, coalesce(jes.grade_inserted_by, jes.changed_by, jes.inserted_by) as grade_inserted_by,"
                        + " jes.add_info, je.homework, je.homework_duedate, jes.absence_code,"
                        + " jes.is_remark, jes.remark_inserted, jes.remark_inserted_by, je.name_et from journal j"
                        + " join journal_entry je on j.id = je.journal_id"
                        + " join journal_student js on j.id = js.journal_id"
                        + " left join journal_entry_student jes on je.id = jes.journal_entry_id and jes.journal_student_id = js.id"
                        + " join student s on js.student_id = s.id" + " where j.id = ?1 and s.id = ?2"
                        + " order by je.entry_date is null, je.entry_date desc");
        entriesQuery.setParameter(1, journal.getId());
        entriesQuery.setParameter(2, studentId);
        List<?> data = entriesQuery.getResultList();

        List<StudentJournalEntryDto> entries = StreamUtil
                .toMappedList(r -> new StudentJournalEntryDto(resultAsLong(r, 0), resultAsLong(r, 1),
                        resultAsString(r, 2), resultAsLocalDate(r, 3), resultAsString(r, 15), resultAsString(r, 4), resultAsString(r, 5),
                        resultAsLocalDateTime(r, 6), PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 7)),
                        resultAsString(r, 8), resultAsString(r, 9), resultAsLocalDate(r, 10), resultAsString(r, 11),
                        resultAsBoolean(r, 12), resultAsLocalDateTime(r, 13),
                        PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 14))), data);
        entries = getEntriesWithPreviousResults(entries, studentId);
        entries = getEntriesWithLessonAbsences(entries, studentId);
        journal.setJournalEntries(entries);
        return journal;
    }

    private List<StudentJournalEntryDto> getEntriesWithPreviousResults(List<StudentJournalEntryDto> entries, Long studentId) {
        Set<Long> entriesIds = StreamUtil.toMappedSet(StudentJournalEntryDto::getId, entries.stream());
        if(!entriesIds.isEmpty()) {
            Query previousResultsQuery = em.createNativeQuery("select je.id, jesh.grade_code, jesh.grade_inserted,"
                    + " coalesce(jesh.grade_inserted_by, jesh.changed_by, jesh.inserted_by) as grade_inserted_by from journal j"
                    + " join journal_entry je on je.journal_id = j.id"
                    + " join journal_student js on js.journal_id = j.id"
                    + " join student s on s.id=js.student_id"
                    + " join journal_entry_student jes on je.id=jes.journal_entry_id and jes.journal_student_id=js.id"
                    + " join journal_entry_student_history jesh on jesh.journal_entry_student_id=jes.id"
                    + " where je.id in(:entries) and s.id=:studentId"
                    + " order by je.entry_date is null, je.entry_date desc");
            previousResultsQuery.setParameter("entries", entriesIds);
            previousResultsQuery.setParameter("studentId", studentId);
            
            List<?> previousResultQueryResult = previousResultsQuery.getResultList();
            Map<Long, List<StudentJournalEntryPreviousResultDto>> previousResults = previousResultQueryResult.stream().collect(
                    Collectors.groupingBy(r -> resultAsLong(r, 0), 
                    Collectors.mapping(r -> new StudentJournalEntryPreviousResultDto(resultAsString(r, 1), 
                            resultAsLocalDateTime(r, 2), PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 3))),
                    Collectors.toList())));
            for(StudentJournalEntryDto dto : entries) {
                dto.setPreviousResults(previousResults.get(dto.getId()));
            }
        }
        return entries;
    }
    
    private List<StudentJournalEntryDto> getEntriesWithLessonAbsences(List<StudentJournalEntryDto> entries, Long studentId) {
        Set<Long> entriesIds = StreamUtil.toMappedSet(StudentJournalEntryDto::getId, entries.stream());
        if(!entriesIds.isEmpty()) {
            Query lessonAbsencesQuery = em.createNativeQuery("select je.id, jesla.lesson_nr + coalesce(je.start_lesson_nr - 1, 0) as lesson_nr,"
                    + " jesla.absence_code from journal_entry_student jes"
                    + " join journal_entry_student_lesson_absence jesla on jesla.journal_entry_student_id = jes.id"
                    + " join journal_entry je on je.id = jes.journal_entry_id"
                    + " join journal_student js on js.id = jes.journal_student_id"
                    + " where je.id in(:entries) and js.student_id=:studentId");
            lessonAbsencesQuery.setParameter("entries", entriesIds);
            lessonAbsencesQuery.setParameter("studentId", studentId);
            
            List<?> lessonAbsencesQueryResult = lessonAbsencesQuery.getResultList();
            Map<Long, List<StudentJournalEntryLessonAbsenceDto>> lessonAbsences = lessonAbsencesQueryResult.stream().collect(
                    Collectors.groupingBy(r -> resultAsLong(r, 0), 
                    Collectors.mapping(r -> new StudentJournalEntryLessonAbsenceDto(resultAsLong(r, 1), resultAsString(r, 2)),
                    Collectors.toList())));
            for(StudentJournalEntryDto dto : entries) {
                dto.setLessonAbsences(lessonAbsences.get(dto.getId()));
            }
        }
        return entries;
    }
    
    /**
     * Get student's journal tasks for view.
     * @param schoolId
     * @param studentId
     * @return study year info and a list of student's journal tasks, or null if there is no current study year
     */
    public StudentJournalTaskListDto studentJournalTasks(Long schoolId, Long studentId) {
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);
        if (studyYear == null) {
            return null;
        }

        Query q = em.createNativeQuery("select je.id, 'SISSEKANNE_T' as entry_type_code, j.name_et, je.homework_duedate task_date, je.homework AS task_content"
                + " from journal_entry je join journal j on j.id=je.journal_id"
                + " join journal_student js on j.id=js.journal_id"
                + " join student s on js.student_id=s.id"
                + " where j.study_year_id=?1 and s.id=?2"
                + " and je.homework_duedate is not null and coalesce(je.homework, 'x') != 'x'"
                + " union select je.id, je.entry_type_code, j.name_et, je.entry_date as task_date, je.content as task_content"
                + " from journal_entry je join journal j on j.id=je.journal_id"
                + " join journal_student js on j.id=js.journal_id"
                + " join student s on js.student_id=s.id"
                + " where j.study_year_id=?1 and s.id=?2 and je.entry_type_code in (:testEntryTypes)"
                + " and je.entry_date is not null and coalesce(je.content, 'x') != 'x'"
                + " order by task_date desc");
        q.setParameter(1, studyYear.getId());
        q.setParameter(2, studentId);
        q.setParameter("testEntryTypes", testEntryTypeCodes);
        List<?> data = q.getResultList();

        return new StudentJournalTaskListDto(studyYear, StreamUtil.toMappedList(r -> new StudentJournalTaskDto((Object[]) r), data));
    }

    /**
     * Get student's study entries for view.
     * @param schoolId
     * @param studentId
     * @return study year info and a list of student's study entries, or null if there is no current study year
     */
    public StudentJournalStudyListDto studentJournalStudy(Long schoolId, Long studentId) {
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);
        if (studyYear == null) {
            return null;
        }

        Query q = em.createNativeQuery("select je.id, je.entry_date, j.name_et, je.content as content from journal_entry je"
                + " inner join journal j on j.id=je.journal_id"
                + " inner join journal_student js on j.id=js.journal_id"
                + " inner join student s on js.student_id=s.id"
                + " where j.study_year_id=?1 and s.id=?2 and je.entry_date is not null and coalesce(je.content,'x')!='x' order by je.entry_date desc");
        q.setParameter(1, studyYear.getId());
        q.setParameter(2, studentId);
        List<?> data = q.getResultList();

        return new StudentJournalStudyListDto(studyYear, StreamUtil.toMappedList(r -> new StudentJournalStudyDto((Object[]) r), data));
    }

    /**
     * Get student's last 30 days absences for view.
     * @param schoolId
     * @param studentId
     * @return list of student's absences without reason and latenesses, or null if there is no current study year
     */
    public List<StudentJournalAbsenceDto> studentAbsences(Long schoolId, Long studentId) {
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);
        if (studyYear == null) {
            return null;
        }

        LocalDate today = LocalDate.now();
        Query q = em.createNativeQuery("select je.id, je.entry_date, j.name_et, coalesce(jesla.absence_code, jes.absence_code),"
                + " je.start_lesson_nr, je.lessons, jesla.lesson_nr from journal j"
                + " join journal_entry je on je.journal_id=j.id"
                + " join journal_student js on js.journal_id=j.id"
                + " join journal_entry_student jes on jes.journal_entry_id=je.id and jes.journal_student_id=js.id"
                + " left join journal_entry_student_lesson_absence jesla on jesla.journal_entry_student_id = jes.id"
                + " join student s on s.id=js.student_id"
                + " where j.study_year_id= ?1 and s.id= ?2 and (jes.absence_code in (:absenceCodes) or jesla.absence_code in (:absenceCodes))"
                + " and (je.entry_date is null or (je.entry_date >= ?3 and je.entry_date <= ?4))"
                + " group by j.name_et, je.id, jes.absence_code, jesla.absence_code, jesla.lesson_nr"
                + " order by je.entry_date desc nulls last, jesla.lesson_nr");
        q.setParameter(1, studyYear.getId());
        q.setParameter(2, studentId);
        q.setParameter(3, JpaQueryUtil.parameterAsTimestamp(today.minusDays(30)));
        q.setParameter(4, JpaQueryUtil.parameterAsTimestamp(DateUtils.lastMomentOfDay(today)));
        q.setParameter("absenceCodes", EnumUtil.toNameList(Absence.PUUDUMINE_P, Absence.PUUDUMINE_H));
        List<?> data = q.getResultList();
        
        return StreamUtil.toMappedList(
                r -> new StudentJournalAbsenceDto(resultAsLong(r, 0), resultAsLocalDate(r, 1), resultAsString(r, 2),
                        resultAsString(r, 3), resultAsShort(r, 4), resultAsShort(r, 5), resultAsShort(r, 6)),
                data);
    }

    /**
     * Get student's last 10 results for view.
     * @param schoolId
     * @param studentId
     * @return list of student's last results, or null if there is no current study year
     */
    public List<StudentJournalResultDto> studentLastResults(Long schoolId, Long studentId) {
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);
        if(studyYear == null) {
            return null;
        }

        List<?> data = em.createNativeQuery("select * from ("
                    + " select je.id, je.entry_type_code, j.name_et, null as name_en, je.content, jes.grade_code,"
                + " jes.grade_inserted, jes.add_info from journal j"
                + " join journal_entry je on je.journal_id = j.id"
                + " join journal_student js on js.journal_id = j.id"
                + " join journal_entry_student jes on jes.journal_entry_id=je.id and jes.journal_student_id = js.id"
                + " where j.study_year_id = ?1 and js.student_id = ?2 and jes.grade_code is not null"
                    + " union all select p.id, null as entry_type_code, cm.name_et, cm.name_en, null as content, ps.grade_code,"
                + " ps.changed as grade_inserted, ps.add_info from protocol p"
                + " join protocol_student ps on ps.protocol_id = p.id"
                + " join protocol_vdata pvd on pvd.protocol_id = p.id"
                + " join curriculum_version_omodule cvm on cvm.id = pvd.curriculum_version_omodule_id"
                + " join curriculum_module cm on cm.id = cvm.curriculum_module_id"
                + " where ps.student_id = ?2 and ps.grade_code is not null"
                    + " union select scmor.id, 'SISSEKANNE_O' as entry_type_code, cmo.outcome_et, cmo.outcome_en, null as content,"
                + " scmor.grade_code, scmor.grade_inserted, scmor.add_info from student_curriculum_module_outcomes_result scmor"
                + " join curriculum_module_outcomes cmo on cmo.id = scmor.curriculum_module_outcomes_id"
                + " where scmor.student_id = ?2 and scmor.grade_code is not null)"
            + " as results order by grade_inserted desc nulls last limit 10")
            .setParameter(1, studyYear.getId())
            .setParameter(2, studentId)
            .getResultList();
        
        return StreamUtil.toMappedList(r -> new StudentJournalResultDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2),
                resultAsString(r, 3), resultAsString(r, 4), resultAsString(r, 5), resultAsLocalDateTime(r, 6), resultAsString(r, 7)), data);
    }
    
    /**
     * Confirms journals which meet following criteria:
     *  - status = PAEVIK_STAATUS_T
     *  - has any students added
     *  - all active students_O have final results or journal has no assessment (graded by outcomes)
     */
    public Integer confirmAll(HoisUserDetails user) {
        Long currentStudyYear = EntityUtil.getId(studyYearService.getCurrentStudyYear(user.getSchoolId()));

        Query q = em.createNativeQuery("update journal set status_code = :journalStatusConfirmed where id in ("
                + "select id from journal j "
                + "where j.study_year_id = :currentStudyYear and status_code = :journalStatusInWork "
                + "and (j.assessment_code is null or (select students = graded_students from ("
                    + "select count(js.id) students, count(case when jes.grade_code is not null then 1 else null end) "
                    + "graded_students from journal_student js "
                    + "join student s on s.id = js.student_id and s.status_code in (:studentStatus) "
                    + "left join journal_entry je on je.journal_id = js.journal_id and je.entry_type_code = :finalResult "
                    + "left join journal_entry_student jes on jes.journal_student_id = js.id and jes.journal_entry_id = je.id "
                    + "where js.journal_id = j.id) active_students)) "
                + "and exists (select 1 from journal_student js2 where js2.journal_id = j.id))");

        q.setParameter("currentStudyYear", currentStudyYear);
        q.setParameter("journalStatusConfirmed", JournalStatus.PAEVIK_STAATUS_K.name());
        q.setParameter("journalStatusInWork", JournalStatus.PAEVIK_STAATUS_T.name());
        q.setParameter("studentStatus", StudentStatus.STUDENT_STATUS_ACTIVE);
        q.setParameter("finalResult", JournalEntryType.SISSEKANNE_L.name());
        
        return Integer.valueOf(q.executeUpdate());
    }

    public JournalAutomaticAddStudentsResult addAllSuitableStudents(HoisUserDetails user, Long studyYearId) {
        List<Journal> journals = em.createQuery("select j from Journal j "
                + "where j.studyYear.id = ?1 and j.addStudents = true and j.status.code = ?2", Journal.class)
                .setParameter(1, studyYearId)
                .setParameter(2, JournalStatus.PAEVIK_STAATUS_T.name())
                .getResultList();

        if (!journals.isEmpty()) {
            Map<Long, Journal> journalsById = StreamUtil.toMap(BaseEntityWithId::getId, journals);
            JpaNativeQueryBuilder qb = suitedStudentsQb(user);
            qb.requiredCriteria("j.id in (:journalIds)", "journalIds", journalsById.keySet());
            List<?> data = qb.select("j.id j_id, s.id s_id", em).getResultList();

            Set<Long> studentIds = StreamUtil.toMappedSet(r -> resultAsLong(r, 1), data);
            Map<Long, Set<Long>> studentsByJournals = data.stream().collect(
                    Collectors.groupingBy(r -> resultAsLong(r, 0),
                    Collectors.mapping(r -> resultAsLong(r, 1), Collectors.toSet())));

            if (!studentIds.isEmpty()) {
                List<Student> students = em.createQuery("select s from Student s where s.id in ?1", Student.class)
                        .setParameter(1, studentIds)
                        .getResultList();
                Map<Long, Student> studentsById = StreamUtil.toMap(BaseEntityWithId::getId, students);

                for (Long journalId : studentsByJournals.keySet()) {
                    Journal journal = journalsById.get(journalId);
                    Set<Long> journalStudentIds = studentsByJournals.get(journalId);
                    for (Long studentId : journalStudentIds) {
                        JournalStudent js = new JournalStudent();
                        js.setJournal(journal);
                        js.setStudent(studentsById.get(studentId));
                        journal.getJournalStudents().add(js);
                    }
                    EntityUtil.save(journal, em);
                }
            }

            long numberOfAddedStudents = studentsByJournals.entrySet().stream().flatMap(r -> r.getValue().stream()).count();
            return new JournalAutomaticAddStudentsResult(Long.valueOf(studentsByJournals.keySet().size()),
                    Long.valueOf(numberOfAddedStudents));
        }
        return new JournalAutomaticAddStudentsResult(Long.valueOf(0), Long.valueOf(0));
    }

}
