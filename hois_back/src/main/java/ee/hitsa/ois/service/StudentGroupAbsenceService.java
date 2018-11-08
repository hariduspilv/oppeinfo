package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
            + "left join journal_entry_student_lesson_absence jesla on jes.id = jesla.journal_entry_student_id";

    public StudentGroupAbsenceDtoContainer get(StudentGroupAbsenceCommand criteria) {
        StudentGroupAbsenceDtoContainer container = new StudentGroupAbsenceDtoContainer();
        List<StudentGroupAbsenceDto> studentAbsences = studentAbsences(criteria);
        container.setStudentAbsences(studentAbsences);

        Map<Long, AutocompleteResult> journals = journals(StreamUtil.toMappedSet(r -> r.getJournal(), studentAbsences));
        List<LocalDate> dates = weekDates(criteria.getStudyWeekStart(), criteria.getStudyWeekEnd());
        Map<LocalDate, List<AutocompleteResult>> journalsByDates = journalsByDates(studentAbsences, journals, dates);

        container.setStudents(students(criteria.getStudentGroup()));
        container.setDates(dates);
        container.setJournalsByDates(journalsByDates);
        return container;
    }

    private List<StudentGroupAbsenceDto> studentAbsences(StudentGroupAbsenceCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(STUDENT_GROUP_ABSENCE_FROM);
        qb.requiredCriteria("s.student_group_id = :studentGroupId", "studentGroupId", criteria.getStudentGroup());
        qb.requiredCriteria("je.entry_date >= :studyWeekStart", "studyWeekStart", criteria.getStudyWeekStart());
        qb.requiredCriteria("je.entry_date <= :studyWeekEnd", "studyWeekEnd", criteria.getStudyWeekEnd());
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
            if (!journalsByDate.contains(absenceJournal)) {
                journalsByDate.add(absenceJournal);
            }
            journalsByDate.sort(Comparator.comparing(AutocompleteResult::getNameEt));
        }
        return journalsByDates;
    }
    
    private List<AutocompleteResult> students(Long studentGroupId) {
        List<?> data = em
                .createNativeQuery("select s.id, p.firstname, p.lastname from student s "
                        + "join person p on s.person_id = p.id "
                        + "where s.student_group_id = ?1 and s.status_code in (?2) order by p.lastname, p.firstname")
                .setParameter(1, studentGroupId)
                .setParameter(2, StudentStatus.STUDENT_STATUS_ACTIVE)
                .getResultList();
        return StreamUtil.toMappedList(s -> new AutocompleteResult(resultAsLong(s, 0),
                PersonUtil.fullname(resultAsString(s, 1), resultAsString(s, 2)),
                PersonUtil.fullname(resultAsString(s, 1), resultAsString(s, 2))), data);
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
}
