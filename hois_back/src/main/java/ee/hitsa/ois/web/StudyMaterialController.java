package ee.hitsa.ois.web;

import java.util.List;

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
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.service.StudyMaterialService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.web.commandobject.studymaterial.JournalSearchCommand;
import ee.hitsa.ois.web.commandobject.studymaterial.StudyMaterialConnectForm;
import ee.hitsa.ois.web.commandobject.studymaterial.StudyMaterialForm;
import ee.hitsa.ois.web.commandobject.studymaterial.SubjectStudyPeriodSearchCommand;
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
    private StudyMaterialService studyMaterialService;

    @GetMapping("/subjectStudyPeriods")
    public Page<SubjectStudyPeriodSearchDto> subjectStudyPeriods(HoisUserDetails user, SubjectStudyPeriodSearchCommand command, 
            Pageable pageable) {
        if (user.isTeacher()) {
            command.setTeacher(user.getTeacherId());
        } else if (!user.isSchoolAdmin()) {
            throw new AssertionFailedException("User is not school admin or teacher");
        }
        return studyMaterialService.searchSubjectStudyPeriods(user, command, pageable);
    }

    @GetMapping("/subjectStudyPeriod/{id:\\d+}")
    public SubjectStudyPeriodDto subjectStudyPeriod(HoisUserDetails user, 
            @WithEntity SubjectStudyPeriod subjectStudyPeriod) {
        UserUtil.assertSameSchool(user, subjectStudyPeriod.getSubject().getSchool());
        return studyMaterialService.getSubjectStudyPeriod(subjectStudyPeriod);
    }

    @GetMapping("/subjectStudyPeriod/{id:\\d+}/materials")
    public List<StudyMaterialSearchDto> subjectStudyPeriodMaterials(HoisUserDetails user, 
            @WithEntity SubjectStudyPeriod subjectStudyPeriod) {
        UserUtil.assertSameSchool(user, subjectStudyPeriod.getSubject().getSchool());
        return materials(user, EntityUtil.getId(subjectStudyPeriod), null);
    }

    @GetMapping("/journals")
    public Page<JournalSearchDto> journals(HoisUserDetails user, JournalSearchCommand command, Pageable pageable) {
        if (user.isTeacher()) {
            command.setTeacher(user.getTeacherId());
        } else if (!user.isSchoolAdmin()) {
            throw new AssertionFailedException("User is not school admin or teacher");
        }
        return studyMaterialService.searchJournals(user, command, pageable);
    }

    @GetMapping("/journal/{id:\\d+}")
    public JournalDto journal(HoisUserDetails user, @WithEntity Journal journal) {
        UserUtil.assertSameSchool(user, journal.getSchool());
        return studyMaterialService.getJournal(journal);
    }

    @GetMapping("/journal/{id:\\d+}/materials")
    public List<StudyMaterialSearchDto> journalMaterials(HoisUserDetails user, @WithEntity Journal journal) {
        UserUtil.assertSameSchool(user, journal.getSchool());
        return materials(user, null, EntityUtil.getId(journal));
    }

    private List<StudyMaterialSearchDto> materials(HoisUserDetails user, Long subjectStudyPeriodId, Long journalId) {
        Boolean isPublic = user.isRepresentative() ? Boolean.TRUE : null;
        Boolean isVisibleToStudents = user.isStudent() ? Boolean.TRUE : null;
        return studyMaterialService.materials(subjectStudyPeriodId, journalId, isPublic, isVisibleToStudents);
    }

    @GetMapping("/{id:\\d+}")
    public StudyMaterialDto get(HoisUserDetails user, @WithEntity StudyMaterial material) {
        assertAccess(user, material);
        return studyMaterialService.get(material);
    }

    @PostMapping
    public StudyMaterialDto create(HoisUserDetails user, @Valid @RequestBody StudyMaterialForm materialForm) {
        if (user.isTeacher()) {
            materialForm.setTeacher(user.getTeacherId());
        } else if (!user.isSchoolAdmin()) {
            throw new AssertionFailedException("User is not school admin or teacher");
        }
        if (materialForm.getSubjectStudyPeriod() != null) {
            return get(user, studyMaterialService.createSubjectStudyPeriodMaterial(user, materialForm));
        } else if (materialForm.getJournal() != null) {
            return get(user, studyMaterialService.createJournalMaterial(user, materialForm));
        } else {
            throw new AssertionFailedException("No subjectStudyPeriod or journal ID given");
        }
    }

    @PutMapping("/{id:\\d+}")
    public StudyMaterialDto save(HoisUserDetails user,
            @WithVersionedEntity(versionRequestBody = true) StudyMaterial material,
            @Valid @RequestBody StudyMaterialForm materialForm) {
        assertAccess(user, material);
        return get(user, studyMaterialService.save(user, material, materialForm));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user,
            @WithVersionedEntity(versionRequestParam = "version") StudyMaterial material,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        assertAccess(user, material);
        studyMaterialService.delete(user, material);
    }

    @PostMapping("/connect")
    public void connect(HoisUserDetails user, @Valid @RequestBody StudyMaterialConnectForm connectForm) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        if (connectForm.getSubjectStudyPeriod() != null) {
            studyMaterialService.connect(user, connectForm.getStudyMaterial(), connectForm.getSubjectStudyPeriod(), null);
        } else if (connectForm.getJournal() != null) {
            studyMaterialService.connect(user, connectForm.getStudyMaterial(), null, connectForm.getJournal());
        } else {
            throw new AssertionFailedException("No subjectStudyPeriod or journal ID given");
        }
    }

    @DeleteMapping("/connect/{id:\\d+}")
    public void disconnect(HoisUserDetails user,
            @WithVersionedEntity(versionRequestParam = "version") StudyMaterialConnect materialConnect,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        assertAccess(user, materialConnect.getStudyMaterial());
        studyMaterialService.delete(user, materialConnect);
    }

    private static void assertAccess(HoisUserDetails user, StudyMaterial material) {
        if (!(UserUtil.isSchoolAdmin(user, material.getSchool()) 
                || (user.isTeacher() && user.getTeacherId().equals(EntityUtil.getId(material.getTeacher()))))) {
            throw new AssertionFailedException("User is not school admin or teacher who created the material");
        }
    }

}
