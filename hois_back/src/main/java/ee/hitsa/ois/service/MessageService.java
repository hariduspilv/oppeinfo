package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Message;
import ee.hitsa.ois.domain.MessageReceiver;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.MessageRepository;
import ee.hitsa.ois.repository.PersonRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.SearchUtil;
import ee.hitsa.ois.web.commandobject.MessageForm;
import ee.hitsa.ois.web.commandobject.MessageSearchCommand;
import ee.hitsa.ois.web.commandobject.UsersSeachCommand;
import ee.hitsa.ois.web.commandobject.student.StudentSearchCommand;
import ee.hitsa.ois.web.dto.MessageReceiverSearchDto;
import ee.hitsa.ois.web.dto.MessageSearchDto;
import ee.hitsa.ois.web.dto.UsersSearchDto;

@Transactional
@Service
public class MessageService {

    private static final String RECEIVED_MESSAGES_FROM = 
            " from message m inner join message_receiver mr on m.id = mr.message_id inner join person p on m.person_id = p.id ";
    private static final String RECEIVED_MESSAGES_SELECT = 
            " m.id, m.subject, m.content, m.inserted, mr.read is not null as isRead, "
            + "p.firstname || ' ' || p.lastname as sendersName ";
    private static String STUDENT_PARENTS_FROM = 
              " from student s "
            + "inner join student_group sg on s.student_group_id = sg.id "
            + "inner join student_representative sr on s.id = sr.student_id "
            + "inner join person p on p.id = sr.person_id ";
    private static String STUDENT_PARENTS_SELECT =
            " sg.id as studentGroupId, sg.code, s.id as studentId, sr.person_id as representativesId, "
            + "p.firstname, p.lastname, p.idcode ";
    private static final String PERSON_FROM = 
             " from user_ u "
            + "left outer join person p on u.person_id = p.id "
            + "left join school s on s.id = u.school_id ";
    private static final String PERSON_SELECT = 
            " distinct u.id, p.id as personId, p.firstname, p.lastname, p.idcode, u.role_code, s.ehis_school_code ";

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private EntityManager em;

    /**
     * TODO: 
     * The only difference between this method and PersonService.search() 
     * is that this one does not require wanted person to have school_id
     */
    public Page<UsersSearchDto> searchPersons(UsersSeachCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(PERSON_FROM, pageable);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname","p.firstname || ' ' || p.lastname"), "name", criteria.getName());
        qb.optionalCriteria("s.ehis_school_code = :ehiscode", "ehiscode", criteria.getSchool());
        qb.optionalCriteria("u.role_code = :role", "role", criteria.getRole());
        Page<Object[]> result =  JpaQueryUtil.pagingResult(qb, PERSON_SELECT, em, pageable);
        return result.map(r -> {
            UsersSearchDto dto = new UsersSearchDto();
            dto.setIdcode(resultAsString(r, 4));
            dto.setName(PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3)));
            dto.setId(resultAsLong(r, 1));
            dto.setRole(Arrays.asList(resultAsString(r, 5)));
            return dto;
        });
    }

    public Page<MessageSearchDto> searchSent(HoisUserDetails user, MessageSearchCommand criteria, Pageable pageable) {
        return messageRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();

            filters.add(cb.equal(root.get("sender").get("id"), user.getPersonId()));

            if(!user.isMainAdmin()) {
                filters.add(cb.equal(root.get("sendersSchool").get("id"), user.getSchoolId()));
            }

            LocalDate sentFrom = criteria.getSentFrom();
            if(sentFrom != null) {
              filters.add(cb.greaterThanOrEqualTo(root.get("inserted"), DateUtils.firstMomentOfDay(sentFrom)));
            }
            LocalDate sentThru = criteria.getSentThru();
            if(sentThru != null) {
              filters.add(cb.lessThanOrEqualTo(root.get("inserted"), DateUtils.lastMomentOfDay(sentThru)));
            }

            SearchUtil.propertyContains(() -> root.get("subject"), cb, criteria.getSubject(), filters::add);

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(MessageSearchDto::ofSent);
    }

    public Page<MessageSearchDto> show(HoisUserDetails user, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(RECEIVED_MESSAGES_FROM, pageable);
        qb.requiredCriteria("mr.person_id = :personId", "personId", user.getPersonId());
        if(!user.isMainAdmin()) {
            qb.requiredCriteria("m.school_id = :schoolId", "schoolId", user.getSchoolId());
        }
        qb.filter("mr.read is null");
        Page<Object[]> messages = JpaQueryUtil.pagingResult(qb, RECEIVED_MESSAGES_SELECT, em, pageable);
        return messages.map(d -> new MessageSearchDto(resultAsLong(d, 0), resultAsString(d, 1), resultAsString(d, 2), resultAsLocalDate(d, 3), resultAsString(d, 5), Boolean.FALSE));
    }

    public Page<MessageSearchDto> searchReceived(HoisUserDetails user, MessageSearchCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(RECEIVED_MESSAGES_FROM, pageable);
        qb.requiredCriteria("mr.person_id = :personId", "personId", user.getPersonId());
        if(!user.isMainAdmin()) {
            qb.requiredCriteria("m.school_id = :schoolId", "schoolId", user.getSchoolId());
        }

        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getSender());

        qb.optionalContains("m.subject", "subject", criteria.getSubject());

        qb.optionalCriteria("m.inserted >= :sentFrom", "sentFrom", criteria.getSentFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("m.inserted <= :sentThru", "sentThru", criteria.getSentThru(), DateUtils::lastMomentOfDay);

        Page<Object[]> messages = JpaQueryUtil.pagingResult(qb, RECEIVED_MESSAGES_SELECT, em, pageable);
        return messages.map(d -> new MessageSearchDto(resultAsLong(d, 0), resultAsString(d, 1), resultAsLocalDate(d, 3), resultAsString(d, 5), resultAsBoolean(d, 4)));
    }
    
    public Page<MessageReceiverSearchDto> getStudentRepresentatives(StudentSearchCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(STUDENT_PARENTS_FROM, pageable);
        qb.optionalCriteria("sg.id in :group", "group", criteria.getStudentGroupId());
        qb.filter(" sr.relation_code = 'OPPURESINDAJA_L' ");
        Page<Object[]> result = JpaQueryUtil.pagingResult(qb, STUDENT_PARENTS_SELECT, em, pageable);
        return result.map(r -> {
            Long id = resultAsLong(r, 0);
            Long studentGroupId = resultAsLong(r, 3);
            String fullname = PersonUtil.fullname(resultAsString(r, 4), resultAsString(r, 5));
            String idcode = resultAsString(r, 6);
            return new MessageReceiverSearchDto(id, fullname, studentGroupId, idcode);
        });
    }

    public Message create(HoisUserDetails user, MessageForm form) {
        Message message = new Message();
        EntityUtil.bindToEntity(form, message, classifierRepository, 
                "sender", "sendersSchool", "responseTo", "receivers");
        if(user.getSchoolId() != null) {
            message.setSendersSchool(schoolRepository.findOne(user.getSchoolId()));
        }
        message.setSender(personRepository.findOne(user.getPersonId()));
        message.setSendersRole(classifierRepository.findOne(user.getRole()));

        if(form.getResponseTo() != null) {
            Message responseTo = messageRepository.findOne(form.getResponseTo());
            responseTo.getResponses().add(message);
            message.setResponseTo(responseTo);   
        }
        saveReceivers(message, form.getReceivers());

        return messageRepository.save(message);
    }

    private void saveReceivers(Message message, Set<Long> receivers) {
        if(receivers != null) {
            Set<BigInteger> representatives = messageRepository.getRepresentativePersonIds(receivers);
            if(!CollectionUtils.isEmpty(representatives)) {
                representatives.forEach(r -> {
                    receivers.add(r.longValue());
                });
            }
            Classifier statusNew = classifierRepository.findOne("TEATESTAATUS_U");
            receivers.forEach(r -> {
                MessageReceiver receiver = new MessageReceiver();
                receiver.setStatus(statusNew);
                receiver.setPerson(personRepository.findOne(r));
                receiver.setMessage(message);
                message.getReceivers().add(receiver);
            });
        }
    }

    public void delete(Message message) {
        EntityUtil.deleteEntity(messageRepository, message);
    }

    public void setRead(Long personId, Message message) {
        MessageReceiver receiver = message.getReceivers().stream().filter(r -> EntityUtil.getId(r.getPerson()).equals(personId)).findFirst().get();
        receiver.setRead(LocalDateTime.now());
        receiver.setStatus(classifierRepository.findOne("TEATESTAATUS_L"));
        messageRepository.save(message);
    }
}
