package ee.hitsa.ois.service.poll;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.type.CustomType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.config.ArrayUserType;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.ContractSupervisor;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.PracticeJournal;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.poll.Poll;
import ee.hitsa.ois.domain.poll.PollJournal;
import ee.hitsa.ois.domain.poll.PollResultStudentCommand;
import ee.hitsa.ois.domain.poll.PollStudentGroup;
import ee.hitsa.ois.domain.poll.PollTarget;
import ee.hitsa.ois.domain.poll.PollTeacherComment;
import ee.hitsa.ois.domain.poll.PollTeacherOccupation;
import ee.hitsa.ois.domain.poll.PollTheme;
import ee.hitsa.ois.domain.poll.PollThemeQuestion;
import ee.hitsa.ois.domain.poll.PollThemeQuestionFile;
import ee.hitsa.ois.domain.poll.Question;
import ee.hitsa.ois.domain.poll.QuestionAnswer;
import ee.hitsa.ois.domain.poll.Response;
import ee.hitsa.ois.domain.poll.ResponseObject;
import ee.hitsa.ois.domain.poll.ResponseQuestionAnswer;
import ee.hitsa.ois.domain.poll.ResponseSubject;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.teacher.TeacherOccupation;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.PollAnswer;
import ee.hitsa.ois.enums.PollStatus;
import ee.hitsa.ois.enums.PollTargets;
import ee.hitsa.ois.enums.PollType;
import ee.hitsa.ois.enums.ResponseStatus;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.StudentType;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.message.ContractSupervisorMessage;
import ee.hitsa.ois.message.PollReminderMessage;
import ee.hitsa.ois.service.AutomaticMessageService;
import ee.hitsa.ois.service.OisFileService;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.service.StudyYearService;
import ee.hitsa.ois.service.XlsService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.PracticeJournalUserRights;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.OisFileEditDto;
import ee.hitsa.ois.web.commandobject.poll.GraphSearchCommand;
import ee.hitsa.ois.web.commandobject.poll.PollCommand;
import ee.hitsa.ois.web.commandobject.poll.PollCommentCommand;
import ee.hitsa.ois.web.commandobject.poll.PollDatesCommand;
import ee.hitsa.ois.web.commandobject.poll.PollForm;
import ee.hitsa.ois.web.commandobject.poll.PollResultCommand;
import ee.hitsa.ois.web.commandobject.poll.PollResultStatisticsCommand;
import ee.hitsa.ois.web.commandobject.poll.PollSearchCommand;
import ee.hitsa.ois.web.commandobject.poll.QuestionCommand;
import ee.hitsa.ois.web.commandobject.poll.QuestionOrderCommand;
import ee.hitsa.ois.web.commandobject.poll.QuestionSearchCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemePageableCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemeRepedetiveCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemeOrderCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemeCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.LiteralResult;
import ee.hitsa.ois.web.dto.poll.AllPollResultsDto;
import ee.hitsa.ois.web.dto.poll.AnswersDto;
import ee.hitsa.ois.web.dto.poll.AutocompleteSubjectOrJournal;
import ee.hitsa.ois.web.dto.poll.AutocompleteTeacher;
import ee.hitsa.ois.web.dto.poll.AutocompleteWithOrder;
import ee.hitsa.ois.web.dto.poll.DatasetOverride;
import ee.hitsa.ois.web.dto.poll.GraphDto;
import ee.hitsa.ois.web.dto.poll.GraphInfoDto;
import ee.hitsa.ois.web.dto.poll.GraphOptions;
import ee.hitsa.ois.web.dto.poll.GraphSearchDto;
import ee.hitsa.ois.web.dto.poll.GraphTextAnswerDto;
import ee.hitsa.ois.web.dto.poll.GraphThemeDto;
import ee.hitsa.ois.web.dto.poll.PollAnswerResultDto;
import ee.hitsa.ois.web.dto.poll.PollPracticeJournalDto;
import ee.hitsa.ois.web.dto.poll.PollQuestionResultDto;
import ee.hitsa.ois.web.dto.poll.PollRelatedObjectsDto;
import ee.hitsa.ois.web.dto.poll.PollResultStudentDto;
import ee.hitsa.ois.web.dto.poll.PollResultStudentOrTeacherDto;
import ee.hitsa.ois.web.dto.poll.PollResultTargetDto;
import ee.hitsa.ois.web.dto.poll.PollResultsDto;
import ee.hitsa.ois.web.dto.poll.PollResultsSubjectDto;
import ee.hitsa.ois.web.dto.poll.PollSearchDto;
import ee.hitsa.ois.web.dto.poll.PollThemeResultDto;
import ee.hitsa.ois.web.dto.poll.PollTypeDto;
import ee.hitsa.ois.web.dto.poll.PracticeThemesDto;
import ee.hitsa.ois.web.dto.poll.QuestionAnswerDto;
import ee.hitsa.ois.web.dto.poll.QuestionDto;
import ee.hitsa.ois.web.dto.poll.QuestionPollSearchDto;
import ee.hitsa.ois.web.dto.poll.QuestionResponsePairDto;
import ee.hitsa.ois.web.dto.poll.QuestionSearchDto;
import ee.hitsa.ois.web.dto.poll.ResponseDto;
import ee.hitsa.ois.web.dto.poll.SubjectAndSubjectStudyPeriod;
import ee.hitsa.ois.web.dto.poll.SubjectOrJournalDto;
import ee.hitsa.ois.web.dto.poll.SubjectAnswerDto;
import ee.hitsa.ois.web.dto.poll.SubjectCommentDto;
import ee.hitsa.ois.web.dto.poll.ThemeDto;
import ee.hitsa.ois.web.dto.poll.ThemesDto;
import ee.hitsa.ois.web.dto.poll.TitleOptions;
import ee.hitsa.ois.web.dto.poll.xls.PollAnswerXlsDto;
import ee.hitsa.ois.web.dto.poll.xls.PollAnswersXlsDto;
import ee.hitsa.ois.web.dto.poll.xls.PollCommentXlsDto;
import ee.hitsa.ois.web.dto.poll.xls.PollQuestionXlsDto;
import ee.hitsa.ois.web.dto.poll.xls.PollResponseLegendXlsDto;
import ee.hitsa.ois.web.dto.poll.xls.PollResponseXlsDto;
import ee.hitsa.ois.web.dto.poll.xls.PollXlsDto;
import ee.hitsa.ois.web.dto.poll.xls.QuestionAnswerXlsDto;

@Transactional
@Service
public class PollService {
    
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    private DateTimeFormatter dateFormatHois = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Autowired
    private EntityManager em;
    
    @Autowired
    private AutomaticMessageService automaticMessageService;
    
    @Autowired
    private SchoolService schoolService;
    
    @Autowired
    private StudyYearService studyYearService;
    
    @Autowired
    private XlsService xlsService;
    
    @Value("${hois.frontend.baseUrl}")
    private String frontendBaseUrl;

    public PollForm get(Poll poll) {
        return convertToForm(poll);
    }

    public PollForm create(HoisUserDetails user, PollCommand pollForm) {
        Poll poll = new Poll();
        poll.setStatus(em.getReference(Classifier.class, PollStatus.KYSITLUS_STAATUS_E.name()));
        return save(user, poll, pollForm);
    }

    public PollForm save(HoisUserDetails user, Poll poll, PollCommand pollForm) {
        Poll changedPoll = EntityUtil.bindToEntity(pollForm, poll, "typeCode", "id", "isThemePageable");
        changedPoll.setStudyPeriod(EntityUtil.getOptionalOne(StudyPeriod.class, pollForm.getStudyPeriod(), em));
        changedPoll.setType(EntityUtil.getOptionalOne(pollForm.getType(), em));
        if (pollForm.getStatus() != null) changedPoll.setStatus(EntityUtil.getOptionalOne(pollForm.getStatus(), em));
        changedPoll.setSchool(em.getReference(School.class, user.getSchoolId()));
        changedPoll.setNameEn(pollForm.getNameEt());
        // Save student groups
        EntityUtil.bindEntityCollection(poll.getPollStudentGroups(), p -> EntityUtil.getId(p.getStudentGroup()),
                pollForm.getStudentGroups(), p -> p, dto -> {
                    PollStudentGroup pollStudentGroup = new PollStudentGroup();
                    pollStudentGroup.setPoll(changedPoll);
                    pollStudentGroup.setStudentGroup(em.getReference(StudentGroup.class, dto));
                    return pollStudentGroup;
                });
        
        // Save teacher occupations
        EntityUtil.bindEntityCollection(poll.getPollTeacherOccupations(), p -> EntityUtil.getId(p.getTeacherOccupation()),
                pollForm.getTeacherOccupations(), p -> p, dto -> {
                    PollTeacherOccupation pollTeacherOccupation = new PollTeacherOccupation();
                    pollTeacherOccupation.setPoll(changedPoll);
                    pollTeacherOccupation.setTeacherOccupation(em.getReference(TeacherOccupation.class, dto));
                    return pollTeacherOccupation;
                });
        saveSubjectStudyPeriodsAndJournals(pollForm, changedPoll);
        // Save poll targets
        EntityUtil.bindEntityCollection(poll.getPollTargets(), p -> EntityUtil.getCode(p.getTarget()),
                pollForm.getTargetCodes(), p -> p, dto -> {
                    PollTarget pollTarget = new PollTarget();
                    pollTarget.setPoll(changedPoll);
                    pollTarget.setTarget(em.getReference(Classifier.class, dto));
                    return pollTarget;
                });
        boolean hasOutsider = poll.getPollTargets().stream().anyMatch(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_V, p.getTarget()));
        if (hasOutsider && changedPoll.getPollUrl() == null) {
            changedPoll.setPollUrl(generateUniqueUrl());
        } else if (!hasOutsider) {
            changedPoll.setPollUrl(null);
        }
        return convertToForm(EntityUtil.save(changedPoll, em));
    }
    
    private void saveSubjectStudyPeriodsAndJournals(PollCommand pollForm, Poll changedPoll) {
        List<PollJournal> pollJournals = new ArrayList<>();
        if (pollForm.getJournals() != null) {
            for (AutocompleteResult journal : pollForm.getJournals()) {
                PollJournal pollJournal = new PollJournal();
                pollJournal.setPoll(changedPoll);
                pollJournal.setJournal(em.getReference(Journal.class, journal.getId()));
                pollJournals.add(pollJournal);
            }
        }
        if (pollForm.getSubjectStudyPeriods() != null) {
            for (AutocompleteResult subjectStudyPeriod : pollForm.getSubjectStudyPeriods()) {
                PollJournal pollJournal = new PollJournal();
                pollJournal.setPoll(changedPoll);
                pollJournal.setSubjectStudyPeriod(em.getReference(SubjectStudyPeriod.class, subjectStudyPeriod.getId()));
                pollJournals.add(pollJournal);
            }
        }
        changedPoll.getPollJournals().clear();
        changedPoll.getPollJournals().addAll(pollJournals);
    }

    private PollForm convertToForm(Poll poll) {
        PollForm form = new PollForm();
        EntityUtil.bindToDto(poll, form, "pollUrl");
        if (poll.getPollUrl() != null) form.setPollUrl(getFullUrlExpert(poll.getPollUrl()));
        List<ClassifierSelection> polltargets = new ArrayList<>();
        for (PollTarget pollTarget : poll.getPollTargets()) {
            polltargets.add(ClassifierSelection.of(pollTarget.getTarget()));
        }
        form.setTargetCodes(polltargets);
        List<AutocompleteResult> studentGroups = new ArrayList<>();
        for (PollStudentGroup pollStudentGroup : poll.getPollStudentGroups()) {
            studentGroups.add(AutocompleteResult.of(pollStudentGroup.getStudentGroup()));
        }
        form.setStudentGroups(studentGroups);
        List<Long> teacherOccupations = new ArrayList<>();
        for (PollTeacherOccupation teacherOccupation : poll.getPollTeacherOccupations()) {
            teacherOccupations.add(EntityUtil.getId(teacherOccupation.getTeacherOccupation()));
        }
        form.setTeacherOccupations(teacherOccupations);
        form.setJournals(getJournals(poll));
        form.setSubjectStudyPeriods(getSubjectStudyPeriods(poll));
        form.setStatus(EntityUtil.getNullableCode(poll.getStatus()));
        form.setThemes(poll.getPollThemes().stream().filter(p -> p.getPollThemeQuestions() != null && !p.getPollThemeQuestions().isEmpty()).count());
        form.setThemeEmpty(Boolean.valueOf(poll.getPollThemes().stream().anyMatch(p -> p.getPollThemeQuestions() == null || p.getPollThemeQuestions().isEmpty())));
        form.setStudyPeriod(EntityUtil.getNullableId(poll.getStudyPeriod()));
        form.setResponded(Boolean.valueOf(!poll.getResponses().isEmpty()));
        return form;
    }
    
    private List<LiteralResult> getSubjectStudyPeriods(Poll poll) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from poll_journal pj "
                + "join subject_study_period ssp on pj.subject_study_period_id = ssp.id "
                + "join subject s on s.id = ssp.subject_id "
                + "left join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id "
                + "left join teacher t on t.id = sspt.teacher_id "
                + "left join person p on p.id = t.person_id");
        qb.requiredCriteria("s.school_id = :schoolId and t.school_id = s.school_id", "schoolId", EntityUtil.getId(poll.getSchool()));
        qb.requiredCriteria("pj.poll_id = :pollId", "pollId", EntityUtil.getId(poll));
        qb.groupBy("ssp.id, s.name_et, s.name_en, s.code");
        qb.sort("lower(s.name_et)");
        List<?> data = qb.select("ssp.id, s.name_et, s.name_en, s.code, string_agg(distinct concat(p.firstname, ' ', p.lastname), ', ') as teachers", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String teachers = resultAsString(r, 4);
            String code = resultAsString(r, 3);
            String nameEt = getSubjectNameWithTeacher(resultAsString(r, 1), code, teachers);
            String nameEn = getSubjectNameWithTeacher(resultAsString(r, 2), code, teachers);
            return new LiteralResult(resultAsLong(r, 0), nameEt, nameEn, resultAsString(r, 1));
        }, data);
    }
    
    public static String getSubjectNameWithTeacher(String name, String code, String teachers) {
        return  (code != null ?  code + " - "  : "") + name + (teachers != null ? " (" + teachers + ")" : "");
    }

    public List<LiteralResult> getJournals(Poll poll) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from poll_journal pj "
                + "join journal j on pj.journal_id = j.id "
                + "left join journal_omodule_theme jot on j.id = jot.journal_id "
                + "left join lesson_plan_module lpm on jot.lesson_plan_module_id = lpm.id "
                + "left join lesson_plan lp on lpm.lesson_plan_id = lp.id "
                + "left join student_group sg on lp.student_group_id = sg.id");
        qb.requiredCriteria("j.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        qb.requiredCriteria("pj.poll_id = :pollId", "pollId", EntityUtil.getId(poll));
        qb.sort("lower(j.name_et)");
        qb.groupBy("j.id, j.name_et");
        List<?> data = qb.select("j.id, j.name_et, string_agg(distinct sg.code, ', ') as student_groups", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            String studentGroups = resultAsString(r, 2);
            String name = resultAsString(r, 1) + (studentGroups != null ? "(" + studentGroups + ")" : "");
            return new LiteralResult(resultAsLong(r, 0), name, name, resultAsString(r, 1));
        }, data);
    }

    public Page<PollSearchDto> search(HoisUserDetails user, PollSearchCommand command, Pageable pageable) {
        String SEARCH_FROM = "from poll p "
                + "left join poll_target pt on (pt.poll_id = p.id) ";
        String SEARCH_SELECT = "p.id, p.name_et, p.type_code, string_agg(pt.target_code, ';') as targetcodes, p.valid_from, p.valid_thru, "
                + "p.status_code, p.inserted_by, p.changed_by ";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        qb.optionalContains("p.name_et", "name", command.getPollName());
        qb.optionalCriteria("p.status_code = :statusCode", "statusCode", command.getStatusCode());
        qb.optionalCriteria(":targetCode in (select pot.target_code from poll po join poll_target pot on pot.poll_id = po.id where po.id = p.id)",
                "targetCode", command.getTargetCode());
        qb.optionalCriteria("p.type_code = :typeCode", "typeCode", command.getTypeCode());
        qb.optionalCriteria("p.valid_from >= :validFrom", "validFrom", command.getValidFrom());
        qb.optionalCriteria("p.valid_thru <= :validThru", "validThru", command.getValidThru());
        qb.requiredCriteria("p.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.groupBy("p.id, p.name_et, p.type_code, p.valid_from, p.valid_thru, p.status_code");
        
        return JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable).map(r -> {
            PollSearchDto dto = new PollSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setName(resultAsString(r, 1));
            dto.setTypeCode(resultAsString(r, 2));
            String codes = resultAsString(r, 3);
            if (codes != null) dto.setTargetCodes(Arrays.asList(codes.split(";")));
            dto.setValidFrom(JpaQueryUtil.resultAsLocalDate(r, 4));
            dto.setValidThru(JpaQueryUtil.resultAsLocalDate(r, 5));
            dto.setStatus(resultAsString(r, 6));
            dto.setInsertedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 7)));
            dto.setChangedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 8)));
            return dto; 
        });
    }
    
    public Page<AnswersDto> searchAnswers(HoisUserDetails user, PollSearchCommand command, Pageable pageable) {
        String SEARCH_FROM = "from poll p "
                + "join response r on r.poll_id = p.id "
                + "join response_object ro on ro.response_id = r.id "
                + "join poll_target pt on pt.id = ro.poll_target_id "
                + "left join (select pt.poll_id as pollId from poll_theme pt "
                    + "join poll_theme_question ptq on pt.id = ptq.poll_theme_id "
                    + "join question q on q.id = ptq.question_id and q.type_code != '" + PollAnswer.VASTUS_T.name() + "') Q1 on Q1.pollId = p.id";
        String SEARCH_SELECT = "p.id, p.name_et, p.name_en, p.type_code, p.valid_from, p.valid_thru, r.status_code, r.id as responseId,"
                + " p.is_theme_pageable, p.is_teacher_comment, p.is_teacher_comment_visible, p.is_student_visible, Q1.pollId";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        qb.optionalContains("p.name_et", "name", command.getPollName());
        String targetCode = getTargetCode(user);
        if (targetCode == null) throw new HoisException("main.messages.error.nopermission");
        qb.optionalCriteria("pt.target_code = :targetCode", "targetCode", targetCode);
        qb.optionalCriteria("p.type_code = :typeCode", "typeCode", command.getTypeCode());
        qb.optionalCriteria("p.valid_thru <= :validThru", "validThru", command.getValidThru());
        qb.requiredCriteria("p.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("ro.person_id = :personId", "personId", user.getPersonId());
        if (user.isStudent()) qb.requiredCriteria("ro.student_id = :studentId", "studentId", user.getStudentId());
        qb.filter("(r.status_code = 'KYSITVASTUSSTAATUS_P' or r.status_code = 'KYSITVASTUSSTAATUS_V')");
        qb.groupBy("p.id, p.name_et, p.name_en, p.type_code, p.valid_from, p.valid_thru, r.status_code, r.id, p.is_theme_pageable,"
                + " p.is_teacher_comment, p.is_teacher_comment_visible, p.is_student_visible, Q1.pollId");

        return JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable).map(r -> {
            AnswersDto dto = new AnswersDto();
            dto.setName(new AutocompleteResult(null, resultAsString(r, 1), resultAsString(r, 2)));
            dto.setType(resultAsString(r, 3));
            dto.setValidFrom(JpaQueryUtil.resultAsLocalDate(r, 4));
            dto.setValidThru(JpaQueryUtil.resultAsLocalDate(r, 5));
            dto.setStatus(resultAsString(r, 6));
            dto.setId(resultAsLong(r, 7));
            dto.setIsThemePageable(JpaQueryUtil.resultAsBoolean(r, 8));
            dto.setIsTeacherComment(JpaQueryUtil.resultAsBoolean(r, 9));
            dto.setIsTeacherCommentVisible(JpaQueryUtil.resultAsBoolean(r, 10));
            dto.setIsStudentVisible(JpaQueryUtil.resultAsBoolean(r, 11));
            Long themeId = resultAsLong(r, 12);
            if (themeId == null) {
                dto.setAllTextFields(Boolean.TRUE);
            }
            return dto; 
        });
    }
    
    public Page<SubjectAnswerDto> searchAnswersPerSubject(HoisUserDetails user, PollSearchCommand command, Pageable pageable) {
        String SEARCH_SELECT = "p.id, p.valid_from, p.valid_thru, s.id as subjectId, s.name_et as subjectEt, s.name_en as subjectEn, s.code," +
                    " jj.id as journalId, jj.name_et as journalEt, p.name_et as pollNameEt, " +
                    "p.name_en as pollNameEn, cl.name_et as classifierEt, cl.name_en as classifierEn, " +
                    " sp.id as studyPeriodId, ssp.id as subjectStudyPeriodId, TEACHERS.teacherNames";
        String SEARCH_FROM = "from poll p " + 
                "left join poll_journal pj on p.id=pj.poll_id " + 
                "left join study_period sp on sp.id = p.study_period_id " +
                "left join subject_study_period ssp on pj.subject_study_period_id=ssp.id " + 
                "left join (select st.subject_study_period_id, array_agg(st.teacher_id) as teacherIds, string_agg(pp.firstname || ' ' || pp.lastname, ', ') as teacherNames " +
                    "from subject_study_period_teacher st " + 
                    "left join teacher t on t.id = st.teacher_id " + 
                    "left join person pp on t.person_id = pp.id " + 
                    "group by st.subject_study_period_id) TEACHERS " +
                    "on TEACHERS.subject_study_period_id = ssp.id " +
                "left join subject s on ssp.subject_id = s.id " + 
                "left join journal_teacher jt on pj.journal_id=jt.journal_id " + 
                "left join journal jj on jt.journal_id=jj.id " + 
                "left join study_year sy on jj.study_year_id=sy.id " + 
                "left join classifier cl on cl.code = coalesce(sy.year_code, sp.type_code)"; 
        // TOOD: valuemapper for cl
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        qb.optionalContains("p.name_et", "nameEt", command.getPollName());
        qb.requiredCriteria("p.type_code = :typeCode", "typeCode", PollType.KYSITLUS_O.name());
        qb.requiredCriteria("p.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("p.valid_thru <= :validThru", "validThru", command.getValidThru());
        qb.optionalCriteria("(:teacherId = any(TEACHERS.teacherIds) or jt.teacher_id = :teacherId)", "teacherId", user.getTeacherId());
        qb.filter("exists( select 1 " + 
                "from response r " + 
                "join response_object ro on ro.response_id = r.id " +
                "and (r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_P.name() + "' " +
                "or r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_V.name() + "') " + 
                "join response_subject rs  on rs.response_id = r.id " +
                "left join declaration dd on dd.study_period_id=sp.id and dd.student_id=ro.student_id " +
                "left join declaration_subject ds on dd.id=ds.declaration_id and ds.subject_study_period_id=ssp.id " +
                "where r.poll_id = p.id and (rs.subject_id=s.id or rs.journal_id=pj.journal_id))");
        qb.filter("exists(select 1 from poll_theme pt where pt.poll_id = p.id and pt.is_repetitive = true)");
        return JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable).map(r -> {
            SubjectAnswerDto dto = new SubjectAnswerDto();
            Long subjectId = resultAsLong(r, 3);
            if (subjectId != null) {
                String teachers = resultAsString(r, 15);
                String subjectWithCodeEt = resultAsString(r, 6) + " - " + resultAsString(r, 4) + (teachers != null ? " (" + teachers + ")" : "");
                String subjectWithCodeEn = resultAsString(r, 6) + " - " + resultAsString(r, 5) + (teachers != null ? " (" + teachers + ")" : "");
                dto.setName(new AutocompleteResult(subjectId, subjectWithCodeEt, subjectWithCodeEn));
                dto.setIsSubject(Boolean.TRUE);
            } else {
                dto.setName(new AutocompleteResult(resultAsLong(r, 7), resultAsString(r, 8), resultAsString(r, 8)));
                dto.setIsSubject(Boolean.FALSE);
            } 
            String ANSWERS_SELECT = "count(r.id)";
            
            String ANSWERS_FROM = "from response r " + 
                    "join response_object ro on ro.response_id = r.id " +
                    "and (r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_P.name() + "' " +
                    "or r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_V.name() + "') " + 
                    "join response_subject rs  on rs.response_id = r.id " +
                    "left join declaration dd on dd.study_period_id=" + resultAsLong(r, 13) + " and dd.student_id=ro.student_id " + 
                    "left join declaration_subject ds on dd.id=ds.declaration_id and ds.subject_study_period_id=" + resultAsLong(r, 14);
            
            JpaNativeQueryBuilder queryPerResult = new JpaNativeQueryBuilder(ANSWERS_FROM);
            queryPerResult.filter("r.poll_id = " + resultAsLong(r, 0) +  " and " +
                    (subjectId != null ? "rs.subject_id=" + dto.getName().getId() : "rs.journal_id=" + dto.getName().getId()));
            queryPerResult.filter("exists(select 1 from response_question_answer rqa where rqa.response_subject_id = rs.id)");
            List<?> dbAnswers = queryPerResult.select(ANSWERS_SELECT, em).getResultList();
            List<Long> themeAndQuestionList = StreamUtil
                    .toMappedList(a-> resultAsLong(a, 0), dbAnswers);
            dto.setAnswers(themeAndQuestionList.get(0));
            dto.setStartDate(JpaQueryUtil.resultAsLocalDate(r, 1));
            dto.setEndDate(JpaQueryUtil.resultAsLocalDate(r, 2));
            dto.setPoll(new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 9), resultAsString(r, 10)));
            dto.setYearCode(new AutocompleteResult(null, resultAsString(r, 11), resultAsString(r, 12)));
            dto.setTeacher(new AutocompleteResult(resultAsLong(r, 14), resultAsString(r, 15), resultAsString(r, 15)));
            return dto; 
        });
    }
    
    public Page<QuestionSearchDto> searchQuestions(HoisUserDetails user, QuestionSearchCommand command, Pageable pageable) {
        String SEARCH_FROM = "from question q "
                + "left join poll_theme_question ptq on ptq.question_id = q.id "
                + "left join poll_theme pt on pt.id = ptq.poll_theme_id "
                + "left join poll p on (p.id = pt.poll_id and p.school_id = " + user.getSchoolId() + ")";
        String SEARCH_SELECT = "q.id, q.name_et, q.name_en, q.type_code, count(distinct p.id) as polls";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        qb.optionalContains("q.name_et", "name", command.getName());
        qb.optionalCriteria("q.type_code = :type", "type", command.getType());
        qb.requiredCriteria("q.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (command.getPollConnection() != null && command.getPollConnection().booleanValue()) qb.filter("p.id is not null");
        qb.groupBy("q.id, q.name_et, q.name_en, q.type_code");

        return JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable).map(r -> {
            QuestionSearchDto dto = new QuestionSearchDto();
            dto.setName(new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2)));
            dto.setType(resultAsString(r, 3));
            dto.setPolls(resultAsLong(r, 4));
            return dto; 
        });
    }
    
    public Page<QuestionPollSearchDto> questionPolls(HoisUserDetails user, Question question, Pageable pageable) {
        String SEARCH_FROM = "from poll p "
                + "join poll_theme pth on pth.poll_id = p.id "
                + "join poll_theme_question ptq on ptq.poll_theme_id = pth.id "
                + "join question q on q.id = ptq.question_id "
                + "left join poll_target pt on (pt.poll_id = p.id) ";
        String SEARCH_SELECT = "p.id, p.name_et, p.type_code, string_agg(pt.target_code, ';') as targetcodes, p.valid_from, "
                + "p.valid_thru, p.status_code, p.inserted_by, p.changed_by, p.is_theme_pageable ";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        qb.requiredCriteria("q.id = :questionId", "questionId", EntityUtil.getId(question));
        qb.requiredCriteria("p.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.groupBy("p.id, p.name_et, p.type_code, p.valid_from, p.valid_thru, p.status_code, p.inserted_by, p.changed_by, p.is_theme_pageable");

        return JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable).map(r -> {
            QuestionPollSearchDto dto = new QuestionPollSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setName(resultAsString(r, 1));
            dto.setTypeCode(resultAsString(r, 2));
            String codes = resultAsString(r, 3);
            if (codes != null) dto.setTargetCodes(Arrays.asList(codes.split(";")));
            dto.setValidFrom(JpaQueryUtil.resultAsLocalDate(r, 4));
            dto.setValidThru(JpaQueryUtil.resultAsLocalDate(r, 5));
            dto.setStatus(resultAsString(r, 6));
            dto.setInsertedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 7)));
            dto.setChangedBy(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 8)));
            dto.setIsThemePageable(JpaQueryUtil.resultAsBoolean(r, 9));
            return dto; 
        });
    }

    public void deleteTheme(HoisUserDetails user, PollTheme pollTheme) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(pollTheme, em);
    }
    
    public void deleteQuestion(HoisUserDetails user, PollThemeQuestion pollThemeQuestion) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(pollThemeQuestion, em);
    }
    
    public void updateThemeOrderAfterDelete(HoisUserDetails user, Poll poll) {
        for (short order = 1; order <= poll.getPollThemes().size(); order++) {
            poll.getPollThemes().get(order - 1).setOrderNr(Short.valueOf(order));
        }
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.save(poll, em);
    }

    public void createTheme(HoisUserDetails user, Poll poll, ThemeCommand themeCommand) {
        PollTheme pollTheme = new PollTheme();
        EntityUtil.bindToEntity(themeCommand, pollTheme);
        pollTheme.setNameEn(themeCommand.getNameEt());
        if (ClassifierUtil.equals(PollType.KYSITLUS_O, poll.getType())) {
            pollTheme.setIsRepetitive(Boolean.TRUE);
        }
        pollTheme.setPoll(poll);
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.save(pollTheme, em);
    }

    public ThemesDto themes(Poll poll, Long studentId) {
        List<ThemeDto> themeDtos = getPollThemes(poll);
        Collections.sort(themeDtos);
        Boolean confirmed = Boolean.FALSE;
        if (ClassifierUtil.equals(PollStatus.KYSITLUS_STAATUS_K, poll.getStatus())) {
            confirmed = Boolean.TRUE;
        }
        ThemesDto themesDto = new ThemesDto(themeDtos, confirmed, poll.getForeword(), poll.getAfterword());
        themesDto.setIsThemePageable(poll.getIsThemePageable());
        themesDto.setNameEt(poll.getNameEt());
        themesDto.setType(poll.getType().getCode());
        themesDto.setStatus(EntityUtil.getCode(poll.getStatus()));
        if (poll.getStudyPeriod() != null) {
            themesDto.setSubjects(getSubjects(poll, studentId));
        }
        if (poll.getPollJournals() != null) {
            themesDto.setJournals(getJournals(poll));
        }
        return themesDto;   
    }

    private Set<LiteralResult> getSubjects(Poll poll, Long studentId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period ssp "
                + "join subject s on s.id = ssp.subject_id "
                + "join declaration_subject ds on ds.subject_study_period_id = ssp.id "
                + "join declaration d on ds.declaration_id = d.id "
                + "left join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id "
                + "left join teacher t on t.id = sspt.teacher_id "
                + "left join person p on p.id = t.person_id");
        qb.requiredCriteria("s.school_id = :schoolId and t.school_id = s.school_id", "schoolId", EntityUtil.getId(poll.getSchool()));
        qb.requiredCriteria("d.status_code = :declarationStatus", "declarationStatus", DeclarationStatus.OPINGUKAVA_STAATUS_K.name());
        qb.optionalCriteria("ds.student_id = :studentId", "studentId", studentId);
        qb.optionalCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", EntityUtil.getNullableId(poll.getStudyPeriod()));
        qb.optionalCriteria("ssp.id in :subjectStudyPeriods", "subjectStudyPeriods", poll.getPollJournals().stream()
                .filter(p->p.getSubjectStudyPeriod() != null)
                .map(p -> EntityUtil.getId(p.getSubjectStudyPeriod())).collect(Collectors.toSet()));
        qb.groupBy("s.id, s.name_et, s.name_en");
        List<?> data = qb.select("s.id, s.name_et, s.name_en, string_agg(distinct concat(p.firstname, ' ', p.lastname), ', ') as teachers", em).getResultList();
        return StreamUtil.toMappedSet(r -> {
            String teachers = resultAsString(r, 3);
            String nameEt = resultAsString(r, 1) + (teachers != null ? "(" + teachers + ")" : "");
            String nameEn = resultAsString(r, 2) + (teachers != null ? "(" + teachers + ")" : "");
            return new LiteralResult(resultAsLong(r, 0), nameEt, nameEn, resultAsString(r, 1));
        }, data);
    }

    public void updateThemeOrder(HoisUserDetails user, ThemeOrderCommand command) {
        for (ThemeDto theme : command.getThemes()) {
            PollTheme pollTheme = em.getReference(PollTheme.class, theme.getId());
            pollTheme.setOrderNr(theme.getOrderNr());
            EntityUtil.setUsername(user.getUsername(), em);
            EntityUtil.save(pollTheme, em);
        }
    }

    public void updateTheme(HoisUserDetails user, PollTheme pollTheme, ThemeCommand themeCommand) {
        pollTheme.setNameEn(themeCommand.getNameEt());
        pollTheme.setNameEt(themeCommand.getNameEt());
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.save(pollTheme, em);
    }
    
    public void createPollThemeQuestion(HoisUserDetails user, PollTheme pollTheme, QuestionCommand questionCommand) {
        PollThemeQuestion pollThemeQuestion = new PollThemeQuestion();
        pollThemeQuestion.setQuestion(new Question());
        pollThemeQuestion.setPollTheme(pollTheme);
        savePollThemeQuestion(user, questionCommand, pollThemeQuestion);
    }
    
    public Question createQuestion(HoisUserDetails user, QuestionCommand questionCommand, Question oldQuestion) {
        Question question = null;
        if (questionCommand.getQuestion() == null) {
            question = oldQuestion;
            question.setSchool(em.getReference(School.class, user.getSchoolId()));
            EntityUtil.bindToEntity(questionCommand, question);
            question.setAddInfoEn(questionCommand.getAddInfoEt());
            question.setNameEn(questionCommand.getNameEt());
            question.setType(em.getReference(Classifier.class, questionCommand.getType()));
            // Set answers and order nr
            question.getQuestionAnswers().clear();
            List<QuestionAnswer> answers = new ArrayList<>();
            for (short orderNr = 1; orderNr <= questionCommand.getAnswers().size(); orderNr++) {
                QuestionAnswerDto answer = questionCommand.getAnswers().get(orderNr - 1);
                QuestionAnswer questionAnswer = new QuestionAnswer();
                EntityUtil.bindToEntity(answer, questionAnswer);
                questionAnswer.setQuestion(question);
                questionAnswer.setNameEn(answer.getNameEt());
                questionAnswer.setOrderNr(Short.valueOf(orderNr));
                answers.add(questionAnswer);
            }
            question.getQuestionAnswers().addAll(answers);
        } else {
            question = em.getReference(Question.class, questionCommand.getQuestion());
            question.setNameEt(questionCommand.getNameEt());
            question.setNameEn(questionCommand.getNameEt());
        }
        return EntityUtil.save(question, em);
    }

    public void savePollThemeQuestion(HoisUserDetails user, QuestionCommand questionCommand, PollThemeQuestion pollThemeQuestion) {
        EntityUtil.setUsername(user.getUsername(), em);
        
        Question question = createQuestion(user, questionCommand, pollThemeQuestion.getQuestion());
        pollThemeQuestion.setQuestion(question);
        
        EntityUtil.bindToEntity(questionCommand, pollThemeQuestion);
        if (!pollThemeQuestion.getPollTheme().getId().equals(questionCommand.getTheme())) {
            pollThemeQuestion.setPollTheme(em.getReference(PollTheme.class, questionCommand.getTheme()));
            pollThemeQuestion.setOrderNr(Short.valueOf((short) (pollThemeQuestion.getPollTheme().getPollThemeQuestions().size() + 1)));
        }
        EntityUtil.save(pollThemeQuestion, em);
        // Set files
        EntityUtil.bindEntityCollection(pollThemeQuestion.getPollThemeQuestionFiles(), p -> EntityUtil.getId(p.getOisFile()),
                questionCommand.getFiles(), p -> OisFileService.getId(p.getId()), dto -> {
                    PollThemeQuestionFile file = new PollThemeQuestionFile();
                    OisFile oisFile = EntityUtil.bindToEntity(dto, new OisFile());
                    file.setOisFile(EntityUtil.save(oisFile, em));
                    file.setPollThemeQuestion(pollThemeQuestion);
                    return file;
                });
    }

    public void updateQuestionOrderAfterDelete(HoisUserDetails user, PollTheme pollTheme) {
        for (short order = 1; order <= pollTheme.getPollThemeQuestions().size(); order++) {
            pollTheme.getPollThemeQuestions().get(order - 1).setOrderNr(Short.valueOf(order));
        }
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.save(pollTheme, em);
    }

    public void updateQuestionOrder(HoisUserDetails user, QuestionOrderCommand command) {
        for (QuestionDto question : command.getQuestions()) {
            PollThemeQuestion pollThemeQuestion = em.getReference(PollThemeQuestion.class, question.getId());
            pollThemeQuestion.setOrderNr(question.getOrderNr());
            EntityUtil.setUsername(user.getUsername(), em);
            EntityUtil.save(pollThemeQuestion, em);
        }
    }

    public void updatePollThemeQuestion(HoisUserDetails user, PollThemeQuestion pollThemeQuestion,
            QuestionCommand questionCommand) {
        savePollThemeQuestion(user, questionCommand, pollThemeQuestion);
    }

    public void confirm(HoisUserDetails user, Poll poll) {
        if (poll.getPollThemes() == null || poll.getPollThemes().size() == 0) {
            throw new HoisException("poll.basicData.confirmError");
        }
        boolean themeHasQuestion = false;
        for (PollTheme theme : poll.getPollThemes()) {
            if (theme.getPollThemeQuestions() != null || theme.getPollThemeQuestions().size() != 0) {
                themeHasQuestion = true;
                break;
            }
        }
        if (!themeHasQuestion) {
            throw new HoisException("poll.basicData.confirmError");
        }
        poll.setStatus(em.getReference(Classifier.class, PollStatus.KYSITLUS_STAATUS_K.name()));
        calculateTargetCounts(poll);
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.save(poll, em);
    }

    private void calculateTargetCounts(Poll poll) {
        String pollType = EntityUtil.getCode(poll.getType());
        if (PollType.KYSITLUS_Y.name().equals(pollType)) {
            setPollTargetCountOverall(poll);
        } else if (PollType.KYSITLUS_P.name().equals(pollType)) {
            setPollTargetCountPractice(poll);
        } else if (PollType.KYSITLUS_O.name().equals(pollType)) {
            setPollTargetCountJournalOrSubject(poll);
        } else if (PollType.KYSITLUS_V.name().equals(pollType)) {
            setPollTargetCountStudentCouncil(poll);
        } else if (PollType.KYSITLUS_T.name().equals(pollType)) {
            setPollTargetCountTeacher(poll);
        }
    }
    
    /**
     * 
     * @param poll
     */
    private void setPollTargetCountOverall(Poll poll) {
        // Students
        if (poll.getPollTargets().stream().anyMatch(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_O, p.getTarget()))) {
            setPollTargetCountOverallStudent(poll);
        }
        // Teachers
        if (poll.getPollTargets().stream().anyMatch(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_T, p.getTarget()))) {
            setPollTargetCountTeacher(poll);
        }
        // Enterprise supervisors
        if (poll.getPollTargets().stream().anyMatch(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_E, p.getTarget()))) {
            setPollTargetCountOverallSupervisor(poll);
        }
        // Admin
        if (poll.getPollTargets().stream().anyMatch(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_A, p.getTarget()))) {
            setPollTargetCountOverallAdmin(poll);
        }
        // Student representative
        if (poll.getPollTargets().stream().anyMatch(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_L, p.getTarget()))) {
            setPollTargetCountOverallRepresentative(poll);
        }
    }

    /**
     * Count student representatives in this school, filter by related student student group
     * @param poll
     */
    private void setPollTargetCountOverallRepresentative(Poll poll) {
        String SEARCH_SELECT = "count(distinct p.id)";
        String SEARCH_FROM = "from person p "
                + "join student_representative sr on sr.person_id = p.id "
                + "join student s on s.id = sr.student_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        qb.requiredCriteria("s.status_code in (:studentStatus)", "studentStatus", StudentStatus.STUDENT_STATUS_ACTIVE);
        List<PollStudentGroup> pollStudentGroups = poll.getPollStudentGroups();
        if (pollStudentGroups != null && !pollStudentGroups.isEmpty()) {
            qb.requiredCriteria("s.student_group_id in :studentGroups", 
                    "studentGroups",
                    pollStudentGroups.stream().map(p -> EntityUtil.getId(p.getStudentGroup())).collect(Collectors.toList()));
        }
        Object count = qb.select(SEARCH_SELECT, em).getSingleResult();
        Optional<PollTarget> target = poll.getPollTargets().stream().filter(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_L, p.getTarget())).findFirst();
        if (target.isPresent()) {
            PollTarget pollTarget = target.get();
            pollTarget.setTargetCount(JpaQueryUtil.resultAsInteger(count, 0));
        }
    }

    /**
     * Count admins in this school
     * @param poll
     */
    private void setPollTargetCountOverallAdmin(Poll poll) {
        String SEARCH_SELECT = "count(distinct u.id)";
        String SEARCH_FROM = "from user_ u";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);        
        qb.requiredCriteria("u.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        qb.requiredCriteria("u.role_code = :roleCode", "roleCode", Role.ROLL_A.name());
        Object count = qb.select(SEARCH_SELECT, em).getSingleResult();
        Optional<PollTarget> target = poll.getPollTargets().stream().filter(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_A, p.getTarget())).findFirst();
        if (target.isPresent()) {
            PollTarget pollTarget = target.get();
            pollTarget.setTargetCount(JpaQueryUtil.resultAsInteger(count, 0));
        }
    }

    /**
     * Count enterprise supervisors in this school in poll date range
     * @param poll
     */
    private void setPollTargetCountOverallSupervisor(Poll poll) {
        String SEARCH_SELECT = "count(distinct cs.id)";
        String SEARCH_FROM = "from practice_journal pj "
                + "join contract c on pj.contract_id = c.id "
                + "join contract_supervisor cs on cs.contract_id = c.id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("pj.end_date >= :startDate", "startDate", poll.getJournalFrom());
        qb.requiredCriteria("pj.end_date <= :endDate", "endDate", poll.getJournalThru());
        qb.requiredCriteria("pj.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        Object count = qb.select(SEARCH_SELECT, em).getSingleResult();
        Optional<PollTarget> target = poll.getPollTargets().stream().filter(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_E, p.getTarget())).findFirst();
        if (target.isPresent()) {
            PollTarget pollTarget = target.get();
            pollTarget.setTargetCount(JpaQueryUtil.resultAsInteger(count, 0));
        }
    }

    /**
     * Count students in this school, filter by student group if necessary
     * @param poll
     */
    private void setPollTargetCountOverallStudent(Poll poll) {
        String SEARCH_SELECT = "count(distinct s.id)";
        String SEARCH_FROM = "from student s";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);        
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        qb.requiredCriteria("s.status_code in (:studentStatus)", "studentStatus", StudentStatus.STUDENT_STATUS_ACTIVE);
        List<PollStudentGroup> pollStudentGroups = poll.getPollStudentGroups();
        if (pollStudentGroups != null && !pollStudentGroups.isEmpty()) {
            qb.requiredCriteria("s.student_group_id in :studentGroups", 
                    "studentGroups", 
                    pollStudentGroups.stream().map(p -> EntityUtil.getId(p.getStudentGroup())).collect(Collectors.toList()));
        }
        Object count = qb.select(SEARCH_SELECT, em).getSingleResult();
        Optional<PollTarget> target = poll.getPollTargets().stream().filter(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_O, p.getTarget())).findFirst();
        if (target.isPresent()) {
            PollTarget pollTarget = target.get();
            pollTarget.setTargetCount(JpaQueryUtil.resultAsInteger(count, 0));
        }
    }

    /**
     * Sets practice type poll (enterprise supervisor, teacher, student) target maximum count
     * Enterprise supervisor are counted from practice journals in range
     * Teachers are counted from practice journals in range
     * Students are counted from practice journals in range
     * If student groups are selected, practice journals are filtered by student's student group
     * @param poll
     */
    private void setPollTargetCountPractice(Poll poll) {
        // Students
        if (poll.getPollTargets().stream().anyMatch(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_O, p.getTarget()))) {
            setPollTargetCountPracticeStudent(poll);
        }
        // Teachers
        if (poll.getPollTargets().stream().anyMatch(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_T, p.getTarget()))) {
            setPollTargetCountPracticeTeacher(poll);
        }
        // Enterprise supervisors
        if (poll.getPollTargets().stream().anyMatch(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_E, p.getTarget()))) {
            setPollTargetCountPracticeSupervisor(poll);
        }
    }

    /**
     * Count contract supervisors (id) from practice journals in range
     * Student needs to be in selected student groups
     * @param poll
     */
    private void setPollTargetCountPracticeSupervisor(Poll poll) {
        String SEARCH_SELECT = "count(distinct cs.id)";
        String SEARCH_FROM = "from practice_journal pj "
                + "join student s on pj.student_id = s.id "
                + "join contract c on pj.contract_id = c.id "
                + "join contract_supervisor cs on cs.contract_id = c.id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("pj.end_date >= :startDate", "startDate", poll.getJournalFrom());
        qb.requiredCriteria("pj.end_date <= :endDate", "endDate", poll.getJournalThru());
        qb.requiredCriteria("pj.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        List<PollStudentGroup> pollStudentGroups = poll.getPollStudentGroups();
        if (pollStudentGroups != null && !pollStudentGroups.isEmpty()) {
            qb.requiredCriteria("s.student_group_id in :studentGroups", 
                    "studentGroups", 
                    pollStudentGroups.stream().map(p -> EntityUtil.getId(p.getStudentGroup())).collect(Collectors.toList()));
        }
        Object count = qb.select(SEARCH_SELECT, em).getSingleResult();
        Optional<PollTarget> target = poll.getPollTargets().stream().filter(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_E, p.getTarget())).findFirst();
        if (target.isPresent()) {
            PollTarget pollTarget = target.get();
            pollTarget.setTargetCount(JpaQueryUtil.resultAsInteger(count, 0));
        }
    }

    /**
     * If practice poll contains teacher as target
     * Teachers are counted from practice journals in range
     * Student needs to be in selected student groups
     * @param poll
     */
    private void setPollTargetCountPracticeTeacher(Poll poll) {
        String SEARCH_SELECT = "count(distinct t.id)";
        String SEARCH_FROM = "from practice_journal pj "
                + "join teacher t on pj.teacher_id = t.id "
                + "join student s on s.id = pj.student_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("pj.end_date >= :startDate", "startDate", poll.getJournalFrom());
        qb.requiredCriteria("pj.end_date <= :endDate", "endDate", poll.getJournalThru());
        qb.requiredCriteria("pj.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        // Only active teachers
        qb.filter("t.is_active = true");
        List<PollStudentGroup> pollStudentGroups = poll.getPollStudentGroups();
        if (pollStudentGroups != null && !pollStudentGroups.isEmpty()) {
            qb.requiredCriteria("s.student_group_id in :studentGroups", 
                    "studentGroups", 
                    pollStudentGroups.stream().map(p -> EntityUtil.getId(p.getStudentGroup())).collect(Collectors.toList()));
        }
        Object count = qb.select(SEARCH_SELECT, em).getSingleResult();
        Optional<PollTarget> target = poll.getPollTargets().stream().filter(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_T, p.getTarget())).findFirst();
        if (target.isPresent()) {
            PollTarget pollTarget = target.get();
            pollTarget.setTargetCount(JpaQueryUtil.resultAsInteger(count, 0));
        }
    }

    /**
     * If practice poll contains student as target
     * Students are counted from practice journals in range
     * Student needs to be in selected student groups
     * @param poll
     */
    private void setPollTargetCountPracticeStudent(Poll poll) {
        String SEARCH_SELECT = "count(distinct s.id)";
        String SEARCH_FROM = "from practice_journal pj "
                + "join student s on pj.student_id = s.id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("pj.end_date >= :startDate", "startDate", poll.getJournalFrom());
        qb.requiredCriteria("pj.end_date <= :endDate", "endDate", poll.getJournalThru());
        qb.requiredCriteria("pj.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        qb.requiredCriteria("s.status_code in (:studentStatus)", "studentStatus", StudentStatus.STUDENT_STATUS_ACTIVE);
        List<PollStudentGroup> pollStudentGroups = poll.getPollStudentGroups();
        if (pollStudentGroups != null && !pollStudentGroups.isEmpty()) {
            qb.requiredCriteria("s.student_group_id in :studentGroups", 
                    "studentGroups", 
                    pollStudentGroups.stream().map(p -> EntityUtil.getId(p.getStudentGroup())).collect(Collectors.toList()));
        }
        Object count = qb.select(SEARCH_SELECT, em).getSingleResult();
        Optional<PollTarget> target = poll.getPollTargets().stream().filter(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_O, p.getTarget())).findFirst();
        if (target.isPresent()) {
            PollTarget pollTarget = target.get();
            pollTarget.setTargetCount(JpaQueryUtil.resultAsInteger(count, 0));
        }
    }

    /**
     * Sets journal or subject type poll (student) target maximum count
     * @param poll
     */
    private void setPollTargetCountJournalOrSubject(Poll poll) {
        Set<Journal> journals = poll.getPollJournals().stream().filter(p -> p.getJournal() != null).map(p -> p.getJournal()).collect(Collectors.toSet());
        int sum = 0;
        if (!journals.isEmpty()) {
            Integer targetGroupJournal = getPollTargetCountJournal(journals, poll);
            if (targetGroupJournal != null) {
                sum += targetGroupJournal.intValue();
            }
        }
        StudyPeriod studyPeriod = poll.getStudyPeriod();
        if (studyPeriod != null) {
            Integer targetGroupSubject = getPollTargetCountSubject(studyPeriod, poll);
            if (targetGroupSubject != null) {
                sum += targetGroupSubject.intValue();
            }
        }
        // There is only one target group in journal or subject poll, 
        // however still get target group by filtering, for future possible target group addings
        Optional<PollTarget> targetOptional = poll.getPollTargets().stream()
                .filter( p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_O, p.getTarget())).findFirst();
        if (targetOptional.isPresent()) {
            PollTarget target = targetOptional.get();
            target.setTargetCount(Integer.valueOf(sum));
        }
    }
    
    /**
     * Sets subject type poll (student) target maximum count
     * @param poll
     */
    private Integer getPollTargetCountSubject(StudyPeriod studyPeriod, Poll poll) {
        // Subject type poll target is only student
        String SEARCH_SELECT = "count(distinct s.id)";
        String SEARCH_FROM = "from declaration d "
                + "join declaration_subject ds on d.id = ds.declaration_id "
                + "join student s on d.student_id = s.id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);        
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        qb.requiredCriteria("d.study_period_id = :studyPeriodId", "studyPeriodId", EntityUtil.getId(studyPeriod));
        qb.requiredCriteria("d.status_code = :statusCode", "statusCode", DeclarationStatus.OPINGUKAVA_STAATUS_K.name());
        qb.requiredCriteria("s.status_code in (:studentStatus)", "studentStatus", StudentStatus.STUDENT_STATUS_ACTIVE);
        Set<Long> subjectStudyPeriods = poll.getPollJournals().stream()
                .filter(p-> p.getSubjectStudyPeriod() != null)
                .map(p -> EntityUtil.getId(p.getSubjectStudyPeriod())).collect(Collectors.toSet());
        if (subjectStudyPeriods.isEmpty()) return Integer.valueOf(0);
        // Filter by subject study periods if they are present
        qb.optionalCriteria("ds.subject_study_period_id in :subjectStudyPeriods", "subjectStudyPeriods", subjectStudyPeriods);
        Object count = qb.select(SEARCH_SELECT, em).getSingleResult();
        return JpaQueryUtil.resultAsInteger(count, 0);
    }

    /**
     * Sets journal type poll (student) target maximum count
     * @param poll
     */
    private Integer getPollTargetCountJournal(Set<Journal> journals, Poll poll) {
        // Journal type poll target is only student
        String SEARCH_SELECT = "count(distinct s.id)";
        String SEARCH_FROM = "from journal j "
                + "join journal_student js on j.id = js.journal_id "
                + "join student s on s.id = js.student_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        qb.requiredCriteria("j.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        qb.requiredCriteria("s.status_code in (:studentStatus)", "studentStatus", StudentStatus.STUDENT_STATUS_ACTIVE);
        // Filter by journals, journal might not be present
        qb.optionalCriteria("j.id in :journalIds", "journalIds", 
                journals.stream().filter(p -> p != null)
                .map(p -> EntityUtil.getId(p)).collect(Collectors.toList()));
        Object count = qb.select(SEARCH_SELECT, em).getSingleResult();
        return JpaQueryUtil.resultAsInteger(count, 0);
    }

    /**
     * Sets Teacher type poll (teacher) target maximum count
     * @param poll
     */
    private void setPollTargetCountTeacher(Poll poll) {
        // Teacher type poll target is only teacher
        String SEARCH_SELECT = "count(distinct t.id)";
        String SEARCH_FROM = "from teacher t";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);        
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        // Teacher type poll also might have teacer occupations
        List<PollTeacherOccupation> occupations = poll.getPollTeacherOccupations();
        if (!occupations.isEmpty()) {
            qb.requiredCriteria("t.teacher_occupation_id in :occupations", 
                    "occupations", 
                    occupations.stream().map(p -> EntityUtil.getId(p.getTeacherOccupation())).collect(Collectors.toList()));
        }
        // Only active teachers
        qb.filter("t.is_active = true");
        Object count = qb.select(SEARCH_SELECT, em).getSingleResult();
        PollTarget target = poll.getPollTargets().get(0);
        if (ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_T, target.getTarget())) {
            target.setTargetCount(JpaQueryUtil.resultAsInteger(count, 0));
        }
    }
    
    /**
     * Sets student council type poll (student) target maximum count
     * @param poll
     */
    private void setPollTargetCountStudentCouncil(Poll poll) {
        // Student council type poll target is only student
        String SEARCH_SELECT = "count(distinct s.id)";
        String SEARCH_FROM = "from student s";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);        
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        qb.requiredCriteria("s.type_code != :guestType", "guestType", StudentType.OPPUR_K.name());
        qb.requiredCriteria("s.status_code in (:studentStatus)", "studentStatus", StudentStatus.STUDENT_STATUS_ACTIVE);
        Object count = qb.select(SEARCH_SELECT, em).getSingleResult();
        PollTarget target = poll.getPollTargets().get(0);
        if (ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_O, target.getTarget())) {
            target.setTargetCount(JpaQueryUtil.resultAsInteger(count, 0));
        }
    }

    public void deletePoll(HoisUserDetails user, Poll poll) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(poll, em);
    }

    public void unConfirm(HoisUserDetails user, Poll poll) {
        // can only remove confirmation, when there are no responses
        List<Response> responses = poll.getResponses();
        if (responses == null || responses.isEmpty()) {
            poll.setStatus(em.getReference(Classifier.class, PollStatus.KYSITLUS_STAATUS_E.name()));
            EntityUtil.setUsername(user.getUsername(), em);
            EntityUtil.save(poll, em);
        } else {
            throw new HoisException("poll.messages.unConfirmForbidden");
        }
    }

    public Set<AutocompleteResult> pollNames(HoisUserDetails user) {
        String SEARCH_FROM = "from poll p";
        String SEARCH_SELECT = "p.id, p.name_et";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("p.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.groupBy("p.id, p.name_et");
        List<?> results = qb.select(SEARCH_SELECT, em).getResultList();
        return StreamUtil.toMappedSet(r -> {
            return new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 1));
        }, results);
    }

    public Set<AutocompleteResult> questions(HoisUserDetails user, String pollType) {
        String SEARCH_FROM = "from question q join classifier c on q.type_code = c.code";
        String SEARCH_SELECT = "q.id, q.name_et, c.name_et as classifierName";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("q.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (PollType.KYSITLUS_V.name().equals(pollType)) {
            qb.filter("q.type_code = 'VASTUS_S'");
        } else {
            qb.filter("q.type_code != 'VASTUS_S'");
        }
        qb.groupBy("q.id, q.name_et, c.name_et");
        List<?> results = qb.select(SEARCH_SELECT, em).getResultList();
        return StreamUtil.toMappedSet(r -> {
            String type = resultAsString(r, 2);
            String name = resultAsString(r, 1);
            String nameAndType = name + " (" + type + ")";
            return new AutocompleteResult(resultAsLong(r, 0), nameAndType, nameAndType);
        }, results);
    }

    public QuestionDto question(Question question) {
        QuestionDto dto = new QuestionDto();
        EntityUtil.bindToDto(question, dto, "type");
        if (question.getType() != null) {
            dto.setType(question.getType().getCode());
        }
        List<PollThemeQuestion> pollThemeQuestions = question.getPollThemeQuestions();
        int usages = pollThemeQuestions.size();
        if (usages != 1) {
            dto.setDisabled(Boolean.TRUE);
        }
        
        // Set question answers
        List<QuestionAnswerDto> questionAnswers = new ArrayList<>();
        for (QuestionAnswer answer : question.getQuestionAnswers()) {
            QuestionAnswerDto answerDto = new QuestionAnswerDto();
            EntityUtil.bindToDto(answer, answerDto);
            questionAnswers.add(answerDto);
        }
        Collections.sort(questionAnswers);
        dto.setAnswers(questionAnswers);
        return dto;
    }

    public Poll createCopyOfPoll(HoisUserDetails user, Poll poll) {
        EntityUtil.setUsername(user.getUsername(), em);
        Poll pollCopy = new Poll(poll, em.getReference(Classifier.class, PollStatus.KYSITLUS_STAATUS_E.name()), pollNames(user));
        SchoolType type = schoolService.schoolType(user.getSchoolId());
        if (ClassifierUtil.equals(PollType.KYSITLUS_O, pollCopy.getType()) && type.isHigher()) {
            Long studyPeriodId = studyYearService.getCurrentStudyPeriod(user.getSchoolId());
            if (studyPeriodId != null) {
                pollCopy.setStudyPeriod(em.getReference(StudyPeriod.class, studyPeriodId));
            }
        }
        copyStudentGroups(poll, pollCopy);
        copyTargets(poll, pollCopy);
        if (pollCopy.getPollTargets().stream().anyMatch(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_V, p.getTarget()))) pollCopy.setPollUrl(generateUniqueUrl());
        copyPollThemes(poll, pollCopy);
        return EntityUtil.save(pollCopy, em);
    }
    
    private static void copyPollThemes(Poll poll, Poll pollCopy) {
        List<PollTheme> pollThemeCopies = new ArrayList<>();
        for (PollTheme theme : poll.getPollThemes()) {
            PollTheme newTheme = new PollTheme();
            newTheme.setPoll(pollCopy);
            newTheme.setNameEn(theme.getNameEn());
            newTheme.setNameEt(theme.getNameEt());
            newTheme.setOrderNr(theme.getOrderNr());
            newTheme.setIsRepetitive(theme.getIsRepetitive());
            newTheme.setIsTeacher(theme.getIsTeacher());
            copyQuestions(theme, newTheme);
            pollThemeCopies.add(newTheme);
        }
        pollCopy.setPollThemes(pollThemeCopies);
    }

    private static void copyQuestions(PollTheme theme, PollTheme newTheme) {
        List<PollThemeQuestion> newPollThemeQuestions = new ArrayList<>();
        for (PollThemeQuestion pollThemeQuestion : theme.getPollThemeQuestions()) {
            PollThemeQuestion newPollThemeQuestion = new PollThemeQuestion();
            newPollThemeQuestion.setIsInRow(pollThemeQuestion.getIsInRow());
            newPollThemeQuestion.setIsRequired(pollThemeQuestion.getIsRequired());
            newPollThemeQuestion.setOrderNr(pollThemeQuestion.getOrderNr());
            // Question wont be new
            newPollThemeQuestion.setQuestion(pollThemeQuestion.getQuestion());
            newPollThemeQuestion.setPollTheme(newTheme);
            // Set new PollThemeQuestionFiles, so they won't get deleted with another PollThemeQuestion
            copyAnswers(pollThemeQuestion, newPollThemeQuestion);
            newPollThemeQuestions.add(newPollThemeQuestion);
        }
        newTheme.setPollThemeQuestions(newPollThemeQuestions);
    }

    private static void copyAnswers(PollThemeQuestion pollThemeQuestion, PollThemeQuestion newPollThemeQuestion) {
        List<PollThemeQuestionFile> pollThemeQuestionFileCopies = new ArrayList<>();
        for (PollThemeQuestionFile pollThemeQuestionFile : pollThemeQuestion.getPollThemeQuestionFiles()) {
            PollThemeQuestionFile newPollThemeQuestionFile = new PollThemeQuestionFile();
            newPollThemeQuestionFile.setOisFile(pollThemeQuestionFile.getOisFile());
            newPollThemeQuestionFile.setPollThemeQuestion(newPollThemeQuestion);
            pollThemeQuestionFileCopies.add(newPollThemeQuestionFile);
        }
        newPollThemeQuestion.setPollThemeQuestionFiles(pollThemeQuestionFileCopies);
    }

    private static void copyStudentGroups(Poll poll, Poll pollCopy) {
        List<PollStudentGroup> studentGroupCopies = new ArrayList<>();
        for (PollStudentGroup pollStudentGroup : poll.getPollStudentGroups()) {
            PollStudentGroup studentGroupCopy = new PollStudentGroup();
            studentGroupCopy.setPoll(pollCopy);
            studentGroupCopy.setStudentGroup(pollStudentGroup.getStudentGroup());
            studentGroupCopies.add(studentGroupCopy);
        }
        pollCopy.setPollStudentGroups(studentGroupCopies);
    }
    
    private static void copyTargets(Poll poll, Poll pollCopy) {
        List<PollTarget> pollTargetCopies = new ArrayList<>();
        for (PollTarget target : poll.getPollTargets()) {
            PollTarget targetCopy = new PollTarget();
            targetCopy.setPoll(pollCopy);
            targetCopy.setTarget(target.getTarget());
            pollTargetCopies.add(targetCopy);
        }
        pollCopy.setPollTargets(pollTargetCopies);
    }
    
    private static String generateUniqueUrl() {
        return UUID.randomUUID().toString();
    }
    
    private static List<Poll> filterPolls(List<Poll> polls) {
        return polls.stream()
                // Select only ÜLDINE and PRAKTIKA polls
                .filter(p -> ClassifierUtil.equals(PollType.KYSITLUS_P, p.getType()) || ClassifierUtil.equals(PollType.KYSITLUS_Y, p.getType()))
                // Must have journal dates and targets enterprise supervisors
                .filter(p -> p.getJournalFrom() != null && p.getJournalThru() != null && 
                    p.getPollTargets().stream().anyMatch(pt -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_E, pt.getTarget())))
                .collect(Collectors.toList());
    }
    
    /**
     * Used in Job Executor to send emails to supervisors daily containing Poll
     */
    public void sendPracticeJournalSupervisorEmails() {
        List<Poll> polls = em.createQuery("select p from Poll p "
                + "where ?3 between p.validFrom and p.validThru "
                + "and ?3 = p.reminderDt "
                + "and p.status.code = ?1", Poll.class)
                .setParameter(1, PollStatus.KYSITLUS_STAATUS_K.name())
                .setParameter(3, LocalDate.now())
                .getResultList();
        
        polls = filterPolls(polls);
        
        for (Poll poll : polls) {
            List<PracticeJournal> practiceJournals = em.createQuery("select pj from PracticeJournal pj "
                    + "where pj.endDate between ?1 and ?2 and pj.school.id = ?3", PracticeJournal.class)
                    .setParameter(1, poll.getJournalFrom())
                    .setParameter(2, poll.getJournalThru())
                    .setParameter(3, EntityUtil.getId(poll.getSchool()))
                    .getResultList();
            boolean isOverall = ClassifierUtil.equals(PollType.KYSITLUS_Y, poll.getType());
            List<String> sentEmails = new ArrayList<>();
            for (PracticeJournal journal : practiceJournals) {
                Contract contract = journal.getContract();
                List<Long> targetStudentGroups = poll.getPollStudentGroups().stream().map(p -> EntityUtil.getId(p.getStudentGroup())).collect(Collectors.toList());
                if (contract != null && (isOverall || (targetStudentGroups.isEmpty() || 
                        (journal.getStudent() != null && journal.getStudent().getStudentGroup() != null &&  targetStudentGroups.contains(journal.getStudent().getStudentGroup().getId()))))) {
                    List<ContractSupervisor> supervisors = contract.getContractSupervisors().stream().filter(p -> p.getSupervisorEmail() != null).collect(Collectors.toList());
                    for (ContractSupervisor supervisor : supervisors) {
                        if (poll.getResponses().stream().noneMatch(p -> p.getResponseObject() != null &&
                                p.getResponseObject().getContractSupervisor() != null &&
                                p.getResponseObject().getContractSupervisor().getId().equals(supervisor.getId()) &&
                                ((p.getResponseObject().getPracticeJournal() != null &&
                                p.getResponseObject().getPracticeJournal().getId().equals(journal.getId())) || isOverall))) {
                            Student student = journal.getStudent();
                            Response response = new Response();
                            response.setPoll(poll);
                            response.setStatus(em.getReference(Classifier.class, ResponseStatus.KYSITVASTUSSTAATUS_A.name()));
                            ResponseObject responseObject = new ResponseObject();
                            responseObject.setResponse(response);
                            responseObject.setPollUrl(generateUniqueUrl());
                            responseObject.setContractSupervisor(supervisor);
                            if (!isOverall) responseObject.setPracticeJournal(journal);
                            if (student != null && !isOverall) {
                                responseObject.setStudent(student);
                                responseObject.setCurriculumVersion(student.getCurriculumVersion());
                                responseObject.setStudyForm(student.getStudyForm());
                                responseObject.setPerson(student.getPerson());
                            }
                            Optional<PollTarget> pollTarget = poll.getPollTargets().stream().filter(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_E, p.getTarget())).findFirst();
                            if (pollTarget.isPresent()) responseObject.setPollTarget(pollTarget.get());
                            response.setResponseObject(responseObject);
                            EntityUtil.save(response, em);
                            em.persist(responseObject);
                            // send only one poll to one supervisor if poll type is KYSITLUS_Y, else send poll per journal
                            if (isOverall && !sentEmails.contains(supervisor.getSupervisorEmail())) {
                                sentEmails.add(supervisor.getSupervisorEmail());
                                sendUniqueUrlEmailToEnterpriseSupervisor(journal.getSchool(), responseObject, student);
                            } else if (!isOverall) {
                                sendUniqueUrlEmailToEnterpriseSupervisor(journal.getSchool(), responseObject, student);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void sendUniqueUrlEmailToEnterpriseSupervisor(School school, ResponseObject responseObj, Student student) {
        String url = getFullUrlSupervisor(responseObj.getPollUrl());
        ContractSupervisorMessage data = new ContractSupervisorMessage(student, url);
        automaticMessageService.sendMessageToEnterprise(responseObj.getContractSupervisor(), school, MessageType.TEATE_LIIK_KYSI_EV_JUHENDAJA, data);
    }

    private String getFullUrlSupervisor(String uuid) {
        return frontendBaseUrl + "poll/supervisor/" + uuid;
    }
    
    private String getFullUrlExpert(String uuid) {
        return frontendBaseUrl + "poll/expert/" + uuid;
    }

    public Poll getPollFromUrlSupervisor(String uuid) {
        ResponseObject responseObj = getResponseObjectByUuid(uuid);
        Poll poll = responseObj.getResponse().getPoll();
        if (poll == null) {
            log.error("no poll found. uuid={}, ResponseObject={}", uuid, responseObj.getResponse().getId());
            throw new HoisException("poll.messages.noPoll");
        }
        return poll;
    }
    
    public Poll getPollFromUrlExpert(String uuid) {
        return em.createQuery("select p from Poll p where p.pollUrl = ?1", Poll.class)
                .setParameter(1, uuid)
                .setMaxResults(1).getSingleResult();
    }
    
    private ResponseObject getResponseObjectByUuid(String uuid) {
        List<ResponseObject> data = em.createQuery("select ro from ResponseObject ro where ro.pollUrl = ?1", ResponseObject.class)
                .setParameter(1, uuid)
                .setMaxResults(1).getResultList();
        if (data.isEmpty()) {
            log.error("no ResponseObject found. uuid={}", uuid);
            throw new HoisException("poll.messages.noResponse");
        }
        return data.get(0);
    }

    public void assertCanView(Poll poll) {
        if (poll == null) {
            throw new ValidationFailedException("poll.messages.noPollFound");
        } else if (!ClassifierUtil.equals(PollStatus.KYSITLUS_STAATUS_K, poll.getStatus())) {
            throw new ValidationFailedException("poll.messages.statusNotConfirmed");
        } else if (LocalDate.now()
                .isBefore(poll.getValidFrom())) {
            throw new ValidationFailedException("poll.messages.acceptingAnswersHasntStarted");
        } else if (LocalDate.now()
                .isAfter(poll.getValidThru())) {
            throw new ValidationFailedException("poll.messages.acceptingAnswersHasEnded");
        }
    }
    
    public PracticeThemesDto themesForPractice(Poll poll, Response response, Boolean view) {
        PracticeThemesDto themes = new PracticeThemesDto(themes(poll, null));
        ResponseObject responseObject = response.getResponseObject();
        if (responseObject != null) {
            if (poll.getSchool() != null) themes.setSchool(new AutocompleteResult(null, poll.getSchool().getNameEt(), poll.getSchool().getNameEn()));
            PracticeJournal practiceJournal = responseObject.getPracticeJournal();
            if (practiceJournal != null) {
                themes.setPracticeFrom(responseObject.getPracticeJournal().getStartDate());
                themes.setPracticeThru(responseObject.getPracticeJournal().getEndDate());
                if (practiceJournal.getContract() != null && practiceJournal.getContract() != null && practiceJournal.getContract().getEnterprise() != null) {
                    themes.setEnterprise(practiceJournal.getContract().getEnterprise().getName());
                }
                
                Person person = practiceJournal.getStudent().getPerson();
                if (person != null) themes.setStudent(String.join(" ", person.getFirstname(), person.getLastname()));
                StudentGroup studentGroup = practiceJournal.getStudent().getStudentGroup();
                if (studentGroup != null) {
                    themes.setStudentGroup(AutocompleteResult.of(studentGroup));
                }
            }
        }
        setPollValuesToDto(response, themes, view);
        themes.setConfirmed(ResponseStatus.KYSITVASTUSSTAATUS_V.name().equals(response.getStatus().getCode()) ? Boolean.TRUE : Boolean.FALSE);
        themes.setResponseId(EntityUtil.getId(response));
        return themes;
    }
    
    /**
     * Used for hooking new answers to Dto
     * @param poll
     * @param uuid
     * @return dto with answers
     */
    public PracticeThemesDto themesWithAnswers(Poll poll, String uuid) {
        Response response = getResponse(poll, uuid);
        PracticeThemesDto dto = themesForPractice(poll, response, Boolean.FALSE);
        return dto;
    }
    
    /**
     * Used for hooking new answers to Dto
     * @param poll
     * @param uuid
     * @return dto with answers
     */
    public ThemesDto themesWithAnswersExpert(Poll poll, String uuid) {
        ThemesDto dto = null;
        Response response = new Response();
        response.setPoll(poll);
        response.setStatus(em.getReference(Classifier.class, ResponseStatus.KYSITVASTUSSTAATUS_A.name()));
        ResponseObject responseObject = new ResponseObject();
        responseObject.setResponse(response);
        Optional<PollTarget> pollTarget = poll.getPollTargets().stream().filter(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_V, p.getTarget())).findFirst();
        if (pollTarget.isPresent()) {
            responseObject.setPollTarget(pollTarget.get());
        }
        response.setResponseObject(responseObject);
        EntityUtil.save(response, em);
        em.persist(responseObject);
        dto = themes(poll, null);
        dto.setResponseId(EntityUtil.getId(response));
        dto.setConfirmed(ResponseStatus.KYSITVASTUSSTAATUS_V.name().equals(response.getStatus().getCode()) ? Boolean.TRUE : Boolean.FALSE);
        return dto;
    }
    
    /**
     * User won't be head admin or supervisor or ROLL_X
     * @param user
     * @return
     */
    public LinkedHashSet<ResponseDto> getPolls(HoisUserDetails user) {
        // Validate user
        boolean isUserValid = validateUserForPoll(user);
        if (!isUserValid) return new LinkedHashSet<>();
        // Response might be found but response_object wont be present, thus response_object id should be used in dto
        String pollSelect = "p.id, p.type_code, ro.response_id as responseId, r.status_code, p.valid_from, p.valid_thru, p.is_theme_pageable,"
                + " e.name, pe.firstname, pe.lastname, pj.start_date, pj.end_date, pt.target_code,"
                + "p.name_et, p.name_en, p.journal_from, p.journal_thru";
        JpaNativeQueryBuilder pollQuery = new JpaNativeQueryBuilder("from poll p "
                + "join poll_target pt on p.id = pt.poll_id "
                + "left join response r on r.poll_id = p.id "
                + "left join response_object ro on (ro.response_id = r.id and ro.poll_target_id = pt.id and ro.person_id = " + user.getPersonId()
                + (user.isStudent() ? " and ro.student_id = " + user.getStudentId() : "")
                + (user.isTeacher() ? " and ro.teacher_id = " + user.getTeacherId() : "") + ") "
                + "left join practice_journal pj on pj.id = ro.practice_journal_id "
                + "left join student s on pj.student_id = s.id "
                + "left join person pe on s.person_id = pe.id " 
                + "left join contract c on pj.contract_id = c.id "
                + "left join enterprise e on c.enterprise_id = e.id");
        pollQuery.requiredCriteria("p.status_code = :pollStatusCode", "pollStatusCode", PollStatus.KYSITLUS_STAATUS_K.name());
        pollQuery.requiredCriteria("p.school_id = :schoolId", "schoolId",  user.getSchoolId());
        pollQuery.requiredCriteria("pt.target_code = :targetCode", "targetCode", getTargetCode(user));
        pollQuery.requiredCriteria("(p.valid_from <= :now and p.valid_thru >= :now)", "now", LocalDate.now());
        if (user.isStudent() && user.getStudentId() != null) {
            Student student = em.getReference(Student.class, user.getStudentId());
            if (ClassifierUtil.equals(StudentType.OPPUR_K, student.getType())) {
                pollQuery.requiredCriteria("p.type_code != :pollType", "pollType", PollType.KYSITLUS_V.name());
            }
        }
        // TODO: filter this when user changes role - meaning teacher occupation, studentgroups when person id is the same
        List<?> dbPolls = pollQuery.select(pollSelect, em).getResultList();
        List<PollTypeDto> polls = StreamUtil
                .toMappedList(r-> {
                    PollTypeDto dto = new PollTypeDto();
                    dto.setId(resultAsLong(r, 0));
                    dto.setType(resultAsString(r, 1));
                    dto.setResponseId(resultAsLong(r, 2));
                    dto.setResponseStatus(resultAsString(r, 3));
                    dto.setPollValidFrom(JpaQueryUtil.resultAsLocalDate(r, 4));
                    dto.setPollValidThru(JpaQueryUtil.resultAsLocalDate(r, 5));
                    dto.setPollThemePageable(JpaQueryUtil.resultAsBoolean(r, 6));
                    dto.setEnterpriseName(resultAsString(r, 7));
                    dto.setContractStudentName(PersonUtil.fullname(resultAsString(r, 8), resultAsString(r, 9)));
                    LocalDate pjStartDate = JpaQueryUtil.resultAsLocalDate(r, 10);
                    LocalDate pjEndDate = JpaQueryUtil.resultAsLocalDate(r, 11);
                    if (pjStartDate != null && pjEndDate != null) {
                        dto.setPracticeDuration(dateFormatHois.format(JpaQueryUtil.resultAsLocalDate(r, 10)) + 
                                " - " + dateFormatHois.format(JpaQueryUtil.resultAsLocalDate(r, 11)));
                    }
                    dto.setPracticeJournalFrom(pjStartDate);
                    dto.setPracticeJournalThru(pjEndDate);
                    dto.setTargetCode(resultAsString(r, 12));
                    dto.setPollNameEt(resultAsString(r, 13));
                    dto.setPollNameEn(resultAsString(r, 14));
                    dto.setSearchJournalFrom(JpaQueryUtil.resultAsLocalDate(r, 15));
                    dto.setSearchJournalThru(JpaQueryUtil.resultAsLocalDate(r, 16));
                    return dto;
                }, dbPolls);
        // With polls that are confirmed
        List<PollTypeDto> allReadyToAnswer = polls.stream().filter(p -> p.getResponseId() != null).collect(Collectors.toList());
        // Polls that arent confirmed
        List<PollTypeDto> readyToAnswerPolls = allReadyToAnswer.stream().filter(p -> !p.getResponseStatus().equals(ResponseStatus.KYSITVASTUSSTAATUS_V.name())).collect(Collectors.toList());
        // Used when migrating from one representative poll per student representative to one representative poll per person
        filterExcessStudentRepresentativePolls(readyToAnswerPolls);
        // Polls that need response
        List<PollTypeDto> toCreateResponses = polls.stream()
                .filter(p -> p.getResponseId() == null)
                .filter(StreamUtil.distinctByKey(PollTypeDto::getId))
                // Check that this poll isnt already answered
                .filter(p -> allReadyToAnswer.stream().noneMatch(s -> s.getId().equals(p.getId())))
                .collect(Collectors.toList());
        // Newly created polls, ready to answer
        List<PollTypeDto> responses = findAndGenerateResponses(toCreateResponses, user);
        readyToAnswerPolls.addAll(responses);
        // Order polls by end date and by name
        Collections.sort(readyToAnswerPolls, Comparator.comparing(PollTypeDto::getPollValidThru).thenComparing((p, v) -> {
            return p.getPollNameEt().compareToIgnoreCase(v.getPollNameEt());
        }));
        return readyToAnswerPolls.stream().map(p -> {
            ResponseDto dto = new ResponseDto();
            dto.setId(p.getResponseId());
            setDtoName(p, dto, user);
            dto.setValidFrom(p.getPollValidFrom());
            dto.setValidThru(p.getPollValidThru());
            dto.setIsThemePageable(p.getPollThemePageable());
            dto.setType(p.getType());
            dto.setStatus(p.getResponseStatus());
            return dto;
        }).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static void filterExcessStudentRepresentativePolls(List<PollTypeDto> readyToAnswerPolls) {
        Iterator<PollTypeDto> iterator = readyToAnswerPolls.iterator();
        List<Long> seenRepresentativePolls = new ArrayList<>();
        while (iterator.hasNext()) {
            PollTypeDto dto = iterator.next();
            if (PollTargets.KYSITLUS_SIHT_L.name().equals(dto.getTargetCode())) {
                if (seenRepresentativePolls.contains(dto.getId())) {
                    iterator.remove();
                } else {
                    seenRepresentativePolls.add(dto.getId());
                }
            }
        }
    }

    private static void setDtoName(PollTypeDto pollType, ResponseDto dto, HoisUserDetails user) {
        String enterpriseName = pollType.getEnterpriseName();
        String studentName = pollType.getContractStudentName();
        String practiceDuration = pollType.getPracticeDuration();
        if (PollTargets.KYSITLUS_SIHT_O.name().equals(pollType.getTargetCode()) &&
                user.isStudent() && practiceDuration != null) {
            String addInfo;
            if (enterpriseName != null) {
                addInfo = " (" + enterpriseName + ", " + practiceDuration + ")";
            } else {
                addInfo = " (" + practiceDuration + ")";
            }
            String nameEt = pollType.getPollNameEt() + addInfo;
            String nameEn = pollType.getPollNameEn() + addInfo;
            dto.setName(new AutocompleteResult(null, nameEt, nameEn));
        } else if (PollTargets.KYSITLUS_SIHT_T.name().equals(pollType.getTargetCode()) && 
                user.isTeacher() && studentName != null && practiceDuration != null) {
            String addInfo;
            if (enterpriseName != null) {
                addInfo = " (" + enterpriseName + ", " + practiceDuration + ", " + studentName + ")";
            } else {
                addInfo = " (" + practiceDuration + ", " + studentName + ")";
            }
            String nameEt = pollType.getPollNameEt() + addInfo;
            String nameEn = pollType.getPollNameEn() + addInfo;
            dto.setName(new AutocompleteResult(null, nameEt, nameEn));
        } else {
            dto.setName(new AutocompleteResult(null, pollType.getPollNameEt(), pollType.getPollNameEn()));
        }
    }
    
    private void generateResponseForGeneralTypePoll(PollTypeDto pollType, HoisUserDetails user, List<PollTypeDto> responses) {
        Response response = new Response();
        Student student = null;
        Teacher teacher = null;
        Person person = em.getReference(Person.class, user.getPersonId());
        Poll poll = em.getReference(Poll.class, pollType.getId());
        response.setPoll(poll);
        response.setStatus(em.getReference(Classifier.class, ResponseStatus.KYSITVASTUSSTAATUS_A.name()));
        pollType.setResponseStatus(ResponseStatus.KYSITVASTUSSTAATUS_A.name());
        ResponseObject responseObject = new ResponseObject();
        responseObject.setResponse(response);
        responseObject.setPerson(person);
        if (user.isStudent()) {
            List<Long> studentGroupIds = getPollRelatedStudentGroups(pollType.getId());
            student = em.getReference(Student.class, user.getStudentId());
            if (studentGroupIds == null || studentGroupIds.isEmpty() || (student.getStudentGroup() != null && 
                    studentGroupIds.contains(EntityUtil.getId(student.getStudentGroup())))) {
                responseObject.setStudent(student);
                responseObject.setCurriculumVersion(student.getCurriculumVersion());
                responseObject.setStudyForm(student.getStudyForm());
            } else {
                return;
            }
        } else if (user.isTeacher()) {
            List<Long> targetOccupations = getPollRelatedOccupations(pollType.getId());
            teacher = em.getReference(Teacher.class, user.getTeacherId());
            if (teacher.getIsActive().booleanValue() && (targetOccupations.isEmpty() || targetOccupations.contains(EntityUtil.getId(teacher.getTeacherOccupation())))) {
                responseObject.setTeacher(teacher);
            } else {
                return;
            }
        } else if (user.isRepresentative()) {
            List<Long> studentGroupIds = getPollRelatedStudentGroups(pollType.getId());
            student = em.getReference(Student.class, user.getStudentId());
            if (ClassifierUtil.oneOf(student.getStatus(), StudentStatus.OPPURSTAATUS_O, StudentStatus.OPPURSTAATUS_A, StudentStatus.OPPURSTAATUS_V) &&
                (studentGroupIds == null || studentGroupIds.isEmpty() || (student.getStudentGroup() != null && studentGroupIds.contains(EntityUtil.getId(student.getStudentGroup()))))) {
                responseObject.setStudent(student);
            } else {
                return;
            }
        }
        setPollTarget(poll, user, responseObject);
        response.setResponseObject(responseObject);
        EntityUtil.save(response, em);
        em.persist(responseObject);
        pollType.setResponseId(EntityUtil.getId(response));
        responses.add(pollType);
    }
    
    private List<Long> getPollRelatedOccupations(Long pollId) {
        String SEARCH_SELECT = "pto.teacher_occupation_id";
        String SEARCH_FROM = "from poll_teacher_occupation pto";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("pto.poll_id = :pollId", "pollId", pollId);
        List<?> dbOccupations = qb.select(SEARCH_SELECT, em).getResultList();
        List<Long> occupationIds = StreamUtil
                .toMappedList(r-> resultAsLong(r, 0), dbOccupations);
        return occupationIds;
    }

    private void generateResponseForPracticeTypePoll(PollTypeDto pollType, HoisUserDetails user, List<PollTypeDto> responses) {
        List<Long> pollRelatedStudentGroups = getPollRelatedStudentGroups(pollType.getId());
        List<PollPracticeJournalDto> practiceJournalIds = getPracticeJournalIds(pollType, user, pollRelatedStudentGroups);
        Poll poll = em.getReference(Poll.class, pollType.getId());
        for (PollPracticeJournalDto journalDto : practiceJournalIds) {
            PracticeJournal journal = em.getReference(PracticeJournal.class, journalDto.getId());
            Response response = new Response();
            Student student = null;
            Teacher teacher = null;
            Person person = em.getReference(Person.class, user.getPersonId());
            response.setPoll(poll);
            response.setStatus(em.getReference(Classifier.class, ResponseStatus.KYSITVASTUSSTAATUS_A.name()));
            ResponseObject responseObject = new ResponseObject();
            responseObject.setResponse(response);
            responseObject.setPracticeJournal(journal);
            responseObject.setPerson(person);
            if (user.isStudent()) {
                student = em.getReference(Student.class, user.getStudentId());
                responseObject.setStudent(student);
                responseObject.setCurriculumVersion(student.getCurriculumVersion());
                responseObject.setStudyForm(student.getStudyForm());
            }
            if (user.isTeacher()) {
                teacher = em.getReference(Teacher.class, user.getTeacherId());
                responseObject.setTeacher(teacher);
            }
            setPollTarget(poll, user, responseObject);
            response.setResponseObject(responseObject);
            EntityUtil.save(response, em);
            em.persist(responseObject);
            // Create copy of poll dto
            PollTypeDto newPollTypeDto = new PollTypeDto(pollType);
            newPollTypeDto.setResponseStatus(ResponseStatus.KYSITVASTUSSTAATUS_A.name());
            newPollTypeDto.setEnterpriseName(journalDto.getEnterpriseName());
            newPollTypeDto.setPracticeJournalFrom(journalDto.getStartDate());
            newPollTypeDto.setPracticeJournalThru(journalDto.getEndDate());
            newPollTypeDto.setContractStudentName(journalDto.getContractStudentName());
            newPollTypeDto.setPracticeDuration(journalDto.getPracticeDuration());
            newPollTypeDto.setResponseId(EntityUtil.getId(response));
            responses.add(newPollTypeDto);
        }
    }
    
    private List<Long> getPollRelatedStudentGroups(Long pollId) {
        String SEARCH_SELECT = "psg.student_group_id";
        String SEARCH_FROM = "from poll_student_group psg";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("psg.poll_id = :pollId", "pollId", pollId);
        List<?> dbStudentGroups = qb.select(SEARCH_SELECT, em).getResultList();
        List<Long> studentGroupIds = StreamUtil
                .toMappedList(r-> resultAsLong(r, 0), dbStudentGroups);
        return studentGroupIds;
    }

    private List<PollPracticeJournalDto> getPracticeJournalIds(PollTypeDto pollType, HoisUserDetails user, List<Long> pollRelatedStudentGroups) {
        String SEARCH_SELECT = "distinct pj.id, pj.start_date, pj.end_date, e.name, pe.firstname, pe.lastname";
        String SEARCH_FROM = "from practice_journal pj "
                + "left join student s on pj.student_id = s.id "
                + "left join person pe on s.person_id = pe.id "
                + "left join contract c on pj.contract_id = c.id "
                + "left join enterprise e on c.enterprise_id = e.id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("pj.end_date >= :startDate", "startDate", pollType.getSearchJournalFrom());
        qb.requiredCriteria("pj.end_date <= :endDate", "endDate", pollType.getSearchJournalThru());
        if (user.isTeacher()) qb.requiredCriteria("pj.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        if (user.isStudent()) qb.requiredCriteria("pj.student_id = :studentId", "studentId", user.getStudentId());
        if (pollRelatedStudentGroups != null && !pollRelatedStudentGroups.isEmpty()) qb.requiredCriteria("s.student_group_id in :studentGroups", "studentGroups", pollRelatedStudentGroups);
        List<?> dbJournals = qb.select(SEARCH_SELECT, em).getResultList();
        List<PollPracticeJournalDto> journalIds = StreamUtil
                .toMappedList(r-> {
                    PollPracticeJournalDto dto = new PollPracticeJournalDto();
                    dto.setId(resultAsLong(r, 0));
                    dto.setEnterpriseName(resultAsString(r, 3));
                    dto.setContractStudentName(PersonUtil.fullname(resultAsString(r, 4), resultAsString(r, 5)));
                    LocalDate pjStartDate = JpaQueryUtil.resultAsLocalDate(r, 1);
                    LocalDate pjEndDate = JpaQueryUtil.resultAsLocalDate(r, 2);
                    if (pjStartDate != null && pjEndDate != null) {
                        dto.setPracticeDuration(dateFormatHois.format(pjStartDate) + 
                                " - " + dateFormatHois.format(pjEndDate));
                    }
                    dto.setStartDate(pjStartDate);
                    dto.setEndDate(pjEndDate);
                    return dto;
                }, dbJournals);
        return journalIds;
    }

    private void generateResponseForSubjectOrJournalTypePoll(PollTypeDto pollType, HoisUserDetails user, List<PollTypeDto> responses) {
        Response response = new Response();
        Poll poll = em.getReference(Poll.class, pollType.getId());
        response.setPoll(poll);
        response.setStatus(em.getReference(Classifier.class, ResponseStatus.KYSITVASTUSSTAATUS_A.name()));
        pollType.setResponseStatus(ResponseStatus.KYSITVASTUSSTAATUS_A.name());
        ResponseObject responseObject = new ResponseObject();
        responseObject.setResponse(response);
        Person person = em.getReference(Person.class, user.getPersonId());
        responseObject.setPerson(person);
        Student student = em.getReference(Student.class, user.getStudentId());
        setPollTarget(poll, user, responseObject);
        response.setResponseObject(responseObject);
        responseObject.setStudent(student);
        responseObject.setCurriculumVersion(student.getCurriculumVersion());
        responseObject.setStudyForm(student.getStudyForm());
        List<ResponseSubject> responseSubjects = new ArrayList<>();
        if (StudentUtil.isHigher(student) && poll.getStudyPeriod() != null) {
            List<SubjectAndSubjectStudyPeriod> subjectAndPeriods = getDistinctSubjects(poll, user.getStudentId());
            for (SubjectAndSubjectStudyPeriod subjectAndPeriod : subjectAndPeriods) {
                ResponseSubject responseSubject = new ResponseSubject();
                responseSubject.setResponse(response);
                responseSubject.setSubject(em.getReference(Subject.class, subjectAndPeriod.getSubjectId()));
                responseSubject.setSubjectStudyPeriod(em.getReference(SubjectStudyPeriod.class, subjectAndPeriod.getSubjectStudyPeriodId()));
                responseSubjects.add(responseSubject);
            }
        } 
        if (StudentUtil.isVocational(student)) {
            List<Long> journalIds = getUserRelatedJournals(poll, user);
            for (Long journalId : journalIds) {
                ResponseSubject responseSubject = new ResponseSubject();
                responseSubject.setResponse(response);
                responseSubject.setJournal(em.getReference(Journal.class, journalId));
                responseSubjects.add(responseSubject);
            }
        }
        // if there are no subjects/journals, 
        // then this student is not related to this poll
        if (responseSubjects.isEmpty()) {
            return;
        // answers per journal/subject are only created when at least 1 theme is repetitive
        } else if (hasThemeRepetitive(poll)) {
            response.setResponseSubjects(responseSubjects);
        }
        EntityUtil.save(response, em);
        em.persist(responseObject);
        pollType.setResponseId(EntityUtil.getId(response));
        responses.add(pollType);
    }
    
    public boolean hasThemeRepetitive(Poll poll) {
        List<?> repetitiveTheme = em.createNativeQuery("select pt.is_repetitive from poll_theme pt "
                + "join poll p on pt.poll_id = p.id "
                + "where p.id = ?1 "
                + "and pt.is_repetitive = true")
            .setParameter(1, EntityUtil.getId(poll))
            .setMaxResults(1).getResultList();
        if (repetitiveTheme.isEmpty()) {
            return false;
        }
        return true;
    }
    
    private List<Long> getUserRelatedJournals(Poll poll, HoisUserDetails user) {
        String SEARCH_SELECT = "distinct j.id";
        String SEARCH_FROM = "from poll_journal pj "
                + "join journal j on j.id = pj.journal_id "
                + "join journal_student js on js.journal_id = j.id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("pj.poll_id = :pollId", "pollId", EntityUtil.getId(poll));
        qb.requiredCriteria("js.student_id = :studentId", "studentId", user.getStudentId());
        List<?> dbJournals = qb.select(SEARCH_SELECT, em).getResultList();
        List<Long> journalIds = StreamUtil
                .toMappedList(r-> resultAsLong(r, 0), dbJournals);
        return journalIds;
    }

    private List<PollTypeDto> findAndGenerateResponses(List<PollTypeDto> polls, HoisUserDetails user) {
        // At this point, the poll has the correct target code
        List<PollTypeDto> responses = new ArrayList<>();
        for (PollTypeDto pollType : polls) {
            // Üldine tagasiside type poll, one response per person
            if (PollType.KYSITLUS_Y.name().equals(pollType.getType())) {
                generateResponseForGeneralTypePoll(pollType, user, responses);
            // Praktika tagasiside type poll, responses per practiceJournal per techer or student
            } else if (PollType.KYSITLUS_P.name().equals(pollType.getType()) && (user.isStudent() || user.isTeacher())) {
                generateResponseForPracticeTypePoll(pollType, user, responses);
            // Õppeaine või päeviku tagasiside type poll, responses per journal or subject per student
            } else if (PollType.KYSITLUS_O.name().equals(pollType.getType()) && user.isStudent()) {
                generateResponseForSubjectOrJournalTypePoll(pollType, user, responses);
            // Õpilasesinduse valimised type poll, one response per student
            } else if (PollType.KYSITLUS_V.name().equals(pollType.getType()) && user.isStudent()) {
                generateResponseForStudentCouncilTypePoll(pollType, user, responses);
             // Õpetaja või õppejõu tagasiside type poll, one response per teacher
            } else if (PollType.KYSITLUS_T.name().equals(pollType.getType()) && user.isTeacher()) {
                generateResponseForTeacherTypePoll(pollType, user, responses);
            }
        }
        return responses;
    }
    
    private boolean validateUserForPoll(HoisUserDetails user) {
        if (user.isStudent()) {
            Student student = em.getReference(Student.class, user.getStudentId());
            if (!StudentStatus.STUDENT_STATUS_ACTIVE.contains(student.getStatus().getCode())) {
                return false;
            }
        } else if (user.isTeacher()) {
            Teacher teacher = em.getReference(Teacher.class, user.getTeacherId());
            if (teacher.getIsActive() == null || Boolean.FALSE.equals(teacher.getIsActive())) {
                return false;
            }
        }
        return true;
    }

    private void generateResponseForTeacherTypePoll(PollTypeDto pollType, HoisUserDetails user, List<PollTypeDto> responses) {
        Poll poll = em.getReference(Poll.class, pollType.getId());
        List<Long> targetOccupations = getPollRelatedOccupations(pollType.getId());
        Teacher teacher = em.getReference(Teacher.class, user.getTeacherId());
        ResponseObject responseObject;
        if (teacher.getIsActive().booleanValue() && (targetOccupations.isEmpty() || targetOccupations.contains(EntityUtil.getId(teacher.getTeacherOccupation())))) {
            responseObject = new ResponseObject();
            responseObject.setTeacher(teacher);
        } else {
            return;
        }
        Response response = new Response();
        response.setPoll(poll);
        response.setStatus(em.getReference(Classifier.class, ResponseStatus.KYSITVASTUSSTAATUS_A.name()));
        pollType.setResponseStatus(ResponseStatus.KYSITVASTUSSTAATUS_A.name());
        responseObject.setResponse(response);
        responseObject.setPerson(em.getReference(Person.class, user.getPersonId()));
        setPollTarget(poll, user, responseObject);
        response.setResponseObject(responseObject);
        responseObject.setTeacher(teacher);
        EntityUtil.save(response, em);
        em.persist(responseObject);
        pollType.setResponseId(EntityUtil.getId(response));
        responses.add(pollType);
    }
    
    private void generateResponseForStudentCouncilTypePoll(PollTypeDto pollType, HoisUserDetails user, List<PollTypeDto> responses) {
        Response response = new Response();
        Poll poll = em.getReference(Poll.class, pollType.getId());
        response.setPoll(poll);
        response.setStatus(em.getReference(Classifier.class, ResponseStatus.KYSITVASTUSSTAATUS_A.name()));
        pollType.setResponseStatus(ResponseStatus.KYSITVASTUSSTAATUS_A.name());
        ResponseObject responseObject = new ResponseObject();
        responseObject.setResponse(response);
        responseObject.setPerson(em.getReference(Person.class, user.getPersonId()));
        Student student = em.getReference(Student.class, user.getStudentId());
        // Guest students shouldn't answer
        if (ClassifierUtil.equals(StudentType.OPPUR_K, student.getType())) return;
        setPollTarget(poll, user, responseObject);
        response.setResponseObject(responseObject);
        responseObject.setStudent(student);
        responseObject.setPerson(student.getPerson());
        responseObject.setCurriculumVersion(student.getCurriculumVersion());
        responseObject.setStudyForm(student.getStudyForm());
        EntityUtil.save(response, em);
        em.persist(responseObject);
        pollType.setResponseId(EntityUtil.getId(response));
        responses.add(pollType);
    }
    
    private void setPollTarget(Poll poll, HoisUserDetails user, ResponseObject responseObject) {
        try {
            PollTarget pollTarget = em.createQuery("select pt from PollTarget pt "
                    + "where pt.poll.id = ?1 and "
                    + "pt.target.code = ?2", PollTarget.class)
                    .setParameter(1, EntityUtil.getId(poll))
                    .setParameter(2, getTargetCode(user))
                    .getSingleResult();
            responseObject.setPollTarget(pollTarget);
        } catch(Exception e) {
            log.debug(e.getMessage());
            throw new HoisException("poll.messages.matchingTarget");
        }
    }

    /**
     * Gets subjects with confirmed status
     * @param studyPeriod
     * @return subjects
     */
    private List<SubjectAndSubjectStudyPeriod> getDistinctSubjects(Poll poll, Long studentId) {
        String SEARCH_SELECT = "distinct s.id as subjectId, ssp.id as subjectStudyPeriodId";
        String SEARCH_FROM = "from declaration d "
                + "join declaration_subject ds on d.id = ds.declaration_id "
                + "join subject_study_period ssp on ssp.id = ds.subject_study_period_id "
                + "join subject s on ssp.subject_id = s.id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("d.study_period_id = :studyPeriodId", "studyPeriodId", EntityUtil.getId(poll.getStudyPeriod()));
        qb.requiredCriteria("d.student_id = :studentId", "studentId", studentId);
        qb.requiredCriteria("d.status_code = :statusCode", "statusCode", DeclarationStatus.OPINGUKAVA_STAATUS_K.name());
        Set<Long> subjectStudyPeriods = poll.getPollJournals().stream()
                .filter(p -> p.getSubjectStudyPeriod() != null)
                .map(p -> EntityUtil.getId(p.getSubjectStudyPeriod())).collect(Collectors.toSet());
        // Subject study periods are required
        if (subjectStudyPeriods.isEmpty()) return new ArrayList<>();
        qb.optionalCriteria("ssp.id in :subjectStudyPeriods", "subjectStudyPeriods", subjectStudyPeriods);
        List<?> dbSubjects = qb.select(SEARCH_SELECT, em).getResultList();
        List<SubjectAndSubjectStudyPeriod> subjectIds = StreamUtil
                .toMappedList(r-> {
                    SubjectAndSubjectStudyPeriod dto = new SubjectAndSubjectStudyPeriod();
                    dto.setSubjectId(resultAsLong(r, 0));
                    dto.setSubjectStudyPeriodId(resultAsLong(r, 1));
                    return dto;
                }, dbSubjects);
        return subjectIds;
    }

    private static String getTargetCode(HoisUserDetails user) {
        if (user.isExternalExpert()) {
            return PollTargets.KYSITLUS_SIHT_V.name();
        } else if (user.isRepresentative()) {
            return PollTargets.KYSITLUS_SIHT_L.name();
        } else if (user.isSchoolAdmin()) {
            return PollTargets.KYSITLUS_SIHT_A.name();
        } else if (user.isStudent()) {
            return PollTargets.KYSITLUS_SIHT_O.name();
        } else if (user.isTeacher()) {
            return PollTargets.KYSITLUS_SIHT_T.name();
        }
        return null;
    }

    public ThemesDto getPollWithAnswers(Response response, Boolean view) {
        ThemesDto dto = themes(response.getPoll(), EntityUtil.getNullableId(response.getResponseObject().getStudent()));
        setPollValuesToDto(response, dto, view);
        dto.setConfirmed(ClassifierUtil.equals(ResponseStatus.KYSITVASTUSSTAATUS_V, response.getStatus()) ? Boolean.TRUE : Boolean.FALSE);
        return dto;
    }
    
    // TODO: Do this with a single query not streams
    private static void setPollValuesToDto(Response response, ThemesDto dto, Boolean view) {
        if (!view.booleanValue() && ClassifierUtil.equals(ResponseStatus.KYSITVASTUSSTAATUS_V, response.getStatus())) {
            throw new ValidationFailedException("poll.messages.pollEnded");
        }
        for (ResponseQuestionAnswer answer : response.getResponseQuestionAnswers()) {
            Question questionRef = answer.getQuestion();
            // Select, textfield and radiobuttons
            if (questionRef.getType().getCode().equals(PollAnswer.VASTUS_T.name()) ||
                    questionRef.getType().getCode().equals(PollAnswer.VASTUS_R.name()) ||
                    questionRef.getType().getCode().equals(PollAnswer.VASTUS_V.name())) {
                List<Long> ids = questionRef.getPollThemeQuestions().stream().map(PollThemeQuestion::getId).collect(Collectors.toList());
                String answerTxt = answer.getAnswerTxt();
                dto.getThemes().forEach(p-> p.getQuestions().forEach(question -> {
                    if (ids.contains(question.getId()) && 
                            (answer.getResponseSubject() == null || EntityUtil.getId(answer.getResponseSubject()).equals(question.getResponseSubjectId())) &&
                            (answer.getTeacher() == null || EntityUtil.getId(answer.getTeacher()).equals(question.getTeacherId()))) {
                        question.setAnswerTxt(answerTxt);
                    }
                }));
            } else {
            // Checkboxes
                List<Long> ids = questionRef.getPollThemeQuestions().stream().map(PollThemeQuestion::getId).collect(Collectors.toList());
                String answerTxt = answer.getAnswerTxt();
                dto.getThemes().forEach(p-> p.getQuestions().forEach(question -> {
                    if (ids.contains(question.getId()) && 
                            (answer.getResponseSubject() == null || EntityUtil.getId(answer.getResponseSubject()).equals(question.getResponseSubjectId())) &&
                            (answer.getTeacher() == null || EntityUtil.getId(answer.getTeacher()).equals(question.getTeacherId()))) {
                        for (QuestionAnswerDto questionAnswer : question.getAnswers()) {
                            if (answerTxt == null) {
                                questionAnswer.setChosen(Boolean.FALSE);
                            } else if (answerTxt.equals(questionAnswer.getNameEt())) {
                                questionAnswer.setChosen(Boolean.TRUE);
                            }
                        }
                    }
                }));
            }
        }
        dto.setResponseId(response.getId());
        dto.setStartDate(response.getPoll().getValidFrom());
        dto.setEndDate(response.getPoll().getValidThru());
        Poll poll = response.getPoll();
        dto.setName(new AutocompleteResult(poll.getId(), poll.getNameEt(), poll.getNameEn()));
    }

    public void setThemesPageable(HoisUserDetails user, Poll poll, ThemePageableCommand command) {
        EntityUtil.setUsername(user.getUsername(), em);
        poll.setIsThemePageable(command.getIsThemePageable());
        EntityUtil.save(poll, em);
    }

    public void updatePollDates(HoisUserDetails user, Poll poll, PollDatesCommand command) {
        EntityUtil.setUsername(user.getUsername(), em);
        poll.setValidFrom(command.getValidFrom());
        poll.setValidThru(command.getValidThru());
        poll.setReminderDt(command.getReminderDt());
        EntityUtil.save(poll, em);
    }

    public void setThemeRepetitive(HoisUserDetails user, PollTheme pollTheme, ThemeRepedetiveCommand command) {
        EntityUtil.setUsername(user.getUsername(), em);
        pollTheme.setIsRepetitive(command.getIsRepetitive());
        pollTheme.setIsTeacher(command.getIsTeacher());
        EntityUtil.save(pollTheme, em);
    }
    
    /**
     * Used for hooking new answers to Dto
     * @param poll
     * @param uuid
     * @return dto with answers
     */
    public ThemesDto themesWithAnswersJournalOrSubject(Poll poll, Response response, Boolean view) {
        ThemesDto themes = subjectThemes(poll, response);
        setPollValuesToDto(response, themes, view);
        themes.setConfirmed(ResponseStatus.KYSITVASTUSSTAATUS_V.name().equals(response.getStatus().getCode()) ? Boolean.TRUE : Boolean.FALSE);
        return themes;
    }
    
    private static void bindQuestionsToThemes(PollTheme theme, ThemeDto dto, ResponseSubject responseSubject, Teacher teacher) {
        List<QuestionDto> questions = new ArrayList<>();
        for (PollThemeQuestion themeQuestion : theme.getPollThemeQuestions()) {
            QuestionDto questionDto = new QuestionDto();
            questionDto.setResponseSubjectId(EntityUtil.getNullableId(responseSubject));
            questionDto.setTeacherId(EntityUtil.getNullableId(teacher));
            Question question = themeQuestion.getQuestion();
            EntityUtil.bindToDto(question, questionDto);
            questionDto.setQuestion(question.getId());
            EntityUtil.bindToDto(themeQuestion, questionDto);
            if (question.getType() != null) questionDto.setType(question.getType().getCode());
            int usages = question.getPollThemeQuestions().size();
            if (usages != 1) questionDto.setDisabled(Boolean.TRUE);
            
            // Set files
            List<OisFileEditDto> files = new ArrayList<>();
            for (PollThemeQuestionFile file : themeQuestion.getPollThemeQuestionFiles()) {
                OisFileEditDto fileForm = OisFileEditDto.of(file.getOisFile());
                files.add(fileForm);
            }
            questionDto.setFiles(files);
            
            // Set question answers
            List<QuestionAnswerDto> questionAnswers = new ArrayList<>();
            for (QuestionAnswer answer : question.getQuestionAnswers()) {
                QuestionAnswerDto answerDto = new QuestionAnswerDto();
                EntityUtil.bindToDto(answer, answerDto);
                questionAnswers.add(answerDto);
            }
            Collections.sort(questionAnswers);
            questionDto.setAnswers(questionAnswers);
            questions.add(questionDto);
        }
        Collections.sort(questions);
        dto.setQuestions(questions);
        EntityUtil.bindToDto(theme, dto);
    }
    
    public static AutocompleteResult of(Journal journal) {
        StudyYear studyYear = journal.getStudyYear();
        if (studyYear == null) {
            return new AutocompleteResult(journal.getId(), journal.getNameEt(), journal.getNameEt());
        }
        Classifier year = studyYear.getYear();
        if (year == null) {
            return new AutocompleteResult(journal.getId(), journal.getNameEt(), journal.getNameEt());
        }
        return new AutocompleteResult(journal.getId(), journalName(journal.getNameEt(), year.getNameEt()), journalName(journal.getNameEt(), year.getNameEn()));
    }
    
    public static String journalName(String name, String studyYear) {
        return String.format("%1$s (%2$s)", name, studyYear);
    }
    
    private ThemesDto subjectThemes(Poll poll, Response response) {
        List<PollTheme> pollThemes = poll.getPollThemes();
        pollThemes = pollThemes.stream().sorted((o, p) -> o.getOrderNr().compareTo(p.getOrderNr())).collect(Collectors.toList());
        List<ThemeDto> themeDtos = new ArrayList<>();
        List<PollTheme> repedetiveThemes = pollThemes.stream().filter(p -> p.getIsRepetitive() != null && p.getIsRepetitive().booleanValue()).collect(Collectors.toList());
        List<PollTheme> nonRepedetiveThemes = pollThemes.stream().filter(p -> p.getIsRepetitive() == null || !p.getIsRepetitive().booleanValue()).collect(Collectors.toList());
        List<ResponseSubject> responseSubjects = response.getResponseSubjects();
        responseSubjects = orderResponseSubjects(responseSubjects);
        for (ResponseSubject responseSubject : responseSubjects) {
            List<ThemeDto> themesBySubjectOrJournal = new ArrayList<>();
            for (PollTheme theme : repedetiveThemes) {
                ThemeDto dto = new ThemeDto();
                boolean isTeacher = Boolean.TRUE.equals(theme.getIsTeacher());
                List<Teacher> teachers = new ArrayList<>();
                if (responseSubject.getJournal() != null) {
                    dto.setJournal(of(responseSubject.getJournal()));
                    if (isTeacher) {
                        teachers = em.createQuery("select jt.teacher from JournalTeacher jt where jt.journal.id = ?1 "
                                + "order by jt.teacher.person.firstname, jt.teacher.person.lastname", Teacher.class)
                                .setParameter(1, EntityUtil.getId(responseSubject.getJournal()))
                                .getResultList();
                    }
                }
                if (responseSubject.getSubject() != null) {
                    dto.setSubject(AutocompleteResult.of(responseSubject.getSubject()));
                    if (isTeacher) {
                        if (responseSubject.getSubjectStudyPeriod() != null) {
                            teachers = em.createQuery("select sspt.teacher from SubjectStudyPeriodTeacher sspt where sspt.subjectStudyPeriod.id = ?1 "
                                    + "order by sspt.teacher.person.firstname, sspt.teacher.person.lastname", Teacher.class)
                                    .setParameter(1, EntityUtil.getId(responseSubject.getSubjectStudyPeriod()))
                                    .getResultList();
                        } else {
                            teachers = em.createQuery("select sspt.teacher from SubjectStudyPeriodTeacher sspt "
                                    + "where sspt.subjectStudyPeriod.studyPeriod.id = ?1 and sspt.subjectStudyPeriod.subject.id = ?2 "
                                    + "order by sspt.teacher.person.firstname, sspt.teacher.person.lastname", Teacher.class)
                                    .setParameter(1, EntityUtil.getId(poll.getStudyPeriod()))
                                    .setParameter(2, EntityUtil.getId(responseSubject.getSubject()))
                                    .getResultList();
                        }
                    }
                }
                if (Boolean.FALSE.equals(theme.getIsTeacher()) || theme.getIsTeacher() == null) {
                    bindQuestionsToThemes(theme, dto, responseSubject, null);
                    themesBySubjectOrJournal.add(dto);
                } else {
                    for (Teacher teacher : teachers) {
                        ThemeDto newTheme = new ThemeDto();
                        newTheme.setSubject(dto.getSubject());
                        newTheme.setJournal(dto.getJournal());
                        newTheme.setTeacher(AutocompleteResult.of(teacher));
                        bindQuestionsToThemes(theme, newTheme, responseSubject, teacher);
                        themesBySubjectOrJournal.add(newTheme);
                    }
                }
            }
            themeDtos.addAll(themesBySubjectOrJournal);
        }
        
        List<ThemeDto> themesNonRepedetive = new ArrayList<>();
        for (PollTheme theme : nonRepedetiveThemes) {
            ThemeDto dto = new ThemeDto();
            bindQuestionsToThemes(theme, dto, null, null);
            themesNonRepedetive.add(dto);
        }
        Collections.sort(themesNonRepedetive);
        themeDtos.addAll(themesNonRepedetive);
        Boolean confirmed = Boolean.FALSE;
        if (poll.getStatus() != null && ClassifierUtil.equals(PollStatus.KYSITLUS_STAATUS_K, poll.getStatus())) {
            confirmed = Boolean.TRUE;
        }
        ThemesDto themesDto = new ThemesDto(themeDtos, confirmed, poll.getForeword(), poll.getAfterword());
        themesDto.setIsThemePageable(poll.getIsThemePageable());
        themesDto.setType(poll.getType().getCode());
        return themesDto;
    }

    private static List<ResponseSubject> orderResponseSubjects(List<ResponseSubject> responseSubjects) {
        return responseSubjects.stream().sorted((o, p) -> {
            String name1 = null;
            String name2 = null;
            if (o.getJournal() != null) {
                name1 = o.getJournal().getNameEt().toLowerCase();
            } else if (o.getSubject() != null) {
                name1 = o.getSubject().getNameEt().toLowerCase();
            } else if (o.getSubjectStudyPeriod() != null && o.getSubjectStudyPeriod().getSubject() != null) {
                name1 = p.getSubjectStudyPeriod().getSubject().getNameEt().toLowerCase();
            }
            if (p.getJournal() != null) {
                name2 = p.getJournal().getNameEt().toLowerCase();
            } else if (p.getSubject() != null) {
                name2 = p.getSubject().getNameEt().toLowerCase();
            } else if (p.getSubjectStudyPeriod() != null && p.getSubjectStudyPeriod().getSubject() != null) {
                name2 = p.getSubjectStudyPeriod().getSubject().getNameEt().toLowerCase();
            }
            if (name1 != null) {
                return name1.compareTo(name2);
            } else if (name2 == null) {
                return 0;
            } else {
                return "".compareTo(name2);
            }
        }).collect(Collectors.toList());
    }

    /**
     * Used for single question answer saving
     * @param pollThemeQuestion
     * @param response
     * @param question
     */
    public void saveOtherResponse(PollThemeQuestion pollThemeQuestion, Response response, QuestionDto question) {
        //TODO: improve this, too many queries
        // About 40 queries per answer
        if (ClassifierUtil.equals(ResponseStatus.KYSITVASTUSSTAATUS_V, response.getStatus())) {
            throw new ValidationFailedException("poll.messages.pollEnded");
        } else if (response.getStatus() == null || ClassifierUtil.equals(ResponseStatus.KYSITVASTUSSTAATUS_A, response.getStatus())) {
            response.setStatus(em.getReference(Classifier.class, ResponseStatus.KYSITVASTUSSTAATUS_P.name()));
        }
        String answerTxt = question.getAnswerTxt();
        List<ResponseQuestionAnswer> answers = response.getResponseQuestionAnswers();
        Question questionRef = pollThemeQuestion.getQuestion();
        // In case there is no question_answer objects - textfield for example
        Optional<ResponseQuestionAnswer> answerOpt = answers.stream()
                .filter(p -> p.getQuestion().getId().equals(questionRef.getId()) && 
                        (p.getResponseSubject() == null || EntityUtil.getId(p.getResponseSubject()).equals(question.getResponseSubjectId())) &&
                        (p.getTeacher() == null || EntityUtil.getId(p.getTeacher()).equals(question.getTeacherId()))).findFirst();
        if (!StringUtils.isBlank(answerTxt)) {
            //Used for over writing answer
            ResponseQuestionAnswer answer = null;
            Optional<QuestionAnswer> questionAnswerOpt = questionRef.getQuestionAnswers().stream().filter(p -> answerTxt.equals(p.getNameEt())).findFirst();
            if (answerOpt.isPresent()) {
                answer = answerOpt.get();
                if (questionAnswerOpt.isPresent()) {
                    QuestionAnswer questionAnswer = questionAnswerOpt.get();
                    answer.setAnswerNr(questionAnswer.getAnswerNr());
                    answer.setQuestionAnswer(questionAnswer);
                }
            } else {
                // QuestionAnswer is present for radio buttons and select fields
                if (questionRef.getType().getCode().equals(PollAnswer.VASTUS_T.name())) {
                    // Text field wont have questionAnswer object linked to it
                    answer = new ResponseQuestionAnswer();
                    answer.setQuestion(questionRef);
                    answer.setResponse(response);
                    answer.setAnswerNr(Short.valueOf((short) 1));
                } else {
                    answer = new ResponseQuestionAnswer();
                    answer.setQuestion(questionRef);
                    answer.setResponse(response);
                    if (questionAnswerOpt.isPresent()) {
                        QuestionAnswer questionAnswer = questionAnswerOpt.get();
                        answer.setAnswerNr(questionAnswer.getAnswerNr());
                        answer.setQuestionAnswer(questionAnswer);
                    }
                }
            }
            if (question.getResponseSubjectId() != null) answer.setResponseSubject(em.getReference(ResponseSubject.class, question.getResponseSubjectId()));
            answer.setTeacher(EntityUtil.getOptionalOne(Teacher.class, question.getTeacherId(), em));
            answer.setAnswerTxt(answerTxt);
            EntityUtil.save(answer, em);
        // Answers will have 'chosen : true' when answer type is checkbox
        // Might have several answers at once
        } else if (question.getAnswers().stream().anyMatch(p -> p.getChosen() != null)) {
            // Remove all answers from response regarding this question
            response.getResponseQuestionAnswers().removeIf(p -> p.getQuestion().getId().equals(questionRef.getId()) && 
                    (p.getResponseSubject() == null || EntityUtil.getId(p.getResponseSubject()).equals(question.getResponseSubjectId())) && 
                    (p.getTeacher() == null || EntityUtil.getId(p.getTeacher()).equals(question.getTeacherId())));
            List<QuestionAnswerDto> chosen = question.getAnswers().stream().filter(p -> p.getChosen() != null && p.getChosen().booleanValue()).collect(Collectors.toList());
            for (QuestionAnswerDto choice : chosen) {
                ResponseQuestionAnswer answer = new ResponseQuestionAnswer();
                answer.setQuestion(questionRef);
                answer.setResponse(response);
                answer.setAnswerNr(choice.getAnswerNr());
                answer.setAnswerTxt(choice.getNameEt());
                Optional<QuestionAnswer> questionAnswerOpt = questionRef.getQuestionAnswers().stream().filter(p -> choice.getNameEt().equals(p.getNameEt())).findFirst();
                if (questionAnswerOpt.isPresent()) {
                    answer.setQuestionAnswer(questionAnswerOpt.get());
                }
                if (question.getResponseSubjectId() != null) answer.setResponseSubject(em.getReference(ResponseSubject.class, question.getResponseSubjectId()));
                answer.setTeacher(EntityUtil.getOptionalOne(Teacher.class, question.getTeacherId(), em));
                EntityUtil.save(answer, em);
            }
        } else if (answerTxt == null && answerOpt.isPresent()) {
            ResponseQuestionAnswer answer = answerOpt.get();
            // Reference to this answer should be deleted
            if (answer.getResponse() != null && answer.getResponse().getResponseQuestionAnswers() != null) {
                answer.getResponse().getResponseQuestionAnswers().remove(answer);
            }
            EntityUtil.deleteEntity(answer, em);
        }
    }
    
    public void setResponseFinished(HoisUserDetails user, Response response) {
        if (!ClassifierUtil.equals(ResponseStatus.KYSITVASTUSSTAATUS_V, response.getStatus())) {
            response.setStatus(em.getReference(Classifier.class, ResponseStatus.KYSITVASTUSSTAATUS_V.name()));
            EntityUtil.setUsername(user.getUsername(), em);
            EntityUtil.save(response, em);
        }
    }
    
    private Response getResponse(Poll poll, String uuid) {
        return em.createQuery("select r from Response r "
                + "where r.poll.id = ?1 and "
                + "r.responseObject.pollUrl = ?2", Response.class)
                .setParameter(1, EntityUtil.getId(poll))
                .setParameter(2, uuid)
                .getSingleResult();
    }
    
    public void setResponseFinishedSupervisor(Poll poll, String uuid) {
        Response response = getResponse(poll, uuid);
        setResponseFinishedExpert(response);
    }

    public void setResponseFinishedExpert(Response response) {
        if (!ClassifierUtil.equals(ResponseStatus.KYSITVASTUSSTAATUS_V, response.getStatus())) {
            response.setStatus(em.getReference(Classifier.class, ResponseStatus.KYSITVASTUSSTAATUS_V.name()));
            EntityUtil.save(response, em);
        }
    }
    
    /**
     * Used in Job Executor to send emails containing Poll
     */
    public void sendEmails() {
        List<Poll> polls = em.createQuery("select p from Poll p "
                + "where ?3 >= p.validFrom and ?3 <= p.validThru "
                + "and ?3 = p.reminderDt "
                + "and p.status.code = ?1", Poll.class)
                .setParameter(1, PollStatus.KYSITLUS_STAATUS_K.name())
                .setParameter(3, LocalDate.now())
                .getResultList();
        for (Poll poll : polls) {
            checkPollType(poll);
        }
    }
    
    public void checkPollType(Poll poll) {
        String pollType = poll.getType().getCode();
        if (PollType.KYSITLUS_Y.name().equals(pollType)) {
            sendEmailToYldine(poll);
        } else if (PollType.KYSITLUS_P.name().equals(pollType)) {
            sendEmailToPractice(poll);
        } else if (PollType.KYSITLUS_O.name().equals(pollType)) {
            sendEmailToJournalOrSubject(poll);
        } else if (PollType.KYSITLUS_V.name().equals(pollType)) {
            sendEmailToStudents(poll);
        } else if (PollType.KYSITLUS_T.name().equals(pollType)) {
            sendEmailToTeachers(poll);
        }
    }
    
    private void sendEmailToTeachers(Poll poll) {
        Long pollId = EntityUtil.getId(poll);
        List<PollTeacherOccupation> occupations = poll.getPollTeacherOccupations();
        boolean hasOccupations = occupations != null && !occupations.isEmpty();
        String SEARCH_SELECT = "distinct p.id, coalesce(t.email, p.email)";
        String SEARCH_FROM = "from teacher t join person p on p.id = t.person_id "
                + (hasOccupations ? "" : "left ") + "join teacher_occupation teo on t.teacher_occupation_id = teo.id "
                + (hasOccupations ? "" : "left ") + "join poll_teacher_occupation pto on (pto.poll_id = " + pollId + " and pto.teacher_occupation_id = teo.id)";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        qb.filter("t.is_active = true");
        qb.filter("not exists(select 1 from response_object ro " 
                + "join response r on (r.poll_id = " + pollId + " and r.id = ro.response_id "
                + "and r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_V.name() + "' and ro.teacher_id = t.id))");
        List<?> dbTeachers = qb.select(SEARCH_SELECT, em).getResultList();
        dbTeachers.forEach(r -> sendEmailToPerson(poll.getSchool(), em.getReference(Person.class, resultAsLong(r, 0)), poll, resultAsString(r, 1)));
    }

    private void sendEmailToStudents(Poll poll) {
        Long pollId = EntityUtil.getId(poll);
        String SEARCH_SELECT = "distinct p.id, coalesce(s.email, p.email)";
        String SEARCH_FROM = "from student s join person p on p.id = s.person_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        qb.filter("s.status_code != '" + StudentStatus.OPPURSTAATUS_K.name() + "' and s.status_code != '" + StudentStatus.OPPURSTAATUS_L.name() + "'");
        qb.filter("not exists(select 1 from response_object ro " 
                + "join response r on (r.poll_id = " + pollId + " and r.id = ro.response_id "
                + "and r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_V.name() + "' and ro.student_id = s.id))");
        List<?> dbStudents = qb.select(SEARCH_SELECT, em).getResultList();
        dbStudents.forEach(r -> sendEmailToPerson(poll.getSchool(), em.getReference(Person.class, resultAsLong(r, 0)), poll, resultAsString(r, 1)));
    }

    private void sendEmailToJournalOrSubject(Poll poll) {
        Long pollId = EntityUtil.getId(poll);
        Long studyPeriodId = null;
        boolean isHigherSchool = schoolService.schoolType(EntityUtil.getId(poll.getSchool())).isHigher();
        StudyPeriod period = poll.getStudyPeriod();
        if (period != null) {
            studyPeriodId = EntityUtil.getId(poll.getStudyPeriod());
        }
        String SEARCH_SELECT = "distinct p.id, coalesce(s.email, p.email)";
        String SEARCH_FROM = "from student s join person p on p.id = s.person_id "
                + (isHigherSchool ? "join declaration d on (d.student_id = s.id and d.study_period_id = " + studyPeriodId + ")" 
                        : "join journal_student js on js.student_id = s.id join journal j on j.id = js.journal_id "
                        + "join poll_journal pj on (pj.journal_id = j.id and pj.poll_id = " + pollId + ")");
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        if (isHigherSchool) qb.requiredCriteria("d.status_code = :declarationStatus", "declarationStatus", DeclarationStatus.OPINGUKAVA_STAATUS_K.name());
        qb.filter("s.status_code != '" + StudentStatus.OPPURSTAATUS_K.name() + "' and s.status_code != '" + StudentStatus.OPPURSTAATUS_L.name() + "'");
        qb.filter("not exists(select 1 from response_object ro " 
                + "join response r on (r.poll_id = " + pollId + " and r.id = ro.response_id "
                + "and r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_V.name() + "' and ro.student_id = s.id))");
        List<?> dbStudents = qb.select(SEARCH_SELECT, em).getResultList();
        dbStudents.forEach(r -> sendEmailToPerson(poll.getSchool(), em.getReference(Person.class, resultAsLong(r, 0)), poll, resultAsString(r, 1)));
    }

    private void sendEmailToPractice(Poll poll) {
        // Students
        if (poll.getPollTargets().stream().anyMatch(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_O, p.getTarget()))) {
            sendEmailToPracticeStudent(poll);
        }
        // Teachers
        if (poll.getPollTargets().stream().anyMatch(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_T, p.getTarget()))) {
            sendEmailToPracticeTeacher(poll);
        }
    }

    private void sendEmailToPracticeTeacher(Poll poll) {
        Long pollId = EntityUtil.getId(poll);
        List<PollStudentGroup> pollRelatedStudentGroups = poll.getPollStudentGroups();
        boolean hasStudentGroups = pollRelatedStudentGroups != null && !pollRelatedStudentGroups.isEmpty();
        String SEARCH_SELECT = "distinct p.id, coalesce(t.email, p.email)";
        String SEARCH_FROM = "from teacher t join person p on p.id = t.person_id "
                + "join practice_journal pj on pj.teacher_id = t.id "
                + "join student s on pj.student_id = s.id "
                + (hasStudentGroups ? "join student_group sg on s.student_group_id = sg.id" : "");
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("pj.end_date >= :startDate", "startDate", poll.getJournalFrom());
        qb.requiredCriteria("pj.end_date <= :endDate", "endDate", poll.getJournalThru());
        qb.requiredCriteria("pj.school_id = :schoolId", "schoolId", EntityUtil.getNullableId(poll.getSchool()));
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", EntityUtil.getNullableId(poll.getSchool()));
        //null check isnt actually necessary
        if (hasStudentGroups && pollRelatedStudentGroups != null) qb.requiredCriteria("sg.id in :studentGroups", "studentGroups", pollRelatedStudentGroups.stream().map(p -> EntityUtil.getId(p.getStudentGroup())).collect(Collectors.toList()));
        qb.filter("t.is_active = true");
        qb.filter("not exists(select 1 from response_object ro " 
                + "join response r on (r.poll_id = " + pollId + " and r.id = ro.response_id "
                + "and r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_V.name() + "' and ro.teacher_id = t.id and ro.practice_journal_id = pj.id))");
        List<?> dbTeachers = qb.select(SEARCH_SELECT, em).getResultList();
        dbTeachers.forEach(r -> sendEmailToPerson(poll.getSchool(), em.getReference(Person.class, resultAsLong(r, 0)), poll, resultAsString(r, 1)));
    }

    
    private void sendEmailToPracticeStudent(Poll poll) {
        Long pollId = EntityUtil.getId(poll);
        List<PollStudentGroup> pollRelatedStudentGroups = poll.getPollStudentGroups();
        boolean hasStudentGroups = pollRelatedStudentGroups != null && !pollRelatedStudentGroups.isEmpty();
        String SEARCH_SELECT = "distinct p.id, coalesce(s.email, p.email)";
        String SEARCH_FROM = "from student s join person p on p.id = s.person_id "
                + "join practice_journal pj on pj.student_id = s.id ";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("pj.end_date >= :startDate", "startDate", poll.getJournalFrom());
        qb.requiredCriteria("pj.end_date <= :endDate", "endDate", poll.getJournalThru());
        qb.requiredCriteria("pj.school_id = :schoolId", "schoolId", EntityUtil.getNullableId(poll.getSchool()));
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", EntityUtil.getNullableId(poll.getSchool()));
        //null check isnt actually necessary
        if (hasStudentGroups && pollRelatedStudentGroups != null) qb.requiredCriteria("s.student_group_id in :studentGroups", "studentGroups", pollRelatedStudentGroups.stream().map(p -> EntityUtil.getId(p.getStudentGroup())).collect(Collectors.toList()));
        qb.filter("s.status_code != '" + StudentStatus.OPPURSTAATUS_K.name() + "' and s.status_code != '" + StudentStatus.OPPURSTAATUS_L.name() + "'");
        qb.filter("not exists(select 1 from response_object ro " 
                + "join response r on (r.poll_id = " + pollId + " and r.id = ro.response_id "
                + "and r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_V.name() + "' and ro.student_id = s.id and ro.practice_journal_id = pj.id))");
        List<?> dbStudents = qb.select(SEARCH_SELECT, em).getResultList();
        dbStudents.forEach(r -> sendEmailToPerson(poll.getSchool(), em.getReference(Person.class, resultAsLong(r, 0)), poll, resultAsString(r, 1)));
    }

    private void sendEmailToYldine(Poll poll) {
        // Students - check studentgroup
        if (poll.getPollTargets().stream().anyMatch(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_O, p.getTarget()))) {
            sendEmailToYldineStudent(poll);
        }
        // Teachers - check occupation
        if (poll.getPollTargets().stream().anyMatch(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_T, p.getTarget()))) {
            sendEmailToTeachers(poll);
        }
        // Student representatives - related to student, person, student has to be in the right studentgroup
        if (poll.getPollTargets().stream().anyMatch(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_L, p.getTarget()))) {
            sendEmailToYldineRepresentative(poll);
        }
        // Admin - check nothing
        if (poll.getPollTargets().stream().anyMatch(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_A, p.getTarget()))) {
            sendEmailToYldineAdmin(poll);
        }
    }
    
    private void sendEmailToYldineAdmin(Poll poll) {
        Long pollId = EntityUtil.getId(poll);
        String SEARCH_SELECT = "distinct p.id, p.email";
        String SEARCH_FROM = "from user_ u join person p on (p.id = u.person_id and u.role_code = '" + Role.ROLL_A.name() + "')";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("u.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        qb.filter("not exists(select 1 from response_object ro " 
                + "join response r on (r.poll_id = " + pollId + " and r.id = ro.response_id "
                + "and r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_V.name() + "' and ro.person_id = p.id))");
        List<?> dbAdmins = qb.select(SEARCH_SELECT, em).getResultList();
        dbAdmins.forEach(r -> sendEmailToPerson(poll.getSchool(), em.getReference(Person.class, resultAsLong(r, 0)), poll, resultAsString(r, 1)));
    }

    private void sendEmailToYldineRepresentative(Poll poll) {
        Long pollId = EntityUtil.getId(poll);
        List<PollStudentGroup> pollRelatedStudentGroups = poll.getPollStudentGroups();
        boolean hasStudentGroups = pollRelatedStudentGroups != null && !pollRelatedStudentGroups.isEmpty();
        String SEARCH_SELECT = "distinct p.id, p.email";
        String SEARCH_FROM = "from student_representative sr join person p on p.id = sr.person_id "
                + "join student s on s.id = sr.student_id "
                + (hasStudentGroups ? "join student_group sg on sg.id = s.student_group_id "
                        + "join poll_student_group psg on (psg.student_group_id = sg.id and psg.poll_id = " + pollId + ")" : "");
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.filter("s.status_code != '" + StudentStatus.OPPURSTAATUS_K.name() + "' and s.status_code != '" + StudentStatus.OPPURSTAATUS_L.name() + "'");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        qb.filter("not exists(select 1 from response_object ro " 
                + "join response r on (r.poll_id = " + pollId + " and r.id = ro.response_id "
                + "and r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_V.name() + "' and ro.person_id = p.id))");
        List<?> dbRepresentatives = qb.select(SEARCH_SELECT, em).getResultList();
        dbRepresentatives.forEach(r -> sendEmailToPerson(poll.getSchool(), em.getReference(Person.class, resultAsLong(r, 0)), poll, resultAsString(r, 1)));
    }

    private void sendEmailToYldineStudent(Poll poll) {
        Long pollId = EntityUtil.getId(poll);
        List<PollStudentGroup> pollRelatedStudentGroups = poll.getPollStudentGroups();
        boolean hasStudentGroups = pollRelatedStudentGroups != null && !pollRelatedStudentGroups.isEmpty();
        String SEARCH_SELECT = "distinct p.id, coalesce(s.email, p.email)";
        String SEARCH_FROM = "from student s join person p on p.id = s.person_id "
                + (hasStudentGroups ? "join student_group sg on sg.id = s.student_group_id "
                        + "join poll_student_group psg on (psg.student_group_id = sg.id and psg.poll_id = " + pollId + ")" : "");
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.filter("s.status_code != '" + StudentStatus.OPPURSTAATUS_K.name() + "' and s.status_code != '" + StudentStatus.OPPURSTAATUS_L.name() + "'");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
        qb.filter("not exists(select 1 from response_object ro " 
                + "join response r on (r.poll_id = " + pollId + " and r.id = ro.response_id "
                + "and r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_V.name() + "' and ro.student_id = s.id))");
        List<?> dbStudents = qb.select(SEARCH_SELECT, em).getResultList();
        dbStudents.forEach(r -> sendEmailToPerson(poll.getSchool(), em.getReference(Person.class, resultAsLong(r, 0)), poll, resultAsString(r, 1)));
    }

    private void sendEmailToPerson(School school, Person person, Poll poll, String email) {
        if (email != null) {
            String pollName = poll.getNameEt();
            PollReminderMessage data = new PollReminderMessage(pollName);
            automaticMessageService.sendMessageToEmail(MessageType.TEATE_LIIK_KYSI_MEELDETULETUS, school, person, data, email);
        }
    }

    public QuestionDto updateQuestion(HoisUserDetails user, Question question, QuestionDto questionDto) {
        boolean save = false;
        if (!question.getNameEt().equals(questionDto.getNameEt())) {
            save = true;
            question.setNameEt(questionDto.getNameEt());
            question.setNameEn(questionDto.getNameEt());
        }
        if (questionDto.getAddInfoEt() != null && !questionDto.getAddInfoEt().equals(question.getAddInfoEt())) {
            save = true;
            question.setAddInfoEt(questionDto.getAddInfoEt());
        }
        for (QuestionAnswerDto answerDto : questionDto.getAnswers()) {
            QuestionAnswer questionAnswer = em.getReference(QuestionAnswer.class, answerDto.getId());
            if (!questionAnswer.getNameEt().equals(answerDto.getNameEt())) {
                save = true;
                questionAnswer.setNameEt(answerDto.getNameEt());
                questionAnswer.setNameEn(answerDto.getNameEt());
            }
        }
        // dont perform save, if nothing changed
        if (save) {
            EntityUtil.setUsername(user.getUsername(), em);
            EntityUtil.save(question, em);
        }
        return question(question);
        
    }

    public PollRelatedObjectsDto searchAnswersPerResponse(Response response) {
        // Not all themes will be answered completely since we are keeping polls that arent completed
        String SEARCH_SELECT = "j.id as journalId, j.name_et as journalName, ssp.id as subjectId,"
                + " s.name_et as subjectEt, s.name_en as subjectEn, s.code, c.name_et as yearEt, c.name_en as yearEn, "
                + "string_agg(distinct(p.firstname || ' ' || p.lastname), ', ') as teachers, s.id";
        String SEARCH_FROM = "from response_subject rs "
                + "join response_question_answer rqa on rqa.response_id = rs.response_id "
                + "left join question_answer qa on qa.id = rqa.question_answer_id "
                + "join question q on ((q.id = qa.question_id or q.type_code = 'VASTUS_T') and rqa.question_id = q.id) "
                + "join poll_theme_question ptq on ptq.question_id = q.id "
                + "join poll_theme pt on pt.id = ptq.poll_theme_id "
                + "left join journal j on j.id = rs.journal_id "
                + "left join study_year sy on sy.id = j.study_year_id "
                + "left join classifier c on c.code = sy.year_code "
                + "left join subject_study_period ssp on ssp.id = rs.subject_study_period_id "
                + "left join subject s on s.id = ssp.subject_id "
                + "left join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id "
                + "left join teacher t on t.id = sspt.teacher_id "
                + "left join person p on p.id = t.person_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("rs.response_id = :responseId", "responseId", EntityUtil.getId(response));
        qb.filter("pt.is_repetitive = true");
        qb.groupBy("j.id, j.name_et, ssp.id, s.name_et, s.name_en, s.code, c.name_et, c.name_en, s.id");
        qb.sort("j.name_et, s.name_et, teachers");
        List<?> dbSubjectOrJournal = qb.select(SEARCH_SELECT, em).getResultList();
        List<SubjectOrJournalDto> subjectOrJournal = StreamUtil
                .toMappedList(r-> {
                    SubjectOrJournalDto dto = new SubjectOrJournalDto();
                    String yearEt = resultAsString(r, 6);
                    String yearEn = resultAsString(r, 7);
                    dto.setJournal(new AutocompleteResult(resultAsLong(r, 0), 
                            (StringUtils.isEmpty(yearEt) ? resultAsString(r, 1) : resultAsString(r, 1) + " (" + yearEt + ")"), 
                            (StringUtils.isEmpty(yearEn) ? resultAsString(r, 1) : resultAsString(r, 1) + " (" + yearEn + ")")));
                    String code = resultAsString(r, 5);
                    dto.setSubject(new AutocompleteResult(resultAsLong(r, 2), 
                            getSubjectNameWithTeacher(resultAsString(r, 3), code, resultAsString(r, 8)), 
                            getSubjectNameWithTeacher(resultAsString(r, 4), code, resultAsString(r, 8))));
                    return dto;
                }, dbSubjectOrJournal);
        
        PollRelatedObjectsDto dto = new PollRelatedObjectsDto();
        dto.setJournals(subjectOrJournal.stream().filter(p -> p.getJournal() != null && p.getJournal().getId() != null).map(p -> p.getJournal()).collect(Collectors.toList()));
        dto.setSubjects(subjectOrJournal.stream().filter(p -> p.getSubject() != null && p.getSubject().getId() != null).map(p -> p.getSubject()).collect(Collectors.toList()));
        
        // Make sure all non repetitive themes arent filled with textfields
        String POLL_SELECT = "pt.id";
        String POLL_FROM = "from poll_theme pt "
                + "join poll_theme_question ptq on pt.id = ptq.poll_theme_id "
                + "join question q on q.id = ptq.question_id";
        JpaNativeQueryBuilder themeQuery = new JpaNativeQueryBuilder(POLL_FROM);
        themeQuery.requiredCriteria("pt.poll_id = :pollId", "pollId", EntityUtil.getId(response.getPoll()));
        themeQuery.filter("(pt.is_repetitive is null or pt.is_repetitive = false)");
        List<?> dbNotRepetitiveThemes = themeQuery.select(POLL_SELECT, em).getResultList();
        dto.setThemes(Boolean.valueOf(!dbNotRepetitiveThemes.isEmpty()));
        return dto;
    }

    public GraphDto createGraph(HoisUserDetails user, GraphSearchCommand command, boolean isStudent) {
        Response response;
        Long responseId = null;
        Poll poll;
        Long pollId = null;
        if (isStudent) {
            response = em.getReference(Response.class, command.getResponseId());
            poll = response.getPoll();
            pollId = EntityUtil.getId(poll);
            responseId = command.getResponseId();
        } else {
            poll = em.getReference(Poll.class, command.getPollId());
            pollId = command.getPollId();
        }
        
        String SEARCH_SELECT = "pt.id as theme_id, pt.name_et as themeEt, pt.name_en as themeEn, pt.order_nr as themeOrder, qq.id as questionId, qq.name_et as questionEt, "
                + "qq.name_en as questionEn, qq.type_code as questionType, pq.order_nr as questionOrder, qa.id as answerId, qa.name_et as answerEt, qa.name_en as answerEn, "
                + "qa.answer_nr, qa1.min_nr, qa1.max_nr, count(T4.id), array_to_string(array_agg(T4.responseId), ',') as responseIds, SUBJECT_JOURNAL.sspId, "
                + "SUBJECT_JOURNAL.journalId, SUBJECT_JOURNAL.journalEt, SUBJECT_JOURNAL.subjectId, "
                + "SUBJECT_JOURNAL.subjectCode || ' - ' || SUBJECT_JOURNAL.subjectEt || ' (' || SUBJECT_JOURNAL.teacherNames || ')' as subjectEt , "
                + "SUBJECT_JOURNAL.subjectCode || ' - ' || SUBJECT_JOURNAL.subjectEn || ' (' || SUBJECT_JOURNAL.teacherNames || ')' as subjectEn , "
                + "SUBJECT_JOURNAL.teacherId as teacherId, SUBJECT_JOURNAL.teacherName, pt.is_teacher, pt.is_repetitive, "
                + "string_agg(replace(T4.answer_txt, '`', ''''), '`') as textAnswers";
        
        String SEARCH_FROM = "from poll pp" + 
                " join poll_theme pt on pp.id=pt.poll_id" + 
                " join poll_theme_question pq on pt.id=pq.poll_theme_id" + 
                " join question qq on pq.question_id=qq.id" + 
                " left join question_answer qa on qa.question_id = qq.id " + 
                "LEFT JOIN (SELECT r.poll_id, s.id AS subjectId,j.id AS journalId, j.name_et AS journalEt, s.name_et AS subjectEt, s.name_en AS subjectEn, s.code as subjectCode, " + 
                    "rqa.teacher_id AS teacherId, concat(p.firstname, ' ', p.lastname) AS teacherName, rqa.question_id, rqa.question_answer_id, ssp.id as sspId, " +
                    "string_agg(distinct concat(pp.firstname, ' ', pp.lastname), ', ') as teacherNames " + 
                    "FROM response r JOIN response_subject rs ON rs.response_id = r.id " +
                    "LEFT JOIN journal j ON rs.journal_id = j.id " + 
                    "LEFT JOIN subject_study_period ssp on rs.subject_study_period_id = ssp.id " +
                    "LEFT JOIN subject s ON ssp.subject_id = s.id " +
                    "LEFT JOIN subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id " +
                    "LEFT JOIN teacher tt on sspt.teacher_id = tt.id " +
                    "LEFT JOIN person pp on pp.id = tt.person_id " +
                    "JOIN response_question_answer rqa ON rqa.response_subject_id = rs.id " + 
                    "LEFT JOIN teacher t ON rqa.teacher_id = t.id " + 
                    "LEFT JOIN person p ON t.person_id = p.id " + 
                    "GROUP BY r.poll_id, s.id, j.id, j.name_et, s.name_et, s.name_en, rqa.teacher_id, rqa.question_id, rqa.question_answer_id, ssp.id, " + 
                    "concat (p.firstname, ' ', p.lastname)) SUBJECT_JOURNAL ON " +
                    "subject_journal.question_id=qq.id and (SUBJECT_JOURNAL.question_answer_id = qa.id OR qq.type_code = 'VASTUS_T') " +
                    "and (SUBJECT_JOURNAL.poll_id = pt.poll_id AND pt.is_repetitive = TRUE) " + 
                    "and((coalescE(pt.is_teacher,false)=false and subject_journal.teacherId is null) " +
                    "or (pt.is_teacher=true and subject_journal.teacherId is not null)) " + 
                " left join (select rqa.question_answer_id, r.poll_id, rqa.id, r.id as responseId, rqa.answer_txt, rqa.question_id " +
                    ", rs.journal_id, rs.subject_study_period_id as sspId, rqa.teacher_id as teacherId, ro.student_id as studentId" +
                    " from response_question_answer rqa" + 
                    " left join response_subject rs on rqa.response_subject_id = rs.id" +
                    " left join subject_study_period ssp on rs.subject_study_period_id = ssp.id " +
                    " join response r on (rqa.response_id = r.id" +
                    (command.getJournalId() != null ? " and rs.response_id = r.id and rs.journal_id=" + command.getJournalId() : "") +
                    (command.getSubjectStudyPeriodId() != null ? " and rs.response_id = r.id and rs.subject_study_period_id=" + command.getSubjectStudyPeriodId() : "") +
                    ") join response_object ro on ro.response_id = r.id " +
                    "join poll_target pt on ro.poll_target_id = pt.id" +
                    (command.getStudents() != null && command.getStudents().booleanValue() ? " and pt.target_code = 'KYSITLUS_SIHT_O'" : "" ) +
                    (command.getTeachers() != null && command.getTeachers().booleanValue() ? " and pt.target_code = 'KYSITLUS_SIHT_T'" : "" ) +
                    (command.getEnterpriseId() != null ? (" and pt.target_code = 'KYSITLUS_SIHT_E' join contract_supervisor cs on cs.id = ro.contract_supervisor_id "
                            + "join contract c on (cs.contract_id = c.id and c.enterprise_id = " + command.getEnterpriseId() +  ")") : "") +
                    " ) T4 on ((T4.question_answer_id = qa.id OR qq.type_code = 'VASTUS_T') " +
                    (isStudent ? "and (qq.type_code != 'VASTUS_T' or (qq.type_code = 'VASTUS_T' and T4.studentId = " + user.getStudentId() + ")) " : "") +
                    "and T4.poll_id = pp.id " +
                    "and T4.question_id = qq.id " +
                    "AND (((coalesce(pt.is_repetitive,false)=false or pp.type_code != 'KYSITLUS_O') and T4.sspId is null and T4.journal_id is null) or " +
                    "(coalesce(pt.is_repetitive,false)=true and coalescE(pt.is_teacher,false)=false and subject_journal.question_id=qq.id " +
                    "and (T4.journal_id = SUBJECT_JOURNAL.journalId OR T4.sspId = SUBJECT_JOURNAL.sspId)) or " +
                    "(coalesce(pt.is_repetitive,false)=true and pt.is_teacher=true and subject_journal.question_id=qq.id " +
                    "and (T4.journal_id = SUBJECT_JOURNAL.journalId OR T4.sspId = SUBJECT_JOURNAL.sspId) " +
                    "and T4.teacherId = SUBJECT_JOURNAL.teacherId)))" + 
                " left join (select min(qa1.answer_nr) as min_nr, max(qa1.answer_nr) as max_nr, qa1.question_id" + 
                    " from question_answer qa1" + 
                    " join question qq on qa1.question_id = qq.id" + 
                    " join poll_theme_question pq on pq.question_id = qq.id" + 
                    " join poll_theme pt on pq.poll_theme_id = pt.id and pt.poll_id = " + pollId +
                    " group by qa1.question_id) qa1 on pq.question_id = qa1.question_id";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM)
                .groupBy("pt.id, pt.name_et, pt.name_en, pt.order_nr, pq.order_nr, qq.id, qq.name_et, qq.name_en, qq.type_code, qa.id, qa.answer_nr, "
                        + "qa.name_et, qa1.max_nr, qa1.min_nr, SUBJECT_JOURNAL.sspId, SUBJECT_JOURNAL.journalId, SUBJECT_JOURNAL.journalEt, "
                        + "SUBJECT_JOURNAL.subjectId, SUBJECT_JOURNAL.subjectEt, SUBJECT_JOURNAL.subjectEn, SUBJECT_JOURNAL.subjectCode, "
                        + "SUBJECT_JOURNAL.teacherId, SUBJECT_JOURNAL.teacherNames, SUBJECT_JOURNAL.teacherName, pt.is_teacher, pt.is_repetitive")
                .sort("pt.order_nr, subject_journal.subjectet, subject_journal.journalet, subject_journal.teachernames, subject_journal.teachername, pq.order_nr, qa.order_nr");
        qb.requiredCriteria("pp.id = :pollId", "pollId", pollId);
        qb.optionalCriteria("SUBJECT_JOURNAL.sspId = :sspId", "sspId", command.getSubjectStudyPeriodId());
        qb.optionalCriteria("SUBJECT_JOURNAL.journalId = :journalId", "journalId", command.getJournalId());
        if (Boolean.TRUE.equals(command.getThemes())) {
            qb.filter("((pt.is_repetitive is null or pt.is_repetitive = false) or pp.type_code != 'KYSITLUS_O')");
        }
        boolean repetitive = false;
        if (ClassifierUtil.equals(PollType.KYSITLUS_O, poll.getType()) && (command.getJournalId() != null || command.getSubjectStudyPeriodId() != null)) {
            qb.filter("pt.is_repetitive = true");
            repetitive = true;
        }
        List<?> dbAnswers = qb.select(SEARCH_SELECT, em).getResultList();
        
        List<GraphSearchDto> themeAndQuestionList = StreamUtil
                .toMappedList(r-> {
                    Long themeOrder = resultAsLong(r, 3);
                    Long questionOrder = resultAsLong(r, 8);
                    GraphSearchDto dto = new GraphSearchDto();
                    dto.setTheme(new AutocompleteResult(resultAsLong(r, 0), 
                            getThemeName(themeOrder, resultAsString(r, 1)),
                            getThemeName(themeOrder, resultAsString(r, 2))));
                    QuestionDto questionDto = new QuestionDto();
                    questionDto.setId(resultAsLong(r, 4));
                    questionDto.setNameEt(getQuestionName(themeOrder, questionOrder, resultAsString(r, 5)));
                    questionDto.setNameEn(getQuestionName(themeOrder, questionOrder, resultAsString(r, 6)));
                    questionDto.setType(resultAsString(r, 7));
                    dto.setQuestion(questionDto);
                    dto.setQuestionAnswer(new AutocompleteResult(resultAsLong(r, 9), resultAsString(r, 10), resultAsString(r, 11)));
                    dto.setAnswerNr(resultAsLong(r, 12));
                    dto.setMinNr(resultAsLong(r, 13));
                    dto.setMaxNr(resultAsLong(r, 14));
                    dto.setAnswers(resultAsLong(r, 15));
                    String responsesString = resultAsString(r, 16);
                    if (!StringUtils.isEmpty(responsesString)) {
                        dto.setResponseIds(Arrays.asList(responsesString.split(",")).stream().map(p -> Long.valueOf(p)).collect(Collectors.toList()));
                    }
                    dto.setResponseSubjectId(resultAsLong(r, 17));
                    Long journalId = resultAsLong(r, 18);
                    Long subjectId = resultAsLong(r, 20);
                    if (journalId != null) {
                        dto.setSubjectOrJournal(new AutocompleteResult(journalId, resultAsString(r, 19), resultAsString(r, 19)));
                    } else if (subjectId != null) {
                        dto.setSubjectOrJournal(new AutocompleteResult(subjectId, resultAsString(r, 21), resultAsString(r, 22)));
                    }
                    dto.setTeacher(new AutocompleteResult(resultAsLong(r, 23), resultAsString(r, 24), resultAsString(r, 24)));
                    dto.setIsTeacher(JpaQueryUtil.resultAsBoolean(r, 25));
                    dto.setIsRepetitive(JpaQueryUtil.resultAsBoolean(r, 26));
                    if (PollAnswer.VASTUS_T.name().equals(resultAsString(r, 7))) {
                        String textAnswers = resultAsString(r, 27);
                        if (textAnswers != null) {
                            dto.setTextAnswers(Arrays.asList(textAnswers.split("`")));
                        }
                    }
                    return dto;
                }, dbAnswers);
        // Map themes
        LinkedHashMap<Long, List<GraphSearchDto>> map = themeAndQuestionList.stream().collect(Collectors.toMap(
                p -> p.getTheme().getId(), // key
                p -> themeAndQuestionList.stream().filter(o -> o.getTheme().getId().equals(p.getTheme().getId())).collect(Collectors.toList()) // value
                , (oldValue, newValue) -> oldValue, // merge
                LinkedHashMap::new)); // map type
        List<GraphThemeDto> graphByTheme = new ArrayList<>();
        for (Map.Entry<Long, List<GraphSearchDto>> entry : map.entrySet()) {
            // Second list for student's answer
            List<GraphSearchDto> entryValues = entry.getValue();
            GraphSearchDto theme = entryValues.get(0);
            List<GraphSubjectDto> subjectOrJournals = new ArrayList<>();
            // Map responsesubjects
            SchoolService.SchoolType type = schoolService.schoolType(user.getSchoolId());
            if (type.isHigher()) {
                LinkedHashMap<Long, List<GraphSearchDto>> responseSubjectMap = entryValues.stream().collect(Collectors.toMap(
                        p -> p.getResponseSubjectId(), // key
                        p -> entryValues.stream().filter(o -> o.getResponseSubjectId() != null && o.getResponseSubjectId().equals(p.getResponseSubjectId())).collect(Collectors.toList()), //value
                        (oldValue, newValue) -> oldValue, // merge
                        LinkedHashMap::new)); // map type
                mapResponses(responseSubjectMap, poll, isStudent, responseId, subjectOrJournals, Boolean.TRUE);
            }
            if (type.isVocational()) {
                LinkedHashMap<Long, List<GraphSearchDto>> journalMap = entryValues.stream()
                        .collect(Collectors.toMap(p -> p.getSubjectOrJournal() == null ? null : p.getSubjectOrJournal().getId(), 
                        p -> entryValues.stream()
                        .filter(o -> o.getResponseSubjectId() == null && p.getResponseSubjectId() == null && o.getSubjectOrJournal() != null && p.getSubjectOrJournal() != null && o.getSubjectOrJournal().getId().equals(p.getSubjectOrJournal().getId()))
                        .collect(Collectors.toList()), 
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
                mapResponses(journalMap, poll, isStudent, responseId, subjectOrJournals, Boolean.FALSE);
            }
            LinkedHashMap<Long, List<GraphSearchDto>> otherAnswers = entryValues.stream()
                    .collect(Collectors.toMap(p -> null, 
                    p -> entryValues.stream().filter(o -> o.getResponseSubjectId() == null && p.getResponseSubjectId() == null && o.getSubjectOrJournal() == null && p.getSubjectOrJournal() == null)
                    .collect(Collectors.toList()), 
                    (oldValue, newValue) -> oldValue,
                    LinkedHashMap::new));
            mapResponses(otherAnswers, poll, isStudent, responseId, subjectOrJournals, null);
            // Add theme
            GraphThemeDto themeDto = new GraphThemeDto();
            themeDto.setTitle(theme.getTheme());
            themeDto.setJournalOrSubject(subjectOrJournals);
            themeDto.setIsRepetitive(theme.getIsRepetitive());
            graphByTheme.add(themeDto);
        }
        GraphDto dto = new GraphDto();
        if (command.getSubjectStudyPeriodId() != null || command.getJournalId() != null) {
            dto.setComments(getComments(user, pollId, command));
        }
        dto.setCommentDisabled(Boolean.valueOf(!PracticeJournalUserRights.isBeforeDaysAfterCanEdit(poll.getValidThru())));
        dto.setCanComment(poll.getIsTeacherComment());
        dto.setCanStudentView(poll.getIsTeacherCommentVisible());
        dto.setType(EntityUtil.getNullableCode(poll.getType()));
        dto.setTheme(graphByTheme);
        if (!Boolean.TRUE.equals(command.getThemes())) {
            mapMissingData(dto, pollId, repetitive, isStudent);
        }
        return dto;
    }
    
    private static void mapResponses(Map<Long, List<GraphSearchDto>> responseSubjectMap, Poll poll, boolean isStudent, Long responseId, List<GraphSubjectDto> subjectOrJournals, Boolean higher) {
     // Loop per response subject in theme
        for (Map.Entry<Long, List<GraphSearchDto>> responseSubjectEntry : responseSubjectMap.entrySet()) {
            List<GraphSearchDto> responseSubjectEntryValues = responseSubjectEntry.getValue();
            // Map teachers
            LinkedHashMap<Long, List<GraphSearchDto>> teacherMap = responseSubjectEntryValues.stream().collect(Collectors.toMap(
                    p -> p.getTeacher().getId(), // key
                    p -> responseSubjectEntryValues.stream().filter(o -> (o.getTeacher().getId() == null && p.getTeacher().getId() == null) || 
                            (o.getTeacher().getId() != null && o.getTeacher().getId().equals(p.getTeacher().getId()))).collect(Collectors.toList()), // value
                    (oldValue, newValue) -> oldValue, // merge
                    LinkedHashMap::new)); // map type
            GraphSubjectDto subjectDto = new GraphSubjectDto();
            // Loop per teacher
            for (Map.Entry<Long, List<GraphSearchDto>> teacherEntry : teacherMap.entrySet()) {
                GraphInfoDto infoDto = new GraphInfoDto();
                List<GraphInfoDto> checkBoxQuestions = new ArrayList<>();
                List<GraphInfoDto> textQuestions = new ArrayList<>();
                // second list for student's answer
                if (isStudent && infoDto.getData().size() < 2) infoDto.getData().add(new ArrayList<>());
                long maxWeight = 0L;
                List<AutocompleteResult> labels = new ArrayList<>();
                List<GraphSearchDto> teacherEntryValues = teacherEntry.getValue();
                GraphSearchDto subjectOrJournal = null;
                // Map questions
                LinkedHashMap<Long, List<GraphSearchDto>> questionMap = teacherEntryValues.stream()
                        .collect(Collectors.toMap(p -> p.getQuestion().getId(), // key
                        p -> teacherEntryValues.stream().filter(o -> o.getQuestion().getId().equals(p.getQuestion().getId())).collect(Collectors.toList()), // value
                        (oldValue, newValue) -> oldValue, // merge
                        LinkedHashMap::new)); // map type
                // loop per question
                for (Map.Entry<Long, List<GraphSearchDto>> questionEntry : questionMap.entrySet()) {
                    List<QuestionResponsePairDto> responses = new ArrayList<>();
                    List<GraphSearchDto> questionEntries = questionEntry.getValue();
                    GraphSearchDto firstQuestion = questionEntries.get(0);
                    subjectOrJournal = firstQuestion;
                    
                    List<Integer> myAnswers = new ArrayList<>();
                    List<Long> weights = new ArrayList<>();
                    if (PollAnswer.VASTUS_M.name().equals(firstQuestion.getQuestion().getType())) {
                        GraphInfoDto checkboxQuestion = createCheckboxGraph(questionEntries, isStudent, responseId);
                        checkBoxQuestions.add(checkboxQuestion);
                    } else if (PollAnswer.VASTUS_T.name().equals(firstQuestion.getQuestion().getType())) {
                        GraphInfoDto textAnswer = new GraphInfoDto();
                        GraphTextAnswerDto textAnswerDto = new GraphTextAnswerDto();
                        textAnswerDto.setQuestion(new AutocompleteResult(firstQuestion.getQuestion().getId(), firstQuestion.getQuestion().getNameEt(), firstQuestion.getQuestion().getNameEn()));
                        textAnswerDto.setTextAnswers(firstQuestion.getTextAnswers());
                        textAnswer.setIsText(Boolean.TRUE);
                        textAnswer.setTextAnswer(textAnswerDto);
                        TitleOptions titleOptions = new TitleOptions();
                        titleOptions.setDisplay(Boolean.FALSE);
                        GraphOptions options = new GraphOptions(titleOptions);
                        textAnswer.setOptions(options);
                        textQuestions.add(textAnswer);
                        continue;
                    } else {
                        // loop per answer;
                        for (GraphSearchDto answer : questionEntries) {
                            if (answer.getMaxNr() != null && answer.getMaxNr().longValue() > maxWeight) {
                                maxWeight = answer.getMaxNr().longValue();
                            }
                            if (answer.getResponseIds() != null && isStudent && answer.getResponseIds().contains(responseId)) {
                                myAnswers.add(Integer.valueOf(answer.getAnswerNr().intValue()));
                            }
                            for (int i = 0; i < answer.getAnswers().intValue(); i++) {
                                weights.add(answer.getAnswerNr());
                            }
                            QuestionResponsePairDto responseDto = new QuestionResponsePairDto();
                            responseDto.setAnswer(answer.getQuestionAnswer());
                            responseDto.setAnswers(answer.getAnswers());
                            responseDto.setAnswerNr(answer.getAnswerNr());
                            responses.add(responseDto);
                        }
                        IntSummaryStatistics stats = weights.stream().filter(p -> p != null).mapToInt(p -> p.intValue()).summaryStatistics();
                        Long uniqueAnswers = Long.valueOf(questionEntries.stream()
                                .filter(p -> p.getResponseIds() != null)
                                .flatMap(p -> p.getResponseIds().stream()).distinct().count());
                        infoDto.getLabelOverride().add(new DatasetOverride(responses, uniqueAnswers));
                        if (ClassifierUtil.equals(PollType.KYSITLUS_V, poll.getType())) {
                            infoDto.getData().get(0).add(BigDecimal.valueOf(uniqueAnswers.longValue()));
                        } else {
                            infoDto.getData().get(0).add(BigDecimal.valueOf(stats.getAverage()).setScale(2, RoundingMode.HALF_UP));
                        }
                        if (isStudent) {
                            infoDto.getData().get(1).add(BigDecimal.valueOf(myAnswers.stream().mapToInt(Integer::intValue).summaryStatistics().getAverage()).setScale(2, RoundingMode.HALF_UP));
                        }
                        labels.add(new AutocompleteResult(firstQuestion.getQuestion().getId(), firstQuestion.getQuestion().getNameEt(), firstQuestion.getQuestion().getNameEn()));
                    }
                    
                }
                infoDto.setLabels(labels);
                if (ClassifierUtil.equals(PollType.KYSITLUS_V, poll.getType())) {
                    OptionalLong maximumAnswers = infoDto.getLabelOverride().stream().mapToLong(p -> p.getSum().longValue()).max();
                    if (maximumAnswers.isPresent() && maximumAnswers.getAsLong() != 0L) {
                        infoDto.setMax(Long.valueOf(maximumAnswers.getAsLong()));
                    } else {
                        infoDto.setMax(Long.valueOf(1L));
                    }
                } else {
                    infoDto.setMax(Long.valueOf(maxWeight));
                }
                GraphTeacherDto teacher = new GraphTeacherDto();
                TitleOptions titleOptions = new TitleOptions();
                titleOptions.setDisplay(Boolean.FALSE);
                GraphOptions options = new GraphOptions(titleOptions);
                infoDto.setOptions(options);
                teacher.getGraph().add(infoDto);
                teacher.getGraph().addAll(checkBoxQuestions);
                teacher.getGraph().addAll(textQuestions);
                // Put questions in the right order
                orderQuestions(teacher.getGraph());
                if (subjectOrJournal != null) {
                    subjectDto.setSubjectOrJournal(subjectOrJournal.getSubjectOrJournal());
                    subjectDto.setSubject(higher);
                    teacher.setTeacher(subjectOrJournal.getTeacher());
                }
                subjectDto.getTeachers().add(teacher);
            }
            // Add subject study period
            subjectOrJournals.add(subjectDto);
        }
    }

    private void mapMissingData(GraphDto dto, Long pollId, boolean repetitive, boolean isStudent) {
        if (pollId == null) return;
        List<ThemeDto> themes = getPollThemes(em.getReference(Poll.class, pollId));
        if (repetitive) themes = themes.stream().filter(p -> Boolean.TRUE.equals(p.getIsRepetitive())).collect(Collectors.toList());
        for (ThemeDto theme : themes) {
            GraphThemeDto graphTheme = mapGraphTheme(theme, dto);
            List<QuestionDto> questions = theme.getQuestions();
            for (QuestionDto question : questions) {
                for (GraphSubjectDto subject : graphTheme.getJournalOrSubject()) {
                    for (GraphTeacherDto teacher : subject.getTeachers()) {
                        if (PollAnswer.VASTUS_T.name().equals(question.getType())) {
                            mapTextualAnswer(question, teacher, theme);
                        } else if (PollAnswer.VASTUS_M.name().equals(question.getType())) {
                            mapCheckboxAnswer(question, teacher, theme, isStudent);
                        } else {
                            mapOtherAnswer(question, teacher, theme);
                        }
                        orderQuestions(teacher.getGraph());
                    }
                }
            }
        }
    }
    
    private static void mapOtherAnswer(QuestionDto question, GraphTeacherDto teacher, ThemeDto theme) {
     // If question is missing, add it and recalculate everything (max weight might not be same)
        Optional<GraphInfoDto> otherChartOptional = teacher.getGraph().stream()
                .filter(p -> (p.getIsCheckbox() == null || Boolean.FALSE.equals(p.getIsCheckbox())) &&
                (p.getIsText() == null || Boolean.FALSE.equals(p.getIsText()))).findFirst();
        GraphInfoDto graph;
        DatasetOverride datasetOverride;
        long maxWeight;
        if (otherChartOptional.isPresent()) {
            graph = otherChartOptional.get();
            // Check if this question is already mapped
            if (graph.getLabelOverride().isEmpty()) {
                datasetOverride = new DatasetOverride(new ArrayList<>(), Long.valueOf(0L), BigDecimal.valueOf(0));
            } else {
                int indexOfQuestion = graph.getLabels().indexOf(new AutocompleteResult(question.getQuestion(), null, null));
                if (indexOfQuestion != -1) {
                    datasetOverride = graph.getLabelOverride().get(indexOfQuestion);
                } else {
                    datasetOverride = new DatasetOverride(new ArrayList<>(), Long.valueOf(0L), BigDecimal.valueOf(0));
                }
            }
            maxWeight = graph.getMax().longValue();
        } else {
            graph = new GraphInfoDto();
            datasetOverride = new DatasetOverride(new ArrayList<>(), Long.valueOf(0L), BigDecimal.valueOf(0));
            maxWeight = 0L;
        }
        for (QuestionAnswerDto answer : question.getAnswers()) {
            Optional<QuestionResponsePairDto> label = datasetOverride.getPointBorderColor()
                    .stream().filter(p -> p.getAnswer() != null && answer.getId().equals(p.getAnswer().getId())).findFirst();
            if (!label.isPresent()) {
                int answerIndex = question.getAnswers().indexOf(answer);
                if (answer.getAnswerNr() != null && answer.getAnswerNr().longValue() > maxWeight) {
                    maxWeight = answer.getAnswerNr().longValue();
                }
                QuestionResponsePairDto responseDto = new QuestionResponsePairDto();
                responseDto.setAnswer(new AutocompleteResult(answer.getId(), answer.getNameEt(), answer.getNameEt()));
                responseDto.setAnswers(Long.valueOf(0L));
                responseDto.setAnswerNr(Long.valueOf(answer.getAnswerNr().longValue()));
                datasetOverride.getPointBorderColor().add(answerIndex, responseDto);
            }
        }
        int indexOfQuestion = graph.getLabels().indexOf(new AutocompleteResult(question.getQuestion(), null, null));
        graph.setMax(Long.valueOf(maxWeight));
        if (indexOfQuestion == -1) {
            graph.getData().get(0).add(BigDecimal.ZERO);
            String questionEt = getQuestionName(theme.getOrderNr(), question.getOrderNr(), question.getNameEt());
            String questionEn = getQuestionName(theme.getOrderNr(), question.getOrderNr(), question.getNameEn());
            graph.getLabels().add(new AutocompleteResult(question.getQuestion(), questionEt, questionEn));
        } else {
            graph.getLabelOverride().set(indexOfQuestion, datasetOverride);
        }
        if (!otherChartOptional.isPresent()) {
            TitleOptions titleOptions = new TitleOptions();
            titleOptions.setDisplay(Boolean.FALSE);
            GraphOptions options = new GraphOptions(titleOptions);
            graph.setOptions(options);
            teacher.getGraph().add(graph);
        }
    }
    
    private static long roundMaxVal(long maxVal) {
        long roundUpTo = maxVal / 5;
        maxVal = maxVal + roundUpTo;
        if (maxVal > 100) return ((maxVal+5)/10)*10;
        return maxVal;
    }

    private static void mapCheckboxAnswer(QuestionDto question, GraphTeacherDto teacher, ThemeDto theme, boolean isStudent) {
        Optional<GraphInfoDto> checkBoxChartOptional = teacher.getGraph().stream()
                .filter(p -> p.getOptions() != null &&
                p.getOptions().getTitle() != null &&
                p.getOptions().getTitle().getText() != null &&
                question.getQuestion().equals(p.getOptions().getTitle().getText().getId())).findFirst();
        GraphInfoDto graph;
        DatasetOverride datasetOverride;
        if (checkBoxChartOptional.isPresent()) {
            graph = checkBoxChartOptional.get();
            datasetOverride = graph.getLabelOverride().get(0);
        } else {
            graph = new GraphInfoDto();
            datasetOverride = new DatasetOverride(new ArrayList<>(), Long.valueOf(0L), BigDecimal.valueOf(0));
        }
        for (QuestionAnswerDto answer : question.getAnswers()) {
            Optional<AutocompleteResult> label = graph.getLabels().stream().filter(p -> answer.getId().equals(p.getId())).findFirst();
            if (!label.isPresent()) {
                // Answers should be added to correct index
                // Then it is not needed to sort answers again
                int answerIndex = question.getAnswers().indexOf(answer);
                graph.getData().get(0).add(answerIndex, BigDecimal.ZERO);
                if (isStudent) {
                    if (graph.getData().size() < 2) graph.getData().add(new ArrayList<>());
                    graph.getData().get(1).add(answerIndex, BigDecimal.ZERO);
                }
                QuestionResponsePairDto responseDto = new QuestionResponsePairDto();
                responseDto.setAnswer(new AutocompleteResult(answer.getId(), answer.getNameEt(), answer.getNameEt()));
                responseDto.setAnswers(Long.valueOf(0L));
                responseDto.setAnswerNr(Long.valueOf(answer.getAnswerNr().longValue()));
                datasetOverride.getPointBorderColor().add(answerIndex, responseDto);
                graph.getLabels().add(answerIndex, responseDto.getAnswer());
            }
        }
        graph.setLabelOverride(new ArrayList<>());
        question.getAnswers().forEach(p -> {
            graph.getLabelOverride().add(datasetOverride);
        });
        if (!checkBoxChartOptional.isPresent()) {
            graph.setMax(Long.valueOf(1L));
            TitleOptions titleOptions = new TitleOptions();
            titleOptions.setDisplay(Boolean.FALSE);
            String questionEt = getQuestionName(theme.getOrderNr(), question.getOrderNr(), question.getNameEt());
            String questionEn = getQuestionName(theme.getOrderNr(), question.getOrderNr(), question.getNameEn());
            titleOptions.setText(new AutocompleteResult(question.getQuestion(), questionEt, questionEn));
            GraphOptions options = new GraphOptions(titleOptions);
            graph.setOptions(options);
            graph.setIsCheckbox(Boolean.TRUE);
            teacher.getGraph().add(graph);
        }
    }

    private static void mapTextualAnswer(QuestionDto question, GraphTeacherDto teacher, ThemeDto theme) {
        Optional<GraphInfoDto> questionChart = teacher.getGraph().stream()
                .filter(p -> p.getTextAnswer() != null && 
                p.getTextAnswer().getQuestion() != null && 
                question.getQuestion().equals(p.getTextAnswer().getQuestion().getId())).findFirst();
        if (!questionChart.isPresent()) {
            GraphInfoDto textAnswer = new GraphInfoDto();
            GraphTextAnswerDto textAnswerDto = new GraphTextAnswerDto();
            String questionEt = getQuestionName(theme.getOrderNr(), question.getOrderNr(), question.getNameEt());
            String questionEn = getQuestionName(theme.getOrderNr(), question.getOrderNr(), question.getNameEn());
            textAnswerDto.setQuestion(new AutocompleteResult(question.getQuestion(), questionEt, questionEn));
            textAnswer.setIsText(Boolean.TRUE);
            textAnswer.setTextAnswer(textAnswerDto);
            TitleOptions titleOptions = new TitleOptions();
            titleOptions.setDisplay(Boolean.FALSE);
            GraphOptions options = new GraphOptions(titleOptions);
            textAnswer.setOptions(options);
            teacher.getGraph().add(textAnswer);
        }
    }

    private GraphThemeDto mapGraphTheme(ThemeDto theme, GraphDto dto) {
        Optional<GraphThemeDto> graphThemeOptional = dto.getTheme().stream()
                .filter(p -> p.getTitle() != null && theme.getId().equals(p.getTitle().getId())).findFirst();
        GraphThemeDto graphTheme;
        if (graphThemeOptional.isPresent()) {
            graphTheme = graphThemeOptional.get();
        } else {
            graphTheme = new GraphThemeDto();
            graphTheme.setTitle(new AutocompleteResult(theme.getId(), 
                    getThemeName(theme.getOrderNr(), theme.getNameEt()),
                    getThemeName(theme.getOrderNr(), theme.getNameEn())));
            graphTheme.setIsRepetitive(theme.getIsRepetitive());
            dto.getTheme().add(graphTheme);
        }
        return graphTheme;
    }

    private static List<ThemeDto> getPollThemes(Poll poll) {
        List<PollTheme> pollThemes = poll.getPollThemes();
        List<ThemeDto> themeDtos = new ArrayList<>();
        for (PollTheme theme : pollThemes) {
            ThemeDto dto = new ThemeDto();
            bindQuestionsToThemes(theme, dto, null, null);
            themeDtos.add(dto);
        }
        return themeDtos;
    }

    private static void orderQuestions(List<GraphInfoDto> questions) {
        Collections.sort(questions, new Comparator<GraphInfoDto>() {
            @Override
            public int compare(GraphInfoDto graph1, GraphInfoDto graph2) {
                AutocompleteResult label1 = new AutocompleteResult(null, "", "");
                AutocompleteResult label2 = new AutocompleteResult(null, "", "");
                if (Boolean.TRUE.equals(graph1.getIsCheckbox())) {
                    label1 = graph1.getOptions().getTitle().getText();
                } else if (Boolean.TRUE.equals(graph1.getIsText())) {
                    label1 = graph1.getTextAnswer().getQuestion();
                } else if (!graph1.getLabels().isEmpty()) {
                    label1 = graph1.getLabels().get(0);
                }
                if (Boolean.TRUE.equals(graph2.getIsCheckbox())) {
                    label2 = graph2.getOptions().getTitle().getText();
                } else if (Boolean.TRUE.equals(graph2.getIsText())) {
                    label2 = graph2.getTextAnswer().getQuestion();
                } else if (!graph2.getLabels().isEmpty()) {
                    label2 = graph2.getLabels().get(0);
                }
                if (label1 == null || label2 == null) {
                    return 0;
                }
                return label1.getNameEt().compareTo(label2.getNameEt());
            }
        });
    }

    private static GraphInfoDto createCheckboxGraph(List<GraphSearchDto> questionEntries, boolean isStudent, Long responseId) {
        GraphInfoDto graph = new GraphInfoDto();
        QuestionDto question = questionEntries.get(0).getQuestion();
        List<AutocompleteResult> labels = new ArrayList<>();
        List<QuestionResponsePairDto> responses = new ArrayList<>();
        List<Long> weights = new ArrayList<>();
        long maxAnswers = 1L;
        // loop per answer;
        for (GraphSearchDto answer : questionEntries) {
            if (answer.getAnswers() != null) {
                if (answer.getAnswers().longValue() > maxAnswers) {
                    maxAnswers = answer.getAnswers().longValue();
                }
                graph.getData().get(0).add(BigDecimal.valueOf(answer.getAnswers().longValue()));
            } else {
                graph.getData().get(0).add(BigDecimal.ZERO);
            }
            if (isStudent) {
                if (graph.getData().size() < 2) graph.getData().add(new ArrayList<>());
                if (answer.getResponseIds() != null && isStudent && answer.getResponseIds().contains(responseId)) {
                    graph.getData().get(1).add(BigDecimal.valueOf(answer.getAnswerNr().intValue()));
                } else {
                    graph.getData().get(1).add(BigDecimal.ZERO);
                }
            }
            
            for (int i = 0; i < answer.getAnswers().intValue(); i++) {
                weights.add(answer.getAnswerNr());
            }
            
            QuestionResponsePairDto responseDto = new QuestionResponsePairDto();
            responseDto.setAnswer(answer.getQuestionAnswer());
            responseDto.setAnswers(answer.getAnswers());
            responseDto.setAnswerNr(answer.getAnswerNr());
            responses.add(responseDto);
            labels.add(answer.getQuestionAnswer());
        }
        Long uniqueAnswers = Long.valueOf(questionEntries.stream()
                .filter(p -> p.getResponseIds() != null)
                .flatMap(p -> p.getResponseIds().stream()).distinct().count());
        IntSummaryStatistics stats = weights.stream().filter(p -> p != null).mapToInt(p -> p.intValue()).summaryStatistics();
        BigDecimal averageWeight = BigDecimal.valueOf(stats.getAverage()).setScale(2, RoundingMode.HALF_UP);
        questionEntries.forEach(p -> {
            graph.getLabelOverride().add(new DatasetOverride(responses, uniqueAnswers, averageWeight));
        });
        
        graph.setMax(Long.valueOf(roundMaxVal(maxAnswers)));
        TitleOptions titleOptions = new TitleOptions();
        titleOptions.setDisplay(Boolean.FALSE);
        titleOptions.setText(new AutocompleteResult(question.getId(), question.getNameEt(), question.getNameEn()));
        GraphOptions options = new GraphOptions(titleOptions);
        graph.setOptions(options);
        graph.setLabels(labels);
        graph.setIsCheckbox(Boolean.TRUE);
        return graph;
    }

    public Map<Long, List<GraphSearchDto>> sortMapByValue(Map<Long, List<GraphSearchDto>> entryMap, Comparator<? super Entry<Long, List<GraphSearchDto>>> comparator) {
        List<Entry<Long, List<GraphSearchDto>>> list = new ArrayList<>(entryMap.entrySet());
        list.sort(comparator);
        Map<Long, List<GraphSearchDto>> sortedQuestionMap = new LinkedHashMap<>();
        for (Entry<Long, List<GraphSearchDto>> listEntry : list) {
            sortedQuestionMap.put(listEntry.getKey(), listEntry.getValue());
        }
        return sortedQuestionMap;
    }
    
    public String getThemeName(Number short1, String name) {
        return short1 + ". " + name;
    }
    
    public static String getQuestionName(Number themeOrder, Number questionOrder, String name) {
        return themeOrder + "." + questionOrder + " " + name;
    }
    
    private List<SubjectCommentDto> getComments(HoisUserDetails user, Long pollId, GraphSearchCommand command) {
        String SEARCH_SELECT = "ptc.add_info, ptc.id as commentId, t.id as teacherId, (p.firstname || ' ' || p.lastname)";
        
        String SEARCH_FROM = "from poll_teacher_comment ptc join teacher t on t.id = ptc.teacher_id join person p on p.id = t.person_id";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);        
        qb.requiredCriteria("ptc.poll_id = :pollId", "pollId", pollId);
        qb.optionalCriteria("ptc.subject_study_period_id = :subjectId", "subjectId", command.getSubjectStudyPeriodId());
        qb.optionalCriteria("ptc.journal_id = :journalId", "journalId", command.getJournalId());
        if (user.isTeacher()) qb.requiredCriteria("ptc.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        List<?> dbComments = qb.select(SEARCH_SELECT, em).getResultList();
        List<SubjectCommentDto> comments = StreamUtil
                .toMappedList(r-> {
                    SubjectCommentDto dto = new SubjectCommentDto();
                    dto.setAddInfo(resultAsString(r, 0));
                    dto.setCommentRef(resultAsLong(r, 1));
                    dto.setTeacher(new AutocompleteResult(resultAsLong(r, 2), resultAsString(r, 3), resultAsString(r, 3)));
                    return dto;
                }, dbComments);
        return comments;
    }

    public SubjectCommentDto saveComment(HoisUserDetails user, Poll poll, PollCommentCommand command) {
        PollTeacherComment comment;
        if (command.getCommentRef() == null) {
            comment = new PollTeacherComment();
            if (command.getJournalId() != null) {
                comment.setJournal(em.getReference(Journal.class, command.getJournalId()));
            } else if (command.getSubjectStudyPeriodId() != null) {
                comment.setSubjectStudyPeriod(em.getReference(SubjectStudyPeriod.class, command.getSubjectStudyPeriodId()));
            }
            comment.setTeacher(em.getReference(Teacher.class, user.getTeacherId()));
            comment.setPoll(poll);
        } else {
            comment = em.getReference(PollTeacherComment.class, command.getCommentRef());
        }
        // if comment is empty or null - delete the entity
        EntityUtil.setUsername(user.getUsername(), em);
        if (StringUtils.isEmpty(command.getComment()) && comment.getId() != null) {
            EntityUtil.deleteEntity(comment, em);
        } else if (!StringUtils.isEmpty(command.getComment())) {
            comment.setAddInfo(command.getComment());
            return SubjectCommentDto.of(EntityUtil.save(comment, em));
        }
        return new SubjectCommentDto();
    }

    public PollResultsDto pollResults(Poll poll) {
        String SEARCH_SELECT = "r.status_code";
        String SEARCH_FROM = "from response r join response_object ro on ro.response_id = r.id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);        
        qb.requiredCriteria("r.poll_id = :pollId", "pollId", EntityUtil.getId(poll));
        qb.filter("r.status_code != 'KYSITVASTUSSTAATUS_A'");
        qb.filter("exists(select rqa.id from response_question_answer rqa where rqa.response_id = r.id)");
        List<?> dbResponses = qb.select(SEARCH_SELECT, em).getResultList();
        long partly = 0;
        long all = 0;
        for (Object r : dbResponses) {
            String statusCode = resultAsString(r, 0);
            if (ResponseStatus.KYSITVASTUSSTAATUS_P.name().equals(statusCode)) {
                all++;
                partly++;
            } else if (ResponseStatus.KYSITVASTUSSTAATUS_V.name().equals(statusCode)) {
                all++;
            }
        }
        PollResultsDto dto = new PollResultsDto();
        dto.setPoll(new AutocompleteResult(EntityUtil.getId(poll), poll.getNameEt(), poll.getNameEn()));
        dto.setType(poll.getType().getCode());
        long totalTargetCount = poll.getPollTargets().stream()
                .filter(p -> p.getTargetCount() != null)
                .mapToInt(p -> p.getTargetCount().intValue()).sum();
        dto.setTargetCount(Long.valueOf(totalTargetCount));
        dto.setAllResponses(Long.valueOf(all));
        dto.setPartialResponses(Long.valueOf(partly));
        dto.setStatus(EntityUtil.getCode(poll.getStatus()));
        return dto;
    }
    
    public Page<PollResultsSubjectDto> pollResultSubjects(Poll poll, Pageable pageable) {
        Long pollId = EntityUtil.getId(poll);
        String SEARCH_SELECT = "s.id as subjectId, s.name_et as subjectEt, s.name_en as subjectEn, s.code, ssp.id, "
                + "string_agg((p.firstname || ' ' || p.lastname), ', ' order by p.firstname, p.lastname) as teacher, s.inserted";
        String SEARCH_FROM = "from study_period sp " + 
                "join subject_study_period ssp on sp.id = ssp.study_period_id " + 
                "join subject s on ssp.subject_id = s.id " +
                "join subject_study_period_teacher sspt on ssp.id = sspt.subject_study_period_id " + 
                "join teacher t on t.id = sspt.teacher_id " +
                "join person p on t.person_id = p.id"; 
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        qb.optionalCriteria("sp.id = :spId", "spId", EntityUtil.getNullableId(poll.getStudyPeriod()));
        qb.filter("exists( select 1 " + 
                "from response r " + 
                "join response_object ro on ro.response_id = r.id " +
                "and (r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_P.name() + "' " +
                "or r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_V.name() + "') " + 
                "join response_subject rs on rs.response_id = r.id " + 
                "join response_question_answer rqa on rqa.response_subject_id = rs.id " +
                "join declaration dd on dd.study_period_id = sp.id and dd.student_id = ro.student_id " + 
                "join declaration_subject ds on dd.id = ds.declaration_id and ds.subject_study_period_id = ssp.id " +
                "where r.poll_id = " + pollId + " and rs.subject_id = s.id)");
        qb.groupBy("s.id, s.name_et, s.name_en, s.code, ssp.id");
        qb.sort(pageable);
        return JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable).map(r -> {
            PollResultsSubjectDto dto = new PollResultsSubjectDto();
            dto.setName(new AutocompleteResult(resultAsLong(r, 0), 
                    resultAsString(r, 3) + " - " + resultAsString(r, 1), 
                    resultAsString(r, 3) + " - " + resultAsString(r, 2)));
            dto.setTeacher(new AutocompleteResult(resultAsLong(r, 4), resultAsString(r, 5),resultAsString(r, 5)));
            return dto; 
        });
    }
    
    public Page<PollResultsSubjectDto> pollResultJournals(Poll poll, Pageable pageable) {
        Long pollId = EntityUtil.getId(poll);
        String SEARCH_SELECT = "jj.id as journalId, jj.name_et as journalEt, string_agg((p.firstname || ' ' || p.lastname), ', ') as teacher, jj.inserted";
        String SEARCH_FROM = "from poll_journal pj " + 
                "join journal jj on (pj.journal_id = jj.id and pj.poll_id = " + pollId + ") " +
                "left join journal_teacher jt on jj.id = jt.journal_id " + 
                "left join teacher t on t.id = jt.teacher_id " +
                "left join person p on t.person_id = p.id"; 
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        qb.filter("exists( select 1 " + 
                "from response r " + 
                "join response_object ro on ro.response_id = r.id " +
                "and (r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_P.name() + "' " +
                "or r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_V.name() + "') " + 
                "join response_subject rs on rs.response_id = r.id " + 
                "join response_question_answer rqa on rqa.response_subject_id = rs.id " +
                "where r.poll_id = " + pollId + " and rs.journal_id = pj.journal_id)");
        qb.groupBy("jj.id, jj.name_et");
        qb.sort(pageable);
        return JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable).map(r -> {
            PollResultsSubjectDto dto = new PollResultsSubjectDto();
            dto.setName(new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 1)));
            dto.setTeacher(new AutocompleteResult(null, resultAsString(r, 2),resultAsString(r, 2)));
            return dto; 
        });
    }

    public Page<PollResultStudentDto> pollResultStudent(PollResultStudentCommand command,
            Pageable pageable) {
        String SEARCH_FROM = "from response r "
                + "join response_object ro on ro.response_id = r.id "
                + "join student s on s.id = ro.student_id "
                + "left join student_group sg on s.student_group_id = sg.id "
                + "join person p on p.id = s.person_id "
                + "join response_subject rs on rs.response_id = r.id";
        String SEARCH_SELECT = "distinct (p.firstname || ' ' || p.lastname) as studentName, sg.code, r.status_code";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        qb.optionalContains("p.firstname || ' ' || p.lastname", "name", command.getStudentName());
        qb.optionalCriteria("rs.journal_id = :journalId", "journalId", command.getJournalId());
        qb.optionalCriteria("rs.subject_study_period_id = :subjectStudyPeriodId", "subjectStudyPeriodId", command.getSubjectStudyPeriodId());
        qb.requiredCriteria("r.poll_id = :pollId", "pollId", command.getPollId());
        qb.filter("(r.status_code = 'KYSITVASTUSSTAATUS_P' or r.status_code = 'KYSITVASTUSSTAATUS_V')");
        return JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable).map(r -> {
            PollResultStudentDto dto = new PollResultStudentDto();
            dto.setName(resultAsString(r, 0));
            dto.setStudentGroup(resultAsString(r, 1));
            dto.setStatus(resultAsString(r, 2));
            return dto; 
        });
    }
    
    public byte[] exportStatistics(HoisUserDetails user, PollResultStatisticsCommand criteria) {
        PollXlsDto pollDto = new PollXlsDto();
        School school = em.getReference(School.class, user.getSchoolId());
        if (school != null) {
            pollDto.setHigher(Boolean.valueOf(schoolService.schoolType(EntityUtil.getId(school)).isHigher()));
        }
        criteria.setMessage("poll.statistics.pollTargetMessage");
        List<PollTarget> pollTargets = em.createQuery("select pt as target from PollTarget pt "
                + "where pt.poll.id in ?1 ", PollTarget.class)
                .setParameter(1, criteria.getPollIds())
                .getResultList();
        if (pollTargets != null && !pollTargets.isEmpty()) {
            setXlsDtoTargets(pollTargets, pollDto);
        }
        criteria.setMessage("poll.statistics.settingLegend");
        setStatisticsLegend(pollDto, criteria);
        criteria.setMessage("poll.statistics.settingHeader");
        setStatisticsTableHeader(pollDto, user);
        criteria.setMessage("poll.statistics.settingData");
        setStatisticsResponses(pollDto, criteria, user);
        Map<String, Object> data = new HashMap<>();
        data.put("poll", pollDto);
        criteria.setMessage("poll.statistics.generatingFile");
        return xlsService.generate("pollstatistics.xlsx", data);
    }
    
    private void setStatisticsTableHeader(PollXlsDto dto, HoisUserDetails user) {
        SchoolType type = schoolService.schoolType(user.getSchoolId());
        List<String> header = new ArrayList<>();
        header.add("poll.code");
        if (dto.getTargetStudent().booleanValue()) {
            header.add("poll.student.code");
            header.add("poll.student.studyform");
            header.add("poll.student.curriculum");
        }
        if (dto.getTargetTeacher().booleanValue()) header.add("poll.teacher.code");
        if (dto.getUsePersonCode().booleanValue()) header.add("poll.person.code");
        header.add("poll.other.code");
        if (dto.getTargetEnterprise().booleanValue()) header.add("poll.enterprise");
        if (type.isHigher()) {
            if (type.isVocational()) {
                header.add("poll.teacher.both");
            } else {
                header.add("poll.teacher.higher");
            }
        } else {
            header.add("poll.teacher");
        }
        if (type.isHigher()) {
            if (type.isVocational()) {
                header.add("poll.subject.and.journal");
            } else {
                header.add("poll.subject");
            }
            header.add("poll.subject.code");
        } else {
            header.add("poll.journal");
        }
        List<String> headerResults = new ArrayList<>();
        for (PollResponseLegendXlsDto legend : dto.getLegend()) {
            for (PollQuestionXlsDto question : legend.getQuestions()) {
                if (PollAnswer.VASTUS_M.name().equals(question.getType()) || PollAnswer.VASTUS_S.name().equals(question.getType())) {
                    headerResults.addAll(question.getAnswers().stream().map(p -> question.getCode() + "(" + p.getOrderNr() + ")").collect(Collectors.toList()));
                } else {
                    headerResults.add(question.getCode());
                }
            }
        }
        dto.setTableHeader(header);
        dto.setTableHeaderResults(headerResults);
    }
    
    private void setStatisticsLegend(PollXlsDto dto, PollResultStatisticsCommand criteria) {
        String SEARCH_SELECT = "distinct q.id as questionId, q.name_et as questionEt, q.name_en as questionEn,"
                + " q.type_code, qa.order_nr as answerOrder, qa.name_et as answerEt, qa.name_en as answerEn, qa.id as answerId,"
                + " qa.answer_nr as weight";
        
        String SEARCH_FROM = "from poll_theme pt "
                + "join poll_theme_question ptq on ptq.poll_theme_id = pt.id "
                + "join question q on q.id = ptq.question_id "
                + "left join question_answer qa on qa.question_id = q.id";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("pt.poll_id in :pollIds", "pollIds", criteria.getPollIds());
        qb.optionalCriteria("q.id in :questionIds", "questionIds", criteria.getQuestions());
        List<?> questions = qb.sort("q.id, qa.order_nr").select(SEARCH_SELECT, em).getResultList();
        List<PollResponseLegendXlsDto> legend = new ArrayList<>();
        PollResponseLegendXlsDto legendTheme = new PollResponseLegendXlsDto();
        legend.add(legendTheme);
        for (Object r : questions) {
            Long questionId = resultAsLong(r, 0);
            Optional<PollQuestionXlsDto> questionOpt = legendTheme.getQuestions().stream().filter(p -> p.getName().getId().equals(questionId)).findFirst();
            PollQuestionXlsDto question;
            if (questionOpt.isPresent()) {
                question = questionOpt.get();
            } else {
                question = new PollQuestionXlsDto();
                question.setCode("K" + questionId);
                question.setName(new AutocompleteResult(questionId, resultAsString(r, 1), resultAsString(r, 2)));
                String type = resultAsString(r, 3);
                question.setType(type);
                if (PollAnswer.VASTUS_T.name().equals(type)) question.setHasAnswers(Boolean.FALSE);
                legendTheme.getQuestions().add(question);
            }
            Long answerId = resultAsLong(r, 7);
            if (answerId != null) {
                AutocompleteResult answer = new AutocompleteResult(resultAsLong(r, 7), resultAsString(r, 5), resultAsString(r, 6));
                QuestionAnswerXlsDto answerDto = new QuestionAnswerXlsDto();
                answerDto.setName(answer);
                answerDto.setOrderNr(resultAsLong(r, 4));
                answerDto.setWeight(resultAsLong(r, 8));
                question.getAnswers().add(answerDto);
            }
        }
        dto.setLegend(legend);
    }
    
    
    
    public byte[] searchExcel(PollResultStudentCommand criteria, HoisUserDetails user) {
        PollXlsDto pollDto = new PollXlsDto();
        Poll poll = em.getReference(Poll.class, criteria.getPollId());
        pollDto.setId(criteria.getPollId());
        pollDto.setName(AutocompleteResult.of(poll));
        pollDto.setType(poll.getType().getCode());
        pollDto.setValidFrom(poll.getValidFrom());
        pollDto.setValidThru(poll.getValidThru());
        School school = poll.getSchool();
        if (school != null) {
            pollDto.setHigher(Boolean.valueOf(schoolService.schoolType(EntityUtil.getId(school)).isHigher()));
        }
        StudyPeriod studyPeriod = poll.getStudyPeriod();
        if (studyPeriod != null && studyPeriod.getStudyYear() != null) {
            pollDto.setStudyPeriod(AutocompleteResult.ofWithYear(studyPeriod));
            pollDto.setHasStudyPeriod(Boolean.TRUE);
        } else if (studyPeriod != null) { 
            pollDto.setStudyPeriod(AutocompleteResult.of(studyPeriod));
            pollDto.setHasStudyPeriod(Boolean.TRUE);
        }
        List<PollTarget> pollTargets = poll.getPollTargets();   
        if (pollTargets != null && !pollTargets.isEmpty()) {
            setXlsDtoTargets(pollTargets, pollDto);
        }
        boolean isSubjectPoll = PollType.KYSITLUS_O.name().equals(pollDto.getType());
        setLegend(pollDto, criteria);
        setTableHeader(pollDto, user);
        setResponses(pollDto, criteria);
        if (isSubjectPoll) setComments(pollDto, criteria);
        Map<String, Object> data = new HashMap<>();
        data.put("poll", pollDto);
        return xlsService.generate(isSubjectPoll ? "pollsubjectresultcomment.xls" : "pollsubjectresult.xls", data);
    }

    private void setComments(PollXlsDto pollDto, PollResultStudentCommand criteria) {
        String SEARCH_SELECT = "ptc.add_info, s.id as subjectId, s.name_et as subjectEt, s.name_en as subjectEn, s.code, j.id as journalId, j.name_et as journalEt, (p.firstname || ' ' || p.lastname)";
        
        String SEARCH_FROM = "from poll_teacher_comment ptc "
                + "join teacher t on t.id = ptc.teacher_id "
                + "join person p on p.id = t.person_id "
                + "left join subject_study_period sps on sps.id = ptc.subject_study_period_id "
                + "left join subject s on sps.subject_id = s.id "
                + "left join journal j on j.id = ptc.journal_id ";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);        
        qb.requiredCriteria("ptc.poll_id = :pollId", "pollId", criteria.getPollId());
        qb.optionalCriteria("ptc.subject_study_period_id = :subjectStudyPeriodId", "subjectStudyPeriodId", criteria.getSubjectStudyPeriodId());
        qb.optionalCriteria("ptc.journal_id = :journalId", "journalId", criteria.getJournalId());
        List<?> dbComments = qb.select(SEARCH_SELECT, em).getResultList();
        List<PollCommentXlsDto> comments = StreamUtil
                .toMappedList(r-> {
                    PollCommentXlsDto dto = new PollCommentXlsDto();
                    dto.setPollId(criteria.getPollId());
                    dto.setComment(resultAsString(r, 0));
                    Long subjectId = resultAsLong(r, 1);
                    if (subjectId != null) {
                        dto.setName(new AutocompleteResult(subjectId, resultAsString(r, 2), resultAsString(r, 3)));
                        dto.setSubjectCode(resultAsString(r, 4));
                    } else {
                        dto.setName(new AutocompleteResult(resultAsLong(r, 5), resultAsString(r, 6), resultAsString(r, 6)));
                    }
                    dto.setTeacher(resultAsString(r, 7));
                    return dto;
                }, dbComments);
        pollDto.setComments(comments);
    }
    
    private void setStatisticsResponses(PollXlsDto pollDto, PollResultStatisticsCommand criteria, HoisUserDetails user) {
        String SEARCH_SELECT = "r.poll_id as pollId, ro.student_id, ss.name_et as formEt, ss.name_en as formEn, ss.code as curriculumCode, "
                + "ro.teacher_id, ro.person_id, r.id as responseId, ee.name, pt.target_code, "
                + "string_agg(concat(qa.id, ':',rqa.question_id, ':', qa.answer_nr, ':', replace(rqa.answer_txt, '`', '''')), '`') as answers, "
                + "string_agg(distinct nullif(concat(p.firstname, ' ', p.lastname), ' '), ', ') as teachers, "
                + "s.id as subjectId, s.name_et as subjectEt, s.name_en as subjectEn, s.code as subjectcode, j.id as journalId, j.name_et as journalEt";
        String SEARCH_FROM = "from response r "
                + "join response_object ro on ro.response_id = r.id "
                + "join poll_target pt on pt.id = ro.poll_target_id "
                    + "left join (select s.id, c.name_et, c.name_en, cu.code from student s "
                    + "left join classifier c on c.code = s.study_form_code "
                    + "left join curriculum_version cv on cv.id = s.curriculum_version_id "
                    + "left join curriculum cu on cu.id = cv.curriculum_id) ss on ss.id = ro.student_id "
                + "left join (select pj.id, e.name from practice_journal pj "
                    + "join contract co on co.id = pj.contract_id "
                    + "join enterprise e on e.id = co.enterprise_id ) ee on ro.practice_journal_id = ee.id "
                + "join response_question_answer rqa on rqa.response_id = r.id "
                + "left join question_answer qa on qa.id = rqa.question_answer_id "
                + "left join response_subject rs on rs.response_id = r.id and rqa.response_subject_id = rs.id " 
                + "left join journal j on j.id=rs.journal_id "
                + "left join journal_teacher jt on jt.journal_id=rs.journal_id "
                + "left join subject_study_period stp on rs.subject_study_period_id = stp.id "
                + "left join subject_study_period_teacher sspt on sspt.subject_study_period_id = stp.id "
                + "left join subject s on s.id = stp.subject_id "
                + "left join teacher t on ((rqa.teacher_id is not null and rqa.teacher_id = t.id and (sspt.teacher_id = t.id or jt.teacher_id = t.id))"
                                    + " or (rqa.teacher_id is null and (sspt.teacher_id = t.id or jt.teacher_id = t.id))) "
                + "left join person p on t.person_id = p.id";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("r.poll_id in :pollIds", "pollIds", criteria.getPollIds());
        qb.requiredCriteria("rqa.question_id in :questionIds", "questionIds", criteria.getQuestions());
        List<?> questions = qb.groupBy("ro.student_id, ss.name_et, ss.name_en, ss.code, ro.teacher_id, ro.person_id, r.id, ee.name, pt.target_code, rqa.teacher_id"
                + ", s.name_en, s.name_et, s.code, j.name_et, s.id, j.id")
                .select(SEARCH_SELECT, em).getResultList(); 
        // Person, student, teacher generated id's should be randomized once
        // If a person/teacher/students id is already used, set its id to an existing one (id + 4 digit random nr)
        List<String> personIds = new ArrayList<>();
        List<String> studentIds = new ArrayList<>();
        List<String> teacherIds = new ArrayList<>();
        List<PollAnswersXlsDto> responses = StreamUtil
                .toMappedList(r-> {
                    SchoolType schoolType = schoolService.schoolType(user.getSchoolId());
                    PollAnswersXlsDto dto = new PollAnswersXlsDto();
                    dto.getBasicData().add(new AutocompleteResult(null, String.valueOf(resultAsLong(r, 0)), String.valueOf(resultAsLong(r, 0))));
                    String target = resultAsString(r, 9);
                    boolean targetStudent = PollTargets.KYSITLUS_SIHT_O.name().equals(target);
                    boolean targetTeacher = PollTargets.KYSITLUS_SIHT_T.name().equals(target);
                    boolean targetEnterprise = PollTargets.KYSITLUS_SIHT_E.name().equals(target);
                    bindStudent(pollDto, dto, resultAsLong(r, 1), resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 4), targetStudent, studentIds);
                    bindTeacher(pollDto, dto, resultAsLong(r, 5), targetTeacher, teacherIds);
                    bindPersonCode(pollDto, dto, resultAsLong(r, 6), resultAsLong(r, 7).toString(), target, personIds);
                    bindEnterprise(pollDto, dto, resultAsString(r, 8), targetEnterprise);
                    optionalAdd(dto, resultAsString(r, 11));
                    if (resultAsLong(r, 12) != null) {
                        // subject
                        dto.getBasicData().add(new AutocompleteResult(resultAsLong(r, 12), resultAsString(r, 13), resultAsString(r, 14)));
                        dto.getBasicData().add(new AutocompleteResult(null, resultAsString(r, 15), resultAsString(r, 15)));
                    } else {
                        // journal
                        dto.getBasicData().add(new AutocompleteResult(resultAsLong(r, 16), resultAsString(r, 17), resultAsString(r, 17)));
                        if (schoolType.isHigher() && schoolType.isVocational()) {
                            dto.getBasicData().add(new AutocompleteResult());
                        }
                    }
                    bindAnswers(dto, resultAsString(r, 10));
                    return dto;
                }, questions);
        mapXlsAnswers(responses, pollDto);
    }

    private static void optionalAdd(PollAnswersXlsDto dto, String object) {
        if (!StringUtils.isEmpty(object)) {
            dto.getBasicData().add(new AutocompleteResult(null, object, object));
        } else {
            dto.getBasicData().add(new AutocompleteResult(null, "-", "-"));
        }
    }

    private static void bindAnswers(PollAnswersXlsDto dto, String answersText) {
        List<String> answers = Arrays.asList(answersText.split("`"));
        List<PollAnswerXlsDto> answerDtos = new ArrayList<>();
        for (String answer : answers) {
            List<String> questionIdanswerNranswerTxt = Arrays.asList(answer.split(":", 4));
            PollAnswerXlsDto answerDto = new PollAnswerXlsDto();
            String answerIdString = questionIdanswerNranswerTxt.get(0);
            String weightString = questionIdanswerNranswerTxt.get(2);
            if (!StringUtils.isEmpty(answerIdString)) {
                answerDto.setAnswerId(Long.valueOf(answerIdString));
            }
            if (!StringUtils.isEmpty(weightString)) {
                answerDto.setWeight(Short.valueOf(weightString));
            }
            Long questionId = Long.valueOf(questionIdanswerNranswerTxt.get(1));
            String answerTxt = questionIdanswerNranswerTxt.get(3);
            answerDto.setQuestionId(questionId);
            answerDto.setAnswerTxt(answerTxt);
            answerDtos.add(answerDto);
        }
        dto.setAnswers(answerDtos);
    }

    private static void bindTeacher(PollXlsDto pollDto, PollAnswersXlsDto dto, Long teacherId, boolean targetTeacher, List<String> teacherIds) {
        if (pollDto.getTargetTeacher().booleanValue()) {
            if (teacherId != null && targetTeacher) {
                Optional<String> existingId = teacherIds.stream().filter(p -> p.substring(0, p.length() - 4).equals(teacherId.toString())).findFirst();
                String teacherIdString;
                if (existingId.isPresent()) {
                    teacherIdString = existingId.get();
                } else {
                    teacherIdString = teacherId + generateRandomNr();
                    teacherIds.add(teacherIdString);
                }
                dto.getBasicData().add(new AutocompleteResult(null, teacherIdString, teacherIdString));
            } else {
                dto.getBasicData().add(new AutocompleteResult(null, "-", "-"));
            }
        }
    }

    private static void bindPersonCode(PollXlsDto pollDto, PollAnswersXlsDto dto, Long personId, String responseIdString, String target, List<String> personIds) {
        boolean shouldShow = EnumUtil.toNameList(
                PollTargets.KYSITLUS_SIHT_O,
                PollTargets.KYSITLUS_SIHT_T,
                PollTargets.KYSITLUS_SIHT_L,
                PollTargets.KYSITLUS_SIHT_A).contains(target);
        if (pollDto.getUsePersonCode().booleanValue()) {
            if (personId != null && shouldShow) {
                Optional<String> existingId = personIds.stream().filter(p -> p.substring(0, p.length() - 4).equals(personId.toString())).findFirst();
                String personIdString;
                if (existingId.isPresent()) {
                    personIdString = existingId.get();
                } else {
                    personIdString = personId + generateRandomNr();
                    personIds.add(personIdString);
                }
                dto.getBasicData().add(new AutocompleteResult(null, personIdString, personIdString));
                dto.getBasicData().add(new AutocompleteResult(null, "-", "-"));
            } else if (responseIdString != null) {
                dto.getBasicData().add(new AutocompleteResult(null, "-", "-"));
                dto.getBasicData().add(new AutocompleteResult(null, responseIdString, responseIdString));
            } else {
                dto.getBasicData().add(new AutocompleteResult(null, "-", "-"));
                dto.getBasicData().add(new AutocompleteResult(null, "-", "-"));
            }
        }
    }

    private void setResponses(PollXlsDto pollDto, PollResultStudentCommand criteria) {
        String SEARCH_SELECT = "ro.student_id, ss.name_et as formEt, ss.name_en as formEn, ss.code as curriculumCode, "
                + "ro.teacher_id, ro.person_id, r.id as responseId, ee.name, pt.target_code, "
                + "string_agg(concat(qa.id, ':',rqa.question_id, ':', qa.answer_nr, ':', replace(rqa.answer_txt, '`', '''')), '`') as answers, "
                + "string_agg(distinct nullif(concat(p.firstname, ' ', p.lastname), ' '), ', ') as teachers, "
                + "s.id as subjectId, s.name_et as subjectEt, s.name_en as subjectEn, s.code as subjectcode, j.id as journalId, j.name_et as journalEt";
        String SEARCH_FROM = "from response r "
                + "join response_object ro on ro.response_id = r.id "
                + "join poll_target pt on pt.id = ro.poll_target_id "
                    + "left join (select s.id, c.name_et, c.name_en, cu.code from student s "
                    + "left join classifier c on c.code = s.study_form_code "
                    + "left join curriculum_version cv on cv.id = s.curriculum_version_id "
                    + "left join curriculum cu on cu.id = cv.curriculum_id) ss on ss.id = ro.student_id "
                + (criteria.getEnterpriseId() != null ? "" : "left ") + "join (select pj.id, e.name from practice_journal pj "
                    + "join contract co on co.id = pj.contract_id "
                    + "join enterprise e on e.id = co.enterprise_id " 
                    + (criteria.getEnterpriseId() != null ? "and e.id = " + criteria.getEnterpriseId() : "") 
                    + ") ee on ro.practice_journal_id = ee.id "
                + " join response_question_answer rqa on rqa.response_id = r.id"
                + " left join question_answer qa on rqa.question_answer_id = qa.id"
                + " left join response_subject rs on rs.response_id = r.id and rqa.response_subject_id = rs.id"
                + " left join journal j on j.id = rs.journal_id"
                + " left join journal_teacher jt on jt.journal_id=rs.journal_id"
                + " left join subject_study_period stp on rs.subject_study_period_id = stp.id"
                + " left join subject s on s.id = stp.subject_id"
                + " left join subject_study_period_teacher sspt on sspt.subject_study_period_id = stp.id"
                + " left join teacher t on ((rqa.teacher_id is not null and rqa.teacher_id = t.id and (sspt.teacher_id = t.id or jt.teacher_id = t.id))"
                    + " or (rqa.teacher_id is null and (sspt.teacher_id = t.id or jt.teacher_id = t.id)))"
                + " left join person p on t.person_id = p.id";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("r.poll_id = :pollId", "pollId", criteria.getPollId());
        if (criteria.getEnterpriseId() != null) qb.filter("pt.target_code = 'KYSITLUS_SIHT_E'");
        if (criteria.getStudents() != null && criteria.getStudents().booleanValue()) qb.filter("pt.target_code = 'KYSITLUS_SIHT_O'");
        if (criteria.getTeachers() != null && criteria.getTeachers().booleanValue()) qb.filter("pt.target_code = 'KYSITLUS_SIHT_T'");
        qb.optionalCriteria("rs.journal_id = :journalId", "journalId", criteria.getJournalId());
        qb.optionalCriteria("rs.subject_study_period_id = :subjectStudyPeriodId", "subjectStudyPeriodId", criteria.getSubjectStudyPeriodId());
        List<?> questions = qb.groupBy("ro.student_id, ss.name_et, ss.name_en, ss.code, ro.teacher_id, ro.person_id, r.id, ee.name, pt.target_code"
                + ", rqa.teacher_id, s.name_en, s.name_et, s.code, j.name_et, s.id, j.id")
                .select(SEARCH_SELECT, em).getResultList();
        // Person, student, teacher generated id's should be randomized once
        // If a person/teacher/students id is already used, set its id to an existing one (id + 4 digit random nr)
        List<String> personIds = new ArrayList<>();
        List<String> studentIds = new ArrayList<>();
        List<String> teacherIds = new ArrayList<>();
        List<PollAnswersXlsDto> responses = StreamUtil
                .toMappedList(r-> {
                    PollAnswersXlsDto dto = new PollAnswersXlsDto();
                    String pollIdString = criteria.getPollId().toString();
                    String target = resultAsString(r, 8);
                    boolean targetStudent = PollTargets.KYSITLUS_SIHT_O.name().equals(target);
                    boolean targetTeacher = PollTargets.KYSITLUS_SIHT_T.name().equals(target);
                    boolean targetEnterprise = PollTargets.KYSITLUS_SIHT_E.name().equals(target);
                    dto.getBasicData().add(new AutocompleteResult(null, pollIdString, pollIdString));
                    bindStudent(pollDto, dto, resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 3), targetStudent, studentIds);
                    bindTeacher(pollDto, dto, resultAsLong(r, 4), targetTeacher, teacherIds);
                    bindPersonCode(pollDto, dto, resultAsLong(r, 5), resultAsLong(r, 6).toString(), target, personIds);
                    bindEnterprise(pollDto, dto, resultAsString(r, 7), targetEnterprise);
                    if (PollType.KYSITLUS_O.name().equals(pollDto.getType())) {
                        // teachers
                        optionalAdd(dto, resultAsString(r, 10));
                        if (pollDto.getStudyPeriod() != null) {
                            if (resultAsLong(r, 11) != null) {
                                dto.getBasicData().add(new AutocompleteResult(resultAsLong(r, 11), resultAsString(r, 12), resultAsString(r, 13)));
                                dto.getBasicData().add(new AutocompleteResult(null, resultAsString(r, 14), resultAsString(r, 14)));
                            } else {
                                dto.getBasicData().add(new AutocompleteResult(resultAsLong(r, 15), resultAsString(r, 16), resultAsString(r, 16)));
                                dto.getBasicData().add(new AutocompleteResult());
                            }
                        } else {
                            dto.getBasicData().add(new AutocompleteResult(null, resultAsString(r, 16), resultAsString(r, 16)));
                        }
                    }
                    bindAnswers(dto, resultAsString(r, 9));
                    return dto;
                }, questions);
        mapXlsAnswers(responses, pollDto);
    }
    
    private static void bindStudent(PollXlsDto pollDto, PollAnswersXlsDto dto, Long studentId, String curriculumEt,
            String curriculumEn, String curriculumCode, boolean targetStudent, List<String> studentIds) {
        if (pollDto.getTargetStudent().booleanValue()) {
            if (studentId != null && targetStudent) {
                Optional<String> existingId = studentIds.stream().filter(p -> p.substring(0, p.length() - 4).equals(studentId.toString())).findFirst();
                String studentIdString;
                if (existingId.isPresent()) {
                    studentIdString = existingId.get();
                } else {
                    studentIdString = studentId + generateRandomNr();
                    studentIds.add(studentIdString);
                }
                dto.getBasicData().add(new AutocompleteResult(null, studentIdString, studentIdString));
                dto.getBasicData().add(new AutocompleteResult(null, curriculumEt, curriculumEn));
                dto.getBasicData().add(new AutocompleteResult(null, curriculumCode, curriculumCode));
            } else {
                dto.getBasicData().add(new AutocompleteResult(null, "-", "-"));
                dto.getBasicData().add(new AutocompleteResult(null, "-", "-"));
                dto.getBasicData().add(new AutocompleteResult(null, "-", "-"));
            }
        }
    }

    private static void bindEnterprise(PollXlsDto pollDto, PollAnswersXlsDto dto, String enterprise, boolean targetEnterprise) {
        if (pollDto.getTargetEnterprise().booleanValue()) {
            if (enterprise != null && targetEnterprise) {
                dto.getBasicData().add(new AutocompleteResult(null, enterprise, enterprise));
            } else {
                dto.getBasicData().add(new AutocompleteResult(null, "-", "-"));
            }
        }
    }

    private static String generateRandomNr() {
        //cant end with 0, needs to be between 1000 and 9999
        Random rand = new Random();
        String randomNr = String.valueOf(rand.nextInt((9999 - 1000) + 1) + 1000);
        if (randomNr.endsWith("0")) {
            randomNr = randomNr.substring(0, 3) + "1";
        }
        return randomNr;
    }

    private static void mapXlsAnswers(List<PollAnswersXlsDto> responses, PollXlsDto dto) {
        for (PollAnswersXlsDto response : responses) {
            List<PollAnswerXlsDto> answers = response.getAnswers();
            List<Object> mapper = new ArrayList<>();
            for (PollResponseLegendXlsDto legend : dto.getLegend()) {
                if (dto.getId() != null) mapper.add(legend.getCode());
                for (PollQuestionXlsDto question : legend.getQuestions()) {
                    if (PollAnswer.VASTUS_M.name().equals(question.getType()) || PollAnswer.VASTUS_S.name().equals(question.getType())) {
                        for (QuestionAnswerXlsDto answerDto : question.getAnswers()) {
                            Optional<PollAnswerXlsDto> answer = Optional.empty();
                            if (answers != null) {
                                answer = answers.stream().filter(p -> answerDto.getName().getId().equals(p.getAnswerId())).findFirst();
                            }
                            if (answer.isPresent()) {
                                mapper.add(answer.get().getWeight());
                            } else {
                                mapper.add("-");
                            }
                        }
                    } else {
                        Optional<PollAnswerXlsDto> answer = Optional.empty();
                        if (answers != null) {
                            answer = answers.stream().filter(p -> question.getName().getId().equals(p.getQuestionId())).findFirst();
                        }
                        if (answer.isPresent()) {
                            if (PollAnswer.VASTUS_T.name().equals(question.getType())) {
                                mapper.add(answer.get().getAnswerTxt());
                            } else {
                                if (answer.get().getWeight() == null) {
                                    mapper.add("-");
                                } else {
                                    mapper.add(answer.get().getWeight());
                                }
                            }
                        } else {
                            mapper.add("-");
                        }
                    }
                }
            }
            PollResponseXlsDto responseDto = new PollResponseXlsDto();
            responseDto.setQuestionAnswer(mapper);
            responseDto.setBasicData(response.getBasicData());
            dto.getResponses().add(responseDto);
        }
    }

    private void setTableHeader(PollXlsDto dto, HoisUserDetails user) {
        List<String> header = new ArrayList<>();
        SchoolType type = schoolService.schoolType(user.getSchoolId());
        header.add("poll.code");
        if (dto.getTargetStudent().booleanValue()) {
            header.add("poll.student.code");
            header.add("poll.student.studyform");
            header.add("poll.student.curriculum");
        }
        if (dto.getTargetTeacher().booleanValue()) header.add("poll.teacher.code");
        if (dto.getUsePersonCode().booleanValue()) header.add("poll.person.code");
        header.add("poll.other.code");
        if (dto.getTargetEnterprise().booleanValue()) header.add("poll.enterprise");
        if (PollType.KYSITLUS_O.name().equals(dto.getType())) {
            if (type.isHigher()) {
                if (type.isVocational()) {
                    header.add("poll.teacher.both");
                } else {
                    header.add("poll.teacher.higher");
                }
            } else {
                header.add("poll.teacher");
            }
            if (dto.getStudyPeriod() != null) {
                if (type.isHigher() && type.isVocational()) {
                    header.add("poll.subject.and.journal");
                } else {
                    header.add("poll.subject");
                }
                header.add("poll.subject.code");
            } else {
                header.add("poll.journal");
            }
        }
        List<String> headerResults = new ArrayList<>();
        for (PollResponseLegendXlsDto legend : dto.getLegend()) {
            headerResults.add("poll.theme.code");
            for (PollQuestionXlsDto question : legend.getQuestions()) {
                if (PollAnswer.VASTUS_M.name().equals(question.getType()) || PollAnswer.VASTUS_S.name().equals(question.getType())) {
                    headerResults.addAll(question.getAnswers().stream().map(p -> question.getCode() + "(" + p.getOrderNr() + ")").collect(Collectors.toList()));
                } else {
                    headerResults.add(question.getCode());
                }
            }
        }
        dto.setTableHeader(header);
        dto.setTableHeaderResults(headerResults);
    }

    private static void setXlsDtoTargets(List<PollTarget> pollTargets, PollXlsDto dto) {
        for (PollTarget target : pollTargets) {
            if (target.getTarget() != null) {
                String targetCode = target.getTarget().getCode();
                if (PollTargets.KYSITLUS_SIHT_O.name().equals(targetCode)) {
                    dto.setTargetStudent(Boolean.TRUE);
                    dto.setUsePersonCode(Boolean.TRUE);
                } else if (PollTargets.KYSITLUS_SIHT_T.name().equals(targetCode)) {
                    dto.setTargetTeacher(Boolean.TRUE);
                    dto.setUsePersonCode(Boolean.TRUE);
                } else if (PollTargets.KYSITLUS_SIHT_E.name().equals(targetCode)) {
                    dto.setTargetEnterprise(Boolean.TRUE);
                } else if (PollTargets.KYSITLUS_SIHT_A.name().equals(targetCode) || PollTargets.KYSITLUS_SIHT_L.name().equals(targetCode)) {
                    dto.setUsePersonCode(Boolean.TRUE);
                }
            }
        }
    }

    private void setLegend(PollXlsDto dto, PollResultStudentCommand criteria) {
        String SEARCH_SELECT = "pt.id as pollId, pt.name_et as pollEt, pt.name_en as pollEn, pt.order_nr as themeOrder,"
                + " ptq.order_nr as questionOrder, q.id as questionId, q.name_et as questionEt, q.name_en as questionEn,"
                + " q.type_code, qa.order_nr as answerOrder, qa.name_et as answerEt, qa.name_en as answerEn, qa.id as answerId,"
                + " qa.answer_nr as weight";
        
        String SEARCH_FROM = "from poll_theme pt "
                + "join poll_theme_question ptq on ptq.poll_theme_id = pt.id "
                + "join question q on q.id = ptq.question_id "
                + "left join question_answer qa on qa.question_id = q.id";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("pt.poll_id = :pollId", "pollId", criteria.getPollId());
        boolean isRepetitive = PollType.KYSITLUS_O.name().equals(dto.getType());
        if (isRepetitive && (criteria.getJournalId() != null || criteria.getSubjectStudyPeriodId() != null)) {
            qb.filter("pt.is_repetitive = true");
        }
        List<?> questions = qb.sort("pt.order_nr, ptq.order_nr, qa.order_nr").select(SEARCH_SELECT, em).getResultList();
        List<PollResponseLegendXlsDto> legend = new ArrayList<>();
        for (Object r : questions) {
            Long themeId = resultAsLong(r, 0);
            Optional<PollResponseLegendXlsDto> legendThemeOpt = legend.stream().filter(p -> p.getName().getId().equals(themeId)).findFirst();
            PollResponseLegendXlsDto legendTheme;
            if (legendThemeOpt.isPresent()) {
                legendTheme = legendThemeOpt.get();
            } else {
                legendTheme = new PollResponseLegendXlsDto();
                legendTheme.setName(new AutocompleteResult(themeId, resultAsString(r, 1), resultAsString(r, 2)));
                legendTheme.setCode("T" + resultAsLong(r, 3));
                legend.add(legendTheme);
            }
            Long questionId = resultAsLong(r, 5);
            Optional<PollQuestionXlsDto> questionOpt = legendTheme.getQuestions().stream().filter(p -> p.getName().getId().equals(questionId)).findFirst();
            PollQuestionXlsDto question;
            if (questionOpt.isPresent()) {
                question = questionOpt.get();
            } else {
                question = new PollQuestionXlsDto();
                question.setCode("K" + resultAsLong(r, 3) + resultAsLong(r, 4));
                question.setName(new AutocompleteResult(questionId, resultAsString(r, 6), resultAsString(r, 7)));
                question.setType(resultAsString(r, 8));
                legendTheme.getQuestions().add(question);
            }
            AutocompleteResult answer = new AutocompleteResult(resultAsLong(r, 12), resultAsString(r, 10), resultAsString(r, 11));
            QuestionAnswerXlsDto answerDto = new QuestionAnswerXlsDto();
            answerDto.setName(answer);
            answerDto.setOrderNr(resultAsLong(r, 9));
            answerDto.setWeight(resultAsLong(r, 13));
            question.getAnswers().add(answerDto);
        }
        dto.setLegend(legend);
    }

    public Page<AutocompleteResult> pollResultEnterprises(Long schoolId, Poll poll, Pageable pageable) {
        Long pollId = EntityUtil.getId(poll);
        
        String SEARCH_SELECT = "distinct ccss.enterpriseId, ccss.name";
        String SEARCH_FROM = "from response r "
                + "join response_object ro on r.id = ro.response_id "
                + "join poll p on p.id = r.poll_id "
                + "join poll_target pt on pt.id = ro.poll_target_id "
                + "join (select e.id as enterpriseId, cs.id as supervisorId, e.name "
                    + "from contract_supervisor cs "
                    + "join contract c on c.id = cs.contract_id "
                    + "join enterprise e on c.enterprise_id = e.id) ccss on ccss.supervisorId = ro.contract_supervisor_id";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        qb.requiredCriteria("p.id = :pollId", "pollId", pollId);
        qb.requiredCriteria("p.school_id = :schoolId", "schoolId", schoolId);
        qb.filter("pt.target_code = '" + PollTargets.KYSITLUS_SIHT_E.name() + "'");
        qb.filter("(r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_P.name() + "' or r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_V.name() + "')");
        qb.filter("exists(select rqa.id from response_question_answer rqa where rqa.response_id = r.id)");
        return JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable).map(r -> new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 1))); 
    }

    @SuppressWarnings("unused")
    public PollResultStudentOrTeacherDto pollResultStudentOrTeacher(HoisUserDetails user, Poll poll) {
        Long pollId = EntityUtil.getId(poll);
        
        Integer studentResponses = getResponsesByTargetCode(PollTargets.KYSITLUS_SIHT_O, pollId);
        
        Integer teacherResponses = getResponsesByTargetCode(PollTargets.KYSITLUS_SIHT_T, pollId);
        
        PollResultStudentOrTeacherDto dto = new PollResultStudentOrTeacherDto();
        Optional<PollTarget> target = poll.getPollTargets().stream()
                .filter(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_O, p.getTarget())).findFirst();
        if (target.isPresent()) {
            PollResultTargetDto studentDto = new PollResultTargetDto();
            studentDto.setCurrentResponses(studentResponses);
            studentDto.setMaxResponses(target.get().getTargetCount());
            dto.setStudentResponse(studentDto);
        }
        target = poll.getPollTargets().stream()
                .filter(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_T, p.getTarget())).findFirst();
        if (target.isPresent()) {
            PollResultTargetDto teacherDto = new PollResultTargetDto();
            teacherDto.setCurrentResponses(teacherResponses);
            teacherDto.setMaxResponses(target.get().getTargetCount());
            dto.setTeacherResponse(teacherDto);
        }
        return dto;
    }
    
    private Integer getResponsesByTargetCode(PollTargets target, Long pollId) {
        String SEARCH_SELECT = "count(distinct r.id)";
        String SEARCH_FROM = "from response r "
                + "join response_object ro on r.id = ro.response_id "
                + "join poll_target pt on pt.id = ro.poll_target_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);        
        qb.requiredCriteria("r.poll_id = :pollId", "pollId", pollId);
        qb.requiredCriteria("pt.target_code = :targetCode", "targetCode", target.name());
        qb.requiredCriteria("r.status_code in :statusCodes", "statusCodes", 
                Arrays.asList(ResponseStatus.KYSITVASTUSSTAATUS_P.name(), ResponseStatus.KYSITVASTUSSTAATUS_V.name()));
        qb.filter("exists(select rqa.id from response_question_answer rqa where rqa.response_id = r.id)");
        Object count = qb.select(SEARCH_SELECT, em).getSingleResult();
        return JpaQueryUtil.resultAsInteger(count, 0);
    }

    /**
     * Used as a job to change poll status to finished
     * Poll valid thru needs to be in the past
     */
    public void checkPollValidThru() {
        List<Poll> polls = em.createQuery("select p from Poll p "
                + "where p.status.code = ?1 "
                + "and p.validThru < ?2", Poll.class)
                .setParameter(1, PollStatus.KYSITLUS_STAATUS_K.name())
                .setParameter(2, LocalDate.now())
                .getResultList();
        for (Poll poll : polls) {
            poll.setStatus(EntityUtil.getOptionalOne(PollStatus.KYSITLUS_STAATUS_L.name(), em));
            EntityUtil.save(poll, em);
        }
    }
    
    public AllPollResultsDto allPollResults(Poll poll, PollResultCommand command) {
        String SEARCH_SELECT = "pt.id as themeId, pt.name_et as themeEt, pt.name_en as themeEn, pt.order_nr as themeOrder, "
                             + "q.id as questionId, q.name_et as questionEt, ptq.order_nr as questionOrder, q.type_code as questionType, "
                             + "qa.id as answerId, qa.name_et as answerEt, qa.name_en as answerEn, qa.order_nr as answerOrder, qa.answer_nr as answerNr, "
                             + "A1.answerCount as answerCount, A1.answers as answers, A2.responses as responseCount, A2.average as averageWeight, "
                             + "SUBJECT_JOURNAL.journalId, SUBJECT_JOURNAL.journalEt, SUBJECT_JOURNAL.subjectId, "
                             + "SUBJECT_JOURNAL.subjectCode || ' - ' || SUBJECT_JOURNAL.subjectEt || ' (' || SUBJECT_JOURNAL.teacherNames || ')' as subjectEt , "
                             + "SUBJECT_JOURNAL.subjectCode || ' - ' || SUBJECT_JOURNAL.subjectEn || ' (' || SUBJECT_JOURNAL.teacherNames || ')' as subjectEn , "
                             + "SUBJECT_JOURNAL.teacherId as teacherId, SUBJECT_JOURNAL.teacherName, SUBJECT_JOURNAL.sspId";
        String SEARCH_FROM = "FROM poll p " +
                "JOIN poll_theme pt on pt.poll_id = p.id " + 
                "JOIN poll_theme_question ptq ON ptq.poll_theme_id = pt.id " + 
                "JOIN question q ON ptq.question_id = q.id " + 
                "LEFT JOIN question_answer qa ON qa.question_id = q.id " + 
                "LEFT JOIN (SELECT r.poll_id, s.id AS subjectId,j.id AS journalId, j.name_et AS journalEt, s.name_et AS subjectEt, s.name_en AS subjectEn, s.code as subjectCode, " + 
                    "rqa.teacher_id AS teacherId, concat(p.firstname, ' ', p.lastname) AS teacherName, rqa.question_id, rqa.question_answer_id, ssp.id as sspId, " +
                    "string_agg(distinct concat(pp.firstname, ' ', pp.lastname), ', ') as teacherNames " + 
                    "FROM response r JOIN response_subject rs ON rs.response_id = r.id " +
                    "LEFT JOIN journal j ON rs.journal_id = j.id " + 
                    "LEFT JOIN subject_study_period ssp on rs.subject_study_period_id = ssp.id " +
                    "LEFT JOIN subject s ON ssp.subject_id = s.id " +
                    "LEFT JOIN subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id " +
                    "LEFT JOIN teacher tt on sspt.teacher_id = tt.id " +
                    "LEFT JOIN person pp on pp.id = tt.person_id " +
                    "JOIN response_question_answer rqa ON rqa.response_subject_id = rs.id " + 
                    "LEFT JOIN teacher t ON rqa.teacher_id = t.id " + 
                    "LEFT JOIN person p ON t.person_id = p.id " + 
                    "GROUP BY r.poll_id, s.id, j.id, j.name_et, s.name_et, s.name_en, rqa.teacher_id, rqa.question_id, rqa.question_answer_id, ssp.id, " + 
                    "concat (p.firstname, ' ', p.lastname)) SUBJECT_JOURNAL ON " +
                    "subject_journal.question_id=q.id and (SUBJECT_JOURNAL.question_answer_id = qa.id OR q.type_code = 'VASTUS_T') and (SUBJECT_JOURNAL.poll_id = pt.poll_id AND pt.is_repetitive = TRUE) " + 
                    "and((coalescE(pt.is_teacher,false)=false and subject_journal.teacherId is null) " +
                    "or (pt.is_teacher=true and subject_journal.teacherId is not null)) " + 
                "LEFT JOIN (SELECT COUNT (rqa.id) AS answerCount, ARRAY_AGG (rqa.answer_txt) AS answers, r.poll_id, rqa.question_id, rs.subject_study_period_id as sspId, " + 
                    "rqa.question_answer_id, rs.journal_id, rs.subject_id, rqa.teacher_id AS teacherId " + 
                    "FROM response_question_answer rqa " + 
                    "JOIN response r ON rqa.response_id = r.id " + 
                    "LEFT JOIN response_subject rs ON (rs.response_id = r.id AND rqa.response_subject_id = rs.id) " +
                    "GROUP BY r.poll_id, rqa.question_id, rqa.question_answer_id, rs.journal_id, rs.subject_id, rqa.teacher_id, rs.subject_study_period_id) A1 " +
                    "ON A1.question_id = q. ID AND A1.poll_id = pt.poll_id " + 
                    "AND (A1.question_answer_id = qa.id OR q.type_code = 'VASTUS_T') " + 
                    "AND (((coalesce(pt.is_repetitive,false)=false or p.type_code != 'KYSITLUS_O') and a1.sspId is null and a1.journal_id is null) " +
                        "or (coalesce(pt.is_repetitive,false)=true and coalesce(pt.is_teacher,false)=false and subject_journal.question_id=q.id " +
                        "and (A1.journal_id = SUBJECT_JOURNAL.journalId OR A1.sspId = SUBJECT_JOURNAL.sspId)) " +
                        "or (coalesce(pt.is_repetitive,false)=true and pt.is_teacher=true and subject_journal.question_id=q.id " +
                        "and (A1.journal_id = SUBJECT_JOURNAL.journalId OR A1.sspId = SUBJECT_JOURNAL.sspId) " +
                        "and A1.teacherId = SUBJECT_JOURNAL.teacherId)) " + 
                "LEFT JOIN (SELECT COUNT (DISTINCT r.id) AS responses, CAST (AVG (rqa.answer_nr) AS DECIMAL (10, 2)) AS average, " +
                    "r.poll_id, rqa.question_id, rs.subject_id, rs.journal_id, rqa.teacher_id as teacherId, rs.subject_study_period_id as sspId " + 
                    "FROM response_question_answer rqa JOIN response r ON rqa.response_id = r.id " +
                    "LEFT JOIN response_subject rs ON ( rs.response_id = r.id AND rqa.response_subject_id = rs.id) " +
                    "GROUP BY r.poll_id, rqa.question_id, rs.journal_id, rs.subject_id, rqa.teacher_id, rs.subject_study_period_id) A2 ON " +
                    "A2.question_id = q.id " +
                    "AND A2.poll_id = pt.poll_id " +       
                    "AND (((coalesce(pt.is_repetitive,false)=false or p.type_code != 'KYSITLUS_O') and a2.subject_id is null and a2.journal_id is null) or " +
                    "(coalesce(pt.is_repetitive,false)=true and coalescE(pt.is_teacher,false)=false and subject_journal.question_id=q.id " +
                    "and (A2.journal_id = SUBJECT_JOURNAL.journalId OR A2.sspId = SUBJECT_JOURNAL.sspId)) or " +
                    "(coalesce(pt.is_repetitive,false)=true and pt.is_teacher=true and subject_journal.question_id=q.id " +
                    "and (A2.journal_id = SUBJECT_JOURNAL.journalId OR A2.sspId = SUBJECT_JOURNAL.sspId) " +
                    "and A2.teacherId = SUBJECT_JOURNAL.teacherId))";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("p.id = :pollId", "pollId", EntityUtil.getId(poll));
        qb.optionalCriteria("SUBJECT_JOURNAL.sspId = :sspId", "sspId", command.getSubjectStudyPeriodId());
        qb.optionalCriteria("SUBJECT_JOURNAL.journalId = :journalId", "journalId", command.getJournalId());
        // Order and scalar
        SQLQuery query = getArrayTypeQuery(qb, SEARCH_SELECT);
        List<?> answers = query.list();
        LinkedHashMap<Long, PollThemeResultDto> processedThemes = mapAnswerToTheme(answers);
        mapAllResultsThemes(processedThemes, poll);
        return new AllPollResultsDto(new ArrayList<>(processedThemes.values()));
    }

    private static LinkedHashMap<Long, PollThemeResultDto> mapAnswerToTheme(List<?> answers) {
        LinkedHashMap<Long, PollThemeResultDto> processedThemes = new LinkedHashMap<>();
        for (Object r : answers) {
            Long themeId = resultAsLong(r, 0);
            Long journalId = resultAsLong(r, 17);
            Long subjectStudyPeriodId = resultAsLong(r, 24);
            Long teacherId = resultAsLong(r, 22);
            // Text answers arent related to question answer
            List<String> answersText = JpaQueryUtil.resultAsStringList(r, 14);
            PollThemeResultDto themeResult;
            if (!processedThemes.containsKey(themeId)) {
                themeResult = new PollThemeResultDto();
                themeResult.setTheme(new AutocompleteWithOrder(themeId, resultAsString(r, 1), resultAsString(r, 2), resultAsLong(r, 3)));
                processedThemes.put(themeId, themeResult);
            } else {
                themeResult = processedThemes.get(themeId);
            }
            if (journalId != null && (themeResult.getJournal().isEmpty() || themeResult.getJournal().stream().noneMatch(p -> journalId.equals(p.getId())))) {
                AutocompleteSubjectOrJournal newJournal = new AutocompleteSubjectOrJournal(journalId, resultAsString(r, 18), resultAsString(r, 18));
                themeResult.getJournal().add(newJournal);
            } else if (subjectStudyPeriodId != null && (themeResult.getSubject().isEmpty() || themeResult.getSubject().stream().noneMatch(p -> subjectStudyPeriodId.equals(p.getId())))) {
                AutocompleteSubjectOrJournal newSubject = new AutocompleteSubjectOrJournal(subjectStudyPeriodId, resultAsString(r, 20), resultAsString(r, 21));
                themeResult.getSubject().add(newSubject);
            }
            Optional<AutocompleteSubjectOrJournal> subjectOrJournal = Optional.empty();
            if (journalId != null) {
                subjectOrJournal = themeResult.getJournal().stream().filter(p -> journalId.equals(p.getId())).findFirst();
            } else if (subjectStudyPeriodId != null) {
                subjectOrJournal = themeResult.getSubject().stream().filter(p -> subjectStudyPeriodId.equals(p.getId())).findFirst();
            }
            if (subjectOrJournal.isPresent()) {
                if (teacherId != null && subjectOrJournal.get().getTeachers().stream().noneMatch(p -> teacherId.equals(p.getId()))) {
                    subjectOrJournal.get().getTeachers().add(new AutocompleteTeacher(teacherId, resultAsString(r, 23), resultAsString(r, 23)));
                } else if (teacherId == null && subjectOrJournal.get().getTeachers().stream().noneMatch(p -> p.getId() == null)) {
                    subjectOrJournal.get().getTeachers().add(new AutocompleteTeacher());
                }
            }
            Long questionId = resultAsLong(r, 4);
            if (questionId == null) continue;
            PollQuestionResultDto questionResult;
            Optional<PollQuestionResultDto> optionalQuestion = Optional.empty(); 
            if (journalId != null) {
                optionalQuestion = themeResult.getJournal().stream()
                        .filter(p -> journalId.equals(p.getId()))
                        .flatMap(p -> p.getTeachers().stream())
                        .filter(p -> (teacherId != null && teacherId.equals(p.getId())) || (teacherId == null && p.getId() == null))
                        .flatMap(p -> p.getQuestions().stream())
                        .filter(p -> questionId.equals(p.getQuestion().getId()))
                        .collect(Collectors.toList()).stream().filter(p -> questionId.equals(p.getQuestion().getId())).findFirst();
            } else if (subjectStudyPeriodId != null) {
                optionalQuestion = themeResult.getSubject().stream()
                        .filter(p -> subjectStudyPeriodId.equals(p.getId()))
                        .flatMap(p -> p.getTeachers().stream())
                        .filter(p -> (teacherId != null && teacherId.equals(p.getId())) || (teacherId == null && p.getId() == null))
                        .flatMap(p -> p.getQuestions().stream())
                        .filter(p -> questionId.equals(p.getQuestion().getId()))
                        .collect(Collectors.toList()).stream().filter(p -> questionId.equals(p.getQuestion().getId())).findFirst();
            } else {
                optionalQuestion = themeResult.getQuestions().stream()
                        .filter(p -> p.getQuestion().getId().equals(questionId)).findFirst();
            }
            if (optionalQuestion.isPresent()) {
                questionResult = optionalQuestion.get();
            } else {
                questionResult = new PollQuestionResultDto();
                String questionName = resultAsString(r, 5);
                questionResult.setQuestion(new AutocompleteWithOrder(questionId, questionName, questionName, resultAsLong(r, 6)));
                questionResult.setType(resultAsString(r, 7));
                if (journalId != null) {
                    Optional<AutocompleteSubjectOrJournal> journal = themeResult.getJournal().stream().filter(p -> journalId.equals(p.getId())).findFirst();
                    if (journal.isPresent()) {
                        Optional<AutocompleteTeacher> teacher = journal.get().getTeachers().stream()
                                .filter(p-> (teacherId != null && teacherId.equals(p.getId())) || (teacherId == null && p.getId() == null)).findFirst();
                        if (teacher.isPresent()) {
                            teacher.get().getQuestions().add(questionResult);
                        }
                    }
                } else if (subjectStudyPeriodId != null) {
                    Optional<AutocompleteSubjectOrJournal> subject = themeResult.getSubject().stream().filter(p -> subjectStudyPeriodId.equals(p.getId())).findFirst();
                    if (subject.isPresent()) {
                        Optional<AutocompleteTeacher> teacher = subject.get().getTeachers().stream()
                                .filter(p-> (teacherId != null && teacherId.equals(p.getId())) || (teacherId == null && p.getId() == null)).findFirst();
                        if (teacher.isPresent()) {
                            teacher.get().getQuestions().add(questionResult);
                        }
                    }
                } else {
                    themeResult.getQuestions().add(questionResult);
                }
            }
            Long answerId = resultAsLong(r, 8);
            if (PollAnswer.VASTUS_T.name().equals(questionResult.getType())) {
                if (answersText != null) {
                    for (String answer : answersText) {
                        PollAnswerResultDto textAnswer = new PollAnswerResultDto();
                        textAnswer.setAnswers(resultAsLong(r, 13));
                        textAnswer.setAnswerTxt(answer);
                        questionResult.getAnswers().add(textAnswer);
                    }
                }
            } else {
                PollAnswerResultDto answerResult = new PollAnswerResultDto(answerId, resultAsString(r, 9), resultAsString(r, 10), resultAsLong(r, 11));
                answerResult.setWeight(resultAsLong(r, 12));
                Long answersCount = resultAsLong(r, 13);
                if (answersCount != null) {
                    questionResult.setAllAnswers(Long.valueOf(questionResult.getAllAnswers().longValue() + answersCount.longValue()));
                    answerResult.setAnswers(answersCount);
                }
                Long individualAnswers = resultAsLong(r, 15);
                questionResult.setIndividualAnswers(individualAnswers != null ? individualAnswers : Long.valueOf(0));
                questionResult.setAverage(JpaQueryUtil.resultAsDecimal(r, 16));
                questionResult.getAnswers().add(answerResult);
            }
        }
        return processedThemes;
    }

    /**
     * Used to add missing answers and questions
     * @param processedThemes
     */
    private static void mapAllResultsThemes(HashMap<Long, PollThemeResultDto> processedThemes, Poll poll) {
        List<ThemeDto> themes = getPollThemes(poll);
        for (ThemeDto theme : themes) {
            PollThemeResultDto graphTheme = processedThemes.get(theme.getId());
            if (graphTheme == null) continue;
            List<QuestionDto> questions = theme.getQuestions();
            for (QuestionDto question : questions) {
                for (AutocompleteSubjectOrJournal subject : graphTheme.getSubject()) {
                    mapUnAnswered(question, subject, questions);
                }
                for (AutocompleteSubjectOrJournal journal : graphTheme.getJournal()) {
                    mapUnAnswered(question, journal, questions);
                }
            }
        }
    }

    private static void mapUnAnswered(QuestionDto question, AutocompleteSubjectOrJournal subject, List<QuestionDto> questions) {
        for (AutocompleteTeacher teacher : subject.getTeachers()) {
            Optional<PollQuestionResultDto> graphQuestionOptional = teacher.getQuestions().stream()
                    .filter(p -> p.getQuestion() != null && question.getQuestion().equals(p.getQuestion().getId()))
                    .findFirst();
            PollQuestionResultDto graphQuestion;
            if (graphQuestionOptional.isPresent()) {
                graphQuestion = graphQuestionOptional.get();
                for (QuestionAnswerDto answer : question.getAnswers()) {
                    if (graphQuestion.getAnswers().stream().noneMatch(p -> answer.getId().equals(p.getId()))) {
                        PollAnswerResultDto newAnswer = new PollAnswerResultDto(answer.getId(), answer.getNameEt(), answer.getNameEt(), Long.valueOf(answer.getOrderNr().longValue()));
                        newAnswer.setPercentage(Double.valueOf(0));
                        newAnswer.setWeight(Long.valueOf(answer.getAnswerNr().longValue()));
                        graphQuestion.getAnswers().add(question.getAnswers().indexOf(answer), newAnswer);
                    }
                }
            } else {
                graphQuestion = new PollQuestionResultDto();
                graphQuestion.setAllAnswers(Long.valueOf(0));
                graphQuestion.setAverage(BigDecimal.ZERO);
                graphQuestion.setIndividualAnswers(Long.valueOf(0));
                graphQuestion.setQuestion(new AutocompleteWithOrder(question.getQuestion(), question.getNameEt(), question.getNameEn(), Long.valueOf(question.getOrderNr().longValue())));
                graphQuestion.setType(question.getType());
                List<PollAnswerResultDto> answers = question.getAnswers().stream().map(p -> {
                   PollAnswerResultDto answer = new PollAnswerResultDto(p.getId(), p.getNameEt(), p.getNameEt(), Long.valueOf(p.getOrderNr().longValue()));
                   answer.setPercentage(Double.valueOf(0));
                   answer.setWeight(Long.valueOf(p.getAnswerNr().longValue()));
                   return answer;
                }).collect(Collectors.toList());
                graphQuestion.setAnswers(answers);
                teacher.getQuestions().add(questions.indexOf(question), graphQuestion);
            }
        }
    }

    private SQLQuery getArrayTypeQuery(JpaNativeQueryBuilder qb, String SEARCH_SELECT) {
        Type arrayType = new CustomType(new ArrayUserType());
        return qb.sort("pt.order_nr, subject_journal.subjectet, subject_journal.journalet, subject_journal.teachername, ptq.order_nr, qa.order_nr")
        .select(SEARCH_SELECT, em).unwrap(SQLQuery.class)
        .addScalar("themeId")
        .addScalar("themeEt")
        .addScalar("themeEn")
        .addScalar("themeOrder")
        .addScalar("questionId")
        .addScalar("questionEt")
        .addScalar("questionOrder")
        .addScalar("questionType")
        .addScalar("answerId")
        .addScalar("answerEt")
        .addScalar("answerEn")
        .addScalar("answerOrder")
        .addScalar("answerNr")
        .addScalar("answerCount")
        .addScalar("answers", arrayType)
        .addScalar("responseCount")
        .addScalar("averageWeight")
        .addScalar("journalId")
        .addScalar("journalEt")
        .addScalar("subjectId")
        .addScalar("subjectEt")
        .addScalar("subjectEn")
        .addScalar("teacherId")
        .addScalar("teacherName")
        .addScalar("sspId");
    }
}
