package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

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
import ee.hitsa.ois.util.SearchUtil;
import ee.hitsa.ois.web.commandobject.MessageForm;
import ee.hitsa.ois.web.commandobject.MessageSearchCommand;
import ee.hitsa.ois.web.dto.MessageSearchDto;

@Transactional
@Service
public class MessageService {

    private static final String RECEIVED_MESSAGES_FROM = 
            "from message m inner join message_receiver mr on m.id = mr.message_id inner join person p on m.person_id = p.id ";
    private static final String RECEIVED_MESSAGES_SELECT = 
            "m.id, m.subject, m.content, m.inserted, mr.read is not null as isRead, p.firstname || ' ' || p.lastname as sendersName ";

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

        qb.optionalCriteria("m.inserted >= :sent", "sent", criteria.getSentFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("m.inserted <= :sent", "sent", criteria.getSentThru(), DateUtils::lastMomentOfDay);

        Page<Object[]> messages = JpaQueryUtil.pagingResult(qb, RECEIVED_MESSAGES_SELECT, em, pageable);
        return messages.map(d -> new MessageSearchDto(resultAsLong(d, 0), resultAsString(d, 1), resultAsLocalDate(d, 3), resultAsString(d, 5), resultAsBoolean(d, 4)));
    }

    public Message create(HoisUserDetails user, MessageForm form) {
        Message message = new Message();
        EntityUtil.bindToEntity(form, message, classifierRepository, 
                "sender", "sendersSchool", "responseTo", "receivers");
        message.setSendersSchool(schoolRepository.findOne(user.getSchoolId()));
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
