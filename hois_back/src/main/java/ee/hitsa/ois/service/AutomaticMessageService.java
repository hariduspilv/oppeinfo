package ee.hitsa.ois.service;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Message;
import ee.hitsa.ois.domain.MessageReceiver;
import ee.hitsa.ois.domain.MessageTemplate;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentRepresentative;
import ee.hitsa.ois.enums.MessageStatus;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.MessageRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;

@Transactional
@Service
public class AutomaticMessageService {

    private static final Logger log = LoggerFactory.getLogger(AutomaticMessageService.class);

    private final ExpressionParser spelParser = new SpelExpressionParser();

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageTemplateService messageTemplateService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    public void sendMessageToSchoolAdmins(MessageType type, School school, Object dataBean) {
        List<Person> persons = StreamUtil.toMappedList(User::getPerson, userService.findAllValidSchoolUsersByRole(school, Role.ROLL_A));

        Message message = sendMessageToPersons(type, school, persons, dataBean);

        if(message != null) {
            List<String> receivers = StreamUtil.toMappedList(Person::getEmail, persons);
            mailService.sendMail(message.getSender().getEmail(), receivers, message.getSubject(), message.getContent());
        }
    }

    public void sendMessageToStudent(MessageType type, Student student, Object dataBean, HoisUserDetails initiator) {
        Message message = sendMessageToPersons(type, student.getSchool(), Collections.singletonList(student.getPerson()), dataBean);
        if (message != null) {
            mailService.sendMail(message.getSender().getEmail(), student.getEmail(), message.getSubject(), message.getContent());
        }

        if (!StudentUtil.isAdult(student)) {
            sendMessageToStudentRepresentatives(type, student, dataBean, message, initiator);
        }
    }

    public void sendMessageToStudent(MessageType type, Student student, Object dataBean) {
        sendMessageToStudent(type, student, dataBean, null);
    }

    public void sendMessageToStudentRepresentatives(MessageType type, Student student, Object dataBean, Message existingMessage, HoisUserDetails initiator) {
        List<Person> persons = StreamUtil.toMappedList(StudentRepresentative::getPerson, student.getRepresentatives().stream()
                .filter(sr -> Boolean.TRUE.equals(sr.getIsStudentVisible()))
                .filter(sr -> initiator == null || !EntityUtil.getId(sr.getPerson()).equals(initiator.getPersonId())));

        Message message = sendMessageToPersons(type, student.getSchool(), persons, dataBean, existingMessage);

        if(message != null) {
            List<String> receivers = StreamUtil.toMappedList(Person::getEmail, persons);
            mailService.sendMail(message.getSender().getEmail(), receivers, message.getSubject(), message.getContent());
        }
    }

    public void sendMessageToStudentRepresentatives(MessageType type, Student student, Object dataBean) {
        sendMessageToStudentRepresentatives(type, student, dataBean, null, null);
    }

    public void sendMessageToPerson(MessageType type, School school, Person person, Object data) {
        Message message = sendMessageToPersons(type, school, Collections.singletonList(person), data);

        if (message != null) {
            mailService.sendMail(message.getSender().getEmail(), person.getEmail(), message.getSubject(), message.getContent());
        }
    }

    private Message sendMessageToPersons(MessageType type, School school, List<Person> persons, Object dataBean, Message existingMessage) {
        Classifier status = classifierRepository.getOne(MessageStatus.TEATESTAATUS_U.name());
        List<MessageReceiver> messageReceivers = StreamUtil.toMappedList(person -> {
            MessageReceiver messageReceiver = new MessageReceiver();
            messageReceiver.setPerson(person);
            messageReceiver.setStatus(status);
            return messageReceiver;
        }, persons);

        Person automaticSender = personRepository.getOne(PersonUtil.AUTOMATIC_SENDER_ID);
        return sendTemplateMessage(type, school, automaticSender, messageReceivers, dataBean, existingMessage);
    }

    private Message sendMessageToPersons(MessageType type, School school, List<Person> persons, Object dataBean) {
        return sendMessageToPersons(type, school, persons, dataBean, null);
    }

    private Message getMessage(MessageType type, School school, Object dataBean) {
        if (!type.validBean(dataBean)) {
            // TODO meaningful exception class
            throw new RuntimeException(String.format("invalid data bean for template %s", type.name()));
        }

        Long schoolId = EntityUtil.getId(school);
        MessageTemplate template = messageTemplateService.findValidTemplate(type, schoolId);
        if (template == null) {
            // TODO meaningful exception class
            throw new RuntimeException(String.format("no message template %s found for school %d", type.name(), schoolId));
        }

        try {
            String content = spelParser.parseExpression(template.getContent(), new TemplateParserContext()).getValue(dataBean, String.class);
            Message message = new Message();
            message.setSubject(template.getHeadline());
            message.setContent(content);
            return message;
        } catch (Exception e) {
            // TODO avoid use of String.format
            log.error(String.format("message %s could not be sent for school %d", type.name(), schoolId), e);
        }
        return null;
    }

    private Message sendTemplateMessage(MessageType type, School school, Person sender, List<MessageReceiver> messageReceivers, Object dataBean, Message existingMessage) {
        if (!type.validBean(dataBean)) {
            // TODO meaningful exception class
            throw new RuntimeException(String.format("invalid data bean for template %s", type.name()));
        }

        Message message = existingMessage;
        if (existingMessage == null) {
            message = getMessage(type, school, dataBean);
            if (message != null) {
                message.setSendersSchool(school);
                message.setSender(sender);
            }
        }

        if (message != null) {
            message.getReceivers().addAll(messageReceivers);
            messageRepository.save(message);
        }

        return message;
    }
}
