package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Committee;
import ee.hitsa.ois.domain.CommitteeCurriculum;
import ee.hitsa.ois.domain.CommitteeMember;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.enums.CommitteeType;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.CommitteeUserRights;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.CommitteeSearchCommand;
import ee.hitsa.ois.web.dto.ApelApplicationDecisionDto;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.CommitteeDto;
import ee.hitsa.ois.web.dto.CommitteeMemberDto;
import ee.hitsa.ois.web.dto.CommitteeScholarshipDecisionDto;
import ee.hitsa.ois.web.dto.CommitteeSearchDto;

@Transactional
@Service
public class CommitteeService {

    @Autowired
    private EntityManager em;
    @Autowired
    private CommitteeValidationService committeeValidationService;
    @Autowired
    private ClassifierRepository classifierRepository;

    private static final String COMMITTEE_SELECT = "distinct c.id, "
            + "array_to_string(array_agg(case when cm.is_external "
            + "then cm.member_name "
            + "else p.firstname || ' ' || p.lastname end), ', ') as members,  "
            + "(select case when cm2.is_external "
            + "then cm2.member_name else p2.firstname || ' ' || p2.lastname end "
            + "from committee_member cm2 "
            + "left join teacher t2 on t2.id = cm2.teacher_id "
            + "left join person p2 on p2.id = t2.person_id or p2.id = cm2.person_id "
            + "where cm2.committee_id = c.id and cm2.is_chairman) as chairman, "
            + "c.valid_from,  c.valid_thru, c.name_et";
    private static final String COMMITTEE_FROM = " from committee c "
            + "left join committee_member cm on c.id = cm.committee_id "
            + "left join teacher t on t.id = cm.teacher_id left join person p on p.id = t.person_id or p.id = cm.person_id ";
    
    private static final String MEMBER_SELECT = " distinct "
            + "(case when cm.member_name is not null "
            + "then cm.member_name "
            + "else p.firstname || ' ' || p.lastname "
            + "end), "
            + "cm.teacher_id ";
    private static final String MEMBER_FROM = " from committee_member cm "
            + "left join teacher t on t.id = cm.teacher_id "
            + "left join person p on p.id = t.person_id "
            + "left join committee c on c.id = cm.committee_id ";

    public Page<CommitteeSearchDto> search(Long schoolId, CommitteeSearchCommand criteria, Pageable pageable) {
        // TODO refactor using two queries - committee list and members
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(COMMITTEE_FROM).sort(pageable);

        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", schoolId);
        qb.requiredCriteria("c.type_code = :type", "type", criteria.getType());
        qb.optionalContains("c.name_et", "nameEt", criteria.getNameEt());
        qb.optionalCriteria("c.valid_from >= :from", "from", criteria.getValidFrom());
        qb.optionalCriteria("c.valid_thru <= :thru", "thru", criteria.getValidThru());
        if(Boolean.FALSE.equals(criteria.getShowInvalid())) {
            qb.filter(" (c.valid_from <= current_date and c.valid_thru >= current_date) ");
        }
        qb.optionalCriteria("exists(select cm3.id from committee_member cm3 " 
                + "left join teacher t3 on t3.id = cm3.teacher_id left join person p3 on p3.id = t3.person_id or p3.id = cm3.person_id "
                + "where cm3.committee_id = c.id and (upper(case when cm3.is_external then cm3.member_name else p3.firstname || ' ' || p3.lastname end) like :searchName))", "searchName",
                criteria.getMemberName() == null ? null : JpaQueryUtil.toContains(criteria.getMemberName()));
        
//        qb.optionalCriteria("exists(select cm3.id from committee_member cm3 where cm3.committee_id = c.id and cm3.member_name = :memberName)", 
//                "memberName", criteria.getMemberName());
//        qb.optionalCriteria("exists(select cm3.id from committee_member cm3 where cm3.committee_id = c.id and cm3.teacher_id = :teacherId)", 
//                "teacherId", criteria.getTeacher());
//        qb.optionalCriteria("exists(select cm3.id from committee_member cm3 where cm3.committee_id = c.id and cm3.person_id = :personId)", 
//                "personId", criteria.getPerson());
        
        qb.groupBy(" c.id ");
        return JpaQueryUtil.pagingResult(qb, COMMITTEE_SELECT, em, pageable).map(row -> {
            CommitteeSearchDto dto = new CommitteeSearchDto();

            dto.setId(resultAsLong(row, 0));
            String members = resultAsString(row, 1);
            dto.setChairman(resultAsString(row, 2));
            if(members != null) {
                dto.setMembers(new ArrayList<>());
                dto.getMembers().addAll(Arrays.asList(members.split(", ")));
                dto.getMembers().remove(dto.getChairman());
            }
            dto.setValidFrom(resultAsLocalDate(row, 3));
            dto.setValidThru(resultAsLocalDate(row, 4));
            dto.setNameEt(resultAsString(row, 5));
            return dto;
        });
    }

    public CommitteeDto get(HoisUserDetails user, Committee committee) {
        CommitteeDto dto = CommitteeDto.of(committee);
        dto.setCanEdit(Boolean.valueOf(CommitteeUserRights.canEdit(user, committee)));
        if (CommitteeType.KOMISJON_T.name().equals(dto.getType())) {
            dto.setScholarshipDecisions(getScholarshipDecisions(committee));
        }
        if (CommitteeType.KOMISJON_V.name().equals(dto.getType())) {
            dto.setApelApplicationDecisions(getApelApplicationDecisions(committee));
        }
        return dto;
    }

    private List<CommitteeScholarshipDecisionDto> getScholarshipDecisions(Committee committee) {
        List<?> result = em.createNativeQuery("select id, protocol_nr, decided, scholarship_type" 
                + " from (select sd.id, sd.protocol_nr, sd.decided, "
                + " (select st.type_code from scholarship_application sa"
                    + " join scholarship_term st on st.id = sa.scholarship_term_id"
                    + " where sa.scholarship_decision_id = sd.id limit 1) as scholarship_type"
                + " from scholarship_decision sd"
                + " where sd.committee_id = ?1) sub" 
                + " where scholarship_type is not null")
                .setParameter(1, EntityUtil.getId(committee))
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            CommitteeScholarshipDecisionDto dto = new CommitteeScholarshipDecisionDto();
            dto.setId(resultAsLong(r, 0));
            dto.setProtocolNr(resultAsString(r, 1));
            dto.setDecided(resultAsLocalDate(r, 2));
            dto.setScholarshipType(resultAsString(r, 3));
            return dto;
        }, result);
    }

    private List<ApelApplicationDecisionDto> getApelApplicationDecisions(Committee committee) {
        List<?> result = em.createNativeQuery("select aa.id, p.firstname, p.lastname, aa.decision from apel_application aa "
        		+ "join apel_application_committee aac on aac.apel_application_id = aa.id "
                + "join student s on aa.student_id = s.id "
                + "join person p on s.person_id = p.id "
                + "where aac.committee_id = ?1 and aa.decision is not null "
                + "order by p.lastname, p.firstname")
                .setParameter(1, EntityUtil.getId(committee))
                .getResultList();
        return StreamUtil.toMappedList(r -> {
            ApelApplicationDecisionDto dto = new ApelApplicationDecisionDto();
            dto.setApplicationId(resultAsLong(r, 0));
            dto.setStudentName(PersonUtil.fullname(resultAsString(r, 1), resultAsString(r, 2)));
            dto.setDecision(resultAsString(r, 3));
            return dto;
        }, result);
    }

    public Committee create(Long schoolId, CommitteeDto dto) {
        Committee committee = new Committee();
        committee.setSchool(em.getReference(School.class, schoolId));
        return save(committee, dto);
    }

    public Committee save(Committee committee, CommitteeDto dto) {
        committeeValidationService.validate(dto);

        EntityUtil.bindToEntity(dto, committee, classifierRepository, "members", "curriculums");
        updateMembers(committee, dto.getMembers());
        updateCurriculums(committee, dto.getCurriculums());
        return EntityUtil.save(committee, em);
    }

    public void updateMembers(Committee committee, List<CommitteeMemberDto> memberDtos) {
        allScholarshipDecisionCommitteeMembersExist(committee, memberDtos);
        EntityUtil.bindEntityCollection(committee.getMembers(), CommitteeMember::getId,
                memberDtos, CommitteeMemberDto::getId, dto -> {
                    return createMember(dto, committee);
                }, this::updateMember);
    }

    private void allScholarshipDecisionCommitteeMembersExist(Committee committee, List<CommitteeMemberDto> memberDtos) {
        if (!CommitteeType.KOMISJON_T.name().equals(EntityUtil.getCode(committee.getType()))) {
            return;
        }
        Long committeeId = EntityUtil.getNullableId(committee);
        
        if (committeeId != null) {
            List<?> data = em.createNativeQuery("select cm.id, cm.person_id from committee_member cm"
                    + " join scholarship_decision_committee_member sdcm on cm.id = sdcm.committee_member_id"
                    + " where cm.committee_id = ?1")
                    .setParameter(1, committeeId)
                    .getResultList();
            Set<Long> scholarshipCommitteeMembers = StreamUtil.toMappedSet(r -> resultAsLong(r, 0), data);
            Set<Long> scholarshipCommitteePersons = StreamUtil.toMappedSet(r -> resultAsLong(r, 1), data);
            Set<Long> formMembers = StreamUtil.toMappedSet(r -> r.getId(), memberDtos);
            Set<Long> formPersons = StreamUtil.toMappedSet(r -> r.getPerson().getId(), memberDtos);
            
            for (Long committeeMember : scholarshipCommitteeMembers) {
                if (!formMembers.contains(committeeMember)) {
                    throw new ValidationFailedException("committee.message.committeeMemberInScholarshipDecision");
                }
            }
            for (Long committeePerson : scholarshipCommitteePersons) {
                if (!formPersons.contains(committeePerson)) {
                    throw new ValidationFailedException("committee.message.committeeMemberInScholarshipDecision");
                }
            }
        }
    }

    public CommitteeMember createMember(CommitteeMemberDto dto, Committee committee) {
        CommitteeMember member = new CommitteeMember();
        member.setCommittee(committee);
        return updateMember(dto, member);
    }

    public CommitteeMember updateMember(CommitteeMemberDto dto, CommitteeMember member) {
        EntityUtil.bindToEntity(dto, member, "teacher");
        if(Boolean.FALSE.equals(dto.getIsExternal())) {
            Long teacherId = dto.getTeacher();
            AutocompleteResult person = dto.getPerson();
            member.setTeacher(teacherId != null ? em.getReference(Teacher.class, teacherId) : null);
            member.setPerson(person != null ? em.getReference(Person.class, person.getId()) : null);
            member.setMemberName(null);
        } else if(Boolean.TRUE.equals(dto.getIsExternal())){
            member.setTeacher(null);
            member.setPerson(null);
        }
        return member;
    }

    private void updateCurriculums(Committee committee, List<AutocompleteResult> curriculumDtos) {
        if (curriculumDtos == null) {
            return;
        }
        EntityUtil.bindEntityCollection(committee.getCurriculums(), cc -> EntityUtil.getId(cc.getCurriculum()),
                curriculumDtos, AutocompleteResult::getId, dto -> {
                    return createCurriculum(dto, committee);
                });
    }

    private CommitteeCurriculum createCurriculum(AutocompleteResult dto, Committee committee) {
        CommitteeCurriculum committeeCurriculum = new CommitteeCurriculum();
        committeeCurriculum.setCommittee(committee);
        committeeCurriculum.setCurriculum(em.getReference(Curriculum.class, dto.getId()));
        return committeeCurriculum;
    }

    public Set<AutocompleteResult> getMembers(Long schoolId) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder(MEMBER_FROM);
        qb.requiredCriteria("c.school_id = :schoolId", "schoolId", schoolId);
        List<?> members = qb.select(MEMBER_SELECT, em).getResultList();
        return StreamUtil.toMappedSet(r -> {
            String name = resultAsString(r, 0);
            return new AutocompleteResult(resultAsLong(r, 1), name, name);
        }, members);
    }

    public void delete(HoisUserDetails user, Committee committee) {
        EntityUtil.setUsername(user.getUsername(), em);
        EntityUtil.deleteEntity(committee, em);
    }
}
