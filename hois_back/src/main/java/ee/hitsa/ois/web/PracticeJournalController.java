package ee.hitsa.ois.web;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.Enterprise;
import ee.hitsa.ois.domain.PracticeJournal;
import ee.hitsa.ois.enums.JournalStatus;
import ee.hitsa.ois.service.ContractService;
import ee.hitsa.ois.service.PracticeJournalService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.PracticeJournalEntriesStudentForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalEntriesSupervisorForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalEntriesTeacherForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalForm;
import ee.hitsa.ois.web.commandobject.PracticeJournalSearchCommand;
import ee.hitsa.ois.web.dto.ContractStudentModuleDto;
import ee.hitsa.ois.web.dto.ContractStudentSubjectDto;
import ee.hitsa.ois.web.dto.PracticeJournalDto;
import ee.hitsa.ois.web.dto.PracticeJournalSearchDto;

@RestController
@RequestMapping("/practiceJournals")
public class PracticeJournalController {

    private static final int DAYS_TO_ACCESS_AFTER_CONTRACT_END_DATE = 30;

    @Autowired
    private PracticeJournalService practiceJournalService;
    @Autowired
    private ContractService contractService;

    @GetMapping
    public Page<PracticeJournalSearchDto> search(HoisUserDetails user, PracticeJournalSearchCommand command,
            Pageable pageable) {
        if (user.isStudent()) {
            command.setStudent(user.getStudentId());
        } else if (user.isTeacher()) {
            command.setTeacher(user.getTeacherId());
        }
        return practiceJournalService.search(user, command, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public PracticeJournalDto get(HoisUserDetails user, @WithEntity("id") PracticeJournal practiceJournal) {
        UserUtil.assertSameSchool(user, practiceJournal.getStudent().getSchool());
        return practiceJournalService.get(practiceJournal);
    }

    @PostMapping
    public PracticeJournalDto create(HoisUserDetails user,
            @Valid @RequestBody PracticeJournalForm practiceJournalForm) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return get(user, practiceJournalService.create(user.getSchoolId(), practiceJournalForm));
    }

    @PutMapping("/{id:\\d+}")
    public PracticeJournalDto save(HoisUserDetails user,
            @WithVersionedEntity(value = "id", versionRequestBody = true) PracticeJournal practiceJournal,
            @Valid @RequestBody PracticeJournalForm practiceJournalForm) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return get(user, practiceJournalService.save(practiceJournal, practiceJournalForm));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user,
            @WithVersionedEntity(value = "id", versionRequestParam = "version") PracticeJournal practiceJournal,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        practiceJournalService.delete(practiceJournal);
    }

    @GetMapping("studentPracticeModules/{studentId:\\d+}")
    public Collection<ContractStudentModuleDto> studentPracticeModules(HoisUserDetails user,
            @PathVariable Long studentId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return contractService.studentPracticeModules(user, studentId);
    }

    @GetMapping("studentPracticeSubjects/{studentId:\\d+}")
    public Collection<ContractStudentSubjectDto> studentSubjects(HoisUserDetails user, @PathVariable Long studentId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return contractService.studentPracticeHigherModules(user, studentId)
                .stream().flatMap(it -> it.getSubjects().stream()).collect(Collectors.toList());
    }

    @PutMapping("/{id:\\d+}/saveEntries/student")
    public PracticeJournalDto saveEntriesStudent(HoisUserDetails user,
            @WithEntity("id") PracticeJournal practiceJournal,
            @RequestBody PracticeJournalEntriesStudentForm practiceJournalEntriesStudentForm) {
        UserUtil.assertIsStudent(user);
        return get(user, practiceJournalService.saveEntriesStudent(practiceJournal, practiceJournalEntriesStudentForm));
    }

    @PutMapping("/{id:\\d+}/saveEntries/teacher")
    public PracticeJournalDto saveEntriesTeacher(HoisUserDetails user,
            @WithEntity("id") PracticeJournal practiceJournal,
            @RequestBody PracticeJournalEntriesTeacherForm practiceJournalEntriesTeacherForm) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return get(user, practiceJournalService.saveEntriesTeacher(practiceJournal, practiceJournalEntriesTeacherForm));
    }

    @GetMapping("/supervisor/{uuid}")
    public PracticeJournalDto supervisorGet(@PathVariable String uuid) {
        PracticeJournal practiceJournal = authAndGet(uuid);
        assertSupervisorView(practiceJournal);
        return practiceJournalService.get(practiceJournal);
    }

    @PutMapping("/supervisor/{uuid}/saveEntries")
    public PracticeJournalDto saveEntriesSupervisor(@PathVariable String uuid,
            @RequestBody PracticeJournalEntriesSupervisorForm practiceJournalEntriesSupervisorForm) {
        PracticeJournal practiceJournal = authAndGet(uuid);
        assertSupervisorView(practiceJournal);
        return practiceJournalService.get(
                practiceJournalService.saveEntriesSupervisor(practiceJournal, practiceJournalEntriesSupervisorForm));
    }

    /**
     * Uses enterprise contact person name and enterprise name as username
     *
     * An other way could be to create two separate UserDetailServices having separate authentication managers
     * and separate UserDetail objects. AuthenticationToken could be created in OncePerRequestFilter before BasicAuthenticationFilter
     */
    private PracticeJournal authAndGet(String uuid) {
        PracticeJournal practiceJournal = practiceJournalService.getFromSupervisorUrl(uuid);
        if (practiceJournal == null) {
            return null;
        }

        Enterprise enterprise = practiceJournal.getContract().getEnterprise();
        Authentication auth = new PracticeJournalUniqueUrlAuthenticationToken(uuid, enterprise);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return practiceJournal;
    }

    private static void assertSupervisorView(PracticeJournal practiceJournal) {
        if (practiceJournal == null) {
            throw new ValidationFailedException("practiceJournal.messages.noPracticeJournalFound");
        } else if (JournalStatus.PAEVIK_STAATUS_K.name()
                .equals(EntityUtil.getNullableCode(practiceJournal.getStatus()))) {
            throw new ValidationFailedException("practiceJournal.messages.acessNotAllowedJournalStatusIsConfirmed");
        } else if (LocalDate.now()
                .isAfter(practiceJournal.getContract().getEndDate().plusDays(DAYS_TO_ACCESS_AFTER_CONTRACT_END_DATE))) {
            throw new ValidationFailedException("practiceJournal.messages.acessNotAllowedContractHasEnded");
        }
    }

    public static class PracticeJournalUniqueUrlAuthenticationToken extends UsernamePasswordAuthenticationToken {
        private static final long serialVersionUID = -6508244435442364322L;
        private static final String ROLE = "ROLE_ENTERPRISE";

        public PracticeJournalUniqueUrlAuthenticationToken(String uuid, Enterprise enterprise) {
            super(getUsername(enterprise), uuid, Collections.singletonList((GrantedAuthority)(() -> ROLE)));
            setDetails(new User(getPrincipal().toString(), uuid, getAuthorities()));
        }

        private static String getUsername(Enterprise enterprise) {
            return enterprise.getContactPersonName() + " (" + enterprise.getName() + ")";
        }
    }
}
