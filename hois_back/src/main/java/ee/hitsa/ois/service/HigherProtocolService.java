package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDateTime;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodExamStudent;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.enums.ExamType;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.enums.ProtocolType;
import ee.hitsa.ois.report.HigherProtocolReport;
import ee.hitsa.ois.repository.ProtocolRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HigherProtocolGradeUtil;
import ee.hitsa.ois.util.HigherProtocolUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.HigherProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.HigherProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.HigherProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.HigherProtocolStudentSearchCommand;
import ee.hitsa.ois.web.commandobject.ProtocolCalculateCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.HigherProtocolDto;
import ee.hitsa.ois.web.dto.HigherProtocolSearchDto;
import ee.hitsa.ois.web.dto.HigherProtocolStudentDto;
import ee.hitsa.ois.web.dto.ProtocolPracticeJournalResultDto;
import ee.hitsa.ois.web.dto.ProtocolStudentResultDto;
import ee.hitsa.ois.web.dto.student.StudentSearchDto;

@Transactional
@Service
public class HigherProtocolService extends AbstractProtocolService {

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

    @Autowired
    private ProtocolRepository protocolRepository;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private SchoolService schoolService;

    public Page<HigherProtocolSearchDto> search(HoisUserDetails user, HigherProtocolSearchCommand criteria,
            Pageable pageable) {

        Page<Protocol> protocols = protocolRepository.findAll((root, query, cb) -> {

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("isFinal"), Boolean.FALSE));
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
                        .where(cb.equal(targetRoot.get("teacher").get("id"), user.getTeacherId()));
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

    public HigherProtocolDto create(HoisUserDetails user, HigherProtocolCreateForm form) {
        Protocol protocol = new Protocol();
        protocol.setIsFinal(Boolean.FALSE);
        protocol.setIsVocational(Boolean.FALSE);
        protocol.setProtocolNr(generateProtocolNumber());
        protocol.setSchool(em.getReference(School.class, user.getSchoolId()));
        protocol.setStatus(em.getReference(Classifier.class, ProtocolStatus.PROTOKOLL_STAATUS_S.name()));

        ProtocolHdata protocolHData = new ProtocolHdata();
        protocolHData.setType(em.getReference(Classifier.class, form.getProtocolType()));
        protocolHData.setSubjectStudyPeriod(em.getReference(SubjectStudyPeriod.class, form.getSubjectStudyPeriod()));
        protocolHData.setProtocol(protocol);
        protocol.setProtocolHdata(protocolHData);

        Map<Long, Long> examRegistrations = new HashMap<>();
        Set<Long> studentIds = form.getStudents();
        if(studentIds != null && !studentIds.isEmpty()) {
            boolean repeating = ClassifierUtil.equals(ProtocolType.PROTOKOLLI_LIIK_K, protocolHData.getType());
            if(repeating) {
                List<?> data = em.createNativeQuery("select d.student_id, sspes.id from subject_study_period_exam_student sspes "
                        + "join subject_study_period_exam sspe on sspes.subject_study_period_exam_id = sspe.id "
                        + "join declaration_subject ds on sspes.declaration_subject_id = ds.id "
                        + "join declaration d on ds.declaration_id = d.id "
                        + "where sspe.type_code = ?1 and ds.subject_study_period_id = ?2 "
                        + "and not exists (select ps.id from protocol_student ps where ps.subject_study_period_exam_student_id = sspes.id)")
                    .setParameter(1, ExamType.SOORITUS_K.name())
                    .setParameter(2, EntityUtil.getId(protocolHData.getSubjectStudyPeriod()))
                    .getResultList();
                data.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> resultAsLong(r, 1), (o, n) -> o, () -> examRegistrations));
            }
        }
        protocol.setProtocolStudents(StreamUtil.toMappedList(studentId -> {
            ProtocolStudent protocolStudent =  new ProtocolStudent();
            protocolStudent.setStudent(em.getReference(Student.class, studentId));
            protocolStudent.setProtocol(protocol);
            // link with exam
            Long examRegistrationId = examRegistrations.get(studentId);
            protocolStudent.setSubjectStudyPeriodExamStudent(EntityUtil.getOptionalOne(SubjectStudyPeriodExamStudent.class, examRegistrationId, em));
            return protocolStudent;
        }, studentIds));

        return HigherProtocolDto.ofWithIdOnly(EntityUtil.save(protocol, em));
    }

    public Protocol save(Protocol protocol, HigherProtocolSaveForm form) {
        updateProtocolStudents(protocol, form);
        return EntityUtil.save(protocol, em);
    }

    private void updateProtocolStudents(Protocol protocol, HigherProtocolSaveForm form) {
        Boolean isLetterGrade = protocol.getSchool().getIsLetterGrade();
        EntityUtil.bindEntityCollection(protocol.getProtocolStudents(), ProtocolStudent::getId,
                // no protocol students created here
                form.getProtocolStudents(), HigherProtocolStudentDto::getId, null, (dto, ps) -> {
                    if (gradeChangedButNotRemoved(dto, ps)) {
                        HigherProtocolUtil.assertHasAddInfoIfProtocolConfirmed(dto, protocol);
                        addHistory(ps);
                        Classifier grade = em.getReference(Classifier.class, dto.getGrade());
                        Short mark = HigherAssessment.getGradeMark(dto.getGrade());
                        gradeStudent(ps, grade, mark, isLetterGrade);
                    } else if (gradeRemoved(dto, ps)) {
                        HigherProtocolUtil.assertHasAddInfoIfProtocolConfirmed(dto, protocol);
                        addHistory(ps);
                        removeGrade(ps);
                    }
                    ps.setAddInfo(dto.getAddInfo());
                });
    }

    public Protocol confirm(HoisUserDetails user, Protocol protocol, HigherProtocolSaveForm form) {
        setConfirmation(user, protocol);
        
        if (form != null) {
            updateProtocolStudents(protocol, form);
        }
        protocol = EntityUtil.save(protocol, em);
        
        for (ProtocolStudent protocolStudent : protocol.getProtocolStudents()) {
            if (protocolStudent.getGrade() == null) {
                throw new ValidationFailedException("higherProtocol.message.gradeNotSelectedForAllStudents");
            }
        }
        sendStudentResultMessages(protocol);
        return protocol;
    }

    public List<AutocompleteResult> getSubjectStudyPeriods(Long schoolId) {
        Long studyPeriodId = studyYearService.getCurrentStudyPeriod(schoolId);
        if(studyPeriodId == null) {
            return Collections.emptyList();
        }

        List<SubjectStudyPeriod> ssps = em.createQuery("select ssp from SubjectStudyPeriod ssp where ssp.studyPeriod.id = ?1", SubjectStudyPeriod.class)
                .setParameter(1, studyPeriodId).getResultList();
        Map<Long, List<String>> allTeachers = new HashMap<>();
        Set<Long> sspIds = StreamUtil.toMappedSet(SubjectStudyPeriod::getId, ssps);
        if(!sspIds.isEmpty()) {
            List<?> data = em.createNativeQuery("select sspt.subject_study_period_id, p.firstname, p.lastname from subject_study_period_teacher sspt "
                    + "join teacher t on sspt.teacher_id = t.id "
                    + "join person p on t.person_id = p.id where sspt.subject_study_period_id in (?1) order by p.lastname, p.firstname")
                .setParameter(1, sspIds)
                .getResultList();
            data.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), () -> allTeachers,
                    Collectors.mapping(r -> PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)), Collectors.toList())));
        }
        return StreamUtil.toMappedList(ssp -> {
            Subject s = ssp.getSubject();
            List<String> teachers = allTeachers.get(ssp.getId());
            String teacherNames = teachers != null ? (" - " + String.join(", ", teachers)) : "";
            String nameEt = SubjectUtil.subjectName(s.getCode(), s.getNameEt(), s.getCredits()) + teacherNames;
            String nameEn = SubjectUtil.subjectName(s.getCode(), s.getNameEn(), s.getCredits()) + teacherNames;
            return new AutocompleteResult(ssp.getId(), nameEt, nameEn);
        }, ssps);
    }

    public List<StudentSearchDto> getStudents(Long schoolId, HigherProtocolStudentSearchCommand criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(STUDENT_FROM);

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("d.status_code = :status", "status", DeclarationStatus.OPINGUKAVA_STAATUS_K);
        qb.requiredCriteria("ds.subject_study_period_id = :subjectStudyPeriodId",
                "subjectStudyPeriodId", criteria.getSubjectStudyPeriod());

        boolean repeating = ProtocolType.PROTOKOLLI_LIIK_K.name().equals(criteria.getProtocolType());
        if(repeating) {
            qb.filter("exists (select p.id from protocol p " 
                    + "join protocol_hdata ph on p.id = ph.protocol_id "
                    + "left join protocol_student ps on p.id = ps.protocol_id "
                    + "where ph.type_code = '" + ProtocolType.PROTOKOLLI_LIIK_P.name() + "' "
                    + "and ph.subject_study_period_id = ds.subject_study_period_id and ps.student_id = s.id "
                    + "and ps.grade_code is not null)");
            
            // student has registration for exam and no other protocol is pointing to same exam
            qb.filter("exists (select sspes.id from subject_study_period_exam_student sspes "
                    + "join subject_study_period_exam sspe on sspes.subject_study_period_exam_id = sspe.id "
                    + "left join protocol_student ps on ps.subject_study_period_exam_student_id = sspes.id "
                    + "where sspes.declaration_subject_id = ds.id and sspe.type_code = '"+ExamType.SOORITUS_K.name()+"' and ps.id is null)");
        } else {
            qb.filter("not exists (select p.id from protocol p " 
                    + "join protocol_hdata ph on p.id = ph.protocol_id "
                    + "left join protocol_student ps on p.id = ps.protocol_id "
                    + "where ph.type_code = '" + ProtocolType.PROTOKOLLI_LIIK_P.name() + "' "
                    + "and ph.subject_study_period_id = ds.subject_study_period_id and ps.student_id = s.id)");
        }

        qb.sort("p.firstname, p.lastname");
        List<?> result = qb.select(STUDENT_SELECT, em).getResultList();
        return StreamUtil.toMappedList(r -> {
            StudentSearchDto dto = new StudentSearchDto();
            dto.setId(resultAsLong(r, 0));
            String firstname = resultAsString(r, 1);
            String lastname = resultAsString(r, 2);
            dto.setFullname(PersonUtil.fullname(firstname, lastname));
            dto.setStudentGroup(new AutocompleteResult(null, resultAsString(r, 3), resultAsString(r, 3)));
            dto.setCurriculum(new AutocompleteResult(null, resultAsString(r, 4), resultAsString(r, 4)));
            return dto;
        }, result);
    }

    public List<ProtocolStudentResultDto> calculateGrades(ProtocolCalculateCommand command) {
        List<ProtocolStudentResultDto> calculatedResults = new ArrayList<>();
        for(Long protocolStudentId : command.getProtocolStudents()) {
            ProtocolStudent ps = em.getReference(ProtocolStudent.class, protocolStudentId);
            HigherAssessment grade = HigherProtocolGradeUtil.calculateGrade(ps);
            calculatedResults.add(new ProtocolStudentResultDto(protocolStudentId, grade));
        }
        return calculatedResults;
    }

    public HigherProtocolDto get(HoisUserDetails user, Protocol protocol) {
        HigherProtocolDto dto = HigherProtocolDto.ofWithUserRights(user, protocol);

        if(Boolean.TRUE.equals(dto.getSubjectStudyPeriodMidtermTaskDto().getSubjectStudyPeriod().getIsPracticeSubject())) {
            Set<Long> students = StreamUtil.toMappedSet(ps -> ps.getStudent().getId(), dto.getProtocolStudents());
            JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from practice_journal pj "
                    + "join practice_journal_module_subject pjms on pjms.practice_journal_id = pj.id");

            qb.requiredCriteria("pj.student_id in (:studentId)", "studentId", students);
            qb.requiredCriteria("pjms.subject_id = :subjectId", "subjectId",
                    dto.getSubjectStudyPeriodMidtermTaskDto().getSubjectStudyPeriod().getSubject().getId());
            qb.filter("pj.grade_code is not null");

            List<?> data = qb.select("pj.student_id, pj.id, pj.grade_code, pj.grade_inserted", em).getResultList();

            Map<Long, List<ProtocolPracticeJournalResultDto>> practiceResults = StreamUtil.nullSafeList(data).stream()
                    .collect(Collectors.groupingBy(r -> resultAsLong(r, 0), Collectors.mapping(r ->
                    new ProtocolPracticeJournalResultDto(resultAsLong(r, 1), resultAsString(r, 2),
                            resultAsLocalDateTime(r, 3), null), Collectors.toList())));

            for(HigherProtocolStudentDto protocolStudent : dto.getProtocolStudents()) {
                Long studentId = protocolStudent.getStudent().getId();
                List<ProtocolPracticeJournalResultDto> studentResults = practiceResults.get(studentId);
                if (studentResults != null) {
                    protocolStudent.setPracticeJournalResults(studentResults);
                }
            }
        }
        return dto;
    }

    public HigherProtocolReport higherProtocolReport(Protocol protocol) {
        School school = protocol.getSchool();
        Boolean isHigherSchool = Boolean.valueOf(schoolService.schoolType(EntityUtil.getId(school)).isHigher());
        return new HigherProtocolReport(protocol, isHigherSchool, school.getIsLetterGrade());
    }
}
