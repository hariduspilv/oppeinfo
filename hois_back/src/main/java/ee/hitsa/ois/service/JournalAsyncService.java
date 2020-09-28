package ee.hitsa.ois.service;

import ee.hitsa.ois.concurrent.AsyncManager;
import ee.hitsa.ois.concurrent.AsyncMemoryManager;
import ee.hitsa.ois.concurrent.AsyncRequest;
import ee.hitsa.ois.concurrent.WrapperCallable;
import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.enums.JournalStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CollectionUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.dto.FutureStatusResponse;
import ee.hitsa.ois.web.dto.timetable.JournalAutomaticAddStudentsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

@Transactional
@Service
public class JournalAsyncService {

    @Autowired
    private JournalService journalService;
    @Autowired
    private AsyncManager asyncManager;
    @Autowired
    private EntityManager em;

    public String addAllSuitableStudentsRequest(HoisUserDetails user, Long studyYearId) {
        String requestHash = asyncManager.generateKey(user);
        AllSuitableStudentsRequest request = createAllSuitableStudentsRequest(user, requestHash, studyYearId);
        asyncManager.createRequest(AsyncMemoryManager.JOURNAL_STUDENTS, user.getSchoolId(), requestHash, request);
        asyncManager.processRequest(request);
        return requestHash;
    }

    public FutureStatusResponse addAllSuitableStudentsStatus(HoisUserDetails user, String requestHash) {
        return asyncManager.getState(user, AsyncMemoryManager.JOURNAL_STUDENTS, requestHash, true);
    }

    private Queue<JournalAutomaticAddStudentsResult> addAllSuitableStudents(HoisUserDetails user, Long studyYearId,
            AtomicReference<Queue<JournalAutomaticAddStudentsResult>> wrapper, AtomicInteger maxRequests) {
        List<Journal> journals = em.createQuery("select j from Journal j "
                + "where j.studyYear.id = ?1 and j.addStudents = true and j.status.code = ?2 "
                + "order by j.nameEt", Journal.class)
                .setParameter(1, studyYearId)
                .setParameter(2, JournalStatus.PAEVIK_STAATUS_T.name())
                .getResultList();

        Queue<JournalAutomaticAddStudentsResult> result = new ConcurrentLinkedQueue<>();
        wrapper.set(result);
        maxRequests.set(journals.size());

        if (!journals.isEmpty()) {
            Map<Long, Journal> journalsById = StreamUtil.toMap(BaseEntityWithId::getId, journals);
            Map<Long, List<Long>> journalsByJournalSub = journals.stream()
                    .filter(j -> EntityUtil.getNullableId(j.getJournalSub()) != null)
                    .collect(Collectors.groupingBy(j -> EntityUtil.getId(j.getJournalSub()),
                            Collectors.mapping(BaseEntityWithId::getId, Collectors.toList())));
            Set<Long> journalsWithJournalSub = journalsByJournalSub.values().stream()
                    .flatMap(Collection::stream).collect(Collectors.toSet());

            JpaNativeQueryBuilder qb = journalService.suitedStudentsQb(user);
            qb.requiredCriteria("j.id in (:journalIds)", "journalIds", journalsById.keySet());
            qb.sort("p.lastname, p.firstname");
            List<?> data = qb.select("j.id j_id, s.id s_id", em).getResultList();

            Set<Long> studentIds = StreamUtil.toMappedSet(r -> resultAsLong(r, 1), data);
            Map<Long, Set<Long>> studentsByJournals = data.stream().collect(
                    Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r -> resultAsLong(r, 1),
                            Collectors.toCollection(LinkedHashSet::new))));

            if (!studentIds.isEmpty()) {
                List<Student> students = em.createQuery("select s from Student s where s.id in ?1", Student.class)
                        .setParameter(1, studentIds)
                        .getResultList();
                Map<Long, Student> studentsById = StreamUtil.toMap(BaseEntityWithId::getId, students);

                for (Long journalId : journalsById.keySet()) {
                    if (journalsWithJournalSub.contains(journalId)) {
                        continue;
                    }
                    Journal journal = journalsById.get(journalId);
                    Set<Long> journalStudentIds = studentsByJournals.containsKey(journalId)
                            ? studentsByJournals.get(journalId) : new HashSet<>();
                    for (Long studentId : journalStudentIds) {
                        JournalStudent js = JournalStudent.of(studentsById.get(studentId));
                        js.setJournal(journal);
                        EntityUtil.save(js, em);
                    }
                    result.add(new JournalAutomaticAddStudentsResult(journalId, Long.valueOf(journalStudentIds.size())));
                }

                for (Long journalSubId : journalsByJournalSub.keySet()) {
                    List<Long> jsJournals = journalsByJournalSub.get(journalSubId);
                    Set<Long> jsStudents = studentsByJournals.get(jsJournals.get(0));
                    List<List<Long>> studentsPerJs = CollectionUtil.partitionIntoEqualSubLists(jsStudents, jsJournals.size());

                    for (int i = 0; i < jsJournals.size(); i++) {
                        Long journalId = jsJournals.get(i);
                        Journal journal = journalsById.get(journalId);
                        List<Long> journalStudentIds = studentsPerJs.get(i) != null ? studentsPerJs.get(i) : new ArrayList<>();
                        for (Long studentId : journalStudentIds) {
                            JournalStudent js = JournalStudent.of(studentsById.get(studentId));
                            js.setJournal(journal);
                            EntityUtil.save(js, em);
                        }
                        result.add(new JournalAutomaticAddStudentsResult(journalId, Long.valueOf(journalStudentIds.size())));
                    }
                }
            }
        }
        return result;
    }

    private AllSuitableStudentsRequest createAllSuitableStudentsRequest(HoisUserDetails user, String hash, Long studyYearId) {
        Optional<AllSuitableStudentsRequest> overlapped = findOverlappedAllSuitableStudentsRequest(user, studyYearId);
        if (overlapped.isPresent() && !overlapped.get().isDone()) {
            Map<Object, Object> errorParams = Collections.singletonMap("user",
                    PersonUtil.stripIdcodeFromFullnameAndIdcode(overlapped.get().getUser()));
            throw new ValidationFailedException("journal.messages.overlappedRequest", errorParams);
        }
        return new AllSuitableStudentsRequest(new WrapperCallable<Queue<JournalAutomaticAddStudentsResult>>() {

            private AtomicInteger max = new AtomicInteger();

            @Override
            public Queue<JournalAutomaticAddStudentsResult> wrapperCall() {
                return addAllSuitableStudents(user, studyYearId, getWrapper(), max);
            }

            @Override
            public float getProgress() {
                if (max.get() == 0) {
                    return 0;
                }
                return getWrapper().get().size() / (float) max.get();
            }

            @Override
            public String getMessage() {
                return null;
            }
        }, hash, user, studyYearId);
    }

    private Optional<AllSuitableStudentsRequest> findOverlappedAllSuitableStudentsRequest(HoisUserDetails user,
            Long studyYearId) {
        Optional<AsyncRequest<?>> optAsyncRequest = AsyncMemoryManager.findAny(AsyncMemoryManager.JOURNAL_STUDENTS,
                user.getSchoolId(), (requestsByHash) -> requestsByHash.values().stream().filter(request -> {
                    if (request instanceof AllSuitableStudentsRequest) {
                        AllSuitableStudentsRequest castedRequest = (AllSuitableStudentsRequest) request;
                        return !castedRequest.isDone() && castedRequest.getStudyYearId().equals(studyYearId);
                    }
                    return false;
                }).findAny().orElse(null));
        return optAsyncRequest.map(asyncRequest -> (AllSuitableStudentsRequest) asyncRequest);
    }

    private static class AllSuitableStudentsRequest extends AsyncRequest<Queue<JournalAutomaticAddStudentsResult>> {

        private final String user;
        private final Long schoolId;
        private final Long studyYearId;

        private String cancelledBy;

        /**
         * Method reference for getting a wrapper
         */
        private final Supplier<AtomicReference<Queue<JournalAutomaticAddStudentsResult>>> wrapper;
        /**
         * Method reference for getting a progress
         */
        private final Supplier<Float> progress;

        public AllSuitableStudentsRequest(WrapperCallable<Queue<JournalAutomaticAddStudentsResult>> callable,
                String key, HoisUserDetails user, Long studyYearId) {
            super(callable, key);
            wrapper = callable::getWrapper;
            progress = callable::getProgress;
            this.user = user.getUsername();
            this.schoolId = user.getSchoolId();
            this.studyYearId = studyYearId;
        }

        public synchronized boolean cancel(HoisUserDetails user, boolean mayInterruptIfRunning) {
            cancelledBy = PersonUtil.stripIdcodeFromFullnameAndIdcode(user.getUsername());
            return cancel(mayInterruptIfRunning);
        }

        public String getUser() {
            return user;
        }

        public Long getSchoolId() {
            return schoolId;
        }

        public Long getStudyYearId() {
            return studyYearId;
        }

        @Override
        public String getMessage() {
            return null;
        }

        @Override
        public Queue<JournalAutomaticAddStudentsResult> getInterruptedResult() {
            return wrapper.get().get();
        }

        @Override
        public float getProgress() {
            return progress.get().floatValue();
        }
    }
}
