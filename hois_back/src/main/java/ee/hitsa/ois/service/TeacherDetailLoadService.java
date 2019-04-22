package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.report.teacherdetailload.LoadTypeDto;
import ee.hitsa.ois.report.teacherdetailload.PeriodDto;
import ee.hitsa.ois.report.teacherdetailload.ResultRowDto;
import ee.hitsa.ois.report.teacherdetailload.TeacherDetailLoadReport;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.LessonPlanUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.report.TeacherDetailLoadCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.report.teacherdetailload.PeriodDetailLoadDto;
import ee.hitsa.ois.web.dto.report.teacherdetailload.TeacherDetailLoadDto;
import ee.hitsa.ois.web.dto.report.teacherdetailload.TeacherDetailLoadJournalSubjectDto;
import ee.hitsa.ois.web.dto.report.teacherdetailload.TeacherDetailLoadReportDataDto;
import ee.hitsa.ois.web.dto.timetable.LessonPlanDto.StudyPeriodDto;

@Transactional
@Service
public class TeacherDetailLoadService {

    @Autowired
    private EntityManager em;
    @Autowired
    private XlsService xlsService;
    @Autowired
    private SchoolService schoolService;

    private static final String NO_CAPACITY_TYPE = "?";

    public TeacherDetailLoadReportDataDto teacherDetailLoadReportData(TeacherDetailLoadCommand criteria) {
        TeacherDetailLoadReportDataDto dto = new TeacherDetailLoadReportDataDto();

        StudyYear studyYear = em.getReference(StudyYear.class, criteria.getStudyYear());
        dto.setStudyPeriods(studyYear.getStudyPeriods().stream().sorted(Comparator.comparing(StudyPeriod::getStartDate))
                .map(StudyPeriodDto::new).collect(Collectors.toList()));

        if (Boolean.FALSE.equals(criteria.getIsHigher())) {
            List<Short> weekNrs = dto.getStudyPeriods().stream().flatMap(r -> r.getWeekNrs().stream())
                    .collect(Collectors.toList());
            dto.setWeekNrs(weekNrs);
            List<LocalDate> weekBeginningDates = dto.getStudyPeriods().stream()
                    .flatMap(r -> r.getWeekBeginningDates().stream()).collect(Collectors.toList());
            dto.setWeekBeginningDates(weekBeginningDates);
            dto.setWeekBeginningDateMap(LessonPlanUtil.weekBeginningDateMap(weekNrs, weekBeginningDates));
            dto.setWeekNrsByMonth(weeksByMonth(dto.getWeekBeginningDateMap()));
            dto.setMonths(monthsMatchingCriteria(criteria, weekBeginningDates));
        }

        dto.setCriteria(criteria);
        return dto;
    }

    private static Map<Long, List<Short>> weeksByMonth(Map<Short, LocalDate> weekBeginningDateMap) {
        Map<Long, List<Short>> weeksByMonth = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            weeksByMonth.put(Long.valueOf(i), new ArrayList<>());
        }

        // sort weeks by months
        for (Short weekNr : weekBeginningDateMap.keySet()) {
            LocalDate weekNrStart = weekBeginningDateMap.get(weekNr);
            Long monthValue = Long.valueOf(weekNrStart.getMonthValue());
            List<Short> monthWeeks = weeksByMonth.get(monthValue);
            if (!monthWeeks.contains(weekNr)) {
                monthWeeks.add(weekNr);
            }
        }

        // if most of month's last week is in the next month - transfer it to that month
        TemporalField wom = WeekFields.ISO.weekOfMonth();
        for (Long monthValue : weeksByMonth.keySet()) {
            List<Short> monthWeeks = weeksByMonth.get(monthValue);
            Short lastWeekStartNr = monthWeeks.size() > 0 ? monthWeeks.get(monthWeeks.size() - 1) : null;
            if (lastWeekStartNr == null) {
                continue;
            }
            LocalDate lastWeekStart = weekBeginningDateMap.get(lastWeekStartNr);

            if (lastWeekStart != null) {
                LocalDate lastDayOfMonth = lastWeekStart.with(TemporalAdjusters.lastDayOfMonth());
                int monthLastWeekNr = lastDayOfMonth.get(wom);
                if (lastWeekStart.get(wom) == monthLastWeekNr) {
                    if (Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY)
                            .contains(lastDayOfMonth.getDayOfWeek())) {
                        int nextMonth = monthValue.intValue() == 12 ? 1 : monthValue.intValue() + 1;
                        weeksByMonth.get(monthValue).remove(lastWeekStartNr);

                        List<Short> nextMonthWeekStarts = weeksByMonth.get(Long.valueOf(nextMonth));
                        nextMonthWeekStarts.add(0, lastWeekStartNr);
                    }
                }
            }
        }
        return weeksByMonth;
    }

    private static List<Long> monthsMatchingCriteria(TeacherDetailLoadCommand criteria,
            List<LocalDate> weekBeginningDates) {
        List<Long> months = new ArrayList<>();
        LocalDate from = criteria.getFrom();
        LocalDate thru = criteria.getThru();
        for (LocalDate weekStart : weekBeginningDates) {
            if ((from == null || weekStart.equals(from) || weekStart.isAfter(from))
                    && (thru == null || weekStart.equals(thru) || weekStart.isBefore(thru))) {
                Long monthValue = Long.valueOf(weekStart.getMonthValue());
                if (!months.contains(monthValue)) {
                    months.add(Long.valueOf(weekStart.getMonthValue()));
                }
            }
        }

        Long fromMonthValue = from != null ? Long.valueOf(from.getMonthValue()) : null;
        if (fromMonthValue != null && !months.contains(fromMonthValue)) {
            months.add(0, fromMonthValue);
        }
        Long thruMonthValue = thru != null ? Long.valueOf(thru.getMonthValue()) : null;
        if (fromMonthValue != null && !months.contains(thruMonthValue)) {
            months.add(thruMonthValue);
        }
        return months;
    }

    public Page<TeacherDetailLoadDto> teacherVocationalDetailLoad(Long schoolId, TeacherDetailLoadCommand criteria,
            Pageable pageable) {
        TeacherDetailLoadReportDataDto report = teacherDetailLoadReportData(criteria);
        Page<TeacherDetailLoadDto> teachers = teachers(schoolId, criteria, pageable);
        List<Long> teacherIds = StreamUtil.toMappedList(t -> t.getTeacher().getId(), teachers.getContent());

        List<Classifier> capacities = getTeacherCapacities(schoolId, criteria, teacherIds);
        Map<Long, List<PlannedLoad>> plannedLoads = new HashMap<>();
        if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
            plannedLoads = teacherVocationalPlannedLoads(criteria, teacherIds, report);
        }
        Map<Long, List<TimetableEvent>> occurredLessons = teacherOccurredLessons(schoolId, criteria, teacherIds);
        Map<Long, List<TimetableEvent>> substitutedLessons = teacherLessonsAsSubstitute(schoolId, criteria, teacherIds);
        Map<Long, List<TimetableEvent>> singleEvents = teacherSingleEvents(schoolId, criteria, teacherIds);

        for (TeacherDetailLoadDto dto : teachers.getContent()) {
            Long teacherId = dto.getTeacher().getId();

            if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
                List<PlannedLoad> teacherPlannedHours = plannedLoads.get(teacherId);
                setTeacherDetailLoadPlannedHours(dto, criteria, teacherPlannedHours, capacities, report);
            }

            List<TimetableEvent> teacherOccurredHours = occurredLessons.get(teacherId);
            setTeacherDetailLoadOccurredHours(dto, criteria, teacherOccurredHours, capacities, report);

            if (Boolean.TRUE.equals(criteria.getByCapacities())) {
                dto.setTeacherCapacities(getTeacherPeriodCapacities(dto));
            }

            if (Boolean.TRUE.equals(criteria.getShowSingleEvents())) {
                List<TimetableEvent> teacherSingleEvents = singleEvents.get(teacherId);
                setTeacherDetailLoadSingleEvents(dto, criteria, teacherSingleEvents, report);
            }

            List<TimetableEvent> teacherSubstitutedLessons = substitutedLessons.get(teacherId);
            setTeacherDetailLoadSubstitutedLessons(dto, criteria, teacherSubstitutedLessons, report);
        }
        return teachers;
    }

    public Page<TeacherDetailLoadDto> teacherHigherDetailLoad(Long schoolId, TeacherDetailLoadCommand criteria,
            Pageable pageable) {
        TeacherDetailLoadReportDataDto report = teacherDetailLoadReportData(criteria);
        Page<TeacherDetailLoadDto> teachers = teachers(schoolId, criteria, pageable);
        List<Long> teacherIds = StreamUtil.toMappedList(t -> t.getTeacher().getId(), teachers.getContent());

        List<Classifier> capacities = getTeacherCapacities(schoolId, criteria, teacherIds);
        Map<Long, List<PlannedLoad>> plannedLoads = new HashMap<>();
        if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
            plannedLoads = teacherHigherPlannedLoads(criteria, teacherIds);
        }
        Map<Long, List<TimetableEvent>> occurredLessons = teacherOccurredLessons(schoolId, criteria, teacherIds);
        Map<Long, List<TimetableEvent>> substitutedLessons = teacherLessonsAsSubstitute(schoolId, criteria, teacherIds);
        Map<Long, List<TimetableEvent>> singleEvents = teacherSingleEvents(schoolId, criteria, teacherIds);

        for (TeacherDetailLoadDto dto : teachers.getContent()) {
            Long teacherId = dto.getTeacher().getId();

            if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
                List<PlannedLoad> teacherPlannedHours = plannedLoads.get(teacherId);
                setTeacherDetailLoadPlannedHours(dto, criteria, teacherPlannedHours, capacities, report);
            }

            List<TimetableEvent> teacherOccurredHours = occurredLessons.get(teacherId);
            setTeacherDetailLoadOccurredHours(dto, criteria, teacherOccurredHours, capacities, report);

            if (Boolean.TRUE.equals(criteria.getByCapacities())) {
                dto.setTeacherCapacities(getTeacherPeriodCapacities(dto));
            }

            if (Boolean.TRUE.equals(criteria.getShowSingleEvents())) {
                List<TimetableEvent> teacherSingleEvents = singleEvents.get(teacherId);
                setTeacherDetailLoadSingleEvents(dto, criteria, teacherSingleEvents, report);
            }

            List<TimetableEvent> teacherSubstitutedLessons = substitutedLessons.get(teacherId);
            setTeacherDetailLoadSubstitutedLessons(dto, criteria, teacherSubstitutedLessons, report);
        }

        return teachers;
    }

    private static Set<String> getTeacherPeriodCapacities(PeriodDetailLoadDto dto) {
        Set<String> teacherCapacities = new HashSet<>();
        if (!dto.getTotalCapacityOccurredLessons().isEmpty()) {
            teacherCapacities.addAll(nonNullCapacities(dto.getTotalCapacityOccurredLessons()));
        }
        if (!dto.getTotalCapacityPlannedHours().isEmpty()) {
            teacherCapacities.addAll(nonNullCapacities(dto.getTotalCapacityPlannedHours()));
        }
        return teacherCapacities;
    }

    private static List<String> nonNullCapacities(Map<String, Long> capacityTotals) {
        return StreamUtil.toFilteredList(c -> capacityTotals.get(c).longValue() > 0, capacityTotals.keySet());
    }

    private static void setTeacherDetailLoadPlannedHours(PeriodDetailLoadDto dto, TeacherDetailLoadCommand criteria,
            List<PlannedLoad> teacherPlannedHours, List<Classifier> capacities,
            TeacherDetailLoadReportDataDto report) {
        if (teacherPlannedHours == null) {
            return;
        }

        if (Boolean.TRUE.equals(criteria.getByCapacities())) {
            if (Boolean.TRUE.equals(criteria.getByStudyPeriods()) || Boolean.TRUE.equals(criteria.getIsHigher())) {
                List<StudyPeriodDto> studyPeriods = report.getStudyPeriods();
                dto.setCapacityPlannedHours(studyPeriodCapacityPlannedHours(criteria.getIsHigher(), teacherPlannedHours,
                        studyPeriods, capacities));
            } else if (Boolean.TRUE.equals(criteria.getByWeeks())) {
                List<Short> weekNrs = report.getWeekNrs();
                dto.setCapacityPlannedHours(weekCapacityPlannedHours(teacherPlannedHours, weekNrs, capacities));
            } else if (Boolean.TRUE.equals(criteria.getByMonths())) {
                Map<Long, List<Short>> weeksByMonth = report.getWeekNrsByMonth();
                dto.setCapacityPlannedHours(
                        monthCapacityPlannedHours(teacherPlannedHours, weeksByMonth, capacities));
            }
            dto.setTotalCapacityPlannedHours(totalCapacityPlannedHours(teacherPlannedHours, capacities));
        } else {
            if (Boolean.TRUE.equals(criteria.getByStudyPeriods()) || Boolean.TRUE.equals(criteria.getIsHigher())) {
                List<StudyPeriodDto> studyPeriods = report.getStudyPeriods();
                dto.setPlannedHours(studyPeriodPlannedHours(criteria.getIsHigher(), teacherPlannedHours, studyPeriods));
            } else if (Boolean.TRUE.equals(criteria.getByWeeks())) {
                List<Short> weekNrs = report.getWeekNrs();
                dto.setPlannedHours(weekPlannedHours(teacherPlannedHours, weekNrs));
            } else if (Boolean.TRUE.equals(criteria.getByMonths())) {
                Map<Long, List<Short>> weeksByMonth = report.getWeekNrsByMonth();
                dto.setPlannedHours(monthPlannedHours(teacherPlannedHours, weeksByMonth));
            }
        }
        dto.setTotalPlannedHours(totalPlannedHours(teacherPlannedHours));
    }

    private static void setTeacherDetailLoadOccurredHours(PeriodDetailLoadDto dto, TeacherDetailLoadCommand criteria,
            List<TimetableEvent> teacherOccurredHours, List<Classifier> capacities,
            TeacherDetailLoadReportDataDto report) {
        if (teacherOccurredHours == null) {
            return;
        }

        if (Boolean.TRUE.equals(criteria.getByCapacities())) {
            if (Boolean.TRUE.equals(criteria.getByStudyPeriods()) || Boolean.TRUE.equals(criteria.getIsHigher())) {
                List<StudyPeriodDto> studyPeriods = report.getStudyPeriods();
                dto.setCapacityOccurredLessons(
                        studyPeriodCapacityOccurredEvents(teacherOccurredHours, studyPeriods, capacities));
            } else if (Boolean.TRUE.equals(criteria.getByWeeks())) {
                Map<Short, LocalDate> weekBeginningDateMap = report.getWeekBeginningDateMap();
                dto.setCapacityOccurredLessons(
                        weekCapacityOccurredEvents(teacherOccurredHours, weekBeginningDateMap, capacities));
            } else if (Boolean.TRUE.equals(criteria.getByMonths())) {
                Set<Long> months = report.getWeekNrsByMonth().keySet();
                dto.setCapacityOccurredLessons(
                        monthCapacityOccurredEvents(teacherOccurredHours, months, capacities));
            }
            dto.setTotalCapacityOccurredLessons(totalCapacityOccurredEvents(teacherOccurredHours, capacities));
        } else {
            if (Boolean.TRUE.equals(criteria.getByStudyPeriods()) || Boolean.TRUE.equals(criteria.getIsHigher())) {
                List<StudyPeriodDto> studyPeriods = report.getStudyPeriods();
                dto.setOccurredLessons(studyPeriodOccurredEvents(teacherOccurredHours, studyPeriods));
            } else if (Boolean.TRUE.equals(criteria.getByWeeks())) {
                Map<Short, LocalDate> weekBeginningDateMap = report.getWeekBeginningDateMap();
                dto.setOccurredLessons(weekOccurredEvents(teacherOccurredHours, weekBeginningDateMap));
            } else if (Boolean.TRUE.equals(criteria.getByMonths())) {
                Set<Long> months = report.getWeekNrsByMonth().keySet();
                dto.setOccurredLessons(monthOccurredEvents(teacherOccurredHours, months));
            }
        }
        dto.setTotalOccurredLessons(totalOccurredEvents(teacherOccurredHours));
    }

    private static void setTeacherDetailLoadSingleEvents(PeriodDetailLoadDto dto, TeacherDetailLoadCommand criteria,
            List<TimetableEvent> teacherSingleEvents, TeacherDetailLoadReportDataDto report) {
        if (teacherSingleEvents == null) {
            return;
        }

        if (Boolean.TRUE.equals(criteria.getByStudyPeriods()) || Boolean.TRUE.equals(criteria.getIsHigher())) {
            List<StudyPeriodDto> studyPeriods = report.getStudyPeriods();
            dto.setSingleEvents(studyPeriodOccurredEvents(teacherSingleEvents, studyPeriods));
        } else if (Boolean.TRUE.equals(criteria.getByWeeks())) {
            Map<Short, LocalDate> weekBeginningDateMap = report.getWeekBeginningDateMap();
            dto.setSingleEvents(weekOccurredEvents(teacherSingleEvents, weekBeginningDateMap));
        } else if (Boolean.TRUE.equals(criteria.getByMonths())) {
            Set<Long> months = report.getWeekNrsByMonth().keySet();
            dto.setSingleEvents(monthOccurredEvents(teacherSingleEvents, months));
        }
        dto.setTotalSingleEvents(totalOccurredEvents(teacherSingleEvents));
    }

    private static void setTeacherDetailLoadSubstitutedLessons(PeriodDetailLoadDto dto,
            TeacherDetailLoadCommand criteria, List<TimetableEvent> teacherSubstitutedLessons,
            TeacherDetailLoadReportDataDto report) {
        if (teacherSubstitutedLessons == null) {
            return;
        }

        if (Boolean.TRUE.equals(criteria.getByStudyPeriods()) || Boolean.TRUE.equals(criteria.getIsHigher())) {
            List<StudyPeriodDto> studyPeriods = report.getStudyPeriods();
            dto.setSubstitutedLessons(studyPeriodOccurredEvents(teacherSubstitutedLessons, studyPeriods));
        } else if (Boolean.TRUE.equals(criteria.getByWeeks())) {
            Map<Short, LocalDate> weekBeginningDateMap = report.getWeekBeginningDateMap();
            dto.setSubstitutedLessons(weekOccurredEvents(teacherSubstitutedLessons, weekBeginningDateMap));
        } else if (Boolean.TRUE.equals(criteria.getByMonths())) {
            Set<Long> months = report.getWeekNrsByMonth().keySet();
            dto.setSubstitutedLessons(monthOccurredEvents(teacherSubstitutedLessons, months));
        }
        dto.setTotalSubstitutedLessons(totalOccurredEvents(teacherSubstitutedLessons));
    }

    private Page<TeacherDetailLoadDto> teachers(Long schoolId, TeacherDetailLoadCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from teacher t inner join person p on t.person_id = p.id")
                .sort("p.lastname", "p.firstname");

        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", schoolId);
        if (Boolean.TRUE.equals(criteria.getIsHigher())) {
            qb.optionalCriteria("t.is_higher = :higher", "higher", Boolean.TRUE);
        } else {
            qb.optionalCriteria("t.is_vocational = :vocational", "vocational", Boolean.TRUE);
        }
        qb.optionalCriteria("t.is_active = :active", "active", Boolean.TRUE);
        qb.optionalCriteria("t.id = :teacherId", "teacherId", criteria.getTeacher());

        return JpaQueryUtil.pagingResult(qb, "t.id, p.firstname, p.lastname", em, pageable).map(r -> {
            TeacherDetailLoadDto dto = new TeacherDetailLoadDto(); 
            String name = PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2));
            dto.setTeacher(new AutocompleteResult(resultAsLong(r, 0), name, name));
            return dto;
        });
    }

    private List<Classifier> getTeacherCapacities(Long schoolId, TeacherDetailLoadCommand criteria,
            List<Long> teacherIds) {
        String capacitiesQuery = "select c.code from school_capacity_type sct"
                + " join classifier c on sct.capacity_type_code = c.code"
                + " where sct.school_id = :schoolId and sct.is_usable = true and sct.is_timetable = true";

        if (!teacherIds.isEmpty()) {
            if (Boolean.TRUE.equals(criteria.getIsHigher())) {
                capacitiesQuery += " union all"
                        + " select sspc.capacity_type_code from subject_study_period_capacity sspc"
                        + " join subject_study_period ssp on ssp.id = sspc.subject_study_period_id"
                        + " join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id"
                        + " join study_period sp on sp.id = ssp.study_period_id"
                        + " where sspt.teacher_id in (:teacherIds) and sp.study_year_id = :studyYearId";
                if (criteria.getStudyPeriod() != null) {
                    capacitiesQuery += " and sp.id = :studyPeriodId";
                }
            } else {
                capacitiesQuery += " union all"
                        + " select jct.capacity_type_code from journal_teacher jt"
                        + " join journal j on jt.journal_id = j.id"
                        + " join journal_capacity_type jct on j.id = jct.journal_id"
                        + " where jt.teacher_id in (:teacherIds) and j.study_year_id = :studyYearId";
            }
        }
        
        Query capacacitiesQuery = em.createNativeQuery(capacitiesQuery);
        capacacitiesQuery.setParameter("schoolId", schoolId);
        if (!teacherIds.isEmpty()) {
            capacacitiesQuery.setParameter("teacherIds", teacherIds);
            capacacitiesQuery.setParameter("studyYearId", criteria.getStudyYear());
            if (Boolean.TRUE.equals(criteria.getIsHigher()) && criteria.getStudyPeriod() != null) {
                capacacitiesQuery.setParameter("studyPeriodId", criteria.getStudyPeriod());
            }
        }

        List<?> capacitiesData = capacacitiesQuery.getResultList();
        Set<String> capacityCodes = StreamUtil.toMappedSet(r -> resultAsString(r, 0), capacitiesData);
        List<Classifier> capacities = new ArrayList<>();
        if (!capacityCodes.isEmpty()) {
            capacities = em.createQuery("select c from Classifier c where c.code in (:codes)", Classifier.class)
                    .setParameter("codes", capacityCodes).getResultList();
        }
        return capacities;
    }

    private Map<Long, List<PlannedLoad>> teacherVocationalPlannedLoads(TeacherDetailLoadCommand criteria,
            List<Long> teacherIds, TeacherDetailLoadReportDataDto report) {
        StudyPeriodDto studyPeriod = null;
        if (criteria.getStudyPeriod() != null) {
            StudyPeriod sp = em.getReference(StudyPeriod.class, criteria.getStudyPeriod());
            studyPeriod = new StudyPeriodDto(sp);
        }
        List<Short> weeksBetweenFromAndThru = weeksBetweenFromAndThru(criteria.getFrom(), criteria.getThru(),
                report.getWeekBeginningDateMap());

        Map<Long, List<PlannedLoad>> plannedLoads = teacherJournalPlannedLoads(criteria, studyPeriod,
                weeksBetweenFromAndThru, teacherIds);
        Map<Long, List<PlannedLoad>> specificPlannedLoads = teacherJournalSpecificPlannedLoads(criteria,
                studyPeriod, weeksBetweenFromAndThru, teacherIds);
        joinTeacherPlannedLoads(plannedLoads, specificPlannedLoads);
        return plannedLoads;
    }

    private static List<Short> weeksBetweenFromAndThru(LocalDate from, LocalDate thru, Map<Short, LocalDate> weeks) {
        List<Short> weeksBetweenFromAndThru = new ArrayList<>();
        if (from != null || thru != null) {
            LocalDate fromWeekStart = from != null ? previousMonday(from) : null;
            LocalDate thruWeekStart = thru != null ? previousMonday(thru) : null;
            for (Short weekNr : weeks.keySet()) {
                LocalDate weekStart = weeks.get(weekNr);
                if ((fromWeekStart == null || weekStart.isEqual(fromWeekStart) || weekStart.isAfter(fromWeekStart)) &&
                    (thruWeekStart == null || weekStart.isEqual(thruWeekStart) || weekStart.isBefore(thruWeekStart))) {
                    weeksBetweenFromAndThru.add(weekNr);
                }
            }
        }
        return weeksBetweenFromAndThru;
    }

    private static LocalDate previousMonday(LocalDate date) {
        if (!date.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            return date.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        }
        return date;
    }

    private Map<Long, List<PlannedLoad>> teacherJournalPlannedLoads(TeacherDetailLoadCommand criteria,
            StudyPeriodDto studyPeriod, List<Short> weeksBetweenFromAndThru, List<Long> teacherIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal_teacher jt"
                + " join journal j on jt.journal_id = j.id"
                + " join journal_capacity jc on jc.journal_id = j.id and (j.is_capacity_diff is null or j.is_capacity_diff = false)"
                + " join journal_capacity_type jct on jc.journal_capacity_type_id = jct.id");
        qb.requiredCriteria("j.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("jt.teacher_id in (:teacherIds)", "teacherIds", teacherIds);

        if (criteria.getStudyPeriod() != null) {
            qb.requiredCriteria("jc.week_nr in (:spWeekNrs)", "spWeekNrs", studyPeriod.getWeekNrs());
        }

        if (criteria.getFrom() != null || criteria.getThru() != null) {
            if (weeksBetweenFromAndThru.isEmpty() ) {
                return new HashMap<>();
            }
            qb.requiredCriteria("jc.week_nr in (:weekNrs)", "weekNrs", weeksBetweenFromAndThru);
        }

        List<?> data = qb.select("jt.teacher_id, jct.capacity_type_code, jc.week_nr, jc.hours,"
                + " j.id journal_id, null study_period_id, null subject_study_period_id", em).getResultList();
        return mapTeacherLoads(data);
    }

    private Map<Long, List<PlannedLoad>> teacherJournalSpecificPlannedLoads(
            TeacherDetailLoadCommand criteria, StudyPeriodDto studyPeriod, List<Short> weeksBetweenFromAndThru,
            List<Long> teacherIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal_teacher jt"
                + " join journal j on jt.journal_id = j.id"
                + " join journal_teacher_capacity jtc on jt.id = jtc.journal_teacher_id and j.is_capacity_diff = true"
                + " join journal_capacity_type jct on jtc.journal_capacity_type_id = jct.id");
        qb.requiredCriteria("j.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("jt.teacher_id in (:teacherIds)", "teacherIds", teacherIds);
        
        if (criteria.getStudyPeriod() != null) {
            qb.requiredCriteria("jtc.week_nr in (:spWeekNrs)", "spWeekNrs", studyPeriod.getWeekNrs());
        }

        if (criteria.getFrom() != null || criteria.getThru() != null) {
            if (weeksBetweenFromAndThru.isEmpty() ) {
                return new HashMap<>();
            }
            qb.requiredCriteria("jtc.week_nr in (:weekNrs)", "weekNrs", weeksBetweenFromAndThru);
        }

        List<?> data = qb.select("jt.teacher_id, jct.capacity_type_code, jtc.week_nr, jtc.hours,"
                + " j.id journal_id, null study_period_id, null subject_study_period_id", em).getResultList();
        return mapTeacherLoads(data);
    }

    private Map<Long, List<PlannedLoad>> teacherHigherPlannedLoads(TeacherDetailLoadCommand criteria,
            List<Long> teacherIds) {
        Map<Long, List<PlannedLoad>> plannedLoads = teacherSubjectStudyPeriodPlannedLoads(criteria, teacherIds);
        Map<Long, List<PlannedLoad>> specificPlannedLoads = teacherSubjectStudyPeriodSpecificPlannedLoads(criteria,
                teacherIds);
        joinTeacherPlannedLoads(plannedLoads, specificPlannedLoads);
        return plannedLoads;
    }

    private Map<Long, List<PlannedLoad>> teacherSubjectStudyPeriodPlannedLoads(TeacherDetailLoadCommand criteria,
            List<Long> teacherIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period_capacity sspc"
                + " join subject_study_period ssp on ssp.id = sspc.subject_study_period_id"
                    + " and (ssp.is_capacity_diff is null or ssp.is_capacity_diff = false)"
                + " join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id"
                + " join study_period sp on sp.id = ssp.study_period_id");
        qb.requiredCriteria("sp.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("sp.id = :studyPeriodId", "studyPeriodId", criteria.getStudyPeriod());
        qb.optionalCriteria("sspt.teacher_id in (:teacherIds)", "teacherIds", teacherIds);

        List<?> data = qb.select("sspt.teacher_id, sspc.capacity_type_code, null week_nr, sspc.hours,"
                + " null journal_id, sp.id study_period_id, ssp.id subject_study_period_id", em).getResultList();
        return mapTeacherLoads(data);
    }

    private Map<Long, List<PlannedLoad>> teacherSubjectStudyPeriodSpecificPlannedLoads(
            TeacherDetailLoadCommand criteria, List<Long> teacherIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period_capacity sspc"
                + " join subject_study_period ssp on ssp.id = sspc.subject_study_period_id and ssp.is_capacity_diff = true"
                + " join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id"
                + " join subject_study_period_teacher_capacity ssptc on ssptc.subject_study_period_capacity_id = sspc.id"
                    + " and ssptc.subject_study_period_teacher_id = sspt.id"
                + " join study_period sp on sp.id = ssp.study_period_id");
        qb.requiredCriteria("sp.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("sp.id = :studyPeriodId", "studyPeriodId", criteria.getStudyPeriod());
        qb.optionalCriteria("sspt.teacher_id in (:teacherIds)", "teacherIds", teacherIds);

        List<?> data = qb.select("sspt.teacher_id, sspc.capacity_type_code, null week_nr, ssptc.hours,"
                + " null journal_id, sp.id study_period_id, ssp.id subject_study_period_id", em).getResultList();
        return mapTeacherLoads(data);
    }

    private static Map<Long, List<PlannedLoad>> mapTeacherLoads(List<?> data) {
        Map<Long, List<PlannedLoad>> teacherLoads = new HashMap<>();
        if (!data.isEmpty()) {
            teacherLoads = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), 
                    Collectors.mapping(r -> new PlannedLoad(resultAsString(r, 1), resultAsShort(r, 2), 
                            resultAsLong(r, 3), resultAsLong(r, 4), resultAsLong(r, 5), resultAsLong(r, 6)),
                            Collectors.toList())));
        }
        return teacherLoads;
    }

    private static void joinTeacherPlannedLoads(Map<Long, List<PlannedLoad>> plannedLoads,
            Map<Long, List<PlannedLoad>> specificPlannedLoads) {
        for (Long teacherId : specificPlannedLoads.keySet()) {
            List<PlannedLoad> teacherSpecificCapacities = specificPlannedLoads.get(teacherId);
            if (!plannedLoads.containsKey(teacherId)) {
                plannedLoads.put(teacherId, teacherSpecificCapacities);
            } else {
                plannedLoads.get(teacherId).addAll(teacherSpecificCapacities);
            }
        }
    }

    private static Map<Long, Long> studyPeriodPlannedHours(Boolean isHigher, List<PlannedLoad> teacherPlannedHours,
            List<StudyPeriodDto> studyPeriods) {
        Map<Long, Long> plannedHoursBySp = new HashMap<>();
        for (StudyPeriodDto sp : studyPeriods) {
            Long sum = Boolean.TRUE.equals(isHigher)
                    ? periodPlannedHoursSumByStudyPeriod(teacherPlannedHours, sp.getId())
                    : periodPlannedHoursSumByWeeks(teacherPlannedHours, sp.getWeekNrs());
            plannedHoursBySp.put(sp.getId(), sum);
        }
        return plannedHoursBySp;
    }

    private static Map<Long, Long> monthPlannedHours(List<PlannedLoad> teacherPlannedHours,
            Map<Long, List<Short>> weeksByMonth) {
        Map<Long, Long> plannedHoursByMonth = new HashMap<>();
        for (Long month : weeksByMonth.keySet()) {
            List<Short> monthWeeks = weeksByMonth.containsKey(month) ? weeksByMonth.get(month) : new ArrayList<>();
            plannedHoursByMonth.put(month, periodPlannedHoursSumByWeeks(teacherPlannedHours, monthWeeks));
        }
        return plannedHoursByMonth;
    }

    private static Map<Long, Long> weekPlannedHours(List<PlannedLoad> teacherPlannedHours, List<Short> weekNrs) {
        Map<Long, Long> plannedHoursByWeek = new HashMap<>();
        for (Short weekNr : weekNrs) {
            plannedHoursByWeek.put(Long.valueOf(weekNr.longValue()),
                    periodPlannedHoursSumByWeeks(teacherPlannedHours, Arrays.asList(weekNr)));
        }
        return plannedHoursByWeek;
    }

    private static Long periodPlannedHoursSumByWeeks(List<PlannedLoad> teacherPlannedHours, List<Short> weeks) {
        List<PlannedLoad> periodPlannedHours = StreamUtil.toFilteredList(h -> weeks.contains(h.getWeekNr()),
                teacherPlannedHours);
        return Long.valueOf(periodPlannedHours.stream().mapToLong(h -> h.getHours().longValue()).sum());
    }

    private static Long periodPlannedHoursSumByStudyPeriod(List<PlannedLoad> teacherPlannedHours, Long studyPeriod) {
        List<PlannedLoad> periodPlannedHours = StreamUtil.toFilteredList(h -> studyPeriod.equals(h.getStudyPeriodId()),
                teacherPlannedHours);
        return Long.valueOf(periodPlannedHours.stream().mapToLong(h -> h.getHours().longValue()).sum());
    }

    private static Map<Long, Map<String, Long>> studyPeriodCapacityPlannedHours(Boolean isHigher,
            List<PlannedLoad> teacherPlannedHours, List<StudyPeriodDto> studyPeriods, List<Classifier> capacitites) {
        Map<Long, Map<String, Long>> plannedHoursBySp = new HashMap<>();
        for (StudyPeriodDto sp : studyPeriods) {
            Map<String, Long> plannedHours = Boolean.TRUE.equals(isHigher)
                    ? periodCapacityPlannedHoursByStudyPeriod(teacherPlannedHours, sp.getId(), capacitites)
                    : periodCapacityPlannedHoursByWeeks(teacherPlannedHours, sp.getWeekNrs(), capacitites);
            plannedHoursBySp.put(sp.getId(), plannedHours);
        }
        return plannedHoursBySp;
    }

    private static Map<Long, Map<String, Long>> monthCapacityPlannedHours(List<PlannedLoad> teacherPlannedHours,
            Map<Long, List<Short>> weeksByMonth, List<Classifier> capacitites) {
        Map<Long, Map<String, Long>> plannedHoursByMonth = new HashMap<>();
        for (Long month : weeksByMonth.keySet()) {
            List<Short> monthWeeks = weeksByMonth.containsKey(month) ? weeksByMonth.get(month) : new ArrayList<>();
            plannedHoursByMonth.put(month, periodCapacityPlannedHoursByWeeks(teacherPlannedHours, monthWeeks, capacitites));
        }
        return plannedHoursByMonth;
    }

    private static Map<Long, Map<String, Long>> weekCapacityPlannedHours(List<PlannedLoad> teacherPlannedHours,
            List<Short> weekNrs, List<Classifier> capacitites) {
        Map<Long, Map<String, Long>> plannedHoursByWeek = new HashMap<>();
        for (Short weekNr : weekNrs) {
            plannedHoursByWeek.put(Long.valueOf(weekNr.longValue()),
                    periodCapacityPlannedHoursByWeeks(teacherPlannedHours, Arrays.asList(weekNr), capacitites));
        }
        return plannedHoursByWeek;
    }

    private static Map<String, Long> periodCapacityPlannedHoursByWeeks(List<PlannedLoad> teacherPlannedHours,
            List<Short> weekNrs, List<Classifier> capacitites) {
        Map<String, Long> capacityPeriodHours = new HashMap<>();
        for (Classifier c : capacitites) {
            List<PlannedLoad> capacityWeekHours = StreamUtil.toFilteredList(
                    h -> c.getCode().equals(h.getCapacityType()) && weekNrs.contains(h.getWeekNr()),
                    teacherPlannedHours);
            Long capacityPeriodHoursSum = Long
                    .valueOf(capacityWeekHours.stream().mapToLong(h -> h.getHours().longValue()).sum());
            capacityPeriodHours.put(c.getCode(), capacityPeriodHoursSum);
        }
        return capacityPeriodHours;
    }

    private static Map<String, Long> periodCapacityPlannedHoursByStudyPeriod(List<PlannedLoad> teacherPlannedHours,
            Long studyPeriod, List<Classifier> capacitites) {
        Map<String, Long> capacityPeriodHours = new HashMap<>();
        for (Classifier c : capacitites) {
            List<PlannedLoad> capacityHours = StreamUtil.toFilteredList(
                    h -> c.getCode().equals(h.getCapacityType()) && studyPeriod.equals(h.getStudyPeriodId()),
                    teacherPlannedHours);
            Long capacityPeriodHoursSum = Long
                    .valueOf(capacityHours.stream().mapToLong(h -> h.getHours().longValue()).sum());
            capacityPeriodHours.put(c.getCode(), capacityPeriodHoursSum);
        }
        return capacityPeriodHours;
    }

    private static Long totalPlannedHours(List<PlannedLoad> teacherPlannedHours) {
        return Long.valueOf(teacherPlannedHours.stream().mapToLong(h -> h.getHours().longValue()).sum());
    }

    private static Map<String, Long> totalCapacityPlannedHours(List<PlannedLoad> teacherPlannedHours,
            List<Classifier> capacitites) {
        Map<String, Long> teacherTotalCapacityHours = new HashMap<>();
        for (Classifier c : capacitites) {
            List<PlannedLoad> capacityWeekHours = StreamUtil
                    .toFilteredList(h -> c.getCode().equals(h.getCapacityType()), teacherPlannedHours);
            Long capacityPeriodHoursSum = Long.valueOf(capacityWeekHours.stream()
                    .mapToLong(h -> h.getHours().longValue()).sum());
            teacherTotalCapacityHours.put(c.getCode(), capacityPeriodHoursSum);
        }
        return teacherTotalCapacityHours;
    }

    private static JpaNativeQueryBuilder teacherEventsQb(TeacherDetailLoadCommand criteria, List<Long> teacherIds,
            boolean singleEvents) {
        String from = "from timetable_event_time tem"
                + " join timetable_event te on tem.timetable_event_id = te.id"
                + " join timetable_event_teacher tet on tem.id = tet.timetable_event_time_id"
                + " left join timetable_object t_o on te.timetable_object_id = t_o.id"
                + " left join timetable t on t_o.timetable_id = t.id"
                + " left join study_period sp on t.study_period_id = sp.id";

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.optionalCriteria("tem.start >= :studyYearStart", "studyYearStart", criteria.getStudyYearStart(),
                DateUtils::firstMomentOfDay);
        qb.optionalCriteria("tem.end <= :studyYearEnd", "studyYearEnd", criteria.getStudyYearEnd(),
                DateUtils::lastMomentOfDay);

        if (criteria.getStudyPeriod() != null) {
            qb.optionalCriteria("tem.start >= :studyPeriodStart", "studyPeriodStart", criteria.getStudyPeriodStart(),
                    DateUtils::firstMomentOfDay);
            qb.optionalCriteria("tem.end <= :studyPeriodEnd", "studyPeriodEnd", criteria.getStudyPeriodEnd(),
                    DateUtils::lastMomentOfDay);
        }

        qb.optionalCriteria("tem.start >= :from", "from", criteria.getFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("tem.end <= :thru", "thru", criteria.getThru(), DateUtils::lastMomentOfDay);

        qb.optionalCriteria("tet.teacher_id in (:teacherIds)", "teacherIds", teacherIds);

        if (singleEvents) {
            qb.filter("t_o.subject_study_period_id is null and t_o.journal_id is null");
            qb.filter("not exists(select 1 from subject_study_period_exam sspe"
                    + " where sspe.timetable_event_id = tem.timetable_event_id)");
        } else {
            if (Boolean.TRUE.equals(criteria.getIsHigher())) {
                qb.filter("(t_o.subject_study_period_id is not null or exists (select 1 from "
                        + "subject_study_period_exam sspe where sspe.timetable_event_id = tem.timetable_event_id))");
            } else {
                qb.filter("t_o.journal_id is not null");
            }
        }
        return qb;
    }

    private static Map<Long, List<TimetableEvent>> resultAsEvents(List<?> data) {
        Map<Long, List<TimetableEvent>> occurredLessons = new HashMap<>();
        if (!data.isEmpty()) {
            occurredLessons = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                    Collectors.mapping(r -> new TimetableEvent(resultAsString(r, 2), resultAsLocalDate(r, 3),
                            resultAsLong(r, 4), resultAsLong(r, 5), resultAsLong(r, 6)), Collectors.toList())));
        }
        return occurredLessons;
    }

    private Map<Long, List<TimetableEvent>> teacherOccurredLessons(Long schoolId, TeacherDetailLoadCommand criteria,
            List<Long> teacherIds) {
        JpaNativeQueryBuilder qb = teacherEventsQb(criteria, teacherIds, false);
        qb.filter("(tet.is_substitute is null or tet.is_substitute = false)");

        List<?> data = qb.select("tet.teacher_id, tem.id tem_id, te.capacity_type_code, te.start, "
                + " get_study_period(date(tem.start), " + schoolId + ") sp_id, t_o.journal_id, "
                + "t_o.subject_study_period_id", em).getResultList();
        return resultAsEvents(data);
    }

    private Map<Long, List<TimetableEvent>> teacherLessonsAsSubstitute(Long schoolId, TeacherDetailLoadCommand criteria,
            List<Long> teacherIds) {
        JpaNativeQueryBuilder qb = teacherEventsQb(criteria, teacherIds, false);
        qb.filter("tet.is_substitute = true");

        List<?> data = qb.select("tet.teacher_id, tem.id tem_id, te.capacity_type_code, te.start, "
                + " get_study_period(date(tem.start)," + schoolId + ") sp_id, t_o.journal_id, "
                + "t_o.subject_study_period_id", em).getResultList();
        return resultAsEvents(data);
    }

    private Map<Long, List<TimetableEvent>> teacherSingleEvents(Long schoolId, TeacherDetailLoadCommand criteria,
            List<Long> teacherIds) {
        JpaNativeQueryBuilder qb = teacherEventsQb(criteria, teacherIds, true);

        List<?> data = qb.select("tet.teacher_id, tem.id tem_id, te.capacity_type_code, te.start,"
                + " get_study_period(date(tem.start), " + schoolId + ") sp_id, null journal_id, "
                + "null subject_study_period_id", em).getResultList();
        return resultAsEvents(data);
    }

    private static Map<Long, Long> studyPeriodOccurredEvents(List<TimetableEvent> occurredEvents,
            List<StudyPeriodDto> studyPeriods) {
        Map<Long, Long> periodLessons = new HashMap<>();
        for (StudyPeriodDto sp : studyPeriods) {
            long lessonsCount = StreamUtil.toFilteredList(l -> sp.getId().equals(l.getStudyPeriodId()), occurredEvents)
                    .size();
            periodLessons.put(sp.getId(), Long.valueOf(lessonsCount));
        }
        return periodLessons;
    }

    private static Map<Long, Long> monthOccurredEvents(List<TimetableEvent> occurredEvents, Set<Long> months) {
        Map<Long, Long> periodLessons = new HashMap<>();
        for (Long month : months) {
            long lessonsCount = StreamUtil
                    .toFilteredList(l -> month.intValue() == l.getEventStart().getMonthValue(), occurredEvents).size();
            periodLessons.put(month, Long.valueOf(lessonsCount));
        }
        return periodLessons;
    }

    private static Map<Long, Long> weekOccurredEvents(List<TimetableEvent> occurredEvents,
            Map<Short, LocalDate> weekBeginningDateMap) {
        Map<Long, Long> periodLessons = new HashMap<>();
        for (Short weekNr : weekBeginningDateMap.keySet()) {
            LocalDate weekStart = weekBeginningDateMap.get(weekNr);
            LocalDate weekEnd = weekStart.plusDays(6);
            long lessonsCount = weekEvents(occurredEvents, weekStart, weekEnd).size();
            periodLessons.put(Long.valueOf(weekNr.longValue()), Long.valueOf(lessonsCount));
        }
        return periodLessons;
    }

    private static List<TimetableEvent> weekEvents(List<TimetableEvent> occurredEvents, LocalDate weekStart,
            LocalDate weekEnd) {
        return StreamUtil.toFilteredList(l -> !l.getEventStart().isBefore(weekStart) &&
                !l.getEventStart().isAfter(weekEnd), occurredEvents);
    }

    private static Map<Long, Map<String, Long>> studyPeriodCapacityOccurredEvents(List<TimetableEvent> occurredEvents,
            List<StudyPeriodDto> studyPeriods, List<Classifier> capacitites) {
        Map<Long, Map<String, Long>> periodCapacityLessons = new HashMap<>();
        for (StudyPeriodDto sp : studyPeriods) {
            List<TimetableEvent> periodOccurredEvents = StreamUtil
                    .toFilteredList(l -> sp.getId().equals(l.getStudyPeriodId()), occurredEvents);
            periodCapacityLessons.put(sp.getId(), capacityOccurredEvents(periodOccurredEvents, capacitites));
        }
        return periodCapacityLessons;
    }

    private static Map<Long, Map<String, Long>> monthCapacityOccurredEvents(List<TimetableEvent> occurredEvents,
            Set<Long> months, List<Classifier> capacitites) {
        Map<Long, Map<String, Long>> periodCapacityLessons = new HashMap<>();
        for (Long month : months) {
            List<TimetableEvent> periodOccurredEvents = StreamUtil
                    .toFilteredList(l -> month.intValue() == l.getEventStart().getMonthValue(), occurredEvents);
            periodCapacityLessons.put(month, capacityOccurredEvents(periodOccurredEvents, capacitites));
        }
        return periodCapacityLessons;
    }

    private static Map<Long, Map<String, Long>> weekCapacityOccurredEvents(List<TimetableEvent> occurredEvents,
            Map<Short, LocalDate> weekBeginningDateMap, List<Classifier> capacitites) {
        Map<Long, Map<String, Long>> periodCapacityLessons = new HashMap<>();
        for (Short weekNr : weekBeginningDateMap.keySet()) {
            LocalDate weekStart = weekBeginningDateMap.get(weekNr);
            LocalDate weekEnd = weekStart.plusDays(6);
            List<TimetableEvent> periodOccurredEvents = weekEvents(occurredEvents, weekStart, weekEnd);
            periodCapacityLessons.put(Long.valueOf(weekNr.longValue()),
                    capacityOccurredEvents(periodOccurredEvents, capacitites));
        }
        return periodCapacityLessons;
    }

    private static Map<String, Long> capacityOccurredEvents(List<TimetableEvent> periodOccurredEvents,
            List<Classifier> capacitites) {
        Map<String, Long> capacityLessons = new HashMap<>();
        for (Classifier c : capacitites) {
            long lessonsCount = periodOccurredEvents.stream().filter(l -> c.getCode().equals(l.getCapacityType()))
                    .count();
            capacityLessons.put(c.getCode(), Long.valueOf(lessonsCount));
        }
        long withoutCapacitySum = periodOccurredEvents.stream().filter(l -> l.getCapacityType() == null).count();
        capacityLessons.put(NO_CAPACITY_TYPE, Long.valueOf(withoutCapacitySum));
        return capacityLessons;
    }

    private static Long totalOccurredEvents(List<TimetableEvent> occurredEvents) {
        return Long.valueOf(occurredEvents.stream().count());
    }

    private static Map<String, Long> totalCapacityOccurredEvents(List<TimetableEvent> occurredEvents,
            List<Classifier> capacitites) {
        Map<String, Long> totalCapacityLessons = new HashMap<>();
        for (Classifier c : capacitites) {
            long capacityTotalSum = occurredEvents.stream().filter(l -> c.getCode().equals(l.getCapacityType()))
                    .count();
            totalCapacityLessons.put(c.getCode(), Long.valueOf(capacityTotalSum));
        }
        long withoutCapacitySum = occurredEvents.stream().filter(l -> l.getCapacityType() == null).count();
        totalCapacityLessons.put(NO_CAPACITY_TYPE, Long.valueOf(withoutCapacitySum));
        return totalCapacityLessons;
    }

    public TeacherDetailLoadDto teacherDetailLoadJournals(Long schoolId, TeacherDetailLoadCommand criteria,
            Teacher teacher) {
        TeacherDetailLoadReportDataDto report = teacherDetailLoadReportData(criteria);

        Long teacherId = EntityUtil.getId(teacher);
        List<Long> teacherIds = Arrays.asList(teacherId);
        List<Classifier> capacities = getTeacherCapacities(schoolId, criteria, teacherIds);
        List<PlannedLoad> plannedLoads = new ArrayList<>();
        if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
            plannedLoads = teacherVocationalPlannedLoads(criteria, teacherIds, report).get(teacherId);
        }
        List<TimetableEvent> occurredLessons = teacherOccurredLessons(schoolId, criteria, teacherIds).get(teacherId);
        List<TimetableEvent> substitutedLessons = teacherLessonsAsSubstitute(schoolId, criteria, teacherIds).get(teacherId);

        Set<Long> journalIds = StreamUtil.toMappedSet(e -> e.getJournalId(), occurredLessons);
        journalIds.addAll(StreamUtil.toMappedSet(e -> e.getJournalId(), substitutedLessons));
        if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
            journalIds.addAll(StreamUtil.toMappedSet(p -> p.getJournalId(), plannedLoads));
        }
        List<TeacherDetailLoadJournalSubjectDto> journals = teacherJournals(journalIds);

        TeacherDetailLoadDto dto = new TeacherDetailLoadDto();
        String name = PersonUtil.fullname(teacher.getPerson());
        dto.setTeacher(new AutocompleteResult(teacherId, name, name));

        for (TeacherDetailLoadJournalSubjectDto journal : journals) {
            Long journalId = journal.getJournalSubject().getId();
            if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
                List<PlannedLoad> journalPlannedLoads = StreamUtil
                        .toFilteredList(l -> journalId.equals(l.getJournalId()), plannedLoads);
                setTeacherDetailLoadPlannedHours(journal, criteria, journalPlannedLoads, capacities, report);
            }
            List<TimetableEvent> journalOccurredLessons = StreamUtil
                    .toFilteredList(l -> journalId.equals(l.getJournalId()), occurredLessons);
            setTeacherDetailLoadOccurredHours(journal, criteria, journalOccurredLessons, capacities, report);

            if (Boolean.TRUE.equals(criteria.getByCapacities())) {
                dto.getTeacherCapacities().addAll(getTeacherPeriodCapacities(journal));
            }

            List<TimetableEvent> journalSubstitutedLessons = StreamUtil
                    .toFilteredList(l -> journalId.equals(l.getJournalId()), substitutedLessons);
            setTeacherDetailLoadSubstitutedLessons(journal, criteria, journalSubstitutedLessons, report);
        }
        dto.setJournalSubjects(journals);
        return dto;
    }

    private List<TeacherDetailLoadJournalSubjectDto> teacherJournals(Set<Long> journalIds) {
        List<TeacherDetailLoadJournalSubjectDto> journals = new ArrayList<>();
        if (!journalIds.isEmpty()) {
            Map<Long, Set<String>> studentGroups = journalStudentGroups(journalIds);

            List<?> data = em.createNativeQuery("select distinct j.id, j.name_et from journal j"
                    + " where j.id in (:journalIds) order by j.name_et")
                    .setParameter("journalIds", journalIds).getResultList();
            journals = StreamUtil.toMappedList(r -> {
                Long journalId = resultAsLong(r, 0);
                TeacherDetailLoadJournalSubjectDto dto = new TeacherDetailLoadJournalSubjectDto();
                dto.setJournalSubject(new AutocompleteResult(journalId, resultAsString(r, 1), resultAsString(r, 1)));
                dto.setStudentGroups(studentGroups.get(journalId));
                return dto;
            }, data);
        }
        return journals;
    }

    private Map<Long, Set<String>> journalStudentGroups(Set<Long> journalIds) {
        List<?> data = em.createNativeQuery("select j.id, sg.code from journal j"
                + " join journal_omodule_theme jot on jot.journal_id = j.id"
                + " join lesson_plan_module lpm on jot.lesson_plan_module_id = lpm.id"
                + " join lesson_plan lp on lpm.lesson_plan_id = lp.id"
                + " join student_group sg on lp.student_group_id = sg.id"
                + " where j.id in (:journalIds)")
                .setParameter("journalIds", journalIds)
                .getResultList();
        return StreamUtil.nullSafeList(data).stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> resultAsString(r, 1), Collectors.toSet())));
    }

    public TeacherDetailLoadDto teacherDetailLoadSubjects(Long schoolId, TeacherDetailLoadCommand criteria,
            Teacher teacher) {
        TeacherDetailLoadReportDataDto report = teacherDetailLoadReportData(criteria);

        Long teacherId = EntityUtil.getId(teacher);
        List<Long> teacherIds = Arrays.asList(teacherId);
        List<Classifier> capacities = getTeacherCapacities(schoolId, criteria, teacherIds);
        List<PlannedLoad> plannedLoads = new ArrayList<>();
        if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
            plannedLoads = teacherHigherPlannedLoads(criteria, teacherIds).get(teacherId);
        }
        List<TimetableEvent> occurredLessons = teacherOccurredLessons(schoolId, criteria, teacherIds).get(teacherId);
        List<TimetableEvent> substitutedLessons = teacherLessonsAsSubstitute(schoolId, criteria, teacherIds)
                .get(teacherId);

        Set<Long> subjectStudyPeriodIds = StreamUtil.toMappedSet(e -> e.getSubjectStudyPeriodId(), occurredLessons);
        subjectStudyPeriodIds.addAll(StreamUtil.toMappedSet(e -> e.getSubjectStudyPeriodId(), substitutedLessons));
        if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
            subjectStudyPeriodIds.addAll(StreamUtil.toMappedSet(p -> p.getSubjectStudyPeriodId(), plannedLoads));
        }
        List<TeacherDetailLoadJournalSubjectDto> subjectStudyPeriods = teacherSubjectStudyPeriods(
                subjectStudyPeriodIds);

        TeacherDetailLoadDto dto = new TeacherDetailLoadDto();
        String name = PersonUtil.fullname(teacher.getPerson());
        dto.setTeacher(new AutocompleteResult(teacherId, name, name));

        for (TeacherDetailLoadJournalSubjectDto subject : subjectStudyPeriods) {
            Long sspId = subject.getJournalSubject().getId();
            if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
                List<PlannedLoad> subjectPlannedLoads = StreamUtil.toFilteredList(l -> sspId.equals(l.getSubjectStudyPeriodId()),
                        plannedLoads);
                setTeacherDetailLoadPlannedHours(subject, criteria, subjectPlannedLoads, capacities, report);
            }
            List<TimetableEvent> subjectOccurredLessons = StreamUtil.toFilteredList(l -> sspId.equals(l.getSubjectStudyPeriodId()),
                    occurredLessons);
            setTeacherDetailLoadOccurredHours(subject, criteria, subjectOccurredLessons, capacities, report);

            if (Boolean.TRUE.equals(criteria.getByCapacities())) {
                dto.getTeacherCapacities().addAll(getTeacherPeriodCapacities(subject));
            }

            List<TimetableEvent> subjectSubstitutedLessons = StreamUtil
                    .toFilteredList(l -> sspId.equals(l.getSubjectStudyPeriodId()), substitutedLessons);
            setTeacherDetailLoadSubstitutedLessons(subject, criteria, subjectSubstitutedLessons, report);
        }
        dto.setJournalSubjects(subjectStudyPeriods);
        return dto;
    }

    private List<TeacherDetailLoadJournalSubjectDto> teacherSubjectStudyPeriods(Set<Long> subjectStudyPeriodIds) {
        List<TeacherDetailLoadJournalSubjectDto> subjectStudyPeriods = new ArrayList<>();
        if (!subjectStudyPeriodIds.isEmpty()) {
            Map<Long, Set<String>> studentGroups = studyPeriodStudentGroups(subjectStudyPeriodIds);

            List<?> data = em.createNativeQuery("select ssp.id, s.name_et, s.name_en from subject_study_period ssp"
                    + " join subject s on s.id = ssp.subject_id"
                    + " where ssp.id in (:sspIds) order by s.name_et")
                    .setParameter("sspIds", subjectStudyPeriodIds).getResultList();
            subjectStudyPeriods = StreamUtil.toMappedList(r -> {
                Long sspId = resultAsLong(r, 0);
                TeacherDetailLoadJournalSubjectDto dto = new TeacherDetailLoadJournalSubjectDto();
                dto.setJournalSubject(new AutocompleteResult(sspId, resultAsString(r, 1), resultAsString(r, 2)));
                dto.setStudentGroups(studentGroups.get(sspId));
                return dto;
            }, data);
        }
        return subjectStudyPeriods;
    }

    private Map<Long, Set<String>> studyPeriodStudentGroups(Set<Long> subjectStudyPeriodIds) {
        List<?> data = em.createNativeQuery("select ssp.id, sg.code from subject_study_period ssp"
                + " join subject_study_period_student_group sspsg on sspsg.subject_study_period_id = ssp.id"
                + " join student_group sg on sg.id = sspsg.student_group_id"
                + " where ssp.id in (:sspIds)")
                .setParameter("sspIds", subjectStudyPeriodIds)
                .getResultList();
        return StreamUtil.nullSafeList(data).stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> resultAsString(r, 1), Collectors.toSet())));
    }

    public byte[] teacherDetailLoadAsExcel(Long schoolId, TeacherDetailLoadCommand criteria) {
        List<TeacherDetailLoadDto> teachers = Boolean.TRUE.equals(criteria.getIsHigher())
                ? teacherHigherDetailLoad(schoolId, criteria, new PageRequest(0, Integer.MAX_VALUE)).getContent()
                : teacherVocationalDetailLoad(schoolId, criteria, new PageRequest(0, Integer.MAX_VALUE)).getContent();

        List<Classifier> capacities = Boolean.TRUE.equals(criteria.getByCapacities()) 
                ? excelTeacherCapacities(teachers) : new ArrayList<>();
        boolean entriesWithoutCapacity = Boolean.TRUE.equals(criteria.getByCapacities())
                ? existsEntriesWithoutCapacity(teachers) : false;
 
        TeacherDetailLoadReport report = detailLoadExcelReportDto(schoolId, criteria, capacities,
                entriesWithoutCapacity);
        List<ResultRowDto> resultRows = new ArrayList<>();
        for (TeacherDetailLoadDto teacher : teachers) {
            ResultRowDto row = getResultRow(criteria, teacher, report.getLoadTypesRow());
            row.setName(teacher.getTeacher());
            resultRows.add(row);
        }
        report.setRows(resultRows);
        report.setJournalSubjectReport(Boolean.FALSE);
        return xlsService.generate("teachersdetailload" + ".xlsx", Collections.singletonMap("report", report));
    }

    private List<Classifier> excelTeacherCapacities(List<TeacherDetailLoadDto> teachers) {
        Set<String> capacityCodes = new HashSet<>();
        for (TeacherDetailLoadDto teacher : teachers) {
            capacityCodes.addAll(teacher.getTeacherCapacities());
        }

        List<Classifier> capacities = new ArrayList<>();
        if (!capacityCodes.isEmpty()) {
            capacities = em.createQuery("select c from Classifier c where c.code in (:codes)", Classifier.class)
                    .setParameter("codes", capacityCodes).getResultList();
        }
        capacities.sort(Comparator.comparing(Classifier::getValue, String.CASE_INSENSITIVE_ORDER));
        return capacities;
    }

    public byte[] teacherDetailLoadJournalSubjectsAsExcel(Long schoolId, TeacherDetailLoadCommand criteria, Teacher teacher) {
        TeacherDetailLoadDto teacherLoad = Boolean.TRUE.equals(criteria.getIsHigher())
                ? teacherDetailLoadSubjects(schoolId, criteria, teacher)
                : teacherDetailLoadJournals(schoolId, criteria, teacher);

        List<Classifier> capacities = Boolean.TRUE.equals(criteria.getByCapacities()) 
                ? excelTeacherCapacities(Arrays.asList(teacherLoad)) : new ArrayList<>();
        boolean entriesWithoutCapacity = Boolean.TRUE.equals(criteria.getByCapacities())
                ? existsEntriesWithoutCapacity(Arrays.asList(teacherLoad)) : false;

        TeacherDetailLoadReport report = detailLoadExcelReportDto(schoolId, criteria, capacities,
                entriesWithoutCapacity);
        report.setTeacher(teacherLoad.getTeacher());
        List<ResultRowDto> resultRows = new ArrayList<>();
        for (TeacherDetailLoadJournalSubjectDto journalSubject : teacherLoad.getJournalSubjects()) {
            ResultRowDto row = getResultRow(criteria, journalSubject, report.getLoadTypesRow());
            row.setName(journalSubject.getJournalSubject());
            row.setStudentGroups(journalSubject.getStudentGroups() != null ?
                    String.join(", ", journalSubject.getStudentGroups()) : null);
            resultRows.add(row);
        }
        report.setRows(resultRows);
        report.setJournalSubjectReport(Boolean.TRUE);
        return xlsService.generate("teachersdetailload" + ".xlsx", Collections.singletonMap("report", report));
    }

    private TeacherDetailLoadReport detailLoadExcelReportDto(Long schoolId, TeacherDetailLoadCommand criteria,
            List<Classifier> capacities, boolean entriesWithoutCapacity) {
        TeacherDetailLoadReportDataDto reportData = teacherDetailLoadReportData(criteria);

        int studyColspan = capacities.size() > 0 ? capacities.size() : 1;
        int plannedLessonsColspan = Boolean.TRUE.equals(criteria.getShowPlannedLessons()) ? studyColspan : 0;
        int occurredLessonsColspan = 1 + studyColspan + (Boolean.TRUE.equals(criteria.getShowSingleEvents()) ? 1 : 0)
                + (entriesWithoutCapacity ? 1 : 0);
        int periodColspan = plannedLessonsColspan + occurredLessonsColspan;

        List<PeriodDto> periods = new ArrayList<>();
        List<Map<String, Object>> periodTypesRow = new ArrayList<>();
        List<LoadTypeDto> loadTypesRow = new ArrayList<>();

        if (Boolean.TRUE.equals(criteria.getByStudyPeriods()) || Boolean.TRUE.equals(criteria.getIsHigher())) {
            List<StudyPeriodDto> studyPeriods = new ArrayList<>();
            if (criteria.getStudyPeriod() != null) {
                studyPeriods.add(studyPeriodDtoById(criteria.getStudyPeriod(), reportData.getStudyPeriods()));
            } else {
                studyPeriods = reportData.getStudyPeriods();
            }

            for (StudyPeriodDto dto : studyPeriods) {
                PeriodDto period = new PeriodDto();
                period.setStudyPeriod(Boolean.TRUE);
                period.setName(dto.getNameEt());
                period.setColspan(Long.valueOf(periodColspan));
                periods.add(period);

                addPeriodTypes(criteria, periodTypesRow, plannedLessonsColspan, occurredLessonsColspan);
                addLoadTypes(criteria, dto.getId(), loadTypesRow, capacities, entriesWithoutCapacity, false);
            }
        } else if (Boolean.TRUE.equals(criteria.getByWeeks())) {
            List<Short> periodWeekNrs = new ArrayList<>();
            List<Long> weekNrs = new ArrayList<>();
            if (criteria.getStudyPeriod() != null) {
                StudyPeriodDto studyPeriod = studyPeriodDtoById(criteria.getStudyPeriod(),
                        reportData.getStudyPeriods());
                periodWeekNrs = studyPeriod.getWeekNrs();
            } else {
                periodWeekNrs = reportData.getWeekNrs();
            }
            weekNrs = StreamUtil.nullSafeList(periodWeekNrs).stream().map(r -> Long.valueOf(r.longValue())).distinct()
                    .collect(Collectors.toList());

            for (Long weekNr : weekNrs) {
                PeriodDto period = new PeriodDto();
                period.setWeek(Boolean.TRUE);
                period.setNr(weekNr);
                period.setColspan(Long.valueOf(periodColspan));
                periods.add(period);

                addPeriodTypes(criteria, periodTypesRow, plannedLessonsColspan, occurredLessonsColspan);
                addLoadTypes(criteria, weekNr, loadTypesRow, capacities, entriesWithoutCapacity, false);
            }
        } else if (Boolean.TRUE.equals(criteria.getByMonths())) {
            for (Long monthValue : reportData.getMonths()) {
                PeriodDto period = new PeriodDto();
                period.setMonth(Boolean.TRUE);
                period.setNr(monthValue);
                period.setColspan(Long.valueOf(periodColspan));
                periods.add(period);

                addPeriodTypes(criteria, periodTypesRow, plannedLessonsColspan, occurredLessonsColspan);
                addLoadTypes(criteria, monthValue, loadTypesRow, capacities, entriesWithoutCapacity, false);
            }
        }

        // Total columns
        PeriodDto period = new PeriodDto();
        period.setTotal(Boolean.TRUE);
        period.setColspan(Long.valueOf(periodColspan + 1));
        periods.add(period);
        addPeriodTypes(criteria, periodTypesRow, plannedLessonsColspan, occurredLessonsColspan);
        addLoadTypes(criteria, null, loadTypesRow, capacities, entriesWithoutCapacity, true);

        TeacherDetailLoadReport report = new TeacherDetailLoadReport();
        report.setStudyYear(EntityUtil.getOptionalOne(StudyYear.class, criteria.getStudyYear(), em));
        report.setStudyPeriod(EntityUtil.getOptionalOne(StudyPeriod.class, criteria.getStudyPeriod(), em));
        report.setFrom(criteria.getFrom());
        report.setThru(criteria.getThru());
        report.setCapacities(capacities);
        report.setPeriods(periods);
        report.setPeriodTypesRow(periodTypesRow);
        report.setLoadTypesRow(loadTypesRow);
        report.setIsHigher(criteria.getIsHigher());
        report.setIsHigherSchool(Boolean.valueOf(schoolService.schoolType(schoolId).isHigher()));
        return report;
    }

    private static StudyPeriodDto studyPeriodDtoById(Long id, List<StudyPeriodDto> studyPeriods) {
        return StreamUtil.toFilteredList(p -> id.equals(p.getId()), studyPeriods).get(0);
    }

    private static boolean existsEntriesWithoutCapacity(List<TeacherDetailLoadDto> teachers) {
        boolean entriesWithoutCapacity = false;
        for (TeacherDetailLoadDto teacher : teachers) {
            if (teacher.getTeacherCapacities().contains(NO_CAPACITY_TYPE)) {
                entriesWithoutCapacity = true;
                break;
            }
        }
        return entriesWithoutCapacity;
    }

    private static void addPeriodTypes(TeacherDetailLoadCommand criteria, List<Map<String, Object>> periodTypesRow,
            int plannedLessonsColspan, int occurredLessonsColspan) {
        if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
            Map<String, Object> plannedPeriodType = new HashMap<>();
            plannedPeriodType.put("name", "query.teachersdetailload.planned");
            plannedPeriodType.put("colspan", Long.valueOf(plannedLessonsColspan));
            periodTypesRow.add(plannedPeriodType);
        }

        Map<String, Object> occurredPeriodType = new HashMap<>();
        occurredPeriodType.put("name", "query.teachersdetailload.occurred");
        occurredPeriodType.put("colspan", Long.valueOf(occurredLessonsColspan));
        periodTypesRow.add(occurredPeriodType);
    }

    private static void addLoadTypes(TeacherDetailLoadCommand criteria, Long periodIndex,
            List<LoadTypeDto> loadTypesRow, List<Classifier> capacities, boolean entriesWithoutCapacity,
            boolean total) {
        if (Boolean.TRUE.equals(criteria.getByCapacities())) {
            if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
                for (Classifier capacity : capacities) {
                    LoadTypeDto plannedLessons = new LoadTypeDto();
                    plannedLessons.setPlannedLessons(Boolean.TRUE);
                    plannedLessons.setPeriodIndex(periodIndex);
                    plannedLessons.setIsCapacity(Boolean.TRUE);
                    plannedLessons.setCapacityCode(capacity.getCode());
                    plannedLessons.setCapacityValue(capacity.getValue());
                    loadTypesRow.add(plannedLessons);
                }
            }

            for (Classifier capacity : capacities) {
                LoadTypeDto occurredLesson = new LoadTypeDto();
                occurredLesson.setOccurredLessons(Boolean.TRUE);
                occurredLesson.setPeriodIndex(periodIndex);
                occurredLesson.setIsCapacity(Boolean.TRUE);
                occurredLesson.setCapacityCode(capacity.getCode());
                occurredLesson.setCapacityValue(capacity.getValue());
                loadTypesRow.add(occurredLesson);
            }

            if (entriesWithoutCapacity) {
                LoadTypeDto plannedLessons = new LoadTypeDto();
                plannedLessons.setOccurredLessons(Boolean.TRUE);
                plannedLessons.setPeriodIndex(periodIndex);
                plannedLessons.setIsCapacity(Boolean.TRUE);
                plannedLessons.setCapacityCode(NO_CAPACITY_TYPE);
                plannedLessons.setCapacityValue(NO_CAPACITY_TYPE);
                loadTypesRow.add(plannedLessons);
            }
        } else {
            if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
                LoadTypeDto plannedLessonsStudy = new LoadTypeDto();
                plannedLessonsStudy.setPlannedLessons(Boolean.TRUE);
                plannedLessonsStudy.setPeriodIndex(periodIndex);
                plannedLessonsStudy.setName("query.teachersdetailload.study");
                loadTypesRow.add(plannedLessonsStudy);
            }

            LoadTypeDto occurredStudy = new LoadTypeDto();
            occurredStudy.setOccurredLessons(Boolean.TRUE);
            occurredStudy.setPeriodIndex(periodIndex);
            occurredStudy.setName("query.teachersdetailload.study");
            loadTypesRow.add(occurredStudy);
        }

        LoadTypeDto substituted = new LoadTypeDto();
        substituted.setSubstitutableEvents(Boolean.TRUE);
        substituted.setPeriodIndex(periodIndex);
        substituted.setName("query.teachersdetailload.substitute");
        loadTypesRow.add(substituted);

        if (Boolean.TRUE.equals(criteria.getShowSingleEvents())) {
            LoadTypeDto singleEvents = new LoadTypeDto();
            singleEvents.setSingleEvents(Boolean.TRUE);
            singleEvents.setPeriodIndex(periodIndex);
            singleEvents.setName("query.teachersdetailload.singleEvent");
            loadTypesRow.add(singleEvents);
        }

        if (total) {
            LoadTypeDto grandTotal = new LoadTypeDto();
            grandTotal.setGrandTotal(Boolean.TRUE);
            grandTotal.setName("query.teachersdetailload.grandTotal");
            loadTypesRow.add(grandTotal);
        }
    }

    private static ResultRowDto getResultRow(TeacherDetailLoadCommand criteria, PeriodDetailLoadDto data,
            List<LoadTypeDto> loadTypesRow) {
        ResultRowDto row = new ResultRowDto();
        if (Boolean.TRUE.equals(criteria.getByCapacities())) {
            setPeriodCapacityLoads(data, loadTypesRow, row);
        } else {
            setPeriodLoads(data, loadTypesRow, row);
        }
        return row;
    }

    private static void setPeriodLoads(PeriodDetailLoadDto data, List<LoadTypeDto> loadTypesRow, ResultRowDto row) {
        for (LoadTypeDto type : loadTypesRow) {
            if (type.getPeriodIndex() != null) {
                if (Boolean.TRUE.equals(type.getPlannedLessons())) {
                    row.getLoads().add(data.getPlannedHours().get(type.getPeriodIndex()));
                } else if (Boolean.TRUE.equals(type.getOccurredLessons())) {
                    row.getLoads().add(data.getOccurredLessons().get(type.getPeriodIndex()));
                } else if (Boolean.TRUE.equals(type.getSubstitutableEvents())) {
                    row.getLoads().add(data.getSubstitutedLessons().get(type.getPeriodIndex()));
                } else if (Boolean.TRUE.equals(type.getSingleEvents())) {
                    row.getLoads().add(data.getSingleEvents().get(type.getPeriodIndex()));
                }
            } else {
                if (Boolean.TRUE.equals(type.getPlannedLessons())) {
                    row.getLoads().add(data.getTotalPlannedHours());
                } else if (Boolean.TRUE.equals(type.getOccurredLessons())) {
                    row.getLoads().add(data.getTotalOccurredLessons());
                } else if (Boolean.TRUE.equals(type.getSubstitutableEvents())) {
                    row.getLoads().add(data.getTotalSubstitutedLessons());
                } else if (Boolean.TRUE.equals(type.getSingleEvents())) {
                    row.getLoads().add(data.getTotalSingleEvents());
                } else if (Boolean.TRUE.equals(type.getGrandTotal())) {
                    row.getLoads().add(grandTotal(data));
                }
            }
        }
    }

    private static void setPeriodCapacityLoads(PeriodDetailLoadDto data, List<LoadTypeDto> loadTypesRow, ResultRowDto row) {
        for (LoadTypeDto type : loadTypesRow) {
            if (type.getPeriodIndex() != null) {
                if (Boolean.TRUE.equals(type.getPlannedLessons())) {
                    Map<String, Long> periodCapacityLessons = data.getCapacityPlannedHours().get(type.getPeriodIndex());
                    row.getLoads()
                            .add(periodCapacityLessons != null ? periodCapacityLessons.get(type.getCapacityCode()) : null);
                } else if (Boolean.TRUE.equals(type.getOccurredLessons())) {
                    Map<String, Long> periodCapacityLessons = data.getCapacityOccurredLessons().get(type.getPeriodIndex());
                    row.getLoads().add(
                            periodCapacityLessons != null ? periodCapacityLessons.remove(type.getCapacityCode()) : null);
                } else if (Boolean.TRUE.equals(type.getSubstitutableEvents())) {
                    row.getLoads().add(data.getSubstitutedLessons().get(type.getPeriodIndex()));
                } else if (Boolean.TRUE.equals(type.getSingleEvents())) {
                    row.getLoads().add(data.getSingleEvents().get(type.getPeriodIndex()));
                }
            } else {
                if (Boolean.TRUE.equals(type.getPlannedLessons())) {
                    Map<String, Long> periodCapacityLessons = data.getTotalCapacityPlannedHours();
                    row.getLoads().add(periodCapacityLessons != null ? periodCapacityLessons.get(type.getCapacityCode()) : null);
                } else if (Boolean.TRUE.equals(type.getOccurredLessons())) {
                    Map<String, Long> periodCapacityLessons = data.getTotalCapacityOccurredLessons();
                    row.getLoads().add(periodCapacityLessons != null ? periodCapacityLessons.get(type.getCapacityCode()) : null);
                } else if (Boolean.TRUE.equals(type.getSubstitutableEvents())) {
                    row.getLoads().add(data.getTotalSubstitutedLessons());
                } else if (Boolean.TRUE.equals(type.getSingleEvents())) {
                    row.getLoads().add(data.getTotalSingleEvents());
                } else if (Boolean.TRUE.equals(type.getGrandTotal())) {
                    row.getLoads().add(grandTotal(data));
                }
            }
        }
    }

    private static Long grandTotal(PeriodDetailLoadDto data) {
        long grandTotal = (data.getTotalOccurredLessons() != null ? data.getTotalOccurredLessons().longValue() : 0) +
                (data.getTotalSubstitutedLessons() != null ? data.getTotalSubstitutedLessons().longValue() : 0) +
                (data.getTotalSingleEvents() != null ? data.getTotalSingleEvents().longValue() : 0);
        return Long.valueOf(grandTotal);
    }

    private static class TimetableEvent {
        private final String capacityType;
        private final LocalDate eventStart;
        private final Long studyPeriodId;
        private final Long journalId;
        private final Long subjectStudyPeriodId;

        public TimetableEvent(String capacityType, LocalDate eventStart, Long studyPeriodId, Long journalId,
                Long subjectStudyPeriodId) {
            this.capacityType = capacityType;
            this.eventStart = eventStart;
            this.studyPeriodId = studyPeriodId;
            this.journalId = journalId;
            this.subjectStudyPeriodId = subjectStudyPeriodId;
        }

        public String getCapacityType() {
            return capacityType;
        }

        public LocalDate getEventStart() {
            return eventStart;
        }

        public Long getStudyPeriodId() {
            return studyPeriodId;
        }

        public Long getJournalId() {
            return journalId;
        }

        public Long getSubjectStudyPeriodId() {
            return subjectStudyPeriodId;
        }
    }

    private static class PlannedLoad {
        private final String capacityType;
        private final Short weekNr;
        private final Long hours;
        private final Long journalId;
        private final Long studyPeriodId;
        private final Long subjectStudyPeriodId;

        public PlannedLoad(String capacityType, Short weekNr, Long hours, Long journalId, Long studyPeriodId,
                Long subjectStudyPeriodId) {
            this.capacityType = capacityType;
            this.weekNr = weekNr;
            this.hours = hours;
            this.journalId = journalId;
            this.studyPeriodId = studyPeriodId;
            this.subjectStudyPeriodId = subjectStudyPeriodId;
        }

        public String getCapacityType() {
            return capacityType;
        }

        public Short getWeekNr() {
            return weekNr;
        }

        public Long getHours() {
            return hours;
        }

        public Long getJournalId() {
            return journalId;
        }

        public Long getStudyPeriodId() {
            return studyPeriodId;
        }

        public Long getSubjectStudyPeriodId() {
            return subjectStudyPeriodId;
        }
    }
}
