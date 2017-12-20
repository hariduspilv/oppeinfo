package ee.hitsa.ois.service.kutseregister;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.config.KutseregisterProperties;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.ClassifierConnect;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentOccupationCertificate;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.OccupationCertificateImportForm;
import ee.hitsa.ois.web.dto.StudentOccupationCertificateDto;
import ee.hois.soap.LogResult;
import ee.hois.xroad.kutseregister.generated.IdNimetusType;
import ee.hois.xroad.kutseregister.generated.Kutsetunnistus;
import ee.hois.xroad.kutseregister.generated.KutsetunnistusV2Type;
import ee.hois.xroad.kutseregister.generated.MuutunudKutsestandardType;
import ee.hois.xroad.kutseregister.generated.MuutunudKutsestandardidParing;
import ee.hois.xroad.kutseregister.service.KutseregisterClient;

@Transactional
@Service
public class KutseregisterService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String OCCUPATION_PREFIX = "KUTSE_";
    private static final String PARTOCCUPATION_PREFIX = "OSAKUTSE_";
    private static final String SPECIALIZATION_PREFIX = "SPETSKUTSE_";
    private static final String OCCUPATION_CERTIFICATE_TYPE_OCCUPATION = "kutsetunnistus";
    private static final String OCCUPATION_CERTIFICATE_TYPE_PARTOCCUPATION = "osakutsetunnistus";

    @Autowired
    private EntityManager em;
    @Autowired
    private KutseregisterClient kutseregisterClient;
    @Autowired
    private KutseregisterLogService kutseregisterLogService;
    @Autowired
    private KutseregisterProperties kutseregisterProperties;

    public List<StudentOccupationCertificateDto> importFromKutseregister(Long schoolId, OccupationCertificateImportForm criteria) {
        // all students with given curriculum versions
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from student s inner join person p on s.person_id = p.id");
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("s.curriculum_version_id in (:curriculumVersion)", "curriculumVersion", criteria.getCurriculumVersion());
        qb.requiredCriteria("s.status_code in (:status)", "status", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.optionalCriteria("s.student_group_id in (:studentGroup)", "studentGroup", criteria.getStudentGroup());
        qb.optionalContains(Arrays.asList("p.firstname", "p.lastname", "p.firstname || ' ' || p.lastname"), "name", criteria.getStudentName());

        List<?> students = qb.select("s.id, p.idcode, s.curriculum_version_id", em).getResultList();
        if(students.isEmpty()) {
            // no students found
            throw new ValidationFailedException("occupationcertificate.nostudents");
        }
        List<?> data = em.createNativeQuery("select cv.id, cocl.name_et, cocl.code, cocl.valid_from, cocl.valid_thru, " +
                "cscl.name_et as cname_et, cscl.code as ccode, cscl.valid_from as cvalid_from, cscl.valid_thru as cvalid_thru " +
                "from curriculum_version cv " +
                "join curriculum c on cv.curriculum_id = c.id " +
                "join curriculum_occupation co on co.curriculum_id = c.id " +
                "join classifier cocl on co.occupation_code = cocl.code " +
                "left join curriculum_occupation_speciality cs on cs.curriculum_occupation_id = co.id " +
                "left join classifier cscl on cs.speciality_code = cscl.code " +
                "where c.school_id = ?1 and cv.id in (?2)")
                .setParameter(1, schoolId)
                .setParameter(2, criteria.getCurriculumVersion())
                .getResultList();
        Map<Long, List<OccupationDto>> occupations = new HashMap<>();
        for(Object r : data) {
            Long cvId = resultAsLong(r, 0);
            List<OccupationDto> cvOccupations = occupations.computeIfAbsent(cvId, (key) -> new ArrayList<>());
            cvOccupations.add(new OccupationDto(resultAsString(r, 1), resultAsString(r, 2), resultAsLocalDate(r, 3), resultAsLocalDate(r, 4)));
            String specialityName = resultAsString(r, 5);
            if(StringUtils.hasText(specialityName)) {
                cvOccupations.add(new OccupationDto(specialityName, resultAsString(r, 6), resultAsLocalDate(r, 7), resultAsLocalDate(r, 8)));
            }
        }

        // existing occupation certificates
        data = em.createNativeQuery("select soc.student_id, soc.certificate_nr from student_occupation_certificate soc where soc.student_id in (?1)")
                .setParameter(1, StreamUtil.toMappedList(r -> resultAsLong(r, 0), students))
                .getResultList();
        Map<Long, Set<String>> certificates = data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> resultAsString(r, 1), Collectors.toSet())));

        List<StudentOccupationCertificate> addedCerts = new ArrayList<>();
        for(Object s : students) {
            Long studentId = resultAsLong(s, 0);
            StudentDto studentDto = new StudentDto(studentId, resultAsString(s, 1));
            Long curriculumVersionId = resultAsLong(s, 2);
            addedCerts.addAll(kutsetunnistus(studentDto, criteria.getFrom(), criteria.getThru(),
                    occupations.getOrDefault(curriculumVersionId, Collections.emptyList()),
                    certificates.computeIfAbsent(studentId, key -> new HashSet<>())));
        }
        return StreamUtil.toMappedList(StudentOccupationCertificateDto::new, addedCerts);
    }

    /**
     * Nightly job for checking changes
     *
     * @param from
     */
    public void muutunudKutsestandardid(LocalDate from) {
        MuutunudKutsestandardidParing request = new MuutunudKutsestandardidParing();
        request.setAlates(from);

        withResponse(kutseregisterClient.muutunudKutsestandardid(kutseregisterProperties.xroadHeader("muutunudKutsestandardid"), request), null, (response) -> {
            if(response.getKutsestandardid() != null && !response.getKutsestandardid().getKutsestandard().isEmpty()) {
                Map<String, Classifier> classifiers = findClassifiers();
                for(MuutunudKutsestandardType occupation : response.getKutsestandardid().getKutsestandard()) {
                    Classifier occ = syncParent(occupation, classifiers);

                    if(occupation.getOsakutsed() != null && !occupation.getOsakutsed().getOsakutse().isEmpty()) {
                        for(IdNimetusType partoccupation : occupation.getOsakutsed().getOsakutse()) {
                            syncChild(partoccupation, PARTOCCUPATION_PREFIX, MainClassCode.OSAKUTSE, occ, classifiers);
                        }
                    }

                    if(occupation.getSpetsialiseerumised() != null && !occupation.getSpetsialiseerumised().getSpetsialiseerumine().isEmpty()) {
                        for(IdNimetusType specialization : occupation.getSpetsialiseerumised().getSpetsialiseerumine()) {
                            syncChild(specialization, SPECIALIZATION_PREFIX, MainClassCode.SPETSKUTSE, occ, classifiers);
                        }
                    }
                }
            }
        });
    }

    private List<StudentOccupationCertificate> kutsetunnistus(StudentDto studentDto, LocalDate from, LocalDate thru, List<OccupationDto> occupations, Set<String> existingCertificates) {
        Kutsetunnistus request = new Kutsetunnistus();
        request.setIsikukood(studentDto.getIdcode());
        request.setValjastatudalgus(from);
        request.setValjastatudlopp(thru);

        Student student = em.getReference(Student.class, studentDto.getStudentId());

        List<StudentOccupationCertificate> certificates = new ArrayList<>();
        withResponse(kutseregisterClient.kutsetunnistus(kutseregisterProperties.xroadHeader(KutseregisterProperties.KUTSETUNNISTUS_SERVICE_CODE), request), student.getSchool(), (response) -> {
            if(response.getKutsetunnistused() != null) {
                for(KutsetunnistusV2Type certificate : response.getKutsetunnistused().getKutsetunnistus()) {
                    if(!studentDto.getIdcode().equals(certificate.getIsikukood())) {
                        continue;
                    }
                    String certificateNr = certificate.getRegistrinumber();
                    if(existingCertificates.contains(certificateNr)) {
                        // certificate already exists
                        continue;
                    }
                    String type = certificate.getTyyp();
                    Classifier occupation = null, partOccupation = null, speciality = null;
                    if(OCCUPATION_CERTIFICATE_TYPE_OCCUPATION.equals(type)) {
                        // check for specialty certificate
                        speciality = findOccupation(certificate.getSpetsialiseerumine(), SPECIALIZATION_PREFIX, certificate.getValjaantud(), occupations);
                        if(speciality != null) {
                            occupation = occupationFor(speciality);
                        } else if(!StringUtils.hasText(certificate.getSpetsialiseerumine())) {
                            // just occupation certificate
                            occupation = findOccupation(certificate.getStandard(), OCCUPATION_PREFIX, certificate.getValjaantud(), occupations);
                        }
                    } else if(OCCUPATION_CERTIFICATE_TYPE_PARTOCCUPATION.equals(type)) {
                        partOccupation = findOccupation(certificate.getOsakutse(), PARTOCCUPATION_PREFIX, certificate.getValjaantud(), occupations);
                        occupation = occupationFor(partOccupation);
                    }
                    if(occupation == null) {
                        // no matching (part)occupation
                        continue;
                    }
                    // add new occupation certificate
                    StudentOccupationCertificate studentCertificate = new StudentOccupationCertificate();
                    studentCertificate.setStudent(student);
                    studentCertificate.setCertificateNr(certificateNr);
                    studentCertificate.setOccupation(occupation);
                    studentCertificate.setPartOccupation(partOccupation);
                    studentCertificate.setSpeciality(speciality);
                    studentCertificate.setValidFrom(certificate.getKehtibalates());
                    studentCertificate.setValidThru(certificate.getKehtibkuni());
                    studentCertificate.setIssuer(certificate.getValjastaja());
                    studentCertificate.setIssueDate(certificate.getValjaantud());
                    studentCertificate.setLanguage(certificate.getKeel());
                    em.persist(studentCertificate);
                    existingCertificates.add(certificateNr);
                    certificates.add(studentCertificate);
                }
            }
        });
        return certificates;
    }

    private Classifier syncParent(MuutunudKutsestandardType occupation, Map<String, Classifier> classifiers) {
        String value = occupation.getId().toString();
        String code = OCCUPATION_PREFIX + value;
        String name = occupation.getNimetus();
        String ekrLevel = occupation.getEkrtase();
        LocalDate validFrom = occupation.getKehtivusealgus();
        LocalDate validThru = occupation.getKehtivuselopp();

        Classifier occ = classifiers.get(code);
        if(occ == null) {
            occ = new Classifier();
            occ.setCode(code);
            occ.setValue(value);
            occ.setMainClassCode(MainClassCode.KUTSE.name());
            occ.setNameEt(name);
            occ.setExtraval1(ekrLevel);
            occ.setValidFrom(validFrom);
            occ.setValidThru(validThru);
            em.persist(occ);
            if(StringUtils.hasText(ekrLevel)) {
                // add new connection to EKR
                Classifier ekr = classifiers.get("EKR_" + ekrLevel);
                if(ekr != null) {
                    ClassifierConnect connect = new ClassifierConnect();
                    connect.setClassifier(occ);
                    connect.setConnectClassifier(ekr);
                    connect.setMainClassifierCode(MainClassCode.EKR.name());
                    em.persist(connect);
                } else {
                    LOG.warn("Kutseregister sync: cannot find EKR classifier with code EKR_{}, connection from occupation {} to EKR not created", ekrLevel, code);
                }
            }
        } else {
            // check if changed
            if(!name.equals(occ.getNameEt()) || !ekrLevel.equals(occ.getExtraval1()) || !validFrom.equals(occ.getValidFrom()) || !validThru.equals(occ.getValidThru())) {
                occ.setNameEt(name);
                occ.setExtraval1(ekrLevel);
                occ.setValidFrom(validFrom);
                occ.setValidThru(validThru);
                occ = em.merge(occ);
            }
        }
        return occ;
    }

    private void syncChild(IdNimetusType item, String prefix, MainClassCode domain, Classifier occupation, Map<String, Classifier> classifiers) {
        String value = item.getId().toString();
        String code = prefix + value;
        String name = item.getNimetus();

        Classifier child = classifiers.get(code);
        if(child != null) {
            if(!child.getNameEt().equals(name)) {
                child.setNameEt(name);
                em.merge(child);
            }
            return;
        }
        // new child
        child = new Classifier();
        child.setCode(code);
        child.setValue(value);
        child.setMainClassCode(domain.name());
        child.setNameEt(name);
        em.persist(child);
        // new connection to occupation
        ClassifierConnect connect = new ClassifierConnect();
        connect.setClassifier(child);
        connect.setConnectClassifier(occupation);
        connect.setMainClassifierCode(MainClassCode.KUTSE.name());
        em.persist(connect);
    }

    private Map<String, Classifier> findClassifiers() {
        return em.createQuery("select c from Classifier c where c.mainClassCode in (?1)", Classifier.class)
            .setParameter(1, EnumUtil.toNameList(MainClassCode.KUTSE, MainClassCode.OSAKUTSE, MainClassCode.SPETSKUTSE, MainClassCode.EKR))
            .getResultList().stream().collect(Collectors.toMap(Classifier::getCode, r -> r));
    }

    private <T> void withResponse(LogResult<T> result, School school, Consumer<T> handler) {
        try {
            if(!result.hasError()) {
                handler.accept(result.getResult());
            }
        } catch (Exception e) {
            result.getLog().setError(e);
            LOG.error("Error while handling Kutseregister response :", e);
        } finally {
            kutseregisterLogService.insertLog(school, result.getLog());
        }
    }

    private Classifier findOccupation(String occupationName, String prefix, LocalDate issued, List<OccupationDto> occupations) {
        if(!StringUtils.hasText(occupationName)) {
            return null;
        }
        List<OccupationDto> matches = new ArrayList<>();
        for(OccupationDto occ : occupations) {
            if(occupationName.equals(occ.getName()) ) {
                String occupationCode = occ.getCode();
                if(occupationCode != null && occupationCode.startsWith(prefix)) {
                    matches.add(occ);
                }
            }
        }
        // check validFrom/thru if there are more than one occupation with same name
        OccupationDto match = matches.size() == 1 || issued == null ? matches.get(0) : matches.stream().filter(r -> {
            return (r.getValidFrom() == null || !issued.isBefore(r.getValidFrom())) && (r.getValidThru() == null || !issued.isAfter(r.getValidThru()));
        }).findAny().orElse(null);

        return match != null ? em.getReference(Classifier.class, match.getCode()) : null;
    }

    private static Classifier occupationFor(Classifier partOccupation) {
        if(partOccupation == null) {
            return null;
        }
        // find matching occupation
        return partOccupation.getClassifierConnects().stream()
                .filter(r -> MainClassCode.KUTSE.name().equals(r.getMainClassifierCode()))
                .findAny().map(r -> r.getConnectClassifier()).orElse(null);
    }

    static class StudentDto {
        private final Long studentId;
        private final String idcode;

        public StudentDto(Long studentId, String idcode) {
            this.studentId = studentId;
            this.idcode = idcode;
        }

        public Long getStudentId() {
            return studentId;
        }

        public String getIdcode() {
            return idcode;
        }
    }

    static class OccupationDto {
        private final String name;
        private final String code;
        private final LocalDate validFrom;
        private final LocalDate validThru;

        public OccupationDto(String name, String code, LocalDate validFrom, LocalDate validThru) {
            this.name = name;
            this.code = code;
            this.validFrom = validFrom;
            this.validThru = validThru;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public LocalDate getValidFrom() {
            return validFrom;
        }

        public LocalDate getValidThru() {
            return validThru;
        }
    }
}
