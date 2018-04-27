package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Committee;
import ee.hitsa.ois.domain.CommitteeMember;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.protocol.ProtocolCommitteeMember;
import ee.hitsa.ois.domain.protocol.ProtocolHdata;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.protocol.ProtocolStudentOccupation;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentOccupationCertificate;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.enums.ProtocolType;
import ee.hitsa.ois.enums.SubjectAssessment;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.FinalExamProtocolUtil;
import ee.hitsa.ois.util.HigherProtocolUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.web.commandobject.HigherProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.finalexamprotocol.FinalExamHigherProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.finalexamprotocol.FinalExamHigherProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.finalexamprotocol.FinalExamHigherProtocolStudentSaveForm;
import ee.hitsa.ois.web.commandobject.finalexamprotocol.FinalExamProtocolCommitteeMemberForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.HigherProtocolSearchDto;
import ee.hitsa.ois.web.dto.finalexamprotocol.FinalExamHigherProtocolDto;
import ee.hitsa.ois.web.dto.finalexamprotocol.FinalExamHigherProtocolStudentDto;
import ee.hitsa.ois.web.dto.finalexamprotocol.FinalExamHigherProtocolSubjectDto;

@Transactional
@Service
public class FinalExamHigherProtocolService extends AbstractProtocolService {

    private static final String STUDENT_FROM =
              "from student s "
            + "join person p on p.id = s.person_id "
            + "join student_group sg on sg.id = s.student_group_id "
            + "join curriculum_version cv on cv.id = s.curriculum_version_id "
            + "join curriculum c on c.id = cv.curriculum_id "
            + "left join declaration d on d.student_id = s.id "
            + "left join declaration_subject ds on d.id = ds.declaration_id";

    @Autowired
    private StudyYearService studyYearService;
    
    public Page<HigherProtocolSearchDto> search(HoisUserDetails user, HigherProtocolSearchCommand criteria,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(
                "from protocol p " + "inner join protocol_hdata phd on phd.protocol_id = p.id").sort(pageable);
            
        qb.filter("p.is_final = true");
        qb.filter("p.is_vocational = false");
        qb.requiredCriteria("p.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria(
                "exists (select protocol_id from protocol_hdata phd "
                        + "join subject_study_period ssp on phd.subject_study_period_id = ssp.id "
                        + "where phd.protocol_id = p.id and ssp.study_period_id = :studyPeriodId)",
                "studyPeriodId", criteria.getStudyPeriod());
        qb.optionalCriteria("p.status_code = :statusCode", "statusCode", criteria.getStatus());
        qb.optionalCriteria("p.protocol_nr = :protocolNr", "protocolNr", criteria.getProtocolNr());
        qb.optionalCriteria("p.inserted >= :from", "from", criteria.getInsertedFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("p.inserted <= :thru", "thru", criteria.getInsertedThru(), DateUtils::lastMomentOfDay);
        qb.optionalCriteria("p.confirm_date >= :from", "from", criteria.getConfirmDateFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("p.confirm_date <= :thru", "thru", criteria.getConfirmDateThru(), DateUtils::lastMomentOfDay);

        qb.optionalCriteria(
                "exists (select protocol_id " + "from protocol_hdata phd "
                        + "join subject_study_period ssp on phd.subject_study_period_id = ssp.id "
                        + "join subject s on ssp.subject_id = s.id "
                        + "where upper(s.code) like lower(:subject) or upper(s.name_et) like :subject or upper(s.name_en) like :subject)",
                "subject", criteria.getSubject(), JpaQueryUtil::toContains);
        
        if (user.isTeacher()) {
            qb.optionalCriteria(
                    "exists (select protocol_id " + "from protocol_hdata phd "
                            + "join subject_study_period ssp on phd.subject_study_period_id = ssp.id "
                            + "join subject_study_period_teacher sspt on ssp.id = sspt.subject_study_period_id "
                            + "where sspt.teacher_id = :teacher)",
                    "teacher", user.getTeacherId());
        } else {
            qb.optionalCriteria("exists (select protocol_id " + "from protocol_hdata phd "
                    + "join subject_study_period ssp on phd.subject_study_period_id = ssp.id "
                    + "join subject_study_period_teacher sspt on ssp.id = sspt.subject_study_period_id "
                    + "where sspt.teacher_id = :teacher)", "teacher", criteria.getTeacher());
        }

        Page<HigherProtocolSearchDto> result = JpaQueryUtil.pagingResult(qb, "p.id", em, pageable).map(r -> {
            return HigherProtocolSearchDto.ofWithUserRithts(em.getReference(Protocol.class, resultAsLong(r, 0)), user);
        });

        return result;
    }

    public FinalExamHigherProtocolDto finalExamHigherProtocol(HoisUserDetails user, Protocol protocol) {
        includeCorrectImportedOccupationCertificates(protocol);
        
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
        protocol.setProtocolNr(generateProtocolNumber());
        protocol.setSchool(em.getReference(School.class, user.getSchoolId()));
        protocol.setStatus(em.getReference(Classifier.class, ProtocolStatus.PROTOKOLL_STAATUS_S.name()));

        ProtocolHdata protocolHData = new ProtocolHdata();
        protocolHData.setType(em.getReference(Classifier.class, ProtocolType.PROTOKOLLI_LIIK_P.name()));
        protocolHData.setSubjectStudyPeriod(em.getReference(SubjectStudyPeriod.class, form.getSubjectStudyPeriod()));
        protocolHData.setCurriculum(em.getReference(Curriculum.class, form.getCurriculum()));
        protocolHData.setProtocol(protocol);
        protocol.setProtocolHdata(protocolHData);
        
        List<Long> studentIds = StreamUtil.toMappedList(ps -> ps.getStudentId(), form.getProtocolStudents());
        Set<String> occupations = curriculumOccpations(protocol);
        Map<Long, List<StudentOccupationCertificate>> studentOccupationCertificates = studentOccupationCertificates(studentIds, occupations);

        protocol.setProtocolStudents(StreamUtil.toMappedList(dto -> {
            ProtocolStudent protocolStudent = EntityUtil.bindToEntity(dto, new ProtocolStudent());
            protocolStudent.setStudent(em.getReference(Student.class, dto.getStudentId()));

            List<StudentOccupationCertificate> studentCertificates = studentOccupationCertificates.get(dto.getStudentId());
            if (studentCertificates != null) {
                for(StudentOccupationCertificate sc : studentCertificates) {
                    addStudentOccupationCertificateToProtocol(protocolStudent, sc);
                }
            }
            return protocolStudent;
        }, form.getProtocolStudents()));

        protocol.setProtocolStudents(StreamUtil.toMappedList(dto -> {
            ProtocolStudent protocolStudent = EntityUtil.bindToEntity(dto, new ProtocolStudent());
            protocolStudent.setStudent(em.getReference(Student.class, dto.getStudentId()));
            return protocolStudent;
        }, form.getProtocolStudents()));

        return EntityUtil.save(protocol, em);
    }
    
    public Protocol save(Protocol protocol, FinalExamHigherProtocolSaveForm form) {
        EntityUtil.bindToEntity(form, protocol, "committee", "protocolStudents", "protocolCommitteeMembers");
        protocol.setCommittee(form.getCommitteeId() != null ? em.getReference(Committee.class, form.getCommitteeId()) : null);
        saveCommitteeMembers(protocol, form);
        saveStudents(protocol, form);
        
        return EntityUtil.save(protocol, em);
    }
    
    private void saveCommitteeMembers(Protocol protocol, FinalExamHigherProtocolSaveForm form) {
        EntityUtil.bindEntityCollection(protocol.getProtocolCommitteeMembers(), ProtocolCommitteeMember::getId,
                form.getProtocolCommitteeMembers(), FinalExamProtocolCommitteeMemberForm::getId, dto -> {
                    ProtocolCommitteeMember pcm = EntityUtil.bindToEntity(dto, new ProtocolCommitteeMember(), "committeeMember");
                    pcm.setCommitteeMember(em.getReference(CommitteeMember.class, dto.getCommitteeMemberId()));
                    return pcm;
                });
    }

    private void saveStudents(Protocol protocol, FinalExamHigherProtocolSaveForm form) {
        EntityUtil.bindEntityCollection(protocol.getProtocolStudents(), ProtocolStudent::getId,
                form.getProtocolStudents(), FinalExamHigherProtocolStudentSaveForm::getId, dto -> {
                    ProtocolStudent ps = EntityUtil.bindToEntity(dto, new ProtocolStudent(), "student");
                    ps.setStudent(em.getReference(Student.class, dto.getStudentId()));
                    saveOccupationCertificates(ps, dto);
                    return ps;
                }, (dto, ps) -> {
                    if (gradeChangedButNotRemoved(dto, ps)) {
                        HigherProtocolUtil.assertHasAddInfoIfProtocolConfirmed(dto, protocol);
                        addHistory(ps);
                        Classifier grade = em.getReference(Classifier.class, dto.getGrade());
                        Short mark = getHigherGradeMark(grade);
                        gradeStudent(ps, grade, mark);
                        ps.setAddInfo(dto.getAddInfo());
                    } else if (gradeRemoved(dto, ps)) {
                        HigherProtocolUtil.assertHasAddInfoIfProtocolConfirmed(dto, protocol);
                        addHistory(ps);
                        removeGrade(ps);
                    }
                    ps.setCurriculumGrade(dto.getCurriculumGrade() != null
                            ? em.getReference(CurriculumGrade.class, dto.getCurriculumGrade()) : null);
                    saveOccupationCertificates(ps, dto);
                });
    }
    
    private void saveOccupationCertificates(ProtocolStudent student, FinalExamHigherProtocolStudentSaveForm form) {
        Map<String, Long> currentCertificates = currentCertificateCodes(student);
        for (String code : form.getOccupations()) {
            Long certificateId = currentCertificates.remove(code);
            if (certificateId == null) {
                Classifier occupation = em.getReference(Classifier.class, code);
                
                ProtocolStudentOccupation pso = new ProtocolStudentOccupation();
                pso.setProtocolStudent(student);
                pso.setOccupation(occupation);
                pso.setPartOccupation(null);
                student.getProtocolStudentOccupations().add(pso);
            }
        }
        
        student.getProtocolStudentOccupations().removeIf(it -> currentCertificates.containsValue(it.getId()));
    }

    public List<AutocompleteResult> subjectStudyPeriodsForSelection(Long schoolId) {
        Long studyPeriodId = studyYearService.getCurrentStudyPeriod(schoolId);
        if(studyPeriodId == null) {
            return Collections.emptyList();
        }

        List<SubjectStudyPeriod> ssps = em.createQuery("select ssp from SubjectStudyPeriod ssp where ssp.studyPeriod.id = ?1", SubjectStudyPeriod.class)
                .setParameter(1, studyPeriodId).getResultList();
        return StreamUtil.toMappedList(ssp -> {
            Subject s = ssp.getSubject();
            String nameEt = SubjectUtil.subjectName(s.getCode(), s.getNameEt(), s.getCredits());
            String nameEn = SubjectUtil.subjectName(s.getCode(), s.getNameEn(), s.getCredits());
            return new AutocompleteResult(ssp.getId(), nameEt, nameEn);
        }, ssps);
    }
    
    public List<AutocompleteResult> curriculumsForSelection(Long schoolId) {
        String from = "from curriculum c"
                + " join curriculum_version cv on c.id=cv.curriculum_id"
                + " join curriculum_version_hmodule cvh on cv.id=cvh.curriculum_version_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", schoolId);
        //TODO: uncomment
        //qb.requiredCriteria("c.status_code = :curriculumStatus", "curriculumStatus", CurriculumStatus.OPPEKAVA_STAATUS_K.name());
        //qb.requiredCriteria("cv.status_code = :versionStatus", "versionStatus", CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_K.name());
        qb.requiredCriteria("cvh.type_code = :type", "type", HigherModuleType.KORGMOODUL_F.name());
        
        List<?> data = qb.select("c.id, c.name_et, c.name_en", em).getResultList();
        Map<Long, AutocompleteResult> results = new HashMap<>();
        for (Object r : data) {
            results.put(resultAsLong(r, 0), new AutocompleteResult(resultAsLong(r, 0), resultAsString(r, 1), resultAsString(r, 2)));
        }
        return new ArrayList<>(results.values());
    }

    public List<AutocompleteResult> subjectsForSelection(HoisUserDetails user, Long studyPeriodId, Long curriclumId) {
        String from = "from subject s"
                + " join subject_study_period ssp on ssp.subject_id = s.id"
                + " join curriculum_version_hmodule_subject cvhs on cvhs.subject_id = s.id"
                + " join curriculum_version_hmodule cvh on cvhs.curriculum_version_hmodule_id = cvh.id"
                + " join curriculum_version cv on cvh.curriculum_version_id = cv.id"
                + " left join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id";

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("s.status_code = :statusCode", "statusCode", SubjectStatus.AINESTAATUS_K.name());
        qb.requiredCriteria("s.assessment_code = :assessmentCode", "assessmentCode", SubjectAssessment.HINDAMISVIIS_E.name());
        qb.requiredCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", studyPeriodId);
        qb.requiredCriteria("cv.curriculum_id = :curriclumId", "curriclumId", curriclumId);
        qb.optionalCriteria("sspt.teacher_id = .teacherId", "teacherId", user.getTeacherId());

        String select = "distinct ssp.id as studyPeriod_id, s.code, s.name_et, s.name_en, s.credits";
        List<?> data = qb.select(select, em).getResultList();
        
        Map<Long, List<String>> allTeachers = new HashMap<>();
        Set<Long> sspIds = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);
        if(!sspIds.isEmpty()) {
            List<?> teachersData = em.createNativeQuery("select sspt.subject_study_period_id, p.firstname, p.lastname from subject_study_period_teacher sspt "
                    + "join teacher t on sspt.teacher_id = t.id "
                    + "join person p on t.person_id = p.id where sspt.subject_study_period_id in (?1) order by p.lastname, p.firstname")
                .setParameter(1, sspIds)
                .getResultList();
            teachersData.stream().collect(Collectors.groupingBy(r -> resultAsLong(r, 0), () -> allTeachers,
                    Collectors.mapping(r -> PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)), Collectors.toList())));
        }
        
        List<AutocompleteResult> results = new ArrayList<>();
        for (Object r : data) {
            List<String> teachers = allTeachers.get(resultAsLong(r, 0));
            String teacherNames = teachers != null ? (" - " + String.join(", ", teachers)) : "";
            results.add(new AutocompleteResult(resultAsLong(r, 0),
                    SubjectUtil.subjectName(resultAsString(r, 1), resultAsString(r, 2), resultAsDecimal(r, 4)) + teacherNames,
                    SubjectUtil.subjectName(resultAsString(r, 1), resultAsString(r, 3), resultAsDecimal(r, 4)) + teacherNames));
        }
        
        return results;
    }

    public FinalExamHigherProtocolSubjectDto subject(HoisUserDetails user, Long subjectStudyPeriodId) {
        FinalExamHigherProtocolSubjectDto dto = new FinalExamHigherProtocolSubjectDto();
        dto.setSubjectStudents(subjectStudents(user, subjectStudyPeriodId));

        return dto;
    }

    public Collection<FinalExamHigherProtocolStudentDto> subjectStudents(HoisUserDetails user, Long subjectStudyPeriodId) {
        Map<Long, FinalExamHigherProtocolStudentDto> result = studentsForSelection(user.getSchoolId(), subjectStudyPeriodId);
        return result.values();
    }

    public Map<Long, FinalExamHigherProtocolStudentDto> studentsForSelection(Long schoolId, Long subjectStudyPeriodId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(STUDENT_FROM);

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        //qb.requiredCriteria("d.status_code = :status", "status", DeclarationStatus.OPINGUKAVA_STAATUS_K);
        qb.requiredCriteria("ds.subject_study_period_id = :subjectStudyPeriodId", "subjectStudyPeriodId", subjectStudyPeriodId);
        
        qb.filter("not exists (select p.id from protocol p "
          + "join protocol_hdata ph on p.id = ph.protocol_id "
          + "left join protocol_student ps on p.id = ps.protocol_id "
          + "where ph.subject_study_period_id = ds.subject_study_period_id "
          + "and ps.student_id = s.id)");

        List<?> students = qb.select(
                "distinct s.id, p.firstname, p.lastname, p.idcode as idCode, s.status_code as studentStatusCode", em)
                .getResultList();
        
        return students.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> {
            FinalExamHigherProtocolStudentDto dto = new FinalExamHigherProtocolStudentDto();
            dto.setStudentId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setIdcode(resultAsString(r, 3));
            dto.setStatus(resultAsString(r, 4));
            return dto;
        }));
    }
}