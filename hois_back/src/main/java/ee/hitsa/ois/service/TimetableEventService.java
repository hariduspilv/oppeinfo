package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

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

import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.TeacherAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.timetable.TimetableEventSearchCommand;
import ee.hitsa.ois.web.dto.timetable.GeneralTimetableCurriculumDto;
import ee.hitsa.ois.web.dto.timetable.GeneralTimetableDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByGroupDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByRoomDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByStudentDto;
import ee.hitsa.ois.web.dto.timetable.TimetableByTeacherDto;
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

    public TimetableByGroupDto groupTimetableForWeek(Long studentGroupId, Long timetableId) {
        TimetableEventSearchCommand command = new TimetableEventSearchCommand();
        command.setStudentGroups(Arrays.asList(studentGroupId));
        command.setTimetable(timetableId);

        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(command);
        String select = "tet.id, coalesce(te.name, j.name_et, subj.name_et) as name_et, coalesce(subj.name_en, te.name, j.name_et) as name_en,"
                + " tet.start, tet.end, te.consider_break";
        qb.sort("tet.start,tet.end");
        List<?> eventResult = qb.select(select, em).getResultList();
        List<TimetableEventSearchDto> eventResultList = StreamUtil
                .toMappedList(r -> new TimetableEventSearchDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2),
                        resultAsLocalDateTime(r, 3).toLocalDate(), resultAsLocalDateTime(r, 3).toLocalTime(), resultAsLocalDateTime(r, 4).toLocalTime(),
                        resultAsBoolean(r, 5)), eventResult);
        setRoomsTeachersAndGroupsForSearchDto(eventResultList);
        
        GeneralTimetableCurriculumDto generalTimetableCurriculum = getGeneralTimetableCurriculum(studentGroupId);
        GeneralTimetableDto generalTimetable = getGeneralTimetable(timetableId);
        
        return new TimetableByGroupDto(generalTimetableCurriculum, generalTimetable, eventResultList);
    }
    
    public TimetableByTeacherDto teacherTimetableForWeek(Long teacherId, Long timetableId) {
        TimetableEventSearchCommand command = new TimetableEventSearchCommand();
        command.setTimetable(timetableId);
        command.setTeachers(Arrays.asList(teacherId));

        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(command);
        String select = "distinct tet.id, coalesce(te.name, j.name_et, subj.name_et) as name_et, coalesce(subj.name_en, te.name, j.name_et) as name_en,"
                + " tet.start, tet.end, te.consider_break";
        qb.sort("tet.start,tet.end");

        List<?> eventResult = qb.select(select, em).getResultList();
        List<TimetableEventSearchDto> eventResultList = StreamUtil
                .toMappedList(r -> new TimetableEventSearchDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2),
                        resultAsLocalDateTime(r, 3).toLocalDate(), resultAsLocalDateTime(r, 3).toLocalTime(), resultAsLocalDateTime(r, 4).toLocalTime(),
                        resultAsBoolean(r, 5)), eventResult);
        setRoomsTeachersAndGroupsForSearchDto(eventResultList);

        GeneralTimetableDto generalTimetable = getGeneralTimetable(timetableId);

        Query q = em.createNativeQuery("select t.id, p.firstname, p.lastname from teacher t inner join person p on t.person_id=p.id where t.id=?1");
        q.setParameter(1, teacherId);
        Object teacher = q.getSingleResult();

        return new TimetableByTeacherDto(resultAsLong(teacher, 0), resultAsString(teacher, 1), resultAsString(teacher, 2), generalTimetable, eventResultList);
    }
    
    public TimetableByStudentDto studentTimetableForWeek(Long studentId, Long timetableId) {
        TimetableEventSearchCommand command = new TimetableEventSearchCommand();
        command.setTimetable(timetableId);
        command.setStudent(studentId);

        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(command);
        String select = "distinct tet.id, coalesce(te.name, j.name_et, subj.name_et) as name_et, coalesce(subj.name_en, te.name, j.name_et) as name_en,"
                + " tet.start, tet.end, te.consider_break";
        qb.sort("tet.start,tet.end");

        List<?> eventResult = qb.select(select, em).getResultList();
        List<TimetableEventSearchDto> eventResultList = StreamUtil
                .toMappedList(r -> new TimetableEventSearchDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2),
                        resultAsLocalDateTime(r, 3).toLocalDate(), resultAsLocalDateTime(r, 3).toLocalTime(), resultAsLocalDateTime(r, 4).toLocalTime(),
                        resultAsBoolean(r, 5)), eventResult);
        setRoomsTeachersAndGroupsForSearchDto(eventResultList);

        GeneralTimetableDto generalTimetable = getGeneralTimetable(timetableId);

        Query studentQuery = em.createNativeQuery("select s.id, p.firstname, p.lastname from student s inner join person p on s.person_id=p.id where s.id=?1");
        studentQuery.setParameter(1, studentId);
        Object student = studentQuery.getSingleResult();

        return new TimetableByStudentDto(resultAsLong(student, 0), resultAsString(student, 1), resultAsString(student, 2), generalTimetable, eventResultList);
    }
    
    public TimetableByRoomDto roomTimetableForWeek(Long roomId, Long timetableId) {
        TimetableEventSearchCommand command = new TimetableEventSearchCommand();
        command.setTimetable(timetableId);
        command.setRoom(roomId);

        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(command);
        String select = "distinct tet.id, coalesce(te.name, j.name_et, subj.name_et) as nameEt, coalesce(subj.name_en, te.name, j.name_et) as nameEn,"
                + " tet.start, tet.end, te.consider_break";
        qb.sort("tet.start,tet.end");

        List<?> eventResult = qb.select(select, em).getResultList();
        List<TimetableEventSearchDto> eventResultList = StreamUtil
                .toMappedList(r -> new TimetableEventSearchDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2),
                        resultAsLocalDateTime(r, 3).toLocalDate(), resultAsLocalDateTime(r, 3).toLocalTime(), resultAsLocalDateTime(r, 4).toLocalTime(),
                        resultAsBoolean(r, 5)), eventResult);
        setRoomsTeachersAndGroupsForSearchDto(eventResultList);
        
        GeneralTimetableDto generalTimetable = getGeneralTimetable(timetableId);
        
        Query q = em.createNativeQuery("select r.id, r.code as roomCode, b.code as buildingCode from room r inner join building b on r.building_id=b.id where r.id=?1");
        q.setParameter(1, roomId);
        Object room = q.getSingleResult();
        
        return new TimetableByRoomDto(resultAsLong(room, 0), resultAsString(room, 1), resultAsString(room, 2), generalTimetable, eventResultList);
    }
    
    private GeneralTimetableDto getGeneralTimetable(Long timetableId) {
        Query generalTimetableQuery = em.createNativeQuery("select tt.id, tt.start_date, tt.end_date, tt.study_period_id, sp.name_et, sp.name_en"
                + " from timetable tt join study_period sp on tt.study_period_id=sp.id where tt.id =?1");
        generalTimetableQuery.setParameter(1, timetableId);
        Object generalTimetableresult = generalTimetableQuery.getSingleResult();
        
        return new GeneralTimetableDto((Object[]) generalTimetableresult);
    }
    
    private GeneralTimetableCurriculumDto getGeneralTimetableCurriculum(Long studentGroupId) {
        Query generalTimetableCurriculumQuery = em.createNativeQuery("select sg.code as sgCode, cv.code as cvCode, c.name_et, c.name_en from student_group sg"
                + " left join curriculum_version cv on sg.curriculum_version_id=cv.id"
                + " inner join curriculum c on sg.curriculum_id=c.id where sg.id=?1");
        generalTimetableCurriculumQuery.setParameter(1, studentGroupId);
        Object generalTimetableCurriculumResult = generalTimetableCurriculumQuery.getSingleResult();
        
        return new GeneralTimetableCurriculumDto((Object[]) generalTimetableCurriculumResult);
    }

    private static JpaNativeQueryBuilder getTimetableEventTimeQuery(TimetableEventSearchCommand criteria) {
        String from = "from timetable_event_time tet"
                + " inner join timetable_event te on tet.timetable_event_id = te.id"
                + " inner join timetable_object tobj on te.timetable_object_id = tobj.id"
                + " inner join timetable_object_student_group tog on tobj.id = tog.timetable_object_id"
                + " inner join student_group sg on sg.id = tog.student_group_id "
                + " inner join timetable t on tobj.timetable_id = t.id"
                + " left join journal j on tobj.journal_id = j.id"
                + " left join subject_study_period ssp on ssp.id = tobj.subject_study_period_id"
                + " left join subject subj on subj.id = ssp.subject_id";
                
        if (criteria.getStudent() != null) {
            from += " left join journal_student js on js.journal_id=j.id"
                    + " left join student s on s.id=js.student_id";
        }
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.optionalCriteria("te.consider_break = :singleEvent", "singleEvent", criteria.getSingleEvent());
        qb.optionalCriteria("t.study_period_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        qb.optionalCriteria("tog.student_group_id in (:studentGroup)", "studentGroup", criteria.getStudentGroups());
        qb.optionalCriteria("te.name = :name", "name", criteria.getName());
        qb.optionalCriteria("tet.start >= :start", "start", criteria.getFrom());
        qb.optionalCriteria("tet.end <= :end", "end", criteria.getThru());
        qb.optionalCriteria("t.id = :timetable", "timetable", criteria.getTimetable());
        qb.optionalCriteria("s.id = :student", "student", criteria.getStudent());

        //TODO: param query
        if (criteria.getTeachers() != null && !criteria.getTeachers().isEmpty()) {
            String teacherQueryFromEvent = String.format(
                    " (select teteach.timetable_event_time_id from timetable_event_teacher teteach where teteach.teacher_id in (%s))",
                    criteria.getTeachers().stream().map(r -> r.toString()).collect(Collectors.joining(", ")));
            String teacherQueryFromJournal = String.format(
                    " (select count(jt.*) from journal_teacher jt where jt.journal_id=j.id and jt.teacher_id in (%s))",
                    criteria.getTeachers().stream().map(r -> r.toString()).collect(Collectors.joining(", ")));
            String teacherQueryFromStudyPeriod = String.format(
                    " (select count(sspt.*) from subject_study_period_teacher sspt where sspt.subject_study_period_id=ssp.id and sspt.teacher_id in (%s))",
                    criteria.getTeachers().stream().map(r -> r.toString()).collect(Collectors.joining(", ")));
            qb.filter("(tet.id in" + teacherQueryFromEvent + " or " + teacherQueryFromJournal + " > 0 or " + teacherQueryFromStudyPeriod + " > 0)");
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
                        StreamUtil.toMappedList(r -> new TimetableEventSearchTeacherDto(r.getObjectId(), r.getValue()), teachersByTimetableEventTime.get(dto.getId())));
                dto.setRooms(
                        StreamUtil.toMappedList(r -> new TimetableEventSearchRoomDto(r.getObjectId(), r.getValue()), roomsByTimetableEventTime.get(dto.getId())));
                dto.setStudentGroups(
                        StreamUtil.toMappedList(r -> new TimetableEventSearchGroupDto(r.getObjectId(), r.getValue()), groupsByTimetableEventTime.get(dto.getId())));
            }
        }
    }

    public Page<TimetableEventSearchDto> search(TimetableEventSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = getTimetableEventTimeQuery(criteria);
        qb.sort(pageable);
        // TODO: name_en null ? 
        String select = "tet.id, te.name_et, null, tet.start, tet.end, te.consider_break";
        Page<TimetableEventSearchDto> result = JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new TimetableEventSearchDto(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2),
                    resultAsLocalDateTime(r, 3).toLocalDate(), resultAsLocalDateTime(r, 4).toLocalTime(), resultAsLocalDateTime(r, 5).toLocalTime(),
                    resultAsBoolean(r, 6));

        });
        setRoomsTeachersAndGroupsForSearchDto(result.getContent());
        return result;
    }

    private Map<Long, List<ResultObject>> getTeachersByTimetableEventTime(List<Long> tetIds) {
        String from = "from timetable_event_time tem " + 
        		"JOIN timetable_event te on tem.timetable_event_id=te.id " + 
        		"join timetable_object tob on te.timetable_object_id=tob.id " + 
        		"left join journal jj on tob.journal_id=jj.id " + 
        		"left join journal_teacher jt on jj.id=jt.journal_id " + 
        		"left join timetable_event_teacher tet on tem.id=tet.timetable_event_time_id " + 
        	    "left join subject_study_period ssp on ssp.id=tob.subject_study_period_id " +
        	    "left join subject_study_period_teacher sspt on sspt.subject_study_period_id=ssp.id " +
        		"inner join teacher t  on t.id = COALESCE(tet.teacher_id, jt.teacher_id, sspt.teacher_id) " +
        		"inner join person p on p.id = t.person_id  ";
        // TODO add ordering by teacher name
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.requiredCriteria("tem.id in (:tetIds)", "tetIds", tetIds);

        List<?> queryResult = qb.select("tem.id, t.id as teacherId, p.firstname, p.lastname", em).getResultList();
        List<ResultObject> resultObjects = StreamUtil.toMappedList(
                r -> new ResultObject(resultAsLong(r, 0), resultAsLong(r, 1), PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3))),
                queryResult);
        return resultObjects.stream().collect(Collectors.groupingBy(r -> r.getTimetableEventId()));
    }

    private Map<Long, List<ResultObject>> getRoomsByTimetableEventTime(List<Long> tetIds) {
        String from = "from timetable_event_room ter" + " inner join room r on r.id = ter.room_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.requiredCriteria("ter.timetable_event_time_id in (:tetIds)", "tetIds", tetIds);

        List<?> queryResult = qb.select("ter.timetable_event_time_id, r.id as roomId, r.code", em).getResultList();
        List<ResultObject> resultObjects = StreamUtil
                .toMappedList(r -> new ResultObject(resultAsLong(r, 0), resultAsLong(r, 1), resultAsString(r, 2)), queryResult);
        return resultObjects.stream().collect(Collectors.groupingBy(r -> r.getTimetableEventId()));
    }
    
    private Map<Long, List<ResultObject>> getGroupsByTimetableEventTime(List<Long> tetIds) {
        String from ="from timetable_event_time tem"
                + " inner join timetable_event te on tem.timetable_event_id = te.id"
                + " inner join timetable_object tobj on te.timetable_object_id = tobj.id"
                + " inner join timetable_object_student_group tog on tobj.id = tog.timetable_object_id"
                + " inner join student_group sg on sg.id = tog.student_group_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        
        qb.requiredCriteria("tem.id in (:tetIds)", "tetIds", tetIds);
        
        List<?> queryResult = qb.select("tem.id, sg.id as studentGroupId, sg.code", em).getResultList();
        List<ResultObject> resultObjects = StreamUtil
                .toMappedList(r -> new ResultObject(resultAsLong(r, 0), resultAsLong(r, 1), resultAsString(r, 2)), queryResult);
        return resultObjects.stream().collect(Collectors.groupingBy(r -> r.getTimetableEventId()));
    }

    private static class ResultObject {
        private Long timetableEventId;
        private Long objectId;
        private String value;

        public ResultObject(Long tetId, Long objectId, String value) {
            this.timetableEventId = tetId;
            this.objectId = objectId;
            this.value = value;
        }

        public Long getTimetableEventId() {
            return timetableEventId;
        }
        
        public Long getObjectId() {
            return objectId;
        }

        public String getValue() {
            return value;
        }
    }

}
