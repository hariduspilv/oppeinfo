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
import ee.hitsa.ois.domain.CommitteeMember;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.CommitteeSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.CommitteeDto;
import ee.hitsa.ois.web.dto.CommitteeMemberDto;
import ee.hitsa.ois.web.dto.CommitteeSearchDto;

@Transactional
@Service
public class CommitteeService {

    @Autowired
    private EntityManager em;
    @Autowired
    private CommitteeValidationService committeeValidationService;

    private static final String COMMITTEE_SELECT = "distinct c.id, "
            + "array_to_string(array_agg(case when cm.is_external "
            + "then cm.member_name "
            + "else p.firstname || ' ' || p.lastname end), ', ') as members,  "
            + "(select case when cm2.is_external "
            + "then cm2.member_name else p2.firstname || ' ' || p2.lastname end "
            + "from committee_member cm2 "
            + "left join teacher t2 on t2.id = cm2.teacher_id "
            + "left join person p2 on p2.id = t2.person_id "
            + "where cm2.committee_id = c.id and cm2.is_chairman) as chairman, "
            + "c.valid_from,  c.valid_thru";
    private static final String COMMITTEE_FROM = " from committee c "
            + "left join committee_member cm on c.id = cm.committee_id "
            + "left join teacher t on t.id = cm.teacher_id left join person p on p.id = t.person_id ";
    
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
        qb.optionalCriteria("c.valid_from >= :from", "from", criteria.getValidFrom());
        qb.optionalCriteria("c.valid_thru <= :thru", "thru", criteria.getValidThru());
        if(Boolean.FALSE.equals(criteria.getShowInvalid())) {
            qb.filter(" (c.valid_from <= current_date and c.valid_thru >= current_date) ");
        }
        qb.optionalCriteria("exists(select cm3.id from committee_member cm3 where cm3.committee_id = c.id and cm3.member_name = :memberName)", 
                "memberName", criteria.getMemberName());
        qb.optionalCriteria("exists(select cm3.id from committee_member cm3 where cm3.committee_id = c.id and cm3.teacher_id = :teacherId)", 
                "teacherId", criteria.getTeacher());
        
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
            return dto;
        });
    }

    public CommitteeDto get(Committee committee) {
        return CommitteeDto.of(committee);
    }

    public CommitteeDto create(Long schoolId, CommitteeDto dto) {
        Committee committee = new Committee();
        committee.setSchool(em.getReference(School.class, schoolId));
        return save(committee, dto);
    }

    public CommitteeDto save(Committee committee, CommitteeDto dto) {
        committeeValidationService.validate(dto);

        EntityUtil.bindToEntity(dto, committee, "members");
        updateMembers(committee, dto.getMembers());
        return get(EntityUtil.save(committee, em));
    }

    public void updateMembers(Committee committee, List<CommitteeMemberDto> memberDtos) {
        EntityUtil.bindEntityCollection(committee.getMembers(), CommitteeMember::getId,
                memberDtos, CommitteeMemberDto::getId, dto -> {
                    return createMember(dto, committee);
                }, this::updateMember);
    }

    public CommitteeMember createMember(CommitteeMemberDto dto, Committee committee) {
        CommitteeMember member = new CommitteeMember();
        member.setCommittee(committee);
        return updateMember(dto, member);
    }

    public CommitteeMember updateMember(CommitteeMemberDto dto, CommitteeMember member) {
        EntityUtil.bindToEntity(dto, member, "teacher");
        if(Boolean.FALSE.equals(dto.getIsExternal())) {
            member.setTeacher(em.getReference(Teacher.class, dto.getTeacher()));
            member.setMemberName(null);
        } else if(Boolean.TRUE.equals(dto.getIsExternal())){
            member.setTeacher(null);
        }
        return member;
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
