package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Committee;
import ee.hitsa.ois.domain.CommitteeMember;
import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
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
import ee.hitsa.ois.enums.CurriculumStatus;
import ee.hitsa.ois.enums.CurriculumVersionStatus;
import ee.hitsa.ois.enums.DeclarationStatus;
import ee.hitsa.ois.enums.HigherAssessment;
import ee.hitsa.ois.enums.HigherModuleType;
import ee.hitsa.ois.enums.ProtocolStatus;
import ee.hitsa.ois.enums.ProtocolType;
import ee.hitsa.ois.enums.StudentStatus;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.FinalProtocolUtil;
import ee.hitsa.ois.util.HigherProtocolUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.SubjectUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalHigherProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalHigherProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalHigherProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalHigherProtocolSearchDto;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalHigherProtocolStudentSaveForm;
import ee.hitsa.ois.web.commandobject.finalprotocol.FinalProtocolCommitteeMemberForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.HigherProtocolSearchDto;
import ee.hitsa.ois.web.dto.finalprotocol.FinalHigherProtocolDto;
import ee.hitsa.ois.web.dto.finalprotocol.FinalHigherProtocolStudentDto;
import ee.hitsa.ois.web.dto.finalprotocol.FinalHigherProtocolSubjectDto;

@Transactional
@Service
public class FinalHigherProtocolService extends AbstractProtocolService {
    
    private static final String LIST_FROM = "from protocol p "
            + "join protocol_hdata phd on phd.protocol_id = p.id "
            + "left join subject_study_period ssp on phd.subject_study_period_id = ssp.id "
            + "left join subject s on ssp.subject_id = s.id or phd.final_subject_id = s.id "
            + "left join committee com on p.committee_id = com.id "
            + "left join committee_member cme on com.id = cme.committee_id and cme.is_chairman = true "
            + "left join teacher t on cme.teacher_id = t.id " + "left join person per on t.person_id = per.id";
    
    private static final String LIST_SELECT = "p.id as p_id, p.protocol_nr, p.is_final_thesis, p.status_code, s.id as s_id, "
            + "s.code, s.name_et, s.name_en, s.credits, t.id as t_id, per.firstname, per.lastname, cme.member_name, p.inserted, "
            + "p.confirm_date, p.confirmer";
    
    private static final List<String> POSITIVE_HIGHER_GRADES = EnumUtil.toNameList(
            HigherAssessment.KORGHINDAMINE_A, HigherAssessment.KORGHINDAMINE_1, HigherAssessment.KORGHINDAMINE_2,
            HigherAssessment.KORGHINDAMINE_3, HigherAssessment.KORGHINDAMINE_4, HigherAssessment.KORGHINDAMINE_5);
    
    public Page<HigherProtocolSearchDto> search(HoisUserDetails user, FinalHigherProtocolSearchCommand criteria,
            Pageable pageable) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(LIST_FROM).sort(pageable);
            
        qb.filter("p.is_final = true");
        qb.filter("p.is_vocational = false");
        qb.requiredCriteria("p.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.optionalCriteria("p.is_final_thesis = :isFinalThesis", "isFinalThesis", criteria.getIsFinalThesis());
        qb.optionalCriteria("p.status_code = :statusCode", "statusCode", criteria.getStatus());
        qb.optionalCriteria("p.protocol_nr = :protocolNr", "protocolNr", criteria.getProtocolNr());
        qb.optionalCriteria("p.inserted >= :insertedFrom", "insertedFrom", criteria.getInsertedFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("p.inserted <= :insertedThru", "insertedThru", criteria.getInsertedThru(), DateUtils::lastMomentOfDay);
        qb.optionalCriteria("p.confirm_date >= :confirmedFrom", "confirmedFrom", criteria.getConfirmDateFrom(), DateUtils::firstMomentOfDay);
        qb.optionalCriteria("p.confirm_date <= :confirmedThru", "confirmedThru", criteria.getConfirmDateThru(), DateUtils::lastMomentOfDay);
        
        if (criteria.getStudentName() != null) {
            qb.filter("p.id in (select p.id from protocol p "
                    + "join protocol_student ps on p.id = ps.protocol_id "
                    + "join student s on ps.student_id = s.id "
                    + "join person per on s.person_id = per.id "
                    + "where upper(per.firstname || ' ' || per.lastname) like '%" + criteria.getStudentName().toUpperCase() + "%')");
        }

        qb.optionalCriteria(
                "exists (select protocol_id " + "from protocol_hdata phd "
                        + "join subject_study_period ssp on phd.subject_study_period_id = ssp.id "
                        + "join subject s on ssp.subject_id = s.id "
                        + "where upper(s.code) like lower(:subject) or upper(s.name_et) like :subject or upper(s.name_en) like :subject)",
                "subject", criteria.getSubject(), JpaQueryUtil::toContains);
        
        if (user.isTeacher()) {
            qb.optionalCriteria("p.is_final_thesis = :isFinalThesis", "isFinalThesis", Boolean.FALSE);
            qb.optionalCriteria("exists (select protocol_id from protocol_hdata phd "
                    + "join subject_study_period ssp on phd.subject_study_period_id = ssp.id "
                    + "join subject_study_period_teacher sspt on ssp.id = sspt.subject_study_period_id "
                    + "where sspt.teacher_id = :teacher)", "teacher", user.getTeacherId());
        }

        Page<HigherProtocolSearchDto> result = JpaQueryUtil.pagingResult(qb, LIST_SELECT, em, pageable).map(r -> {
            FinalHigherProtocolSearchDto dto = new FinalHigherProtocolSearchDto();
            dto.setId(resultAsLong(r, 0));
            dto.setProtocolNr(resultAsString(r, 1));
            dto.setIsFinalThesis(resultAsBoolean(r, 2));
            dto.setStatus(resultAsString(r, 3));
            dto.setSubject(new AutocompleteResult(resultAsLong(r, 4),
                    SubjectUtil.subjectName(resultAsString(r, 5), resultAsString(r, 6), resultAsDecimal(r, 8)),
                    SubjectUtil.subjectName(resultAsString(r, 5), resultAsString(r, 7), resultAsDecimal(r, 8))));
            
            
            if (resultAsLong(r, 9) != null) {
                dto.setCommitteeChairman(PersonUtil.fullname(resultAsString(r, 10), resultAsString(r, 11)));
            } else {
                dto.setCommitteeChairman(resultAsString(r, 12));
            }
            
            dto.setInserted(resultAsLocalDate(r, 13));
            dto.setConfirmDate(resultAsLocalDate(r, 14));
            dto.setConfirmer(PersonUtil.stripIdcodeFromFullnameAndIdcode(resultAsString(r, 15)));
            dto.setCanChange(Boolean.valueOf(FinalProtocolUtil.canEdit(user, em.getReference(Protocol.class, dto.getId()))));
            return dto;
        });

        return result;
    }

    public FinalHigherProtocolDto finalHigherProtocol(HoisUserDetails user, Protocol protocol) {
        includeCorrectImportedOccupationCertificates(protocol);
        
        FinalHigherProtocolDto dto = FinalHigherProtocolDto.of(protocol);
        dto.setCanBeEdited(Boolean.valueOf(FinalProtocolUtil.canEdit(user, protocol)));
        dto.setCanBeConfirmed(Boolean.valueOf(FinalProtocolUtil.canConfirm(user, protocol)));
        dto.setCanBeDeleted(Boolean.valueOf(FinalProtocolUtil.canDelete(user, protocol)));
        return dto;
    }

    public Protocol create(HoisUserDetails user, FinalHigherProtocolCreateForm form) {
        Protocol protocol = new Protocol();
        protocol.setIsFinal(Boolean.TRUE);
        protocol.setIsVocational(Boolean.FALSE);
        protocol.setIsFinalThesis(form.getIsFinalThesis());
        //TODO: protocolNr
        protocol.setProtocolNr(generateProtocolNumber());
        protocol.setSchool(em.getReference(School.class, user.getSchoolId()));
        protocol.setStatus(em.getReference(Classifier.class, ProtocolStatus.PROTOKOLL_STAATUS_S.name()));
        
        ProtocolHdata protocolHData = new ProtocolHdata();
        protocolHData.setType(em.getReference(Classifier.class, ProtocolType.PROTOKOLLI_LIIK_P.name()));
        CurriculumVersion cv = em.getReference(CurriculumVersion.class, form.getCurriculumVersion());
        protocolHData.setCurriculum(cv.getCurriculum());
        protocolHData.setProtocol(protocol);
        
        if (protocol.getIsFinalThesis().booleanValue()) {
            protocolHData.setFinalSubject(em.getReference(Subject.class, form.getSubject()));
        } else {
            protocolHData.setSubjectStudyPeriod(em.getReference(SubjectStudyPeriod.class, form.getSubjectStudyPeriod()));
        }
        
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
    
    public Protocol save(Protocol protocol, FinalHigherProtocolSaveForm form) {
        EntityUtil.bindToEntity(form, protocol, "committee", "protocolStudents", "protocolCommitteeMembers");
        protocol.setCommittee(form.getCommitteeId() != null ? em.getReference(Committee.class, form.getCommitteeId()) : null);
        saveCommitteeMembers(protocol, form);
        saveStudents(protocol, form);
        
        return EntityUtil.save(protocol, em);
    }
    
    private void saveCommitteeMembers(Protocol protocol, FinalHigherProtocolSaveForm form) {
        EntityUtil.bindEntityCollection(protocol.getProtocolCommitteeMembers(), ProtocolCommitteeMember::getId,
                form.getProtocolCommitteeMembers(), FinalProtocolCommitteeMemberForm::getId, dto -> {
                    ProtocolCommitteeMember pcm = EntityUtil.bindToEntity(dto, new ProtocolCommitteeMember(), "committeeMember");
                    pcm.setCommitteeMember(em.getReference(CommitteeMember.class, dto.getCommitteeMemberId()));
                    return pcm;
                });
    }

    private void saveStudents(Protocol protocol, FinalHigherProtocolSaveForm form) {
        EntityUtil.bindEntityCollection(protocol.getProtocolStudents(), ProtocolStudent::getId,
                form.getProtocolStudents(), FinalHigherProtocolStudentSaveForm::getId, dto -> {
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
                    ps.setCurriculumGrade(dto.getCurriculumGradeId() != null
                            ? em.getReference(CurriculumGrade.class, dto.getCurriculumGradeId()) : null);
                    saveOccupationCertificates(ps, dto);
                });
    }
    
    private void saveOccupationCertificates(ProtocolStudent student, FinalHigherProtocolStudentSaveForm form) {
        Map<String, Long> currentCertificates = currentCertificateCodes(student);
        if (form.getOccupationCode() != null) {
            Long certificateId = currentCertificates.remove(form.getOccupationCode());
            if (certificateId == null) {
                Classifier occupation = em.getReference(Classifier.class, form.getOccupationCode());
                
                ProtocolStudentOccupation pso = new ProtocolStudentOccupation();
                pso.setProtocolStudent(student);
                pso.setOccupation(occupation);
                pso.setPartOccupation(null);
                student.getProtocolStudentOccupations().add(pso);
            }
        }
        
        student.getProtocolStudentOccupations().removeIf(it -> currentCertificates.containsValue(it.getId()));
    }
    
    public List<AutocompleteResult> curriculumsForSelection(Long schoolId, Boolean isFinalThesis) {
        String from = "from curriculum c"
                + " join curriculum_version cv on c.id=cv.curriculum_id"
                + " join curriculum_version_hmodule cvh on cv.id=cvh.curriculum_version_id";
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("c.status_code != :curriculumStatus", "curriculumStatus", CurriculumStatus.OPPEKAVA_STAATUS_C.name());
        qb.requiredCriteria("cv.status_code != :versionStatus", "versionStatus", CurriculumVersionStatus.OPPEKAVA_VERSIOON_STAATUS_C.name());
        qb.requiredCriteria("cvh.type_code = :type", "type",
                Boolean.TRUE.equals(isFinalThesis) ? HigherModuleType.KORGMOODUL_L : HigherModuleType.KORGMOODUL_F);
        
        List<?> data = qb.select("cv.id, cv.code, c.name_et, c.name_en", em).getResultList();
        Map<Long, AutocompleteResult> results = new HashMap<>();
        for (Object r : data) {
            results.put(resultAsLong(r, 0),
                    new AutocompleteResult(resultAsLong(r, 0),
                            CurriculumUtil.versionName(resultAsString(r, 1), resultAsString(r, 2)),
                            CurriculumUtil.versionName(resultAsString(r, 1), resultAsString(r, 3))));
        }
        return new ArrayList<>(results.values());
    }

    public List<AutocompleteResult> subjectsForSelection(HoisUserDetails user, Long studyPeriodId, Long curriculumVersionId, Boolean isFinalThesis) {
        String from = "from subject s"
                + " join subject_study_period ssp on ssp.subject_id = s.id"
                + " join curriculum_version_hmodule_subject cvhs on cvhs.subject_id = s.id"
                + " join curriculum_version_hmodule cvh on cvhs.curriculum_version_hmodule_id = cvh.id"
                + " join curriculum_version cv on cvh.curriculum_version_id = cv.id"
                + " left join subject_study_period_teacher sspt on sspt.subject_study_period_id = ssp.id";

        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);
        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", user.getSchoolId());
        qb.requiredCriteria("s.status_code = :statusCode", "statusCode", SubjectStatus.AINESTAATUS_K.name());
        qb.optionalCriteria("ssp.study_period_id = :studyPeriodId", "studyPeriodId", studyPeriodId);
        qb.requiredCriteria("cv.id = :id", "id", curriculumVersionId);
        qb.requiredCriteria("cvh.type_code = :type", "type",
                Boolean.TRUE.equals(isFinalThesis) ? HigherModuleType.KORGMOODUL_L : HigherModuleType.KORGMOODUL_F);
        qb.optionalCriteria("sspt.teacher_id = :teacherId", "teacherId", user.getTeacherId());

        String select = isFinalThesis.booleanValue() ? "distinct s.id as subject_id, s.code, s.name_et, s.name_en, s.credits"
                : "distinct ssp.id as subject_study_period_id, s.code, s.name_et, s.name_en, s.credits";
        List<?> data = qb.select(select, em).getResultList();
        
        List<AutocompleteResult> results = new ArrayList<>();

        if (isFinalThesis.booleanValue()) {
            for (Object r : data) {
                results.add(new AutocompleteResult(resultAsLong(r, 0),
                        SubjectUtil.subjectName(resultAsString(r, 1), resultAsString(r, 2), resultAsDecimal(r, 4)),
                        SubjectUtil.subjectName(resultAsString(r, 1), resultAsString(r, 3), resultAsDecimal(r, 4))));
            }
        } else {
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
            for (Object r : data) {
                List<String> teachers = allTeachers.get(resultAsLong(r, 0));
                String teacherNames = teachers != null ? (" - " + String.join(", ", teachers)) : "";
                results.add(new AutocompleteResult(resultAsLong(r, 0),
                        SubjectUtil.subjectName(resultAsString(r, 1), resultAsString(r, 2), resultAsDecimal(r, 4)) + teacherNames,
                        SubjectUtil.subjectName(resultAsString(r, 1), resultAsString(r, 3), resultAsDecimal(r, 4)) + teacherNames));
            }
        }
        
        return results;
    }

    public FinalHigherProtocolSubjectDto subject(HoisUserDetails user, Long curriculumVersionId, Long subjectId, Boolean isFinalThesis) {
        FinalHigherProtocolSubjectDto dto = new FinalHigherProtocolSubjectDto();
        dto.setSubjectStudents(subjectStudents(user, curriculumVersionId, subjectId, isFinalThesis));

        return dto;
    }

    public Collection<FinalHigherProtocolStudentDto> subjectStudents(HoisUserDetails user, Long curriculumVersionId, Long subjectId, Boolean isFinalThesis) {
        Map<Long, FinalHigherProtocolStudentDto> result = isFinalThesis.booleanValue()
                ? thesisStudentsForSelection(user.getSchoolId(), curriculumVersionId, subjectId)
                : examStudentsForSelection(user.getSchoolId(), curriculumVersionId, subjectId); 
        return result.values();
    }

    public Map<Long, FinalHigherProtocolStudentDto> thesisStudentsForSelection(Long schoolId, Long curriculumVersionId, Long subjectId) {
        JpaNativeQueryBuilder qb = studentsForSelectionQuery(schoolId, curriculumVersionId, Boolean.TRUE);
        qb.requiredCriteria("subj.id = :subjectId", "subjectId", subjectId);

        List<?> students = qb.select("distinct s.id, p.firstname, p.lastname, p.idcode as id_code, s.status_code as student_status_code", em)
                .getResultList();
        
        return students.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> {
            FinalHigherProtocolStudentDto dto = new FinalHigherProtocolStudentDto();
            dto.setStudentId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setIdcode(resultAsString(r, 3));
            dto.setStatus(resultAsString(r, 4));
            return dto;
        }));
    }
    
    public Map<Long, FinalHigherProtocolStudentDto> examStudentsForSelection(Long schoolId, Long curriculumVersionId, Long subjectStudyPeriodId) {
        JpaNativeQueryBuilder qb = studentsForSelectionQuery(schoolId, curriculumVersionId, Boolean.FALSE);
        qb.requiredCriteria("ds.subject_study_period_id = :subjectStudyPeriodId", "subjectStudyPeriodId", subjectStudyPeriodId);

        List<?> students = qb.select(
                "distinct s.id, p.firstname, p.lastname, p.idcode as idCode, s.status_code as studentStatusCode", em)
                .getResultList();
        
        return students.stream().collect(Collectors.toMap(r -> resultAsLong(r, 0), r -> {
            FinalHigherProtocolStudentDto dto = new FinalHigherProtocolStudentDto();
            dto.setStudentId(resultAsLong(r, 0));
            dto.setFullname(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setIdcode(resultAsString(r, 3));
            dto.setStatus(resultAsString(r, 4));
            return dto;
        }));
    }
    
    private static JpaNativeQueryBuilder studentsForSelectionQuery(Long schoolId, Long curriculumVersionId, Boolean isFinalThesis) {
        String from = "from student s "
                + "join person p on p.id = s.person_id "
                + "join student_group sg on sg.id = s.student_group_id "
                + "join curriculum_version cv on cv.id = s.curriculum_version_id "
                + "join curriculum c on c.id = cv.curriculum_id ";
        
        if (isFinalThesis.booleanValue()) {
            from += "join curriculum_version_hmodule hc on hc.curriculum_version_id=cv.id and hc.type_code='KORGMOODUL_L' "
                    + "join curriculum_version_hmodule_subject hcs on hc.id=hcs.curriculum_version_hmodule_id "
                    + "join subject subj on hcs.subject_id=subj.id";
        } else {
            from += "join declaration d on d.student_id = s.id "
                    + "join declaration_subject ds on d.id = ds.declaration_id "
                    + "join subject_study_period ssp on ds.subject_study_period_id = ssp.id ";
        }
        
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(from);

        qb.requiredCriteria("s.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("s.status_code in :activeStatuses", "activeStatuses", StudentStatus.STUDENT_STATUS_ACTIVE);
        qb.requiredCriteria("cv.id = :curriculumVersionId", "curriculumVersionId", curriculumVersionId);
        
        if (Boolean.TRUE.equals(isFinalThesis)) {
            qb.filter("s.id in (select ft.student_id from final_thesis ft "
                    + "join student s on s.id = ft.student_id where ft.status_code = 'LOPUTOO_STAATUS_K')");
            qb.filter("not exists (select p.id from protocol p "
                    + "join protocol_hdata ph on p.id = ph.protocol_id "
                    + "left join protocol_student ps on p.id = ps.protocol_id "
                    + "where ph.final_subject_id = subj.id and ps.student_id = s.id "
                    + "and (ps.grade_code is null or ps.grade_code in (" 
                    + POSITIVE_HIGHER_GRADES.stream().map(g -> "'" + g + "'").collect(Collectors.joining(", ")) + ")))");
        } else {
            qb.requiredCriteria("d.status_code = :status", "status", DeclarationStatus.OPINGUKAVA_STAATUS_K);
            qb.filter("not exists (select p.id from protocol p "
                    + "join protocol_hdata ph on p.id = ph.protocol_id "
                    + "left join protocol_student ps on p.id = ps.protocol_id "
                    + "left join subject subj on ph.subject_study_period_id = subj.id "
                    + "where ph.subject_study_period_id = ds.subject_study_period_id and ps.student_id = s.id "
                    + "and (ps.grade_code is null or ps.grade_code in ("
                    + POSITIVE_HIGHER_GRADES.stream().map(g -> "'" + g + "'").collect(Collectors.joining(", ")) + ")))");
        }
        qb.sort("p.firstname, p.lastname");
        
        return qb;
    }
    
    public Protocol confirm(HoisUserDetails user, Protocol protocol, FinalHigherProtocolSaveForm protocolSaveForm) {
        setConfirmation(user, protocol);
        Protocol confirmedProtocol = null;
        if (protocolSaveForm != null) {
            confirmedProtocol = save(protocol, protocolSaveForm);
        } else {
            confirmedProtocol = EntityUtil.save(protocol, em);
        }

        if (confirmedProtocol.getProtocolStudents().stream().anyMatch(ps -> ps.getGrade() == null)) {
            throw new ValidationFailedException("finalProtocol.messages.gradeNotSelectedForAllStudents");
        }

        sendStudentResultMessages(confirmedProtocol);
        return confirmedProtocol;
    }
}