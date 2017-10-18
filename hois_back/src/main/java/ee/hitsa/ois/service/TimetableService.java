package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsShort;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalRoom;
import ee.hitsa.ois.domain.timetable.LessonTime;
import ee.hitsa.ois.domain.timetable.Timetable;
import ee.hitsa.ois.domain.timetable.TimetableEvent;
import ee.hitsa.ois.domain.timetable.TimetableEventRoom;
import ee.hitsa.ois.domain.timetable.TimetableEventTeacher;
import ee.hitsa.ois.domain.timetable.TimetableEventTime;
import ee.hitsa.ois.domain.timetable.TimetableObject;
import ee.hitsa.ois.domain.timetable.TimetableObjectStudentGroup;
import ee.hitsa.ois.enums.TimetableEventRepeat;
import ee.hitsa.ois.enums.TimetableStatus;
import ee.hitsa.ois.enums.TimetableType;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.TimetableObjectRepository;
import ee.hitsa.ois.repository.TimetableRepository;
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.TimetableCopyForm;
import ee.hitsa.ois.web.commandobject.TimetableRoomAndTimeForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEditForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventHigherForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventVocationalForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableManagementSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.RoomDto;
import ee.hitsa.ois.web.dto.timetable.DateRangeDto;
import ee.hitsa.ois.web.dto.timetable.GeneralTimetableDto;
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
import ee.hitsa.ois.web.dto.timetable.TimetableDifferenceExcelDto;
import ee.hitsa.ois.web.dto.timetable.TimetableDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventDto;
import ee.hitsa.ois.web.dto.timetable.TimetableJournalDto;
import ee.hitsa.ois.web.dto.timetable.TimetableManagementSearchDto;
import ee.hitsa.ois.web.dto.timetable.TimetableStudentGroupCapacityDto;
import ee.hitsa.ois.web.dto.timetable.TimetableStudentGroupDto;
import ee.hitsa.ois.web.dto.timetable.VocationalTimetablePlanDto;

@Transactional
@Service
public class TimetableService {

    private static final long LESSON_LENGTH = 45;
    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private EntityManager em;
    @Autowired
    private TimetableRepository timetableRepository;
    @Autowired
    private TimetableObjectRepository timetableObjectRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private XlsService xlsService;

    public TimetableDto get(HoisUserDetails user, Timetable timetable) {
        TimetableDto dto = new TimetableDto();
        dto.setId(EntityUtil.getId(timetable));
        dto.setStudyYears(autocompleteService.studyYears(user.getSchoolId()));
        dto.setStudyPeriods(autocompleteService.studyPeriods(user.getSchoolId()));
        dto.setCurrentStudyPeriod(studyYearService.getCurrentStudyPeriod(user.getSchoolId()));
        dto.setCode(timetable.getIsHigher().booleanValue() ? TimetableType.TUNNIPLAAN_LIIK_H.name()
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
        if (timetable.getIsHigher().booleanValue()) {
            dto.setPairs(getPairsforTimetable(timetable));
            dto.setCurriculums(getHigherStudentGroupsByCurriculum(timetable));
        } else {
            dto.setCurriculums(getStudentGroupsByCurriculum(timetable));
        }
        dto.setStatus(timetable.getStatus().getCode());
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
        return dto;
    }
    
    public Timetable confirm(Timetable timetable) {
        if(EntityUtil.getId(timetable.getSchool()).equals(EntityUtil.getId(timetable.getSchool()))) {
            timetable.setStatus(classifierRepository.getOne(TimetableStatus.TUNNIPLAAN_STAATUS_K.toString()));
            timetableRepository.save(timetable);
        }
        return timetable;
    }
    
    public Timetable publicize(Timetable timetable) {
        if(EntityUtil.getId(timetable.getSchool()).equals(EntityUtil.getId(timetable.getSchool()))) {
            timetable.setStatus(classifierRepository.getOne(TimetableStatus.TUNNIPLAAN_STAATUS_P.toString()));
            timetableRepository.save(timetable);
        }
        return timetable;
    }
    
    private static Boolean isTimetableEditable(Timetable timetable) {
        if(!timetable.getStatus().getCode().equals(TimetableStatus.TUNNIPLAAN_STAATUS_S.toString())) {
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
                + " inner join timetable_event te on tet.timetable_event_id = te.id"
                + " inner join timetable_object too on te.timetable_object_id = too.id"
                + " inner join subject_study_period ssp on ssp.id = too.subject_study_period_id"
                + " inner join subject s on s.id = ssp.subject_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.requiredCriteria("too.timetable_id = :timetableId", "timetableId", EntityUtil.getId(timetable));

        qb.filter("too.id not in (select tosg.timetable_object_id from timetable_object_student_group tosg where tosg.timetable_object_id = too.id)");

        String select = "tet.id as timetable_event_id, tet.start, tet.end, te.lesson_nr, te.capacity_type_code, too.subject_study_period_id,s.code as subject_code, s.name_et";
        List<?> data = qb.select(select, em).getResultList();

        // first get timetable events into a list and add rooms then group by subject study periods
        List<TimetableEventDto> timetableEventTimes = StreamUtil
                .toMappedList(r -> new TimetableEventDto(resultAsLong(r, 0), resultAsLocalDateTime(r, 1),
                        resultAsLocalDateTime(r, 2), resultAsInteger(r, 3), resultAsString(r, 4), resultAsString(r, 6),
                        resultAsString(r, 6), resultAsLong(r, 5)), data);
        if (!timetableEventTimes.isEmpty()) {
            timetableEventTimes = addRoomsListToEvents(timetableEventTimes);
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
                + " inner join timetable_event te on tet.timetable_event_id = te.id"
                + " inner join timetable_object too on te.timetable_object_id = too.id"
                + " inner join timetable_object_student_group tosg on tosg.timetable_object_id = too.id"
                + " inner join student_group sg on sg.id = tosg.student_group_id"
                + " inner join subject_study_period ssp on ssp.id = too.subject_study_period_id"
                + " inner join subject s on s.id = ssp.subject_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.requiredCriteria("too.timetable_id = :timetableId", "timetableId", EntityUtil.getId(timetable));

        String select = "tet.id as timetable_event_id, tet.start, tet.end, te.lesson_nr, te.capacity_type_code, sg.id as student_group_id, s.code as subject_code, ssp.id as subject_study_period_id";
        List<?> data = qb.select(select, em).getResultList();
        
        //first get timetable events into a list and add rooms then group by student groups
        List<TimetableEventDto> timetableEventTimes = StreamUtil.toMappedList(
                r -> new TimetableEventDto(resultAsLong(r, 0), resultAsLocalDateTime(r, 1), resultAsLocalDateTime(r, 2),
                        resultAsInteger(r, 3), resultAsString(r, 4), resultAsString(r, 6), resultAsLong(r, 5), resultAsString(r, 6), resultAsLong(r, 7)),
                data);
        if (!timetableEventTimes.isEmpty()) {
            timetableEventTimes = addRoomsListToEvents(timetableEventTimes);
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
        saveVocationalTimetableEventTime(timetableEvent);
        timetableRepository.save(timetable);
        return timetable;
    }

    public byte[] timetableDifferenceExcel(Long timetableId) {
        Map<String, Object> data = new HashMap<>();
        Timetable currTimetable = em.getReference(Timetable.class, timetableId);
        Long prevTimetable = findPreviousTimetable(currTimetable);
        if(prevTimetable != null) {
            List<TimetableDifferenceExcelDto> difference = getTimetableDifferenceForExcel(
                    Arrays.asList(prevTimetable, EntityUtil.getId(currTimetable)), EntityUtil.getId(currTimetable),
                    prevTimetable);
            data.put("journals", difference);
            
        }
        return xlsService.generate("timetabledifference.xls", data);
    }

    private Long findPreviousTimetable(Timetable timetable) {
        LocalDate previousStart = timetable.getStartDate().with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable t");
        qb.requiredCriteria("t.start_date = :startDate", "startDate", previousStart);
        qb.optionalCriteria("t.is_higher = :isHigher", "isHigher", timetable.getIsHigher());
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", EntityUtil.getId(timetable.getSchool()));
        List<?> data = qb.select("t.id", em).getResultList();
        List<Long> prevTimetables = StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
        Long prevTimetableId = prevTimetables.isEmpty() ? null : prevTimetables.get(0);
        return prevTimetableId;
    }

    private List<TimetableDifferenceExcelDto> getTimetableDifferenceForExcel(List<Long> timetableIds, Long currTimetableId, Long prevTimetabelId) {
        String from = "from timetable t inner join timetable_object too on too.timetable_id = t.id"
                + " inner join timetable_event te on te.timetable_object_id = too.id"
                + " inner join timetable_object_student_group tosg on tosg.timetable_object_id = too.id"
                + " inner join journal j on j.id = too.journal_id"
                + " inner join student_group sg on sg.id = tosg.student_group_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("t.id in (:timetableIds)", "timetableIds", timetableIds);

        String select = "t.id as timetable_id, j.id as journal_id, j.name_et, sg.id, sg.code, te.capacity_type_code, count(te.capacity_type_code)";
        qb.groupBy("t.id, j.id, j.name_et, sg.id, sg.code, te.capacity_type_code");
        List<?> data = qb.select(select, em).getResultList();
        //this and last week lesson counts by their ids
        Map<Long, List<TimetableDifferenceExcelDto>> differencesByWeeks = data.stream()
                .collect(
                        Collectors
                                .groupingBy(r -> resultAsLong(r, 0),
                                        Collectors.mapping(
                                                r -> new TimetableDifferenceExcelDto(resultAsLong(r, 1), resultAsString(r, 2),
                                                        resultAsLong(r, 3), resultAsString(r, 4), resultAsString(r, 5), resultAsLong(r, 6)),
                                                Collectors.toList())));
        Map<Long, List<String>> teachersByJournals = getTeachersForJournals(
                differencesByWeeks.get(currTimetableId).stream().map(r -> r.getJournalId()).collect(Collectors.toList()));
        // key = journalId + _ + studentGroupId + _ + capacityType
        Map<String, TimetableDifferenceExcelDto> resultMap = new HashMap<>();
        for(TimetableDifferenceExcelDto curr : differencesByWeeks.get(currTimetableId)) {
            curr.setTeacherNames(String.join(", ", teachersByJournals.get(curr.getJournalId())));
            resultMap.put(curr.getJournalId() + "_" + curr.getStudentGroupId() + "_" + curr.getCapacityType(), curr);
        }
        if(differencesByWeeks.get(prevTimetabelId) != null) {
            for(TimetableDifferenceExcelDto prev : differencesByWeeks.get(prevTimetabelId)) {
                String currentKey = prev.getJournalId() + "_" + prev.getStudentGroupId() + "_" + prev.getCapacityType();
                TimetableDifferenceExcelDto resultRow = resultMap.get(currentKey);
                if(resultRow == null) {
                    prev.setPreviousWeek(prev.getCurrentWeek());
                    prev.setDifference(Long.valueOf(0 - prev.getCurrentWeek().longValue()));
                    prev.setCurrentWeek(Long.valueOf(0));
                    prev.setTeacherNames(String.join(", ", teachersByJournals.get(prev.getJournalId())));
                    resultMap.put(currentKey, prev);
                } else {
                    resultRow.setPreviousWeek(prev.getCurrentWeek());
                    resultRow.setDifference(Long.valueOf(resultRow.getPreviousWeek().longValue() - resultRow.getCurrentWeek().longValue()));
                }
            }
        }

        return resultMap.values().stream()
                .sorted(Comparator.comparing(TimetableDifferenceExcelDto::getJournalName)
                        .thenComparing(TimetableDifferenceExcelDto::getStudentGroup)
                        .thenComparing(TimetableDifferenceExcelDto::getCapacityType))
                .collect(Collectors.toList());
    }
    
    private Map<Long, List<String>> getTeachersForJournals(List<Long> journalIds) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j" + " inner join journal_teacher jt on jt.journal_id = j.id"
                + " inner join teacher t on t.id = jt.teacher_id" + " inner join person p on p.id = t.person_id");

        qb.requiredCriteria("j.id in (:journalIds)", "journalIds", journalIds);

        List<?> data = qb.select("j.id, p.firstname, p.lastname", em).getResultList();
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)),
                        Collectors.toList())));
    }
    
    private Map<Long, List<String>> getTeachersForSubjectStudyPeriods(Set<Long> subjectStudyPeriods) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from subject_study_period ssp"
                + " inner join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id"
                + " inner join teacher t on t.id = sspt.teacher_id"
                + " inner join person p on p.id = t.person_id");
        
        qb.requiredCriteria("ssp.id in (:sspIds)", "sspIds", subjectStudyPeriods);
        
        List<?> data = qb.select("ssp.id, p.firstname, p.lastname", em).getResultList();
        return data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)), Collectors.toList())));
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
            editForm.setCode(TimetableType.TUNNIPLAAN_LIIK_V.toString());
            copyTimetable = createTimetable(user, editForm);
        }
        copyTimetableObjects(originalTimetable, copyTimetable);
        copyTimetableEvents(originalTimetable, copyTimetable);

        return copyTimetable;
    }
    
    public static void copyTimetableObjects(Timetable timetable, Timetable copyTimetable) {
        Map<Long, TimetableObject> copyObjectsByJournal = StreamUtil.toMap(r -> EntityUtil.getId(r.getJournal()), copyTimetable.getTimetableObjects());
        for(TimetableObject originalObject : timetable.getTimetableObjects()) {
            TimetableObject copyObject = copyObjectsByJournal.get(EntityUtil.getId(originalObject.getJournal()));
            if(copyObject == null) {
                copyObject = new TimetableObject();
                copyObject.setJournal(originalObject.getJournal());
                copyObject.setTimetable(copyTimetable);
                copyTimetable.getTimetableObjects().add(copyObject);
            }
            List<Long> tosgIds = StreamUtil.toMappedList(r -> EntityUtil.getId(r.getStudentGroup()), originalObject.getTimetableObjectStudentGroups());
            for(TimetableObjectStudentGroup tosg : originalObject.getTimetableObjectStudentGroups()) {
                if(!tosgIds.contains(EntityUtil.getId(tosg))) {
                    TimetableObjectStudentGroup copyTosg = new TimetableObjectStudentGroup();
                    copyTosg.setStudentGroup(tosg.getStudentGroup());
                    copyTosg.setTimetableObject(copyObject);
                    copyObject.getTimetableObjectStudentGroups().add(copyTosg);
                }
            }
        }
    }
    
    public static void copyTimetableEvents(Timetable timetable, Timetable copyTimetable) {
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
            TimetableObject to = copyObjectsByJournal.get(EntityUtil.getId(event.getTimetableObject().getJournal()));
            copyEvent.setTimetableObject(to);
            to.getTimetableEvents().add(copyEvent);
            copyTimetableEventTime(event, copyEvent);
        }
    }
    
    public static void copyTimetableEventTime(TimetableEvent event, TimetableEvent copyEvent) {
        TimetableEventTime copyTet = new TimetableEventTime();
        copyTet.setStart(copyEvent.getStart());
        copyTet.setEnd(copyEvent.getEnd());
        //vocational timetables only have only timetableeventtime
        copyTimetableEventRooms(event.getTimetableEventTimes().get(0), copyTet);
        copyTimetableEventTeachers(event.getTimetableEventTimes().get(0), copyTet);
        copyEvent.getTimetableEventTimes().add(copyTet);
    }
    
    public static void copyTimetableEventRooms(TimetableEventTime time, TimetableEventTime copyTime) {
        for(TimetableEventRoom room : time.getTimetableEventRooms()) {
            TimetableEventRoom copyRoom = new TimetableEventRoom();
            copyRoom.setRoom(room.getRoom());
            copyRoom.setTimetableEventTime(copyTime);
            copyTime.getTimetableEventRooms().add(copyRoom);
        }
    }
    
    public static void copyTimetableEventTeachers(TimetableEventTime time, TimetableEventTime copyTime) {
        for(TimetableEventTeacher teacher : time.getTimetableEventTeachers()) {
            TimetableEventTeacher copyTeacher = new TimetableEventTeacher();
            copyTeacher.setTeacher(teacher.getTeacher());
            copyTeacher.setTimetableEventTime(copyTime);
            copyTime.getTimetableEventTeachers().add(copyTeacher);
        }
    }

    public Timetable saveHigherEvent(TimetableEventHigherForm form) {
        Timetable timetable = em.getReference(Timetable.class, form.getTimetable());
        TimetableObject timetableObject = getTimetableObjectForHigher(form, timetable);
        TimetableEvent timetableEvent = saveHigherTimetableEvent(timetableObject, form);
        saveHigherTimetableEventTimes(timetable, timetableEvent, form);
        timetableRepository.save(timetable);
        return timetable;
    }

    private TimetableEvent saveHigherTimetableEvent(TimetableObject object, TimetableEventHigherForm form) {
        TimetableEvent timetableEvent = addOrGetTimetableEvent(form.getOldEventId(), object);
        if(form.getLessonAmount() != null && form.getRepeatCode() != null) {
            timetableEvent.setStart(form.getStartTime());
            timetableEvent.setEnd(form.getStartTime().plusMinutes(LESSON_LENGTH * form.getLessonAmount().longValue()));
            timetableEvent.setLessons(form.getLessonAmount());
            timetableEvent.setRepeatCode(classifierRepository.getOne(form.getRepeatCode()));
            timetableEvent.setCapacityType(classifierRepository.getOne(form.getCapacityType()));
            timetableEvent.setConsiderBreak(Boolean.FALSE);
            Subject sub = object.getSubjectStudyPeriod().getSubject();
            timetableEvent.setName(sub.getNameEt() + " (" + sub.getCode() + ")");
        } else {
            timetableEvent.setStart(form.getStartTime());
            timetableEvent.setEnd(form.getStartTime().plusMinutes(LESSON_LENGTH * timetableEvent.getLessons().longValue()));
        }
        return timetableEvent;
    }

    private void saveHigherTimetableEventTimes(Timetable timetable, TimetableEvent timetableEvent, TimetableEventHigherForm form) {
        List<TimetableEventTime> timetableEventTimes = timetableEvent.getTimetableEventTimes();
        timetableEventTimes.clear();
        TimetableEventTime timetableEventTime = new TimetableEventTime();
        timetableEventTime.setStart(timetableEvent.getStart());
        timetableEventTime.setEnd(timetableEvent.getEnd());
        if(form.getRoom() != null) {
            TimetableEventRoom timetableEventRoom = new TimetableEventRoom();
            timetableEventRoom.setRoom(em.getReference(Room.class, form.getRoom().getId()));
            timetableEventRoom.setTimetableEventTime(timetableEventTime);
            timetableEventTime.getTimetableEventRooms().add(timetableEventRoom);
        }
        timetableEventTimes.add(timetableEventTime);
        long daysToAdd;
        if (timetableEvent.getRepeat().getCode().equals(TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_P.name())) {
            daysToAdd = 1;
        } else if (timetableEvent.getRepeat().getCode()
                .equals(TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_N.name())) {
            daysToAdd = 7;
        } else if (timetableEvent.getRepeat().getCode()
                .equals(TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_N2.name())) {
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
            if(form.getRoom() != null) {
                TimetableEventRoom timetableEventRoom = new TimetableEventRoom();
                timetableEventRoom.setRoom(em.getReference(Room.class, form.getRoom().getId()));
                timetableEventRoom.setTimetableEventTime(timetableEventTime);
                currentTimetableEventTime.getTimetableEventRooms().add(timetableEventRoom);
            }
            timetableEventTimes.add(currentTimetableEventTime);
            currentStart = currentStart.plusDays(daysToAdd);
            currentEnd = currentEnd.plusDays(daysToAdd);
        }

    }

    private TimetableObject getTimetableObjectForHigher(TimetableEventHigherForm form, Timetable timetable) {
        String from = "from timetable_object too";
        if(!form.getIsSubjectTeacherPair().booleanValue()) {
            from += " inner join timetable_object_student_group tosg on tosg.timetable_object_id = too.id";
        }
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.requiredCriteria("too.timetable_id = :timetableId", "timetableId", form.getTimetable());
        qb.requiredCriteria("too.subject_study_period_id = :subjectStudyPeriodId", "subjectStudyPeriodId", form.getSubjectStudyPeriod());
        if(form.getIsSubjectTeacherPair().booleanValue()) {
            qb.filter("too.id not in (select tosg.timetable_object_id from timetable_object_student_group tosg)");
        } else {
            qb.requiredCriteria("tosg.student_group_id = :studentGroupId", "studentGroupId", form.getStudentGroupId());
        }

        List<?> data = qb.select("too.id", em).setMaxResults(1).getResultList();
        List<Long> timetableObjectIds = StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
        Long timetableObjectId = timetableObjectIds.isEmpty() ? null : timetableObjectIds.get(0);

        if (timetableObjectId == null) {
            TimetableObject timetableObject = new TimetableObject();
            timetableObject
                    .setSubjectStudyPeriod(em.getReference(SubjectStudyPeriod.class, form.getSubjectStudyPeriod()));
            if(!form.getIsSubjectTeacherPair().booleanValue()) {
                timetableObject.getTimetableObjectStudentGroups().add(createTimetableObjectStudentGroupForHigher(
                        timetableObject, form.getStudentGroupId()));
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
                .setRepeatCode(classifierRepository.getOne(TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_EI.name()));
        timetableEvent.setLessonNr(lessonTime.getLessonNr());
        timetableEvent.setConsiderBreak(Boolean.FALSE);
        if (form.getCapacityType() != null && !form.getCapacityType().isEmpty()) {
            timetableEvent.setCapacityType(classifierRepository.getOne(form.getCapacityType()));
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
        if(timetableObject.getJournal() != null) {
            timetableEvent.setName(timetableObject.getJournal().getNameEt());
        } else {
            timetableObject.getSubjectStudyPeriod().getSubject().getNameEt();
        }
        return timetableEvent;
    }

    public Timetable saveEventRoomsAndTimes(TimetableRoomAndTimeForm form) {
        if(form.getTimetableEventId() != null && form.getEndTime() != null && form.getStartTime() != null) {
            TimetableEventTime timetableEventTime = em.getReference(TimetableEventTime.class, form.getTimetableEventId());
            TimetableEvent timetableEvent = timetableEventTime.getTimetableEvent();
            for(TimetableEventTime currentTime : timetableEvent.getTimetableEventTimes()) {
                List<TimetableEventRoom> oldRooms = currentTime.getTimetableEventRooms();
                EntityUtil.bindEntityCollection(oldRooms, r -> EntityUtil.getId(r.getRoom()), form.getRooms(),
                        RoomDto::getId, dto -> {
                            TimetableEventRoom timetableEventRoom = new TimetableEventRoom();
                            timetableEventRoom.setRoom(em.getReference(Room.class, dto.getId()));
                            timetableEventRoom.setTimetableEventTime(timetableEventTime);
                            return timetableEventRoom;
                        });
                currentTime.setStart(currentTime.getStart().withHour(form.getStartTime().getHour())
                        .withMinute(form.getStartTime().getMinute()));
                currentTime.setEnd(currentTime.getEnd().withHour(form.getEndTime().getHour())
                        .withMinute(form.getEndTime().getMinute()));
            }
            timetableEvent.setStart(timetableEventTime.getStart());
            timetableEvent.setEnd(timetableEventTime.getEnd());
            Timetable timetable = timetableEvent.getTimetableObject().getTimetable();
            timetableRepository.save(timetable);
            return timetable;
        }
        return null;
    }

    public Timetable deleteEvent(TimetableRoomAndTimeForm form) {
        TimetableEventTime timetableEventTime = em.getReference(TimetableEventTime.class, form.getTimetableEventId());
        TimetableEvent timetableEvent = timetableEventTime.getTimetableEvent();
        Timetable timetable = timetableEvent.getTimetableObject().getTimetable();
        EntityUtil.deleteEntity(timetableEvent, em);
        return timetable;
    }

    private static TimetableEventTime saveVocationalTimetableEventTime(TimetableEvent timetableEvent) {
        TimetableEventTime timetableEventTime;
        if (timetableEvent.getTimetableEventTimes().isEmpty()) {
            timetableEventTime = new TimetableEventTime();
            timetableEventTime.setTimetableEvent(timetableEvent);
            timetableEvent.getTimetableEventTimes().add(timetableEventTime);
            for(JournalRoom room : timetableEvent.getTimetableObject().getJournal().getJournalRooms()) {
                TimetableEventRoom timetableRoom = new TimetableEventRoom();
                timetableRoom.setRoom(room.getRoom());
                timetableRoom.setTimetableEventTime(timetableEventTime);
                timetableEventTime.getTimetableEventRooms().add(timetableRoom);
            }
        } else {
            // only for lower level of education, if is_higher and we want to
            // use the same function it will have to be changed
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
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable t").sort("t.start_date desc");

        qb.requiredCriteria("t.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("t.is_higher = :isHigher", "isHigher",
                Boolean.valueOf(TimetableType.isHigher(criteria.getType())));

        String select = "t.id, t.status_code, t.start_date, t.end_date, t.is_higher";
        return JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new TimetableManagementSearchDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsLocalDate(r, 2),
                    resultAsLocalDate(r, 3), resultAsBoolean(r, 4));
        });
    }

    private Page<TimetableManagementSearchDto> searchVocationalTimetableForManagement(
            TimetableManagementSearchCommand criteria, Pageable pageable, HoisUserDetails user) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable t").sort("t.start_date desc");

        qb.requiredCriteria("t.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("t.is_higher = :isHigher", "isHigher",
                Boolean.valueOf(TimetableType.isHigher(criteria.getType())));

        String select = "t.id, t.status_code, t.start_date, t.end_date, t.is_higher";
        List<?> data = qb.select(select, em).getResultList();

        List<TimetableManagementSearchDto> wrappedData = StreamUtil.toMappedList(r -> {
            Long id = resultAsLong(r, 0);
            String status = resultAsString(r, 1);
            LocalDate start = resultAsLocalDate(r, 2);
            LocalDate end = resultAsLocalDate(r, 3);
            Boolean isHigher = resultAsBoolean(r, 4);
            return new TimetableManagementSearchDto(id, status, start, end, isHigher);
        }, data);

        StudyPeriod sp = em.getReference(StudyPeriod.class, criteria.getStudyPeriod());
        wrappedData = addMissingDatesToBlocked(sp, wrappedData);

        int start = pageable.getOffset();
        int end = (start + pageable.getPageSize()) > wrappedData.size() ? wrappedData.size()
                : (start + pageable.getPageSize());
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
        wrappedData = wrappedData.stream().filter(wrapped -> wrapped.getStart().isAfter(timetable.getStartDate())).collect(Collectors.toList());
        Collections.sort(wrappedData, (dto1, dto2) -> dto1.getStart().compareTo(dto2.getStart()));
        return wrappedData;
    }

    private List<TimetableManagementSearchDto> addMissingDatesToBlocked(StudyPeriod sp,
            List<TimetableManagementSearchDto> data) {
        LocalDate currentStart = sp.getStartDate();
        LocalDate currentEnd = sp.getStartDate();
        LocalDate spEnd = sp.getEndDate();
        Classifier tunniplaanStaatusA = classifierRepository.getOne(TimetableStatus.TUNNIPLAAN_STAATUS_A.name());
        List<TimetableManagementSearchDto> toAdd = new ArrayList<>();

        Collections.sort(data, (dto1, dto2) -> dto1.getStart().compareTo(dto2.getStart()));

        for (TimetableManagementSearchDto dto : data) {
            // TODO: put the while into a function , both whiles are same
            while (currentStart.isBefore(dto.getStart())) {
                currentEnd = currentStart.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
                currentEnd = currentEnd.isBefore(dto.getStart()) ? currentEnd : dto.getStart().minusDays(1);
                toAdd.add(
                        new TimetableManagementSearchDto(null, tunniplaanStaatusA.getCode(), currentStart, currentEnd, Boolean.FALSE, EntityUtil.getId(sp)));
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
                        new TimetableManagementSearchDto(null, tunniplaanStaatusA.getCode(), currentStart, currentEnd, Boolean.FALSE, EntityUtil.getId(sp)));
                currentStart = currentStart.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            }
        }

        // TODO: add sort to a function
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
        timetable.setStatus(classifierRepository.getOne(TimetableStatus.TUNNIPLAAN_STAATUS_S.name()));
        EntityUtil.bindToEntity(form, timetable);
        timetable.setStudyPeriod(em.getReference(StudyPeriod.class, form.getStudyPeriod()));
        return timetableRepository.save(timetable);
    }

    public Timetable save(HoisUserDetails user, TimetableEditForm form, Timetable timetable) {
        EntityUtil.bindToEntity(form, timetable);
        timetable.setIsHigher(Boolean.valueOf(TimetableType.isHigher(form.getCode())));
        timetable.setSchool(em.getReference(School.class, user.getSchoolId()));
        timetable.setStatus(classifierRepository.getOne(TimetableStatus.TUNNIPLAAN_STAATUS_S.name()));
        timetable.setStudyPeriod(em.getReference(StudyPeriod.class, form.getStudyPeriod()));
        return timetableRepository.save(timetable);
    }

    private List<?> getVocationalStudentGroupsUnformatted(Timetable timetable, Long journalId) {
        String from = "from lesson_plan lp" + " inner join curriculum_version cv on cv.id = lp.curriculum_version_id"
                + " inner join curriculum c on c.id = cv.curriculum_id"
                + " inner join student_group sg on sg.id = lp.student_group_id";
        if (journalId != null) {
            from += " inner join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id"
                    + " inner join journal_omodule_theme jot on jot.lesson_plan_module_id = lpm.id"
                    + " inner join journal j on j.id = jot.journal_id";
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

    private List<TimetableStudentGroupDto> getVocationalStudentGroups(Timetable timetable) {
        List<?> data = getVocationalStudentGroupsUnformatted(timetable, null);
        List<TimetableStudentGroupDto> groups = StreamUtil.toMappedList(
                r -> (new TimetableStudentGroupDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsLong(r, 4))),
                data);
        return groups;
    }

    private List<HigherTimetableStudentGroupDto> getHigherStudentGroups(Timetable timetable) {
        String from = "from student_group sg inner join subject_study_period_student_group sspsg on sspsg.student_group_id = sg.id"
                + " inner join subject_study_period ssp on ssp.id = sspsg.subject_study_period_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from).sort(new Sort(Direction.ASC, "sg.code"));
        qb.requiredCriteria("ssp.study_period_id = :subjectStudyPeriod", "subjectStudyPeriod",
                EntityUtil.getId(timetable.getStudyPeriod()));

        String select = "distinct sg.id, sg.code";
        List<?> data = qb.select(select, em).getResultList();
        return StreamUtil
                .toMappedList(r -> new HigherTimetableStudentGroupDto(resultAsLong(r, 0), resultAsString(r, 1), null), data);
    }
    
    private List<TimetableCurriculumDto> getHigherStudentGroupsByCurriculum(Timetable timetable) {
        String from = "from student_group sg inner join subject_study_period_student_group sspsg on sspsg.student_group_id = sg.id"
                + " inner join subject_study_period ssp on ssp.id = sspsg.subject_study_period_id"
                + " inner join curriculum c on c.id = sg.curriculum_id";
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
        return curriculumsById.values().stream().collect(Collectors.toList());
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
                + " inner join subject_study_period ssp on ssp.id = sspt.subject_study_period_id"
                + " inner join subject s on s.id = ssp.subject_id"
                + " inner join teacher tea on tea.id = sspt.teacher_id"
                + " inner join person p on p.id = tea.person_id");

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
                + " inner join subject_study_period ssp on ssp.id = sspc.subject_study_period_id "
                + " left join subject_study_period_student_group sspsg on sspsg.subject_study_period_id = ssp.id"
                + " inner join subject s on s.id = ssp.subject_id");
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

        String select = "sspsg.student_group_id as student_group_id, sspc.capacity_type_code, sspc.hours, s.code, s.name_et, ssp.id as subject_study_period_id";
        List<?> data = qb.select(select, em).getResultList();
        List<HigherTimetableStudentGroupCapacityDto> capacities = StreamUtil
                .toMappedList(
                        r -> new HigherTimetableStudentGroupCapacityDto(resultAsLong(r, 0), resultAsLong(r, 2),
                                resultAsString(r, 1), resultAsString(r, 3), resultAsString(r, 4), resultAsLong(r, 5)),
                        data);

        Map<Long, List<String>> teachersBySsps = getTeachersForSubjectStudyPeriods(capacities.stream().map(r -> r.getSubjectStudyPeriod())
                .collect(Collectors.toSet()));
        // key = subject_study_period.id + _ + student_group.id + _ + capacity_type
        Map<String, Long> plannedLessonsBySspSgCt = getPlannedTotalsForHigherStudentGroups(EntityUtil.getId(timetable));
        
        for(HigherTimetableStudentGroupCapacityDto dto : capacities) {
            dto.setTeacherNames(teachersBySsps.get(dto.getSubjectStudyPeriod()));
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
                + " inner join timetable_event te on te.id = tet.timetable_event_id"
                + " inner join timetable_object too on too.id = te.timetable_object_id"
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
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j" + " inner join journal_capacity jc on jc.journal_id = j.id"
                + " inner join journal_omodule_theme jot on jot.journal_id = j.id"
                + " inner join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
                + " inner join lesson_plan lp on lp.id = lpm.lesson_plan_id"
                + " inner join journal_capacity_type jct on jct.id = jc.journal_capacity_type_id");

        qb.requiredCriteria("lp.study_year_id = :studyYearId", "studyYearId",
                EntityUtil.getId(timetable.getStudyPeriod().getStudyYear()));
        qb.filter("lp.is_usable = true");
        qb.requiredCriteria("lp.student_group_id in (:studentGroupIds)", "studentGroupIds", studentGroupIds);
        qb.groupBy("j.id, lp.student_group_id, jct.id");
        String subselect = "(select sum(jc.hours)"
                + " from journal_capacity jc where jc.journal_id = j.id and jc.journal_capacity_type_id = jct.id and "
                + "jc.week_nr in (" + currentWeekNr + "))";
        qb.filter(subselect + " is not null");
        String select = "j.id as journal_id, sum(jc.hours) as total_hours, lp.student_group_id, " + subselect
                + " as current_week_hours, jct.capacity_type_code";
        List<?> data = qb.select(select, em).getResultList();

        Map<Long, Map<String, AllocatedLessons>> allocatedCapacities = getAllocatedLessonsForByJournalByStudentGroupAndCapacity(
                timetable);
        List<TimetableStudentGroupCapacityDto> result = StreamUtil
                .toMappedList(r -> new TimetableStudentGroupCapacityDto(resultAsLong(r, 2), resultAsLong(r, 0),
                        resultAsLong(r, 1), resultAsLong(r, 3), resultAsString(r, 4)), data);

        for (TimetableStudentGroupCapacityDto dto : result) {
            Map<String, AllocatedLessons> groupAllocatedLessons = allocatedCapacities.get(dto.getJournal());
            if (groupAllocatedLessons != null) {
                AllocatedLessons currentLessons = groupAllocatedLessons
                        .get(dto.getStudentGroup() + "/" + dto.getCapacityType());
                if (currentLessons != null) {
                    dto.setTotalAllocatedLessons(currentLessons.getTotalAllocated());
                    dto.setLessonsLeft(Long.valueOf(dto.getThisPlannedLessons().longValue()
                            - currentLessons.getCurrentWeekAllocated().longValue()));
                    dto.setCapacityType(currentLessons.getCapacityType());
                }
            }
        }
        return result;
    }

    private Map<Long, Map<String, AllocatedLessons>> getAllocatedLessonsForByJournalByStudentGroupAndCapacity(
            Timetable timetable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event te INNER JOIN timetable_object too"
                + " ON too.id = te.timetable_object_id INNER JOIN timetable_object_student_group tsog"
                + " ON tsog.timetable_object_id = too.id");

        qb.requiredCriteria("too.timetable_id = :timetableId", "timetableId", EntityUtil.getId(timetable));

        qb.groupBy(
                "too.journal_id, tsog.student_group_id, too.id, too.timetable_id, te.capacity_type_code having count(te) > 0");

        String select = "tsog.student_group_id, too.journal_id, too.id, too.timetable_id, count(te) as current_week_allocated"
                + ", (select count(te2) from timetable_event te2 inner join timetable_object too2 on too2.id = te2.timetable_object_id"
                + " inner join timetable_object_student_group tsog2 on tsog2.timetable_object_id = too2.id where too2.journal_id = too.journal_id"
                + " and tsog2.student_group_id = tsog.student_group_id and te2.capacity_type_code = te.capacity_type_code) as total_allocated"
                + ", te.capacity_type_code";

        List<?> data = qb.select(select, em).getResultList();

        return data.stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 1), Collectors.toMap(
                        r -> resultAsLong(r, 0).toString() + "/" + resultAsString(r, 6),
                        r -> new AllocatedLessons(resultAsLong(r, 4), resultAsLong(r, 5), resultAsString(r, 6)))));
    }

    private List<TimetableJournalDto> getJournalsForPlanning(List<Long> studentGroupIds, Timetable timetable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from journal j inner join journal_omodule_theme jot on jot.journal_id = j.id"
                + " inner join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
                + " inner join lesson_plan lp on lp.id = lpm.lesson_plan_id");

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
                    "from journal_teacher jt inner join teacher t on t.id = jt.teacher_id inner join person p on p.id = t.person_id");
            qb.requiredCriteria("jt.journal_id in (:journalIds)", "journalIds",
                    journals.stream().map(TimetableJournalDto::getId).collect(Collectors.toList()));
            select = "jt.journal_id, p.firstName, p.lastName";
            data = qb.select(select, em).getResultList();
            Map<Long, List<String>> teachersByJournals = data.stream()
                    .collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                            Collectors.mapping(r -> PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)),
                                    Collectors.toList())));
            for (TimetableJournalDto journal : journals) {
                List<String> teacherNames = teachersByJournals.get(journal.getId());
                if (teacherNames != null) {
                    journal.getTeacherNames().addAll(teacherNames);
                }
            }
        }

        return journals;
    }

    private List<LessonTimeDto> getLessonTimesForPlanning(Timetable timetable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from lesson_time lt"
                + " inner join lesson_time_building_group ltbg on ltbg.id = lt.lesson_time_building_group_id"
                + " inner join lesson_time_building ltb on ltb.lesson_time_building_group_id = ltbg.id");

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

    private List<TimetableEventDto> getPlannedLessonsForVocationalTimetable(Timetable timetable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event te" + " inner join timetable_object too on too.id = te.timetable_object_id"
                + " inner join timetable_object_student_group tosg on tosg.timetable_object_id = too.id"
                + " inner join timetable_event_time tet on tet.timetable_event_id = te.id");

        qb.requiredCriteria("too.timetable_id = :timetableId", "timetableId", EntityUtil.getId(timetable));

        String select = "tet.id, tet.start, tet.end, te.lesson_nr, te.capacity_type_code, too.journal_id, tosg.student_group_id";
        List<?> data = qb.select(select, em).getResultList();

        List<TimetableEventDto> result = StreamUtil.toMappedList(
                r -> new TimetableEventDto(resultAsLong(r, 0), resultAsLocalDateTime(r, 1), resultAsLocalDateTime(r, 2),
                        resultAsInteger(r, 3), resultAsString(r, 4), resultAsLong(r, 5), resultAsLong(r, 6)),
                data);

        if (!result.isEmpty()) {
            result = addRoomsListToEvents(result);
        }

        return result;
    }

    private List<TimetableEventDto> addRoomsListToEvents(List<TimetableEventDto> result) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event_room ter inner join timetable_event_time tet on tet.id = ter.timetable_event_time_id"
                + " inner join room r on r.id = ter.room_id inner join building b on b.id = r.building_id").sort("r.code");

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

    public List<GeneralTimetableDto> generalTimetables(Long schoolId) {
        StudyYear studyYear = studyYearService.getCurrentStudyYear(schoolId);

        if (studyYear != null) {
            Query q = em.createNativeQuery("select tt.id, tt.start_date, tt.end_date, tt.study_period_id, sp.name_et, sp.name_en"
                    + " from timetable tt" + " join study_period sp on tt.study_period_id=sp.id"
                    + " where tt.school_id=?1 and sp.study_year_id=?2" + " order by 2");
            q.setParameter(1, schoolId);
            q.setParameter(2, studyYear.getId());

            List<?> data = q.getResultList();
            return StreamUtil.toMappedList(r -> new GeneralTimetableDto((Object[]) r), data);
        }
        return null;
    }

    public List<GroupTimetableDto> groupPeriodTimetables(Long schoolId, Long studyPeriodId, Long timetableId) {
        Query q = em.createNativeQuery("select distinct sg.id, sg.code from student_group sg"
                + " inner join timetable_object_student_group tosg on sg.id=tosg.student_group_id"
                + " inner join timetable_object tobj on tosg.timetable_object_id=tobj.id"
                + " inner join timetable tt on tobj.timetable_id=tt.id"
                + " inner join study_period sp on tt.study_period_id=sp.id"
                + " where tt.school_id=?1 and sp.id=?2 and tt.id=?3 order by 2");
        q.setParameter(1, schoolId);
        q.setParameter(2, studyPeriodId);
        q.setParameter(3, timetableId);
        
        List<?> data = q.getResultList();
        return StreamUtil.toMappedList(r -> new GroupTimetableDto((Object[]) r), data);
    }

    public List<TeacherTimetableDto> teacherPeriodTimetables(Long schoolId, Long studyPeriodId, Long timetableId) {
        Query q = em.createNativeQuery("select distinct t.id, p.firstname, p.lastname from teacher t"
                + " inner join person p on t.person_id = p.id"
                + " inner join journal_teacher jt on t.id = jt.teacher_id"
                + " inner join journal j on jt.journal_id = j.id"
                + " inner join timetable_object tobj on j.id = tobj.journal_id"
                + " inner join timetable tt on tt.id = tobj.timetable_id"
                + " where tt.school_id=?1 and tt.study_period_id=?2 and tt.id=?3 "
                + " union"
                + " select distinct t.id, p.firstname, p.lastname from teacher t"
                + " inner join person p on t.person_id = p.id"
                + " inner join subject_study_period_teacher sspp on sspp.teacher_id=t.id"
                + " inner join subject_study_period ssp on ssp.id=sspp.subject_study_period_id"
                + " inner join timetable_object tobj on tobj.subject_study_period_id=ssp.id"
                + " inner join timetable tt on tt.id=tobj.timetable_id"
                + " where tt.school_id=?1 and tt.study_period_id=?2 and tt.id=?3"
                + " order by lastname");
        q.setParameter(1, schoolId);
        q.setParameter(2, studyPeriodId);
        q.setParameter(3, timetableId);
        
        List<?> data = q.getResultList();
        return StreamUtil.toMappedList(r -> new TeacherTimetableDto((Object[])r), data);
    }

    public List<RoomTimetableDto> roomPeriodTimetables(Long schoolId, Long studyPeriodId, Long timetableId) {
        Query q = em.createNativeQuery("select distinct r.id, r.code from room r"
                + " inner join timetable_event_room ter on r.id=ter.room_id"
                + " inner join timetable_event_time tet on ter.timetable_event_time_id=tet.id"
                + " inner join timetable_event te on tet.timetable_event_id=te.id"
                + " inner join timetable_object tobj on te.timetable_object_id=tobj.id"
                + " inner join timetable tt on tobj.timetable_id=tt.id"
                + " where tt.school_id=?1 and tt.study_period_id=?2 and tt.id=?3"
                + " order by r.code");
        q.setParameter(1, schoolId);
        q.setParameter(2, studyPeriodId);
        q.setParameter(3, timetableId);
        
        List<?> data = q.getResultList();
        return StreamUtil.toMappedList(r -> new RoomTimetableDto((Object[])r), data);
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
}
