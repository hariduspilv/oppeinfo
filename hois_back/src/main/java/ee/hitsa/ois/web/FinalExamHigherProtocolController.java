package ee.hitsa.ois.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.service.FinalExamHigherProtocolService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.FinalExamProtocolValidationUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.commandobject.HigherProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.finalexamprotocol.FinalExamHigherProtocolCreateForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.HigherProtocolSearchDto;
import ee.hitsa.ois.web.dto.finalexamprotocol.FinalExamHigherProtocolDto;
import ee.hitsa.ois.web.dto.finalexamprotocol.FinalExamHigherProtocolSubjectDto;
import ee.hitsa.ois.web.dto.finalexamprotocol.FinalExamHigherProtocolSubjectResult;
import ee.hitsa.ois.web.dto.finalexamprotocol.FinalExamVocationalProtocolDto;

@RestController
@RequestMapping("/finalExamHigherProtocols")
public class FinalExamHigherProtocolController {

    @Autowired
    private FinalExamHigherProtocolService finalExamProtocolService;
    
    @GetMapping
    public Page<HigherProtocolSearchDto> search(HoisUserDetails user, @Valid HigherProtocolSearchCommand command,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalExamProtocolService.search(user, command, pageable);
    }
    
    @GetMapping("/{id:\\d+}")
    public FinalExamHigherProtocolDto get(HoisUserDetails user, @WithEntity Protocol protocol) {
        UserUtil.assertIsSchoolAdminOrTeacher(user, protocol.getSchool());
        return finalExamProtocolService.finalExamVocationalProtocol(user, protocol);
    }
    
    @PostMapping
    public FinalExamVocationalProtocolDto create(HoisUserDetails user,
            @Valid @RequestBody FinalExamHigherProtocolCreateForm finalExamProtocolCreateForm) {
        FinalExamProtocolValidationUtil.assertCanCreateHigherProtocol(user);
        return FinalExamVocationalProtocolDto.of(finalExamProtocolService.create(user, finalExamProtocolCreateForm));
    }
    
    @GetMapping("/subjectStudyPeriods")
    public List<AutocompleteResult> getSubjectStudyPeriods(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalExamProtocolService.subjectStudyPeriodsForSelection(user.getSchoolId());
    }
    
    @GetMapping("/subjects/{studyPeriodId:\\d+}")
    public List<FinalExamHigherProtocolSubjectResult> subjects(HoisUserDetails user, @PathVariable Long studyPeriodId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalExamProtocolService.subjectsForSelection(user, studyPeriodId);
    }
    
    @GetMapping("/subject/{subjectId:\\d+}")
    public FinalExamHigherProtocolSubjectDto subject(HoisUserDetails user, @PathVariable Long subjectId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return finalExamProtocolService.subject(user, subjectId);
    }
}
