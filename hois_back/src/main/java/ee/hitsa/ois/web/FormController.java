package ee.hitsa.ois.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.enums.Permission;
import ee.hitsa.ois.enums.PermissionObject;
import ee.hitsa.ois.service.FormService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.HttpUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.form.FormDefectedForm;
import ee.hitsa.ois.web.commandobject.form.FormForm;
import ee.hitsa.ois.web.commandobject.form.FormSearchForm;
import ee.hitsa.ois.web.dto.form.FormSearchDto;

@RestController
@RequestMapping("/forms")
public class FormController {

    @Autowired
    private FormService formService;

    @GetMapping
    public List<FormSearchDto> search(HoisUserDetails user, @Valid FormSearchForm criteria) {
        assertCanView(user);
        return formService.search(user, criteria);
    }

    @GetMapping("/forms.xls")
    public void excel(HoisUserDetails user, @Valid FormSearchForm criteria, HttpServletResponse response) throws IOException {
        assertCanView(user);
        HttpUtil.xls(response, "forms.xls", formService.excel(user, criteria));
    }

    @PostMapping("/new")
    public HttpUtil.NoContentResponse create(HoisUserDetails user, @Valid @RequestBody FormForm form) {
        assertCanEdit(user);
        formService.create(user, form);
        return HttpUtil.NO_CONTENT_RESPONSE;
    }

    @PostMapping("/delete")
    public HttpUtil.NoContentResponse delete(HoisUserDetails user, @Valid @RequestBody FormForm form) {
        assertCanEdit(user);
        formService.delete(user, form);
        return HttpUtil.NO_CONTENT_RESPONSE;
    }

    @PostMapping("/defected")
    public HttpUtil.NoContentResponse defected(HoisUserDetails user, @Valid @RequestBody FormDefectedForm form) {
        assertCanEdit(user);
        formService.defected(user, form);
        return HttpUtil.NO_CONTENT_RESPONSE;
    }

    private static void assertCanView(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_V, PermissionObject.TEEMAOIGUS_LOPBLANKETT);
    }

    private static void assertCanEdit(HoisUserDetails user) {
        UserUtil.assertIsSchoolAdmin(user);
        UserUtil.assertHasPermission(user, Permission.OIGUS_M, PermissionObject.TEEMAOIGUS_LOPBLANKETT);
    }
}
