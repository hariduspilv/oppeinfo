package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsStringList;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.RoomEquipment;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodExam;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.teacher.TeacherAbsence;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.LessonTime;
import ee.hitsa.ois.domain.timetable.Timetable;
import ee.hitsa.ois.domain.timetable.TimetableEvent;
import ee.hitsa.ois.domain.timetable.TimetableEventRoom;
import ee.hitsa.ois.domain.timetable.TimetableEventStudentGroup;
import ee.hitsa.ois.domain.timetable.TimetableEventTeacher;
import ee.hitsa.ois.domain.timetable.TimetableEventTime;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.enums.StudentType;
import ee.hitsa.ois.enums.TimetableEventRepeat;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.message.TimetableEventCreated;
import ee.hitsa.ois.service.TimetableService.TimetablePersonHolder;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.TimetableUserRights;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.JournalAndSubjectAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.RoomAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.RoomForm.RoomEquipmentCommand;
import ee.hitsa.ois.web.commandobject.StudentGroupAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.TeacherAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventRoomsCommand;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.TimetableNewHigherTimeOccupiedCommand;
import ee.hitsa.ois.web.commandobject.timetable.TimetableNewVocationalTimeOccupiedCommand;
import ee.hitsa.ois.web.commandobject.timetable.TimetableSingleEventForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableTimeOccupiedCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.timetable.GeneralTimetableCurriculumDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByGroupDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByRoomDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByStudentDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByTeacherDto;
import ee.hitsa.ois.web.dto.timetable.TimetableCalendarDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventRoomSearchDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchGroupDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchRoomDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchTeacherDto;
import ee.hitsa.ois.web.dto.timetable.TimetableSingleEventTeacherForm;
import ee.hitsa.ois.web.dto.timetable.TimetableTimeOccupiedDto;

@Transactional
@Service
public class TimetableEventService {
    private static final long LESSON_LENGTH = 45;
    private static final String QUERY_ROOM_RECURSIVE = "with recursive cte as ("
            + "select ttet.id, ttet.\"start\" final_start_timestamp, ttet.\"end\" final_end_timestamp, "
                + "ttet.\"start\" start_timestamp, "
                + "case when ttet.\"start\"\\:\\:date != ttet.\"end\"\\:\\:date then date_trunc('day', ttet.\"start\") + interval '1 day' else ttet.\"end\" end end_timestamp, "
                + "tter.room_id room_id "
                + "from timetable_event_time ttet "
                + "join timetable_event tte on tte.id = ttet.timetable_event_id "
                + "join timetable_event_room tter on tter.timetable_event_time_id = ttet.id "
                + "where tte.school_id = :schoolId "
            + "union all "
            + "select cte.id, cte.final_start_timestamp, cte.final_end_timestamp, cast(date_trunc('day', cte.start_timestamp + interval '1 day') as timestamp(6)) start_timestamp, "
                + "case when cte.start_timestamp\\:\\:date + interval '1 day' != cte.final_end_timestamp\\:\\:date "
                    + "then date_trunc('day', cte.start_timestamp) + interval '1 day' "
                    + "else cte.final_end_timestamp end end_timestamp, "
                + "cte.room_id "
                + "from cte "
                + "where cte.start_timestamp\\:\\:date + interval '1 day' <= cte.final_end_timestamp\\:\\:date ), "
            + "room_cte as (select r.id id, r.code r_code, b.code || ' - ' || b.\"name\" b_name, b.school_id school_id, "
                + "r.seats, r.is_study, b.is_dormitory, :from\\:\\:date d_from, :thru\\:\\:date d_thru "
                + "from room r "
                + "join building b on b.id = r.building_id "
                + "where b.school_id = :schoolId "
            + "union all "
            + "select room_cte.id, room_cte.r_code, room_cte.b_name, room_cte.school_id, room_cte.seats, room_cte.is_study, "
                + "room_cte.is_dormitory, room_cte.d_from + 1 d_form, room_cte.d_thru "
                + "from room_cte "
                + "where room_cte.d_from + 1 <= d_thru) ";
    private static final String QUERY_ROOM_SELECT = "r.r_code, r.b_name, coalesce(cte.start_timestamp\\:\\:date, r.d_from) s_date, coalesce(cte.end_timestamp\\:\\:date, r.d_from) e_date, "
            + "string_agg(to_char(cte.start_timestamp, 'HH24:MI') || ' - ' || to_char(cte.end_timestamp, 'HH24:MI'), ';' order by cte.start_timestamp, cte.end_timestamp) all_time, "
            + "r.seats, r.is_study, r.is_dormitory, r.id r_id";

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private TimetableService timetableService;
    @Autowired
    private TimetableGenerationService timetableGenerationService;
    @Autowired
    private EntityManager em;
    @Autowired
    private AutomaticMessageService automaticMessageService;

    public TimetableEvent createEvent(HoisUserDetails user, TimetableSingleEventForm form) {
        TimetableEvent te = createEvent(form, user.getSchoolId(), user.getPersonId());
        if (TimetableUserRights.isTeachersEvent(user, StreamUtil.toMappedList(t -> t.getTeacher().getId(), form.getTeachers()))) {
            sendTeacherEventCreatedMessages(te);
        }
        return te;
    }

    private void sendTeacherEventCreatedMessages(TimetableEvent event) {
        School school = event.getSchool();
        for (TimetableEventTime time : event.getTimetableEventTimes()) {
            TimetableEventCreated data = new TimetableEventCreated(event.getName(), time.getStart(), time.getEnd());
            for (Person receiver : getEventCreatedMessageReceivers(time, school)) {
                automaticMessageService.sendMessageToPerson(MessageType.TEATE_LIIK_TUNN_SYNDMUS, school, receiver, data);
            }
        }
    }
    
    private List<Person> getEventCreatedMessageReceivers(TimetableEventTime time, School school) {
        List<?> data = em.createNativeQuery("select inserted_by"
                + " from timetable"
                + " where school_id = ?1 and ?2 between start_date and end_date")
                .setParameter(1, EntityUtil.getId(school))
                .setParameter(2, JpaQueryUtil.parameterAsTimestamp(time.getStart().toLocalDate()))
                .getResultList();
        List<String> idcodes = new ArrayList<>();
        for (Object r : data) {
            String insertedBy = resultAsString(r, 0);
            String idcode = PersonUtil.idcodeFromFullnameAndIdcode(insertedBy);
            if (idcode != null) {
                idcodes.add(idcode);
            } else {
                log.error("Cannot parse idcode from: {}", insertedBy);
            }
        }
        if (idcodes.isEmpty()) {
            return Collections.emptyList();
        }
        return em.createQuery("select p from Person p where p.idcode in ?1", Person.class)
                .setParameter(1, idcodes)
                .getResultList();
    }
    
    public TimetableEvent createEvent(TimetableSingleEventForm form, Long schoolId, Long personId) {
        TimetableEvent te = new TimetableEvent();
        te = bindSingleEventFormToEvent(form, te, schoolId, personId);
        return EntityUtil.save(te, em);
    }

    public TimetableEvent updateEvent(TimetableSingleEventForm form) {
        TimetableEventTime tet = em.getReference(TimetableEventTime.class, form.getId());
        TimetableEvent te = tet.getTimetableEvent();
        if (form.getDate().isAfter(LocalDate.now())
                || (form.getDate().isEqual(LocalDate.now()) && form.getStartTime().toLocalTime().isAfter(LocalTime.now()))) {
            te = bindSingleEventFormToTimetableEventTime(form, tet);
            return EntityUtil.save(te, em);
        }
        throw new ValidationFailedException("timetable.error.isBeforeNow");
    }
    
    private TimetableEvent bindSingleEventFormToTimetableEventTime(TimetableSingleEventForm form, TimetableEventTime tet) {
        boolean modified = !Objects.equals(tet.getStart(), form.getStartTime());
        if(!modified && !Objects.equals(tet.getEnd(), form.getEndTime())) {
            modified = true;
        }
        
        tet.setStart(form.getDate().atTime(form.getStartTime().toLocalTime()));
        tet.setEnd(form.getDate().atTime(form.getEndTime().toLocalTime()));
        tet.getTimetableEvent().setName(form.getName());
        
        boolean roomsModified = false, teachersModified = false, studentGroupsModified = false;
        if(Boolean.TRUE.equals(form.getRepeat())) {
            tet.getTimetableEvent().setRepeatCode(em.getReference(Classifier.class, form.getRepeatCode()));
            tet.getTimetableEvent().setStart(tet.getStart());
            tet.getTimetableEvent().setEnd(tet.getEnd());
            addTimetableEventTimes(tet.getTimetableEvent(), form);
        } else {
            roomsModified = bindRoomsToTimetableEvent(tet, form);
            teachersModified = bindTeachersToTimetableEvent(tet, form);
            studentGroupsModified = bindStudentGroupsToTimetableEvent(tet, form);
        }
        
        modified = modified || roomsModified || teachersModified || studentGroupsModified;
        if (modified && tet.getTimetableEvent().getTimetableObject() != null) {
            timetableService.sendTimetableChangesMessages(tet.getTimetableEvent().getTimetableObject());
        }
        return tet.getTimetableEvent();
    }

    private TimetableEvent bindSingleEventFormToEvent(TimetableSingleEventForm form, TimetableEvent te, Long schoolId,
            Long personId) {
        te.setStart(form.getDate().atTime(form.getStartTime().toLocalTime()));
        te.setEnd(form.getDate().atTime(form.getEndTime().toLocalTime()));
        te.setSchool(em.getReference(School.class, schoolId));
        te.setName(form.getName());
        te.setConsiderBreak(Boolean.FALSE);
        te.setRepeatCode(Boolean.TRUE.equals(form.getRepeat()) ? em.getReference(Classifier.class, form.getRepeatCode())
                : em.getReference(Classifier.class, TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_EI.name()));
        te.setIsPersonal(form.getIsPersonal());
        if (Boolean.TRUE.equals(form.getIsPersonal())) {
            try {
                te.setPerson(em.getReference(Person.class, personId));
            } catch (NullPointerException e) {
                throw new HoisException("timetable.error.noPerson", e);
            }
            // if event is personal then no student groups and teachers can be added
            if (form.getStudentGroups() != null) {
                form.getStudentGroups().clear();
            }
            if (form.getTeachers() != null) {
                form.getTeachers().clear();
            }
            form.setOtherTeacher(null);
        }
        addTimetableEventTimes(te, form);
        return te;
    }


    public void createEvents(TeacherAbsence absence) {
        TimetableSingleEventForm form = new TimetableSingleEventForm();
        form.setStartTime(absence.getStartDate().atTime(LocalTime.of(7, 00)));
        form.setEndTime(absence.getEndDate().atTime(LocalTime.of(23, 00)));
        form.setRepeat(Boolean.FALSE);
        form.setName(absence.getReason());
        form.setTeachers(new ArrayList<>(Arrays.asList(new TimetableSingleEventTeacherForm(null,
                EntityUtil.getId(absence.getTeacher()), null, null, Boolean.FALSE))));
        
        for(LocalDate date = absence.getStartDate(); date.isBefore(absence.getEndDate()); date = date.plusDays(1)) {
            form.setDate(date);
            createEvent(form, EntityUtil.getId(absence.getTeacher().getSchool()), null);
        }
    }

    private boolean bindRoomsToTimetableEvent(TimetableEventTime timetableEventTime, TimetableSingleEventForm form) {
        boolean modified = false;
        if (EntityUtil.bindEntityCollection(timetableEventTime.getTimetableEventRooms(),
                r -> EntityUtil.getId(r.getRoom()), form.getRooms(), r -> r.getId(), r -> {
                    TimetableEventRoom ter = new TimetableEventRoom();
                    ter.setRoom(em.getReference(Room.class, r.getId()));
                    ter.setTimetableEventTime(timetableEventTime);
                    return ter;
                })) {
            modified = true;
        }
        
        if ((timetableEventTime.getOtherRoom() == null && form.getOtherRoom() != null)
                || (timetableEventTime.getOtherRoom() != null
                        && !timetableEventTime.getOtherRoom().equals(form.getOtherRoom()))) {
            timetableEventTime.setOtherRoom(form.getOtherRoom());
            modified = true;
        }
        return modified;
    }

    private boolean bindTeachersToTimetableEvent(TimetableEventTime timetableEventTime, TimetableSingleEventForm form) {
        boolean modified = areTeachersModified(timetableEventTime, form);
        EntityUtil.bindEntityCollection(timetableEventTime.getTimetableEventTeachers(), r -> EntityUtil.getId(r),
                form.getTeachers(), r -> r.getId(), t -> {
                    TimetableEventTeacher tet = new TimetableEventTeacher();
                    tet.setTeacher(em.getReference(Teacher.class, t.getTeacher().getId()));
                    tet.setTimetableEventTime(timetableEventTime);
                    tet.setIsSubstitute(t.getIsSubstitute());
                    return tet;
                }, (t, tet) -> {
                    tet.setIsSubstitute(t.getIsSubstitute());
                });
        
        if ((timetableEventTime.getOtherTeacher() == null && form.getOtherTeacher() != null)
                || (timetableEventTime.getOtherTeacher() != null
                        && !timetableEventTime.getOtherTeacher().equals(form.getOtherTeacher()))) {
            timetableEventTime.setOtherTeacher(form.getOtherTeacher());
            modified = true;
        }
        return modified;
    }
    
    private static boolean areTeachersModified(TimetableEventTime timetableEventTime, TimetableSingleEventForm form) {
        List<Long> savedTeachers = StreamUtil.toMappedList(t -> EntityUtil.getId(t.getTeacher()),
                timetableEventTime.getTimetableEventTeachers());
        List<Long> formTeachers = StreamUtil.toMappedList(t -> t.getTeacher().getId(), form.getTeachers());
        List<Long> savedSubstitutes = StreamUtil.toMappedList(t -> EntityUtil.getId(t.getTeacher()),
                StreamUtil.toFilteredList(t -> Boolean.TRUE.equals(t.getIsSubstitute()),
                        timetableEventTime.getTimetableEventTeachers()));
        List<Long> formSubstitutes = StreamUtil.toMappedList(t -> t.getTeacher().getId(),
                StreamUtil.toFilteredList(t -> Boolean.TRUE.equals(t.getIsSubstitute()), form.getTeachers()));
        
        return !savedTeachers.equals(formTeachers) || !savedSubstitutes.equals(formSubstitutes);
    }
    
    private boolean bindStudentGroupsToTimetableEvent(TimetableEventTime timetableEventTime, TimetableSingleEventForm form) {
        boolean modified = false;
        if (EntityUtil.bindEntityCollection(timetableEventTime.getTimetableEventStudentGroups(),
                r -> EntityUtil.getId(r.getStudentGroup()), form.getStudentGroups(), r -> r.getId(), sg -> {
                    TimetableEventStudentGroup tesg = new TimetableEventStudentGroup();
                    tesg.setStudentGroup(em.getReference(StudentGroup.class, sg.getId()));
                    tesg.setTimetableEventTime(timetableEventTime);
                    return tesg;
                })) {
            modified = true;
        }
        return modified;
    }
    
    private void addTimetableEventTimes(TimetableEvent te, TimetableSingleEventForm form) {
        List<TimetableEventTime> timetableEventTimes = te.getTimetableEventTimes();
        //timetableEventTimes.clear();
        TimetableEventTime timetableEventTime = new TimetableEventTime();
        timetableEventTime.setStart(te.getStart());
        timetableEventTime.setEnd(te.getEnd());
        timetableEventTime.setTimetableEvent(te);
        bindRoomsToTimetableEvent(timetableEventTime, form);
        bindTeachersToTimetableEvent(timetableEventTime, form);
        bindStudentGroupsToTimetableEvent(timetableEventTime, form);
        timetableEventTimes.add(timetableEventTime);
        long daysToAdd;
        if (ClassifierUtil.equals(TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_P, te.getRepeat())) {
            daysToAdd = 1;
        } else if (ClassifierUtil.equals(TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_N, te.getRepeat())) {
            daysToAdd = 7;
        } else if (ClassifierUtil.equals(TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_N2, te.getRepeat())) {
            daysToAdd = 14;
        } else {
            return;
        }
        LocalDateTime endTime = te.getStart().plusWeeks(form.getWeekAmount().longValue());
        LocalDateTime currentStart = te.getStart().plusDays(daysToAdd);
        LocalDateTime currentEnd = te.getEnd().plusDays(daysToAdd);
        while (endTime.isAfter(currentStart)) {
            TimetableEventTime currentTimetableEventTime = new TimetableEventTime();
            currentTimetableEventTime.setStart(currentStart);
            currentTimetableEventTime.setEnd(currentEnd);
            bindRoomsToTimetableEvent(currentTimetableEventTime, form);
            bindTeachersToTimetableEvent(currentTimetableEventTime, form);
            bindStudentGroupsToTimetableEvent(currentTimetableEventTime, form);
            timetableEventTimes.add(currentTimetableEventTime);
            currentStart = currentStart.plusDays(daysToAdd);
            currentEnd = currentEnd.plusDays(daysToAdd);
        }
    }

    /**
     * Get group's timetable for view.
     * @param command
     * @return timetable with timetable's curriculum and events
     */
    public TimetableByGroupDto groupTimetable(TimetableEventSearchCommand command, Long schoolId) {
        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(command, schoolId).sort("tet.start,tet.end");
        List<TimetableEventSearchDto> eventResultList = getTimetableEventsList(qb);
        setRoomsTeachersAndGroupsForSearchDto(eventResultList, Boolean.FALSE);
        setShowStudyMaterials(eventResultList);
        filterTimetableSingleEvents(eventResultList, null);

        GeneralTimetableCurriculumDto generalTimetableCurriculum = getGeneralTimetableCurriculum(
                command.getStudentGroups().get(0));

        return new TimetableByGroupDto(getStudyPeriods(schoolId, command.getFrom(), command.getThru()), eventResultList,
                generalTimetableCurriculum);
    }

    public TimetableByTeacherDto teacherTimetable(TimetableEventSearchCommand command, Long schoolId) {
        HoisUserDetails user = TimetableService.userFromPrincipal();
        boolean withPersonalUrl = user != null && user.isTeacher() && user.getTeacherId().equals(command.getTeachers().get(0));
        return teacherTimetable(command, schoolId, withPersonalUrl);
    }

    /**
     * Get teacher's timetable for view.
     * @param command
     * @return TimetableByTeacherDto
     */
    private TimetableByTeacherDto teacherTimetable(TimetableEventSearchCommand command, Long schoolId,
            boolean withPersonalUrl) {
        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(command, schoolId).sort("tet.start,tet.end");
        List<TimetableEventSearchDto> eventResultList = getTimetableEventsList(qb);
        setRoomsTeachersAndGroupsForSearchDto(eventResultList, Boolean.FALSE);
        setShowStudyMaterials(eventResultList);
        filterTimetableSingleEvents(eventResultList, command.getPerson());

        Query q = em.createNativeQuery("select t.id, p.firstname, p.lastname from teacher t join person p on t.person_id=p.id where t.id=?1");
        q.setParameter(1, command.getTeachers().get(0));
        Object teacher = q.getSingleResult();

        TimetableByTeacherDto dto = new TimetableByTeacherDto(
                getStudyPeriods(schoolId, command.getFrom(), command.getThru()), eventResultList,
                resultAsLong(teacher, 0), resultAsString(teacher, 1), resultAsString(teacher, 2));
        if (withPersonalUrl) {
            dto.setPersonalUrl(timetableService.getPersonalUrl(Role.ROLL_O, dto.getTeacherId()));
        }
        return dto;
    }

    /**
     * Get student's timetable for view.
     * @param command
     * @return TimetableByStudentDto
     */
    public TimetableByStudentDto studentTimetable(HoisUserDetails user, TimetableEventSearchCommand command) {
        boolean withPersonalUrl = user.isStudent() && user.getStudentId().equals(command.getStudent());
        return studentTimetable(command, user.getSchoolId(), withPersonalUrl);
    }

    private TimetableByStudentDto studentTimetable(TimetableEventSearchCommand command, Long schoolId,
            boolean withPersonalUrl) {
        Object student;
        try {
            Query studentQuery = em.createNativeQuery("select s.id, p.firstname, p.lastname, c.is_higher, s.student_group_id, s.type_code from student s "
                    + "join person p on s.person_id=p.id "
                    + "left join curriculum_version cv on s.curriculum_version_id = cv.id "
                    + "left join curriculum c on cv.curriculum_id = c.id where s.id=?1");
            studentQuery.setParameter(1, command.getStudent());
            student = studentQuery.getSingleResult();
        } catch(@SuppressWarnings("unused") NoResultException e) {
            return null;
        }
        List<TimetableEventSearchDto> eventResultList = new ArrayList<>();
        // get events only if student is in student group or requesting user is guest student
        if (resultAsLong(student, 4) != null || StudentType.OPPUR_K.name().equals(resultAsString(student, 5))) {
            JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(command, schoolId);
            eventResultList = getTimetableEventsList(qb);
            setRoomsTeachersAndGroupsForSearchDto(eventResultList, Boolean.FALSE);
            setShowStudyMaterials(eventResultList);
            filterTimetableSingleEvents(eventResultList, command.getPerson());
        }

        TimetableByStudentDto dto = new TimetableByStudentDto(
                getStudyPeriods(schoolId, command.getFrom(), command.getThru()), eventResultList,
                resultAsLong(student, 0), resultAsString(student, 1), resultAsString(student, 2),
                resultAsBoolean(student, 3));
        if (withPersonalUrl) {
            dto.setPersonalUrl(timetableService.getPersonalUrl(Role.ROLL_T, dto.getStudentId()));
        }
        return dto;
    }
    
    /**
     * Get room's timetable for view.
     * @param command
     * @return timetable with room's info and timetable's events
     */
    public TimetableByRoomDto roomTimetable(TimetableEventSearchCommand command, Long schoolId) {
        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(command, schoolId);
        List<TimetableEventSearchDto> eventResultList = getTimetableEventsList(qb);
        setRoomsTeachersAndGroupsForSearchDto(eventResultList, Boolean.FALSE);
        setShowStudyMaterials(eventResultList);
        filterTimetableSingleEvents(eventResultList, null);

        Query q = em.createNativeQuery("select r.id, r.code as roomCode, b.code as buildingCode from room r join building b on r.building_id=b.id where r.id=?1");
        q.setParameter(1, command.getRoom());
        Object room = q.getSingleResult();

        return new TimetableByRoomDto(getStudyPeriods(schoolId, command.getFrom(), command.getThru()), eventResultList, resultAsLong(room, 0), resultAsString(room, 1), resultAsString(room, 2));
    }

    public TimetableByDto personalTimetable(TimetableEventSearchCommand command, String encodedPerson) {
        TimetableByDto timetable = null;
        TimetablePersonHolder person = timetableService.getPerson(encodedPerson);
        command.setPerson(person);
        if (person != null) {
            if (Role.ROLL_O.name().equals(person.getRole())) {
                Teacher teacher = em.getReference(Teacher.class, person.getRoleId());
                command.setTeachers(new ArrayList<>());
                command.getTeachers().add(teacher.getId());

                Long schoolId = EntityUtil.getId(teacher.getSchool());
                timetable = teacherTimetable(command, schoolId, false);
                timetable.setSchoolId(schoolId);
            } else if (Role.ROLL_T.name().equals(person.getRole())) {
                Student student = em.getReference(Student.class, person.getRoleId());
                command.setStudent(student.getId());

                Long schoolId = EntityUtil.getId(student.getSchool());
                timetable = studentTimetable(command, schoolId, false);
                timetable.setSchoolId(schoolId);
            }
        }
        return timetable;
    }

    private void filterTimetableSingleEvents(List<TimetableEventSearchDto> events, TimetablePersonHolder person) {
        if (person != null) {
            if (Role.ROLL_O.name().equals(person.getRole())) {
                hideOtherTeachersSingleEventsData(events, person.getRoleId());
            } else if (Role.ROLL_T.name().equals(person.getRole())) {
                hideStudentSingleEventsData(events, person.getRoleId());
            } else {
                hideSingleEventsData(events);
            }
        } else {
            HoisUserDetails user = TimetableService.userFromPrincipal();
            if (user != null) {
                if (user.isSchoolAdmin()) {
                    hideOtherAdminsSingleEventsData(events, user);
                } else if (user.isTeacher()) {
                    hideOtherTeachersSingleEventsData(events, user.getTeacherId());
                } else if (user.isStudent() || user.isRepresentative()) {
                    hideStudentSingleEventsData(events, user.getStudentId());
                } else {
                    hideSingleEventsData(events);
                }
            } else {
                hideSingleEventsData(events);
            }
        }
    }

    private static void hideSingleEventsData(List<TimetableEventSearchDto> events) {
        for (TimetableEventSearchDto event : events) {
            if (Boolean.TRUE.equals(event.getSingleEvent())) {
                hideSingleEventData(event);
            }
        }
    }

    // if admin doesn't have TEEMAOIGUS_SYNDMUS view right then other teacher's
    // personal events should be hidden
    private static void hideOtherAdminsSingleEventsData(List<TimetableEventSearchDto> events, HoisUserDetails user) {
        if (!UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_SYNDMUS)) {
            for (TimetableEventSearchDto event : events) {
                if (event.getPerson() != null && !user.getPersonId().equals(event.getPerson().getId())) {
                    hideSingleEventData(event);
                }
            }
        }
    }

    private static void hideOtherTeachersSingleEventsData(List<TimetableEventSearchDto> events, Long teacherId) {
        for (TimetableEventSearchDto event : events) {
            if (Boolean.TRUE.equals(event.getSingleEvent()) && !isTeachersEvent(event.getTeachers(), teacherId)) {
                hideSingleEventData(event);
            }
        }
    }

    // Student can see exam time single events and his/her student group single events
    private void hideStudentSingleEventsData(List<TimetableEventSearchDto> events, Long studentId) {
        Student student = em.getReference(Student.class, studentId);
        Long studentGroupId = EntityUtil.getNullableId(student.getStudentGroup());

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period_exam spe "
                + "join subject_study_period_exam_student spes on spes.subject_study_period_exam_id = spe.id "
                + "join declaration_subject ds on spes.declaration_subject_id = ds.id "
                + "join declaration d on ds.declaration_id = d.id "
                + "join timetable_event_time tet on tet.timetable_event_id = spe.timetable_event_id");
        qb.requiredCriteria("d.student_id = :studentId", "studentId", studentId);

        List<?> data = qb.select("tet.id", em).getResultList();
        List<Long> examTimetableEventTimes = StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);

        for (TimetableEventSearchDto event : events) {
            List<Long> eventStudentGroups = StreamUtil.toMappedList(sg -> sg.getId(), event.getStudentGroups());
            if (Boolean.TRUE.equals(event.getSingleEvent()) && !(examTimetableEventTimes.contains(event.getId())
                    || studentGroupId != null && eventStudentGroups.contains(studentGroupId))) {
                hideSingleEventData(event);
            }
        }
    }

    private static void hideSingleEventData(TimetableEventSearchDto event) {
        event.setNameEn(null);
        event.setNameEt(null);
        event.setRooms(null);
        event.setTeachers(null);
        event.setStudentGroups(null);
        event.setPerson(null);
        event.setIsPersonal(null);
        event.setPublicEvent(Boolean.FALSE);
    }
    
    private static boolean isTeachersEvent(List<TimetableEventSearchTeacherDto> eventTeachers, Long teacherId) {
        for (TimetableEventSearchTeacherDto eventTeacher : eventTeachers) {
            if (teacherId.equals(eventTeacher.getId())) {
                return true;
            }
        }
        return false;
    }
    
    private String getStudyPeriods(Long schoolId, LocalDate start, LocalDate end) {
        String studyPeriods = "";
        if (start != null && end != null) {
            Query q = em.createNativeQuery("select sp.name_et from study_year sy" + 
                    " join study_period sp on sy.id=sp.study_year_id" + 
                    " where (?2 between sp.start_date and sp.end_date" + 
                    " or ?3 between sp.start_date and sp.end_date" + 
                    " or sp.start_date between ?2 and ?3" + 
                    " or sp.end_date between ?2 and ?3)" + 
                    " and sy.school_id = ?1" +
                    " order by sp.start_date");
            q.setParameter(1, schoolId);
            q.setParameter(2, JpaQueryUtil.parameterAsTimestamp(DateUtils.firstMomentOfDay(start)));
            q.setParameter(3, JpaQueryUtil.parameterAsTimestamp(DateUtils.lastMomentOfDay(end)));
            List<?> data = q.getResultList();
            
            if (!data.isEmpty()) {
                studyPeriods = data.stream().map(r -> resultAsString(r, 0)).collect(Collectors.joining(", "));
            }
        }
        return studyPeriods;
    }
    
    private GeneralTimetableCurriculumDto getGeneralTimetableCurriculum(Long studentGroupId) {
        GeneralTimetableCurriculumDto curriculum = null;
        Query generalTimetableCurriculumQuery = em.createNativeQuery("select sg.code as sgCode, cv.code as cvCode, c.name_et, c.name_en from student_group sg"
                + " left join curriculum_version cv on sg.curriculum_version_id=cv.id"
                + " join curriculum c on sg.curriculum_id=c.id where sg.id=?1");
        generalTimetableCurriculumQuery.setParameter(1, studentGroupId);
        List<?> generalTimetableCurriculumResult = generalTimetableCurriculumQuery.getResultList();
        
        if (!generalTimetableCurriculumResult.isEmpty()) {
            curriculum = new GeneralTimetableCurriculumDto((Object[]) generalTimetableCurriculumResult.get(0));
        }
        return curriculum;
    }

    private JpaNativeQueryBuilder getTimetableEventTimeQuery(TimetableEventSearchCommand criteria, Long schoolId) {
        String from = "from timetable_event_time tet"
                + " join timetable_event te on tet.timetable_event_id = te.id"
                + " left join person p on p.id = te.person_id and te.is_personal = true"
                + " left join timetable_object tobj on te.timetable_object_id = tobj.id"
                + " left join timetable t on tobj.timetable_id = t.id left join journal j on tobj.journal_id = j.id";

        Student student = criteria.getStudent() != null ? em.getReference(Student.class, criteria.getStudent()) : null;
        boolean higherstudent = student != null && StudentUtil.isHigher(student);

        if(!higherstudent) {
            from += " left join (subject_study_period ssp join subject subj on subj.id = ssp.subject_id) on ssp.id = tobj.subject_study_period_id";
        }

        if (student != null && (student.getStudentGroup() != null || ClassifierUtil.equals(StudentType.OPPUR_K, student.getType()))) {
            if (higherstudent) {
                from += " left join subject_study_period_exam sspe on sspe.timetable_event_id = te.id";
                from += " left join (subject_study_period ssp join subject subj on subj.id = ssp.subject_id) on (ssp.id = tobj.subject_study_period_id or ssp.id = sspe.subject_study_period_id)";
                from += " left join declaration_subject decls on decls.subject_study_period_id=ssp.id"
                        + " left join declaration decl on (decls.declaration_id=decl.id and decl.status_code = '" + DeclarationStatus.OPINGUKAVA_STAATUS_K.name() + "')"
                        + " left join student s on decl.student_id=s.id";
            } else {
                from += " left join  journal_student js  on js.journal_id=j.id"
                        + " left join student s on s.id=js.student_id";
            }
            criteria.setStudentGroups(Arrays.asList(EntityUtil.getNullableId(student.getStudentGroup())));
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.optionalContains("te.name", "eventName", criteria.getName());

        if (criteria.getStudyPeriod() != null) {
            StudyPeriod sp = em.getReference(StudyPeriod.class, criteria.getStudyPeriod());
            qb.filter("(t.study_period_id = " + sp.getId() + " or tet.start >= '" + sp.getStartDate() + "' and tet.end <= '" + sp.getEndDate() +"')");
        }

        qb.optionalCriteria("tet.start >= :start", "start", criteria.getFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("tet.end <= :end", "end", criteria.getThru(), DateUtils::lastMomentOfDay);
        qb.optionalCriteria("(t.id in (:timetable) or tobj.id is null)", "timetable", criteria.getTimetables());
        qb.requiredCriteria("(te.school_id = :schoolId or t.school_id = :schoolId)", "schoolId", schoolId);
        qb.requiredCriteria("(te.timetable_object_id is null or t.status_code in (:shownStatusCodes))", "shownStatusCodes", timetableService.shownStatusCodes());

        String studentGroupEvents = studentGroupEventsQuery(StringUtils.join(criteria.getStudentGroups(), ", "));
   
        if (student == null && criteria.getStudentGroups() != null && !criteria.getStudentGroups().isEmpty() && !higherstudent) {
            qb.filter("tet.id in (" + studentGroupEvents + ")");
        }

        if (student != null) {
            if (student.getStudentGroup() != null && !higherstudent) {
                qb.filter("(s.id = " + criteria.getStudent() + " or tet.id in (" + studentGroupEvents + "))");
            } else {
                qb.filter("s.id = " + criteria.getStudent());
            }
        }

        if (!Boolean.TRUE.equals(criteria.getPersonalEvent()) && Boolean.TRUE.equals(criteria.getLeadingTeacherEvents())
                && criteria.getUser() != null) {
            String leadingTeacherGroups = "select sg.id from student_group sg"
                    + " join user_curriculum uc on uc.curriculum_id = sg.curriculum_id" + " where uc.user_id = "
                    + criteria.getUser();
            String leadingTeacherGroupEvents = studentGroupEventsQuery(leadingTeacherGroups);
            qb.filter("tet.id in (" + leadingTeacherGroupEvents + ")");
        }

        if (higherstudent) {
            qb.filter("(sspe.id is null or exists(select 1 from subject_study_period_exam_student sspes where sspes.declaration_subject_id = decls.id and sspes.subject_study_period_exam_id = sspe.id))");
        }

        if (Boolean.TRUE.equals(criteria.getSingleEvent())) {
            qb.filter("tobj.id is null");
        } else if (Boolean.FALSE.equals(criteria.getSingleEvent())){
            qb.filter("tobj.id is not null");
        }

        if (criteria.getTeachers() != null && !criteria.getTeachers().isEmpty()) {
            String teacherQueryFromEvent = String.format(
                    " (select teteach.timetable_event_time_id from timetable_event_teacher teteach where teteach.teacher_id in (%s))",
                    criteria.getTeachers().stream().map(r -> r.toString()).collect(Collectors.joining(", ")));
            qb.filter("(tet.id in" + teacherQueryFromEvent + ")");
        }
        if (criteria.getRoom() != null) {
            String roomQuery = String.format(
                    " (select r.timetable_event_time_id from timetable_event_room r where r.room_id = %d)",
                    criteria.getRoom());
            qb.filter("tet.id in" + roomQuery);
        }

        qb.optionalCriteria("tet.other_teacher = :otherTeacher", "otherTeacher", criteria.getOtherTeacher());
        qb.optionalCriteria("tet.other_room = :otherRoom", "otherRoom", criteria.getOtherRoom());
        
        if (criteria.getJournalOrSubjectId() != null) {
            if (criteria.getJournalOrSubjectId().longValue() > 0) {
                qb.optionalCriteria("subj.id = :subjectId", "subjectId", criteria.getJournalOrSubjectId());
            } else {
                qb.optionalCriteria("j.id = :journalId", "journalId", Long.valueOf(-criteria.getJournalOrSubjectId().longValue()));
            }
        }

        if (Boolean.TRUE.equals(criteria.getShowOnlySubstitutes())) {
            qb.filter("tet.id in (select teta.timetable_event_time_id from timetable_event_teacher teta "
                    + "where teta.timetable_event_time_id = tet.id and teta.is_substitute)");
        }

        if (Boolean.TRUE.equals(criteria.getPersonalEvent())) {
            qb.filter("te.is_personal = true");
            if (criteria.getUser() != null) {
                qb.optionalCriteria("te.person_id in (select u.person_id from user_ u where u.id = :userId)", "userId",
                        criteria.getUser());
            }
        } else if (Boolean.FALSE.equals(criteria.getPersonalEvent())) {
            qb.filter("(te.is_personal = false or te.is_personal is null)");
        }

        return qb;
    }

    private static String studentGroupEventsQuery(String studentGroups) {
        String query = "select tet.id from timetable_event_time tet "
                + "join timetable_event te on tet.timetable_event_id = te.id "
                + "left join timetable_object tobj on te.timetable_object_id = tobj.id "
                + "left join timetable_object_student_group tosg on tobj.id = tosg.timetable_object_id "
                + "left join timetable_event_student_group tesg on tet.id = tesg.timetable_event_time_id "
                + "where tosg.student_group_id in (" + studentGroups + ") "
                + "or tesg.student_group_id in (" + studentGroups + ")";
        return query;
    }

    private List<TimetableEventSearchDto> getTimetableEventsList(JpaNativeQueryBuilder qb) {
        String select = "distinct tet.id, j.id as journal_id, ssp.id as subject_study_period_id,"
                + " coalesce(te.name, j.name_et, subj.name_et) as name_et, coalesce(te.name, j.name_et, subj.name_en) as name_en,"
                + " tet.start, tet.end, te.consider_break, tobj.id as single_event, t.id as timetableId, te.capacity_type_code,"
                + " te.is_personal, te.person_id, p.firstname, p.lastname";
        List<?> eventResult = qb.select(select, em).getResultList();

        List<TimetableEventSearchDto> eventResultList = StreamUtil.toMappedList(r -> {
            TimetableEventSearchDto dto = new TimetableEventSearchDto(resultAsLong(r, 0), resultAsLong(r, 1),
                    resultAsLong(r, 2), resultAsString(r, 3), resultAsString(r, 4),
                    resultAsLocalDateTime(r, 5).toLocalDate(), resultAsLocalDateTime(r, 5).toLocalTime(),
                    resultAsLocalDateTime(r, 6).toLocalTime(), resultAsBoolean(r, 7),
                    Boolean.valueOf(resultAsLong(r, 8) == null), resultAsLong(r, 9), resultAsString(r, 10),
                    resultAsBoolean(r, 11));
            if (resultAsLong(r, 12) != null) {
                String personName = PersonUtil.fullname(resultAsString(r, 13), resultAsString(r, 14));
                AutocompleteResult person = new AutocompleteResult(resultAsLong(r, 12), personName, personName);
                dto.setPerson(person);
            }
            return dto;
        }, eventResult);
        return eventResultList;
    }

    private void setRoomsTeachersAndGroupsForSearchDto(List<TimetableEventSearchDto> timetableEventTimes, Boolean showOnlySubstitutes) {
        List<Long> timetableEventTimeIds = StreamUtil.toMappedList(r -> r.getId(), timetableEventTimes);
        if (!timetableEventTimeIds.isEmpty()) {
            Map<Long, List<ResultObject>> teachersByTimetableEventTime = getTeachersByTimetableEventTime(
                    timetableEventTimeIds, showOnlySubstitutes);
            Map<Long, List<ResultObject>> roomsByTimetableEventTime = getRoomsByTimetableEventTime(
                    timetableEventTimeIds);
            Map<Long, List<ResultObject>> groupsByTimetableEventTime = getGroupsByTimetableEventTime(
                    timetableEventTimeIds);

            for (TimetableEventSearchDto dto : timetableEventTimes) {
                dto.setTeachers(
                        StreamUtil.toMappedList(r -> new TimetableEventSearchTeacherDto(r.getObjectId(), r.getFirstValue()), teachersByTimetableEventTime.get(dto.getId())));
                dto.setRooms(
                        StreamUtil.toMappedList(r -> new TimetableEventSearchRoomDto(r.getObjectId(), r.getFirstValue(), r.getSecondValue()), roomsByTimetableEventTime.get(dto.getId())));
                dto.setStudentGroups(
                        StreamUtil.toMappedList(r -> new TimetableEventSearchGroupDto(r.getObjectId(), r.getFirstValue()), groupsByTimetableEventTime.get(dto.getId())));
            }
        }
    }
    
    private void setShowStudyMaterials(List<TimetableEventSearchDto> timetableEventTimes) {
        List<Long> timetableEventTimeIds = StreamUtil.toMappedList(r -> r.getId(), timetableEventTimes);
        if (!timetableEventTimeIds.isEmpty()) {
            HoisUserDetails user = TimetableService.userFromPrincipal();
            
            Map<Long, List<ResultObject>> studyMaterialsByTimetableEventTime = getStudyMaterialsByTimetableEventTime(user, timetableEventTimeIds);
            
            for (TimetableEventSearchDto dto : timetableEventTimes) {
                dto.setShowStudyMaterials(studyMaterialsByTimetableEventTime.get(dto.getId()) != null
                        && studyMaterialsByTimetableEventTime.get(dto.getId()).size() > 0 ? Boolean.TRUE
                                : Boolean.FALSE);
            }
        }
    }

    public Page<TimetableEventSearchDto> search(TimetableEventSearchCommand criteria, Pageable pageable, HoisUserDetails user) {
        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(criteria, user.getSchoolId()).sort(pageable);
        String select = "tet.id, coalesce(te.name, j.name_et, subj.name_et) as name_et, coalesce(te.name, j.name_et, subj.name_en) as name_en,"
                    + " tet.start, tet.end, te.consider_break, tobj.id as single_event, t.id as timetableId, te.capacity_type_code,"
                    + " te.is_personal, te.person_id, p.firstname, p.lastname";
        // do not show exams, they are managed thru separate UI
        qb.filter("not exists(select 1 from subject_study_period_exam sspe2 where sspe2.timetable_event_id = tet.timetable_event_id)");

        Page<TimetableEventSearchDto> result = JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            TimetableEventSearchDto dto = new TimetableEventSearchDto(resultAsLong(r, 0), null, null,
                    resultAsString(r, 1), resultAsString(r, 2), resultAsLocalDateTime(r, 3).toLocalDate(),
                    resultAsLocalDateTime(r, 3).toLocalTime(), resultAsLocalDateTime(r, 4).toLocalTime(),
                    resultAsBoolean(r, 5), Boolean.valueOf(resultAsLong(r, 6) == null), resultAsLong(r, 7),
                    resultAsString(r, 8), resultAsBoolean(r, 9));
            if (resultAsLong(r, 10) != null) {
                String personName = PersonUtil.fullname(resultAsString(r, 11), resultAsString(r, 12));
                AutocompleteResult person = new AutocompleteResult(resultAsLong(r, 10), personName, personName);
                dto.setPerson(person);
            }
            if (Boolean.TRUE.equals(dto.getIsPersonal())) {
                dto.setCanEdit(Boolean.valueOf(UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_PERSYNDMUS)));
            } else {
                dto.setCanEdit(Boolean.valueOf(UserUtil.hasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_SYNDMUS)));
            }
            return dto;
        });
        setRoomsTeachersAndGroupsForSearchDto(result.getContent(), criteria.getShowOnlySubstitutes());
        setShowStudyMaterials(result.getContent());
        return result;
    }
    
    /**
     * Timetable events search
     * @param criteria
     * @return list of timetable events
     */
    public TimetableByDto searchTimetable(TimetableEventSearchCommand criteria, Long schoolId) {
        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(criteria, schoolId);
        List<TimetableEventSearchDto> eventResultList = getTimetableEventsList(qb);
        setRoomsTeachersAndGroupsForSearchDto(eventResultList, Boolean.FALSE);
        setShowStudyMaterials(eventResultList);
        filterTimetableSingleEvents(eventResultList, null);

        return new TimetableByDto(null, eventResultList);
    }

    private Map<Long, List<ResultObject>> getTeachersByTimetableEventTime(List<Long> tetIds, Boolean showOnlySubstitutes) {
        String from = "from timetable_event_time tem " + 
                "join timetable_event te on tem.timetable_event_id=te.id " + 
                "left join timetable_object tob on te.timetable_object_id=tob.id " + 
                "left join journal jj on tob.journal_id=jj.id " + 
                "left join journal_teacher jt on jj.id=jt.journal_id " + 
                "left join timetable_event_teacher tet on (tem.id=tet.timetable_event_time_id and tet.teacher_id=jt.teacher_id) or tem.id=tet.timetable_event_time_id " + 
                "left join subject_study_period ssp on ssp.id=tob.subject_study_period_id " +
                "left join subject_study_period_teacher sspt on sspt.subject_study_period_id=ssp.id " +
                "join teacher t  on t.id = COALESCE(tet.teacher_id, jt.teacher_id, sspt.teacher_id) " +
                "join person p on p.id = t.person_id  ";
        // TODO add ordering by teacher name
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        
        if (Boolean.TRUE.equals(showOnlySubstitutes)) {
            qb.filter("tet.is_substitute");
        }

        qb.requiredCriteria("tem.id in (:tetIds)", "tetIds", tetIds);
        
        qb.sort("p.lastname, p.firstname");
        List<?> queryResult = qb.select("distinct tem.id, t.id as teacherId, p.firstname, p.lastname", em).getResultList();
        List<ResultObject> resultObjects = StreamUtil.toMappedList(
                r -> new ResultObject(resultAsLong(r, 0), resultAsLong(r, 1), PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3))),
                queryResult);
        if (Boolean.FALSE.equals(showOnlySubstitutes)) {
            setOtherTeachersByTimetableEventTime(tetIds, resultObjects);
        }
        return resultObjects.stream().collect(Collectors.groupingBy(r -> r.getTimetableEventId()));
    }
    
    private void setOtherTeachersByTimetableEventTime(List<Long> tetIds, List<ResultObject> resultObjects) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event_time tem");
        qb.requiredCriteria("tem.id in (:tetIds)", "tetIds", tetIds);
        qb.filter("tem.other_teacher is not null");
        
        List<?> queryResult = qb.select("distinct tem.id, tem.other_teacher", em).getResultList();
        List<ResultObject> otherTeacherResultObjects = StreamUtil.toMappedList(
                r -> new ResultObject(resultAsLong(r, 0), null, resultAsString(r, 1)),
                queryResult);
        resultObjects.addAll(otherTeacherResultObjects);
    }

    private Map<Long, List<ResultObject>> getRoomsByTimetableEventTime(List<Long> tetIds) {
        String from = "from timetable_event_room ter" 
                + " join room r on r.id = ter.room_id"
                + " join building b on b.id = r.building_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.requiredCriteria("ter.timetable_event_time_id in (:tetIds)", "tetIds", tetIds);

        qb.sort("b.code, r.code");
        List<?> queryResult = qb
                .select("ter.timetable_event_time_id, r.id as roomId, r.code as room_code, b.code as building_code", em)
                .getResultList();
        
        List<ResultObject> resultObjects = StreamUtil
                .toMappedList(r -> new ResultObject(resultAsLong(r, 0), resultAsLong(r, 1), resultAsString(r, 2), resultAsString(r, 3)), queryResult);
        setOtherRoomsByTimetableEventTime(tetIds, resultObjects);
        return resultObjects.stream().collect(Collectors.groupingBy(r -> r.getTimetableEventId()));
    }
    
    private void setOtherRoomsByTimetableEventTime(List<Long> tetIds, List<ResultObject> resultObjects) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event_time tem");
        qb.requiredCriteria("tem.id in (:tetIds)", "tetIds", tetIds);
        qb.filter("tem.other_room is not null");
        
        List<?> queryResult = qb.select("distinct tem.id, tem.other_room", em).getResultList();
        List<ResultObject> otherRoomResultObjects = StreamUtil.toMappedList(
                r -> new ResultObject(resultAsLong(r, 0), null, resultAsString(r, 1)),
                queryResult);
        resultObjects.addAll(otherRoomResultObjects);
    }
    
    private Map<Long, List<ResultObject>> getGroupsByTimetableEventTime(List<Long> tetIds) {
        String from ="from timetable_event_time tem"
                + " join timetable_event te on tem.timetable_event_id = te.id"
                + " left join timetable_object tobj on te.timetable_object_id = tobj.id"
                + " left join timetable_object_student_group tog on tobj.id = tog.timetable_object_id"
                + " left join timetable_event_student_group tesg on tem.id = tesg.timetable_event_time_id"
                + " join student_group sg on sg.id = tog.student_group_id or sg.id = tesg.student_group_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        
        qb.requiredCriteria("tem.id in (:tetIds)", "tetIds", tetIds);
        
        qb.sort("sg.code");
        List<?> queryResult = qb.select("tem.id, sg.id as studentGroupId, sg.code", em).getResultList();
        List<ResultObject> resultObjects = StreamUtil
                .toMappedList(r -> new ResultObject(resultAsLong(r, 0), resultAsLong(r, 1), resultAsString(r, 2)), queryResult);
        return resultObjects.stream().collect(Collectors.groupingBy(r -> r.getTimetableEventId()));
    }
    
    private Map<Long, List<ResultObject>> getStudyMaterialsByTimetableEventTime(HoisUserDetails user, List<Long> tetIds) {
        String from = "from timetable_event_time tem" + 
                " join timetable_event te on tem.timetable_event_id = te.id" + 
                " join timetable_object tobj on te.timetable_object_id = tobj.id" + 
                " left join journal j on tobj.journal_id = j.id" + 
                " left join subject_study_period ssp on tobj.subject_study_period_id = ssp.id" + 
                " join study_material_connect smc on j.id = smc.journal_id or ssp.id = smc.subject_study_period_id" + 
                " join study_material sm on smc.study_material_id = sm.id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        
        qb.requiredCriteria("tem.id in (:tetIds)", "tetIds", tetIds);

        if (user != null) {
            if (user.isStudent()) {
                qb.optionalCriteria("sm.is_visible_to_students = :isVisibleToStudents", "isVisibleToStudents", Boolean.TRUE);
            }
        } else {
            qb.optionalCriteria("sm.is_public = :isPublic", "isPublic", Boolean.TRUE);
        }
        
        List<?> queryResult = qb.select("tem.id, sm.id as studyMaterialId", em).getResultList();
        List<ResultObject> resultObjects = StreamUtil
                .toMappedList(r -> new ResultObject(resultAsLong(r, 0), resultAsLong(r, 1)), queryResult);
        return resultObjects.stream().collect(Collectors.groupingBy(r -> r.getTimetableEventId()));
    }

    /**
     * Generate iCalendar for group timetable
     * @param command
     * @param lang
     * @return calendar filename and iCalendar format calendar as a string
     */
    public TimetableCalendarDto getGroupCalendar(TimetableEventSearchCommand command, String language, Long schoolId) {
        TimetableByGroupDto groupTimetable = groupTimetable(command, schoolId);
        return timetableGenerationService.getICal(groupTimetable, getLanguage(language));
    }

    /**
     * Generate iCalendar for teacher timetable
     * @param command
     * @param lang
     * @return calendar filename and iCalendar format calendar as a string
     */
    public TimetableCalendarDto getTeacherCalendar(TimetableEventSearchCommand command, String language, Long schoolId) {
        TimetableByTeacherDto teacherTimetable = teacherTimetable(command, schoolId, false);
        return timetableGenerationService.getICal(teacherTimetable, getLanguage(language));
    }

    /**
     * Generate iCalendar for room timetable
     * @param command
     * @param lang
     * @return calendar filename and iCalendar format calendar as a string
     */
    public TimetableCalendarDto getRoomCalendar(TimetableEventSearchCommand command, String language, Long schoolId) {
        TimetableByRoomDto roomTimetable = roomTimetable(command, schoolId);
        return timetableGenerationService.getICal(roomTimetable, getLanguage(language));
    }

    /**
     * Generate iCalendar for student timetable
     * @param command
     * @param lang
     * @return calendar filename and iCalendar format calendar as a string
     */
    public TimetableCalendarDto getStudentCalendar(HoisUserDetails user, TimetableEventSearchCommand command,
            String language) {
        TimetableByStudentDto studentTimetable = studentTimetable(command, user.getSchoolId(), false);
        return timetableGenerationService.getICal(studentTimetable, getLanguage(language));
    }

    /**
     * Generate iCalendar for person timetable
     * @param command
     * @param lang
     * @return calendar filename and iCalendar format calendar as a string
     */
    public TimetableCalendarDto getPersonalCalendar(TimetableEventSearchCommand command, String encodedPerson,
            String language) {
        TimetableByDto personTimetable = personalTimetable(command, encodedPerson);
        return timetableGenerationService.getICal(personTimetable, getLanguage(language));
    }

    /**
     * Generate iCalendar for search result
     * @param command
     * @param lang
     * @return calendar filename and iCalendar format calendar as a string
     */
    public TimetableCalendarDto getSearchCalendar(TimetableEventSearchCommand command, String language, Long schoolId) {
        TimetableByDto searchResult = searchTimetable(command, schoolId);
        return timetableGenerationService.getICal(searchResult.getTimetableEvents(), getLanguage(language));
    }

    public Map<String, ?> searchTimetableFormData(Long schoolId, Long studyYearId) {
        Map<String, Object> data = new HashMap<>();

        StudentGroupAutocompleteCommand studentGroupLookup = new StudentGroupAutocompleteCommand();
        studentGroupLookup.setValid(Boolean.TRUE);
        data.put("studentGroups", autocompleteService.studentGroups(schoolId, studentGroupLookup, false));

        TeacherAutocompleteCommand teacherLookup = new TeacherAutocompleteCommand();
        teacherLookup.setValid(Boolean.TRUE);
        data.put("teachers", autocompleteService.teachers(schoolId, teacherLookup, false));

        RoomAutocompleteCommand roomLookup = new RoomAutocompleteCommand();
        data.put("rooms", autocompleteService.rooms(schoolId, roomLookup));

        JournalAndSubjectAutocompleteCommand journalSubjectLookup = new JournalAndSubjectAutocompleteCommand();
        journalSubjectLookup.setStudyYear(studyYearId);
        data.put("subjects", autocompleteService.journalsAndSubjects(schoolId, journalSubjectLookup));

        return data;
    }

    private static Language getLanguage(String language) {
        return "et".equals(language) ? Language.ET : Language.EN;
    }

    private static class ResultObject {
        private Long timetableEventId;
        private Long objectId;
        private String firstValue;
        private String secondValue;
        
        public ResultObject(Long tetId, Long objectId) {
            this.timetableEventId = tetId;
            this.objectId = objectId;
        }

        public ResultObject(Long tetId, Long objectId, String firstValue) {
            this.timetableEventId = tetId;
            this.objectId = objectId;
            this.firstValue = firstValue;
        }
        
        public ResultObject(Long tetId, Long objectId, String firstValue, String secondValue) {
            this.timetableEventId = tetId;
            this.objectId = objectId;
            this.firstValue = firstValue;
            this.secondValue = secondValue;
        }

        public Long getTimetableEventId() {
            return timetableEventId;
        }
        
        public Long getObjectId() {
            return objectId;
        }

        public String getFirstValue() {
            return firstValue;
        }
        
        public String getSecondValue() {
            return secondValue;
        }
    }

    public TimetableSingleEventForm get(HoisUserDetails user, TimetableEventTime eventTime) {
        TimetableSingleEventForm dto = TimetableSingleEventForm.of(eventTime);
        dto.setCanEdit(Boolean.valueOf(TimetableUserRights.canEditOrDeleteEvent(user, eventTime)));
        return dto;
    }

    public void delete(HoisUserDetails user, TimetableEventTime timetableEventTime) {
        EntityUtil.setUsername(user.getUsername(), em);
        TimetableEvent te = timetableEventTime.getTimetableEvent();
        te.getTimetableEventTimes().remove(timetableEventTime);
        EntityUtil.deleteEntity(timetableEventTime, em);
        if(te.getTimetableEventTimes().isEmpty()) {
            EntityUtil.deleteEntity(te, em);
        }
    }
    
    public TimetableTimeOccupiedDto timetableTimeOccupied(TimetableNewVocationalTimeOccupiedCommand command) {
        if (command.getOldEventId() != null) {
            TimetableEventTime eventTime = em.getReference(TimetableEventTime.class, command.getOldEventId());
            List<TimetableEventTime> eventTimes = eventTime.getTimetableEvent().getTimetableEventTimes();

            Timetable timetable = em.getReference(Timetable.class, command.getTimetable());
            LessonTime lessonTime = em.getReference(LessonTime.class,command.getLessonTime());
            LocalDate start = command.getSelectedDay().equals(timetable.getStartDate().getDayOfWeek())
                    ? timetable.getStartDate()
                    : timetable.getStartDate().with(TemporalAdjusters.next(command.getSelectedDay()));
            
            List<Long> teachers = StreamUtil.toMappedList(t -> EntityUtil.getId(t.getTeacher()), eventTimes.get(0).getTimetableEventTeachers());
            List<Long> rooms = StreamUtil.toMappedList(r -> EntityUtil.getId(r.getRoom()), eventTimes.get(0).getTimetableEventRooms());
                    
            return timetableTimeOccupied(Arrays.asList(start.atTime(lessonTime.getStartTime())),
                    Arrays.asList(start.atTime(lessonTime.getEndTime())), teachers, rooms,
                    StreamUtil.toMappedList(et -> EntityUtil.getId(et), eventTimes), null);
        }
        Journal journal = em.getReference(Journal.class, command.getJournal());
        Timetable timetable = em.getReference(Timetable.class, command.getTimetable());
        LessonTime lessonTime = em.getReference(LessonTime.class,command.getLessonTime());
        LocalDate start = command.getSelectedDay().equals(timetable.getStartDate().getDayOfWeek())
                ? timetable.getStartDate()
                : timetable.getStartDate().with(TemporalAdjusters.next(command.getSelectedDay()));
        List<Long> teachers = StreamUtil.toMappedList(it -> EntityUtil.getId(it.getTeacher()), journal.getJournalTeachers());
        List<Long> rooms = StreamUtil.toMappedList(it -> EntityUtil.getId(it.getRoom()), journal.getJournalRooms());
        
        return timetableTimeOccupied(Arrays.asList(start.atTime(lessonTime.getStartTime())),
                Arrays.asList(start.atTime(lessonTime.getEndTime())), teachers, rooms, null, null);
    }
    
    public TimetableTimeOccupiedDto timetableTimeOccupied(TimetableNewHigherTimeOccupiedCommand command) {
        SubjectStudyPeriod subjectStudyPeriod = em.getReference(SubjectStudyPeriod.class, command.getSubjectStudyPeriod());
        
        List<LocalDateTime> starts = new ArrayList<>();
        List<LocalDateTime> ends = new ArrayList<>();
        starts.add(command.getStartTime());
        
        List<Long> teachers = StreamUtil.toMappedList(it -> EntityUtil.getId(it.getTeacher()), subjectStudyPeriod.getTeachers());
        List<Long> rooms = new ArrayList<>();
        List<Long> timetableEventTimeIds = new ArrayList<>();
        
        if (command.getRoom() != null) {
            rooms.add(command.getRoom());
        }
        
        if (command.getOldEventId() != null) {
            TimetableEventTime eventTime = em.getReference(TimetableEventTime.class, command.getOldEventId());
            LocalDateTime end = command.getStartTime().plus(Duration.between(eventTime.getStart(), eventTime.getEnd()));
            ends.add(end);
            
            rooms = StreamUtil.toMappedList(r -> EntityUtil.getId(r.getRoom()), eventTime.getTimetableEventRooms());
            timetableEventTimeIds.add(command.getOldEventId());
        } else if (command.getLessonAmount() != null) {
            ends.add(command.getStartTime().plusMinutes(LESSON_LENGTH * command.getLessonAmount().longValue()));
        }
        autocompleteService.eventRepeatStartAndEndTimes(command.getTimetable(), command.getRepeatCode(), starts, ends);
        
        return timetableTimeOccupied(starts, ends, teachers, rooms, timetableEventTimeIds, null);
    }
    
    public TimetableTimeOccupiedDto timetableTimeOccupied(TimetableTimeOccupiedCommand command) {
        List<LocalDateTime> starts = new ArrayList<>();
        List<LocalDateTime> ends = new ArrayList<>();
        starts.add(command.getStartTime());
        ends.add(command.getEndTime());
        
        if (command.getRepeatCode() != null) {
            if (command.getWeekAmount() != null) {
                autocompleteService.eventRepeatStartAndEndTimes(command.getRepeatCode(), command.getWeekAmount(), starts, ends);
            } else if (command.getTimetable() != null) {
                autocompleteService.eventRepeatStartAndEndTimes(command.getTimetable(), command.getRepeatCode(), starts, ends);
            }
            
        }
        
        SubjectStudyPeriod ssp = command.getSubjectStudyPeriod() != null
                ? em.getReference(SubjectStudyPeriod.class, command.getSubjectStudyPeriod())
                : null;
        List<Long> teachers = ssp != null
                ? StreamUtil.toMappedList(sspt -> EntityUtil.getId(sspt.getTeacher()), ssp.getTeachers())
                : command.getTeachers();
                
        List<Long> timetableEventTimeIds = new ArrayList<>();
        if (command.getTimetableEventId() != null) {
            TimetableEventTime eventTime = em.getReference(TimetableEventTime.class, command.getTimetableEventId());
            timetableEventTimeIds.addAll(StreamUtil.toMappedList(et -> EntityUtil.getId(et),
                    eventTime.getTimetableEvent().getTimetableEventTimes()));
        }
        if (command.getExam() != null) {
            SubjectStudyPeriodExam exam = em.getReference(SubjectStudyPeriodExam.class, command.getExam());
            timetableEventTimeIds.add(EntityUtil.getId(exam.getTimetableEvent().getTimetableEventTimes().get(0)));
        }
        
        return timetableTimeOccupied(starts, ends, teachers, command.getRooms(), timetableEventTimeIds, command.getStudentGroups());
    }
    
    private TimetableTimeOccupiedDto timetableTimeOccupied(List<LocalDateTime> starts, List<LocalDateTime> ends,
            List<Long> teachers, List<Long> rooms, List<Long> timetableEventTimeIds,  List<Long> studentGroups) {
        TimetableTimeOccupiedDto dto = new TimetableTimeOccupiedDto();
        dto.setOccupied(Boolean.FALSE);
        
        if (CollectionUtils.isEmpty(teachers) && CollectionUtils.isEmpty(rooms) && CollectionUtils.isEmpty(studentGroups)) {
            return dto;
        }
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event te " + 
                "join timetable_event_time tet on te.id = tet.timetable_event_id " + 
                "left join timetable_event_teacher tett on tet.id = tett.timetable_event_time_id " +
                "left join teacher t on tett.teacher_id = t.id " +
                "left join person p on t.person_id = p.id " +
                "left join timetable_event_room ter on tet.id = ter.timetable_event_time_id " +
                "left join room r on ter.room_id = r.id " +
                "left join timetable_event_student_group tesg on  tet.id = tesg.timetable_event_time_id " + 
                "left join student_group sg on tesg.student_group_id = sg.id");
        
        qb.optionalCriteria("tet.id not in (:currentEventTimeIds)", "currentEventTimeIds", timetableEventTimeIds);
        
        if (!starts.isEmpty() && !ends.isEmpty()) {
            String timeFilter = "";
            for (int i = 0; i < starts.size(); i++) {
                timeFilter += timeFilter.isEmpty() ? "(" : " or ";
                timeFilter += "(tet.start < '" + JpaQueryUtil.parameterAsTimestamp(ends.get(i)) + "' and tet.end > '"
                        + JpaQueryUtil.parameterAsTimestamp(starts.get(i)) + "')";
            }
            timeFilter += ")";
            qb.filter(timeFilter);
        }
        
        if (!CollectionUtils.isEmpty(teachers) && !CollectionUtils.isEmpty(rooms) && !CollectionUtils.isEmpty(studentGroups)) {
            qb.filter("(tett.teacher_id in (" + StringUtils.join(teachers, ", ") + ")"
                    + " or ter.room_id in (" + StringUtils.join(rooms, ", ") + ")"
                    + " or tesg.student_group_id in (" + StringUtils.join(studentGroups, ", ") + "))");
        } else if (!CollectionUtils.isEmpty(teachers) && !CollectionUtils.isEmpty(rooms)) {
            qb.filter("(tett.teacher_id in (" + StringUtils.join(teachers, ", ") + ")"
                    + " or ter.room_id in (" + StringUtils.join(rooms, ", ") + "))");
        } else if (!CollectionUtils.isEmpty(rooms) && !CollectionUtils.isEmpty(studentGroups)) {
            qb.filter("(ter.room_id in (" + StringUtils.join(rooms, ", ") + ")"
                    + " or tesg.student_group_id in (" + StringUtils.join(studentGroups, ", ") + "))");
        } else if (!CollectionUtils.isEmpty(teachers) && !CollectionUtils.isEmpty(studentGroups)) {
            qb.filter("(tett.teacher_id in (" + StringUtils.join(teachers, ", ") + ")"
                    + " or tesg.student_group_id in (" + StringUtils.join(studentGroups, ", ") + "))");
        } else {
            qb.optionalCriteria("tett.teacher_id in (:teacherIds)", "teacherIds", teachers);
            qb.optionalCriteria("ter.room_id in (:roomIds)", "roomIds", rooms);
            qb.optionalCriteria("tesg.student_group_id in (:studentGroupIds)", "studentGroupIds", studentGroups);
        }
        
        List<?> data = qb.select(
                "tet.id, tett.teacher_id, p.firstname, p.lastname, ter.room_id, r.code as room_code, tesg.student_group_id, sg.code as stundet_group_code",
                em).getResultList();
        if (!data.isEmpty()) {
            dto.setOccupied(Boolean.TRUE);
            if (!CollectionUtils.isEmpty(teachers)) {
                dto.setTeachers(StreamUtil.toMappedSet(
                        r -> new AutocompleteResult(resultAsLong(r, 1),
                                PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3)),
                                PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3))),
                        StreamUtil.toFilteredList(r -> resultAsLong(r, 1) != null && teachers.contains(resultAsLong(r, 1)), data)));
            }
            if (!CollectionUtils.isEmpty(rooms)) {
                dto.setRooms(StreamUtil.toMappedSet(
                        r -> new AutocompleteResult(resultAsLong(r, 4), resultAsString(r, 5), resultAsString(r, 5)),
                        StreamUtil.toFilteredList(r -> resultAsLong(r, 4) != null && rooms.contains(resultAsLong(r, 4)), data)));
            }
            if (!CollectionUtils.isEmpty(studentGroups)) {
                dto.setStudentGroups(StreamUtil.toMappedSet(
                        r -> new AutocompleteResult(resultAsLong(r, 6), resultAsString(r, 7), resultAsString(r, 7)),
                        StreamUtil.toFilteredList(r -> resultAsLong(r, 6) != null && studentGroups.contains(resultAsLong(r, 6)), data)));
            }
        }
        return dto; 
    }

    public Page<TimetableEventRoomSearchDto> searchRooms(Long schoolId, TimetableEventRoomsCommand cmd, Pageable pageable) {
        StringBuilder from = new StringBuilder("from room_cte r "
                + "left join cte on cte.room_id = r.id "
                    + "and cte.start_timestamp\\:\\:date <= r.d_from and cte.end_timestamp\\:\\:date >= r.d_from ");
        
        if (cmd.getStartTime() != null && cmd.getEndTime() != null) {
            // within start/end time
            // if isBusyRoom and room time is 12:00 - 13:00 then search 12:00 - 12:00 should show it as busy room.
            // while search 11:00 - 12:00 should show it as free room
            from.append("and cte.start_timestamp\\:\\:time " + (Boolean.TRUE.equals(cmd.getIsBusyRoom()) ? "<=" : "<")
                    + " :endTime\\:\\:time and cte.end_timestamp\\:\\:time > :startTime\\:\\:time ");
        }
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from.toString())
                .beforeSelect(QUERY_ROOM_RECURSIVE)
                .groupBy("r_id, r_code, b_name, seats, is_study, is_dormitory, s_date, e_date")
                .sort(pageable);
        
        qb.requiredCriteria("r.school_id = :schoolId", "schoolId", schoolId);

        qb.optionalContains("r.b_name", "building", cmd.getBuilding());
        qb.optionalContains("r.r_code", "room", cmd.getRoom());
        qb.parameter("from", cmd.getFrom());
        qb.parameter("thru", cmd.getThru());
        
        if (cmd.getStartTime() != null && cmd.getEndTime() != null) {
            qb.parameter("startTime", cmd.getStartTime().toString());
            qb.parameter("endTime", cmd.getEndTime().toString());
        }
        
        if (Boolean.TRUE.equals(cmd.getIsBusyRoom())) {
            qb.filter("cte is not null");
        } else if (Boolean.TRUE.equals(cmd.getIsPartlyBusyRoom())) { // Can be only with isFreeRoom
            if (cmd.getStartTime() != null && cmd.getEndTime() != null) {
                qb.filter("(cte is null or cte.start_timestamp\\:\\:time > :startTime\\:\\:time or cte.end_timestamp\\:\\:time < :endTime\\:\\:time)");
            } else {
                // set control that it is not for all the day event
                qb.filter("(cte is null or cte.start_timestamp\\:\\:time > '00:00' or cte.end_timestamp\\:\\:time <= '00:00')");
            }
        } else {
            qb.filter("cte is null");
        }
        
        Page<Object> result = JpaQueryUtil.pagingResult(qb.select(QUERY_ROOM_SELECT, em), pageable, () -> qb.count("count(*)", QUERY_ROOM_SELECT, em, null));
        
        // load room equipment with single query
        List<Long> roomIds = result.getContent().stream().map(r -> resultAsLong(r, 8)).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        Map<Long, List<RoomEquipment>> equipment = JpaQueryUtil.loadRelationChilds(
                RoomEquipment.class, roomIds, em, "room", "id").stream()
                .collect(Collectors.groupingBy(re -> EntityUtil.getId(re.getRoom())));
        
        return result.map(r -> {
            TimetableEventRoomSearchDto dto = new TimetableEventRoomSearchDto();
            dto.setId(resultAsLong(r, 8));
            dto.setRoom(resultAsString(r, 0));
            dto.setBuilding(resultAsString(r, 1));
            dto.setStartDate(resultAsLocalDate(r, 2));
            dto.setEndDate(resultAsLocalDate(r, 3));
            dto.setTimes(resultAsStringList(r, 4, ";"));
            dto.setPlaces(resultAsInteger(r, 5));
            dto.setIsUsedInStudy(resultAsBoolean(r, 6));
            dto.setIsDormitoryRoom(resultAsBoolean(r, 7));
            dto.setEquipment(StreamUtil.toMappedList(
                    re -> EntityUtil.bindToDto(re, new RoomEquipmentCommand()), 
                    equipment.get(resultAsLong(r, 8))));
            return dto;
        });
    }
}
