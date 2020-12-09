package ee.hitsa.ois.service;

import static ee.hitsa.ois.service.subjectstudyperiod.SubjectStudyPeriodCapacitiesService.SQL_SELECT_TEACHER_CAPACITY;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;

import java.math.BigDecimal;
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
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import ee.hitsa.ois.web.dto.StudyPeriodWithWeeksDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.report.teacherdetailload.LoadTypeDto;
import ee.hitsa.ois.report.teacherdetailload.PeriodDto;
import ee.hitsa.ois.report.teacherdetailload.ResultRowDto;
import ee.hitsa.ois.report.teacherdetailload.TeacherDetailLoadReport;
import ee.hitsa.ois.report.teacherdetailload.TeacherDetailLoadSubjectJournalReport;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.LessonPlanUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.report.TeacherDetailLoadCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.WeekDto;
import ee.hitsa.ois.web.dto.report.teacherdetailload.PeriodDetailLoadDto;
import ee.hitsa.ois.web.dto.report.teacherdetailload.TeacherDetailLoadDto;
import ee.hitsa.ois.web.dto.report.teacherdetailload.TeacherDetailLoadJournalSubjectDto;
import ee.hitsa.ois.web.dto.report.teacherdetailload.TeacherDetailLoadReportDataDto;
import ee.hitsa.ois.xls.XlsDynamicColumnCommand;

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
                .map(StudyPeriodWithWeeksDto::new).collect(Collectors.toList()));

        List<Short> weekNrs = dto.getStudyPeriods().stream().flatMap(r -> r.getWeekNrs().stream())
                .collect(Collectors.toList());

        dto.setWeekNrs(weekNrs);
        List<LocalDate> weekBeginningDates = dto.getStudyPeriods().stream()
                .flatMap(r -> r.getWeekBeginningDates().stream()).collect(Collectors.toList());
        dto.setWeekBeginningDateMap(LessonPlanUtil.weekBeginningDateMap(weekNrs, weekBeginningDates));
        dto.setWeekNrsByMonth(weeksByMonth(dto.getWeekBeginningDateMap()));
        dto.setWeeks(LessonPlanUtil.weeks(weekNrs, weekBeginningDates));
        dto.setMonths(monthsMatchingCriteria(criteria, weekBeginningDates));
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
        if (teacherIds.isEmpty()) {
            return teachers;
        }

        List<Classifier> capacities = getTeacherCapacities(schoolId, criteria, teacherIds);
        Map<Long, List<PlannedLoad>> plannedLoads = new HashMap<>();
        if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
            plannedLoads = teacherVocationalPlannedLoads(criteria, teacherIds, report, schoolId);
        }

        Map<Long, List<JournalEntry>> journalOccurredLessons = teacherJournalOccurredLessons(schoolId, criteria,
                teacherIds);

        Map<Long, List<TimetableEvent>> timetableOccurredLessons = new HashMap<>();
        Map<Long, List<TimetableEvent>> substitutedLessons = new HashMap<>();
        if (Boolean.TRUE.equals(criteria.getShowTimetableLoad())) {
            timetableOccurredLessons = teacherTimetableOccurredLessons(schoolId, criteria, teacherIds);
            substitutedLessons = teacherLessonsAsSubstitute(schoolId, criteria, teacherIds);
        }

        Map<Long, List<TimetableEvent>> singleEvents = new HashMap<>();
        if (Boolean.TRUE.equals(criteria.getShowSingleEvents())) {
            singleEvents = teacherSingleEvents(schoolId, criteria, teacherIds);
        }

        for (TeacherDetailLoadDto dto : teachers.getContent()) {
            Long teacherId = dto.getTeacher().getId();

            if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
                List<PlannedLoad> teacherPlannedHours = plannedLoads.get(teacherId);
                setTeacherDetailLoadPlannedHours(dto, criteria, teacherPlannedHours, capacities, report);
            }

            List<JournalEntry> teacherJournalOccurredLessons = journalOccurredLessons.get(teacherId);
            setTeacherDetailLoadJournalOccurredLessons(dto, criteria, teacherJournalOccurredLessons, capacities, report);

            if (Boolean.TRUE.equals(criteria.getShowTimetableLoad())) {
                List<TimetableEvent> teacherTimetableOccurredHours = timetableOccurredLessons.get(teacherId);
                setTeacherDetailLoadTimetableOccurredHours(dto, criteria, teacherTimetableOccurredHours, capacities, report);
            }

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
        if (teacherIds.isEmpty()) {
            return teachers;
        }

        List<Classifier> capacities = getTeacherCapacities(schoolId, criteria, teacherIds);
        Map<Long, List<PlannedLoad>> plannedLoads = new HashMap<>();
        if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
            plannedLoads = teacherHigherPlannedLoads(criteria, teacherIds, schoolId);
        }
        Map<Long, List<TimetableEvent>> occurredLessons = teacherTimetableOccurredLessons(schoolId, criteria,
                teacherIds);
        Map<Long, List<TimetableEvent>> substitutedLessons = teacherLessonsAsSubstitute(schoolId, criteria, teacherIds);

        Map<Long, List<TimetableEvent>> singleEvents = new HashMap<>();
        if (Boolean.TRUE.equals(criteria.getShowSingleEvents())) {
            singleEvents = teacherSingleEvents(schoolId, criteria, teacherIds);
        }

        for (TeacherDetailLoadDto dto : teachers.getContent()) {
            Long teacherId = dto.getTeacher().getId();

            if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
                List<PlannedLoad> teacherPlannedHours = plannedLoads.get(teacherId);
                // set contact/coef hours here too
                setTeacherDetailLoadPlannedHours(dto, criteria, teacherPlannedHours, capacities, report);
            }

            List<TimetableEvent> teacherOccurredHours = occurredLessons.get(teacherId);
            // set contact/coef hours here too
            setTeacherDetailLoadTimetableOccurredHours(dto, criteria, teacherOccurredHours, capacities, report);
            
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
        teacherCapacities.addAll(nonNullCapacities(dto.getJournalTotalCapacityOccurredLessons()));
        teacherCapacities.addAll(nonNullCapacities(dto.getTimetableTotalCapacityOccurredLessons()));
        teacherCapacities.addAll(nonNullCapacities(dto.getTotalCapacityPlannedHours()));
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
            if (Boolean.TRUE.equals(criteria.getByStudyPeriods())) {
                List<StudyPeriodWithWeeksDto> studyPeriods = report.getStudyPeriods();
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
            if (Boolean.TRUE.equals(criteria.getByStudyPeriods())) {
                List<StudyPeriodWithWeeksDto> studyPeriods = report.getStudyPeriods();
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
        dto.setTotalPlannedContactHours(totalPlannedContactHours(teacherPlannedHours));
        dto.setTotalPlannedKoefHours(totalPlannedKoefHours(teacherPlannedHours));
    }

	private static void setTeacherDetailLoadTimetableOccurredHours(PeriodDetailLoadDto dto,
            TeacherDetailLoadCommand criteria, List<TimetableEvent> timetableOccurredHours, List<Classifier> capacities,
            TeacherDetailLoadReportDataDto report) {
        if (timetableOccurredHours == null) {
            return;
        }

        if (Boolean.TRUE.equals(criteria.getByCapacities())) {
            if (Boolean.TRUE.equals(criteria.getByStudyPeriods())) {
                List<StudyPeriodWithWeeksDto> studyPeriods = report.getStudyPeriods();
                dto.setCapacityTimetableOccurredLessons(
                        studyPeriodCapacityTimetableOccurredEvents(timetableOccurredHours, studyPeriods, capacities));
            } else if (Boolean.TRUE.equals(criteria.getByWeeks())) {
                List<WeekDto> weeks = report.getWeeks();
                dto.setCapacityTimetableOccurredLessons(weekCapacityTimetableOccurredEvents(timetableOccurredHours, 
                        weeks, capacities));
            } else if (Boolean.TRUE.equals(criteria.getByMonths())) {
                Set<Long> months = report.getWeekNrsByMonth().keySet();
                dto.setCapacityTimetableOccurredLessons(monthCapacityTimetableOccurredEvents(timetableOccurredHours,
                        months, capacities));
            }
            dto.setTimetableTotalCapacityOccurredLessons(capacityTimetableOccurredEvents(timetableOccurredHours,
                    capacities));
        } else {
            if (Boolean.TRUE.equals(criteria.getByStudyPeriods())) {
                List<StudyPeriodWithWeeksDto> studyPeriods = report.getStudyPeriods();
                dto.setTimetableOccurredLessons(
                        studyPeriodTimetableOccurredEvents(timetableOccurredHours, studyPeriods));
            } else if (Boolean.TRUE.equals(criteria.getByWeeks())) {
                List<WeekDto> weeks = report.getWeeks();
                dto.setTimetableOccurredLessons(weekTimetableOccurredEvents(timetableOccurredHours, weeks));
            } else if (Boolean.TRUE.equals(criteria.getByMonths())) {
                Set<Long> months = report.getWeekNrsByMonth().keySet();
                dto.setTimetableOccurredLessons(monthTimetableOccurredEvents(timetableOccurredHours, months));
            }
        }
        dto.setTimetableTotalOccurredLessons(timetableTotalOccurredEvents(timetableOccurredHours));
        dto.setTimetableTotalOccurredContactLessons(timetableTotalOccurredContactEvents(timetableOccurredHours));
        dto.setTimetableTotalOccurredKoefLessons(timetableTotalOccurredKoefEvents(timetableOccurredHours));
    }

    private static void setTeacherDetailLoadJournalOccurredLessons(PeriodDetailLoadDto dto,
            TeacherDetailLoadCommand criteria, List<JournalEntry> journalOccurredLessons, List<Classifier> capacities,
            TeacherDetailLoadReportDataDto report) {
        if (journalOccurredLessons == null) {
            return;
        }

        if (Boolean.TRUE.equals(criteria.getByCapacities())) {
            if (Boolean.TRUE.equals(criteria.getByStudyPeriods())) {
                List<StudyPeriodWithWeeksDto> studyPeriods = report.getStudyPeriods();
                dto.setCapacityJournalOccurredLessons(
                		studyPeriodCapacityJournalOccurredLessons(journalOccurredLessons, studyPeriods, capacities));
            } else if (Boolean.TRUE.equals(criteria.getByWeeks())) {
                List<WeekDto> weeks = report.getWeeks();
                dto.setCapacityJournalOccurredLessons(weekCapacityJournalOccurredLessons(journalOccurredLessons, 
                        weeks, capacities));
            } else if (Boolean.TRUE.equals(criteria.getByMonths())) {
                Set<Long> months = report.getWeekNrsByMonth().keySet();
                dto.setCapacityJournalOccurredLessons(
                        monthCapacityJournalOccurredLessons(journalOccurredLessons, months, capacities));
            }
            dto.setJournalTotalCapacityOccurredLessons(
                    capacityJournalOccurredLessons(journalOccurredLessons, capacities));
        } else {
            if (Boolean.TRUE.equals(criteria.getByStudyPeriods())) {
                List<StudyPeriodWithWeeksDto> studyPeriods = report.getStudyPeriods();
                dto.setJournalOccurredLessons(studyPeriodJournalOccurredLessons(journalOccurredLessons, studyPeriods));
            } else if (Boolean.TRUE.equals(criteria.getByWeeks())) {
                List<WeekDto> weeks = report.getWeeks();
                dto.setJournalOccurredLessons(weekJournalOccurredLessons(journalOccurredLessons, weeks));
            } else if (Boolean.TRUE.equals(criteria.getByMonths())) {
                Set<Long> months = report.getWeekNrsByMonth().keySet();
                dto.setJournalOccurredLessons(monthJournalOccurredLessons(journalOccurredLessons, months));
            }
        }
        dto.setJournalTotalOccurredLessons(journalTotalOccurredLessons(journalOccurredLessons));
        dto.setJournalTotalOccurredContactLessons(timetableTotalOccurredContactEvents(journalOccurredLessons));
        dto.setJournalTotalOccurredKoefLessons(timetableTotalOccurredKoefEvents(journalOccurredLessons));
    }

    private static void setTeacherDetailLoadSingleEvents(PeriodDetailLoadDto dto, TeacherDetailLoadCommand criteria,
            List<TimetableEvent> teacherSingleEvents, TeacherDetailLoadReportDataDto report) {
        if (teacherSingleEvents == null) {
            return;
        }

        if (Boolean.TRUE.equals(criteria.getByStudyPeriods())) {
            List<StudyPeriodWithWeeksDto> studyPeriods = report.getStudyPeriods();
            dto.setSingleEvents(studyPeriodTimetableOccurredEvents(teacherSingleEvents, studyPeriods));
        } else if (Boolean.TRUE.equals(criteria.getByWeeks())) {
            List<WeekDto> weeks = report.getWeeks();
            dto.setSingleEvents(weekTimetableOccurredEvents(teacherSingleEvents, weeks));
        } else if (Boolean.TRUE.equals(criteria.getByMonths())) {
            Set<Long> months = report.getWeekNrsByMonth().keySet();
            dto.setSingleEvents(monthTimetableOccurredEvents(teacherSingleEvents, months));
        }
        dto.setTotalSingleEvents(timetableTotalOccurredEvents(teacherSingleEvents));
    }

    private static void setTeacherDetailLoadSubstitutedLessons(PeriodDetailLoadDto dto,
            TeacherDetailLoadCommand criteria, List<TimetableEvent> teacherSubstitutedLessons,
            TeacherDetailLoadReportDataDto report) {
        if (teacherSubstitutedLessons == null) {
            return;
        }

        if (Boolean.TRUE.equals(criteria.getByStudyPeriods())) {
            List<StudyPeriodWithWeeksDto> studyPeriods = report.getStudyPeriods();
            dto.setSubstitutedLessons(studyPeriodTimetableOccurredEvents(teacherSubstitutedLessons, studyPeriods));
        } else if (Boolean.TRUE.equals(criteria.getByWeeks())) {
            List<WeekDto> weeks = report.getWeeks();
            dto.setSubstitutedLessons(weekTimetableOccurredEvents(teacherSubstitutedLessons, weeks));
        } else if (Boolean.TRUE.equals(criteria.getByMonths())) {
            Set<Long> months = report.getWeekNrsByMonth().keySet();
            dto.setSubstitutedLessons(monthTimetableOccurredEvents(teacherSubstitutedLessons, months));
        }
        dto.setTotalSubstitutedLessons(timetableTotalOccurredEvents(teacherSubstitutedLessons));
    }
    
    private JpaNativeQueryBuilder getTeacherQueryBuilder(Long schoolId, TeacherDetailLoadCommand criteria, Pageable pageable) {
    	JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from teacher t "
        		+ "join person p on t.person_id = p.id " +
        		(Boolean.TRUE.equals(criteria.getIsHigher()) ?
        		"left join (select s.id, s.name_et, s.name_en, s.code, sspt.teacher_id, sy.id as studyYearId, sp.id as studyPeriodId, ssp.id as subjectStudyPeriodId "
        			+ "from subject_study_period_teacher sspt "
        			+ "join subject_study_period ssp on ssp.id = sspt.subject_study_period_id "
        			+ "join subject s on ssp.subject_id = s.id "
        			+ "join study_period sp on ssp.study_period_id = sp.id "
        			+ "join study_year sy on sy.id = sp.study_year_id) S1 "
        			+ "on S1.teacher_id = t.id "
        			+ "and S1.studyYearId = :studyYearId " 
        			+ (criteria.getStudyPeriod() != null ? "and S1.studyPeriodId = :studyPeriodId" : "")
        		: 
        		"left join (select j.name_et, j.id, sy.id as studyYearId, sp.id as studyPeriodId, jt.teacher_id "
	        		+ "from journal_teacher jt "
	        		+ "join journal j on jt.journal_id = j.id "
	        		+ "join study_year sy on j.study_year_id = sy.id "
	        		+ "join study_period sp on sp.study_year_id = sy.id) J1 "
	        		+ "on J1.teacher_id = t.id "
	        		+ "and J1.studyYearId = :studyYearId "
	        		+ (criteria.getStudyPeriod() != null ? "and J1.studyPeriodId = :studyPeriodId" : "")))
	                .sort("p.lastname", "p.firstname");
    	qb.parameter("studyYearId", criteria.getStudyYear());
    	if (criteria.getStudyPeriod() != null) {
    		qb.parameter("studyPeriodId", criteria.getStudyPeriod());
    	}
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", schoolId);
        
        if (Boolean.TRUE.equals(criteria.getIsHigher())) {
            qb.optionalCriteria("t.is_higher = :higher", "higher", Boolean.TRUE);
            qb.optionalCriteria("exists(select sspsg.id "
            		+ "from subject_study_period_student_group sspsg "
            		+ "where sspsg.student_group_id in (:studentGroupIds) "
            		+ "and sspsg.subject_study_period_id = S1.subjectStudyPeriodId)", "studentGroupIds", criteria.getStudentGroups());
            qb.optionalCriteria("(exists"
                    + "(select spsg.id " 
                    + "from subject_study_period_student_group spsg "
                    + "join student_group sg on spsg.student_group_id = sg.id "
                    + "where sg.curriculum_id = :curriculumId "
                    + "and spsg.subject_study_period_id = S1.subjectStudyPeriodId) "
                    + "or exists"
                    + "(select c.id "
                    + "from curriculum c "
                    + "join curriculum_version cv on cv.curriculum_id = c.id "
                    + "join curriculum_version_hmodule cvh on cvh.curriculum_version_id = cv.id "
                    + "join curriculum_version_hmodule_subject cvhs on cvhs.curriculum_version_hmodule_id = cvh.id "
                    + "where cvhs.subject_id = S1.id "
                    + "and c.id = :curriculumId))", "curriculumId", criteria.getCurriculum());
        } else {
            qb.optionalCriteria("t.is_vocational = :vocational", "vocational", Boolean.TRUE);
            qb.optionalCriteria("exists(select lp.id from lesson_plan lp "
            		+ "join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id "
            		+ "join journal_omodule_theme jot on jot.lesson_plan_module_id = lpm.id "
            		+ "where lp.student_group_id in (:studentGroupIds) "
            		+ "and jot.journal_id = J1.id)", "studentGroupIds", criteria.getStudentGroups());
            qb.optionalCriteria("exists(select lp.id from lesson_plan lp "
            		+ "join curriculum_version cv on lp.curriculum_version_id = cv.id "
            		+ "join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id "
            		+ "join journal_omodule_theme jot on jot.lesson_plan_module_id = lpm.id "
            		+ "where cv.curriculum_id = :curriculumId "
            		+ "and jot.journal_id = J1.id)", "curriculumId", criteria.getCurriculum());
        }
        qb.optionalCriteria("t.is_active = :active", "active", Boolean.TRUE);
        qb.optionalCriteria("t.id = :teacherId", "teacherId", criteria.getTeacher());
        qb.optionalCriteria("t.id in (:teacherIds)", "teacherIds", criteria.getTeachers());
        
        if (StringUtils.hasText(criteria.getTeacherPosition())) {
        	LocalDate now = LocalDate.now();
            qb.optionalCriteria("t.id in (select tpe.teacher_id from teacher_position_ehis tpe "
            		+ "where tpe.is_contract_ended = false "
            		+ "and (tpe.contract_end is null or tpe.contract_end >= :now) "
            		+ "and tpe.position_code = :teacherPosition)", "teacherPosition", criteria.getTeacherPosition());
            qb.parameter("now", now);
        }
        qb.sort(pageable);
        return qb;
    }

    private Page<TeacherDetailLoadDto> teachers(Long schoolId, TeacherDetailLoadCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = getTeacherQueryBuilder(schoolId, criteria, pageable);
        qb.groupBy("t.id, p.firstname, p.lastname");
        return JpaQueryUtil.pagingResult(qb, "t.id, p.firstname, p.lastname", em, pageable).map(r -> {
            TeacherDetailLoadDto dto = new TeacherDetailLoadDto(); 
            String name = PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2));
            dto.setTeacher(new AutocompleteResult(resultAsLong(r, 0), name, name));
            return dto;
        });
    }
    
    private Map<Long, List<TeacherDetailLoadJournalSubjectDto>> teachersWithSubjectOrJournal(Long schoolId, TeacherDetailLoadCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = getTeacherQueryBuilder(schoolId, criteria, pageable);
        if (Boolean.TRUE.equals(criteria.getIsHigher())) {
        	List<?> data = qb.select("distinct t.id as teacherId, p.firstname, p.lastname, S1.id as subjectId, S1.name_et, S1.name_en, S1.code as subjectCode", em).getResultList();

            Map<Long, List<TeacherDetailLoadJournalSubjectDto>> mappedTeachers = StreamUtil.nullSafeList(data).stream()
            		.collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                    Collectors.mapping(r -> {
                    	TeacherDetailLoadJournalSubjectDto subjectJournal = new TeacherDetailLoadJournalSubjectDto();
                    	subjectJournal.setJournalSubject(new AutocompleteResult(resultAsLong(r, 3), resultAsString(r, 4), resultAsString(r, 5)));
                    	subjectJournal.setSubjectCode(resultAsString(r, 6));
                    	return subjectJournal;
                    }, Collectors.toList())));
            return mappedTeachers;
        } else {
        	List<?> data = qb.select("distinct t.id as teacherId, p.firstname, p.lastname, J1.id as journalId, J1.name_et", em).getResultList();

            Map<Long, List<TeacherDetailLoadJournalSubjectDto>> mappedTeachers = StreamUtil.nullSafeList(data).stream()
            		.collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                    Collectors.mapping(r -> {
                    	TeacherDetailLoadJournalSubjectDto subjectJournal = new TeacherDetailLoadJournalSubjectDto();
                    	subjectJournal.setJournalSubject(new AutocompleteResult(resultAsLong(r, 3), resultAsString(r, 4), resultAsString(r, 4)));
                    	return subjectJournal;
                    }, Collectors.toList())));
            return mappedTeachers;
        }
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
                        + " select ject.capacity_type_code from journal j"
                        + " join journal_entry je on j.id = je.journal_id"
                        + " join journal_teacher jt on j.id = jt.journal_id"
                        + " join journal_entry_capacity_type ject on je.id = ject.journal_entry_id"
                        + " where jt.teacher_id in (:teacherIds) and j.study_year_id = :studyYearId";
                if (Boolean.TRUE.equals(criteria.getShowTimetableLoad())) {
                    capacitiesQuery += " union all"
                            + " select jct2.capacity_type_code from journal_teacher jt2"
                            + " join journal j2 on jt2.journal_id = j2.id"
                            + " join journal_capacity_type jct2 on j2.id = jct2.journal_id"
                            + " where jt2.teacher_id in (:teacherIds) and j2.study_year_id = :studyYearId";
                }
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
            List<Long> teacherIds, TeacherDetailLoadReportDataDto report, Long schoolId) {
        StudyPeriodWithWeeksDto studyPeriod = null;
        if (criteria.getStudyPeriod() != null) {
            StudyPeriod sp = em.getReference(StudyPeriod.class, criteria.getStudyPeriod());
            studyPeriod = new StudyPeriodWithWeeksDto(sp);
        }
        List<Short> weeksBetweenFromAndThru = weeksBetweenFromAndThru(criteria.getFrom(), criteria.getThru(),
                report.getWeeks());

        Map<Long, List<PlannedLoad>> plannedLoads = teacherJournalPlannedLoads(criteria, studyPeriod,
                weeksBetweenFromAndThru, teacherIds, schoolId);
        Map<Long, List<PlannedLoad>> specificPlannedLoads = teacherJournalSpecificPlannedLoads(criteria,
                studyPeriod, weeksBetweenFromAndThru, teacherIds, schoolId);
        joinTeacherPlannedLoads(plannedLoads, specificPlannedLoads);
        return plannedLoads;
    }

    private static List<Short> weeksBetweenFromAndThru(LocalDate from, LocalDate thru, List<WeekDto> weeks) {
        List<Short> weeksBetweenFromAndThru = new ArrayList<>();
        if (from != null || thru != null) {
            LocalDate fromWeekStart = from != null ? previousMonday(from) : null;
            LocalDate thruWeekStart = thru != null ? previousMonday(thru) : null;
            for (WeekDto week : weeks) {
                LocalDate weekStart = week.getWeekStart();
                if ((fromWeekStart == null || weekStart.isEqual(fromWeekStart) || weekStart.isAfter(fromWeekStart)) &&
                    (thruWeekStart == null || weekStart.isEqual(thruWeekStart) || weekStart.isBefore(thruWeekStart))) {
                    weeksBetweenFromAndThru.add(week.getNr());
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
            StudyPeriodWithWeeksDto studyPeriod, List<Short> weeksBetweenFromAndThru, List<Long> teacherIds, Long schoolId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal_teacher jt"
                + " join journal j on jt.journal_id = j.id"
                + " join journal_capacity jc on jc.journal_id = j.id and (j.is_capacity_diff is null or j.is_capacity_diff = false)"
                + " join journal_capacity_type jct on jc.journal_capacity_type_id = jct.id"
                + " left join classifier lp_coefficient on lp_coefficient.code = (select min(lp.coefficient_code) from lesson_plan lp"
                    + " join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id"
                    + " join journal_omodule_theme jot on jot.lesson_plan_module_id = lpm.id"
                    + " where jot.journal_id = j.id)"
                + " left join school_capacity_type sct on sct.capacity_type_code = jct.capacity_type_code and sct.is_higher is not true " 
                    + (schoolId != null ? "and sct.school_id = " + schoolId : "")
                + " left join school_capacity_type_load sctl on sctl.school_capacity_type_id = sct.id and sctl.study_year_id = j.study_year_id"
                    + " and coalesce(lp_coefficient.code, 'KOEFITSIENT_K1') = sctl.coefficient_code");
        
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
        
        qb.optionalCriteria("exists(select lp.id from lesson_plan lp "
        		+ "join curriculum_version cv on lp.curriculum_version_id = cv.id "
        		+ "join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id "
        		+ "join journal_omodule_theme jot on jot.lesson_plan_module_id = lpm.id "
        		+ "where cv.curriculum_id = :curriculumId "
        		+ "and jot.journal_id = j.id)", "curriculumId", criteria.getCurriculum());
        
        qb.optionalCriteria("exists(select lp.id from lesson_plan lp "
        		+ "join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id "
        		+ "join journal_omodule_theme jot on jot.lesson_plan_module_id = lpm.id "
        		+ "where lp.student_group_id in (:studentGroupIds) "
        		+ "and jot.journal_id = j.id)", "studentGroupIds", criteria.getStudentGroups());

        List<?> data = qb.select("jt.teacher_id, jct.capacity_type_code, jc.week_nr, jc.hours,"
                + " j.id journal_id, null study_period_id, null subject_study_period_id, sct.is_contact as isContact,"
                + " coalesce(sctl.load_percentage, 100) * coalesce(jc.hours, 1) / 100.0 as hoursWithLoad", em).getResultList();
        return mapTeacherLoads(data);
    }

    private Map<Long, List<PlannedLoad>> teacherJournalSpecificPlannedLoads(
            TeacherDetailLoadCommand criteria, StudyPeriodWithWeeksDto studyPeriod, List<Short> weeksBetweenFromAndThru,
            List<Long> teacherIds, Long schoolId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal_teacher jt"
                + " join journal j on jt.journal_id = j.id"
                + " join journal_teacher_capacity jtc on jt.id = jtc.journal_teacher_id and j.is_capacity_diff = true"
                + " join journal_capacity_type jct on jtc.journal_capacity_type_id = jct.id"
                + " left join classifier lp_coefficient on lp_coefficient.code = (select min(lp.coefficient_code) from lesson_plan lp"
                    + " join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id"
                    + " join journal_omodule_theme jot on jot.lesson_plan_module_id = lpm.id"
                    + " where jot.journal_id = j.id)"
                + " left join school_capacity_type sct on sct.capacity_type_code = jct.capacity_type_code and sct.is_higher is not true "
                    + (schoolId != null ? "and sct.school_id = " + schoolId : "")
                + " left join school_capacity_type_load sctl on sctl.school_capacity_type_id = sct.id"
                    + " and sctl.study_year_id = j.study_year_id and coalesce(lp_coefficient.code, 'KOEFITSIENT_K1') = sctl.coefficient_code");
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
        
        qb.optionalCriteria("exists(select lp.id from lesson_plan lp "
        		+ "join curriculum_version cv on lp.curriculum_version_id = cv.id "
        		+ "join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id "
        		+ "join journal_omodule_theme jot on jot.lesson_plan_module_id = lpm.id "
        		+ "where cv.curriculum_id = :curriculumId "
        		+ "and jot.journal_id = j.id)", "curriculumId", criteria.getCurriculum());
        
        qb.optionalCriteria("exists(select lp.id from lesson_plan lp "
        		+ "join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id "
        		+ "join journal_omodule_theme jot on jot.lesson_plan_module_id = lpm.id "
        		+ "where lp.student_group_id in (:studentGroupIds) "
        		+ "and jot.journal_id = j.id)", "studentGroupIds", criteria.getStudentGroups());

        List<?> data = qb.select("jt.teacher_id, jct.capacity_type_code, jtc.week_nr, jtc.hours,"
                + " j.id journal_id, null study_period_id, null subject_study_period_id, sct.is_contact as isContact,"
                + " coalesce(sctl.load_percentage, 100) * coalesce(jtc.hours, 1) / 100.0 as hoursWithLoad", em).getResultList();
        return mapTeacherLoads(data);
    }

    private Map<Long, List<PlannedLoad>> teacherHigherPlannedLoads(TeacherDetailLoadCommand criteria,
            List<Long> teacherIds, Long schoolId) {
        Map<Long, List<PlannedLoad>> plannedLoads = teacherSubjectStudyPeriodPlannedLoads(criteria, teacherIds, schoolId);
        Map<Long, List<PlannedLoad>> specificPlannedLoads = teacherSubjectStudyPeriodSpecificPlannedLoads(criteria,
                teacherIds, schoolId);
        joinTeacherPlannedLoads(plannedLoads, specificPlannedLoads);
        return plannedLoads;
    }

    private Map<Long, List<PlannedLoad>> teacherSubjectStudyPeriodPlannedLoads(TeacherDetailLoadCommand criteria,
            List<Long> teacherIds, Long schoolId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period_capacity sspc"
                + " join subject_study_period ssp on ssp.id = sspc.subject_study_period_id"
                    + " and (ssp.is_capacity_diff is null or ssp.is_capacity_diff = false)"
                + " join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id"
                + " join study_period sp on sp.id = ssp.study_period_id"
                + " left join subject s on ssp.subject_id = s.id"
                + " left join (select sspp.ssp_id, ssppc.is_contact, ssppc.capacity_type_code"
                	+ " from (" + SubjectStudyPeriodPlanService.SQL_JOIN_SELECT_PLAN_BY_SSP + ") sspp"
                	+ " join subject_study_period_plan_capacity ssppc on ssppc.subject_study_period_plan_id = sspp.plan_id) plan"
                		+ " on plan.ssp_id = ssp.id and plan.capacity_type_code = sspc.capacity_type_code"
		        + " left join school_capacity_type sct on sct.capacity_type_code = sspc.capacity_type_code and sct.is_higher = true"
		        + " left join school_capacity_type_load sctl on sctl.school_capacity_type_id = sct.id and sctl.study_year_id = sp.study_year_id"
                    + " and coalesce(ssp.coefficient_code, 'KOEFITSIENT_K1') = sctl.coefficient_code");
        qb.requiredCriteria("sp.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("sp.id = :studyPeriodId", "studyPeriodId", criteria.getStudyPeriod());
        qb.requiredCriteria("sct.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("sspt.teacher_id in (:teacherIds)", "teacherIds", teacherIds);
        qb.optionalCriteria("ssp.id in (select sspsg.subject_study_period_id from subject_study_period_student_group sspsg where sspsg.student_group_id in (:studentGroupIds))", 
        		"studentGroupIds", criteria.getStudentGroups());
        qb.optionalCriteria("(exists"
                + "(select spsg.id " 
                + "from subject_study_period_student_group spsg "
                + "join student_group sg on spsg.student_group_id = sg.id "
                + "where sg.curriculum_id = :curriculumId "
                + "and spsg.subject_study_period_id = ssp.id) "
                + "or exists"
                + "(select c.id "
                + "from curriculum c "
                + "join curriculum_version cv on cv.curriculum_id = c.id "
                + "join curriculum_version_hmodule cvh on cvh.curriculum_version_id = cv.id "
                + "join curriculum_version_hmodule_subject cvhs on cvhs.curriculum_version_hmodule_id = cvh.id "
                + "where cvhs.subject_id = s.id "
                + "and c.id = :curriculumId))", "curriculumId", criteria.getCurriculum());

        List<?> data = qb.select("sspt.teacher_id, sspc.capacity_type_code, null week_nr, sspc.hours,"
                + " null journal_id, sp.id study_period_id, ssp.id subject_study_period_id, coalesce(plan.is_contact, true), "
                + "coalesce(sctl.load_percentage, 100) * coalesce(sspc.hours, 1) / 100.0 as hoursWithLoad", em).getResultList();
        return mapTeacherLoads(data);
    }

    private Map<Long, List<PlannedLoad>> teacherSubjectStudyPeriodSpecificPlannedLoads(
            TeacherDetailLoadCommand criteria, List<Long> teacherIds, Long schoolId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period_capacity sspc"
                + " join subject_study_period ssp on ssp.id = sspc.subject_study_period_id and ssp.is_capacity_diff = true"
                + " join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id"
                + " join (" + SQL_SELECT_TEACHER_CAPACITY + ") ssptc on ssptc.subject_study_period_capacity_id = sspc.id"
                    + " and ssptc.subject_study_period_teacher_id = sspt.id"
                + " join study_period sp on sp.id = ssp.study_period_id "
                + " left join subject s on ssp.subject_id = s.id"
                + " left join (select sspp.ssp_id, ssppc.is_contact, ssppc.capacity_type_code"
                    + " from (" + SubjectStudyPeriodPlanService.SQL_JOIN_SELECT_PLAN_BY_SSP + ") sspp"
                    + " join subject_study_period_plan_capacity ssppc on ssppc.subject_study_period_plan_id = sspp.plan_id) plan"
                        + " on plan.ssp_id = ssp.id and plan.capacity_type_code = sspc.capacity_type_code"
		        + " left join school_capacity_type sct on sct.capacity_type_code = sspc.capacity_type_code and sct.is_higher = true"
		        + " left join school_capacity_type_load sctl on sctl.school_capacity_type_id = sct.id and sctl.study_year_id = sp.study_year_id"
                    + " and coalesce(ssp.coefficient_code, 'KOEFITSIENT_K1') = sctl.coefficient_code");
        qb.requiredCriteria("sp.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("sp.id = :studyPeriodId", "studyPeriodId", criteria.getStudyPeriod());
        qb.optionalCriteria("sspt.teacher_id in (:teacherIds)", "teacherIds", teacherIds);
        qb.requiredCriteria("sct.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("ssp.id in (select sspsg.subject_study_period_id from subject_study_period_student_group sspsg where sspsg.student_group_id in (:studentGroupIds))", 
        		"studentGroupIds", criteria.getStudentGroups());
        qb.optionalCriteria("(exists"
                + "(select spsg.id " 
                + "from subject_study_period_student_group spsg "
                + "join student_group sg on spsg.student_group_id = sg.id "
                + "where sg.curriculum_id = :curriculumId "
                + "and spsg.subject_study_period_id = ssp.id) "
                + "or exists"
                + "(select c.id "
                + "from curriculum c "
                + "join curriculum_version cv on cv.curriculum_id = c.id "
                + "join curriculum_version_hmodule cvh on cvh.curriculum_version_id = cv.id "
                + "join curriculum_version_hmodule_subject cvhs on cvhs.curriculum_version_hmodule_id = cvh.id "
                + "where cvhs.subject_id = s.id "
                + "and c.id = :curriculumId))", "curriculumId", criteria.getCurriculum());

        List<?> data = qb.select("sspt.teacher_id, sspc.capacity_type_code, null week_nr, ssptc.hours,"
                + " null journal_id, sp.id study_period_id, ssp.id subject_study_period_id, coalesce(plan.is_contact, true), "
                + "coalesce(sctl.load_percentage, 100) * coalesce(ssptc.hours, 1) / 100.0 as hoursWithLoad", em).getResultList();
        return mapTeacherLoads(data);
    }

    private static Map<Long, List<PlannedLoad>> mapTeacherLoads(List<?> data) {
        Map<Long, List<PlannedLoad>> teacherLoads = new HashMap<>();
        if (!data.isEmpty()) {
            teacherLoads = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), 
                    Collectors.mapping(r -> new PlannedLoad(resultAsString(r, 1), resultAsShort(r, 2), 
                            resultAsLong(r, 3), resultAsLong(r, 4), resultAsLong(r, 5), resultAsLong(r, 6),
                            resultAsBoolean(r, 7), resultAsDecimal(r,8)),
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

    private static Map<Long, TeacherDetailLoad> studyPeriodPlannedHours(Boolean isHigher, List<PlannedLoad> teacherPlannedHours,
            List<StudyPeriodWithWeeksDto> studyPeriods) {
        Map<Long, TeacherDetailLoad> plannedHoursBySp = new HashMap<>();
        for (StudyPeriodWithWeeksDto sp : studyPeriods) {
            plannedHoursBySp.put(sp.getId(), new TeacherDetailLoad(
            		Boolean.TRUE.equals(isHigher)
                    ? periodPlannedHoursSumByStudyPeriod(teacherPlannedHours, sp.getId())
                    : periodPlannedHoursSumByWeeks(teacherPlannedHours, sp.getWeekNrs()),
            		periodPlannedContactHoursSumByStudyPeriod(teacherPlannedHours, sp.getId()),
            		periodPlannedKoefHoursSumByStudyPeriod(teacherPlannedHours, sp.getId())));
        }
        return plannedHoursBySp;
    }
    
    private static BigDecimal periodPlannedKoefHoursSumByStudyPeriod(List<PlannedLoad> teacherPlannedHours, Long studyPeriod) {
    	return StreamUtil.nullSafeList(teacherPlannedHours).stream()
                .filter(h -> studyPeriod.equals(h.getStudyPeriodId())).map(PlannedLoad::getKoef).reduce(BigDecimal::add).orElse(new BigDecimal(0));
    }
    
    private static Long periodPlannedContactHoursSumByStudyPeriod(List<PlannedLoad> teacherPlannedHours, Long studyPeriod) {
        return Long.valueOf(StreamUtil.nullSafeList(teacherPlannedHours).stream()
        		.filter(p -> Boolean.TRUE.equals(p.getIsContact()))
                .filter(h -> studyPeriod.equals(h.getStudyPeriodId())).mapToLong(h -> h.getLessons().longValue()).sum());
    }
    
    private static Long periodPlannedContactHoursSumByWeeks(List<PlannedLoad> teacherPlannedHours, List<Short> weeks) {
        return Long.valueOf(StreamUtil.nullSafeList(teacherPlannedHours).stream()
        		.filter(p -> Boolean.TRUE.equals(p.getIsContact()))
                .filter(h -> weeks.contains(h.getWeekNr())).mapToLong(h -> h.getLessons().longValue()).sum());
    }

    private static Map<Long, TeacherDetailLoad> monthPlannedHours(List<PlannedLoad> teacherPlannedHours,
            Map<Long, List<Short>> weeksByMonth) {
        Map<Long, TeacherDetailLoad> plannedHoursByMonth = new HashMap<>();
        for (Long month : weeksByMonth.keySet()) {
            List<Short> monthWeeks = weeksByMonth.containsKey(month) ? weeksByMonth.get(month) : new ArrayList<>();
            plannedHoursByMonth.put(month, new TeacherDetailLoad(
            		periodPlannedHoursSumByWeeks(teacherPlannedHours, monthWeeks),
            		periodPlannedContactHoursSumByWeeks(teacherPlannedHours, monthWeeks),
            		periodPlannedKoefHoursSumByWeeks(teacherPlannedHours, monthWeeks)));
        }
        return plannedHoursByMonth;
    }

    private static Map<Long, TeacherDetailLoad> weekPlannedHours(List<PlannedLoad> teacherPlannedHours, List<Short> weekNrs) {
        Map<Long, TeacherDetailLoad> plannedHoursByWeek = new HashMap<>();
        for (Short weekNr : weekNrs) {
            plannedHoursByWeek.put(Long.valueOf(weekNr.longValue()),
                    new TeacherDetailLoad(
                    		periodPlannedHoursSumByWeeks(teacherPlannedHours, Arrays.asList(weekNr)),
                    		periodPlannedContactHoursSumByWeeks(teacherPlannedHours, Arrays.asList(weekNr)),
                    		periodPlannedKoefHoursSumByWeeks(teacherPlannedHours, Arrays.asList(weekNr))));
        }
        return plannedHoursByWeek;
    }

    private static BigDecimal periodPlannedKoefHoursSumByWeeks(List<PlannedLoad> teacherPlannedHours, List<Short> weeks) {
    	return StreamUtil.nullSafeList(teacherPlannedHours).stream()
                .filter(h -> weeks.contains(h.getWeekNr())).map(PlannedLoad::getKoef).reduce(BigDecimal::add).orElse(new BigDecimal(0));
	}

	private static Long periodPlannedHoursSumByWeeks(List<PlannedLoad> teacherPlannedHours, List<Short> weeks) {
        return Long.valueOf(StreamUtil.nullSafeList(teacherPlannedHours).stream()
                .filter(h -> weeks.contains(h.getWeekNr())).mapToLong(h -> h.getLessons().longValue()).sum());
    }

    private static Long periodPlannedHoursSumByStudyPeriod(List<PlannedLoad> teacherPlannedHours, Long studyPeriod) {
        return Long.valueOf(StreamUtil.nullSafeList(teacherPlannedHours).stream()
                .filter(h -> studyPeriod.equals(h.getStudyPeriodId())).mapToLong(h -> h.getLessons().longValue()).sum());
    }

    private static Map<Long, TeacherDetailLoad> studyPeriodCapacityPlannedHours(Boolean isHigher,
            List<PlannedLoad> teacherPlannedHours, List<StudyPeriodWithWeeksDto> studyPeriods, List<Classifier> capacitites) {
        Map<Long, TeacherDetailLoad> plannedHoursBySp = new HashMap<>();
        for (StudyPeriodWithWeeksDto sp : studyPeriods) {
            plannedHoursBySp.put(sp.getId(), new TeacherDetailLoad(
            		Boolean.TRUE.equals(isHigher) ?
            				periodPlannedContactHoursSumByStudyPeriod(teacherPlannedHours, sp.getId()) :
        					periodPlannedContactHoursSumByWeeks(teacherPlannedHours, sp.getWeekNrs()),
            		Boolean.TRUE.equals(isHigher) ?
	            		periodPlannedKoefHoursSumByStudyPeriod(teacherPlannedHours, sp.getId()) :
	        			periodPlannedKoefHoursSumByWeeks(teacherPlannedHours, sp.getWeekNrs()),
            		Boolean.TRUE.equals(isHigher) ? 
            				periodCapacityPlannedHoursByStudyPeriod(teacherPlannedHours, sp.getId(), capacitites) : 
        					periodCapacityPlannedHoursByWeeks(teacherPlannedHours, sp.getWeekNrs(), capacitites)
            		));
        }
        return plannedHoursBySp;
    }

    private static Map<Long, TeacherDetailLoad> monthCapacityPlannedHours(List<PlannedLoad> teacherPlannedHours,
            Map<Long, List<Short>> weeksByMonth, List<Classifier> capacitites) {
        Map<Long, TeacherDetailLoad> plannedHoursByMonth = new HashMap<>();
        for (Long month : weeksByMonth.keySet()) {
            List<Short> monthWeeks = weeksByMonth.containsKey(month) ? weeksByMonth.get(month) : new ArrayList<>();
            plannedHoursByMonth.put(month, new TeacherDetailLoad(
            		periodPlannedContactHoursSumByWeeks(teacherPlannedHours, monthWeeks),
    				periodPlannedKoefHoursSumByWeeks(teacherPlannedHours, monthWeeks),
    				periodCapacityPlannedHoursByWeeks(teacherPlannedHours, monthWeeks, capacitites)
            		));
        }
        return plannedHoursByMonth;
    }

    private static Map<Long, TeacherDetailLoad> weekCapacityPlannedHours(List<PlannedLoad> teacherPlannedHours,
            List<Short> weekNrs, List<Classifier> capacitites) {
        Map<Long, TeacherDetailLoad> plannedHoursByWeek = new HashMap<>();
        for (Short weekNr : weekNrs) {
            plannedHoursByWeek.put(Long.valueOf(weekNr.longValue()),
            		new TeacherDetailLoad(
            				periodPlannedContactHoursSumByWeeks(teacherPlannedHours, Arrays.asList(weekNr)),
            				periodPlannedKoefHoursSumByWeeks(teacherPlannedHours, Arrays.asList(weekNr)),
	        				periodCapacityPlannedHoursByWeeks(teacherPlannedHours, Arrays.asList(weekNr), capacitites)
	        				));
        }
        return plannedHoursByWeek;
    }

    private static Map<String, Long> periodCapacityPlannedHoursByWeeks(List<PlannedLoad> teacherPlannedHours,
            List<Short> weekNrs, List<Classifier> capacitites) {
        Map<String, Long> capacityPeriodHours = new HashMap<>();
        for (Classifier c : capacitites) {
            Long capacityPeriodHoursSum = Long.valueOf(StreamUtil.nullSafeList(teacherPlannedHours).stream()
                    .filter(h -> c.getCode().equals(h.getCapacityType()) && weekNrs.contains(h.getWeekNr()))
                    .mapToLong(h -> h.getLessons().longValue()).sum());
            capacityPeriodHours.put(c.getCode(), capacityPeriodHoursSum);
        }
        return capacityPeriodHours;
    }

    private static Map<String, Long> periodCapacityPlannedHoursByStudyPeriod(List<PlannedLoad> teacherPlannedHours,
            Long studyPeriod, List<Classifier> capacitites) {
        Map<String, Long> capacityPeriodHours = new HashMap<>();
        for (Classifier c : capacitites) {
            Long capacityPeriodHoursSum = Long.valueOf(StreamUtil.nullSafeList(teacherPlannedHours).stream()
                    .filter(h -> c.getCode().equals(h.getCapacityType()) && studyPeriod.equals(h.getStudyPeriodId()))
                    .mapToLong(h -> h.getLessons().longValue()).sum());
            capacityPeriodHours.put(c.getCode(), capacityPeriodHoursSum);
        }
        return capacityPeriodHours;
    }

    private static Long totalPlannedHours(List<PlannedLoad> teacherPlannedHours) {
        return Long.valueOf(teacherPlannedHours.stream().mapToLong(h -> h.getLessons().longValue()).sum());
    }
    
    private static BigDecimal totalPlannedKoefHours(List<PlannedLoad> teacherPlannedHours) {
    	return StreamUtil.nullSafeList(teacherPlannedHours).stream()
    			.filter(p -> p.getKoef() != null)
    			.map(PlannedLoad::getKoef).reduce(BigDecimal::add).orElse(new BigDecimal(0));
	}

	private static Long totalPlannedContactHours(List<PlannedLoad> teacherPlannedHours) {
		return Long.valueOf(teacherPlannedHours.stream()
				.filter(p -> Boolean.TRUE.equals(p.getIsContact()))
				.mapToLong(h -> h.getLessons().longValue()).sum());
	}

    private static Map<String, Long> totalCapacityPlannedHours(List<PlannedLoad> teacherPlannedHours,
            List<Classifier> capacitites) {
        Map<String, Long> teacherTotalCapacityHours = new HashMap<>();
        for (Classifier c : capacitites) {
            Long capacityPeriodHoursSum = Long.valueOf(StreamUtil.nullSafeList(teacherPlannedHours).stream()
                    .filter(h -> c.getCode().equals(h.getCapacityType())).mapToLong(h -> h.getLessons().longValue())
                    .sum());
            teacherTotalCapacityHours.put(c.getCode(), capacityPeriodHoursSum);
        }
        return teacherTotalCapacityHours;
    }

    private static JpaNativeQueryBuilder teacherEventsQb(TeacherDetailLoadCommand criteria, List<Long> teacherIds,
            boolean singleEvents, Long schoolId) {
        String from = "from timetable_event_time tem"
                + " join timetable_event te on tem.timetable_event_id = te.id"
                + " join timetable_event_teacher tet on tem.id = tet.timetable_event_time_id"
                + " left join timetable_object t_o on te.timetable_object_id = t_o.id"
                + " left join timetable t on t_o.timetable_id = t.id"
                + " left join study_period sp on t.study_period_id = sp.id " + (criteria.getStudyYear() != null ? "and sp.study_year_id = " + criteria.getStudyYear() : "")
                + " left join classifier lp_coefficient on lp_coefficient.code = (select min(lp.coefficient_code) from lesson_plan lp"
                    + " join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id"
                    + " join journal_omodule_theme jot on jot.lesson_plan_module_id = lpm.id"
                    + " where jot.journal_id = t_o.journal_id)"
                + " left join subject_study_period ssp on ssp.id = t_o.subject_study_period_id"
                + " left join subject s on ssp.subject_id = s.id"
                + " left join (select sspp.ssp_id, ssppc.is_contact, ssppc.capacity_type_code"
                    + " from (" + SubjectStudyPeriodPlanService.SQL_JOIN_SELECT_PLAN_BY_SSP + ") sspp"
                    + " join subject_study_period_plan_capacity ssppc on ssppc.subject_study_period_plan_id = sspp.plan_id) plan"
                        + " on plan.ssp_id = ssp.id and plan.capacity_type_code = te.capacity_type_code"
		        + " left join school_capacity_type sct on sct.capacity_type_code = te.capacity_type_code"
		        	+ " and sct.school_id = :schoolId and " 
        			+ (Boolean.TRUE.equals(criteria.getIsHigher()) ? "sct.is_higher = true" : "sct.is_higher is not true")
		        + " left join school_capacity_type_load sctl on sctl.school_capacity_type_id = sct.id and sctl.study_year_id = sp.study_year_id"
                    + (Boolean.TRUE.equals(criteria.getIsHigher())
                            ? " and coalesce(ssp.coefficient_code, 'KOEFITSIENT_K1') = sctl.coefficient_code"
                            : " and coalesce(lp_coefficient.code, 'KOEFITSIENT_K1') = sctl.coefficient_code");
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.optionalCriteria("sp.id = :studyPeriodId", "studyPeriodId", criteria.getStudyPeriod());
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
        qb.parameter("schoolId", schoolId);
        qb.optionalCriteria("tet.teacher_id in (:teacherIds)", "teacherIds", teacherIds);

        if (singleEvents) {
            qb.filter("t_o.subject_study_period_id is null and t_o.journal_id is null");
            qb.filter("not exists(select 1 from subject_study_period_exam sspe"
                    + " where sspe.timetable_event_id = tem.timetable_event_id)");
        } else {
            if (Boolean.TRUE.equals(criteria.getIsHigher())) {
                qb.filter("(t_o.subject_study_period_id is not null or exists (select 1 from "
                        + "subject_study_period_exam sspe where sspe.timetable_event_id = tem.timetable_event_id))");
                qb.optionalCriteria("exists(select sspsg.id "
                		+ "from subject_study_period_student_group sspsg "
                		+ "where sspsg.student_group_id in (:studentGroupIds) "
                		+ "and sspsg.subject_study_period_id = t_o.subject_study_period_id)", "studentGroupIds", criteria.getStudentGroups());
                qb.optionalCriteria("(exists"
                        + "(select spsg.id " 
                        + "from subject_study_period_student_group spsg "
                        + "join student_group sg on spsg.student_group_id = sg.id "
                        + "where sg.curriculum_id = :curriculumId "
                        + "and spsg.subject_study_period_id = ssp.id) "
                        + "or exists"
                        + "(select c.id "
                        + "from curriculum c "
                        + "join curriculum_version cv on cv.curriculum_id = c.id "
                        + "join curriculum_version_hmodule cvh on cvh.curriculum_version_id = cv.id "
                        + "join curriculum_version_hmodule_subject cvhs on cvhs.curriculum_version_hmodule_id = cvh.id "
                        + "where cvhs.subject_id = s.id "
                        + "and c.id = :curriculumId))", "curriculumId", criteria.getCurriculum());
            } else {
                qb.filter("t_o.journal_id is not null");
                qb.optionalCriteria("exists(select lp.id from lesson_plan lp "
                		+ "join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id "
                		+ "join journal_omodule_theme jot on jot.lesson_plan_module_id = lpm.id "
                		+ "where lp.student_group_id in (:studentGroupIds) "
                		+ "and jot.journal_id = t_o.journal_id)", "studentGroupIds", criteria.getStudentGroups());
                qb.optionalCriteria("exists(select lp.id from lesson_plan lp "
                		+ "join curriculum_version cv on lp.curriculum_version_id = cv.id "
                		+ "join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id "
                		+ "join journal_omodule_theme jot on jot.lesson_plan_module_id = lpm.id "
                		+ "where cv.curriculum_id = :curriculumId "
                		+ "and jot.journal_id = t_o.journal_id)", "curriculumId", criteria.getCurriculum());
            }
        }
        return qb;
    }

    private static Map<Long, List<TimetableEvent>> resultAsEvents(List<?> data) {
        Map<Long, List<TimetableEvent>> occurredLessons = new HashMap<>();
        if (!data.isEmpty()) {
            occurredLessons = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                    Collectors.mapping(r -> new TimetableEvent(resultAsString(r, 2), resultAsLocalDate(r, 3),
                                    resultAsLong(r, 4), resultAsLong(r, 5), resultAsLong(r, 6), resultAsLong(r, 7), 
                                    resultAsBoolean(r, 8), resultAsDecimal(r, 9)),
                            Collectors.toList())));
        }
        return occurredLessons;
    }

    private Map<Long, List<TimetableEvent>> teacherTimetableOccurredLessons(Long schoolId,
            TeacherDetailLoadCommand criteria, List<Long> teacherIds) {
        JpaNativeQueryBuilder qb = teacherEventsQb(criteria, teacherIds, false, schoolId);
        qb.filter("(tet.is_substitute is null or tet.is_substitute = false)");

        List<?> data = qb.select("tet.teacher_id, tem.id tem_id, te.capacity_type_code, te.start, "
                + "get_lessons(tem.start, tem.end) lessons, get_study_period(date(tem.start), " + schoolId + ") sp_id, "
                + "t_o.journal_id, t_o.subject_study_period_id, "
                + (Boolean.TRUE.equals(criteria.getIsHigher()) ? "coalesce(plan.is_contact, true)" : "sct.is_contact")
                + ", coalesce(sctl.load_percentage, 100) * get_lessons(tem.start, tem.end) / 100.0 as hoursWithLoad", em)
                .getResultList();
        return resultAsEvents(data);
    }

    private Map<Long, List<TimetableEvent>> teacherLessonsAsSubstitute(Long schoolId, TeacherDetailLoadCommand criteria,
            List<Long> teacherIds) {
        JpaNativeQueryBuilder qb = teacherEventsQb(criteria, teacherIds, false, schoolId);
        qb.filter("tet.is_substitute = true");

        List<?> data = qb.select("tet.teacher_id, tem.id tem_id, te.capacity_type_code, te.start, "
                + "get_lessons(tem.start, tem.end) lessons, get_study_period(date(tem.start)," + schoolId + ") sp_id, "
                + "t_o.journal_id, t_o.subject_study_period_id, null as isContact, null as load", em).getResultList();
        return resultAsEvents(data);
    }

    private Map<Long, List<TimetableEvent>> teacherSingleEvents(Long schoolId, TeacherDetailLoadCommand criteria,
            List<Long> teacherIds) {
        JpaNativeQueryBuilder qb = teacherEventsQb(criteria, teacherIds, true, schoolId);

        List<?> data = qb.select("tet.teacher_id, tem.id tem_id, te.capacity_type_code, te.start, "
                + "get_lessons(tem.start, tem.end) lessons, get_study_period(date(tem.start), " + schoolId + ") sp_id, "
                + "null journal_id, null subject_study_period_id, null as isContact, null as load", em).getResultList();
        return resultAsEvents(data);
    }

    private Map<Long, List<JournalEntry>> teacherJournalOccurredLessons(Long schoolId, TeacherDetailLoadCommand criteria,
            List<Long> teacherIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j"
                + " join journal_entry je on j.id = je.journal_id"
                + " join journal_entry_teacher jt on je.id = jt.journal_entry_id"
                + " left join journal_entry_capacity_type ject on je.id = ject.journal_entry_id"
                + " left join classifier lp_coefficient on lp_coefficient.code = (select min(lp.coefficient_code) from lesson_plan lp"
                    + " join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id"
                    + " join journal_omodule_theme jot on jot.lesson_plan_module_id = lpm.id"
                    + " where jot.journal_id = j.id)"
                + " left join school_capacity_type sct on sct.capacity_type_code = ject.capacity_type_code and sct.is_higher is not true "
                    + (schoolId != null ? "and sct.school_id = " + schoolId : "")
                + " left join school_capacity_type_load sctl on sctl.school_capacity_type_id = sct.id"
                    + " and sctl.study_year_id = j.study_year_id and coalesce(lp_coefficient.code, 'KOEFITSIENT_K1') = sctl.coefficient_code");

        qb.requiredCriteria("j.study_year_id = :studyYearId", "studyYearId", criteria.getStudyYear());
        qb.optionalCriteria("jt.teacher_id in (:teacherIds)", "teacherIds", teacherIds);
        qb.filter("je.lessons is not null");

        if (criteria.getStudyPeriod() != null) {
            qb.optionalCriteria("je.entry_date >= :studyPeriodStart", "studyPeriodStart",
                    criteria.getStudyPeriodStart(), DateUtils::firstMomentOfDay);
            qb.optionalCriteria("je.entry_date <= :studyPeriodEnd", "studyPeriodEnd", criteria.getStudyPeriodEnd(),
                    DateUtils::lastMomentOfDay);
        }

        String entryDate = "coalesce(je.entry_date, je.homework_duedate, je.inserted)";
        qb.optionalCriteria(entryDate + " >= :from", "from", criteria.getFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria(entryDate + " <= :thru", "thru", criteria.getThru(), DateUtils::lastMomentOfDay);
        
        qb.optionalCriteria("exists(select lp.id from lesson_plan lp "
        		+ "join curriculum_version cv on lp.curriculum_version_id = cv.id "
        		+ "join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id "
        		+ "join journal_omodule_theme jot on jot.lesson_plan_module_id = lpm.id "
        		+ "where cv.curriculum_id = :curriculumId "
        		+ "and jot.journal_id = j.id)", "curriculumId", criteria.getCurriculum());
        
        qb.optionalCriteria("exists(select lp.id from lesson_plan lp "
        		+ "join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id "
        		+ "join journal_omodule_theme jot on jot.lesson_plan_module_id = lpm.id "
        		+ "where lp.student_group_id in (:studentGroupIds) "
        		+ "and jot.journal_id = j.id)", "studentGroupIds", criteria.getStudentGroups());

        List<?> data = qb.select("jt.teacher_id, ject.capacity_type_code, " + entryDate + ","
                + " je.lessons, get_study_period(date(" + entryDate + "), " + schoolId + ") sp_id,"
                + " j.id journal_id, je.id journal_entry_id, sct.is_contact as isContact,"
                + " coalesce(sctl.load_percentage, 100) * coalesce(je.lessons, 1) / 100.0 as hoursWithLoad", em).getResultList();

        Map<Long, List<JournalEntry>> journalEntries = new HashMap<>();
        if (!data.isEmpty()) {
            journalEntries = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                    Collectors.mapping(r -> new JournalEntry(resultAsString(r, 1), resultAsLocalDate(r, 2),
                            resultAsLong(r, 3), resultAsLong(r, 4), resultAsLong(r, 5), resultAsLong(r, 6), 
                            resultAsBoolean(r, 7), resultAsDecimal(r, 8)),
                            Collectors.toList())));
        }
        return journalEntries;
    }

    private static Map<Long, TeacherDetailLoad> studyPeriodTimetableOccurredEvents(List<TimetableEvent> occurredEvents,
            List<StudyPeriodWithWeeksDto> studyPeriods) {
        Map<Long, TeacherDetailLoad> periodLessons = new HashMap<>();

        Map<Long, List<TimetableEvent>> occurredEventsBySp = StreamUtil.nullSafeList(occurredEvents).stream()
                .collect(Collectors.groupingBy(TimetableEvent::getStudyPeriodId));
        for (StudyPeriodWithWeeksDto sp : studyPeriods) {
            List<TimetableEvent> spOccurredEvents = occurredEventsBySp.get(sp.getId());
            periodLessons.put(sp.getId(), 
        		new TeacherDetailLoad(lessonsCount(StreamUtil.nullSafeList(spOccurredEvents).stream()), 
        						contactLessonsCount(StreamUtil.nullSafeList(spOccurredEvents).stream()), 
        						koefLessonsCount(StreamUtil.nullSafeList(spOccurredEvents).stream())));
        }
        return periodLessons;
    }

	private static BigDecimal koefLessonsCount(Stream<? extends TeacherLoad> stream) {
		return stream.filter(p -> p.getKoef() != null).map(TeacherLoad::getKoef).reduce(BigDecimal::add).orElse(new BigDecimal(0));
	}

	private static Map<Long, TeacherDetailLoad> monthTimetableOccurredEvents(List<TimetableEvent> occurredEvents, Set<Long> months) {
        Map<Long, TeacherDetailLoad> periodLessons = new HashMap<>();

        Map<Long, List<TimetableEvent>> eventsByMonths = StreamUtil.nullSafeList(occurredEvents).stream()
                .collect(Collectors.groupingBy(e -> Long.valueOf(e.getDate().getMonthValue())));
        for (Long month : months) {
            List<TimetableEvent> monthEvents = eventsByMonths.get(month);
            periodLessons.put(month, 
    		new TeacherDetailLoad(lessonsCount(StreamUtil.nullSafeList(monthEvents).stream()), 
					contactLessonsCount(StreamUtil.nullSafeList(monthEvents).stream()), 
					koefLessonsCount(StreamUtil.nullSafeList(monthEvents).stream())));
        }
        return periodLessons;
    }

    private static Map<Long, TeacherDetailLoad> weekTimetableOccurredEvents(List<TimetableEvent> occurredEvents,
            List<WeekDto> weeks) {
        Map<Long, TeacherDetailLoad> periodLessons = new HashMap<>();

        Map<Short, List<TimetableEvent>> occurredEventsByWeek = occurredEventsGroupedByWeek(occurredEvents, weeks);
        for (Short weekNr : occurredEventsByWeek.keySet()) {
            List<TimetableEvent> weekEvents = occurredEventsByWeek.get(weekNr);
            periodLessons.put(Long.valueOf(weekNr.longValue()),
            new TeacherDetailLoad(lessonsCount(StreamUtil.nullSafeList(weekEvents).stream()), 
					contactLessonsCount(StreamUtil.nullSafeList(weekEvents).stream()), 
					koefLessonsCount(StreamUtil.nullSafeList(weekEvents).stream())));
        }
        return periodLessons;
    }

    private static Map<Long, TeacherDetailLoad> studyPeriodCapacityTimetableOccurredEvents(
            List<TimetableEvent> occurredEvents, List<StudyPeriodWithWeeksDto> studyPeriods, List<Classifier> capacitites) {
        Map<Long, TeacherDetailLoad> periodCapacityLessons = new HashMap<>();

        Map<Long, List<TimetableEvent>> occurredEventsBySp = StreamUtil.nullSafeList(occurredEvents).stream()
                .collect(Collectors.groupingBy(TimetableEvent::getStudyPeriodId));
        for (StudyPeriodWithWeeksDto sp : studyPeriods) {
        	List<TimetableEvent> studyPeriodEvents = occurredEventsBySp.get(sp.getId());
            periodCapacityLessons.put(sp.getId(), new TeacherDetailLoad(
            		timetableTotalOccurredContactEvents(studyPeriodEvents),
            		timetableTotalOccurredKoefEvents(studyPeriodEvents),
            		capacityTimetableOccurredEvents(studyPeriodEvents, capacitites)));
        }
        return periodCapacityLessons;
    }

    private static Map<Long, TeacherDetailLoad> monthCapacityTimetableOccurredEvents(List<TimetableEvent> occurredEvents,
            Set<Long> months, List<Classifier> capacitites) {
        Map<Long, TeacherDetailLoad> periodCapacityLessons = new HashMap<>();

        Map<Long, List<TimetableEvent>> eventsByMonths = StreamUtil.nullSafeList(occurredEvents).stream()
                .collect(Collectors.groupingBy(e -> Long.valueOf(e.getDate().getMonthValue())));
        for (Long month : months) {
            List<TimetableEvent> monthEvents = eventsByMonths.get(month);
            periodCapacityLessons.put(month, new TeacherDetailLoad(
            		timetableTotalOccurredContactEvents(monthEvents),
            		timetableTotalOccurredKoefEvents(monthEvents),
            		capacityTimetableOccurredEvents(monthEvents, capacitites)));
        }
        return periodCapacityLessons;
    }

    private static Map<Long, TeacherDetailLoad> weekCapacityTimetableOccurredEvents(List<TimetableEvent> occurredEvents,
            List<WeekDto> weeks, List<Classifier> capacitites) {
        Map<Long, TeacherDetailLoad> periodCapacityLessons = new HashMap<>();

        Map<Short, List<TimetableEvent>> occurredEventsByWeek = occurredEventsGroupedByWeek(occurredEvents, weeks);
        for (Short weekNr : occurredEventsByWeek.keySet()) {
        	List<TimetableEvent> weekEvents = occurredEventsByWeek.get(weekNr);
            periodCapacityLessons.put(Long.valueOf(weekNr.longValue()), new TeacherDetailLoad(
            		timetableTotalOccurredContactEvents(weekEvents),
            		timetableTotalOccurredKoefEvents(weekEvents),
                    capacityTimetableOccurredEvents(weekEvents, capacitites)));
        }
        return periodCapacityLessons;
    }

    private static Map<Short, List<TimetableEvent>> occurredEventsGroupedByWeek(List<TimetableEvent> occurredEvents,
            List<WeekDto> weeks) {
        Map<Short, List<TimetableEvent>> groupedEvents = new HashMap<>();
        for (WeekDto week : weeks) {
            List<TimetableEvent> weekEvents = new ArrayList<>();
            for (TimetableEvent event : occurredEvents) {
                if (!event.getDate().isBefore(week.getWeekStart())
                        && !event.getDate().isAfter(week.getWeekEnd())) {
                    weekEvents.add(event);
                }
            }
            groupedEvents.put(week.getNr(), weekEvents);
        }
        return groupedEvents;
    }

    private static Map<String, Long> capacityTimetableOccurredEvents(List<? extends TeacherLoad> periodOccurredEvents,
            List<Classifier> capacitites) {
        Map<String, Long> capacityLessons = new HashMap<>();
        for (Classifier c : capacitites) {
            Long lessonsCount = lessonsCount(StreamUtil.nullSafeList(periodOccurredEvents).stream()
                    .filter(l -> c.getCode().equals(l.getCapacityType())));
            capacityLessons.put(c.getCode(), lessonsCount);
        }
        Long withoutCapacitySum = lessonsCount(StreamUtil.nullSafeList(periodOccurredEvents).stream()
                .filter(l -> l.getCapacityType() == null));
        capacityLessons.put(NO_CAPACITY_TYPE, withoutCapacitySum);
        return capacityLessons;
    }

    private static Long lessonsCount(Stream<? extends TeacherLoad> events) {
       return events.map(TeacherLoad::getLessons).reduce(Long::sum).orElse(0L);
    }
    
    private static Long contactLessonsCount(Stream<? extends TeacherLoad> events) {
        return events.filter(p -> Boolean.TRUE.equals(p.getIsContact())).map(TeacherLoad::getLessons).reduce(Long::sum).orElse(0L);
    }

    private static Long timetableTotalOccurredEvents(List<? extends TeacherLoad> occurredEvents) {
        return lessonsCount(StreamUtil.nullSafeList(occurredEvents).stream());
    }
    
    private static Long timetableTotalOccurredContactEvents(List<? extends TeacherLoad> occurredEvents) {
        return contactLessonsCount(StreamUtil.nullSafeList(occurredEvents).stream());
    }
    
    private static BigDecimal timetableTotalOccurredKoefEvents(List<? extends TeacherLoad> occurredEvents) {
        return koefLessonsCount(StreamUtil.nullSafeList(occurredEvents).stream());
    }

    private static Map<Long, TeacherDetailLoad> studyPeriodJournalOccurredLessons(List<JournalEntry> occurredLessons,
            List<StudyPeriodWithWeeksDto> studyPeriods) {
        Map<Long, TeacherDetailLoad> periodLessons = new HashMap<>();
        Map<Long, List<JournalEntry>> occurredEventsBySp = StreamUtil.nullSafeList(occurredLessons).stream()
                .collect(Collectors.groupingBy(JournalEntry::getStudyPeriodId));
        for (StudyPeriodWithWeeksDto sp : studyPeriods) {
            List<JournalEntry> spOccurredEvents = occurredEventsBySp.get(sp.getId());
            periodLessons.put(sp.getId(), new TeacherDetailLoad(
            		timetableTotalOccurredEvents(spOccurredEvents),
            		timetableTotalOccurredContactEvents(spOccurredEvents),
            		timetableTotalOccurredKoefEvents(spOccurredEvents)));
        }
        return periodLessons;
    }

    private static Map<Long, TeacherDetailLoad> monthJournalOccurredLessons(List<JournalEntry> occurredLessons, Set<Long> months) {
        Map<Long, TeacherDetailLoad> periodLessons = new HashMap<>();
        Map<Long, List<JournalEntry>> eventsByMonths = StreamUtil.nullSafeList(occurredLessons).stream()
                .collect(Collectors.groupingBy(e -> Long.valueOf(e.getDate().getMonthValue())));
        for (Long month : months) {
        	List<JournalEntry> occuredEvents = eventsByMonths.get(month);
            periodLessons.put(month, new TeacherDetailLoad(
            		timetableTotalOccurredEvents(occuredEvents),
            		timetableTotalOccurredContactEvents(occuredEvents),
            		timetableTotalOccurredKoefEvents(occuredEvents)));
        }
        return periodLessons;
    }

    private static Map<Long, TeacherDetailLoad> weekJournalOccurredLessons(List<JournalEntry> occurredLessons, List<WeekDto> weeks) {
        Map<Long, TeacherDetailLoad> periodLessons = new HashMap<>();

        Map<Short, List<JournalEntry>> occurredLessonsByWeek = occurredLessonsGroupedByWeek(occurredLessons, weeks);
        for (Short weekNr : occurredLessonsByWeek.keySet()) {
            List<JournalEntry> occuredEvents = occurredLessonsByWeek.get(weekNr);
            periodLessons.put(Long.valueOf(weekNr.longValue()), new TeacherDetailLoad(
            		timetableTotalOccurredEvents(occuredEvents),
            		timetableTotalOccurredContactEvents(occuredEvents),
            		timetableTotalOccurredKoefEvents(occuredEvents)));
        }
        return periodLessons;
    }

    private static Long journalTotalOccurredLessons(List<JournalEntry> occurredLessons) {
        return StreamUtil.nullSafeList(occurredLessons).stream()
                .filter(StreamUtil.distinctByKey(JournalEntry::getJournalEntryId))
                .map(JournalEntry::getLessons)
                .reduce(Long::sum).orElse(0L);
    }

    private static Map<Long, TeacherDetailLoad> studyPeriodCapacityJournalOccurredLessons(
            List<JournalEntry> occurredLessons, List<StudyPeriodWithWeeksDto> studyPeriods, List<Classifier> capacitites) {
        Map<Long, TeacherDetailLoad> periodCapacityLessons = new HashMap<>();
        for (StudyPeriodWithWeeksDto sp : studyPeriods) {
            List<JournalEntry> periodOccurredLessons = StreamUtil
                    .toFilteredList(l -> sp.getId().equals(l.getStudyPeriodId()), occurredLessons);
            periodCapacityLessons.put(sp.getId(), new TeacherDetailLoad(
            		timetableTotalOccurredContactEvents(periodOccurredLessons),
            		timetableTotalOccurredKoefEvents(periodOccurredLessons),
            		capacityJournalOccurredLessons(periodOccurredLessons, capacitites)));
        }
        return periodCapacityLessons;
    }

    private static Map<Long, TeacherDetailLoad> monthCapacityJournalOccurredLessons(List<JournalEntry> occurredLessons,
            Set<Long> months, List<Classifier> capacitites) {
        Map<Long, TeacherDetailLoad> periodCapacityLessons = new HashMap<>();

        Map<Long, List<JournalEntry>> lessonsByMonths = StreamUtil.nullSafeList(occurredLessons).stream()
                .collect(Collectors.groupingBy(l -> Long.valueOf(l.getDate().getMonthValue())));
        for (Long month : months) {
            List<JournalEntry> periodOccurredLessons = lessonsByMonths.get(month);
            periodCapacityLessons.put(month, new TeacherDetailLoad(
            		timetableTotalOccurredContactEvents(periodOccurredLessons),
            		timetableTotalOccurredKoefEvents(periodOccurredLessons),
            		capacityJournalOccurredLessons(periodOccurredLessons, capacitites)));
        }
        return periodCapacityLessons;
    }

    private static Map<Long, TeacherDetailLoad> weekCapacityJournalOccurredLessons(List<JournalEntry> occurredLessons,
            List<WeekDto> weeks, List<Classifier> capacitites) {
        Map<Long, TeacherDetailLoad> periodCapacityLessons = new HashMap<>();

        Map<Short, List<JournalEntry>> occurredLessonsByWeek = occurredLessonsGroupedByWeek(occurredLessons, weeks);
        for (Short weekNr : occurredLessonsByWeek.keySet()) {
        	List<JournalEntry> occuredLessons = occurredLessonsByWeek.get(weekNr);
            periodCapacityLessons.put(Long.valueOf(weekNr.longValue()), new TeacherDetailLoad(
            		timetableTotalOccurredContactEvents(occuredLessons),
            		timetableTotalOccurredKoefEvents(occuredLessons),
                    capacityJournalOccurredLessons(occuredLessons, capacitites)));
        }
        return periodCapacityLessons;
    }

    private static Map<Short, List<JournalEntry>> occurredLessonsGroupedByWeek(List<JournalEntry> occurredLessons,
            List<WeekDto> weeks) {
        Map<Short, List<JournalEntry>> groupedLessons = new HashMap<>();
        for (WeekDto week : weeks) {
            List<JournalEntry> weekLessons = new ArrayList<>();
            for (JournalEntry lesson : occurredLessons) {
                if (!lesson.getDate().isBefore(week.getWeekStart())
                        && !lesson.getDate().isAfter(week.getWeekEnd())) {
                    weekLessons.add(lesson);
                }
            }
            groupedLessons.put(week.getNr(), weekLessons);
        }
        return groupedLessons;
    }

    private static Map<String, Long> capacityJournalOccurredLessons(List<JournalEntry> periodOccurredLessons,
            List<Classifier> capacitites) {
        Map<String, Long> capacityLessons = new HashMap<>();
        for (Classifier c : capacitites) {
            Long lessonsCount = StreamUtil.nullSafeList(periodOccurredLessons).stream()
                    .filter(l -> c.getCode().equals(l.getCapacityType()))
                    .map(JournalEntry::getLessons)
                    .reduce(Long::sum).orElse(0L);
            capacityLessons.put(c.getCode(), lessonsCount);
        }
        Long withoutCapacitySum = StreamUtil.nullSafeList(periodOccurredLessons).stream()
                .filter(l -> l.getCapacityType() == null)
                .map(JournalEntry::getLessons)
                .reduce(Long::sum).orElse(0L);
        capacityLessons.put(NO_CAPACITY_TYPE, withoutCapacitySum);
        return capacityLessons;
    }

    public TeacherDetailLoadDto teacherDetailLoadJournals(Long schoolId, TeacherDetailLoadCommand criteria,
            Teacher teacher) {
        TeacherDetailLoadReportDataDto report = teacherDetailLoadReportData(criteria);

        Long teacherId = EntityUtil.getId(teacher);
        List<Long> teacherIds = Collections.singletonList(teacherId);
        List<Classifier> capacities = getTeacherCapacities(schoolId, criteria, teacherIds);
        List<PlannedLoad> plannedLoads = new ArrayList<>();
        if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
            plannedLoads = teacherVocationalPlannedLoads(criteria, teacherIds, report, schoolId).get(teacherId);
        }

        List<JournalEntry> journalOccurredLessons = teacherJournalOccurredLessons(schoolId, criteria, teacherIds)
                .get(teacherId);
        List<TimetableEvent> timetableOccurredLessons = new ArrayList<>();
        List<TimetableEvent> substitutedLessons = new ArrayList<>();
        if (Boolean.TRUE.equals(criteria.getShowTimetableLoad())) {
            timetableOccurredLessons = teacherTimetableOccurredLessons(schoolId, criteria, teacherIds).get(teacherId);
            substitutedLessons = teacherLessonsAsSubstitute(schoolId, criteria, teacherIds).get(teacherId);
        }

        Set<Long> journalIds = StreamUtil.toMappedSet(e -> e.getJournalId(), journalOccurredLessons);
        if (Boolean.TRUE.equals(criteria.getShowTimetableLoad())) {
            journalIds.addAll(StreamUtil.toMappedSet(e -> e.getJournalId(), timetableOccurredLessons));
            journalIds.addAll(StreamUtil.toMappedSet(e -> e.getJournalId(), substitutedLessons));
        }
        if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
            journalIds.addAll(StreamUtil.toMappedSet(p -> p.getJournalId(), plannedLoads));
        }
        List<TeacherDetailLoadJournalSubjectDto> journals = teacherJournals(journalIds, criteria);

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

            List<JournalEntry> journalOccurredLessonsOfJournal = StreamUtil
                    .toFilteredList(l -> journalId.equals(l.getJournalId()), journalOccurredLessons);
            setTeacherDetailLoadJournalOccurredLessons(journal, criteria, journalOccurredLessonsOfJournal, capacities,
                    report);

            if (Boolean.TRUE.equals(criteria.getShowTimetableLoad())) {
                List<TimetableEvent> timetableOccurredLessonsOfJournal = StreamUtil
                        .toFilteredList(l -> journalId.equals(l.getJournalId()), timetableOccurredLessons);
                setTeacherDetailLoadTimetableOccurredHours(journal, criteria, timetableOccurredLessonsOfJournal,
                        capacities, report);
            }

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

    private List<TeacherDetailLoadJournalSubjectDto> teacherJournals(Set<Long> journalIds, TeacherDetailLoadCommand criteria) {
        List<TeacherDetailLoadJournalSubjectDto> journals = new ArrayList<>();
        if (!journalIds.isEmpty()) {
            Map<Long, List<String>> studentGroups = journalStudentGroups(journalIds, criteria);

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

    private Map<Long, List<String>> journalStudentGroups(Set<Long> journalIds, TeacherDetailLoadCommand criteria) {
    	JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j"
    			+ " join journal_omodule_theme jot on jot.journal_id = j.id"
                + " join lesson_plan_module lpm on jot.lesson_plan_module_id = lpm.id"
                + " join lesson_plan lp on lpm.lesson_plan_id = lp.id"
                + " join student_group sg on lp.student_group_id = sg.id"
                + " left join curriculum_version cv on lp.curriculum_version_id = cv.id");
    	
        qb.requiredCriteria("j.id in (:journalIds)", "journalIds", journalIds);
        qb.optionalCriteria("sg.id in (:studentGroupIds)", "studentGroupIds", criteria.getStudentGroups());
        qb.optionalCriteria("cv.curriculum_id = :curriculumId", "curriculumId", criteria.getCurriculum());
        qb.sort("sg.code");
        
        List<?> data = qb.select("distinct j.id, sg.code", em).getResultList();
        
        return StreamUtil.nullSafeList(data).stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> resultAsString(r, 1), Collectors.toList())));
    }

    public TeacherDetailLoadDto teacherDetailLoadSubjects(Long schoolId, TeacherDetailLoadCommand criteria,
            Teacher teacher) {
        TeacherDetailLoadReportDataDto report = teacherDetailLoadReportData(criteria);

        Long teacherId = EntityUtil.getId(teacher);
        List<Long> teacherIds = Collections.singletonList(teacherId);
        List<Classifier> capacities = getTeacherCapacities(schoolId, criteria, teacherIds);
        List<PlannedLoad> plannedLoads = new ArrayList<>();
        if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
            plannedLoads = teacherHigherPlannedLoads(criteria, teacherIds, schoolId).get(teacherId);
        }
        List<TimetableEvent> occurredLessons = teacherTimetableOccurredLessons(schoolId, criteria, teacherIds)
                .get(teacherId);
        List<TimetableEvent> substitutedLessons = teacherLessonsAsSubstitute(schoolId, criteria, teacherIds)
                .get(teacherId);

        Set<Long> subjectStudyPeriodIds = StreamUtil.toMappedSet(e -> e.getSubjectStudyPeriodId(), occurredLessons);
        subjectStudyPeriodIds.addAll(StreamUtil.toMappedSet(e -> e.getSubjectStudyPeriodId(), substitutedLessons));
        if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
            subjectStudyPeriodIds.addAll(StreamUtil.toMappedSet(p -> p.getSubjectStudyPeriodId(), plannedLoads));
        }
        subjectStudyPeriodIds = subjectStudyPeriodIds.stream().filter(s -> s != null).collect(Collectors.toSet());
        List<TeacherDetailLoadJournalSubjectDto> subjectStudyPeriods = teacherSubjectStudyPeriods(subjectStudyPeriodIds, criteria);

        TeacherDetailLoadDto dto = new TeacherDetailLoadDto();
        String name = PersonUtil.fullname(teacher.getPerson());
        dto.setTeacher(new AutocompleteResult(teacherId, name, name));

        for (TeacherDetailLoadJournalSubjectDto subject : subjectStudyPeriods) {
            Long sspId = subject.getJournalSubject().getId();
            if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
                List<PlannedLoad> subjectPlannedLoads = StreamUtil
                        .toFilteredList(l -> sspId.equals(l.getSubjectStudyPeriodId()), plannedLoads);
                setTeacherDetailLoadPlannedHours(subject, criteria, subjectPlannedLoads, capacities, report);
            }
            List<TimetableEvent> subjectOccurredLessons = StreamUtil
                    .toFilteredList(l -> sspId.equals(l.getSubjectStudyPeriodId()), occurredLessons);
            setTeacherDetailLoadTimetableOccurredHours(subject, criteria, subjectOccurredLessons, capacities, report);

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

    private List<TeacherDetailLoadJournalSubjectDto> teacherSubjectStudyPeriods(Set<Long> subjectStudyPeriodIds, TeacherDetailLoadCommand criteria) {
        List<TeacherDetailLoadJournalSubjectDto> subjectStudyPeriods = new ArrayList<>();
        if (!subjectStudyPeriodIds.isEmpty()) {
            Map<Long, List<String>> studentGroups = studyPeriodStudentGroups(subjectStudyPeriodIds, criteria);

            List<?> data = em.createNativeQuery("select ssp.id, s.name_et, s.name_en, s.code, s.credits from subject_study_period ssp"
                    + " join subject s on s.id = ssp.subject_id"
                    + " where ssp.id in (:sspIds) order by s.name_et")
                    .setParameter("sspIds", subjectStudyPeriodIds).getResultList();
            subjectStudyPeriods = StreamUtil.toMappedList(r -> {
                Long sspId = resultAsLong(r, 0);
                TeacherDetailLoadJournalSubjectDto dto = new TeacherDetailLoadJournalSubjectDto();
                dto.setJournalSubject(new AutocompleteResult(sspId, resultAsString(r, 1), resultAsString(r, 2)));
                dto.setStudentGroups(studentGroups.get(sspId));
                dto.setSubjectCode(resultAsString(r, 3));
                dto.setCredits(resultAsDecimal(r, 4));
                return dto;
            }, data);
        }
        return subjectStudyPeriods;
    }

    private Map<Long, List<String>> studyPeriodStudentGroups(Set<Long> subjectStudyPeriodIds, TeacherDetailLoadCommand criteria) {
		JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period ssp"
		        + " join subject_study_period_student_group sspsg on sspsg.subject_study_period_id = ssp.id"
		        + " join student_group sg on sg.id = sspsg.student_group_id");
		
		qb.requiredCriteria("ssp.id in (:sspIds)", "sspIds", subjectStudyPeriodIds);
		qb.optionalCriteria("sg.id in (:studentGroupIds)", "studentGroupIds", criteria.getStudentGroups());
		qb.optionalCriteria("(exists"
                + "(select spsg.id " 
                + "from subject_study_period_student_group spsg "
                + "join student_group sg on spsg.student_group_id = sg.id "
                + "where sg.curriculum_id = :curriculumId "
                + "and spsg.subject_study_period_id = ssp.id) "
                + "or exists"
                + "(select c.id "
                + "from curriculum c "
                + "join curriculum_version cv on cv.curriculum_id = c.id "
                + "join curriculum_version_hmodule cvh on cvh.curriculum_version_id = cv.id "
                + "join curriculum_version_hmodule_subject cvhs on cvhs.curriculum_version_hmodule_id = cvh.id "
                + "where cvhs.subject_id = ssp.subject_id "
                + "and c.id = :curriculumId))", "curriculumId", criteria.getCurriculum());
		qb.sort("sg.code");
		
		List<?> data = qb.select("distinct ssp.id, sg.code", em).getResultList();
        return StreamUtil.nullSafeList(data).stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> resultAsString(r, 1), Collectors.toList())));
    }

    public byte[] teacherDetailLoadAsExcel(Long schoolId, TeacherDetailLoadCommand criteria) {
        List<TeacherDetailLoadDto> teachers = Boolean.TRUE.equals(criteria.getIsHigher())
                ? teacherHigherDetailLoad(schoolId, criteria, new PageRequest(0, Integer.MAX_VALUE)).getContent()
                : teacherVocationalDetailLoad(schoolId, criteria, new PageRequest(0, Integer.MAX_VALUE)).getContent();

        List<Classifier> capacities = Boolean.TRUE.equals(criteria.getByCapacities()) 
                ? excelTeacherCapacities(teachers) : new ArrayList<>();
        boolean entriesWithoutCapacity = Boolean.TRUE.equals(criteria.getByCapacities())
                && existsEntriesWithoutCapacity(teachers);
 
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
        return xlsService.generate("teachersdetailload" + ".xlsx", Collections.singletonMap("report", report), null, new XlsDynamicColumnCommand("Sheet1", 2));
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
    
    public byte[] teacherDetailLoadSubjectJournalAsExcel(Long schoolId, TeacherDetailLoadCommand criteria) {
    	TeacherDetailLoadSubjectJournalReport report = new TeacherDetailLoadSubjectJournalReport();
    	report.setIsHigher(criteria.getIsHigher());
        report.setIsHigherSchool(Boolean.valueOf(schoolService.schoolType(schoolId).isHigher()));
        report.setStudyYear(EntityUtil.getOptionalOne(StudyYear.class, criteria.getStudyYear(), em));
        report.setStudyPeriod(EntityUtil.getOptionalOne(StudyPeriod.class, criteria.getStudyPeriod(), em));
    	report.setTeachers(getTeachers(criteria, schoolId));
		return xlsService.generate("teachersdetailloadjournalsubject.xlsx", Collections.singletonMap("report", report));
	}

    private List<TeacherDetailLoadDto> getTeachers(TeacherDetailLoadCommand criteria, Long schoolId) {
    	Page<TeacherDetailLoadDto> teachers = teachers(schoolId, criteria, new PageRequest(0, Integer.MAX_VALUE, new Sort("p.lastname, p.firstname")));
    	List<Long> teacherIds = StreamUtil.toMappedList(t -> t.getTeacher().getId(), teachers.getContent());
		if (teacherIds.isEmpty()) return new ArrayList<>();
    	Map<Long, List<TeacherDetailLoadJournalSubjectDto>> teacherJournalSubjects = teachersWithSubjectOrJournal(schoolId, criteria, new PageRequest(0, Integer.MAX_VALUE));
    	for (TeacherDetailLoadDto teacher : teachers) {
    		List<TeacherDetailLoadJournalSubjectDto> journalSubjects = teacherJournalSubjects.get(teacher.getTeacher().getId());
    		if (journalSubjects != null) {
    			teacher.setJournalSubjects(journalSubjects);
    		}
    	}
		return teachers.getContent();
	}
    
	public byte[] teacherDetailLoadJournalSubjectsAsExcel(Long schoolId, TeacherDetailLoadCommand criteria,
            Teacher teacher) {
        TeacherDetailLoadDto teacherLoad = Boolean.TRUE.equals(criteria.getIsHigher())
                ? teacherDetailLoadSubjects(schoolId, criteria, teacher)
                : teacherDetailLoadJournals(schoolId, criteria, teacher);

        List<Classifier> capacities = Boolean.TRUE.equals(criteria.getByCapacities()) 
                ? excelTeacherCapacities(Collections.singletonList(teacherLoad)) : new ArrayList<>();
        boolean entriesWithoutCapacity = Boolean.TRUE.equals(criteria.getByCapacities())
                && existsEntriesWithoutCapacity(Collections.singletonList(teacherLoad));

        TeacherDetailLoadReport report = detailLoadExcelReportDto(schoolId, criteria, capacities,
                entriesWithoutCapacity);
        report.setTeacher(teacherLoad.getTeacher());
        List<ResultRowDto> resultRows = new ArrayList<>();
        for (TeacherDetailLoadJournalSubjectDto journalSubject : teacherLoad.getJournalSubjects()) {
            ResultRowDto row = getResultRow(criteria, journalSubject, report.getLoadTypesRow());
            row.setName(journalSubject.getJournalSubject());
            row.setStudentGroups(journalSubject.getStudentGroups() != null ?
                    String.join(", ", journalSubject.getStudentGroups()) : null);
            row.setSubjectCode(journalSubject.getSubjectCode());
            row.setEap(journalSubject.getCredits());
            resultRows.add(row);
        }
        report.setRows(resultRows);
        report.setJournalSubjectReport(Boolean.TRUE);
        if (Boolean.TRUE.equals(criteria.getIsHigher())) {
        	report.setTeacherReport(Boolean.TRUE);
        }
        return xlsService.generate("teachersdetailload" + ".xlsx", Collections.singletonMap("report", report), null, 
        		new XlsDynamicColumnCommand("Sheet1", Boolean.TRUE.equals(criteria.getIsHigher()) ? 4 : 2));
    }

    private TeacherDetailLoadReport detailLoadExcelReportDto(Long schoolId, TeacherDetailLoadCommand criteria,
            List<Classifier> capacities, boolean entriesWithoutCapacity) {
        TeacherDetailLoadReportDataDto reportData = teacherDetailLoadReportData(criteria);

        int studyColspan = capacities.size() > 0 ? capacities.size() + 2 : 2 + (Boolean.TRUE.equals(criteria.getByCapacities()) ? 0 : 1);
        int plannedLessonsColspan = Boolean.TRUE.equals(criteria.getShowPlannedLessons()) ? studyColspan : 0;

        int occurredJournalLessonsColspan = 0;
        if (Boolean.FALSE.equals(criteria.getIsHigher())) {
            occurredJournalLessonsColspan = studyColspan + (entriesWithoutCapacity ? 1 : 0);
        }

        int occurredTimetableLessonsColspan = 0;
        if (Boolean.TRUE.equals(criteria.getIsHigher()) || Boolean.TRUE.equals(criteria.getShowTimetableLoad())) {
            occurredTimetableLessonsColspan = 1 + studyColspan + (entriesWithoutCapacity ? 1 : 0)
                    + (Boolean.TRUE.equals(criteria.getShowSingleEvents()) ? 1 : 0);
        }

        int periodColspan = plannedLessonsColspan + occurredJournalLessonsColspan + occurredTimetableLessonsColspan;

        List<PeriodDto> periods = new ArrayList<>();
        List<Map<String, Object>> periodTypesRow = new ArrayList<>();
        List<LoadTypeDto> loadTypesRow = new ArrayList<>();

        if (Boolean.TRUE.equals(criteria.getByStudyPeriods())) {
            List<StudyPeriodWithWeeksDto> studyPeriods = new ArrayList<>();
            if (criteria.getStudyPeriod() != null) {
                studyPeriods.add(studyPeriodDtoById(criteria.getStudyPeriod(), reportData.getStudyPeriods()));
            } else {
                studyPeriods = reportData.getStudyPeriods();
            }

            for (StudyPeriodWithWeeksDto dto : studyPeriods) {
                PeriodDto period = new PeriodDto();
                period.setStudyPeriod(Boolean.TRUE);
                period.setName(dto.getNameEt() + " (" + DateUtils.shortDayMonth(dto.getStartDate()) + "-" + DateUtils.shortDayMonth(dto.getEndDate()) + ")");
                period.setColspan(Long.valueOf(periodColspan));
                periods.add(period);

                addPeriodTypes(criteria, periodTypesRow, plannedLessonsColspan, occurredJournalLessonsColspan,
                        occurredTimetableLessonsColspan, false);
                addLoadTypes(criteria, dto.getId(), loadTypesRow, capacities, entriesWithoutCapacity, false);
            }
        } else if (Boolean.TRUE.equals(criteria.getByWeeks())) {
            List<Short> periodWeekNrs = new ArrayList<>();
            List<Long> weekNrs = new ArrayList<>();
            if (criteria.getStudyPeriod() != null) {
                StudyPeriodWithWeeksDto studyPeriod = studyPeriodDtoById(criteria.getStudyPeriod(),
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

                addPeriodTypes(criteria, periodTypesRow, plannedLessonsColspan, occurredJournalLessonsColspan,
                        occurredTimetableLessonsColspan, false);
                addLoadTypes(criteria, weekNr, loadTypesRow, capacities, entriesWithoutCapacity, false);
            }
        } else if (Boolean.TRUE.equals(criteria.getByMonths())) {
            for (Long monthValue : reportData.getMonths()) {
                PeriodDto period = new PeriodDto();
                period.setMonth(Boolean.TRUE);
                period.setNr(monthValue);
                period.setColspan(Long.valueOf(periodColspan));
                periods.add(period);

                addPeriodTypes(criteria, periodTypesRow, plannedLessonsColspan, occurredJournalLessonsColspan,
                        occurredTimetableLessonsColspan, false);
                addLoadTypes(criteria, monthValue, loadTypesRow, capacities, entriesWithoutCapacity, false);
            }
        }

        // Total columns
        PeriodDto period = new PeriodDto();
        period.setTotal(Boolean.TRUE);
        period.setColspan(Long.valueOf(periodColspan + 1));
        periods.add(period);
        addPeriodTypes(criteria, periodTypesRow, plannedLessonsColspan, occurredJournalLessonsColspan,
                occurredTimetableLessonsColspan, true);
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

    private static StudyPeriodWithWeeksDto studyPeriodDtoById(Long id, List<StudyPeriodWithWeeksDto> studyPeriods) {
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
            int plannedLessonsColspan, int occurredJournalLessonsColspan, int occurredTimetableLessonsColspan,
            boolean total) {
        if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
            Map<String, Object> plannedPeriodType = new HashMap<>();
            plannedPeriodType.put("name", "query.teachersdetailload.planned");
            plannedPeriodType.put("colspan", Long.valueOf(plannedLessonsColspan));
            periodTypesRow.add(plannedPeriodType);
        }

        if (Boolean.FALSE.equals(criteria.getIsHigher())) {
            Map<String, Object> occurredJournalPeriodType = new HashMap<>();
            occurredJournalPeriodType.put("name", "query.teachersdetailload.occurred");
            if (total && Boolean.TRUE.equals(criteria.getByCapacities())) {
                occurredJournalLessonsColspan++;
            }
            occurredJournalPeriodType.put("colspan", Long.valueOf(occurredJournalLessonsColspan));
            periodTypesRow.add(occurredJournalPeriodType);
        }

        if (Boolean.TRUE.equals(criteria.getIsHigher()) || Boolean.TRUE.equals(criteria.getShowTimetableLoad())) {
            Map<String, Object> occurredTimetablePeriodType = new HashMap<>();
            String name = Boolean.TRUE.equals(criteria.getShowTimetableLoad())
                    ? "query.teachersdetailload.timetableOccurred"
                    : "query.teachersdetailload.occurred";
            occurredTimetablePeriodType.put("name", name);
            if (total) {
                occurredTimetableLessonsColspan++;
            }
            occurredTimetablePeriodType.put("colspan", Long.valueOf(occurredTimetableLessonsColspan));
            periodTypesRow.add(occurredTimetablePeriodType);
        }
    }
    
    private static LoadTypeDto createCapacityLesson(Long periodIndex, Classifier capacity) {
    	LoadTypeDto lesson = new LoadTypeDto();
    	lesson.setPeriodIndex(periodIndex);
    	lesson.setIsCapacity(Boolean.TRUE);
    	lesson.setCapacityCode(capacity.getCode());
    	lesson.setCapacityValue(capacity.getValue());
        return lesson;
    }

    private static void addLoadTypes(TeacherDetailLoadCommand criteria, Long periodIndex,
            List<LoadTypeDto> loadTypesRow, List<Classifier> capacities, boolean entriesWithoutCapacity,
            boolean total) {
        if (Boolean.TRUE.equals(criteria.getByCapacities())) {
            if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
                for (Classifier capacity : capacities) {
                    LoadTypeDto plannedLessons = createCapacityLesson(periodIndex, capacity);
                    plannedLessons.setPlannedLessons(Boolean.TRUE);
                    loadTypesRow.add(plannedLessons);
                }
                addKoefAndContactLessonHeader(loadTypesRow, periodIndex, true, false, false);
            }

            if (Boolean.FALSE.equals(criteria.getIsHigher())) {
                for (Classifier capacity : capacities) {
                    LoadTypeDto occurredLesson = createCapacityLesson(periodIndex, capacity);
                    occurredLesson.setJournalOccurredLessons(Boolean.TRUE);
                    loadTypesRow.add(occurredLesson);
                }

                if (entriesWithoutCapacity) {
                    LoadTypeDto plannedLessons = new LoadTypeDto();
                    plannedLessons.setJournalOccurredLessons(Boolean.TRUE);
                    plannedLessons.setPeriodIndex(periodIndex);
                    plannedLessons.setIsCapacity(Boolean.TRUE);
                    plannedLessons.setCapacityCode(NO_CAPACITY_TYPE);
                    plannedLessons.setCapacityValue(NO_CAPACITY_TYPE);
                    loadTypesRow.add(plannedLessons);
                }
                addKoefAndContactLessonHeader(loadTypesRow, periodIndex, false, true, false);
                if (total) {
                    LoadTypeDto journalGrandTotal = new LoadTypeDto();
                    journalGrandTotal.setJournalGrandTotal(Boolean.TRUE);
                    journalGrandTotal.setName("query.teachersdetailload.grandTotal");
                    loadTypesRow.add(journalGrandTotal);
                }
            }

            if (Boolean.TRUE.equals(criteria.getIsHigher()) || Boolean.TRUE.equals(criteria.getShowTimetableLoad())) {
                for (Classifier capacity : capacities) {
                    LoadTypeDto occurredLesson = createCapacityLesson(periodIndex, capacity);
                    occurredLesson.setTimetableOccurredLessons(Boolean.TRUE);
                    loadTypesRow.add(occurredLesson);
                }

                if (entriesWithoutCapacity) {
                    LoadTypeDto plannedLessons = new LoadTypeDto();
                    plannedLessons.setTimetableOccurredLessons(Boolean.TRUE);
                    plannedLessons.setPeriodIndex(periodIndex);
                    plannedLessons.setIsCapacity(Boolean.TRUE);
                    plannedLessons.setCapacityCode(NO_CAPACITY_TYPE);
                    plannedLessons.setCapacityValue(NO_CAPACITY_TYPE);
                    loadTypesRow.add(plannedLessons);
                }
                addKoefAndContactLessonHeader(loadTypesRow, periodIndex, false, false, true);
            }
        } else {
            if (Boolean.TRUE.equals(criteria.getShowPlannedLessons())) {
                LoadTypeDto plannedLessonsStudy = new LoadTypeDto();
                plannedLessonsStudy.setPlannedLessons(Boolean.TRUE);
                plannedLessonsStudy.setPeriodIndex(periodIndex);
                plannedLessonsStudy.setName("query.teachersdetailload.study");
                loadTypesRow.add(plannedLessonsStudy);
                addKoefAndContactLessonHeader(loadTypesRow, periodIndex, true, false, false);
            }

            if (Boolean.FALSE.equals(criteria.getIsHigher())) {
                LoadTypeDto journalOccurredStudy = new LoadTypeDto();
                journalOccurredStudy.setJournalOccurredLessons(Boolean.TRUE);
                journalOccurredStudy.setPeriodIndex(periodIndex);
                journalOccurredStudy.setName("query.teachersdetailload.study");
                loadTypesRow.add(journalOccurredStudy);
                addKoefAndContactLessonHeader(loadTypesRow, periodIndex, false, true, false);
            }

            if (Boolean.TRUE.equals(criteria.getIsHigher()) || Boolean.TRUE.equals(criteria.getShowTimetableLoad())) {
                LoadTypeDto timetableOccurredStudy = new LoadTypeDto();
                timetableOccurredStudy.setTimetableOccurredLessons(Boolean.TRUE);
                timetableOccurredStudy.setPeriodIndex(periodIndex);
                timetableOccurredStudy.setName("query.teachersdetailload.study");
                loadTypesRow.add(timetableOccurredStudy);
                addKoefAndContactLessonHeader(loadTypesRow, periodIndex, false, false, true);
            }
        }

        if (Boolean.TRUE.equals(criteria.getIsHigher()) || Boolean.TRUE.equals(criteria.getShowTimetableLoad())) {
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
                LoadTypeDto timetableGrandTotal = new LoadTypeDto();
                timetableGrandTotal.setTimetableGrandTotal(Boolean.TRUE);
                String name = Boolean.TRUE.equals(criteria.getShowTimetableLoad())
                        ? "query.teachersdetailload.timetableGrandTotal"
                        : "query.teachersdetailload.grandTotal";
                timetableGrandTotal.setName(name);
                loadTypesRow.add(timetableGrandTotal);
            }
        }

    }

    private static void addKoefAndContactLessonHeader(List<LoadTypeDto> loadTypesRow, Long periodIndex, 
    		boolean plannedLesson, boolean journalOccuredLesson, boolean timetableOccuredLesson) {
        LoadTypeDto contact = new LoadTypeDto();
        contact.setPlannedLessons(Boolean.valueOf(plannedLesson));
        contact.setJournalOccurredLessons(Boolean.valueOf(journalOccuredLesson));
        contact.setTimetableOccurredLessons(Boolean.valueOf(timetableOccuredLesson));
        contact.setContactLesson(Boolean.TRUE);
        contact.setPeriodIndex(periodIndex);
        contact.setName("query.teachersdetailload.contactLesson");
        loadTypesRow.add(contact);
        LoadTypeDto koef = new LoadTypeDto();
        koef.setPlannedLessons(Boolean.valueOf(plannedLesson));
        koef.setJournalOccurredLessons(Boolean.valueOf(journalOccuredLesson));
        koef.setTimetableOccurredLessons(Boolean.valueOf(timetableOccuredLesson));
    	koef.setKoefLesson(Boolean.TRUE);
    	koef.setPeriodIndex(periodIndex);
    	koef.setName("query.teachersdetailload.koefLesson");
        loadTypesRow.add(koef);
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
                	addLessonContactKoefToRow(data.getPlannedHours(), type, row);
                } else if (Boolean.TRUE.equals(type.getJournalOccurredLessons())) {
                	addLessonContactKoefToRow(data.getJournalOccurredLessons(), type, row);
                } else if (Boolean.TRUE.equals(type.getTimetableOccurredLessons())) {
                	addLessonContactKoefToRow(data.getTimetableOccurredLessons(), type, row);
                } else if (Boolean.TRUE.equals(type.getSubstitutableEvents())) {
                    row.getLoads().add(getLessonValueAtIndex(data.getSubstitutedLessons(), type.getPeriodIndex()));
                } else if (Boolean.TRUE.equals(type.getSingleEvents())) {
                    row.getLoads().add(getLessonValueAtIndex(data.getSingleEvents(), type.getPeriodIndex()));
                }
            } else {
                if (Boolean.TRUE.equals(type.getPlannedLessons())) {
                	if (Boolean.TRUE.equals(type.getContactLesson()) && !Boolean.TRUE.equals(type.getKoefLesson())) {
                		row.getLoads().add(data.getTotalPlannedHours());
                	}
                    if (Boolean.TRUE.equals(type.getContactLesson())) row.getLoads().add(data.getTotalPlannedContactHours());
                    if (Boolean.TRUE.equals(type.getKoefLesson())) row.getLoads().add(data.getTotalPlannedKoefHours());
                } else if (Boolean.TRUE.equals(type.getJournalOccurredLessons())) {
                	if (Boolean.TRUE.equals(type.getContactLesson()) && !Boolean.TRUE.equals(type.getKoefLesson())) {
                		row.getLoads().add(data.getJournalTotalOccurredLessons());
                	}
                    if (Boolean.TRUE.equals(type.getContactLesson())) row.getLoads().add(data.getJournalTotalOccurredContactLessons());
                    if (Boolean.TRUE.equals(type.getKoefLesson())) row.getLoads().add(data.getJournalTotalOccurredKoefLessons());
                } else if (Boolean.TRUE.equals(type.getTimetableOccurredLessons())) {
                	if (Boolean.TRUE.equals(type.getContactLesson()) && !Boolean.TRUE.equals(type.getKoefLesson())) {
                		row.getLoads().add(data.getTimetableTotalOccurredLessons());
                	}
                    if (Boolean.TRUE.equals(type.getContactLesson())) row.getLoads().add(data.getTimetableTotalOccurredContactLessons());
                    if (Boolean.TRUE.equals(type.getKoefLesson())) row.getLoads().add(data.getTimetableTotalOccurredKoefLessons());
                } else if (Boolean.TRUE.equals(type.getSubstitutableEvents())) {
                    row.getLoads().add(data.getTotalSubstitutedLessons());
                } else if (Boolean.TRUE.equals(type.getSingleEvents())) {
                    row.getLoads().add(data.getTotalSingleEvents());
                } else if (Boolean.TRUE.equals(type.getTimetableGrandTotal())) {
                    row.getLoads().add(grandTimetableTotal(data));
                }
            }
        }
    }
    
    private static Long getCapacityValueAtIndex(Map<Long, TeacherDetailLoad> mappedValues, Long periodIndex, String capacityType) {
    	TeacherDetailLoad load = mappedValues.get(periodIndex);
    	if (load != null) {
    		Map<String, Long> capacities = load.getCapacity();
    		if (capacities != null) {
    			return capacities.get(capacityType);
    		}
    	}
		return null;
    }
    
    private static Long getLessonValueAtIndex(Map<Long, TeacherDetailLoad> mappedValues, Long periodIndex) {
    	TeacherDetailLoad load = mappedValues.get(periodIndex);
    	if (load != null) {
    		return load.getLesson();
    	}
		return null;
    }
    
    private static Long getContactValueAtIndex(Map<Long, TeacherDetailLoad> mappedValues, Long periodIndex) {
    	TeacherDetailLoad load = mappedValues.get(periodIndex);
    	if (load != null) {
    		return load.getContactLesson();
    	}
		return null;
    }
    
    private static BigDecimal getKoefValueAtIndex(Map<Long, TeacherDetailLoad> mappedValues, Long periodIndex) {
    	TeacherDetailLoad load = mappedValues.get(periodIndex);
    	if (load != null) {
    		return load.getKoefLesson();
    	}
		return null;
    }
    
    private static void addLessonContactKoefToRow(Map<Long, TeacherDetailLoad> map, LoadTypeDto type, ResultRowDto row) {
    	if (!Boolean.TRUE.equals(type.getContactLesson()) && !Boolean.TRUE.equals(type.getKoefLesson())) {
    		row.getLoads().add(getLessonValueAtIndex(map, type.getPeriodIndex()));
    	}
        if (Boolean.TRUE.equals(type.getContactLesson())) row.getLoads().add(getContactValueAtIndex(map, type.getPeriodIndex()));
        if (Boolean.TRUE.equals(type.getKoefLesson())) row.getLoads().add(getKoefValueAtIndex(map, type.getPeriodIndex()));
    }
    
    private static void addCapacityContactKoefToRow(Map<Long, TeacherDetailLoad> map, LoadTypeDto type, ResultRowDto row) {
    	if (!Boolean.TRUE.equals(type.getContactLesson()) && !Boolean.TRUE.equals(type.getKoefLesson())) {
    		row.getLoads().add(getCapacityValueAtIndex(map, type.getPeriodIndex(), type.getCapacityCode()));
    	}
        if (Boolean.TRUE.equals(type.getContactLesson())) row.getLoads().add(getContactValueAtIndex(map, type.getPeriodIndex()));
        if (Boolean.TRUE.equals(type.getKoefLesson())) row.getLoads().add(getKoefValueAtIndex(map, type.getPeriodIndex()));
    }
    
    private static void addTotalToRow(Map<String, Long> map, LoadTypeDto type, ResultRowDto row) {
        if (!Boolean.TRUE.equals(type.getContactLesson()) && !Boolean.TRUE.equals(type.getKoefLesson())) {
        	row.getLoads().add(map != null ? map.get(type.getCapacityCode()) : null);
        }
    }

    private static void setPeriodCapacityLoads(PeriodDetailLoadDto data, List<LoadTypeDto> loadTypesRow, ResultRowDto row) {
        for (LoadTypeDto type : loadTypesRow) {
            if (type.getPeriodIndex() != null) {
                if (Boolean.TRUE.equals(type.getPlannedLessons())) {
                	addCapacityContactKoefToRow(data.getCapacityPlannedHours(), type, row);
                } else if (Boolean.TRUE.equals(type.getJournalOccurredLessons())) {
                	addCapacityContactKoefToRow(data.getCapacityJournalOccurredLessons(), type, row);
                } else if (Boolean.TRUE.equals(type.getTimetableOccurredLessons())) {
                	addCapacityContactKoefToRow(data.getCapacityTimetableOccurredLessons(), type, row);
                } else if (Boolean.TRUE.equals(type.getSubstitutableEvents())) {
                	row.getLoads().add(getLessonValueAtIndex(data.getSubstitutedLessons(), type.getPeriodIndex()));
                } else if (Boolean.TRUE.equals(type.getSingleEvents())) {
                	row.getLoads().add(getLessonValueAtIndex(data.getSingleEvents(), type.getPeriodIndex()));
                }
            } else {
                if (Boolean.TRUE.equals(type.getPlannedLessons())) {
                	addTotalToRow(data.getTotalCapacityPlannedHours(), type, row);
                    if (Boolean.TRUE.equals(type.getContactLesson())) row.getLoads().add(data.getTotalPlannedContactHours());
                    if (Boolean.TRUE.equals(type.getKoefLesson())) row.getLoads().add(data.getTotalPlannedKoefHours());
                } else if (Boolean.TRUE.equals(type.getJournalOccurredLessons())) {
                	addTotalToRow(data.getJournalTotalCapacityOccurredLessons(), type, row);
                    if (Boolean.TRUE.equals(type.getContactLesson())) row.getLoads().add(data.getJournalTotalOccurredContactLessons());
                    if (Boolean.TRUE.equals(type.getKoefLesson())) row.getLoads().add(data.getJournalTotalOccurredKoefLessons());
                } else if (Boolean.TRUE.equals(type.getTimetableOccurredLessons())) {
                	addTotalToRow(data.getTimetableTotalCapacityOccurredLessons(), type, row);
                    if (Boolean.TRUE.equals(type.getContactLesson())) row.getLoads().add(data.getTimetableTotalOccurredContactLessons());
                    if (Boolean.TRUE.equals(type.getKoefLesson())) row.getLoads().add(data.getTimetableTotalOccurredKoefLessons());
                } else if (Boolean.TRUE.equals(type.getSubstitutableEvents())) {
                    row.getLoads().add(data.getTotalSubstitutedLessons());
                } else if (Boolean.TRUE.equals(type.getSingleEvents())) {
                    row.getLoads().add(data.getTotalSingleEvents());
                } else if (Boolean.TRUE.equals(type.getTimetableGrandTotal())) {
                    row.getLoads().add(grandTimetableTotal(data));
                } else if (Boolean.TRUE.equals(type.getJournalGrandTotal())) {
                    row.getLoads().add(data.getJournalTotalOccurredLessons());
                }
            }
        }
    }

    private static Long grandTimetableTotal(PeriodDetailLoadDto data) {
        long grandTotal = (data.getTimetableTotalOccurredLessons() != null ? data.getTimetableTotalOccurredLessons().longValue() : 0) +
                (data.getTotalSubstitutedLessons() != null ? data.getTotalSubstitutedLessons().longValue() : 0) +
                (data.getTotalSingleEvents() != null ? data.getTotalSingleEvents().longValue() : 0);
        return Long.valueOf(grandTotal);
    }

    private static class TimetableEvent extends TeacherLoad {
        private final Long journalId;
        private final Long subjectStudyPeriodId;

        public TimetableEvent(String capacityType, LocalDate eventStart, Long lessons, Long studyPeriodId,
                Long journalId, Long subjectStudyPeriodId, Boolean isContact, BigDecimal koef) {
        	super(capacityType, lessons, isContact, koef, studyPeriodId, eventStart);
            this.journalId = journalId;
            this.subjectStudyPeriodId = subjectStudyPeriodId;
        }

        public Long getJournalId() {
            return journalId;
        }

        public Long getSubjectStudyPeriodId() {
            return subjectStudyPeriodId;
        }
    }

    private static class JournalEntry extends TeacherLoad {
        private final Long journalId;
        private final Long journalEntryId;

        public JournalEntry(String capacityType, LocalDate entryDate, Long lessons, Long studyPeriodId, Long journalId,
                Long journalEntryId, Boolean isContact, BigDecimal koef) {
        	super(capacityType, lessons, isContact, koef, studyPeriodId, entryDate);
            this.journalId = journalId;
            this.journalEntryId = journalEntryId;
        }

        public Long getJournalId() {
            return journalId;
        }

        public Long getJournalEntryId() {
            return journalEntryId;
        }

    }
    
    public static class TeacherDetailLoad {
    	private final Long lesson;
        private final Long contactLesson;
        private final BigDecimal koefLesson;
        private final Map<String, Long> capacity;
        
        public TeacherDetailLoad(Long lesson, Long contactLesson, BigDecimal koefLesson) {
        	this.lesson = lesson;
        	this.contactLesson = contactLesson;
        	this.koefLesson = koefLesson;
        	this.capacity = null;
        }
        
        public TeacherDetailLoad(Long contactLesson, BigDecimal koefLesson, Map<String, Long> capacity) {
        	this.lesson = null;
        	this.contactLesson = contactLesson;
        	this.koefLesson = koefLesson;
        	this.capacity = capacity;
        }
        
		public Long getLesson() {
			return lesson;
		}
		public Long getContactLesson() {
			return contactLesson;
		}
		public BigDecimal getKoefLesson() {
			return koefLesson;
		}

		public Map<String, Long> getCapacity() {
			return capacity;
		}
    }

    private static class PlannedLoad extends TeacherLoad {
        private final Short weekNr;
        private final Long journalId;
        private final Long subjectStudyPeriodId;

        public PlannedLoad(String capacityType, Short weekNr, Long hours, Long journalId, Long studyPeriodId,
                Long subjectStudyPeriodId, Boolean isContact, BigDecimal koef) {
        	super(capacityType, hours, isContact, koef, studyPeriodId, null);
            this.weekNr = weekNr;
            this.journalId = journalId;
            this.subjectStudyPeriodId = subjectStudyPeriodId;
        }

        public Short getWeekNr() {
            return weekNr;
        }

        public Long getJournalId() {
            return journalId;
        }

        public Long getSubjectStudyPeriodId() {
            return subjectStudyPeriodId;
        }
    }
}
