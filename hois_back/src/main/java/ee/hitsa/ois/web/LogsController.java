package ee.hitsa.ois.web;

import java.util.AbstractMap.SimpleEntry;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.rr.WsRrLogSchool;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.ehis.EhisLogService;
import ee.hitsa.ois.service.ekis.EkisLogService;
import ee.hitsa.ois.service.kutseregister.KutseregisterLogService;
import ee.hitsa.ois.service.kutseregister.KutseregisterService;
import ee.hitsa.ois.service.rr.PopulationRegisterLogService;
import ee.hitsa.ois.service.rtip.RtipLogService;
import ee.hitsa.ois.service.rtip.RtipService;
import ee.hitsa.ois.service.sais.SaisLogService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.KutseregisterSyncForm;
import ee.hitsa.ois.web.commandobject.RtipZemploeesForm;
import ee.hitsa.ois.web.commandobject.ehis.EhisLogCommand;
import ee.hitsa.ois.web.commandobject.sais.SaisLogCommand;
import ee.hitsa.ois.web.dto.EhisLogDto;
import ee.hitsa.ois.web.dto.EkisLogDto;
import ee.hitsa.ois.web.dto.QfLogDto;
import ee.hitsa.ois.web.dto.RtipLogDto;
import ee.hitsa.ois.web.dto.rr.PopulationRegisterChangesSearchDto;
import ee.hitsa.ois.web.dto.rr.PopulationRegisterSearchDto;
import ee.hitsa.ois.web.dto.rr.WsRrChangeLogDto;
import ee.hitsa.ois.web.dto.rr.WsRrLogDto;
import ee.hitsa.ois.web.dto.sais.SaisLogDto;

@RestController
@RequestMapping("/logs")
public class LogsController {

    @Autowired
    private EntityManager em;
    @Autowired
    private EhisLogService ehisLogService;
    @Autowired
    private EkisLogService ekisLogService;
    @Autowired
    private KutseregisterLogService kutseregisterLogService;
    @Autowired
    private KutseregisterService kutseregisterService;
    @Autowired
    private RtipLogService rtipLogService;
    @Autowired
    private RtipService rtipService;
    @Autowired
    private SaisLogService saisLogService;
    @Autowired
    private PopulationRegisterLogService rrLogService;

    @GetMapping("/ehis")
    public Page<EhisLogDto> ehisSearch(HoisUserDetails user, @Valid EhisLogCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V,
                PermissionObject.TEEMAOIGUS_ANDMEVAHETUS_EHIS);
        return ehisLogService.search(user.getSchoolId(), command, pageable);
    }

    @GetMapping("/ehis/{id:\\d+}")
    public EhisLogDto ehisGet(HoisUserDetails user, @PathVariable("id") Long id,
            @NotNull @RequestParam("messageType") String messageType) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V,
                PermissionObject.TEEMAOIGUS_ANDMEVAHETUS_EHIS);
        return ehisLogService.get(user, id, messageType);
    }

    @GetMapping("/ekis")
    public Page<EkisLogDto> ekisSearch(HoisUserDetails user, @Valid EhisLogCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V,
                PermissionObject.TEEMAOIGUS_ANDMEVAHETUS_EKIS);
        return ekisLogService.search(user.getSchoolId(), command, pageable);
    }

    @GetMapping("/ekis/{id:\\d+}")
    public EkisLogDto ekisGet(HoisUserDetails user, @PathVariable("id") Long id,
            @NotNull @RequestParam("messageType") String messageType) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V,
                PermissionObject.TEEMAOIGUS_ANDMEVAHETUS_EKIS);
        return ekisLogService.get(user, id, messageType);
    }

    @GetMapping("/kutseregister")
    public Page<QfLogDto> kutseregisterSearch(HoisUserDetails user, @Valid EhisLogCommand command, Pageable pageable) {
        UserUtil.assertIsMainAdminOrSchoolAdminOrLeadingTeacher(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_ANDMEVAHETUS_KUTSEREGISTER);
        return kutseregisterLogService.search(user, command, pageable);
    }

    @GetMapping("/kutseregister/{id:\\d+}")
    public QfLogDto kutseregisterGet(HoisUserDetails user, @PathVariable("id") Long id, @NotNull @RequestParam("messageType") String messageType) {
        UserUtil.assertIsMainAdminOrSchoolAdminOrLeadingTeacher(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_ANDMEVAHETUS_KUTSEREGISTER);
        return kutseregisterLogService.get(user, id, messageType);
    }

    // TODO remove - for testing only
    @PostMapping("/kutseregister/sync")
    public void kutseregisterSync(HoisUserDetails user, @RequestBody @Valid KutseregisterSyncForm form) {
        UserUtil.assertIsMainAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_ANDMEVAHETUS_KUTSEREGISTER);
        kutseregisterService.muutunudKutsestandardid(form.getFrom());
    }

    @GetMapping("/rtip")
    public Page<RtipLogDto> rtipSearch(HoisUserDetails user, @Valid EhisLogCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_ANDMEVAHETUS_RTIP);
        return rtipLogService.search(user.getSchoolId(), command, pageable);
    }

    @GetMapping("/rtip/{id:\\d+}")
    public RtipLogDto rtipGet(HoisUserDetails user, @PathVariable("id") Long id, @NotNull @RequestParam("messageType") String messageType) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_ANDMEVAHETUS_RTIP);
        return rtipLogService.get(user, id, messageType);
    }

    @PostMapping("/rtip/sync")
    public void rtipSync(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_ANDMEVAHETUS_RTIP);
        rtipService.syncSchoolTeacherData(user);
    }

    // TODO remove - for testing only
    @PostMapping("/rtip/zemploees")
    public void rtipZemploees(HoisUserDetails user, @RequestBody @Valid RtipZemploeesForm form) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_ANDMEVAHETUS_RTIP);
        rtipService.syncSchool(user, form.getFrom(), form.getThru());
    }

    @GetMapping("/sais")
    public Page<SaisLogDto> saisSearch(HoisUserDetails user, @Valid SaisLogCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_ANDMEVAHETUS_SAIS);
        return saisLogService.search(user.getSchoolId(), command, pageable);
    }

    @GetMapping("/sais/{id:\\d+}")
    public SaisLogDto saisGet(HoisUserDetails user, @PathVariable("id") Long id, @NotNull @RequestParam("messageType") String messageType) {
        UserUtil.assertIsSchoolAdmin(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_ANDMEVAHETUS_SAIS);
        return saisLogService.get(user, id, messageType);
    }
    
    @GetMapping("/rr/changelogs")
    public Page<WsRrChangeLogDto> rrSearchChanges(HoisUserDetails user, PopulationRegisterChangesSearchDto cmd, Pageable pageable) {
        if (cmd.getStudent() != null) {
            UserUtil.assertCanViewStudentSpecificData(user, em.getReference(Student.class, cmd.getStudent().getId()));
        } else {
            UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_RR);
        }
        return rrLogService.searchChanges(user, cmd, pageable);
    }

    @GetMapping("/rr")
    public Page<WsRrLogDto> rrSearch(HoisUserDetails user, PopulationRegisterSearchDto cmd, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_RR);
        return rrLogService.search(user, cmd, pageable);
    }

    @GetMapping("/rr/{id:\\d+}")
    public WsRrLogDto rrGet(HoisUserDetails user, @PathVariable("id") Long id) {
        WsRrLogSchool logSchool = em.getReference(WsRrLogSchool.class, id);
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, logSchool.getSchool(), Permission.OIGUS_V,
                PermissionObject.TEEMAOIGUS_RR);
        return rrLogService.get(logSchool);
    }

    @GetMapping("/rr/hasrecentchangelogs")
    public SimpleEntry<String, Boolean> hasRecentChangeLogs(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacher(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_RR);
        return new SimpleEntry<>("hasRecentChangeLogs", rrLogService.hasRecentChangeLogs(user));
    }
}
