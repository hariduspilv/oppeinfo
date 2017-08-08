package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;

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
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.LessonTime;
import ee.hitsa.ois.domain.timetable.Timetable;
import ee.hitsa.ois.domain.timetable.TimetableEvent;
import ee.hitsa.ois.domain.timetable.TimetableEventTime;
import ee.hitsa.ois.domain.timetable.TimetableObject;
import ee.hitsa.ois.domain.timetable.TimetableObjectStudentGroup;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.enums.TimetableStatus;
import ee.hitsa.ois.enums.TimetableType;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.LessonTimeRepository;
import ee.hitsa.ois.repository.TimetableEventRepository;
import ee.hitsa.ois.repository.TimetableObjectRepository;
import ee.hitsa.ois.repository.TimetableRepository;
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEditForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventForm;
import ee.hitsa.ois.web.commandobject.timetable.TimetableManagementSearchCommand;
import ee.hitsa.ois.web.dto.timetable.TimetableCurriculumDto;
import ee.hitsa.ois.web.dto.timetable.TimetableStudentGroupDto;
import ee.hitsa.ois.web.dto.timetable.LessonTimeDto;
import ee.hitsa.ois.web.dto.timetable.SubjectTeacherPairDto;
import ee.hitsa.ois.web.dto.timetable.TimetableDatesDto;
import ee.hitsa.ois.web.dto.timetable.TimetableDto;
import ee.hitsa.ois.web.dto.timetable.TimetableEventDto;
import ee.hitsa.ois.web.dto.timetable.TimetableJournalDto;
import ee.hitsa.ois.web.dto.timetable.TimetableManagementSearchDto;
import ee.hitsa.ois.web.dto.timetable.TimetablePlanDto;
import ee.hitsa.ois.web.dto.timetable.TimetableStudentGroupCapacityDto;

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
    private LessonTimeRepository lessonTimeRepository;
    @Autowired
    private SubjectStudyPeriodService subjectStudyPeriodService;
    @Autowired
    private TimetableEventRepository timetableEventRepository;

    public TimetableDto get(HoisUserDetails user, Timetable timetable) {
        TimetableDto dto = new TimetableDto();
        dto.setId(EntityUtil.getId(timetable));
        dto.setStudyYears(autocompleteService.studyYears(user.getSchoolId()));
        dto.setStudyPeriods(autocompleteService.studyPeriodsWithYear(user.getSchoolId()));
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
        dto.setJournals(getJournalsForPlanning(StreamUtil.toMappedList(r -> (r.getId()), dto.getStudentGroups()), timetable));
        dto.setLessonTimes(getLessonTimesForPlanning(timetable));
        dto.setPlannedLessons(getPlannedLessonsForTimetable(timetable));
        return dto;
    }

    public Map<String, Object> managementSearchFormData(Long schoolId) {
        Map<String, Object> data = new HashMap<>();
        data.put("studyYears", autocompleteService.studyYears(schoolId));
        data.put("studyPeriods", autocompleteService.studyPeriodsWithYear(schoolId));
        data.put("currentStudyPeriod", studyYearService.getCurrentStudyPeriod(schoolId));
        SchoolType type = schoolService.schoolType(schoolId);
        data.put("higher", Boolean.valueOf(type.isHigher()));
        data.put("vocational", Boolean.valueOf(type.isVocational()));
        return data;
    }
    
    public void saveEvent(TimetableEventForm form, HoisUserDetails user) {
        Timetable timetable = em.getReference(Timetable.class, form.getTimetable());
        List<TimetableStudentGroupDto> studentGroups = getStudentGroups(timetable);
        Journal journal = em.getReference(Journal.class, form.getJournal());
        TimetableObject timetableObject = saveTimetableObject(timetable, journal, studentGroups);
        LessonTime lessonTime = lessonTimeRepository.getOne(form.getLessonTime());
        TimetableEvent timetableEvent = saveTimetableEvent(timetableObject, lessonTime, form);
        TimetableEventTime timetableEventTime = saveTimetableEventTime(timetableEvent);
        timetableRepository.save(timetable);
        
    }
    
    private TimetableEvent saveTimetableEvent(TimetableObject timetableObject, LessonTime lessonTime, TimetableEventForm form) {
        TimetableEvent timetableEvent = new TimetableEvent();
        LocalDate start = form.getSelectedDay() == timetableObject.getTimetable().getStartDate().getDayOfWeek()
                ? timetableObject.getTimetable().getStartDate()
                : timetableObject.getTimetable().getStartDate().with(TemporalAdjusters.next(form.getSelectedDay()));
        timetableEvent.setStart(start.atTime(lessonTime.getStartTime()));
        timetableEvent.setEnd(start.atTime(lessonTime.getEndTime()));
        timetableEvent.setRepeatCode(classifierRepository.getOne("TUNNIPLAAN_SYNDMUS_KORDUS_EI"));
        timetableEvent.setLessonNr(lessonTime.getLessonNr());
        timetableObject.getTimetableEvents().add(timetableEvent);
        return timetableEvent;
    }
    
    private TimetableEventTime saveTimetableEventTime(TimetableEvent timetableEvent) {
        TimetableEventTime timetableEventTime = new TimetableEventTime();
        timetableEventTime.setTimetableEvent(timetableEvent);
        timetableEventTime.setStart(timetableEvent.getStart());
        timetableEventTime.setEnd(timetableEvent.getEnd());
        timetableEvent.getTimetableEventTimes().add(timetableEventTime);
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
        timetable.setTimetableObjects(objects);
        timetableRepository.save(timetable);
        return timetableObject;
    }
    
    private void bindObjectGroups(TimetableObject timetableObject, List<TimetableStudentGroupDto> studentGroups) {
        Map<Long, TimetableObjectStudentGroup> oldValues = StreamUtil.toMap(it -> EntityUtil.getId(it.getStudentGroup()), timetableObject.getTimetableObjectStudentGroups());
        for(TimetableStudentGroupDto newGroup : studentGroups) {
            if(oldValues.get(newGroup.getId()) == null) {
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
    
    private List<?> getStudentGroupsUnformatted(Timetable timetable) {
        String from = "from lesson_plan lp" + " inner join curriculum_version cv on cv.id = lp.curriculum_version_id"
                + " inner join curriculum c on c.id = cv.curriculum_id"
                + " inner join student_group sg on sg.id = lp.student_group_id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);
        qb.requiredCriteria("lp.school_id = :schoolId", "schoolId", EntityUtil.getId(timetable.getSchool()));
        qb.requiredCriteria("lp.study_year_id = :studyYearId", "studyYearId",
                EntityUtil.getId(timetable.getStudyPeriod().getStudyYear()));
        qb.optionalCriteria("lp.is_usable = :isUsable", "isUsable", Boolean.TRUE);
        qb.optionalCriteria("c.is_higher = :isHigher", "isHigher", timetable.getIsHigher());

        String select = "lp.student_group_id, sg.code as student_group_code"
                + ", c.name_et, c.name_en, cv.id, cv.code as curriculum_version_code, c.orig_study_level_code";
        return qb.select(select, em).getResultList();
    }
    
    private List<TimetableStudentGroupDto> getStudentGroups(Timetable timetable) {
        List<?> data = getStudentGroupsUnformatted(timetable);
        List<TimetableStudentGroupDto> groups = StreamUtil.toMappedList(
                r -> (new TimetableStudentGroupDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsLong(r, 4))),
                data);
        return groups;
    }
    
    private List<TimetableCurriculumDto> getStudentGroupsByCurriculum(Timetable timetable) {
        List<?> data = getStudentGroupsUnformatted(timetable);
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
                resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 4), resultAsString(r, 5))), data);
    }

    private List<TimetableStudentGroupCapacityDto> getCapacitiesForPlanning(List<Long> studentGroupIds,
            Timetable timetable) {
        Integer currentWeekNr = timetable.getStudyPeriod().getWeekNrForDate(timetable.getStartDate());
        String from = "from journal j" + " inner join journal_capacity jc on jc.journal_id = j.id"
                + " inner join journal_omodule_theme jot on jot.journal_id = j.id"
                + " inner join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
                + " inner join lesson_plan lp on lp.id = lpm.lesson_plan_id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);
        qb.requiredCriteria("lp.study_year_id = :studyYearId", "studyYearId",
                EntityUtil.getId(timetable.getStudyPeriod().getStudyYear()));
        qb.optionalCriteria("lp.is_usable = :isUsable", "isUsable", Boolean.TRUE);
        qb.requiredCriteria("lp.student_group_id in (:studentGroupIds)", "studentGroupIds", studentGroupIds);
        qb.groupBy("j.id, lp.student_group_id");
        String select = "j.id as journal_id, sum(jc.hours) as total_hours, lp.student_group_id, (select sum(jc.hours) from journal_capacity jc where jc.journal_id = j.id and jc.week_nr in (" + currentWeekNr + "))";
        List<?> data = qb.select(select, em).getResultList();
        
        /*
         * query et saada journalite ja gruppide kaupa allocated tundide arv
SELECT
    tsog.student_group_id,
    too.journal_id,
    too.id,
    too.timetable_id,
    COUNT(te) AS current_week_allocated,
    (
        SELECT
            COUNT(te2)
        FROM
            timetable_event te2 INNER JOIN timetable_object too2
                ON too2.id = te2.timetable_object_id INNER JOIN timetable_object_student_group tsog2
                ON tsog2.timetable_object_id = too2.id
        WHERE
            too2.journal_id = too.journal_id
            AND tsog2.student_group_id = tsog.student_group_id
    ) as total_allocated
FROM
    timetable_event te INNER JOIN timetable_object too
        ON too.id = te.timetable_object_id INNER JOIN timetable_object_student_group tsog
        ON tsog.timetable_object_id = too.id
WHERE
    too.timetable_id = 34
GROUP BY
    too.journal_id,
    tsog.student_group_id,
    too.id,
    too.timetable_id;
         */
        
        return StreamUtil.toMappedList(r -> (new TimetableStudentGroupCapacityDto(resultAsLong(r, 2), resultAsLong(r, 0), resultAsLong(r, 1), resultAsLong(r, 3))), data);
    }
    
    private List<TimetableJournalDto> getJournalsForPlanning(List<Long> studentGroupIds, Timetable timetable) {
        String from = "from journal j"
                + " inner join journal_omodule_theme jot on jot.journal_id = j.id"
                + " inner join lesson_plan_module lpm on lpm.id = jot.lesson_plan_module_id"
                + " inner join lesson_plan lp on lp.id = lpm.lesson_plan_id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);
        qb.requiredCriteria("lp.study_year_id = :studyYearId", "studyYearId",
                EntityUtil.getId(timetable.getStudyPeriod().getStudyYear()));
        qb.optionalCriteria("lp.is_usable = :isUsable", "isUsable", Boolean.TRUE);
        qb.requiredCriteria("lp.student_group_id in (:studentGroupIds)", "studentGroupIds", studentGroupIds);
        String select = "distinct j.id as journal_id, j.name_et";
        List<?> data = qb.select(select, em).getResultList();
        return StreamUtil.toMappedList(r -> (new TimetableJournalDto(resultAsLong(r, 0), resultAsString(r, 1))), data);
    }
    
    private List<LessonTimeDto> getLessonTimesForPlanning(Timetable timetable) {
        String from = "from lesson_time lt"
                + " inner join lesson_time_building_group ltbg on ltbg.id = lt.lesson_time_building_group_id"
                + " inner join lesson_time_building ltb on ltb.lesson_time_building_group_id = ltbg.id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);
        qb.requiredCriteria("lt.school_id = :schoolId", "schoolId", EntityUtil.getId(timetable.getSchool()));
        qb.requiredCriteria("ltbg.valid_from <= :timetableStartDate", "timetableStartDate", timetable.getStartDate());
        qb.filter("(ltbg.valid_thru >= :timetableStartDate or ltbg.valid_thru is null)");
        
        String select = "distinct lt.id, lt.start_time, lt.end_time, lt.lesson_nr, lt.day_mon, lt.day_tue, lt.day_wed"
                + " ,lt.day_thu, lt.day_fri, lt.day_sat, lt.day_sun";
        List<?> data = qb.select(select, em).getResultList();
        
        return StreamUtil.toMappedList(r -> (new LessonTimeDto(resultAsLong(r, 0), resultAsLocalTime(r, 1), resultAsLocalTime(r, 2), resultAsInteger(r, 3).intValue()
                , resultAsBoolean(r, 4), resultAsBoolean(r, 5), resultAsBoolean(r, 6), resultAsBoolean(r, 7), resultAsBoolean(r, 8), resultAsBoolean(r, 9), resultAsBoolean(r, 10))), data);
    }
    
    private List<TimetableEventDto> getPlannedLessonsForTimetable(Timetable timetable) {
        String from = "from timetable_event te" + " inner join timetable_object too on too.id = te.timetable_object_id"
                + " inner join timetable_object_student_group tosg on tosg.timetable_object_id = too.id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);
        qb.requiredCriteria("too.timetable_id = :timetableId", "timetableId", EntityUtil.getId(timetable));

        String select = "te.id, te.start, te.end, te.lesson_nr, too.journal_id, tosg.student_group_id";
        List<?> data = qb.select(select, em).getResultList();

        return StreamUtil.toMappedList(r -> new TimetableEventDto(resultAsLong(r, 0), resultAsLocalDateTime(r, 1),
                resultAsLocalDateTime(r, 2), resultAsInteger(r, 3), null, resultAsLong(r, 4), resultAsLong(r, 5)), data);
    }

}
