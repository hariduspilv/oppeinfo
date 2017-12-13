package ee.hitsa.ois.web.sso.idp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ee.hitsa.ois.config.IdpConfiguration;

@Controller
public class SsoController {

    @Autowired
    private SAMLMessageHandler samlMessageHandler;

    @Autowired
    private IdpConfiguration idpConfiguration;
    
    @GetMapping("/SingleSignOnService")
    public void singleSignOnServiceGet(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws Exception { // TODO handle exceptions
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            samlMessageHandler.sendAuthnResponse(request, response, authentication);
        } else {
            response.sendRedirect(idpConfiguration.getLoginRedirect() + "?" + request.getQueryString());
        }
    }

}
