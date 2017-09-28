package ee.hitsa.ois.web;

import java.security.Principal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ee.hitsa.ois.auth.EstonianIdCardAuthenticationToken;
import ee.hitsa.ois.auth.LoginMethod;
import ee.hitsa.ois.config.HoisJwtProperties;
import ee.hitsa.ois.domain.User;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.repository.UserRepository;
import ee.hitsa.ois.service.security.AuthenticatedUser;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.HoisUserDetailsService;
import ee.hitsa.ois.util.EntityUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class AuthenticationController {

    @Autowired
    private HoisUserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HoisJwtProperties hoisJwtProperties;

    @RequestMapping("/user")
    @ResponseBody
    public AuthenticatedUser user(Principal principal) {
        return principal != null ? userDetailsService.authenticatedUser(principal) : null;
    }

    @CrossOrigin
    @RequestMapping("/idlogin")
    @ResponseBody
    public void idlogin(Principal principal, HttpServletResponse response) {
        //one minute token
        System.out.println(hoisJwtProperties.getClaimLoginMethod());
        if (principal != null) {
            String token = Jwts.builder()
                    .setSubject(((EstonianIdCardAuthenticationToken)principal).getPrincipal().toString())
                    .claim(hoisJwtProperties.getClaimLoginMethod(), LoginMethod.ID_CARD.name())
                    .setExpiration(new Date(System.currentTimeMillis() + 60_000))
                    .signWith(SignatureAlgorithm.HS512, hoisJwtProperties.getSecret())
                    .compact();
            response.addHeader(hoisJwtProperties.getHeader(), hoisJwtProperties.getTokenPrefix() + " " + token);
        }
    }

    @PostMapping("/changeUser")
    public AuthenticatedUser updateUser(Principal principal, @RequestBody Map<String, Long> json) {
        Long id = json.get("id");

        if (principal != null && id != null) {

            HoisUserDetails oldUserDetails = HoisUserDetails.fromPrincipal(principal);
            User oldUser = userRepository.getOne(oldUserDetails.getUserId());
            User newUser = userRepository.getOne(id);
            if(!EntityUtil.getId(oldUser.getPerson()).equals(EntityUtil.getId(newUser.getPerson()))) {
                throw new HoisException(String.format("Person has no user with id : %d", id));
            }

            HoisUserDetails userDetails = userDetailsService.getHoisUserDetails(newUser);

            AbstractAuthenticationToken auth = new PreAuthenticatedAuthenticationToken(principal, "", userDetails.getAuthorities());
            auth.setDetails(userDetails);

            SecurityContextHolder.getContext().setAuthentication(auth);

            return user(SecurityContextHolder.getContext().getAuthentication());
        }
        return null;
    }

    @GetMapping("/refresh")
    public void refreshSession() {
    }
}

