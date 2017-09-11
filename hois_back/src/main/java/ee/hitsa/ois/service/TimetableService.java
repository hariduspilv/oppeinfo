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
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.LessonTime;
import ee.hitsa.ois.domain.timetable.Timetable;
import ee.hitsa.ois.domain.timetable.TimetableEvent;
import ee.hitsa.ois.domain.timetable.TimetableEventRoom;
import ee.hitsa.ois.domain.timetable.TimetableEventTime;
import ee.hitsa.ois.domain.timetable.TimetableObject;
import ee.hitsa.ois.domain.timetable.TimetableObjectStudentGroup;
import ee.hitsa.ois.enums.TimetableStatus;
import ee.hitsa.ois.enums.TimetableType;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.TimetableObjectRepository;
import ee.hitsa.ois.repository.TimetableRepository;
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.TimetableRoomAndTimeForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEditForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableManagementSearchCommand;
import ee.hitsa.ois.web.dto.RoomDto;
import ee.hitsa.ois.web.dto.timetable.LessonTimeDto;
import ee.hitsa.ois.web.dto.timetable.SubjectTeacherPairDto;
import ee.hitsa.ois.web.dto.timetable.TimetableCurriculumDto;
import ee.hitsa.ois.web.dto.timetable.TimetableDatesDto;
import ee.hitsa.ois.web.dto.timetable.TimetableDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventDto;
import ee.hitsa.ois.web.dto.timetable.TimetableJournalDto;
import ee.hitsa.ois.web.dto.timetable.TimetableManagementSearchDto;
import ee.hitsa.ois.web.dto.timetable.TimetablePlanDto;
import ee.hitsa.ois.web.dto.timetable.TimetableStudentGroupCapacityDto;
import ee.hitsa.ois.web.dto.timetable.TimetableStudentGroupDto;

@Transactional
@Service
public class TimetableService {

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
    private SubjectStudyPeriodService subjectStudyPeriodService;

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
        return dto;
    }

    public TimetableDto getForView(Timetable timetable) {
        TimetableDto dto = new TimetableDto();
        dto.setId(EntityUtil.getId(timetable));
        dto.setStartDate(timetable.getStartDate());
        dto.setEndDate(timetable.getEndDate());
        dto.setCurriculums(getStudentGroupsByCurriculum(timetable));
        if (timetable.getIsHigher().booleanValue()) {
            dto.setPairs(getPairsforTimetable(timetable));
        }
        dto.setStatus(timetable.getStatus().getCode());
        return dto;
    }

    public TimetablePlanDto getPlan(Timetable timetable) {
        TimetablePlanDto dto = new TimetablePlanDto();
        dto.setStartDate(timetable.getStartDate());
        dto.setEndDate(timetable.getEndDate());
        dto.setStudentGroups(getStudentGroups(timetable));
        dto.setStudentGroupCapacities(
                getCapacitiesForPlanning(StreamUtil.toMappedList(r -> (r.getId()), dto.getStudentGroups()), timetable));
        dto.setJournals(
                getJournalsForPlanning(StreamUtil.toMappedList(r -> (r.getId()), dto.getStudentGroups()), timetable));
        dto.setLessonTimes(getLessonTimesForPlanning(timetable));
        dto.setPlannedLessons(getPlannedLessonsForTimetable(timetable));
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

    public Timetable saveEvent(TimetableEventForm form) {
        Timetable timetable = em.getReference(Timetable.class, form.getTimetable());
        List<TimetableStudentGroupDto> studentGroups = getStudentGroups(timetable, form.getJournal());
        Journal journal = em.getReference(Journal.class, form.getJournal());
        TimetableObject timetableObject = saveTimetableObject(timetable, journal, studentGroups);
        LessonTime lessonTime = em.getReference(LessonTime.class, form.getLessonTime());
        TimetableEvent timetableEvent = saveTimetableEvent(timetableObject, lessonTime, form);
        /* TimetableEventTime timetableEventTime = */saveTimetableEventTime(timetableEvent);
        timetableRepository.save(timetable);
        return timetable;
    }

    private TimetableEvent saveTimetableEvent(TimetableObject timetableObject, LessonTime lessonTime,
            TimetableEventForm form) {
        TimetableEvent timetableEvent;
        if (form.getOldEventId() != null) {
            timetableEvent = timetableObject.getTimetableEvents().stream().filter(r -> form.getOldEventId().equals(r.getId())).findFirst().get();
        } else {
            timetableEvent = new TimetableEvent();
            timetableObject.getTimetableEvents().add(timetableEvent);
        }
        LocalDate start = form.getSelectedDay() == timetableObject.getTimetable().getStartDate().getDayOfWeek()
                ? timetableObject.getTimetable().getStartDate()
                : timetableObject.getTimetable().getStartDate().with(TemporalAdjusters.next(form.getSelectedDay()));
        timetableEvent.setStart(start.atTime(lessonTime.getStartTime()));
        timetableEvent.setEnd(start.atTime(lessonTime.getEndTime()));
        // TODO use Enum constant
        timetableEvent.setRepeatCode(classifierRepository.getOne("TUNNIPLAAN_SYNDMUS_KORDUS_EI"));
        timetableEvent.setLessonNr(lessonTime.getLessonNr());
        if(form.getCapacityType() != null && !form.getCapacityType().isEmpty()) {
            timetableEvent.setCapacityType(classifierRepository.getOne(form.getCapacityType()));
        }
        return timetableEvent;
    }

    public Timetable saveEventRoomsAndTimes(TimetableRoomAndTimeForm form) {
        TimetableEvent timetableEvent = em.getReference(TimetableEvent.class, form.getTimetableEventId());
        TimetableEventTime timetableEventTime = timetableEvent.getTimetableEventTimes().isEmpty() ? null : timetableEvent.getTimetableEventTimes().get(0);
        if(timetableEventTime != null) {
        /*if(timetableEventTime!= null) {
            List<TimetableEventRoom> rooms = timetableEventTime.getTimetableEventRooms();
            List<Long> storedValues = rooms.stream().map(room -> EntityUtil.getId(room.getRoom())).collect(Collectors.toList());
            for(TimetableEventRoom room : rooms) {
                
            }
        }*/
        List<TimetableEventRoom> oldRooms = timetableEventTime.getTimetableEventRooms();
        EntityUtil.bindEntityCollection(oldRooms, r -> EntityUtil.getId(r.getRoom())
                , form.getRooms(), RoomDto::getId, dto -> {
                    TimetableEventRoom timetableEventRoom = new TimetableEventRoom();
                    timetableEventRoom.setRoom(em.getReference(Room.class, dto.getId()));
                    timetableEventRoom.setTimetableEventTime(timetableEventTime);
                    return timetableEventRoom;
                });
        /*EntityUtil.bindEntityCollection(timetableEventTime.getTimetableEventRooms(), old -> EntityUtil.getId(old.getRoom())
                , form.getRooms(), RoomDto::getId, dto -> {
                    TimetableEventRoom timetableEventRoom = new TimetableEventRoom();
                    EntityUtil.setEntityFromRepository(dto, timetableEventRoom, roomRepository, "id");
                }, null);*/

        //return null;
        }
        Timetable timetable = timetableEvent.getTimetableObject().getTimetable();
        return timetable;
    }
    

    public Timetable deleteEvent(TimetableRoomAndTimeForm form) {
        TimetableEvent timetableEvent = em.getReference(TimetableEvent.class, form.getTimetableEventId());
        Timetable timetable = timetableEvent.getTimetableObject().getTimetable();
        EntityUtil.deleteEntity(timetableEvent, em);
        return timetable;
    }

    private static TimetableEventTime saveTimetableEventTime(TimetableEvent timetableEvent) {
        TimetableEventTime timetableEventTime;
        if (timetableEvent.getTimetableEventTimes().isEmpty()) {
            timetableEventTime = new TimetableEventTime();
            timetableEventTime.setTimetableEvent(timetableEvent);
            timetableEvent.getTimetableEventTimes().add(timetableEventTime);
        } else {
            // only for lower level of education, if is_higher and we want to
            // use the same function it will have to be changed
            timetableEventTime = timetableEvent.getTimetableEventTimes().get(0);
        }
        timetableEventTime.setStart(timetableEvent.getStart());
        timetableEventTime.setEnd(timetableEvent.getEnd());
        return timetableEventTime;
    }

    private TimetableObject saveTimetableObject(Timetable timetable, Journal journal,
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
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from timetable t")
                .sort("t.start_date desc");

        qb.requiredCriteria("t.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("t.is_higher = :isHigher", "isHigher",
                Boolean.valueOf(TimetableType.isHigher(criteria.getType())));

        String select = "t.id, t.status_code, t.start_date, t.end_date";
        return JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new TimetableManagementSearchDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsLocalDate(r, 2),
                    resultAsLocalDate(r, 3));
        });
    }

    private Page<TimetableManagementSearchDto> searchVocationalTimetableForManagement(
            TimetableManagementSearchCommand criteria, Pageable pageable, HoisUserDetails user) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from timetable t")
                .sort("t.start_date desc");

        qb.requiredCriteria("t.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        qb.requiredCriteria("t.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("t.is_higher = :isHigher", "isHigher",
                Boolean.valueOf(TimetableType.isHigher(criteria.getType())));

        String select = "t.id, t.status_code, t.start_date, t.end_date";
        List<?> data = qb.select(select, em).getResultList();

        List<TimetableManagementSearchDto> wrappedData = StreamUtil.toMappedList(r -> {
            Long id = resultAsLong(r, 0);
            String status = resultAsString(r, 1);
            LocalDate start = resultAsLocalDate(r, 2);
            LocalDate end = resultAsLocalDate(r, 3);
            return new TimetableManagementSearchDto(id, status, start, end);
        }, data);

        StudyPeriod sp = em.getReference(StudyPeriod.class, criteria.getStudyPeriod());
        wrappedData = addMissingDatesToBlocked(sp, wrappedData);

        int start = pageable.getOffset();
        int end = (start + pageable.getPageSize()) > wrappedData.size() ? wrappedData.size()
                : (start + pageable.getPageSize());
        return new PageImpl<>(wrappedData.subList(start, end), pageable, wrappedData.size());
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
                        new TimetableManagementSearchDto(null, tunniplaanStaatusA.getCode(), currentStart, currentEnd));
                currentStart = currentStart.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            }
            currentEnd = dto.getEnd().plusDays(1);
            currentStart = currentEnd.getDayOfWeek() == DayOfWeek.MONDAY ? currentEnd
                    : currentEnd.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }
        if (currentEnd.isBefore(spEnd)) {
            while (currentStart.isBefore(spEnd)) {
                currentEnd = currentStart.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
                currentEnd = currentEnd.isBefore(spEnd) ? currentEnd : spEnd;
                toAdd.add(
                        new TimetableManagementSearchDto(null, tunniplaanStaatusA.getCode(), currentStart, currentEnd));
                currentStart = currentStart.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            }
        }

        // TODO: add sort to a function
        Collections.sort(data, (dto1, dto2) -> dto2.getStart().compareTo(dto1.getStart()));
        Collections.sort(toAdd, (dto1, dto2) -> dto2.getStart().compareTo(dto1.getStart()));

        toAdd.addAll(data);
        return toAdd;
    }

    public List<TimetableDatesDto> getBlockedDatesForPeriod(HoisUserDetails user, Long studyPeriod, String code,
            Long currentTimetable) {
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from timetable t");

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

    private List<?> getStudentGroupsUnformatted(Timetable timetable, Long journalId) {
        String from = "from lesson_plan lp" + " inner join curriculum_version cv on cv.id = lp.curriculum_version_id"
                + " inner join curriculum c on c.id = cv.curriculum_id"
                + " inner join student_group sg on sg.id = lp.student_group_id";
        if (journalId != null) {
            from += " inner join lesson_plan_module lpm on lpm.lesson_plan_id = lp.id"
                    + " inner join journal_omodule_theme jot on jot.lesson_plan_module_id = lpm.id"
                    + " inner join journal j on j.id = jot.journal_id";
        }
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);
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

    private List<TimetableStudentGroupDto> getStudentGroups(Timetable timetable) {
        List<?> data = getStudentGroupsUnformatted(timetable, null);
        List<TimetableStudentGroupDto> groups = StreamUtil.toMappedList(
                r -> (new TimetableStudentGroupDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsLong(r, 4))),
                data);
        return groups;
    }

    private List<TimetableStudentGroupDto> getStudentGroups(Timetable timetable, Long journalId) {
        List<?> data = getStudentGroupsUnformatted(timetable, journalId);
        List<TimetableStudentGroupDto> groups = StreamUtil.toMappedList(
                r -> (new TimetableStudentGroupDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsLong(r, 4))),
                data);
        return groups;
    }

    private List<TimetableCurriculumDto> getStudentGroupsByCurriculum(Timetable timetable) {
        List<?> data = getStudentGroupsUnformatted(timetable, null);
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
        List<Long> sspIds = subjectStudyPeriodService.getSubjectStudyPeriodsForSchoolAndPeriod(
                EntityUtil.getId(timetable.getSchool()), EntityUtil.getId(timetable.getStudyPeriod()));
        String from = "from subject_study_period ssp" + " inner join subject s on s.id = ssp.subject_id"
                + " inner join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id"
                + " inner join teacher tea on tea.id = sspt.teacher_id"
                + " inner join person p on p.id = tea.person_id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);
        qb.requiredCriteria("t.id = :timetableId", "timetableId", EntityUtil.getId(timetable));
        qb.requiredCriteria("ssp.id in (:sspIds)", "sspIds", sspIds);
        String select = "ssp.id, s.name_et, s.name_en, s.code, p.firstname, p.lastname";
        List<?> data = qb.select(select, em).getResultList();

        return StreamUtil.toMappedList(r -> (new SubjectTeacherPairDto(resultAsLong(r, 0), resultAsString(r, 1),
                resultAsString(r, 2), resultAsString(r, 3), PersonUtil.fullname(resultAsString(r, 4), resultAsString(r, 5)))), data);
    }

    private List<TimetableStudentGroupCapacityDto> getCapacitiesForPlanning(List<Long> studentGroupIds,
            Timetable timetable) {
        Integer currentWeekNr = timetable.getStudyPeriod().getWeekNrForDate(timetable.getStartDate());
        String from = "from journal j" + " inner join journal_capacity jc on jc.journal_id = j.id"
                + " inner join journal_omodule_theme jot on jot.journal_id = j.id"
                + " inner join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
                + " inner join lesson_plan lp on lp.id = lpm.lesson_plan_id"
                + " inner join journal_capacity_type jct on jct.id = jc.journal_capacity_type_id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);
        qb.requiredCriteria("lp.study_year_id = :studyYearId", "studyYearId",
                EntityUtil.getId(timetable.getStudyPeriod().getStudyYear()));
        qb.filter("lp.is_usable = true");
        qb.requiredCriteria("lp.student_group_id in (:studentGroupIds)", "studentGroupIds", studentGroupIds);
        qb.groupBy("j.id, lp.student_group_id, jct.id");
        String subselect = "(select sum(jc.hours)"
                + " from journal_capacity jc where jc.journal_id = j.id and jc.journal_capacity_type_id = jct.id and "
                + "jc.week_nr in (" + currentWeekNr + "))";
        qb.filter(subselect + " is not null");
        String select = "j.id as journal_id, sum(jc.hours) as total_hours, lp.student_group_id, " 
                + subselect + " as current_week_hours, jct.capacity_type_code";
        List<?> data = qb.select(select, em).getResultList();

        Map<Long, Map<String, AllocatedLessons>> allocatedCapacities = getAllocatedLessonsForByJournalByStudentGroupAndCapacity(
                timetable);
        List<TimetableStudentGroupCapacityDto> result = StreamUtil
                .toMappedList(r -> new TimetableStudentGroupCapacityDto(resultAsLong(r, 2), resultAsLong(r, 0),
                        resultAsLong(r, 1), resultAsLong(r, 3), resultAsString(r, 4)), data);

        for (TimetableStudentGroupCapacityDto dto : result) {
            Map<String, AllocatedLessons> groupAllocatedLessons = allocatedCapacities.get(dto.getJournal());
            if (groupAllocatedLessons != null) {
                AllocatedLessons currentLessons = groupAllocatedLessons.get(dto.getStudentGroup() + "/" + dto.getCapacityType());
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

    private Map<Long, Map<String, AllocatedLessons>> getAllocatedLessonsForByJournalByStudentGroupAndCapacity(Timetable timetable) {
        String from = "from timetable_event te INNER JOIN timetable_object too"
                + " ON too.id = te.timetable_object_id INNER JOIN timetable_object_student_group tsog"
                + " ON tsog.timetable_object_id = too.id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);
        qb.requiredCriteria("too.timetable_id = :timetableId", "timetableId", EntityUtil.getId(timetable));
        

        qb.groupBy("too.journal_id, tsog.student_group_id, too.id, too.timetable_id, te.capacity_type_code having count(te) > 0");

        String select = "tsog.student_group_id, too.journal_id, too.id, too.timetable_id, count(te) as current_week_allocated"
                + ", (select count(te2) from timetable_event te2 inner join timetable_object too2 on too2.id = te2.timetable_object_id"
                + " inner join timetable_object_student_group tsog2 on tsog2.timetable_object_id = too2.id where too2.journal_id = too.journal_id"
                + " and tsog2.student_group_id = tsog.student_group_id and te2.capacity_type_code = te.capacity_type_code) as total_allocated"
                + ", te.capacity_type_code";

        List<?> data = qb.select(select, em).getResultList();

        return data.stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 1), Collectors.toMap(r -> resultAsLong(r, 0).toString() + "/" + resultAsString(r, 6),
                        r -> new AllocatedLessons(resultAsLong(r, 4), resultAsLong(r, 5), resultAsString(r, 6)))));
    }

    private List<TimetableJournalDto> getJournalsForPlanning(List<Long> studentGroupIds, Timetable timetable) {
        String from = "from journal j" + " inner join journal_omodule_theme jot on jot.journal_id = j.id"
                + " inner join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
                + " inner join lesson_plan lp on lp.id = lpm.lesson_plan_id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);
        qb.requiredCriteria("lp.study_year_id = :studyYearId", "studyYearId",
                EntityUtil.getId(timetable.getStudyPeriod().getStudyYear()));
        qb.filter("lp.is_usable = true");
        qb.requiredCriteria("lp.student_group_id in (:studentGroupIds)", "studentGroupIds", studentGroupIds);
        String select = "distinct j.id as journal_id, j.name_et";
        List<?> data = qb.select(select, em).getResultList();
        // Map<Long, TimetableJournalDto> journalsById = StreamUtil.toMap(r ->
        // resultAsLong(r, 0), r -> new TimetableJournalDto(resultAsLong(r, 0),
        // resultAsString(r, 1)), data);
        List<TimetableJournalDto> journals = StreamUtil
                .toMappedList(r -> new TimetableJournalDto(resultAsLong(r, 0), resultAsString(r, 1)), data);
        if(!journals.isEmpty()) {
            // load teachers
            // TODO add order by teacher name
            qb = new JpaQueryUtil.NativeQueryBuilder("from journal_teacher jt inner join teacher t on t.id = jt.teacher_id inner join person p on p.id = t.person_id");
            qb.requiredCriteria("jt.journal_id in (:journalIds)", "journalIds",
                    journals.stream().map(TimetableJournalDto::getId).collect(Collectors.toList()));
            select = "jt.journal_id, p.firstName, p.lastName";
            data = qb.select(select, em).getResultList();
            Map<Long, List<String>> teachersByJournals = data.stream().collect(Collectors.groupingBy(
                    r -> resultAsLong(r, 0),
                    Collectors.mapping(r -> PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)), Collectors.toList())));
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
        String from = "from lesson_time lt"
                + " inner join lesson_time_building_group ltbg on ltbg.id = lt.lesson_time_building_group_id"
                + " inner join lesson_time_building ltb on ltb.lesson_time_building_group_id = ltbg.id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);
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

    private List<TimetableEventDto> getPlannedLessonsForTimetable(Timetable timetable) {
        String from = "from timetable_event te" + " inner join timetable_object too on too.id = te.timetable_object_id"
                + " inner join timetable_object_student_group tosg on tosg.timetable_object_id = too.id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);
        qb.requiredCriteria("too.timetable_id = :timetableId", "timetableId", EntityUtil.getId(timetable));

        String select = "te.id, te.start, te.end, te.lesson_nr, te.capacity_type_code, too.journal_id, tosg.student_group_id";
        List<?> data = qb.select(select, em).getResultList();

        return StreamUtil.toMappedList(
                r -> new TimetableEventDto(resultAsLong(r, 0), resultAsLocalDateTime(r, 1), resultAsLocalDateTime(r, 2),
                        resultAsInteger(r, 3), resultAsString(r, 4), null, resultAsLong(r, 5), resultAsLong(r, 6)),
                data);
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
