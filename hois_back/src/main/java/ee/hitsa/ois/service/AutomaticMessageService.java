package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Enterprise;
import ee.hitsa.ois.domain.Message;
import ee.hitsa.ois.domain.MessageReceiver;
import ee.hitsa.ois.domain.MessageTemplate;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.MessageStatus;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.exception.BadConfigurationException;
import ee.hitsa.ois.service.MessageTemplateService.HoisReflectivePropertyAccessor;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.validation.ValidationFailedException;

@Transactional
@Service
public class AutomaticMessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ExpressionParser spelParser = new SpelExpressionParser();

    @Autowired
    private EntityManager em;
    @Autowired
    private MessageTemplateService messageTemplateService;
    @Autowired
    private MailService mailService;

    public void sendMessageToSchoolAdmins(MessageType type, School school, Object dataBean) {
        List<Person> persons = getPersonsWithRole(school, Role.ROLL_A);

        Message message = sendMessageToPersons(type, school, persons, dataBean);

        if(message != null) {
            List<String> receivers = StreamUtil.toMappedList(Person::getEmail, persons);
            mailService.sendMail(message, receivers);
        }
    }

    public void sendMessageToStudent(MessageType type, Student student, Object dataBean) {
        sendMessageToStudent(type, student, dataBean, null);
    }

    public void sendMessageToStudent(MessageType type, Student student, Object dataBean, HoisUserDetails initiator) {
        Message message = sendMessageToPersons(type, student.getSchool(), Collections.singletonList(student.getPerson()), dataBean);
        if (message != null) {
            mailService.sendMail(message, Collections.singletonList(student.getEmail()));
        }

        if (!StudentUtil.isAdultAndDoNotNeedRepresentative(student)) {
            sendMessageToStudentRepresentatives(type, student, dataBean, message, initiator);
        }
    }

    public void sendMessageToStudentRepresentatives(MessageType type, Student student, Object dataBean) {
        sendMessageToStudentRepresentatives(type, student, dataBean, null, null);
    }

    public void sendMessageToStudentRepresentatives(MessageType type, Student student, Object dataBean, Message existingMessage, HoisUserDetails initiator) {
        if(!StudentUtil.hasRepresentatives(student)) {
            LOG.error("No representatives found to send message {} to", type.name());
            return;
        }

        List<Person> persons = getStudentRepresentativePersons(student, initiator);

        Message message = sendMessageToPersons(type, student.getSchool(), persons, dataBean, existingMessage);

        if(message != null) {
            List<String> receivers = StreamUtil.toMappedList(Person::getEmail, persons);
            mailService.sendMail(message, receivers);
        }
    }

    public void sendMessageToPerson(MessageType type, School school, Person person, Object data) {
        Message message = sendMessageToPersons(type, school, Collections.singletonList(person), data);

        if (message != null) {
            mailService.sendMail(message, Collections.singletonList(person.getEmail()));
        }
    }

    public void sendMessageToEnterprise(Enterprise enterprise, School school, MessageType type, Object dataBean) {
        Message message = getMessage(type, school, dataBean);

        if (message != null && StringUtils.hasText(enterprise.getContactPersonEmail())) {
            Person automaticSender = em.getReference(Person.class, PersonUtil.AUTOMATIC_SENDER_ID);
            mailService.sendMail(automaticSender.getEmail(), enterprise.getContactPersonEmail(), message.getSubject(), message.getContent());
        }
    }

    private Message sendMessageToPersons(MessageType type, School school, List<Person> persons, Object dataBean) {
        return sendMessageToPersons(type, school, persons, dataBean, null);
    }

    private Message sendMessageToPersons(MessageType type, School school, List<Person> persons, Object dataBean, Message existingMessage) {
        Classifier status = em.getReference(Classifier.class, MessageStatus.TEATESTAATUS_U.name());
        List<MessageReceiver> messageReceivers = StreamUtil.toMappedList(person -> {
            MessageReceiver messageReceiver = new MessageReceiver();
            messageReceiver.setPerson(person);
            messageReceiver.setStatus(status);
            return messageReceiver;
        }, persons);

        Person automaticSender = em.getReference(Person.class, PersonUtil.AUTOMATIC_SENDER_ID);
        return sendTemplateMessage(type, school, automaticSender, messageReceivers, dataBean, existingMessage);
    }

    private List<Person> getStudentRepresentativePersons(Student student, HoisUserDetails initiator) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from person p "
                + "inner join student_representative sr on sr.person_id = p.id");

        qb.requiredCriteria("sr.student_id = :studentId", "studentId", EntityUtil.getId(student));
        qb.filter("sr.is_student_visible = true");
        qb.optionalCriteria("p.id = :initiatorId", "initiatorId", initiator != null ? initiator.getPersonId() : null);

        List<?> results = qb.select("p.id", em).getResultList();
        return StreamUtil.toMappedList(r -> em.getReference(Person.class, resultAsLong(r, 0)), results);
    }

    private List<Person> getPersonsWithRole(School school, Role role) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from person p "
                + "inner join user_ u on u.person_id = p.id");

        qb.requiredCriteria("u.school_id = :schoolId", "schoolId", EntityUtil.getId(school));
        qb.requiredCriteria("u.role_code = :roleCode", "roleCode", role);
        qb.validNowCriteria("u.valid_from", "u.valid_thru");

        List<?> results = qb.select("p.id", em).getResultList();
        return StreamUtil.toMappedList(r -> em.getReference(Person.class, resultAsLong(r, 0)), results);
    }

    /**
     * Get message for given message type
     *
     * @param type
     * @param school
     * @param dataBean
     * @return
     * @throws BadConfigurationException if there is no valid template for given message type
     * @throws ValidationFailedException if dataBean is of wrong type
     */
    private Message getMessage(MessageType type, School school, Object dataBean) {
        if (!type.validBean(dataBean)) {
            throw new ValidationFailedException(String.format("invalid data bean for template %s", type.name()));
        }

        Long schoolId = EntityUtil.getId(school);
        MessageTemplate template = messageTemplateService.findValidTemplate(type, schoolId);
        if(template == null) {
            LOG.error("Cannot send message {} in school {}: template is missing", type.name(), EntityUtil.getId(school));
            return null;
        }

        try {
            StandardEvaluationContext ctx = new StandardEvaluationContext(dataBean);
            ctx.setPropertyAccessors(Arrays.asList(new HoisReflectivePropertyAccessor()));
            String content = spelParser.parseExpression(template.getContent(), new TemplateParserContext()).getValue(ctx, String.class);
            Message message = new Message();
            message.setSubject(template.getHeadline());
            message.setContent(content);
            return message;
        } catch (Exception e) {
            LOG.error("message {} could not be sent for school {}", type.name(), schoolId, e);
        }
        return null;
    }

    private Message sendTemplateMessage(MessageType type, School school, Person sender, List<MessageReceiver> messageReceivers, Object dataBean, Message existingMessage) {
        if (!type.validBean(dataBean)) {
            throw new ValidationFailedException(String.format("invalid data bean for template %s", type.name()));
        }

        Message message = existingMessage;
        if (message == null) {
            message = getMessage(type, school, dataBean);
            if (message != null) {
                message.setSendersSchool(school);
                message.setSender(sender);
            }
        }

        if (message != null) {
            message.getReceivers().addAll(messageReceivers);
            message = EntityUtil.save(message, em);
        }

        return message;
    }
}
