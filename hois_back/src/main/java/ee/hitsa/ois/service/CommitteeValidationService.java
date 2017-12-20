package ee.hitsa.ois.service;

import java.util.List;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.validation.CommitteeMemberValidator;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.dto.CommitteeDto;
import ee.hitsa.ois.web.dto.CommitteeMemberDto;

@Service
public class CommitteeValidationService {

    @Autowired
    private Validator validator;

    public void validate(CommitteeDto dto) {
        assertHasOneChairman(dto);
        validateMembers(dto.getMembers());
    }

    private static void assertHasOneChairman(CommitteeDto dto) {
        if(dto.getMembers().stream().filter(m -> Boolean.TRUE.equals(m.getIsChairman())).count() != 1) {
            throw new ValidationFailedException("committee.error.chairman");
        }
    }

    private void validateMembers(List<CommitteeMemberDto> members) {
        for(CommitteeMemberDto member : members) {
            Class<?> group = Boolean.TRUE.equals(member.getIsExternal()) ? CommitteeMemberValidator.External.class : CommitteeMemberValidator.Internal.class;
            // FIXME should validate all members before throwing exception?
            ValidationFailedException.throwOnError(validator.validate(member, group));
        }
    }
}