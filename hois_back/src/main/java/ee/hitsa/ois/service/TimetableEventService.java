package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.JpaQueryUtil;
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

    public Page<TimetableEventSearchDto> search(TimetableEventSearchCommand criteria,
            Pageable pageable) {
        String from = "from timetable_event_time tet"
                + " inner join timetable_event te on tet.timetable_event_id = te.id"
                + " inner join timetable_object to on te.timetable_object_id = to.id"
                + " inner join timetable_object_student_group tog on to.id = tog.timetable_object_id"
                + " inner join student_group sg on sg.id = tog.student_group_id "
                + " inner join timetable t on to.timetable_id = t.id";
        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(from).sort(pageable);

        qb.requiredCriteria("tet.consider_break = :singleEvent", "singleEvent", criteria.getSingleEvent().toString());

        qb.optionalCriteria("t.studyPeriod_id = :studyPeriod", "studyPeriod", criteria.getStudyPeriod());
        qb.optionalCriteria("tog.student_group_id in (:studentGroup)", "studentGroup", criteria.getStudentGroups());
        qb.optionalCriteria("te.name = :name", "name", criteria.getName());
        qb.optionalCriteria("tet.start >= :studyPeriod", "studyPeriod", criteria.getFrom());
        qb.optionalCriteria("tet.end <= :studyPeriod", "studyPeriod", criteria.getThru());
        qb.optionalCriteria("tog.student_group_id in (:studentGroup)", "studentGroup", criteria.getStudentGroups());

        if (criteria.getTeachers() != null && !criteria.getTeachers().isEmpty()) {
            String teacherQuery = String.format(
                    " (select t.timetable_event_id from timetable_event_teacher t where t.teacher_id in (%s)",
                    criteria.getTeachers().toString().replace("[", "").replace("]", "").trim());
            qb.filter("tet.id in" + teacherQuery);
        }
        if (criteria.getRoom() != null) {
            String roomQuery = String.format(
                    " (select r.timetable_event_id from timetable_event_room r where r.room_id = %d)",
                    criteria.getRoom());
            qb.filter("tet.id in" + roomQuery);
        }

        qb.optionalCriteria("tet.other_teacher = :otherTeacher", "otherTeacher", criteria.getOtherTeacher());
        qb.optionalCriteria("tet.other_room = :otherRoom", "otherRoom", criteria.getOtherRoom());

        String select = "te.name, tet.start, sg.code, tet.consider_break";
        return JpaQueryUtil.pagingResult(qb, select, em, pageable).map(r -> {
            return new TimetableEventSearchDto(resultAsString(r, 0), resultAsLocalDateTime(r, 1).toLocalDate(),
                    resultAsLocalDateTime(r, 1).toLocalTime(), resultAsString(r, 2), resultAsBoolean(r, 3));
        });
    }

}
