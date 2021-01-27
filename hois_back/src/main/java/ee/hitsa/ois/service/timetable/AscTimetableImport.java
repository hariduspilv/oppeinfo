package ee.hitsa.ois.service.timetable;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.timetable.TimetableEventStudent;
import ee.hitsa.ois.enums.StudentStatus;
import org.apache.commons.lang.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.Timetable;
import ee.hitsa.ois.domain.timetable.TimetableEvent;
import ee.hitsa.ois.domain.timetable.TimetableEventRoom;
import ee.hitsa.ois.domain.timetable.TimetableEventStudentGroup;
import ee.hitsa.ois.domain.timetable.TimetableEventTeacher;
import ee.hitsa.ois.domain.timetable.TimetableEventTime;
import ee.hitsa.ois.enums.TimetableEventRepeat;
import ee.hitsa.ois.service.TimetableService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.dto.timetable.TimetableImportDto;
import ee.hitsa.ois.web.dto.timetable.TimetableImportMessage;
import ee.hitsa.ois.xml.timetable.asc.AscCard;
import ee.hitsa.ois.xml.timetable.asc.AscClass;
import ee.hitsa.ois.xml.timetable.asc.AscClassroom;
import ee.hitsa.ois.xml.timetable.asc.AscLesson;
import ee.hitsa.ois.xml.timetable.asc.AscPeriod;
import ee.hitsa.ois.xml.timetable.asc.AscTeacher;
import ee.hitsa.ois.xml.timetable.asc.AscTimetable;

public class AscTimetableImport extends AbstractTimetableImport<AscTimetable, AscCard, TimetableImportMessage> {

    private EntityManager em;
    private HoisUserDetails user;
    private TimetableService tService;

    private Map<String, AscPeriod> mappedPeriods;
    private Map<String, SimpleEntry<AscClass, StudentGroup>> mappedStudentGroups;
    private Map<String, SimpleEntry<AscClass, Student>> mappedStudents;
    private Map<String, SimpleEntry<AscTeacher, Teacher>> mappedTeachers;
    private Map<String, SimpleEntry<AscClassroom, Room>> mappedRooms;
    private Set<TimetableImportMessage> messages;

    public AscTimetableImport(EntityManager em, HoisUserDetails user, TimetableService tService) {
        this.em = em;
        this.user = user;
        this.tService = tService;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public HoisUserDetails getUser() {
        return user;
    }

    @Override
    protected void init(TimetableImportDto dto, AscTimetable data) {
        Long schoolId = user.getSchoolId();
        try {
            em.createQuery("select tt from Timetable tt "
                    + "where tt.school.id = ?1 "
                    + "and tt.studyPeriod.id = ?2 "
                    + "and tt.startDate = ?3 "
                    + "and tt.endDate = ?4 " 
                    + "and tt.isHigher = ?5", Timetable.class)
                    .setParameter(1, schoolId)
                    .setParameter(2, dto.getStudyPeriod())
                    .setParameter(3, dto.getStartDate())
                    .setParameter(4, dto.getEndDate())
                    .setParameter(5, dto.getIsHigher())
                    .getSingleResult();
        } catch (@SuppressWarnings("unused") NoResultException nre) {
            tService.createTimetable(user, dto);
        }
    }
    
    private boolean containsNonstandardCharset(String value) {
        return value.toUpperCase().matches("^.*[ŽŠÖÜÕÄÉ].*$");
    }
    
    private Set<String> modifyFullname(String fullname) {
        Set<String> result = new LinkedHashSet<>();
        String value = fullname.toUpperCase();
        result.add(value);
        String[][] mapping = {
            {"Ž", "Z", "ZH"},
            {"Š", "S", "SH"},
            {"Ö", "O"},
            {"Ä", "A"},
            {"Ü", "U", "Y"},
            {"Õ", "O", "6"},
			{"É", "E"}
        };
        
        combination(value, mapping, result, 0);
        
        return result;
    }
    
    private void combination(String from, String[][] data, Set<String> collection, int deep) {
        if (deep >= data.length) {
            return;
        }
        for (int i = 0; i < data[deep].length; i++) {
            String nValue = from.replaceAll(data[deep][0], data[deep][i]);
            collection.add(nValue);
            combination(nValue, data, collection, deep + 1);
        }
    }

    @Override
    protected Set<TimetableImportMessage> processDataCore(School school, TimetableImportDto dto, AscTimetable data) {
        if (data == null) {
            throw new ValidationFailedException("timetable.importDialog.messages.invalidDocument");
        }

        // FIXME Better to use @XmlID and @XmlIDREF, but for the 1st one it does
        // not work and for the others it cannot be used because of problem with
        // @XmlIDREF and XmlAdapter ("thanks" to multiple values in attribute
        // separated by comma, not space which @XmlList supports)
        mappedPeriods = data.getPeriods().stream().collect(Collectors.toMap(AscPeriod::getPeriod, p -> p, (o, n) -> o));

        if (Boolean.TRUE.equals(dto.getIsIndividual())) {
            /* Students */
            Map<String, AscClass> mappedAscStudents = data.getClasses().stream()
                    .filter(o -> !StringUtils.isBlank(o.getName()))
                    .collect(Collectors.toMap(AscClass::getId, o -> o, (o, n) -> o));
            Set<String> sgStudentNames = mappedAscStudents.values().stream().filter(Objects::nonNull).map(cl -> cl.getName())
                    .filter(Objects::nonNull).map(name -> name.trim().toUpperCase()).collect(Collectors.toSet());

            Map<String, Set<Student>> mappedStudentsByName = new HashMap<>();
            if (!sgStudentNames.isEmpty())
            {
	            List<Student> students = em.createQuery("select s from Student s "
	                    + "join fetch s.studentGroup sg "
	                    + "join fetch s.person p "
	                    + "where s.school.id = ?1 and s.status.code in (?2) "
	                    /*+ "and upper(concat(s.studentGroup.code, '-', s.person.lastname)) in (?3)"*/, Student.class)
	                    .setParameter(1, school.getId())
	                    .setParameter(2, StudentStatus.STUDENT_STATUS_ACTIVE)
	                    /*.setParameter(3, sgStudentNames)*/
	                    .getResultList();
	            Map<String, Set<Student>> additionalmappedStudents = new HashMap<>();
	            additionalmappedStudents = students.stream().collect(Collectors.groupingBy(
                        s -> (s.getStudentGroup().getCode() + "-" + s.getPerson().getLastname()).toUpperCase(),
                        Collectors.mapping(s -> s, Collectors.toSet())));
	            mappedStudentsByName.putAll(additionalmappedStudents);	
	            //collect also group+name+idcode
	            Map<String, Set<Student>> mappedStudentsByIdcode=students.stream().collect(Collectors.groupingBy(
	                    s -> (s.getStudentGroup().getCode() + "-" + s.getPerson().getLastname() +(s.getPerson().getIdcode().isEmpty() ? s.getPerson().getUniqueCode(): " "+s.getPerson().getIdcode()) ).toUpperCase(),
	                    Collectors.mapping(s -> s, Collectors.toSet())));
	            mappedStudentsByName.putAll(mappedStudentsByIdcode);	
            }

           Map<String, Set<Student>> additionalmappedStudents = new HashMap<>();
           mappedStudentsByName.entrySet().stream()
                .filter(e -> containsNonstandardCharset(e.getKey()))
                .forEach(e -> {
                    Set<String> modifiedNames = modifyFullname(e.getKey());
                    additionalmappedStudents.putAll(modifiedNames.stream()
                            .collect(Collectors.toMap(n -> n, n -> e.getValue(), (o, n) -> o)));
                });
			mappedStudentsByName.putAll(additionalmappedStudents);	           

		   mappedStudents = mappedAscStudents.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), (e) -> {
                String key = e.getValue().getName().trim().toUpperCase();
                Set<Student> foundStudents = mappedStudentsByName.get(key);
                return new SimpleEntry<>(e.getValue(),
                        foundStudents != null ? foundStudents.stream().findAny().orElse(null) : null);
                }, (o, n) -> o));
				
			
			
			
        } else {
            /* Student Groups */
            Map<String, AscClass> mappedAscStudentGroups = data.getClasses().stream()
                    .filter(o -> !StringUtils.isBlank(o.getName()))
                    .collect(Collectors.toMap(AscClass::getId, o -> o, (o, n) -> o));
            Set<String> sgNames = mappedAscStudentGroups.values().stream().filter(Objects::nonNull).map(cl -> cl.getName())
                    .filter(Objects::nonNull).map(name -> name.trim().toUpperCase()).collect(Collectors.toSet());

            Map<String, Set<StudentGroup>> mappedStudentGroupsByName = sgNames.isEmpty() ? Collections.emptyMap()
                    : em.createQuery("select sg from StudentGroup sg where sg.school.id = ?1 and upper(sg.code) in (?2)",
                    StudentGroup.class).setParameter(1, school.getId()).setParameter(2, sgNames).getResultList()
                    .stream().collect(Collectors.groupingBy(sg -> sg.getCode().toUpperCase(),
                            Collectors.mapping(sg -> sg, Collectors.toSet())));

            mappedStudentGroups = mappedAscStudentGroups.entrySet().stream()
                    .collect(Collectors.toMap(e -> e.getKey(), (e) -> {
                        String key = e.getValue().getName().trim().toUpperCase();
                        Set<StudentGroup> foundGroups = mappedStudentGroupsByName.get(key);
                        return new SimpleEntry<>(e.getValue(),
                                foundGroups != null ? foundGroups.stream().findAny().orElse(null) : null);
                    }, (o, n) -> o));
        }

        /* Teachers */

        Map<String, AscTeacher> mappedAscTeachers = data.getTeachers().stream()
                .filter(o -> !StringUtils.isBlank(o.getFullname()))
                .collect(Collectors.toMap(AscTeacher::getId, o -> o, (o, n) -> o));
        Set<String> tNames = mappedAscTeachers.values().stream().filter(Objects::nonNull).map(AscTeacher::getFullname)
                .filter(Objects::nonNull).map(name -> name.trim().toUpperCase()).collect(Collectors.toSet());

        Map<String, Set<Teacher>> mappedTeachersByName = tNames.isEmpty() ? Collections.emptyMap()
                : em.createQuery(
                        "select t from Teacher t where t.school.id = ?1",
                        Teacher.class).setParameter(1, school.getId()).getResultList().stream()
                        .collect(Collectors.groupingBy(t -> t.getPerson().getFullname().toUpperCase(),
                                Collectors.mapping(t -> t, Collectors.toSet())));

        Map<String, Set<Teacher>> additionalTeacherByModifiedName = new HashMap<>();
        mappedTeachersByName.entrySet().stream()
                .filter(e -> containsNonstandardCharset(e.getKey()))
                .forEach(e -> {
                    Set<String> modifiedNames = modifyFullname(e.getKey());
                    additionalTeacherByModifiedName.putAll(modifiedNames.stream()
                            .collect(Collectors.toMap(n -> n, n -> e.getValue(), (o, n) -> o)));
                });
        mappedTeachersByName.putAll(additionalTeacherByModifiedName);
        
        mappedTeachers = mappedAscTeachers.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), (e) -> {
            String key = e.getValue().getFullname().trim().toUpperCase();
            Set<Teacher> foundTeachers = mappedTeachersByName.get(key);
            return new SimpleEntry<>(e.getValue(),
                    foundTeachers != null ? foundTeachers.stream().findAny().orElse(null) : null);
        }, (o, n) -> o));
        
        /* Rooms */
        
        Map<String, AscClassroom> mappedAscClassrooms = data.getClassrooms().stream()
                .filter(o -> !StringUtils.isBlank(o.getName()))
                .collect(Collectors.toMap(AscClassroom::getId, o -> o, (o, n) -> o));
        Set<String> rNames = mappedAscClassrooms.values().stream().filter(Objects::nonNull).map(cl -> cl.getName())
                .filter(Objects::nonNull).map(name -> name.trim().toUpperCase()).collect(Collectors.toSet());

        Map<String, Set<Room>> mappedRoomsByName = rNames.isEmpty() ? Collections.emptyMap()
                : em.createQuery("select r from Room r where r.building.school.id = ?1 and upper(r.code) in (?2)",
                        Room.class).setParameter(1, school.getId()).setParameter(2, rNames).getResultList().stream()
                        .collect(Collectors.groupingBy(r -> r.getCode().toUpperCase(),
                                Collectors.mapping(r -> r, Collectors.toSet())));

        mappedRooms = mappedAscClassrooms.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), (e) -> {
            String key = e.getValue().getName().trim().toUpperCase();
            Set<Room> foundRooms = mappedRoomsByName.get(key);
            return new SimpleEntry<>(e.getValue(),
                    foundRooms != null ? foundRooms.stream().findAny().orElse(null) : null);
        }, (o, n) -> o));

        messages = new LinkedHashSet<>();
        data.getCards().forEach(card -> {
            processItem(school, dto, card);
        });
        return messages;
    }

    @Override
    protected void processItem(School school, TimetableImportDto dto, AscCard card) {
        AscLesson lesson = card.getLesson();

        if (lesson == null) {
            messages.add(new TimetableImportMessage("timetable.importDialog.messages.noLesson",
                    generateCardErrorParameters(card), TimetableImportMessage.ErrorLevel.ERROR, Boolean.FALSE));
            return;
        }

        Set<String> notExistsStudent = new LinkedHashSet<>();
        List<Student> students = new ArrayList<>();
        Set<String> notExistsGroup = new LinkedHashSet<>();
        List<StudentGroup> studentGroups = new ArrayList<>();
        Set<String> notExistsTeacher = new LinkedHashSet<>();
        List<Teacher> teachers = new ArrayList<>();
        Set<String> notExistsRoom = new LinkedHashSet<>();
        List<Room> rooms = new ArrayList<>();
        
        for (String id : lesson.getClasses()) {
            if (Boolean.TRUE.equals(dto.getIsIndividual())) {
                SimpleEntry<AscClass, Student> entry = mappedStudents.get(id);
                if (entry == null || entry.getKey() == null || entry.getKey().getName() == null) {
                    notExistsStudent.add(id);
                    continue;
                }
                String name = entry.getKey().getName();
                if (entry.getValue() == null) {
                    notExistsStudent.add(name);
                    continue;
                }
                students.add(entry.getValue());
            } else {
                SimpleEntry<AscClass, StudentGroup> entry = mappedStudentGroups.get(id);
                if (entry == null || entry.getKey() == null || entry.getKey().getName() == null) {
                    notExistsGroup.add(id);
                    continue;
                }
                String name = entry.getKey().getName();
                if (entry.getValue() == null) {
                    notExistsGroup.add(name);
                    continue;
                }
                studentGroups.add(entry.getValue());
            }
        }

        for (String id : lesson.getTeachers()) {
            SimpleEntry<AscTeacher,Teacher> entry = mappedTeachers.get(id);
            if (entry == null || entry.getKey() == null || entry.getKey().getName() == null) {
                notExistsTeacher.add(id);
                continue;
            }
            String name = entry.getKey().getName();
            if (entry.getValue() == null) {
                notExistsTeacher.add(name);
                continue;
            }
            teachers.add(entry.getValue());
        }

        for (String id : card.getClassrooms().isEmpty() ? lesson.getClassrooms() : card.getClassrooms()) {
            SimpleEntry<AscClassroom,Room> entry = mappedRooms.get(id);
            if (entry == null || entry.getKey() == null || entry.getKey().getName() == null) {
                notExistsRoom.add(id);
                continue;
            }
            String name = entry.getKey().getName();
            if (entry.getValue() == null) {
                notExistsRoom.add(name);
                continue;
            }
            rooms.add(entry.getValue());
        }

        if (students.isEmpty() && studentGroups.isEmpty() && teachers.isEmpty() && rooms.isEmpty()) {
            messages.add(new TimetableImportMessage("timetable.importDialog.messages.notFoundEverything",
                    generateCardErrorParameters(card), TimetableImportMessage.ErrorLevel.ERROR, Boolean.FALSE));
            return;
        }

        Map<String, Object> params;
        TimetableImportMessage message;
        String uniid = UUID.randomUUID().toString();
        if (Boolean.TRUE.equals(dto.getIsIndividual())) {
            if (!notExistsStudent.isEmpty()) {
                params = generateCardErrorParameters(card);
                params.put("nfStudents", notExistsStudent);
                message = new TimetableImportMessage("timetable.importDialog.messages.notFoundStudent", params,
                        TimetableImportMessage.ErrorLevel.WARNING, Boolean.FALSE);
                message.setUniid(uniid);
                messages.add(message);
            }
        } else {
            if (!notExistsGroup.isEmpty()) {
                params = generateCardErrorParameters(card);
                params.put("nfGroups", notExistsGroup);
                message = new TimetableImportMessage("timetable.importDialog.messages.notFoundGroup", params,
                        TimetableImportMessage.ErrorLevel.WARNING, Boolean.FALSE);
                message.setUniid(uniid);
                messages.add(message);
            }
        }
        if (!notExistsTeacher.isEmpty()) {
            params = generateCardErrorParameters(card);
            params.put("nfTeachers", notExistsTeacher);
            message = new TimetableImportMessage("timetable.importDialog.messages.notFoundTeacher", params,
                    TimetableImportMessage.ErrorLevel.WARNING, Boolean.FALSE);
            message.setUniid(uniid);
            messages.add(message);
        }
        if (!notExistsRoom.isEmpty()) {
            params = generateCardErrorParameters(card);
            params.put("nfRooms", notExistsRoom);
            message = new TimetableImportMessage("timetable.importDialog.messages.notFoundRoom", params,
                    TimetableImportMessage.ErrorLevel.WARNING, Boolean.FALSE);
            message.setUniid(uniid);
            messages.add(message);
        }

        TimetableEvent event = new TimetableEvent();
        AscPeriod period = mappedPeriods.get(card.getPeriod());
        if (period == null || period.getStartTime() == null || period.getEndTime() == null) {
            messages.add(new TimetableImportMessage("timetable.importDialog.messages.wrongPeriod",
                    generateCardErrorParameters(card), TimetableImportMessage.ErrorLevel.ERROR, Boolean.FALSE));
            return;
        }
        if (lesson.getSubject() != null) {
            event.setName(lesson.getSubject().getName());
        }
        event.setStart(dto.getStartDate().plusDays(card.getDays().get(0).intValue() - 1).atTime(period.getStartTime()));
        event.setEnd(dto.getStartDate().plusDays(card.getDays().get(0).intValue() - 1).atTime(period.getEndTime()));
        event.setLessonNr(Short.valueOf(card.getPeriod()));
        event.setSchool(school);
        event.setRepeatCode(
                em.getReference(Classifier.class, TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_EI.name()));
        event.setIsImported(Boolean.TRUE);

        TimetableEventTime eventTime = new TimetableEventTime();
        eventTime.setStart(event.getStart());
        eventTime.setEnd(event.getEnd());
        eventTime.setTimetableEvent(event);
        event.getTimetableEventTimes().add(eventTime);

        for (Student s : students) {
            TimetableEventStudent tes = new TimetableEventStudent();
            tes.setStudent(s);
            tes.setTimetableEventTime(eventTime);
            eventTime.getTimetableEventStudents().add(tes);
        }

        for (StudentGroup sg : studentGroups) {
            TimetableEventStudentGroup ttsg = new TimetableEventStudentGroup();
            ttsg.setStudentGroup(sg);
            ttsg.setTimetableEventTime(eventTime);
            eventTime.getTimetableEventStudentGroups().add(ttsg);
        }

        for (Teacher teacher : teachers) {
            TimetableEventTeacher ttet = new TimetableEventTeacher();
            ttet.setTeacher(teacher);
            ttet.setTimetableEventTime(eventTime);
            eventTime.getTimetableEventTeachers().add(ttet);
        }

        for (Room room : rooms) {
            TimetableEventRoom tter = new TimetableEventRoom();
            tter.setRoom(room);
            tter.setTimetableEventTime(eventTime);
            eventTime.getTimetableEventRooms().add(tter);
        }

        EntityUtil.save(event, em);
    }

    private static Map<String, Object> generateCardErrorParameters(AscCard card) {
        Map<String, Object> params = new HashMap<>();
        if (card.getLesson() != null) {
            params.put("lessonId", card.getLesson().getId());
            if (card.getLesson().getSubject() != null) {
                params.put("subject", card.getLesson().getSubject().getName());
            }
        }
        if (card.getPeriod() != null) {
            params.put("period", card.getPeriod());
        }
        if (card.getDays() != null && !card.getDays().isEmpty()) {
            params.put("day", getDayTranslationCode(card.getDays().get(0)));
        }
        return params;
    }

    private static String getDayTranslationCode(Integer day) {
        switch (day.intValue()) {
        case 1:
            return "timetable.timetablePlan.dayMon";
        case 2:
            return "timetable.timetablePlan.dayTue";
        case 3:
            return "timetable.timetablePlan.dayWed";
        case 4:
            return "timetable.timetablePlan.dayThu";
        case 5:
            return "timetable.timetablePlan.dayFri";
        case 6:
            return "timetable.timetablePlan.daySat";
        case 7:
            return "timetable.timetablePlan.daySun";
        default:
            return "-";
        }
    }
}
