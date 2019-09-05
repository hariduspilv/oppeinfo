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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import ee.hitsa.ois.auth.IdentityTokenResponse;
import ee.hitsa.ois.auth.LoginMethod;
import ee.hitsa.ois.auth.OAuthAuthenticationToken;
import ee.hitsa.ois.auth.UserInfoResponse;
import ee.hitsa.ois.config.HarIdConfiguration;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.service.UserService;
import ee.hitsa.ois.util.HttpUtil;

@Transactional
@Service
public class HarIdService {

    @Autowired
    private HarIdConfiguration harIdConfiguration;
    @Autowired
    private HoisUserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public String authenticationRequest(String crsfToken) throws Exception {
        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("client_id", harIdConfiguration.getClientId());
        uriParams.put("redirect_uri", harIdConfiguration.getRedirectUri());
        uriParams.put("response_type", "code");
        uriParams.put("scope", harIdConfiguration.getScope().stream().collect(Collectors.joining(" ")));
        uriParams.put("state", crsfToken);

        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(harIdConfiguration.getUserAuthorizationUri());
        for (String param : uriParams.keySet()) {
            uri.queryParam(param, uriParams.get(param));
        }
        String request = uri.toUriString();
        log.info("REQUEST: {}", request);
        return request;
    }

    public String getAuthenticatedPerson(String authCode, String state, HttpServletRequest request) throws Exception {
        Cookie csrfTokenCookie = HttpUtil.getCookie(request, "haridStateToken");
        if (csrfTokenCookie != null && csrfTokenCookie.getValue().equals(state)) {
            UserInfoResponse userInfo = getUserInfo(authCode);

            if (userInfo.getPersonalCode() != null) {
                // given idcode format: EE:EID:10101010005
                String idcode = userInfo.getPersonalCode().substring(7);
                String lastname = userInfo.getFamilyName();
                String firstname = userInfo.getGivenName();

                try {
                    setAuthentication(idcode, lastname, firstname);
                    log.info("Person authenticated");
                    return idcode;
                } catch (Exception e) {
                    log.error("Authentication failed: ", e);
                    throw new HoisException("Authentication failed", e);
                }
            }
            log.info(String.format("Authenticated person %s idcode not found", userInfo.getName()));
        }
        return null;
    }

    private UserInfoResponse getUserInfo(String authCode) throws Exception {
        ResponseEntity<IdentityTokenResponse> response = null;
        LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();

        try {
            params.set("code", authCode);
            params.set("grant_type", "authorization_code");
            params.set("redirect_uri", harIdConfiguration.getRedirectUri());

            response = identityTokenRequest(params);
        } catch (Exception e) {
            log.error("Could not obtain access token: ", e);
            throw new HoisException("Could not obtain access token", e);
        }

        if (response != null && HttpStatus.OK.equals(response.getStatusCode())) {
            try {
                String idToken = response.getBody().getIdToken();
                String keyIdentifier = JwtHelper.headers(idToken).get("kid");
                Jwt tokenDecoded = JwtHelper.decodeAndVerify(idToken, verifier(keyIdentifier));

                @SuppressWarnings("unchecked")
                Map<String, Object> claims = new ObjectMapper().readValue(tokenDecoded.getClaims(), Map.class);
                if (verifyClaims(claims)) {
                    ResponseEntity<UserInfoResponse> userInfo = userInfoRequest(response.getBody().getAccessToken());
                    if (claims.get("sub").equals(userInfo.getBody().getSub())) {
                        log.info("Id token is valid");
                        return userInfo.getBody();
                    }
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
                (harIdConfiguration.getClientId() + ":" + harIdConfiguration.getClientSecret()).getBytes());
        headers.add("Authorization", "Basic " + base64ClientIdSec);

        log.info("REQUEST: {}", harIdConfiguration.getAccessTokenUri());
        log.info("PARAMS: {}", params);

        HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
        ResponseEntity<IdentityTokenResponse> response = restTemplate.postForEntity(harIdConfiguration.getAccessTokenUri(),
                request, IdentityTokenResponse.class);
        return response;
    }

    private RsaVerifier verifier(String signatureKeyIdentifier) throws Exception {
        JwkProvider provider = new UrlJwkProvider(new URL(harIdConfiguration.getJwkUri()));
        Jwk jwk = provider.get(signatureKeyIdentifier);
        return new RsaVerifier((RSAPublicKey) jwk.getPublicKey());
    }

    public boolean verifyClaims(Map<String, Object> claims) {
        boolean valid = true;

        String issuer = (String) claims.get("iss");
        if (!harIdConfiguration.getIssuer().equals(issuer)) {
            valid = false;
            log.error("Issuer does not match");
        }

        String addressee = (String) claims.get("aud");
        if (!harIdConfiguration.getClientId().equals(addressee)) {
            valid = false;
            log.error("Addresssee does not match");
        }

        long currentTime = Instant.now().getEpochSecond();
        Integer expiredTimestamp = (Integer) claims.get("exp");
        if (!(expiredTimestamp.intValue() > currentTime)) {
            valid = false;
            log.error("Certificate has expired");
        }
        return valid;
    }

    private ResponseEntity<UserInfoResponse> userInfoRequest(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setAcceptCharset(Arrays.asList(StandardCharsets.UTF_8));
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<>(headers);
        ResponseEntity<UserInfoResponse> response = restTemplate.exchange(harIdConfiguration.getUserInfoUri(),
                HttpMethod.GET, request, UserInfoResponse.class);
        return response;
    }

    private void setAuthentication(String idcode, String lastname, String firstname) {
        userService.createPersonUserIfNecessary(idcode, lastname, firstname);
        HoisUserDetails hoisUserDetails = userDetailsService.loadUserByUsername(idcode);

        OAuthAuthenticationToken token = new OAuthAuthenticationToken(hoisUserDetails);
        hoisUserDetails.setLoginMethod(LoginMethod.LOGIN_TYPE_H);
        token.setDetails(hoisUserDetails);
        token.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
