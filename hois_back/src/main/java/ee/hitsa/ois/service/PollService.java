package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.poll.Poll;
import ee.hitsa.ois.domain.poll.PollStudentGroup;
import ee.hitsa.ois.domain.poll.PollTarget;
import ee.hitsa.ois.domain.poll.PollTheme;
import ee.hitsa.ois.domain.poll.PollThemeQuestion;
import ee.hitsa.ois.domain.poll.PollThemeQuestionFile;
import ee.hitsa.ois.domain.poll.Question;
import ee.hitsa.ois.domain.poll.QuestionAnswer;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.enums.PollStatus;
import ee.hitsa.ois.enums.PollType;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.OisFileCommand;
import ee.hitsa.ois.web.commandobject.OisFileForm;
import ee.hitsa.ois.web.commandobject.poll.PollCommand;
import ee.hitsa.ois.web.commandobject.poll.PollForm;
import ee.hitsa.ois.web.commandobject.poll.PollSearchCommand;
import ee.hitsa.ois.web.commandobject.poll.QuestionCommand;
import ee.hitsa.ois.web.commandobject.poll.QuestionOrderCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemeOrderCommand;
import ee.hitsa.ois.web.commandobject.poll.ThemeCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.poll.PollSearchDto;
import ee.hitsa.ois.web.dto.poll.QuestionAnswerDto;
import ee.hitsa.ois.web.dto.poll.QuestionDto;
import ee.hitsa.ois.web.dto.poll.ThemeDto;
import ee.hitsa.ois.web.dto.poll.ThemesDto;

@Transactional
@Service
public class PollService {

    @Autowired
    private EntityManager em;

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
        if (pollForm.getTypeCode() != null && PollType.KYSITLUS_LIST.contains(pollForm.getTypeCode())) {
            changedPoll.setType(em.getReference(Classifier.class, pollForm.getTypeCode()));
        }
        
        EntityUtil.bindEntityCollection(poll.getPollStudentGroups(), EntityUtil::getId,
                pollForm.getStudentGroups(), p -> p, dto -> {
                    PollStudentGroup pollStudentGroup = new PollStudentGroup();
                    pollStudentGroup.setPoll(changedPoll);
                    pollStudentGroup.setStudentGroup(em.getReference(StudentGroup.class, dto));
                    return pollStudentGroup;
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
        if (poll.getType() != null) {
            form.setTypeCode(poll.getType().getCode());
        }
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
        if (poll.getSchool() != null) {
            form.setStatus(poll.getStatus().getCode());
        }
        form.setThemes(poll.getPollThemes().size());
        return form;
    }

    public Page<PollSearchDto> search(HoisUserDetails user, PollSearchCommand command, Pageable pageable) {
        String SEARCH_FROM = "from poll p "
                + "left join poll_target pt on (pt.poll_id = p.id) ";
        String SEARCH_SELECT = "p.id, p.name_et, p.type_code, string_agg(pt.target_code, ';') as targetcodes, p.valid_from, p.valid_thru, p.status_code ";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(SEARCH_FROM).sort(pageable);
        qb.optionalContains("p.name_et = :name", "name", command.getPollName());
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
                List<OisFileForm> files = new ArrayList<>();
                for (PollThemeQuestionFile file : themeQuestion.getPollThemeQuestionFiles()) {
                    OisFileForm fileForm = new OisFileForm();
                    OisFile oisFile = file.getOisFile();
                    fileForm.setId(oisFile.getId());
                    fileForm.setOisFile(EntityUtil.bindToDto(oisFile, new OisFileCommand()));
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
        if (poll.getStatus() != null && PollStatus.KYSITLUS_STAATUS_K.name().equals(poll.getStatus().getCode())) {
            confirmed = Boolean.TRUE;
        }
        return new ThemesDto(themeDtos, confirmed);
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
        if (pollThemeQuestion.getPollTheme().getId() != questionCommand.getTheme()) {
            pollThemeQuestion.setPollTheme(em.getReference(PollTheme.class, questionCommand.getTheme()));
            pollThemeQuestion.setOrderNr(Short.valueOf((short) (pollThemeQuestion.getPollTheme().getPollThemeQuestions().size() + 1)));
        }
        // Set files
        EntityUtil.bindEntityCollection(pollThemeQuestion.getPollThemeQuestionFiles(), EntityUtil::getId,
                questionCommand.getFiles(), OisFileForm::getId, dto -> {
                    PollThemeQuestionFile file = new PollThemeQuestionFile();
                    file.setOisFile(EntityUtil.bindToEntity(dto.getOisFile(), new OisFile()));
                    file.setPollThemeQuestion(pollThemeQuestion);
                    return file;
                });
        EntityUtil.save(pollThemeQuestion, em);
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
            String nameAndType = name + "(" + type + ")";
            return new AutocompleteResult(resultAsLong(r, 0), nameAndType, nameAndType);
        }, results);
    }

    public QuestionDto question(HoisUserDetails user, Question question) {
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
    

}
