package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsStringList;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.timetable.TimetableEventStudent;
import ee.hitsa.ois.enums.SchoolTimetableType;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.message.TimetableEventReport;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchStudentDto;
import ee.hitsa.ois.util.TranslateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.RoomEquipment;
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
import ee.hitsa.ois.domain.timetable.TimetableObject;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.enums.TimetableEventRepeat;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.message.TimetableEventCreated;
import ee.hitsa.ois.service.SchoolService.SchoolType;
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
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchSubgroupDto;
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
            + "room_cte as (select r.id id, r.code r_code, r.name r_name, b.code b_code, b.\"name\" b_name, b.school_id school_id, "
                + "r.seats, r.is_study, b.is_dormitory, :from\\:\\:date d_from, :thru\\:\\:date d_thru "
                + "from room r "
                + "join building b on b.id = r.building_id "
                + "where b.school_id = :schoolId "
            + "union all "
            + "select room_cte.id, room_cte.r_code, room_cte.r_name, room_cte.b_code, room_cte.b_name, room_cte.school_id, room_cte.seats, room_cte.is_study, "
                + "room_cte.is_dormitory, room_cte.d_from + 1 d_form, room_cte.d_thru "
                + "from room_cte "
                + "where room_cte.d_from + 1 <= d_thru) ";
    private static final String QUERY_ROOM_SELECT = "r.r_code, r.r_name, r.b_code, r.b_name, coalesce(cte.start_timestamp\\:\\:date, r.d_from) s_date, coalesce(cte.end_timestamp\\:\\:date, r.d_from) e_date, "
            + "string_agg(to_char(cte.start_timestamp, 'HH24:MI') || ' - ' || to_char(cte.end_timestamp, 'HH24:MI'), ';' order by cte.start_timestamp, cte.end_timestamp) all_time, "
            + "r.seats, r.is_study, r.is_dormitory, r.id r_id";

    private static final String STUDENT_GROUP_SINGLE_EVENT = "tet.id in (select tet2.id from timetable_event_time tet2 "
                    + "join timetable_event_student_group tesg2 on tet2.id = tesg2.timetable_event_time_id "
                    + "where tesg2.student_group_id in (:studentGroupIds))";

    private static final String TIMETABLE_EVENT_NAME_ET = "coalesce(te.name, j.name_et, subj.name_et)";
    private static final String TIMETABLE_EVENT_NAME_EN = "case when subj.id is null then coalesce(te.name, j.name_et) "
            + "else subj.name_en || ' (' || subj.code || ')' end";
    private static final String EXAM_EVENT_NAME_EN = "exam_subj.name_en || ' (' || exam_subj.code || ') '"
            + " || lower(coalesce(exam_type.name_en, exam_type.name_et))";

    private static final String PUBLIC_EVENTS_PERSONAL = "te.person_id is null or te.person_id = :publicEventPersonId";
    private static final String PUBLIC_EVENTS_TEACHER = "exists (select 1 from timetable_event_teacher teteach"
            + " where teteach.teacher_id = :publicEventTeacherId and teteach.timetable_event_time_id = tet.id)";
    private static final String PUBLIC_EVENTS_STUDENT_SINGLE = "exists (select 1 from timetable_event_student tes"
            + " where tes.timetable_event_time_id = tet.id and tes.student_id = :publicEventStudentId"
            + " union all"
            + " select 1 from timetable_event_student_group tesg"
            + " join student_group sg on sg.id = tesg.student_group_id"
            + " join student s on s.student_group_id = sg.id"
            + " where tesg.timetable_event_time_id = tet.id and s.id = :publicEventStudentId)";
    private static final String PUBLIC_EVENTS_STUDENT_EXAMS = "exists (select 1 from subject_study_period_exam spe"
            + " join subject_study_period_exam_student spes on spes.subject_study_period_exam_id = spe.id"
            + " join declaration_subject ds on spes.declaration_subject_id = ds.id"
            + " join declaration d on ds.declaration_id = d.id"
            + " join timetable_event_time tet on tet.timetable_event_id = spe.timetable_event_id"
            + " where d.student_id = :publicEventStudentId)";

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
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private PdfService pdfService;

    @Value("${hois.frontend.baseUrl}")
    private String frontEndUrl;

    public TimetableEvent createEvent(HoisUserDetails user, TimetableSingleEventForm form) {
        TimetableEvent te = createEvent(user, form, user.getSchoolId());
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
    
    public TimetableEvent createEvent(HoisUserDetails user, TimetableSingleEventForm form, Long schoolId) {
        TimetableEvent te = new TimetableEvent();
        te = bindSingleEventFormToEvent(user, form, te, schoolId);
        return EntityUtil.save(te, em);
    }

    public TimetableEvent updateEvent(TimetableSingleEventForm form) {
        TimetableEventTime tet = em.getReference(TimetableEventTime.class, form.getId());
        TimetableEvent te = tet.getTimetableEvent();
        if (EntityUtil.getNullableId(te.getTimetableObject()) == null || form.getDate().isAfter(LocalDate.now())
                || (form.getDate().isEqual(LocalDate.now()) && form.getStartTime().toLocalTime().isAfter(LocalTime.now()))) {
            te.setIsPublic(form.getIsPublic());
            te = bindSingleEventFormToTimetableEventTime(form, tet, te);
            return EntityUtil.save(te, em);
        }
        throw new ValidationFailedException("timetable.error.isBeforeNow");
    }
    
    private TimetableEvent bindSingleEventFormToTimetableEventTime(TimetableSingleEventForm form,
            TimetableEventTime tet, TimetableEvent te) {
        boolean modified = !Objects.equals(tet.getStart(), form.getStartTime());
        if(!modified && !Objects.equals(tet.getEnd(), form.getEndTime())) {
            modified = true;
        }
        
        tet.setStart(form.getDate().atTime(form.getStartTime().toLocalTime()));
        tet.setEnd(form.getDate().atTime(form.getEndTime().toLocalTime()));
        tet.getTimetableEvent().setName(form.getName());

        if(Boolean.TRUE.equals(form.getRepeat())) {
            tet.getTimetableEvent().setRepeatCode(em.getReference(Classifier.class, form.getRepeatCode()));
            tet.getTimetableEvent().setStart(tet.getStart());
            tet.getTimetableEvent().setEnd(tet.getEnd());
            addTimetableEventTimes(tet.getTimetableEvent(), form);
        } else {
            if (bindRoomsToTimetableEvent(tet, form)) {
                modified = true;
            }
            if (bindTeachersToTimetableEvent(tet, form)) {
                modified = true;
            }
            // only single events student groups and students can be changed
            if (te.getTimetableObject() == null) {
                if (bindStudentGroupsToTimetableEvent(tet, form)) {
                    modified = true;
                }
                if (bindStudentsToTimetableEvent(tet, form)) {
                    modified = true;
                }
            }
            tet.setAddInfo(form.getAddInfo());
            timetableEventTimeValidation(te, tet);
        }

        TimetableObject timetableObject = tet.getTimetableEvent().getTimetableObject();
        if (modified && timetableObject != null) {
            timetableService.sendTimetableChangesMessages(timetableObject, Collections.singletonList(tet),
                    timetableObject.getTimetableObjectStudentGroups());
        }
        return tet.getTimetableEvent();
    }

    private TimetableEvent bindSingleEventFormToEvent(HoisUserDetails user, TimetableSingleEventForm form,
            TimetableEvent te, Long schoolId) {
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
                te.setPerson(em.getReference(Person.class, user.getPersonId()));
            } catch (NullPointerException e) {
                throw new HoisException("timetable.error.noPerson", e);
            }
            // if event is personal then no student groups, teachers or students can be added
            if (form.getStudentGroups() != null) {
                form.getStudentGroups().clear();
            }
            if (form.getTeachers() != null) {
                form.getTeachers().clear();
            }
            if (form.getStudents() != null) {
                form.getStudents().clear();
            }
            form.setOtherTeacher(null);
        }
        if (user != null && user.isTeacher()) {
            te.setInsertedTeacher(em.getReference(Teacher.class, user.getTeacherId()));
        }
        te.setIsPublic(form.getIsPublic());
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
        
        for(LocalDate date = absence.getStartDate(); date.isBefore(absence.getEndDate().plusDays(1)); date = date.plusDays(1)) {
            form.setDate(date);
            createEvent(null, form, EntityUtil.getId(absence.getTeacher().getSchool()));
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

    private boolean bindStudentsToTimetableEvent(TimetableEventTime timetableEventTime, TimetableSingleEventForm form) {
        boolean modified = false;
        if (EntityUtil.bindEntityCollection(timetableEventTime.getTimetableEventStudents(),
                r -> EntityUtil.getId(r.getStudent()), form.getStudents(), r -> r.getId(), s -> {
                    TimetableEventStudent tes = new TimetableEventStudent();
                    tes.setStudent(em.getReference(Student.class, s.getId()));
                    tes.setTimetableEventTime(timetableEventTime);
                    return tes;
                })) {
            modified = true;
        }
        return modified;
    }
    
    private void addTimetableEventTimes(TimetableEvent te, TimetableSingleEventForm form) {
        List<TimetableEventTime> timetableEventTimes = te.getTimetableEventTimes();
        TimetableEventTime timetableEventTime = new TimetableEventTime();
        timetableEventTime.setStart(te.getStart());
        timetableEventTime.setEnd(te.getEnd());
        timetableEventTime.setTimetableEvent(te);
        bindRoomsToTimetableEvent(timetableEventTime, form);
        bindTeachersToTimetableEvent(timetableEventTime, form);
        bindStudentGroupsToTimetableEvent(timetableEventTime, form);
        bindStudentsToTimetableEvent(timetableEventTime, form);
        timetableEventTime.setAddInfo(form.getAddInfo());
        timetableEventTimeValidation(te, timetableEventTime);
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
            bindStudentsToTimetableEvent(currentTimetableEventTime, form);
            currentTimetableEventTime.setAddInfo(form.getAddInfo());
            timetableEventTimeValidation(te, timetableEventTime);
            timetableEventTimes.add(currentTimetableEventTime);
            currentStart = currentStart.plusDays(daysToAdd);
            currentEnd = currentEnd.plusDays(daysToAdd);
        }
    }

    private void timetableEventTimeValidation(TimetableEvent timetableEvent, TimetableEventTime timetableEventTime) {
        if (Boolean.TRUE.equals(timetableEvent.getIsPersonal())) {
            if (!timetableEventTime.getTimetableEventStudentGroups().isEmpty() || !timetableEventTime.getTimetableEventStudents().isEmpty()
                    || !timetableEventTime.getTimetableEventTeachers().isEmpty() || !StringUtils.isEmpty(timetableEventTime.getOtherTeacher())) {
                throw new ValidationFailedException("if event is personal then no student groups, teachers or students can be added");
            }
        }
    }

    /**
     * Get group's timetable for view.
     * @param school
     * @param command
     * @param checkSchool
     * @return timetable with timetable's curriculum and events
     */
    public TimetableByGroupDto groupTimetable(School school, TimetableEventSearchCommand command, boolean checkSchool) {
        if (checkSchool) {
            UserUtil.throwAccessDeniedIf(!TimetableService.allowedToViewSchoolTimetable(school));
        }
        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(command, school.getId());
        List<TimetableEventSearchDto> eventResultList = getTimetableEventsList(qb);
        setRoomsTeachersStudentsAndGroupsForSearchDto(eventResultList, Boolean.FALSE, Boolean.FALSE);
        setShowStudyMaterials(eventResultList);
        filterTimetableEvents(eventResultList, school, null, Boolean.TRUE.equals(command.getSchoolBoard()));

        GeneralTimetableCurriculumDto generalTimetableCurriculum = getGeneralTimetableCurriculum(
                command.getStudentGroups().get(0));
        SchoolType schoolType = schoolService.schoolType(EntityUtil.getId(school));
        return new TimetableByGroupDto(getStudyPeriods(school.getId(), command.getFrom(), command.getThru()), eventResultList,
                generalTimetableCurriculum, Boolean.valueOf(schoolType.isHigher()));
    }

    public TimetableByTeacherDto teacherTimetable(School school, TimetableEventSearchCommand command) {
        HoisUserDetails user = TimetableService.userFromPrincipal();
        boolean withPersonalParam = user != null && user.isTeacher() && user.getTeacherId().equals(command.getTeachers().get(0));
        return teacherTimetable(school, command, withPersonalParam, true);
    }

    /**
     * Get teacher's timetable for view.
     * @param school
     * @param command
     * @param withPersonalParam
     * @param checkSchool
     * @return TimetableByTeacherDto
     */
    public TimetableByTeacherDto teacherTimetable(School school, TimetableEventSearchCommand command,
            boolean withPersonalParam, boolean checkSchool) {
        if (checkSchool) {
            UserUtil.throwAccessDeniedIf(command.getPerson() == null && !TimetableService.allowedToViewSchoolTimetable(school));
        }
        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(command, school.getId());
        List<TimetableEventSearchDto> eventResultList = getTimetableEventsList(qb);
        setRoomsTeachersStudentsAndGroupsForSearchDto(eventResultList, Boolean.FALSE, Boolean.FALSE);
        setShowStudyMaterials(eventResultList);
        filterTimetableEvents(eventResultList, school, command.getPerson(), Boolean.TRUE.equals(command.getSchoolBoard()));

        Query q = em.createNativeQuery("select t.id, p.firstname, p.lastname from teacher t join person p on t.person_id=p.id where t.id=?1");
        q.setParameter(1, command.getTeachers().get(0));
        Object teacher = q.getSingleResult();
        SchoolType schoolType = schoolService.schoolType(EntityUtil.getId(school));
        TimetableByTeacherDto dto = new TimetableByTeacherDto(
                getStudyPeriods(school.getId(), command.getFrom(), command.getThru()), eventResultList,
                resultAsLong(teacher, 0), resultAsString(teacher, 1), resultAsString(teacher, 2), Boolean.valueOf(schoolType.isHigher()));
        if (withPersonalParam) {
            dto.setPersonalParam(timetableService.getPersonalUrlParam(Role.ROLL_O, dto.getTeacherId()));
        }
        return dto;
    }

    /**
     * Get student's timetable for view.
     * @param command
     * @return TimetableByStudentDto
     */
    public TimetableByStudentDto studentTimetable(HoisUserDetails user, TimetableEventSearchCommand command) {
        School school = em.getReference(School.class, user.getSchoolId());
        boolean withPersonalParam = user.isStudent() && user.getStudentId().equals(command.getStudent());
        return studentTimetable(school, command, withPersonalParam);
    }

    private TimetableByStudentDto studentTimetable(School school, TimetableEventSearchCommand command,
            boolean withPersonalParam) {
        UserUtil.throwAccessDeniedIf(command.getPerson() == null && !TimetableService.allowedToViewSchoolTimetable(school));
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
        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(command, school.getId());
        List<TimetableEventSearchDto> eventResultList = getTimetableEventsList(qb);
        setRoomsTeachersStudentsAndGroupsForSearchDto(eventResultList, Boolean.FALSE, Boolean.FALSE);
        setShowStudyMaterials(eventResultList);
        filterTimetableEvents(eventResultList, school, command.getPerson(), false);

        TimetableByStudentDto dto = new TimetableByStudentDto(
                getStudyPeriods(school.getId(), command.getFrom(), command.getThru()), eventResultList,
                resultAsLong(student, 0), resultAsString(student, 1), resultAsString(student, 2),
                resultAsBoolean(student, 3));
        if (withPersonalParam) {
            dto.setPersonalParam(timetableService.getPersonalUrlParam(Role.ROLL_T, dto.getStudentId()));
        }
        return dto;
    }
    
    /**
     * Get room's timetable for view.
     * @param school
     * @param command
     * @param checkSchool
     * @return timetable with room's info and timetable's events
     */
    public TimetableByRoomDto roomTimetable(School school, TimetableEventSearchCommand command, boolean checkSchool) {
        if (checkSchool) {
            UserUtil.throwAccessDeniedIf(!TimetableService.allowedToViewSchoolTimetable(school));
        }
        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(command, school.getId());
        List<TimetableEventSearchDto> eventResultList = getTimetableEventsList(qb);
        setRoomsTeachersStudentsAndGroupsForSearchDto(eventResultList, Boolean.FALSE, Boolean.FALSE);
        setShowStudyMaterials(eventResultList);
        filterTimetableEvents(eventResultList, school, null, Boolean.TRUE.equals(command.getSchoolBoard()));

        Query q = em.createNativeQuery("select r.id, r.code as roomCode, b.code as buildingCode from room r join building b on r.building_id=b.id where r.id=?1");
        q.setParameter(1, command.getRoom());
        Object room = q.getSingleResult();
        SchoolType schoolType = schoolService.schoolType(EntityUtil.getId(school));
        return new TimetableByRoomDto(getStudyPeriods(school.getId(), command.getFrom(), command.getThru()),
                eventResultList, resultAsLong(room, 0), resultAsString(room, 1), resultAsString(room, 2), Boolean.valueOf(schoolType.isHigher()));
    }

    public TimetableByDto personalTimetable(TimetableEventSearchCommand command, String encodedPerson) {
        setPersonalTimetablePerson(command, encodedPerson);
        TimetableByDto timetable = personalTimetable(command);
        timetable.setPersonalParam(encodedPerson);
        return timetable;
    }

    public TimetableByDto personalTimetable(TimetableEventSearchCommand command) {
        TimetableByDto timetable = null;
        if (command.getPerson() != null) {
            if (Role.ROLL_O.name().equals(command.getPerson().getRole())) {
                School school = command.getPerson().getSchool();
                timetable = teacherTimetable(school, command, false, true);
                timetable.setSchoolId(school.getId());
            } else if (Role.ROLL_T.name().equals(command.getPerson().getRole())) {
                School school = command.getPerson().getSchool();
                timetable = studentTimetable(school, command, false);
                timetable.setSchoolId(school.getId());
            }
        }
        return timetable;
    }

    private void setPersonalTimetablePerson(TimetableEventSearchCommand command, String encodedPerson) {
        TimetablePersonHolder person = timetableService.getPerson(encodedPerson);
        command.setPerson(person);
        if (person != null) {
            if (Role.ROLL_O.name().equals(person.getRole())) {
                Teacher teacher = em.getReference(Teacher.class, person.getRoleId());
                command.getPerson().setSchool(teacher.getSchool());
                command.setTeachers(new ArrayList<>());
                command.getTeachers().add(teacher.getId());
            } else if (Role.ROLL_T.name().equals(person.getRole())) {
                Student student = em.getReference(Student.class, person.getRoleId());
                command.getPerson().setSchool(student.getSchool());
                command.setStudent(student.getId());
            }
        }
    }

    private void filterTimetableEvents(List<TimetableEventSearchDto> events, School school,
            TimetablePersonHolder person, boolean ignoreUser) {
        if (person != null) {
            if (!EnumUtil.toNameList(Role.ROLL_O, Role.ROLL_T).contains(person.getRole())) {
                hideTimetableEventsData(events);
            }
        } else {
            HoisUserDetails user = !ignoreUser ? TimetableService.userFromPrincipal() : null;
            if (user == null || !school.getId().equals(user.getSchoolId())) {
                hideTimetableEventsData(events);
            }
        }
    }

    private void hideTimetableEventsData(List<TimetableEventSearchDto> events) {
        for (TimetableEventSearchDto event : events) {
            event.setStudents(null);
        }
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
        JpaNativeQueryBuilder qb;
        Map<String, Object> parameters = new HashMap<>();

        Student student = EntityUtil.getOptionalOne(Student.class, criteria.getStudent(), em);
        if (student != null) {
            criteria.setStudentGroups(Collections.singletonList(EntityUtil.getNullableId(student.getStudentGroup())));
        }

        String timetableObjectEvents = "";
        if (!Boolean.TRUE.equals(criteria.getSingleEvent())) {
            qb = getTimetableObjectEventQuery(criteria, schoolId, student);
            timetableObjectEvents = qb.querySql("tet.id tet_id, j.id journal_id, ssp.id subject_study_period_id,"
                    + TIMETABLE_EVENT_NAME_ET + " name_et, "  + TIMETABLE_EVENT_NAME_EN + " name_en,"
                    + " tet.start tet_start, tet.end tet_end, false is_single_event, t.id timetable_id, te.capacity_type_code,"
                    + " te.is_personal, cast(null as bigint) person_id, cast(null as varchar) person_firstname,"
                    + " cast(null as varchar) person_lastname, cast(null as bigint) juhan_event_id,"
                    + " cast(null as bigint) exam_id, coalesce(tet.changed, tet.inserted) changed,"
                    + " (select count(tes.id) from timetable_event_student tes where tes.timetable_event_time_id = tet.id) > 0 includes_event_students,"
                    + " tet.add_info tet_add_info, cast(null as bigint) inserted_teacher_id,"
                    + " true is_public_event", false);
            parameters.putAll(qb.queryParameters());
        }

        String singleEvents = "";
        if (!Boolean.FALSE.equals(criteria.getSingleEvent()) && criteria.getJournalId() == null && criteria.getSubjectId() == null) {
            qb = getTimetableSingleEventQuery(criteria, schoolId, student);
            singleEvents = qb.querySql("tet.id tet_id, cast(null as bigint) journal_id, cast(null as bigint) subject_study_period_id,"
                    + " te.name name_et, null name_en, tet.start tet_start, tet.end tet_end,"
                    + " true is_single_event, cast(null as bigint) timetable_id, cast(null as varchar) capacity_type_code,"
                    + " te.is_personal, te.person_id, p.firstname person_firstname, p.lastname person_lastname,"
                    + " te.juhan_event_id, cast(null as bigint) exam_id, coalesce(tet.changed, tet.inserted) changed,"
                    + " (select count(tes.id) from timetable_event_student tes where tes.timetable_event_time_id = tet.id) > 0 includes_event_students,"
                    + " tet.add_info tet_add_info, te.inserted_teacher_id, "
                    + " (" + isTimetableSingleEventPublic(qb, criteria, schoolId) + ") is_public_event", false);
            parameters.putAll(qb.queryParameters());
        }

        String examEvents = "";
        if (!Boolean.TRUE.equals(criteria.getHideExams()) && criteria.getJournalId() == null) {
            qb = getTimetableExamEventQuery(criteria, schoolId, student);
            examEvents = qb.querySql("tet.id tet_id, cast(null as bigint) journal_id, cast(null as bigint) subject_study_period_id,"
                    + " te.name name_et, " + EXAM_EVENT_NAME_EN + " name_en, tet.start tet_start, tet.end tet_end,"
                    + " false is_single_event, cast(null as bigint) timetable_id, cast(null as varchar) capacity_type_code,"
                    + " false is_personal, cast(null as bigint) person_id, cast(null as varchar) person_firstname,"
                    + " cast(null as varchar) person_lastname, cast(null as bigint) juhan_event_id,"
                    + " sspe.id exam_id, coalesce(tet.changed, tet.inserted) changed, false includes_event_students,"
                    + " tet.add_info tet_add_info, cast(null as bigint) inserted_teacher_id,"
                    + " (" + isTimetableExamEventPublic(qb, criteria, schoolId) + ") is_public_event", false);
            parameters.putAll(qb.queryParameters());
        }

        String allEvents = Stream.of(timetableObjectEvents, singleEvents, examEvents).filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(" union all "));
        qb = new JpaNativeQueryBuilder("from (" + allEvents + ") as all_events");
        qb.parameters(parameters);
        return qb;
    }

    private JpaNativeQueryBuilder getTimetableObjectEventQuery(TimetableEventSearchCommand criteria, Long schoolId,
            Student student) {
        String from = "from timetable_event_time tet"
                + " join timetable_event te on tet.timetable_event_id = te.id"
                + " join timetable_object tobj on tobj.id = te.timetable_object_id"
                + " join timetable t on t.id = tobj.timetable_id"
                + " left join journal j on j.id = tobj.journal_id"
                + " left join (subject_study_period ssp join subject subj on subj.id = ssp.subject_id)"
                    + " on ssp.id = tobj.subject_study_period_id";

        boolean isHigherStudent = student != null && StudentUtil.isHigher(student);
        if (student != null) {
            if (isHigherStudent) {
                from += " join declaration_subject decls on decls.subject_study_period_id = ssp.id"
                        + " join declaration decl on decls.declaration_id = decl.id"
                        + " join student s on decl.student_id = s.id";
            } else {
                from += " left join journal_student js on js.journal_id = j.id"
                        + " left join student s on s.id = js.student_id";
            }
            from += " left join timetable_event_student student_tes on tet.id = student_tes.timetable_event_time_id";
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        setEventQuerySearchCriteria(qb, criteria, schoolId);
        qb.optionalContains(Language.EN.equals(criteria.getLang()) ? TIMETABLE_EVENT_NAME_EN : TIMETABLE_EVENT_NAME_ET,
                "eventName", criteria.getName());
        qb.optionalCriteria("t.study_period_id = :studyPeriodId", "studyPeriodId", criteria.getStudyPeriod());
        qb.requiredCriteria("t.status_code in (:shownStatusCodes)", "shownStatusCodes",
                timetableService.shownStatusCodes(criteria.getPerson(), Boolean.TRUE.equals(criteria.getSchoolBoard())));
        qb.optionalCriteria("subj.id = :subjectId", "subjectId", criteria.getSubjectId());
        qb.optionalCriteria("j.id = :journalId", "journalId", criteria.getJournalId());

        if (student != null) {
            String studentEvents;
            if (isHigherStudent) {
                // declaration or lesson have no subgroup or they match
                studentEvents = "(s.id = :studentId and (decls.subject_study_period_subgroup_id is null or"
                        + " (exists (select tet2.id from timetable_event_time tet2"
                        + " left join timetable_event_subgroup tesub on tet2.id = tesub.timetable_event_time_id"
                        + " where tet2.id = tet.id and (tesub.id is null"
                        + " or tesub.subject_study_period_subgroup_id = decls.subject_study_period_subgroup_id)))))";
            } else {
                // journal events that are either not individual or are student's individual events
                studentEvents = "(s.id = :studentId and (student_tes.id is null or student_tes.student_id = :studentId))";
                if (student.getStudentGroup() != null) {
                    // student group journal events that aren't individual
                    // vocational student sees group journal events even without being in them
                    studentEvents += " or tet.id in (select tet2.id from timetable_event_time tet2"
                            + " join timetable_event te2 on tet2.timetable_event_id = te2.id"
                            + " join timetable_object tobj2 on te2.timetable_object_id = tobj2.id"
                            + " join timetable_object_student_group tosg on tobj2.id = tosg.timetable_object_id"
                            + " where tobj2.id = tobj.id and tosg.student_group_id in (:studentGroupIds)"
                            + " and student_tes.student_id is null)";
                    qb.parameter("studentGroupIds", criteria.getStudentGroups());
                }
            }
            qb.filter("(" + studentEvents + ")");
            qb.parameter("studentId", criteria.getStudent());
        } else {
            qb.optionalCriteria("tet.id in (select tet2.id from timetable_event_time tet2"
                    + " join timetable_event te2 on tet2.timetable_event_id = te2.id"
                    + " join timetable_object tobj2 on te2.timetable_object_id = tobj2.id"
                    + " join timetable_object_student_group tosg on tobj2.id = tosg.timetable_object_id"
                    + " where tobj2.id = tobj.id and tosg.student_group_id in (:studentGroupIds))",
                    "studentGroupIds", criteria.getStudentGroups());
        }

        return qb;
    }

    private void setEventQuerySearchCriteria(JpaNativeQueryBuilder qb, TimetableEventSearchCommand criteria,
            Long schoolId) {
        qb.requiredCriteria("te.school_id = :schoolId", "schoolId", schoolId);
        qb.optionalCriteria("tet.start >= :start", "start", criteria.getFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("tet.end <= :end", "end", criteria.getThru(), DateUtils::lastMomentOfDay);

        qb.optionalCriteria("tet.id in (select teteach.timetable_event_time_id from timetable_event_teacher teteach"
                + " where teteach.teacher_id in (:teacherIds))", "teacherIds", criteria.getTeachers());
        qb.optionalCriteria("tet.id in (select ter.timetable_event_time_id from timetable_event_room ter"
                + " where ter.room_id = :roomId)", "roomId", criteria.getRoom());

        qb.optionalContains("tet.other_teacher", "otherTeacher", criteria.getOtherTeacher());
        qb.optionalContains("tet.other_room", "otherRoom", criteria.getOtherRoom());

        qb.optionalCriteria("exists (select 1 from timetable_event_student tes_sname"
                        + " join student s_sname on s_sname.id = tes_sname.student_id"
                        + " join person p_sname on p_sname.id = s_sname.person_id"
                        + " where tes_sname.timetable_event_time_id = tet.id and"
                        + " (upper(p_sname.firstname) like :studentName or upper(p_sname.lastname) like :studentName"
                        + " or upper(p_sname.firstname || ' ' || p_sname.lastname) like :studentName))", "studentName",
                criteria.getStudentName() == null ? null : JpaQueryUtil.toContains(criteria.getStudentName()));

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

        if (criteria.getJuhanEvent() != null) {
            qb.filter("te.juhan_event_id is " + (Boolean.TRUE.equals(criteria.getJuhanEvent()) ? "not " : "") + "null");
        }
    }

    private JpaNativeQueryBuilder getTimetableSingleEventQuery(TimetableEventSearchCommand criteria, Long schoolId,
            Student student) {
        String from = "from timetable_event_time tet"
                + " join timetable_event te on tet.timetable_event_id = te.id"
                + " left join person p on p.id = te.person_id and te.is_personal = true";

        if (student != null) {
            from += " left join timetable_event_student student_tes on tet.id = student_tes.timetable_event_time_id";
        }
        if (criteria.getStudyPeriod() != null) {
            from += " join study_period sp on sp.id = :studyPeriodId";
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        setEventQuerySearchCriteria(qb, criteria, schoolId);
        qb.optionalContains("te.name", "eventName", criteria.getName());
        qb.optionalCriteria("(tet.start >= sp.start_date and tet.end <= sp.end_date)",
                "studyPeriodId", criteria.getStudyPeriod());
        qb.filter("te.timetable_object_id is null");
        qb.filter("not exists (select 1 from subject_study_period_exam sspe where sspe.timetable_event_id = te.id)");

        if (student != null) {
            // individual single events
            String studentEvents = "student_tes.student_id = :studentId";
            if (student.getStudentGroup() != null) {
                // student group single events
                studentEvents += " or " + STUDENT_GROUP_SINGLE_EVENT;
                qb.parameter("studentGroupIds", criteria.getStudentGroups());
            }
            qb.filter("(" + studentEvents + ")");
            qb.parameter("studentId", criteria.getStudent());
        } else {
            qb.optionalCriteria(STUDENT_GROUP_SINGLE_EVENT, "studentGroupIds", criteria.getStudentGroups());
        }

        return qb;
    }

    private String isTimetableSingleEventPublic(JpaNativeQueryBuilder qb, TimetableEventSearchCommand criteria,
            Long schoolId) {
        TimetablePersonHolder person = criteria.getPerson();
        School school = em.getReference(School.class, schoolId);
        boolean ascImportedTimetables = SchoolTimetableType.TIMETABLE_ASC.name()
                .equals(EntityUtil.getCode(school.getTimetable()));

        String publicSingleEvents = "te.is_public = true or te.juhan_event_id is not null";
        if (ascImportedTimetables) {
            publicSingleEvents += " or te.is_imported = true";
        }

        if (person != null) {
            if (Role.ROLL_O.name().equals(person.getRole())) {
                qb.parameter("publicEventTeacherId", person.getRoleId());
                publicSingleEvents += " or " + PUBLIC_EVENTS_TEACHER;
            } else if (Role.ROLL_T.name().equals(person.getRole())) {
                qb.parameter("publicEventStudentId", person.getRoleId());
                publicSingleEvents += " or " + PUBLIC_EVENTS_STUDENT_SINGLE;
            }
        } else {
            HoisUserDetails user = !Boolean.TRUE.equals(criteria.getSchoolBoard()) ? TimetableService.userFromPrincipal() : null;
            if (user != null && school.getId().equals(user.getSchoolId())) {
                if (user.isSchoolAdmin()) {
                    // if admin doesn't have TEEMAOIGUS_SYNDMUS view right then other teacher's
                    // personal events should be hidden, otherwise admins see every event
                    if (UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_SYNDMUS)) {
                        return "true";
                    }
                    qb.parameter("publicEventPersonId", user.getPersonId());
                    publicSingleEvents += " or " + PUBLIC_EVENTS_PERSONAL;
                } else if (user.isLeadingTeacher()) {
                    qb.parameter("publicEventPersonId", user.getPersonId());
                    publicSingleEvents += " or " + PUBLIC_EVENTS_PERSONAL;
                } else if (user.isTeacher()) {
                    qb.parameter("publicEventTeacherId", user.getTeacherId());
                    publicSingleEvents += " or " + PUBLIC_EVENTS_TEACHER;
                } else if (user.isStudent() || user.isRepresentative()) {
                    qb.parameter("publicEventStudentId", user.getStudentId());
                    publicSingleEvents += " or " + PUBLIC_EVENTS_STUDENT_SINGLE;
                }
            }
        }
        return publicSingleEvents;
    }

    private JpaNativeQueryBuilder getTimetableExamEventQuery(TimetableEventSearchCommand criteria, Long schoolId,
            Student student) {
        String from = "from timetable_event_time tet"
                + " join timetable_event te on tet.timetable_event_id = te.id"
                + " join subject_study_period_exam sspe on sspe.timetable_event_id = te.id"
                + " join subject_study_period exam_ssp on exam_ssp.id = sspe.subject_study_period_id"
                + " join subject exam_subj on exam_subj.id = exam_ssp.subject_id"
                + " join classifier exam_type on exam_type.code = sspe.type_code";

        if (student != null) {
            from += " join declaration_subject decls on decls.subject_study_period_id = exam_ssp.id"
                    + " join declaration decl on decls.declaration_id = decl.id"
                    + " join student s on decl.student_id = s.id";
        }
        if (criteria.getStudyPeriod() != null) {
            from += " join study_period sp on sp.id = :studyPeriodId";
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        setEventQuerySearchCriteria(qb, criteria, schoolId);
        qb.optionalContains(Language.EN.equals(criteria.getLang()) ? EXAM_EVENT_NAME_EN : "te.name",
                "eventName", criteria.getName());
        if (student != null) {
            qb.requiredCriteria("s.id = :studentId", "studentId", criteria.getStudent());
        } else {
            //Exams do not have connected groups but should not be shown when events are searched by them
            qb.optionalCriteria(STUDENT_GROUP_SINGLE_EVENT, "studentGroupIds", criteria.getStudentGroups());
        }
        qb.optionalCriteria("(tet.start >= sp.start_date and tet.end <= sp.end_date)",
                "studyPeriodId", criteria.getStudyPeriod());
        qb.optionalCriteria("exam_subj.id = :subjectId", "subjectId", criteria.getSubjectId());

        return qb;
    }

    private String isTimetableExamEventPublic(JpaNativeQueryBuilder qb, TimetableEventSearchCommand criteria,
            Long schoolId) {
        TimetablePersonHolder person = criteria.getPerson();
        School school = em.getReference(School.class, schoolId);

        if (person != null) {
            if (Role.ROLL_O.name().equals(person.getRole())) {
                qb.parameter("publicEventTeacherId", person.getRoleId());
                return PUBLIC_EVENTS_TEACHER;
            } else if (Role.ROLL_T.name().equals(person.getRole())) {
                qb.parameter("publicEventStudentId", person.getRoleId());
                return PUBLIC_EVENTS_STUDENT_EXAMS;
            }
        } else {
            HoisUserDetails user = !Boolean.TRUE.equals(criteria.getSchoolBoard()) ? TimetableService.userFromPrincipal() : null;
            if (user != null && school.getId().equals(user.getSchoolId())) {
                if (user.isSchoolAdmin() || user.isLeadingTeacher()) {
                    return "true";
                } else if (user.isTeacher()) {
                    qb.parameter("publicEventTeacherId", user.getTeacherId());
                    return PUBLIC_EVENTS_TEACHER;
                } else if (user.isStudent() || user.isRepresentative()) {
                    qb.parameter("publicEventStudentId", user.getStudentId());
                    return PUBLIC_EVENTS_STUDENT_EXAMS;
                }
            }
        }
        return "false";
    }

    private List<TimetableEventSearchDto> getTimetableEventsList(JpaNativeQueryBuilder qb) {
        String select = "distinct tet_id, journal_id, subject_study_period_id,"
                + " case when is_public_event then name_et else null end name_et,"
                + " case when is_public_event then name_en else null end name_en,"
                + " tet_start, tet_end, is_single_event, timetable_id, capacity_type_code, juhan_event_id, is_public_event,"
                + " is_personal, person_id, person_firstname, person_lastname,"
                + " exam_id, tet_add_info, includes_event_students, changed";
        List<?> eventResult = qb.select(select, em).getResultList();

        return StreamUtil.toMappedList(r -> {
            TimetableEventSearchDto dto = new TimetableEventSearchDto(resultAsLong(r, 0), resultAsLong(r, 1),
                    resultAsLong(r, 2), resultAsString(r, 3), resultAsString(r, 4), resultAsLocalDateTime(r, 5),
                    resultAsLocalDateTime(r, 6), resultAsBoolean(r, 7), resultAsLong(r, 8), resultAsString(r, 9),
                    resultAsLong(r, 10), resultAsBoolean(r, 11));
            if (Boolean.TRUE.equals(dto.getPublicEvent())) {
                dto.setIsPersonal(resultAsBoolean(r, 12));
                Long personalId = resultAsLong(r, 13);
                if (personalId != null) {
                    String fullname = PersonUtil.fullname(resultAsString(r, 14), resultAsString(r, 15));
                    dto.setPerson(new AutocompleteResult(personalId, fullname, fullname));
                }
                dto.setIsExam(Boolean.valueOf(resultAsLong(r, 16) != null));
                dto.setAddInfo(resultAsString(r, 17));
            }
            dto.setIncludesEventStudents(resultAsBoolean(r, 18));
            dto.setChanged(resultAsLocalDateTime(r, 19));
            return dto;
        }, eventResult);
    }

    private void setRoomsTeachersStudentsAndGroupsForSearchDto(List<TimetableEventSearchDto> timetableEventTimes,
            Boolean showOnlySubstitutes, Boolean ignoreEventFiltering) {
        List<Long> timetableEventTimeIds = StreamUtil.nullSafeList(timetableEventTimes).stream()
                .filter(t -> Boolean.TRUE.equals(ignoreEventFiltering) || Boolean.TRUE.equals(t.getPublicEvent()))
                .map(TimetableEventSearchDto::getId).collect(Collectors.toList());
        if (!timetableEventTimeIds.isEmpty()) {
            Map<Long, List<ResultObject>> teachersByTimetableEventTime = getTeachersByTimetableEventTime(
                    timetableEventTimeIds, showOnlySubstitutes);
            Map<Long, List<ResultObject>> roomsByTimetableEventTime = getRoomsByTimetableEventTime(
                    timetableEventTimeIds);
            Map<Long, List<ResultObject>> subgroupsByTimetableEventTime = getSubgroupsByTimetableEventTime(
                    timetableEventTimeIds);
            Map<Long, List<ResultObject>> groupsByTimetableEventTime = getGroupsByTimetableEventTime(
                    timetableEventTimeIds);
            Map<Long, List<ResultObject>> studentsByTimetableEventTime = getStudentsByTimetableEventTime(
                    timetableEventTimeIds);

            for (TimetableEventSearchDto dto : timetableEventTimes) {
                dto.setTeachers(StreamUtil.toMappedList(r -> new TimetableEventSearchTeacherDto(r.getObjectId(),
                        r.getFirstValue()), teachersByTimetableEventTime.get(dto.getId())));
                dto.setRooms(StreamUtil.toMappedList(r -> new TimetableEventSearchRoomDto(r.getObjectId(),
                        r.getFirstValue(), r.getSecondValue()), roomsByTimetableEventTime.get(dto.getId())));
                dto.setSubgroups(StreamUtil.toMappedList(r -> new TimetableEventSearchSubgroupDto(r.getObjectId(),
                        r.getFirstValue()), subgroupsByTimetableEventTime.get(dto.getId())));
                if (dto.getSubgroups().isEmpty()) {
                    dto.setStudentGroups(StreamUtil.toMappedList(r -> new TimetableEventSearchGroupDto(r.getObjectId(),
                            r.getFirstValue()), groupsByTimetableEventTime.get(dto.getId())));
                }
                dto.setStudents(StreamUtil.toMappedList(r -> new TimetableEventSearchStudentDto(r.getObjectId(),
                        r.getFirstValue(), r.getSecondValue()), studentsByTimetableEventTime.get(dto.getId())));
            }
        }
    }

    private void setShowStudyMaterials(List<TimetableEventSearchDto> timetableEventTimes) {
        List<Long> timetableEventTimeIds = StreamUtil.nullSafeList(timetableEventTimes).stream()
                .filter(t -> Boolean.TRUE.equals(t.getPublicEvent()))
                .map(TimetableEventSearchDto::getId).collect(Collectors.toList());
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

    public Page<TimetableEventSearchDto> search(HoisUserDetails user, TimetableEventSearchCommand criteria,
            Pageable pageable) {
        if (!UserUtil.hasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_SYNDMUS)) {
            criteria.setSingleEvent(Boolean.TRUE);
            criteria.setPersonalEvent(Boolean.TRUE);
            criteria.setUser(user.getUserId());
        }
        // do not show exams, they are managed thru separate UI
        criteria.setHideExams(Boolean.TRUE);

        Page<TimetableEventSearchDto> result = searchEvents(criteria, user.getSchoolId(), pageable, true);
        setShowStudyMaterials(result.getContent());
        return result;
    }

    private Page<TimetableEventSearchDto> searchEvents(TimetableEventSearchCommand criteria, Long schoolId,
            Pageable pageable, boolean onlyPublicEvents) {
        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(criteria, schoolId);
        if (onlyPublicEvents) {
            qb.filter("is_public_event = true");
        }

        String select = "distinct tet_id, case when is_public_event then name_et else null end name_et,"
                + " case when is_public_event then name_en else null end name_en, tet_start, tet_end, is_single_event,"
                + " timetable_id, capacity_type_code,juhan_event_id, is_public_event,"
                + " is_personal, person_id, person_firstname, person_lastname,"
                + " inserted_teacher_id, cast(tet_start as time) start_time, cast(tet_end as time) end_time";

        qb.sort(pageable.getSort() != null ? pageable.getSort() : new Sort("tet_start, cast(tet_start as time), cast(tet_end as time)"));
        Page<TimetableEventSearchDto> result = JpaQueryUtil.pagingResult(qb.select(select, em), pageable,
                () -> qb.count("count(*)", select, em, null)).map(r -> {
            TimetableEventSearchDto dto = new TimetableEventSearchDto(resultAsLong(r, 0), null, null,
                    resultAsString(r, 1), resultAsString(r, 2), resultAsLocalDateTime(r, 3),
                    resultAsLocalDateTime(r, 4), resultAsBoolean(r, 5), resultAsLong(r, 6), resultAsString(r, 7),
                    resultAsLong(r, 8), resultAsBoolean(r, 9));
            if (Boolean.TRUE.equals(dto.getPublicEvent())) {
                dto.setIsPersonal(resultAsBoolean(r, 10));
                Long personalId = resultAsLong(r, 11);
                if (personalId != null) {
                    String fullname = PersonUtil.fullname(resultAsString(r, 12), resultAsString(r, 13));
                    dto.setPerson(new AutocompleteResult(personalId, fullname, fullname));
                }
                dto.setInsertedTeacherId(resultAsLong(r, 14));
            }
            return dto;
        });
        setRoomsTeachersStudentsAndGroupsForSearchDto(result.getContent(), criteria.getShowOnlySubstitutes(), Boolean.FALSE);
        setShowStudyMaterials(result.getContent());
        return result;
    }

    public byte[] searchPrint(HoisUserDetails user, TimetableEventSearchCommand criteria) {
        return pdfService.generate("event.search.xhtml", search(user, criteria, new PageRequest(0, Integer.MAX_VALUE)));
    }

    /**
     * Timetable events search
     * @param criteria
     * @param school
     * @param pageable
     * @return list of timetable events
     */
    public Page<TimetableEventSearchDto> searchTimetable(TimetableEventSearchCommand criteria, School school,
            Pageable pageable) {
        UserUtil.throwAccessDeniedIf(!TimetableService.allowedToViewSchoolTimetable(school));

        Page<TimetableEventSearchDto> result = searchEvents(criteria, school.getId(), pageable, false);
        List<TimetableEventSearchDto> eventResultList = result.getContent();
        filterTimetableEvents(eventResultList, school, null, false);
        return result;
    }

    private Map<Long, List<ResultObject>> getTeachersByTimetableEventTime(List<Long> tetIds, Boolean showOnlySubstitutes) {
        String from = "from timetable_event_time tem " +
                "join timetable_event_teacher tet on tet.timetable_event_time_id = tem.id " +
                "join teacher t on t.id = tet.teacher_id " +
                "join person p on p.id = t.person_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("tem.id in (:tetIds)", "tetIds", tetIds);

        if (Boolean.TRUE.equals(showOnlySubstitutes)) {
            qb.filter("tet.is_substitute");
        }

        qb.sort("p.lastname, p.firstname");
        List<?> queryResult = qb.select("tem.id, t.id as teacherId, p.firstname, p.lastname", em).getResultList();
        List<ResultObject> resultObjects = StreamUtil.toMappedList(
                r -> new ResultObject(resultAsLong(r, 0), resultAsLong(r, 1), PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3))),
                queryResult);
        if (!Boolean.TRUE.equals(showOnlySubstitutes)) {
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

    private Map<Long, List<ResultObject>> getStudentsByTimetableEventTime(List<Long> tetIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event_time tem"
                + " join timetable_event_student tes on tes.timetable_event_time_id = tem.id"
                + " join student s on s.id = tes.student_id"
                + " join person p on p.id = s.person_id"
                + " left join student_group sg on sg.id = s.student_group_id");
        qb.requiredCriteria("tem.id in (:tetIds)", "tetIds", tetIds);

        qb.sort("p.lastname, p.firstname");
        List<?> data = qb.select("tem.id, s.id as studentId, p.firstname, p.lastname, sg.code", em).getResultList();
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> new ResultObject(resultAsLong(r, 0), resultAsLong(r, 1),
                                PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3)), resultAsString(r, 4)),
                        Collectors.toList())));
    }

    private Map<Long, List<ResultObject>> getSubgroupsByTimetableEventTime(List<Long> tetIds) {
        String from = "from timetable_event_subgroup tes"
                + " join timetable_event_time tet on tet.id = tes.timetable_event_time_id"
                + " join subject_study_period_subgroup sspg on sspg.id = tes.subject_study_period_subgroup_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("tet.id in (:tetIds)", "tetIds", tetIds);

        qb.sort("sspg.code");
        List<?> queryResult = qb.select("tet.id, tes.subject_study_period_subgroup_id, sspg.code", em).getResultList();
        List<ResultObject> resultObjects = StreamUtil.toMappedList(r -> new ResultObject(resultAsLong(r, 0),
                resultAsLong(r, 1), resultAsString(r, 2)), queryResult);
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
     * @param school
     * @param command
     * @param lang
     * @return calendar filename and iCalendar format calendar as a string
     */
    public TimetableCalendarDto getGroupCalendar(School school, TimetableEventSearchCommand command, Language lang) {
        TimetableByGroupDto groupTimetable = groupTimetable(school, command, true);
        return timetableGenerationService.getICal(groupTimetable, lang);
    }

    /**
     * Generate iCalendar for teacher timetable
     * @param school
     * @param command
     * @param lang
     * @return calendar filename and iCalendar format calendar as a string
     */
    public TimetableCalendarDto getTeacherCalendar(School school, TimetableEventSearchCommand command, Language lang) {
        TimetableByTeacherDto teacherTimetable = teacherTimetable(school, command, false, true);
        return timetableGenerationService.getICal(teacherTimetable, lang);
    }

    /**
     * Generate iCalendar for room timetable
     * @param school
     * @param command
     * @param lang
     * @return calendar filename and iCalendar format calendar as a string
     */
    public TimetableCalendarDto getRoomCalendar(School school, TimetableEventSearchCommand command, Language lang) {
        TimetableByRoomDto roomTimetable = roomTimetable(school, command, true);
        return timetableGenerationService.getICal(roomTimetable, lang);
    }

    /**
     * Generate iCalendar for student timetable
     * @param user
     * @param command
     * @param lang
     * @return calendar filename and iCalendar format calendar as a string
     */
    public TimetableCalendarDto getStudentCalendar(HoisUserDetails user, TimetableEventSearchCommand command,
            Language lang) {
        School school = em.getReference(School.class, user.getSchoolId());
        TimetableByStudentDto studentTimetable = studentTimetable(school, command, false);
        return timetableGenerationService.getICal(studentTimetable, lang);
    }

    /**
     * Generate iCalendar for person timetable
     * @param command
     * @param encodedPerson
     * @param lang
     * @return calendar filename and iCalendar format calendar as a string
     */
    public TimetableCalendarDto getPersonalCalendar(TimetableEventSearchCommand command, String encodedPerson,
            Language lang) {
        TimetableByDto personTimetable = personalTimetable(command, encodedPerson);
        return timetableGenerationService.getICal(personTimetable, lang);
    }

    /**
     * Generate iCalendar link for person timetable
     * @param response
     * @param encodedPerson
     * @param lang
     * @return calendar filename and iCalendar format calendar as a string
     */
    public void getPersonalCalendarLink(HttpServletResponse response, String encodedPerson, Language lang) {
        TimetableEventSearchCommand command = new TimetableEventSearchCommand();
        setPersonalTimetablePerson(command, encodedPerson);
        StudyYear studyYear = studyYearService.getCurrentStudyYear(command.getPerson().getSchool().getId());
        command.setFrom(studyYear != null ? studyYear.getStartDate() : LocalDate.now());

        try {
            TimetableByDto personTimetable = personalTimetable(command);
            String filename = String.format("%s.ics", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")));
            String iCalContent = timetableGenerationService.getICalContent(personTimetable.getTimetableEvents(), lang);
            HttpUtil.ical(response, filename, iCalContent.getBytes());
        } catch (IOException e) {
            throw new HoisException("Exception occured generating ical file", e);
        }
    }

    public Map<String, ?> searchTimetableFormData(School school) {
        if (!TimetableService.allowedToViewSchoolTimetable(school)) {
            return Collections.emptyMap();
        }
        Map<String, Object> data = new HashMap<>();

        StudentGroupAutocompleteCommand studentGroupLookup = new StudentGroupAutocompleteCommand();
        studentGroupLookup.setValid(Boolean.TRUE);
        data.put("studentGroups", autocompleteService.studentGroups(school.getId(), studentGroupLookup, false));

        TeacherAutocompleteCommand teacherLookup = new TeacherAutocompleteCommand();
        teacherLookup.setValid(Boolean.TRUE);
        data.put("teachers", autocompleteService.teachers(school.getId(), teacherLookup, false));

        RoomAutocompleteCommand roomLookup = new RoomAutocompleteCommand();
        data.put("rooms", autocompleteService.rooms(school.getId(), roomLookup));

        return data;
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
        if (te.getTimetableObject() != null) {
            timetableService.sendTimetableChangesMessages(te.getTimetableObject(),
                    Collections.singletonList(timetableEventTime),
                    te.getTimetableObject().getTimetableObjectStudentGroups());
        }

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
        autocompleteService.eventRepeatStartAndEndTimes(command.getRepeatCode(), command.getRepeatUntil(), starts, ends);
        
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
            } else if (command.getRepeatUntil() != null) {
                autocompleteService.eventRepeatStartAndEndTimes(command.getRepeatCode(), command.getRepeatUntil(), starts, ends);
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
            List<Long> teachers, List<Long> rooms, List<Long> timetableEventTimeIds, List<Long> studentGroups) {
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
                .groupBy("r_id, r_code, r_name, b_code, b_name, seats, is_study, is_dormitory, s_date, e_date")
                .sort(pageable);
        
        qb.requiredCriteria("r.school_id = :schoolId", "schoolId", schoolId);
        if (cmd.getIsDormitory() != null) {
            if (Boolean.TRUE.equals(cmd.getIsDormitory())) {
                qb.filter("r.is_dormitory = true");
            } else {
                qb.filter("(r.is_dormitory = false or r.is_dormitory is null)");
            }
        }

        qb.optionalContains("r.b_code || ' - ' || r.b_name", "building", cmd.getBuilding());
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
        List<Long> roomIds = result.getContent().stream().map(r -> resultAsLong(r, 10)).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        Map<Long, List<RoomEquipment>> equipment = JpaQueryUtil.loadRelationChilds(
                RoomEquipment.class, roomIds, em, "room", "id").stream()
                .collect(Collectors.groupingBy(re -> EntityUtil.getId(re.getRoom())));

        return result.map(r -> {
            TimetableEventRoomSearchDto dto = new TimetableEventRoomSearchDto();
            dto.setId(resultAsLong(r, 10));
            dto.setRoomCode(resultAsString(r, 0));
            dto.setRoomName(resultAsString(r, 1));
            dto.setBuildingCode(resultAsString(r, 2));
            dto.setBuildingName(resultAsString(r, 3));
            dto.setStartDate(resultAsLocalDate(r, 4));
            dto.setEndDate(resultAsLocalDate(r, 5));
            dto.setTimes(resultAsStringList(r, 6, ";"));
            dto.setPlaces(resultAsInteger(r, 7));
            dto.setIsUsedInStudy(resultAsBoolean(r, 8));
            dto.setIsDormitoryRoom(resultAsBoolean(r, 9));
            dto.setEquipment(StreamUtil.toMappedList(
                    re -> EntityUtil.bindToDto(re, new RoomEquipmentCommand()), 
                    equipment.get(resultAsLong(r, 10))));
            return dto;
        });
    }

    public List<TimetableEventSearchDto> schoolBoardEvents(School school, Long roomId) {
        TimetableEventSearchCommand criteria = new TimetableEventSearchCommand();
        criteria.setRoom(roomId);
        criteria.setSchoolBoard(Boolean.TRUE);

        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(criteria, school.getId());
        LocalDateTime currentTime = LocalDateTime.now();
        qb.requiredCriteria("date(tet_start) = :today", "today", currentTime.toLocalDate());
        qb.requiredCriteria("tet_end >= :now", "now", currentTime);
        qb.sort("tet_start, tet_end");
        qb.limit(20);

        List<TimetableEventSearchDto> eventResultList = getTimetableEventsList(qb);
        setRoomsTeachersStudentsAndGroupsForSearchDto(eventResultList, Boolean.FALSE, Boolean.TRUE);

        for (TimetableEventSearchDto event : eventResultList) {
            event.setIsOngoing(Boolean.valueOf(!event.getTimeStart().isAfter(currentTime.toLocalTime())));
            event.setStudents(null);
        }
        return eventResultList;
    }

    public void sendTeacherWeekSchedules() {
        List<Teacher> teachers = em.createQuery("select t from Teacher t" +
                " join fetch t.school s" +
                " join t.user u" +
                " where u.role.code = :roleCode" +
                " and (u.validFrom is null or u.validFrom <= :now)" +
                " and (u.validThru is null or u.validThru >= :now)" +
                " and exists(select 1" +
                " from MessageTemplate mt" +
                " where mt.school.id = u.school.id" +
                " and mt.type.code = :messageTypeCode" +
                " and (mt.validFrom is null or mt.validFrom <= :now)" +
                " and (mt.validThru is null or mt.validThru >= :now))", Teacher.class)
                .setParameter("roleCode", Role.ROLL_O.name())
                .setParameter("now", LocalDate.now())
                .setParameter("messageTypeCode", MessageType.TEATE_LIIK_TUNN_OPTEADE.name())
                .getResultList();
        teachers.forEach(this::sendTeacherWeekSchedule);
    }

    public void sendTeacherWeekSchedule(Teacher teacher) {
        TimetableEventSearchCommand cmd = new TimetableEventSearchCommand();
        cmd.setTeachers(Collections.singletonList(teacher.getId()));
        cmd.setPerson(new TimetablePersonHolder(Role.ROLL_O.name(), teacher.getId()));

        TimetableEventReport report = new TimetableEventReport();
        report.setTeacher(PersonUtil.fullname(teacher.getPerson()));
        String url = "<a href=\"" +
                frontEndUrl +
                "timetable/personalGeneralTimetable/" +
                timetableService.getPersonalUrlParam(Role.ROLL_O, teacher.getId()) +
                "\" target=\"_blank\">" +
                TranslateUtil.optionalTranslate("timetable.teacher.mailLink", Language.ET) +
                "</a>";
        report.setUrl(url);

        // First week
        LocalDate nextWeekDay = LocalDate.now().plusWeeks(1);
        report.setWeek1(getEventWeekReport(teacher.getSchool(), nextWeekDay, cmd));
        // Second week
        LocalDate nextNextWeekDay = nextWeekDay.plusWeeks(1);
        report.setWeek2(getEventWeekReport(teacher.getSchool(), nextNextWeekDay, cmd));

        // if there is no events then it should not send a message
        if (report.getNadal1Sundmused().isEmpty() && report.getNadal2Sundmused().isEmpty()) {
            return;
        }
        automaticMessageService.sendMessageToTeacher(MessageType.TEATE_LIIK_TUNN_OPTEADE, teacher, report);
    }

    private TimetableEventReport.Week getEventWeekReport(School school, LocalDate weekDay,
                                                         TimetableEventSearchCommand cmd) {
        cmd.setFrom(weekDay.with(DayOfWeek.MONDAY));
        cmd.setThru(weekDay.with(DayOfWeek.SUNDAY));

        TimetableEventReport.Week week = new TimetableEventReport.Week();
        week.setNr((short) weekDay.get(WeekFields.ISO.weekOfWeekBasedYear()));
        week.setStartDt(cmd.getFrom());
        week.setEndDt(cmd.getThru());

        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(cmd, EntityUtil.getId(school)).sort("tet_start, tet_end");
        List<TimetableEventSearchDto> eventResultList = getTimetableEventsList(qb);
        setRoomsTeachersStudentsAndGroupsForSearchDto(eventResultList, Boolean.FALSE, Boolean.FALSE);
        filterTimetableEvents(eventResultList, school,
                cmd.getPerson(), Boolean.TRUE.equals(cmd.getSchoolBoard()));

        eventResultList.sort(Comparator.comparing(TimetableEventSearchDto::getDate)
                .thenComparing(TimetableEventSearchDto::getTimeStart));

        List<TimetableEventReport.Event> events = new ArrayList<>();
        for (int i = 0; i < eventResultList.size(); i++) {
            TimetableEventSearchDto ev = eventResultList.get(i);
            TimetableEventReport.Event event = new TimetableEventReport.Event();
            event.setNr(Short.valueOf((short) (i + 1)));
            event.setName(ev.getNameEt());
            event.setStartTime(ev.getTimeStart());
            event.setEndTime(ev.getTimeEnd());
            event.setWeekDay(TranslateUtil.optionalTranslate(
                    "day." + ev.getDate().getDayOfWeek().getValue(), Language.ET));
            event.setGroups(ev.getSubgroups() == null || ev.getSubgroups().isEmpty()
                    ? ev.getStudentGroups().stream()
                    .map(TimetableEventSearchGroupDto::getCode)
                    .collect(Collectors.toList())
                    : ev.getSubgroups().stream()
                    .map(TimetableEventSearchSubgroupDto::getCode)
                    .collect(Collectors.toList()));
            event.setRooms(ev.getRooms().stream()
                    .map(r -> r.getBuildingCode() != null
                            ? r.getBuildingCode() + "-" + r.getRoomCode()
                            : r.getRoomCode())
                    .collect(Collectors.toList()));
            events.add(event);
        }
        week.setEvents(events);
        return week;
    }
}
