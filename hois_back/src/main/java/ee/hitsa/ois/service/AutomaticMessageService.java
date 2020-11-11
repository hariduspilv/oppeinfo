package ee.hitsa.ois.service;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.ContractSupervisor;
import ee.hitsa.ois.domain.Message;
import ee.hitsa.ois.domain.MessageReceiver;
import ee.hitsa.ois.domain.MessageTemplate;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.MessageStatus;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.exception.BadConfigurationException;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.service.MessageTemplateService.HoisReflectivePropertyAccessor;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

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
        sendMessageToSchoolAdmins(type, school, dataBean, type.getPermissions());
    }

    public void sendMessageToSchoolAdmins(MessageType type, School school,
                                          Object dataBean, PermissionObject permission) {
        sendMessageToSchoolAdmins(type, school, dataBean,
                permission != null ? Collections.singleton(permission) : type.getPermissions());
    }

    public void sendMessageToSchoolAdmins(MessageType type, School school, Object dataBean,
                                          Set<PermissionObject> permissions) {
        checkTypeAndPermissionObjects(type, permissions);
        List<Person> admins = getPersonsWithRoleAndUserRight(school, Role.ROLL_A, permissions);

        Message message = sendMessageToPersons(type, school, admins, dataBean);

        if (message != null) {
            List<String> receivers = StreamUtil.toMappedList(Person::getEmail, admins);
            mailService.sendMail(message, receivers, type.isHtml());
        }
    }

    public void sendMessageToStudentAndSchoolAdmins(MessageType type, Student student, Object dataBean) {
        School school = student.getSchool();
        List<Person> admins = getPersonsWithRoleAndUserRight(school, Role.ROLL_A, type.getPermissions());

        Message adminMessage = sendMessageToPersons(type, school, admins, dataBean);
        // 461: user can receive a message twice as a student and as an admin.
        Message studentMessage = admins.contains(student.getPerson()) ? null
                : sendMessageToPersons(type, school, Collections.singletonList(student.getPerson()), dataBean);

        if(adminMessage != null) {
            List<String> receivers = StreamUtil.toMappedList(Person::getEmail, admins);
            mailService.sendMail(adminMessage, receivers, type.isHtml());
        }
        if (studentMessage != null) {
            List<String> receivers = Collections.singletonList(getEmail(student).orElse(null));
            mailService.sendMail(studentMessage, receivers, type.isHtml());
        }
    }

    public void sendMessageToStudentAndRepresentativeAndSchoolAdmins(MessageType type, Student student, Object dataBean) {
        sendMessageToStudentAndRepresentativeAndSchoolAdmins(type, student, dataBean, type.getPermissions());
    }

    public void sendMessageToStudentAndRepresentativeAndSchoolAdmins(MessageType type, Student student, Object dataBean,
                                                                     PermissionObject permissionObject) {

        sendMessageToStudentAndRepresentativeAndSchoolAdmins(type, student, dataBean,
                permissionObject != null ? Collections.singleton(permissionObject) : type.getPermissions());
    }

    /**
     * Sends a message to student and his/her representative (in case if student needs it that means student is not adult or needs representative) and school admins.
     *
     * It should create 2 different messages for (1) student with representative (if needs) and (2) admins
     *
     * @param type Message type
     * @param student Student
     * @param dataBean Message
     */
    public void sendMessageToStudentAndRepresentativeAndSchoolAdmins(MessageType type, Student student, Object dataBean,
                                                                     Set<PermissionObject> permissions) {
        checkTypeAndPermissionObjects(type, permissions);
        sendMessageToStudent(type, student, dataBean);

        List<Person> admins = getPersonsWithRoleAndUserRight(student.getSchool(), Role.ROLL_A, permissions);
        Message message = sendMessageToPersons(type, student.getSchool(), admins, dataBean);

        if (message != null) {
            List<String> receivers = admins.stream()
                    .map(AutomaticMessageService::getEmail)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            mailService.sendMail(message, receivers, type.isHtml());
        }
    }

    public void sendMessageToStudent(MessageType type, Student student, Object dataBean) {
        sendMessageToStudent(type, student, dataBean, null);
    }

    public void sendMessageToStudent(MessageType type, Student student, Object dataBean, HoisUserDetails initiator) {
        Message message = sendMessageToPersons(type, student.getSchool(), Collections.singletonList(student.getPerson()), dataBean);
        if (message != null) {
            mailService.sendMail(message, Collections.singletonList(getEmail(student).orElse(null)), type.isHtml());
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
            mailService.sendMail(message, receivers, type.isHtml());
        }
    }

    public void sendMessageToPerson(MessageType type, School school, Person person, Object data) {
        Message message = sendMessageToPersons(type, school, Collections.singletonList(person), data);

        if (message != null) {
            mailService.sendMail(message, Collections.singletonList(person.getEmail()), type.isHtml());
        }
    }

    public void sendMessageToEmail(MessageType type, School school, Person person, Object data, String email) {
        Message message = sendMessageToPersons(type, school, Collections.singletonList(person), data);

        if (message != null) {
            mailService.sendMail(message, Collections.singletonList(email), type.isHtml());
        }
    }

    public void sendMessageToEnterprise(ContractSupervisor supervisor, School school, MessageType type, Object dataBean) {
        String to = supervisor.getSupervisorEmail();
        if (StringUtils.hasText(to)) {
            Person automaticSender = em.getReference(Person.class, PersonUtil.AUTOMATIC_SENDER_ID);
            sendMessageToEmail(type, school, automaticSender, dataBean, to);
        }
    }

    public void sendMessageToTeacher(MessageType type, Teacher teacher, Object dataBean) {
        List<Person> teachers = getPersonsWithRoleAndUserRight(teacher.getSchool(), Role.ROLL_O,
                type.getPermissions(), EntityUtil.getId(teacher.getPerson()));
        Message message = sendMessageToPersons(type, teacher.getSchool(), teachers, dataBean);
        if (message != null) {
            mailService.sendMail(message, Collections.singletonList(getEmail(teacher).orElse(null)), type.isHtml());
        }
    }

    public Message sendMessageToPersons(MessageType type, School school, List<Person> persons, Object dataBean) {
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

    @Deprecated
    private List<Person> getPersonsWithRole(School school, Role role) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from person p "
                + "inner join user_ u on u.person_id = p.id");

        qb.requiredCriteria("u.school_id = :schoolId", "schoolId", EntityUtil.getId(school));
        qb.requiredCriteria("u.role_code = :roleCode", "roleCode", role);
        qb.validNowCriteria("u.valid_from", "u.valid_thru");

        List<?> results = qb.select("p.id", em).getResultList();
        return results.stream()
                .map(r -> em.getReference(Person.class, resultAsLong(r, 0)))
                .distinct()
                .collect(Collectors.toList());
    }

    private List<Person> getPersonsWithRoleAndUserRight(School school, Role role,
                                                        Set<PermissionObject> permissions) {
        return getPersonsWithRoleAndUserRight(school, role, permissions, null);
    }

    private List<Person> getPersonsWithRoleAndUserRight(School school, Role role,
                                                        Set<PermissionObject> permissions,
                                                        Long personId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from person p "
                + "inner join user_ u on u.person_id = p.id");

        qb.requiredCriteria("u.school_id = :schoolId", "schoolId", EntityUtil.getId(school));
        qb.requiredCriteria("u.role_code = :roleCode", "roleCode", role);
        qb.validNowCriteria("u.valid_from", "u.valid_thru");
        qb.optionalCriteria("p.id = :pId", "pId", personId);

        if (permissions != null && !permissions.isEmpty()) {
            qb.filter("exists(select 1 from user_rights ur where ur.user_id = u.id" +
                    " and ur.permission_code = :permissionCode and ur.object_code in :objectCode)");
            qb.parameter("permissionCode", Permission.OIGUS_T.name());
            qb.parameter("objectCode", permissions.stream().map(Enum::name).collect(Collectors.toList()));
        }

        List<?> results = qb.select("p.id", em).getResultList();
        return results.stream()
                .map(r -> em.getReference(Person.class, resultAsLong(r, 0)))
                .distinct()
                .collect(Collectors.toList());
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
            ctx.setPropertyAccessors(Collections.singletonList(new HoisReflectivePropertyAccessor()));
            String contentWithoutFor = MessageTemplateService.forPatterns(
                    spelParser, ctx, dataBean, template.getContent());
            String content = spelParser.parseExpression(contentWithoutFor, new TemplateParserContext()).getValue(ctx, String.class);
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

    private static Optional<String> getEmail(BaseEntityWithId entity) {
        if (entity instanceof Student) {
            return Optional.of((Student) entity)
                    .map(student -> student.getEmail() != null
                            ? student.getEmail() : student.getPerson().getEmail());
        } else if (entity instanceof Teacher) {
            return Optional.of((Teacher) entity)
                    .map(Teacher::getEmail);
        } else if (entity instanceof Person) {
            return Optional.of((Person) entity)
                    .map(Person::getEmail);
        }
        return Optional.empty();
    }

    private static void checkTypeAndPermissionObjects(MessageType type, Set<PermissionObject> permissionObjects) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(permissionObjects);

        permissionObjects.stream()
                .filter(po -> !typeHasGivenPermissionObject(type, po))
                .findAny()
                .ifPresent(po -> {
                    throw new HoisException("Permission Object '" + po.name() + "' is not specified in MessageType.");
                });
    }

    private static boolean typeHasGivenPermissionObject(MessageType type, PermissionObject permissionObject) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(permissionObject);

        return type.getPermissions().contains(permissionObject);
    }
}
