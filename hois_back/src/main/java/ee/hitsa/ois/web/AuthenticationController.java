package ee.hitsa.ois.web;

import java.security.Principal;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import ee.hitsa.ois.service.UserService;
import ee.hitsa.ois.service.security.AuthenticatedUser;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.HoisUserDetailsService;
import ee.hitsa.ois.service.security.LdapService;
import ee.hitsa.ois.service.security.MobileIdLoginService;
import ee.hitsa.ois.service.security.MobileIdSession;
import ee.hitsa.ois.service.security.MobileIdSessionResponse;
import ee.hitsa.ois.service.security.MobileIdStatus;
import ee.hitsa.ois.util.EntityUtil;
import io.jsonwebtoken.Claims;
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
    @Autowired
    private MobileIdLoginService mobileIdService;
    @Autowired
    private LdapService ldapService;
    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    @ResponseBody
    public AuthenticatedUser user(HttpServletRequest request, Principal principal) {
        return principal != null ? userDetailsService.authenticatedUser(request, principal) : null;
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
                    .claim(hoisJwtProperties.getClaimLoginMethod(), LoginMethod.LOGIN_TYPE_I.name())
                    .setExpiration(new Date(System.currentTimeMillis() + 60_000))
                    .signWith(SignatureAlgorithm.HS512, hoisJwtProperties.getSecret())
                    .compact();
            addJwtHeader(response, token);
        }
    }

    private void addJwtHeader(HttpServletResponse response, String token) {
        response.addHeader(hoisJwtProperties.getHeader(), hoisJwtProperties.getTokenPrefix() + " " + token);
    }

    @PostMapping("/mIdLogin")
    public MobileIdSessionResponse mIdLogin(@RequestBody Map<String, String> json, HttpServletResponse response) {
        String mobileNumber = json.get("mobileNumber");
        MobileIdSession session = mobileIdService.login(mobileNumber);
        MobileIdSessionResponse result = new MobileIdSessionResponse();
        if (session.getErrorCode() != null) {
            result.setErrorCode(MobileIdLoginService.NOT_MOBILE_ID_USER_ERROR.equals(session.getErrorCode())
                    ? MobileIdLoginService.NOT_MOBILE_ID_USER_ERROR : MobileIdLoginService.GENERAL_ERROR);
            return result;
        }
        String token = Jwts.builder()
                .setSubject(session.getUserIDCode())
                .claim(hoisJwtProperties.getClaimSesscode(), session.getSesscode())
                .claim(hoisJwtProperties.getClaimGivenname(), session.getUserGivenname())
                .claim(hoisJwtProperties.getClaimSurname(), session.getUserSurname())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(4)))
                .signWith(SignatureAlgorithm.HS512, hoisJwtProperties.getSecret())
                .compact();
        addJwtHeader(response, token);
        result.setChallengeID(session.getChallengeID());
        return result;
    }

    @RequestMapping("/mIdStatus")
    public MobileIdStatus mIdStatus(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute(Claims.class.getName());
        if (claims == null) {
            MobileIdStatus status = new MobileIdStatus();
            status.setStatus("NOT_AUTHORIZED");
            return status;
        }
        Integer sesscode = (Integer) claims.get(hoisJwtProperties.getClaimSesscode());
        MobileIdStatus status = mobileIdService.status(sesscode.intValue());
        if ("USER_AUTHENTICATED".equals(status.getStatus())) {
            String idcode = claims.getSubject();
            String lastname = (String) claims.get(hoisJwtProperties.getClaimSurname());
            String firstname = (String) claims.get(hoisJwtProperties.getClaimGivenname());
            userService.createPersonUserIfNecessary(idcode, lastname, firstname);
            String username = claims.getSubject();
            HoisUserDetails hoisUserDetails = userDetailsService.loadUserByUsername(username);
            EstonianIdCardAuthenticationToken token = new EstonianIdCardAuthenticationToken(username);
            hoisUserDetails.setLoginMethod(LoginMethod.LOGIN_TYPE_M);
            token.setDetails(hoisUserDetails);
            token.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        return status;
    }

    @PostMapping("/ldap")
    public void ldap(@RequestBody Map<String, String> json, HttpServletResponse response) {
        String username = json.get("username");
        String password = json.get("password");
        if (username == null || password == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        Long schoolId = Long.valueOf(json.get("school"));
        String idcode = ldapService.getIdCode(schoolId, username, password);
        if (idcode == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        HoisUserDetails hoisUserDetails = userDetailsService.loadUserByUsername(idcode);
        EstonianIdCardAuthenticationToken token = new EstonianIdCardAuthenticationToken(idcode);
        hoisUserDetails.setLoginMethod(LoginMethod.LOGIN_TYPE_K);
        token.setDetails(hoisUserDetails);
        token.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @PostMapping("/changeUser")
    public AuthenticatedUser updateUser(HttpServletRequest request, Principal principal, @RequestBody Map<String, Long> json) {
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

            return user(request, SecurityContextHolder.getContext().getAuthentication());
        }
        return null;
    }

    @GetMapping("/refresh")
    public void refreshSession() {
    }
}

