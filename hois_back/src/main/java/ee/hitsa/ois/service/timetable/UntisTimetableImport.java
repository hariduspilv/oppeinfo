package ee.hitsa.ois.service.timetable;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

import java.lang.invoke.MethodHandles;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.Timetable;
import ee.hitsa.ois.domain.timetable.TimetableEvent;
import ee.hitsa.ois.domain.timetable.TimetableEventRoom;
import ee.hitsa.ois.domain.timetable.TimetableEventTeacher;
import ee.hitsa.ois.domain.timetable.TimetableEventTime;
import ee.hitsa.ois.domain.timetable.TimetableObject;
import ee.hitsa.ois.domain.timetable.TimetableObjectStudentGroup;
import ee.hitsa.ois.enums.CapacityType;
import ee.hitsa.ois.enums.TimetableEventRepeat;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.repository.TimetableObjectRepository;
import ee.hitsa.ois.service.TimetableService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.timetable.NameAndCode;
import ee.hitsa.ois.web.dto.timetable.TimetableImportDto;

public class UntisTimetableImport extends AbstractTimetableImport<Document, Node, NameAndCode> {
    
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    private HoisUserDetails user;
    private EntityManager em;
    private TimetableService tService;
    private TimetableObjectRepository tObjectRepository;
    
    private List<String> allStudentGroupCodes;
    private List<String> usedIds;
    private Set<NameAndCode> messages;
    private NodeList nodeList;
    
    private Timetable timetable;
    private Long studyYearId;
    
    public UntisTimetableImport(HoisUserDetails user, EntityManager em, TimetableService service, TimetableObjectRepository repository) {
        this.em = em;
        this.user = user;
        this.tService = service;
        this.tObjectRepository = repository;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected HoisUserDetails getUser() {
        return user;
    }

    @Override
    protected Set<NameAndCode> processDataCore(School school, TimetableImportDto dto, Document data) {
        messages = new LinkedHashSet<>();
        if (data != null) {
            allStudentGroupCodes = getAllStudentGroupCodes(data);
            usedIds = new ArrayList<>();
            studyYearId = EntityUtil.getId(timetable.getStudyPeriod().getStudyYear());
            
            nodeList = data.getElementsByTagName("lesson");
            
            // Find node <lesson>
            for (int lessonIndex = 0; lessonIndex < nodeList.getLength(); lessonIndex++) {
                try {
                    processItem(school, dto, nodeList.item(lessonIndex));
                } catch (HoisException ex) {
                    throw ex;
                } catch (Exception ex) {
                    LOG.error("Error during processing UNTIS xml file.", ex);
                    throw new HoisException("timetable.importDialog.messages.invalidDocument", ex);
                }
            }
        }
        return messages.stream().filter(StreamUtil.distinctByKey(NameAndCode::toString))
                .collect(Collectors.toSet());
    
    }

    @Override
    protected void processItem(School school, TimetableImportDto dto, Node node) {
        // Get journalId from node
        String journalId =  node.getAttributes().item(0).getTextContent();
        // Skip, if this journal id is already processed
        if (usedIds.contains(journalId.substring(0, journalId.length() - 2))) return;
        Journal journal;

        NodeList children = node.getChildNodes();
        String subjectCode = null;
        String subjectUntis = null;
        for (int nodeIndex = 0; nodeIndex < children.getLength(); nodeIndex++) {
            Node childNode = node.getChildNodes().item(nodeIndex);
            if (childNode.getNodeType() == Node.ELEMENT_NODE && Objects.equals("lesson_subject", childNode.getNodeName())) {
                subjectCode = childNode.getAttributes().getNamedItem("id").getTextContent();
                if (subjectCode != null && subjectCode.length() > 3) {
                    int lastIndexCapacity = subjectCode.lastIndexOf('`');
                    if (lastIndexCapacity != -1) {
                        subjectUntis = subjectCode.substring(3, lastIndexCapacity);
                    } else {
                        subjectUntis = subjectCode.substring(3);
                    }
                }
                break;
            }
        }
        
        if (subjectUntis == null) {
            messages.add(new NameAndCode(journalId.substring(0, journalId.length() - 2)
                        + (subjectCode != null ? " (" + subjectCode + ")" : ""),
                    "timetable.importDialog.errors.emptySubjectUntis"));
            return;
        }
        
        List<Journal> foundJournals = em.createQuery("select j from Journal j "
                + "where upper(j.untisCode) = ?1 and j.school.id = ?2 and j.studyYear.id = ?3 "
                + "order by j.id asc", Journal.class)
            .setParameter(1, subjectUntis.toUpperCase())
            .setParameter(2, user.getSchoolId())
            .setParameter(3, studyYearId)
            .setMaxResults(1)
            .getResultList();
        
        if (foundJournals.isEmpty()) {
            messages.add(new NameAndCode(journalId.substring(0, journalId.length() - 2)
                        + (subjectCode != null ? " (" + subjectCode + ")" : ""),
                    "timetable.importDialog.errors.journalNotFound"));
            if (journalId.endsWith("00")) { // means that has main information
                // we should not repeat check for xxxxx00 and xxxxx01
                // as the 1st will give journalNotFound and the 2nd will give emptySubjectUnits
                usedIds.add(journalId.substring(0, journalId.length() -2));
            }
            return;
        }
        
        try {
            journal = foundJournals.get(0);
            usedIds.add(journalId.substring(0, journalId.length() - 2));
        } catch(@SuppressWarnings("unused") Exception e) {
            messages.add(new NameAndCode(journalId, "timetable.importDialog.errors.journalNotFound"));
            return;
        }
        
        if (!user.getSchoolId().equals(EntityUtil.getNullableId(journal.getSchool()))) {
            messages.add(new NameAndCode(journal.getNameEt(), "timetable.importDialog.errors.wrongSchool"));
            return;
        }
        
        // Update timetable object
        TimetableObject timetableObject = null;
        try {
            timetableObject = tObjectRepository.findByJournalAndTimetable(journal, timetable);
        } catch(@SuppressWarnings("unused") EntityNotFoundException e) {
            messages.add(new NameAndCode(journal.getNameEt(), "timetable.importDialog.errors.relationToTimetable"));
            return;
        }
        if (timetableObject == null) {
            timetableObject = new TimetableObject();
            timetableObject.setJournal(journal);
            timetableObject.setTimetable(timetable);
        }
        NodeList lessonChildrenList = node.getChildNodes();
        NodeList lessontimesNodeList = null;
        for (int lessonChildrenIndex = 0; lessonChildrenIndex < lessonChildrenList.getLength(); lessonChildrenIndex++) {
            // Find node <times>
            Node lessonTimes = lessonChildrenList.item(lessonChildrenIndex);
            if (lessonTimes.getNodeType() == Node.ELEMENT_NODE && Objects.equals("times", lessonTimes.getNodeName())) {
                lessontimesNodeList = lessonTimes.getChildNodes();
            }
        }
        if (lessontimesNodeList == null) {
            messages.add(new NameAndCode(journal.getNameEt(), "timetable.importDialog.errors.documentFormat"));
            return;
        }
        for (int lessontimesNodeChild = 0; lessontimesNodeChild < lessontimesNodeList.getLength(); lessontimesNodeChild++) {
            Node lessonTime = lessontimesNodeList.item(lessontimesNodeChild);
            if (lessonTime.getNodeType() == Node.ELEMENT_NODE && Objects.equals("time", lessonTime.getNodeName())) {
                Integer day = null;
                Short period = null;
                String startTime = null;
                String endTime = null;
                NodeList lessonTimeChildren = lessonTime.getChildNodes();
                List<String> buildingRoomCodes = null;
                
                // Find <time> child nodes and assign its node values
                try {
                    for (int timeNodeIndex = 0; timeNodeIndex < lessonTimeChildren.getLength(); timeNodeIndex++) {
                        Node lessonTimeNode = lessonTimeChildren.item(timeNodeIndex);
                        if (lessonTimeNode.getNodeType() == Node.ELEMENT_NODE && "assigned_day".equals(lessonTimeNode.getNodeName())) {
                            day = Integer.valueOf(lessonTimeNode.getTextContent());
                        } else if (lessonTimeNode.getNodeType() == Node.ELEMENT_NODE && "assigned_period".equals(lessonTimeNode.getNodeName())) {
                            period = Short.valueOf(lessonTimeNode.getTextContent());
                        } else if (lessonTimeNode.getNodeType() == Node.ELEMENT_NODE && "assigned_starttime".equals(lessonTimeNode.getNodeName())) {
                            startTime = lessonTimeNode.getTextContent();
                        } else if (lessonTimeNode.getNodeType() == Node.ELEMENT_NODE && "assigned_endtime".equals(lessonTimeNode.getNodeName())) {
                            endTime = lessonTimeNode.getTextContent();
                        } else if (lessonTimeNode.getNodeType() == Node.ELEMENT_NODE && "assigned_room".equals(lessonTimeNode.getNodeName())) {
                            buildingRoomCodes = new ArrayList<>(Arrays.asList(lessonTimeNode.getAttributes().item(0).getTextContent().split(" ")));
                        }
                    }
                } catch (@SuppressWarnings("unused") NumberFormatException nfe) {
                    throw new HoisException("timetable.importDialog.errors.nrFormat");
                }
                if (day == null || period == null || startTime == null || endTime == null) {
                    messages.add(new NameAndCode(journal.getNameEt(), "timetable.importDialog.errors.hourData"));
                    continue;
                }
                
                // Get student group and capacity
                List<String> studentGroupCodes = null;
                String capacityType = null;
                for (int lessonChildren = 0; lessonChildren < lessonChildrenList.getLength(); lessonChildren++) {
                    Node lessonNode = lessonChildrenList.item(lessonChildren);
                    if (lessonNode.getNodeType() == Node.ELEMENT_NODE && "lesson_classes".equals(lessonNode.getNodeName())) {
                        studentGroupCodes = allStudentGroupCodes.stream()
                                .filter(p -> lessonNode.getAttributes().item(0).getTextContent().contains(p)).collect(Collectors.toList());
                    } else if (lessonNode.getNodeType() == Node.ELEMENT_NODE && "lesson_subject".equals(lessonNode.getNodeName())) {
                        String subjectId = lessonNode.getAttributes().item(0).getTextContent();
                        int lastIndex = subjectId.lastIndexOf('`');
                        if (lastIndex != -1) {
                            capacityType = subjectId.substring(lastIndex + 1);
                        } else {
                            capacityType = CapacityType.MAHT_a.name();
                        }
                    }
                }
                
                if (studentGroupCodes == null || studentGroupCodes.isEmpty()) {
                    messages.add(new NameAndCode(journal.getNameEt(), "timetable.importDialog.errors.studentgroups"));
                    continue;
                } else if (buildingRoomCodes == null || buildingRoomCodes.isEmpty()) {
                    messages.add(new NameAndCode(journal.getNameEt(), "timetable.importDialog.errors.rooms"));
                }
                
                // Remove CL_ and RM_ from the start of string, substring is safer than replace
                studentGroupCodes = studentGroupCodes.stream().map(p->p.substring(3)).collect(Collectors.toList());
                Set<Room> rooms = null;
                if (buildingRoomCodes != null) {
                    buildingRoomCodes = buildingRoomCodes.stream().map(p->p.substring(3)).collect(Collectors.toList());
                    
                    // Get rooms by code
                    
                    Query roomQuery = em.createNativeQuery("select r.id as buildingRoom from room r"
                            + " join building b on b.id = r.building_id"
                            + " where b.school_id = :schoolId"
                            + " and replace(concat(b.code, r.code), ' ', '') in (:buildingRoomCodes)");
                    roomQuery.setParameter("schoolId", school.getId());
                    roomQuery.setParameter("buildingRoomCodes", buildingRoomCodes);
                    List<?> dbRooms = roomQuery.getResultList();
                    rooms = StreamUtil.toMappedSet(r -> em.getReference(Room.class, resultAsLong(r, 0)), dbRooms);
                }
                
                
                
                // Get student groups
                List<StudentGroup> studentGroups = em.createQuery("select sg from StudentGroup sg "
                        + "where sg.code in (?1) "
                        + "and sg.school.id = ?2", StudentGroup.class)
                        .setParameter(1, studentGroupCodes)
                        .setParameter(2, school.getId())
                        .getResultList();
                
                TimetableEvent timetableEvent = new TimetableEvent();
                LocalTime startHourMinutes = LocalTime.parse(startTime, TimetableService.DOCUMENT_TIME_FORMAT_SHORT);
                LocalTime endHourMinutes = LocalTime.parse(endTime, TimetableService.DOCUMENT_TIME_FORMAT_SHORT);
                timetableEvent.setStart(dto.getStartDate().plusDays(day.intValue() - 1).atTime(startHourMinutes.getHour(), startHourMinutes.getMinute()));
                timetableEvent.setEnd(dto.getStartDate().plusDays(day.intValue() - 1).atTime(endHourMinutes.getHour(), endHourMinutes.getMinute()));
                timetableEvent.setLessonNr(period);
                timetableEvent.setTimetableObject(timetableObject);
                timetableEvent.setSchool(em.getReference(School.class, user.getSchoolId()));
                timetableEvent.setRepeatCode(em.getReference(Classifier.class, TimetableEventRepeat.TUNNIPLAAN_SYNDMUS_KORDUS_EI.name()));
                timetableEvent.setIsImported(Boolean.TRUE);
                if (capacityType != null) timetableEvent.setCapacityType(em.getReference(Classifier.class, capacityType));
                
                TimetableEventTime timetableEventTime = new TimetableEventTime();
                timetableEventTime.setStart(timetableEvent.getStart());
                timetableEventTime.setEnd(timetableEvent.getEnd());
                timetableEventTime.setTimetableEvent(timetableEvent);
                
                List<TimetableEventTime> timetableEventTimes = timetableEvent.getTimetableEventTimes();
                timetableEventTimes.add(timetableEventTime);
                timetableEvent.setTimetableEventTimes(timetableEventTimes);
                
                List<TimetableEvent> timetableEvents = timetableObject.getTimetableEvents();
                timetableEvents.add(timetableEvent);
                timetableObject.setTimetableEvents(timetableEvents);
                List<TimetableObject> existingTimetableObjects = timetable.getTimetableObjects();
                existingTimetableObjects.add(timetableObject);
                timetable.setTimetableObjects(existingTimetableObjects);
                
             
                // Create timetable event rooms
                if (rooms != null && !rooms.isEmpty()) {
                    for (Room room : rooms) {
                        TimetableEventRoom timetableEventRoom = new TimetableEventRoom();
                        timetableEventRoom.setRoom(room);
                        timetableEventRoom.setTimetableEventTime(timetableEventTime);
                        List<TimetableEventRoom> timetableEventRooms = timetableEventTime.getTimetableEventRooms();
                        timetableEventRooms.add(timetableEventRoom);
                        timetableEventTime.setTimetableEventRooms(timetableEventRooms);
                    }
                }
                
                // Hook all the teachers with same LS id to the event
                List<String> teacherUntisCodes = new ArrayList<>();
                String ourJournalId = journalId.substring(0, journalId.length() - 2);
                for (int nodeIndex = 0; nodeIndex < nodeList.getLength(); nodeIndex++) {
                    Node lessonNode = nodeList.item(nodeIndex);
                    String differentJournalId = null;
                    try {
                        differentJournalId =  lessonNode.getAttributes().item(0).getTextContent().substring(0, journalId.length() - 2);
                    } catch(@SuppressWarnings("unused") Exception e) {
                        continue;
                    }
                    if (ourJournalId.equals(differentJournalId)) {
                        NodeList lessonNodeChildren = lessonNode.getChildNodes();
                        String teacherUntisCode = null;
                        for (int lessonChildren = 0; lessonChildren < lessonNodeChildren.getLength(); lessonChildren++) {
                            Node lessonChildrenNode = lessonNodeChildren.item(lessonChildren);
                            if (lessonChildrenNode.getNodeType() == Node.ELEMENT_NODE && "lesson_teacher".equals(lessonChildrenNode.getNodeName())) {
                                teacherUntisCode = lessonChildrenNode.getAttributes().item(0).getTextContent().substring(3);
                            }
                        }
                        if (teacherUntisCode != null) teacherUntisCodes.add(teacherUntisCode);
                    }
                }
                if (teacherUntisCodes.isEmpty()) {
                    messages.add(new NameAndCode(journal.getNameEt(), "timetable.importDialog.errors.teachers"));
                }
                
                // Get teacher by code
                List<Teacher> teachers = teacherUntisCodes.isEmpty() ? Collections.emptyList() : em.createQuery("select t from Teacher t "
                        + "where t.untisCode in ?1 "
                        + "and t.school.id = ?2", Teacher.class)
                        .setParameter(1, teacherUntisCodes)
                        .setParameter(2, school.getId())
                        .getResultList();
                
                Set<String> uniqueTeacherUntisCodesFromDB = teachers.stream().map(Teacher::getUntisCode).collect(Collectors.toSet());
                List<String> notFoundUntis = teacherUntisCodes.stream().filter(tUntis -> !uniqueTeacherUntisCodesFromDB.contains(tUntis)).collect(Collectors.toList());
                
                if (!notFoundUntis.isEmpty()) {
                    messages.add(new NameAndCode(journal.getNameEt(), "timetable.importDialog.errors.teachers", String.join(", ", notFoundUntis)));
                }
                
                // Create timetable event teacher
                List<TimetableEventTeacher> timetableEventTeachers =  timetableEventTime.getTimetableEventTeachers();
                for (Teacher teacher : teachers) {
                    TimetableEventTeacher timetableEventTeacher = new TimetableEventTeacher();
                    timetableEventTeacher.setTeacher(teacher);
                    timetableEventTeacher.setTimetableEventTime(timetableEventTime);
                    timetableEventTeachers.add(timetableEventTeacher);
                }
                
                timetableEventTime.setTimetableEventTeachers(timetableEventTeachers);
                
                // Find if this timetable object already has the student groups attached to it
                List<Long> studentGroupIds = studentGroups.stream().map(p->p.getId()).collect(Collectors.toList());
                List<TimetableObjectStudentGroup> timetableObjectStudentGroups = new ArrayList<>();
                if (timetableObject.getId() != null && !studentGroupIds.isEmpty()) {
                    timetableObjectStudentGroups = em.createQuery("select tosg from TimetableObjectStudentGroup tosg "
                            + "where tosg.studentGroup.id in (?1) "
                            + "and tosg.timetableObject.id = ?2", TimetableObjectStudentGroup.class)
                            .setParameter(1, studentGroupIds)
                            .setParameter(2, timetableObject.getId())
                            .getResultList();
                }
                
                List<Long> existingStudentGroupIds = timetableObjectStudentGroups.stream().map(p->p.getId()).collect(Collectors.toList());
                // Make new timetable object student groups if needed
                for (StudentGroup studentGroup : studentGroups) {
                    if (!existingStudentGroupIds.contains(studentGroup.getId()) && 
                            timetableObject.getTimetableObjectStudentGroups().stream().noneMatch(p->p.getStudentGroup().getId() == studentGroup.getId())) {
                        TimetableObjectStudentGroup timetableObjectStudentGroup = new TimetableObjectStudentGroup();
                        timetableObjectStudentGroup.setStudentGroup(studentGroup);
                        timetableObjectStudentGroup.setTimetableObject(timetableObject);
                        List<TimetableObjectStudentGroup> timetableobjectSG = timetableObject.getTimetableObjectStudentGroups();
                        timetableobjectSG.add(timetableObjectStudentGroup);
                        timetableObject.setTimetableObjectStudentGroups(timetableobjectSG);
                    }
                }
            }
        }
        List<TimetableObject> timetableObjects = timetable.getTimetableObjects();
        boolean gotEvent = false;
        for (TimetableObject timetableObj : timetableObjects) {
            if (timetableObj != null && timetableObj.getTimetableEvents() != null && !timetableObj.getTimetableEvents().isEmpty() 
                    && timetableObj.getTimetableEvents().stream().anyMatch(p -> p != null && p.getIsImported() != null && p.getIsImported().booleanValue())) {
                gotEvent = true;
                break;
            }
        }
        if (gotEvent) {
            EntityUtil.save(timetable, em);
        }
    }

    @Override
    protected void init(TimetableImportDto dto, Document data) {
        Long schoolId = user.getSchoolId();
        try {
            timetable = em.createQuery("select tt from Timetable tt "
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
            timetable = tService.createTimetable(user, dto);
        }
    }
    
    private static List<String> getAllStudentGroupCodes(Document document) {
        NodeList allStudentGroupNodes = document.getElementsByTagName("class");
        List<String> allStudentGroups = new ArrayList<>();
        for (int sgIndex = 0; sgIndex < allStudentGroupNodes.getLength(); sgIndex++) {
            Node node = allStudentGroupNodes.item(sgIndex);
            // Get student group code
            String sgCode =  node.getAttributes().item(0).getTextContent();
            allStudentGroups.add(sgCode);
        }
        return allStudentGroups;
    }
}
