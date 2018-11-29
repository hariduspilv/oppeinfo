package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.LessonTime;
import ee.hitsa.ois.domain.timetable.Timetable;
import ee.hitsa.ois.domain.timetable.TimetableEvent;
import ee.hitsa.ois.domain.timetable.TimetableEventRoom;
import ee.hitsa.ois.domain.timetable.TimetableEventTeacher;
import ee.hitsa.ois.domain.timetable.TimetableEventTime;
import ee.hitsa.ois.domain.timetable.TimetableObject;
import ee.hitsa.ois.domain.timetable.TimetableObjectStudentGroup;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.TimetableEventRepeat;
import ee.hitsa.ois.enums.TimetableStatus;
import ee.hitsa.ois.enums.TimetableType;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.message.TimetableChanged;
import ee.hitsa.ois.repository.TimetableObjectRepository;
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.SchoolCapacityTypeCommand;
import ee.hitsa.ois.web.commandobject.TimetableCopyForm;
import ee.hitsa.ois.web.commandobject.TimetableRoomAndTimeForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEditForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventHigherForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventVocationalForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableManagementSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.OisFileDto;
import ee.hitsa.ois.web.dto.RoomDto;
import ee.hitsa.ois.web.dto.timetable.DateRangeDto;
import ee.hitsa.ois.web.dto.timetable.GroupTimetableDto;
import ee.hitsa.ois.web.dto.timetable.HigherTimetablePlanDto;
import ee.hitsa.ois.web.dto.timetable.HigherTimetableStudentGroupCapacityDto;
import ee.hitsa.ois.web.dto.timetable.HigherTimetableStudentGroupDto;
import ee.hitsa.ois.web.dto.timetable.LessonTimeDto;
import ee.hitsa.ois.web.dto.timetable.RoomTimetableDto;
import ee.hitsa.ois.web.dto.timetable.SubjectTeacherPairDto;
import ee.hitsa.ois.web.dto.timetable.TeacherTimetableDto;
import ee.hitsa.ois.web.dto.timetable.TimetableCurriculumDto;
import ee.hitsa.ois.web.dto.timetable.TimetableDatesDto;
import ee.hitsa.ois.web.dto.timetable.TimetableDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventDto;
import ee.hitsa.ois.web.dto.timetable.TimetableJournalDto;
import ee.hitsa.ois.web.dto.timetable.TimetableManagementSearchDto;
import ee.hitsa.ois.web.dto.timetable.TimetableStudentGroupCapacityDto;
import ee.hitsa.ois.web.dto.timetable.TimetableStudentGroupDto;
import ee.hitsa.ois.web.dto.timetable.TimetableStudentStudyYearWeekDto;
import ee.hitsa.ois.web.dto.timetable.TimetableStudyYearWeekDto;
import ee.hitsa.ois.web.dto.timetable.VocationalTimetablePlanDto;
import ee.hitsa.ois.xml.exportTimetable.ClassTeacher;
import ee.hitsa.ois.xml.exportTimetable.Classes;
import ee.hitsa.ois.xml.exportTimetable.Document;
import ee.hitsa.ois.xml.exportTimetable.General;
import ee.hitsa.ois.xml.exportTimetable.Lesson;
import ee.hitsa.ois.xml.exportTimetable.LessonClasses;
import ee.hitsa.ois.xml.exportTimetable.LessonRoom;
import ee.hitsa.ois.xml.exportTimetable.LessonSubject;
import ee.hitsa.ois.xml.exportTimetable.LessonTeacher;
import ee.hitsa.ois.xml.exportTimetable.Lessons;
import ee.hitsa.ois.xml.exportTimetable.Rooms;
import ee.hitsa.ois.xml.exportTimetable.Subjects;
import ee.hitsa.ois.xml.exportTimetable.Teachers;
import ee.hitsa.ois.xml.exportTimetable.TimePeriod;
import ee.hitsa.ois.xml.exportTimetable.TimePeriods;

@Transactional
@Service
public class TimetableService {
    private static final long LESSON_LENGTH = 45;
    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private AutomaticMessageService automaticMessageService;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private EntityManager em;
    @Autowired
    private TimetableObjectRepository timetableObjectRepository;

    private static final List<String> PUBLIC_TIMETABLES = EnumUtil.toNameList(TimetableStatus.TUNNIPLAAN_STAATUS_P);
    private static final List<String> TEACHER_TIMETABLES = EnumUtil
            .toNameList(TimetableStatus.TUNNIPLAAN_STAATUS_P, TimetableStatus.TUNNIPLAAN_STAATUS_K);
    private static final List<String> ADMIN_TIMETABLES = EnumUtil.toNameList(TimetableStatus.TUNNIPLAAN_STAATUS_P,
            TimetableStatus.TUNNIPLAAN_STAATUS_K, TimetableStatus.TUNNIPLAAN_STAATUS_A,
            TimetableStatus.TUNNIPLAAN_STAATUS_S);
    private DateTimeFormatter documentDateFormatHois = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private DateTimeFormatter documentDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
    private DateTimeFormatter documentTimeFormat = DateTimeFormatter.ofPattern("HHmmss");
    private DateTimeFormatter documentTimeFormatShort = DateTimeFormatter.ofPattern("HHmm");
    private DateTimeFormatter documentDateFormatYear = DateTimeFormatter.ofPattern("yyyy");

    public TimetableDto get(HoisUserDetails user, Timetable timetable) {
        TimetableDto dto = new TimetableDto();
        dto.setId(EntityUtil.getId(timetable));
        dto.setStudyYears(autocompleteService.studyYears(user.getSchoolId()));
        dto.setStudyPeriods(autocompleteService.studyPeriods(user.getSchoolId()));
        dto.setCurrentStudyPeriod(studyYearService.getCurrentStudyPeriod(user.getSchoolId()));
        dto.setCode(Boolean.TRUE.equals(timetable.getIsHigher()) ? TimetableType.TUNNIPLAAN_LIIK_H.name()
                : TimetableType.TUNNIPLAAN_LIIK_V.name());
        dto.setStartDate(timetable.getStartDate());
        dto.setEndDate(timetable.getEndDate());
        dto.setHigher(timetable.getIsHigher());
        dto.setEditable(isTimetableEditable(timetable));
        return dto;
    }

    public TimetableDto getForView(Timetable timetable) {
        TimetableDto dto = new TimetableDto();
        dto.setId(EntityUtil.getId(timetable));
        dto.setStartDate(timetable.getStartDate());
        dto.setEndDate(timetable.getEndDate());
        if (Boolean.TRUE.equals(timetable.getIsHigher())) {
            dto.setPairs(getPairsforTimetable(timetable));
            dto.setCurriculums(getHigherStudentGroupsByCurriculum(timetable));
        } else {
            dto.setCurriculums(getStudentGroupsByCurriculum(timetable));
        }
        dto.setStatus(EntityUtil.getCode(timetable.getStatus()));
        dto.setHigher(timetable.getIsHigher());
        return dto;
    }
    

    public HigherTimetablePlanDto getHigherPlan(Timetable timetable) {
        HigherTimetablePlanDto dto = new HigherTimetablePlanDto();
        dto.setStartDate(timetable.getStartDate());
        dto.setEndDate(timetable.getEndDate());
        dto.setSubjectTeacherPairs(getPairsforTimetable(timetable));
        dto.setStudentGroups(getHigherStudentGroups(timetable));
        dto.setStudentGroupCapacities(
                getCapacitiesForHigherPlanning(timetable, StreamUtil.toMappedList(r -> (r.getId()), dto.getStudentGroups()),
                        StreamUtil.toMappedList(r -> (r.getId()), dto.getSubjectTeacherPairs())));
        dto.setLessonTimes(getLessonTimesForPlanning(timetable));
        dto.setStudentGroups(getPlannedLessonsForHigherTimetable(timetable, dto.getStudentGroups()));
        dto.setSubjectTeacherPairs(getPlannedLessonsForHigherTimetableSsp(timetable, dto.getSubjectTeacherPairs()));
        dto.setWeeks(getTimetableWeekRanges(timetable));
        dto.setBuildings(autocompleteService.buildings(EntityUtil.getId(timetable.getSchool())));
        dto.setTimetableCapacities(higherTimetableCapacities(timetable, dto.getStudentGroupCapacities()));
        return dto;
    }

    public Timetable confirm(Timetable timetable) {
        setStatus(timetable, TimetableStatus.TUNNIPLAAN_STAATUS_K);
        return EntityUtil.save(timetable, em);
    }

    public Timetable publicize(Timetable timetable) {
        setStatus(timetable, TimetableStatus.TUNNIPLAAN_STAATUS_P);
        return EntityUtil.save(timetable, em);
    }

    private Timetable setStatus(Timetable timetable, TimetableStatus status) {
        timetable.setStatus(em.getReference(Classifier.class, status.name()));
        return timetable;
    }

    private static Boolean isTimetableEditable(Timetable timetable) {
        if(!ClassifierUtil.equals(TimetableStatus.TUNNIPLAAN_STAATUS_S, timetable.getStatus())) {
            return Boolean.FALSE;
        }
        for (TimetableObject to : timetable.getTimetableObjects()) {
            if (!to.getTimetableEvents().isEmpty()) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    private List<SubjectTeacherPairDto> getPlannedLessonsForHigherTimetableSsp(Timetable timetable,
            List<SubjectTeacherPairDto> pairs) {
        String from = "from timetable_event_time tet"
                + " join timetable_event te on tet.timetable_event_id = te.id"
                + " join timetable_object too on te.timetable_object_id = too.id"
                + " join subject_study_period ssp on ssp.id = too.subject_study_period_id"
                + " join subject s on s.id = ssp.subject_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.requiredCriteria("too.timetable_id = :timetableId", "timetableId", EntityUtil.getId(timetable));

        qb.filter("too.id not in (select tosg.timetable_object_id from timetable_object_student_group tosg where tosg.timetable_object_id = too.id)");

        String select = "tet.id as timetable_event_id, tet.start, tet.end, te.capacity_type_code, too.subject_study_period_id,s.code as subject_code, s.name_et";
        List<?> data = qb.select(select, em).getResultList();

        // first get timetable events into a list and add rooms then group by subject study periods
        List<TimetableEventDto> timetableEventTimes = StreamUtil
                .toMappedList(r -> new TimetableEventDto(resultAsLong(r, 0), resultAsLocalDateTime(r, 1),
                        resultAsLocalDateTime(r, 2), resultAsString(r, 3), resultAsString(r, 5),
                        resultAsString(r, 5), resultAsLong(r, 4)), data);
        if (!timetableEventTimes.isEmpty()) {
            timetableEventTimes = addRoomsListToEvents(timetableEventTimes);
            timetableEventTimes = addTeachersListToEvents(timetableEventTimes);
        }

        Map<Long, List<TimetableEventDto>> eventsBySubjectStudyPeriods = timetableEventTimes.stream()
                .collect(Collectors.groupingBy(r -> r.getSubjectStudyPeriod()));

        if (!eventsBySubjectStudyPeriods.isEmpty()) {
            for (SubjectTeacherPairDto pairDto : pairs) {
                pairDto.setLessons(eventsBySubjectStudyPeriods.get(pairDto.getId()));
            }
        }

        return pairs;
    }

    private List<HigherTimetableStudentGroupDto> getPlannedLessonsForHigherTimetable(Timetable timetable,
            List<HigherTimetableStudentGroupDto> studentGroups) {
        String from = "from timetable_event_time tet"
                + " join timetable_event te on tet.timetable_event_id = te.id"
                + " join timetable_object too on te.timetable_object_id = too.id"
                + " join timetable_object_student_group tosg on tosg.timetable_object_id = too.id"
                + " join student_group sg on sg.id = tosg.student_group_id"
                + " join subject_study_period ssp on ssp.id = too.subject_study_period_id"
                + " join subject s on s.id = ssp.subject_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.requiredCriteria("too.timetable_id = :timetableId", "timetableId", EntityUtil.getId(timetable));
        //TODO: hardcoded language flag
        String select = "tet.id as timetable_event_id, tet.start, tet.end, te.capacity_type_code, sg.id as student_group_id, s.code as subject_code, ssp.id as subject_study_period_id, s.name_et";
        List<?> data = qb.select(select, em).getResultList();
        
        //first get timetable events into a list and add rooms then group by student groups
        List<TimetableEventDto> timetableEventTimes = StreamUtil.toMappedList(
                r -> new TimetableEventDto(resultAsLong(r, 0), resultAsLocalDateTime(r, 1), resultAsLocalDateTime(r, 2),
                        resultAsString(r, 3), resultAsString(r, 5), resultAsLong(r, 4), resultAsString(r, 7), resultAsLong(r, 6)),
                data);
        if (!timetableEventTimes.isEmpty()) {
            timetableEventTimes = addRoomsListToEvents(timetableEventTimes);
            timetableEventTimes = addTeachersListToEvents(timetableEventTimes);
        }
        
        Map<Long, List<TimetableEventDto>> eventsByStudentGroups = timetableEventTimes.stream()
                .collect(Collectors.groupingBy(r -> r.getStudentGroup()));

        if (!eventsByStudentGroups.isEmpty()) {
            for (HigherTimetableStudentGroupDto studentGroupDto : studentGroups) {
                studentGroupDto.setLessons(eventsByStudentGroups.get(studentGroupDto.getId()));
            }
        }

        return studentGroups;
    }

    public VocationalTimetablePlanDto getVocationalPlan(Timetable timetable) {
        VocationalTimetablePlanDto dto = new VocationalTimetablePlanDto();
        dto.setStartDate(timetable.getStartDate());
        dto.setEndDate(timetable.getEndDate());
        dto.setStudentGroups(getVocationalStudentGroups(timetable));
        dto.setStudentGroupCapacities(getCapacitiesForVocationalPlanning(
                StreamUtil.toMappedList(r -> (r.getId()), dto.getStudentGroups()), timetable));
        dto.setJournals(
                getJournalsForPlanning(StreamUtil.toMappedList(r -> (r.getId()), dto.getStudentGroups()), timetable));
        dto.setLessonTimes(getLessonTimesForPlanning(timetable));
        dto.setPlannedLessons(getPlannedLessonsForVocationalTimetable(timetable));
        dto.setTimetableCapacities(vocationalTimetableCapacities(timetable, dto.getStudentGroupCapacities()));
        return dto;
    }

    public Map<String, Object> managementSearchFormData(Long schoolId) {
        Map<String, Object> data = new HashMap<>();
        data.put("studyYears", autocompleteService.studyYears(schoolId));
        data.put("studyPeriods", autocompleteService.studyPeriods(schoolId));
        data.put("currentStudyPeriod", studyYearService.getCurrentStudyPeriod(schoolId));
        SchoolType type = schoolService.schoolType(schoolId);
        data.put("higher", Boolean.valueOf(type.isHigher()));
        data.put("vocational", Boolean.valueOf(type.isVocational()));
        return data;
    }

    public Timetable saveVocationalEvent(TimetableEventVocationalForm form) {
        Timetable timetable = em.getReference(Timetable.class, form.getTimetable());
        List<TimetableStudentGroupDto> studentGroups = getStudentGroups(timetable, form.getJournal());
        Journal journal = em.getReference(Journal.class, form.getJournal());
        TimetableObject timetableObject = saveVocationalTimetableObject(timetable, journal, studentGroups);
        LessonTime lessonTime = em.getReference(LessonTime.class, form.getLessonTime());
        TimetableEvent timetableEvent = saveVocationalTimetableEvent(timetableObject, lessonTime, form);
        saveVocationalTimetableEventTime(timetableEvent, journal);
        sendTimetableChangesMessages(timetableObject);
        return EntityUtil.save(timetable, em);
    }

    private List<TimetableEventDto> addTeachersListToEvents(List<TimetableEventDto> timetableEvents) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event_teacher tett"
                + " join timetable_event_time tet on tet.id = tett.timetable_event_time_id"
                + " join teacher t on t.id = tett.teacher_id"
                + " join person p on p.id = t.person_id");

        qb.requiredCriteria("tet.id in (:timetableEventIds)", "timetableEventIds",
                timetableEvents.stream().map(r -> r.getId()).collect(Collectors.toSet()));

        List<?> data = qb.select("tet.id, t.id as teacher_id, p.firstname, p.lastname", em).getResultList();

        Map<Long, List<Long>> teachersByTimetableEventTimes = data.stream().collect(Collectors
                .groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> resultAsLong(r, 1), Collectors.toList())));
        
        Map<Long, List<String>> teachersNamesByTimetableEventTimes = data.stream().collect(Collectors
                .groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3)), Collectors.toList())));

        for (TimetableEventDto dto : timetableEvents) {
            List<Long> teachers = teachersByTimetableEventTimes.get(dto.getId());
            if(teachers != null) {
                dto.setTeachers(teachers);
            }
            
            List<String> teacherNames = teachersNamesByTimetableEventTimes.get(dto.getId());
            if(teacherNames != null) {
                dto.setTeacherNames(teacherNames);
            }
        }

        return timetableEvents;
    }

    private Map<Long, List<AutocompleteResult>> getTeachersForSubjectStudyPeriods(Set<Long> subjectStudyPeriods) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period ssp"
                + " join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id"
                + " join teacher t on t.id = sspt.teacher_id"
                + " join person p on p.id = t.person_id");
        
        qb.requiredCriteria("ssp.id in (:sspIds)", "sspIds", subjectStudyPeriods);
        
        List<?> data = qb.select("ssp.id, p.firstname, p.lastname, t.id as teacher_id", em).getResultList();
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> new AutocompleteResult(resultAsLong(r, 3), PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)), null), Collectors.toList())));
    }

    public Timetable cloneTimetable(HoisUserDetails user, TimetableCopyForm form) {
        Timetable originalTimetable = em.getReference(Timetable.class, form.getOriginalTimetable());
        Timetable copyTimetable;
        if (form.getId() != null) {
            copyTimetable = em.getReference(Timetable.class, form.getId());
        } else {
            TimetableEditForm editForm = new TimetableEditForm();
            editForm.setStartDate(form.getStart());
            int week = 6;
            editForm.setEndDate(form.getStart().plusDays(week));
            editForm.setStudyPeriod(form.getStudyPeriod());
            editForm.setCode(TimetableType.TUNNIPLAAN_LIIK_V.name());
            copyTimetable = createTimetable(user, editForm);
        }
        copyTimetableObjects(originalTimetable, copyTimetable);
        copyTimetableEvents(originalTimetable, copyTimetable);

        return copyTimetable;
    }
    
    private static void copyTimetableObjects(Timetable timetable, Timetable copyTimetable) {
        Map<Long, TimetableObject> copyObjectsByJournal = StreamUtil.toMap(r -> EntityUtil.getId(r.getJournal()), copyTimetable.getTimetableObjects());
        for(TimetableObject originalObject : timetable.getTimetableObjects()) {
            TimetableObject copyObject = copyObjectsByJournal.get(EntityUtil.getId(originalObject.getJournal()));
            if(copyObject == null) {
                copyObject = new TimetableObject();
                copyObject.setJournal(originalObject.getJournal());
                copyObject.setTimetable(copyTimetable);
                copyTimetable.getTimetableObjects().add(copyObject);
            }
            List<Long> tosgIds = StreamUtil.toMappedList(r -> EntityUtil.getId(r.getStudentGroup()), copyObject.getTimetableObjectStudentGroups());
            for(TimetableObjectStudentGroup tosg : originalObject.getTimetableObjectStudentGroups()) {
                if(!tosgIds.contains(EntityUtil.getId(tosg.getStudentGroup()))) {
                    TimetableObjectStudentGroup copyTosg = new TimetableObjectStudentGroup();
                    copyTosg.setStudentGroup(tosg.getStudentGroup());
                    copyTosg.setTimetableObject(copyObject);
                    copyObject.getTimetableObjectStudentGroups().add(copyTosg);
                }
            }
        }
    }
    
    private static void copyTimetableEvents(Timetable timetable, Timetable copyTimetable) {
        Map<Long, TimetableObject> copyObjectsByJournal = StreamUtil.toMap(r -> EntityUtil.getId(r.getJournal()), copyTimetable.getTimetableObjects());
        List<TimetableEvent> events = timetable.getTimetableObjects().stream().flatMap(currObj -> currObj.getTimetableEvents().stream()).collect(Collectors.toList());
        long daysBetween = ChronoUnit.DAYS.between(timetable.getStartDate(), copyTimetable.getStartDate());
        for(TimetableEvent event : events) {
            TimetableEvent copyEvent = new TimetableEvent();
            copyEvent.setStart(event.getStart().plusDays(daysBetween));
            copyEvent.setEnd(event.getEnd().plusDays(daysBetween));
            copyEvent.setLessons(event.getLessons());
            copyEvent.setConsiderBreak(event.getConsiderBreak());
            copyEvent.setLessonNr(event.getLessonNr());
            copyEvent.setCapacityType(event.getCapacityType());
            copyEvent.setRepeat(event.getRepeat());
            copyEvent.setSchool(event.getSchool());
            TimetableObject to = copyObjectsByJournal.get(EntityUtil.getId(event.getTimetableObject().getJournal()));
            copyEvent.setTimetableObject(to);
            to.getTimetableEvents().add(copyEvent);
            copyTimetableEventTime(event, copyEvent);
        }
    }
    
    private static void copyTimetableEventTime(TimetableEvent event, TimetableEvent copyEvent) {
        TimetableEventTime copyTet = new TimetableEventTime();
        copyTet.setStart(copyEvent.getStart());
        copyTet.setEnd(copyEvent.getEnd());
        //vocational timetables only have only timetableeventtime
        if(!event.getTimetableEventTimes().isEmpty()) {
            copyTimetableEventRooms(event.getTimetableEventTimes().get(0), copyTet);
            copyTimetableEventTeachers(event.getTimetableEventTimes().get(0), copyTet);
        }
        copyEvent.getTimetableEventTimes().add(copyTet);
    }
    
    private static void copyTimetableEventRooms(TimetableEventTime time, TimetableEventTime copyTime) {
        for(TimetableEventRoom room : time.getTimetableEventRooms()) {
            TimetableEventRoom copyRoom = new TimetableEventRoom();
            copyRoom.setRoom(room.getRoom());
            copyRoom.setTimetableEventTime(copyTime);
            copyTime.getTimetableEventRooms().add(copyRoom);
        }
    }
    
    private static void copyTimetableEventTeachers(TimetableEventTime time, TimetableEventTime copyTime) {
        for(TimetableEventTeacher teacher : time.getTimetableEventTeachers()) {
            TimetableEventTeacher copyTeacher = new TimetableEventTeacher();
            copyTeacher.setTeacher(teacher.getTeacher());
            copyTeacher.setTimetableEventTime(copyTime);
            copyTime.getTimetableEventTeachers().add(copyTeacher);
        }
    }

    public Timetable saveHigherEvent(TimetableEventHigherForm form) {
        Timetable timetable = em.getReference(Timetable.class, form.getTimetable());
        List<Long> studentGroups;
        if(Boolean.TRUE.equals(form.isForAllGroups())) {
            studentGroups = getAllStudentGroupsForSubjectStudyPeriod(form.getSubjectStudyPeriod());
        } else {
            studentGroups = Arrays.asList(form.getStudentGroupId());
        }
        for(Long studentGroupId : studentGroups) {
            TimetableObject timetableObject = getTimetableObjectForHigher(form, timetable, studentGroupId);
            TimetableEvent timetableEvent = saveHigherTimetableEvent(timetableObject, form);
            saveHigherTimetableEventTimes(timetable, timetableEvent, form);
            sendTimetableChangesMessages(timetableObject);
        }
        return EntityUtil.save(timetable, em);
    }

    private List<Long> getAllStudentGroupsForSubjectStudyPeriod(Long subjectStudyPeriodId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period ssp"
                + " join subject_study_period_student_group sspsg on sspsg.subject_study_period_id = ssp.id");
        qb.requiredCriteria("ssp.id = :subjectStudyPeriodId", "subjectStudyPeriodId", subjectStudyPeriodId);
        List<?> data = qb.select("distinct sspsg.student_group_id", em).getResultList();
        return StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
    }

    private TimetableEvent saveHigherTimetableEvent(TimetableObject object, TimetableEventHigherForm form) {
        TimetableEvent timetableEvent = addOrGetTimetableEvent(form.getOldEventId(), object);
        if(form.getLessonAmount() != null && form.getRepeatCode() != null && form.getOldEventId() == null) {
            timetableEvent.setStart(form.getStartTime());
            timetableEvent.setEnd(form.getStartTime().plusMinutes(LESSON_LENGTH * form.getLessonAmount().longValue()));
            timetableEvent.setLessons(form.getLessonAmount());
            timetableEvent.setRepeatCode(em.getReference(Classifier.class, form.getRepeatCode()));
            timetableEvent.setCapacityType(em.getReference(Classifier.class, form.getCapacityType()));
            timetableEvent.setConsiderBreak(Boolean.FALSE);
            timetableEvent.setSchool(object.getTimetable().getSchool());
            Subject sub = object.getSubjectStudyPeriod().getSubject();
            timetableEvent.setName(sub.getNameEt() + " (" + sub.getCode() + ")");
        } else {
            timetableEvent.setEnd(form.getStartTime().plus(Duration.between(timetableEvent.getStart(), timetableEvent.getEnd())));
            timetableEvent.setStart(form.getStartTime());
            //timetableEvent.setEnd(form.getStartTime().plusMinutes(LESSON_LENGTH * timetableEvent.getLessons().longValue()));
        }
        return timetableEvent;
    }
    
    private void addRoomsToTimetableEventTime(TimetableEventTime timetableEventTime, List<Long> rooms) {
        for(Long room : rooms) {
            TimetableEventRoom timetableEventRoom = new TimetableEventRoom();
            timetableEventRoom.setRoom(em.getReference(Room.class, room));
            timetableEventRoom.setTimetableEventTime(timetableEventTime);
            timetableEventTime.getTimetableEventRooms().add(timetableEventRoom);
        }
    }
    
    private void addTeachersToTimetableEvent(TimetableEventTime timetableEventTime, List<Teacher> teachers) {
        for(Teacher teacher : teachers) {
            TimetableEventTeacher timetableEventTeacher = new TimetableEventTeacher();
            timetableEventTeacher.setTeacher(em.getReference(Teacher.class, EntityUtil.getId(teacher)));
            timetableEventTeacher.setTimetableEventTime(timetableEventTime);
            timetableEventTime.getTimetableEventTeachers().add(timetableEventTeacher);
        }
    }

    private void saveHigherTimetableEventTimes(Timetable timetable, TimetableEvent timetableEvent, TimetableEventHigherForm form) {
        List<TimetableEventTime> timetableEventTimes = timetableEvent.getTimetableEventTimes();
        TimetableEventTime oldEventTime = form.getOldEventId() != null ? em.getReference(TimetableEventTime.class, form.getOldEventId()) : null;
        
        List<Teacher> teachers = oldEventTime != null
                ? StreamUtil.toMappedList(it -> it.getTeacher(), oldEventTime.getTimetableEventTeachers())
                : StreamUtil.toMappedList(it -> it.getTeacher(), timetableEvent.getTimetableObject().getSubjectStudyPeriod().getTeachers());
        timetableEventTimes.clear();
        TimetableEventTime timetableEventTime = new TimetableEventTime();
        timetableEventTime.setStart(timetableEvent.getStart());
        timetableEventTime.setEnd(timetableEvent.getEnd());
        
        if (oldEventTime != null) {
            addRoomsToTimetableEventTime(timetableEventTime, StreamUtil
                    .toMappedList(er -> EntityUtil.getId(er.getRoom()), oldEventTime.getTimetableEventRooms()));
        } else if(form.getRoom() != null) {
            addRoomsToTimetableEventTime(timetableEventTime, Arrays.asList(form.getRoom().getId()));
        }
        addTeachersToTimetableEvent(timetableEventTime, teachers);
        timetableEventTimes.add(timetableEventTime);
        long daysToAdd;
        if (ClassifierUtil.equals(TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_P, timetableEvent.getRepeat())) {
            daysToAdd = 1;
        } else if (ClassifierUtil.equals(TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_N, timetableEvent.getRepeat())) {
            daysToAdd = 7;
        } else if (ClassifierUtil.equals(TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_N2, timetableEvent.getRepeat())) {
            daysToAdd = 14;
        } else {
            return;
        }
        StudyPeriod sp = timetable.getStudyPeriod();
        LocalDateTime currentStart = timetableEvent.getStart().plusDays(daysToAdd);
        LocalDateTime currentEnd = timetableEvent.getEnd().plusDays(daysToAdd);
        while (sp.getEndDate().isAfter(currentStart.toLocalDate())) {
            TimetableEventTime currentTimetableEventTime = new TimetableEventTime();
            currentTimetableEventTime.setStart(currentStart);
            currentTimetableEventTime.setEnd(currentEnd);
            if (oldEventTime != null) {
                addRoomsToTimetableEventTime(currentTimetableEventTime, StreamUtil
                        .toMappedList(er -> EntityUtil.getId(er.getRoom()), oldEventTime.getTimetableEventRooms()));
            } else if (form.getRoom() != null) {
                addRoomsToTimetableEventTime(currentTimetableEventTime, Arrays.asList(form.getRoom().getId()));
            }
            addTeachersToTimetableEvent(currentTimetableEventTime, teachers);
            timetableEventTimes.add(currentTimetableEventTime);
            currentStart = currentStart.plusDays(daysToAdd);
            currentEnd = currentEnd.plusDays(daysToAdd);
        }

    }

    private TimetableObject getTimetableObjectForHigher(TimetableEventHigherForm form, Timetable timetable, Long studentGroupId) {
        boolean isPair = Boolean.TRUE.equals(form.isSubjectTeacherPair());
        String from = "from timetable_object too";
        if(!isPair) {
            from += " join timetable_object_student_group tosg on tosg.timetable_object_id = too.id";
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.requiredCriteria("too.timetable_id = :timetableId", "timetableId", form.getTimetable());
        qb.requiredCriteria("too.subject_study_period_id = :subjectStudyPeriodId", "subjectStudyPeriodId", form.getSubjectStudyPeriod());
        if(isPair) {
            qb.filter("too.id not in (select tosg.timetable_object_id from timetable_object_student_group tosg)");
        } else {
            qb.requiredCriteria("tosg.student_group_id = :studentGroupId", "studentGroupId", studentGroupId);
        }

        List<?> data = qb.select("too.id", em).setMaxResults(1).getResultList();
        List<Long> timetableObjectIds = StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
        Long timetableObjectId = timetableObjectIds.isEmpty() ? null : timetableObjectIds.get(0);

        if (timetableObjectId == null) {
            TimetableObject timetableObject = new TimetableObject();
            timetableObject
                    .setSubjectStudyPeriod(em.getReference(SubjectStudyPeriod.class, form.getSubjectStudyPeriod()));
            if(!isPair) {
                timetableObject.getTimetableObjectStudentGroups().add(createTimetableObjectStudentGroupForHigher(
                        timetableObject, studentGroupId));
            }
            timetableObject.setTimetable(timetable);
            List<TimetableObject> objects = timetable.getTimetableObjects();
            objects.add(timetableObject);
            return timetableObject;
        }
        return em.getReference(TimetableObject.class, timetableObjectId);
    }

    private TimetableObjectStudentGroup createTimetableObjectStudentGroupForHigher(TimetableObject timetableObject,
            Long studentGroup) {
        TimetableObjectStudentGroup tosg = new TimetableObjectStudentGroup();
        tosg.setStudentGroup(em.getReference(StudentGroup.class, studentGroup));
        tosg.setTimetableObject(timetableObject);
        return tosg;
    }

    private TimetableEvent saveVocationalTimetableEvent(TimetableObject timetableObject, LessonTime lessonTime,
            TimetableEventVocationalForm form) {
        TimetableEvent timetableEvent = addOrGetTimetableEvent(form.getOldEventId(), timetableObject);
        LocalDate start = form.getSelectedDay().equals(timetableObject.getTimetable().getStartDate().getDayOfWeek())
                ? timetableObject.getTimetable().getStartDate()
                : timetableObject.getTimetable().getStartDate().with(TemporalAdjusters.next(form.getSelectedDay()));
        timetableEvent.setStart(start.atTime(lessonTime.getStartTime()));
        timetableEvent.setEnd(start.atTime(lessonTime.getEndTime()));
        timetableEvent
                .setRepeatCode(em.getReference(Classifier.class, TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_EI.name()));
        timetableEvent.setLessonNr(lessonTime.getLessonNr());
        timetableEvent.setConsiderBreak(Boolean.FALSE);
        timetableEvent.setSchool(timetableObject.getTimetable().getSchool());
        if (form.getCapacityType() != null && !form.getCapacityType().isEmpty()) {
            timetableEvent.setCapacityType(em.getReference(Classifier.class, form.getCapacityType()));
        }
        return timetableEvent;
    }

    private static TimetableEvent addOrGetTimetableEvent(Long oldEventId, TimetableObject timetableObject) {
        if (oldEventId != null) {
            for(TimetableEvent event : timetableObject.getTimetableEvents()) {
                Optional<TimetableEventTime> timetableEventTime = event.getTimetableEventTimes().stream().filter(r -> oldEventId.equals(r.getId())).findFirst();
                if(timetableEventTime.isPresent()) {
                    return timetableEventTime.get().getTimetableEvent();
                }
            }
        }
        TimetableEvent timetableEvent = new TimetableEvent();
        timetableObject.getTimetableEvents().add(timetableEvent);
        timetableEvent.setTimetableObject(timetableObject);
        if(Boolean.TRUE.equals(timetableObject.getTimetable().getIsHigher())) {
            timetableEvent.setName(timetableObject.getSubjectStudyPeriod().getSubject().getNameEt());
        } else {
            timetableEvent.setName(timetableObject.getJournal().getNameEt());
        }
        return timetableEvent;
    }
    
    public Timetable saveEventRoomsAndTimes(HoisUserDetails user, TimetableRoomAndTimeForm form) {
        if(form.getTimetableEventId() != null && form.getEndTime() != null && form.getStartTime() != null) {
            EntityUtil.setUsername(user.getUsername(), em);
            TimetableEventTime timetableEventTime = em.getReference(TimetableEventTime.class, form.getTimetableEventId());
            TimetableEvent timetableEvent = timetableEventTime.getTimetableEvent();
            boolean modified = !Objects.equals(timetableEvent.getStart().toLocalTime(), form.getStartTime());
            if(!modified && !Objects.equals(timetableEvent.getEnd().toLocalTime(), form.getEndTime())) {
                modified = true;
            }
            for(TimetableEventTime currentTime : timetableEvent.getTimetableEventTimes()) {
                List<TimetableEventRoom> oldRooms = currentTime.getTimetableEventRooms();
                if(EntityUtil.bindEntityCollection(oldRooms, r -> EntityUtil.getId(r.getRoom()), form.getRooms(),
                        RoomDto::getId, dto -> {
                            TimetableEventRoom timetableEventRoom = new TimetableEventRoom();
                            timetableEventRoom.setRoom(em.getReference(Room.class, dto.getId()));
                            timetableEventRoom.setTimetableEventTime(timetableEventTime);
                            return timetableEventRoom;
                        })) {
                    modified = true;
                }
                List<TimetableEventTeacher> oldTeachers = currentTime.getTimetableEventTeachers();
                if(EntityUtil.bindEntityCollection(oldTeachers, r -> EntityUtil.getId(r.getTeacher()), form.getTeachers(),
                        r -> r, id -> {
                            TimetableEventTeacher timetableEventTeacher = new TimetableEventTeacher();
                            timetableEventTeacher.setTeacher(em.getReference(Teacher.class, id));
                            timetableEventTeacher.setTimetableEventTime(timetableEventTime);
                            return timetableEventTeacher;
                        })) {
                    modified = true;
                }
                currentTime.setStart(currentTime.getStart().withHour(form.getStartTime().getHour())
                        .withMinute(form.getStartTime().getMinute()));
                currentTime.setEnd(currentTime.getEnd().withHour(form.getEndTime().getHour())
                        .withMinute(form.getEndTime().getMinute()));
            }
            timetableEvent.setStart(timetableEventTime.getStart());
            timetableEvent.setEnd(timetableEventTime.getEnd());
            Timetable timetable = timetableEvent.getTimetableObject().getTimetable();
            timetable = EntityUtil.save(timetable, em);
            if(modified) {
                sendTimetableChangesMessages(timetableEvent.getTimetableObject());
            }
            return timetable;
        }
        return null;
    }

    public Timetable deleteEvent(HoisUserDetails user, TimetableRoomAndTimeForm form) {
        TimetableEventTime timetableEventTime = em.getReference(TimetableEventTime.class, form.getTimetableEventId());
        TimetableEvent timetableEvent = timetableEventTime.getTimetableEvent();
        Timetable timetable = timetableEvent.getTimetableObject().getTimetable();
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(timetableEvent, em);
        sendTimetableChangesMessages(timetableEvent.getTimetableObject());
        return timetable;
    }

    private TimetableEventTime saveVocationalTimetableEventTime(TimetableEvent timetableEvent, Journal journal) {
        TimetableEventTime timetableEventTime;
        if(timetableEvent.getTimetableEventTimes().isEmpty()) {
            timetableEventTime = new TimetableEventTime();
            timetableEventTime.setTimetableEvent(timetableEvent);
            addRoomsToTimetableEventTime(timetableEventTime, StreamUtil.toMappedList(it -> EntityUtil.getId(it.getRoom()), journal.getJournalRooms()));
            addTeachersToTimetableEvent(timetableEventTime,
                    StreamUtil.toMappedList(it -> it.getTeacher(), journal.getJournalTeachers()));
            timetableEvent.getTimetableEventTimes().add(timetableEventTime);
        } else {
            timetableEventTime = timetableEvent.getTimetableEventTimes().get(0);
        }
        timetableEventTime.setStart(timetableEvent.getStart());
        timetableEventTime.setEnd(timetableEvent.getEnd());
        return timetableEventTime;
    }

    private TimetableObject saveVocationalTimetableObject(Timetable timetable, Journal journal,
            List<TimetableStudentGroupDto> studentGroups) {
        TimetableObject timetableObject = timetableObjectRepository.findByJournalAndTimetable(journal, timetable);
        if (timetableObject == null) {
            timetableObject = new TimetableObject();
            timetableObject.setJournal(journal);
            timetableObject.setTimetable(timetable);
        }
        bindObjectGroups(timetableObject, studentGroups);

        List<TimetableObject> objects = timetable.getTimetableObjects();
        if (!objects.contains(timetableObject)) {
            objects.add(timetableObject);
        }
        return timetableObject;
    }

    private void bindObjectGroups(TimetableObject timetableObject, List<TimetableStudentGroupDto> studentGroups) {
        Map<Long, TimetableObjectStudentGroup> oldValues = StreamUtil
                .toMap(it -> EntityUtil.getId(it.getStudentGroup()), timetableObject.getTimetableObjectStudentGroups());
        for (TimetableStudentGroupDto newGroup : studentGroups) {
            if (oldValues.get(newGroup.getId()) == null) {
                TimetableObjectStudentGroup tosg = new TimetableObjectStudentGroup();
                tosg.setTimetableObject(timetableObject);
                tosg.setStudentGroup(em.getReference(StudentGroup.class, newGroup.getId()));
                timetableObject.getTimetableObjectStudentGroups().add(tosg);
            }
        }
    }

    public Page<TimetableManagementSearchDto> searchTimetableForManagement(TimetableManagementSearchCommand criteria,
            Pageable pageable, HoisUserDetails user) {
        if (TimetableType.isHigher(criteria.getType())) {
            return searchHigherTimetableForManagement(criteria, pageable, user);
        }
        return searchVocationalTimetableForManagement(criteria, pageable, user);
    }

    private Page<TimetableManagementSearchDto> searchHigherTimetableForManagement(
            TimetableManagementSearchCommand criteria, Pageable pageable, HoisUserDetails user) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable t").sort(pageable);

        qb.requiredCriteria("t.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("t.is_higher = :isHigher", "isHigher",
                Boolean.valueOf(TimetableType.isHigher(criteria.getType())));

        String select = "t.id, t.status_code, t.start_date, t.end_date, t.is_higher, t.study_period_id";
        return JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new TimetableManagementSearchDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsLocalDate(r, 2),
                    resultAsLocalDate(r, 3), resultAsBoolean(r, 4), resultAsLong(r, 5));
        });
    }

    private Page<TimetableManagementSearchDto> searchVocationalTimetableForManagement(
            TimetableManagementSearchCommand criteria, Pageable pageable, HoisUserDetails user) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable t");

        qb.requiredCriteria("t.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("t.is_higher = :isHigher", "isHigher",
                Boolean.valueOf(TimetableType.isHigher(criteria.getType())));

        String select = "t.id, t.status_code, t.start_date, t.end_date, t.is_higher";
        List<?> data = qb.select(select, em).getResultList();
        StudyPeriod sp = em.getReference(StudyPeriod.class, criteria.getStudyPeriod());

        List<TimetableManagementSearchDto> wrappedData = StreamUtil.toMappedList(r -> {
            Long id = resultAsLong(r, 0);
            String status = resultAsString(r, 1);
            LocalDate start = resultAsLocalDate(r, 2);
            LocalDate end = resultAsLocalDate(r, 3);
            Boolean isHigher = resultAsBoolean(r, 4);
            return new TimetableManagementSearchDto(id, status, start, end, isHigher, EntityUtil.getId(sp));
        }, data);
        wrappedData = addMissingDatesToBlocked(sp, wrappedData);

        String pageableSort = pageable.getSort().toString();
        if ("3: ASC, 4: ASC".equals(pageableSort)) {
            Collections.sort(wrappedData, StreamUtil.comparingWithNullsLast(TimetableManagementSearchDto::getStart));
        } else if ("3: DESC, 4: DESC".equals(pageableSort)) {
            Collections.sort(wrappedData, StreamUtil.comparingWithNullsLast(TimetableManagementSearchDto::getStart));
            Collections.reverse(wrappedData);
        } else if ("2: ASC".equals(pageableSort)) {
            Collections.sort(wrappedData, StreamUtil.comparingWithNullsLast(TimetableManagementSearchDto::getStatus));
        } else if ("2: DESC".equals(pageableSort)) {
            Collections.sort(wrappedData, StreamUtil.comparingWithNullsLast(TimetableManagementSearchDto::getStatus));
            Collections.reverse(wrappedData);
        }

        int totalCount = wrappedData.size();
        int start = Math.min(pageable.getOffset(), totalCount);
        int end = Math.min(start + pageable.getPageSize(), totalCount);
        return new PageImpl<>(wrappedData.subList(start, end), pageable, wrappedData.size());
    }

    public List<TimetableManagementSearchDto> getPossibleTargetsForCopy(HoisUserDetails user, Long timetableId) {
        Timetable timetable = em.getReference(Timetable.class, timetableId);
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable t").sort("t.start_date desc");

        qb.requiredCriteria("t.study_period_id in (:studyPeriod)", "studyPeriod", timetable.getStudyPeriod().getStudyYear().getStudyPeriods());
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("t.is_higher = :isHigher", "isHigher", timetable.getIsHigher());

        String select = "t.id, t.start_date, t.end_date, t.study_period_id";
        List<?> data = qb.select(select, em).getResultList();

        List<TimetableManagementSearchDto> wrappedData = StreamUtil.toMappedList(r -> {
            Long id = resultAsLong(r, 0);
            LocalDate start = resultAsLocalDate(r, 1);
            LocalDate end = resultAsLocalDate(r, 2);
            Long studyPeriod = resultAsLong(r, 3);
            return new TimetableManagementSearchDto(id, start, end, studyPeriod);
        }, data);

        for(StudyPeriod sp : timetable.getStudyPeriod().getStudyYear().getStudyPeriods()) {
            wrappedData = addMissingDatesToBlocked(sp, wrappedData);
        }
        wrappedData = StreamUtil.toFilteredList(wrapped -> wrapped.getStart().isAfter(timetable.getStartDate()), wrappedData);
        Collections.sort(wrappedData, (dto1, dto2) -> dto1.getStart().compareTo(dto2.getStart()));
        return wrappedData;
    }

    private static List<TimetableManagementSearchDto> addMissingDatesToBlocked(StudyPeriod sp,
            List<TimetableManagementSearchDto> data) {
        LocalDate currentStart = sp.getStartDate();
        LocalDate currentEnd = sp.getStartDate();
        LocalDate spEnd = sp.getEndDate();
        List<TimetableManagementSearchDto> toAdd = new ArrayList<>();

        Collections.sort(data, (dto1, dto2) -> dto1.getStart().compareTo(dto2.getStart()));

        for (TimetableManagementSearchDto dto : data) {
            // TODO: put the while into a function , both whiles are same
            while (currentStart.isBefore(dto.getStart())) {
                currentEnd = currentStart.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
                currentEnd = currentEnd.isBefore(dto.getStart()) ? currentEnd : dto.getStart().minusDays(1);
                toAdd.add(
                        new TimetableManagementSearchDto(null, TimetableStatus.TUNNIPLAAN_STAATUS_A.name(), currentStart, currentEnd, Boolean.FALSE, EntityUtil.getId(sp)));
                currentStart = currentStart.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            }
            currentEnd = dto.getEnd().plusDays(1);
            currentStart = currentEnd.getDayOfWeek().equals(DayOfWeek.MONDAY) ? currentEnd
                    : currentEnd.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }
        if (currentEnd.isBefore(spEnd)) {
            while (currentStart.isBefore(spEnd)) {
                currentEnd = currentStart.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
                currentEnd = currentEnd.isBefore(spEnd) ? currentEnd : spEnd;
                toAdd.add(
                        new TimetableManagementSearchDto(null, TimetableStatus.TUNNIPLAAN_STAATUS_A.name(), currentStart, currentEnd, Boolean.FALSE, EntityUtil.getId(sp)));
                currentStart = currentStart.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            }
        }

        Collections.sort(data, (dto1, dto2) -> dto2.getStart().compareTo(dto1.getStart()));
        Collections.sort(toAdd, (dto1, dto2) -> dto2.getStart().compareTo(dto1.getStart()));

        toAdd.addAll(data);
        return toAdd;
    }

    public List<TimetableDatesDto> blockedDatesForPeriod(HoisUserDetails user, Long studyPeriod, String code,
            Long currentTimetable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable t");

        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("t.study_period_id = :studyPeriod", "studyPeriod", studyPeriod);
        qb.optionalCriteria("t.is_higher = :isHigher", "isHigher", Boolean.valueOf(TimetableType.isHigher(code)));
        qb.optionalCriteria("t.id != :currentTimetable", "currentTimetable", currentTimetable);

        List<?> data = qb.select("t.id, t.start_date, t.end_date", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            LocalDate from = resultAsLocalDate(r, 1);
            LocalDate thru = resultAsLocalDate(r, 2);
            return new TimetableDatesDto(from, thru);
        }, data);
    }

    public Timetable createTimetable(HoisUserDetails user, TimetableEditForm form) {
        Timetable timetable = new Timetable();
        timetable.setIsHigher(Boolean.valueOf(TimetableType.isHigher(form.getCode())));
        timetable.setSchool(em.getReference(School.class, user.getSchoolId()));
        timetable.setStatus(em.getReference(Classifier.class, TimetableStatus.TUNNIPLAAN_STAATUS_S.name()));
        EntityUtil.bindToEntity(form, timetable);
        timetable.setStudyPeriod(em.getReference(StudyPeriod.class, form.getStudyPeriod()));
        return EntityUtil.save(timetable, em);
    }

    public Timetable save(HoisUserDetails user, TimetableEditForm form, Timetable timetable) {
        EntityUtil.bindToEntity(form, timetable);
        timetable.setIsHigher(Boolean.valueOf(TimetableType.isHigher(form.getCode())));
        timetable.setSchool(em.getReference(School.class, user.getSchoolId()));
        timetable.setStatus(em.getReference(Classifier.class, TimetableStatus.TUNNIPLAAN_STAATUS_S.name()));
        timetable.setStudyPeriod(em.getReference(StudyPeriod.class, form.getStudyPeriod()));
        return EntityUtil.save(timetable, em);
    }

    private List<?> getVocationalStudentGroupsUnformatted(Timetable timetable, Long journalId) {
        String from = "from lesson_plan lp" + " join curriculum_version cv on cv.id = lp.curriculum_version_id"
                + " join curriculum c on c.id = cv.curriculum_id"
                + " join student_group sg on sg.id = lp.student_group_id";
        if (journalId != null) {
            from += " join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id"
                    + " join journal_omodule_theme jot on jot.lesson_plan_module_id = lpm.id"
                    + " join journal j on j.id = jot.journal_id";
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from).sort(new Sort(Direction.ASC, "student_group_code"));
        qb.requiredCriteria("lp.school_id = :schoolId", "schoolId", EntityUtil.getId(timetable.getSchool()));
        qb.requiredCriteria("lp.study_year_id = :studyYearId", "studyYearId",
                EntityUtil.getId(timetable.getStudyPeriod().getStudyYear()));
        qb.filter("lp.is_usable = true");
        qb.optionalCriteria("c.is_higher = :isHigher", "isHigher", timetable.getIsHigher());
        qb.optionalCriteria("j.id = :journalId", "journalId", journalId);

        String select = "distinct lp.student_group_id, sg.code as student_group_code"
                + ", c.name_et, c.name_en, cv.id, cv.code as curriculum_version_code, c.orig_study_level_code";
        return qb.select(select, em).getResultList();
    }

    List<TimetableStudentGroupDto> getVocationalStudentGroups(Timetable timetable) {
        List<?> data = getVocationalStudentGroupsUnformatted(timetable, null);
        List<TimetableStudentGroupDto> groups = StreamUtil.toMappedList(
                r -> (new TimetableStudentGroupDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsLong(r, 4))),
                data);
        return groups;
    }

    private List<HigherTimetableStudentGroupDto> getHigherStudentGroups(Timetable timetable) {
        String from = "from student_group sg join subject_study_period_student_group sspsg on sspsg.student_group_id = sg.id"
                + " join subject_study_period ssp on ssp.id = sspsg.subject_study_period_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from).sort(new Sort(Direction.ASC, "sg.code"));
        qb.requiredCriteria("ssp.study_period_id = :subjectStudyPeriod", "subjectStudyPeriod",
                EntityUtil.getId(timetable.getStudyPeriod()));

        String select = "distinct sg.id, sg.code";
        List<?> data = qb.select(select, em).getResultList();
        return StreamUtil
                .toMappedList(r -> new HigherTimetableStudentGroupDto(resultAsLong(r, 0), resultAsString(r, 1), null), data);
    }
    
    private List<TimetableCurriculumDto> getHigherStudentGroupsByCurriculum(Timetable timetable) {
        String from = "from student_group sg join subject_study_period_student_group sspsg on sspsg.student_group_id = sg.id"
                + " join subject_study_period ssp on ssp.id = sspsg.subject_study_period_id"
                + " join curriculum c on c.id = sg.curriculum_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from).sort(new Sort(Direction.ASC, "sg.code"));
        qb.requiredCriteria("ssp.study_period_id = :subjectStudyPeriod", "subjectStudyPeriod",
                EntityUtil.getId(timetable.getStudyPeriod()));

        String select = "distinct sg.id, sg.code, c.name_et, c.name_en, c.orig_study_level_code, c.id as curriculum_id";
        List<?> data = qb.select(select, em).getResultList();

        Map<Long, TimetableCurriculumDto> curriculumsById = data.stream().collect(Collectors.toMap(
                r -> resultAsLong(r, 5),
                r -> new TimetableCurriculumDto(resultAsString(r, 2), resultAsString(r, 3), null, resultAsString(r, 4)),
                (o, n) -> o));
        List<TimetableStudentGroupDto> groups = StreamUtil.toMappedList(
                r -> new TimetableStudentGroupDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsLong(r, 5)), data);

        for (TimetableStudentGroupDto group : groups) {
            curriculumsById.get(group.getCurriculumId()).getGroups().add(group);
        }
        return new ArrayList<>(curriculumsById.values());
    }

    private List<TimetableStudentGroupDto> getStudentGroups(Timetable timetable, Long journalId) {
        List<?> data = getVocationalStudentGroupsUnformatted(timetable, journalId);
        List<TimetableStudentGroupDto> groups = StreamUtil.toMappedList(
                r -> (new TimetableStudentGroupDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsLong(r, 4))),
                data);
        return groups;
    }

    private List<TimetableCurriculumDto> getStudentGroupsByCurriculum(Timetable timetable) {
        List<?> data = getVocationalStudentGroupsUnformatted(timetable, null);
        Map<Long, TimetableCurriculumDto> curriculums = data.stream()
                .collect(
                        Collectors.toMap(
                                r -> (resultAsLong(r, 4)), r -> (new TimetableCurriculumDto(resultAsString(r, 2),
                                        resultAsString(r, 3), resultAsString(r, 5), resultAsString(r, 6))),
                                (o, n) -> o));

        List<TimetableStudentGroupDto> groups = StreamUtil.toMappedList(
                r -> (new TimetableStudentGroupDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsLong(r, 4))),
                data);

        for (TimetableStudentGroupDto group : groups) {
            curriculums.get(group.getCurriculumId()).getGroups().add(group);
        }
        return new ArrayList<>(curriculums.values());
    }

    private List<SubjectTeacherPairDto> getPairsforTimetable(Timetable timetable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period_teacher sspt"
                + " join subject_study_period ssp on ssp.id = sspt.subject_study_period_id"
                + " join subject s on s.id = ssp.subject_id"
                + " join teacher tea on tea.id = sspt.teacher_id"
                + " join person p on p.id = tea.person_id");

        qb.requiredCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId",
                EntityUtil.getId(timetable.getStudyPeriod()));
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", EntityUtil.getId(timetable.getSchool()));
        qb.filter(
                "ssp.id not in (select sspsg.subject_study_period_id from subject_study_period_student_group sspsg where sspsg.subject_study_period_id = ssp.id)");
        String select = "ssp.id, s.code, string_agg(p.firstname || ' ' || p.lastname, ', ')"
                + " as teacher_names, string_agg(LEFT(p.firstname, 2) || LEFT(p.lastname, 2),"
                + " ', ') as teacher_names_short, s.name_et, s.name_en";
        qb.groupBy("ssp.id, s.code, s.name_et, s.name_en");
        List<?> data = qb.select(select, em).getResultList();

        return StreamUtil.toMappedList(r -> (new SubjectTeacherPairDto(resultAsLong(r, 0), resultAsString(r, 1),
                resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 4), resultAsString(r, 5))), data);
    }

    private List<HigherTimetableStudentGroupCapacityDto> getCapacitiesForHigherPlanning(Timetable timetable, List<Long> studentGroupIds, List<Long> subjectStudyPeriodIds) {
        Long studyPeriodId = EntityUtil.getId(timetable.getStudyPeriod());
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period_capacity sspc "
                + " join subject_study_period ssp on ssp.id = sspc.subject_study_period_id "
                + " left join subject_study_period_student_group sspsg on sspsg.subject_study_period_id = ssp.id"
                + " join subject s on s.id = ssp.subject_id");
        if(studentGroupIds.isEmpty() && subjectStudyPeriodIds.isEmpty()) {
            return new ArrayList<>();
        }
        if (!studentGroupIds.isEmpty() && !subjectStudyPeriodIds.isEmpty()) {
            qb.requiredCriteria("(sspsg.student_group_id in (:studentGroupIds) or ssp.id in (:subjectStudyPeriodIds))", "studentGroupIds", studentGroupIds);
            qb.parameter("subjectStudyPeriodIds", subjectStudyPeriodIds);
        } else {
            qb.optionalCriteria("sspsg.student_group_id in (:studentGroupIds)", "studentGroupIds", studentGroupIds);
            qb.optionalCriteria("ssp.id in (:subjectStudyPeriodIds)", "subjectStudyPeriodIds",
                    subjectStudyPeriodIds);
        }
        
        qb.requiredCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", studyPeriodId);

        String select = "distinct sspsg.student_group_id as student_group_id, sspc.capacity_type_code, sspc.hours, s.code, s.name_et, ssp.id as subject_study_period_id";
        List<?> data = qb.select(select, em).getResultList();
        List<HigherTimetableStudentGroupCapacityDto> capacities = StreamUtil
                .toMappedList(
                        r -> new HigherTimetableStudentGroupCapacityDto(resultAsLong(r, 0), resultAsLong(r, 2),
                                resultAsString(r, 1), resultAsString(r, 3), resultAsString(r, 4), resultAsLong(r, 5)),
                        data);

        if(StreamUtil.toMappedSet(r -> r.getSubjectStudyPeriod(), capacities).isEmpty()) {
            throw new ValidationFailedException("timetable.error.missingCapacities");
        }
        Map<Long, List<AutocompleteResult>> teachersBySsps = getTeachersForSubjectStudyPeriods(StreamUtil.toMappedSet(r -> r.getSubjectStudyPeriod(), capacities));
        // key = subject_study_period.id + _ + student_group.id + _ + capacity_type
        Map<String, Long> plannedLessonsBySspSgCt = getPlannedTotalsForHigherStudentGroups(EntityUtil.getId(timetable));
        
        for(HigherTimetableStudentGroupCapacityDto dto : capacities) {
            dto.setTeachers(teachersBySsps.get(dto.getSubjectStudyPeriod()));
            if(dto.getStudentGroup() != null) {
                dto.setTotalAllocatedLessons(plannedLessonsBySspSgCt.get(dto.getSubjectStudyPeriod().toString() + "_" + dto.getStudentGroup().toString() + "_" + dto.getCapacityType()));
            } else {
                dto.setTotalAllocatedLessons(plannedLessonsBySspSgCt.get(dto.getSubjectStudyPeriod().toString() + "__" + dto.getCapacityType()));
            }
        }
        return capacities;
    }

    private Map<String, Long> getPlannedTotalsForHigherStudentGroups(Long timetableId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event_time tet"
                + " join timetable_event te on te.id = tet.timetable_event_id"
                + " join timetable_object too on too.id = te.timetable_object_id"
                + " left join timetable_object_student_group tosg on tosg.timetable_object_id = too.id"
                + " left join student_group sg on sg.id = tosg.student_group_id");
        
        qb.requiredCriteria("too.timetable_id = :timetableId", "timetableId", timetableId);
        
        qb.groupBy("too.subject_study_period_id, sg.id, te.capacity_type_code");
        
        List<?> data = qb.select("too.subject_study_period_id as ssp_id, sg.id as sg_id, te.capacity_type_code, count(*)", em).getResultList();
        // key = subject_study_period.id + _ + student_group.id + _ + capacity_type
        return StreamUtil.toMap(r -> resultAsLong(r, 0).toString() + "_" + (resultAsLong(r, 1) != null ? resultAsLong(r, 1).toString() : "") + "_" + resultAsString(r, 2), r -> resultAsLong(r, 3), data);
    }
    
    private List<TimetableStudentGroupCapacityDto> getCapacitiesForVocationalPlanning(List<Long> studentGroupIds,
            Timetable timetable) {
        Integer currentWeekNr = timetable.getStudyPeriod().getWeekNrForDate(timetable.getStartDate());
        //TODO: query might be too slow
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j" + " join journal_capacity jc on jc.journal_id = j.id"
                + " join (select distinct ot.lesson_plan_module_id, journal_id from journal_omodule_theme ot) jot on jot.journal_id = j.id"
                + " join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
                + " join lesson_plan lp on lp.id = lpm.lesson_plan_id"
                + " join journal_capacity_type jct on jct.id = jc.journal_capacity_type_id");

        qb.requiredCriteria("jc.study_period_id = :studyPeriodId", "studyPeriodId", EntityUtil.getId(timetable.getStudyPeriod()));
        qb.filter("lp.is_usable = true");
        qb.requiredCriteria("lp.student_group_id in (:studentGroupIds)", "studentGroupIds", studentGroupIds);
        qb.groupBy("j.id, lp.student_group_id, jct.id");
        
        String subselect = "(select coalesce(sum(jc.hours), 0)"
                + " from journal_capacity jc where jc.journal_id = j.id and jc.journal_capacity_type_id = jct.id and "
                + "jc.week_nr in (" + currentWeekNr + "))";
        
        String select = "j.id as journal_id, sum(jc.hours) as total_hours, lp.student_group_id, " + subselect
                + " as current_week_hours, jct.capacity_type_code";
        List<?> data = qb.select(select, em).getResultList();

        List<TimetableStudentGroupCapacityDto> result = StreamUtil
                .toMappedList(r -> new TimetableStudentGroupCapacityDto(resultAsLong(r, 2), resultAsLong(r, 0),
                        resultAsLong(r, 1), resultAsLong(r, 3), resultAsString(r, 4)), data);
        
        Map<Long, Map<String, LeftOverLessons>> leftOverLessons = leftOverLessons(studentGroupIds, timetable);
        Map<Long, Map<String, AllocatedLessons>> allocatedCapacities = getAllocatedLessonsForByJournalAndCapacity(
                timetable);
        
        for (TimetableStudentGroupCapacityDto dto : result) {
            Map<String, AllocatedLessons> groupAllocatedLessons = allocatedCapacities.get(dto.getJournal());
            if (groupAllocatedLessons != null) {
                AllocatedLessons currentLessons = groupAllocatedLessons
                        .get(dto.getStudentGroup() + "/" + dto.getCapacityType());
                if (currentLessons != null) {
                    dto.setTotalAllocatedLessons(currentLessons.getTotalAllocated());
                    dto.setLessonsLeft(Long.valueOf(dto.getThisPlannedLessons().longValue()
                            - currentLessons.getCurrentWeekAllocated().longValue()));
                    dto.setTotalLessonsLeft(Long.valueOf(
                            dto.getTotalPlannedLessons().longValue() - dto.getTotalAllocatedLessons().longValue()));
                    dto.setCapacityType(currentLessons.getCapacityType());
                } else {
                    dto.setTotalAllocatedLessons(Long.valueOf(0));
                    dto.setTotalLessonsLeft(Long.valueOf(
                            dto.getTotalPlannedLessons().longValue() - dto.getTotalAllocatedLessons().longValue()));
                }
            } else {
                dto.setTotalLessonsLeft(dto.getTotalPlannedLessons());
            }
            setLeftOverLessonsGroupCapacities(dto, leftOverLessons);
        }
        setPeriodExternalJournalCapacities(result, leftOverLessons);
        
        return result;
    }
    
    private static void setLeftOverLessonsGroupCapacities(TimetableStudentGroupCapacityDto dto, Map<Long, Map<String, LeftOverLessons>> leftOverLessons) {
        Map<String, LeftOverLessons> groupLeftOverLessons = leftOverLessons.get(dto.getJournal());
        if (groupLeftOverLessons != null) {
            LeftOverLessons capacityLeftOvers = groupLeftOverLessons.remove(dto.getStudentGroup() + "/" + dto.getCapacityType());
            if (capacityLeftOvers != null) {
                dto.setLeftOverLessons(Long.valueOf(capacityLeftOvers.getTotalHours().longValue()
                        - capacityLeftOvers.getOutsidePeriodAllocated().longValue()));
            }
            
            if (groupLeftOverLessons.isEmpty()) {
                leftOverLessons.remove(dto.getJournal());
            }
        }
    }
    
    private static void setPeriodExternalJournalCapacities(List<TimetableStudentGroupCapacityDto> studentGroupCapacities,
            Map<Long, Map<String, LeftOverLessons>> leftOverLessons) {
        for (Long journalId : leftOverLessons.keySet()) {
            Map<String, LeftOverLessons> journalCapacities = leftOverLessons.get(journalId);
            
            for (LeftOverLessons capacityLeftOvers : journalCapacities.values()) {
                TimetableStudentGroupCapacityDto dto = new TimetableStudentGroupCapacityDto(capacityLeftOvers.getStudentGroup(), 
                        journalId, Long.valueOf(0), Long.valueOf(0), capacityLeftOvers.getCapacityType());
                dto.setLeftOverLessons(Long.valueOf(capacityLeftOvers.getTotalHours().longValue()
                        - capacityLeftOvers.getOutsidePeriodAllocated().longValue()));
                dto.setTotalAllocatedLessons(capacityLeftOvers.getPeriodAllocated());
                dto.setLessonsLeft(Long.valueOf(dto.getThisPlannedLessons().longValue()
                        - capacityLeftOvers.getCurrentWeekAllocated().longValue()));
                studentGroupCapacities.add(dto);
            }
        }
    }

    private Map<Long, Map<String, LeftOverLessons>> leftOverLessons(List<Long> studentGroupIds, Timetable timetable) {
        Long currentTimetableSpId = EntityUtil.getId(timetable.getStudyPeriod());
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j" + " join journal_capacity jc on jc.journal_id = j.id"
                + " join (select distinct ot.lesson_plan_module_id, journal_id from journal_omodule_theme ot) jot on jot.journal_id = j.id"
                + " join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
                + " join lesson_plan lp on lp.id = lpm.lesson_plan_id"
                + " join journal_capacity_type jct on jct.id = jc.journal_capacity_type_id");

        qb.requiredCriteria("lp.study_year_id = :studyYearId", "studyYearId",
                EntityUtil.getId(timetable.getStudyPeriod().getStudyYear()));
        qb.requiredCriteria("jc.study_period_id != :studyPeriodId", "studyPeriodId", currentTimetableSpId);
        qb.filter("lp.is_usable = true");
        qb.requiredCriteria("lp.student_group_id in (:studentGroupIds)", "studentGroupIds", studentGroupIds);
        qb.groupBy("j.id, lp.student_group_id, jct.capacity_type_code");
        
        String totalAllocatedSelect = "(select count(*) from timetable_event te "
                + "join timetable_object too on too.id = te.timetable_object_id and too.journal_id is not null "
                + "join timetable_object_student_group tsog on tsog.timetable_object_id = too.id "
                + "join timetable t on t.id = too.timetable_id "
                + "where too.journal_id = j.id and tsog.student_group_id = lp.student_group_id and te.capacity_type_code = jct.capacity_type_code";
        String outsidePeriodAllocated = " and t.study_period_id != " + currentTimetableSpId + ") as outside_period_allocated";
        String periodAllocated = " and t.study_period_id = " + currentTimetableSpId + ") as period_allocated";
        String currentAllocated = " and t.id = " + EntityUtil.getId(timetable) + ") as current_allocated";

        String select = "j.id as journal_id, lp.student_group_id, jct.capacity_type_code, sum(jc.hours) as total_hours, " + totalAllocatedSelect
                + outsidePeriodAllocated + "," + totalAllocatedSelect + periodAllocated + "," + totalAllocatedSelect + currentAllocated;
        List<?> data = qb.select(select, em).getResultList();
        
        return !data.isEmpty() ? data.stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.toMap(
                        r -> resultAsLong(r, 1).toString() + "/" + resultAsString(r, 2),
                        r -> new LeftOverLessons(resultAsLong(r, 3), resultAsLong(r, 4), resultAsLong(r, 5),  resultAsLong(r, 6), resultAsLong(r, 1), resultAsString(r, 2)))))
                : new HashMap<>();
    }

    private Map<Long, Map<String, AllocatedLessons>> getAllocatedLessonsForByJournalAndCapacity(
            Timetable timetable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event te join timetable_object too"
                + " on too.id = te.timetable_object_id and too.journal_id is not null join timetable_object_student_group tsog"
                + " on tsog.timetable_object_id = too.id join timetable t on t.id = too.timetable_id");

        qb.requiredCriteria("t.study_period_id = :studyPeriodId", "studyPeriodId", EntityUtil.getId(timetable.getStudyPeriod()));
        String groupBy = "too.journal_id, tsog.student_group_id, te.capacity_type_code";
        qb.parameter("timetableId", EntityUtil.getId(timetable));
        
        qb.groupBy(groupBy);
        String select = groupBy + ", count(*) filter (where too.timetable_id = :timetableId ) as current_allocated, count(*) as total_allocated";

        List<?> data = qb.select(select, em).getResultList();

        return data.stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.toMap(
                        r -> resultAsLong(r, 1).toString() + "/" + resultAsString(r, 2),
                        r -> new AllocatedLessons(resultAsLong(r, 3), resultAsLong(r, 4), resultAsString(r, 2)))));
    }

    private List<TimetableJournalDto> getJournalsForPlanning(List<Long> studentGroupIds, Timetable timetable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j join journal_omodule_theme jot on jot.journal_id = j.id"
                + " join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
                + " join lesson_plan lp on lp.id = lpm.lesson_plan_id");

        qb.requiredCriteria("lp.study_year_id = :studyYearId", "studyYearId",
                EntityUtil.getId(timetable.getStudyPeriod().getStudyYear()));
        qb.filter("lp.is_usable = true");
        qb.requiredCriteria("lp.student_group_id in (:studentGroupIds)", "studentGroupIds", studentGroupIds);
        String select = "distinct j.id as journal_id, j.name_et";
        List<?> data = qb.select(select, em).getResultList();
        List<TimetableJournalDto> journals = StreamUtil
                .toMappedList(r -> new TimetableJournalDto(resultAsLong(r, 0), resultAsString(r, 1)), data);
        if (!journals.isEmpty()) {
            // load teachers
            // TODO add order by teacher name
            qb = new JpaNativeQueryBuilder(
                    "from journal_teacher jt join teacher t on t.id = jt.teacher_id join person p on p.id = t.person_id");
            qb.requiredCriteria("jt.journal_id in (:journalIds)", "journalIds",
                    StreamUtil.toMappedList(TimetableJournalDto::getId, journals));
            select = "jt.journal_id, p.firstName, p.lastName, t.id as teacher_id";
            data = qb.select(select, em).getResultList();
            Map<Long, List<AutocompleteResult>> teachersByJournals = data.stream()
                    .collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                            Collectors.mapping(r -> new AutocompleteResult(resultAsLong(r, 3)
                                    , PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2))
                                    , PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2))),
                                    Collectors.toList())));
            for (TimetableJournalDto journal : journals) {
                List<AutocompleteResult> teachers = teachersByJournals.get(journal.getId());
                if (teachers != null) {
                    journal.getTeachers().addAll(teachers);
                }
            }
        }

        return journals;
    }

    List<LessonTimeDto> getLessonTimesForPlanning(Timetable timetable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from lesson_time lt"
                + " join lesson_time_building_group ltbg on ltbg.id = lt.lesson_time_building_group_id"
                + " join lesson_time_building ltb on ltb.lesson_time_building_group_id = ltbg.id");

        qb.requiredCriteria("lt.school_id = :schoolId", "schoolId", EntityUtil.getId(timetable.getSchool()));
        qb.requiredCriteria("ltbg.valid_from <= :timetableStartDate", "timetableStartDate", timetable.getStartDate());
        qb.filter("(ltbg.valid_thru >= :timetableStartDate or ltbg.valid_thru is null)");

        String select = "lt.id, lt.start_time, lt.end_time, lt.lesson_nr, lt.day_mon, lt.day_tue, lt.day_wed"
                + " ,lt.day_thu, lt.day_fri, lt.day_sat, lt.day_sun, ltb.building_id";
        List<?> data = qb.select(select, em).getResultList();

        Map<Long, List<Long>> buildingIdsByLessonTimes = data.stream().collect(Collectors.groupingBy(
                r -> resultAsLong(r, 0), Collectors.mapping(r -> resultAsLong(r, 11), Collectors.toList())));
        select = "distinct lt.id, lt.start_time, lt.end_time, lt.lesson_nr, lt.day_mon, lt.day_tue, lt.day_wed"
                + " ,lt.day_thu, lt.day_fri, lt.day_sat, lt.day_sun";
        data = qb.select(select, em).getResultList();
        return StreamUtil.toMappedList(r -> (new LessonTimeDto(resultAsLong(r, 0), resultAsLocalTime(r, 1),
                resultAsLocalTime(r, 2), resultAsShort(r, 3).shortValue(), resultAsBoolean(r, 4), resultAsBoolean(r, 5),
                resultAsBoolean(r, 6), resultAsBoolean(r, 7), resultAsBoolean(r, 8), resultAsBoolean(r, 9),
                resultAsBoolean(r, 10), buildingIdsByLessonTimes.get(resultAsLong(r, 0)))), data);
    }

    List<TimetableEventDto> getPlannedLessonsForVocationalTimetable(Timetable timetable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event te" + " join timetable_object too on too.id = te.timetable_object_id"
                + " join timetable_object_student_group tosg on tosg.timetable_object_id = too.id"
                + " join timetable_event_time tet on tet.timetable_event_id = te.id"
                + " join journal j on j.id = too.journal_id");

        qb.requiredCriteria("too.timetable_id = :timetableId", "timetableId", EntityUtil.getId(timetable));

        String select = "tet.id, tet.start, tet.end, te.lesson_nr, te.capacity_type_code, too.journal_id, tosg.student_group_id, j.name_et";
        qb.sort("cast(te.start as date), te.lesson_nr");
        List<?> data = qb.select(select, em).getResultList();

        List<TimetableEventDto> result = StreamUtil.toMappedList(
                r -> new TimetableEventDto(resultAsLong(r, 0), resultAsLocalDateTime(r, 1), resultAsLocalDateTime(r, 2),
                        resultAsInteger(r, 3), resultAsString(r, 4), resultAsLong(r, 5), resultAsLong(r, 6), resultAsString(r, 7)),
                data);
        if (!result.isEmpty()) {
            result = addRoomsListToEvents(result);
            result = addTeachersListToEvents(result);
        }

        return result;
    }

    private List<TimetableEventDto> addRoomsListToEvents(List<TimetableEventDto> result) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event_room ter join timetable_event_time tet on tet.id = ter.timetable_event_time_id"
                + " join room r on r.id = ter.room_id join building b on b.id = r.building_id").sort("r.code");

        qb.requiredCriteria("tet.id in (:timetableEventIds)", "timetableEventIds",
                result.stream().map(r -> r.getId()).collect(Collectors.toSet()));

        String select = "tet.id as timetable_event_id, ter.room_id, r.code, b.id as building_id";
        List<?> data = qb.select(select, em).getResultList();

        Map<Long, List<AutocompleteResult>> roomsByTimetableEvent = data.stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                        Collectors.mapping(
                                r -> new AutocompleteResult(resultAsLong(r, 1), resultAsString(r, 2), resultAsString(r, 2)),
                                Collectors.toList())));
        for (TimetableEventDto dto : result) {
            dto.setRooms(roomsByTimetableEvent.get(dto.getId()));
        }
        return result;
    }

    private static List<DateRangeDto> getTimetableWeekRanges(Timetable timetable) {
        List<DateRangeDto> result = new ArrayList<>();
        LocalDate start = timetable.getStartDate();
        if (start.getDayOfWeek() != DayOfWeek.MONDAY) {
            start = start.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        }
        while (start.isBefore(timetable.getEndDate())) {
            result.add(new DateRangeDto(start, start.with(TemporalAdjusters.next(DayOfWeek.SUNDAY))));
            start = start.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }

        return result;
    }

    public List<GroupTimetableDto> groupTimetables(Long schoolId) {
        StudyYear current = studyYearService.getCurrentStudyYear(schoolId);
        if (current == null) {
            return null;
        }
        
        Query q = em.createNativeQuery("select distinct sg.id, sg.code from student_group sg"
                + " join timetable_object_student_group tosg on sg.id=tosg.student_group_id"
                + " join timetable_object tobj on tosg.timetable_object_id=tobj.id"
                + " join timetable tt on tobj.timetable_id=tt.id"
                + " join study_period sp on tt.study_period_id=sp.id"
                + " join study_year sy on sp.study_year_id = sy.id"
                + " where tt.school_id=?1 and sy.id=?2 and tt.status_code in (:shownStatusCodes)"
                + " union"
                + " select distinct sg.id, sg.code from timetable_event te"
                + " join timetable_event_time tet on te.id = tet.timetable_event_id"
                + " join timetable_event_student_group tesg on tet.id = tesg.timetable_event_time_id"
                + " join student_group sg on tesg.student_group_id = sg.id"
                + " join study_year sy on sy.id = ?2"
                + " where te.school_id=?1 and te.timetable_object_id is null and (tet.start <= sy.end_date and tet.end >= sy.start_date)"
                + " order by 2");
        q.setParameter(1, schoolId);
        q.setParameter(2, current);
        q.setParameter("shownStatusCodes", shownStatusCodes());
        
        List<?> data = q.getResultList();
        return StreamUtil.toMappedList(r -> new GroupTimetableDto((Object[]) r), data);
    }

    public List<TeacherTimetableDto> teacherTimetables(Long schoolId) {
        StudyYear current = studyYearService.getCurrentStudyYear(schoolId);
        if (current == null) {
            return null;
        }
        
        Query q = em.createNativeQuery("select distinct t.id, p.firstname, p.lastname from teacher t"
                + " join person p on t.person_id = p.id"
                + " join journal_teacher jt on t.id = jt.teacher_id"
                + " join journal j on jt.journal_id = j.id"
                + " join timetable_object tobj on j.id = tobj.journal_id"
                + " join timetable tt on tt.id = tobj.timetable_id"
                + " join study_period sp on tt.study_period_id=sp.id"
                + " join study_year sy on sp.study_year_id = sy.id"
                + " where tt.school_id=?1 and sy.id=?2 and tt.status_code in (:shownStatusCodes)"
                + " union"
                + " select distinct t.id, p.firstname, p.lastname from teacher t"
                + " join person p on t.person_id = p.id"
                + " join subject_study_period_teacher sspp on sspp.teacher_id=t.id"
                + " join subject_study_period ssp on ssp.id=sspp.subject_study_period_id"
                + " join timetable_object tobj on tobj.subject_study_period_id=ssp.id"
                + " join timetable tt on tt.id=tobj.timetable_id"
                + " join study_period sp on tt.study_period_id=sp.id"
                + " join study_year sy on sp.study_year_id = sy.id"
                + " where tt.school_id=?1 and sy.id=?2 and tt.status_code in (:shownStatusCodes)"
                + " union"
                + " select distinct t.id, p.firstname, p.lastname  from timetable_event te"
                + " join timetable_event_time tet on te.id = tet.timetable_event_id"
                + " join timetable_event_teacher teta on tet.id = teta.timetable_event_time_id"
                + " join teacher t on teta.teacher_id = t.id"
                + " join study_year sy on sy.id = ?2"
                + " join person p on t.person_id = p.id and te.timetable_object_id is null"
                + " where te.school_id=?1 and te.timetable_object_id is null and (tet.start <= sy.end_date and tet.end >= sy.start_date)"
                + " order by lastname");
        q.setParameter(1, schoolId);
        q.setParameter(2, current);
        q.setParameter("shownStatusCodes", shownStatusCodes());
        
        List<?> data = q.getResultList();
        return StreamUtil.toMappedList(r -> new TeacherTimetableDto((Object[])r), data);
    }

    public List<RoomTimetableDto> roomTimetables(Long schoolId) {
        StudyYear current = studyYearService.getCurrentStudyYear(schoolId);
        if (current == null) {
            return null;
        }
        
        Query q = em.createNativeQuery("select distinct r.id, b.code as building_code, r.code as room_code from room r"
                + " join building b on r.building_id = b.id"
                + " join timetable_event_room ter on r.id=ter.room_id"
                + " join timetable_event_time tet on ter.timetable_event_time_id=tet.id"
                + " join timetable_event te on tet.timetable_event_id=te.id"
                + " join timetable_object tobj on te.timetable_object_id=tobj.id"
                + " join timetable tt on tobj.timetable_id=tt.id"
                + " join study_period sp on tt.study_period_id=sp.id"
                + " join study_year sy on sp.study_year_id = sy.id"
                + " where te.school_id=?1 and sy.id = ?2 and tt.status_code in (:shownStatusCodes)"
                + " union"
                + " select distinct r.id, b.code as building_code, r.code as room_code from room r"
                + " join building b on r.building_id = b.id"
                + " join timetable_event_room ter on r.id=ter.room_id"
                + " join timetable_event_time tet on ter.timetable_event_time_id=tet.id"
                + " join timetable_event te on tet.timetable_event_id=te.id"
                + " join study_year sy on sy.id = ?2"
                + " where te.school_id=?1 and te.timetable_object_id is null and (tet.start <= sy.end_date and tet.end >= sy.start_date)"
                + " order by room_code");
        q.setParameter(1, schoolId);
        q.setParameter(2, current);
        q.setParameter("shownStatusCodes", shownStatusCodes());
        
        List<?> data = q.getResultList();
        return StreamUtil.toMappedList(r -> new RoomTimetableDto((Object[])r), data);
    }
    
    List<String> shownStatusCodes() {
        HoisUserDetails user = userFromPrincipal();
        if (user != null && (user.isMainAdmin() || user.isSchoolAdmin() || user.isTeacher())) {
            if (user.isMainAdmin() || user.isSchoolAdmin()) {
                return ADMIN_TIMETABLES;
            } else if ( user.isTeacher()) {
                return TEACHER_TIMETABLES;
            }
        }
        return PUBLIC_TIMETABLES;
    }
    
    public List<TimetableStudyYearWeekDto> timetableStudyYearWeeks(Long schoolId) {
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);
        if (studyYear == null) {
            return null;
        }
        LocalDate start = studyYear.getStartDate();
        LocalDate end = studyYear.getEndDate();
        
        List<TimetableStudyYearWeekDto> weeks = new ArrayList<>();
        int weekNr = 0;
        while (start.isBefore(end)) {
            LocalDate weekStart = start.with(DayOfWeek.MONDAY);
            LocalDate weekEnd = start.with(DayOfWeek.SUNDAY);
            
            weeks.add(new TimetableStudyYearWeekDto(Long.valueOf(weekNr), weekStart,  weekEnd));
            
            weekNr++;
            start = weekEnd.plusDays(1);
        }
        return weeks;
    }
    
    public List<TimetableStudentStudyYearWeekDto> timetableStudyYearWeeksStudent(Student student) {
        Long schoolId = EntityUtil.getId(student.getSchool());
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);
        if (studyYear == null) {
            return null;
        }
        
        boolean isHigher = student.getCurriculumVersion().getCurriculum().getHigher().booleanValue();
        
        Set<DateRangeDto> periodsWithConnectedSubjects = isHigher
                ? higherPeriodsWithConnectedSubjects(EntityUtil.getId(student), EntityUtil.getId(studyYear))
                : vocationalPeriodsWithConnectedSubjects(EntityUtil.getId(student), EntityUtil.getId(studyYear));
        
        List<TimetableStudyYearWeekDto> weeks = timetableStudyYearWeeks(schoolId);
        List<TimetableStudentStudyYearWeekDto> studentWeeks = new ArrayList<>();
        
        for (TimetableStudyYearWeekDto week : weeks) {
            TimetableStudentStudyYearWeekDto studentWeek = new TimetableStudentStudyYearWeekDto(week.getWeekNr(), week.getStart(), week.getEnd());
            for (DateRangeDto period : periodsWithConnectedSubjects) {
                if (Boolean.TRUE.equals(studentWeek.getConnectedSubjects())) {
                    break;
                }
                studentWeek.setConnectedSubjects(Boolean.valueOf(
                        !week.getStart().isAfter(period.getEnd()) && !period.getStart().isAfter(week.getEnd())));
            }
            studentWeeks.add(studentWeek);
        }
        
        return studentWeeks;
    }
    
    private Set<DateRangeDto> higherPeriodsWithConnectedSubjects(Long studentId, Long studyYearId) {
        Query q = em.createNativeQuery("select sp.id, sp.start_date, sp.end_date from study_year sy " + 
                "join study_period sp on sy.id = sp.study_year_id " + 
                "join declaration d on sp.id = d.study_period_id " + 
                "join declaration_subject ds on d.id = ds.declaration_id " + 
                "where d.student_id = :studentId and sy.id = :studyYearId and d.status_code = :declarationStatus");
        q.setParameter("studentId", studentId);
        q.setParameter("studyYearId", studyYearId);
        q.setParameter("declarationStatus", DeclarationStatus.OPINGUKAVA_STAATUS_K.name());
        
        List<?> data = q.getResultList();
        Set<DateRangeDto> periodsWithConnectedSubjects = StreamUtil
                .toMappedSet(r -> new DateRangeDto(resultAsLocalDate(r, 1), resultAsLocalDate(r, 2)), data); 
        
        return periodsWithConnectedSubjects;
    }
    
    // TODO: return study year if there are student journals, temporary solution for unnecessary error messages
    private Set<DateRangeDto> vocationalPeriodsWithConnectedSubjects(Long studentId, Long studyYearId) {
        Query q = em.createNativeQuery("select sy.id, sy.start_date, sy.end_date from study_year sy " + 
                "join journal j on sy.id = j.study_year_id " + 
                "join journal_student js on j.id = js.journal_id " + 
                "where js.student_id = :studentId and sy.id = :studyYearId");
        q.setParameter("studentId", studentId);
        q.setParameter("studyYearId", studyYearId);
        
        List<?> data = q.getResultList();
        Set<DateRangeDto> periodsWithConnectedSubjects = StreamUtil
                .toMappedSet(r -> new DateRangeDto(resultAsLocalDate(r, 1), resultAsLocalDate(r, 2)), data); 
        
        return periodsWithConnectedSubjects;
    }

    public void sendTimetableChangesMessages(TimetableObject object) {
        Timetable timetable = object.getTimetable();
        if(ClassifierUtil.equals(TimetableStatus.TUNNIPLAAN_STAATUS_P, timetable.getStatus())) {
            // send automatic messages about timetable change
            List<Student> students;
            Subject subject = null;
            String journalName = null;
            if(Boolean.TRUE.equals(timetable.getIsHigher())) {
                // send message only to students who have declared this subject
                students = em.createQuery("select ds.declaration.student from DeclarationSubject ds where ds.declaration.status.code=?1 "
                        + "and ds.subjectStudyPeriod.id = ?2 and ds.declaration.student.studentGroup is not null", Student.class)
                    .setParameter(1, DeclarationStatus.OPINGUKAVA_STAATUS_K.name())
                    .setParameter(2, EntityUtil.getId(object.getSubjectStudyPeriod()))
                    .getResultList();
                Set<Long> studentGroups = StreamUtil.toMappedSet(r -> EntityUtil.getId(r.getStudentGroup()), object.getTimetableObjectStudentGroups());
                if(!studentGroups.isEmpty()) {
                    // filter by student group id
                    students = StreamUtil.toFilteredList(r -> studentGroups.contains(EntityUtil.getId(r.getStudentGroup())), students);
                }
                subject = object.getSubjectStudyPeriod().getSubject();
            } else {
                Journal journal = object.getJournal();
                students = StreamUtil.toMappedList(r -> r.getStudent(), journal.getJournalStudents());
                journalName = journal.getNameEt();
            }
            for(Student student : students) {
                if(StudentUtil.isActive(student)) {
                    TimetableChanged msg = new TimetableChanged(student, subject, journalName);
                    automaticMessageService.sendMessageToStudent(MessageType.TEATE_LIIK_MUUD_TUNNIPL, student, msg);
                }
            }
        }
    }
    
    private List<ClassifierDto> vocationalTimetableCapacities(Timetable timetable, List<TimetableStudentGroupCapacityDto> studentGroupCapacities) {
        List<ClassifierDto> schoolCapacities = timetableSchoolCapacityTypeDtos(timetable);
        Set<String> groupCapacityCodes = StreamUtil.toMappedSet(sgc -> sgc.getCapacityType(), studentGroupCapacities);
        addMissingGroupCapacities(schoolCapacities, groupCapacityCodes);
        return schoolCapacities;
    }

    private List<ClassifierDto> higherTimetableCapacities(Timetable timetable, List<HigherTimetableStudentGroupCapacityDto> studentGroupCapacities) {
        List<ClassifierDto> schoolCapacities = timetableSchoolCapacityTypeDtos(timetable);
        Set<String> groupCapacityCodes = StreamUtil.toMappedSet(sgc -> sgc.getCapacityType(), studentGroupCapacities);
        addMissingGroupCapacities(schoolCapacities, groupCapacityCodes);
        return schoolCapacities;
    }
    
    private List<ClassifierDto> timetableSchoolCapacityTypeDtos(Timetable timetable) {
        SchoolCapacityTypeCommand command = new SchoolCapacityTypeCommand();
        command.setIsHigher(timetable.getIsHigher());
        command.setIsTimetable(Boolean.TRUE);
        
        return autocompleteService.schoolCapacityTypeDtos(EntityUtil.getId(timetable.getSchool()), command);
    }
    
    private void addMissingGroupCapacities(List<ClassifierDto> schoolCapacities, Set<String> groupCapacityCodes) {
        Set<String> schoolCapacityCodes = StreamUtil.toMappedSet(sc -> sc.getCode(), schoolCapacities);
        for (String code : groupCapacityCodes) {
            if (!schoolCapacityCodes.contains(code)) {
                schoolCapacities.add(ClassifierDto.of(em.getReference(Classifier.class, code)));
            }
        }
    }

    static HoisUserDetails userFromPrincipal() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        if (principal != null && !(principal instanceof AnonymousAuthenticationToken)) {
            return HoisUserDetails.fromPrincipal(principal);
        }
        return null;
    }

    private static class AllocatedLessons {
        private final Long currentWeekAllocated;
        private final Long totalAllocated;
        private final String capacityType;

        public AllocatedLessons(Long currentWeekAllocated, Long totalAllocated, String capacityType) {
            this.currentWeekAllocated = currentWeekAllocated;
            this.totalAllocated = totalAllocated;
            this.capacityType = capacityType;
        }

        public Long getCurrentWeekAllocated() {
            return currentWeekAllocated;
        }

        public Long getTotalAllocated() {
            return totalAllocated;
        }

        public String getCapacityType() {
            return capacityType;
        }
    }
    
    private static class LeftOverLessons {
        private final Long totalHours;
        private final Long outsidePeriodAllocated;
        private final Long periodAllocated;
        private final Long currentWeekAllocated;
        private final Long studentGroup;
        private final String capacityType;
        
        public LeftOverLessons(Long totalHours, Long outsidePeriodAllocated, Long periodAllocated, Long currentWeekAllocated, 
                Long studentGroup, String capacityType) {
            this.totalHours = totalHours;
            this.outsidePeriodAllocated = outsidePeriodAllocated;
            this.periodAllocated = periodAllocated;
            this.currentWeekAllocated = currentWeekAllocated;
            this.studentGroup = studentGroup;
            this.capacityType = capacityType;
        }

        public Long getTotalHours() {
            return totalHours;
        }

        public Long getOutsidePeriodAllocated() {
            return outsidePeriodAllocated;
        }

        public Long getPeriodAllocated() {
            return periodAllocated;
        }

        public Long getCurrentWeekAllocated() {
            return currentWeekAllocated;
        }

        public Long getStudentGroup() {
            return studentGroup;
        }

        public String getCapacityType() {
            return capacityType;
        }
    }

	public Document getExportedWeek(LocalDate startDate, LocalDate endDate, StudyPeriod studyPeriod, HoisUserDetails user) {
		
		Long schoolId = user.getSchoolId();
		
		//Set general
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from study_year sy join school s on s.id = sy.school_id");
        qb.requiredCriteria("s.id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("sy.id = :studyYearId", "studyYearId", EntityUtil.getId(studyPeriod.getStudyYear()));
        String select = "s.name_et, sy.start_date, sy.end_date";
        		
        //Formatters for LocalDate and LocalTime
        List<?> data = qb.select(select, em).setMaxResults(1).getResultList();
        
        Document document = new Document();
        document.setGenerationDate(documentDateFormat.format(LocalDate.now()));
        document.setGenerationTime(documentTimeFormat.format(LocalTime.now()));
        
        // Set general data
        List<General> generals = StreamUtil
                .toMappedList(r -> new General(resultAsString(r, 0),
                		documentDateFormat.format(resultAsLocalDate(r, 1)),
                		documentDateFormat.format(resultAsLocalDate(r, 2)),
                		"Tunniplaan " + documentDateFormatYear.format(resultAsLocalDate(r, 1)) + "/" + documentDateFormatYear.format(resultAsLocalDate(r, 2)),
                		resultAsString(r, 0),
                		documentDateFormatHois.format(startDate) + "-" + documentDateFormatHois.format(endDate),
                		documentDateFormat.format(startDate),
                		documentDateFormat.format(endDate)), data);
        document.setGeneral(generals.get(0));
        
        //Set time periods
        JpaNativeQueryBuilder timePeriodQuery = new JpaNativeQueryBuilder("from lesson_time lt"
                + " join lesson_time_building_group ltbg on ltbg.id = lt.lesson_time_building_group_id"
                + " join lesson_time_building ltb on ltb.lesson_time_building_group_id = ltbg.id").sort("ltb.building_id");

        timePeriodQuery.requiredCriteria("lt.school_id = :schoolId", "schoolId", schoolId);
        timePeriodQuery.requiredCriteria("ltbg.valid_from <= :timetableStartDate", "timetableStartDate", startDate);
        timePeriodQuery.filter("(ltbg.valid_thru >= :timetableStartDate or ltbg.valid_thru is null)");
        
        String timePeriodSelect = "distinct lt.id, lt.start_time, lt.end_time, lt.lesson_nr, lt.day_mon, lt.day_tue, lt.day_wed"
                + " ,lt.day_thu, lt.day_fri, lt.day_sat, lt.day_sun, ltb.building_id";
        List<?> timePeriodResult= timePeriodQuery.select(timePeriodSelect, em).getResultList();

        Set<TimePeriod> timePeriods = StreamUtil
              .toMappedSet(r -> new ee.hitsa.ois.xml.exportTimetable.TimePeriod(getLessonNumber(resultAsInteger(r, 3)),
        			Arrays.asList(resultAsBoolean(r, 4), resultAsBoolean(r, 5), resultAsBoolean(r, 6), resultAsBoolean(r, 7), resultAsBoolean(r, 8), resultAsBoolean(r, 9), resultAsBoolean(r, 10)),
              		resultAsInteger(r, 3),
              		documentTimeFormatShort.format(resultAsLocalTime(r, 1)),
              		documentTimeFormatShort.format(resultAsLocalTime(r, 2))), timePeriodResult);
        Set<TimePeriod> extendedList = new HashSet<>();
        
        //Set periods by week day
        for (TimePeriod period : timePeriods) {
        	Integer weekdayNr = 1;
        	for (Boolean isWeekDay : period.getDays()) {
        		if (isWeekDay) {
        			extendedList.add(new TimePeriod(
        					"TP_" + weekdayNr + period.getId(),
            				weekdayNr,
            				period.getPeriod(),
            				period.getStarttime(),
            				period.getEndtime()
            				));
        		}
        		weekdayNr++;
        	}
        }
        
        extendedList = extendedList.stream().filter(distinctByKey(TimePeriod::getId)).collect(Collectors.toSet());
        
        document.setTimeperiods(new TimePeriods(extendedList));
        
        //Set rooms
        Query roomQuery = em.createNativeQuery("select b.code as buildingCode, r.code as roomCode, r.name from room r"
        		+ " join building b on b.id = r.building_id"
        		+ " where b.school_id = :schoolId");
        roomQuery.setParameter("schoolId", schoolId);
        List<?> dbRooms = roomQuery.getResultList();
        Set<ee.hitsa.ois.xml.exportTimetable.Room> rooms = StreamUtil
                .toMappedSet(r -> new ee.hitsa.ois.xml.exportTimetable.Room(("RM_" 
                + resultAsString(r, 0)
                + resultAsString(r, 1)).replaceAll(" ", ""),
                "RM_" + resultAsString(r, 1).replaceAll(" ", "")), dbRooms);
        document.setRooms(new Rooms(rooms));
        
        //Set journals
        //Get journals, teachers, student_groups, lessons
        Integer weekNr = studyPeriod.getWeekNrForDate(startDate);
        JpaNativeQueryBuilder journalQuery = new JpaNativeQueryBuilder("from journal_omodule_theme jot " +
        	    "join journal j on jot.journal_id = j.id " +
        	    "join journal_capacity jc on jc.journal_id = j.id " +
        	    "join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id " +
        	    "join lesson_plan lp on lp.id = lpm.lesson_plan_id " +
        	    "join student_group sg on sg.id = lp.student_group_id " +
        	    "join curriculum c on c.id = sg.curriculum_id " +
        	    "left join teacher t on sg.teacher_id = t.id and t.untis_code is not null").sort("j.id, sg.code");
        journalQuery.requiredCriteria("j.school_id = :schoolId", "schoolId", schoolId);
        journalQuery.requiredCriteria("jc.week_nr = :weekNr", "weekNr",  weekNr.longValue());
        journalQuery.requiredCriteria("jc.study_period_id = :studyPeriod", "studyPeriod",  studyPeriod.getId());
        journalQuery.filter("lp.is_usable = true");
        journalQuery.filter("j.untis_code is not null");
        journalQuery.groupBy("j.untis_code, j.name_et, sg.code, j.id, c.name_et, t.untis_code");
        String journalSelect = "j.untis_code as journalCode," + 
        		" j.name_et as journalName," + 
        		" sg.code as StudentGroupCode," + 
        		" c.name_et as curriculumName," + 
        		" t.untis_code as teacherCode," + 
        		" j.id as journalId," + 
        		" sum(jc.hours) as capacity";
        
        List<?> dbJournals= journalQuery.select(journalSelect, em).getResultList();
        Set<ee.hitsa.ois.xml.exportTimetable.Subject> subjects = StreamUtil
                .toMappedSet(r -> new ee.hitsa.ois.xml.exportTimetable.Subject("SU_" 
                + resultAsString(r, 0),
                resultAsString(r, 1)), dbJournals);
        subjects = subjects.stream().filter(distinctByKey(ee.hitsa.ois.xml.exportTimetable.Subject::getId)).collect(Collectors.toSet());
        document.setSubjects(new Subjects(subjects));
        
        //Set teacher
        List<Long> journalIds = StreamUtil.toMappedList(r->resultAsLong(r, 5), dbJournals);
        if (!journalIds.isEmpty()) {
        	Query teacherQuery = em.createNativeQuery("select t.untis_code, p.firstname, p.lastname, p.sex_code"
            		+ " from journal_teacher jt"
            		+ " join journal j on j.id = jt.journal_id"
            		+ " join teacher t on jt.teacher_id = t.id"
            		+ " join person p on p.id = t.person_id"
            		+ " where jt.journal_id in :journalIds"
            		+ " and t.untis_code is not null");
            teacherQuery.setParameter("journalIds", journalIds);
            List<?> dbTeachers = teacherQuery.getResultList();
            
            Set<ee.hitsa.ois.xml.exportTimetable.Teacher> teachers = StreamUtil
                    .toMappedSet(r -> new ee.hitsa.ois.xml.exportTimetable.Teacher("TR_" 
                    + resultAsString(r, 0),
                    resultAsString(r, 1),
                    resultAsString(r, 2),
                    resultAsString(r, 3).substring(resultAsString(r, 3).length()-1)), dbTeachers);
            teachers = teachers.stream().filter(distinctByKey(ee.hitsa.ois.xml.exportTimetable.Teacher::getId)).collect(Collectors.toSet());
            document.setTeachers(new Teachers(teachers));
        }
      
        //Set classes
        Set<ee.hitsa.ois.xml.exportTimetable.Class> classes = StreamUtil
                .toMappedSet(r -> {
                	if (resultAsString(r, 4) == null) {
                		return new ee.hitsa.ois.xml.exportTimetable.Class("CL_" + resultAsString(r, 2),resultAsString(r, 3));
                	} else {
                		return new ee.hitsa.ois.xml.exportTimetable.Class("CL_" + resultAsString(r, 2),resultAsString(r, 3), new ClassTeacher("TR_" + resultAsString(r, 4)));
                	}
                }, dbJournals);
        classes = classes.stream().filter(distinctByKey(ee.hitsa.ois.xml.exportTimetable.Class::getId)).collect(Collectors.toSet());
        document.setClasses(new Classes(classes));
        
        //Set lessons
        Set<ee.hitsa.ois.xml.exportTimetable.Lesson> lessons = StreamUtil
                .toMappedSet(r -> new ee.hitsa.ois.xml.exportTimetable.Lesson("LS_" 
                                + resultAsLong(r, 5),
                                resultAsInteger(r, 6),
                                new LessonSubject("SU_" + resultAsString(r, 0)),
                                documentDateFormat.format(startDate),
                                documentDateFormat.format(endDate),
                                "1111F111111F111111F111111F111111F111111F111111F111111F111111F111111F111111F111111F"
                                + "111111F111111F111111F111111F111111F111111F111111F111111F111111F111111F111111F"
                                + "111111F111111F111111F111111F111111F111111F111111F111111F111111F111111F111111F"
                                + "111111F111111F111111F111111F111111F111111F111111F11111"), dbJournals);
        
        if(!journalIds.isEmpty()) {
        	Query lessonRooms = em.createNativeQuery("select concat(b.code, rr.code) as buildingRoom, jr.journal_id"
            		+ " from journal_room jr"
            		+ " join room rr on jr.room_id = rr.id"
            		+ " join building b on rr.building_id = b.id"
            		+ " where jr.journal_id in :journalIds");
            lessonRooms.setParameter("journalIds", journalIds);
            List<?> dbLessonRooms = lessonRooms.getResultList();
            
            // Attach rooms to lessons
            StreamUtil.toMappedList(r -> {
                    	for (Lesson lesson : lessons) {
                    		if (Long.parseLong(lesson.id.replace("LS_", "")) == resultAsLong(r, 1) && !"null".equals(resultAsString(r, 0))) {
                    			if (lesson.getLesson_room() == null) {
                    				lesson.setLesson_room(new LessonRoom("RM_" + resultAsString(r, 0).replaceAll(" ", "")));
                    			} else {
                    				lesson.getLesson_room().addId("RM_" + resultAsString(r, 0).replaceAll(" ", ""));
                    			}
                    		}
                    	}
                    	return resultAsString(r, 0);
                    },dbLessonRooms);
            
            Query lessonTeachers = em.createNativeQuery("select tt.untis_code, jt.journal_id, pp.firstname, pp.lastname"
            		+ " from journal_teacher jt"
            		+ " join teacher tt on tt.id = jt.teacher_id"
            		+ " join person pp on pp.id = tt.person_id"
            		+ " where jt.journal_id in :journalIds"
            		+ " and tt.untis_code is not null");
            lessonTeachers.setParameter("journalIds", journalIds);
            List<?> dbLessonTeachers = lessonTeachers.getResultList();
            
            // Attach teachers to lessons
            StreamUtil.toMappedList(r -> {
                    	for (Lesson lesson : lessons) {
                    		if (Long.parseLong(lesson.id.replace("LS_", "")) == resultAsLong(r, 1) && !"null".equals(resultAsString(r, 0))) {
                    			if (lesson.getLesson_teacher() == null) {
                    				lesson.setLesson_teacher(new LessonTeacher("TR_" + resultAsString(r, 0)));
                    			} else {
                    				lesson.getLesson_teacher().addId("TR_" + resultAsString(r, 0));
                    			}
                    		}
                    	}
                    	return resultAsString(r, 0);
                    },dbLessonTeachers);
            lessons.removeIf(p -> p.getLesson_teacher() == null);
        }
        
        // Put student groups from journal query result to lesson
        
        StreamUtil.toMappedList(r -> {
        	for (Lesson lesson : lessons) {
        		if (Long.parseLong(lesson.id.replace("LS_", "")) == resultAsLong(r, 5)) {
        			if (lesson.getLesson_classes() == null) {
        				lesson.setLesson_classes(new LessonClasses("CL_" + resultAsString(r, 2)));
        			} else {
        				lesson.getLesson_classes().addId("CL_" + resultAsString(r, 2));
        			}
        		}
        	}
        	return resultAsString(r, 0);
        },dbJournals);
        
        // Filter duplicate journal identifiers
         Set<Lesson> filteredLessons = lessons.stream().filter(distinctByKey(p->p.id)).collect(Collectors.toSet());
        // Extend lessons, until this moment, teachers are kept as list of strings on one line divided by space
        Set<Lesson> extendedLessons = extendLessonsByTeacher(filteredLessons);
        
        document.setLessons(new Lessons(extendedLessons));
		return document;
	}
	private String getLessonNumber(Integer lessonNumber) {
		if (lessonNumber == null) {
			return "00";
		}
		int lessonNumberLength = lessonNumber.toString().length();
		if (lessonNumberLength == 1) {
			return "0" + lessonNumber;
		} else {
			return lessonNumber.toString();
		}
	}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	    Set<Object> seen = ConcurrentHashMap.newKeySet();
	    return t -> seen.add(keyExtractor.apply(t));
	}
	
	private Set<Lesson> extendLessonsByTeacher(Set<Lesson> lessons) {
		Set<Lesson> extendedLessons = new HashSet<>();
        
        for (Lesson lesson : lessons) {
        	if (lesson.getLesson_teacher() == null) {
        		lesson.setId(lesson.id + "00");
        		extendedLessons.add(lesson);
        		continue;
        	}
        	String[] teachers = lesson.getLesson_teacher().getId().split(" ");
        	if (teachers.length == 0) {
        		lesson.setId(lesson.id + "00");
        	} else {
        		for (int index = 0; index < teachers.length; index++) {
        			Lesson newLesson = new Lesson(lesson.id + getLessonNumber(index), lesson.getPeriods(), lesson.getLesson_subject(), lesson.getLesson_room(),
        					lesson.getLesson_classes(), lesson.getEffectivebegindate(), lesson.getEffectiveenddate(), lesson.getOccurence());
        			newLesson.setLesson_teacher(new LessonTeacher(teachers[index]));
        			extendedLessons.add(newLesson);
        		}
        	}
        }
        return extendedLessons;
	}
	
	public void checkUntiscodes(LocalDate startDate, LocalDate endDate, StudyPeriod studyPeriod, HoisUserDetails user) {
        Long schoolId = user.getSchoolId();
        Integer weekNr = studyPeriod.getWeekNrForDate(startDate);
        JpaNativeQueryBuilder journalQuery = new JpaNativeQueryBuilder("from journal_omodule_theme jot " +
                "join journal j on jot.journal_id = j.id " +
                "join journal_capacity jc on jc.journal_id = j.id " +
                "join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id " +
                "join lesson_plan lp on lp.id = lpm.lesson_plan_id " +
                "join student_group sg on sg.id = lp.student_group_id " +
                "join curriculum c on c.id = sg.curriculum_id " +
                "left join teacher t on sg.teacher_id = t.id " +
                "left join person p on p.id = t.person_id");
        journalQuery.requiredCriteria("j.school_id = :schoolId", "schoolId", schoolId);
        journalQuery.requiredCriteria("jc.week_nr = :weekNr", "weekNr",  weekNr.longValue());
        journalQuery.requiredCriteria("jc.study_period_id = :studyPeriod", "studyPeriod",  studyPeriod.getId());
        journalQuery.filter("lp.is_usable = true");
        journalQuery.filter("j.untis_code is not null");
        journalQuery.groupBy("j.untis_code, j.name_et, sg.code, j.id, c.name_et, t.untis_code, t.id, p.firstname, p.lastname");
        String journalSelect = "j.untis_code as journalCode," + 
                " j.name_et as journalName," + 
                " sg.code as StudentGroupCode," + 
                " c.name_et as curriculumName," + 
                " t.untis_code as teacherCode," + 
                " j.id as journalId," + 
                " t.id as teacherId," +
                " p.firstname," +
                " p.lastname";
        List<?> dbJournals= journalQuery.select(journalSelect, em).getResultList();
        List<TeacherStudentGroupAndCode> teacherCodes = StreamUtil
        		.toMappedList(r-> new TeacherStudentGroupAndCode(resultAsString(r, 7) + " " + resultAsString(r, 8), resultAsString(r, 4)), dbJournals);
        List<JournalNameAndCode> journalCodes = StreamUtil.toMappedList(r-> new JournalNameAndCode(resultAsString(r, 1), resultAsString(r, 0)), dbJournals);
        teacherCodes = teacherCodes.stream()
        		.filter(p->(p.getTeacherCode() == null || "null".equals(p.getTeacherCode())) && !"null null".equals(p.getName())).collect(Collectors.toList());
        journalCodes = journalCodes.stream()
        		.filter(p->p.getJournalCode() == null || "null".equals(p.getJournalCode())).collect(Collectors.toList());
        
        if (!teacherCodes.isEmpty() && !journalCodes.isEmpty()) {
        	throw new HoisException("Järgnevatel õpperühmade juhatajatel puudub tunniplaani kood : " +
        			String.join(", ", teacherCodes.stream().filter(distinctByKey(TeacherStudentGroupAndCode::getName))
        					.map(TeacherStudentGroupAndCode::getName).collect(Collectors.toList())) + ".\n" +
        			"Järgnevatel päevikutel puudub tunniplaani kood : " +
        			String.join(", ", journalCodes.stream().filter(distinctByKey(JournalNameAndCode::getJournalName))
        					.map(JournalNameAndCode::getJournalName).collect(Collectors.toList())) + ".");
        } else if (!teacherCodes.isEmpty()) {
        	throw new HoisException("Järgnevatel õpperühmade juhatajatel puudub tunniplaani kood : " +
        			String.join(", ", teacherCodes.stream().filter(distinctByKey(TeacherStudentGroupAndCode::getName))
        					.map(TeacherStudentGroupAndCode::getName).collect(Collectors.toList())) + ".");
        } else if (!journalCodes.isEmpty()) {
        	throw new HoisException("Järgnevatel päevikutel puudub tunniplaani kood : " +
        			String.join(", ", journalCodes.stream().filter(distinctByKey(JournalNameAndCode::getJournalName))
        					.map(JournalNameAndCode::getJournalName).collect(Collectors.toList())) + ".");
        }
        List<Long> journalIds = StreamUtil.toMappedList(r->resultAsLong(r, 5), dbJournals);
        if (!journalIds.isEmpty()) {
        	Query teacherQuery = em.createNativeQuery("select t.untis_code, p.firstname, p.lastname, p.sex_code"
            		+ " from journal_teacher jt"
            		+ " join journal j on j.id = jt.journal_id"
            		+ " join teacher t on jt.teacher_id = t.id"
            		+ " join person p on p.id = t.person_id"
            		+ " where jt.journal_id in :journalIds");
            teacherQuery.setParameter("journalIds", journalIds);
            List<?> dbTeachers = teacherQuery.getResultList();
            
            //Using StudentGroup as teacher first and last name.
            List<TeacherStudentGroupAndCode> nameAndCode = StreamUtil
            		.toMappedList(r-> new TeacherStudentGroupAndCode(resultAsString(r, 1) + " " + resultAsString(r, 2), resultAsString(r, 0)), dbTeachers);
            nameAndCode = nameAndCode.stream()
            		.filter(p->p.getTeacherCode() == null || "null".equals(p.getTeacherCode())).collect(Collectors.toList());
            if (!teacherCodes.isEmpty()) {
            	throw new HoisException("Järgnevatel õpetajatel puudub tunniplaani kood : " +
            			String.join(", ", teacherCodes.stream().filter(distinctByKey(TeacherStudentGroupAndCode::getName)).map(TeacherStudentGroupAndCode::getName).collect(Collectors.toList())) + ".");
            }
        }
    }
	
	private class TeacherStudentGroupAndCode {
		private String name;
		private String teacherCode;
		private Long teacherId;
		
		public TeacherStudentGroupAndCode(String name, String teacherCode, Long teacherId) {
			this.name = name;
			this.teacherCode = teacherCode;
			this.teacherId = teacherId;
		}
		
		public TeacherStudentGroupAndCode(String name, String teacherCode) {
			this.name = name;
			this.teacherCode = teacherCode;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getTeacherCode() {
			return teacherCode;
		}
		public void setTeacherCode(String teacherCode) {
			this.teacherCode = teacherCode;
		}
		public Long getTeacherId() {
			return teacherId;
		}
		public void setTeacherId(Long teacherId) {
			this.teacherId = teacherId;
		}
	}
	
	private class JournalNameAndCode {
		private String journalName;
		private String journalCode;
		public JournalNameAndCode(String journalName, String journalCode) {
			this.journalName = journalName;
			this.journalCode = journalCode;
		}
		public String getJournalCode() {
			return journalCode;
		}
		public void setJournalCode(String journalCode) {
			this.journalCode = journalCode;
		}
		public String getJournalName() {
			return journalName;
		}
		public void setJournalName(String journalName) {
			this.journalName = journalName;
		}
	}

	public String importXml(HoisUserDetails user, OisFileDto oisFile, LocalDate startDate, LocalDate endDate, StudyPeriod studyPeriod, Boolean isHigher) {
		String returnValue = null;
		org.w3c.dom.Document document = null;
		 try {
			document = byteArrayToDocument(oisFile.getFdata());
		} catch (Exception e) {
			throw new HoisException( "Fail pole XML formaadis.");
		}
		if (document != null) {
			Integer weekNr = studyPeriod.getWeekNrForDate(startDate);
			Long schoolId = user.getSchoolId();
	        
	        Timetable timetable = em.createQuery("select tt from Timetable tt "
	        		+ "where tt.school.id = ?1 "
	        		+ "and tt.studyPeriod.id = ?2 "
	        		+ "and tt.startDate = ?3 "
	        		+ "and tt.endDate = ?4 " 
	        		+ "and tt.isHigher = ?5", Timetable.class)
	        		.setParameter(1, schoolId)
	        		.setParameter(2, studyPeriod.getId())
	        		.setParameter(3, startDate)
	        		.setParameter(4, endDate)
	        		.setParameter(5, isHigher)
	        		.getSingleResult();
	        
	        // Delete existing imported timetable events and remove them from java object
	        EntityUtil.setUsername(user.getUsername(), em);
	        Iterator<TimetableObject> timetableIterator = timetable.getTimetableObjects().iterator();
	        while (timetableIterator.hasNext()) {
	        	TimetableObject timetableObject = timetableIterator.next();
	        	Iterator<TimetableEvent> timetableEventIterator = timetableObject.getTimetableEvents().iterator();
	        	while (timetableEventIterator.hasNext()) {
	        		TimetableEvent timetableEvent = timetableEventIterator.next();
	        		if (timetableEvent.getIsImported() != null && timetableEvent.getIsImported()) {
	    	        	EntityUtil.deleteEntity(timetableEvent, em);
	    	        	timetableEventIterator.remove();
	        		}
	        	}
	        	if (timetableObject.getTimetableEvents().isEmpty()) {
	        		timetableIterator.remove();
	        	}
	        }
	        EntityUtil.save(timetable, em);
			NodeList nodeList = document.getElementsByTagName("lesson");
			// Find node <lesson>
			for (int lessonIndex = 0; lessonIndex < nodeList.getLength(); lessonIndex++) {
				Node node = nodeList.item(lessonIndex);
				
				// Get journal
	            String journalId =  node.getAttributes().item(0).getTextContent();
	            Journal journal = em.getReference(Journal.class, Long.parseLong(journalId.substring(3, journalId.length() - 2)));
				// Update timetable object
	            TimetableObject timetableObject = null;
	            if (journal != null) {
	            	try {
	            		timetableObject = timetableObjectRepository.findByJournalAndTimetable(journal, timetable);
	            	} catch(EntityNotFoundException e) {
	            		returnValue = "Päevik " + journalId + " ei kuulu " + timetable.getId() + " tunniplaani.";
	            		continue;
	            	}
	            }
	            if (timetableObject == null) {
	            	timetableObject = new TimetableObject();
	            	timetableObject.setJournal(journal);
	            	timetableObject.setTimetable(timetable);
	            }
				NodeList lessonChildrenList = node.getChildNodes();
				NodeList lessontimesNodeList = null;
				for (int lessonChildrenIndex = 0; lessonChildrenIndex < lessonChildrenList.getLength(); lessonChildrenIndex++) {
					// Find node <times>
					Node lessonTimes = lessonChildrenList.item(lessonChildrenIndex);
					if (lessonTimes.getNodeType() == Node.ELEMENT_NODE && Objects.equals("times", lessonTimes.getNodeName())) {
						lessontimesNodeList = lessonTimes.getChildNodes();
					}
				}
				if (lessontimesNodeList == null) {
					returnValue = "Dokument on vales formaadis, päevikul " + journalId + " puuduvad ajad.";
					continue;
				}
				for (int lessontimesNodeChild = 0; lessontimesNodeChild < lessontimesNodeList.getLength(); lessontimesNodeChild++) {
					Node lessonTime = lessontimesNodeList.item(lessontimesNodeChild);
					if (lessonTime.getNodeType() == Node.ELEMENT_NODE && Objects.equals("time", lessonTime.getNodeName())) {
			            Integer day = null;
			            Short period = null;
			            String startTime = null;
			            String endTime = null;
			            NodeList lessonTimeChildren = lessonTime.getChildNodes();
			            List<String> buildingRoomCodes = null;
			            
			            // Find <time> child nodes and assign its node values
			            try {
			            	for (int timeNodeIndex = 0; timeNodeIndex < lessonTimeChildren.getLength(); timeNodeIndex++) {
				            	Node lessonTimeNode = lessonTimeChildren.item(timeNodeIndex);
				            	if (lessonTimeNode.getNodeType() == Node.ELEMENT_NODE && "assigned_day".equals(lessonTimeNode.getNodeName())) {
				            		day = Integer.parseInt(lessonTimeNode.getTextContent());
				            	} else if (lessonTimeNode.getNodeType() == Node.ELEMENT_NODE && "assigned_period".equals(lessonTimeNode.getNodeName())) {
				            		period = Short.valueOf(lessonTimeNode.getTextContent());
				            	} else if (lessonTimeNode.getNodeType() == Node.ELEMENT_NODE && "assigned_starttime".equals(lessonTimeNode.getNodeName())) {
				            		startTime = lessonTimeNode.getTextContent();
				            	} else if (lessonTimeNode.getNodeType() == Node.ELEMENT_NODE && "assigned_endtime".equals(lessonTimeNode.getNodeName())) {
				            		endTime = lessonTimeNode.getTextContent();
				            	} else if (lessonTimeNode.getNodeType() == Node.ELEMENT_NODE && "assigned_room".equals(lessonTimeNode.getNodeName())) {
				            		buildingRoomCodes = new ArrayList<String>(Arrays.asList(lessonTimeNode.getAttributes().item(0).getTextContent().split(" ")));
				            	}
				            }
			            } catch (NumberFormatException nfe) {
			            	throw new HoisException("Tunni päev või periood ei ole numbriformaadis.");
			            }
			            if (day == null || period == null || startTime == null || endTime == null) {
			            	returnValue = "Päeviku " + journal.getNameEt() + " tunni toimumise kohta pole piisavalt andmeid.";
			            	continue;
			            }
			            
			            // Get teacher and student groups
			            String teacherUntisCode = null;
			            List<String> studentGroupCodes = null;
			            for (int lessonChildren = 0; lessonChildren < lessonChildrenList.getLength(); lessonChildren++) {
			            	Node lessonNode = lessonChildrenList.item(lessonChildren);
			            	if (lessonNode.getNodeType() == Node.ELEMENT_NODE && "lesson_teacher".equals(lessonNode.getNodeName())) {
			            		teacherUntisCode = lessonNode.getAttributes().item(0).getTextContent().substring(3);
			            	} else if (lessonNode.getNodeType() == Node.ELEMENT_NODE && "lesson_classes".equals(lessonNode.getNodeName())) {
			            		studentGroupCodes = new ArrayList<String>(Arrays.asList(lessonNode.getAttributes().item(0).getTextContent().split(" ")));
			            	}
			            }
			            
			            if (studentGroupCodes == null) {
			            	returnValue = "Päevikul " + journal.getNameEt() + " puuduvad õpperühmad.";
			            	continue;
			            } else if (buildingRoomCodes == null) {
			            	returnValue = "Päevikul " + journal.getNameEt() + " puuduvad ruumid.";
			            } else if (teacherUntisCode == null) {
			            	returnValue = "Päevikul " + journal.getNameEt() + " puudub õpetaja.";
			            	continue;
			            }
			            
			            // Remove CL_ and RM_ from the start of string
			            studentGroupCodes = studentGroupCodes.stream().map(p->p.replace("CL_", "")).collect(Collectors.toList());
			            Set<Room> rooms = null;
			            if (buildingRoomCodes != null) {
			            	buildingRoomCodes = buildingRoomCodes.stream().map(p->p.replace("RM_", "")).collect(Collectors.toList());
				            
				            // Get rooms by code
				            
				            Query roomQuery = em.createNativeQuery("select r.id as buildingRoom from room r"
				            		+ " join building b on b.id = r.building_id"
				            		+ " where b.school_id = :schoolId"
				            		+ " and replace(concat(b.code, r.code), ' ', '') in (:buildingRoomCodes)");
				            roomQuery.setParameter("schoolId", schoolId);
				            roomQuery.setParameter("buildingRoomCodes", buildingRoomCodes);
				            List<?> dbRooms = roomQuery.getResultList();
				            rooms = StreamUtil.toMappedSet(r -> em.getReference(Room.class, resultAsLong(r, 0)), dbRooms);
			            }
			            
			            // Get teacher by code
			            Teacher teacher = em.createQuery("select t from Teacher t "
				        		+ "where t.untisCode = ?1 "
				        		+ "and t.school.id = ?2", Teacher.class)
				        		.setParameter(1, teacherUntisCode)
				        		.setParameter(2, schoolId)
				        		.getSingleResult();
			            
			            // Get student groups
			            List<StudentGroup> studentGroups = em.createQuery("select sg from StudentGroup sg "
				        		+ "where sg.code in (?1) "
				        		+ "and sg.school.id = ?2", StudentGroup.class)
				        		.setParameter(1, studentGroupCodes)
				        		.setParameter(2, schoolId)
				        		.getResultList();
			            
			            TimetableEvent timetableEvent = new TimetableEvent();
			            LocalTime startHourMinutes = LocalTime.parse(startTime, documentTimeFormatShort);
			            LocalTime endHourMinutes = LocalTime.parse(endTime, documentTimeFormatShort);
			            timetableEvent.setStart(startDate.plusDays(day-1).atTime(startHourMinutes.getHour(), startHourMinutes.getMinute()));
			            timetableEvent.setEnd(startDate.plusDays(day-1).atTime(endHourMinutes.getHour(), endHourMinutes.getMinute()));
			            timetableEvent.setLessonNr(period);
			            timetableEvent.setTimetableObject(timetableObject);
			            timetableEvent.setSchool(em.getReference(School.class, user.getSchoolId()));
			            timetableEvent.setRepeatCode(em.getReference(Classifier.class, TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_EI.name()));
			            timetableEvent.setIsImported(true);
			            
			            TimetableEventTime timetableEventTime = new TimetableEventTime();
			            timetableEventTime.setStart(timetableEvent.getStart());
			            timetableEventTime.setEnd(timetableEvent.getEnd());
			            timetableEventTime.setTimetableEvent(timetableEvent);
			            
			            List<TimetableEventTime> timetableEventTimes = timetableEvent.getTimetableEventTimes();
			            timetableEventTimes.add(timetableEventTime);
			            timetableEvent.setTimetableEventTimes(timetableEventTimes);
			            
			            List<TimetableEvent> timetableEvents = timetableObject.getTimetableEvents();
			            timetableEvents.add(timetableEvent);
			            timetableObject.setTimetableEvents(timetableEvents);
		            	List<TimetableObject> existingTimetableObjects = timetable.getTimetableObjects();
		            	existingTimetableObjects.add(timetableObject);
		            	timetable.setTimetableObjects(existingTimetableObjects);
			        	
			         
			            // Create timetable event rooms
		            	if (rooms != null && !rooms.isEmpty()) {
		            		for (Room room : rooms) {
				            	TimetableEventRoom timetableEventRoom = new TimetableEventRoom();
				            	timetableEventRoom.setRoom(room);
				            	timetableEventRoom.setTimetableEventTime(timetableEventTime);
				            	List<TimetableEventRoom> timetableEventRooms = timetableEventTime.getTimetableEventRooms();
				            	timetableEventRooms.add(timetableEventRoom);
				            	timetableEventTime.setTimetableEventRooms(timetableEventRooms);
				            }
		            	}
			            
			            // Create timetable event teacher
			            TimetableEventTeacher timetableEventTeacher = new TimetableEventTeacher();
			            timetableEventTeacher.setTeacher(teacher);
			            timetableEventTeacher.setTimetableEventTime(timetableEventTime);
			            List<TimetableEventTeacher> timetableEventTeachers =  timetableEventTime.getTimetableEventTeachers();
			            timetableEventTeachers.add(timetableEventTeacher);
			            timetableEventTime.setTimetableEventTeachers(timetableEventTeachers);
			            
			            // Find if this timetable object already has the student groups attached to it
			            List<Long> studentGroupIds = studentGroups.stream().map(p->p.getId()).collect(Collectors.toList());
			            List<TimetableObjectStudentGroup> timetableObjectStudentGroups = new ArrayList<>();
			            if (timetableObject.getId() != null && !studentGroupIds.isEmpty()) {
			            	timetableObjectStudentGroups = em.createQuery("select tosg from TimetableObjectStudentGroup tosg "
					        		+ "where tosg.studentGroup.id in (?1) "
					        		+ "and tosg.timetableObject.id = ?2", TimetableObjectStudentGroup.class)
					        		.setParameter(1, studentGroupIds)
					        		.setParameter(2, timetableObject.getId())
					        		.getResultList();
			            }
			            
			            List<Long> existingStudentGroupIds = timetableObjectStudentGroups.stream().map(p->p.getId()).collect(Collectors.toList());
			            // Make new timetable object student groups if needed
			            for (StudentGroup studentGroup : studentGroups) {
			            	if (!existingStudentGroupIds.contains(studentGroup.getId()) && 
			            			timetableObject.getTimetableObjectStudentGroups().stream().noneMatch(p->p.getStudentGroup().getId() == studentGroup.getId())) {
			            		TimetableObjectStudentGroup timetableObjectStudentGroup = new TimetableObjectStudentGroup();
			            		timetableObjectStudentGroup.setStudentGroup(studentGroup);
			            		timetableObjectStudentGroup.setTimetableObject(timetableObject);
			            		List<TimetableObjectStudentGroup> timetableobjectSG = timetableObject.getTimetableObjectStudentGroups();
			            		timetableobjectSG.add(timetableObjectStudentGroup);
			            		timetableObject.setTimetableObjectStudentGroups(timetableobjectSG);
			            	}
		            	}
			        }
				}
				List<TimetableObject> timetableObjects = timetable.getTimetableObjects();
				Boolean gotEvent = false;
				for (TimetableObject timetableObj : timetableObjects) {
					if (!timetableObj.getTimetableEvents().isEmpty() && timetableObj.getTimetableEvents().stream().anyMatch(p->p.getIsImported())) {
						gotEvent = true;
						break;
					}
				}
				if (gotEvent) {
					EntityUtil.save(timetable, em);
				}
			}
		}
		return returnValue;
	}
	
	private org.w3c.dom.Document byteArrayToDocument(byte[] bytes) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setNamespaceAware(true);
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    return builder.parse(new ByteArrayInputStream(bytes), "document");
	}
}
