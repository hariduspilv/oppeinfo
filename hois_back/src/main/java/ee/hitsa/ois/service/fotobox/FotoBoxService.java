package ee.hitsa.ois.service.fotobox;

import ee.hitsa.ois.concurrent.AsyncManager;
import ee.hitsa.ois.concurrent.AsyncMemoryManager;
import ee.hitsa.ois.concurrent.AsyncRequest;
import ee.hitsa.ois.concurrent.WrapperCallable;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.WsPhotoLog;
import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.FotoBoxStudentResultDto;
import ee.hitsa.ois.web.commandobject.OisFileCommand;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

/**
 *
 */
@Transactional
@Service
public class FotoBoxService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private EntityManager em;
    @Autowired
    private FotoBoxLogService fotoBoxLogService;
    @Autowired
    private AsyncManager asyncManager;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Value("${fotobox.endpoint}")
    private String endpoint;
    @Value("${fotobox.usercodesPerRequestCount}")
    private int usercodesPerRequestCount;

    /**
     * Request student's photo from FotoBox and if it exists save it
     * @param user
     * @param student
     * @return student photo, null if there isn't one
     * @throws IOException
     */
    public OisFileCommand requestStudentPhoto(HoisUserDetails user, Student student) {
        Person person = student.getPerson();
        String usercode = person.getIdcode() != null ? person.getIdcode() : person.getUniqueCode();

        List<String> usercodes = Collections.singletonList(usercode);
        Map<String, List<Student>> studentsPerUsercode = activeStudentsPerUsercode(user.getSchoolId(), usercodes);

        WsPhotoLog wsPhotoLog = new WsPhotoLog();
        wsPhotoLog.setSchool(em.getReference(School.class, user.getSchoolId()));
        wsPhotoLog.setStudent(student);
        wsPhotoLog.setIsMultipleRequest(Boolean.FALSE);
        updateStudentPhotos(usercodes, studentsPerUsercode, wsPhotoLog);

        return student.getPhoto() != null ? EntityUtil.bindToDto(student.getPhoto(), new OisFileCommand()) : null;
    }

    private Map<String, List<Student>> activeStudentsPerUsercode(Long schoolId, List<String> usercodes) {
        List<Student> students = em.createQuery("select s from Student s join fetch s.person p"
                + " where s.school.id = ?1 and (p.idcode in ?2 or p.uniqueCode in ?2)"
                + " and s.status.code in ?3", Student.class)
                .setParameter(1, schoolId)
                .setParameter(2, usercodes)
                .setParameter(3, StudentStatus.STUDENT_STATUS_ACTIVE)
                .getResultList();
        return students.stream().collect(Collectors.groupingBy(s -> s.getPerson().getIdcode() != null
                ? s.getPerson().getIdcode() : s.getPerson().getUniqueCode()));
    }


    private Set<Long> updateStudentPhotos(List<String> usercodes, Map<String, List<Student>> studentsPerUsercode,
            WsPhotoLog wsPhotoLog) {
        Map<String, List<Long>> result = new HashMap<>();
        Exception error = null;
        FotoBoxRequestParams requestParams = fotoBoxRequestParams(usercodes);

        try {
            ResponseEntity<ByteArrayResource> response = fotoBoxRequest(requestParams);

            InputStream is = new ByteArrayInputStream(response.getBody().getByteArray());
            ZipInputStream zis = new ZipInputStream(is);
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) !=  null) {
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n;
                while ((n = zis.read(buf, 0, 1024)) != -1) {
                    output.write(buf, 0, n);
                }
                output.close();

                String photoUsercode = FilenameUtils.getBaseName(entry.getName());
                result.put(photoUsercode, new ArrayList<>());
                log.info("Found image with usercode: " + photoUsercode);

                List<Student> newPhotoStudents = studentsPerUsercode.get(photoUsercode);
                if (newPhotoStudents != null) {
                    for (Student student : newPhotoStudents) {
                        log.info("Updating student with id: " + student.getId());
                        saveStudentPhoto(student, photoUsercode, output);
                        result.get(photoUsercode).add(student.getId());
                    }
                }
            }
            zis.close();
            wsPhotoLog.setResponse(fotoBoxRequestLogResponse(response));
        } catch (Exception e) {
            error = e;
            throw new HoisException("student.photoBox.requestResult.failure");
        } finally {
            fotoBoxLogService.insertLog(wsPhotoLog, requestParams, result, error);
        }
        return result.keySet().stream().map(result::get).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    private FotoBoxRequestParams fotoBoxRequestParams(List<String> usercodes) {
        FotoBoxRequestParams params = new FotoBoxRequestParams();
        for (String usercode : usercodes) {
            params.getUsercodes().add(new FotoBoxUserParams(usercode));
        }
        return params;
    }

    private String fotoBoxRequestLogResponse(ResponseEntity<ByteArrayResource> response) {
        HttpStatus status = response.getStatusCode();
        return status.value() + " " + status.getReasonPhrase() + "\n" +
                response.getHeaders().toString() + "\n" +
                Base64.getEncoder().encodeToString(response.getBody().getByteArray());
    }

    private void saveStudentPhoto(Student student, String usercode, ByteArrayOutputStream output) {
        OisFile photo = student.getPhoto();
        if (photo == null) {
            photo = new OisFile();
            student.setPhoto(photo);
        }
        photo.setFtype("image/jpeg");
        photo.setFname(usercode);
        photo.setFdata(output.toByteArray());
        EntityUtil.save(photo, em);
        EntityUtil.save(student, em);
    }

    private ResponseEntity<ByteArrayResource> fotoBoxRequest(FotoBoxRequestParams params) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(endpoint, params, ByteArrayResource.class);
    }

    public Long studentsWithoutPhoto(Long schoolId) {
        List<?> data = em.createNativeQuery("select p.id from student s"
                + " join person p on p.id = s.person_id"
                + " where s.school_id = :schoolId and s.status_code in (:studentStatus) and s.ois_file_id is null")
                .setParameter("schoolId", schoolId)
                .setParameter("studentStatus", StudentStatus.STUDENT_STATUS_ACTIVE)
                .getResultList();
        Set<Long> personIds = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);
        return Long.valueOf(personIds.size());
    }

    private Queue<FotoBoxStudentResultDto> requestStudentPhotos(School school, Directive directive, List<String> usercodes,
           AtomicReference<Queue<FotoBoxStudentResultDto>> wrapper, AtomicInteger maxRequests) {
        Queue<FotoBoxStudentResultDto> studentChanges = new ConcurrentLinkedQueue<>();
        wrapper.set(studentChanges);
        maxRequests.set(usercodes.size());

        if (!usercodes.isEmpty()) {
            Map<String, List<Student>> studentsPerUsercode = activeStudentsPerUsercode(school.getId(), usercodes);
            List<List<String>> usercodesPerRequest = ListUtils.partition(usercodes, usercodesPerRequestCount);
            boolean isMultipleRequest = usercodesPerRequest.size() > 1;

            for (List<String> requestUsercodes : usercodesPerRequest) {
                WsPhotoLog wsPhotoLog = new WsPhotoLog();
                wsPhotoLog.setSchool(school);
                wsPhotoLog.setDirective(directive);
                wsPhotoLog.setIsMultipleRequest(Boolean.valueOf(isMultipleRequest));
                Set<Long> updatedStudents = updateStudentPhotos(requestUsercodes, studentsPerUsercode, wsPhotoLog);

                for (String usercode : requestUsercodes) {
                    List<Student> usercodeStudents = studentsPerUsercode.get(usercode);
                    if (usercodeStudents != null) {
                        Student student = usercodeStudents.get(0);
                        FotoBoxStudentResultDto dto = new FotoBoxStudentResultDto(student);
                        dto.setSuccess(Boolean.valueOf(updatedStudents.contains(student.getId())));
                        studentChanges.add(dto);
                    }
                }
            }
        }
        return studentChanges;
    }

    private void requestStudentPhotos(Long school, Directive directive, List<String> usercodes, String authentication) {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);

        TransactionStatus status = transactionManager.getTransaction(definition);
        Authentication auth = new UsernamePasswordAuthenticationToken(authentication, null,
                Collections.singletonList((GrantedAuthority)(() -> "ROLE_JOB")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        EntityUtil.setUsername(authentication, em);
        try {
            if (!usercodes.isEmpty()) {
                Map<String, List<Student>> studentsPerUsercode = activeStudentsPerUsercode(school, usercodes);
                List<List<String>> usercodesPerRequest = ListUtils.partition(usercodes, usercodesPerRequestCount);
                boolean isMultipleRequest = usercodesPerRequest.size() > 1;

                for (List<String> requestUsercodes : usercodesPerRequest) {
                    WsPhotoLog wsPhotoLog = new WsPhotoLog();
                    wsPhotoLog.setSchool(em.getReference(School.class, school));
                    wsPhotoLog.setDirective(directive);
                    wsPhotoLog.setIsMultipleRequest(Boolean.valueOf(isMultipleRequest));

                    Set<Long> updatedStudents = updateStudentPhotos(requestUsercodes, studentsPerUsercode, wsPhotoLog);

                    for (String usercode : requestUsercodes) {
                        List<Student> usercodeStudents = studentsPerUsercode.get(usercode);
                        if (usercodeStudents != null) {
                            Student student = usercodeStudents.get(0);
                            FotoBoxStudentResultDto dto = new FotoBoxStudentResultDto(student);
                            dto.setSuccess(Boolean.valueOf(updatedStudents.contains(student.getId())));
                        }
                    }
                }
            }
            transactionManager.commit(status);
        } catch (Exception ex) {
            transactionManager.rollback(status);
            throw ex;
        }
    }

    /**
     * Make async request to get photos for students without one
     * @param schoolId
     * @param username
     * @return async request key
     */
    public String studentsWithoutPhotoAsyncRequest(Long schoolId, String username) {
        School school = em.getReference(School.class, schoolId);
        List<String> usercodes = studentWithoutPhotoUsercodes(schoolId);
        log.info("Students without photo usercodes: " + String.join(", ", usercodes));
        String requestHash = asyncManager.generateKey(username);
        FotoBoxAsyncRequest request = createRequest(school, null, username, requestHash, usercodes);
        asyncManager.createRequest(AsyncMemoryManager.FOTOBOX, schoolId, requestHash, request);
        asyncManager.processRequest(request);
        return requestHash;
    }

    private List<String> studentWithoutPhotoUsercodes(Long schoolId) {
        List<?> data = em.createNativeQuery("select coalesce(p.idcode, p.unique_code)"
                + " from student s"
                + " join person p on p.id = s.person_id"
                + " where s.school_id = ?1 and s.status_code in (?2) and s.ois_file_id is null")
                .setParameter("1", schoolId)
                .setParameter("2", StudentStatus.STUDENT_STATUS_ACTIVE)
                .getResultList();
        return new ArrayList<>(StreamUtil.toMappedSet(r -> resultAsString(r, 0), data));
    }

    /**
     * Make async request to get photos for students in directive
     * @param confirmer
     * @param directive
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void directiveStudentPhotoAsyncRequest(String confirmer, Directive directive) {
        List<String> usercodes = directiveStudentUsercodes(directive);
        log.info("Directive connected usercodes: " + String.join(", ", usercodes));
        String requestHash = asyncManager.generateKey(confirmer);
        FotoBoxAsyncRequest request = createRequest(directive.getSchool(), directive, confirmer, requestHash, usercodes);
        asyncManager.processRequest(request);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void directiveStudentPhotoJobRequest(String confirmer, Directive directive, boolean isEKIS) {
        List<String> usercodes = directiveStudentUsercodes(directive);
        log.info("Directive connected usercodes: " + String.join(", ", usercodes));
        requestStudentPhotos(EntityUtil.getId(directive.getSchool()), directive, usercodes, isEKIS ? "EKIS" : confirmer);
    }

    private List<String> directiveStudentUsercodes(Directive directive) {
        List<?> data = em.createNativeQuery("select coalesce(p.idcode, p.unique_code) from directive_student ds"
                + " join student s on s.id = ds.student_id"
                + " join person p on p.id = s.person_id"
                + " where ds.directive_id = ?1")
                .setParameter(1, EntityUtil.getId(directive))
                .getResultList();
        return new ArrayList<>(StreamUtil.toMappedSet(r -> resultAsString(r, 0), data));
    }

    private FotoBoxAsyncRequest createRequest(School school, Directive directive, String username,
            String hash, List<String> usercodes) {
        return new FotoBoxAsyncRequest(new WrapperCallable<Queue<FotoBoxStudentResultDto>>() {

            private AtomicInteger max = new AtomicInteger();

            @Override
            public Queue<FotoBoxStudentResultDto> wrapperCall() {
                return requestStudentPhotos(school, directive, usercodes, getWrapper(), max);
            }

            @Override
            public float getProgress() {
                if (max.get() == 0) {
                    return 0;
                }
                return getWrapper().get().size() / (float) max.get();
            }
        }, hash, school.getId(), username);
    }

    public static class FotoBoxAsyncRequest extends AsyncRequest<Queue<FotoBoxStudentResultDto>> {
        private final String user;
        private final Long schoolId;

        /** Method reference for getting a wrapper */
        private final Supplier<AtomicReference<Queue<FotoBoxStudentResultDto>>> wrapper;
        /** Method reference for getting a progress */
        private final Supplier<Float> progress;

        public FotoBoxAsyncRequest(WrapperCallable<Queue<FotoBoxStudentResultDto>> callable, String key,
                Long schoolId, String username) {
            super(callable, key);
            wrapper = callable::getWrapper;
            progress = callable::getProgress;
            this.schoolId = schoolId;
            this.user = username;
        }
        public String getUser() {
            return user;
        }

        public Long getSchoolId() {
            return schoolId;
        }

        @Override
        public String getMessage() {
            return null;
        }

        @Override
        public Queue<FotoBoxStudentResultDto> getInterruptedResult() {
            return wrapper.get().get();
        }

        @Override
        public float getProgress() {
            return progress.get().floatValue();
        }
    }

    static class FotoBoxRequestParams {
        @NotEmpty
        private List<FotoBoxUserParams> usercodes = new ArrayList<>();

        public List<FotoBoxUserParams> getUsercodes() {
            return usercodes;
        }

        public void setUsercodes(List<FotoBoxUserParams> usercodes) {
            this.usercodes = usercodes;
        }
    }

    private static class FotoBoxUserParams {
        @Required
        private String usercode;

        public FotoBoxUserParams(String usercode) {
            this.usercode = usercode;
        }

        public String getUsercode() {
            return usercode;
        }

        public void setUsercode(String usercode) {
            this.usercode = usercode;
        }
    }

}
