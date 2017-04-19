package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
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
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Message;
import ee.hitsa.ois.domain.MessageReceiver;
import ee.hitsa.ois.enums.MessageStatus;
import ee.hitsa.ois.enums.Role;
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
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.MessageForm;
import ee.hitsa.ois.web.commandobject.MessageSearchCommand;
import ee.hitsa.ois.web.commandobject.UsersSearchCommand;
import ee.hitsa.ois.web.commandobject.student.StudentSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.MessageReceiverDto;
import ee.hitsa.ois.web.dto.MessageSearchDto;

@Transactional
@Service
public class MessageService {

    private static final String RECEIVED_MESSAGES_FROM =
            " from message m inner join message_receiver mr on m.id = mr.message_id inner join person p on m.person_id = p.id ";
    private static final String RECEIVED_MESSAGES_SELECT =
            " m.id, m.subject, m.content, m.inserted, mr.read is not null as isRead, "
            + "p.firstname, p.lastname";
    private static final String STUDENT_PARENTS_FROM =
              " from student s "
            + "inner join student_group sg on s.student_group_id = sg.id "
            + "inner join student_representative sr on s.id = sr.student_id "
            + "inner join person p on p.id = sr.person_id "
            + "inner join curriculum c on sg.curriculum_id = c.id";
    private static final String STUDENT_PARENTS_SELECT =
            " sg.id as studentGroupId, sg.code as studentGroupCode, "
            + "s.id as studentId, s.study_form_code as studyForm, "
            + "sr.person_id as representativesId, "
            + "p.firstname, p.lastname, p.idcode,"
            + "c.id as curriculumId, c.name_et as curriculumNameEt, c.name_en as curriculumNameEn ";
    private static final String PERSON_FROM =
             " from user_ u "
            + "left outer join person p on u.person_id = p.id "
            + "left join school s on s.id = u.school_id ";
    private static final String PERSON_SELECT =
            " distinct u.id, p.id as personId, p.firstname, p.lastname, p.idcode, u.role_code, u.student_id ";
    private static final String STUDENT_PERSON_TEACHER_FROM =
            " from student s "
            + "left join person p1 on s.person_id = p1.id "
            + "left join student_group sg on sg.id = s.student_group_id "
            + "left join student_representative sr on sr.student_id = s.id "
            + "left join teacher t on t.id = sg.teacher_id "
            + "left join person p2 on p2.id = sr.person_id "
            + "left join person p3 on p3.id = t.person_id "
            + "left join curriculum c on sg.curriculum_id = c.id";
    private static final String SELECT_TEACHERS = "distinct p3.id as teacherPersonId, p3.firstname as teacherFirstname, "
                + "p3.lastname as teacherLastname, p3.idcode as teacherIdcode";
    private static final String SELECT_STUDENTS = "distinct p1.id as studentPersonId, s.id as studentId"
                + "p1.firstname as studFirstname, p1.lastname as studLastname, p1.idcode as studIdcode, "
                + "sg.id as sgId, sg.code as sgCode, "
                + "c.id as curriculumId, c.name_et as curriculumNameEt, c.name_en as curriculumNameEn";
    private static final String SELECT_PARENTS = "distinct p2.id as repPersonId, p2.firstname as repFirstname, "
                + "p2.lastname as repLastname, p2.idcode as repIdcode,"
                + "sg.id as sgId, sg.code as sgCode, "
                + "c.id as curriculumId, c.name_et as curriculumNameEt, c.name_en as curriculumNameEn, "
                + "s.id as studentId";
    private static final String STUDENT_REQUIRES_REPRESENTATIVE = String.format("(date_part('year', age(p.birthdate)) < %d OR s.special_need_code is not null)",
            Integer.valueOf(PersonUtil.ADULT_YEARS));

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private EntityManager em;
    

    public Page<MessageSearchDto> show(HoisUserDetails user, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(RECEIVED_MESSAGES_FROM, pageable);
        qb.requiredCriteria("mr.person_id = :personId", "personId", user.getPersonId());
        /**
         * Usually users can only view messages sent by others from the same school.
         * However, they also need to have possibility to see messages from users with no school,
         * such as main administrator.
         */
        if(!user.isMainAdmin()) {
            qb.requiredCriteria("(m.school_id is null OR m.school_id = :schoolId)", "schoolId", user.getSchoolId());
        }
        qb.filter("mr.read is null");
        Page<Object[]> messages = JpaQueryUtil.pagingResult(qb, RECEIVED_MESSAGES_SELECT, em, pageable);
        return messages.map(d -> new MessageSearchDto(resultAsLong(d, 0), resultAsString(d, 1), resultAsString(d, 2), resultAsLocalDateTime(d, 3), PersonUtil.fullname(resultAsString(d, 5), resultAsString(d, 6)), Boolean.FALSE));
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
    
    public Page<MessageSearchDto> searchSentAutomatic(Long schoolId, MessageSearchCommand criteria, Pageable pageable) {
        return messageRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("sender").get("id"), PersonUtil.AUTOMATIC_SENDER_ID));
            filters.add(cb.equal(root.get("sendersSchool").get("id"), schoolId));
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

    public Page<MessageSearchDto> searchReceived(HoisUserDetails user, MessageSearchCommand criteria, Pageable pageable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(RECEIVED_MESSAGES_FROM, pageable);
        qb.requiredCriteria("mr.person_id = :personId", "personId", user.getPersonId());
        /**
         * Usually users can only view messages sent by others from the same school.
         * However, they also need to have possibility to see messages from users with no school,
         * such as main administrator.
         */
        if(!user.isMainAdmin()) {
            qb.requiredCriteria("(m.school_id is null OR m.school_id = :schoolId)", "schoolId", user.getSchoolId());
        }
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getSender());
        qb.optionalContains("m.subject", "subject", criteria.getSubject());
        qb.optionalCriteria("m.inserted >= :sentFrom", "sentFrom", criteria.getSentFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("m.inserted <= :sentThru", "sentThru", criteria.getSentThru(), DateUtils::lastMomentOfDay);
        Page<Object[]> messages = JpaQueryUtil.pagingResult(qb, RECEIVED_MESSAGES_SELECT, em, pageable);
        return messages.map(d -> new MessageSearchDto(resultAsLong(d, 0), resultAsString(d, 1), resultAsLocalDateTime(d, 3), PersonUtil.fullname(resultAsString(d, 5), resultAsString(d, 6)), resultAsBoolean(d, 4)));
    }

    public Message create(HoisUserDetails user, MessageForm form) {
        Message message = new Message();
        EntityUtil.bindToEntity(form, message, classifierRepository,
                "sender", "sendersSchool", "responseTo", "receivers");
        if(user.getSchoolId() != null) {
            message.setSendersSchool(schoolRepository.getOne(user.getSchoolId()));
        }
        message.setSender(personRepository.getOne(user.getPersonId()));
        message.setSendersRole(classifierRepository.getOne(user.getRole()));

        if(form.getResponseTo() != null) {
            Message responseTo = messageRepository.getOne(form.getResponseTo());
            responseTo.getResponses().add(message);
            message.setResponseTo(responseTo);
        }
        saveReceivers(message, form.getReceivers());

        return messageRepository.save(message);
    }

    private void saveReceivers(Message message, Set<Long> receivers) {
        if(receivers != null) {
//            receivers.addAll(getRepresentativePersonIds(receivers));

            Classifier statusNew = classifierRepository.getOne(MessageStatus.TEATESTAATUS_U.name());
            message.getReceivers().addAll(StreamUtil.toMappedList(r -> {
                MessageReceiver receiver = new MessageReceiver();
                receiver.setStatus(statusNew);
                receiver.setPerson(personRepository.getOne(r));
                return receiver;
            }, receivers));
        }
    }
    /**
     * Now method is not used. All representatives are sent to front end 
     */
//    public Set<Long> getRepresentativePersonIds(Set<Long> receiversIds) {
//        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from student s " +
//                "inner join person p on p.id = s.person_id " +
//                "inner join student_representative sr on sr.student_id = s.id");
//        qb.filter(STUDENT_REQUIRES_REPRESENTATIVE);
//        qb.requiredCriteria("p.id in (:personId)", "personId", receiversIds);
//        List<?> data = qb.select("distinct sr.person_id", em).getResultList();
//        return StreamUtil.toMappedSet(r -> Long.valueOf(((Number)r).longValue()), data);
//    }

    public void delete(Message message) {
        EntityUtil.deleteEntity(messageRepository, message);
    }

    public void setRead(Long personId, Message message) {
        MessageReceiver receiver = message.getReceivers().stream().filter(r -> EntityUtil.getId(r.getPerson()).equals(personId)).findFirst().get();
        receiver.setRead(LocalDateTime.now());
        receiver.setStatus(classifierRepository.getOne(MessageStatus.TEATESTAATUS_L.name()));
        messageRepository.save(message);
    }

    public List<MessageReceiverDto> getStudentRepresentatives(StudentSearchCommand criteria) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(STUDENT_PARENTS_FROM);
        qb.optionalCriteria("sg.id in (:group)", "group", criteria.getStudentGroupId());
        List<?> result = qb.select(STUDENT_PARENTS_SELECT, em).getResultList();
        return StreamUtil.toMappedList(r -> {
            Long id = resultAsLong(r, 3);
            String fullname = PersonUtil.fullname(resultAsString(r, 4), resultAsString(r, 5));
            MessageReceiverDto dto = new MessageReceiverDto();
            dto.setPersonId(id);
            dto.setFullname(fullname);
            AutocompleteResult studentGroup = new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 2), resultAsString(r, 2));
            dto.setStudentGroup(studentGroup);
            return dto;
        }, result);
    }

    public List<MessageReceiverDto> searchPersons(HoisUserDetails user, UsersSearchCommand criteria) {
        if(user.isSchoolAdmin()) {
            return searchAllUsers(user, criteria);
        }
        if (user.isTeacher()) {
            if(Role.ROLL_T.name().equals(criteria.getRole())) {
                return searchTeachersStudents(user, criteria);
            }
            if (Role.ROLL_L.name().equals(criteria.getRole())) {
                return searchTeachersParents(user, criteria);
            }
        } else if (user.isRepresentative()) {
            return searchParentsTeachers(user, criteria);
        } else if(user.isStudent()) {
            return searchStudentsTeachers(user, criteria);
        }
        return null;
    }

    /**
     * TODO:
     * The only difference between this method and PersonService.search()
     * is that this one does not require wanted person to have school_id
     */
    public List<MessageReceiverDto> searchAllUsers(HoisUserDetails user, UsersSearchCommand criteria) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(PERSON_FROM);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getName());
        if(criteria.getRole() != null && !Role.ROLL_P.name().equals(criteria.getRole()) ) {
            qb.requiredCriteria("s.id = :schoolId", "schoolId", user.getSchoolId());
         }
        qb.optionalCriteria("u.role_code = :role", "role", criteria.getRole());
        List<?> result = qb.select(PERSON_SELECT, em).getResultList();
        return StreamUtil.toMappedList(r -> {
            MessageReceiverDto dto = new MessageReceiverDto();
            dto.setPersonId(resultAsLong(r, 1));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3)));
            dto.setIdcode(resultAsString(r, 4));
            dto.setRole(Arrays.asList(resultAsString(r, 5)));
            dto.setId(resultAsLong(r, 6));
            return dto;
        }, result);
    }

    public List<MessageReceiverDto> searchParentsTeachers(HoisUserDetails user, UsersSearchCommand criteria) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(STUDENT_PERSON_TEACHER_FROM);
        qb.optionalContains(Arrays.asList("p3.firstname", "p3.lastname", "p3.firstname || ' ' || p3.lastname"), "name", criteria.getName());
        qb.requiredCriteria("p2.id = :parentsPersonId", "parentsPersonId", user.getPersonId());
        qb.requiredCriteria("s.school_id = :studentsSchoolId", "studentsSchoolId", user.getSchoolId());
        List<?> result = qb.select(SELECT_TEACHERS, em).getResultList();
        return mapUserSearchDtoPage(result, Role.ROLL_O);
    }

    public List<MessageReceiverDto> searchStudentsTeachers(HoisUserDetails user, UsersSearchCommand criteria) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(STUDENT_PERSON_TEACHER_FROM);
        qb.optionalContains(Arrays.asList("p3.firstname", "p3.lastname", "p3.firstname || ' ' || p3.lastname"), "name", criteria.getName());
        qb.requiredCriteria("p1.id = :studentsPersonId", "studentsPersonId", user.getPersonId());
        qb.requiredCriteria("s.school_id = :studentsSchoolId", "studentsSchoolId", user.getSchoolId());
        List<?> result = qb.select(SELECT_TEACHERS, em).getResultList();
        return mapUserSearchDtoPage(result, Role.ROLL_O);
    }

    public List<MessageReceiverDto> searchTeachersStudents(HoisUserDetails user, UsersSearchCommand criteria) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(STUDENT_PERSON_TEACHER_FROM);
        qb.optionalContains(Arrays.asList("p1.firstname", "p1.lastname", "p1.firstname || ' ' || p1.lastname"), "name", criteria.getName());
        qb.requiredCriteria("p3.id = :teachersPersonId", "teachersPersonId", user.getPersonId());
        qb.requiredCriteria("s.school_id = :studentsSchoolId", "studentsSchoolId", user.getSchoolId());
        List<?> result = qb.select(SELECT_STUDENTS, em).getResultList();
        return StreamUtil.toMappedList(r -> {
            MessageReceiverDto dto = new MessageReceiverDto();
            dto.setPersonId(resultAsLong(r, 0));
            dto.setId(resultAsLong(r, 1));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3)));
            dto.setIdcode(resultAsString(r, 4));
            dto.setRole(Arrays.asList(Role.ROLL_T.name()));
            AutocompleteResult studentGroup = new AutocompleteResult(resultAsLong(r, 5), resultAsString(r, 6), resultAsString(r, 6));
            dto.setStudentGroup(studentGroup);
            AutocompleteResult curriculum = new AutocompleteResult(resultAsLong(r, 7), resultAsString(r, 8), resultAsString(r, 9));
            dto.setCurriculum(curriculum);
            return dto;
        }, result);
    }

    public List<MessageReceiverDto> searchTeachersParents(HoisUserDetails user, UsersSearchCommand criteria) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(STUDENT_PERSON_TEACHER_FROM);
        qb.optionalContains(Arrays.asList("p2.firstname", "p2.lastname", "p2.firstname || ' ' || p2.lastname"), "name", criteria.getName());
        qb.requiredCriteria("p3.id = :teachersPersonId", "teachersPersonId", user.getPersonId());
        qb.requiredCriteria("s.school_id = :studentsSchoolId", "studentsSchoolId", user.getSchoolId());
        List<?> result = qb.select(SELECT_PARENTS, em).getResultList();
        return StreamUtil.toMappedList(r -> {
            MessageReceiverDto dto = new MessageReceiverDto();
            dto.setPersonId(resultAsLong(r, 0));
            dto.setId(resultAsLong(r, 9));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setIdcode(resultAsString(r, 3));
            dto.setRole(Arrays.asList(Role.ROLL_L.name()));
            AutocompleteResult studentGroup = new AutocompleteResult(resultAsLong(r, 4), resultAsString(r, 5), resultAsString(r, 5));
            dto.setStudentGroup(studentGroup);
            AutocompleteResult curriculum = new AutocompleteResult(resultAsLong(r, 6), resultAsString(r, 7), resultAsString(r, 8));
            dto.setCurriculum(curriculum);            
            return dto;
        }, result);
    }

    private static List<MessageReceiverDto> mapUserSearchDtoPage(List<?> result, Role role) {
        return StreamUtil.toMappedList(r -> {
            MessageReceiverDto dto = new MessageReceiverDto();
            dto.setPersonId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setIdcode(resultAsString(r, 3));
            dto.setRole(Arrays.asList(role.name()));
            return dto;
        }, result);
    }

    public List<MessageReceiverDto> getStudents(Long schoolId, StudentSearchCommand criteria, Pageable pageable) {
        List<MessageReceiverDto> students = studentService.search(schoolId, criteria, pageable)
                .map(MessageReceiverDto::of).getContent();
        List<Long> studentIds = StreamUtil.toMappedList(MessageReceiverDto::getId, students);
        if(!studentIds.isEmpty()) {
            List<MessageReceiverDto> parents = getStudentRepresentatives(studentIds);
            if(!CollectionUtils.isEmpty(parents)) {
                students = new ArrayList<>(students);
                students.addAll(parents);
            }
        }
        return students;
    }

    private List<MessageReceiverDto> getStudentRepresentatives(List<Long> studentIds) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(STUDENT_PARENTS_FROM);
        qb.optionalCriteria("sr.student_id in (:studentIds)", "studentIds", studentIds);
        qb.filter("sr.is_student_visible is true");
        List<?> result = qb.select(STUDENT_PARENTS_SELECT, em).getResultList();

        return StreamUtil.toMappedList(r -> {
            MessageReceiverDto dto = new MessageReceiverDto();
            Long studentId = resultAsLong(r, 2);
            String studyForm = resultAsString(r, 3);
            Long personId = resultAsLong(r, 4);

            String fullname = PersonUtil.fullname(resultAsString(r, 5), resultAsString(r, 6));
            AutocompleteResult studentGroup = new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 1));
            AutocompleteResult curriculum = new AutocompleteResult(resultAsLong(r, 8), resultAsString(r, 9), resultAsString(r, 10));

            dto.setId(studentId);
            dto.setPersonId(personId);
            dto.setFullname(fullname);
            dto.setStudentGroup(studentGroup);
            dto.setCurriculum(curriculum);
            dto.setStudyForm(studyForm);
            dto.setRole(Arrays.asList(Role.ROLL_L.name()));
            return dto;
        }, result);
    }
}
