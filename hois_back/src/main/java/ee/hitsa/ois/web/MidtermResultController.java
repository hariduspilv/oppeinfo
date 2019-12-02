package ee.hitsa.ois.web;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.service.MidtermResultService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.web.dto.midtermresult.MidtermResultSearchDto;
import ee.hitsa.ois.web.dto.midtermresult.MidtermResultStudentDto;

@RestController
@RequestMapping("/midtermResult")
public class MidtermResultController {
    
    @Autowired
    private MidtermResultService midtermResultService;
    @Autowired
    private EntityManager em;
    
    @GetMapping
    public Page<MidtermResultSearchDto> getDeclaredSubjects(HoisUserDetails user, @RequestParam(name="studyPeriod") Long periodId, Pageable pageable) {
        UserUtil.assertIsStudent(user);
        StudyPeriod period = em.getReference(StudyPeriod.class, periodId);
        UserUtil.assertSameSchool(user, period.getStudyYear().getSchool());
        return midtermResultService.getDeclaredSubjectsByPeriod(user.getStudentId(), period, pageable);
    }
    
    @GetMapping("/{id:\\d+}")
    public MidtermResultStudentDto getMidtermResult(HoisUserDetails user, @WithEntity DeclarationSubject subject) {
        UserUtil.assertIsStudent(user, subject.getDeclaration().getStudent());
        return midtermResultService.getDeclarationSubjectMidtermResults(subject);
    }
}
