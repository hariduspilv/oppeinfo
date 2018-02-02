package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.repository.ProtocolRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.FinalExamProtocolUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.ProtocolUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.web.commandobject.HigherProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.finalexamprotocol.FinalExamHigherProtocolCreateForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.HigherProtocolSearchDto;
import ee.hitsa.ois.web.dto.finalexamprotocol.FinalExamHigherProtocolDto;
import ee.hitsa.ois.web.dto.finalexamprotocol.FinalExamHigherProtocolStudentDto;
import ee.hitsa.ois.web.dto.finalexamprotocol.FinalExamHigherProtocolSubjectDto;
import ee.hitsa.ois.web.dto.finalexamprotocol.FinalExamHigherProtocolSubjectResult;

@Transactional
@Service
public class FinalExamHigherProtocolService {

    private static final String STUDENT_FROM =
              "from student s "
            + "join person p on p.id = s.person_id "
            + "join student_group sg on sg.id = s.student_group_id "
            + "join curriculum_version cv on cv.id = s.curriculum_version_id "
            + "join curriculum c on c.id = cv.curriculum_id "
            + "left join declaration d on d.student_id = s.id "
            + "left join declaration_subject ds on d.id = ds.declaration_id";

    @Autowired
    private EntityManager em;
    @Autowired
    private ProtocolRepository protocolRepository;
    @Autowired
    private StudyYearService studyYearService;
    
    public Page<HigherProtocolSearchDto> search(HoisUserDetails user, HigherProtocolSearchCommand criteria,
            Pageable pageable) {

        Page<Protocol> protocols = protocolRepository.findAll((root, query, cb) -> {

            List<Predicate> filters = new ArrayList<>();
            filters.add(cb.equal(root.get("isFinal"), Boolean.TRUE));
            filters.add(cb.equal(root.get("isVocational"), Boolean.FALSE));
            filters.add(cb.equal(root.get("school").get("id"), user.getSchoolId()));

            filters.add(cb.equal(root.get("protocolHdata").get("subjectStudyPeriod").get("studyPeriod").get("id"),
                    criteria.getStudyPeriod()));

            if (criteria.getStatus() != null) {
                filters.add(cb.equal(root.get("status").get("code"), criteria.getStatus()));
            }
            if (criteria.getProtocolNr() != null) {
                filters.add(cb.equal(root.get("protocolNr"), criteria.getProtocolNr()));
            }
            if (criteria.getSubject() != null) {
                List<Predicate> subjectFilters = new ArrayList<>();
                propertyContains(() -> root.get("protocolHdata").get("subjectStudyPeriod").get("subject").get("code"),
                        cb, criteria.getSubject(), subjectFilters::add);
                propertyContains(() -> root.get("protocolHdata").get("subjectStudyPeriod").get("subject").get("nameEt"),
                        cb, criteria.getSubject(), subjectFilters::add);
                propertyContains(() -> root.get("protocolHdata").get("subjectStudyPeriod").get("subject").get("nameEn"),
                        cb, criteria.getSubject(), subjectFilters::add);
                filters.add(cb.or(subjectFilters.toArray(new Predicate[subjectFilters.size()])));
            }
            if (user.isTeacher()) {
                Subquery<Long> targetQuery = query.subquery(Long.class);
                Root<SubjectStudyPeriodTeacher> targetRoot = targetQuery.from(SubjectStudyPeriodTeacher.class);
                targetQuery = targetQuery.select(targetRoot.get("subjectStudyPeriod").get("id"))
                        .where(cb.equal(targetRoot.get("teacher").get("id"), user.getTeacherId()));
                filters.add(root.get("protocolHdata").get("subjectStudyPeriod").get("id").in(targetQuery));
            } else {
                if (criteria.getTeacher() != null) {
                    Subquery<Long> targetQuery = query.subquery(Long.class);
                    Root<SubjectStudyPeriodTeacher> targetRoot = targetQuery.from(SubjectStudyPeriodTeacher.class);
                    targetQuery = targetQuery.select(targetRoot.get("subjectStudyPeriod").get("id"))
                            .where(cb.equal(targetRoot.get("teacher").get("id"), criteria.getTeacher()));
                    filters.add(root.get("protocolHdata").get("subjectStudyPeriod").get("id").in(targetQuery));
                }
            }

            if (criteria.getInsertedFrom() != null) {
                filters.add(cb.greaterThanOrEqualTo(root.get("inserted"),
                        DateUtils.firstMomentOfDay(criteria.getInsertedFrom())));
            }
            if (criteria.getInsertedThru() != null) {
                filters.add(cb.lessThanOrEqualTo(root.get("inserted"),
                        DateUtils.lastMomentOfDay(criteria.getInsertedThru())));
            }
            if (criteria.getConfirmDateFrom() != null) {
                filters.add(cb.greaterThanOrEqualTo(root.get("confirmDate"), criteria.getConfirmDateFrom()));
            }
            if (criteria.getConfirmDateThru() != null) {
                filters.add(cb.lessThanOrEqualTo(root.get("confirmDate"), criteria.getConfirmDateThru()));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, pageable);

        return protocols.map(p -> HigherProtocolSearchDto.ofWithUserRithts(p, user));
    }
    
    public FinalExamHigherProtocolDto finalExamVocationalProtocol(HoisUserDetails user, Protocol protocol) {
        FinalExamHigherProtocolDto dto = FinalExamHigherProtocolDto.of(protocol);
        dto.setCanBeEdited(Boolean.valueOf(FinalExamProtocolUtil.canEdit(user, protocol)));
        dto.setCanBeDeleted(Boolean.valueOf(FinalExamProtocolUtil.canDelete(user, protocol)));
        return dto;
    }
    
    public Protocol create(HoisUserDetails user, FinalExamHigherProtocolCreateForm form) {
        Protocol protocol = new Protocol();
        protocol.setIsFinal(Boolean.TRUE);
        protocol.setIsVocational(Boolean.FALSE);
        //TODO: protocolNr
        protocol.setProtocolNr(ProtocolUtil.generateProtocolNumber(em));
        protocol.setSchool(em.getReference(School.class, user.getSchoolId()));
        protocol.setStatus(em.getReference(Classifier.class, ProtocolStatus.PROTOKOLL_STAATUS_S.name()));

        ProtocolHdata protocolHData = new ProtocolHdata();
        // TODO: type?
        protocolHData.setType(em.getReference(Classifier.class, form.getProtocolType()));
        protocolHData.setSubjectStudyPeriod(em.getReference(SubjectStudyPeriod.class, form.getSubjectStudyPeriod()));
        protocolHData.setProtocol(protocol);
        protocol.setProtocolHdata(protocolHData);

        protocol.setProtocolStudents(StreamUtil.toMappedList(dto -> {
            ProtocolStudent protocolStudent = EntityUtil.bindToEntity(dto, new ProtocolStudent());
            protocolStudent.setStudent(em.getReference(Student.class, dto.getStudentId()));
            return protocolStudent;
        }, form.getProtocolStudents()));

        return EntityUtil.save(protocol, em);
    }
    
    public List<AutocompleteResult> subjectStudyPeriodsForSelection(Long schoolId) {
        Long studyPeriod = studyYearService.getCurrentStudyPeriod(schoolId);

        List<SubjectStudyPeriod> ssps = em.createQuery("select ssp from SubjectStudyPeriod ssp where ssp.studyPeriod.id = ?1", SubjectStudyPeriod.class)
                .setParameter(1, studyPeriod).getResultList();
        return StreamUtil.toMappedList(ssp -> {
            Subject s = ssp.getSubject();
            String nameEt = SubjectUtil.subjectName(s.getCode(), s.getNameEt(), s.getCredits());
            String nameEn = SubjectUtil.subjectName(s.getCode(), s.getNameEn(), s.getCredits());
            return new AutocompleteResult(ssp.getId(), nameEt, nameEn);
        }, ssps);
    }

    public List<FinalExamHigherProtocolSubjectResult> subjectsForSelection(HoisUserDetails user, Long studyPeriodId) {
        String from = "from subject s"
                + " inner join subject_study_period ssp on ssp.subject_id = s.id";

        if (user.getTeacherId() != null) {
            from += " inner join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id";
        }

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("s.status_code = :statusCode", "statusCode", SubjectStatus.AINESTAATUS_K.name());
        qb.requiredCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", studyPeriodId);
        qb.optionalCriteria("sspt.teacher_id = .teacherId", "teacherId", user.getTeacherId());

        String select = "s.id as subjectId, s.name_et, s.name_en, ssp.id as studyPeriodId";
        List<?> data = qb.select(select, em).getResultList();

        List<FinalExamHigherProtocolSubjectResult> results = new ArrayList<>();
        for (Object r : data) {
            results.add(new FinalExamHigherProtocolSubjectResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2), resultAsLong(r, 3)));
        }
        return results;
    }
    
    public FinalExamHigherProtocolSubjectDto subject(HoisUserDetails user, Long subjectId) {
        FinalExamHigherProtocolSubjectDto dto = new FinalExamHigherProtocolSubjectDto();
        dto.setSubjectStudents(subjectStudents(user, subjectId));

        return dto;
    }
    
    public Collection<FinalExamHigherProtocolStudentDto> subjectStudents(HoisUserDetails user,
            Long subjectStudyPeriod) {
        Map<Long, FinalExamHigherProtocolStudentDto> result = studentsForSelection(user.getSchoolId(), subjectStudyPeriod);
        return result.values();
    }
    
    public Map<Long, FinalExamHigherProtocolStudentDto> studentsForSelection(Long schoolId, Long subjectStudyPeriod) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(STUDENT_FROM);

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("d.status_code = :status", "status", DeclarationStatus.OPINGUKAVA_STAATUS_K);
        qb.requiredCriteria("ds.subject_study_period_id = :subjectStudyPeriodId",
                "subjectStudyPeriodId", subjectStudyPeriod);

        /*
        if(ProtocolType.PROTOKOLLI_LIIK_P.name().equals(criteria.getProtocolType())) {
            qb.filter(" not " + STUDENT_HAS_BASIC_PROTOCOL);
        } else {
            qb.filter(STUDENT_HAS_BASIC_PROTOCOL);
        }
        */

        List<?> students = qb.select("s.id, p.firstname, p.lastname, p.idcode as idCode, s.status_code as studentStatusCode,"
                + " sg.code as studentGroupCode, c.id as curriculumId,"
                + " c.name_et as curriculumNameEt, c.name_en as curriculumNameEn", em).getResultList();
        
        return students.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> {
            FinalExamHigherProtocolStudentDto dto = new FinalExamHigherProtocolStudentDto();
            dto.setStudentId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setIdcode(resultAsString(r, 3));
            dto.setStatus(resultAsString(r, 4));
            dto.setStudentGroup(resultAsString(r, 5));
            dto.setCurriculum(new AutocompleteResult(resultAsLong(r, 6), resultAsString(r, 7), resultAsString(r, 8)));
            return dto;
        }));
    }
}