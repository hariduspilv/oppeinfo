package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.propertyContains;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.List;
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

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolHdata;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.enums.HigherProtocolAssessment;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.enums.ProtocolType;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.ProtocolRepository;
import ee.hitsa.ois.repository.SchoolRepository;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.repository.SubjectStudyPeriodRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.HigherProtocolUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.ProtocolUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.web.commandobject.HigherProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.HigherProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.HigherProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.HigherProtocolStudentSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.HigherProtocolSearchDto;
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
    
    private static final String SELECT_STUDENTS_BASIC_PROTOCOL = 
              "select p.id from protocol p "
            + "join protocol_hdata ph on p.id = ph.protocol_id "
            + "left join protocol_student ps on p.id = ps.protocol_id "
            + "where ph.type_code = '" + ProtocolType.PROTOKOLLI_LIIK_P.name() + "' "
            + "and ps.student_id = s.id ";
    
    /**
     * Student has any PROTOKOLLI_LIIK_P protocol
     * with status other than KORGHINDAMINE_MI
     */
    private static final String STUDENT_CAN_HAVE_REPEATING_PROTOCOL = 
            "exists ("
            + SELECT_STUDENTS_BASIC_PROTOCOL 
            + "and ps.grade_code <> '" + HigherProtocolAssessment.KORGHINDAMINE_MI.name() 
            + "')";
    
    /**
     * Student has no PROTOKOLLI_LIIK_P protocol 
     * or its status is KORGHINDAMINE_MI
     */
    private static final String STUDENT_CAN_HAVE_BASIC_PROTOCOL = ""
            + "not exists ("
            + SELECT_STUDENTS_BASIC_PROTOCOL
            + ") "
          + "or exists ("
            + SELECT_STUDENTS_BASIC_PROTOCOL
            + "and ps.grade_code = '" + HigherProtocolAssessment.KORGHINDAMINE_MI.name() 
            + "')"
          + "and not " + STUDENT_CAN_HAVE_REPEATING_PROTOCOL;

            
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
                filters.add(cb.or(subjectFilters.toArray(new Predicate[filters.size()])));
            }
            if(criteria.getTeacher() != null) {
                Subquery<Long> targetQuery = query.subquery(Long.class);
                Root<SubjectStudyPeriodTeacher> targetRoot = targetQuery.from(SubjectStudyPeriodTeacher.class);
                targetQuery = targetQuery.select(targetRoot.get("subjectStudyPeriod").get("id"))
                        .where(cb.equal(targetRoot.get("teacher").get("id"), criteria.getTeacher()));                
                filters.add(root.get("protocolHdata").get("subjectStudyPeriod").get("id").in(targetQuery));
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
        return protocolRepository.save(protocol);
    }

    public Protocol confirm(Protocol protocol, HoisUserDetails user) {
        protocol.setStatus(classifierRepository.getOne(ProtocolStatus.PROTOKOLL_STAATUS_K.name()));
        protocol.setConfirmer(user.getUsername());
        return protocolRepository.save(protocol);
    }

    public Protocol saveAndConfirm(HoisUserDetails user, Protocol protocol, HigherProtocolSaveForm form) {
        protocol.setStatus(classifierRepository.getOne(ProtocolStatus.PROTOKOLL_STAATUS_K.name()));
        protocol.setConfirmer(user.getUsername());
        return save(protocol, form);
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
            qb.filter(STUDENT_CAN_HAVE_BASIC_PROTOCOL);
        } else {
            qb.filter(STUDENT_CAN_HAVE_REPEATING_PROTOCOL);
        }
        List<?> result = qb.select(STUDENT_SELECT, em).getResultList();
        return resultToStudentSearchDto(result);
    }

    private List<StudentSearchDto> resultToStudentSearchDto(List<?> result) {
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
}
