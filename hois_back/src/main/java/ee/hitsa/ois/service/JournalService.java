package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalEntry;
import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.domain.timetable.JournalEntryStudentHistory;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.JournalEntryRepository;
import ee.hitsa.ois.repository.JournalEntryStudentRepository;
import ee.hitsa.ois.repository.JournalRepository;
import ee.hitsa.ois.repository.JournalStudentRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.validation.JournalEntryValidation;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.timetable.JournalEndDateCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalEntryForm;
import ee.hitsa.ois.web.commandobject.timetable.JournalSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalStudentsCommand;
import ee.hitsa.ois.web.commandobject.timetable.JournalStudentsSearchCommand;
import ee.hitsa.ois.web.dto.ClassifierSelection;
import ee.hitsa.ois.web.dto.timetable.JournalDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryLessonInfoDto;
import ee.hitsa.ois.web.dto.timetable.JournalEntryStudentDto;
import ee.hitsa.ois.web.dto.timetable.JournalSearchDto;
import ee.hitsa.ois.web.dto.timetable.JournalStudentDto;

@Service
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private JournalStudentRepository journalStudentRepository;
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private JournalEntryStudentRepository journalEntryStudentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private Validator validator;

    public Page<JournalSearchDto> search(HoisUserDetails user, JournalSearchCommand command, Pageable pageable) {
        return journalRepository.findAll((root, query, cb) -> {
            root.fetch("journalOccupationModuleThemes").fetch("lessonPlanModule", JoinType.LEFT).fetch("lessonPlan", JoinType.LEFT).fetch("studentGroup", JoinType.LEFT);
            root.fetch("journalOccupationModuleThemes").fetch("curriculumVersionOccupationModuleTheme", JoinType.LEFT).fetch("module", JoinType.LEFT).fetch("curriculumModule", JoinType.LEFT).fetch("curriculum", JoinType.LEFT);
            root.fetch("journalOccupationModuleThemes").fetch("curriculumVersionOccupationModuleTheme", JoinType.LEFT).fetch("module", JoinType.LEFT).fetch("curriculumModule", JoinType.LEFT).fetch("module", JoinType.LEFT);
            root.fetch("journalCapacities", JoinType.LEFT);
            root.fetch("journalTeachers", JoinType.LEFT).fetch("teacher", JoinType.LEFT).fetch("person", JoinType.LEFT);
            root.fetch("journalEntries", JoinType.LEFT);

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), user.getSchoolId()));

            if (command.getStudentGroup() != null || !CollectionUtils.isEmpty(command.getModule())) {
                Join<Object, Object> journalOmoduleThemesJoin = root.join("journalOccupationModuleThemes", JoinType.INNER);
                if (command.getStudentGroup() != null) {
                    filters.add(cb.equal(journalOmoduleThemesJoin.get("lessonPlanModule").get("lessonPlan")
                            .get("studentGroup").get("id"), command.getStudentGroup()));
                }

                if (!CollectionUtils.isEmpty(command.getModule())) {
                    filters.add(journalOmoduleThemesJoin.get("curriculumVersionOccupationModuleTheme").get("module")
                            .get("curriculumModule").get("id").in(command.getModule()));
                }
            }

            if (command.getTeacher() != null) {
                Join<Object, Object> teacherJoin = root.join("journalTeachers", JoinType.INNER);
                filters.add(cb.equal(teacherJoin.get("teacher").get("id"), command.getTeacher()));
            }

            if (user.isTeacher()) {
                Join<Object, Object> teacherJoin = root.join("journalTeachers", JoinType.INNER);
                filters.add(cb.equal(teacherJoin.get("teacher").get("person").get("id"), user.getPersonId()));
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
        //TODO: refactor to use native query
        return JournalDto.of(journal);
    }

    public Page<JournalStudentDto> otherStudents(HoisUserDetails user, Long journalId, JournalStudentsSearchCommand command, Pageable pageable) {
        //TODO: kellel puudub vastavas moodulis positiivne tulemus.
        return studentRepository.findAll((root, query, cb) -> {
            root.fetch("person", JoinType.INNER);
            root.fetch("studentGroup", JoinType.LEFT);
            root.fetch("curriculumVersion", JoinType.INNER).fetch("curriculum", JoinType.INNER);

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), user.getSchoolId()));
            filters.add(cb.equal(root.get("status").get("code"), StudentStatus.OPPURSTAATUS_O.name()));

            if (StringUtils.hasText(command.getStudentName())) {
                List<Predicate> name = new ArrayList<>();
                propertyContains(() -> root.get("person").get("firstname"), cb, command.getStudentName(), name::add);
                propertyContains(() -> root.get("person").get("lastname"), cb, command.getStudentName(), name::add);
                name.add(cb.like(cb.concat(cb.upper(root.get("person").get("firstname")), cb.concat(" ", cb.upper(root.get("person").get("lastname")))), JpaQueryUtil.toContains(command.getStudentName())));
                if(!name.isEmpty()) {
                    filters.add(cb.or(name.toArray(new Predicate[name.size()])));
                }
            }

            Subquery<Long> studentsQuery = query.subquery(Long.class);
            Root<Journal> journalRoot = studentsQuery.from(Journal.class);
            Join<Object, Object> journalStudentsJoin = journalRoot.join("journalStudents", JoinType.LEFT);
            studentsQuery.select(journalStudentsJoin.get("student").get("id"))
                .where(cb.and(
                        cb.equal(journalRoot.get("id"), journalId),
                        cb.equal(journalStudentsJoin.get("student").get("id"), root.get("id"))));
            filters.add(cb.not(cb.exists(studentsQuery)));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(JournalStudentDto::of);
    }

    public List<JournalStudentDto> suitedStudents(HoisUserDetails user, Long journalId) {
      //TODO: kellel puudub vastavas moodulis positiivne tulemus.
        return studentRepository.findAll((root, query, cb) -> {
            root.fetch("person", JoinType.INNER);
            root.fetch("studentGroup", JoinType.LEFT);
            root.fetch("curriculumVersion", JoinType.INNER).fetch("curriculum", JoinType.INNER);

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("school").get("id"), user.getSchoolId()));
            filters.add(cb.equal(root.get("status").get("code"), StudentStatus.OPPURSTAATUS_O.name()));

            Subquery<Long> studentGroupsQuery = query.subquery(Long.class);
            Root<Journal> journalRoot = studentGroupsQuery.from(Journal.class);
            Join<Object, Object> journalOmoduleThemesJoin = journalRoot.join("journalOccupationModuleThemes", JoinType.INNER);

            studentGroupsQuery.select(journalOmoduleThemesJoin.get("lessonPlanModule").get("lessonPlan").get("studentGroup").get("id"))
                .where(cb.and(
                    cb.equal(journalRoot.get("id"), journalId),
                    cb.equal(journalOmoduleThemesJoin.get("lessonPlanModule").get("lessonPlan").get("studentGroup").get("id"), root.get("studentGroup").get("id"))));
            filters.add(cb.exists(studentGroupsQuery));

            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }).stream().map(JournalStudentDto::of).collect(Collectors.toList());
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

    public Journal saveJournalEntry(Journal journal, JournalEntryForm journalEntryForm) {
        validateJournalEntry(journalEntryForm);
        JournalEntry journalEntry = EntityUtil.bindToEntity(journalEntryForm, new JournalEntry(), classifierRepository, "journalEntryStudents");
        journal.getJournalEntries().add(journalEntry);
        saveJournalEntryStudents(journalEntryForm, journalEntry);
        return journalRepository.save(journal);
    }

    public void updateJournalEntry(JournalEntryForm journalEntryForm, Long journalEntrylId) {
        validateJournalEntry(journalEntryForm);
        JournalEntry journalEntry = journalEntryRepository.getOne(journalEntrylId);
        EntityUtil.bindToEntity(journalEntryForm, journalEntry, classifierRepository, "journalEntryStudents");
        saveJournalEntryStudents(journalEntryForm, journalEntry);
        journalEntryRepository.save(journalEntry);
    }

    private void validateJournalEntry(JournalEntryForm journalEntryForm) {
        if (StringUtils.hasText(journalEntryForm.getHomework())) {
            Set<ConstraintViolation<JournalEntryForm>> errors = validator.validate(journalEntryForm, JournalEntryValidation.Homework.class);
            if(!errors.isEmpty()) {
                throw new ValidationFailedException(errors);
            }
        }

        if (journalEntryForm.getEntryType().equals(JournalEntryType.SISSEKANNE_T.name())) {
            Set<ConstraintViolation<JournalEntryForm>> errors = validator.validate(journalEntryForm, JournalEntryValidation.Lesson.class);
            if(!errors.isEmpty()) {
                throw new ValidationFailedException(errors);
            }
        }
    }

    private void saveJournalEntryStudents(JournalEntryForm journalEntryForm, JournalEntry journalEntry) {
        for (JournalEntryStudentDto journalEntryStudentDto : journalEntryForm.getJournalEntryStudents()) {
            if (journalEntryStudentDto.getId() != null) {
                updateJournalStudentEntry(journalEntryStudentDto);
            } else {
                saveJournalStudentEntry(journalEntry, journalEntryStudentDto);
            }
        }
    }

    private void updateJournalStudentEntry(JournalEntryStudentDto journalEntryStudentDto) {
        JournalEntryStudent journalEntryStudent = journalEntryStudentRepository.getOne(journalEntryStudentDto.getId());
        if (journalEntryStudent.getGrade() != null && !EntityUtil.getCode(journalEntryStudent.getGrade()).equals(journalEntryStudentDto.getGrade())) {
            JournalEntryStudentHistory journalEntryStudentHistory = new JournalEntryStudentHistory();
            journalEntryStudentHistory.setGrade(journalEntryStudent.getGrade());
            journalEntryStudentHistory.setGradeInserted(journalEntryStudent.getGradeInserted());
            journalEntryStudent.getJournalEntryStudentHistories().add(journalEntryStudentHistory);
        }
        if (journalEntryStudentDto.getGrade() != null && !journalEntryStudentDto.getGrade().equals(EntityUtil.getCode(journalEntryStudent.getGrade()))) {
            journalEntryStudent.setGradeInserted(LocalDateTime.now());
        }
        EntityUtil.bindToEntity(journalEntryStudentDto, journalEntryStudent,
                classifierRepository, "journalEntryStudentHistories", "gradeInserted");
    }

    private void saveJournalStudentEntry(JournalEntry journalEntry, JournalEntryStudentDto journalEntryStudentDto) {
        JournalStudent journalStudent = journalStudentRepository.getOne(journalEntryStudentDto.getJournalStudent());
        JournalEntryStudent journalEntryStudent = EntityUtil.bindToEntity(journalEntryStudentDto, new JournalEntryStudent(),
                classifierRepository, "journalEntryStudentHistories", "gradeInserted");
        journalEntryStudent.setJournalStudent(journalStudent);
        if (journalEntryStudentDto.getGrade() != null) {
            journalEntryStudent.setGradeInserted(LocalDateTime.now());
        }
        List<Long> existingJournalStudentEntries = journalEntry.getJournalEntryStudents().stream().map(it -> EntityUtil.getId(it.getJournalStudent())).collect(Collectors.toList());
        if (!existingJournalStudentEntries.contains(EntityUtil.getId(journalStudent))) {
            journalEntry.getJournalEntryStudents().add(journalEntryStudent);
        } else {
            throw new ValidationFailedException("journal.messages.dublicateJournalStudentInJournalEntry");
        }
    }

    public JournalEntryLessonInfoDto journalEntryLessonInfo(HoisUserDetails user, Journal journal) {
        //TODO
        JournalEntryLessonInfoDto dto = new JournalEntryLessonInfoDto();
        return dto;
    }

    public Page<JournalEntryDto> journalEntries(Long journalId, Pageable pageable) {
        return journalEntryRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("journal").get("id"), journalId));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable).map(JournalEntryDto::of);
    }

    public JournalEntryDto journalEntry(Long journalId, Long journalEntrylId) {
        JournalEntry journalEntry = journalEntryRepository.findOne((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("journal").get("id"), journalId));
            filters.add(cb.equal(root.get("id"), journalEntrylId));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
        return JournalEntryDto.of(journalEntry);
    }

    public Map<LocalDate, Map<Long, ClassifierSelection>> journalEntriesByDate(Journal journal) {
        Map<LocalDate, Map<Long, ClassifierSelection>> result = new HashMap<>();
        for (JournalEntry journalEntry : journal.getJournalEntries()) {
            Map<Long, ClassifierSelection> journalEntryStudents = result.computeIfAbsent(journalEntry.getInserted().toLocalDate(), (date) -> new HashMap<>());
            for (JournalEntryStudent journalEntryStudent : journalEntry.getJournalEntryStudents()) {
                if (journalEntryStudent.getAbsence() != null) {
                    journalEntryStudents.put(journalEntryStudent.getJournalStudent().getId(), ClassifierSelection.of(journalEntryStudent.getAbsence()));
                } else if (journalEntryStudent.getGrade() != null) {
                    journalEntryStudents.put(journalEntryStudent.getJournalStudent().getId(), ClassifierSelection.of(journalEntryStudent.getGrade()));
                }
            }
        }
        return result;
    }

}
