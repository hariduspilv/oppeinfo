package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.JpaQueryUtil.NativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.TeacherAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventSearchCommand;
import ee.hitsa.ois.web.dto.timetable.TimetableEventSearchDto;

@Transactional
@Service
public class TimetableEventService {

    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private EntityManager em;

    public Map<String, ?> searchFormData(Long schoolId) {
        Map<String, Object> data = new HashMap<>();
        data.put("studyPeriods", autocompleteService.studyPeriods(schoolId));
        data.put("studentGroups", autocompleteService.studentGroups(schoolId, Boolean.TRUE, Boolean.FALSE));
        TeacherAutocompleteCommand teacherLookup = new TeacherAutocompleteCommand();
        teacherLookup.setValid(Boolean.TRUE);
        data.put("teachers", autocompleteService.teachers(schoolId, teacherLookup));
        data.put("singleEvent", Boolean.TRUE);

        return data;
    }

    public List<TimetableEventSearchDto> getTimetableForWeek(Long studyPeriodId, Long studentGroupId, Long weekNr) {
        TimetableEventSearchCommand command = new TimetableEventSearchCommand();
        command.setStudentGroups(Arrays.asList(studentGroupId));
        LocalDate start = getStartDateForStudyPeriod(studyPeriodId, weekNr);

        command.setFrom(start);
        command.setThru(start.plusDays(6));

        NativeQueryBuilder qb = getTimetableEventTimeQuery(command);
        String select = "tet.id, te.name, tet.start, sg.code, te.consider_break";
        List<?> result = qb.select(select, em).getResultList();
        List<TimetableEventSearchDto> resultList = StreamUtil
                .toMappedList(r -> new TimetableEventSearchDto(resultAsLong(r, 0), resultAsString(r, 1),
                        resultAsLocalDateTime(r, 2).toLocalDate(), resultAsLocalDateTime(r, 2).toLocalTime(),
                        resultAsString(r, 3), resultAsBoolean(r, 4)), result);
        setRoomsAndTeachersForSearchDto(resultList);

        return resultList;
    }

    private LocalDate getStartDateForStudyPeriod(Long studyPeriodId, Long weekNr) {
        StudyPeriod studyPeriod = em.getReference(StudyPeriod.class, studyPeriodId);
        LocalDate start;
        if (weekNr != null) {
            start = studyPeriod.getStudyYear().getWeekBeginningDate(weekNr);
        } else {
            start = LocalDate.now();
            if (start.getDayOfWeek() != DayOfWeek.MONDAY) {
                start = start.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
            }
        }
        return start;
    }

    private static NativeQueryBuilder getTimetableEventTimeQuery(TimetableEventSearchCommand criteria) {
        String from = "from timetable_event_time tet"
                + " inner join timetable_event te on tet.timetable_event_id = te.id"
                + " inner join timetable_object tobj on te.timetable_object_id = tobj.id"
                + " inner join timetable_object_student_group tog on tobj.id = tog.timetable_object_id"
                + " inner join student_group sg on sg.id = tog.student_group_id "
                + " inner join timetable t on tobj.timetable_id = t.id";
        NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);

        qb.optionalCriteria("te.consider_break = :singleEvent", "singleEvent", criteria.getSingleEvent());
        qb.optionalCriteria("t.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        qb.optionalCriteria("tog.student_group_id in (:studentGroup)", "studentGroup", criteria.getStudentGroups());
        qb.optionalCriteria("te.name = :name", "name", criteria.getName());
        qb.optionalCriteria("tet.start >= :start", "start", criteria.getFrom());
        qb.optionalCriteria("tet.end <= :end", "end", criteria.getThru());
        qb.optionalCriteria("tog.student_group_id in (:studentGroup)", "studentGroup", criteria.getStudentGroups());

        if (criteria.getTeachers() != null && !criteria.getTeachers().isEmpty()) {
            String teacherQuery = String.format(
                    " (select teteach.timetable_event_time_id from timetable_event_teacher teteach where teteach.teacher_id in (%s))",
                    criteria.getTeachers().stream().map(r -> r.toString()).collect(Collectors.joining(", ")));
            qb.filter("tet.id in" + teacherQuery);
        }
        if (criteria.getRoom() != null) {
            String roomQuery = String.format(
                    " (select r.timetable_event_time_id from timetable_event_room r where r.room_id = %d)",
                    criteria.getRoom());
            qb.filter("tet.id in" + roomQuery);
        }

        qb.optionalCriteria("tet.other_teacher = :otherTeacher", "otherTeacher", criteria.getOtherTeacher());
        qb.optionalCriteria("tet.other_room = :otherRoom", "otherRoom", criteria.getOtherRoom());
        return qb;
    }

    private void setRoomsAndTeachersForSearchDto(List<TimetableEventSearchDto> timetableEventTimes) {
        List<Long> timetableEventTimeIds = StreamUtil.toMappedList(r -> r.getId(), timetableEventTimes);
        if (!timetableEventTimeIds.isEmpty()) {
            Map<Long, List<ResultObject>> teacherNamesByTimetableEventTime = getTeacherNamesByTimetableEventTime(
                    timetableEventTimeIds);
            Map<Long, List<ResultObject>> roomCodesByTimetableEventTime = getRoomNrsByTimetableEventTime(
                    timetableEventTimeIds);

            for (TimetableEventSearchDto dto : timetableEventTimes) {
                dto.setTeachers(
                        StreamUtil.toMappedList(r -> r.getValue(), teacherNamesByTimetableEventTime.get(dto.getId())));
                dto.setRooms(
                        StreamUtil.toMappedList(r -> r.getValue(), roomCodesByTimetableEventTime.get(dto.getId())));
            }
        }
    }

    public Page<TimetableEventSearchDto> search(TimetableEventSearchCommand criteria, Pageable pageable) {
        NativeQueryBuilder qb = getTimetableEventTimeQuery(criteria);
        qb.sort(pageable);
        String select = "tet.id, te.name, tet.start, sg.code, te.consider_break";
        Page<TimetableEventSearchDto> result = JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new TimetableEventSearchDto(resultAsLong(r, 0), resultAsString(r, 1),
                    resultAsLocalDateTime(r, 2).toLocalDate(), resultAsLocalDateTime(r, 2).toLocalTime(),
                    resultAsString(r, 3), resultAsBoolean(r, 4));
        });
        setRoomsAndTeachersForSearchDto(result.getContent());
        return result;
    }

    private Map<Long, List<ResultObject>> getTeacherNamesByTimetableEventTime(List<Long> tetIds) {
        String from = "timetable_event_teacher tet" + " inner join teacher t on t.id = teteacher.teacher_id"
                + " inner join person p on p.id = t.person_id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);

        qb.requiredCriteria("teteacher.timetable_event_time_id in (:tetIds)", "tetIds", tetIds);

        List<?> queryResult = qb.select("tet.timetable_event_time_id, p.firstname, p.lastname", em).getResultList();
        List<ResultObject> resultObjects = StreamUtil.toMappedList(
                r -> new ResultObject(resultAsLong(r, 0), resultAsString(r, 1) + " " + resultAsString(r, 2)),
                queryResult);
        return resultObjects.stream().collect(Collectors.groupingBy(r -> r.getTimetableEventId()));
    }

    private Map<Long, List<ResultObject>> getRoomNrsByTimetableEventTime(List<Long> tetIds) {
        String from = "timetable_event_room ter" + " inner join room r on r.id = ter.room_id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);

        qb.requiredCriteria("tet.timetable_event_time_id in (:tetIds)", "tetIds", tetIds);

        List<?> queryResult = qb.select("ter.timetable_event_time_id, r.code", em).getResultList();
        List<ResultObject> resultObjects = StreamUtil
                .toMappedList(r -> new ResultObject(resultAsLong(r, 0), resultAsString(r, 1)), queryResult);
        return resultObjects.stream().collect(Collectors.groupingBy(r -> r.getTimetableEventId()));
    }

    private static class ResultObject {
        private static Long timetableEventId;
        private static String value;

        public ResultObject(Long tetId, String value) {
            ResultObject.timetableEventId = tetId;
            ResultObject.value = value;
        }

        public Long getTimetableEventId() {
            return timetableEventId;
        }

        public String getValue() {
            return value;
        }
    }

}
