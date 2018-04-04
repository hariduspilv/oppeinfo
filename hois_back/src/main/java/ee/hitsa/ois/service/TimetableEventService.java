package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.teacher.TeacherAbsence;
import ee.hitsa.ois.domain.timetable.TimetableEvent;
import ee.hitsa.ois.domain.timetable.TimetableEventRoom;
import ee.hitsa.ois.domain.timetable.TimetableEventTeacher;
import ee.hitsa.ois.domain.timetable.TimetableEventTime;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.TimetableEventRepeat;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.StudentGroupAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.TeacherAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventSearchCommand;
import ee.hitsa.ois.web.commandobject.timetable.TimetableSingleEventForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.timetable.GeneralTimetableCurriculumDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByGroupDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByRoomDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByStudentDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByTeacherDto;
import ee.hitsa.ois.web.dto.timetable.TimetableCalendarDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchGroupDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchRoomDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchTeacherDto;

@Transactional
@Service
public class TimetableEventService {

    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private TimetableService timetableService;
    @Autowired
    private TimetableGenerationService timetableGenerationService;
    @Autowired
    private EntityManager em;

    public Map<String, ?> searchFormData(Long schoolId) {
        Map<String, Object> data = new HashMap<>();
        data.put("studyPeriods", autocompleteService.studyPeriods(schoolId));
        StudentGroupAutocompleteCommand studentGroupLookup = new StudentGroupAutocompleteCommand();
        data.put("studentGroups", autocompleteService.studentGroups(schoolId, studentGroupLookup));
        TeacherAutocompleteCommand teacherLookup = new TeacherAutocompleteCommand();
        teacherLookup.setValid(Boolean.TRUE);
        data.put("teachers", autocompleteService.teachers(schoolId, teacherLookup));
        data.put("singleEvent", Boolean.TRUE);
        return data;
    }

    public TimetableEvent createEvent(TimetableSingleEventForm form, Long schoolId) {
        TimetableEvent te = new TimetableEvent();
        te = bindSingleEventFormToEvent(form, te, schoolId);
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
        tet.setStart(form.getDate().atTime(form.getStartTime().toLocalTime()));
        tet.setEnd(form.getDate().atTime(form.getEndTime().toLocalTime()));
        tet.getTimetableEvent().setName(form.getName());
        if(Boolean.TRUE.equals(form.getRepeat())) {
            tet.getTimetableEvent().setRepeatCode(em.getReference(Classifier.class, form.getRepeatCode()));
            tet.getTimetableEvent().setStart(tet.getStart());
            tet.getTimetableEvent().setEnd(tet.getEnd());
            addTimetableEventTimes(tet.getTimetableEvent(), form);
        } else {
            bindRoomsToTimetableEvent(tet, form);
            bindTeachersToTimetableEvent(tet, form);
        }
        return tet.getTimetableEvent();
    }

    private TimetableEvent bindSingleEventFormToEvent(TimetableSingleEventForm form, TimetableEvent te, Long schoolId) {
        te.setStart(form.getDate().atTime(form.getStartTime().toLocalTime()));
        te.setEnd(form.getDate().atTime(form.getEndTime().toLocalTime()));
        te.setSchool(em.getReference(School.class, schoolId));
        te.setName(form.getName());
        te.setConsiderBreak(Boolean.FALSE);
        te.setRepeatCode(Boolean.TRUE.equals(form.getRepeat()) ? em.getReference(Classifier.class, form.getRepeatCode())
                : em.getReference(Classifier.class, TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_EI.name()));
        addTimetableEventTimes(te, form);
        return te;
    }


    public void createEvents(TeacherAbsence absence) {
        TimetableSingleEventForm form = new TimetableSingleEventForm();
        form.setStartTime(absence.getStartDate().atTime(LocalTime.of(7, 00)));
        form.setEndTime(absence.getEndDate().atTime(LocalTime.of(23, 00)));
        form.setRepeat(Boolean.FALSE);
        form.setName(absence.getReason());
        form.setTeachers(new ArrayList<>(Arrays.asList(new AutocompleteResult(EntityUtil.getId(absence.getTeacher()), null, null))));
        
        for(LocalDate date = absence.getStartDate(); date.isBefore(absence.getEndDate()); date = date.plusDays(1)) {
            form.setDate(date);
            createEvent(form, EntityUtil.getId(absence.getTeacher().getSchool()));
        }
    }

    private void bindRoomsToTimetableEvent(TimetableEventTime timetableEventTime, TimetableSingleEventForm form) {
        EntityUtil.bindEntityCollection(timetableEventTime.getTimetableEventRooms(), r -> EntityUtil.getId(r.getRoom()),
                form.getRooms(), r -> r.getId(), r -> {
                    TimetableEventRoom ter = new TimetableEventRoom();
                    ter.setRoom(em.getReference(Room.class, r.getId()));
                    ter.setTimetableEventTime(timetableEventTime);
                    return ter;
                });
    }

    private void bindTeachersToTimetableEvent(TimetableEventTime timetableEventTime, TimetableSingleEventForm form) {
        EntityUtil.bindEntityCollection(timetableEventTime.getTimetableEventTeachers(),
                r -> EntityUtil.getId(r.getTeacher()), form.getTeachers(), r -> r.getId(), t -> {
                    TimetableEventTeacher tet = new TimetableEventTeacher();
                    tet.setTeacher(em.getReference(Teacher.class, t.getId()));
                    tet.setTimetableEventTime(timetableEventTime);
                    return tet;
                });
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
        setRoomsTeachersAndGroupsForSearchDto(eventResultList);
        setShowStudyMaterials(eventResultList);
        eventResultList = filterTimetableSingleEvents(eventResultList);
        
        GeneralTimetableCurriculumDto generalTimetableCurriculum = getGeneralTimetableCurriculum(command.getStudentGroups().get(0));
        
        return new TimetableByGroupDto(getStudyPeriods(schoolId, command.getFrom(), command.getThru()), eventResultList, generalTimetableCurriculum);
    }
    
    /**
     * Get teacher's timetable for view.
     * @param command
     * @return TimetableByTeacherDto
     */
    public TimetableByTeacherDto teacherTimetable(TimetableEventSearchCommand command, Long schoolId) {
        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(command, schoolId).sort("tet.start,tet.end");
        List<TimetableEventSearchDto> eventResultList = getTimetableEventsList(qb);
        setRoomsTeachersAndGroupsForSearchDto(eventResultList);
        setShowStudyMaterials(eventResultList);
        eventResultList = filterTimetableSingleEvents(eventResultList);

        Query q = em.createNativeQuery("select t.id, p.firstname, p.lastname from teacher t join person p on t.person_id=p.id where t.id=?1");
        q.setParameter(1, command.getTeachers().get(0));
        Object teacher = q.getSingleResult();

        return new TimetableByTeacherDto(getStudyPeriods(schoolId, command.getFrom(), command.getThru()), eventResultList, resultAsLong(teacher, 0), resultAsString(teacher, 1), resultAsString(teacher, 2));
    }
    
    /**
     * Get student's timetable for view.
     * @param command
     * @return TimetableByStudentDto
     */
    public TimetableByStudentDto studentTimetable(TimetableEventSearchCommand command, Long schoolId) {
        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(command, schoolId);
        List<TimetableEventSearchDto> eventResultList = getTimetableEventsList(qb);
        setRoomsTeachersAndGroupsForSearchDto(eventResultList);
        setShowStudyMaterials(eventResultList);
        eventResultList = filterTimetableSingleEvents(eventResultList);

        Query studentQuery = em.createNativeQuery("select s.id, p.firstname, p.lastname from student s join person p on s.person_id=p.id where s.id=?1");
        studentQuery.setParameter(1, command.getStudent());
        Object student = studentQuery.getSingleResult();

        return new TimetableByStudentDto(getStudyPeriods(schoolId, command.getFrom(), command.getThru()), eventResultList, resultAsLong(student, 0), resultAsString(student, 1), resultAsString(student, 2));
    }
    
    /**
     * Get room's timetable for view.
     * @param command
     * @return timetable with room's info and timetable's events
     */
    public TimetableByRoomDto roomTimetable(TimetableEventSearchCommand command, Long schoolId) {
        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(command, schoolId);
        List<TimetableEventSearchDto> eventResultList = getTimetableEventsList(qb);
        setRoomsTeachersAndGroupsForSearchDto(eventResultList);
        setShowStudyMaterials(eventResultList);
        eventResultList = filterTimetableSingleEvents(eventResultList);
        
        Query q = em.createNativeQuery("select r.id, r.code as roomCode, b.code as buildingCode from room r join building b on r.building_id=b.id where r.id=?1");
        q.setParameter(1, command.getRoom());
        Object room = q.getSingleResult();
        
        return new TimetableByRoomDto(getStudyPeriods(schoolId, command.getFrom(), command.getThru()), eventResultList, resultAsLong(room, 0), resultAsString(room, 1), resultAsString(room, 2));
    }
    
    private static List<TimetableEventSearchDto> filterTimetableSingleEvents(List<TimetableEventSearchDto> events) {
        List<TimetableEventSearchDto> filteredEvents = events;
        
        HoisUserDetails user = TimetableService.userFromPrincipal();
        if(user != null) {
            if (!user.isSchoolAdmin() && !user.isTeacher()) {
                filteredEvents = hideSingleEventsData(events);
            } else if (user.isTeacher()) {
                filteredEvents = hideOtherTeachersSingleEventsData(events, user.getTeacherId());
            }
        } else {
            filteredEvents = hideSingleEventsData(events);
        }
        return filteredEvents;
    }
    
    private static List<TimetableEventSearchDto> hideSingleEventsData(List<TimetableEventSearchDto> events) {
        for (TimetableEventSearchDto event : events) {
            if (Boolean.TRUE.equals(event.getSingleEvent())) {
                hideSingleEventData(event);
            }
        }
        return events;
    }
    
    private static List<TimetableEventSearchDto> hideOtherTeachersSingleEventsData(List<TimetableEventSearchDto> events, Long teacherId) {
        for (TimetableEventSearchDto event : events) {
            if (Boolean.TRUE.equals(event.getSingleEvent()) && !isTeachersEvent(event.getTeachers(), teacherId)) {
                hideSingleEventData(event);
            }
        }
        return events;
    }
    
    private static void hideSingleEventData(TimetableEventSearchDto event) {
        event.setNameEn(null);
        event.setNameEt(null);
        event.setRooms(null);
        event.setTeachers(null);
        event.setStudentGroups(null);
        event.setPublicEvent(Boolean.FALSE);
    }
    
    private static boolean isTeachersEvent(List<TimetableEventSearchTeacherDto> eventTeachers, Long teacherId) {
        for (TimetableEventSearchTeacherDto eventTeacher : eventTeachers) {
            if (eventTeacher.getId().equals(teacherId)) {
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
                + " left join timetable_object tobj on te.timetable_object_id = tobj.id"
                + " left join (timetable_object_student_group tog"
                + " join student_group sg on sg.id = tog.student_group_id ) on tobj.id = tog.timetable_object_id"
                + " left join timetable t on tobj.timetable_id = t.id left join journal j on tobj.journal_id = j.id"
                + " left join (subject_study_period ssp"
                + " join subject subj on subj.id = ssp.subject_id) on ssp.id = tobj.subject_study_period_id";
                
        if (criteria.getStudent() != null && Boolean.TRUE.equals(criteria.getVocational())) {
            from += " left join  journal_student js  on js.journal_id=j.id"
                    + " left join student s on s.id=js.student_id";
        } else if (criteria.getStudent() != null && Boolean.TRUE.equals(criteria.getHigher())) {
            from += " left join declaration_subject decls on decls.subject_study_period_id=ssp.id"
                    + " left join declaration decl on decls.declaration_id=decl.id"
                    + " left join student s on decl.student_id=s.id";
        }
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.optionalContains("te.name", "eventName", criteria.getName());
        
        qb.optionalCriteria("t.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        qb.optionalCriteria("tog.student_group_id in (:studentGroup)", "studentGroup", criteria.getStudentGroups());
        qb.optionalCriteria("tet.start >= :start", "start", criteria.getFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("tet.end <= :end", "end", criteria.getThru(), DateUtils::lastMomentOfDay);
        qb.optionalCriteria("(t.id in (:timetable) or tobj.id is null)", "timetable", criteria.getTimetables());
        qb.optionalCriteria("s.id = :student", "student", criteria.getStudent());
        qb.requiredCriteria("(te.school_id = :schoolId or t.school_id = :schoolId)", "schoolId", schoolId);
        qb.requiredCriteria("(te.timetable_object_id is null or t.status_code in (:shownStatusCodes))", "shownStatusCodes", timetableService.shownStatusCodes());
        
        if(Boolean.TRUE.equals(criteria.getSingleEvent())) {
            qb.filter("tobj.id is null");
        } else if (Boolean.FALSE.equals(criteria.getSingleEvent())){
            qb.filter("tobj.id is not null");
        }
        

        //TODO: param query
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
        return qb;
    }
    
    private List<TimetableEventSearchDto> getTimetableEventsList(JpaNativeQueryBuilder qb) {
        String select = "distinct tet.id, j.id as journal_id, ssp.id as subject_study_period_id, coalesce(te.name, j.name_et, subj.name_et) as name_et, coalesce(te.name, j.name_et, subj.name_en) as name_en,"
                + " tet.start, tet.end, te.consider_break, tobj.id as single_event, t.id as timetableId";
        List<?> eventResult = qb.select(select, em).getResultList();
        
        List<TimetableEventSearchDto> eventResultList = StreamUtil.toMappedList(
                r -> new TimetableEventSearchDto(resultAsLong(r, 0), resultAsLong(r, 1), resultAsLong(r, 2), resultAsString(r, 3), resultAsString(r, 4),
                        resultAsLocalDateTime(r, 5).toLocalDate(), resultAsLocalDateTime(r, 5).toLocalTime(),
                        resultAsLocalDateTime(r, 6).toLocalTime(), resultAsBoolean(r, 7), 
                        Boolean.valueOf(resultAsLong(r, 8) == null), resultAsLong(r, 9)), eventResult);
        return eventResultList;
    }

    private void setRoomsTeachersAndGroupsForSearchDto(List<TimetableEventSearchDto> timetableEventTimes) {
        List<Long> timetableEventTimeIds = StreamUtil.toMappedList(r -> r.getId(), timetableEventTimes);
        if (!timetableEventTimeIds.isEmpty()) {
            Map<Long, List<ResultObject>> teachersByTimetableEventTime = getTeachersByTimetableEventTime(
                    timetableEventTimeIds);
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

    public Page<TimetableEventSearchDto> search(TimetableEventSearchCommand criteria, Pageable pageable, Long schoolId) {
        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(criteria, schoolId).sort(pageable);
        String select = "tet.id, coalesce(te.name, j.name_et, subj.name_et) as name_et, coalesce(te.name, j.name_et, subj.name_en) as name_en,"
                    + " tet.start, tet.end, te.consider_break, tobj.id as single_event, t.id as timetableId";
        Page<TimetableEventSearchDto> result = JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new TimetableEventSearchDto(resultAsLong(r, 0), null, null, resultAsString(r, 1), resultAsString(r, 2),
                    resultAsLocalDateTime(r, 3).toLocalDate(), resultAsLocalDateTime(r, 3).toLocalTime(),
                    resultAsLocalDateTime(r, 4).toLocalTime(), resultAsBoolean(r, 5), Boolean.valueOf(resultAsLong(r, 6) == null), resultAsLong(r, 7));
        });
        setRoomsTeachersAndGroupsForSearchDto(result.getContent());
        setShowStudyMaterials(result.getContent());
        return result;
    }
    
    /**
     * Timetable events search
     * @param criteria
     * @return list of timetable events
     */
    public List<TimetableEventSearchDto> searchTimetable(TimetableEventSearchCommand criteria, Long schoolId) {
        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(criteria, schoolId);
        List<TimetableEventSearchDto> eventResultList = getTimetableEventsList(qb);
        setRoomsTeachersAndGroupsForSearchDto(eventResultList);
        setShowStudyMaterials(eventResultList);
        return eventResultList;
    }

    private Map<Long, List<ResultObject>> getTeachersByTimetableEventTime(List<Long> tetIds) {
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

        qb.requiredCriteria("tem.id in (:tetIds)", "tetIds", tetIds);

        List<?> queryResult = qb.select("distinct tem.id, t.id as teacherId, p.firstname, p.lastname", em).getResultList();
        List<ResultObject> resultObjects = StreamUtil.toMappedList(
                r -> new ResultObject(resultAsLong(r, 0), resultAsLong(r, 1), PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3))),
                queryResult);
        return resultObjects.stream().collect(Collectors.groupingBy(r -> r.getTimetableEventId()));
    }

    private Map<Long, List<ResultObject>> getRoomsByTimetableEventTime(List<Long> tetIds) {
        String from = "from timetable_event_room ter" 
                + " join room r on r.id = ter.room_id"
                + " join building b on b.id = r.building_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.requiredCriteria("ter.timetable_event_time_id in (:tetIds)", "tetIds", tetIds);

        List<?> queryResult = qb
                .select("ter.timetable_event_time_id, r.id as roomId, r.code as room_code, b.code as building_code", em)
                .getResultList();
        
        List<ResultObject> resultObjects = StreamUtil
                .toMappedList(r -> new ResultObject(resultAsLong(r, 0), resultAsLong(r, 1), resultAsString(r, 2), resultAsString(r, 3)), queryResult);
        return resultObjects.stream().collect(Collectors.groupingBy(r -> r.getTimetableEventId()));
    }
    
    private Map<Long, List<ResultObject>> getGroupsByTimetableEventTime(List<Long> tetIds) {
        String from ="from timetable_event_time tem"
                + " join timetable_event te on tem.timetable_event_id = te.id"
                + " join timetable_object tobj on te.timetable_object_id = tobj.id"
                + " join timetable_object_student_group tog on tobj.id = tog.timetable_object_id"
                + " join student_group sg on sg.id = tog.student_group_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        
        qb.requiredCriteria("tem.id in (:tetIds)", "tetIds", tetIds);
        
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
        TimetableByTeacherDto teacherTimetable = teacherTimetable(command, schoolId);
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
    public TimetableCalendarDto getStudentCalendar(TimetableEventSearchCommand command, String language, Long schoolId) {
        TimetableByStudentDto studentTimetable = studentTimetable(command, schoolId);
        return timetableGenerationService.getICal(studentTimetable, getLanguage(language));
    }
    
    /**
     * Generate iCalendar for search result
     * @param command
     * @param lang
     * @return calendar filename and iCalendar format calendar as a string
     */
    public TimetableCalendarDto getSearchCalendar(TimetableEventSearchCommand command, String language, Long schoolId) {
        List<TimetableEventSearchDto> searchResult = searchTimetable(command, schoolId);
        return timetableGenerationService.getICal(searchResult, getLanguage(language));
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
    
    public void isAdminOrIsTeachersEvent(HoisUserDetails user, TimetableEventTime eventTime) {
        List<Long> teacherIds = StreamUtil.toMappedList(tet -> tet.getTeacher().getId(), eventTime.getTimetableEventTeachers());
        
        if (!UserUtil.isSchoolAdmin(user, eventTime.getTimetableEvent().getSchool()) && !isTeachersEvent(user, teacherIds)) {
            throw new AssertionFailedException("User is not school admin in given school or it is not teacher's event");
        }
    }
    
    public void isAdminOrIsTeachersEvent(HoisUserDetails user, Long schoolId, List<AutocompleteResult> teachers) {
        List<Long> teacherIds = StreamUtil.toMappedList(t -> t.getId(), teachers);
        
        if (!UserUtil.isSchoolAdmin(user, em.getReference(School.class, schoolId)) && !isTeachersEvent(user, teacherIds)) {
            throw new AssertionFailedException("User is not school admin in given school or it is not teacher's event");
        }
    }

    public boolean isTeachersEvent(HoisUserDetails user, List<Long> teacherIds) {
        if (!user.isTeacher()) {
            return false;
        }
        if (teacherIds != null && teacherIds.contains(user.getTeacherId())) {
            return true;
        }
        return false;
    }
    
    public TimetableSingleEventForm get(TimetableEventTime eventTime) {
        return TimetableSingleEventForm.of(eventTime);
    }
    

    public TimetableSingleEventForm getTimetableSingleEventForm(TimetableEvent createEvent) {
        return TimetableSingleEventForm.of(createEvent.getTimetableEventTimes().stream().sorted(Comparator.comparing(TimetableEventTime::getStart)).findFirst());
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
}
