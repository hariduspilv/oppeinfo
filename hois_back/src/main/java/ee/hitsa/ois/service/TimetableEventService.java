package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

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

import ee.hitsa.ois.util.JpaQueryUtil;
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

    public Page<TimetableEventSearchDto> search(TimetableEventSearchCommand criteria, Pageable pageable) {
        String from = "from timetable_event_time tet"
                + " inner join timetable_event te on tet.timetable_event_id = te.id"
                + " inner join timetable_object tobj on te.timetable_object_id = tobj.id"
                + " inner join timetable_object_student_group tog on tobj.id = tog.timetable_object_id"
                + " inner join student_group sg on sg.id = tog.student_group_id "
                + " inner join timetable t on tobj.timetable_id = t.id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from).sort(pageable);

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

        String select = "tet.id, te.name, tet.start, sg.code, te.consider_break";
        Page<TimetableEventSearchDto> result = JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new TimetableEventSearchDto(resultAsLong(r, 0), resultAsString(r, 1),
                    resultAsLocalDateTime(r, 2).toLocalDate(), resultAsLocalDateTime(r, 2).toLocalTime(),
                    resultAsString(r, 3), resultAsBoolean(r, 4));
        });

        List<Long> timetableEventTimeIds = StreamUtil.toMappedList(r -> r.getId(), result.getContent());
        if (!timetableEventTimeIds.isEmpty()) {
            Map<Long, List<ResultObject>> teacherNamesByTimetableEventTime = getTeacherNamesByTimetableEventTime(
                    timetableEventTimeIds);
            Map<Long, List<ResultObject>> roomCodesByTimetableEventTime = getRoomNrsByTimetableEventTime(
                    timetableEventTimeIds);

            for (TimetableEventSearchDto dto : result.getContent()) {
                dto.setTeachers(
                        StreamUtil.toMappedList(r -> r.getValue(), teacherNamesByTimetableEventTime.get(dto.getId())));
                dto.setRooms(
                        StreamUtil.toMappedList(r -> r.getValue(), roomCodesByTimetableEventTime.get(dto.getId())));
            }
        }

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
