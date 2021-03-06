package ee.hitsa.ois.services;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.WsAdLog;
import ee.hitsa.ois.enums.DirectiveStatus;
import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.api.StudentCommand;
import ee.hitsa.ois.web.commandobject.api.TimetableStudentCommand;
import ee.hitsa.ois.web.dto.api.ExmatStudentsDto;
import ee.hitsa.ois.web.dto.api.StudentBaseDto;
import ee.hitsa.ois.web.dto.api.StudentDto;
import ee.hitsa.ois.web.dto.api.StudentsDto;
import ee.hitsa.ois.web.dto.api.TimetableEventDto;
import ee.hitsa.ois.web.dto.api.TimetableEventRoomDto;
import ee.hitsa.ois.web.dto.api.TimetableEventTeacherDto;
import ee.hitsa.ois.web.dto.api.TimetableEventsDto;

@Transactional
@Service
public class StudentApiService {
	
	@Autowired
    private EntityManager em;
	
	public StudentsDto immat(String schoolEhisValue, StudentCommand command) {
		JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
		        + "join school sch on sch.id = s.school_id "
		        + "join classifier ehis_c on ehis_c.code = sch.ehis_school_code "
				+ "join person p on s.person_id = p.id "
				+ "left join curriculum_version cv on cv.id = s.curriculum_version_id "
				+ "left join curriculum c on cv.curriculum_id = c.id "
				+ "left join (select distinct on(cd.curriculum_id) sd.id, sd.name_et, sd.name_en, cd.curriculum_id "
				        + "from school_department sd "
                        + "join curriculum_department cd on cd.school_department_id = sd.id "
                        + "order by cd.curriculum_id, sd.name_et) CD on CD.curriculum_id = cv.curriculum_id and c.is_higher = true "
				+ "left join school_department sd on sd.id = cv.school_department_id and c.is_higher = false");

        qb.requiredCriteria("s.status_code in (:activeStatuses)", "activeStatuses", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.requiredCriteria("exists(select d.id from directive d "
        		+ "join directive_student ds on ds.directive_id = d.id "
        		+ "where ds.student_id = s.id "
        		+ "and d.status_code = :directiveConfirmedStatus "
        		+ "and d.type_code in (:directiveTypes) "
        		+ "and ds.canceled is not true "
        		+ "and d.confirm_date >= :startDate "
        		+ (command.getStartThru() != null ? "and d.confirm_date <= :endDate " : "")
        		+ ")", "startDate", command.getStartFrom());
        
        qb.requiredCriteria("ehis_c.ehis_value = :ehisValue", "ehisValue", schoolEhisValue);
        qb.parameter("directiveConfirmedStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
        qb.parameter("directiveTypes", EnumUtil.toNameList(DirectiveType.KASKKIRI_EKSTERN, DirectiveType.KASKKIRI_ENNIST, DirectiveType.KASKKIRI_IMMAT, 
        		DirectiveType.KASKKIRI_IMMATV, DirectiveType.KASKKIRI_KYLALIS));
        if (command.getStartThru() != null) {
        	qb.parameter("endDate" , command.getStartThru());
        }
        qb.sort("s.inserted");
        StudentsDto dto = new StudentsDto();
        List<?> data = qb.select("p.firstname, p.lastname, p.idcode, p.unique_code, p.birthdate, "
        		+ "c.mer_code, c.name_et, cv.code, right(s.type_code, 1), s.email as studentEmail, p.email as personalEmail, "
        		+ "p.phone, coalesce(sd.name_et, CD.name_et) as departmentEt", em).getResultList();
        List<StudentDto> students = StreamUtil.toMappedList(r -> {
        	StudentDto student = new StudentDto();
        	student.setFirstname(resultAsString(r, 0));
        	student.setLastname(resultAsString(r, 1));
        	student.setIdcode(resultAsString(r, 2));
        	if (student.getIdcode() == null) {
                student.setUqcode(resultAsString(r, 3));
                LocalDate birthDate = resultAsLocalDate(r, 4);
                if (birthDate != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    String formattedBirthDate = birthDate.format(formatter);
                    student.setBirthdate(formattedBirthDate);
                }
            }
        	student.setCurriculumMerCode(resultAsString(r, 5));
        	student.setCurriculumNameEt(resultAsString(r, 6));
        	student.setCurriculumVersionCode(resultAsString(r, 7));
        	student.setType(resultAsString(r, 8));
        	student.setEmail(resultAsString(r, 9));
        	student.setPersonalEmail(resultAsString(r, 10));
        	student.setPhone(resultAsString(r, 11));
        	student.setSchoolDepartment(resultAsString(r, 12));
            return student;
        }, data);
        dto.setStudents(students);
        return dto;
    }
	
	public ExmatStudentsDto exmat(String schoolEhisValue, StudentCommand command) {
		JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
		        + "join school sch on sch.id = s.school_id "
                + "join classifier ehis_c on ehis_c.code = sch.ehis_school_code "
				+ "join person p on s.person_id = p.id "
				+ "left join curriculum_version cv on cv.id = s.curriculum_version_id "
				+ "left join curriculum c on cv.curriculum_id = c.id");

        qb.requiredCriteria("s.status_code in (:inactiveStatuses)", "inactiveStatuses", EnumUtil.toNameList(StudentStatus.OPPURSTAATUS_K, StudentStatus.OPPURSTAATUS_L));
        qb.requiredCriteria("ehis_c.ehis_value = :ehisValue", "ehisValue", schoolEhisValue);
        qb.requiredCriteria("exists(select d.id from directive d "
        		+ "join directive_student ds on ds.directive_id = d.id "
        		+ "where ds.student_id = s.id "
        		+ "and d.status_code = :directiveConfirmedStatus "
        		+ "and d.type_code in (:exmatDirectiveTypes) "
        		+ "and ds.canceled is not true "
        		+ "and ((d.confirm_date >= :startDate and d.type_code != :guestDirective) "
        			+ "or (ds.end_date >= :startDate and d.type_code = :guestDirective)) "
        		+ (command.getStartThru() != null ? "and ((d.confirm_date <= :endDate and d.type_code != :guestDirective) "
            			+ "or (ds.end_date <= :endDate and d.type_code = :guestDirective)) " : "")
        		+ ")", "startDate", command.getStartFrom());
        
        // same person is not actively studying on a different curriculum
        qb.filter("not exists(select s1.id from student s1 "
                + "where s1.person_id = p.id "
                + "and s1.id != s.id "
                + "and s1.status_code in (:activeStatuses))");
        
        qb.parameter("activeStatuses", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.parameter("guestDirective", DirectiveType.KASKKIRI_KYLALIS.name());
        qb.parameter("directiveConfirmedStatus", DirectiveStatus.KASKKIRI_STAATUS_KINNITATUD.name());
        qb.parameter("exmatDirectiveTypes", EnumUtil.toNameList(DirectiveType.KASKKIRI_EKSMAT, DirectiveType.KASKKIRI_EKSTERNKATK, 
        		DirectiveType.KASKKIRI_LOPET, DirectiveType.KASKKIRI_KYLALIS));
        if (command.getStartThru() != null) {
        	qb.parameter("endDate" , command.getStartThru());
        }
        List<?> data = qb.select("p.firstname, p.lastname, p.idcode, p.unique_code, p.birthdate, "
        		+ "c.mer_code, c.name_et, cv.code, s.email", em).getResultList();
        ExmatStudentsDto dto = new ExmatStudentsDto();
        List<StudentBaseDto> students = StreamUtil.toMappedList(r -> {
        	StudentBaseDto student = new StudentDto();
        	student.setFirstname(resultAsString(r, 0));
        	student.setLastname(resultAsString(r, 1));
        	student.setIdcode(resultAsString(r, 2));
        	if (student.getIdcode() == null) {
        	    student.setUqcode(resultAsString(r, 3));
        	    LocalDate birthDate = resultAsLocalDate(r, 4);
                if (birthDate != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    String formattedBirthDate = birthDate.format(formatter);
                    student.setBirthdate(formattedBirthDate);
                }
        	}
        	student.setCurriculumMerCode(resultAsString(r, 5));
        	student.setCurriculumNameEt(resultAsString(r, 6));
        	student.setCurriculumVersionCode(resultAsString(r, 7));
        	student.setEmail(resultAsString(r, 8));
            return student;
        }, data);
        dto.setStudents(students);
        return dto;
    }

	public TimetableEventsDto timetable(String schoolEhisValue, TimetableStudentCommand command) {
		JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event_time tet "
				+ "join timetable_event te on te.id = tet.timetable_event_id "
				+ "join timetable_object t_o on t_o.id = te.timetable_object_id "
				+ "join timetable t on t_o.timetable_id = t.id "
				+ "join school sch on sch.id = t.school_id "
                + "join classifier ehis_c on ehis_c.code = sch.ehis_school_code ");
		
		qb.requiredCriteria("ehis_c.ehis_value = :ehisValue", "ehisValue", schoolEhisValue);
		qb.requiredCriteria("tet.start <= :endDate" , "endDate", command.getStartThru());
		qb.requiredCriteria("tet.end >= :startDate" , "startDate", command.getStartFrom());
		if (command.getRoomName() != null) {
			qb.filter("exists(select r1.id from timetable_event_room ter1"
				+ " join room r1 on r1.id = ter1.room_id "
				+ " join building b on b.id = r1.building_id "
				+ "where ter1.timetable_event_time_id = tet.id "
				+ "and upper(concat(b.code, '-', r1.code)) like '%" + command.getRoomName().toUpperCase() + "%')");
		}
		qb.sort("tet.start");
		List<?> data = qb.select("tet.id, te.name, tet.start, tet.end, t_o.journal_id, t_o.subject_study_period_id", em).getResultList();
		List<Long> timetableEventTimes = StreamUtil.toMappedList(r -> resultAsLong(r, 0), data);
	    Map<Long, List<TimetableEventTeacherDto>> timetableEventTeachers = !timetableEventTimes.isEmpty() ? getTimetableEventTeachers(timetableEventTimes) : null;
	    Map<Long, List<TimetableEventRoomDto>> timetableEventRooms = !timetableEventTimes.isEmpty() ? getTimetableEventRooms(timetableEventTimes, command.getRoomName()) : null;
		TimetableEventsDto dto = new TimetableEventsDto();
		List<TimetableEventDto> timetableEvents = StreamUtil.toMappedList(r -> {
			TimetableEventDto event = new TimetableEventDto();
			event.setNameEt(resultAsString(r, 1));
			event.setEventStart(DateUtils.toISOString(resultAsLocalDateTime(r, 2)));
			event.setEventEnd(DateUtils.toISOString(resultAsLocalDateTime(r, 3)));
			event.setJournalId(resultAsLong(r, 4));
			event.setSubjectStudyPeriodId(resultAsLong(r, 5));
		    event.setTeachers(timetableEventTeachers.get(resultAsLong(r, 0)));
            event.setRooms(timetableEventRooms.get(resultAsLong(r, 0)));
            return event;
        }, data);
        dto.setTimetableEvents(timetableEvents);
        return dto;
	}
	
	private Map<Long, List<TimetableEventRoomDto>> getTimetableEventRooms(List<Long> timetableEventTimes, String roomName) {
		JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event_time tet"
		        + " left join (select r.id, concat(b.code, '-', r.code) as name, ter.timetable_event_time_id "
		            + " from timetable_event_room ter"
		            + " join room r on r.id = ter.room_id"
		            + " join building b on r.building_id = b.id"
		            + ") R1 on R1.timetable_event_time_id = tet.id"
		            + (roomName != null ? " and upper(R1.name) like '%" + roomName.toUpperCase() + "%'" : ""));
		qb.optionalContains("upper(R1.name)", "roomName", roomName != null ? roomName.toUpperCase() : null);
		qb.requiredCriteria("tet.id in (:timetableEventTimes)", "timetableEventTimes", timetableEventTimes);
		qb.filter("(R1.id is not null or tet.other_room is not null)");
		List<?> data = qb.select("distinct tet.id, R1.id as roomId, R1.name", em).getResultList();
        return StreamUtil.nullSafeList(data).stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> new TimetableEventRoomDto(resultAsLong(r, 1), resultAsString(r, 2)), Collectors.toList())));
	}

	private Map<Long, List<TimetableEventTeacherDto>> getTimetableEventTeachers(List<Long> timetableEventTimes) {
		JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from timetable_event_time tetim "
		        + " left join timetable_event_teacher tet on tet.timetable_event_time_id = tetim.id"
		        + " left join teacher t on t.id = tet.teacher_id"
		        + " left join person p on p.id = t.person_id");
		
		qb.requiredCriteria("tetim.id in (:timetableEventTimes)", "timetableEventTimes", timetableEventTimes);
		qb.filter("(t.id is not null or tetim.other_teacher is not null)");
		List<?> data = qb.select("distinct tetim.id, t.id as teacherId, p.firstname, "
		        + "case when t.id is not null then p.lastname else tetim.other_teacher end", em).getResultList();
        return StreamUtil.nullSafeList(data).stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0),
                Collectors.mapping(r -> new TimetableEventTeacherDto(resultAsLong(r, 1), resultAsString(r, 2), resultAsString(r, 3)), Collectors.toList())));
	}
	
	public void saveLog(WsAdLog log) {
        em.persist(log);
    }

}
