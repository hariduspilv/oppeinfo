package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.domain.timetable.JournalEntryStudentLessonAbsence;
import ee.hitsa.ois.enums.Absence;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.student.StudentGroupAbsenceCommand;
import ee.hitsa.ois.web.commandobject.student.StudentGroupAbsenceDto;
import ee.hitsa.ois.web.commandobject.student.StudentGroupAbsenceDtoContainer;
import ee.hitsa.ois.web.commandobject.student.StudentGroupAbsenceForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.StudyWeekDto;

@Transactional
@Service
public class StudentGroupAbsenceService {

    @Autowired
    private EntityManager em;

    private static final String STUDENT_GROUP_ABSENCE_SELECT = "js.student_id, je.entry_date, je.journal_id as journal_id, "
            + "jes.id as student_entry_id, jesla.id as lesson_absence_id, jesla.lesson_nr + coalesce(je.start_lesson_nr - 1, 0) as lesson_nr, "
            + "coalesce(jesla.absence_code, jes.absence_code) as absence_code";

    private static final String STUDENT_GROUP_ABSENCE_FROM = "from journal_entry_student jes "
            + "join journal_entry je on jes.journal_entry_id = je.id "
            + "join journal_student js on jes.journal_student_id = js.id "
            + "join student s on js.student_id = s.id "
            + "left join student_group sg on sg.id = s.student_group_id "
            + "left join journal_entry_student_lesson_absence jesla on jes.id = jesla.journal_entry_student_id";

    public StudentGroupAbsenceDtoContainer get(HoisUserDetails user, StudentGroupAbsenceCommand criteria) {
        StudentGroupAbsenceDtoContainer container = new StudentGroupAbsenceDtoContainer();
        List<StudentGroupAbsenceDto> studentAbsences = studentAbsences(user, criteria);
        container.setStudentAbsences(studentAbsences);

        List<Long> studentIds = StreamUtil.toMappedList(r -> r.getStudent(), studentAbsences);
        List<AutocompleteResult> students = students(user, criteria,
                Boolean.TRUE.equals(criteria.getTodaysAbsences()) ? studentIds : null);

        Map<Long, AutocompleteResult> journals = journals(StreamUtil.toMappedSet(r -> r.getJournal(), studentAbsences));
        List<LocalDate> dates = Boolean.TRUE.equals(criteria.getTodaysAbsences()) ? Arrays.asList(LocalDate.now())
                : weekDates(criteria.getStudyWeekStart(), criteria.getStudyWeekEnd());
        Map<LocalDate, List<AutocompleteResult>> journalsByDates = journalsByDates(studentAbsences, journals, dates);

        container.setStudents(students);
        container.setDates(dates);
        container.setJournalsByDates(journalsByDates);
        return container;
    }

    private List<StudentGroupAbsenceDto> studentAbsences(HoisUserDetails user, StudentGroupAbsenceCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(STUDENT_GROUP_ABSENCE_FROM);
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("exists(select c.id from curriculum_version cv "
                    + "join curriculum c on c.id = cv.curriculum_id "
                    + "where s.curriculum_version_id = cv.id and c.id in (:userCurriculumIds))",
                    "userCurriculumIds", user.getCurriculumIds());
        } else if (user.isTeacher()) {
            qb.requiredCriteria("sg.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        }

        LocalDate from = Boolean.TRUE.equals(criteria.getTodaysAbsences()) ? LocalDate.now()
                : criteria.getStudyWeekStart();
        LocalDate thru = Boolean.TRUE.equals(criteria.getTodaysAbsences()) ? LocalDate.now()
                : criteria.getStudyWeekEnd();
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("sg.id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());
        qb.requiredCriteria("je.entry_date >= :from", "from", from);
        qb.requiredCriteria("je.entry_date <= :thru", "thru", thru);
        qb.filter("(jes.absence_code is not null or jesla.absence_code is not null)");

        qb.sort("je.id");
        List<?> data = qb.select(STUDENT_GROUP_ABSENCE_SELECT, em).getResultList();

        return StreamUtil.toMappedList(r -> {
            StudentGroupAbsenceDto dto = new StudentGroupAbsenceDto();
            dto.setStudent(resultAsLong(r, 0));
            dto.setEntryDate(resultAsLocalDate(r, 1));
            dto.setJournal(resultAsLong(r, 2));
            dto.setJournalStudentEntry(resultAsLong(r, 3));
            dto.setJournalEntryStudentLessonAbsence(resultAsLong(r, 4));
            dto.setLessonNr(resultAsLong(r, 5));
            dto.setAbsence(resultAsString(r, 6));
            return dto;
        }, data);
    }
    
    private Map<Long, AutocompleteResult> journals(Set<Long> journalIds) {
        Map<Long, AutocompleteResult> journals = new HashMap<>(); 
        if (!journalIds.isEmpty()) {
            List<?> data = em.createNativeQuery("select j.id, j.name_et from journal j where j.id in (?1)")
                    .setParameter(1, journalIds).getResultList();
            journals = StreamUtil.toMap(r -> resultAsLong(r, 0),
                    r -> new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 1)), data);
        }
        return journals;
    }

    private static List<LocalDate> weekDates(LocalDate start, LocalDate end) {
        List<LocalDate> weekDates = new ArrayList<>();
        while (end.isAfter(start) || end.isEqual(start)) {
            weekDates.add(start);
            start = start.plusDays(1);
        }
        return weekDates;
    }

    private static Map<LocalDate, List<AutocompleteResult>> journalsByDates(List<StudentGroupAbsenceDto> studentAbsences,
            Map<Long, AutocompleteResult> journals, List<LocalDate> dates) {
        Map<LocalDate, List<AutocompleteResult>> journalsByDates = new LinkedHashMap<>();
        for (LocalDate date : dates) {
            journalsByDates.put(date, new ArrayList<>());
        }
        for (StudentGroupAbsenceDto absence : studentAbsences) {
            AutocompleteResult absenceJournal = journals.get(absence.getJournal()); 
            List<AutocompleteResult> journalsByDate = journalsByDates.get(absence.getEntryDate());
            if (journalsByDate != null) {
                if (!journalsByDate.contains(absenceJournal)) {
                    journalsByDate.add(absenceJournal);
                }
                journalsByDate.sort(Comparator.comparing(AutocompleteResult::getNameEt));
            }
        }
        return journalsByDates;
    }

    private List<AutocompleteResult> students(HoisUserDetails user, StudentGroupAbsenceCommand criteria,
            List<Long> studentIds) {
        // studentIds are used in criteria when only todays absences are searched
        // when there are no absences then there no students should be shown
        if (studentIds != null && studentIds.isEmpty()) {
            return new ArrayList<>();
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
                + "join person p on s.person_id = p.id "
                + "left join student_group sg on sg.id = s.student_group_id");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("s.id in (:studentIds)", "studentIds", studentIds);
        if (user.isTeacher()) {
            qb.optionalCriteria("sg.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        }
        qb.optionalCriteria("s.student_group_id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());
        qb.optionalCriteria("s.status_code in (:studentStatuses)", "studentStatuses",
                StudentStatus.STUDENT_STATUS_ACTIVE);

        qb.sort("p.lastname, p.firstname");
        List<?> data = qb.select("s.id, p.firstname, p.lastname, s.type_code as studentType", em).getResultList();
        return StreamUtil.toMappedList(s -> new AutocompleteResult(resultAsLong(s, 0),
                PersonUtil.fullnameOptionalGuest(resultAsString(s, 1), resultAsString(s, 2), resultAsString(s, 3)),
                PersonUtil.fullnameOptionalGuest(resultAsString(s, 1), resultAsString(s, 2), resultAsString(s, 3))), data);
    }

    public List<StudyWeekDto> studyYearWeeks(Long studyYearId) {
        List<StudyWeekDto> weeks = new ArrayList<>();
        
        List<StudyPeriod> studyPeriods = em
                .createQuery("select sp from StudyPeriod sp where sp.studyYear.id = ?1 order by sp.startDate",
                        StudyPeriod.class)
                .setParameter(1, studyYearId).getResultList();
        
        int weekNr = 1;
        for (StudyPeriod studyPeriod : studyPeriods) {
            for (LocalDate start : studyPeriod.getWeekBeginningDates()) {
                StudyWeekDto week = new StudyWeekDto();
                week.setNr(Long.valueOf(weekNr++));
                week.setStart(start);
                week.setEnd(start.plusDays(6));
                weeks.add(week);
            }
        }
        return weeks;
    }

    public void updateJournalEntryStudentAbsence(JournalEntryStudent absence, StudentGroupAbsenceForm form) {
        absence.setAbsence(em.getReference(Classifier.class, form.getAbsence()));
        if (Absence.PUUDUMINE_V.name().equals(form.getAbsence()) || Absence.PUUDUMINE_PR.name().equals(form.getAbsence())) {
            absence.setAbsenceAccepted(LocalDateTime.now());
        } else {
            absence.setAbsenceAccepted(null);
        }
        EntityUtil.save(absence, em);
    }

    public void updateJournalEntryStudentLessonAbsence(JournalEntryStudentLessonAbsence absence, StudentGroupAbsenceForm form) {
        absence.setAbsence(em.getReference(Classifier.class, form.getAbsence()));
        if (Absence.PUUDUMINE_V.name().equals(form.getAbsence()) || Absence.PUUDUMINE_PR.name().equals(form.getAbsence())) {
            absence.setAbsenceAccepted(LocalDateTime.now());
        } else {
            absence.setAbsenceAccepted(null);
        }
        EntityUtil.save(absence, em);

    }

    public Map<String, Boolean> teacherHasTodaysAbsences(HoisUserDetails user) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(STUDENT_GROUP_ABSENCE_FROM);
        qb.requiredCriteria("sg.teacher_id = :teacherId", "teacherId", user.getTeacherId());
        LocalDate today = LocalDate.now();
        qb.requiredCriteria("je.entry_date >= :from", "from", today);
        qb.requiredCriteria("je.entry_date <= :thru", "thru", today);
        qb.filter("(jes.absence_code is not null or jesla.absence_code is not null)");

        List<?> data = qb.select(STUDENT_GROUP_ABSENCE_SELECT, em).getResultList();
        return Collections.singletonMap("hasTodaysAbsences", Boolean.valueOf(data.size() > 0));
    }

}
