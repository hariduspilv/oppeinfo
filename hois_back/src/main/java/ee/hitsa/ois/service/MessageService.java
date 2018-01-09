package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Message;
import ee.hitsa.ois.domain.MessageReceiver;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.MessageStatus;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
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
            + "p.firstname, p.lastname, m.person_id";
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

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private EntityManager em;
    

    public Page<MessageSearchDto> show(HoisUserDetails user, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(RECEIVED_MESSAGES_FROM).sort(pageable);
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
        return messages.map(d -> new MessageSearchDto(resultAsLong(d, 0), resultAsString(d, 1), resultAsString(d, 2), resultAsLocalDateTime(d, 3), PersonUtil.fullname(resultAsString(d, 5), resultAsString(d, 6)), Boolean.FALSE, resultAsLong(d, 7)));
    }

    public Page<MessageSearchDto> searchSent(HoisUserDetails user, MessageSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from message m").sort(pageable);

        qb.requiredCriteria("m.person_id = :senderId", "senderId", user.getPersonId());
        if(!user.isMainAdmin()) {
            qb.requiredCriteria("m.school_id = :schoolId", "schoolId", user.getSchoolId());
        }
        qb.optionalCriteria("m.inserted >= :sentFrom", "sentFrom", criteria.getSentFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("m.inserted <= :sentThru", "sentThru", criteria.getSentThru(), DateUtils::lastMomentOfDay);
        qb.optionalContains("m.subject", "subject", criteria.getSubject());

        return fetchSent(qb, pageable);
    }

    public Page<MessageSearchDto> searchSentAutomatic(Long schoolId, MessageSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from message m").sort(pageable);

        qb.requiredCriteria("m.person_id = :senderId", "senderId", PersonUtil.AUTOMATIC_SENDER_ID);
        qb.requiredCriteria("m.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("m.inserted >= :sentFrom", "sentFrom", criteria.getSentFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("m.inserted <= :sentThru", "sentThru", criteria.getSentThru(), DateUtils::lastMomentOfDay);
        qb.optionalContains("m.subject", "subject", criteria.getSubject());

        return fetchSent(qb, pageable);
    }

    private Page<MessageSearchDto> fetchSent(JpaNativeQueryBuilder qb, Pageable pageable) {
        Page<MessageSearchDto> messages = JpaQueryUtil.pagingResult(qb, "m.id, m.subject, m.inserted, m.person_id", em, pageable).map(r -> {
            return new MessageSearchDto(resultAsLong(r, 0), resultAsString(r, 1), null, resultAsLocalDateTime(r, 2), null, null, resultAsLong(r, 3));
        });
        Set<Long> messageIds = StreamUtil.toMappedSet(MessageSearchDto::getId, messages.getContent());
        if(!messageIds.isEmpty()) {
            // fetch receiver names
            Query q = em.createNativeQuery("select mr.message_id, p.firstname, p.lastname from message_receiver mr join person p on mr.person_id = p.id where mr.message_id in (?1) order by p.lastname, p.firstname");
            q.setParameter(1, messageIds);
            List<?> receivers = q.getResultList();
            Map<Long, List<String>> persons = receivers.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)), Collectors.toList())));
            for(MessageSearchDto dto : messages.getContent()) {
                dto.setReceivers(persons.get(dto.getId()));
            }
        }
        return messages;
    }

    public Page<MessageSearchDto> searchReceived(HoisUserDetails user, MessageSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(RECEIVED_MESSAGES_FROM).sort(pageable);
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
        return messages.map(d -> new MessageSearchDto(resultAsLong(d, 0), resultAsString(d, 1), null, resultAsLocalDateTime(d, 3), PersonUtil.fullname(resultAsString(d, 5), resultAsString(d, 6)), resultAsBoolean(d, 4), resultAsLong(d, 7)));
    }

    public Message create(HoisUserDetails user, MessageForm form) {
        Message message = EntityUtil.bindToEntity(form, new Message(), classifierRepository,
                "sender", "sendersSchool", "responseTo", "receivers");
        message.setSendersSchool(EntityUtil.getOptionalOne(School.class, user.getSchoolId(), em));
        message.setSender(em.getReference(Person.class, user.getPersonId()));
        message.setSendersRole(em.getReference(Classifier.class, user.getRole()));

        if(form.getResponseTo() != null) {
            Message responseTo = em.getReference(Message.class, form.getResponseTo());
            responseTo.getResponses().add(message);
            message.setResponseTo(responseTo);
        }
        saveReceivers(message, form.getReceivers());

        return EntityUtil.save(message, em);
    }

    private void saveReceivers(Message message, Set<Long> receivers) {
        if(receivers != null) {
            Classifier statusNew = em.getReference(Classifier.class, MessageStatus.TEATESTAATUS_U.name());
            message.getReceivers().addAll(StreamUtil.toMappedList(r -> {
                MessageReceiver receiver = new MessageReceiver();
                receiver.setStatus(statusNew);
                receiver.setPerson(em.getReference(Person.class, r));
                return receiver;
            }, receivers));
        }
    }

    public void delete(HoisUserDetails user, Message message) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(message, em);
    }

    public void setRead(Long personId, Message message) {
        MessageReceiver receiver = message.getReceivers().stream().filter(r -> EntityUtil.getId(r.getPerson()).equals(personId)).findFirst().get();
        receiver.setRead(LocalDateTime.now());
        receiver.setStatus(em.getReference(Classifier.class, MessageStatus.TEATESTAATUS_L.name()));
        EntityUtil.save(message, em);
    }

    public List<MessageReceiverDto> getStudentRepresentatives(StudentSearchCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(STUDENT_PARENTS_FROM);
        qb.optionalCriteria("sg.id in (:group)", "group", criteria.getStudentGroupId());
        qb.filter("sr.is_student_visible = true");
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
     * The only difference between this method and PersonService.search()
     * is that this one does not require wanted person to have school_id
     */
    public List<MessageReceiverDto> searchAllUsers(HoisUserDetails user, UsersSearchCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(PERSON_FROM);
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

    /**
     * Finds following teachers:
     *  - student group teacher
     *  - higher student: teacher, whose subject was declared
     *  - vocational student: lesson plan teacher
     *  
     *  note, that student_representative.is_student_visible is considered
     */
    private List<MessageReceiverDto> searchParentsTeachers(HoisUserDetails user, UsersSearchCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(" from teacher t join person p on p.id = t.person_id ");
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getName());
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", user.getSchoolId());
        
        qb.requiredCriteria(" (exists(select s.id from student s "
                + "join student_group sg on sg.id = s.student_group_id "
                + "join student_representative sr on sr.student_id = s.id "
                + "where sg.teacher_id = t.id "
                + "and sr.is_student_visible "
                + "and sr.person_id = :personId) "
                + "or exists(select d.id "
                + "from declaration d "
                + "join declaration_subject ds on ds.declaration_id = d.id "
                + "join subject_study_period ssp on ssp.id = ds.subject_study_period_id "
                + "join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id "
                + "join student_representative sr on sr.student_id = d.student_id "
                + "where sspt.teacher_id = t.id and sr.is_student_visible and sr.person_id = :personId) "
                + "or exists(select s.id from student s "
                + "join student_group sg on sg.id = s.student_group_id "
                + "join lesson_plan lp on lp.student_group_id = sg.id "
                + "join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id "
                + "join student_representative sr on sr.student_id = s.id "
                + "where lpm.teacher_id = t.id and sr.is_student_visible and sr.person_id = :personId) )", "personId", user.getPersonId());

        List<?> result = qb.select(" distinct p.id, p.firstname, p.lastname ", em).getResultList();
        return mapUserSearchDtoPage(result, Role.ROLL_O);
    }

    /**
     * Finds following teachers:
     *  - student group teacher
     *  - higher student: teacher, whose subject was declared
     *  - vocational student: lesson plan teacher
     */
    private List<MessageReceiverDto> searchStudentsTeachers(HoisUserDetails user, UsersSearchCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(" from teacher t join person p on p.id = t.person_id ");
        qb.optionalContains("p.firstname || ' ' || p.lastname", "name", criteria.getName());

        qb.requiredCriteria(" (exists(select s.id from student s "
                + "join student_group sg on sg.id = s.student_group_id "
                + "where sg.teacher_id = t.id "
                + "and s.id = :studentId) "
                + "or exists(select d.id "
                + "from declaration d "
                + "join declaration_subject ds on ds.declaration_id = d.id "
                + "join subject_study_period ssp on ssp.id = ds.subject_study_period_id "
                + "join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id "
                + "where d.student_id = :studentId and sspt.teacher_id = t.id) "
                + " or exists(select s.id from student s "
                + "join student_group sg on sg.id = s.student_group_id "
                + "join lesson_plan lp on lp.student_group_id = sg.id "
                + "join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id "
                + "where lpm.teacher_id = t.id and s.id = :studentId) )", "studentId", user.getStudentId());

        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", user.getSchoolId());

        List<?> result = qb.select(" distinct p.id, p.firstname, p.lastname ", em).getResultList();
        return mapUserSearchDtoPage(result, Role.ROLL_O);
    }

    private List<MessageReceiverDto> searchTeachersStudents(HoisUserDetails user, UsersSearchCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(" from student s "
                + "left join person p on s.person_id = p.id left join student_group sg on sg.id = s.student_group_id "
                + "left join curriculum c on sg.curriculum_id = c.id ");
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getName());
        qb.requiredCriteria("s.school_id = :studentsSchoolId", "studentsSchoolId", user.getSchoolId());

        qb.requiredCriteria(" (sg.teacher_id = :teacherId "
                
                + "or exists( select d.id from declaration d "
                + "join declaration_subject ds on ds.declaration_id = d.id "
                + "join subject_study_period ssp on ssp.id = ds.subject_study_period_id "
                + "join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id "
                + "where sspt.teacher_id = :teacherId and d.student_id = s.id) "
                
                + "or exists( select lp.id from lesson_plan lp "
                + "join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id "
                + "where lpm.teacher_id = :teacherId "
                + "and lp.student_group_id = sg.id ) )", "teacherId", user.getTeacherId());

        List<?> result = qb.select(" distinct p.id as studentPersonId, s.id as studentId, "
                + "p.firstname, p.lastname, p.idcode, "
                + "sg.id as sgId, sg.code as sgCode, "
                + "c.id as curriculumId, c.name_et, c.name_en ", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            MessageReceiverDto dto = new MessageReceiverDto();
            dto.setPersonId(resultAsLong(r, 0));
            dto.setId(resultAsLong(r, 1));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3)));
            dto.setRole(Arrays.asList(Role.ROLL_T.name()));
            AutocompleteResult studentGroup = new AutocompleteResult(resultAsLong(r, 5), resultAsString(r, 6), resultAsString(r, 6));
            dto.setStudentGroup(studentGroup);
            AutocompleteResult curriculum = new AutocompleteResult(resultAsLong(r, 7), resultAsString(r, 8), resultAsString(r, 9));
            dto.setCurriculum(curriculum);
            return dto;
        }, result);
    }

    private List<MessageReceiverDto> searchTeachersParents(HoisUserDetails user, UsersSearchCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(" from student_representative sr "
                + "join person p on sr.person_id = p.id "
                + "join student s on s.id = sr.student_id "
                + "left join student_group sg on sg.id = s.student_group_id "
                + "left join curriculum c on sg.curriculum_id = c.id ");
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getName());
        qb.requiredCriteria("s.school_id = :studentsSchoolId", "studentsSchoolId", user.getSchoolId());
        
        qb.requiredCriteria(" (sg.teacher_id = :teacherId "
                
                + "or exists( select d.id from declaration d "
                + "join declaration_subject ds on ds.declaration_id = d.id "
                + "join subject_study_period ssp on ssp.id = ds.subject_study_period_id "
                + "join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id "
                + "where sspt.teacher_id = :teacherId and d.student_id = s.id) "
                
                + "or exists( select lp.id from lesson_plan lp "
                + "join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id "
                + "where lpm.teacher_id = :teacherId "
                + "and lp.student_group_id = sg.id ) )", "teacherId", user.getTeacherId());
        
        List<?> result = qb.select("distinct p.id as repPersonId, p.firstname, "
                + "p.lastname, p.idcode,"
                + "sg.id as sgId, sg.code, "
                + "c.id as curriculumId, c.name_et, c.name_en, "
                + "s.id as studentId", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            MessageReceiverDto dto = new MessageReceiverDto();
            dto.setPersonId(resultAsLong(r, 0));
            dto.setId(resultAsLong(r, 9));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
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
            dto.setRole(Arrays.asList(role.name()));
            return dto;
        }, result);
    }

    public List<MessageReceiverDto> getStudents(Long schoolId, StudentSearchCommand criteria, Pageable pageable) {
        List<MessageReceiverDto> students = studentService.search(schoolId, criteria, pageable)
                .map(MessageReceiverDto::of).getContent();
        List<Long> studentIds = StreamUtil.toMappedList(MessageReceiverDto::getId, students);
        if(!studentIds.isEmpty()) {
            List<MessageReceiverDto> parents = studentRepresentatives(studentIds);
            if(!parents.isEmpty()) {
                students = new ArrayList<>(students);
                students.addAll(parents);
            }
        }
        return students;
    }

    private List<MessageReceiverDto> studentRepresentatives(List<Long> studentIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(STUDENT_PARENTS_FROM);
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
