package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolHdata;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.protocol.ProtocolStudentHistory;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.enums.ProtocolType;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.ProtocolRepository;
import ee.hitsa.ois.repository.ProtocolStudentRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.repository.SubjectStudyPeriodRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HigherProtocolGradeUtil;
import ee.hitsa.ois.util.HigherProtocolUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.ProtocolUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.HigherProtocolCalculateCommand;
import ee.hitsa.ois.web.commandobject.HigherProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.HigherProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.HigherProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.HigherProtocolStudentSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.HigherProtocolDto;
import ee.hitsa.ois.web.dto.HigherProtocolSearchDto;
import ee.hitsa.ois.web.dto.HigherProtocolStudentDto;
import ee.hitsa.ois.web.dto.HigherProtocolStudentResultDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

@Transactional
@Service
public class HigherProtocolService {

    private static final String STUDENT_SELECT = "s.id, p.firstname, p.lastname, "
            + "sg.code as studentGroupCode, c.code as curriculumCode";
    private static final String STUDENT_FROM =
              "from student s "
            + "join person p on p.id = s.person_id "
            + "join student_group sg on sg.id = s.student_group_id "
            + "join curriculum_version cv on cv.id = s.curriculum_version_id "
            + "join curriculum c on c.id = cv.curriculum_id "
            + "left join declaration d on d.student_id = s.id "
            + "left join declaration_subject ds on d.id = ds.declaration_id";

    private static final String STUDENT_HAS_BASIC_PROTOCOL =
            " exists ( select p.id from protocol p "
          + "join protocol_hdata ph on p.id = ph.protocol_id "
          + "left join protocol_student ps on p.id = ps.protocol_id "
          + "where ph.type_code = '" + ProtocolType.PROTOKOLLI_LIIK_P.name() + "' "
          + "and ph.subject_study_period_id = ds.subject_study_period_id "
          + "and ps.student_id = s.id ) ";

    @Autowired
    private EntityManager em;
    @Autowired
    private ProtocolRepository protocolRepository;
    @Autowired
    private ClassifierRepository classifierRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private SubjectStudyPeriodRepository subjectStudyPeriodRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private ProtocolStudentRepository protocolStudentRepository;

    public Page<HigherProtocolSearchDto> search(HoisUserDetails user, HigherProtocolSearchCommand criteria,
            Pageable pageable) {

        Page<Protocol> protocols = protocolRepository.findAll((root, query, cb) -> {

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("isVocational"), Boolean.FALSE));
            filters.add(cb.equal(root.get("school").get("id"), user.getSchoolId()));

            filters.add(cb.equal(root.get("protocolHdata").get("subjectStudyPeriod")
                    .get("studyPeriod").get("id"), criteria.getStudyPeriod()));

            if(criteria.getStatus() != null) {
                filters.add(cb.equal(root.get("status").get("code"), criteria.getStatus()));
            }
            if(criteria.getProtocolNr() != null) {
                filters.add(cb.equal(root.get("protocolNr"), criteria.getProtocolNr()));
            }
            if(criteria.getSubject() != null) {
                List<Predicate> subjectFilters = new ArrayList<>();
                propertyContains(() -> root.get("protocolHdata").get("subjectStudyPeriod").get("subject").get("code"),
                        cb, criteria.getSubject(), subjectFilters::add);
                propertyContains(() -> root.get("protocolHdata").get("subjectStudyPeriod").get("subject").get("nameEt"),
                        cb, criteria.getSubject(), subjectFilters::add);
                propertyContains(() -> root.get("protocolHdata").get("subjectStudyPeriod").get("subject").get("nameEn"),
                        cb, criteria.getSubject(), subjectFilters::add);
                filters.add(cb.or(subjectFilters.toArray(new Predicate[subjectFilters.size()])));
            }
            if(user.isTeacher()) {
                Subquery<Long> targetQuery = query.subquery(Long.class);
                Root<SubjectStudyPeriodTeacher> targetRoot = targetQuery.from(SubjectStudyPeriodTeacher.class);
                targetQuery = targetQuery.select(targetRoot.get("subjectStudyPeriod").get("id"))
                        .where(cb.equal(targetRoot.get("teacher").get("person").get("id"), user.getPersonId()));
                filters.add(root.get("protocolHdata").get("subjectStudyPeriod").get("id").in(targetQuery));
            } else {
                if(criteria.getTeacher() != null) {
                    Subquery<Long> targetQuery = query.subquery(Long.class);
                    Root<SubjectStudyPeriodTeacher> targetRoot = targetQuery.from(SubjectStudyPeriodTeacher.class);
                    targetQuery = targetQuery.select(targetRoot.get("subjectStudyPeriod").get("id"))
                            .where(cb.equal(targetRoot.get("teacher").get("id"), criteria.getTeacher()));
                    filters.add(root.get("protocolHdata").get("subjectStudyPeriod").get("id").in(targetQuery));
                }
            }

            if(criteria.getInsertedFrom() != null) {
                filters.add(cb.greaterThanOrEqualTo(root.get("inserted"), DateUtils.firstMomentOfDay(criteria.getInsertedFrom())));
            }
            if(criteria.getInsertedThru() != null) {
                filters.add(cb.lessThanOrEqualTo(root.get("inserted"), DateUtils.lastMomentOfDay(criteria.getInsertedThru())));
            }
            if(criteria.getConfirmDateFrom() != null) {
                filters.add(cb.greaterThanOrEqualTo(root.get("confirmDate"), criteria.getConfirmDateFrom()));
            }
            if(criteria.getConfirmDateThru() != null) {
                filters.add(cb.lessThanOrEqualTo(root.get("confirmDate"), criteria.getConfirmDateThru()));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable);

        return protocols.map(p -> HigherProtocolSearchDto.ofWithUserRithts(p, user));
    }

    public Protocol create(HoisUserDetails user, HigherProtocolCreateForm form) {
        Protocol protocol = new Protocol();
        protocol.setIsVocational(Boolean.FALSE);
        protocol.setProtocolNr(ProtocolUtil.generateProtocolNumber(em));
        protocol.setSchool(schoolRepository.getOne(user.getSchoolId()));
        protocol.setStatus(classifierRepository.getOne(ProtocolStatus.PROTOKOLL_STAATUS_S.name()));

        ProtocolHdata protocolHData = new ProtocolHdata();
        protocolHData.setType(classifierRepository.getOne(form.getProtocolType()));
        protocolHData.setSubjectStudyPeriod(subjectStudyPeriodRepository.getOne(form.getSubjectStudyPeriod()));
        protocolHData.setProtocol(protocol);
        protocol.setProtocolHdata(protocolHData);

        protocol.setProtocolStudents(form.getStudents().stream().map(studentId -> {
            ProtocolStudent protocolStudent =  new ProtocolStudent();
            protocolStudent.setStudent(studentRepository.getOne(studentId));
            protocolStudent.setProtocol(protocol);
            return protocolStudent;
        }).collect(Collectors.toList()));

        return protocolRepository.save(protocol);
    }

    public Protocol save(Protocol protocol, HigherProtocolSaveForm form) {
        updateProtocolStudents(protocol, form);
        unconfirmProtocol(protocol);
        return protocolRepository.save(protocol);
    }

    private void unconfirmProtocol(Protocol protocol) {
        if(HigherProtocolUtil.isConfirmed(protocol)) {
            protocol.setStatus(classifierRepository.getOne(ProtocolStatus.PROTOKOLL_STAATUS_S.name()));
            protocol.setConfirmer(null);
            protocol.setConfirmDate(null);
        }
    }

    public void updateProtocolStudents(Protocol protocol, HigherProtocolSaveForm form) {
        EntityUtil.bindEntityCollection(protocol.getProtocolStudents(), ProtocolStudent::getId,
                // no protocol students created here
                form.getProtocolStudents(), HigherProtocolStudentDto::getId, null, (dto, ps) -> {
                    if (gradeChangedButNotRemoved(dto, ps)) {
                        HigherProtocolUtil.assertHasAddInfoIfProtocolConfirmed(dto, protocol);
                        addHistory(ps);
                        gradeStudent(ps, classifierRepository.getOne(dto.getGrade()));
                    } else if (gradeRemoved(dto, ps)) {
                        HigherProtocolUtil.assertHasAddInfoIfProtocolConfirmed(dto, protocol);
                        addHistory(ps);
                        removeGrade(ps);
                    }
                    ps.setAddInfo(dto.getAddInfo());
                });
    }

    private static void addHistory(ProtocolStudent ps) {
        if(EntityUtil.getNullableCode(ps.getGrade()) != null) {
            ProtocolStudentHistory history = new ProtocolStudentHistory();
            history.setProtocolStudent(ps);
            history.setAddInfo(ps.getAddInfo());
            history.setGrade(ps.getGrade());
            ps.getProtocolStudentHistories().add(history);
        }
    }

    private static boolean gradeChangedButNotRemoved(HigherProtocolStudentDto dto, ProtocolStudent ps) {
        return dto.getGrade() != null && !dto.getGrade().isEmpty() &&
                !dto.getGrade().equals(EntityUtil.getNullableCode(ps.getGrade()));
    }

    private static boolean gradeRemoved(HigherProtocolStudentDto dto, ProtocolStudent ps) {
        return dto.getGrade() == null || dto.getGrade().isEmpty() &&
                EntityUtil.getNullableCode(ps.getGrade()) != null;
    }

    public void gradeStudent(ProtocolStudent ps, Classifier grade) {
        Short gradeMark = null;
        String gradeValue = grade.getValue();

        if(gradeValue.matches("[0-5]")) {
            gradeMark = Short.valueOf(gradeValue);
        }

        ps.setGradeMark(gradeMark);
        ps.setGradeValue(gradeValue);
        ps.setGrade(grade);
        ps.setGradeDate(LocalDate.now());
    }

    public void removeGrade(ProtocolStudent ps) {
        ps.setGrade(null);
        ps.setGradeDate(LocalDate.now());
        ps.setGradeMark(null);
        ps.setGradeValue(null);
    }

    private void confirmProtocol(Protocol protocol, HoisUserDetails user) {
        protocol.setStatus(classifierRepository.getOne(ProtocolStatus.PROTOKOLL_STAATUS_K.name()));
        protocol.setConfirmDate(LocalDate.now());
        protocol.setConfirmer(user.getUsername());
    }

    public Protocol confirm(Protocol protocol, HoisUserDetails user) {
        confirmProtocol(protocol, user);
        return protocolRepository.save(protocol);
    }

    public Protocol saveAndConfirm(HoisUserDetails user, Protocol protocol, HigherProtocolSaveForm form) {
        updateProtocolStudents(protocol, form);
        confirmProtocol(protocol, user);
        return protocolRepository.save(protocol);
    }

    public List<AutocompleteResult> getSubjectStudyPeriods(Long schoolId) {
        Long studyPeriod = studyYearService.getCurrentStudyPeriod(schoolId);

        List<SubjectStudyPeriod> ssps = subjectStudyPeriodRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("studyPeriod").get("id"), studyPeriod));
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        });
        return subjectStudyPeriodsToAutocompleteResultList(ssps);
    }

    public List<AutocompleteResult> subjectStudyPeriodsToAutocompleteResultList(List<SubjectStudyPeriod> ssps) {
        return StreamUtil.toMappedList(ssp -> {
            Subject s = ssp.getSubject();
            String nameEt = SubjectUtil.subjectName(s.getCode(), s.getNameEt(), s.getCredits());
            String nameEn = SubjectUtil.subjectName(s.getCode(), s.getNameEn(), s.getCredits());
            return new AutocompleteResult(ssp.getId(), nameEt, nameEn);
        }, ssps);
    }

    public List<StudentSearchDto> getStudents(Long schoolId, HigherProtocolStudentSearchCommand criteria) {

        JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder(STUDENT_FROM);

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.filter("d.status_code = '" + DeclarationStatus.OPINGUKAVA_STAATUS_K.name() + "'");
        qb.requiredCriteria("ds.subject_study_period_id = :subjectStudyPeriodId",
                "subjectStudyPeriodId", criteria.getSubjectStudyPeriod());

        if(ProtocolType.PROTOKOLLI_LIIK_P.name().equals(criteria.getProtocolType())) {
            qb.filter(" not " + STUDENT_HAS_BASIC_PROTOCOL);
        } else {
            qb.filter(STUDENT_HAS_BASIC_PROTOCOL);
        }
        List<?> result = qb.select(STUDENT_SELECT, em).getResultList();
        return resultToStudentSearchDto(result);
    }

    private static List<StudentSearchDto> resultToStudentSearchDto(List<?> result) {
        return StreamUtil.toMappedList(r -> {
            StudentSearchDto dto = new StudentSearchDto();
            dto.setId(resultAsLong(r, 0));
            String firstname = resultAsString(r, 1);
            String lastname = resultAsString(r, 2);
            dto.setFullname(PersonUtil.fullname(firstname, lastname));
            dto.setCurriculum(new AutocompleteResult(null, resultAsString(r, 3), resultAsString(r, 3)));
            dto.setStudentGroup(new AutocompleteResult(null, resultAsString(r, 4), resultAsString(r, 4)));
            return dto;
        }, result);
    }

    public List<HigherProtocolStudentResultDto> calculateGrades(HigherProtocolCalculateCommand command) {
        List<HigherProtocolStudentResultDto> calculatedResults = new ArrayList<>();
        for(Long protocolStudentId : command.getProtocolStudents()) {
            ProtocolStudent ps = protocolStudentRepository.getOne(protocolStudentId);
            String grade = HigherProtocolGradeUtil.calculateGrade(ps);
            calculatedResults.add(new HigherProtocolStudentResultDto(protocolStudentId, grade));
        }
        return calculatedResults;
    }

    public void setStudentsPracticeResults(HigherProtocolDto dto) {
        if(Boolean.TRUE.equals(dto.getSubjectStudyPeriodMidtermTaskDto().getSubjectStudyPeriod().getIsPracticeSubject())) {

            Set<Long> students = StreamUtil.toMappedSet(ps -> ps.getStudent().getId(), dto.getProtocolStudents());
            JpaQueryUtil.NativeQueryBuilder qb = new JpaQueryUtil.NativeQueryBuilder("from practice_journal pj join classifier c on c.code = pj.grade_code");

            qb.requiredCriteria("pj.student_id in(:studentId)", "studentId", students);
            qb.requiredCriteria("pj.subject_id = :subjectId", "subjectId", dto.getSubjectStudyPeriodMidtermTaskDto().getSubjectStudyPeriod().getSubject().getId());

            List<?> data = qb.select("pj.student_id, c.value", em).getResultList();

            assertDoesNotHaveDuplicates(data);

            Map<Long, String> practiceResults = StreamUtil.toMap(r -> resultAsLong(r, 0),  r -> resultAsString(r, 1), data);

            for(HigherProtocolStudentDto protocolStudent : dto.getProtocolStudents()) {
                if(practiceResults.containsKey(protocolStudent.getStudent().getId())) {
                    protocolStudent.setPracticeResult(practiceResults.get(protocolStudent.getStudent().getId()));
                }
            }
        }
    }

    private static void assertDoesNotHaveDuplicates(List<?> data) {
        Set<Long> existingIds = new HashSet<>();
        for (Object r : data) {
            Long id = resultAsLong(r, 0);
            if (existingIds.contains(id)) {
                throw new ValidationFailedException("higherProtocol.error.duplicatesInPracticeResults");
            }
            existingIds.add(id);
        }
    }
}
