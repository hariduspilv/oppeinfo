package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.StudentCardUtil.isMissing;
import static ee.hitsa.ois.util.StudentCardUtil.isOrdered;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.StudentCardStatus;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentCardUtil;
import ee.hitsa.ois.web.commandobject.student.StudentCardSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.ClassifierDto;
import ee.hitsa.ois.web.dto.student.StudentCardData;
import ee.hitsa.ois.web.dto.student.StudentCardSearchDto;

@Transactional
@Service
public class StudentCardService {

    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierService classifierService;
    @Autowired
    private XlsService xlsService;

    private static final String SEARCH_SELECT = "s.id student_id, p.firstname, p.lastname, sg.id sg_id, "
            + "sg.code sg_code, case when s.ois_file_id is not null then true else false end picture_exists, "
            + "s.status_code, s.student_card, s.student_card_status_code, s.student_card_valid_thru, "
            + "s.student_card_given_dt, s.student_card_returned_dt, s.is_student_card_repetitive, "
            + "s.is_student_card_given, s.is_student_card_returned, s.type_code as studentType";

    private List<StudentCardData> searchStudentCardData(HoisUserDetails user, StudentCardSearchCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
                + "join person p on p.id = s.person_id "
                + "join school sc on sc.id = s.school_id "
                + "left join student_group sg on sg.id = s.student_group_id");
        qb.sort(new Sort(Direction.ASC, "p.lastname, p.firstname"));
        setSearchCriteria(qb, user, criteria);
        
        List<?> results = qb.select("sc.name_et, sc.name_en, p.firstname, p.lastname, p.idcode,"
                + " s.student_card, s.student_card_valid_thru, s.ois_file_id, s.id", em).getResultList();
        return StreamUtil.toMappedList(r -> {
            StudentCardData data = new StudentCardData();
            data.setStudentId(resultAsLong(r, 8));
            data.setSchool(new AutocompleteResult(null, resultAsString(r, 0), resultAsString(r, 1)));
            data.setStudentName(PersonUtil.fullname(resultAsString(r, 2), resultAsString(r, 3)));
            data.setStudentIdcode(resultAsString(r, 4));
            data.setStudentCardNr(resultAsString(r, 5));
            data.setValidThru(resultAsLocalDate(r, 6));
            data.setPhotoId(resultAsLong(r, 7));
            return data;
        }, results);
    }
    
    private List<StudentCardSearchDto> search(HoisUserDetails user, StudentCardSearchCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
                + "join person p on p.id = s.person_id "
                + "left join student_group sg on sg.id = s.student_group_id");
        qb.sort(new Sort(Direction.ASC, "p.lastname, p.firstname"));
        setSearchCriteria(qb, user, criteria);
        ClassifierCache cache = new ClassifierCache(classifierService);

        List<?> results = qb.select(SEARCH_SELECT, em).getResultList();
        return StreamUtil.toMappedList(r -> {
            return mapStudentCardSearchDto(r, new StudentCardSearchDto(), cache);
        }, results);
    }
    
    public Page<StudentCardSearchDto> search(HoisUserDetails user, StudentCardSearchCommand criteria,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s "
                + "join person p on p.id = s.person_id "
                + "left join student_group sg on sg.id = s.student_group_id").sort(pageable);
        setSearchCriteria(qb, user, criteria);
        ClassifierCache cache = new ClassifierCache(classifierService);

        return JpaQueryUtil.pagingResult(qb, SEARCH_SELECT, em, pageable).map(r -> {
            return mapStudentCardSearchDto(r, new StudentCardSearchDto(), cache);
        });
    }
    
    private static StudentCardSearchDto mapStudentCardSearchDto(Object row, StudentCardSearchDto dto, ClassifierCache cache) {
        dto.setStudentId(resultAsLong(row, 0));
        dto.setFullname(PersonUtil.fullnameOptionalGuest(resultAsString(row, 1), resultAsString(row, 2), resultAsString(row, 15)));
        String studentGroupCode = resultAsString(row, 4);
        dto.setStudentGroup(new AutocompleteResult(resultAsLong(row, 3), studentGroupCode, studentGroupCode));
        dto.setPictureExists(resultAsBoolean(row, 5));
        dto.setIsStudentActive(Boolean.valueOf(StudentStatus.STUDENT_STATUS_ACTIVE.contains(resultAsString(row, 6))));
        dto.setCardNr(resultAsString(row, 7));
        String cardStatus = resultAsString(row, 8);
        dto.setStatus(cardStatus == null 
                ? ClassifierDto.of(cache.getByCode(StudentCardStatus.OPILASPILET_STAATUS_P.name(), MainClassCode.OPILASPILET_STAATUS))
                : ClassifierDto.of(cache.getByCode(cardStatus, MainClassCode.OPILASPILET_STAATUS)));
        dto.setValidThru(resultAsLocalDate(row, 9));
        dto.setGivenDate(resultAsLocalDate(row, 10));
        dto.setReturnedDate(resultAsLocalDate(row, 11));
        dto.setIsStudentCardRepetitive(resultAsBoolean(row, 12));
        dto.setGiven(resultAsBoolean(row, 13));
        dto.setReturned(resultAsBoolean(row, 14));
        return dto;
    }
    
    /**
     * Tables to be in sql:
     * - student s
     * - person p
     * - student_group sg
     * 
     * @param qb
     * @param user
     * @param criteria
     */
    private static void setSearchCriteria(JpaNativeQueryBuilder qb, HoisUserDetails user, StudentCardSearchCommand criteria) {
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        if (user.isLeadingTeacher()) {
            qb.requiredCriteria("exists(select c.id from curriculum_version cv "
                    + "join curriculum c on c.id = cv.curriculum_id "
                    + "where s.curriculum_version_id = cv.id and c.id in (:userCurriculumIds))",
                    "userCurriculumIds", user.getCurriculumIds());
        }

        qb.optionalCriteria("sg.id = :groupId", "groupId", criteria.getStudentGroup() != null ? criteria.getStudentGroup().getId() : null);
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", 
                "p.firstname || ' ' || p.lastname"), "name", criteria.getName());
        qb.optionalCriteria("p.idcode = :idcode", "idcode", criteria.getIdcode());
        qb.optionalContains("s.student_card", "cardNr", criteria.getCardNr());
        if (criteria.getStatus() != null && criteria.getStatus().contains(StudentCardStatus.OPILASPILET_STAATUS_P.name())) {
            qb.optionalCriteria("(s.student_card_status_code in (:status) or s.student_card_status_code is null)", "status", criteria.getStatus());
        } else {
            qb.optionalCriteria("s.student_card_status_code in (:status)", "status", criteria.getStatus());
        }

        if (!Boolean.TRUE.equals(criteria.getNotActiveStudents())) {
            qb.requiredCriteria("s.status_code in (:studentStatus)", "studentStatus",
                    StudentStatus.STUDENT_STATUS_ACTIVE);
        }
    }

    public void updateStudentCards(List<StudentCardSearchDto> studentCardDtos) {
        ClassifierCache cache = new ClassifierCache(classifierService);
        StreamUtil.nullSafeList(studentCardDtos).forEach(dto -> {
            save(em.getReference(Student.class, dto.getStudentId()), dto, cache);
        });
    }
    
    public StudentCardSearchDto orderRepetition(Student student) {
        clear(student);
        return StudentCardSearchDto.of(student);
    }
    
    public byte[] excel(HoisUserDetails user, StudentCardSearchCommand criteria) {
        Map<String, Object> data = new HashMap<>();
        data.put("results", search(user, criteria));
        return xlsService.generate("studentcardexcel.xlsx", data);
    }
    
    private byte[] orderExcel(List<StudentCardData> students) {
        Map<String, Object> data = new HashMap<>();
        data.put("students", students);
        return xlsService.generate("studentcardorder.xlsx", data);
    }
    
    /**
     * Writes bytes into response to evade OutOfMemory.
     * 
     * @param response
     * @param user
     * @param criteria
     */
    public void order(HttpServletResponse response, HoisUserDetails user, StudentCardSearchCommand criteria) {
        criteria.setStatus(Arrays.asList(StudentCardStatus.OPILASPILET_STAATUS_P.name()));
        criteria.setNotActiveStudents(Boolean.FALSE);
        
        List<StudentCardData> data = searchStudentCardData(user, criteria);
        ClassifierCache cache = new ClassifierCache(classifierService);
        data.forEach(d -> {
            d.setValidThru(null);
            d.setStudentCardNr(null);
            order(em.getReference(Student.class, d.getStudentId()), cache);
        });
        
        orderProcessZip(response, "opilaspilet_telli", data);
    }
    
    public void orderExtension(HttpServletResponse response, HoisUserDetails user, StudentCardSearchCommand criteria) {
        criteria.setStatus(Arrays.asList(
                StudentCardStatus.OPILASPILET_STAATUS_K.name(),
                StudentCardStatus.OPILASPILET_STAATUS_G.name()));
        criteria.setNotActiveStudents(Boolean.FALSE);
        
        List<StudentCardData> data = searchStudentCardData(user, criteria);
        data.forEach(d -> d.setValidThru(null));
        
        orderProcessZip(response, "opilaspilet_tellipikendus", data);
    }
    
    public void orderAgain(HttpServletResponse response, HoisUserDetails user, StudentCardSearchCommand criteria) {
        criteria.setStatus(Arrays.asList(
                StudentCardStatus.OPILASPILET_STAATUS_P.name(),
                StudentCardStatus.OPILASPILET_STAATUS_T.name()));
        criteria.setNotActiveStudents(Boolean.FALSE);
        
        List<StudentCardData> data = searchStudentCardData(user, criteria);
        ClassifierCache cache = new ClassifierCache(classifierService);
        data.forEach(d -> {
            d.setValidThru(null);
            d.setStudentCardNr(null);
            order(em.getReference(Student.class, d.getStudentId()), cache);
        });
        
        orderProcessZip(response, "opilaspilet_telliuuesti", data);
    }
    
    private void orderProcessZip(HttpServletResponse response, String filename, List<StudentCardData> data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMYYYY");
        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            HttpUtil.zip(response, String.format("%s_%s.zip", filename, formatter.format(LocalDate.now())));
            
            ZipEntry excel = new ZipEntry(String.format("%s_%s.xlsx", filename, formatter.format(LocalDate.now())));
            zipOut.putNextEntry(excel);
            zipOut.write(orderExcel(data));
            
            Map<String, StudentCardData> mappedByFilename = data.stream()
                    .filter(d -> d.getPhotoId() != null)
                    .collect(Collectors.toMap(
                            d -> d.getStudentIdcode() != null ? d.getStudentIdcode() : d.getStudentId().toString(),
                            d -> d,
                            (o, n) -> o));
            for (Entry<String, StudentCardData> entry : mappedByFilename.entrySet()) {
                zipOut.flush();
                // Every loop it should get bytes from DB.
                OisFile file = em.getReference(OisFile.class, entry.getValue().getPhotoId());
                ZipEntry zipEntry = new ZipEntry(String.format("%s.%s",
                        entry.getKey(), getFileExtension(file.getFname())));
                zipOut.putNextEntry(zipEntry);
                zipOut.write(file.getFdata(), 0, file.getFdata().length);
                // Forget instances of OisFile, we don't need to store MBs of data.
                em.getEntityManagerFactory().getCache().evict(OisFile.class);
            }
            zipOut.closeEntry();
        } catch (IOException e) {
            throw new HoisException("Exception occured during making zip file.", e);
        }
    }
    
    public void save(Student student, StudentCardSearchDto dto) {
        save(student, dto, null);
    }
    
    private void save(Student student, StudentCardSearchDto dto, ClassifierCache cache) {
        if (StringUtils.isBlank(dto.getCardNr())) {
            student.setStudentCard(null);
            student.setStudentCardValidThru(null);
            StudentCardUtil.setReturned(student, Boolean.FALSE, null);
            StudentCardUtil.setGiven(student, Boolean.FALSE, null);
            if (!(isMissing(student) || isOrdered(student))) {
                setCardStatus(student, StudentCardStatus.OPILASPILET_STAATUS_P, cache);
            }
        } else {
            student.setStudentCard(dto.getCardNr());
            student.setStudentCardValidThru(dto.getValidThru());
            if (Boolean.TRUE.equals(dto.getReturned())) {
                setCardStatus(student, StudentCardStatus.OPILASPILET_STAATUS_R, cache);
                StudentCardUtil.setReturned(student, dto.getReturned(), dto.getReturnedDate());
            } else if (Boolean.TRUE.equals(dto.getGiven())) {
                setCardStatus(student, StudentCardStatus.OPILASPILET_STAATUS_G, cache);
                StudentCardUtil.setGiven(student, dto.getGiven(), dto.getGivenDate());
            } else {
                setCardStatus(student, StudentCardStatus.OPILASPILET_STAATUS_K, cache);
                StudentCardUtil.setReturned(student, Boolean.FALSE, null);
                StudentCardUtil.setGiven(student, Boolean.FALSE, null);
            }
        }
    }
    
    public void clear(Student student) {
        clear(student, null);
    }
    
    private void clear(Student student, ClassifierCache cache) {
        student.setStudentCard(null);
        student.setStudentCardValidThru(null);
        StudentCardUtil.setReturned(student, Boolean.FALSE, null);
        StudentCardUtil.setGiven(student, Boolean.FALSE, null);
        setCardStatus(student, StudentCardStatus.OPILASPILET_STAATUS_P, cache);
        student.setIsStudentCardRepetitive(Boolean.TRUE);
    }
    
    public void order(Student student) {
        order(student, null);
    }
    
    private void order(Student student, ClassifierCache cache) {
        setCardStatus(student, StudentCardStatus.OPILASPILET_STAATUS_T, cache);
    }
    
    public void setCardStatus(Student student, StudentCardStatus status) {
        setCardStatus(student, status, null);
    }
    
    private void setCardStatus(Student student, StudentCardStatus status, ClassifierCache cache) {
        if (student != null && status != null) {
            if (cache != null) {
                student.setStudentCardStatus(cache.getByCode(status.name(), MainClassCode.OPILASPILET_STAATUS));
            } else {
                student.setStudentCardStatus(em.getReference(Classifier.class, status.name()));
            }
        }
    }
    
    private static String getFileExtension(String filename) {
        String[] split = filename.split("\\.");
        return split.length == 0 ? filename : split[split.length - 1];
    }
}
