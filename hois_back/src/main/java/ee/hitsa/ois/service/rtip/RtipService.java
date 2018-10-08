package ee.hitsa.ois.service.rtip;

import static ee.hitsa.ois.util.JpaQueryUtil.parameterAsTimestamp;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.config.RtipProperties;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.teacher.TeacherAbsence;
import ee.hitsa.ois.domain.teacher.TeacherPositionEhis;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.Sex;
import ee.hitsa.ois.service.ClassifierService;
import ee.hitsa.ois.service.SchoolService;
import ee.hitsa.ois.service.SchoolService.SchoolType;
import ee.hitsa.ois.service.TimetableEventService;
import ee.hitsa.ois.service.UserService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hois.soap.LogResult;
import ee.hois.xroad.rtip.generated.TOOTAJAPOHIANDMEDX;
import ee.hois.xroad.rtip.generated.TootajaPohiandmedRequestType;
import ee.hois.xroad.rtip.generated.ZABSENCE;
import ee.hois.xroad.rtip.generated.ZEMPLOEE1;
import ee.hois.xroad.rtip.generated.ZEMPLOEESRequestType;
import ee.hois.xroad.rtip.generated.ZEMPLOEESResponseType;
import ee.hois.xroad.rtip.generated.ZHRPPAPOHIANDMED0002;
import ee.hois.xroad.rtip.generated.ZHRPPAPOHIANDMED0016;
import ee.hois.xroad.rtip.generated.ZHRPPATOOTAJAPOHIANDMED;
import ee.hois.xroad.rtip.generated.ZLEAVERS;
import ee.hois.xroad.rtip.service.RtipClient;

@Transactional(TxType.REQUIRES_NEW)
@Service
public class RtipService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String COMPANY_CODE_FOR_TOOTAJA_POHIANDMED_REQUEST = "G000";
    private static final String SELECTED_FILTER_FOR_TOOTAJA_POHIANDMED_REQUEST = "X";
    private static final String EHIS_AMETIKOHT_MUU = "EHIS_AMETIKOHT_MUU";
    private static final String EHIS_CONTRACT_TYPE_VOCATIONAL = "EHIS_LEPING_TL";
    private static final Map<String, Sex> SEX = new HashMap<>();
    static {
        SEX.put("Hr", Sex.SUGU_M);
        SEX.put("Pr", Sex.SUGU_N);
    }
    private static final Map<String, String> EHIS_CONTRACT_TYPE = new HashMap<>();
    static {
        EHIS_CONTRACT_TYPE.put("01", "EHIS_LEPING_TAHTAJATU");
        EHIS_CONTRACT_TYPE.put("02", "EHIS_LEPING_TAHTAJALINE");
    }

    @Autowired
    private ClassifierService classifierService;
    @Autowired
    private EntityManager em;
    @Autowired
    private RtipClient rtipClient;
    @Autowired
    private RtipLogService rtipLogService;
    @Autowired
    private RtipProperties rtipProperties;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private TimetableEventService timetableEventService;
    @Autowired
    private UserService userService;

    // TODO remove - for testing only
    public void syncSchool(HoisUserDetails user, LocalDate from, LocalDate to) {
        School school = em.getReference(School.class, user.getSchoolId());
        assertRtipSchoolCode(school);
        syncSchool(school, from, to);
    }

    /**
     * Synchronize all school teacher absences from RTIP
     *
     * @param school
     */
    public void syncSchool(School school, LocalDate from, LocalDate to) {
        ZEMPLOEESRequestType request = new ZEMPLOEESRequestType();
        request.setCOMPANYCODE(school.getRtipSchoolCode());
        request.setDATEFROM(from);
        request.setDATETO(to);
        request.setLAHKUJAD(new ZEMPLOEESRequestType.LAHKUJAD());
        request.setPUUDUMINE(new ZEMPLOEESRequestType.PUUDUMINE());
        request.setTOOTAJAD(new ZEMPLOEESRequestType.TOOTAJAD());

        withResponse(rtipClient.zEMPLOEES(rtipProperties.xroadHeader("Z_EMPLOEES"), request), school, (result) -> {
            List<?> data = teacherIds(school.getId());
            Map<String, Long> teachersByIdcode = data.stream().filter(r -> StringUtils.hasText(resultAsString(r, 0))).collect(Collectors.toMap(r -> resultAsString(r, 0), r -> resultAsLong(r, 1), (o, n) -> o));
            Map<String, Long> teacherByRtipNr = data.stream().filter(r -> StringUtils.hasText(resultAsString(r, 2))).collect(Collectors.toMap(r -> resultAsString(r, 2), r -> resultAsLong(r, 1), (o, n) -> o));

            if(result.getTOOTAJAD() != null && !result.getTOOTAJAD().getItem().isEmpty()) {
                // update teacher data
                SchoolType schoolType = schoolType(school);
                List<Classifier> ehisPositions = classifierService.findAllByMainClassCode(MainClassCode.EHIS_AMETIKOHT);
                for(ZEMPLOEE1 emploee : result.getTOOTAJAD().getItem()) {
                    Long teacherId = teacherByRtipNr.get(emploee.getPERNR());
                    if(teacherId == null) {
                        teacherId = teachersByIdcode.get(emploee.getIKOOD());
                    }
                    if(teacherId != null) {
                        // last parameter - position is_vocational flag logic
                        teacherUpdated(teacherId, emploee, teacherByRtipNr, schoolType, ehisPositions);
                    }
                }
            }
            syncAbsences(result, teacherByRtipNr);
            if(result.getLAHKUJAD() != null) {
                // mark teacher as not active
                for(ZLEAVERS leaver : result.getLAHKUJAD().getItem()) {
                    Long teacherId = teacherByRtipNr.get(leaver.getPERNR());
                    if(teacherId != null) {
                        teacherLeft(teacherId);
                    }
                }
            }
        });
    }

    /**
     * Synchronize single teacher absences from RTIP
     *
     * @param teacher
     */
    public void syncTeacher(Teacher teacher) {
        School school = teacher.getSchool();
        assertRtipSchoolCode(school);

        ZABSENCE absence = new ZABSENCE();
        absence.setPERNR(teacher.getRtipNr());
        ZEMPLOEESRequestType.PUUDUMINE list = new ZEMPLOEESRequestType.PUUDUMINE();
        list.getItem().add(absence);

        ZEMPLOEESRequestType request = new ZEMPLOEESRequestType();
        request.setCOMPANYCODE(school.getRtipSchoolCode());
        request.setDATEFROM(LocalDate.now().minusMonths(1));
        request.setDATETO(LocalDate.now().plusYears(1));
        request.setPUUDUMINE(list);

        withResponse(rtipClient.zEMPLOEES(rtipProperties.xroadHeader("Z_EMPLOEES"), request), school, (result) -> {
            syncAbsences(result, Collections.singletonMap(teacher.getRtipNr(), teacher.getId()));
        });
    }

    /**
     * Synchronize all school teacher data from RTIP
     *
     * @param user
     */
    public void syncSchoolTeacherData(HoisUserDetails user) {
        School school = em.getReference(School.class, user.getSchoolId());
        assertRtipSchoolCode(school);

        syncSchoolTeacherData(school);
    }

    /**
     * Sync all school teachers
     *
     * @param school
     */
    public void syncSchoolTeacherData(School school) {
        TootajaPohiandmedRequestType request = createTootajaPohiandmedRequest(school);

        withResponse(rtipClient.tootajaPohiandmed(rtipProperties.xroadHeader("tootajaPohiandmed"), request), school, (result) -> {
            if(result.getTOOTAJAPOHIANDMED() == null || result.getTOOTAJAPOHIANDMED().getItem().isEmpty()) {
                return;
            }

            SchoolType schoolType = schoolType(school);
            List<?> data = teacherIds(school.getId());
            Map<String, Long> teachersByIdcode = data.stream().filter(r -> StringUtils.hasText(resultAsString(r, 0))).collect(Collectors.toMap(r -> resultAsString(r, 0), r -> resultAsLong(r, 1), (o, n) -> o));
            Map<String, Long> teacherByRtipNr = data.stream().filter(r -> StringUtils.hasText(resultAsString(r, 2))).collect(Collectors.toMap(r -> resultAsString(r, 2), r -> resultAsLong(r, 1), (o, n) -> o));
            String rtipSchoolCode = school.getRtipSchoolCode();
            for(ZHRPPATOOTAJAPOHIANDMED td : result.getTOOTAJAPOHIANDMED().getItem()) {
                // check for matching PERSONALI_ALA (school rtip code)
                if(td.getTOOTAJAPOHIANDMED0001() != null) {
                    if(!td.getTOOTAJAPOHIANDMED0001().getItem().stream().anyMatch(r -> rtipSchoolCode.equals(r.getPERSONALIALA()))) {
                        // wrong school
                        continue;
                    }
                }
                String rtipNr = td.getPERSONALINR();
                Long teacherId = teacherByRtipNr.get(rtipNr);
                if(teacherId == null) {
                    teacherId = teachersByIdcode.get(td.getISIKUKOOD());
                }
                if(teacherId == null) {
                    // unknown teacher
                    continue;
                }
                Teacher teacher = syncTeacherRtipNr(teacher(teacherId), rtipNr, teacherByRtipNr);
                if(td.getTOOTAJAPOHIANDMED0002() != null && !td.getTOOTAJAPOHIANDMED0002().getItem().isEmpty()) {
                    ZHRPPAPOHIANDMED0002 td02 = td.getTOOTAJAPOHIANDMED0002().getItem().get(0);
                    Person person = teacher.getPerson();
                    Sex sex = SEX.get(td02.getSUGU());
                    if(sex != null && !ClassifierUtil.equals(sex, person.getSex())) {
                        person.setSex(em.getReference(Classifier.class, sex.name()));
                    }
                    person.setBirthdate(td02.getSYNNIKUUPAEV());
                    em.merge(person);
                }
                if(td.getTOOTAJAPOHIANDMED0016() != null) {
                    // update contract data
                    for(ZHRPPAPOHIANDMED0016 data16 : td.getTOOTAJAPOHIANDMED0016().getItem()) {
                        // matching contract is determined based on start date and contract type
                        TeacherPositionEhis position = findPositionEhis(teacher, data16.getKEHTIVALATES());
                        String contractType = isOnlyVocational(schoolType) ? EHIS_CONTRACT_TYPE_VOCATIONAL : EHIS_CONTRACT_TYPE.get(data16.getLEPINGUTYYP());
                        if(position != null && contractType != null && contractType.equals(EntityUtil.getNullableCode(position.getContractType()))) {
                            updateContractEnd(position, data16.getLEPINGULOPP());
                            break;
                        }
                    }
                }
                EntityUtil.save(teacher, em);
            }
        });
    }

    /**
     * Find schools which have RTIP id
     *
     * @return
     */
    public List<School> rtipSchools() {
        return em.createQuery("select s from School s where s.rtipSchoolCode is not null", School.class).getResultList();
    }

    private void syncAbsences(ZEMPLOEESResponseType result, Map<String, Long> teacherByRtipNr) {
        if(result.getPUUDUMINE() != null) {
            // insert teacher absences
            for(ZABSENCE absence : result.getPUUDUMINE().getItem()) {
                Long teacherId = teacherByRtipNr.get(absence.getPERNR());
                if(teacherId != null) {
                    teacherAbsence(teacherId, absence);
                }
            }
        }
    }

    private void teacherAbsence(Long teacherId, ZABSENCE rtipAbsence) {
        LocalDate startDate = rtipAbsence.getBEGDA();
        LocalDate endDate = rtipAbsence.getENDDA();
        String reason = rtipAbsence.getATEXT();
        if(startDate == null || endDate == null || !StringUtils.hasText(reason)) {
            // ignore absences without start/end or without reason
            return;
        }

        // check that absence is not already present - for matching all parameters should be same
        List<?> data = em.createNativeQuery("select ta.id from teacher_absence ta where ta.start_date = ?1 and ta.end_date = ?2 and ta.reason = ?3 and ta.teacher_id = ?4")
                .setParameter(1, parameterAsTimestamp(startDate))
                .setParameter(2, parameterAsTimestamp(endDate))
                .setParameter(3, reason)
                .setParameter(4, teacherId).setMaxResults(1).getResultList();

        if(data.isEmpty()) {
            // new absence
            TeacherAbsence absence = new TeacherAbsence();
            absence.setTeacher(em.getReference(Teacher.class, teacherId));
            absence.setStartDate(startDate);
            absence.setEndDate(endDate);
            absence.setReason(reason);
            em.persist(absence);

            // update timetable events
            timetableEventService.createEvents(absence);
        }
    }

    private void teacherLeft(Long teacherId) {
        Teacher teacher = teacher(teacherId);
        teacher.setIsActive(Boolean.FALSE);
        teacher = EntityUtil.save(teacher, em);
        userService.disableUser(teacher, LocalDate.now());
    }

    private void teacherUpdated(Long teacherId, ZEMPLOEE1 emploee, Map<String, Long> teacherByRtipNr, SchoolType schoolType, List<Classifier> ehisPositions) {
        Teacher teacher = syncTeacherRtipNr(teacher(teacherId), emploee.getPERNR(), teacherByRtipNr);

        Person person = teacher.getPerson();
        updateNotEmptyField(person::setFirstname, emploee.getVORNA());
        updateNotEmptyField(person::setLastname, emploee.getNACHN());
        em.merge(person);

        updateNotEmptyField(teacher::setPhone, emploee.getLAUATELEFON());
        updateNotEmptyField(teacher::setEmail, emploee.getEMAIL());

        // sync position
        TeacherPositionEhis position = findPositionEhis(teacher, emploee.getALGUSKP());
        if(position == null) {
            // create new record, if possible (decided inside fillContractData)
            position = new TeacherPositionEhis();
            position.setTeacher(teacher);
            // TODO is this correct?
            position.setIsTeacher(Boolean.TRUE);
            position.setIsVocational(Boolean.valueOf(isOnlyVocational(schoolType)));
            position.setContractStart(emploee.getALGUSKP());
            position.setLoad(emploee.getKOORMUS() != null ? emploee.getKOORMUS().divide(BigDecimal.valueOf(100)) : null);
            // load contract data with second query
            fillContractData(teacher, position);
            // contract type is indicator for creating success
            if(position.getContractType() != null) {
                // try to set position
                String positionName = emploee.getPLSTX();
                Classifier positionCl = null;
                if(StringUtils.hasText(positionName)) {
                   // try to find matching value in our classifier
                   positionCl = ehisPositions.stream().filter(r -> positionName.equalsIgnoreCase(r.getNameEt())).findAny().orElse(null);
                   if(positionCl == null) {
                       position.setPositionSpecificationEt(positionName);
                   }
                }
                if(positionCl == null && schoolType.isHigher()) {
                    positionCl = em.getReference(Classifier.class, EHIS_AMETIKOHT_MUU);
                }
                position.setPosition(positionCl);
                EntityUtil.save(position, em);
            }
        } else {
            fillContractData(teacher, position);
        }

        EntityUtil.save(teacher, em);
    }

    private static TeacherPositionEhis findPositionEhis(Teacher teacher, LocalDate startDate) {
        return teacher.getTeacherPositionEhis().stream().filter(r -> r.getContractStart().equals(startDate)).findAny().orElse(null);
    }

    private void fillContractData(Teacher teacher, TeacherPositionEhis position) {
        School school = teacher.getSchool();
        TootajaPohiandmedRequestType request = createTootajaPohiandmedRequest(school);
        request.setPERSONALINR(teacher.getRtipNr());

        withResponse(rtipClient.tootajaPohiandmed(rtipProperties.xroadHeader("tootajaPohiandmed"), request), school, (result) -> {
            if(result.getTOOTAJAPOHIANDMED() != null) {
                for(ZHRPPATOOTAJAPOHIANDMED teacherData : result.getTOOTAJAPOHIANDMED().getItem()) {
                    // checks that we really have same person
                    if(!samePerson(teacher, teacherData)) {
                        continue;
                    }
                    if(teacherData.getTOOTAJAPOHIANDMED0016() != null) {
                        for(ZHRPPAPOHIANDMED0016 data16 : teacherData.getTOOTAJAPOHIANDMED0016().getItem()) {
                            String contractType = EHIS_CONTRACT_TYPE.get(data16.getLEPINGUTYYP());
                            if(contractType != null && position.getContractStart().equals(data16.getKEHTIVALATES())) {
                                if(Boolean.TRUE.equals(position.getIsVocational())) {
                                    contractType = EHIS_CONTRACT_TYPE_VOCATIONAL;
                                }
                                position.setContractType(em.getReference(Classifier.class, contractType));
                                updateContractEnd(position, data16.getLEPINGULOPP());
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    private static boolean samePerson(Teacher teacher, ZHRPPATOOTAJAPOHIANDMED teacherData) {
        if(!teacher.getRtipNr().equals(teacherData.getPERSONALINR())) {
            return false;
        }
        if(teacherData.getTOOTAJAPOHIANDMED0001() == null || !teacherData.getTOOTAJAPOHIANDMED0001().getItem().stream().anyMatch(r -> teacher.getSchool().getRtipSchoolCode().equals(r.getPERSONALIALA()))) {
            return false;
        }
        return true;
    }

    private List<?> teacherIds(Long schoolId) {
        return em.createNativeQuery("select p.idcode, t.id, t.rtip_nr from teacher t inner join person p on t.person_id = p.id where t.school_id = ?1")
                .setParameter(1, schoolId).getResultList();
    }

    private Teacher teacher(Long teacherId) {
        return em.find(Teacher.class, teacherId);
    }

    private static Teacher syncTeacherRtipNr(Teacher teacher, String rtipNr, Map<String, Long> teacherByRtipNr) {
        if(!rtipNr.equals(teacher.getRtipNr())) {
            if(StringUtils.hasText(teacher.getRtipNr())) {
                // rtip nr changed
                teacherByRtipNr.remove(teacher.getRtipNr());
            }
            teacherByRtipNr.put(rtipNr, teacher.getId());
            teacher.setRtipNr(rtipNr);
        }
        return teacher;
    }

    private SchoolType schoolType(School school) {
        return schoolService.schoolType(school.getId());
    }

    private <T> void withResponse(LogResult<T> result, School school, Consumer<T> handler) {
        try {
            if(!result.hasError()) {
                handler.accept(result.getResult());
            }
        } catch (Exception e) {
            result.getLog().setError(e);
            LOG.error("Error while handling RTIP response :", e);
        } finally {
            rtipLogService.insertLog(school, result.getLog());
        }
    }

    private static TootajaPohiandmedRequestType createTootajaPohiandmedRequest(School school) {
        TootajaPohiandmedRequestType request = new TootajaPohiandmedRequestType();
        request.setKOMPANIIKOOD(COMPANY_CODE_FOR_TOOTAJA_POHIANDMED_REQUEST);
        request.setPERSONALIALA(school.getRtipSchoolCode());
        TOOTAJAPOHIANDMEDX filter = new TOOTAJAPOHIANDMEDX();
        filter.setXTOOTAJAPOHIANDMED0001(SELECTED_FILTER_FOR_TOOTAJA_POHIANDMED_REQUEST);
        filter.setXTOOTAJAPOHIANDMED0002(SELECTED_FILTER_FOR_TOOTAJA_POHIANDMED_REQUEST);
        filter.setXTOOTAJAPOHIANDMED0016(SELECTED_FILTER_FOR_TOOTAJA_POHIANDMED_REQUEST);
        request.setTOOTAJAPOHIANDMEDX(filter);
        return request;
    }

    private static boolean isOnlyVocational(SchoolType schoolType) {
        return !schoolType.isHigher();
    }

    private static void assertRtipSchoolCode(School school) {
        if(!StringUtils.hasText(school.getRtipSchoolCode())) {
            throw new ValidationFailedException("rtip.noschoolcode");
        }
    }

    private static void updateContractEnd(TeacherPositionEhis position, LocalDate endDate) {
        position.setContractEnd(endDate);
        position.setIsContractEnded(Boolean.valueOf(position.getContractEnd() != null && position.getContractEnd().isBefore(LocalDate.now())));
    }

    private static void updateNotEmptyField(Consumer<String> setter, String value) {
        if(StringUtils.hasText(value)) {
            setter.accept(value);
        }
    }
}
