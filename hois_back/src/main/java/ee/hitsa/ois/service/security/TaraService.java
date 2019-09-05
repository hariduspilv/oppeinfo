package ee.hitsa.ois.service.security;

import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ee.hitsa.ois.auth.IdentityTokenResponse;
import ee.hitsa.ois.auth.LoginMethod;
import ee.hitsa.ois.auth.OAuthAuthenticationToken;
import ee.hitsa.ois.config.TaraConfiguration;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.WsTaraLog;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.TaraRequestType;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.service.UserService;
import ee.hitsa.ois.util.ExceptionUtil;
import ee.hitsa.ois.util.HttpUtil;

@Transactional
@Service
public class TaraService {

    @Autowired
    private EntityManager em;
    @Autowired
    private TaraConfiguration taraConfiguration;
    @Autowired
    private HoisUserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public String authenticationRequest(String crsfToken, Language lang) throws Exception {
        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("client_id", taraConfiguration.getClientId());
        uriParams.put("response_type", "code");
        uriParams.put("scope", taraConfiguration.getScope().stream().collect(Collectors.joining(" ")));
        uriParams.put("redirect_uri", taraConfiguration.getRedirectUri());
        uriParams.put("state", crsfToken);
        if (Language.EN.equals(lang)) {
            uriParams.put("ui_locales", "en");
        }

        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(taraConfiguration.getUserAuthorizationUri());
        for (String param : uriParams.keySet()) {
            uri.queryParam(param, uriParams.get(param));
        }
        String request = uri.toUriString();
        log.info("REQUEST: {}", request);
        insertAuthenticationRequestLog(crsfToken, request, uriParams);
        return request;
    }

    public String getAuthenticatedPerson(String authCode, String state, HttpServletRequest request) throws Exception {
        insertRedirectRequestLog(state, request);

        Cookie csrfTokenCookie = HttpUtil.getCookie(request, "taraStateToken");
        if (csrfTokenCookie != null && csrfTokenCookie.getValue().equals(state)) {
            Map<String, Object> claims = getValidAccessTokenClaims(state, authCode);

            @SuppressWarnings("unchecked")
            Map<String, Object> profileAttributes = (Map<String, Object>) claims.get("profile_attributes");
            String sub = (String) claims.get("sub");
            // given idcode format: EE10101010005
            String idcode = sub.substring(2);
            String lastname = (String) profileAttributes.get("family_name");
            String firstname = (String) profileAttributes.get("given_name");

            try {
                setAuthentication(idcode, lastname, firstname);
                log.info("Person authenticated");
                return idcode;
            } catch (Exception e) {
                log.error("Authentication failed: ", e);
                throw new HoisException("Authentication failed", e);
            }
        }
        return null;
    }

    private Map<String, Object> getValidAccessTokenClaims(String state, String authCode) throws Exception {
        ResponseEntity<IdentityTokenResponse> response = null;
        LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        Exception identiyRequextException = null;

        try {
            params.set("code", authCode);
            params.set("grant_type", "authorization_code");
            params.set("redirect_uri", taraConfiguration.getRedirectUri());

            response = identityTokenRequest(params);
        } catch (Exception e) {
            identiyRequextException = e;
            log.error("Could not obtain access token: ", e);
            throw new HoisException("Could not obtain access token", e);
        } finally {
            insertIdentiyTokenRequestLog(state, taraConfiguration.getAccessTokenUri(), params,
                    response != null ? response.getBody() : null, identiyRequextException);
        }

        if (response != null && HttpStatus.OK.equals(response.getStatusCode())) {
            try {
                String idToken = response.getBody().getIdToken();
                String keyIdentifier = JwtHelper.headers(idToken).get("kid");
                Jwt tokenDecoded = JwtHelper.decodeAndVerify(idToken, verifier(keyIdentifier));

                @SuppressWarnings("unchecked")
                Map<String, Object> claims = new ObjectMapper().readValue(tokenDecoded.getClaims(), Map.class);
                if (verifyClaims(claims)) {
                    log.info("Token is valid");
                    return claims;
                }
            } catch (Exception e) {
                log.error("Could not obtain user details from token: ", e);
                throw new HoisException("Could not obtain user details from token", e);
            }
        }
        return null;
    }

    private ResponseEntity<IdentityTokenResponse> identityTokenRequest(LinkedMultiValueMap<String, Object> params) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setAcceptCharset(Arrays.asList(StandardCharsets.UTF_8));
        String base64ClientIdSec = Base64.encodeBase64String(
                (taraConfiguration.getClientId() + ":" + taraConfiguration.getClientSecret()).getBytes());
        headers.add("Authorization", "Basic " + base64ClientIdSec);

        log.info("REQUEST: {}", taraConfiguration.getAccessTokenUri());
        log.info("PARAMS: {}", params);

        HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
        ResponseEntity<IdentityTokenResponse> response = restTemplate.postForEntity(taraConfiguration.getAccessTokenUri(),
                request, IdentityTokenResponse.class);
        return response;
    }

    public boolean verifyClaims(Map<String, Object> claims) {
        boolean valid = true;

        String issuer = (String) claims.get("iss");
        if (!taraConfiguration.getIssuer().equals(issuer)) {
            valid = false;
            log.error("Issuer does not match");
        }

        String addressee = (String) claims.get("aud");
        if (!taraConfiguration.getClientId().equals(addressee)) {
            valid = false;
            log.error("Addresssee does not match");
        }

        long currentTime = Instant.now().getEpochSecond();

        Integer notBeforeTimestamp = (Integer) claims.get("nbf");
        if (!(notBeforeTimestamp.intValue() <= currentTime)) {
            valid = false;
            log.error("Certificate start time is not valid");
        }

        Integer expiredTimestamp = (Integer) claims.get("exp");
        if (!(expiredTimestamp.intValue() > currentTime)) {
            valid = false;
            log.error("Certificate has expired");
        }
        return valid;
    }

    private void setAuthentication(String idcode, String lastname, String firstname) {
        userService.createPersonUserIfNecessary(idcode, lastname, firstname);
        HoisUserDetails hoisUserDetails = userDetailsService.loadUserByUsername(idcode);

        OAuthAuthenticationToken token = new OAuthAuthenticationToken(hoisUserDetails);
        hoisUserDetails.setLoginMethod(LoginMethod.LOGIN_TYPE_T);
        token.setDetails(hoisUserDetails);
        token.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    private RsaVerifier verifier(String signatureKeyIdentifier) throws Exception {
        JwkProvider provider = new UrlJwkProvider(new URL(taraConfiguration.getJwkUri()));
        Jwk jwk = provider.get(signatureKeyIdentifier);
        return new RsaVerifier((RSAPublicKey) jwk.getPublicKey());
    }

    private WsTaraLog insertAuthenticationRequestLog(String uuid, String request, Map<String, String> uriParams)
            throws JsonProcessingException {
        WsTaraLog logRecord = new WsTaraLog();
        logRecord.setUid(uuid);
        logRecord.setType(em.getReference(Classifier.class, TaraRequestType.TARA_PARING_A.name()));
        logRecord.setRequestUrl(request);
        logRecord.setRequestParam(objectMapper.writeValueAsString(uriParams));
        logRecord.setHasErrors(Boolean.FALSE);
        em.persist(logRecord);
        return logRecord;
    }

    private WsTaraLog insertRedirectRequestLog(String uuid, HttpServletRequest request) throws JsonProcessingException {
        WsTaraLog logRecord = new WsTaraLog();
        logRecord.setUid(uuid);
        logRecord.setType(em.getReference(Classifier.class, TaraRequestType.TARA_PARING_T.name()));
        logRecord.setRequestUrl(request.getRequestURL().toString());
        logRecord.setRequestParam(objectMapper.writeValueAsString(request.getParameterMap()));
        boolean hasErrors = request.getParameter("error") != null;
        logRecord.setHasErrors(Boolean.valueOf(hasErrors));
        if (hasErrors) {
            logRecord.setLogTxt(request.getParameter("error_description"));
        }
        em.persist(logRecord);
        return logRecord;
    }

    private WsTaraLog insertIdentiyTokenRequestLog(String uuid, String requestUri,
            LinkedMultiValueMap<String, Object> params, IdentityTokenResponse response, Exception exception)
            throws JsonProcessingException {
        WsTaraLog logRecord = new WsTaraLog();
        logRecord.setUid(uuid);
        logRecord.setType(em.getReference(Classifier.class, TaraRequestType.TARA_PARING_I.name()));
        logRecord.setRequestUrl(requestUri);
        logRecord.setRequestParam(objectMapper.writeValueAsString(params));
        logRecord.setResponse(objectMapper.writeValueAsString(response));
        logRecord.setHasErrors(Boolean.valueOf(exception != null));
        logRecord.setLogTxt(exception != null ? ExceptionUtil.getRootCause(exception).toString() : null);
        em.persist(logRecord);
        return logRecord;
    }

}
