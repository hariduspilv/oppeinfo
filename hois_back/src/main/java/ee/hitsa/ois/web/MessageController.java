package ee.hitsa.ois.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.Message;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.service.MessageService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.MessageForm;
import ee.hitsa.ois.web.commandobject.MessageSearchCommand;
import ee.hitsa.ois.web.commandobject.UsersSearchCommand;
import ee.hitsa.ois.web.commandobject.student.StudentGroupSearchCommand;
import ee.hitsa.ois.web.commandobject.student.StudentSearchCommand;
import ee.hitsa.ois.web.dto.MessageDto;
import ee.hitsa.ois.web.dto.MessageReceiverDto;
import ee.hitsa.ois.web.dto.MessageSearchDto;
import ee.hitsa.ois.web.dto.SubjectDto;
import ee.hitsa.ois.web.dto.student.StudentGroupSearchDto;
import ee.hitsa.ois.web.dto.studymaterial.JournalDto;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/received/mainPage")
    public Page<MessageSearchDto> show(HoisUserDetails user, Pageable pageable) {
        return messageService.show(user, pageable);
    }

    @GetMapping("/received/new")
    public Map<String, Long> unreadReceivedCount(HoisUserDetails user) {
        return messageService.unreadReceivedCount(user);
    }

    @GetMapping("/sent")
    public Page<MessageSearchDto> searchSent(HoisUserDetails user, @Valid MessageSearchCommand criteria, Pageable pageable) {
        return messageService.searchSent(user, criteria, pageable);
    }

    @GetMapping("/sent/automatic")
    public Page<MessageSearchDto> searchSentAutomatic(HoisUserDetails user, @Valid MessageSearchCommand criteria, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user);
        return messageService.searchSentAutomatic(user.getSchoolId(), criteria, pageable);
    }

    @GetMapping("/received")
    public Page<MessageSearchDto> searchReceived(HoisUserDetails user, @Valid MessageSearchCommand criteria, Pageable pageable) {
        return messageService.searchReceived(user, criteria, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public MessageDto get(HoisUserDetails user, @WithEntity Message message) {
        assertCanView(user, message);
        MessageDto dto = MessageDto.of(message);
        dto.setIsRead(message.isReadBy(user.getPersonId()));
        return dto;
    }

    //TODO: add checks to forbid replying for automatic messages
    @PostMapping
    public MessageDto create(HoisUserDetails user, @Valid @RequestBody MessageForm form) {
        return get(user, messageService.create(user, form));
    }

    @PutMapping("/{id:\\d+}")
    public void setRead(HoisUserDetails user, @WithEntity Message message) {
        assertIsReceiver(user, message);
        messageService.setRead(user.getPersonId(), message);
    }

    @GetMapping("/parents")
    public List<MessageReceiverDto> getStudentRepresentatives(StudentSearchCommand criteria) {
        return messageService.getStudentRepresentatives(criteria);
    }

    /**
     * UsersController.search() is not used as school should not always be set as parameter
     */
    @GetMapping("/persons")
    public List<MessageReceiverDto> searchPersons(HoisUserDetails user, UsersSearchCommand command) {
        return messageService.searchPersons(user, command);
    }

    @GetMapping("/students")
    public List<MessageReceiverDto> getStudents(HoisUserDetails user, @Valid StudentSearchCommand criteria, Pageable pageable) {
        return messageService.getStudents(user, criteria, pageable);
    }

    @GetMapping("/studentgroups")
    public List<StudentGroupSearchDto> studentGroups(HoisUserDetails user, @Valid StudentGroupSearchCommand criteria) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacherOrTeacher(user);
        return messageService.searchStudentGroups(user, criteria);
    }

    @GetMapping("/teacherjournals")
    public List<JournalDto> getTeacherJournals(HoisUserDetails user) {
        UserUtil.assertIsTeacher(user);
        return messageService.searchTeacherJournals(user);
    }

    @GetMapping("/teachersubjects")
    public List<SubjectDto> getTeacherSubjects(HoisUserDetails user) {
        UserUtil.assertIsTeacher(user);
        return messageService.searchTeacherSubjects(user);
    }

    @GetMapping("/{studentId:\\d+}/parents")
    public List<MessageReceiverDto> getStudentsParents(HoisUserDetails user, @WithEntity("studentId") Student student) {
        UserUtil.assertSameSchool(user, student.getSchool());
        return messageService.getStudentRepresentatives(student);
    }
    
    @GetMapping("/hasEmail")
    public Map<String, Object> userHasEmail(HoisUserDetails user) {
        Map<String, Object> map = new HashMap<>();
        map.put("hasEmail", Boolean.valueOf(messageService.userHasEmail(user)));
        return map;
    }

    private static void assertIsReceiver(HoisUserDetails user, Message message) {
        UserUtil.throwAccessDeniedIf(!message.getReceivers().stream()
                .anyMatch(r -> EntityUtil.getId(r.getPerson()).equals(user.getPersonId())), 
                "User is not message receiver");
    }

    private static void assertCanView(HoisUserDetails user, Message message) {
        School school = message.getSendersSchool();
        if (!user.isMainAdmin() && school != null) {
            UserUtil.assertSameSchool(user, school);
        }
        Long senderId = EntityUtil.getId(message.getSender());
        UserUtil.throwAccessDeniedIf(!(user.isSchoolAdmin() && PersonUtil.AUTOMATIC_SENDER_ID.equals(senderId))
                && !senderId.equals(user.getPersonId()) &&
                !message.getReceivers().stream()
                .anyMatch(r -> EntityUtil.getId(r.getPerson()).equals(user.getPersonId())), 
                "User is not message sender/receiver or school admin");
    }
}
