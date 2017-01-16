package ee.hitsa.ois;

import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.HoisUserDetailsService;

public class HoisUserDetailsArgumentResolver implements HandlerMethodArgumentResolver {

    private final HoisUserDetailsService hoisUserDetailsService;

    public HoisUserDetailsArgumentResolver(HoisUserDetailsService hoisUserDetailsService) {
        this.hoisUserDetailsService = hoisUserDetailsService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return HoisUserDetails.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null) {
            throw new AuthenticationCredentialsNotFoundException("No current user associated with this request");
        }
        if(!(auth instanceof AnonymousAuthenticationToken)) {
            HoisUserDetails user = HoisUserDetails.fromPrincipal(auth);
            if(user != null && user.getSchool() == null) {
                // reload to get school property filled
                user = hoisUserDetailsService.getHoisUserDetails(user.getUserId());
            }
            if(user != null) {
                return user;
            }
        }
        throw new UsernameNotFoundException("Cannot find user");
    }
}
