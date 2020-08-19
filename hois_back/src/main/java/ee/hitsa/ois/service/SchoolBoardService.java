package ee.hitsa.ois.service;

import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.TimetableStatus;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.util.CryptoUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventRoomsCommand;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.SchoolWithLogo;
import ee.hitsa.ois.web.dto.timetable.GroupTimetableDto;
import ee.hitsa.ois.web.dto.timetable.RoomTimetableDto;
import ee.hitsa.ois.web.dto.timetable.TeacherTimetableDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByGroupDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByRoomDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByTeacherDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventRoomSearchDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchDto;
import ee.hitsa.ois.web.dto.timetable.TimetableStudyYearWeekDto;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;

@Service
@Transactional
public class SchoolBoardService {

    @Autowired
    private SchoolService schoolService;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private TimetableService timetableService;
    @Autowired
    private TimetableEventService timetableEventService;
    @Autowired
    private EntityManager em;

    @Value("${hois.frontend.baseUrl}")
    private String frontendBaseUrl;
    @Value("${timetable.cypher.key}")
    private String encryptionKey;

    public SchoolWithLogo school(String schoolIdentifier) {
        School school = schoolByIdentifier(schoolIdentifier);
        SchoolWithLogo dto = new SchoolWithLogo(school);
        dto.setLogo(school.getLogo() != null ? school.getLogo().getFdata() : null);

        SchoolService.SchoolType type = schoolService.schoolType(dto.getId());
        dto.setVocational(Boolean.valueOf(type.isVocational()));
        dto.setHigher(Boolean.valueOf(type.isHigher()));
        dto.setDoctoral(Boolean.valueOf(type.isDoctoral()));
        return dto;
    }

    public List<GroupTimetableDto> groupTimetables(String schoolIdentifier) {
        School school = schoolByIdentifier(schoolIdentifier);

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event_time tet"
            + " join timetable_event te on te.id = tet.timetable_event_id"
            + " left join timetable_event_student_group tesg on tesg.timetable_event_time_id = tet.id and te.timetable_object_id is null"
            + " left join (timetable_object_student_group tosg"
                + " join timetable_object tobj on tosg.timetable_object_id = tobj.id"
                + " join timetable tt on tobj.timetable_id = tt.id) "
                + " on te.timetable_object_id = tobj.id and tt.status_code in (:timetableStatus)"
            + "join student_group sg on sg.id = coalesce(tesg.student_group_id, tosg.student_group_id)");
        qb.requiredCriteria("sg.school_id = :schoolId", "schoolId", school.getId());
        qb.parameter("timetableStatus", TimetableStatus.TUNNIPLAAN_STAATUS_P.name());
        setCurrentWeekEventsCriteria(qb);

        qb.sort("code");
        List<?> data = qb.select("distinct sg.id, sg.code", em).getResultList();
        return StreamUtil.toMappedList(r -> new GroupTimetableDto((Object[]) r), data);
    }

    public TimetableByGroupDto groupTimetableForWeek(String schoolIdentifier, TimetableEventSearchCommand criteria) {
        School school = schoolByIdentifier(schoolIdentifier);
        criteria.setSchoolBoard(Boolean.TRUE);
        return timetableEventService.groupTimetable(criteria, school, false);
    }

    public List<TeacherTimetableDto> teacherTimetables(String schoolIdentifier) {
        School school = schoolByIdentifier(schoolIdentifier);

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event_time tet"
            + " join timetable_event te on te.id = tet.timetable_event_id"
            + " join timetable_event_teacher teta on tet.id = teta.timetable_event_time_id"
            + " left join (timetable_object tobj join timetable tt on tt.id = tobj.timetable_id) on tobj.id = te.timetable_object_id"
            + " join teacher t on t.id = teta.teacher_id"
            + " join person p on p.id = t.person_id");
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", school.getId());
        qb.requiredCriteria("(tt.id is null or tt.status_code in (:timetableStatus))", "timetableStatus",
                TimetableStatus.TUNNIPLAAN_STAATUS_P);
        setCurrentWeekEventsCriteria(qb);

        qb.sort("p.lastname, p.firstname");
        List<?> data = qb.select("distinct t.id, p.firstname, p.lastname", em).getResultList();
        return StreamUtil.toMappedList(r -> new TeacherTimetableDto((Object[]) r), data);
    }

    public TimetableByTeacherDto teacherTimetableForWeek(String schoolIdentifier, TimetableEventSearchCommand criteria) {
        School school = schoolByIdentifier(schoolIdentifier);
        criteria.setSchoolBoard(Boolean.TRUE);
        return timetableEventService.teacherTimetable(criteria, school, false, false);
    }

    public List<RoomTimetableDto> roomTimetables(String schoolIdentifier) {
        School school = schoolByIdentifier(schoolIdentifier);

        JpaNativeQueryBuilder qb = roomTimetableEventsQb(school.getId());
        setCurrentWeekEventsCriteria(qb);

        qb.sort("room_code");
        List<?> data = qb.select("distinct r.id, b.code as building_code, r.code as room_code", em).getResultList();
        return StreamUtil.toMappedList(r -> new RoomTimetableDto((Object[]) r), data);
    }

    private JpaNativeQueryBuilder roomTimetableEventsQb(Long schoolId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event_time tet"
                + " join timetable_event te on te.id = tet.timetable_event_id"
                + " join timetable_event_room ter on ter.timetable_event_time_id = tet.id"
                + " left join (timetable_object tobj join timetable tt on tt.id = tobj.timetable_id) on tobj.id = te.timetable_object_id"
                + " join room r on r.id = ter.room_id"
                + " join building b on b.id = r.building_id");
        qb.requiredCriteria("b.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("(tt.id is null or tt.status_code in (:timetableStatus))", "timetableStatus",
                TimetableStatus.TUNNIPLAAN_STAATUS_P);
        return qb;
    }

    public TimetableByRoomDto roomTimetableForWeek(String schoolIdentifier, TimetableEventSearchCommand criteria) {
        School school = schoolByIdentifier(schoolIdentifier);
        criteria.setSchoolBoard(Boolean.TRUE);
        return timetableEventService.roomTimetable(criteria, school, false);
    }

    private static void setCurrentWeekEventsCriteria(JpaNativeQueryBuilder qb) {
        LocalDate now = LocalDate.now();
        TemporalField dow = WeekFields.ISO.dayOfWeek();
        LocalDate weekStart = now.with(dow, 1L);
        LocalDate weekEnd = now.with(dow, 7L);
        qb.optionalCriteria("tet.start >= :start", "start", weekStart, DateUtils::firstMomentOfDay);
        qb.optionalCriteria("tet.end <= :end", "end", weekEnd, DateUtils::lastMomentOfDay);
    }

    public List<TimetableStudyYearWeekDto> timetableStudyYearWeeks(String schoolIdentifier) {
        School school = schoolByIdentifier(schoolIdentifier);
        StudyYear studyYear = studyYearService.getCurrentStudyYear(school.getId());
        return timetableService.studyYearWeeks(studyYear, null);
    }

    public List<TimetableEventSearchDto> currentEvents(String schoolIdentifier) {
        School school = schoolByIdentifier(schoolIdentifier);
        return timetableEventService.schoolBoardEvents(school, null);
    }

    public List<RoomTimetableDto> roomsWithCurrentEvents(String schoolIdentifier) {
        School school = schoolByIdentifier(schoolIdentifier);

        JpaNativeQueryBuilder qb = roomTimetableEventsQb(school.getId());
        LocalDateTime currentTime = LocalDateTime.now();
        qb.requiredCriteria("date(tet.start) = :today", "today", currentTime.toLocalDate());
        qb.requiredCriteria("tet.end >= :now", "now", currentTime);

        qb.sort("room_code");
        List<?> data = qb.select("distinct r.id, b.code as building_code, r.code as room_code", em).getResultList();
        return StreamUtil.toMappedList(r -> new RoomTimetableDto((Object[]) r), data);
    }

    public List<TimetableEventSearchDto> roomCurrentEvents(String schoolIdentifier, Long roomId) {
        School school = schoolByIdentifier(schoolIdentifier);
        return timetableEventService.schoolBoardEvents(school, roomId);
    }

    public AutocompleteResult room(String schoolIdentifier, Room room) {
        // schoolByIdentifier used for rights check in case of unencoded schoolId
        schoolByIdentifier(schoolIdentifier);
        return AutocompleteResult.of(room);
    }

    public Page<TimetableEventRoomSearchDto> currentlyFreeRooms(String schoolIdentifier, Pageable pageable) {
        School school = schoolByIdentifier(schoolIdentifier);
        TimetableEventRoomsCommand command = freeRoomsCommand();
        command.setStartTime(LocalTime.now());
        command.setEndTime(LocalTime.now());
        return timetableEventService.searchRooms(school.getId(), command, pageable);
    }

    public Page<TimetableEventRoomSearchDto> wholeDayFreeRooms(String schoolIdentifier, Pageable pageable) {
        School school = schoolByIdentifier(schoolIdentifier);
        TimetableEventRoomsCommand command = freeRoomsCommand();
        command.setStartTime(LocalTime.MIN);
        command.setEndTime(LocalTime.MAX);
        return timetableEventService.searchRooms(school.getId(), command, pageable);
    }

    private TimetableEventRoomsCommand freeRoomsCommand() {
        TimetableEventRoomsCommand command = new TimetableEventRoomsCommand();
        command.setIsFreeRoom(Boolean.TRUE);
        command.setIsDormitory(Boolean.FALSE);
        command.setFrom(LocalDate.now());
        command.setThru(LocalDate.now());
        return command;
    }

    private School schoolByIdentifier(String schoolIdentifier) {
        try {
            School school = em.getReference(School.class, Long.valueOf(schoolIdentifier));
            UserUtil.throwAccessDeniedIf(Boolean.TRUE.equals(school.getIsNotPublicTimetable()));
            return school;
        } catch (NumberFormatException e) {
            String id = CryptoUtil.decrypt(encryptionKey, Base64.decodeBase64(schoolIdentifier));
            return em.getReference(School.class, Long.valueOf(id));
        }
    }

    public String schoolBoardUrl(School school) {
        String url = frontendBaseUrl + "schoolBoard/";
        url += Boolean.TRUE.equals(school.getIsNotPublicTimetable()) ? encryptedSchoolId(school) : school.getId();
        return url;
    }

    private String encryptedSchoolId(School school) {
        byte[] encryptedId = CryptoUtil.encrypt(encryptionKey, school.getId());
        try {
            return Base64.encodeBase64URLSafeString(encryptedId);
        } catch (Exception e) {
            throw new HoisException(e);
        }
    }
}
