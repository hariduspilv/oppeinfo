package ee.hitsa.ois.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.service.PracticeJournalService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.PracticeJournalSearchCommand;
import ee.hitsa.ois.web.dto.PracticeJournalSearchDto;

@RestController
@RequestMapping("/practiceJournals")
public class PracticeJournalController {

    @Autowired
    private PracticeJournalService practiceJournalService;

    @GetMapping
    public Page<PracticeJournalSearchDto> search(HoisUserDetails user, PracticeJournalSearchCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return practiceJournalService.search(user, command, pageable);
    }

}
