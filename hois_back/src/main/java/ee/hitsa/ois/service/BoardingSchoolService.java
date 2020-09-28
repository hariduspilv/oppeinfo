package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Dormitory;
import ee.hitsa.ois.domain.Room;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.boardingschool.BoardingSchoolManagementForm;
import ee.hitsa.ois.web.commandobject.boardingschool.BoardingSchoolManagementSearchCommand;
import ee.hitsa.ois.web.commandobject.boardingschool.BoardingSchoolRoomCommand;
import ee.hitsa.ois.web.commandobject.boardingschool.BoardingSchoolSearchCommand;
import ee.hitsa.ois.web.commandobject.boardingschool.DormitoryForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.RoomAutocompleteResult;
import ee.hitsa.ois.web.dto.boardingschool.BoardingSchoolManagementCheckDto;
import ee.hitsa.ois.web.dto.boardingschool.BoardingSchoolManagementDto;
import ee.hitsa.ois.web.dto.boardingschool.BoardingSchoolResidentDto;
import ee.hitsa.ois.web.dto.boardingschool.BoardingSchoolRoomDto;
import ee.hitsa.ois.web.dto.boardingschool.BoardingSchoolSearchDto;
import ee.hitsa.ois.web.dto.boardingschool.DormitoryDto;

@Transactional
@Service
public class BoardingSchoolService {

    @Autowired
    private EntityManager em;
    @Autowired
    private XlsService xlsService;

    private static final String SEARCH_SELCT = "d.id d_id, s.id s_id, p.firstname, p.lastname, p.idcode, "
            + "sg.id sg_id, sg.code sg_code, d.valid_from, d.valid_thru, r.id r_id, b.code b_code, "
            + "r.code r_code, s.type_code typeCode";

    private static final String MANAGEMENT_SEARCH_SELCT = "s.id s_id, p.firstname, p.lastname, p.idcode, "
            + "sg.id sg_id, sg.code sg_code, s.dormitory_code, s.status_code, s.type_code typeCode";

    private static final String ROOM_SELECT = "b.id b_id, b.name b_name, r.id r_id, b.code b_code, "
            + "r.code r_code, r.seats";

    public Page<BoardingSchoolSearchDto> search(HoisUserDetails user, BoardingSchoolSearchCommand criteria,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from dormitory d "
                + "join student s on s.id = d.student_id "
                + "join person p on p.id = s.person_id "
                + "left join student_group sg on sg.id = s.student_group_id "
                + "join room r on r.id = d.room_id "
                + "join building b on b.id = r.building_id").sort(pageable);

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("exists(select c.id from curriculum_version cv "
                    + "join curriculum c on c.id = cv.curriculum_id "
                    + "where s.curriculum_version_id = cv.id and c.id in (:userCurriculumIds))",
                    "userCurriculumIds", user.getCurriculumIds());
        }

        qb.optionalCriteria("sg.id = :groupId", "groupId",
                criteria.getStudentGroup() != null ? criteria.getStudentGroup().getId() : null);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name",
                criteria.getName());
        qb.optionalCriteria("p.idcode = :idcode", "idcode", criteria.getIdcode());
        qb.optionalCriteria("r.id = :roomId", "roomId", criteria.getRoom());
        qb.optionalCriteria("d.valid_from >= :from", "from", criteria.getValidFrom());
        qb.optionalCriteria("d.valid_thru <= :thru", "thru", criteria.getValidThru());

        if (Boolean.TRUE.equals(criteria.getShowValid())) {
            qb.optionalCriteria("d.valid_from <= :today and d.valid_thru >= :today", "today", LocalDate.now());
        }

        Page<BoardingSchoolSearchDto> page = JpaQueryUtil.pagingResult(qb, SEARCH_SELCT, em, pageable).map(r -> {
            BoardingSchoolSearchDto dto = new BoardingSchoolSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setStudent(resultAsLong(r, 1));
            dto.setFullname(PersonUtil.fullnameTypeSpecific(resultAsString(r, 2), resultAsString(r, 3), resultAsString(r, 12)));
            dto.setIdcode(resultAsString(r, 4));
            dto.setStudentGroup(new AutocompleteResult(resultAsLong(r, 5), resultAsString(r, 6), resultAsString(r, 6)));
            dto.setValidFrom(resultAsLocalDate(r, 7));
            dto.setValidThru(resultAsLocalDate(r, 8));

            String roomCode = resultAsString(r, 10) + "-" + resultAsString(r, 11);
            dto.setRoom(new AutocompleteResult(resultAsLong(r, 9), roomCode, roomCode));
            return dto;
        });

        if (Boolean.TRUE.equals(criteria.getShowNeighbours()) && page.getContent().size() > 0) {
            setBoardingSchoolResidentNeighbours(criteria, page.getContent());
        }

        return page;
    }

    private void setBoardingSchoolResidentNeighbours(BoardingSchoolSearchCommand criteria,
            List<BoardingSchoolSearchDto> boardingSchools) {
        JpaNativeQueryBuilder qb;

        String query = "";
        Map<String, Object> parameters = new HashMap<>();

        for (int i = 0; i < boardingSchools.size(); i++) {
            if (i > 0) {
                query += " union all ";
            }
            BoardingSchoolSearchDto dto = boardingSchools.get(i);

            qb = new JpaNativeQueryBuilder("from dormitory d "
                    + "join student s on s.id = d.student_id "
                    + "join person p on p.id = s.person_id");
            qb.requiredCriteria("d.id != :boarding_school_id" + i, "boarding_school_id" + i, dto.getId());
            qb.requiredCriteria("d.room_id = :room_id" + i, "room_id" + i, dto.getRoom());
            qb.requiredCriteria("d.valid_from <= :thru" + i, "thru" + i, dto.getValidThru());
            qb.requiredCriteria("d.valid_thru >= :from" + i, "from" + i, dto.getValidFrom());

            if (Boolean.TRUE.equals(criteria.getShowValid())) {
                qb.optionalCriteria("d.valid_from <= :today and d.valid_thru >= :today", "today", LocalDate.now());
            }

            query += qb.querySql(i + " row_nr, s.id s_id, p.firstname, p.lastname, p.idcode,"
                    + " d.valid_from, d.valid_thru", false);
            parameters.putAll(qb.queryParameters());
        }

        qb = new JpaNativeQueryBuilder("from (" + query + ") as f");
        qb.sort("lastname, firstname");

        List<?> data = qb.select("*", em, parameters).getResultList();
        Map<Long, List<BoardingSchoolResidentDto>> studentNeighbours = StreamUtil.nullSafeList(data).stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
                    BoardingSchoolResidentDto neighbourDto = new BoardingSchoolResidentDto();
                    neighbourDto.setStudent(resultAsLong(r, 1));
                    neighbourDto.setFullname(PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3)));
                    neighbourDto.setIdcode(resultAsString(r, 4));
                    neighbourDto.setValidFrom(resultAsLocalDate(r, 5));
                    neighbourDto.setValidThru(resultAsLocalDate(r, 6));
                    return neighbourDto;
                }, Collectors.toList())));

        for (int i = 0; i < boardingSchools.size(); i++) {
            BoardingSchoolSearchDto dto = boardingSchools.get(i);
            List<BoardingSchoolResidentDto> neighbours = studentNeighbours.get(Long.valueOf(i));
            if (neighbours != null) {
                dto.setNeighbours(neighbours);
            }
        }
    }

    public byte[] boardingSchoolResidetnsAsExcel(HoisUserDetails user, BoardingSchoolSearchCommand criteria) {
        List<BoardingSchoolSearchDto> boardingSchools = search(user, criteria, new PageRequest(0, Integer.MAX_VALUE))
                .getContent();
        for (BoardingSchoolSearchDto dto : boardingSchools) {
            String neighboursXls = "";
            for (int i = 0; i < dto.getNeighbours().size(); i++) {
                if (i > 0) {
                    neighboursXls += ";\n";
                }

                BoardingSchoolResidentDto neighbourDto = dto.getNeighbours().get(i);
                neighboursXls += neighbourDto.getFullname()
                        + (neighbourDto.getIdcode() != null ? " " + neighbourDto.getIdcode() : "");
            }
            dto.setNeighboursXls(neighboursXls);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("criteria", criteria);
        data.put("boardingSchools", boardingSchools);
        return xlsService.generate("boarding.schools.xlsx", data);
    }

    public Page<BoardingSchoolManagementDto> managementSearch(HoisUserDetails user,
            BoardingSchoolManagementSearchCommand criteria, Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
                + "join person p on p.id = s.person_id "
                + "left join student_group sg on sg.id = s.student_group_id").sort(pageable);
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("sg.id = :groupId", "groupId",
                criteria.getStudentGroup() != null ? criteria.getStudentGroup().getId() : null);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name",
                criteria.getName());
        qb.optionalCriteria("p.idcode = :idcode", "idcode", criteria.getIdcode());
        qb.optionalCriteria("s.dormitory_code in (:dormitoryCode)", "dormitoryCode", criteria.getDormitory());

        if (!Boolean.TRUE.equals(criteria.getNotActiveStudents())) {
            qb.requiredCriteria("s.status_code in (:studentStatus)", "studentStatus",
                    StudentStatus.STUDENT_STATUS_ACTIVE);
        }

        Page<BoardingSchoolManagementDto> page = JpaQueryUtil.pagingResult(qb, MANAGEMENT_SEARCH_SELCT, em, pageable)
                .map(r -> {
                    BoardingSchoolManagementDto dto = new BoardingSchoolManagementDto();
                    dto.setStudent(resultAsLong(r, 0));
                    dto.setFullname(PersonUtil.fullnameTypeSpecific(resultAsString(r, 1), resultAsString(r, 2), resultAsString(r, 8)));
                    dto.setIdcode(resultAsString(r, 3));
                    dto.setStudentGroup(
                            new AutocompleteResult(resultAsLong(r, 4), resultAsString(r, 5), resultAsString(r, 5)));
                    dto.setDormitory(resultAsString(r, 6));
                    dto.setStudentStatus(resultAsString(r, 7));
                    return dto;
                });

        setManagementDormitories(page.getContent());
        return page;
    }

    private void setManagementDormitories(List<BoardingSchoolManagementDto> managementDtos) {
        List<Long> studentIds = StreamUtil.toMappedList(r -> r.getStudent(), managementDtos);
        if (!studentIds.isEmpty()) {
            Map<Long, List<DormitoryDto>> dormitoriesByStudent = studentDormitories(studentIds);
            for (BoardingSchoolManagementDto dto : managementDtos) {
                List<DormitoryDto> studentDorms = dormitoriesByStudent.get(dto.getStudent());
                if (studentDorms != null) {
                    DormitoryDto latestDorm = studentDorms.remove(0);
                    if (Boolean.FALSE.equals(latestDorm.getExpired())) {
                        dto.setLatestDorm(latestDorm);
                    } else {
                        dto.getPreviousDorms().add(latestDorm);
                    }
                    dto.getPreviousDorms().addAll(studentDorms);
                }

                boolean studentIsActive = StudentStatus.STUDENT_STATUS_ACTIVE.contains(dto.getStudentStatus());
                dto.setCanAddNew(Boolean.valueOf(studentIsActive));
                dto.setCanEditLatest(Boolean.valueOf(studentIsActive || dto.getLatestDorm() != null));
            }
        }
    }

    private Map<Long, List<DormitoryDto>> studentDormitories(List<Long> studentIds) {
        List<?> data = em.createNativeQuery("select d.id d_id, s.id s_id, r.id r_id, r.code r_code, b.code b_code, "
                + "d.valid_from, d.valid_thru, d.add_info, case when (?2 >= d.valid_from and ?2 <= d.valid_thru) "
                + "or ?2 < d.valid_from then false else true end as expired "
                + "from dormitory d "
                + "join student s on s.id = d.student_id "
                + "join room r on r.id = d.room_id "
                + "join building b on b.id = r.building_id "
                + "where s.id in (?1) order by d.valid_thru desc, d.valid_from desc")
                .setParameter(1, studentIds)
                .setParameter(2, JpaQueryUtil.parameterAsTimestamp(LocalDate.now()))
                .getResultList();

        return StreamUtil.nullSafeList(data).stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 1), Collectors.mapping(r -> {
                    DormitoryDto dto = new DormitoryDto();
                    dto.setId(resultAsLong(r, 0));
                    dto.setStudent(resultAsLong(r, 1));
                    dto.setRoom(resultAsLong(r, 2));
                    String roomCode = resultAsString(r, 4) + "-" + resultAsString(r, 3);
                    dto.setRoomObject(new AutocompleteResult(resultAsLong(r, 2), roomCode, roomCode));
                    dto.setValidFrom(resultAsLocalDate(r, 5));
                    dto.setValidThru(resultAsLocalDate(r, 6));
                    dto.setAddInfo(resultAsString(r, 7));
                    dto.setExpired(resultAsBoolean(r, 8));
                    return dto;
                }, Collectors.toCollection(ArrayList::new))));
    }

    public List<BoardingSchoolManagementCheckDto> checkBoardingSchoolResidents(
            List<BoardingSchoolManagementForm> form) {
        form = StreamUtil.nullSafeList(form).stream()
                .filter(f -> f.getLatestDorm() != null && f.getLatestDorm().getRoom() != null)
                .collect(Collectors.toList());

        Map<Long, BoardingSchoolResidentDto> modifiedRowsMap = getModifiedResidents(form);
        List<Long> modifiedDorms = modifiedRowsMap.values().stream().filter(r -> r.getDormitory() != null)
                .map(r -> r.getDormitory()).collect(Collectors.toList());

        Map<Long, List<BoardingSchoolResidentDto>> roomResidentsMap = new HashMap<>();
        if (form.size() > 0) {
            roomResidentsMap = getSavedResidents(form, modifiedDorms);
        }

        Map<Long, BoardingSchoolManagementCheckDto> checksMap = new HashMap<>();
        for (int i = 0; i < form.size(); i++) {
            BoardingSchoolManagementForm formDto = form.get(i);
            DormitoryForm latestDorm = formDto.getLatestDorm();

            BoardingSchoolManagementCheckDto checkDto = new BoardingSchoolManagementCheckDto();
            checkDto.setStudent(formDto.getStudent());
            checkDto.setFullname(formDto.getFullname());
            checkDto.setIdcode(formDto.getIdcode());
            checkDto.setDormitory(latestDorm.getId());
            checkDto.setRoom(latestDorm.getRoom());
            checkDto.setValidFrom(latestDorm.getValidFrom());
            checkDto.setValidThru(latestDorm.getValidThru());

            List<BoardingSchoolResidentDto> residents = roomResidentsMap.get(Long.valueOf(i));
            if (residents != null) {
                checkDto.getResidents().addAll(residents);
            }

            List<Long> dormIds = StreamUtil.nullSafeList(residents).stream().filter(r -> r.getDormitory() != null)
                    .map(r -> r.getDormitory()).collect(Collectors.toList());

            for (Long rowNr2 : modifiedRowsMap.keySet()) {
                BoardingSchoolResidentDto latestDormResident = modifiedRowsMap.get(rowNr2);

                if (latestDormResident.getDormitory() == null || !dormIds.contains(latestDormResident.getDormitory())) {
                    if (Long.valueOf(i).equals(rowNr2)) {
                        checkDto.getResidents().add(latestDormResident);
                    } else if (checkDto.getRoom().equals(latestDormResident.getRoom())) {
                        if (DateUtils.periodsOverlap(checkDto.getValidFrom(), checkDto.getValidThru(),
                                latestDormResident.getValidFrom(), latestDormResident.getValidThru())) {
                            checkDto.getResidents().add(latestDormResident);
                        }
                    }
                }
                checkDto.setDuplicateStudents(Boolean.valueOf(duplicateStudentsCheck(checkDto.getStudent(),
                        checkDto.getResidents())));
            }
            checksMap.put(Long.valueOf(i), checkDto);
        }

        List<BoardingSchoolManagementCheckDto> checks = new ArrayList<>(checksMap.values());
        setChecksRooms(checks);
        return checks;
    }

    private Map<Long, List<BoardingSchoolResidentDto>> getSavedResidents(List<BoardingSchoolManagementForm> form,
            List<Long> modifiedDorms) {
        JpaNativeQueryBuilder qb;

        String query = "";
        Map<String, Object> parameters = new HashMap<>();

        for (int i = 0; i < form.size(); i++) {
            BoardingSchoolManagementForm formDto = form.get(i);
            DormitoryForm bsDto = formDto.getLatestDorm();

            if (i > 0) {
                query += " union all ";
            }

            qb = new JpaNativeQueryBuilder("from dormitory d "
                    + "join room r on r.id = d.room_id "
                    + "join student s on s.id = d.student_id "
                    + "join person p on p.id = s.person_id");
            qb.requiredCriteria("d.room_id = :room_id" + i, "room_id" + i, bsDto.getRoom());
            qb.optionalCriteria("d.valid_from <= :thru" + i, "thru" + i, bsDto.getValidThru());
            qb.optionalCriteria("d.valid_thru >= :from" + i, "from" + i, bsDto.getValidFrom());
            qb.optionalCriteria("d.id not in (:modifiedDorms)", "modifiedDorms", modifiedDorms);

            query += qb.querySql(i + " row_nr, s.id s_id, p.firstname, p.lastname, p.idcode, d.id d_id, "
                    + "r.id r_id, d.valid_from, d.valid_thru", false);
            parameters.putAll(qb.queryParameters());
        }

        qb = new JpaNativeQueryBuilder("from (" + query + ") as f");
        List<?> data = qb.select("*", em, parameters).getResultList();
        return StreamUtil.nullSafeList(data).stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
                    BoardingSchoolResidentDto resident = new BoardingSchoolResidentDto();
                    resident.setStudent(resultAsLong(r, 1));
                    resident.setFullname(PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3)));
                    resident.setIdcode(resultAsString(r, 4));
                    resident.setDormitory(resultAsLong(r, 5));
                    resident.setRoom(resultAsLong(r, 6));
                    resident.setValidFrom(resultAsLocalDate(r, 7));
                    resident.setValidThru(resultAsLocalDate(r, 8));
                    return resident;
                }, Collectors.toList())));
    }

    private static Map<Long, BoardingSchoolResidentDto> getModifiedResidents(List<BoardingSchoolManagementForm> form) {
        Map<Long, BoardingSchoolResidentDto> modifiedResidents = new HashMap<>();

        for (int i = 0; i < form.size(); i++) {
            BoardingSchoolManagementForm formDto = form.get(i);
            DormitoryForm bsDto = formDto.getLatestDorm();

            modifiedResidents.put(Long.valueOf(i), new BoardingSchoolResidentDto(bsDto.getId(), 
                    formDto.getStudent(), formDto.getFullname(), formDto.getIdcode(), bsDto.getRoom(), 
                    bsDto.getValidFrom(), bsDto.getValidThru()));
        }
        return modifiedResidents;
    }

    private static boolean duplicateStudentsCheck(Long studentId, List<BoardingSchoolResidentDto> residents) {
        long test = StreamUtil.nullSafeList(residents).stream().filter(r -> r.getStudent().equals(studentId)).count();
        return test > 1;
    }

    public void setChecksRooms(List<BoardingSchoolManagementCheckDto> checks) {
        Set<Long> roomIds = StreamUtil.toMappedSet(c -> c.getRoom(), checks);

        Map<Long, RoomAutocompleteResult> rooms = new HashMap<>();
        if (!roomIds.isEmpty()) {
            List<?> data = em.createNativeQuery("select r.id r_id, b.id b_id, b.code b_code, r.code r_code, r.seats from room r "
                    + "join building b on r.building_id = b.id "
                    + "where r.id in (?1)")
                    .setParameter(1, roomIds).getResultList();
            rooms = StreamUtil.toMap(r -> resultAsLong(r, 0), r -> new RoomAutocompleteResult(resultAsLong(r, 0),
                    resultAsLong(r, 1), resultAsString(r, 2), resultAsString(r, 3), resultAsLong(r, 4)), data);
        }

        for (BoardingSchoolManagementCheckDto check : checks) {
            RoomAutocompleteResult room = rooms.get(check.getRoom());

            Long seats = room.getSeats();
            if (seats != null) {
                Long residentsCount = Long.valueOf(check.getResidents() != null ? check.getResidents().size() : 0);
                check.setOccupied(Boolean.valueOf(residentsCount.compareTo(seats) > 0));
            }

            check.setRoomObject(room);
        }
    }

    public void saveBoardingSchoolResidents(List<BoardingSchoolManagementForm> form) {
        for (BoardingSchoolManagementForm row : form) {
            Student student = em.getReference(Student.class, row.getStudent());
            if (row.getDormitory() != null) {
                student.setDormitory(em.getReference(Classifier.class, row.getDormitory()));
            }

            List<DormitoryForm> dorms = row.getPreviousDorms();
            if (row.getLatestDorm() != null && row.getLatestDorm().getRoom() != null) {
                dorms.add(row.getLatestDorm());
            }

            EntityUtil.bindEntityCollection(student.getBoardingSchools(), Dormitory::getId, dorms, DormitoryForm::getId,
                    dormForm -> {
                        Dormitory dorm = EntityUtil.bindToEntity(row, new Dormitory(), "student", "room");
                        dorm.setStudent(em.getReference(Student.class, row.getStudent()));
                        dorm.setValidFrom(dormForm.getValidFrom());
                        dorm.setValidThru(dormForm.getValidThru());
                        dorm.setRoom(EntityUtil.getOptionalOne(Room.class, dormForm.getRoom(), em));
                        dorm.setAddInfo(dormForm.getAddInfo());
                        return dorm;
                    }, (dormForm, dorm) -> {
                        dorm.setValidFrom(dormForm.getValidFrom());
                        dorm.setValidThru(dormForm.getValidThru());
                        dorm.setRoom(EntityUtil.getOptionalOne(Room.class, dormForm.getRoom(), em));
                        dorm.setAddInfo(dormForm.getAddInfo());
                    });
        }
    }

    public Page<BoardingSchoolRoomDto> rooms(HoisUserDetails user, BoardingSchoolRoomCommand criteria,
            Pageable pageable) {
        JpaNativeQueryBuilder qb;
        Map<String, Object> parameters = new HashMap<>();

        // residents count query
        qb = new JpaNativeQueryBuilder("from dormitory d");
        qb.optionalCriteria("d.valid_from <= :thru", "thru", criteria.getThru());
        qb.optionalCriteria("d.valid_thru >= :from", "from", criteria.getFrom());
        qb.filter("d.room_id = r.id");

        if (Boolean.TRUE.equals(criteria.getShowValid())) {
            qb.optionalCriteria("d.valid_from <= :today and d.valid_thru >= :today", "today", LocalDate.now());
        }
        qb.groupBy("r.id");

        String residentsCountQuery = qb.querySql("count(d.id)", false);
        parameters.putAll(qb.queryParameters());

        // rooms query
        qb = new JpaNativeQueryBuilder("from room r join building b on b.id = r.building_id");
        qb.requiredCriteria("b.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("b.id = :buildingId", "buildingId", criteria.getBuilding());
        qb.optionalCriteria("r.id = :roomId", "roomId", criteria.getRoom());
        qb.filter("b.is_dormitory = true");

        String roomsQuery = qb.querySql(ROOM_SELECT + ", (" + residentsCountQuery + ") residents", false);
        parameters.putAll(qb.queryParameters());

        // combine queries
        qb = new JpaNativeQueryBuilder("from (" + roomsQuery + ") as rooms").sort(pageable);
        if (Boolean.TRUE.equals(criteria.getShowFreeRooms())) {
            qb.filter("rooms.seats - coalesce(rooms.residents, 0) is null or "
                    + "rooms.seats - coalesce(rooms.residents, 0) > 0");
        }

        Page<BoardingSchoolRoomDto> page = JpaQueryUtil.pagingResult(qb,
                "*, rooms.seats - coalesce(rooms.residents, 0) as free_seats", parameters, em, pageable).map(r -> {
            BoardingSchoolRoomDto dto = new BoardingSchoolRoomDto();
            dto.setBuilding(new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1),
                resultAsString(r, 1)));
            dto.setRoom(new RoomAutocompleteResult(resultAsLong(r, 2), resultAsLong(r, 0), resultAsString(r, 3),
                resultAsString(r, 4), resultAsLong(r, 5)));

            Long freeSeats = resultAsLong(r, 7);
            if (freeSeats != null) {
                dto.setFreeSeats(freeSeats.intValue() > 0 ? freeSeats : Long.valueOf(0));
                dto.setOccupied(Boolean.valueOf(freeSeats.intValue() <= 0));
            }
            return dto;
        });

        if (Boolean.TRUE.equals(criteria.getShowResidents()) && page.getContent().size() > 0) {
            setRoomResidents(criteria, page.getContent());
        }
        return page;
    }

    private void setRoomResidents(BoardingSchoolRoomCommand criteria, List<BoardingSchoolRoomDto> rooms) {
        JpaNativeQueryBuilder qb;

        String query = "";
        Map<String, Object> parameters = new HashMap<>();

        for (int i = 0; i < rooms.size(); i++) {
            if (i > 0) {
                query += " union all ";
            }
            BoardingSchoolRoomDto dto = rooms.get(i);

            qb = new JpaNativeQueryBuilder("from dormitory d "
                    + "join student s on s.id = d.student_id "
                    + "join person p on p.id = s.person_id");
            qb.requiredCriteria("d.room_id = :room_id" + i, "room_id" + i, dto.getRoom());
            qb.optionalCriteria("d.valid_from <= :thru", "thru", criteria.getThru());
            qb.optionalCriteria("d.valid_thru >= :from", "from", criteria.getFrom());

            if (Boolean.TRUE.equals(criteria.getShowValid())) {
                qb.optionalCriteria("d.valid_from <= :today and d.valid_thru >= :today", "today", LocalDate.now());
            }

            query += qb.querySql(i + " row_nr, s.id s_id, p.firstname, p.lastname, p.idcode,"
                    + " d.valid_from, d.valid_thru", false);
            parameters.putAll(qb.queryParameters());
        }

        qb = new JpaNativeQueryBuilder("from (" + query + ") as f");
        List<?> data = qb.select("*", em, parameters).getResultList();
        Map<Long, List<BoardingSchoolResidentDto>> roomResidents = StreamUtil.nullSafeList(data).stream()
                .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> {
                    BoardingSchoolResidentDto neighbourDto = new BoardingSchoolResidentDto();
                    neighbourDto.setStudent(resultAsLong(r, 1));
                    neighbourDto.setFullname(PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3)));
                    neighbourDto.setIdcode(resultAsString(r, 4));
                    neighbourDto.setValidFrom(resultAsLocalDate(r, 5));
                    neighbourDto.setValidThru(resultAsLocalDate(r, 6));
                    return neighbourDto;
                }, Collectors.toList())));

        for (int i = 0; i < rooms.size(); i++) {
            BoardingSchoolRoomDto dto = rooms.get(i);
            List<BoardingSchoolResidentDto> residents = roomResidents.get(Long.valueOf(i));
            dto.setResidents(residents);
        }
    }
}
