package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.bdoc.UnsignedBdocContainer;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.report.ModuleProtocolReport;
import ee.hitsa.ois.service.AutocompleteService;
import ee.hitsa.ois.service.BdocService;
import ee.hitsa.ois.service.ModuleProtocolService;
import ee.hitsa.ois.service.PdfService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.ModuleProtocolUtil;
import ee.hitsa.ois.util.ModuleProtocolValidationUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.util.WithEntity;
import ee.hitsa.ois.util.WithVersionedEntity;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.ModuleProtocolCreateForm;
import ee.hitsa.ois.web.commandobject.ModuleProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.ModuleProtocolSearchCommand;
import ee.hitsa.ois.web.commandobject.ModuleProtocolSignForm;
import ee.hitsa.ois.web.commandobject.ProtocolCalculateCommand;
import ee.hitsa.ois.web.commandobject.TeacherAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.EntitySignDto;
import ee.hitsa.ois.web.dto.ModuleProtocolDto;
import ee.hitsa.ois.web.dto.ModuleProtocolOccupationalModuleDto;
import ee.hitsa.ois.web.dto.ModuleProtocolSearchDto;
import ee.hitsa.ois.web.dto.ModuleProtocolStudentSelectDto;
import ee.hitsa.ois.web.dto.ProtocolStudentResultDto;


@RestController
@RequestMapping("/moduleProtocols")
public class ModuleProtocolController {

    private static final String BDOC_TO_SIGN = "moduleProtocolBdocContainerToSign";

    @Autowired
    private ModuleProtocolService moduleProtocolService;
    @Autowired
    private AutocompleteService autocompleteService;
    @Autowired
    private BdocService bdocService;
    @Autowired
    private PdfService pdfService;

    @GetMapping
    public Page<ModuleProtocolSearchDto> search(HoisUserDetails user, ModuleProtocolSearchCommand command,
            Pageable pageable) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return moduleProtocolService.search(user, command, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public ModuleProtocolDto get(HoisUserDetails user, @WithEntity("id") Protocol protocol) {
        UserUtil.assertIsSchoolAdminOrTeacher(user, protocol.getSchool());
        ModuleProtocolDto dto = ModuleProtocolDto.of(protocol);
        dto.setCanBeEdited(ModuleProtocolUtil.canEdit(user, protocol));
        dto.setCanBeDeleted(ModuleProtocolUtil.canDelete(user, protocol));
        return dto;
    }

    @PostMapping
    public ModuleProtocolDto create(HoisUserDetails user,
            @Valid @RequestBody ModuleProtocolCreateForm moduleProtocolCreateForm) {
        ModuleProtocolValidationUtil.assertIsSchoolAdminOrTeacherResponsible(user, moduleProtocolCreateForm.getProtocolVdata().getTeacher());
        return get(user, moduleProtocolService.create(user, moduleProtocolCreateForm));
    }

    @PutMapping("/{id:\\d+}")
    public ModuleProtocolDto save(HoisUserDetails user,
            @WithVersionedEntity(value = "id", versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody ModuleProtocolSaveForm moduleProtocolSaveForm) {
        ModuleProtocolValidationUtil.assertCanEdit(user, protocol);
        return get(user, moduleProtocolService.save(protocol, moduleProtocolSaveForm));
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(HoisUserDetails user,
            @WithVersionedEntity(value = "id", versionRequestParam = "version") Protocol protocol,
            @SuppressWarnings("unused") @RequestParam("version") Long version) {
        ModuleProtocolValidationUtil.assertCanDelete(user, protocol);
        moduleProtocolService.delete(user, protocol);
    }

    @GetMapping("occupationModules/{curriculumVersionId:\\d+}")
    public List<AutocompleteResult> occupationModules(HoisUserDetails user, @PathVariable Long curriculumVersionId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return moduleProtocolService.occupationModules(user, curriculumVersionId);
    }

    @GetMapping("teachers")
    public List<AutocompleteResult> teachers(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return autocompleteService.teachers(user.getSchoolId(), new TeacherAutocompleteCommand());
    }

    @GetMapping("occupationModule/{curriculumVersionOccupationModuleId:\\d+}")
    public ModuleProtocolOccupationalModuleDto occupationModule(HoisUserDetails user,
            @PathVariable Long curriculumVersionOccupationModuleId) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return moduleProtocolService.occupationModule(user, curriculumVersionOccupationModuleId);
    }

    @GetMapping("/{id:\\d+}/otherStudents")
    public Collection<ModuleProtocolStudentSelectDto> otherStudents(HoisUserDetails user,
            @WithEntity("id") Protocol protocol) {
        UserUtil.assertIsSchoolAdminOrTeacher(user);
        return moduleProtocolService.otherStudents(user, protocol);
    }

    @PostMapping("/{id:\\d+}/addStudents")
    public ModuleProtocolDto addStudents(HoisUserDetails user,
            @WithVersionedEntity(value = "id", versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody ModuleProtocolSaveForm moduleProtocolSaveForm) {
        ModuleProtocolValidationUtil.assertCanEdit(user, protocol);
        return get(user, moduleProtocolService.addStudents(protocol, moduleProtocolSaveForm));
    }

    @PostMapping("/{id:\\d+}/signToConfirm")
    public EntitySignDto signToConfirm(HoisUserDetails user,
            @WithVersionedEntity(value = "id", versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody ModuleProtocolSignForm moduleProtocolSignForm, HttpSession httpSession) {
        //Administratiivne töötaja saab moodulite protokolle kinnitada ilma digiallkirjata, õpetaja peab mooduli protokollid kinnitama digiallkirjaga.

        ModuleProtocolValidationUtil.assertCanEdit(user, protocol);

        Protocol savedProtocol = moduleProtocolService.save(protocol, moduleProtocolSignForm);

        UnsignedBdocContainer unsignedBdocContainer = bdocService.createUnsignedBdocContainer("mooduli_protokoll.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                pdfService.generate(ModuleProtocolReport.TEMPLATE_NAME, new ModuleProtocolReport(savedProtocol)),
                moduleProtocolSignForm.getCertificate());

        httpSession.setAttribute(BDOC_TO_SIGN, unsignedBdocContainer);
        return EntitySignDto.of(savedProtocol, unsignedBdocContainer);
    }

    @PostMapping("/{id:\\d+}/signToConfirmFinalize")
    public ModuleProtocolDto signToConfirmFinalize(HoisUserDetails user, @WithVersionedEntity(value = "id", versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody SignatureCommand signatureCommand, HttpSession httpSession) {

        UserUtil.assertIsSchoolAdminOrTeacher(user, protocol.getSchool());

        UnsignedBdocContainer unsignedBdocContainer = (UnsignedBdocContainer) httpSession.getAttribute(BDOC_TO_SIGN);
        protocol.setOisFile(bdocService.getSignedBdoc(unsignedBdocContainer, signatureCommand.getSignature(), "protokoll"));
        httpSession.removeAttribute(BDOC_TO_SIGN);
        return get(user, moduleProtocolService.confirm(user, protocol, null));
    }

    @PostMapping("/{id:\\d+}/confirm")
    public ModuleProtocolDto confirm(HoisUserDetails user,
            @WithVersionedEntity(value = "id", versionRequestBody = true) Protocol protocol,
            @Valid @RequestBody ModuleProtocolSaveForm moduleProtocolSaveForm) {
        ModuleProtocolValidationUtil.assertCanEdit(user, protocol);
        return get(user, moduleProtocolService.confirm(user, protocol, moduleProtocolSaveForm));
    }

    @GetMapping("/{id:\\d+}/print/protocol.pdf")
    public void print(HoisUserDetails user, @WithEntity("id") Protocol protocol, HttpServletResponse response)
            throws IOException {
        UserUtil.assertIsSchoolAdminOrTeacher(user, protocol.getSchool());
        HttpUtil.pdf(response, protocol.getProtocolNr() + ".pdf",
                pdfService.generate(ModuleProtocolReport.TEMPLATE_NAME, new ModuleProtocolReport(protocol)));
    }
    
    @GetMapping("/{id:\\d+}/calculate")
    public List<ProtocolStudentResultDto> calculateGrades(HoisUserDetails user,
            @NotNull @Valid ProtocolCalculateCommand command, @WithEntity(value = "id") Protocol protocol) {
        ModuleProtocolValidationUtil.assertCanEdit(user, protocol);
        if(!Boolean.TRUE.equals(protocol.getIsVocational())) {
            throw new ValidationFailedException("not vocational protocol");
        }
        return moduleProtocolService.calculateGrades(command);
    }
}

class SignatureCommand extends VersionedCommand {

    @NotEmpty
    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
