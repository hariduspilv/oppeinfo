package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.repository.JournalRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.timetable.JournalEndDateCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalStudentsCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalStudentsSearchCommand;
import ee.hitsa.ois.web.dto.timetable.JournalDto;
import ee.hitsa.ois.web.dto.timetable.JournalSearchDto;
import ee.hitsa.ois.web.dto.timetable.JournalStudentDto;

@Service
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private StudentRepository studentRepository;

    public Page<JournalSearchDto> search(HoisUserDetails user, JournalSearchCommand command, Pageable pageable) {
        return journalRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), user.getSchoolId()));

            if (command.getStudentGroup() != null || !CollectionUtils.isEmpty(command.getModule())) {
                Join<Object, Object> journalOmoduleThemesJoin = root.join("journalOmoduleThemes", JoinType.INNER);
                if (command.getStudentGroup() != null) {
                    filters.add(cb.equal(journalOmoduleThemesJoin.get("lessonPlanModule").get("lessonPlan")
                            .get("studentGroup").get("id"), command.getStudentGroup()));
                }

                if (!CollectionUtils.isEmpty(command.getModule())) {
                    filters.add(journalOmoduleThemesJoin.get("curriculumVersionOmoduleTheme").get("module")
                            .get("curriculumModule").get("id").in(command.getModule()));
                }
            }

            if (command.getTeacher() != null) {
                Join<Object, Object> teacherJoin = root.join("journalTeachers", JoinType.INNER);
                if (user.isTeacher()) {
                    filters.add(cb.equal(teacherJoin.get("teacher").get("person").get("id"), user.getPersonId()));
                } else {
                    filters.add(cb.equal(teacherJoin.get("teacher").get("id"), command.getTeacher()));
                }
            }

            if (command.getJournal() != null) {
                filters.add(cb.equal(root.get("id"), command.getJournal()));
            }

            if (command.getStatus() != null) {
                filters.add(cb.equal(root.get("status").get("code"), command.getStatus()));
            }


            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(JournalSearchDto::of);
    }

    public JournalDto get(Journal journal) {
        return JournalDto.of(journal);
    }

    public Page<JournalStudentDto> students(HoisUserDetails user, Journal journal, JournalStudentsSearchCommand command, Pageable pageable) {
        //TODO: kellel puudub vastavas moodulis positiivne tulemus.
        return studentRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), user.getSchoolId()));
            filters.add(cb.equal(root.get("status").get("code"), StudentStatus.OPPURSTAATUS_O.name()));

            if (Boolean.TRUE.equals(command.getRestrictStudentGroup())) {
                List<Long> journalStudentGroups = journal.getJournalOmoduleThemes().stream().map(theme -> theme.getLessonPlanModule().getLessonPlan().getStudentGroup().getId()).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(journalStudentGroups)) {
                    root.get("studentGroup").get("id").in(journalStudentGroups);
                }
            }

            if (StringUtils.hasText(command.getStudentName())) {
                List<Predicate> name = new ArrayList<>();
                propertyContains(() -> root.get("person").get("firstname"), cb, command.getStudentName(), name::add);
                propertyContains(() -> root.get("person").get("lastname"), cb, command.getStudentName(), name::add);
                name.add(cb.like(cb.concat(cb.upper(root.get("person").get("firstname")), cb.concat(" ", cb.upper(root.get("person").get("lastname")))), JpaQueryUtil.toContains(command.getStudentName())));
                if(!name.isEmpty()) {
                    filters.add(cb.or(name.toArray(new Predicate[name.size()])));
                }
            }
            List<Long> studentIds = journal.getJournalStudents().stream().map(it -> EntityUtil.getId(it.getStudent())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(studentIds)) {
                filters.add(cb.not(root.get("id").in(studentIds)));
            }

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(JournalStudentDto::of);
    }

    public Journal saveEndDate(Journal journal, JournalEndDateCommand command) {
        if (command.getEndDate() != null) {
            journal.setEndDate(command.getEndDate());
        }
        return journalRepository.save(journal);
    }

    public Journal addStudentsToJournal(Journal journal, JournalStudentsCommand command) {
        List<Long> existingStudents = journal.getJournalStudents().stream().map(js -> EntityUtil.getId(js.getStudent())).collect(Collectors.toList());
        for (Long student : command.getStudents()) {
            if (!existingStudents.contains(student)) {
                journal.getJournalStudents().add(JournalStudent.of(studentRepository.getOne(student)));
            }
        }
        return journalRepository.save(journal);
    }

    public Journal removeStudentsFromJournal(Journal journal, JournalStudentsCommand command) {
        journal.getJournalStudents()
            .removeIf(js -> command.getStudents().contains(EntityUtil.getId(js.getStudent())));
        return journalRepository.save(journal);
    }



}
