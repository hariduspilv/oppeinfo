package ee.hitsa.ois.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import ee.hitsa.ois.auth.EstonianIdCardAuthenticationToken;
import ee.hitsa.ois.auth.LoginMethod;
import ee.hitsa.ois.config.HoisJwtProperties;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.service.security.HoisUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private HoisJwtProperties hoisJwtProperties;
    private HoisUserDetailsService hoisUserDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, HoisUserDetailsService hoisUserDetailsService, HoisJwtProperties jwtProperties) {
        super(authenticationManager);
        this.hoisUserDetailsService = hoisUserDetailsService;
        this.hoisJwtProperties = jwtProperties;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(hoisJwtProperties.getHeader());

        if (header == null || !header.startsWith(hoisJwtProperties.getTokenPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        Claims claims = getClaims(request);
        if(claims != null) {
            String username = claims.getSubject();
            String loginMethod = (String) claims.get(hoisJwtProperties.getClaimLoginMethod());

            HoisUserDetails hoisUserDetails = hoisUserDetailsService.loadUserByUsername(username);
            if (hoisUserDetails != null) {
                PreAuthenticatedAuthenticationToken token = null;
                if (LoginMethod.ID_CARD.name().equals(loginMethod)) {
                    token = new EstonianIdCardAuthenticationToken(username);
                    hoisUserDetails.setLoginMethod(LoginMethod.ID_CARD);
                } else {
                    token = new PreAuthenticatedAuthenticationToken(username, username);
                }
                token.setDetails(hoisUserDetails);
                token.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * This method throws exception when token is invalid (expired, tampered etc)
     */
    private Claims getClaims(HttpServletRequest request) {
        String token = request.getHeader(hoisJwtProperties.getHeader());
        if (token != null) {
            return Jwts.parser()
                    .setSigningKey(hoisJwtProperties.getSecret())
                    .parseClaimsJws(token.replace(hoisJwtProperties.getTokenPrefix(), ""))
                    .getBody();
        }
        return null;
    }

}
