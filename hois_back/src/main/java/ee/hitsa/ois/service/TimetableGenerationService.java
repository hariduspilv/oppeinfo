package ee.hitsa.ois.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.web.dto.timetable.TimetableByDto;
import ee.hitsa.ois.web.dto.timetable.TimetableCalendarDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchGroupDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchRoomDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchTeacherDto;

@Transactional
@Service
public class TimetableGenerationService {
    
    private static final String ICalBegin = "BEGIN:VCALENDAR\r\nVERSION: 2.0\r\nPRODID:-//HOIS//NONSGML v2.0//UTF8\r\n";
    private static final String ICalEnd = "END:VCALENDAR";
    private static final String eventStart = "BEGIN:VEVENT\r\n";
    private static final String eventEnd = "END:VEVENT\r\n";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
    
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
            for (TimetableEventSearchRoomDto room : rooms) {
                eventRooms += room.getBuildingCode() + "-" + room.getRoomCode() + " ";
            }            
        }
        return eventRooms;
    }
}
