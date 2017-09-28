package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
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

import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.JpaQueryUtil.NativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.TeacherAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventSearchCommand;
import ee.hitsa.ois.web.dto.timetable.GeneralTimetableDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByGroupDto;
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

    public TimetableByGroupDto getTimetableForWeek(Long studyPeriodId, Long studentGroupId, Long timetableId) {
        TimetableEventSearchCommand command = new TimetableEventSearchCommand();
        command.setStudentGroups(Arrays.asList(studentGroupId));
        command.setTimetable(timetableId);

        NativeQueryBuilder qb = getTimetableEventTimeQuery(command);
        String select = "tet.id, coalesce(te.name, j.name_et) as name, tet.start, tet.end, sg.code, te.consider_break";
        qb.sort("tet.start,tet.end");
        List<?> eventResult = qb.select(select, em).getResultList();
        List<TimetableEventSearchDto> eventResultList = StreamUtil
                .toMappedList(r -> new TimetableEventSearchDto(resultAsLong(r, 0), resultAsString(r, 1),
                        resultAsLocalDateTime(r, 2).toLocalDate(), resultAsLocalDateTime(r, 2).toLocalTime(), resultAsLocalDateTime(r, 3).toLocalTime(),
                        resultAsString(r, 4), resultAsBoolean(r, 5)), eventResult);
        setRoomsAndTeachersForSearchDto(eventResultList);

        Query q = em.createNativeQuery("select tt.id, tt.start_date, tt.end_date, tt.study_period_id, sp.name_et, sp.name_en"
                + " from timetable tt join study_period sp on tt.study_period_id=sp.id where tt.id =?1");
        q.setParameter(1, timetableId);
        Object generalTimetableresult = q.getSingleResult();
        
        return new TimetableByGroupDto(new GeneralTimetableDto((Object[]) generalTimetableresult), eventResultList);
    }

    private static NativeQueryBuilder getTimetableEventTimeQuery(TimetableEventSearchCommand criteria) {
        String from = "from timetable_event_time tet"
                + " inner join timetable_event te on tet.timetable_event_id = te.id"
                + " inner join timetable_object tobj on te.timetable_object_id = tobj.id"
                + " inner join timetable_object_student_group tog on tobj.id = tog.timetable_object_id"
                + " inner join student_group sg on sg.id = tog.student_group_id "
                + " inner join timetable t on tobj.timetable_id = t.id"
                + " left join journal j on tobj.journal_id = j.id";
        NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);

        qb.optionalCriteria("te.consider_break = :singleEvent", "singleEvent", criteria.getSingleEvent());
        qb.optionalCriteria("t.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        qb.optionalCriteria("tog.student_group_id in (:studentGroup)", "studentGroup", criteria.getStudentGroups());
        qb.optionalCriteria("te.name = :name", "name", criteria.getName());
        qb.optionalCriteria("tet.start >= :start", "start", criteria.getFrom());
        qb.optionalCriteria("tet.end <= :end", "end", criteria.getThru());
        qb.optionalCriteria("t.id = :timetable", "timetable", criteria.getTimetable());

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
        String select = "tet.id, te.name, tet.start, tet.end, sg.code, te.consider_break";
        Page<TimetableEventSearchDto> result = JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new TimetableEventSearchDto(resultAsLong(r, 0), resultAsString(r, 1),
                    resultAsLocalDateTime(r, 2).toLocalDate(), resultAsLocalDateTime(r, 2).toLocalTime(), resultAsLocalDateTime(r, 3).toLocalTime(),
                    resultAsString(r, 4), resultAsBoolean(r, 5));
        });
        setRoomsAndTeachersForSearchDto(result.getContent());
        return result;
    }

    private Map<Long, List<ResultObject>> getTeacherNamesByTimetableEventTime(List<Long> tetIds) {
        String from = "from timetable_event_time tem " + 
        		"JOIN timetable_event te on tem.timetable_event_id=te.id " + 
        		"join timetable_object tob on te.timetable_object_id=tob.id " + 
        		"left join journal jj on tob.journal_id=jj.id " + 
        		"left join journal_teacher jt on jj.id=jt.journal_id " + 
        		"left join timetable_event_teacher tet on tem.id=tet.timetable_event_time_id " + 
        		"inner join teacher t  on t.id = COALESCE(tet.teacher_id ,jt.teacher_id)"
        		+ "inner join person p on p.id = t.person_id  ";
        // TODO add ordering by teacher name
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);

        qb.requiredCriteria("tem.id in (:tetIds)", "tetIds", tetIds);

        List<?> queryResult = qb.select("tem.id, p.firstname, p.lastname", em).getResultList();
        List<ResultObject> resultObjects = StreamUtil.toMappedList(
                r -> new ResultObject(resultAsLong(r, 0), PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2))),
                queryResult);
        return resultObjects.stream().collect(Collectors.groupingBy(r -> r.getTimetableEventId()));
    }

    private Map<Long, List<ResultObject>> getRoomNrsByTimetableEventTime(List<Long> tetIds) {
        String from = "from timetable_event_room ter" + " inner join room r on r.id = ter.room_id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from);

        qb.requiredCriteria("ter.timetable_event_time_id in (:tetIds)", "tetIds", tetIds);

        List<?> queryResult = qb.select("ter.timetable_event_time_id, r.code", em).getResultList();
        List<ResultObject> resultObjects = StreamUtil
                .toMappedList(r -> new ResultObject(resultAsLong(r, 0), resultAsString(r, 1)), queryResult);
        return resultObjects.stream().collect(Collectors.groupingBy(r -> r.getTimetableEventId()));
    }

    private static class ResultObject {
        private Long timetableEventId;
        private String value;

        public ResultObject(Long tetId, String value) {
            this.timetableEventId = tetId;
            this.value = value;
        }

        public Long getTimetableEventId() {
            return timetableEventId;
        }

        public String getValue() {
            return value;
        }
    }

}
