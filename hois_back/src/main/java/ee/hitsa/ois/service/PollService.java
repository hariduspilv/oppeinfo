package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.PracticeJournal;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.poll.Poll;
import ee.hitsa.ois.domain.poll.PollJournal;
import ee.hitsa.ois.domain.poll.PollStudentGroup;
import ee.hitsa.ois.domain.poll.PollTarget;
import ee.hitsa.ois.domain.poll.PollTeacherOccupation;
import ee.hitsa.ois.domain.poll.PollTheme;
import ee.hitsa.ois.domain.poll.PollThemeQuestion;
import ee.hitsa.ois.domain.poll.PollThemeQuestionFile;
import ee.hitsa.ois.domain.poll.Question;
import ee.hitsa.ois.domain.poll.QuestionAnswer;
import ee.hitsa.ois.domain.poll.Response;
import ee.hitsa.ois.domain.poll.ResponseObject;
import ee.hitsa.ois.domain.poll.ResponseQuestionAnswer;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.teacher.TeacherOccupation;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.PollAnswer;
import ee.hitsa.ois.enums.PollStatus;
import ee.hitsa.ois.enums.PollTargets;
import ee.hitsa.ois.enums.PollType;
import ee.hitsa.ois.enums.ResponseStatus;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.message.ContractSupervisorMessage;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.OisFileEditDto;
import ee.hitsa.ois.web.commandobject.poll.PollCommand;
import ee.hitsa.ois.web.commandobject.poll.PollDatesCommand;
import ee.hitsa.ois.web.commandobject.poll.PollForm;
import ee.hitsa.ois.web.commandobject.poll.PollSearchCommand;
import ee.hitsa.ois.web.commandobject.poll.QuestionCommand;
import ee.hitsa.ois.web.commandobject.poll.QuestionOrderCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemePageableCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemeRepedetiveCommand;
import ee.hitsa.ois.web.commandobject.poll.ResponseForm;
import ee.hitsa.ois.web.commandobject.poll.ThemeOrderCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemeCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.poll.PollSearchDto;
import ee.hitsa.ois.web.dto.poll.PracticeThemesDto;
import ee.hitsa.ois.web.dto.poll.QuestionAnswerDto;
import ee.hitsa.ois.web.dto.poll.QuestionDto;
import ee.hitsa.ois.web.dto.poll.ResponseDto;
import ee.hitsa.ois.web.dto.poll.ThemeDto;
import ee.hitsa.ois.web.dto.poll.ThemesDto;

@Transactional
@Service
public class PollService {
    
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private EntityManager em;
    
    @Autowired
    private AutomaticMessageService automaticMessageService;
    
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
        Poll changedPoll = EntityUtil.bindToEntity(pollForm, poll, "typeCode", "id");
        if (pollForm.getStudyPeriod() != null) changedPoll.setStudyPeriod(em.getReference(StudyPeriod.class, pollForm.getStudyPeriod()));
        if (pollForm.getType() != null && PollType.KYSITLUS_LIST.contains(pollForm.getType())) {
            changedPoll.setType(em.getReference(Classifier.class, pollForm.getType()));
        }
        if (pollForm.getStatus() != null && PollStatus.KYSITLUS_STAATUS_LIST.contains(pollForm.getStatus())) {
            changedPoll.setStatus(em.getReference(Classifier.class, pollForm.getStatus()));
        }
        EntityUtil.bindEntityCollection(poll.getPollStudentGroups(), EntityUtil::getId,
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
        
        EntityUtil.bindEntityCollection(poll.getPollTargets(), EntityUtil::getId,
                pollForm.getTargetCodes(), p -> p, dto -> {
                    PollTarget pollTarget = new PollTarget();
                    pollTarget.setPoll(changedPoll);
                    pollTarget.setTarget(em.getReference(Classifier.class, dto));
                    return pollTarget;
                });
        changedPoll.setSchool(em.getReference(School.class, user.getSchoolId()));
        changedPoll.setNameEn(pollForm.getNameEt());
        return convertToForm(EntityUtil.save(changedPoll, em));
    }
    
    private static PollForm convertToForm(Poll poll) {
        PollForm form = new PollForm();
        EntityUtil.bindToDto(poll, form);
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
        form.setThemes(poll.getPollThemes().size());
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

    public void deleteTheme(HoisUserDetails user, PollTheme pollTheme) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(pollTheme, em);
    }
    
    public void deleteQuestion(HoisUserDetails user, PollThemeQuestion pollThemeQuestion) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(pollThemeQuestion, em);
    }
    
    public void updateThemeAfterDelete(HoisUserDetails user, Poll poll) {
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
            List<QuestionDto> questions = new ArrayList<>();
            for (PollThemeQuestion themeQuestion : theme.getPollThemeQuestions()) {
                QuestionDto command = new QuestionDto();
                Question question = themeQuestion.getQuestion();
                EntityUtil.bindToDto(question, command);
                command.setQuestion(question.getId());
                EntityUtil.bindToDto(themeQuestion, command);
                if (question.getType() != null) {
                    command.setType(question.getType().getCode());
                }
                int usages = question.getPollThemeQuestions().size();
                if (usages != 1) {
                    command.setDisabled(Boolean.TRUE);
                }
                
                // Set files
                List<OisFileEditDto> files = new ArrayList<>();
                for (PollThemeQuestionFile file : themeQuestion.getPollThemeQuestionFiles()) {
                    OisFileEditDto fileForm = OisFileEditDto.of(file.getOisFile());
                    files.add(fileForm);
                }
                command.setFiles(files);
                
                // Set question answers
                List<QuestionAnswerDto> questionAnswers = new ArrayList<>();
                for (QuestionAnswer answer : question.getQuestionAnswers()) {
                    QuestionAnswerDto answerDto = new QuestionAnswerDto();
                    EntityUtil.bindToDto(answer, answerDto);
                    questionAnswers.add(answerDto);
                }
                Collections.sort(questionAnswers);
                command.setAnswers(questionAnswers);
                
                questions.add(command);
            }
            Collections.sort(questions);
            dto.setQuestions(questions);
            EntityUtil.bindToDto(theme, dto);
            themeDtos.add(dto);
        }
        Collections.sort(themeDtos);
        Boolean confirmed = Boolean.FALSE;
        if (poll.getStatus() != null && ClassifierUtil.equals(PollStatus.KYSITLUS_STAATUS_K, poll.getStatus())) {
            confirmed = Boolean.TRUE;
        }
        ThemesDto themesDto = new ThemesDto(themeDtos, confirmed, poll.getForeword(), poll.getAfterword());
        themesDto.setIsThemePageable(poll.getIsThemePageable());
        themesDto.setType(poll.getType().getCode());
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

    public void updateQuestionAfterDelete(HoisUserDetails user, PollTheme pollTheme) {
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
        poll.setStatus(em.getReference(Classifier.class, PollStatus.KYSITLUS_STAATUS_E.name()));
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.save(poll, em);
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
        setPollThemes(poll, pollCopy);
        Poll newPoll = EntityUtil.save(pollCopy, em);
        return newPoll;
    }
    
    private static void setPollThemes(Poll poll, Poll pollCopy) {
        List<PollTheme> pollThemeCopies = new ArrayList<>();
        for (PollTheme theme : poll.getPollThemes()) {
            PollTheme newTheme = new PollTheme();
            newTheme.setPoll(pollCopy);
            newTheme.setNameEn(theme.getNameEn());
            newTheme.setNameEt(theme.getNameEt());
            newTheme.setOrderNr(theme.getOrderNr());
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
    
    
    /**
     * Used in Job Executor to send emails to supervisors daily containing Poll
     */
    public void sendPracticeJournalSupervisorEmails() {
        List<Poll> polls = em.createQuery("select p from Poll p "
                + "where ?3 between p.validFrom and p.validThru "
                + "and ?3 = p.reminderDt "
                + "and p.status.code = ?1 "
                + "and p.type.code = ?2", Poll.class)
                .setParameter(1, PollStatus.KYSITLUS_STAATUS_K.name())
                .setParameter(2, PollType.KYSITLUS_P.name())
                .setParameter(3, LocalDate.now())
                .getResultList();
        for (Poll poll : polls) {
            if (poll.getJournalFrom() != null && poll.getJournalThru() != null && 
                    poll.getPollTargets().stream().anyMatch(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_E, p.getTarget()))) {
                List<PracticeJournal> practiceJournals = em.createQuery("select pj from PracticeJournal pj "
                        + "where pj.endDate between ?1 and ?2", PracticeJournal.class)
                        .setParameter(1, poll.getJournalFrom())
                        .setParameter(2, poll.getJournalThru())
                        .getResultList();
                for (PracticeJournal journal : practiceJournals) {
                    Contract contract = journal.getContract();
                    if (contract != null) {
                        List<ContractSupervisor> supervisors = contract.getContractSupervisors().stream().filter(p -> p.getSupervisorEmail() != null).collect(Collectors.toList());
                        for (ContractSupervisor supervisor : supervisors) {
                            if (poll.getResponses().stream().noneMatch(p -> p.getResponseObject().getContractSupervisor() != null && p.getResponseObject().getContractSupervisor() == supervisor)) {
                                Student student = journal.getStudent();
                                Response response = new Response();
                                response.setPoll(poll);
                                response.setStatus(em.getReference(Classifier.class, ResponseStatus.KYSITVASTUSSTAATUS_A.name()));
                                ResponseObject responseObject = new ResponseObject();
                                responseObject.setResponse(response);
                                responseObject.setPollUrl(generateUniqueUrl());
                                responseObject.setContractSupervisor(supervisor);
                                responseObject.setPracticeJournal(journal);
                                if (student != null) {
                                    responseObject.setStudent(student);
                                    responseObject.setCurriculumVersion(student.getCurriculumVersion());
                                    responseObject.setStudyForm(student.getStudyForm());
                                    responseObject.setPerson(student.getPerson());
                                }
                                Optional<PollTarget> pollTarget = poll.getPollTargets().stream().filter(p -> ClassifierUtil.equals(PollTargets.KYSITLUS_SIHT_E, p.getTarget())).findFirst();
                                if (pollTarget.isPresent()) {
                                    responseObject.setPollTarget(pollTarget.get());
                                }
                                response.setResponseObject(responseObject);
                                EntityUtil.save(response, em);
                                em.persist(responseObject);
                                sendUniqueUrlEmailToEnterpriseSupervisor(journal.getSchool(), responseObject, student);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void sendUniqueUrlEmailToEnterpriseSupervisor(School school, ResponseObject responseObj, Student student) {
        String url = getFullUrl(responseObj.getPollUrl());
        ContractSupervisorMessage data = new ContractSupervisorMessage(student, url);
        automaticMessageService.sendMessageToEnterprise(responseObj.getContractSupervisor(), school, MessageType.TEATE_LIIK_KYSI_EV_JUHENDAJA, data);
    }

    private String getFullUrl(String uuid) {
        return frontendBaseUrl + "poll/supervisor/" + uuid;
    }

    public Poll getPollFromUrl(String uuid) {
        ResponseObject responseObj = getResponseObjectByUuid(uuid);
        Poll poll = responseObj.getResponse().getPoll();
        if (poll == null) {
            log.error("no poll found. uuid={}, ResponseObject={}", uuid, responseObj.getResponse().getId());
            throw new HoisException("poll.messages.noPoll");
        }
        return poll;
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

    public void assertSupervisorView(Poll poll) {
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
    
    public void saveResponse(ResponseForm form, Boolean finalAnswer, Optional<Response> responseOpt) {
        Response response = null;
        if (responseOpt.isPresent()) {
            response = responseOpt.get();
            if (ClassifierUtil.equals(ResponseStatus.KYSITVASTUSSTAATUS_V, response.getStatus())) {
                throw new ValidationFailedException("poll.messages.pollEnded");
            }
            if (finalAnswer != null && finalAnswer.booleanValue()) {
                response.setStatus(em.getReference(Classifier.class, ResponseStatus.KYSITVASTUSSTAATUS_V.name()));
            } else if (response.getStatus() == null || ClassifierUtil.equals(ResponseStatus.KYSITVASTUSSTAATUS_A, response.getStatus())) {
                response.setStatus(em.getReference(Classifier.class, ResponseStatus.KYSITVASTUSSTAATUS_P.name()));
            }
        } else {
            return;
        }
        for (ThemeDto theme : form.getThemes()) {
            for (QuestionDto question : theme.getQuestions()) {
                String answerTxt = question.getAnswerTxt();
                List<ResponseQuestionAnswer> answers = response.getResponseQuestionAnswers();
                PollThemeQuestion pollThemeQuestion = em.getReference(PollThemeQuestion.class, question.getId());
                Question questionRef = pollThemeQuestion.getQuestion();
                // In case there is no question_answer objects - textfield for example
                Optional<ResponseQuestionAnswer> answerOpt = answers.stream().filter(p -> p.getQuestion().getId() == questionRef.getId()).findFirst();
                if (!StringUtils.isBlank(answerTxt)) {
                    //Used for over writing answer
                    ResponseQuestionAnswer answer = null;
                    if (answerOpt.isPresent()) {
                        answer = answerOpt.get();
                    } else {
                        // QuestionAnswer is present for radio buttons and select fields
                        Optional<QuestionAnswer> questionAnswerOpt = questionRef.getQuestionAnswers().stream().filter(p -> answerTxt.equals(p.getNameEt())).findFirst();
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
                    // Set weight 1, shouldn't make a difference in statistics
                    answer.setAnswerTxt(answerTxt);
                    EntityUtil.save(answer, em);
                // Answers will have 'chosen : true' when answer type is checkbox
                // Might have several answers at once
                } else if (question.getAnswers().stream().anyMatch(p -> p.getChosen() != null)) {
                    // Remove all answers from response regarding this question
                    response.getResponseQuestionAnswers().removeIf(p -> p.getQuestion().getId().equals(questionRef.getId()));
                    List<QuestionAnswerDto> chosen = question.getAnswers().stream().filter(p -> p.getChosen() != null && p.getChosen().booleanValue()).collect(Collectors.toList());
                    for (QuestionAnswerDto choice : chosen) {
                        ResponseQuestionAnswer answer = new ResponseQuestionAnswer();
                        answer.setQuestion(questionRef);
                        answer.setResponse(response);
                        answer.setAnswerNr(choice.getAnswerNr());
                        answer.setAnswerTxt(choice.getNameEt());
                        EntityUtil.save(answer, em);
                    }
                } else if (answerTxt == null && answerOpt.isPresent()) {
                    ResponseQuestionAnswer answer = answerOpt.get();
                    answer.setAnswerTxt(answerTxt);
                    EntityUtil.deleteEntity(answer, em);
                }
            }
        }
    }

    public void saveSupervisorResponse(Poll poll, ResponseForm form, String uuid, Boolean finalAnswer) {
       Optional<Response> responseOpt = poll.getResponses().stream()
               .filter(p -> p.getResponseObject() != null 
               && p.getResponseObject().getContractSupervisor() != null
               && uuid.equals(p.getResponseObject().getPollUrl())).findFirst();
       saveResponse(form, finalAnswer, responseOpt);
    }
    
    public PracticeThemesDto themesForPractice(Poll poll, Response response) {
        PracticeThemesDto themes = new PracticeThemesDto(themes(poll));
        ResponseObject responseObject = response.getResponseObject();
        if (responseObject != null) {
            if (poll.getSchool() != null) themes.setSchool(new AutocompleteResult(null, poll.getSchool().getNameEt(), poll.getSchool().getNameEn()));
            if (responseObject.getStudent() != null && responseObject.getStudent().getPerson() != null) {
                Person person = responseObject.getStudent().getPerson();
                if (person != null) themes.setStudent(String.join(" ", person.getFirstname(), person.getLastname()));
            }
            PracticeJournal practiceJournal = responseObject.getPracticeJournal();
            if (practiceJournal != null) {
                themes.setPracticeFrom(responseObject.getPracticeJournal().getStartDate());
                themes.setPracticeThru(responseObject.getPracticeJournal().getEndDate());
                if (practiceJournal.getContract() != null && practiceJournal.getContract() != null && practiceJournal.getContract().getEnterprise() != null) {
                    themes.setEnterprise(practiceJournal.getContract().getEnterprise().getName());
                }
            }
        }
        return themes;
    }
    
    /**
     * Used for hooking new answers to Dto
     * @param poll
     * @param uuid
     * @return dto with answers
     */
    public PracticeThemesDto themesWithAnswers(Poll poll, String uuid) {
        Optional<Response> responseOpt = poll.getResponses().stream()
                .filter(p -> p.getResponseObject() != null 
                && p.getResponseObject().getContractSupervisor() != null
                && uuid.equals(p.getResponseObject().getPollUrl())).findFirst();
        PracticeThemesDto dto = null;
        if (responseOpt.isPresent()) {
            Response response = responseOpt.get();
            dto = themesForPractice(poll, response);
            setPollValuesToDto(response, dto);
            dto.setConfirmed(ResponseStatus.KYSITVASTUSSTAATUS_V.name().equals(response.getStatus().getCode()) ? Boolean.TRUE : Boolean.FALSE);
        }
        return dto;
    }
    
    /**
     * User won't be head admin or supervisor or ROLL_X
     * @param user
     * @return
     */
    public Set<ResponseDto> getPolls(HoisUserDetails user) {
        List<Poll> polls = em.createQuery("select p from Poll p "
                + "where (?1 between p.validFrom and p.validThru) "
                + "and p.status.code = ?2 "
                + "and p.school.id = ?3", Poll.class)
                .setParameter(1, LocalDate.now())
                .setParameter(2, PollStatus.KYSITLUS_STAATUS_K.name())
                .setParameter(3, user.getSchoolId())
                .getResultList();
        polls = polls.stream()
                .filter(p-> {
                    // Remove polls with incorrect target code
                    for (PollTarget target : p.getPollTargets()) {
                        if (target.getTarget() != null 
                                && getTargetCode(user).equals(target.getTarget().getCode())) {
                            return true;
                        }
                    }
                    return false;
                })
                .filter(p-> {
                    // Remove polls with incorrect Student group
                    if (user.isStudent())  {
                        Student student = em.getReference(Student.class, user.getStudentId());
                        if (p.getPollStudentGroups() == null || p.getPollStudentGroups().isEmpty()) {
                            return true;
                        }
                        if (p.getPollStudentGroups().stream().noneMatch(psg -> {
                            return psg.getStudentGroup() != null 
                                    &&  psg.getStudentGroup().getId() != null 
                                    && student.getStudentGroup() != null
                                    && psg.getStudentGroup().getId().equals(EntityUtil.getId(student.getStudentGroup()));
                        })) {
                            return false;
                        }
                        
                    }
                    return true;
                }).collect(Collectors.toList());
        List<Response> responses = findAndGenerateResponses(polls, user);
        return responses.stream().map(p -> {
            ResponseDto dto = new ResponseDto();
            EntityUtil.bindToDto(p, dto);
            Classifier responseStatus = p.getStatus();
            if (p.getPoll() != null) {
                Poll poll = p.getPoll();
                dto.setName(new AutocompleteResult(null, poll.getNameEt(), poll.getNameEn()));
                dto.setValidFrom(poll.getValidFrom());
                dto.setValidThru(poll.getValidThru());
                Classifier pollType = p.getPoll().getType();
                if (pollType != null) {
                    dto.setType(pollType.getCode());
                }
            }
            if (responseStatus != null) {
                dto.setStatus(responseStatus.getCode());
            }
            return dto;
        }).collect(Collectors.toSet());
    }
    
    private List<Response> findAndGenerateResponses(List<Poll> polls, HoisUserDetails user) {
        // At this point, the poll has the correct target code
        List<Response> responses = new ArrayList<>();
        for (Poll poll : polls) {
            // Üldine tagasiside
            // Üldine tagasiside type poll should only have one response per person
            if (ClassifierUtil.equals(PollType.KYSITLUS_Y, poll.getType())) {
                Response response = null;
                Optional<Response> responseOpt = poll.getResponses().stream()
                        .filter(p -> p.getResponseObject() != null 
                        && p.getResponseObject().getPerson() != null
                        && user.getPersonId().equals(p.getResponseObject().getPerson().getId())
                        && p.getResponseObject().getPollTarget() != null
                        && p.getResponseObject().getPollTarget().getTarget() != null
                        && getTargetCode(user).equals(p.getResponseObject().getPollTarget().getTarget().getCode())).findFirst();
                if (responseOpt.isPresent()) {
                    response = responseOpt.get();
                    responses.add(response);
                    continue;
                }
                response = new Response();
                Student student = null;
                Teacher teacher = null;
                Person person = null;
                person = em.getReference(Person.class, user.getPersonId());
                response.setPoll(poll);
                response.setStatus(em.getReference(Classifier.class, ResponseStatus.KYSITVASTUSSTAATUS_A.name()));
                ResponseObject responseObject = new ResponseObject();
                responseObject.setResponse(response);
                responseObject.setPerson(person);
                if (user.isStudent()) {
                    student = em.getReference(Student.class, user.getStudentId());
                    responseObject.setStudent(student);
                    responseObject.setCurriculumVersion(student.getCurriculumVersion());
                    responseObject.setStudyForm(student.getStudyForm());
                } else if (user.isTeacher()) {
                    teacher = em.getReference(Teacher.class, user.getTeacherId());
                    responseObject.setTeacher(teacher);
                }
                Optional<PollTarget> pollTarget = poll.getPollTargets().stream()
                        .filter(p-> p.getTarget() != null 
                        && p.getTarget().getCode() != null 
                        && getTargetCode(user).equals(p.getTarget().getCode())).findFirst();
                if (pollTarget.isPresent()) {
                    responseObject.setPollTarget(pollTarget.get());
                } else {
                    throw new HoisException("poll.messages.matchingTarget");
                }
                response.setResponseObject(responseObject);
                EntityUtil.save(response, em);
                em.persist(responseObject);
                responses.add(response);
            } else if (ClassifierUtil.equals(PollType.KYSITLUS_P, poll.getType())) {
                /**
                List<Response> practiceResponses = poll.getResponses().stream()
                        .filter(p -> p.getResponseObject() != null 
                        && p.getResponseObject().getPerson() != null
                        && user.getPersonId().equals(p.getResponseObject().getPerson().getId())
                        && p.getResponseObject().getPollTarget() != null
                        && p.getResponseObject().getPollTarget().getTarget() != null
                        && getTargetCode(user).equals(p.getResponseObject().getPollTarget().getTarget().getCode())).collect(Collectors.toList());
                // Remove practice polls that arent with the correct practice journal end date
                List<PracticeJournal> practiceJournals = em.createQuery("select pj from PracticeJournal pj "
                        + "where pj.endDate between ?1 and ?2 "
                        + "and pj.school.id = ?3", PracticeJournal.class)
                        .setParameter(1, poll.getJournalFrom())
                        .setParameter(2, poll.getJournalThru())
                        .setParameter(3, user.getSchoolId())
                        .getResultList();
                if (user.isStudent()) {
                    practiceJournals = practiceJournals.stream().filter(pj -> pj.getStudent() != null 
                            && user.getStudentId().equals(pj.getStudent().getId())).collect(Collectors.toList());
                } else if (user.isTeacher()) {
                    practiceJournals = practiceJournals.stream().filter(pj -> pj.getTeacher() != null 
                            && user.getTeacherId().equals(pj.getTeacher().getId())).collect(Collectors.toList());
                }
                if (!practiceJournals.isEmpty()) {
                    // generate response objects
                }
                */
            }
        }
        return responses.stream().filter(p -> !ClassifierUtil.equals(ResponseStatus.KYSITVASTUSSTAATUS_V, p.getStatus())).collect(Collectors.toList());
    }

    /**
    private void generateResponsesForPracticePoll(Poll poll, HoisUserDetails user) {
        List<PracticeJournal> practiceJournals = em.createQuery("select pj from PracticeJournal pj "
                + "where pj.endDate between ?1 and ?2", PracticeJournal.class)
                .setParameter(1, poll.getJournalFrom())
                .setParameter(2, poll.getJournalThru())
                .getResultList();
        for (PracticeJournal journal : practiceJournals) {
            if (poll.getResponses().stream().noneMatch(p -> )) {
                Student student = journal.getStudent();
                Response response = new Response();
                response.setPoll(poll);
                response.setStatus(em.getReference(Classifier.class, ResponseStatus.KYSITVASTUSSTAATUS_A.name()));
                ResponseObject responseObject = new ResponseObject();
                responseObject.setResponse(response);
                responseObject.setPollUrl(generateUniqueUrl());
                if (student != null) {
                    responseObject.setStudent(student);
                    responseObject.setCurriculumVersion(student.getCurriculumVersion());
                    responseObject.setStudyForm(student.getStudyForm());
                    responseObject.setPerson(student.getPerson());
                }
                Optional<PollTarget> pollTarget = poll.getPollTargets().stream()
                        .filter(p-> p.getTarget() != null 
                        && p.getTarget().getCode() != null 
                        && p.getTarget().getCode().equals(getTargetCode(user))).findFirst();
                if (pollTarget.isPresent()) {
                    responseObject.setPollTarget(pollTarget.get());
                }
                response.setResponseObject(responseObject);
                EntityUtil.save(response, em);
                em.persist(responseObject);
            }
        }
    }
    */

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

    public ThemesDto getPollWithAnswers(Response response) {
        ThemesDto dto = themes(response.getPoll());
        setPollValuesToDto(response, dto);
        dto.setConfirmed(ClassifierUtil.equals(ResponseStatus.KYSITVASTUSSTAATUS_V, response.getStatus()) ? Boolean.TRUE : Boolean.FALSE);
        dto.setResponseId(response.getId());
        return dto;
    }

    private static void setPollValuesToDto(Response response, ThemesDto dto) {
        if (ClassifierUtil.equals(ResponseStatus.KYSITVASTUSSTAATUS_V, response.getStatus())) {
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
                    if (ids.contains(l.getId())) {
                        l.setAnswerTxt(answerTxt);
                    }
                }));
            } else {
                // Checkboxes
                List<Long> ids = questionRef.getPollThemeQuestions().stream().map(PollThemeQuestion::getId).collect(Collectors.toList());
                String answerTxt = answer.getAnswerTxt();
                dto.getThemes().forEach(p-> p.getQuestions().forEach(l -> {
                    if (ids.contains(l.getId())) {
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
    }

    public void saveOtherResponse(Response response, ResponseForm form, Boolean finalAnswer) {
        saveResponse(form, finalAnswer, Optional.of(response));
    }

    public void updateThemesPageable(HoisUserDetails user, Poll poll, ThemePageableCommand command) {
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

    public void updateThemeRepetitive(HoisUserDetails user, PollTheme pollTheme, ThemeRepedetiveCommand command) {
        EntityUtil.setUsername(user.getUsername(), em);
        pollTheme.setIsRepetitive(command.getIsRepetitive());
        EntityUtil.save(pollTheme, em);
    }
}
