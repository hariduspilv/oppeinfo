package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ee.hitsa.ois.domain.StudyPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.timetable.Timetable;
import ee.hitsa.ois.enums.Day;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.timetable.LessonTimeDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByDto;
import ee.hitsa.ois.web.dto.timetable.TimetableCalendarDto;
import ee.hitsa.ois.web.dto.timetable.TimetableDifferenceExcelDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchGroupDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchRoomDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchTeacherDto;
import ee.hitsa.ois.web.dto.timetable.TimetablePlanExcelCell;
import ee.hitsa.ois.web.dto.timetable.TimetablePlanExcelEventDto;
import ee.hitsa.ois.web.dto.timetable.TimetableStudentGroupDto;

@Transactional
@Service
public class TimetableGenerationService {

    private static final String ICAL_BEGIN = "BEGIN:VCALENDAR\r\nVERSION: 2.0\r\nPRODID:-//HOIS//NONSGML v2.0//UTF8\r\n";
    private static final String ICAL_END = "END:VCALENDAR";
    private static final String TIMEZONE = "BEGIN:VTIMEZONE\r\n"
            + "TZID:Europe/Tallinn\r\n" 
            + "X-LIC-LOCATION:Europe/Tallinn\r\n" 
            + "BEGIN:DAYLIGHT\r\n" 
            + "TZOFFSETFROM:+0200\r\n" 
            + "TZOFFSETTO:+0300\r\n" 
            + "TZNAME:EEST\r\n" 
            + "DTSTART:19700329T030000\r\n"
            + "RRULE:FREQ=YEARLY;BYMONTH=3;BYDAY=-1SU\r\n" 
            + "END:DAYLIGHT\r\n" 
            + "BEGIN:STANDARD\r\n"
            + "TZOFFSETFROM:+0300\r\n" 
            + "TZOFFSETTO:+0200\r\n" 
            + "TZNAME:EET\r\n" 
            + "DTSTART:19701025T040000\r\n"
            + "RRULE:FREQ=YEARLY;BYMONTH=10;BYDAY=-1SU\r\n" 
            + "END:STANDARD\r\n" 
            + "END:VTIMEZONE\r\n";
    private static final String EVENT_START = "BEGIN:VEVENT\r\n";
    private static final String EVENT_END = "END:VEVENT\r\n";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");

    private static final EnumMap<Day, Function<LessonTimeDto, Boolean>> DAY_MAPPING = new EnumMap<>(Day.class);
    static {
        DAY_MAPPING.put(Day.NADALAPAEV_E, LessonTimeDto::getDayMon);
        DAY_MAPPING.put(Day.NADALAPAEV_T, LessonTimeDto::getDayTue);
        DAY_MAPPING.put(Day.NADALAPAEV_K, LessonTimeDto::getDayWed);
        DAY_MAPPING.put(Day.NADALAPAEV_N, LessonTimeDto::getDayThu);
        DAY_MAPPING.put(Day.NADALAPAEV_R, LessonTimeDto::getDayFri);
        DAY_MAPPING.put(Day.NADALAPAEV_L, LessonTimeDto::getDaySat);
        DAY_MAPPING.put(Day.NADALAPAEV_P, LessonTimeDto::getDaySun);
    }

    @Autowired
    private TimetableService timetableService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private EntityManager em;
    @Autowired
    private XlsService xlsService;

    /** Generate iCalendar for given timetable
     * @param groupTimetable
     * @param lang
     * @return calendar filename and iCalendar format calendar as a string
     */
    public TimetableCalendarDto getICal(TimetableByDto timetable, Language lang) {
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
        String iCalContent = timetable != null ? getICalContent(timetable.getTimetableEvents(), lang) : null;
        return new TimetableCalendarDto(fileName, iCalContent);
    }

    /**
     * Generate iCalendar for given search result
     * @param studentTimetable
     * @param lang
     * @return calendar filename and iCalendar format calendar as a string
     */
    public TimetableCalendarDto getICal(List<TimetableEventSearchDto> searchResult, Language lang) {
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
        String iCalContent = getICalContent(searchResult, lang);
        return new TimetableCalendarDto(fileName, iCalContent);
    }

    private static String getICalContent(List<TimetableEventSearchDto> events, Language lang) {
        String calEvents = getICalEvents(events, lang);
        return ICAL_BEGIN + TIMEZONE + calEvents + ICAL_END;
    }

    private static String getICalEvents(List<TimetableEventSearchDto> events, Language lang) {
        String iCalEvents = "";
        for (TimetableEventSearchDto event : events) {
            iCalEvents += getICalEvent(event, lang);
        }
        return iCalEvents;
    }

    private static String getICalEvent(TimetableEventSearchDto event, Language lang) {
        String uid = "UID:" + getEventUID(event.getId()) + "\r\n";
        String dtStart = "DTSTART:" + getEventTime(event.getDate(), event.getTimeStart()) + "\r\n";
        String dtEnd = "DTEND:" + getEventTime(event.getDate(), event.getTimeEnd()) + "\r\n";
        String eventName = getEventName(event, lang);
        String summary = "SUMMARY:" + eventName + "\r\n";
        String description = "DESCRIPTION:" + getEventDescription(event, lang) + "\r\n";
        String location = "LOCATION:" + getEventLocation(event.getRooms(), lang) + "\r\n";

        return EVENT_START + uid + dtStart + dtEnd + summary + description + location + EVENT_END;
    }

    private static String getEventUID(Long eventId) {
        LocalDateTime currentTime = LocalDateTime.now();
        String timeString = currentTime.format(FORMATTER);

        return eventId.toString() + ";" + timeString + "@hois";
    }

    private static String getEventTime(LocalDate date, LocalTime time) {
        LocalDateTime eventTime = LocalDateTime.of(date, time);
        return eventTime.format(FORMATTER);
    }

    private static String getEventName(TimetableEventSearchDto event, Language lang) {
        if (Boolean.TRUE.equals(event.getPublicEvent())) {
            return Language.ET.equals(lang) ? event.getNameEt() : event.getNameEn();
        }
        return TranslateUtil.translate("timetable.occupied", lang);
    }

    private static String getEventDescription(TimetableEventSearchDto event, Language lang) {
        List<String> descriptionParts = new ArrayList<>();
        descriptionParts.add(getPersonalEventPerson(event.getPerson(), lang));
        descriptionParts.add(getEventTeachers(event.getTeachers(), lang));
        descriptionParts.add(getEventStudentGroups(event.getStudentGroups(), lang));

        return descriptionParts.stream().filter(p -> p != null).collect(Collectors.joining("\\n"));
    }

    private static String getPersonalEventPerson(AutocompleteResult person, Language lang) {
        String personalEventPerson = null;
        if (person != null) {
            personalEventPerson = TranslateUtil.translate("timetable.person", lang) + ": ";
            personalEventPerson += person.getNameEt();
        }
        return personalEventPerson;
    }

    private static String getEventTeachers(List<TimetableEventSearchTeacherDto> teachers, Language lang) {
        String eventTeachers = null;
        if (teachers != null && teachers.size() > 0) {
            eventTeachers = TranslateUtil.translate("timetable.teachers", lang) + ": ";
            eventTeachers += teachers.stream().map(t -> t.getName()).collect(Collectors.joining(" "));
        }
        return eventTeachers;
    }
    
    private static String getEventStudentGroups(List<TimetableEventSearchGroupDto> groups, Language lang) {
        String eventGroups = null;
        if (groups != null && groups.size() > 0) {
            eventGroups = TranslateUtil.translate("timetable.groups", lang) + ": ";
            eventGroups += groups.stream().map(g -> g.getCode()).collect(Collectors.joining(" "));
        }
        return eventGroups;
    }

    private static String getEventLocation(List<TimetableEventSearchRoomDto> rooms, Language lang) {
        String eventRooms = "";
        if (rooms != null && rooms.size() > 0) {
            eventRooms = TranslateUtil.translate("timetable.rooms", lang) + ": ";
            eventRooms += rooms.stream().map(r -> r.getBuildingCode() + "-" + r.getRoomCode())
                    .collect(Collectors.joining(" "));
        }
        return eventRooms;
    }

    /**
     * Timetable plan generation for excel formats
     */
    public byte[] timetablePlanExcel(Long timetableId) {
        Map<String, Object> data = new HashMap<>();
        Timetable timetable = em.getReference(Timetable.class, timetableId);

        if (Boolean.TRUE.equals(timetable.getIsHigher())) {
            data.putAll(getSheetDataHigher(timetable));
            return xlsService.generate("timetableplanhigher.xlsx", data);
        }
        data.putAll(getSheetData(timetable));
        return xlsService.generate("timetableplan.xlsx", data);
    }

    public Map<String, Object> getSheetDataHigher(Timetable timetable) {
        List<TimetablePlanExcelEventDto> timetablePlanEvents = timetablePlanEvents(timetable);

        Map<String, Object> data = new HashMap<>();
        data.put("timetableStart", timetable.getStartDate());
        data.put("timetableEnd", timetable.getEndDate());

        Map<LocalDate, List<TimetablePlanExcelEventDto>> eventsByDate = new LinkedHashMap<>();

        LocalDate date = timetable.getStartDate();
        while (!date.isAfter(timetable.getEndDate())) {
            LocalDate day = date;
            List<TimetablePlanExcelEventDto> dayEvents = StreamUtil
                    .toFilteredList(e -> day.equals(e.getStart().toLocalDate()), timetablePlanEvents);
            if (!dayEvents.isEmpty()) {
                timetablePlanEvents.removeAll(dayEvents);
                eventsByDate.put(day, dayEvents);
            }
            date = date.plusDays(1);
        }
        data.put("days", eventsByDate);

        return data;
    }

    private List<TimetablePlanExcelEventDto> timetablePlanEvents(Timetable timetable) {
        List<?> data = em.createNativeQuery("select tet.start, tet.end, s.code, s.name_et, s.name_en, "
                + "string_agg(distinct sg.code, ', ' order by sg.code) student_groups, "
                + "string_agg(b.code || '-' || r.code, ', ' order by b.code, r.code) rooms, "
                + "string_agg(p.firstname || ' ' || p.lastname, ', ' order by p.lastname, p.firstname) teachers, "
                + "te.capacity_type_code from timetable t "
                + "join timetable_object tobj on tobj.timetable_id = t.id "
                + "join timetable_event te on te.timetable_object_id = tobj.id "
                + "join timetable_event_time tet on tet.timetable_event_id = te.id "
                + "join subject_study_period ssp on ssp.id = tobj.subject_study_period_id "
                + "join subject s on s.id = ssp.subject_id "
                + "left join timetable_object_student_group tosg on tosg.timetable_object_id = tobj.id "
                + "left join student_group sg on sg.id = tosg.student_group_id "
                + "left join timetable_event_room ter on ter.timetable_event_time_id = tet.id "
                + "left join room r on r.id = ter.room_id left join building b on b.id = r.building_id "
                + "left join timetable_event_teacher tete on tete.timetable_event_time_id = tet.id "
                + "left join teacher tea on tea.id = tete.teacher_id left join person p on p.id = tea.person_id "
                + "where t.id = :timetableId group by s.id, te.id, tet.id order by tet.start, tet.end")
                .setParameter("timetableId", timetable.getId())
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            TimetablePlanExcelEventDto dto = new TimetablePlanExcelEventDto();
            dto.setStart(resultAsLocalDateTime(r, 0));
            dto.setEnd(resultAsLocalDateTime(r, 1));

            String subjectCode = resultAsString(r, 2);
            dto.setName(new AutocompleteResult(null, SubjectUtil.subjectName(subjectCode, resultAsString(r, 3)),
                    SubjectUtil.subjectName(subjectCode, resultAsString(r, 4))));

            dto.setStudentGroups(resultAsString(r, 5));
            dto.setRooms(resultAsString(r, 6));
            dto.setTeachers(resultAsString(r, 7));
            dto.setCapacityType(resultAsString(r, 8));
            return dto;
        }, data);
    }

    public byte[] timetableDifferenceExcel(StudyPeriod studyPeriod, LocalDate startDate) {
        Long schoolId = EntityUtil.getId(studyPeriod.getStudyYear().getSchool());
        Map<String, Object> data = new HashMap<>();

        Integer currWeekNr = studyPeriod.getWeekNrForDate(startDate);
        if (currWeekNr != null) {
            LocalDate prevWeekStart = startDate.minusDays(7);
            StudyPeriod prevWeekStudyPeriod = findPreviousWeekStudyPeriod(schoolId, prevWeekStart);
            Integer prevWeekNr = prevWeekStudyPeriod != null ? prevWeekStudyPeriod.getWeekNrForDate(prevWeekStart) : null;
            data.put("journals", getTimetableDifferenceForExcel(studyPeriod, currWeekNr, prevWeekStudyPeriod, prevWeekNr));
        }
        data.put("isHigherSchool", Boolean.valueOf(schoolService.schoolType(schoolId).isHigher()));
        return xlsService.generate("timetabledifference.xls", data);
    }

    private StudyPeriod findPreviousWeekStudyPeriod(Long schoolId, LocalDate previousWeekStart) {
        List<StudyPeriod> data = em.createQuery("select sp from StudyPeriod sp where sp.studyYear.school.id = ?1"
                + " and sp.startDate <= ?2 and sp.endDate >= ?2", StudyPeriod.class)
                .setParameter(1, schoolId)
                .setParameter(2, previousWeekStart)
                .setMaxResults(1).getResultList();
        return data.isEmpty() ? null : data.get(0);
    }

    private List<LessonTimeExcel> getLessonTimesForExcel(Timetable timetable) {
        List<LessonTimeDto> lessonTimes = timetableService.getLessonTimesForPlanning(timetable);
        List<LessonTimeExcel> result = new ArrayList<>();
        for (LessonTimeDto dto : lessonTimes) {
            for (Entry<Day, Function<LessonTimeDto, Boolean>> entry : DAY_MAPPING.entrySet()) {
                if (Boolean.TRUE.equals(entry.getValue().apply(dto))) {
                    result.add(new LessonTimeExcel(entry.getKey(), dto.getLessonNr()));
                }
            }
        }
        return result.stream()
                .sorted(Comparator.comparing(LessonTimeExcel::getDay).thenComparing(LessonTimeExcel::getLessonNr))
                .collect(Collectors.toList());
    }

    private static class LessonTimeExcel {
        private final Day day;
        private final Short lessonNr;

        public LessonTimeExcel(Day day, Short lessonNr) {
            this.day = day;
            this.lessonNr = lessonNr;
        }

        public Day getDay() {
            return day;
        }

        public Short getLessonNr() {
            return lessonNr;
        }
    }

    private Map<Long, String> getTeachersIntoMapById(Set<Long> teachers) {
        if (!teachers.isEmpty()) {
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                    "from teacher t" + " inner join person p on p.id = t.person_id");
            
            qb.requiredCriteria("t.id in (:teacherIds)", "teacherIds", teachers);
            
            List<?> data = qb.select("t.id, p.firstname, p.lastname", em).getResultList();
            
            return StreamUtil.toMap(r -> resultAsLong(r, 0),
                    r -> PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)), data);
        }
        return new HashMap<>();
    }

    private Map<String, Object> getSheetData(Timetable timetable) {
        List<TimetableEventDto> events = timetableService.getPlannedLessonsForVocationalTimetable(timetable);

        List<LessonTimeExcel> lessonTimesForExcel = getLessonTimesForExcel(timetable);
        
        
        /*TODO: Find a better way to not have duplicate keys. Ignore lessontimes that are valid but are not the latest?
        Map<String, LessonTimeExcel> lessonTimesMapped = StreamUtil
                .toMap(lt -> lt.getDay().getDayOfWeek().name() + lt.getLessonNr(), lessonTimesForExcel);
                */
        Map<String, LessonTimeExcel> lessonTimesMapped = new HashMap<>();
        for (LessonTimeExcel lt : lessonTimesForExcel) {
            lessonTimesMapped.put(lt.getDay().getDayOfWeek().name() + lt.getLessonNr(), lt);
        }

        Set<TimetableStudentGroupDto> studentGroups = new HashSet<>(timetableService.getVocationalStudentGroups(timetable));
        Map<Long, TimetablePlanExcelCell> lessonsByGroups = StreamUtil.toMap(dto -> dto.getId(),
                dto -> new TimetablePlanExcelCell(null, dto.getCode()), studentGroups);

        Set<Long> teachers = events.stream().flatMap(e -> e.getTeachers().stream()).collect(Collectors.toSet());
        Map<Long, String> teachersByIds = getTeachersIntoMapById(teachers);
        Map<Long, TimetablePlanExcelCell> lessonsByTeachers = StreamUtil.toMap(id -> id,
                id -> new TimetablePlanExcelCell(id, teachersByIds.get(id)), teachers);

        Set<AutocompleteResult> rooms = events.stream().flatMap(e -> e.getRooms().stream())
                .collect(Collectors.toSet());
        Map<Long, TimetablePlanExcelCell> lessonsByRooms = StreamUtil.toMap(room -> room.getId(),
                room -> new TimetablePlanExcelCell(room.getId(), room.getNameEt()), rooms);

        for (TimetableEventDto event : events) {
            int index = lessonTimesForExcel
                    .indexOf(lessonTimesMapped.get(event.getStart().getDayOfWeek().name() + event.getLessonNr()));

            TimetablePlanExcelCell groupLessons = lessonsByGroups.get(event.getStudentGroup());
            // events include lessons by studentgroups which lessonplans are not usable, lessonsByGroups do not include those groups
            if (groupLessons == null) {
                continue;
            }
            
            addEmptyBlocks(groupLessons.getDisplayValues(), index);
            String groupResult = event.getSubjectName();
            
            if (!event.getRooms().isEmpty()) {
                for (AutocompleteResult room : event.getRooms()) {
                    TimetablePlanExcelCell roomLessons = lessonsByRooms.get(room.getId());
                    addEmptyBlocks(roomLessons.getDisplayValues(), index);
                    if (index == roomLessons.getDisplayValues().size() - 1) {
                        continue;
                    }
                    roomLessons.getDisplayValues().add(new TimetablePlanExcelCell(event.getId(), event.getSubjectName()));
                }
                groupResult += "; " + event.getRooms().stream().map(r -> r.getNameEt()).collect(Collectors.joining(", "));
            }
            if (!event.getTeachers().isEmpty()) {
                for (Long teacherId : event.getTeachers()) {
                    TimetablePlanExcelCell teacherLessons = lessonsByTeachers.get(teacherId);
                    addEmptyBlocks(teacherLessons.getDisplayValues(), index);
                    if (index == teacherLessons.getDisplayValues().size() - 1) {
                        continue;
                    }
                    teacherLessons.getDisplayValues().add(new TimetablePlanExcelCell(teacherId, event.getSubjectName()));
                }
                groupResult += "; "
                        + event.getTeachers().stream().map(t -> teachersByIds.get(t)).collect(Collectors.joining(", "));
            }
            
            if (!groupLessons.getDisplayValues().isEmpty() && index == groupLessons.getDisplayValues().size() - 1) {
                TimetablePlanExcelCell previous = groupLessons.getDisplayValues().get(index);
                String previousString = previous.getName();
                previousString += "\n" + groupResult;
                previous.setName(previousString);
                groupLessons.getDisplayValues().set(index, previous);
            } else {
                groupLessons.getDisplayValues().add(new TimetablePlanExcelCell(event.getJournal(), groupResult));
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("lessonsByGroups", lessonsByGroups.values().stream().sorted(new Comparator<TimetablePlanExcelCell>() {

            @Override
            public int compare(TimetablePlanExcelCell o1, TimetablePlanExcelCell o2) {
                return o1.getName().compareTo(o2.getName());
            }
        }).collect(Collectors.toList()));
        result.put("lessonsByTeachers", lessonsByTeachers.values());
        result.put("lessonsByRooms", lessonsByRooms.values());
        result.put("lessonTimes",
                StreamUtil.toMappedList(l -> l.getDay().getDisplay() + l.getLessonNr(), lessonTimesForExcel));
        return result;
    }

    private static void addEmptyBlocks(List<TimetablePlanExcelCell> list, int indexOfLesson) {
        while (list.size() < indexOfLesson) {
            list.add(new TimetablePlanExcelCell(null, ""));
        }
    }

    private List<TimetableDifferenceExcelDto> getTimetableDifferenceForExcel(StudyPeriod currStudyPeriod,
            Integer currWeekNr, StudyPeriod prevStudyPeriod, Integer prevWeekNr) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j"
                + " join (select distinct ot.lesson_plan_module_id, journal_id from journal_omodule_theme ot) jot on jot.journal_id = j.id"
                + " join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
                + " join lesson_plan lp on lp.id = lpm.lesson_plan_id"
                + " join student_group sg on sg.id = lp.student_group_id"
                + " left join journal_capacity jc on jc.journal_id = j.id and (j.is_capacity_diff is null or j.is_capacity_diff = false)"
                + " left join journal_capacity_type jct on jct.id = jc.journal_capacity_type_id"
                + " left join journal_teacher jt on jt.journal_id = j.id and j.is_capacity_diff = true"
                + " left join journal_teacher_capacity jtc on jtc.journal_teacher_id = jt.id"
                + " left join journal_capacity_type jct2 on jct2.id = jtc.journal_capacity_type_id"
                + " left join classifier cl on cl.code = coalesce(jct2.capacity_type_code, jct.capacity_type_code)");

        String filter = "(coalesce(jtc.study_period_id, jc.study_period_id) = :currPeriod"
                + " and coalesce(jtc.week_nr, jc.week_nr) = :currWeekNr)";
        qb.parameter("currPeriod", EntityUtil.getId(currStudyPeriod));
        qb.parameter("currWeekNr", currWeekNr);
        if (prevWeekNr != null) {
            filter += " or (coalesce(jtc.study_period_id, jc.study_period_id) = :prevPeriod"
                    + " and coalesce(jtc.week_nr, jc.week_nr) = :prevWeekNr)";
            qb.parameter("prevPeriod", EntityUtil.getId(prevStudyPeriod));
            qb.parameter("prevWeekNr", prevWeekNr);
        }
        qb.filter(filter);

        qb.groupBy(" j.id, jt.id, jtc.id, jc.id, cl.code");
        qb.sort("j.name_et");
        String select = "j.id journal_id, j.name_et, string_agg(sg.code, ', ' order by sg.code), jt.id jt_id,"
                + " cl.code capacity_type_code, coalesce(jtc.hours, jc.hours) hours,"
                + " coalesce(jtc.week_nr, jc.week_nr) week_nr";
        List<?> data = qb.select(select, em).getResultList();
        return new ArrayList<>(mapDifferenceExcelDtos(data, currWeekNr, prevWeekNr).values());
    }

    private Map<String, TimetableDifferenceExcelDto> mapDifferenceExcelDtos(List<?> data, Integer currWeekNr,
            Integer prevWeekNr) {
        Set<Long> journalIds = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);
        Map<Long, Map<Long, String>> teachers = !journalIds.isEmpty() ? getTeachersForJournals(journalIds) : new HashMap<>();

        // key = journalId + _ + journalTeacherId + _ + capacityType
        Map<String, TimetableDifferenceExcelDto> resultMap = new LinkedHashMap<>();
        for (Object r : data) {
            Long journalId = resultAsLong(r, 0);
            String journalName = resultAsString(r, 1);
            String studentGroups = resultAsString(r, 2);
            Long journalTeacherId = resultAsLong(r, 3);
            String capacityType = resultAsString(r, 4);
            Long hours = resultAsLong(r, 5);
            Integer weekNr = resultAsInteger(r, 6);

            String key = journalId + "_" + journalTeacherId + "_" + capacityType;
            TimetableDifferenceExcelDto result = resultMap.get(key);
            if (result == null) {
                result = new TimetableDifferenceExcelDto(journalId, journalName, studentGroups, capacityType);
                Map<Long, String> journalTeachers = teachers.containsKey(journalId) ? teachers.get(journalId) : new HashMap<>();
                result.setTeacherNames(journalTeacherId != null ?
                        journalTeachers.get(journalTeacherId) : String.join(", ", journalTeachers.values()));
            }
            if (weekNr == currWeekNr) result.setCurrentWeek(hours);
            if (weekNr == prevWeekNr) result.setPreviousWeek(hours);
            result.setDifference(Long.valueOf(result.getCurrentWeek().longValue() - result.getPreviousWeek().longValue()));
            resultMap.put(key, result);
        }
        return resultMap;
    }

    private Map<Long, Map<Long, String>> getTeachersForJournals(Set<Long> journalIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j"
                + " join journal_teacher jt on jt.journal_id = j.id"
                + " join teacher t on t.id = jt.teacher_id"
                + " join person p on p.id = t.person_id");

        qb.requiredCriteria("j.id in (:journalIds)", "journalIds", journalIds);
        qb.sort("p.lastname, p.firstname");

        List<?> data = qb.select("j.id journal_id, jt.id jt_id, p.firstname, p.lastname", em).getResultList();
        return StreamUtil.nullSafeList(data).stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors
                .toMap(r -> resultAsLong(r, 1), r -> PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3)),
                        (v1, v2) -> v1, LinkedHashMap::new)));
    }
}
