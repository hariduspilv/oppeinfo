package ee.hitsa.ois.service;

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
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.ContractSupervisor;
import ee.hitsa.ois.domain.Declaration;
import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.PracticeJournal;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.poll.Poll;
import ee.hitsa.ois.domain.poll.PollJournal;
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
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.message.ContractSupervisorMessage;
import ee.hitsa.ois.message.PollReminderMessage;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
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
import ee.hitsa.ois.web.dto.poll.AnswersDto;
import ee.hitsa.ois.web.dto.poll.DatasetOverride;
import ee.hitsa.ois.web.dto.poll.GraphDto;
import ee.hitsa.ois.web.dto.poll.GraphInfoDto;
import ee.hitsa.ois.web.dto.poll.GraphOptions;
import ee.hitsa.ois.web.dto.poll.GraphSearchDto;
import ee.hitsa.ois.web.dto.poll.PollPracticeJournalDto;
import ee.hitsa.ois.web.dto.poll.PollRelatedObjectsDto;
import ee.hitsa.ois.web.dto.poll.PollSearchDto;
import ee.hitsa.ois.web.dto.poll.PollTypeDto;
import ee.hitsa.ois.web.dto.poll.PracticeThemesDto;
import ee.hitsa.ois.web.dto.poll.QuestionAnswerDto;
import ee.hitsa.ois.web.dto.poll.QuestionDto;
import ee.hitsa.ois.web.dto.poll.QuestionPollSearchDto;
import ee.hitsa.ois.web.dto.poll.QuestionResponsePairDto;
import ee.hitsa.ois.web.dto.poll.QuestionSearchDto;
import ee.hitsa.ois.web.dto.poll.ResponseDto;
import ee.hitsa.ois.web.dto.poll.SubjectOrJournalDto;
import ee.hitsa.ois.web.dto.poll.SubjectAnswerDto;
import ee.hitsa.ois.web.dto.poll.SubjectCommentDto;
import ee.hitsa.ois.web.dto.poll.ThemeDto;
import ee.hitsa.ois.web.dto.poll.ThemesDto;
import ee.hitsa.ois.web.dto.poll.TitleOptions;

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
        if (pollForm.getStudyPeriod() != null) changedPoll.setStudyPeriod(em.getReference(StudyPeriod.class, pollForm.getStudyPeriod()));
        if (pollForm.getType() != null && PollType.KYSITLUS_LIST.contains(pollForm.getType())) {
            changedPoll.setType(em.getReference(Classifier.class, pollForm.getType()));
        }
        if (pollForm.getStatus() != null && PollStatus.KYSITLUS_STAATUS_LIST.contains(pollForm.getStatus())) {
            changedPoll.setStatus(em.getReference(Classifier.class, pollForm.getStatus()));
        }
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
        
        EntityUtil.bindEntityCollection(poll.getPollJournals(), p -> EntityUtil.getId(p.getJournal()),
                pollForm.getJournals(), p -> p.getId(), dto -> {
                    PollJournal pollJournal = new PollJournal();
                    pollJournal.setPoll(changedPoll);
                    pollJournal.setJournal(em.getReference(Journal.class, dto.getId()));
                    return pollJournal;
                });
        
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
        changedPoll.setSchool(em.getReference(School.class, user.getSchoolId()));
        changedPoll.setNameEn(pollForm.getNameEt());
        if (pollForm.getStudyPeriod() == null) {
            changedPoll.setStudyPeriod(null);
        }
        return convertToForm(EntityUtil.save(changedPoll, em));
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
        List<AutocompleteResult> journals = new ArrayList<>();
        for (PollJournal pollJournal : poll.getPollJournals()) {
            journals.add(AutocompleteResult.of(pollJournal));
        }
        form.setJournals(journals);
        if (poll.getSchool() != null) {
            form.setStatus(poll.getStatus().getCode());
        }
        form.setThemes(poll.getPollThemes().stream().filter(p -> p.getPollThemeQuestions() != null && !p.getPollThemeQuestions().isEmpty()).count());
        form.setThemeEmpty(Boolean.valueOf(poll.getPollThemes().stream().anyMatch(p -> p.getPollThemeQuestions() == null || p.getPollThemeQuestions().isEmpty())));
        if (poll.getStudyPeriod() != null) form.setStudyPeriod(EntityUtil.getId(poll.getStudyPeriod()));
        form.setResponded(Boolean.valueOf(!poll.getResponses().isEmpty()));
        return form;
    }

    public Page<PollSearchDto> search(HoisUserDetails user, PollSearchCommand command, Pageable pageable) {
        String SEARCH_FROM = "from poll p "
                + "left join poll_target pt on (pt.poll_id = p.id) ";
        String SEARCH_SELECT = "p.id, p.name_et, p.type_code, string_agg(pt.target_code, ';') as targetcodes, p.valid_from, p.valid_thru, p.status_code, p.inserted_by, p.changed_by ";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        qb.optionalContains("p.name_et", "name", command.getPollName());
        qb.optionalCriteria("p.status_code = :statusCode", "statusCode", command.getStatusCode());
        qb.optionalCriteria(":targetCode in (select pot.target_code from poll po join poll_target pot on pot.poll_id = po.id where po.id = p.id)", "targetCode", command.getTargetCode());
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
            if (codes != null) {
                dto.setTargetCodes(Arrays.asList(codes.split(";")));
            }
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
                + "join poll_target pt on pt.id = ro.poll_target_id";
        String SEARCH_SELECT = "p.id, p.name_et, p.name_en, p.type_code, p.valid_from, p.valid_thru, r.status_code, r.id as responseId,"
                + " p.is_theme_pageable, p.is_teacher_comment, p.is_teacher_comment_visible, p.is_student_visible";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        qb.optionalContains("p.name_et", "name", command.getPollName());
        qb.optionalCriteria("pt.target_code = :targetCode", "targetCode", getTargetCode(user));
        qb.optionalCriteria("p.type_code = :typeCode", "typeCode", command.getTypeCode());
        qb.optionalCriteria("p.valid_thru <= :validThru", "validThru", command.getValidThru());
        qb.requiredCriteria("p.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("ro.person_id = :personId", "personId", user.getPersonId());
        qb.filter("(r.status_code = 'KYSITVASTUSSTAATUS_P' or r.status_code = 'KYSITVASTUSSTAATUS_V')");
        qb.groupBy("p.id, p.name_et, p.name_en, p.type_code, p.valid_from, p.valid_thru, r.status_code, r.id, p.is_theme_pageable,"
                + " p.is_teacher_comment, p.is_teacher_comment_visible, p.is_student_visible");

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
            return dto; 
        });
    }
    
    public Page<SubjectAnswerDto> searchAnswersPerSubject(HoisUserDetails user, PollSearchCommand command, Pageable pageable) {
        boolean isHigherSchool = schoolService.schoolType(user.getSchoolId()).isHigher();
        String SEARCH_SELECT = "p.id, p.valid_from, p.valid_thru," +
                (isHigherSchool ? " s.id as subjectId, s.name_et as subjectEt, s.name_en as subjectEn, s.code," : 
                    " jj.id as journalId, jj.name_et as journalEt, jj.name_et as journalEn, jj.name_et as filler,") +
                    " p.name_et as pollNameEt, p.name_en as pollNameEn, cl.name_et as classifierEt, cl.name_en as classifierEn"
                    + (isHigherSchool ? ",sp.id as studyPeriodId, ssp.id as subjectStudyPeriodId" : "");
        String SEARCH_FROM = "from poll p " + 
                (isHigherSchool ? 
                "join study_period sp on p.study_period_id = sp.id " + 
                "join subject_study_period ssp on sp.id=ssp.study_period_id " + 
                "join subject_study_period_teacher st on (ssp.id=st.subject_study_period_id and st.teacher_id=" + user.getTeacherId() + ") " + 
                "join subject s on ssp.subject_id = s.id " + 
                "left join classifier cl on cl.code = sp.type_code":
                "join poll_journal pj on p.id=pj.poll_id " + 
                "join journal_teacher jt on (pj.journal_id=jt.journal_id and jt.teacher_id=" + user.getTeacherId() + ") " + 
                "join journal jj on jt.journal_id=jj.id " + 
                "join study_year sy on jj.study_year_id=sy.id " + 
                "left join classifier cl on cl.code = sy.year_code"); 
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        qb.optionalContains("p.name_et", "nameEt", command.getPollName());
        qb.requiredCriteria("p.type_code = :typeCode", "typeCode", PollType.KYSITLUS_O.name());
        qb.requiredCriteria("p.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.filter("exists( select 1 " + 
                "from response r " + 
                "join response_object ro on ro.response_id = r.id " +
                "and (r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_P.name() + "' " +
                "or r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_V.name() + "') " + 
                "join response_subject rs  on rs.response_id = r.id " + 
                (isHigherSchool ? "join declaration dd on dd.study_period_id=sp.id and dd.student_id=ro.student_id " + 
                        "join declaration_subject ds on dd.id=ds.declaration_id and ds.subject_study_period_id=ssp.id " : "") +
                "where r.poll_id = p.id and " + (isHigherSchool ? "rs.subject_id=s.id" : "rs.journal_id=pj.journal_id") + ")");

        return JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable).map(r -> {
            SubjectAnswerDto dto = new SubjectAnswerDto();
            if (isHigherSchool) {
                dto.setName(new AutocompleteResult(resultAsLong(r, 3), resultAsString(r, 4) + " (" + resultAsString(r, 6) + ")", resultAsString(r, 5) + " (" + resultAsString(r, 6) + ")"));
            } else {
                dto.setName(new AutocompleteResult(resultAsLong(r, 3), resultAsString(r, 4), resultAsString(r, 5)));
            }
            String ANSWERS_SELECT = "count(r.id)";
            
            String ANSWERS_FROM = "from response r " + 
                    "join response_object ro on ro.response_id = r.id " +
                    "and (r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_P.name() + "' " +
                    "or r.status_code = '" + ResponseStatus.KYSITVASTUSSTAATUS_V.name() + "') " + 
                    "join response_subject rs  on rs.response_id = r.id " + 
                    (isHigherSchool ? "join declaration dd on dd.study_period_id=" + resultAsLong(r, 11) + " and dd.student_id=ro.student_id " + 
                            "join declaration_subject ds on dd.id=ds.declaration_id and ds.subject_study_period_id=" + resultAsLong(r, 12) + " " : "") +
                    "where r.poll_id = " + resultAsLong(r, 0) +  " and " + (isHigherSchool ? "rs.subject_id="  : "rs.journal_id=") + dto.getName().getId();
            
            JpaNativeQueryBuilder queryPerResult = new JpaNativeQueryBuilder(ANSWERS_FROM);
            List<?> dbAnswers = queryPerResult.select(ANSWERS_SELECT, em).getResultList();
            List<Long> themeAndQuestionList = StreamUtil
                    .toMappedList(a-> resultAsLong(a, 0), dbAnswers);
            dto.setAnswers(themeAndQuestionList.get(0));
            dto.setStartDate(JpaQueryUtil.resultAsLocalDate(r, 1));
            dto.setEndDate(JpaQueryUtil.resultAsLocalDate(r, 2));
            dto.setPoll(new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 7), resultAsString(r, 8)));
            dto.setYearCode(new AutocompleteResult(null, resultAsString(r, 9), resultAsString(r, 10)));
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
        if (command.getPollConnection() != null && command.getPollConnection().booleanValue()) {
            qb.filter("p.id is not null");
        }
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
        String SEARCH_SELECT = "p.id, p.name_et, p.type_code, string_agg(pt.target_code, ';') as targetcodes, p.valid_from, p.valid_thru, p.status_code, p.inserted_by, p.changed_by, p.is_theme_pageable ";
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
            if (codes != null) {
                dto.setTargetCodes(Arrays.asList(codes.split(";")));
            }
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
        pollTheme.setIsRepetitive(Boolean.TRUE);
        pollTheme.setPoll(poll);
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.save(pollTheme, em);
    }

    public ThemesDto themes(Poll poll) {
        List<PollTheme> pollThemes = poll.getPollThemes();
        List<ThemeDto> themeDtos = new ArrayList<>();
        for (PollTheme theme : pollThemes) {
            ThemeDto dto = new ThemeDto();
            bindQuestionsToThemes(theme, dto, null);
            themeDtos.add(dto);
        }
        Collections.sort(themeDtos);
        Boolean confirmed = Boolean.FALSE;
        if (poll.getStatus() != null && ClassifierUtil.equals(PollStatus.KYSITLUS_STAATUS_K, poll.getStatus())) {
            confirmed = Boolean.TRUE;
        }
        ThemesDto themesDto = new ThemesDto(themeDtos, confirmed, poll.getForeword(), poll.getAfterword());
        themesDto.setIsThemePageable(poll.getIsThemePageable());
        themesDto.setNameEt(poll.getNameEt());
        themesDto.setType(poll.getType().getCode());
        if (poll.getStudyPeriod() != null) {
            Set<Subject> subjects = getAllDistinctSubjects(poll.getStudyPeriod());
            themesDto.setSubjects(subjects.stream().map(AutocompleteResult::of).collect(Collectors.toSet()));
        }
        if (poll.getPollJournals() != null) {
            themesDto.setJournals(poll.getPollJournals().stream().map(p -> AutocompleteResult.of(p.getJournal())).collect(Collectors.toSet()));
        }
        return themesDto;   
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
    
    public void createQuestion(HoisUserDetails user, PollTheme pollTheme, QuestionCommand questionCommand) {
        PollThemeQuestion pollThemeQuestion = new PollThemeQuestion();
        pollThemeQuestion.setQuestion(new Question());
        pollThemeQuestion.setPollTheme(pollTheme);
        saveQuestion(user, questionCommand, pollThemeQuestion);
    }

    public void saveQuestion(HoisUserDetails user, QuestionCommand questionCommand, PollThemeQuestion pollThemeQuestion) {
        EntityUtil.setUsername(user.getUsername(), em);
        Question question = null;
        if (questionCommand.getQuestion() == null) {
            question = pollThemeQuestion.getQuestion();
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
            pollThemeQuestion.setQuestion(question);
        }
        EntityUtil.save(question, em);
        EntityUtil.bindToEntity(questionCommand, pollThemeQuestion);
        if (!pollThemeQuestion.getPollTheme().getId().equals(questionCommand.getTheme())) {
            pollThemeQuestion.setPollTheme(em.getReference(PollTheme.class, questionCommand.getTheme()));
            pollThemeQuestion.setOrderNr(Short.valueOf((short) (pollThemeQuestion.getPollTheme().getPollThemeQuestions().size() + 1)));
        }
        EntityUtil.save(pollThemeQuestion, em);
        // Set files
        EntityUtil.bindEntityCollection(pollThemeQuestion.getPollThemeQuestionFiles(), p -> p.getOisFile().getId(),
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

    public void updateQuestion(HoisUserDetails user, PollThemeQuestion pollThemeQuestion,
            QuestionCommand questionCommand) {
        saveQuestion(user, questionCommand, pollThemeQuestion);
    }

    public void confirm(HoisUserDetails user, Poll poll) {
        if (poll.getPollThemes() == null || poll.getPollThemes().size() == 0) {
            throw new HoisException("poll.basicData.confirmError");
        }
        for (PollTheme theme : poll.getPollThemes()) {
            if (theme.getPollThemeQuestions() == null || theme.getPollThemeQuestions().size() == 0) {
                throw new HoisException("poll.basicData.confirmError");
            }
        }
        poll.setStatus(em.getReference(Classifier.class, PollStatus.KYSITLUS_STAATUS_K.name()));
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.save(poll, em);
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

    public Set<AutocompleteResult> questions(HoisUserDetails user) {
        String SEARCH_FROM = "from question q join classifier c on q.type_code = c.code";
        String SEARCH_SELECT = "q.id, q.name_et, c.name_et as classifierName";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("q.school_id = :schoolId", "schoolId", user.getSchoolId());
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
        setStudentGroups(poll, pollCopy);
        setTargets(poll, pollCopy);
        if (pollCopy.getPollTargets().stream().anyMatch(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_V, p.getTarget()))) pollCopy.setPollUrl(generateUniqueUrl());
        setPollThemes(poll, pollCopy);
        return EntityUtil.save(pollCopy, em);
    }
    
    private static void setPollThemes(Poll poll, Poll pollCopy) {
        List<PollTheme> pollThemeCopies = new ArrayList<>();
        for (PollTheme theme : poll.getPollThemes()) {
            PollTheme newTheme = new PollTheme();
            newTheme.setPoll(pollCopy);
            newTheme.setNameEn(theme.getNameEn());
            newTheme.setNameEt(theme.getNameEt());
            newTheme.setOrderNr(theme.getOrderNr());
            newTheme.setIsRepetitive(theme.getIsRepetitive());
            // Set new PollThemeQuestions
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
                List<PollThemeQuestionFile> pollThemeQuestionFileCopies = new ArrayList<>();
                for (PollThemeQuestionFile pollThemeQuestionFile : pollThemeQuestion.getPollThemeQuestionFiles()) {
                    PollThemeQuestionFile newPollThemeQuestionFile = new PollThemeQuestionFile();
                    newPollThemeQuestionFile.setOisFile(pollThemeQuestionFile.getOisFile());
                    newPollThemeQuestionFile.setPollThemeQuestion(newPollThemeQuestion);
                    pollThemeQuestionFileCopies.add(newPollThemeQuestionFile);
                }
                newPollThemeQuestion.setPollThemeQuestionFiles(pollThemeQuestionFileCopies);
                newPollThemeQuestions.add(newPollThemeQuestion);
            }
            newTheme.setPollThemeQuestions(newPollThemeQuestions);
            pollThemeCopies.add(newTheme);
        }
        pollCopy.setPollThemes(pollThemeCopies);
    }

    private static void setStudentGroups(Poll poll, Poll pollCopy) {
        List<PollStudentGroup> studentGroupCopies = new ArrayList<>();
        for (PollStudentGroup pollStudentGroup : poll.getPollStudentGroups()) {
            PollStudentGroup studentGroupCopy = new PollStudentGroup();
            studentGroupCopy.setPoll(pollCopy);
            studentGroupCopy.setStudentGroup(pollStudentGroup.getStudentGroup());
            studentGroupCopies.add(studentGroupCopy);
        }
        pollCopy.setPollStudentGroups(studentGroupCopies);
    }
    
    private static void setTargets(Poll poll, Poll pollCopy) {
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
                    + "where pj.endDate between ?1 and ?2", PracticeJournal.class)
                    .setParameter(1, poll.getJournalFrom())
                    .setParameter(2, poll.getJournalThru())
                    .getResultList();
            boolean isOverall = ClassifierUtil.equals(PollType.KYSITLUS_Y, poll.getType());
            List<String> sentEmails = new ArrayList<>();
            for (PracticeJournal journal : practiceJournals) {
                Contract contract = journal.getContract();
                List<Long> targetStudentGroups = poll.getPollStudentGroups().stream().map(p -> EntityUtil.getId(p.getStudentGroup())).collect(Collectors.toList());
                if (contract != null && (isOverall || (targetStudentGroups.isEmpty() || targetStudentGroups.contains(journal.getStudent().getStudentGroup().getId())))) {
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
        PracticeThemesDto themes = new PracticeThemesDto(themes(poll));
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
                themes.setStudentGroup(AutocompleteResult.of(practiceJournal.getStudent().getStudentGroup()));
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
        dto = themes(poll);
        dto.setResponseId(EntityUtil.getId(response));
        dto.setConfirmed(ResponseStatus.KYSITVASTUSSTAATUS_V.name().equals(response.getStatus().getCode()) ? Boolean.TRUE : Boolean.FALSE);
        return dto;
    }
    
    /**
     * User won't be head admin or supervisor or ROLL_X
     * @param user
     * @return
     */
    public Set<ResponseDto> getPolls(HoisUserDetails user) {
        // Response if might be found but response_object wont be present, thus response_object id should be used in dto
        String pollSelect = "p.id, p.type_code, ro.response_id as responseId, r.status_code, p.valid_from, p.valid_thru, p.is_theme_pageable,"
                + " e.name, pe.firstname, pe.lastname, pj.start_date, pj.end_date, pt.target_code,"
                + "p.name_et, p.name_en, p.journal_from, p.journal_thru";
        JpaNativeQueryBuilder pollQuery = new JpaNativeQueryBuilder("from poll p "
                + "join poll_target pt on p.id = pt.poll_id "
                + "left join response r on r.poll_id = p.id "
                + "left join response_object ro on (ro.response_id = r.id and ro.poll_target_id = pt.id and ro.person_id = " + user.getPersonId()
                + (user.isStudent() ? " and ro.student_id = " + user.getStudentId() : "") 
                + (user.isRepresentative() ? " and ro.student_id = " + user.getStudentId() : "")
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
        }).collect(Collectors.toSet());
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
            if (studentGroupIds == null || studentGroupIds.isEmpty() || (student.getStudentGroup() != null && studentGroupIds.contains(EntityUtil.getId(student.getStudentGroup())))) {
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
            if (studentGroupIds == null || studentGroupIds.isEmpty() || (student.getStudentGroup() != null && studentGroupIds.contains(EntityUtil.getId(student.getStudentGroup())))) {
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
        boolean isStudentHigher = StudentUtil.isHigher(student);
        setPollTarget(poll, user, responseObject);
        response.setResponseObject(responseObject);
        responseObject.setStudent(student);
        responseObject.setCurriculumVersion(student.getCurriculumVersion());
        responseObject.setStudyForm(student.getStudyForm());
        List<ResponseSubject> responseSubjects = new ArrayList<>();
        if (isStudentHigher) {
            List<Long> subjectIds = getDistinctSubjects(poll.getStudyPeriod(), user.getStudentId());
            for (Long subjectId : subjectIds) {
                ResponseSubject responseSubject = new ResponseSubject();
                responseSubject.setResponse(response);
                responseSubject.setSubject(em.getReference(Subject.class, subjectId));
                responseSubjects.add(responseSubject);
            }
        } else {
            List<Long> journalIds = getUserRelatedJournals(poll, user);
            for (Long journalId : journalIds) {
                ResponseSubject responseSubject = new ResponseSubject();
                responseSubject.setResponse(response);
                responseSubject.setJournal(em.getReference(Journal.class, journalId));
                responseSubjects.add(responseSubject);
            }
        }
        response.setResponseSubjects(responseSubjects);
        if (responseSubjects.isEmpty()) {
            return;
        }
        EntityUtil.save(response, em);
        em.persist(responseObject);
        pollType.setResponseId(EntityUtil.getId(response));
        responses.add(pollType);
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
        setPollTarget(poll, user, responseObject);
        response.setResponseObject(responseObject);
        responseObject.setStudent(student);
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
    private List<Long> getDistinctSubjects(StudyPeriod studyPeriod, Long studentId) {
        String SEARCH_SELECT = "distinct s.id";
        String SEARCH_FROM = "from declaration d "
                + "join declaration_subject ds on d.id = ds.declaration_id "
                + "join curriculum_version_hmodule cvh on ds.curriculum_version_hmodule_id = cvh.id "
                + "join curriculum_version_hmodule_subject cvhs on cvhs.curriculum_version_hmodule_id = cvh.id "
                + "join subject s on cvhs.subject_id = s.id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("d.study_period_id = :studyPeriodId", "studyPeriodId", EntityUtil.getId(studyPeriod));
        qb.requiredCriteria("d.student_id = :studentId", "studentId", studentId);
        qb.requiredCriteria("d.status_code = :statusCode", "statusCode", DeclarationStatus.OPINGUKAVA_STAATUS_K.name());
        List<?> dbSubjects = qb.select(SEARCH_SELECT, em).getResultList();
        List<Long> subjectIds = StreamUtil
                .toMappedList(r-> resultAsLong(r, 0), dbSubjects);
        return subjectIds;
    }
    
    /**
     * Gets subjects with confirmed status
     * @param studyPeriod
     * @return subjects
     */
    private Set<Subject> getAllDistinctSubjects(StudyPeriod studyPeriod) {
        List<Declaration> declarations = em.createQuery("select d from Declaration d "
                + "where d.studyPeriod.id = ?1", Declaration.class)
                .setParameter(1, EntityUtil.getId(studyPeriod))
                .getResultList();
        Set<Subject> subjects = new HashSet<>();
        for (Declaration declaration : declarations) {
            if (ClassifierUtil.equals(DeclarationStatus.OPINGUKAVA_STAATUS_K, declaration.getStatus())) {
                Set<DeclarationSubject> declarationSubjects = declaration.getSubjects();
                for (DeclarationSubject declarationSubject : declarationSubjects) {
                    if (declarationSubject.getModule() != null) {
                        declarationSubject.getModule().getSubjects().forEach(p -> {
                            Subject subject = p.getSubject();
                            if (!subjects.contains(subject)) {
                                subjects.add(subject);
                            }
                        });
                    }
                }
            }
        }
        return subjects;
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
        ThemesDto dto = themes(response.getPoll());
        setPollValuesToDto(response, dto, view);
        dto.setConfirmed(ClassifierUtil.equals(ResponseStatus.KYSITVASTUSSTAATUS_V, response.getStatus()) ? Boolean.TRUE : Boolean.FALSE);
        return dto;
    }

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
                dto.getThemes().forEach(p-> p.getQuestions().forEach(l -> {
                    if (ids.contains(l.getId()) && (answer.getResponseSubject() == null || EntityUtil.getId(answer.getResponseSubject()).equals(l.getResponseSubjectId()))) {
                        l.setAnswerTxt(answerTxt);
                    }
                }));
            } else {
            // Checkboxes
                List<Long> ids = questionRef.getPollThemeQuestions().stream().map(PollThemeQuestion::getId).collect(Collectors.toList());
                String answerTxt = answer.getAnswerTxt();
                dto.getThemes().forEach(p-> p.getQuestions().forEach(l -> {
                    if (ids.contains(l.getId()) && (answer.getResponseSubject() == null || EntityUtil.getId(answer.getResponseSubject()).equals(l.getResponseSubjectId()))) {
                        for (QuestionAnswerDto questionAnswer : l.getAnswers()) {
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
    
    private static void bindQuestionsToThemes(PollTheme theme, ThemeDto dto, ResponseSubject responseSubject) {
        List<QuestionDto> questions = new ArrayList<>();
        for (PollThemeQuestion themeQuestion : theme.getPollThemeQuestions()) {
            QuestionDto questionDto = new QuestionDto();
            if (responseSubject != null) questionDto.setResponseSubjectId(EntityUtil.getId(responseSubject));
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
    
    private static ThemesDto subjectThemes(Poll poll, Response response) {
        List<PollTheme> pollThemes = poll.getPollThemes();
        List<ThemeDto> themeDtos = new ArrayList<>();
        List<PollTheme> repedetiveThemes = pollThemes.stream().filter(p -> p.getIsRepetitive() != null && p.getIsRepetitive().booleanValue()).collect(Collectors.toList());
        List<PollTheme> nonRepedetiveThemes = pollThemes.stream().filter(p -> p.getIsRepetitive() == null || !p.getIsRepetitive().booleanValue()).collect(Collectors.toList());
        List<ResponseSubject> responseSubjects = response.getResponseSubjects();
        for (ResponseSubject responseSubject : responseSubjects) {
            List<ThemeDto> themesBySubjectOrJournal = new ArrayList<>();
            for (PollTheme theme : repedetiveThemes) {
                ThemeDto dto = new ThemeDto();
                if (responseSubject.getJournal() != null) dto.setJournal(of(responseSubject.getJournal()));
                if (responseSubject.getSubject() != null) dto.setSubject(AutocompleteResult.of(responseSubject.getSubject()));
                bindQuestionsToThemes(theme, dto, responseSubject);
                themesBySubjectOrJournal.add(dto);
            }
            Collections.sort(themesBySubjectOrJournal);
            themeDtos.addAll(themesBySubjectOrJournal);
        }
        
        List<ThemeDto> themesNonRepedetive = new ArrayList<>();
        for (PollTheme theme : nonRepedetiveThemes) {
            ThemeDto dto = new ThemeDto();
            bindQuestionsToThemes(theme, dto, null);
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

    /**
     * Used for single question answer saving
     * @param pollThemeQuestion
     * @param response
     * @param question
     */
    public void saveOtherResponse(PollThemeQuestion pollThemeQuestion, Response response, QuestionDto question) {
        if (ClassifierUtil.equals(ResponseStatus.KYSITVASTUSSTAATUS_V, response.getStatus())) {
            throw new ValidationFailedException("poll.messages.pollEnded");
        } else if (response.getStatus() == null || ClassifierUtil.equals(ResponseStatus.KYSITVASTUSSTAATUS_A, response.getStatus())) {
            response.setStatus(em.getReference(Classifier.class, ResponseStatus.KYSITVASTUSSTAATUS_P.name()));
        }
        String answerTxt = question.getAnswerTxt();
        List<ResponseQuestionAnswer> answers = response.getResponseQuestionAnswers();
        Question questionRef = pollThemeQuestion.getQuestion();
        // In case there is no question_answer objects - textfield for example
        Optional<ResponseQuestionAnswer> answerOpt = answers.stream().filter(p -> p.getQuestion().getId().equals(questionRef.getId()) && (p.getResponseSubject() == null || EntityUtil.getId(p.getResponseSubject()).equals(question.getResponseSubjectId()))).findFirst();
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
            answer.setAnswerTxt(answerTxt);
            EntityUtil.save(answer, em);
        // Answers will have 'chosen : true' when answer type is checkbox
        // Might have several answers at once
        } else if (question.getAnswers().stream().anyMatch(p -> p.getChosen() != null)) {
            // Remove all answers from response regarding this question
            response.getResponseQuestionAnswers().removeIf(p -> p.getQuestion().getId().equals(questionRef.getId()) && (p.getResponseSubject() == null || EntityUtil.getId(p.getResponseSubject()).equals(question.getResponseSubjectId())));
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
                EntityUtil.save(answer, em);
            }
        } else if (answerTxt == null && answerOpt.isPresent()) {
            ResponseQuestionAnswer answer = answerOpt.get();
            answer.setAnswerTxt(answerTxt);
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
        String SEARCH_SELECT = "distinct p.id, coalesce(t.email, p.email)";
        String SEARCH_FROM = "from teacher t join person p on p.id = t.person_id "
                + "join practice_journal pj on pj.teacher_id = t.id ";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("pj.end_date >= :startDate", "startDate", poll.getJournalFrom());
        qb.requiredCriteria("pj.end_date <= :endDate", "endDate", poll.getJournalThru());
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
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
                + "join practice_journal pj on pj.student_id = s.id "
                + (hasStudentGroups ? "join student_group sg on sg.id = s.student_group_id "
                        + "join poll_student_group psg on (psg.student_group_id = sg.id and psg.poll_id = " + pollId + ") " : "");
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("pj.end_date >= :startDate", "startDate", poll.getJournalFrom());
        qb.requiredCriteria("pj.end_date <= :endDate", "endDate", poll.getJournalThru());
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", EntityUtil.getId(poll.getSchool()));
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
        String SEARCH_FROM = "from student_representative re join person p on p.id = rs.person_id "
                + "join student s on s.id = rs.student_id "
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
        List<?> dbTeachers = qb.select(SEARCH_SELECT, em).getResultList();
        dbTeachers.forEach(r -> sendEmailToPerson(poll.getSchool(), em.getReference(Person.class, resultAsLong(r, 0)), poll, resultAsString(r, 1)));
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
        String SEARCH_SELECT = "j.id as journalId, j.name_et as journalName, s.id as subjectId,"
                + " s.name_et as subjectName, s.name_en, s.code, c.name_et as yearEt, c.name_en as yearEn";
        String SEARCH_FROM = "from response_subject rs "
                + "left join journal j on j.id = rs.journal_id "
                + "left join study_year sy on sy.id = j.study_year_id "
                + "left join classifier c on c.code = sy.year_code "
                + "left join subject s on s.id = rs.subject_id "
                + "join response_question_answer rqa on rqa.response_id = rs.response_id "
                + "join question_answer qa on qa.id = rqa.question_answer_id "
                + "join question q on q.id = qa.question_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);
        qb.requiredCriteria("rs.response_id = :responseId", "responseId", EntityUtil.getId(response));
        qb.requiredCriteria("q.type_code != :typeCode", "typeCode", PollAnswer.VASTUS_T.name());
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
                            (StringUtils.isEmpty(code) ? resultAsString(r, 3) : resultAsString(r, 3) + " (" + code + ")"), 
                            (StringUtils.isEmpty(code) ? resultAsString(r, 4) : resultAsString(r, 4) + " (" + code + ")")));
                    return dto;
                }, dbSubjectOrJournal);
        
        PollRelatedObjectsDto dto = new PollRelatedObjectsDto();
        dto.setJournals(subjectOrJournal.stream().filter(p -> p.getJournal() != null && p.getJournal().getId() != null).map(p -> p.getJournal()).collect(Collectors.toSet()));
        dto.setSubjects(subjectOrJournal.stream().filter(p -> p.getSubject() != null && p.getSubject().getId() != null).map(p -> p.getSubject()).collect(Collectors.toSet()));
        
        // Make sure all non repetitive themes arent filled with textfields
        String POLL_SELECT = "pt.id";
        String POLL_FROM = "from poll_theme pt "
                + "join poll_theme_question ptq on pt.id = ptq.poll_theme_id "
                + "join question q on q.id = ptq.question_id";
        JpaNativeQueryBuilder themeQuery = new JpaNativeQueryBuilder(POLL_FROM);
        themeQuery.requiredCriteria("q.type_code != :typeCode", "typeCode", PollAnswer.VASTUS_T.name());
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
        
        String SEARCH_SELECT = "pt.id as theme_id, pt.name_et as themeEt, pt.name_en as themeEn, qq.id as questionId, qq.name_et as questionEt, "
                + "qq.name_en as questionEn, qa.id as answerId, qa.name_et as answerEt, qa.name_en as answerEn, qa.answer_nr, qa1.min_nr, qa1.max_nr,"
                + " count(T4.id), array_to_string(array_agg(T4.responseId), ',') as responseIds";
        
        String SEARCH_FROM = "from poll pp" + 
                " join poll_theme pt on pp.id=pt.poll_id" + 
                " join poll_theme_question pq on pt.id=pq.poll_theme_id" + 
                " join question qq on pq.question_id=qq.id" + 
                " join question_answer qa on qa.question_id = qq.id" + 
                " left join (select rqa.question_answer_id, r.poll_id, rqa.id, r.id as responseId" + 
                    " from response_question_answer rqa" + 
                    ((command.getJournalId() != null || command.getSubjectId() != null) ? 
                            " join response_subject rs on rqa.response_subject_id = rs.id" : "") +
                    " join response r on (rqa.response_id = r.id" +
                    (command.getJournalId() != null ? " and rs.response_id = r.id and rs.journal_id=" + command.getJournalId() : "") +
                    (command.getSubjectId() != null ? " and rs.response_id = r.id and rs.subject_id=" + command.getSubjectId() : "") +
                    "))" + 
                    " T4 on (T4.question_answer_id = qa.id and T4.poll_id = pp.id)" + 
                " left join (select min(qa1.answer_nr) as min_nr, max(qa1.answer_nr) as max_nr,qa1.question_id" + 
                    " from question_answer qa1" + 
                    " join question qq on qa1.question_id=qq.id" + 
                    " join poll_theme_question pq on pq.question_id=qq.id" + 
                    " join poll_theme pt on pq.poll_theme_id=pt.id and pt.poll_id=" + pollId +
                    " group by qa1.question_id) qa1 on pq.question_id=qa1.question_id";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM)
                .groupBy("pt.id, pt.name_et,pq.order_nr, qq.id, qq.name_et, qa.id, qa.answer_nr, qa.name_et, qa1.max_nr, qa1.min_nr").sort("pt.order_nr, pq.order_nr, qa.order_nr");
        qb.requiredCriteria("pp.id = :pollId", "pollId", pollId);
        qb.filter("pt.is_repetitive = " + ((command.getThemes() != null && command.getThemes().booleanValue()) ? "false" : "true"));
        List<?> dbAnswers = qb.select(SEARCH_SELECT, em).getResultList();
        
        List<GraphSearchDto> themeAndQuestionList = StreamUtil
                .toMappedList(r-> {
                    GraphSearchDto dto = new GraphSearchDto();
                    dto.setTheme(new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2)));
                    dto.setQuestion(new AutocompleteResult(resultAsLong(r, 3), resultAsString(r, 4), resultAsString(r, 5)));
                    dto.setQuestionAnswer(new AutocompleteResult(resultAsLong(r, 6), resultAsString(r, 7), resultAsString(r, 8)));
                    dto.setAnswerNr(resultAsLong(r, 9));
                    dto.setMinNr(resultAsLong(r, 10));
                    dto.setMaxNr(resultAsLong(r, 11));
                    dto.setAnswers(resultAsLong(r, 12));
                    String responsesString = resultAsString(r, 13);
                    if (!StringUtils.isEmpty(responsesString)) {
                        dto.setResponseIds(Arrays.asList(responsesString.split(",")).stream().map(p -> Long.valueOf(p)).collect(Collectors.toList()));
                    }
                    return dto;
                }, dbAnswers);
        Map<Long, List<GraphSearchDto>> map = themeAndQuestionList.stream().collect(Collectors.toMap(p -> p.getTheme().getId(), 
                p -> themeAndQuestionList.stream().filter(o -> o.getTheme().getId().equals(p.getTheme().getId())).collect(Collectors.toList())
                , (oldValue, newValue) -> oldValue));
        List<GraphInfoDto> graphByTheme = new ArrayList<>();
        for (Map.Entry<Long, List<GraphSearchDto>> entry : map.entrySet()) {
            GraphInfoDto infoDto = new GraphInfoDto();
            if (isStudent) infoDto.getData().add(new ArrayList<>());
            long max = 0;
            List<AutocompleteResult> labels = new ArrayList<>();
            List<GraphSearchDto> entryValues = entry.getValue();
            GraphSearchDto theme = entryValues.get(0);
            Map<Long, List<GraphSearchDto>> questionMap = entryValues.stream().collect(Collectors.toMap(p -> p.getQuestion().getId(), 
                    p -> entryValues.stream().filter(o -> o.getQuestion().getId().equals(p.getQuestion().getId())).collect(Collectors.toList())
                    , (oldValue, newValue) -> oldValue));
            // loop per theme
            for (Map.Entry<Long, List<GraphSearchDto>> questionEntry : questionMap.entrySet()) {
                List<QuestionResponsePairDto> responses = new ArrayList<>();
                List<GraphSearchDto> questionEntries = questionEntry.getValue();
                GraphSearchDto firstQuestion = questionEntries.get(0);
                BigDecimal myAnswer = BigDecimal.valueOf(0L);
                List<Long> weights = new ArrayList<>();
                // loop per question in theme;
                for (GraphSearchDto searchDto : questionEntries) {
                    if (searchDto.getMaxNr() != null && searchDto.getMaxNr().longValue() > max) {
                        max = searchDto.getMaxNr().longValue();
                    }
                    if (searchDto.getResponseIds() != null && isStudent && searchDto.getResponseIds().contains(responseId)) {
                        myAnswer = BigDecimal.valueOf(searchDto.getAnswerNr().longValue());
                    }
                    for (int i = 0; i < searchDto.getAnswers().intValue(); i++) {
                        weights.add(searchDto.getAnswerNr());
                    }
                    QuestionResponsePairDto responseDto = new QuestionResponsePairDto();
                    responseDto.setAnswer(searchDto.getQuestionAnswer());
                    responseDto.setAnswers(searchDto.getAnswers());
                    responseDto.setAnswerNr(searchDto.getAnswerNr());
                    responses.add(responseDto);
                }
                IntSummaryStatistics stats = weights.stream().mapToInt((p) -> p.intValue()).summaryStatistics();
                Long uniqueAnswers = Long.valueOf(questionEntries.stream()
                        .filter(p -> p.getResponseIds() != null)
                        .flatMap(p -> p.getResponseIds().stream()).distinct().count());
                infoDto.getLabelOverride().add(new DatasetOverride(responses, uniqueAnswers));
                infoDto.getData().get(0).add(BigDecimal.valueOf(stats.getAverage()).setScale(2, RoundingMode.HALF_UP));
                if (isStudent) {
                    infoDto.getData().get(1).add(myAnswer);
                }
                labels.add(firstQuestion.getQuestion());
            }
            infoDto.setLabels(labels);
            infoDto.setMax(Long.valueOf(max));
            TitleOptions titleOptions = new TitleOptions();
            titleOptions.setDisplay(Boolean.TRUE);
            titleOptions.setText(theme.getTheme());
            GraphOptions options = new GraphOptions(titleOptions);
            infoDto.setOptions(options);
            graphByTheme.add(infoDto);
        }
        GraphDto dto = new GraphDto();
        if (command.getSubjectId() != null || command.getJournalId() != null) {
            dto.setComments(getComments(user, pollId, command));
        }
        dto.setCommentDisabled(Boolean.valueOf(!PracticeJournalUserRights.isBeforeDaysAfterCanEdit(poll.getValidThru())));
        dto.setGraphByTheme(graphByTheme);
        return dto;
    }
    
    private List<SubjectCommentDto> getComments(HoisUserDetails user, Long pollId, GraphSearchCommand command) {
        String SEARCH_SELECT = "ptc.add_info, ptc.id as commentId, t.id as teacherId, (p.firstname || ' ' || p.lastname)";
        
        String SEARCH_FROM = "from poll_teacher_comment ptc join teacher t on t.id = ptc.teacher_id join person p on p.id = t.person_id";
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM);        
        qb.requiredCriteria("ptc.poll_id = :pollId", "pollId", pollId);
        qb.optionalCriteria("ptc.subject_id = :subjectId", "subjectId", command.getSubjectId());
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

    public void saveComment(HoisUserDetails user, Poll poll, PollCommentCommand command) {
        PollTeacherComment comment;
        if (command.getCommentRef() == null) {
            comment = new PollTeacherComment();
            if (command.getJournalId() != null) {
                comment.setJournal(em.getReference(Journal.class, command.getJournalId()));
            } else if (command.getSubjectId() != null) {
                comment.setSubject(em.getReference(Subject.class, command.getSubjectId()));
            }
            comment.setTeacher(em.getReference(Teacher.class, user.getTeacherId()));
            comment.setPoll(poll);
        } else {
            comment = em.getReference(PollTeacherComment.class, command.getCommentRef());
        }
        comment.setAddInfo(command.getComment());
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.save(comment, em);
    }
}
