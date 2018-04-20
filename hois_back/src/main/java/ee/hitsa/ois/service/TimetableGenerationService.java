package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.timetable.Timetable;
import ee.hitsa.ois.enums.Day;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
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
import ee.hitsa.ois.web.dto.timetable.TimetableStudentGroupDto;

@Transactional
@Service
public class TimetableGenerationService {

    private static final String ICalBegin = "BEGIN:VCALENDAR\r\nVERSION: 2.0\r\nPRODID:-//HOIS//NONSGML v2.0//UTF8\r\n";
    private static final String ICalEnd = "END:VCALENDAR";
    private static final String eventStart = "BEGIN:VEVENT\r\n";
    private static final String eventEnd = "END:VEVENT\r\n";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");

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
        String iCalContent = getICalContent(timetable.getTimetableEvents(), lang);
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
        String timezones = getICalTimezones();
        String calEvents = getICalEvents(events, lang);
        return ICalBegin + timezones + calEvents + ICalEnd;
    }

    private static String getICalTimezones() {
        String summerTz = "BEGIN:VTIMEZONE\r\n" + 
                "TZID:EST-Estonia-suvi\r\n" + 
                "LAST-MODIFIED:19870101T000000Z\r\n" + 
                "BEGIN:STANDARD\r\n" + 
                "DTSTART:19911026T020000\r\n" + 
                "RDATE:19911026T020000\r\n" + 
                "TZOFFSETFROM:+0200\r\n" + 
                "TZOFFSETTO:+0300\r\n" + 
                "TZNAME:EDT\r\n" + 
                "END:STANDARD\r\n" + 
                "BEGIN:DAYLIGHT\r\n" + 
                "DTSTART:19911026T020000\r\n" + 
                "RDATE:19910406T020000\r\n" + 
                "TZOFFSETFROM:+0200\r\n" + 
                "TZOFFSETTO:+0300\r\n" + 
                "TZNAME:EDT\r\n" + 
                "END:DAYLIGHT\r\n" + 
                "END:VTIMEZONE\r\n";
        String winterTz = "BEGIN:VTIMEZONE\r\n" + 
                "TZID:EST-Estonia-talv\r\n" + 
                "LAST-MODIFIED:19870101T000000Z\r\n" + 
                "BEGIN:STANDARD\r\n" + 
                "DTSTART:19911026T020000\r\n" + 
                "RDATE:19911026T020000\r\n" + 
                "TZOFFSETFROM:+0200\r\n" + 
                "TZOFFSETTO:+0200\r\n" + 
                "TZNAME:EDT\r\n" + 
                "END:STANDARD\r\n" + 
                "BEGIN:DAYLIGHT\r\n" + 
                "DTSTART:19911026T020000\r\n" + 
                "RDATE:19910406T020000\r\n" + 
                "TZOFFSETFROM:+0200\r\n" + 
                "TZOFFSETTO:+0200\r\n" + 
                "TZNAME:EDT\r\n" + 
                "END:DAYLIGHT\r\n" + 
                "END:VTIMEZONE\r\n";
        return summerTz + winterTz;
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
        String eventName = Language.ET.equals(lang) ? event.getNameEt() : event.getNameEn();
        String summary = "SUMMARY:" + eventName + "\r\n";
        String description = "DESCRIPTION:" + getEventTeachers(event.getTeachers(), lang)
                + getEventStudentGroups(event.getStudentGroups(), lang) + "\r\n";
        String location = "LOCATION:" + getEventLocation(event.getRooms(), lang) + "\r\n";

        return eventStart + uid + dtStart + dtEnd + summary + description + location + eventEnd;
    }

    private static String getEventUID(Long eventId) {
        LocalDateTime currentTime = LocalDateTime.now();
        String timeString = currentTime.format(formatter);

        return eventId.toString() + ";" + timeString + "@hois";
    }

    private static String getEventTime(LocalDate date, LocalTime time) {
        LocalDateTime eventTime = LocalDateTime.of(date, time);
        return eventTime.format(formatter);
    }

    private static String getEventTeachers(List<TimetableEventSearchTeacherDto> teachers, Language lang) {
        String eventTeachers = "";
        if (teachers != null) {
            eventTeachers = Language.ET.equals(lang) ? "Õpetajad: " : "Teachers: ";
            // TODO use Collectors.joining(" ")
            for (TimetableEventSearchTeacherDto teacher : teachers) {
                eventTeachers += teacher.getName() + " ";
            }
        }
        return eventTeachers;
    }
    
    private static String getEventStudentGroups(List<TimetableEventSearchGroupDto> groups, Language lang) {
        String eventGroups = "";
        if (groups != null) {
            eventGroups = Language.ET.equals(lang) ? "\\nRühmad: " : "\\nGroups: ";
            // TODO use Collectors.joining(" ")
            for (TimetableEventSearchGroupDto group : groups) {
                eventGroups += group.getCode() + " ";
            }
        }
        return eventGroups;
    }

    private static String getEventLocation(List<TimetableEventSearchRoomDto> rooms, Language lang) {
        String eventRooms = "";
        if (rooms != null) {
            eventRooms = Language.ET.equals(lang) ? "Ruumid: " : "Rooms: ";
            // TODO use Collectors.joining(" ")
            for (TimetableEventSearchRoomDto room : rooms) {
                eventRooms += room.getBuildingCode() + "-" + room.getRoomCode() + " ";
            }
        }
        return eventRooms;
    }

    /**
     * Timetable plan generation for excel formats
     */
    public byte[] timetablePlanExcel(Long timetableId) {
        Map<String, Object> data = new HashMap<>();
        Timetable timetable = em.getReference(Timetable.class, timetableId);
        data.putAll(getSheetData(timetable));
        return xlsService.generate("timetableplan.xlsx", data);
    }

    public byte[] timetableDifferenceExcel(Long timetableId) {
        Map<String, Object> data = new HashMap<>();
        Timetable currTimetable = em.getReference(Timetable.class, timetableId);
        Long prevTimetable = findPreviousTimetable(currTimetable);
        if (prevTimetable != null) {
            List<TimetableDifferenceExcelDto> difference = getTimetableDifferenceForExcel(
                    Arrays.asList(prevTimetable, EntityUtil.getId(currTimetable)), EntityUtil.getId(currTimetable),
                    prevTimetable);
            data.put("journals", difference);
        }
        return xlsService.generate("timetabledifference.xls", data);
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
        result.put("lessonsByGroups", lessonsByGroups.values());
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

    private Long findPreviousTimetable(Timetable timetable) {
        LocalDate previousStart = timetable.getStartDate().with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable t");
        qb.requiredCriteria("t.start_date = :startDate", "startDate", previousStart);
        qb.optionalCriteria("t.is_higher = :isHigher", "isHigher", timetable.getIsHigher());
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", EntityUtil.getId(timetable.getSchool()));
        List<?> data = qb.select("t.id", em).setMaxResults(1).getResultList();
        return data.isEmpty() ? null : resultAsLong(data.get(0), 0);
        // List<Long> prevTimetables = StreamUtil.toMappedList(r ->
        // resultAsLong(r, 0), data);
        // return prevTimetables.isEmpty() ? null : prevTimetables.get(0);
    }

    private List<TimetableDifferenceExcelDto> getTimetableDifferenceForExcel(List<Long> timetableIds,
            Long currTimetableId, Long prevTimetabelId) {
        String from = "from timetable t inner join timetable_object too on too.timetable_id = t.id"
                + " inner join timetable_event te on te.timetable_object_id = too.id"
                + " inner join timetable_object_student_group tosg on tosg.timetable_object_id = too.id"
                + " inner join journal j on j.id = too.journal_id"
                + " inner join student_group sg on sg.id = tosg.student_group_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("t.id in (:timetableIds)", "timetableIds", timetableIds);

        String groupBy = "t.id, j.id, j.name_et, sg.id, sg.code, te.capacity_type_code";
        // different string because of multiple id columns
        String select = "t.id as timetable_id, j.id as journal_id, j.name_et, sg.id as student_group_id, sg.code, te.capacity_type_code, count(te.capacity_type_code)";
        qb.groupBy(groupBy);
        List<?> data = qb.select(select, em).getResultList();
        // this and last week lesson counts by their ids
        Map<Long, List<TimetableDifferenceExcelDto>> differencesByWeeks = data.stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(
                        r -> new TimetableDifferenceExcelDto(resultAsLong(r, 1), resultAsString(r, 2),
                                resultAsLong(r, 3), resultAsString(r, 4), resultAsString(r, 5), resultAsLong(r, 6)),
                        Collectors.toList())));
        List<TimetableDifferenceExcelDto> allDifferences = differencesByWeeks.get(currTimetableId);
        allDifferences.addAll(differencesByWeeks.get(prevTimetabelId));
        Map<Long, List<String>> teachersByJournals = getTeachersForJournals(
                StreamUtil.toMappedSet(TimetableDifferenceExcelDto::getJournalId, allDifferences));

        // key = journalId + _ + studentGroupId + _ + capacityType
        Map<String, TimetableDifferenceExcelDto> resultMap = new HashMap<>();
        for (TimetableDifferenceExcelDto curr : differencesByWeeks.get(currTimetableId)) {
            curr.setTeacherNames(String.join(", ", teachersByJournals.get(curr.getJournalId())));
            resultMap.put(curr.getJournalId() + "_" + curr.getStudentGroupId() + "_" + curr.getCapacityType(), curr);
        }
        if (differencesByWeeks.get(prevTimetabelId) != null) {
            for (TimetableDifferenceExcelDto prev : differencesByWeeks.get(prevTimetabelId)) {
                String currentKey = prev.getJournalId() + "_" + prev.getStudentGroupId() + "_" + prev.getCapacityType();
                TimetableDifferenceExcelDto resultRow = resultMap.get(currentKey);
                if (resultRow == null) {
                    prev.setPreviousWeek(prev.getCurrentWeek());
                    prev.setDifference(Long.valueOf(0 - prev.getCurrentWeek().longValue()));
                    prev.setCurrentWeek(Long.valueOf(0));
                    prev.setTeacherNames(String.join(", ", teachersByJournals.get(prev.getJournalId())));
                    resultMap.put(currentKey, prev);
                } else {
                    resultRow.setPreviousWeek(prev.getCurrentWeek());
                    resultRow.setDifference(Long
                            .valueOf(resultRow.getPreviousWeek().longValue() - resultRow.getCurrentWeek().longValue()));
                }
            }
        }

        return resultMap.values().stream()
                .sorted(Comparator.comparing(TimetableDifferenceExcelDto::getJournalName)
                        .thenComparing(TimetableDifferenceExcelDto::getStudentGroup)
                        .thenComparing(TimetableDifferenceExcelDto::getCapacityType))
                .collect(Collectors.toList());
    }

    private Map<Long, List<String>> getTeachersForJournals(Set<Long> journalIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j"
                + " inner join journal_teacher jt on jt.journal_id = j.id"
                + " inner join teacher t on t.id = jt.teacher_id" + " inner join person p on p.id = t.person_id");

        qb.requiredCriteria("j.id in (:journalIds)", "journalIds", journalIds);

        List<?> data = qb.select("j.id, p.firstname, p.lastname", em).getResultList();
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors
                .mapping(r -> PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)), Collectors.toList())));
    }
}
