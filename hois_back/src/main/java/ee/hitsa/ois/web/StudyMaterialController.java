package ee.hitsa.ois.web;

import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.domain.studymaterial.StudyMaterial;
import ee.hitsa.ois.domain.studymaterial.StudyMaterialConnect;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.StudyMaterialService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.StudyMaterialUserRights;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.studymaterial.JournalSearchCommand;
import ee.hitsa.ois.web.commandobject.studymaterial.StudyMaterialForm;
import ee.hitsa.ois.web.commandobject.studymaterial.SubjectStudyPeriodSearchCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.studymaterial.JournalDto;
import ee.hitsa.ois.web.dto.studymaterial.JournalSearchDto;
import ee.hitsa.ois.web.dto.studymaterial.StudyMaterialDto;
import ee.hitsa.ois.web.dto.studymaterial.StudyMaterialSearchDto;
import ee.hitsa.ois.web.dto.studymaterial.SubjectStudyPeriodDto;
import ee.hitsa.ois.web.dto.studymaterial.SubjectStudyPeriodSearchDto;

@RestController
@RequestMapping("/studyMaterial")
public class StudyMaterialController {

    @Autowired
    private EntityManager em;
    @Autowired
    private StudyMaterialService studyMaterialService;

    @GetMapping("/subjectStudyPeriods")
    public Page<SubjectStudyPeriodSearchDto> subjectStudyPeriods(HoisUserDetails user,
            SubjectStudyPeriodSearchCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacherOrTeacher(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_OPPEMATERJAL);
        if (user.isTeacher()) {
            command.setTeacher(user.getTeacherId());
        }
        return studyMaterialService.searchSubjectStudyPeriods(user, command, pageable);
    }

    @GetMapping("/subjectStudyPeriod/{id:\\d+}")
    public SubjectStudyPeriodDto subjectStudyPeriod(HoisUserDetails user,
            @WithEntity SubjectStudyPeriod subjectStudyPeriod) {
        UserUtil.assertSameSchool(user, subjectStudyPeriod.getSubject().getSchool());
        return studyMaterialService.getSubjectStudyPeriod(user, subjectStudyPeriod);
    }

    @GetMapping("/subjectStudyPeriod/{id:\\d+}/materials")
    public List<StudyMaterialSearchDto> subjectStudyPeriodMaterials(HoisUserDetails user,
            @WithEntity SubjectStudyPeriod subjectStudyPeriod) {
        UserUtil.assertSameSchool(user, subjectStudyPeriod.getSubject().getSchool());
        return studyMaterialService.materials(user, null, subjectStudyPeriod);
    }

    @GetMapping("/subjectStudyPeriod/{id:\\d+}/teachers")
    public List<AutocompleteResult> subjectStudyPeriodTeachers(@WithEntity SubjectStudyPeriod subjectStudyPeriod,
            @RequestParam(required = false) Long studyMaterialId) {
        return studyMaterialService.subjectStudyPeriodTeachers(subjectStudyPeriod, studyMaterialId);
    }

    @GetMapping("/journals")
    public Page<JournalSearchDto> journals(HoisUserDetails user, JournalSearchCommand command, Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrLeadingTeacherOrTeacher(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_OPPEMATERJAL);
        if (user.isTeacher()) {
            command.setTeacher(user.getTeacherId());
        }
        return studyMaterialService.searchJournals(user, command, pageable);
    }

    @GetMapping("/journal/{id:\\d+}")
    public JournalDto journal(HoisUserDetails user, @WithEntity Journal journal) {
        UserUtil.assertSameSchoolOrIsMainAdminOrExternalExpert(user, journal.getSchool());
        return studyMaterialService.getJournal(user, journal);
    }

    @GetMapping("/journal/{id:\\d+}/materials")
    public List<StudyMaterialSearchDto> journalMaterials(HoisUserDetails user, @WithEntity Journal journal) {
        UserUtil.assertSameSchoolOrIsMainAdminOrExternalExpert(user, journal.getSchool());
        return studyMaterialService.materials(user, journal, null);
    }

    @GetMapping("/journal/{id:\\d+}/teachers")
    public List<AutocompleteResult> journalTeachers(@WithEntity Journal journal,
            @RequestParam(required = false) Long studyMaterialId) {
        return studyMaterialService.journalMaterialTeachers(journal, studyMaterialId);
    }

    @GetMapping("/{id:\\d+}")
    public StudyMaterialDto get(HoisUserDetails user, @WithEntity StudyMaterial material) {
        StudyMaterialUserRights.assertCanView(user, material);
        return studyMaterialService.get(user, material);
    }

    @PostMapping
    public StudyMaterialDto create(HoisUserDetails user, @Valid @RequestBody StudyMaterialForm materialForm) {
        if (user.isTeacher()) {
            materialForm.setTeacher(user.getTeacherId());
        }
        if (materialForm.getSubjectStudyPeriod() != null) {
            SubjectStudyPeriod subjectStudyPeriod = em.getReference(SubjectStudyPeriod.class,
                    materialForm.getSubjectStudyPeriod());

            StudyMaterialUserRights.canEditSubjectStudyPeriod(user, subjectStudyPeriod);
            StudyMaterial material = studyMaterialService.createSubjectStudyPeriodMaterial(user, materialForm,
                    subjectStudyPeriod);
            return studyMaterialService.get(user, material);
        } else if (materialForm.getJournal() != null) {
            Journal journal = em.getReference(Journal.class, materialForm.getJournal());

            StudyMaterialUserRights.assertCanEditJournal(user, journal);
            StudyMaterial material = studyMaterialService.createJournalMaterial(user, materialForm, journal);
            return studyMaterialService.get(user, material);
        } else {
            throw new AssertionFailedException("No subjectStudyPeriod or journal ID given");
        }
    }

    @PutMapping("/{id:\\d+}")
    public StudyMaterialDto save(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) StudyMaterial material,
            @Valid @RequestBody StudyMaterialForm materialForm) {
        StudyMaterialUserRights.assertCanEdit(user, material);
        return studyMaterialService.get(user, studyMaterialService.save(user, material, materialForm));
    }

    @PostMapping("/subjectStudyPeriod/{subjectStudyPeriodId:\\d+}/connect")
    public void connect(HoisUserDetails user, @WithEntity("subjectStudyPeriodId") SubjectStudyPeriod subjectStudyPeriod,
            @RequestParam Long studyMaterial) {
        StudyMaterialUserRights.canEditSubjectStudyPeriod(user, subjectStudyPeriod);
        studyMaterialService.connectSubjectStudyPeriod(user, subjectStudyPeriod, studyMaterial);
    }

    @DeleteMapping("/subjectStudyPeriod/{subjectStudyPeriodId:\\d+}/connect/{id:\\d+}")
    public void disconnect(HoisUserDetails user,
            @WithEntity("subjectStudyPeriodId") SubjectStudyPeriod subjectStudyPeriod,
            @WithVersionedEntity(versionRequestParam = "version") StudyMaterialConnect materialConnect,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        StudyMaterialUserRights.assertCanEditSubjectStudyPeriod(user, subjectStudyPeriod);
        studyMaterialService.delete(user, materialConnect);
    }

    @PostMapping("/journal/{journalId:\\d+}/connect")
    public void connect(HoisUserDetails user, @WithEntity("journalId") Journal journal,
            @RequestParam Long studyMaterial) {
        StudyMaterialUserRights.assertCanEditJournal(user, journal);
        studyMaterialService.connectJournal(user, journal, studyMaterial);
    }

    @DeleteMapping("/journal/{journalId:\\d+}/connect/{id:\\d+}")
    public void disconnect(HoisUserDetails user, @WithEntity("journalId") Journal journal,
            @WithVersionedEntity(versionRequestParam = "version") StudyMaterialConnect materialConnect,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        StudyMaterialUserRights.assertCanEditJournal(user, journal);
        studyMaterialService.delete(user, materialConnect);
    }

}
