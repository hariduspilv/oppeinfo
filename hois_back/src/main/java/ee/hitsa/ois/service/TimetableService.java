package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

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
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import ee.hitsa.ois.exception.AssertionFailedException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodSubgroup;
import ee.hitsa.ois.domain.timetable.Timetable;
import ee.hitsa.ois.domain.timetable.TimetableEvent;
import ee.hitsa.ois.domain.timetable.TimetableEventRoom;
import ee.hitsa.ois.domain.timetable.TimetableEventSubgroup;
import ee.hitsa.ois.domain.timetable.TimetableEventTeacher;
import ee.hitsa.ois.domain.timetable.TimetableEventTime;
import ee.hitsa.ois.domain.timetable.TimetableObject;
import ee.hitsa.ois.domain.timetable.TimetableObjectStudentGroup;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.enums.MessageType;
import ee.hitsa.ois.enums.Role;
import ee.hitsa.ois.enums.SchoolTimetableType;
import ee.hitsa.ois.enums.TimetableEventRepeat;
import ee.hitsa.ois.enums.TimetableStatus;
import ee.hitsa.ois.enums.TimetableType;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.message.TimetableChanged;
import ee.hitsa.ois.repository.TimetableObjectRepository;
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.CryptoUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.BuildingAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.SchoolCapacityTypeCommand;
import ee.hitsa.ois.web.commandobject.TimetableCopyForm;
import ee.hitsa.ois.web.commandobject.TimetableRoomAndTimeForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEditForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventHigherForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventVocationalForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableManagementSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.RoomAutocompleteResult;
import ee.hitsa.ois.web.dto.RoomDto;
import ee.hitsa.ois.web.dto.StudyYearSearchDto;
import ee.hitsa.ois.web.dto.timetable.DateRangeDto;
import ee.hitsa.ois.web.dto.timetable.GroupTimetableDto;
import ee.hitsa.ois.web.dto.timetable.HigherTimetablePlanDto;
import ee.hitsa.ois.web.dto.timetable.HigherTimetableStudentGroupCapacityDto;
import ee.hitsa.ois.web.dto.timetable.HigherTimetableStudentGroupDto;
import ee.hitsa.ois.web.dto.timetable.HigherTimetableSubgroupDto;
import ee.hitsa.ois.web.dto.timetable.LessonTimeDto;
import ee.hitsa.ois.web.dto.timetable.NameAndCode;
import ee.hitsa.ois.web.dto.timetable.RoomTimetableDto;
import ee.hitsa.ois.web.dto.timetable.SubjectTeacherPairDto;
import ee.hitsa.ois.web.dto.timetable.TeacherTimetableDto;
import ee.hitsa.ois.web.dto.timetable.TimetableCurriculumDto;
import ee.hitsa.ois.web.dto.timetable.TimetableDatesDto;
import ee.hitsa.ois.web.dto.timetable.TimetableDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventDto;
import ee.hitsa.ois.web.dto.timetable.TimetableHigherCapacityDto;
import ee.hitsa.ois.web.dto.timetable.TimetableJournalDto;
import ee.hitsa.ois.web.dto.timetable.TimetableJournalTeacherCapacityDto;
import ee.hitsa.ois.web.dto.timetable.TimetableJournalTeacherDto;
import ee.hitsa.ois.web.dto.timetable.TimetableManagementSearchDto;
import ee.hitsa.ois.web.dto.timetable.TimetableStudentGroupCapacityDto;
import ee.hitsa.ois.web.dto.timetable.TimetableStudentGroupDto;
import ee.hitsa.ois.web.dto.timetable.TimetableStudyYearWeekDto;
import ee.hitsa.ois.web.dto.timetable.TimetableSubjectTeacherDto;
import ee.hitsa.ois.web.dto.timetable.UntisCodeError;
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
    // TODO: forbidden school public data
    private static final Long FORBIDDEN_SCHOOL_ID = Long.valueOf(26);

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

    @Value("${hois.frontend.baseUrl}")
    private String frontendBaseUrl;
    private static String encryptionKey;

    @Value("${file.cypher.key}")
    public void setEncryptionKey(String keyFromProps) {
        encryptionKey = keyFromProps;
    }

    private static final List<String> PUBLIC_TIMETABLES = EnumUtil.toNameList(TimetableStatus.TUNNIPLAAN_STAATUS_P);
    private static final List<String> TEACHER_TIMETABLES = EnumUtil
            .toNameList(TimetableStatus.TUNNIPLAAN_STAATUS_P, TimetableStatus.TUNNIPLAAN_STAATUS_K);
    private static final List<String> ALL_TIMETABLES = EnumUtil.toNameList(TimetableStatus.TUNNIPLAAN_STAATUS_P,
            TimetableStatus.TUNNIPLAAN_STAATUS_K, TimetableStatus.TUNNIPLAAN_STAATUS_A,
            TimetableStatus.TUNNIPLAAN_STAATUS_S);
    private DateTimeFormatter documentDateFormatHois = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private DateTimeFormatter documentDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
    private DateTimeFormatter documentTimeFormat = DateTimeFormatter.ofPattern("HHmmss");
    public static final DateTimeFormatter DOCUMENT_TIME_FORMAT_SHORT = DateTimeFormatter.ofPattern("HHmm");
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

        Long studyPeriodId = EntityUtil.getId(timetable.getStudyPeriod());
        List<Long> studentGroupIds = StreamUtil.toMappedList(r -> (r.getId()), dto.getStudentGroups());
        List<Long> subjectStudyPeriodIds = StreamUtil.toMappedList(r -> (r.getId()), dto.getSubjectTeacherPairs());
        dto.setStudentGroupCapacities(getCapacitiesForHigherPlanning(studyPeriodId, studentGroupIds,
                subjectStudyPeriodIds));
        dto.setSubjectStudyPeriodSubgroups(getSubjectStudyPeriodSubgroups(studyPeriodId, studentGroupIds,
                subjectStudyPeriodIds));

        dto.setLessonTimes(getLessonTimesForPlanning(timetable));
        dto.setStudentGroups(getPlannedLessonsForHigherTimetable(timetable, dto.getStudentGroups()));
        dto.setSubjectTeacherPairs(getPlannedLessonsForHigherTimetableSsp(timetable, dto.getSubjectTeacherPairs()));
        dto.setWeeks(getTimetableWeekRanges(timetable));
        dto.setBuildings(autocompleteService.buildings(EntityUtil.getId(timetable.getSchool()),
                new BuildingAutocompleteCommand()));
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

        String select = "tet.id as timetable_event_id, tet.start, tet.end, te.capacity_type_code,"
                + " s.code as subject_code, s.name_et, s.name_en, too.subject_study_period_id, te.repeat_code";
        List<?> data = qb.select(select, em).getResultList();

        // first get timetable events into a list and add rooms then group by subject study periods
        List<TimetableEventDto> timetableEventTimes = StreamUtil
                .toMappedList(r -> new TimetableEventDto(resultAsLong(r, 0), resultAsLocalDateTime(r, 1),
                        resultAsLocalDateTime(r, 2), resultAsString(r, 3), resultAsString(r, 4),
                        resultAsString(r, 5), resultAsString(r, 6), resultAsLong(r, 7), null,
                        resultAsString(r, 8)), data);
        if (!timetableEventTimes.isEmpty()) {
            timetableEventTimes = addRoomsListToEvents(timetableEventTimes);
            timetableEventTimes = addTeachersListToEvents(timetableEventTimes);
            if (Boolean.TRUE.equals(timetable.getIsHigher())) {
                timetableEventTimes = addSubgroupsListToEvents(timetableEventTimes);
            }
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

        String select = "tet.id as timetable_event_id, tet.start, tet.end, te.capacity_type_code, s.code as subject_code,"
                + " s.name_et, s.name_en, ssp.id as subject_study_period_id, sg.id as student_group_id, te.repeat_code";
        List<?> data = qb.select(select, em).getResultList();
        
        //first get timetable events into a list and add rooms then group by student groups
        List<TimetableEventDto> timetableEventTimes = StreamUtil.toMappedList(
                r -> new TimetableEventDto(resultAsLong(r, 0), resultAsLocalDateTime(r, 1),
                        resultAsLocalDateTime(r, 2), resultAsString(r, 3), resultAsString(r, 4), resultAsString(r, 5),
                        resultAsString(r, 6), resultAsLong(r, 7), resultAsLong(r, 8), resultAsString(r, 9)),
                data);
        if (!timetableEventTimes.isEmpty()) {
            timetableEventTimes = addRoomsListToEvents(timetableEventTimes);
            timetableEventTimes = addTeachersListToEvents(timetableEventTimes);
            if (Boolean.TRUE.equals(timetable.getIsHigher())) {
                timetableEventTimes = addTimetableObjectStudentGroupsListToEvents(timetableEventTimes);
                timetableEventTimes = addSubgroupsListToEvents(timetableEventTimes);
            }
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
        data.put("tType", EntityUtil.getNullableCode(em.getReference(School.class, schoolId).getTimetable()));
        return data;
    }

    public Timetable saveVocationalEvent(TimetableEventVocationalForm form) {
        Timetable timetable = em.getReference(Timetable.class, form.getTimetable());
        List<TimetableStudentGroupDto> studentGroups = getStudentGroups(timetable, form.getJournal());
        Journal journal = em.getReference(Journal.class, form.getJournal());
        TimetableObject timetableObject = saveVocationalTimetableObject(timetable, journal, studentGroups);
        LessonTime lessonTime = em.getReference(LessonTime.class, form.getLessonTime());
        TimetableEvent timetableEvent = saveVocationalTimetableEvent(timetableObject, lessonTime, form);
        TimetableEventTime timetableEventTime = saveVocationalTimetableEventTime(timetableEvent, journal);
        sendTimetableChangesMessages(timetableObject, timetableEventTime.getStart(), timetableEventTime.getEnd(), null);
        return EntityUtil.save(timetable, em);
    }

    private List<TimetableEventDto> addTeachersListToEvents(List<TimetableEventDto> timetableEvents) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event_teacher tett"
                + " join timetable_event_time tet on tet.id = tett.timetable_event_time_id"
                + " join teacher t on t.id = tett.teacher_id"
                + " join person p on p.id = t.person_id");

        qb.requiredCriteria("tet.id in (:timetableEventIds)", "timetableEventIds",
                timetableEvents.stream().map(r -> r.getId()).collect(Collectors.toSet()));

        qb.sort("p.lastname, p.firstname");
        List<?> data = qb.select("tet.id, t.id as teacher_id, p.firstname, p.lastname", em).getResultList();

        Map<Long, Map<Long, String>> teachersByTimetableEventTimes = data.stream().collect(Collectors.
                groupingBy(r -> resultAsLong(r, 0), Collectors.toMap(r -> resultAsLong(r, 1),
                        r -> PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3)),
                        (v1, v2) -> v1, LinkedHashMap::new)));

        for (TimetableEventDto dto : timetableEvents) {
            Map<Long, String> teachers = teachersByTimetableEventTimes.get(dto.getId());
            if(teachers != null) {
                dto.setTeachers(new ArrayList<>(teachers.keySet()));
                dto.setTeacherNames(new ArrayList<>(teachers.values()));
            }
        }

        return timetableEvents;
    }

    private List<TimetableEventDto> addTimetableObjectStudentGroupsListToEvents(List<TimetableEventDto> timetableEvents) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event_time tet"
                + " join timetable_event te on te.id = tet.timetable_event_id"
                + " join timetable_object too on too.id = te.timetable_object_id"
                + " join timetable_object_student_group tosg on tosg.timetable_object_id = too.id"
                + " join student_group sg on sg.id = tosg.student_group_id");
        qb.requiredCriteria("tet.id in (:timetableEventIds)", "timetableEventIds",
                timetableEvents.stream().map(r -> r.getId()).collect(Collectors.toSet()));

        qb.sort("sg.code");
        List<?> data = qb.select("tet.id, sg.id sg_id", em).getResultList();

        Map<Long, List<Long>> studentGroupsByTimetableEventTimes = data.stream().collect(Collectors.
                groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> resultAsLong(r, 1), Collectors.toList())));

        for (TimetableEventDto dto : timetableEvents) {
            List<Long> studentGroups = studentGroupsByTimetableEventTimes.get(dto.getId());
            if (studentGroups != null) {
                dto.setObjectStudentGroups(studentGroups);
            }
        }

        return timetableEvents;
    }

    private List<TimetableEventDto> addSubgroupsListToEvents(List<TimetableEventDto> timetableEvents) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event_subgroup tes"
                + " join timetable_event_time tet on tet.id = tes.timetable_event_time_id"
                + " join subject_study_period_subgroup sspg on sspg.id = tes.subject_study_period_subgroup_id");
        qb.requiredCriteria("tet.id in (:timetableEventIds)", "timetableEventIds",
                timetableEvents.stream().map(r -> r.getId()).collect(Collectors.toSet()));

        qb.sort("sspg.code");
        List<?> data = qb.select("tet.id, tes.subject_study_period_subgroup_id, sspg.code", em).getResultList();

        Map<Long, Map<Long, String>> subgroupsByTimetableEventTimes = data.stream().collect(Collectors.
                groupingBy(r -> resultAsLong(r, 0), Collectors.toMap(r -> resultAsLong(r, 1),
                        r -> resultAsString(r, 2), (v1, v2) -> v1, LinkedHashMap::new)));

        for (TimetableEventDto dto : timetableEvents) {
            Map<Long, String> subgroups = subgroupsByTimetableEventTimes.get(dto.getId());
            if (subgroups != null) {
                dto.setSubgroups(new ArrayList<>(subgroups.keySet()));
                dto.setSubgroupCodes(new ArrayList<>(subgroups.values()));
            }
        }

        return timetableEvents;
    }

    private Map<String, List<TimetableSubjectTeacherDto>> getTeachersForSubjectStudyPeriods(
            Set<Long> subjectStudyPeriods) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period ssp"
                + " join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id"
                + " join teacher t on t.id = sspt.teacher_id"
                + " join person p on p.id = t.person_id");

        qb.requiredCriteria("ssp.id in (:sspIds)", "sspIds", subjectStudyPeriods);

        List<?> data = qb.select("ssp.id, p.firstname, p.lastname, t.id as teacher_id, sspt.id sspt_id", em)
                .getResultList();
        Map<Long, TimetableSubjectTeacherDto> sspTeachers = StreamUtil.toMap(r -> resultAsLong(r, 4), r -> {
            String fullname = PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2));
            return new TimetableSubjectTeacherDto(resultAsLong(r, 3), fullname, fullname, resultAsLong(r, 0),
                    resultAsLong(r, 4));
        }, data);

        return getTeachersByCapacities(sspTeachers);
    }

    private Map<String, List<TimetableSubjectTeacherDto>> getTeachersByCapacities(
            Map<Long, TimetableSubjectTeacherDto> sspTeachers) {
        Map<String, List<TimetableSubjectTeacherDto>> sspTeachersByCt = new HashMap<>();
        if (!sspTeachers.isEmpty()) {
            List<?> data = em.createNativeQuery("select sspt.id, sspc.capacity_type_code,"
                    + " case when ssp.is_capacity_diff then ssptc.hours else sspc.hours end,"
                    + " (select coalesce(sum(case when te.lessons is not null then te.lessons else 1 end), 0) from timetable_event_time tet"
                    + " join timetable_event_teacher tete on tete.timetable_event_time_id = tet.id and tete.teacher_id = sspt.teacher_id"
                    + " join timetable_event te on te.id = tet.timetable_event_id"
                    + " join timetable_object too on too.id = te.timetable_object_id"
                    + " where too.subject_study_period_id = ssp.id and te.capacity_type_code = sspc.capacity_type_code)"
                    + " from subject_study_period_teacher sspt"
                    + " join subject_study_period ssp on ssp.id = sspt.subject_study_period_id"
                    + " join subject_study_period_capacity sspc on sspc.subject_study_period_id = ssp.id"
                    + " left join subject_study_period_teacher_capacity ssptc on ssptc.subject_study_period_teacher_id = sspt.id"
                    + " and ssptc.subject_study_period_capacity_id = sspc.id"
                    + " where sspt.id in (?1)")
                    .setParameter(1, sspTeachers.keySet())
                    .getResultList();

            Map<Long, Map<String, PeriodLessons>> capacities = StreamUtil.nullSafeList(data).stream()
                    .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.toMap(r -> resultAsString(r, 1),
                            r -> new PeriodLessons(resultAsLong(r, 2), null, resultAsLong(r, 3)))));

            for (Long teacher : capacities.keySet()) {
                TimetableSubjectTeacherDto teacherDto = sspTeachers.get(teacher);
                Map<String, PeriodLessons> teacherCapacities = capacities.get(teacher);

                for (String capacity : teacherCapacities.keySet()) {
                    TimetableSubjectTeacherDto teacherCtDto = new TimetableSubjectTeacherDto(teacherDto);
                    PeriodLessons periodLessons = teacherCapacities.get(capacity);
                    teacherCtDto.setCapacity(new TimetableHigherCapacityDto(capacity,
                            periodLessons.getTotalPlannedLessons(), periodLessons.getTotalAllocatedLessons()));

                    String key = teacherDto.getSubjectStudyPeriod().toString() + "_" + capacity;
                    if (sspTeachersByCt.containsKey(key)) {
                        sspTeachersByCt.get(key).add(teacherCtDto);
                    } else {
                        List<TimetableSubjectTeacherDto> teachers = new ArrayList<>();
                        teachers.add(teacherCtDto);
                        sspTeachersByCt.put(key, teachers);
                    }
                }
            }
        }
        return sspTeachersByCt;
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

        TimetableEvent timetableEvent;
        TimetableObject timetableObject;
        LocalDateTime eventStart, eventEnd;
        if (form.getOldEventId() != null) {
            TimetableEventTime timetableEventTime = em.getReference(TimetableEventTime.class, form.getOldEventId());
            timetableEvent = timetableEventTime.getTimetableEvent();
            timetableObject = timetableEvent.getTimetableObject();

            eventStart = form.getStartTime().plus(Duration.between(timetableEventTime.getStart(), timetableEventTime.getEnd()));
            eventEnd = form.getStartTime();
            timetableEventTime.setEnd(eventStart);
            timetableEventTime.setStart(eventEnd);
        } else {
            List<Long> studentGroups;
            if (Boolean.TRUE.equals(form.isForAllGroups())) {
                studentGroups = getAllStudentGroupsForSubjectStudyPeriod(form.getSubjectStudyPeriod());
            } else {
                studentGroups = Arrays.asList(form.getStudentGroupId());
            }

            timetableObject = getTimetableObjectForHigher(form, timetable, studentGroups);
            timetableEvent = addTimetableEvent(timetableObject);
            saveHigherTimetableEvent(timetableEvent, timetableObject, form);
            saveHigherTimetableEventTimes(timetable, timetableEvent, form);
            eventStart = timetableEvent.getStart();
            eventEnd = timetableEvent.getEnd();
        }
        sendTimetableChangesMessages(timetableObject, eventStart, eventEnd, timetableObject.getTimetableObjectStudentGroups());
        return EntityUtil.save(timetable, em);
    }

    private List<Long> getAllStudentGroupsForSubjectStudyPeriod(Long subjectStudyPeriodId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period ssp"
                + " join subject_study_period_student_group sspsg on sspsg.subject_study_period_id = ssp.id");
        qb.requiredCriteria("ssp.id = :subjectStudyPeriodId", "subjectStudyPeriodId", subjectStudyPeriodId);
        List<?> data = qb.select("distinct sspsg.student_group_id", em).getResultList();
        return StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
    }

    private TimetableEvent saveHigherTimetableEvent(TimetableEvent timetableEvent, TimetableObject object,
            TimetableEventHigherForm form) {
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

    private void addSubgroupsToTimetableEvent(TimetableEventTime timetableEventTime,
            Set<SubjectStudyPeriodSubgroup> subgroups) {
        for(SubjectStudyPeriodSubgroup subgroup : subgroups) {
            TimetableEventSubgroup timetableEventSubgroup = new TimetableEventSubgroup();
            timetableEventSubgroup.setSubjectStudyPeriodSubgroup(em.getReference(SubjectStudyPeriodSubgroup.class,
                    EntityUtil.getId(subgroup)));
            timetableEventSubgroup.setTimetableEventTime(timetableEventTime);
            timetableEventTime.getTimetableEventSubgroups().add(timetableEventSubgroup);
        }
    }

    private void saveHigherTimetableEventTimes(Timetable timetable, TimetableEvent timetableEvent, TimetableEventHigherForm form) {
        List<TimetableEventTime> timetableEventTimes = timetableEvent.getTimetableEventTimes();
        SubjectStudyPeriod timetableEventSsp = timetableEvent.getTimetableObject().getSubjectStudyPeriod();
        TimetableEventTime oldEventTime = form.getOldEventId() != null
                ? em.getReference(TimetableEventTime.class, form.getOldEventId()) : null;

        List<Teacher> teachers = oldEventTime != null
                ? StreamUtil.toMappedList(it -> it.getTeacher(), oldEventTime.getTimetableEventTeachers())
                : StreamUtil.toMappedList(it -> it.getTeacher(), timetableEventSsp.getTeachers());
        Set<SubjectStudyPeriodSubgroup> subgroups = oldEventTime != null
                ? StreamUtil.toMappedSet(it -> it.getSubjectStudyPeriodSubgroup(), oldEventTime.getTimetableEventSubgroups())
                : timetableEventSsp.getSubgroups();
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
        addSubgroupsToTimetableEvent(timetableEventTime, subgroups);
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
        LocalDateTime currentStart = timetableEvent.getStart().plusDays(daysToAdd);
        LocalDateTime currentEnd = timetableEvent.getEnd().plusDays(daysToAdd);
        while (!timetable.getEndDate().isBefore(currentStart.toLocalDate())) {
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
            addSubgroupsToTimetableEvent(currentTimetableEventTime, subgroups);
            timetableEventTimes.add(currentTimetableEventTime);
            currentStart = currentStart.plusDays(daysToAdd);
            currentEnd = currentEnd.plusDays(daysToAdd);
        }

    }

    private TimetableObject getTimetableObjectForHigher(TimetableEventHigherForm form, Timetable timetable,
            List<Long> studentGroupIds) {
        TimetableObject timetableObject = new TimetableObject();
        timetableObject.setSubjectStudyPeriod(em.getReference(SubjectStudyPeriod.class, form.getSubjectStudyPeriod()));
        if (Boolean.FALSE.equals(form.isSubjectTeacherPair())) {
            for (Long studentGroupId : studentGroupIds) {
                timetableObject.getTimetableObjectStudentGroups().add(createTimetableObjectStudentGroupForHigher(
                        timetableObject, studentGroupId));
            }
        }
        timetableObject.setTimetable(timetable);
        List<TimetableObject> objects = timetable.getTimetableObjects();
        objects.add(timetableObject);
        return timetableObject;
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
        TimetableEvent timetableEvent;
        if (form.getOldEventId() != null) {
            TimetableEventTime timetableEventTime = em.getReference(TimetableEventTime.class, form.getOldEventId());
            timetableEvent = timetableEventTime.getTimetableEvent();
        } else {
            timetableEvent = addTimetableEvent(timetableObject);
        }
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

    private static TimetableEvent addTimetableEvent(TimetableObject timetableObject) {
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
            if (Boolean.TRUE.equals(form.getChangeUpcomingEvents())) {
                // events that are before the event time in the form should not be changed
                List<TimetableEventTime> eventsToBeChanged = timetableEvent.getTimetableEventTimes().stream()
                        .filter(t -> !t.getStart().toLocalDate().isBefore(timetableEventTime.getStart().toLocalDate()))
                        .collect(Collectors.toList());
                for (TimetableEventTime currentTime : eventsToBeChanged) {
                    if (saveTimetableEventTime(form, timetableEventTime, currentTime)) {
                        modified = true;
                    }
                }
            } else {
                if (saveTimetableEventTime(form, timetableEventTime, timetableEventTime)) {
                    modified = true;
                }
            }

            TimetableObject timetableObject = timetableEvent.getTimetableObject();
            Timetable timetable = timetableObject.getTimetable();
            List<TimetableObjectStudentGroup> messageStudentGroups = new ArrayList<>();
            if (Boolean.TRUE.equals(timetable.getIsHigher()) && timetableObject.getSubjectStudyPeriod()
                    .getSubgroups().isEmpty()) {
                if (form.getStudentGroups() == null || form.getStudentGroups().isEmpty()) {
                    throw new AssertionFailedException("If there is no connected subgroup then one studengroup is required");
                }
                List<TimetableObjectStudentGroup> oldStudentGroups = timetableObject.getTimetableObjectStudentGroups();
                messageStudentGroups.addAll(oldStudentGroups);
                if (EntityUtil.bindEntityCollection(oldStudentGroups, r -> EntityUtil.getId(r.getStudentGroup()),
                        form.getStudentGroups(), r -> r, id -> {
                            TimetableObjectStudentGroup studentGroup = new TimetableObjectStudentGroup();
                            studentGroup.setStudentGroup(em.getReference(StudentGroup.class, id));
                            studentGroup.setTimetableObject(timetableObject);
                            return studentGroup;
                        })) {
                    messageStudentGroups.addAll(StreamUtil.toFilteredList(sg -> !messageStudentGroups.contains(sg),
                            oldStudentGroups));
                    modified = true;
                }
            }

            timetable = EntityUtil.save(timetable, em);
            if(modified) {
                sendTimetableChangesMessages(timetableEvent.getTimetableObject(), timetableEventTime.getStart(),
                        timetableEventTime.getEnd(), messageStudentGroups);
            }
            return timetable;
        }
        return null;
    }

    private boolean saveTimetableEventTime(TimetableRoomAndTimeForm form, TimetableEventTime timetableEventTime,
            TimetableEventTime currentTime) {
        boolean modified = false;
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
        List<TimetableEventSubgroup> oldSubgroups = currentTime.getTimetableEventSubgroups();
        if(EntityUtil.bindEntityCollection(oldSubgroups, r -> EntityUtil.getId(r.getSubjectStudyPeriodSubgroup()),
                form.getSubgroups(), r -> r, id -> {
                    TimetableEventSubgroup subgroup = new TimetableEventSubgroup();
                    subgroup.setSubjectStudyPeriodSubgroup(em.getReference(SubjectStudyPeriodSubgroup.class, id));
                    subgroup.setTimetableEventTime(timetableEventTime);
                    return subgroup;
                })) {
            modified = true;
        }
        currentTime.setStart(currentTime.getStart().withHour(form.getStartTime().getHour())
                .withMinute(form.getStartTime().getMinute()));
        currentTime.setEnd(currentTime.getEnd().withHour(form.getEndTime().getHour())
                .withMinute(form.getEndTime().getMinute()));
        return modified;
    }

    public Timetable deleteEvent(HoisUserDetails user, TimetableRoomAndTimeForm form) {
        TimetableEventTime timetableEventTime = em.getReference(TimetableEventTime.class, form.getTimetableEventId());
        TimetableEvent timetableEvent = timetableEventTime.getTimetableEvent();
        TimetableObject timetableObject = timetableEvent.getTimetableObject();
        Timetable timetable = timetableObject.getTimetable();

        EntityUtil.setUsername(user.getUsername(), em);
        if (Boolean.TRUE.equals(timetable.getIsHigher())) {
            if (Boolean.TRUE.equals(form.getChangeUpcomingEvents())) {
                // events that are before the event time in the form should not be deleted
                List<TimetableEventTime> eventsToBeDeleted = timetableEvent.getTimetableEventTimes().stream()
                        .filter(t -> !t.getStart().toLocalDate().isBefore(timetableEventTime.getStart().toLocalDate()))
                        .collect(Collectors.toList());
                for (TimetableEventTime currentTime : eventsToBeDeleted) {
                    timetableEvent.getTimetableEventTimes().remove(currentTime);
                    EntityUtil.deleteEntity(currentTime, em);
                }
            } else {
                EntityUtil.deleteEntity(timetableEventTime, em);
            }
            if (timetableEvent.getTimetableEventTimes().isEmpty()) {
                EntityUtil.deleteEntity(timetableEvent, em);
            }
        } else {
            EntityUtil.deleteEntity(timetableEvent, em);
        }

        sendTimetableChangesMessages(timetableObject, timetableEventTime.getStart(), timetableEventTime.getEnd(),
                timetableObject.getTimetableObjectStudentGroups());
        if (timetableObject.getTimetableEvents().isEmpty()) {
            EntityUtil.deleteEntity(timetableObject, em);
        }
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
        School school = em.getReference(School.class, user.getSchoolId());
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable t").sort(pageable);

        qb.requiredCriteria("t.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("t.is_higher = :isHigher", "isHigher",
                Boolean.valueOf(TimetableType.isHigher(criteria.getType())));

        String select = "t.id, t.status_code, t.start_date, t.end_date, t.is_higher, t.study_period_id";
        Boolean canExportOrImportTimetable = Boolean.valueOf(ClassifierUtil.oneOf(school.getTimetable(), SchoolTimetableType.TIMETABLE_UNTIS, SchoolTimetableType.TIMETABLE_ASC));
        return JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new TimetableManagementSearchDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsLocalDate(r, 2),
                    resultAsLocalDate(r, 3), resultAsBoolean(r, 4), resultAsLong(r, 5), canExportOrImportTimetable, canExportOrImportTimetable);
        });
    }

    private Page<TimetableManagementSearchDto> searchVocationalTimetableForManagement(
            TimetableManagementSearchCommand criteria, Pageable pageable, HoisUserDetails user) {
        School school = em.getReference(School.class, user.getSchoolId());
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable t");

        qb.requiredCriteria("t.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("t.is_higher = :isHigher", "isHigher",
                Boolean.valueOf(TimetableType.isHigher(criteria.getType())));

        String select = "t.id, t.status_code, t.start_date, t.end_date, t.is_higher";
        List<?> data = qb.select(select, em).getResultList();
        StudyPeriod sp = em.getReference(StudyPeriod.class, criteria.getStudyPeriod());

        Boolean canExportOrImportTimetable = Boolean.valueOf(ClassifierUtil.oneOf(school.getTimetable(), SchoolTimetableType.TIMETABLE_UNTIS, SchoolTimetableType.TIMETABLE_ASC));
        List<TimetableManagementSearchDto> wrappedData = StreamUtil.toMappedList(r -> {
            Long id = resultAsLong(r, 0);
            String status = resultAsString(r, 1);
            LocalDate start = resultAsLocalDate(r, 2);
            LocalDate end = resultAsLocalDate(r, 3);
            Boolean isHigher = resultAsBoolean(r, 4);
            return new TimetableManagementSearchDto(id, status, start, end, isHigher, EntityUtil.getId(sp), canExportOrImportTimetable, canExportOrImportTimetable);
        }, data);
        wrappedData = addMissingDatesToBlocked(sp, wrappedData, canExportOrImportTimetable);

        String pageableSort = pageable.getSort() != null ? pageable.getSort().toString() : null;
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
        return addMissingDatesToBlocked(sp, data, Boolean.FALSE);
    }
    
    private static List<TimetableManagementSearchDto> addMissingDatesToBlocked(StudyPeriod sp,
            List<TimetableManagementSearchDto> data, Boolean canExportOrImportTimetable) {
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
                        new TimetableManagementSearchDto(null, TimetableStatus.TUNNIPLAAN_STAATUS_A.name(),
                                currentStart, currentEnd, Boolean.FALSE, EntityUtil.getId(sp),
                                canExportOrImportTimetable, canExportOrImportTimetable));
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
                        new TimetableManagementSearchDto(null, TimetableStatus.TUNNIPLAAN_STAATUS_A.name(),
                                currentStart, currentEnd, Boolean.FALSE, EntityUtil.getId(sp),
                                canExportOrImportTimetable, canExportOrImportTimetable));
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

    private List<HigherTimetableStudentGroupCapacityDto> getCapacitiesForHigherPlanning(Long studyPeriodId,
            List<Long> studentGroupIds, List<Long> subjectStudyPeriodIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period_capacity sspc "
                + " join subject_study_period ssp on ssp.id = sspc.subject_study_period_id "
                + " left join subject_study_period_student_group sspsg on sspsg.subject_study_period_id = ssp.id"
                + " left join student_group sg on sg.id = sspsg.student_group_id"
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

        qb.sort("sg.code");
        String select = "distinct sg.id as student_group_id, sg.code sg_code, sspc.capacity_type_code,"
                + " sspc.hours, s.code, s.name_et, s.name_en, ssp.id as subject_study_period_id";
        List<?> data = qb.select(select, em).getResultList();
        List<HigherTimetableStudentGroupCapacityDto> capacities = StreamUtil
                .toMappedList(
                        r -> new HigherTimetableStudentGroupCapacityDto(resultAsLong(r, 0), resultAsString(r, 1),
                                resultAsString(r, 2), resultAsLong(r, 3), resultAsString(r, 4), resultAsString(r, 5),
                                resultAsString(r, 6), resultAsLong(r, 7)),
                        data);

        if(StreamUtil.toMappedSet(r -> r.getSubjectStudyPeriod(), capacities).isEmpty()) {
            throw new ValidationFailedException("timetable.error.missingCapacities");
        }
        // key = subject_study_period.id + _ + capacity_type
        Map<String, List<TimetableSubjectTeacherDto>> teachersBySspCt = getTeachersForSubjectStudyPeriods(
                StreamUtil.toMappedSet(r -> r.getSubjectStudyPeriod(), capacities));
        // key = subject_study_period.id + _ + student_group.id + _ + capacity_type
        Map<String, Long> plannedLessonsBySspSgCt = getPlannedTotalsForHigherStudentGroups(studyPeriodId);

        for (HigherTimetableStudentGroupCapacityDto dto : capacities) {
            dto.setTeachers(teachersBySspCt.get(dto.getSubjectStudyPeriod() + "_" + dto.getCapacityType()));
            if (dto.getStudentGroup() != null) {
                dto.setTotalAllocatedLessons(plannedLessonsBySspSgCt.get(dto.getSubjectStudyPeriod().toString() + "_"
                        + dto.getStudentGroup().getId() + "_" + dto.getCapacityType()));
            } else {
                dto.setTotalAllocatedLessons(plannedLessonsBySspSgCt
                        .get(dto.getSubjectStudyPeriod().toString() + "__" + dto.getCapacityType()));
            }
        }
        return capacities;
    }

    private Map<String, Long> getPlannedTotalsForHigherStudentGroups(Long studyPeriodId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period ssp"
                + " join timetable_object too on too.subject_study_period_id = ssp.id"
                + " left join timetable_object_student_group tosg on tosg.timetable_object_id = too.id"
                + " left join student_group sg on sg.id = tosg.student_group_id"
                + " left join subject_study_period_subgroup sspg on sspg.subject_study_period_id = ssp.id"
                + " left join subject_study_period_capacity sspc on sspc.subject_study_period_id = ssp.id");
        qb.requiredCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", studyPeriodId);

        qb.groupBy("ssp.id, sg.id, sspg.id, sspc.capacity_type_code");
        String totalsBySubgroup = qb.querySql("ssp.id ssp_id, sg.id sg_id, sspg.id sspg_id, sspc.capacity_type_code,"
                + " (select coalesce(sum(case when te.lessons is not null then te.lessons else 1 end), 0) from timetable_event te"
                + " join timetable_event_time tet on tet.timetable_event_id = te.id"
                + " join timetable_object too2 on too2.id = te.timetable_object_id and too2.subject_study_period_id = ssp.id"
                + " left join timetable_object_student_group tosg2 on tosg2.timetable_object_id = too2.id"
                + " left join timetable_event_subgroup tes on tes.timetable_event_time_id = tet.id"
                + " where te.capacity_type_code = sspc.capacity_type_code and (sg is null or tosg2.student_group_id = sg.id)"
                + " and (sspg.id is null or tes.subject_study_period_subgroup_id = sspg.id)) lessons", false);
        Map<String, Object> parameters = new HashMap<>();
        parameters.putAll(qb.queryParameters());

        qb = new JpaNativeQueryBuilder("from (" + totalsBySubgroup + ") as capacities");
        qb.groupBy("ssp_id, sg_id, capacity_type_code");

        List<?> data = qb.select("ssp_id, sg_id, capacity_type_code, min(lessons)", em, parameters).getResultList();
        // key = subject_study_period.id + _ + student_group.id + _ + capacity_type
        return StreamUtil.toMap(r -> resultAsLong(r, 0).toString() + "_"
                + (resultAsLong(r, 1) != null ? resultAsLong(r, 1).toString() : "") + "_" + resultAsString(r, 2),
                r -> resultAsLong(r, 3), data);
    }

    private Map<Long, List<HigherTimetableSubgroupDto>> getSubjectStudyPeriodSubgroups(Long studyPeriodId,
            List<Long> studentGroupIds, List<Long> subjectStudyPeriodIds) {
        if(studentGroupIds.isEmpty() && subjectStudyPeriodIds.isEmpty()) {
            return new HashMap<>();
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period ssp "
                + " join subject_study_period_subgroup ssps on ssps.subject_study_period_id = ssp.id "
                + " left join subject_study_period_teacher sspt on sspt.id = ssps.subject_study_period_teacher_id"
                + " left join teacher t on t.id = sspt.teacher_id"
                + " left join person p on p.id = t.person_id");
        if (!studentGroupIds.isEmpty() && !subjectStudyPeriodIds.isEmpty()) {
            qb.requiredCriteria("(exists (select 1 from subject_study_period_student_group sspsg"
                    + " where sspsg.student_group_id in (:studentGroupIds)) or ssp.id in (:subjectStudyPeriodIds))",
                    "studentGroupIds", studentGroupIds);
            qb.parameter("subjectStudyPeriodIds", subjectStudyPeriodIds);
        } else {
            qb.optionalCriteria("exists (select 1 from subject_study_period_student_group sspsg"
                    + " where sspsg.student_group_id in (:studentGroupIds))", "studentGroupIds", studentGroupIds);
            qb.optionalCriteria("ssp.id in (:subjectStudyPeriodIds)", "subjectStudyPeriodIds",
                    subjectStudyPeriodIds);
        }
        qb.requiredCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", studyPeriodId);

        qb.sort("ssps.code");
        String select = "ssp.id ssp_id, ssps.id ssps_id, ssps.code, sspt.teacher_id, p.firstname, p.lastname";
        List<?> data = qb.select(select, em).getResultList();

        return StreamUtil.nullSafeList(data).stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> {
                    AutocompleteResult teacher = null;
                    Long teacherId = resultAsLong(r, 3);
                    if (teacherId != null) {
                        String teacherName = PersonUtil.fullname(resultAsString(r, 4), resultAsString(r, 5));
                        teacher = new AutocompleteResult(teacherId, teacherName, teacherName);
                    }
                    return new HigherTimetableSubgroupDto(resultAsLong(r, 1), resultAsString(r, 2), teacher);
                }, Collectors.toList())));
    }

    private List<TimetableStudentGroupCapacityDto> getCapacitiesForVocationalPlanning(List<Long> studentGroupIds,
            Timetable timetable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j"
                + " join journal_capacity_type jct on jct.journal_id = j.id"
                + " join (select distinct ot.lesson_plan_module_id, journal_id from journal_omodule_theme ot) jot on jot.journal_id = j.id"
                + " join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
                + " join lesson_plan lp on lp.id = lpm.lesson_plan_id");

        qb.requiredCriteria("lp.study_year_id = :studyYearId", "studyYearId",
                EntityUtil.getId(timetable.getStudyPeriod().getStudyYear()));
        qb.requiredCriteria("lp.student_group_id in (:studentGroupIds)", "studentGroupIds", studentGroupIds);
        qb.filter("lp.is_usable = true");

        List<?> data = qb.select("lp.student_group_id, j.id as journal_id, jct.capacity_type_code", em).getResultList();
        List<TimetableStudentGroupCapacityDto> result = StreamUtil.toMappedList(
                r -> new TimetableStudentGroupCapacityDto(resultAsLong(r, 0), resultAsLong(r, 1), resultAsString(r, 2)),
                data);

        Map<Long, Map<String, PeriodLessons>> periodLessonsMap = periodLessons(studentGroupIds, timetable);
        Map<Long, Map<String, LeftOverLessons>> leftOverLessonsMap = leftOverLessons(studentGroupIds, timetable);
        Map<Long, Map<String, AllocatedLessons>> allocatedLessonsMap = getAllocatedLessonsForByJournalAndCapacity(
                timetable);

        for (TimetableStudentGroupCapacityDto dto : result) {
            String key = dto.getStudentGroup() + "/" + dto.getCapacityType();

            Map<String, PeriodLessons> groupLessonPlanHours = periodLessonsMap.get(dto.getJournal());
            if (groupLessonPlanHours != null) {
                PeriodLessons capacityLpHours = groupLessonPlanHours.get(key);
                if (capacityLpHours != null) {
                    dto.setTotalPlannedLessons(capacityLpHours.getTotalPlannedLessons());
                    dto.setTotalLessonsLeft(capacityLpHours.getTotalPlannedLessons());
                    dto.setThisPlannedLessons(capacityLpHours.getThisPlannedLessons());
                    dto.setLessonsLeft(capacityLpHours.getThisPlannedLessons());
                }
            }

            Map<String, LeftOverLessons> groupLeftOverLessons = leftOverLessonsMap.get(dto.getJournal());
            if (groupLeftOverLessons != null) {
                LeftOverLessons capacityLeftOvers = groupLeftOverLessons.remove(key);
                if (capacityLeftOvers != null) {
                    long leftOverLessons = capacityLeftOvers.getOutsidePeriodTotalHours().longValue()
                            - capacityLeftOvers.getOutsidePeriodAllocated().longValue();
                    dto.setLeftOverLessons(Long.valueOf(leftOverLessons));
                }
            }

            Map<String, AllocatedLessons> groupAllocatedLessons = allocatedLessonsMap.get(dto.getJournal());
            if (groupAllocatedLessons != null) {
                AllocatedLessons currentLessons = groupAllocatedLessons.get(key);
                if (currentLessons != null) {
                    dto.setTotalAllocatedLessons(currentLessons.getTotalAllocated());
                    long lessonsLeft = dto.getThisPlannedLessons().longValue()
                            - currentLessons.getCurrentWeekAllocated().longValue();
                    dto.setLessonsLeft(Long.valueOf(lessonsLeft));
                    long totalLessonsLeft = dto.getTotalPlannedLessons().longValue()
                            - dto.getTotalAllocatedLessons().longValue();
                    dto.setTotalLessonsLeft(Long.valueOf(totalLessonsLeft));
                } else {
                    long totalLessonsLeft = dto.getTotalPlannedLessons().longValue()
                            - dto.getTotalAllocatedLessons().longValue();
                    dto.setTotalLessonsLeft(Long.valueOf(totalLessonsLeft));
                }
            }
        }
        return result;
    }

    private Map<Long, Map<String, PeriodLessons>> periodLessons(List<Long> studentGroupIds, Timetable timetable) {
        Integer currentWeekNr = timetable.getStudyPeriod().getWeekNrForDate(timetable.getStartDate());
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j"
                + " join journal_capacity jc on jc.journal_id = j.id"
                + " join (select distinct ot.lesson_plan_module_id, journal_id from journal_omodule_theme ot) jot on jot.journal_id = j.id"
                + " join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
                + " join lesson_plan lp on lp.id = lpm.lesson_plan_id"
                + " join journal_capacity_type jct on jct.id = jc.journal_capacity_type_id and jct.journal_id = j.id");

        qb.requiredCriteria("jc.study_period_id = :studyPeriodId", "studyPeriodId",
                EntityUtil.getId(timetable.getStudyPeriod()));
        qb.filter("lp.is_usable = true");
        qb.requiredCriteria("lp.student_group_id in (:studentGroupIds)", "studentGroupIds", studentGroupIds);
        qb.groupBy("j.id, lp.student_group_id, jct.id");

        String subselect = "(select coalesce(sum(jc.hours), 0)"
                + " from journal_capacity jc where jc.journal_id = j.id and jc.journal_capacity_type_id = jct.id and "
                + "jc.week_nr in (" + currentWeekNr + "))";

        String select = "j.id as journal_id, lp.student_group_id, jct.capacity_type_code, "
                + " sum(jc.hours) as total_hours, " + subselect + " as current_week_hours";
        List<?> data = qb.select(select, em).getResultList();

        return !data.isEmpty() ? data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.toMap(r -> resultAsLong(r, 1).toString() + "/" + resultAsString(r, 2),
                        r -> new PeriodLessons(resultAsLong(r, 3), resultAsLong(r, 4), null))))
                : new HashMap<>();
    }

    private Map<Long, Map<String, LeftOverLessons>> leftOverLessons(List<Long> studentGroupIds, Timetable timetable) {
        StudyPeriod sp = timetable.getStudyPeriod();
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j"
                + " join journal_capacity jc on jc.journal_id = j.id"
                + " join (select distinct ot.lesson_plan_module_id, journal_id from journal_omodule_theme ot) jot on jot.journal_id = j.id"
                + " join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
                + " join lesson_plan lp on lp.id = lpm.lesson_plan_id"
                + " join journal_capacity_type jct on jct.id = jc.journal_capacity_type_id"
                + " join study_period sp on sp.id = jc.study_period_id");

        qb.requiredCriteria("lp.study_year_id = :studyYearId", "studyYearId", EntityUtil.getId(sp.getStudyYear()));
        qb.requiredCriteria("sp.end_date < :timetableStudyPeriodStart", "timetableStudyPeriodStart", sp.getStartDate());
        qb.filter("lp.is_usable = true");
        qb.requiredCriteria("lp.student_group_id in (:studentGroupIds)", "studentGroupIds", studentGroupIds);
        qb.groupBy("j.id, lp.student_group_id, jct.capacity_type_code");

        String totalAllocatedSelect = "(select count(*) from timetable_event te "
                + "join timetable_object too on too.id = te.timetable_object_id and too.journal_id is not null "
                + "join timetable_object_student_group tsog on tsog.timetable_object_id = too.id "
                + "join timetable t on t.id = too.timetable_id "
                + "join study_period sp on sp.id = t.study_period_id "
                + "where too.journal_id = j.id and tsog.student_group_id = lp.student_group_id "
                + "and te.capacity_type_code = jct.capacity_type_code";
        String outsidePeriodAllocated = " and sp.end_date < :timetableStudyPeriodStart) as outside_period_allocated";
        String periodAllocated = " and t.study_period_id = :timetableStudyPeriodId) as period_allocated";
        String currentAllocated = " and t.id = :timetableId) as current_allocated";
        qb.parameter("timetableStudyPeriodId", sp.getId());
        qb.parameter("timetableId", EntityUtil.getId(timetable));

        String select = "j.id as journal_id, lp.student_group_id, jct.capacity_type_code, sum(jc.hours) as outside_period_total_hours, "
                + totalAllocatedSelect + outsidePeriodAllocated + "," + totalAllocatedSelect + periodAllocated + ","
                + totalAllocatedSelect + currentAllocated;
        List<?> data = qb.select(select, em).getResultList();

        return !data.isEmpty() ? data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.toMap(r -> resultAsLong(r, 1).toString() + "/" + resultAsString(r, 2),
                        r -> new LeftOverLessons(resultAsLong(r, 3), resultAsLong(r, 4), resultAsLong(r, 5),
                                resultAsLong(r, 6), resultAsLong(r, 1), null, resultAsString(r, 2)))))
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
            List<Long> journalIds =  StreamUtil.toMappedList(TimetableJournalDto::getId, journals);
            Map<Long, List<TimetableJournalTeacherDto>> teachersByJournals = journalTeachers(journalIds, timetable);
            Map<Long, List<RoomAutocompleteResult>> roomsByJournals = journalRooms(journalIds);

            for (TimetableJournalDto journal : journals) {
                List<TimetableJournalTeacherDto> teachers = teachersByJournals.get(journal.getId());
                if (teachers != null) {
                    journal.getTeachers().addAll(teachers);
                }
                List<RoomAutocompleteResult> rooms = roomsByJournals.get(journal.getId());
                if (rooms != null) {
                    journal.getRooms().addAll(rooms);
                }
            }
        }
        return journals;
    }

    private Map<Long, List<TimetableJournalTeacherDto>> journalTeachers(List<Long> journalIds,
            Timetable timetable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal_teacher jt"
                + " join teacher t on t.id = jt.teacher_id"
                + " join person p on p.id = t.person_id");
        qb.requiredCriteria("jt.journal_id in (:journalIds)", "journalIds", journalIds);

        List<?> data = qb.select("jt.journal_id, jt.id journal_teacher_id, t.id as teacher_id,"
                + " p.firstname, p.lastname", em).getResultList();

        Map<Long, TimetableJournalTeacherDto> journalTeachers = StreamUtil.toMap(r -> resultAsLong(r, 1),
                r -> new TimetableJournalTeacherDto(resultAsLong(r, 2),
                        PersonUtil.fullname(resultAsString(r, 3), resultAsString(r, 4)),
                        PersonUtil.fullname(resultAsString(r, 3), resultAsString(r, 4)),
                        resultAsLong(r, 0), resultAsLong(r, 1)),
                data);

        Map<Long, List<TimetableJournalTeacherDto>> teachersByJournals = new HashMap<>();
        if (!journalTeachers.isEmpty()) {
            setJournalTeacherCapacities(journalTeachers, timetable);
            teachersByJournals = journalTeachers.values().stream().collect(
                    Collectors.groupingBy(t -> t.getJournalId(), Collectors.mapping(t -> t, Collectors.toList())));
        }
        return teachersByJournals;
    }

    private void setJournalTeacherCapacities(Map<Long, TimetableJournalTeacherDto> journalTeachers,
            Timetable timetable) {
        Set<Long> journalTeacherIds = journalTeachers.keySet();

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal_teacher jt"
                + " join journal_capacity_type jct on jct.journal_id = jt.journal_id");
        qb.requiredCriteria("jt.id in (:teacherIds)", "teacherIds", journalTeacherIds);

        List<?> data = qb.select("jt.id, jct.capacity_type_code", em).getResultList();
        Map<Long, List<TimetableJournalTeacherCapacityDto>> capacities = data.stream()
            .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(
                r -> new TimetableJournalTeacherCapacityDto(resultAsLong(r, 0), resultAsString(r, 1)),
                Collectors.toList())));

        Map<Long, Map<String, PeriodLessons>> periodLessonsMap = getJournalTeacherPeriodLessons(journalTeacherIds,
                timetable);
        Map<Long, Map<String, LeftOverLessons>> leftOverLessonsMap = journalTeacherLeftOverLessons(journalTeacherIds,
                timetable);
        Map<Long, Map<String, AllocatedLessons>> allocatedLessonsMap = getAllocatedLessonsForJournalTeacher(timetable);

        for (Long journalTeacherId : journalTeachers.keySet()) {
            TimetableJournalTeacherDto dto = journalTeachers.get(journalTeacherId);

            List<TimetableJournalTeacherCapacityDto> teacherCapacities = capacities.containsKey(journalTeacherId)
                    ? capacities.get(journalTeacherId)
                    : new ArrayList<>();

            for (TimetableJournalTeacherCapacityDto teacherCapacity : teacherCapacities) {
                String key = teacherCapacity.getJournalTeacher() + "/" + teacherCapacity.getCapacityType();

                Map<String, PeriodLessons> teacherPeriodLessons = periodLessonsMap.get(journalTeacherId);
                if (teacherPeriodLessons != null) {
                    PeriodLessons capacityLpHours = teacherPeriodLessons.get(key);
                    if (capacityLpHours != null) {
                        teacherCapacity.setTotalPlannedLessons(capacityLpHours.getTotalPlannedLessons());
                        teacherCapacity.setTotalLessonsLeft(capacityLpHours.getTotalPlannedLessons());
                        teacherCapacity.setThisPlannedLessons(capacityLpHours.getThisPlannedLessons());
                        teacherCapacity.setLessonsLeft(capacityLpHours.getThisPlannedLessons());
                    }
                }

                Map<String, LeftOverLessons> teacherLeftOverLessons = leftOverLessonsMap.get(journalTeacherId);
                if (teacherLeftOverLessons != null) {
                    LeftOverLessons capacityLeftOvers = teacherLeftOverLessons.remove(key);
                    if (capacityLeftOvers != null) {
                        long leftOverLessons = capacityLeftOvers.getOutsidePeriodTotalHours().longValue()
                                - capacityLeftOvers.getOutsidePeriodAllocated().longValue();
                        teacherCapacity.setLeftOverLessons(Long.valueOf(leftOverLessons));
                    }
                }

                Map<String, AllocatedLessons> teacherAllocatedLessons = allocatedLessonsMap.get(journalTeacherId);
                if (teacherAllocatedLessons != null) {
                    AllocatedLessons currentLessons = teacherAllocatedLessons.get(key);
                    if (currentLessons != null) {
                        teacherCapacity.setTotalAllocatedLessons(currentLessons.getTotalAllocated());
                        long lessonsLeft = teacherCapacity.getThisPlannedLessons().longValue()
                                - currentLessons.getCurrentWeekAllocated().longValue();
                        teacherCapacity.setLessonsLeft(Long.valueOf(lessonsLeft));
                        long totalLessonsLeft = teacherCapacity.getTotalPlannedLessons().longValue()
                                - teacherCapacity.getTotalAllocatedLessons().longValue();
                        teacherCapacity.setTotalLessonsLeft(Long.valueOf(totalLessonsLeft));
                    } else {
                        long totalLessonsLeft = teacherCapacity.getTotalPlannedLessons().longValue()
                                - teacherCapacity.getTotalAllocatedLessons().longValue();
                        teacherCapacity.setTotalLessonsLeft(Long.valueOf(totalLessonsLeft));
                    }
                }
            }
            dto.setCapacities(teacherCapacities);
        }
    }

    private Map<Long, Map<String, PeriodLessons>> getJournalTeacherPeriodLessons(Set<Long> journalTeacherIds,
            Timetable timetable) {
        StudyPeriod sp = timetable.getStudyPeriod();
        Integer currentWeekNr = sp.getWeekNrForDate(timetable.getStartDate());

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal_teacher jt"
                + " join journal j on jt.journal_id = j.id"
                + " join journal_capacity jc on jc.journal_id = j.id and (j.is_capacity_diff is null or j.is_capacity_diff = false)"
                + " join journal_capacity_type jct on jct.id = jc.journal_capacity_type_id and jct.journal_id = j.id");
        qb.requiredCriteria("jt.id in (:teacherIds)", "teacherIds", journalTeacherIds);
        qb.requiredCriteria("jc.study_period_id = :studyPeriodId", "studyPeriodId", sp.getId());
        qb.groupBy("j.id, jt.id, jct.id");

        String plannedLoads = qb.querySql("jt.id as journal_teacher_id, sum(jc.hours) as total_hours,"
                + " (sum(case when jc.week_nr = " + currentWeekNr + " then jc.hours else 0 end)) as current_week_hours,"
                + " jct.capacity_type_code", false);
        Map<String, Object> parameters = new HashMap<>(qb.queryParameters());

        qb = new JpaNativeQueryBuilder("from journal_teacher jt2"
                + " join journal j2 on jt2.journal_id = j2.id"
                + " join journal_teacher_capacity jtc on jt2.id = jtc.journal_teacher_id and j2.is_capacity_diff = true"
                + " join journal_capacity_type jct2 on jtc.journal_capacity_type_id = jct2.id and jct2.journal_id = j2.id");
        qb.requiredCriteria("jt2.id in (:teacherIds)", "teacherIds", journalTeacherIds);
        qb.requiredCriteria("jtc.study_period_id = :studyPeriodId", "studyPeriodId", sp.getId());
        qb.groupBy("j2.id, jt2.id, jct2.id");
        
        String specificPlannedLoads = qb.querySql("jt2.id as journal_teacher_id, sum(jtc.hours) as total_hours,"
                + " (sum(case when jtc.week_nr = " + currentWeekNr + " then jtc.hours else 0 end)) as current_week_hours,"
                + " jct2.capacity_type_code", false);
        parameters.putAll(qb.queryParameters());

        qb = new JpaNativeQueryBuilder("from (" + plannedLoads + " union all " + specificPlannedLoads + ") as pl");
        List<?> data = qb.select("*", em, parameters).getResultList();

        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
            Collectors.toMap(r -> resultAsLong(r, 0).toString() + "/" + resultAsString(r, 3),
                    r -> new PeriodLessons(resultAsLong(r, 1), resultAsLong(r, 2), null))));
    }

    private Map<Long, Map<String, LeftOverLessons>> journalTeacherLeftOverLessons(Set<Long> journalTeacherIds, Timetable timetable) {
        StudyPeriod sp = timetable.getStudyPeriod();
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j"
                + " join journal_teacher jt on jt.journal_id = j.id"
                + " left join journal_capacity jc on jc.journal_id = j.id and (j.is_capacity_diff is null or j.is_capacity_diff = false)"
                + " left join journal_capacity_type jct on jct.id = jc.journal_capacity_type_id"
                + " left join study_period sp on sp.id = jc.study_period_id"
                + " left join journal_teacher_capacity jtc on jtc.journal_teacher_id = jt.id and j.is_capacity_diff = true"
                + " left join journal_capacity_type jct2 on jct2.id = jtc.journal_capacity_type_id"
                + " left join study_period sp2 on sp2.id = jtc.study_period_id"
                + " join (select distinct ot.lesson_plan_module_id, journal_id from journal_omodule_theme ot) jot on jot.journal_id = j.id"
                + " join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
                + " join lesson_plan lp on lp.id = lpm.lesson_plan_id");

        qb.requiredCriteria("lp.study_year_id = :studyYearId", "studyYearId", EntityUtil.getId(sp.getStudyYear()));
        qb.requiredCriteria("coalesce(sp2.end_date, sp.end_date) < :timetableStudyPeriodStart",
                "timetableStudyPeriodStart", sp.getStartDate());
        qb.requiredCriteria("jt.id in (:journalTeacherIds)", "journalTeacherIds", journalTeacherIds);
        qb.filter("lp.is_usable = true");
        qb.groupBy("jt.id, jct2.capacity_type_code, jct.capacity_type_code");

        String totalAllocatedSelect = "(select count(*) from timetable_event te"
                + " join timetable_object too on too.id = te.timetable_object_id and too.journal_id is not null"
                + " join timetable t on t.id = too.timetable_id"
                + " join timetable_event_time tet on tet.timetable_event_id = te.id"
                + " join timetable_event_teacher tete on tete.timetable_event_time_id = tet.id"
                + " join study_period sp on sp.id = t.study_period_id"
                + " where too.journal_id = jt.journal_id and tete.teacher_id = jt.teacher_id"
                + " and te.capacity_type_code = coalesce(jct2.capacity_type_code, jct.capacity_type_code)";
        String outsidePeriodAllocated = " and sp.end_date < :timetableStudyPeriodStart) as outside_period_allocated";
        String periodAllocated = " and t.study_period_id = :timetableStudyPeriodId) as period_allocated";
        String currentAllocated = " and t.id = :timetableId) as current_allocated";
        qb.parameter("timetableStudyPeriodId", sp.getId());
        qb.parameter("timetableStudyPeriodStart", JpaQueryUtil.parameterAsTimestamp(sp.getStartDate()));
        qb.parameter("timetableId", EntityUtil.getId(timetable));

        String select = "jt.id, coalesce(jct2.capacity_type_code, jct.capacity_type_code) capacity_type_code,"
                + " sum(coalesce(jtc.hours, jc.hours)) outside_period_total_hours, " + totalAllocatedSelect + outsidePeriodAllocated
                + "," + totalAllocatedSelect + periodAllocated + "," + totalAllocatedSelect + currentAllocated;
        List<?> data = qb.select(select, em).getResultList();

        return !data.isEmpty()
                ? data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                        Collectors.toMap(r -> resultAsLong(r, 0).toString() + "/" + resultAsString(r, 1),
                                r -> new LeftOverLessons(resultAsLong(r, 2), resultAsLong(r, 3), resultAsLong(r, 4),
                                        resultAsLong(r, 5), null, resultAsLong(r, 0), resultAsString(r, 1)))))
                : new HashMap<>();
    }

    private Map<Long, Map<String, AllocatedLessons>> getAllocatedLessonsForJournalTeacher(
            Timetable timetable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event te"
                + " join timetable_object too on too.id = te.timetable_object_id and too.journal_id is not null"
                + " join timetable t on t.id = too.timetable_id"
                + " join timetable_event_time tet on tet.timetable_event_id = te.id"
                + " join timetable_event_teacher tete on tete.timetable_event_time_id = tet.id"
                + " join journal_teacher jt on jt.teacher_id = tete.teacher_id and jt.journal_id = too.journal_id");

        qb.requiredCriteria("t.study_period_id = :studyPeriodId", "studyPeriodId",
                EntityUtil.getId(timetable.getStudyPeriod()));
        String groupBy = "jt.id, te.capacity_type_code";
        qb.parameter("timetableId", EntityUtil.getId(timetable));

        qb.groupBy(groupBy);
        String select = groupBy
                + ", count(*) filter (where too.timetable_id = :timetableId ) as current_allocated, count(*) as total_allocated";

        List<?> data = qb.select(select, em).getResultList();

        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.toMap(
                r -> resultAsLong(r, 0).toString() + "/" + resultAsString(r, 1),
                r -> new AllocatedLessons(resultAsLong(r, 2), resultAsLong(r, 3), resultAsString(r, 1)))));
    }

    private Map<Long, List<RoomAutocompleteResult>> journalRooms(List<Long> journalIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal_room jr"
                + " join room r on r.id = jr.room_id"
                + " join building b on b.id = r.building_id")
                .sort("r.code");

        qb.requiredCriteria("jr.journal_id in (:journalIds)", "journalIds", journalIds);

        String select = "jr.journal_id, r.id r_id, b.id b_id, b.code as b_code, r.code as r_code";
        List<?> data = qb.select(select, em).getResultList();

        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> new RoomAutocompleteResult(resultAsLong(r, 1), resultAsLong(r, 2),
                        resultAsString(r, 3), resultAsString(r, 4)), Collectors.toList())));
    }

    List<LessonTimeDto> getLessonTimesForPlanning(Timetable timetable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from lesson_time lt"
                + " join lesson_time_building_group ltbg on ltbg.id = lt.lesson_time_building_group_id"
                + " join lesson_time_building ltb on ltb.lesson_time_building_group_id = ltbg.id");

        qb.requiredCriteria("lt.school_id = :schoolId", "schoolId", EntityUtil.getId(timetable.getSchool()));
        qb.requiredCriteria("ltbg.valid_from <= :timetableStartDate", "timetableStartDate", timetable.getStartDate());
        qb.filter("(ltbg.valid_thru >= :timetableStartDate or ltbg.valid_thru is null)");

        String select = "lt.id, lt.start_time, lt.end_time, lt.lesson_nr, lt.day_mon, lt.day_tue, lt.day_wed,"
                + " lt.day_thu, lt.day_fri, lt.day_sat, lt.day_sun, ltb.building_id";
        List<?> data = qb.select(select, em).getResultList();

        Map<Long, List<Long>> buildingIdsByLessonTimes = data.stream().collect(Collectors.groupingBy(
                r -> resultAsLong(r, 0), Collectors.mapping(r -> resultAsLong(r, 11), Collectors.toList())));
        select = "distinct lt.id, lt.start_time, lt.end_time, lt.lesson_nr, lt.day_mon, lt.day_tue, lt.day_wed,"
                + " lt.day_thu, lt.day_fri, lt.day_sat, lt.day_sun";
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

        String select = "tet.id, tet.start, tet.end, te.lesson_nr, te.capacity_type_code, too.journal_id,"
                + " j.name_et, tosg.student_group_id";
        qb.sort("cast(te.start as date), te.lesson_nr");
        List<?> data = qb.select(select, em).getResultList();

        List<TimetableEventDto> result = StreamUtil.toMappedList(
                r -> new TimetableEventDto(resultAsLong(r, 0), resultAsLocalDateTime(r, 1),
                        resultAsLocalDateTime(r, 2), resultAsInteger(r, 3), resultAsString(r, 4), resultAsLong(r, 5),
                        resultAsString(r, 6), resultAsLong(r, 7)),
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

        String select = "tet.id as timetable_event_id, r.id r_id, b.id b_id, b.code as b_code, r.code as r_code";
        List<?> data = qb.select(select, em).getResultList();

        Map<Long, List<RoomAutocompleteResult>> roomsByTimetableEvent = data.stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                        Collectors.mapping(r -> new RoomAutocompleteResult(resultAsLong(r, 1), resultAsLong(r, 2),
                                resultAsString(r, 3), resultAsString(r, 4)), Collectors.toList())));
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

    public List<GroupTimetableDto> groupTimetables(School school, Long studyYear) {
        if (!allowedToViewSchoolTimetable(school)) {
            return Collections.emptyList();
        }
        Query q = em.createNativeQuery("select distinct sg.id, sg.code from student_group sg"
                + " join timetable_object_student_group tosg on sg.id = tosg.student_group_id"
                + " join timetable_object tobj on tosg.timetable_object_id = tobj.id"
                + " join timetable tt on tobj.timetable_id = tt.id"
                + " join study_period sp on tt.study_period_id = sp.id"
                + " join study_year sy on sp.study_year_id = sy.id"
                + " where tt.school_id = ?1 and sy.id = ?2 and tt.status_code in (:shownStatusCodes)"
                + " union"
                + " select distinct sg.id, sg.code from timetable_event te"
                + " join timetable_event_time tet on te.id = tet.timetable_event_id"
                + " join timetable_event_student_group tesg on tet.id = tesg.timetable_event_time_id"
                + " join student_group sg on tesg.student_group_id = sg.id"
                + " join study_year sy on sy.id = ?2"
                + " where te.school_id = ?1 and te.timetable_object_id is null and (tet.start <= sy.end_date and tet.end >= sy.start_date)"
                + " order by 2");
        q.setParameter(1, school.getId());
        q.setParameter(2, studyYear);
        q.setParameter("shownStatusCodes", shownStatusCodes());

        List<?> data = q.getResultList();
        return StreamUtil.toMappedList(r -> new GroupTimetableDto((Object[]) r), data);
    }

    public List<TeacherTimetableDto> teacherTimetables(School school, Long studyYearId) {
        if (!allowedToViewSchoolTimetable(school)) {
            return Collections.emptyList();
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
        q.setParameter(1, school.getId());
        q.setParameter(2, studyYearId);
        q.setParameter("shownStatusCodes", shownStatusCodes());

        List<?> data = q.getResultList();
        return StreamUtil.toMappedList(r -> new TeacherTimetableDto((Object[])r), data);
    }

    public List<RoomTimetableDto> roomTimetables(School school, Long studyYearId) {
        if (!allowedToViewSchoolTimetable(school)) {
            return Collections.emptyList();
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
        q.setParameter(1, school.getId());
        q.setParameter(2, studyYearId);
        q.setParameter("shownStatusCodes", shownStatusCodes());

        List<?> data = q.getResultList();
        return StreamUtil.toMappedList(r -> new RoomTimetableDto((Object[])r), data);
    }

    List<String> shownStatusCodes() {
        return shownStatusCodes(null);
    }

    List<String> shownStatusCodes(TimetablePersonHolder person) {
        if (person != null) {
            return Role.ROLL_O.name().equals(person.getRole()) ? TEACHER_TIMETABLES : PUBLIC_TIMETABLES;
        } else {
            HoisUserDetails user = userFromPrincipal();
            if (user != null) {
                if (user.isMainAdmin() || user.isSchoolAdmin() || user.isLeadingTeacher()) {
                    return ALL_TIMETABLES;
                } else if (user.isTeacher()) {
                    return TEACHER_TIMETABLES;
                }
            }
            return PUBLIC_TIMETABLES;
        }
    }

    public List<StudyYearSearchDto> timetableStudyYears(Long schoolId) {
        List<?> data = em.createNativeQuery("select c.code, c.name_et, c.name_en, sy.id, sy.start_date, sy.end_date, 0 as count from"
                + " (select id from study_year sy where sy.school_id = :schoolId and"
                + " ((sy.start_date <= :now and sy.end_date >= :now) or (sy.start_date - interval '2 month') <= :now and sy.end_date >= :now)"
                + " union select first_value(id) over(order by end_date desc) from study_year where school_id = :schoolId and end_date < :now) x"
                + " join study_year sy on sy.id = x.id"
                + " join classifier c on c.code = sy.year_code"
                + " order by sy.start_date asc")
                .setParameter("schoolId", schoolId)
                .setParameter("now", JpaQueryUtil.parameterAsTimestamp(LocalDate.now()))
                .getResultList();

        return StreamUtil.toMappedList(r -> new StudyYearSearchDto((Object[])r), data);
    }

    public List<StudyYearSearchDto> personTimetableStudyYears(String encodedPerson) {
        TimetablePersonHolder person = getPerson(encodedPerson);
        if (person != null) {
            if (Role.ROLL_O.name().equals(person.getRole())) {
                Teacher teacher = em.getReference(Teacher.class, person.getRoleId());
                return timetableStudyYears(EntityUtil.getId(teacher.getSchool()));
            } else if (Role.ROLL_T.name().equals(person.getRole())) {
                Student student = em.getReference(Student.class, person.getRoleId());
                return timetableStudyYears(EntityUtil.getId(student.getSchool()));
            }
        }
        return null;
    }

    public List<TimetableStudyYearWeekDto> personTimetableStudyYearWeeks(StudyYear studyYear, String encodedPerson) {
        Student student = null;
        TimetablePersonHolder person = getPerson(encodedPerson);
        if (person != null && Role.ROLL_T.name().equals(person.getRole())) {
            student = em.getReference(Student.class, person.getRoleId());
        }
        return timetableStudyYearWeeks(studyYear, student);
    }

    public List<TimetableStudyYearWeekDto> timetableStudyYearWeeks(StudyYear studyYear, Student student) {
        Long studyYearId = studyYear.getId();
        LocalDate start = studyYear.getStartDate();
        LocalDate end = studyYear.getEndDate();

        List<TimetableStudyYearWeekDto> weeks = new ArrayList<>();
        int weekNr = 0;
        while (start.isBefore(end)) {
            LocalDate weekStart = start.with(DayOfWeek.MONDAY);
            LocalDate weekEnd = start.with(DayOfWeek.SUNDAY);

            weeks.add(new TimetableStudyYearWeekDto(studyYearId, Long.valueOf(weekNr), weekStart, weekEnd));

            weekNr++;
            start = weekEnd.plusDays(1);
        }
        if (student != null) {
            setConnectedSubjects(studyYear, student, weeks);
        }
        return weeks;
    }

    private void setConnectedSubjects(StudyYear studyYear, Student student, List<TimetableStudyYearWeekDto> weeks) {
        boolean isHigher = StudentUtil.isHigher(student);

        Set<DateRangeDto> periodsWithConnectedSubjects = isHigher
                ? higherPeriodsWithConnectedSubjects(EntityUtil.getId(student), EntityUtil.getId(studyYear))
                : vocationalPeriodsWithConnectedSubjects(EntityUtil.getId(student), EntityUtil.getId(studyYear));

        for (TimetableStudyYearWeekDto week : weeks) {
            week.setConnectedSubjects(Boolean.FALSE);
            for (DateRangeDto period : periodsWithConnectedSubjects) {
                if (Boolean.TRUE.equals(week.getConnectedSubjects())) {
                    break;
                }
                week.setConnectedSubjects(Boolean.valueOf(
                        !week.getStart().isAfter(period.getEnd()) && !period.getStart().isAfter(week.getEnd())));
            }
        }
    }

    private Set<DateRangeDto> higherPeriodsWithConnectedSubjects(Long studentId, Long studyYearId) {
        Query q = em.createNativeQuery("select sp.id, sp.start_date, sp.end_date from study_year sy "
                + "join study_period sp on sy.id = sp.study_year_id "
                + "join declaration d on sp.id = d.study_period_id "
                + "join declaration_subject ds on d.id = ds.declaration_id "
                + "where d.student_id = :studentId and sy.id = :studyYearId and d.status_code = :declarationStatus");
        q.setParameter("studentId", studentId);
        q.setParameter("studyYearId", studyYearId);
        q.setParameter("declarationStatus", DeclarationStatus.OPINGUKAVA_STAATUS_K.name());

        List<?> data = q.getResultList();
        Set<DateRangeDto> periodsWithConnectedSubjects = StreamUtil
                .toMappedSet(r -> new DateRangeDto(resultAsLocalDate(r, 1), resultAsLocalDate(r, 2)), data);

        return periodsWithConnectedSubjects;
    }

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

    public void  sendTimetableChangesMessages(TimetableObject object, LocalDateTime start, LocalDateTime end,
            List<TimetableObjectStudentGroup> objectStudentGroups) {
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
                Set<Long> studentGroups = StreamUtil.toMappedSet(r -> EntityUtil.getId(r.getStudentGroup()), objectStudentGroups);
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
                    TimetableChanged msg = new TimetableChanged(student, subject, journalName, start, end);
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

    // TODO: forbidden school public data
    static boolean allowedToViewSchoolTimetable(School school) {
        if (FORBIDDEN_SCHOOL_ID.equals(school.getId())) {
            HoisUserDetails user = userFromPrincipal();
            return user != null && user.getSchoolId().equals(school.getId());
        }
        return true;
    }

    private static class PeriodLessons {
        private final Long totalPlannedLessons;
        private final Long thisPlannedLessons;
        private final Long totalAllocatedLessons;

        public PeriodLessons(Long totalPlannedLessons, Long thisPlannedLessons, Long totalAllocatedLessons) {
            this.totalPlannedLessons = totalPlannedLessons;
            this.thisPlannedLessons = thisPlannedLessons;
            this.totalAllocatedLessons = totalAllocatedLessons;
        }

        public Long getTotalPlannedLessons() {
            return totalPlannedLessons;
        }

        public Long getThisPlannedLessons() {
            return thisPlannedLessons;
        }

        public Long getTotalAllocatedLessons() {
            return totalAllocatedLessons;
        }

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

        @SuppressWarnings("unused")
        public String getCapacityType() {
            return capacityType;
        }
    }

    private static class LeftOverLessons {
        private final Long outsidePeriodTotalHours;
        private final Long outsidePeriodAllocated;
        private final Long periodAllocated;
        private final Long currentWeekAllocated;
        private final Long studentGroup;
        private final Long journal;
        private final String capacityType;
        
        public LeftOverLessons(Long outsidePeriodTotalHours, Long outsidePeriodAllocated, Long periodAllocated,
                Long currentWeekAllocated, Long studentGroup, Long journal, String capacityType) {
            this.outsidePeriodTotalHours = outsidePeriodTotalHours;
            this.outsidePeriodAllocated = outsidePeriodAllocated;
            this.periodAllocated = periodAllocated;
            this.currentWeekAllocated = currentWeekAllocated;
            this.studentGroup = studentGroup;
            this.journal = journal;
            this.capacityType = capacityType;
        }

        public Long getOutsidePeriodTotalHours() {
            return outsidePeriodTotalHours;
        }

        public Long getOutsidePeriodAllocated() {
            return outsidePeriodAllocated;
        }

        @SuppressWarnings("unused")
        public Long getPeriodAllocated() {
            return periodAllocated;
        }

        @SuppressWarnings("unused")
        public Long getCurrentWeekAllocated() {
            return currentWeekAllocated;
        }

        @SuppressWarnings("unused")
        public Long getStudentGroup() {
            return studentGroup;
        }

        @SuppressWarnings("unused")
        public Long getJournal() {
            return journal;
        }

        @SuppressWarnings("unused")
        public String getCapacityType() {
            return capacityType;
        }
    }
    
    private static Long getIdValue(String id) {
        return Long.valueOf(id.substring(3, id.length() - 2));
    }

	public Document getExportedWeek(LocalDate startDate, LocalDate endDate, StudyPeriod studyPeriod, HoisUserDetails user) {
		Long schoolId = user.getSchoolId();
		Document document = new Document();
		//Set general
		setExportedWeekGeneralData(document, schoolId, studyPeriod, startDate, endDate);
        //Set time periods
        setExportedWeekTimePeriods(document, schoolId, startDate);
        //Set rooms
        setExportedWeekRooms(document, schoolId);
        //Get journals, teachers, student_groups, lessons
        Integer weekNr = studyPeriod.getWeekNrForDate(startDate);
        List<?> dbJournals = getJournals(schoolId, weekNr, studyPeriod);
        List<?> additionalJouralsFromCapacityPerTeacher = getJournalsFromCapacityPerTeacher(schoolId, weekNr, studyPeriod);
        //Set teachers
        Set<Long> journalIds = StreamUtil.toMappedSet(r->resultAsLong(r, 2), dbJournals);
        journalIds.addAll(StreamUtil.toMappedList(r->resultAsLong(r, 2), additionalJouralsFromCapacityPerTeacher));
        setExportedWeekTeachers(document, journalIds);
        
        String occurance = "1111F111111F111111F111111F111111F111111F111111F111111F111111F111111F111111F111111F"
                        + "111111F111111F111111F111111F111111F111111F111111F111111F111111F111111F111111F"
                        + "111111F111111F111111F111111F111111F111111F111111F111111F111111F111111F111111F"
                        + "111111F111111F111111F111111F111111F111111F111111F11111";
        //Set lessons and subjects
        Set<ee.hitsa.ois.xml.exportTimetable.Lesson> lessons = new HashSet<>();
        Set<ee.hitsa.ois.xml.exportTimetable.Subject> subjects = new HashSet<>();
        List<String> usedIds = new ArrayList<>();
        for (Object r : dbJournals) {
            int uniqueCounter = 0;
            String newId = "LS_" + resultAsLong(r, 2) + getLessonNumber(uniqueCounter);
            while (usedIds.contains(newId)) {
                uniqueCounter ++;
                newId = newId.substring(0, newId.length() - 2) + getLessonNumber(uniqueCounter);
            }
            usedIds.add(newId);
            ee.hitsa.ois.xml.exportTimetable.Subject capacitySubject = new ee.hitsa.ois.xml.exportTimetable.Subject("SU_" 
                    + resultAsString(r, 0) + "`" + resultAsString(r, 4), resultAsString(r, 1));
            subjects.add(capacitySubject);
            ee.hitsa.ois.xml.exportTimetable.Lesson lesson = new ee.hitsa.ois.xml.exportTimetable.Lesson(
                    newId,
                    resultAsInteger(r, 3),
                    new LessonSubject(capacitySubject.getId()),
                    documentDateFormat.format(startDate),
                    documentDateFormat.format(endDate),
                    occurance);
            lessons.add(lesson);
        }
        if(!journalIds.isEmpty()) {
            setExportedWeekLessonTeachers(journalIds, lessons);
            
            for (Object r : additionalJouralsFromCapacityPerTeacher) {
                int uniqueCounter = 0;
                String newId = "LS_" + resultAsLong(r, 2) + getLessonNumber(uniqueCounter);
                while (usedIds.contains(newId)) {
                    uniqueCounter ++;
                    newId = newId.substring(0, newId.length() - 2) + getLessonNumber(uniqueCounter);
                }
                usedIds.add(newId);
                ee.hitsa.ois.xml.exportTimetable.Subject capacitySubject = new ee.hitsa.ois.xml.exportTimetable.Subject("SU_" 
                        + resultAsString(r, 0) + "`" + resultAsString(r, 4), resultAsString(r, 1));
                subjects.add(capacitySubject);
                ee.hitsa.ois.xml.exportTimetable.Lesson lesson = new ee.hitsa.ois.xml.exportTimetable.Lesson(
                        newId,
                        resultAsInteger(r, 3),
                        new LessonSubject(capacitySubject.getId()),
                        documentDateFormat.format(startDate),
                        documentDateFormat.format(endDate),
                        occurance);
                lesson.setLesson_teacher(new LessonTeacher("TR_" + resultAsString(r, 5)));
                lessons.add(lesson);
            }
            
            subjects = subjects.stream().filter(StreamUtil.distinctByKey(ee.hitsa.ois.xml.exportTimetable.Subject::getId))
                    .collect(Collectors.toSet());
            document.setSubjects(new Subjects(subjects));
            
            setExportedWeekLessonRooms(journalIds, lessons);
        }
        // Set <lesson_classes> and <classes>
        setExportedWeekClasses(document, journalIds, lessons);
        
        Set<Lesson> extendedLessons = extendLessonsByTeacher(lessons);
        document.setLessons(new Lessons(extendedLessons));
		return document;
	}

    private static void setExportedWeekLessonClasses(Object r, Set<Lesson> lessons, String sgCode) {
        List<Long> journals = Arrays.asList(resultAsString(r, 0).split(",")).stream().map(p -> Long.valueOf(p)).collect(Collectors.toList());
        List<Lesson> filteredLessons = lessons.stream().filter(lesson -> journals.contains(getIdValue(lesson.id))).collect(Collectors.toList());
        for (Lesson lesson : filteredLessons) {
            if (lesson.getLesson_classes() == null) {
                lesson.setLesson_classes(new LessonClasses(sgCode));
            } else {
                if (!StringUtils.isEmpty(lesson.getLesson_classes().id)) {
                    // might have duplicate lessons
                    if (!lesson.getLesson_classes().id.contains(sgCode)) {
                        lesson.getLesson_classes().addId(sgCode);
                    }
                } else {
                    lesson.getLesson_classes().addId(sgCode);
                }
            }
        }
    }

    private void setExportedWeekLessonRooms(Set<Long> journalIds, Set<Lesson> lessons) {
        Query lessonRooms = em.createNativeQuery("select concat(b.code, rr.code) as buildingRoom, jr.journal_id"
                + " from journal_room jr"
                + " join room rr on jr.room_id = rr.id"
                + " join building b on rr.building_id = b.id"
                + " where jr.journal_id in :journalIds");
        lessonRooms.setParameter("journalIds", journalIds);
        List<?> dbLessonRooms = lessonRooms.getResultList();
        
        // Attach rooms to lessons
        for (Object r : dbLessonRooms) {
            for (Lesson lesson : lessons) {
                if (getIdValue(lesson.id).equals(resultAsLong(r, 1)) && !"null".equals(resultAsString(r, 0))) {
                    if (lesson.getLesson_room() == null) {
                        lesson.setLesson_room(new LessonRoom("RM_" + resultAsString(r, 0).replaceAll(" ", "")));
                    } else {
                        lesson.getLesson_room().addId("RM_" + resultAsString(r, 0).replaceAll(" ", ""));
                    }
                }
            }
        }
        lessons.removeIf(p -> p.getLesson_teacher() == null);
    }

    private void setExportedWeekLessonTeachers(Set<Long> journalIds, Set<Lesson> lessons) {
        Query lessonTeachers = em.createNativeQuery("select tt.untis_code, jt.journal_id, pp.firstname, pp.lastname"
                + " from journal_teacher jt"
                + " join teacher tt on tt.id = jt.teacher_id"
                + " join person pp on pp.id = tt.person_id"
                + " where jt.journal_id in :journalIds"
                + " and tt.untis_code is not null");
        lessonTeachers.setParameter("journalIds", journalIds);
        List<?> dbLessonTeachers = lessonTeachers.getResultList();
        
        // Attach teachers to lessons
        for (Object r : dbLessonTeachers) {
            for (Lesson lesson : lessons) {
                if (getIdValue(lesson.id).equals(resultAsLong(r, 1)) && !"null".equals(resultAsString(r, 0))) {
                    if (lesson.getLesson_teacher() == null) {
                        lesson.setLesson_teacher(new LessonTeacher("TR_" + resultAsString(r, 0)));
                    } else {
                        lesson.getLesson_teacher().addId("TR_" + resultAsString(r, 0));
                    }
                }
            }
        }
    }

    private List<?> getJournals(Long schoolId, Integer weekNr, StudyPeriod studyPeriod) {
        JpaNativeQueryBuilder journalQuery = new JpaNativeQueryBuilder("from journal_omodule_theme jot " +
                "join journal j on jot.journal_id = j.id " +
                "join journal_capacity jc on jc.journal_id = j.id " +
                "join journal_capacity_type jct on jct.id = jc.journal_capacity_type_id " +
                "join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id " +
                "join lesson_plan lp on lp.id = lpm.lesson_plan_id").sort("j.id");
        journalQuery.requiredCriteria("j.school_id = :schoolId", "schoolId", schoolId);
        journalQuery.requiredCriteria("jc.week_nr = :weekNr", "weekNr", Long.valueOf(weekNr.longValue()));
        journalQuery.requiredCriteria("jc.study_period_id = :studyPeriod", "studyPeriod", studyPeriod.getId());
        journalQuery.filter("(lp.is_usable = true and j.untis_code is not null and jc.hours != 0)");
        journalQuery.filter("(j.is_capacity_diff is null or j.is_capacity_diff = false)");
        String journalSelect = "distinct j.untis_code as journalCode," + 
                " j.name_et as journalName," + 
                " j.id as journalId," + 
                " jc.hours as capacity," +
                " jct.capacity_type_code as typeCode";
        return journalQuery.sort("j.id").select(journalSelect, em).getResultList();
    }

    private List<?> getJournalsFromCapacityPerTeacher(Long schoolId, Integer weekNr, StudyPeriod studyPeriod) {
        JpaNativeQueryBuilder teacherCapacityQuery = new JpaNativeQueryBuilder("from journal_teacher_capacity jtc " +
                "join journal_capacity_type jct on jct.id = jtc.journal_capacity_type_id " +
                "join study_period sp on sp.id = jtc.study_period_id " +
                "join journal_teacher jt on jt.id = jtc.journal_teacher_id " +
                "join teacher t on jt.teacher_id = t.id " +
                "join journal j on j.id = jt.journal_id " +
                "join journal_omodule_theme jot on jot.journal_id = j.id " +
                "join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id " +
                "join lesson_plan lp on lp.id = lpm.lesson_plan_id");
        teacherCapacityQuery.requiredCriteria("j.school_id = :schoolId", "schoolId", schoolId);
        teacherCapacityQuery.requiredCriteria("jtc.week_nr = :weekNr", "weekNr", Long.valueOf(weekNr.longValue()));
        teacherCapacityQuery.requiredCriteria("jtc.study_period_id = :studyPeriod", "studyPeriod",  studyPeriod.getId());
        teacherCapacityQuery.filter("lp.is_usable = true and j.untis_code is not null and "
                + "j.is_capacity_diff = true and jtc.hours != 0 and t.untis_code is not null");
        String teacherCapacitySelect = "distinct j.untis_code as journalCode," + 
                " j.name_et as journalName, j.id as journalId, jtc.hours as capacity," +
                " jct.capacity_type_code as typeCode, t.untis_code as teacherUntisCode";
        return teacherCapacityQuery.sort("j.id").select(teacherCapacitySelect, em).getResultList();
    }

    private void setExportedWeekTeachers(Document document, Set<Long> journalIds) {
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
            teachers = teachers.stream()
                    .filter(StreamUtil.distinctByKey(ee.hitsa.ois.xml.exportTimetable.Teacher::getId))
                    .collect(Collectors.toSet());
            document.setTeachers(new Teachers(teachers));
        }
    }

    private void setExportedWeekClasses(Document document, Set<Long> journalIds, Set<Lesson> lessons) {
        if (!journalIds.isEmpty()) {
            Query studentGroupQuery = em.createNativeQuery("select string_agg(j.id\\:\\:character varying, ',') as journals, "
                    + "sg.code as studentGroupCode, sgt.untis_code as untisCode, c.name_et as curriculumEt " +
                    "from journal j " +
                    "join journal_omodule_theme jot on jot.journal_id = j.id " +
                    "join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id " +
                    "join lesson_plan lp on lp.id = lpm.lesson_plan_id " +
                    "join student_group sg on sg.id = lp.student_group_id " +
                    "join curriculum c on c.id = sg.curriculum_id " +
                    "left join teacher sgt on sg.teacher_id = sgt.id and sgt.untis_code is not null " +
                    "where j.id in (:journalIds) " +
                    "group by sg.code, sgt.untis_code, c.name_et");
            studentGroupQuery.setParameter("journalIds", journalIds);
            List<?> dbStudentGroups = studentGroupQuery.getResultList();
            Set<ee.hitsa.ois.xml.exportTimetable.Class> classes = StreamUtil
                    .toMappedSet(r -> {
                        String sgCode = "CL_" + resultAsString(r, 1);
                        setExportedWeekLessonClasses(r, lessons, sgCode);
                        String teacherUntisCode = resultAsString(r, 2);
                        if (teacherUntisCode == null) {
                            return new ee.hitsa.ois.xml.exportTimetable.Class(sgCode, resultAsString(r, 3));
                        }
                        return new ee.hitsa.ois.xml.exportTimetable.Class(sgCode, resultAsString(r, 3), new ClassTeacher("TR_" + teacherUntisCode));
                    }, dbStudentGroups);
            document.setClasses(new Classes(classes));
        }
    }

    private void setExportedWeekGeneralData(Document document, Long schoolId, StudyPeriod studyPeriod, LocalDate startDate, LocalDate endDate) {
	    JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from study_year sy join school s on s.id = sy.school_id");
        qb.requiredCriteria("s.id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("sy.id = :studyYearId", "studyYearId", EntityUtil.getId(studyPeriod.getStudyYear()));
        String select = "s.name_et, sy.start_date, sy.end_date";
        List<?> data = qb.select(select, em).setMaxResults(1).getResultList();
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
    }

    private void setExportedWeekRooms(Document document, Long schoolId) {
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
    }

    private void setExportedWeekTimePeriods(Document document, Long schoolId, LocalDate startDate) {
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
              .toMappedSet(r -> new ee.hitsa.ois.xml.exportTimetable.TimePeriod(resultAsInteger(r, 3) != null ? getLessonNumber(resultAsInteger(r, 3).intValue()) : null,
                    Arrays.asList(resultAsBoolean(r, 4), resultAsBoolean(r, 5), resultAsBoolean(r, 6), resultAsBoolean(r, 7), resultAsBoolean(r, 8), resultAsBoolean(r, 9), resultAsBoolean(r, 10)),
                    resultAsInteger(r, 3),
                    DOCUMENT_TIME_FORMAT_SHORT.format(resultAsLocalTime(r, 1)),
                    DOCUMENT_TIME_FORMAT_SHORT.format(resultAsLocalTime(r, 2))), timePeriodResult);
        Set<TimePeriod> extendedList = new HashSet<>();
        
        //Set periods by week day
        for (TimePeriod period : timePeriods) {
            int weekdayNr = 1;
            for (Boolean isWeekDay : period.getDays()) {
                if (isWeekDay != null && isWeekDay.booleanValue()) {
                    extendedList.add(new TimePeriod(
                            "TP_" + weekdayNr + period.getId(),
                            Integer.valueOf(weekdayNr),
                            period.getPeriod(),
                            period.getStarttime(),
                            period.getEndtime()
                            ));
                }
                weekdayNr++;
            }
        }
        extendedList = extendedList.stream().filter(StreamUtil.distinctByKey(TimePeriod::getId)).collect(Collectors.toSet());
        document.setTimeperiods(new TimePeriods(extendedList));
    }

    private static String getLessonNumber(int lessonNumber) {
		int lessonNumberLength = String.valueOf(lessonNumber).length();
		if (lessonNumberLength == 1) {
			return "0" + lessonNumber;
		}
        return String.valueOf(lessonNumber);
	}

	private static Set<Lesson> extendLessonsByTeacher(Set<Lesson> lessons) {
	    Comparator<Lesson> compareById = new Comparator<Lesson>() {
            @Override
            public int compare(Lesson o1, Lesson o2) {
                return o1.id.compareTo(o2.id);
            }
        };
		Set<Lesson> extendedLessons = new TreeSet<>(compareById);
        
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
	
	public UntisCodeError checkExportPossibilites(LocalDate startDate, StudyPeriod studyPeriod, HoisUserDetails user) {
        Long schoolId = user.getSchoolId();
        School school = em.getReference(School.class, user.getSchoolId());
        Integer weekNrInt = studyPeriod.getWeekNrForDate(startDate);
        UntisCodeError errors = new UntisCodeError();
        if (!ClassifierUtil.oneOf(school.getTimetable(), SchoolTimetableType.TIMETABLE_ASC, SchoolTimetableType.TIMETABLE_UNTIS)) {
            return errors;
        }
        Long weekNr = null;
        if (weekNrInt != null) weekNr = Long.valueOf(weekNrInt.longValue());
        if (weekNr == null) {
            throw new HoisException("timetable.management.exportError");
        }
        if (!ClassifierUtil.oneOf(school.getTimetable(), SchoolTimetableType.TIMETABLE_UNTIS)) {
            return errors;
        }
        JpaNativeQueryBuilder journalQuery = new JpaNativeQueryBuilder("from journal_omodule_theme jot " +
                "join journal j on jot.journal_id = j.id " +
                "join journal_capacity jc on jc.journal_id = j.id " +
                "join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id " +
                "join lesson_plan lp on lp.id = lpm.lesson_plan_id " +
                "join student_group sg on sg.id = lp.student_group_id " +
                "join curriculum c on c.id = sg.curriculum_id " +
                "left join teacher t on sg.teacher_id = t.id " +
                "left join person p on p.id = t.person_id " +
                "left join journal_teacher jt on jt.journal_id = j.id " + 
                "left join teacher t2 on t2.id = jt.teacher_id " +
                "left join person p2 on p2.id = t2.person_id");
        journalQuery.requiredCriteria("j.school_id = :schoolId", "schoolId", schoolId);
        journalQuery.requiredCriteria("jc.week_nr = :weekNr", "weekNr",  weekNr);
        journalQuery.requiredCriteria("jc.study_period_id = :studyPeriod", "studyPeriod",  studyPeriod.getId());
        journalQuery.filter("(lp.is_usable = true and jc.hours != 0)");
        journalQuery.groupBy("j.untis_code, j.name_et, sg.code, j.id, c.name_et, t.untis_code, t.id, p.firstname, p.lastname, t2.id, p2.firstname, p2.lastname");
        String journalSelect = "j.untis_code as journalCode," + 
                " j.name_et as journalName," + 
                " sg.code as StudentGroupCode," + 
                " c.name_et as curriculumName," + 
                " t.untis_code as teacherCode," + 
                " j.id as journalId," + 
                " t.id as teacherId," +
                " p.firstname," +
                " p.lastname," +
                " t2.id as teacher2id," +
                " p2.firstname as teacher2firstname," +
                " p2.lastname as teacher2lastname," +
                " t2.untis_code as teacher2untiscode";
        
        List<?> dbJournals= journalQuery.select(journalSelect, em).getResultList();
        
        JpaNativeQueryBuilder teacherCapacityQuery = new JpaNativeQueryBuilder("from journal_teacher_capacity jtc " +
                "join journal_teacher jt on jt.id = jtc.journal_teacher_id " +
                "join study_period sp on sp.id = jtc.study_period_id " +
                "join journal j on j.id = jt.journal_id " +
                "join teacher t on t.id = jt.teacher_id " + 
                "join person p on p.id = t.person_id" );
        teacherCapacityQuery.requiredCriteria("j.school_id = :schoolId", "schoolId", schoolId);
        teacherCapacityQuery.requiredCriteria("jtc.week_nr = :weekNr", "weekNr",  weekNr);
        teacherCapacityQuery.requiredCriteria("jtc.study_period_id = :studyPeriod", "studyPeriod",  studyPeriod.getId());
        teacherCapacityQuery.groupBy("j.untis_code, j.name_et, j.id, t.untis_code, p.firstname, p.lastname");
        String teacherCapacitySelect = "j.untis_code as journalCode," + 
                " j.name_et as journalName," + 
                " t.untis_code as teacherCode," + 
                " j.id as journalId," + 
                " p.firstname, " + 
                " p.lastname";
        
        List<?> additionalJouralsFromCapacityPerTeacher = teacherCapacityQuery.select(teacherCapacitySelect, em).getResultList();
        
        List<NameAndCode> teacherCodes = StreamUtil
        		.toMappedList(r-> new NameAndCode(resultAsString(r, 7) + " " + resultAsString(r, 8), resultAsString(r, 4)), dbJournals);
        List<NameAndCode> timetableTeachers = StreamUtil
        		.toMappedList(r-> new NameAndCode(resultAsString(r, 10) + " " + resultAsString(r, 11), resultAsString(r, 12)), dbJournals);
        List<NameAndCode> personalCapacityTeachers = StreamUtil
                .toMappedList(r-> new NameAndCode(resultAsString(r, 4) + " " + resultAsString(r, 5), resultAsString(r, 2)), additionalJouralsFromCapacityPerTeacher);
        teacherCodes.addAll(timetableTeachers);
        teacherCodes.addAll(personalCapacityTeachers);
        List<NameAndCode> journalCodes = StreamUtil.toMappedList(r-> new NameAndCode(resultAsString(r, 1), resultAsString(r, 0)), dbJournals);
        List<NameAndCode> personalCapacityJournals = StreamUtil.toMappedList(r-> new NameAndCode(resultAsString(r, 1), resultAsString(r, 0)), additionalJouralsFromCapacityPerTeacher);
        journalCodes.addAll(personalCapacityJournals);
        teacherCodes = teacherCodes.stream()
        		.filter(p->(StringUtils.isEmpty(p.getCode()) || "null".equals(p.getCode())) && !"null null".equals(p.getName())).collect(Collectors.toList());
        journalCodes = journalCodes.stream()
        		.filter(p->StringUtils.isEmpty(p.getCode()) || "null".equals(p.getCode())).collect(Collectors.toList());
        List<String> journals = null;
        List<String> journalTeachers = null;
        if (!teacherCodes.isEmpty()) {
            journalTeachers = teacherCodes.stream().filter(StreamUtil.distinctByKey(NameAndCode::getName))
                    .map(NameAndCode::getName).collect(Collectors.toList());
        }
        if (!journalCodes.isEmpty()) {
            journals = journalCodes.stream().filter(StreamUtil.distinctByKey(NameAndCode::getName))
                    .map(NameAndCode::getName).collect(Collectors.toList());
        }
        List<Long> journalIds = StreamUtil.toMappedList(r->resultAsLong(r, 5), dbJournals);
        journalIds.addAll(StreamUtil.toMappedList(r->resultAsLong(r, 3), additionalJouralsFromCapacityPerTeacher));
        if (!journalIds.isEmpty()) {
        	Query teacherQuery = em.createNativeQuery("select t.untis_code, p.firstname, p.lastname, p.sex_code"
            		+ " from journal_teacher jt"
            		+ " join journal j on j.id = jt.journal_id"
            		+ " join teacher t on jt.teacher_id = t.id"
            		+ " join person p on p.id = t.person_id"
            		+ " where jt.journal_id in :journalIds");
            teacherQuery.setParameter("journalIds", journalIds);
            List<?> dbTeachers = teacherQuery.getResultList();
            List<NameAndCode> nameAndCode = StreamUtil
            		.toMappedList(r-> new NameAndCode(resultAsString(r, 1) + " " + resultAsString(r, 2), resultAsString(r, 0)), dbTeachers);
            nameAndCode = nameAndCode.stream()
            		.filter(p->StringUtils.isEmpty(p.getCode()) || "null".equals(p.getCode())).collect(Collectors.toList());
            teacherCodes.addAll(nameAndCode);
            if (!teacherCodes.isEmpty()) {
                journalTeachers = teacherCodes.stream().filter(StreamUtil.distinctByKey(NameAndCode::getName))
                        .map(NameAndCode::getName).collect(Collectors.toList());
            }
        }
        errors.setJournals(journals);
        errors.setTeachers(journalTeachers);
        return errors;
    }

    public TimetablePersonHolder getPerson(String encodedPerson) {
        try {
            String decrypted = CryptoUtil.decrypt(encryptionKey, Base64.decodeBase64(encodedPerson));
            String[] parts = decrypted.split(";");
            String role = parts[0];
            Long roleId = Long.valueOf(parts[1]);
            return new TimetablePersonHolder(role, roleId);
        } catch (Exception e) {
            throw new HoisException("main.messages.error.nopermission", e);
        }
    }

    public String getPersonalUrl(Role role, Long id) {
        String param = getPersonalUrlParam(role, id);
        return frontendBaseUrl + "timetable/personalGeneralTimetable/" + param;
    }

    private static String getPersonalUrlParam(Role role, Long id) {
        byte[] encryptedId = CryptoUtil.encrypt(encryptionKey, role.name() + ";" + id);
        try {
            return Base64.encodeBase64URLSafeString(encryptedId);
        } catch (Exception e) {
            throw new HoisException(e);
        }
    }

    public static class TimetablePersonHolder {
        private final String role;
        private final Long roleId;

        public TimetablePersonHolder(String role, Long roleId) {
            this.role = role;
            this.roleId = roleId;
        }

        public String getRole() {
            return role;
        }

        public Long getRoleId() {
            return roleId;
        }

    }
}
