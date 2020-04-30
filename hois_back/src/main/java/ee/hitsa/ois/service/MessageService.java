package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Message;
import ee.hitsa.ois.domain.MessageReceiver;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.student.StudentRepresentative;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodStudentGroup;
import ee.hitsa.ois.enums.MessageStatus;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.web.commandobject.MessageForm;
import ee.hitsa.ois.web.commandobject.MessageForm.Receiver;
import ee.hitsa.ois.web.commandobject.MessageSearchCommand;
import ee.hitsa.ois.web.commandobject.UsersSearchCommand;
import ee.hitsa.ois.web.commandobject.student.StudentGroupSearchCommand;
import ee.hitsa.ois.web.commandobject.student.StudentSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.MessageReceiverDto;
import ee.hitsa.ois.web.dto.MessageSearchDto;
import ee.hitsa.ois.web.dto.SubjectDto;
import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;
import ee.hitsa.ois.web.dto.studymaterial.JournalDto;

@Transactional
@Service
public class MessageService {

    private static final String RECEIVED_MESSAGES_FROM =
            " from message m join message_receiver mr on m.id = mr.message_id join person p on m.person_id = p.id ";
    private static final String RECEIVED_MESSAGES_SELECT =
            " m.id, m.subject, m.content, m.inserted, mr.read is not null as isRead, "
            + "p.firstname, p.lastname, m.person_id";
    private static final String STUDENT_PARENTS_FROM =
              " from student s "
            + "join student_group sg on s.student_group_id = sg.id "
            + "join student_representative sr on s.id = sr.student_id "
            + "join person p on p.id = sr.person_id "
            + "join curriculum c on sg.curriculum_id = c.id ";
    private static final String STUDENT_PARENTS_SELECT =
            " sg.id as studentGroupId, sg.code as studentGroupCode, "
            + "s.id as studentId, s.study_form_code as studyForm, "
            + "sr.person_id as representativesId, "
            + "p.firstname, p.lastname, p.idcode,"
            + "c.id as curriculumId, c.name_et as curriculumNameEt, c.name_en as curriculumNameEn, c.is_higher ";

    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private EntityManager em;
    @Autowired
    private MailService mailService;
    

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
        qb.optionalContains("p.firstname || ' ' || p.lastname", "name", criteria.getSender());
        qb.optionalContains("m.subject", "subject", criteria.getSubject());
        qb.optionalCriteria("m.inserted >= :sentFrom", "sentFrom", criteria.getSentFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("m.inserted <= :sentThru", "sentThru", criteria.getSentThru(), DateUtils::lastMomentOfDay);
        Page<Object[]> messages = JpaQueryUtil.pagingResult(qb, RECEIVED_MESSAGES_SELECT, em, pageable);
        return messages.map(d -> new MessageSearchDto(resultAsLong(d, 0), resultAsString(d, 1), null, resultAsLocalDateTime(d, 3), PersonUtil.fullname(resultAsString(d, 5), resultAsString(d, 6)), resultAsBoolean(d, 4), resultAsLong(d, 7)));
    }

    public Map<String, Long> unreadReceivedCount(HoisUserDetails user) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from message m join message_receiver mr on m.id = mr.message_id");
        qb.requiredCriteria("mr.person_id = :personId", "personId", user.getPersonId());
        if(!user.isMainAdmin()) {
            qb.requiredCriteria("(m.school_id is null OR m.school_id = :schoolId)", "schoolId", user.getSchoolId());
        }
        qb.filter("mr.read is null");

        Number unreadCount = qb.count(em);
        return Collections.singletonMap("unread", Long.valueOf(unreadCount.longValue()));
    }

    public Message create(HoisUserDetails user, MessageForm form) {
        Message message = EntityUtil.bindToEntity(form, new Message(), classifierRepository,
                "sender", "sendersSchool", "responseTo", "receivers");
        message.setSendersSchool(EntityUtil.getOptionalOne(School.class, user.getSchoolId(), em));
        message.setSender(em.getReference(Person.class, user.getPersonId()));
        message.setSendersRole(em.getReference(Classifier.class, user.getRole()));
        
        Student student = null;
        Teacher teacher = null;
        if (user.isStudent()) {
            student = em.getReference(Student.class, user.getStudentId());
        } else if (user.isTeacher()) {
            teacher = em.getReference(Teacher.class, user.getTeacherId());
        }
        
        final String emailSender = student != null 
                ? (student.getEmail() != null ? student.getEmail() : message.getSender().getEmail())
                : (teacher != null ? (teacher.getEmail() != null ? teacher.getEmail() : message.getSender().getEmail()) : message.getSender().getEmail());

        if(form.getResponseTo() != null) {
            Message responseTo = em.getReference(Message.class, form.getResponseTo());
            responseTo.getResponses().add(message);
            message.setResponseTo(responseTo);
        }
        if (form.getReceivers() != null && !form.getReceivers().isEmpty()) {
            List<Receiver> withoutRole = form.getReceivers().stream().filter(r -> r.getRole() == null).collect(Collectors.toList());
            form.getReceivers().removeAll(withoutRole);
            
            Map<String, Set<Long>> receiversByRole = form.getReceivers().stream()
                    .collect(Collectors.groupingBy(MessageForm.Receiver::getRole,
                            Collectors.mapping(MessageForm.Receiver::getPerson, Collectors.toSet())));
            Set<String> emails = new LinkedHashSet<>();
            Set<Long> personIds = withoutRole.stream().map(r -> r.getPerson()).collect(Collectors.toCollection(LinkedHashSet::new));

            LocalDate now = LocalDate.now();
            if (receiversByRole.containsKey(Role.ROLL_T.name())) {
                // Every student should be active
                List<?> studentEmails = em.createNativeQuery("select coalesce(s.email, p.email) "
                        + "from student s "
                        + "join person p on p.id = s.person_id "
                        + "join user_ u on u.student_id = s.id "
                        + "where s.status_code in ?1 and (u.valid_from is null or u.valid_from <= ?2) and (u.valid_thru is null or u.valid_thru >= ?3) and p.id in ?4 and s.school_id = ?5 "
                        + "group by p.id, s.id")
                        .setParameter(1, StudentStatus.STUDENT_STATUS_ACTIVE)
                        .setParameter(2, JpaQueryUtil.parameterAsTimestamp(now.atTime(LocalTime.MIN)))
                        .setParameter(3, JpaQueryUtil.parameterAsTimestamp(now.atTime(LocalTime.MAX)))
                        .setParameter(4, receiversByRole.get(Role.ROLL_T.name()))
                        .setParameter(5, user.getSchoolId())
                        .getResultList();
                emails.addAll(StreamUtil.toMappedSet(r -> resultAsString(r, 0), studentEmails));
                personIds.addAll(receiversByRole.get(Role.ROLL_T.name()));
                receiversByRole.remove(Role.ROLL_T.name());
            }
            if (receiversByRole.containsKey(Role.ROLL_O.name())) {
                // Every teacher should be active
                List<?> teacherEmails = em.createNativeQuery("select coalesce(t.email, p.email) "
                        + "from teacher t "
                        + "join person p on p.id = t.person_id "
                        + "join user_ u on u.teacher_id = t.id "
                        + "where t.is_active and (u.valid_from is null or u.valid_from <= ?1) and (u.valid_thru is null or u.valid_thru >= ?2) and p.id in ?3 and t.school_id = ?4 "
                        + "group by p.id, t.id")
                        .setParameter(1, JpaQueryUtil.parameterAsTimestamp(now.atTime(LocalTime.MIN)))
                        .setParameter(2, JpaQueryUtil.parameterAsTimestamp(now.atTime(LocalTime.MAX)))
                        .setParameter(3, receiversByRole.get(Role.ROLL_O.name()))
                        .setParameter(4, user.getSchoolId())
                        .getResultList();
                emails.addAll(StreamUtil.toMappedSet(r -> JpaQueryUtil.resultAsString(r, 0), teacherEmails));
                personIds.addAll(receiversByRole.get(Role.ROLL_O.name()));
                receiversByRole.remove(Role.ROLL_O.name());
            }
            
            Set<Long> otherPersonIds = receiversByRole.values().stream().flatMap(pSet -> pSet.stream()).collect(Collectors.toSet());
            
            // Request every person from DB to set it for MessageReceiver.
            personIds.addAll(otherPersonIds);
            List<Person> persons = em.createQuery("select p from Person p where p.id in ?1", Person.class).setParameter(1, personIds).getResultList();
            
            // Add leftover emails
            emails.addAll(persons.stream().filter(p -> otherPersonIds.contains(p.getId())).map(Person::getEmail).collect(Collectors.toSet()));
            
            saveReceivers(message, persons);
            // email should be sent personally to hide everyone's email.
            emails.forEach(email -> {
                mailService.sendMail(message, emailSender, Collections.singleton(email));
            });
        }

        return EntityUtil.save(message, em);
    }

    private void saveReceivers(Message message, List<Person> receivers) {
        if(receivers != null) {
            Classifier statusNew = em.getReference(Classifier.class, MessageStatus.TEATESTAATUS_U.name());
            message.getReceivers().addAll(StreamUtil.toMappedList(r -> {
                MessageReceiver receiver = new MessageReceiver();
                receiver.setStatus(statusNew);
                receiver.setPerson(r);
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

    public List<MessageReceiverDto> getStudentRepresentatives(Student student) {
        StudentGroup studentGroup = student.getStudentGroup();

        final List<MessageReceiverDto> results = new ArrayList<>();
        final List<JournalStudent> journalStudents = student.getJournalStudents();
        final List<SubjectStudyPeriodStudentGroup> subjectStudyPeriods = studentGroup != null
                ? studentGroup.getSubjectStudyPeriods() : new ArrayList<>();

        for (StudentRepresentative studentRepresentative : student.getRepresentatives().stream()
        .filter(sr -> Boolean.TRUE.equals(sr.getIsStudentVisible())).collect(Collectors.toList())) {
            // TODO: Change Journal variable in MessageReceiverDto so it could be a list with all journals. Same for subjects
            for (int i = 0; i < journalStudents.size() || i < subjectStudyPeriods.size() || i < 1; i++) {
                MessageReceiverDto dto = new MessageReceiverDto();
                dto.setId(student.getId());
                dto.setPersonId(studentRepresentative.getPerson().getId());
                dto.setFullname(studentRepresentative.getPerson().getFullname());
                dto.setHigher(Boolean.valueOf(StudentUtil.isHigher(student)));

                if (studentGroup != null) {
                    dto.setStudentGroup(AutocompleteResult.of(studentGroup));
                    Curriculum curriculum = student.getStudentGroup().getCurriculum();
                    dto.setCurriculum(curriculum != null ? AutocompleteResult.of(curriculum) : null);
                }

                dto.setRole(Arrays.asList(Role.ROLL_L.name()));
                if (i < journalStudents.size())
                    dto.setJournal(AutocompleteResult.of(journalStudents.get(i).getJournal()));
                if (i < subjectStudyPeriods.size())
                    dto.setSubject(AutocompleteResult.of(subjectStudyPeriods.get(i).getSubjectStudyPeriod().getSubject()));
                results.add(dto);
            }
        }
        
        return results;
    }

    public List<MessageReceiverDto> getStudentRepresentatives(StudentSearchCommand criteria) {
        StringBuilder select = new StringBuilder(STUDENT_PARENTS_SELECT);
        StringBuilder from = new StringBuilder(STUDENT_PARENTS_FROM);
        final boolean isJournalIds = !CollectionUtils.isEmpty(criteria.getJournalId()); 
        final boolean isSubjectUsed = !CollectionUtils.isEmpty(criteria.getSubjectId());
        if (isJournalIds) {
            select.append(", j.id as journal_id, j.name_et as journal_name");
            from.append("left join journal_student js on js.student_id = s.id ");
            from.append("left join journal j on j.id = js.journal_id ");
        }
        if (isSubjectUsed) {
            select.append(", sj.id as subject_id, sj.name_et as subject_name_et, sj.name_en as subject_name_en");
            from.append("left join declaration d on d.student_id = s.id ");
            from.append("left join declaration_subject ds on ds.declaration_id = d.id ");
            from.append("left join subject_study_period ssp on ssp.id = ds.subject_study_period_id ");
            from.append("left join subject sj on sj.id = ssp.subject_id ");
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString());
        qb.optionalCriteria("sg.id in (:group)", "group", criteria.getStudentGroupId());
        qb.optionalCriteria("j.id in (:journalIds)", "journalIds", criteria.getJournalId());
        qb.optionalCriteria("sj.id in :subjectIds", "subjectIds", criteria.getSubjectId());
        qb.filter("sr.is_student_visible = true");
        List<?> result = qb.select(select.toString(), em).getResultList();
        return StreamUtil.toMappedList(r -> {
            MessageReceiverDto dto = new MessageReceiverDto();
            dto.setId(resultAsLong(r, 2));
            dto.setPersonId(resultAsLong(r, 4));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 5), resultAsString(r, 6)));
            dto.setHigher(resultAsBoolean(r, 11));
            String studentGroupCode = resultAsString(r, 1);
            dto.setStudentGroup(new AutocompleteResult(resultAsLong(r, 0), studentGroupCode, studentGroupCode));
            dto.setCurriculum(new AutocompleteResult(resultAsLong(r, 8), resultAsString(r, 9), resultAsString(r, 10)));
            if (isJournalIds) {
                dto.setJournal(new AutocompleteResult(resultAsLong(r, 12), resultAsString(r, 13), resultAsString(r, 13)));
                if (isSubjectUsed) {
                    dto.setSubject(new AutocompleteResult(resultAsLong(r, 14), resultAsString(r, 15), resultAsString(r, 16)));
                }
            } else {
                if (isSubjectUsed) {
                    dto.setSubject(new AutocompleteResult(resultAsLong(r, 12), resultAsString(r, 13), resultAsString(r, 14)));
                }
            }
            return dto;
        }, result);
    }

    public List<MessageReceiverDto> searchPersons(HoisUserDetails user, UsersSearchCommand criteria) {
        if (user.isSchoolAdmin()) {
            return searchAllUsers(user, criteria);
        } else if (user.isLeadingTeacher()) {
            return searchLeadingTeacherUsers(user, criteria);
        } else if (user.isTeacher()) {
            if (Role.ROLL_T.name().equals(criteria.getRole())) {
                return searchTeachersStudents(user, criteria);
            }
            if (Role.ROLL_L.name().equals(criteria.getRole())) {
                return searchTeachersParents(user, criteria);
            }
        } else if (user.isRepresentative()) {
            return searchParentsTeachers(user, criteria);
        } else if (user.isStudent()) {
            return searchStudentsTeachers(user, criteria);
        }
        return null;
    }

    /**
     * The only difference between this method and PersonService.search()
     * is that this one does not require wanted person to have school_id
     */
    private List<MessageReceiverDto> searchAllUsers(HoisUserDetails user, UsersSearchCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from user_ u join "
                + "person p on u.person_id = p.id "
                + "left join student s on s.id = u.student_id").sort("p.lastname", "p.firstname");
        qb.optionalContains("p.firstname || ' ' || p.lastname", "name", criteria.getName());
        if(criteria.getRole() != null && !Role.ROLL_P.name().equals(criteria.getRole()) ) {
            qb.requiredCriteria("u.school_id = :schoolId", "schoolId", user.getSchoolId());
        }
        String sql = "distinct p.id, p.firstname, p.lastname, p.idcode, u.role_code";
        boolean isstudent = Role.ROLL_T.name().equals(criteria.getRole());
        if(isstudent) {
            // students, search only active ones
            qb.requiredCriteria("s.status_code in (:active)", "active", StudentStatus.STUDENT_STATUS_ACTIVE);
            sql += ", u.student_id, s.type_code";
        }
        qb.optionalCriteria("u.role_code = :role", "role", criteria.getRole());
        List<?> result = qb.select(sql, em).getResultList();
        return mapMessageReceivers(result, isstudent);
    }

    private static List<MessageReceiverDto> mapMessageReceivers(List<?> result, boolean isstudent) {
        return StreamUtil.toMappedList(r -> {
            MessageReceiverDto dto = new MessageReceiverDto();
            dto.setPersonId(resultAsLong(r, 0));
            dto.setIdcode(resultAsString(r, 3));
            dto.setRole(Arrays.asList(resultAsString(r, 4)));
            if (isstudent) {
                dto.setId(resultAsLong(r, 5));
                dto.setFullname(PersonUtil.fullnameOptionalGuest(resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 6)));
            } else {
                dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            }
            return dto;
        }, result);
    }

    private List<MessageReceiverDto> searchLeadingTeacherUsers(HoisUserDetails user, UsersSearchCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from user_ u"
                + " join person p on u.person_id = p.id"
                + " join student s on s.id = u.student_id"
                + " join curriculum_version cv on cv.id = s.curriculum_version_id");
        qb.optionalContains("p.firstname || ' ' || p.lastname", "name", criteria.getName());
        qb.requiredCriteria("s.status_code in (:activeStudents)", "activeStudents",
                StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.requiredCriteria("cv.curriculum_id in (:userCurriculumIds)", "userCurriculumIds", user.getCurriculumIds());
        qb.optionalCriteria("u.role_code = :role", "role", criteria.getRole());

        String select = "distinct p.id, p.firstname, p.lastname, p.idcode, u.role_code";
        boolean isstudent = Role.ROLL_T.name().equals(criteria.getRole());
        if (isstudent) {
            select += ", u.student_id, s.type_code";
        }

        qb.sort("p.lastname", "p.firstname");
        List<?> result = qb.select(select, em).getResultList();
        return mapMessageReceivers(result, isstudent);
    }

    /**
     * Search student groups
     *
     * @param user
     * @param criteria
     * @return
     */
    public List<StudentGroupSearchDto> searchStudentGroups(HoisUserDetails user, StudentGroupSearchCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student_group sg left join curriculum c on sg.curriculum_id=c.id").sort("code");

        qb.requiredCriteria("sg.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("sg.curriculum_id in (:userCurriculumIds)", "userCurriculumIds",
                    user.getCurriculumIds());
        }

        qb.optionalContains("sg.code","studentGroupCode", criteria.getName());
        qb.optionalCriteria("c.id in (:curriculums)", "curriculums", criteria.getCurriculums());
        qb.optionalCriteria("sg.study_form_code in (:studyForm)", "studyForm", criteria.getStudyForm());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("sg.curriculum_id in (:userCurriculumIds)", "userCurriculumIds",
                    user.getCurriculumIds());
        } else if (user.isTeacher()) {
            qb.requiredCriteria("("
                    // student group teacher
                    + "sg.teacher_id = :teacherId"
                    // responsible for module. (Edited for HITSAOIS-54 13)
                    + " or exists( select lp.id from lesson_plan lp "
                    + "join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id "
                    + "left join journal_omodule_theme jot on jot.lesson_plan_module_id = lpm.id "
                    + "left join journal j on j.id = jot.journal_id "
                    + "left join journal_teacher jt on jt.journal_id = j.id "
                    + "where (lpm.teacher_id = :teacherId or jt.teacher_id = :teacherId) and lp.student_group_id = sg.id)"
                    // journal teacher
                    + " or exists( select tosg.id from timetable_object_student_group tosg "
                    + "join timetable_object too on tosg.timetable_object_id = too.id "
                    + "join journal_teacher jt on too.journal_id = jt.journal_id "
                    + "where jt.teacher_id = :teacherId and tosg.student_group_id = sg.id)"
                    // subject teacher HITSAOIS-54 10
                    + " or exists( select sspsg.id "
                    + "from subject_study_period_student_group sspsg "
                    + "join subject_study_period ssp on sspsg.subject_study_period_id = ssp.id "
                    + "join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id "
                    + "where sspt.teacher_id = :teacherId)"
                    + ")", "teacherId", user.getTeacherId());
        }

        List<?> data = qb.select("sg.id, sg.code, sg.study_form_code, c.id as curriculum_id, c.name_et, c.name_en, c.is_higher", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            StudentGroupSearchDto dto = new StudentGroupSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setCode(resultAsString(r, 1));
            dto.setStudyForm(resultAsString(r, 2));
            dto.setCurriculum(new AutocompleteResult(resultAsLong(r, 3), resultAsString(r, 4), resultAsString(r, 5)));
            dto.setHigher(resultAsBoolean(r, 6));
            return dto;
        }, data);
    }
    
    public List<JournalDto> searchTeacherJournals(HoisUserDetails user) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
            "from journal j " +
            "join journal_teacher jt on jt.journal_id = j.id " +
            "left join journal_omodule_theme jot on jot.journal_id = j.id " +
            "left join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
        ).sort("j.id");

        qb.requiredCriteria("j.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("j.study_year_id = :studyYearId", "studyYearId", studyYearService.getCurrentStudyYear(user.getSchoolId()).getId());
        if(user.isTeacher()) {
            qb.requiredCriteria(":teacherId in (jt.teacher_id, lpm.teacher_id)", "teacherId", user.getTeacherId());
        }

        List<?> data = qb.select("j.id, j.name_et", em, true).getResultList();
        return StreamUtil.toMappedList(r -> {
            JournalDto dto = new JournalDto();
            dto.setId(resultAsLong(r, 0));
            dto.setNameEt(resultAsString(r, 1));
            dto.setNameEn(resultAsString(r, 1));
            return dto;
        }, data);
    }
    
    public List<SubjectDto> searchTeacherSubjects(HoisUserDetails user) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
            "from subject s " +
            "join subject_study_period ssp on s.id = ssp.subject_id " +
            "join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id"
        ).sort("s.id");

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", studyYearService.getCurrentStudyPeriod(user.getSchoolId()));
        if(user.isTeacher()) {
            qb.requiredCriteria("sspt.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        }

        List<?> data = qb.select("s.id, s.code, s.name_et, s.name_en", em, true).getResultList();
        return StreamUtil.toMappedList(r -> {
            SubjectDto dto = new SubjectDto();
            dto.setId(resultAsLong(r, 0));
            dto.setCode(resultAsString(r, 1));
            dto.setNameEt(resultAsString(r, 2));
            dto.setNameEn(resultAsString(r, 3));
            return dto;
        }, data);
    }

    /**
     * Finds following teachers:
     *  - student group teacher
     *  - higher student: teacher, whose subject was declared
     *  - vocational student: lesson plan teacher
     *  - journal teacher
     *  
     *  note, that student_representative.is_student_visible is considered
     */
    private List<MessageReceiverDto> searchParentsTeachers(HoisUserDetails user, UsersSearchCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from teacher t join person p on p.id = t.person_id").sort("p.lastname", "p.firstname");
        qb.optionalContains("p.firstname || ' ' || p.lastname", "name", criteria.getName());
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
                + "where lpm.teacher_id = t.id and sr.is_student_visible and sr.person_id = :personId)"
                // HITSAOIS-54 12
                + "or exists(select j.id "
                + "from journal j "
                + "join journal_teacher jt on jt.journal_id = j.id and jt.teacher_id = t.id " 
                + "join journal_student js on js.journal_id = j.id "
                + "where js.student_id in ("
                + "select s.id from student s "
                + "join student_representative sr on sr.student_id = s.id "
                + "where sr.is_student_visible and sr.person_id = :personId) "
                + "and j.study_year_id = :studyYearId)"
                + ")", "personId", user.getPersonId());
        qb.parameter("studyYearId", studyYearService.getCurrentStudyYear(user.getSchoolId()).getId());

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
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from teacher t join person p on p.id = t.person_id").sort("p.lastname", "p.firstname");
        qb.optionalContains("p.firstname || ' ' || p.lastname", "name", criteria.getName());

        qb.requiredCriteria(" ("
                // student group teacher
                + "exists(select s.id from student s "
                + "join student_group sg on sg.id = s.student_group_id "
                + "where sg.teacher_id = t.id "
                + "and s.id = :studentId) "
                // study period/subject/teacher
                + "or exists(select d.id "
                + "from declaration d "
                + "join declaration_subject ds on ds.declaration_id = d.id "
                + "join subject_study_period ssp on ssp.id = ds.subject_study_period_id "
                + "join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id "
                + "where d.student_id = :studentId and sspt.teacher_id = t.id) "
                // responsible for module
                + " or exists(select s.id from student s "
                + "join student_group sg on sg.id = s.student_group_id "
                + "join lesson_plan lp on lp.student_group_id = sg.id "
                + "join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id "
                + "where lpm.teacher_id = t.id and s.id = :studentId) "
                // journal teacher
                + " or exists(select js.student_id from journal_student js "
                + "join journal_teacher jt on js.journal_id = jt.journal_id "
                + "where jt.teacher_id = t.id and js.student_id = :studentId) "
                + ")", "studentId", user.getStudentId());

        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", user.getSchoolId());

        List<?> result = qb.select(" distinct p.id, p.firstname, p.lastname ", em).getResultList();
        return mapUserSearchDtoPage(result, Role.ROLL_O);
    }

    private List<MessageReceiverDto> searchTeachersStudents(HoisUserDetails user, UsersSearchCommand criteria) {
        StringBuilder select = new StringBuilder(" distinct p.id as studentPersonId, s.id as studentId, "
                + "p.firstname, p.lastname, p.idcode, "
                + "sg.id as sgId, sg.code as sgCode, "
                + "c.id as curriculumId, c.name_et, c.name_en, c.is_higher ");
        StringBuilder from = new StringBuilder(" from student s "
                + "join person p on s.person_id = p.id "
                +" left join student_group sg on sg.id = s.student_group_id "
                + "left join curriculum c on sg.curriculum_id = c.id ");

        // Journals
        select.append(", j.id as journal_id, j.name_et as journal_name");
        from.append("left join journal_student js on js.student_id = s.id ");
        from.append("left join journal j on j.id = js.journal_id ");
        // Subjects
        select.append(", sj.id as subject_id, sj.name_et as subject_name_et, sj.name_en as subject_name_en");
        from.append("left join declaration d on d.student_id = s.id ");
        from.append("left join declaration_subject ds on ds.declaration_id = d.id ");
        from.append("left join subject_study_period ssp on ssp.id = ds.subject_study_period_id ");
        from.append("left join subject sj on sj.id = ssp.subject_id ");
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).sort("p.lastname", "p.firstname");
        qb.optionalContains("p.firstname || ' ' || p.lastname", "name", criteria.getName());
        qb.requiredCriteria("s.school_id = :studentsSchoolId", "studentsSchoolId", user.getSchoolId());
        qb.requiredCriteria("s.status_code in (:activeStudents)", "activeStudents", StudentStatus.STUDENT_STATUS_ACTIVE);

        qb.requiredCriteria(" ("
                // student group teacher
                + "sg.teacher_id = :teacherId"
                // study period/subject/teacher
                + " or exists( select d.id from declaration d "
                + "join declaration_subject ds on ds.declaration_id = d.id "
                + "join subject_study_period ssp on ssp.id = ds.subject_study_period_id "
                + "join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id "
                + "where sspt.teacher_id = :teacherId and d.student_id = s.id)"
                // responsible for module
                + " or exists( select lp.id from lesson_plan lp "
                + "join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id "
                + "where lpm.teacher_id = :teacherId and lp.student_group_id = sg.id)"
                // journal teacher
                + " or exists(select js.student_id from journal_student js "
                + "join journal_teacher jt on js.journal_id = jt.journal_id "
                + "where jt.teacher_id = :teacherId and js.student_id = s.id)"
                +")", "teacherId", user.getTeacherId());

        List<?> result = qb.select(select.toString(), em).getResultList();
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
            dto.setHigher(resultAsBoolean(r, 10));
            if (resultAsLong(r, 11) != null) {
                dto.setJournal(new AutocompleteResult(resultAsLong(r, 11), resultAsString(r, 12), resultAsString(r, 12)));
            }
            if (resultAsLong(r, 13) != null) {
                dto.setSubject(new AutocompleteResult(resultAsLong(r, 13), resultAsString(r, 14), resultAsString(r, 15)));
            }
            return dto;
        }, result);
    }

    private List<MessageReceiverDto> searchTeachersParents(HoisUserDetails user, UsersSearchCommand criteria) {
        StringBuilder select = new StringBuilder("distinct p.id as repPersonId, p.firstname, "
                + "p.lastname, p.idcode,"
                + "sg.id as sgId, sg.code, "
                + "c.id as curriculumId, c.name_et, c.name_en, "
                + "s.id as studentId");
        StringBuilder from = new StringBuilder(" from student_representative sr "
                + "join person p on sr.person_id = p.id "
                + "join student s on s.id = sr.student_id "
                + "left join student_group sg on sg.id = s.student_group_id "
                + "left join curriculum c on sg.curriculum_id = c.id ");

        // Journals
        select.append(", j.id as journal_id, j.name_et as journal_name");
        from.append("left join journal_student js on js.student_id = s.id ");
        from.append("left join journal j on j.id = js.journal_id ");
        // Subjects
        select.append(", sj.id as subject_id, sj.name_et as subject_name_et, sj.name_en as subject_name_en");
        from.append("left join declaration d on d.student_id = s.id ");
        from.append("left join declaration_subject ds on ds.declaration_id = d.id ");
        from.append("left join subject_study_period ssp on ssp.id = ds.subject_study_period_id ");
        from.append("left join subject sj on sj.id = ssp.subject_id ");
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString()).sort("p.lastname", "p.firstname");
        qb.optionalContains("p.firstname || ' ' || p.lastname", "name", criteria.getName());
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
        
        List<?> result = qb.select(select.toString(), em).getResultList();
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
            dto.setJournal(new AutocompleteResult(resultAsLong(r, 10), resultAsString(r, 11), resultAsString(r, 11)));
            dto.setSubject(new AutocompleteResult(resultAsLong(r, 12), resultAsString(r, 13), resultAsString(r, 14)));
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

    public List<MessageReceiverDto> getStudents(HoisUserDetails user, StudentSearchCommand criteria, Pageable pageable) {
        // only active students
        criteria.setStatus(StudentStatus.STUDENT_STATUS_ACTIVE);

        List<MessageReceiverDto> students = studentService.search(user, criteria, pageable)
                .map(MessageReceiverDto::of).getContent();
        List<Long> studentIds = StreamUtil.toMappedList(MessageReceiverDto::getId, students);
        List<MessageReceiverDto> parents = studentRepresentatives(studentIds, criteria);
        if(!parents.isEmpty()) {
            students = new ArrayList<>(students);
            students.addAll(parents);
        }
        return students;
    }

    private List<MessageReceiverDto> studentRepresentatives(List<Long> studentIds, StudentSearchCommand criteria) {
        if(studentIds.isEmpty()) {
            return Collections.emptyList();
        }
        
        StringBuilder select = new StringBuilder(STUDENT_PARENTS_SELECT);
        StringBuilder from = new StringBuilder(STUDENT_PARENTS_FROM);
        final boolean isJournalIds = !CollectionUtils.isEmpty(criteria.getJournalId()); 
        final boolean isSubjectUsed = !CollectionUtils.isEmpty(criteria.getSubjectId());
        if (isJournalIds) {
            select.append(", j.id as journal_id, j.name_et as journal_name");
            from.append("left join journal_student js on js.student_id = s.id ");
            from.append("left join journal j on j.id = js.journal_id ");
        }
        if (isSubjectUsed) {
            select.append(", sj.id as subject_id, sj.name_et as subject_name_et, sj.name_en as subject_name_en");
            from.append("left join declaration d on d.student_id = s.id ");
            from.append("left join declaration_subject ds on ds.declaration_id = d.id ");
            from.append("left join subject_study_period ssp on ssp.id = ds.subject_study_period_id ");
            from.append("left join subject sj on sj.id = ssp.subject_id ");
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString());
        qb.optionalCriteria("sr.student_id in (:studentIds)", "studentIds", studentIds);
        qb.optionalCriteria("j.id in (:journalIds)", "journalIds", criteria.getJournalId());
        qb.optionalCriteria("sj.id in (:subjectIds)", "subjectIds", criteria.getSubjectId());
        qb.filter("sr.is_student_visible is true");
        List<?> result = qb.select(select.toString(), em).getResultList();

        return StreamUtil.toMappedList(r -> {
            MessageReceiverDto dto = new MessageReceiverDto();
            dto.setId(resultAsLong(r, 2));
            dto.setPersonId(resultAsLong(r, 4));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 5), resultAsString(r, 6)));
            dto.setHigher(resultAsBoolean(r, 11));
            String studentGroupCode = resultAsString(r, 1);
            dto.setStudentGroup(new AutocompleteResult(resultAsLong(r, 0), studentGroupCode, studentGroupCode));
            dto.setCurriculum(new AutocompleteResult(resultAsLong(r, 8), resultAsString(r, 9), resultAsString(r, 10)));
            dto.setStudyForm(resultAsString(r, 3));
            dto.setRole(Arrays.asList(Role.ROLL_L.name()));
            if (isJournalIds) {
                dto.setJournal(new AutocompleteResult(resultAsLong(r, 12), resultAsString(r, 13), resultAsString(r, 13)));
                if (isSubjectUsed) {
                    dto.setSubject(new AutocompleteResult(resultAsLong(r, 14), resultAsString(r, 15), resultAsString(r, 16)));
                }
            } else {
                if (isSubjectUsed) {
                    dto.setSubject(new AutocompleteResult(resultAsLong(r, 12), resultAsString(r, 13), resultAsString(r, 14)));
                }
            }
            return dto;
        }, result);
    }
    
    public boolean userHasEmail(HoisUserDetails user) {
        Person person = em.getReference(Person.class, user.getPersonId());
        String email = person.getEmail();
        if (user.isStudent()) {
            Student student = em.getReference(Student.class, user.getStudentId());
            email = student.getEmail() != null ? student.getEmail() : email;
        } else if (user.isTeacher()) {
            Teacher teacher = em.getReference(Teacher.class, user.getTeacherId());
            email = teacher.getEmail() != null ? teacher.getEmail() : email;
        }
        return StringUtils.isNotBlank(email);
    }
}
